package es.burke.gotta.dll;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.python.core.Py;
import org.python.core.PyFile;
import org.python.core.PyFunction;
import org.python.core.PyJavaType;
import org.python.core.PyList;
import org.python.core.PyModule;
import org.python.core.PyObject;
import org.python.core.PyReflectedFunction;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import es.burke.gotta.Accion;
import es.burke.gotta.AccionDLL;
import es.burke.gotta.Aplicacion;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorArrancandoAplicacion;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Filas;
import es.burke.gotta.GestorMetaDatos;
import es.burke.gotta.Lote;
import es.burke.gotta.Motor;
import es.burke.gotta.Util;

public class DLLJython extends DLLGotta{	
	public AccionDLL accion;
	private String guion;
	public DLLJython(AccionDLL acc) 
		{accion=acc;}
	public boolean comprueba(){
		String rs;
		try {
			rs = leeGuion();
			} 
		catch(ErrorGotta eg) {
			return false;
		}		
		return rs.length()>0;
	}
	private String leeGuion() throws ErrorGotta{
		Lote lote=this.accion.lote;
		if (lote.guiones.containsKey(this.accion.dll))
			return lote.guiones.get(this.accion.dll);
		
		Filas filas = GestorMetaDatos.leeDLL_Guiones(this.accion.conn(), this.accion.dll);
		String xguion=GestorMetaDatos.concatenaResultado(filas, Constantes.vbCr);
		
		lote.guiones.put(this.accion.dll, xguion);
		
		return xguion;
		}
	public static PythonInterpreter getJython(String nombre, String fuente, Aplicacion apli) throws ErrorAccionDLL {
		fuente=fuente.replace('\r', '\n');
		PythonInterpreter interp = apli.getJython();
		if (!apli.cacheJython.contains(nombre+"\n"+fuente)) {
			synchronized (apli.cacheJython){
			    try{
			    	ByteArrayInputStream src = new ByteArrayInputStream(fuente.getBytes("UTF-8"));
					PyReflectedFunction new_module = (PyReflectedFunction) interp.eval("imp.new_module"); //$NON-NLS-1$
 					PyModule s = (PyModule) new_module.__call__(new PyString(nombre));
 					interp.set(nombre, s);
 					Py.exec(new PyFile(src), s.__dict__,s.__dict__);
			    }
			    catch (Exception e) {
					throw new ErrorAccionDLL(e);
				}
			    apli.cacheJython.add(nombre+"\n"+fuente);
				}
			}
		return interp;
		}
	public static PythonInterpreter nuevoJython(String rutaBase, String aplicacion) {
		String rutaClases=rutaBase+"/WEB-INF/classes"; //$NON-NLS-1$
		String libLocal = rutaBase+"/Aplicaciones/"+aplicacion+"/lib"; //$NON-NLS-1$ //$NON-NLS-2$
		PythonInterpreter interp=new PythonInterpreter();
		
		Properties props = new Properties(); 
		props.setProperty("python.home", "/opt/jython"); 
		String[] args = {"0",""}; 
		PythonInterpreter.initialize(System.getProperties(), props, args);
		
		interp.exec("import sys"); //$NON-NLS-1$
		interp.exec("import imp"); //$NON-NLS-1$
		PyList path = (PyList) interp.eval("sys.path"); //$NON-NLS-1$
		meteSiNoEstá(path,rutaClases);
		File flibLocal = new File(libLocal);
		File[] files = flibLocal.listFiles();
		if (files!=null){
			meteSiNoEstá(path,flibLocal.getAbsolutePath());
			for(File f:files)
				if (f.getName().toLowerCase().endsWith("jar")) //$NON-NLS-1$
					meteSiNoEstá(path,f.getAbsolutePath());
			}
		interp.setErr(new PrintStream(new
				JscLoggingOutputStream(Util.getLogger(aplicacion),
			             Level.ERROR), true));
		return interp;
	}
	private static void meteSiNoEstá(PyList l, String s){
		if (!l.contains(s))
			l.add(s);
	}
	public void verifica() throws ErrorGotta {
		guion = leeGuion();
		PythonInterpreter interp = getJython(this.accion.dll, guion, this.usr.getApli());
		PyFunction verifica = (PyFunction)((PyModule)interp.get(this.accion.dll)).__getattr__("verifica"); //$NON-NLS-1$
		verifica.__call__(PyJavaType.wrapJavaObject(accion.motor),new PyString(accion.accion),pyStringOrNone(accion.parametro));
		}
	public Object ejecuta(String valor) throws ErrorGotta  {
		PythonInterpreter interp = getJython(this.accion.dll, guion, this.usr.getApli());
		PyFunction ejecuta = (PyFunction)((PyModule)interp.get(this.accion.dll)).__getattr__("ejecuta"); //$NON-NLS-1$
		
		try {
			PyObject param;
			param = pyStringOrNone(valor);
			PyObject ret = ejecuta.__call__(PyJavaType.wrapJavaObject(accion.motor),new PyString(accion.accion),param);
			return ret.__tojava__(Object.class);
			}
		catch (Exception e) {
			String msg="Error ejecutando "+this.accion.dll+"."+this.accion.accion+"("+valor+")";
			accion.motor.getApli().println(msg);
			accion.motor.getApli().println(e);
			throw new ErrorAccionDLL(msg +Constantes.ESPACIO + e.toString());
			}
		}
	public Object revivir(String valor) throws ErrorGotta {
		PythonInterpreter interp = getJython(this.accion.dll, guion, this.usr.getApli());
		PyFunction revivir = (PyFunction)((PyModule)interp.get(this.accion.dll)).__getattr__("revivir"); //$NON-NLS-1$
		
		try {
			PyObject param;
			param = pyStringOrNone(valor);
			PyObject ret = revivir.__call__(PyJavaType.wrapJavaObject(accion.motor),new PyString(accion.accion),param);
			return ret.__tojava__(Object.class);
			}
		catch (Exception e) {
			String msg="Error ejecutando "+this.accion.dll+"."+this.accion.accion+"("+valor+")";
			accion.motor.getApli().println(msg);
			accion.motor.getApli().println(e);
			throw new ErrorAccionDLL(msg +Constantes.ESPACIO + e.toString());
			}
		
		}	
	private PyObject pyStringOrNone(String param) {
		if (param!=null)
			return new PyString(param);
		return Py.None;
		}
	@Override
	public String accionesValidas() {
		PythonInterpreter interp;
		try {
			interp = getJython(this.accion.dll, guion, this.usr.getApli());
			} 
		catch (ErrorAccionDLL e) {
			return null;
			} 
		catch (ErrorArrancandoAplicacion e) {
			return null;
			}
		return interp.get("accionesDisponibles").toString(); //$NON-NLS-1$
	}

	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req,
			HttpServletResponse res, HttpSession sesion, String numprod)
			throws IOException, ErrorGotta, ServletException {
		PythonInterpreter interp = getJython(this.accion.dll, guion,this.usr.getApli());
		PyFunction sacaProducto = (PyFunction)((PyModule)interp.get(this.accion.dll)).__getattr__("sacaProducto"); //$NON-NLS-1$
		try {
			
			sacaProducto.__call__(
					PyJavaType.wrapJavaObject(this),
					PyJavaType.wrapJavaObject(req),
					PyJavaType.wrapJavaObject(res),
					new PyString(numprod)
					);
			}
		catch (Exception e) {
			String msg="Error en producto "+this.accion.dll+"."+this.accion.accion;
			accion.motor.getApli().println(msg);
			throw new ErrorAccionDLL(msg +Constantes.ESPACIO + e.toString());
			}
	}
	@Override
	public void verifica(Motor motor, String xaccion, String nombre)
			throws ErrorGotta {
		this.usr=motor.usuario;
		this.verifica();
	}
	@Override
	public Object ejecuta(Accion xaccion, Object valor) throws ErrorGotta {
		return this.ejecuta(Util.noNulo(valor));
	}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return this.revivir(Util.noNulo(valor)); 
	}
} 

/**
* An OutputStream that flushes out to a Category.<p>
* <p/>
* Note that no data is written out to the Category until the stream is
* flushed or closed.<p>
* <p/>
* Example:<pre>
* // make sure everything sent to System.err is logged
* System.setErr(new PrintStream(new
* JscLoggingOutputStream(Category.getRoot(),
* Priority.WARN), true));
* <p/>
* // make sure everything sent to System.out is also logged
* System.setOut(new PrintStream(new
* JscLoggingOutputStream(Category.getRoot(),
* Priority.INFO), true));
* </pre>
*
* @author <a href="[EMAIL PROTECTED]">Jim Moore</a>
* @see Category
*/

//
class JscLoggingOutputStream extends OutputStream {

static Logger myLogger = Logger.getLogger(JscLoggingOutputStream.class.getName());

 /**
  * Used to maintain the contract of [EMAIL PROTECTED] #close()}.
  */
 protected boolean hasBeenClosed = false;

 /**
  * The internal buffer where data is stored.
  */
 protected byte[] buf;

 /**
  * The number of valid bytes in the buffer. This value is always
  * in the range <tt>0</tt> through <tt>buf.length</tt>; elements
  * <tt>buf[0]</tt> through <tt>buf[count-1]</tt> contain valid
  * byte data.
  */
 protected int count;

 /**
  * Remembers the size of the buffer for speed.
  */
 private int bufLength;

 /**
  * The default number of bytes in the buffer. =2048
  */
 public static final int DEFAULT_BUFFER_LENGTH = 2048;


 /**
  * The category to write to.
  */
 protected Logger logger;

 /**
  * The priority to use when writing to the Category.
  */
 protected Level level;

 /**
  * Creates the JscLoggingOutputStream to flush to the given Category.
  *
  * @param log      the Logger to write to
  * @param level the Level to use when writing to the Logger
  * @throws IllegalArgumentException if cat == null or priority ==
  * null
  */
 public JscLoggingOutputStream(Logger log, Level level)
 throws IllegalArgumentException {
   if (log == null) {
     throw new IllegalArgumentException("cat == null");
   }
   if (level == null) {
     throw new IllegalArgumentException("priority == null");
   }

this.level = level;

   logger = log;
   bufLength = DEFAULT_BUFFER_LENGTH;
   buf = new byte[DEFAULT_BUFFER_LENGTH];
   count = 0;
 }


 /**
  * Closes this output stream and releases any system resources
  * associated with this stream. The general contract of
  * <code>close</code>
  * is that it closes the output stream. A closed stream cannot
  * perform
  * output operations and cannot be reopened.
  */
 @Override
public void close() {
   flush();
   hasBeenClosed = true;
 }


 /**
  * Writes the specified byte to this output stream. The general
  * contract for <code>write</code> is that one byte is written
  * to the output stream. The byte to be written is the eight
  * low-order bits of the argument <code>b</code>. The 24
  * high-order bits of <code>b</code> are ignored.
  *
  * @param b the <code>byte</code> to write
  * @throws IOException if an I/O error occurs. In particular,
  *                     an <code>IOException</code> may be thrown if
  * the
  *                     output stream has been closed.
  */
 @Override
public void write(final int b) throws IOException {
   if (hasBeenClosed) {
     throw new IOException("The stream has been closed.");
   }

// would this be writing past the buffer?

   if (count == bufLength) {
     // grow the buffer
     final int newBufLength = bufLength + DEFAULT_BUFFER_LENGTH;
     final byte[] newBuf = new byte[newBufLength];

System.arraycopy(buf, 0, newBuf, 0, bufLength); buf = newBuf;

     bufLength = newBufLength;
   }

buf[count] = (byte) b;

   count++;
 }


 /**
  * Flushes this output stream and forces any buffered output bytes
  * to be written out. The general contract of <code>flush</code> is
  * that calling it is an indication that, if any bytes previously
  * written have been buffered by the implementation of the output
  * stream, such bytes should immediately be written to their
  * intended destination.
  */
 @Override
public void flush() {

if (count == 0) {

     return;
   }

// don't print out blank lines; flushing from PrintStream puts

   // out these

// For linux system

   if (count == 1 && ((char) buf[0]) == '\n') {
     reset();
     return;
   }

// For mac system

   if (count == 1 && ((char) buf[0]) == '\r') {
     reset();
     return;
   }

// On windows system

   if (count==2 && (char)buf[0]=='\r' && (char)buf[1]=='\n') {
     reset();
     return;
   }

final byte[] theBytes = new byte[count]; System.arraycopy(buf, 0, theBytes, 0, count); logger.log(level, new String(theBytes)); reset();

 }


 private void reset() {
   // not resetting the buffer -- assuming that if it grew then it
   //   will likely grow similarly again
   count = 0;
 }

}

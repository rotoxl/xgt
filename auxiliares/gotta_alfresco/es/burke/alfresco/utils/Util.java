package es.burke.alfresco.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
//import org.python.core.PyModule;
//import org.python.core.PyString;
//import org.python.core.PyType;
//import org.python.util.PythonInterpreter;

//import es.burke.gotta.Constantes;
//import es.burke.gotta.ErrorArrancandoAplicacion;
//import es.burke.gotta.ErrorConexionPerdida;
//import es.burke.gotta.PoolAplicaciones;
//import es.burke.gotta.Usuario;

public final class Util {
	public static void inspeccionar(Object o){
		System.out.println(o);
	}
	public static Logger log=getLogger("alfresco-gotta");
	public static Logger getLogger(String name){
		return Logger.getLogger(name);
	}

	public static String rutaFisicaRelativaOAbsoluta(String ruta){
		//String barra="/";
		ruta=arreglaRutaFisica(ruta);
		//boolean acabaEnBarra=ruta.endsWith(barra);
//		if (ruta.startsWith(Constantes.PUNTO)) {
//			ruta=PoolAplicaciones.ctx.getRealPath(ruta)+(acabaEnBarra?barra:"");
//			ruta=arreglaRutaFisica(ruta);
//			}
		return ruta;
		}

 	public static String rutaWebRelativaOAbsoluta(String UrlAplicacion, String ruta) throws Exception{ 
 		ruta=ruta.trim();
 	 	if (ruta.startsWith(Constantes.PUNTO)){
 	 		/*pass*/
 	 		}
 	 	else if (ruta.startsWith("http:") || ruta.startsWith("ftp:") || ruta.startsWith("https:") || ruta.startsWith("file:") ){
 	 		/*pass*/
	 		}
 	 	else
 	 		ruta=UrlAplicacion+ruta.substring(1); 
 	 	return ruta; 
 	 	}

	
	public static String replaceUltimo(String cadena, String car_a_sustituir, String car) {
		if (cadena.contains(car_a_sustituir)) {
			int inicio=cadena.lastIndexOf(car_a_sustituir);
			int fin=car_a_sustituir.length();
			StringBuffer stb=new StringBuffer(cadena);
			String aux = (stb.replace(inicio,inicio+fin,car)).toString();
			return aux;
			}
		return cadena;
		}
	public static String replaceUno(String cadena, String car_a_sustituir, String car) {
		return replace(cadena, car_a_sustituir, car, 1);
		}
	
	public static String replaceTodos(String cadena, String car_a_sustituir, String car) {
		return replace(cadena, car_a_sustituir, car, 0);
		}

	public static String replace(String cadena, String car_a_sustituir, String car, int pcuantos) {
		int cuantos=pcuantos;
		
		if(cadena==null)
			return cadena;

		String cadenaret = cadena;
		int desde, hasta;
		desde=0;
		hasta= cadenaret.indexOf(car_a_sustituir);
		boolean todos = (cuantos==0);
		int len1 = car_a_sustituir.length();
		int len2 = 0;
		if (car!=null) len2=car.length();

		while (hasta >= 0 && (cuantos > 0 || todos)) {
			cadenaret = cadenaret.substring(0, hasta) + car + cadenaret.substring(hasta+len1);
			desde = hasta+len2;
			hasta=cadenaret.indexOf(car_a_sustituir, desde);
			cuantos -= 1;
			}
	return cadenaret;
	}


	public static byte[] getFileContentsAsByteArray(File f) throws IOException {
        byte data[];
        DataInputStream is = new DataInputStream(new FileInputStream(f));
        try{
	        data = new byte[(int)f.length()];
	        is.readFully(data);
	        return data;
        }
        finally{
        	is.close();
        }
    }

	public static String arreglaRutaFisica(String ruta){
		String ret=ruta;
		String barraWin="\\"; String barra="/";
		String dosBarras="//";
		
		boolean empieza=false;
		empieza=ruta.startsWith(dosBarras) || ruta.startsWith(barraWin+barraWin);
		ret=Util.replaceTodos(ret, barraWin, barra);
		ret=Util.replaceTodos(ret, dosBarras, barra);
		
		if (empieza)
			ret=barra+ret;
		return ret;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	//*********** FUNCIONES DE GESTION DE FICHEROS **************************************
	/////////////////////////////////////////////////////////////////////////////////////
    
    public static void copyFile(File in, File out) throws IOException 
		{
		   FileChannel inChannel = new
		       FileInputStream(in).getChannel();
		   FileChannel outChannel = new
		       FileOutputStream(out).getChannel();
		   try {
		       inChannel.transferTo(0, inChannel.size(),
		               outChannel);
		   } 
		   catch (IOException e) {
		       throw e;
		   }
		   finally {
		       if (inChannel != null) inChannel.close();
		       if (outChannel != null) outChannel.close();
		   }
		}
	   
    public static void writeToFile(InputStream is, File file) {
			try {
				DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
				int c;
				while((c = is.read()) != -1) {
					out.writeByte(c);
				}
				is.close();
				out.close();
			}
			catch(IOException e) {
				System.err.println("Error Writing/Reading Streams.");
			}
		}
    
	public static byte[] iStream2ByteA(InputStream inpt) throws IOException {
		InputStream 		  is 	  = null;
        ByteArrayOutputStream bos 	  = new ByteArrayOutputStream();
        byte[ ] 	almacenamiento    = new byte[4 * 1024];
        int 		bytesLeidos;
        byte[] 		bytesDoc		  = null;
        try {
           is = new BufferedInputStream(inpt);
           while ((bytesLeidos = is.read(almacenamiento)) != -1) {
              bos.write(almacenamiento, 0, bytesLeidos);
           }
           bytesDoc = bos.toByteArray();
        } finally {
              if (is != null) {
                    is.close( );
              }             
              bos.close();    
        }	
        return bytesDoc;
	}

	public static byte[] leerFichero(String nombrefich, String rutafich) throws IOException{
		File rutaCompletafich = new File(rutafich + nombrefich);
		return Util.getFileContentsAsByteArray(rutaCompletafich);

	}
	public static Boolean escribirAFichero(String archivo, byte[] buffer) throws IOException{
	    FileOutputStream fos = new FileOutputStream(archivo);
	    try {
		    for (int i= 0; i< buffer.length; i++) {
		    	fos.write(buffer[i]);	
		    }
	    }
	    finally {
	    	fos.flush();
	    	fos.close();
	    }
		return true;
	}

}

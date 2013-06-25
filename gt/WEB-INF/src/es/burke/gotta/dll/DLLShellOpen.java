package es.burke.gotta.dll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Accion;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Motor;
import es.burke.gotta.Util;
import es.burke.gotta.Constantes;


public class DLLShellOpen extends DLLGotta {
	private Accion accion;
	private String comando;
	
	@Override
	public String accionesValidas() {
		return "ejecutarEnServidor ejecutarEnCliente abrir abrirUrlEnCliente";
		}
	@Override
	public Object ejecuta(Accion accion, Object valor) throws ErrorGotta {
		this.accion=accion;
		
		this.comando=valor.toString();
		if (this.accion.accion.equalsIgnoreCase("abrirUrlEnCliente") || this.accion.accion.equalsIgnoreCase("abrir")){
			abrirURLenCliente(this.comando);
			return null;
			}
		else if (this.accion.accion.equalsIgnoreCase("ejecutarEnServidor")){
			return ejecutarEnServidor(this.comando);
			}
		else if (this.accion.accion.equalsIgnoreCase("ejecutarEnCliente")){
			String claveDocu = ""+this.hashCode();
			
			DLLShellOpen this2;
			if (usr.productosGenerados.containsKey(claveDocu)) {
				// si está dentro de un bucle no queremos reciclar esto
				this2=new DLLShellOpen();
				claveDocu = ""+this2.hashCode();
				this2.usr=this.usr;
				this2.mMotor=this.mMotor;	
				}
			else
				this2=this;	
			this2.síncrono=true;
			
			this2.accion=this.accion;
			this2.comando=this.comando;
			
			usr.productosGenerados.put(claveDocu, this2);
			return claveDocu;
			}
		return null;
		}

	private void abrirURLenCliente(String ruta){
		/*OJO! los archivos tienen que estar dentro de lo publicado por el servidor web 
		 * 		(lo suyo es el directorio de la aplicación), y poniéndole en ruta
		 * 		una ruta web válida, relativa, en plan ./Aplicaciones/Embla/Manual%20Embla%20Educ.doc
		 * 		(cuidado con los espacios)
		 *  */
		this.comando=Util.rutaWebRelativaOAbsoluta(this.mMotor.getUsuario(), ruta);
		
		usr.productosGenerados.put(""+this.hashCode(), this);
		this.añadeMSG("Archivo descargado desde ["+this.comando+"]", Constantes.TipoMensajeGotta.archivos);
		}
	private String ejecutarEnServidor(String cmdline) throws ErrorAccionDLL{
		//cmdline="cmd /c start iexplore http://www.google.es"
		try {
			Runtime run = Runtime.getRuntime(); 
			Process pr = null; 
			pr = run.exec(cmdline); 
			pr.waitFor(); 
			
			String ret="";
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream())); 
			String line = ""; 
			while ((line=buf.readLine())!=null) { 
				ret+=line; 
				} 
			return ret;
			} 
		catch (IOException e) {
			throw new ErrorAccionDLL("Error al ejecutar DLL.DLLShellOpen.abrirEnServidor '"+cmdline+"': el error que se ha producido ha sido "+e.getMessage());
			}
		catch (InterruptedException e) { 
			throw new ErrorAccionDLL("Error al ejecutar DLL.DLLShellOpen.abrirEnServidor '"+cmdline+"': el error que se ha producido ha sido "+e.getMessage());
			} 
		
		}
		
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta, ServletException {
		if (this.accion.accion.equalsIgnoreCase("abrirUrlEnCliente") || this.accion.accion.equalsIgnoreCase("abrir")){
			ServletOutputStream salida = res.getOutputStream();
			res.setContentType("text/html");
			res.setHeader("Refresh", "1;URL=\""+this.comando+"\"");
			salida.write(  ("Redirigiendo a <a href=\""+this.comando+"\">"+this.comando+"</a>").getBytes()  );
			}
		else if (this.accion.accion.equalsIgnoreCase("ejecutarEnCliente")){
			DLLShellOpen this2 = (DLLShellOpen) usr.productosEnviados.get(numprod);
			RequestDispatcher rd = inf2.getServletContext().getRequestDispatcher("/AppletShell.jsp");
			req.setAttribute("accion", this2.accion);
			req.setAttribute("comando", this2.comando);
			rd.forward(req, res);
			}
		
		}
	@Override
	public void verifica(Motor motor, String accion, String nombre) throws ErrorGotta {
		verificaAccionValida(accion);
		this.mMotor=motor;
		this.usr=motor.usuario;
		}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
		}
	public String getVariableRetorno() {
		return this.accion.p4;
		}
}

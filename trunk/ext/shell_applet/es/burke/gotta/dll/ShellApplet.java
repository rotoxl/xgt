/*
-Xdebug -Xrunjdwp:transport=dt_socket,address=127.0.0.1:9000,suspend=y
* */

package es.burke.gotta.dll;
import java.applet.Applet;
import java.awt.GridLayout;
import java.awt.Label;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import es.burke.gotta.Constantes;
import es.burke.gotta.Util;

import netscape.javascript.JSObject;

public class ShellApplet extends Applet {
	private static final long serialVersionUID = -5295563618912182933L;

	private String accion;
	private String aplicacionGotta;

	private String cmdline;

	private static final String PARAM_APLICACION = "aplicacion";
	
	void log(String s){
		this.add(new Label(s));
		super.repaint();
		System.out.println(s);
		}
	@Override
	public void init() {
		this.setLayout(new GridLayout(0,1));
		}
	
	@Override
	public void start() {
		this.log("ShellApplet");
		this.cmdline=this.getParameter("comando");
		cmdline=Util.replaceTodos(cmdline, "**COMILLAS**", "\"");

		this.accion=this.getParameter("accion");
		this.aplicacionGotta= this.getParameter(PARAM_APLICACION);
		String sesion = this.getParameter("sesion");
		
		if(sesion!=null)
			aplicacionGotta+="&sesion="+sesion;
		try {
			hazShell();
			
			this.log("Finalizando...");
			}
		catch (IOException e) {
			e.printStackTrace();
			log(e.toString());
			retornarError(e.toString());
			}
		catch (Throwable e) {
			e.printStackTrace();
			log(e.toString());
			retornarError(e.toString());
			}
		}

	public void hazShell() throws IOException, ErrorGenerandoProducto {
		this.log("Ejecutando "+ accion +": "+cmdline);
		
		Long t1, t2;
		if (accion.equalsIgnoreCase(Constantes.ejecutarEnCliente)){
			//cmdline="cmd /c start iexplore http://www.google.es"
			try {
				t1=System.currentTimeMillis();
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
				t2=System.currentTimeMillis();
				this.log("Valor devuelto:"+ret + " ("+(t2-t1)+" ms)");
 
				retornar(0, ret);
				} 
			catch (IOException e) {
				throw new ErrorGenerandoProducto("Error al ejecutar DLL.DLLShellOpen.abrirEnServidor '"+cmdline+"': el error que se ha producido ha sido "+e.getMessage());
				}
			catch (InterruptedException e) { 
				throw new ErrorGenerandoProducto("Error al ejecutar DLL.DLLShellOpen.abrirEnServidor '"+cmdline+"': el error que se ha producido ha sido "+e.getMessage());
				} 
			
			}
		}
		
	private void _retornar(String funcionjs, String msg){
		//nuevo método para cerrar la página del applet sin usar una redirección
		//	(hay algunos firefox y chromes que, al ser redireccionados, pierden la referencia del opener)
		JSObject jso = (JSObject) JSObject.getWindow(this);
		try {
			jso.call(funcionjs, new String[] {msg, "respuesta"});
       		}
       	catch (Exception ex) {
           ex.printStackTrace();
       		}
		}
	private void retornar(int retorno, String msg) {
		if(retorno==0){
			_retornar("devuelveOK", msg);
			}
		else
			this.log(msg+"("+retorno+")");
		}
	private void retornarError(String msg) {
		_retornar("devuelveKO", msg);
		}
}


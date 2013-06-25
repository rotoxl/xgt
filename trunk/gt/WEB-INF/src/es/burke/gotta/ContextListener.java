package es.burke.gotta;

import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {
		// pass
		}
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Inicializando ServletContext...");
		ServletContext ctx = sce.getServletContext();
		java.net.Authenticator.setDefault(new MyAuthenticator());
		try {
			PoolAplicaciones.init(ctx); //si peta aquí prueba a revisar el contenido o borrar y regenerar el miContexto.xml de $tomcat/conf/Catalina/localhost
			} 
		catch (Exception e1) {
			Util.getLogger("gotta").fatal(e1);
			}
		this.arrancaAplicacionesConAutoarranque();
		}
	public void arrancaAplicacionesConAutoarranque() {
		for (String apli: PoolAplicaciones.getAplisConAutoarranque()) {
//			try {
				Aplicacion app=PoolAplicaciones.sacar(apli);
				app.inicializaTrabajos(25);
//				} 
//			catch (ErrorGotta e) {
//				e.printStackTrace();
//				} 
			}
		}
	class MyAuthenticator extends java.net.Authenticator {
		@Override
		protected java.net.PasswordAuthentication getPasswordAuthentication() {
	        URL url = this.getRequestingURL();
	        String userInfo = url.getUserInfo();
	        if(userInfo==null){
	        	return null;
	        }
	        
			String[] usuPwd = userInfo.split(":");
	        String username = usuPwd[0];
	        String password = usuPwd[1];
	        return new java.net.PasswordAuthentication(username, password.toCharArray());
	    }
		
	}

}

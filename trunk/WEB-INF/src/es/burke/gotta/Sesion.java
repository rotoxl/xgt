package es.burke.gotta;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class Sesion implements HttpSessionListener  {
	public void sessionCreated(HttpSessionEvent arg0) {
		//pass
		}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession sesion=arg0.getSession();
				
		try {
			Enumeration<?> names = sesion.getAttributeNames();
			while(names.hasMoreElements()){
				String s=names.nextElement().toString();
				if (s.startsWith("usuario")){
					Object usu= sesion.getAttribute(s);
					if (usu instanceof Usuario)
						Util.deleteSesionAplicacion(usu.hashCode()+"");	
					}
				}
		}
		catch (Exception e) {
			Util.getLog().info("sesionDestroyed: "+e.getMessage());
			}
		}
	}

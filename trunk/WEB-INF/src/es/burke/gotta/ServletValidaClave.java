package es.burke.gotta;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/*
 * Esta clase sirve para validar la contraseña y redirigir 
 */
public class ServletValidaClave extends HttpServlet {
	private static final long serialVersionUID = -7035369533806826083L;
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		doPost(request, response);
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		String login=null, password, aplicacion=null;
		Aplicacion apli=null;
		try {
			aplicacion = Util.obtenValor(request, "aplicacion");
			apli=PoolAplicaciones.sacar(aplicacion);
			
			login = Util.obtenValorOpcional(request, "login");
			password = Util.obtenValorOpcional(request, "password");
			
			Boolean recordarme=false;
			if (request.getParameter("recordarme")!=null)
				recordarme=request.getParameter("recordarme").toString().equals("on");

			Usuario.nuevoUsuario(request, login, password, aplicacion);
			if (recordarme)
				Util.setCookies(aplicacion, login, password, response);
			else
				Util.resetCookies(aplicacion, response);
			}
		catch (Exception e) {
			if (apli!=null) 
				apli.añadeMSG("-1", "Intento fallido de login '"+login+"' ("+e.getMessage()+")", Constantes.TipoMensajeGotta.loginIncorrecto);
			HttpSession sesion = request.getSession();
			if (aplicacion!=null)
				sesion.setAttribute("usuario"+aplicacion, e);
			sesion.setAttribute("errorLogin", e.getMessage());
			}
		
		Util.sendRedirect(request, response, Util.sacaUrlDeOrigen(apli, request, true) );
		}
}


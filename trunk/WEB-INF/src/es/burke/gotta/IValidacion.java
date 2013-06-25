package es.burke.gotta;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class IValidacion {
	public String error;
	public String mensaje;
	
	public String login;
	public String password;
	
	public boolean requiereUsuarioyContrasenha=true;
	
	protected HttpSession session;
	protected HttpServletRequest request;
	protected Aplicacion apli;
	
	public abstract String valida(Aplicacion apli, String usuario, String pwd, HttpServletRequest request) throws ErrorUsuarioNoValido;
}

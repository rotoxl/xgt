package es.burke.gotta;

import javax.servlet.http.HttpServletRequest;

public class ValidacionSinLogin extends IValidacion {
	public ValidacionSinLogin(){
		this.requiereUsuarioyContrasenha=false;
		}
	@Override
	public String valida(Aplicacion apli, String usuGotta, String pwd, HttpServletRequest request) throws ErrorUsuarioNoValido{
		return usuGotta;
		}
}

package es.burke.gotta;

import javax.servlet.http.HttpServletRequest;

import es.burke.gotta.dll.LDAP;

public class ValidacionPorLDAP extends IValidacion {
	@Override
	public String valida(Aplicacion apli, String usuario, String pwd, HttpServletRequest request) throws ErrorUsuarioNoValido {
		try {
			LDAP gl= new LDAP(apli);
			boolean ret= gl.validaUsuario(usuario, pwd);
			if (!ret)
				throw new ErrorUsuarioNoValido("El usuario '"+usuario+"' no valida por LDAP.");
			return usuario;
			} 
		catch (ErrorFaltanDatos e) {
			apli.println("No hay datos para validar el usu por LDAP");
			throw new ErrorUsuarioNoValido("No hay datos para validar el usu por LDAP");
			}
		}
}

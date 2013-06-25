package es.burke.gotta;

import javax.servlet.http.HttpServletRequest;

public class ValidacionPorUsuContrasenha extends IValidacion {
	public String valida(Aplicacion apli, String usuGotta, String pwdGotta, HttpServletRequest request) {
		Conexion con = apli.getConexion();
		boolean valido= GestorMetaDatos.existeUsuarioGotta(con, usuGotta, pwdGotta);
		if (!valido)
			throw new ErrorUsuarioNoValido("El usuario '"+usuGotta+"' no existe o la contraseña introducida no es correcta");
		return usuGotta;
		}
}

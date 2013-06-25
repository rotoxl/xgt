package es.burke.gotta;

import javax.servlet.http.HttpServletRequest;

public class ValidacionPorTramite extends IValidacion {
	@Override
	public String valida(Aplicacion apli, String usuGotta, String pwd, HttpServletRequest request)  throws ErrorUsuarioNoValido{
		try {
			//lo 1º, validar el usuario
			Filas validaUsuario = GestorMetaDatos.leeTramiteEspecial(apli.getConexion(), "gtLogin", apli.getEsquema());
			if (validaUsuario!=null && !validaUsuario.isEmpty()){
				//trámite especial para validar el usuario
				Fila fila0=validaUsuario.get(0);
				
				Usuario usuFicticio=new Usuario();
				usuFicticio.aplicacion=apli.getCd();
				
				Long lng=Long.parseLong(fila0.gets("cd_camino"));
				Lote nl=Lote.montaLote(usuFicticio, lng, fila0.gets("cd_tramite") );
				
				nl.setVariable("@usu", Constantes.STRING, usuGotta);
				nl.setVariable("@pwd", Constantes.STRING, pwd);
				nl.setVariable("@ip", Constantes.STRING, request.getRemoteAddr());
				
				usuFicticio.getMotor().lote.seguir();
				return nl.getVariable("@usu").getValor().toString();
				}
			return null;
			}
		catch (ErrorGotta e){
			throw new ErrorUsuarioNoValido(e.getMessage());
			}
		}

}

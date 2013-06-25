package es.burke.gotta;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletSalir extends HttpServlet {
private static final long serialVersionUID = 3001352057196557451L;

@Override
public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
	req.setCharacterEncoding( Constantes.CODIF );

	String urlOrigen=".";
	Usuario usr=null;
	try {
		usr = Usuario.desdeSesion(req);
		
		urlOrigen=Util.sacaUrlDeOrigen(usr.getApli(), req, true);
		
		//ejecutamos tramite gtLogout si existe a la salida...
		Filas logout = GestorMetaDatos.leeTramiteEspecial(usr.getApli().getConexion(), "gtLogout", usr.getApli().getEsquema());
		if(logout!=null){
			Fila fila0=logout.get(0);
			Long lng=Long.parseLong(fila0.gets("cd_camino"));
			Lote nl=Lote.montaLote(usr, lng, fila0.gets("cd_tramite") );
			nl.seguir();
			}
		Util.resetCookies(usr.getApli().getCd(), res);
		usr.finalize(req);
		} 
	catch (NumberFormatException e) {
		//pass
		}
	catch (ErrorGotta e) {
		//pass
		}
	catch (ErrorConexionPerdida e) {
		//pass
		}
		
	usr=null;
	Util.sendRedirect(req, res, urlOrigen);
	}
}

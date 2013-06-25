package es.burke.gotta.ws;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.IJSONwritable;
import org.json.JSONException;
import org.json.JSONObject;

import es.burke.gotta.Conexion;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorConexionPerdida;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Fila;
import es.burke.gotta.Filas;
import es.burke.gotta.GestorMetaDatos;
import es.burke.gotta.INivelDef;
import es.burke.gotta.Jsp;
import es.burke.gotta.Lote;
import es.burke.gotta.ModoListaDef;
import es.burke.gotta.Motor;
import es.burke.gotta.Usuario;
import es.burke.gotta.Util;
import es.burke.gotta.Variable;
import es.burke.gotta.Constantes.TipoMensajeGotta;
import es.burke.gotta.dll.DLLGotta;

public class JSONWS extends HttpServlet {

	private static final long serialVersionUID = 2457664983782592L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		JSONObject jsret = new JSONObject();
		IJSONwritable ret=jsret;		
		
		String accion = request.getParameter("accion");
		String usu = Util.obtenValor(request, "usr");
		String pwd = Util.desembrollar(Util.obtenValor(request, "pwd"));
		String apl = Util.obtenValor(request, "app");		
		String clbk = Util.obtenValorOpcional(request, "callback");
		Usuario usr = null;	
		try {
			try {
				usr = new Usuario(usu, pwd, apl);
				usr.getApli().añadeMSG("-1", "Inicio de la sesión de '"+usu+"' desde WS (IP: "+request.getRemoteAddr()+"; método GET)", TipoMensajeGotta.login);
				usr.añadeMSG("Inicio de la sesión de '"+usu+"' desde WS (IP: "+request.getRemoteAddr()+"; método GET)", TipoMensajeGotta.login);				
				if (accion.equalsIgnoreCase("nivel")) {					
					ret = ejeNivel(usr, request);
				} else if (accion.equalsIgnoreCase("ejetram")){
					ret = ejeTram(usr, request);
				} else {
					throw new ErrorGotta("Acción no definida: " + accion);
				}
			}
			catch (ErrorGotta e) {
				ret=new JSONObject().put("tipo", "SesionPerdida").put("msg", e.getMessage());
				while (hayCapturaError(usr, e)){
					try {
						JSONObject xret=null;
						xret=continuarLote(usr);
						hazFlush(response, xret);
						return;
						} 
					catch (ErrorGotta e1) {
						e=e1;
						}
				}
			} catch (SQLException se) {
				throw new IOException(se.toString());
			}
		}
		catch (JSONException je) {
			throw new IOException(je.toString());
		}
		finally {
			logout(usr);
		}
			
		if(ret == null) {
			ret=new JSONObject();
		} 
		
		if(clbk == null) {
			hazFlush(response, ret);
		} else {
			hazFlush(response, ret, clbk);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{
		JSONObject jsret = new JSONObject();
		IJSONwritable ret=jsret;		
		
		String accion = request.getParameter("accion");
		String usu = Util.obtenValor(request, "usr");
		String pwd = Util.desembrollar(Util.obtenValor(request, "pwd"));
		String apl = Util.obtenValor(request, "app");
		String clbk = Util.obtenValorOpcional(request, "callback");
		Usuario usr = null;	
		try {
			try {
				usr = new Usuario(usu, pwd, apl);	
				usr.getApli().añadeMSG("-1", "Inicio de la sesión de '"+usu+"' desde WS (IP: "+request.getRemoteAddr()+"; método POST)", TipoMensajeGotta.login);
				usr.añadeMSG("Inicio de la sesión de '"+usu+"' desde WS (IP: "+request.getRemoteAddr()+"; método POST)", TipoMensajeGotta.login);
				if (accion.equalsIgnoreCase("nivel")) {					
					ret = ejeNivel(usr, request);
				} else if (accion.equalsIgnoreCase("ejetram")){
					ret = ejeTram(usr, request);
				} else {
					throw new ErrorGotta("Acción no definida: " + accion);
				}
			}
			catch (ErrorGotta e) {
				ret=new JSONObject().put("tipo", "SesionPerdida").put("msg", e.getMessage());
				while (hayCapturaError(usr, e)){
					try {
						JSONObject xret=null;
						xret=continuarLote(usr);
						hazFlush(response, xret);
						return;
						} 
					catch (ErrorGotta e1) {
						e=e1;
						}
				}
			} catch (SQLException se) {
				throw new IOException(se.toString());
			} 
		}
		catch (JSONException je) {
			throw new IOException(je.toString());
		}
		finally {
			logout(usr);
		}
			
		if(ret == null) {
			ret=new JSONObject();
		}
		
		if(clbk == null) {
			hazFlush(response, ret);
		} else {
			hazFlush(response, ret, clbk);
		}
	}
	
	private boolean hayCapturaError(Usuario usr, ErrorGotta e){
		try {
			if (usr.getMotor()!=null){
				if (usr.getMotor().tramActivo()==null)
					return false;
				usr.getMotor().tramActivo().verificaEtiqueta("#Error", true);
				usr.getMotor().lote.setVariable(Motor.CD_ERROR, Constantes.STRING, e.getClass().getName());
				
				String msg=e.getMessage()+ (e.getCause()!=null? ": "+e.getCause().getMessage():Constantes.CAD_VACIA);
				usr.getMotor().lote.setVariable(Motor.DS_ERROR, Constantes.STRING, msg);
				usr.getMotor().lote.tramActivo().fase=Constantes.TramitePreparadoYVerificado;
				return true;
				}
			}
		catch (ErrorGotta ee){
			//no hay gestión de errores			
			}
		return false;
	}
	
	static void hazFlush(HttpServletResponse response, IJSONwritable ret) throws IOException{
		Jsp.setHeadersJson(response);
		PrintWriter out = response.getWriter();
		try {
			ret.write(out);
		} catch (JSONException e) {
			throw new IOException(e.toString());
		}
	    out.flush();
		}

	/* JSONP */
	static void hazFlush(HttpServletResponse response, IJSONwritable ret, String clbk) throws IOException{
		Jsp.setHeadersJson(response);
		PrintWriter out = response.getWriter();		
		String resultado = clbk + "(" + ret.toString() + ")";	
		out.print(resultado);
	    out.flush();
		}
	
	private static void ristraRefresco(Usuario usr, JSONObject jsret) throws JSONException, ErrorGotta {
		if (usr.getMotor().hayQueRefrescar!=null) {
			Object idml=usr.getMotor().lote.getVariable("@").getValor();
			if(idml!=null && !idml.equals(Constantes.CAD_VACIA)){
				ModoListaDef ml=usr.getModoListaDef( idml.toString() );
				if (ml==null)
					throw new ErrorGotta("Se está intentando navegar a una vista que no existe: '"+idml.toString()+"'");
				jsret.put("dsml", ml.getDs());
				jsret.put("idml",idml);
				}
			}
		}
	
	public static JSONObject respuestaMotor(Motor.Resultado respuesta, Usuario usr) throws ErrorGotta, JSONException{
		JSONObject jsret=new JSONObject();
		Motor motor = usr.getMotor();
		
		//Un caso un poco especial: el autoexec del principio
		if (usr.tieneAutoexec){
			//después del Autoexec: gana la url introducida sobre las 
			//		redirecciones que se puedan hacer con @>@ o mli
			if(respuesta==Motor.Resultado.OK){
				usr.tieneAutoexec=false;
				if (usr.nodoArranque==null){
					if (motor.hayQueRefrescar!=null) {
						usr.arbol.ml=motor.lote.getVariable("@").getValor().toString();
						}
					}
				usr.variables.actualiza(motor.lote.variables);
				jsret.put("direccion", "");
				jsret.put("tipo", "redirigir");
				ristraRefresco(usr, jsret);
				return jsret;
				}
			if(respuesta==Motor.Resultado.SALIR){
				jsret.put("direccion", "salir?aplicacion="+usr.getApli().getCd());
				jsret.put("tipo", "redirigir");
				ristraRefresco(usr, jsret);
				return jsret;
				}
			}
		
		String ristraInforme="";
		while(!motor.usuario.productosGenerados.isEmpty()){
			String numprod=motor.usuario.productosGenerados.getOrden().get(0);
			DLLGotta prod = motor.usuario.productosGenerados.get(numprod);
			motor.usuario.productosEnviados.put(numprod, prod);
			motor.usuario.productosGenerados.remove(numprod);
			String marcaSíncrono = prod.síncrono?"+":"";
			ristraInforme+=" "+numprod+marcaSíncrono; //guardamos los id
			//y las llamadas a la producción
			jsret.put("informe_"+numprod+marcaSíncrono, "./genera?aplicacion="+usr.getApli().getCd()+"&contadoc="+numprod);
			}
		if (ristraInforme.length()>0)
			jsret.put("informes",ristraInforme.substring(1));
		
		jsret.put("idxBloqueo", usr.getMotor().lote.idxBloqueo) ;
		if (respuesta==Motor.Resultado.CAPTURA)
			motor.getCaptura().JSON(usr.getIdSesion(),jsret);
		//else if (respuesta==Motor.Resultado.FIRMA_ELECTRONICA_DATOS){
			//motor.tramActivo().getFirma().firmaDatos(jsret,usr);
		//	}
		//~ else if (respuesta==Motor.Resultado.FIRMA_ELECTRONICA) 
			//~ ret= "{"+ristraIdxBloqueo+Util.pareja("tipo", "firma")+"'datos':"+motor.tramActivo().JSONfirmaElectronica()+"}";
		else if ( respuesta==Motor.Resultado.FRM ) 
			motor.frmDinamico.JSON(jsret);
		else if ( respuesta==Motor.Resultado.MSG ) 
			motor.mensaje.JSON(jsret);
		else if ( respuesta==Motor.Resultado.OK) { 
			usr.variables.actualiza(motor.lote.variables);
			jsret.put("variables",Util.JSON(motor.lote.filasPinchadas));
			
			if (usr.hayQueRefrescar==null)
				jsret.put("tipo", "refrescodetalle");
			else if (usr.hayQueRefrescar instanceof String)
				jsret.put("tipo", "refrescocontroles").put("refrescocontroles_controles", usr.hayQueRefrescar.toString());
			else{
				jsret.put("tipo", "refresco");
				if(usr.arbol!=null && usr.arbol.nodoDestino!=null && usr.arbol.nodoDestino.controles!=null)
					jsret.put("controles",usr.arbol.nodoDestino.controles);
				}
			if (motor.lote.cerrarFlotantes!=null)
				jsret.put("cerrarFlotantes", motor.lote.cerrarFlotantes);
			if (!motor.lote.listaFlotantes.isEmpty())
				jsret.put("flotantes", Util.JSON(motor.lote.listaFlotantes));
		
			if (!motor.lote.infoControles.isEmpty())
				jsret.put("controles", Util.JSON(motor.lote.infoControles));
			ristraRefresco(usr, jsret);
			
			if (!motor.lote.quedanTramites() && usr.productosEnviados.size()==0 && usr.productosGenerados.size()==0) 
				usr.borraMotor();
			}
		else if (respuesta==Motor.Resultado.SALIR) {
			usr.variables.rollback();
			jsret.put("tipo", usr.hayQueRefrescar!=null?"refresco":"cancelar"); 
			if (!motor.lote.quedanTramites()) 
				usr.borraMotor();			
			}
		else if (respuesta == Motor.Resultado.DEPURACION_PAUSA ) {
			if ( motor.lote.tramActivo().getAcciones().getRegistroActual() == 0 || motor.lote.devolverDepuradorCompleto){
				jsret.put("tipo", "depuracion");
				motor.lote.JSON(jsret);
				}
			else{
				jsret.put("tipo", "pausa");
				motor.lote.evolucionJSON(jsret);
			}
			motor.lote.devolverDepuradorCompleto=false; //para la siguiente vuelta
			}
		else jsret.put("respuesta desconocida",respuesta);
		
		return jsret;
	}
	
	private static JSONObject continuarLote(Usuario usr) throws ErrorGotta {
		try {
			return respuestaMotor(usr.getMotor().lote.seguir(), usr);
			} 
		catch (JSONException e) {
			throw new ErrorGotta(e);
			}
		}

	private void logout(Usuario usr) {
		if(usr!=null){
			try {			
				//ejecutamos tramite gtLogout si existe a la salida...
				Filas logout = GestorMetaDatos.leeTramiteEspecial(usr.getApli().getConexion(), "gtLogout", usr.getApli().getEsquema());
				if(logout!=null){
					Fila fila0=logout.get(0);
					Long lng=Long.parseLong(fila0.gets("cd_camino"));
					Lote nl=Lote.montaLote(usr, lng, fila0.gets("cd_tramite") );
					nl.seguir();
					}			
				usr.borraMotores();
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
		}
	}
	
	private JSONObject ejeNivel(Usuario usr, HttpServletRequest request) throws ErrorGotta, SQLException, JSONException {		
		String nombreNivel = Util.obtenValor(request, "nivel");
		String params = Util.obtenValor(request, "params");
		INivelDef nivelDef = usr.getApli().getNivelDef(nombreNivel);				
		Conexion con = usr.getConexion();				
		List<Object> par = Util.splitObjetos(params, Constantes.SEPARADOR);
		Filas filas=nivelDef.obtenerNivel().lookUp(con,par);
		usr.añadeMSG("Petición WS: ejecución del nivel " + nombreNivel + "; parámetros: " + (params ==""? "sin parámetros" : params) + " (IP: "+request.getRemoteAddr()+"; método " + request.getMethod() + ")", TipoMensajeGotta.info);
		return filas.JSON();
	}
	
	private JSONObject ejeTram(Usuario usr, HttpServletRequest request) throws ErrorGotta, JSONException {
		Long camino = Long.parseLong(Util.obtenValor(request, "camino"));
		String tramite = Util.obtenValor(request, "tramite");
		String params = Util.obtenValor(request, "params");
		Lote nl=Lote.montaLote(usr, camino, tramite);
		ArrayList<String> trozos = Util.split(params, Constantes.SEPARADOR);
		for (int i=0; i<trozos.size(); i++){
			List<String> pr = Util.split(trozos.get(i),":");
			if (pr.size()==2)
				nl.setVariable(pr.get(0).trim(), null, pr.get(1).trim());
		}
		usr.getMotor().lote.seguir();
		usr.añadeMSG("Petición WS: ejecución del trámite " + camino.toString() + "-" + tramite + "; parámetros: " + (params ==""? "sin parámetros" : params) + " (IP: "+request.getRemoteAddr()+"; método " + request.getMethod() + ")", TipoMensajeGotta.info);
		Variable retorno = nl.getVariable("@ret");
		if (retorno == null) {
			return new JSONObject().put("Resultado", "ok");			
		} else {
			return retorno.JSON();
		}		
	}
	
}

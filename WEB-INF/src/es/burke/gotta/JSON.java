package es.burke.gotta;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.IJSONwritable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.burke.gotta.Constantes.TipoMensajeGotta;
import es.burke.gotta.Motor.Resultado;
import es.burke.gotta.dll.DLLGotta;
import es.burke.gotta.dll.DLLMapaTramitacion;
import es.burke.gotta.dll.DLLShellOpen;
import es.burke.gotta.dll.InformeJasper;
import es.burke.gotta.dll.Tram;
import es.burke.gotta.dll.WordCliente;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class JSON extends HttpServlet {
	private static final long serialVersionUID = -9041679082010977639L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		JSONObject jsret = new JSONObject();
		IJSONwritable ret=jsret;
		Usuario usr=null;

		String accion = request.getParameter("accion"); 

		try {
			if (accion.equalsIgnoreCase("diccionarioFisico"))
				ret=this.diccionarioFisico(request);
			else {
				try {
					usr=Usuario.desdeSesion(request);
					
					if (accion.equalsIgnoreCase("experimientos.notificaciones")){
						sacaNotificaciones(usr, response);
						return;
						}
					else if (accion.startsWith("depurador.generaTramite")){
						ret=generaTramite(usr, request);
						}
					else if (accion.equalsIgnoreCase("sacaEstadisticas"))
						ret=sacaEstadisticas(usr);
					else if (accion.equalsIgnoreCase("cerrarFormulario"))
						ret=respuestaMotor(Resultado.SALIR, usr);
					else if ( accion.equalsIgnoreCase("disenho") )
						activaDisenho(request, usr);
					else if ( accion.equalsIgnoreCase("depuracion") )
						activaDepuracion(request, usr) ;

//					else if (accion.equalsIgnoreCase("irA")){//en el depurador
//						usr.getMotor().lote.tramActivo().setNumeroLinea(Util.obtenValor(request, "idx").toString());
//						ret= continuarLote(usr);
//						}
					else if ( accion.equalsIgnoreCase("pausa") ) {
						usr.tipoDepuracion=Motor.TDepuracion.PAUSA;
						ret= continuarLote(usr);
						}
					else if ( accion.equalsIgnoreCase("play") ) {
						usr.tipoDepuracion=Motor.TDepuracion.PLAY;
						ret= continuarLote(usr);
						}

					else if (accion.equalsIgnoreCase("ponerpausa")){ 
						this.alternarPuntoInterrupcion(true, Util.obtenValor(request, "idx").toString(), usr);
						}
					else if (accion.equalsIgnoreCase("quitarpausa")){ 
						this.alternarPuntoInterrupcion(false, Util.obtenValor(request, "idx").toString(), usr);	
						}
					else if ( accion.equalsIgnoreCase("recargarAcciones") ) {
						this.recargarAcciones(usr);
						ret= respuestaMotor(Motor.Resultado.DEPURACION_PAUSA, usr);
						}
					else if (accion.equalsIgnoreCase("renumerarAcciones")){
						String cam, tram;
						cam=Util.obtenValor(request, "camino");
						tram=Util.obtenValor(request, "tramite");
						
						Tram.renumerar(usr.getMotor(), cam, tram);
						
						this.recargarAcciones(usr);
						ret= respuestaMotor(Motor.Resultado.DEPURACION_PAUSA, usr);
						}
					else if (accion.equalsIgnoreCase("buscar"))
						ret= buscar(request, usr);

					//				else if ( accion.equalsIgnoreCase("cambiaNodoActivo") )
					//					ret= cambiaNodoActivo(request, usr);
					else if ( accion.equalsIgnoreCase("rellenaMD") )
						ret= rellenaMD(request, usr);
					else if ( accion.equalsIgnoreCase("navegarA") )
						ret= navegarA(request, usr);

					else if ( accion.equalsIgnoreCase("md") )	
						ret= controlesMD(request, usr);
					else if ( accion.equalsIgnoreCase("ml") )	
						ret= controlesML(request, usr);
					else if ( accion.equalsIgnoreCase("eval") ) 
						ret= new JSONArray().put(evaluaExpresionMotor(request, usr));
					else if ( accion.equalsIgnoreCase("evalMD") ) 
						ret= evaluaExpresionMD(request, usr);

					else if ( accion.equalsIgnoreCase("EXP_Menu") )
						ret=GestorMetaDatos.leeEXP_Menu(usr.getConexion(), usr.getApli().getEsquema()).JSON();

					else if ( accion.equalsIgnoreCase(Constantes.LVW) ) 
						ret= montaLVW(request, usr);
					else if ( accion.equalsIgnoreCase("paginaLvw") ) 
						ret= paginaLVW(request, usr);

					else if ( accion.equalsIgnoreCase(Constantes.DESP) ) 
						ret= montaDESP(request, usr);
					else if ( accion.equalsIgnoreCase(Constantes.RD) ) 
						ret= montaRD(request, usr);
					else if ( accion.equalsIgnoreCase(Constantes.WWW) )
						ret= montaWWW(request, usr);
					
					else if (accion.equalsIgnoreCase("menuBBB") ) 
						ret= menuBBB(request, usr) ;
					else if (accion.equalsIgnoreCase("mapaTram") ) {
						String idcamino=Util.obtenValor(request, "camino");
						String ob=Util.obtenValorOpcional(request, "ob");
						ret=sacaMapaTramitacion(idcamino, ob, usr);
						}
					else if (accion.equalsIgnoreCase("listaCaminos")){
						JSONArray xret = new JSONArray();
						
						for (Camino cam: usr.getApli().generaListaCaminos().values())
							xret.put(cam.json());
						ret=xret;
						
//						ret=usr.getApli().generaListaCaminos().JSON();
						}
					else if (Util.en(accion, "arbol", "expandir", Constantes.ARB, Constantes.SUBGRID) ) 
						ret= arbol(request, usr);
					
					else if (accion.equalsIgnoreCase("vistasExp")){
						if (usr.menuGeneral!=null)
							ret=new JSONObject().put("menu", usr.menuGeneral.JSON(false, false));
						}
					//editor de niveles
					else if (accion.equalsIgnoreCase("traeNivel") )
						ret=this.traeNivel(request, usr);
					else if (accion.equalsIgnoreCase("eliminarNivel") ) 
						ret=this.eliminarNivel(request, usr);

					//	else if (accion.equalsIgnoreCase("imagenes"))
					//		ret=this.imagenes(usr);
					else if (accion.equalsIgnoreCase("tiposControl"))
						ret=GestorMetaDatos.leeTiposControl(usr.getApli().getConexion(), usr.getApli().getEsquema()).JSON(false);

					else if (accion.equalsIgnoreCase("estilosControl"))
						ret=GestorMetaDatos.leeEXP_Estilos(usr.getApli().getConexion(), usr.getApli().getEsquema()).JSON(false);

					else if (accion.equalsIgnoreCase("getAplicaciones"))
						ret=this.getAplicaciones(usr);
					
					else if (accion.equalsIgnoreCase("idiomasAplicacion"))
						ret=Util.JSONFilas(usr.getApli().idiomas);

					else if (accion.equalsIgnoreCase("getFilasMonitor")){
						String cdApli=Util.obtenValor(request, "apliCargar");
						Aplicacion apli=PoolAplicaciones.sacar(cdApli);
						ret=apli.getMensajesMonitor(Util.obtenValor(request, "usuario") );
						}
					else if (accion.equalsIgnoreCase("vaciaFilasMonitor"))
						usr.getApli().vaciaMonitor();
					
					else if (accion.equalsIgnoreCase("progresoSubida"))
						ret=vigilaProgresoSubida(usr);
						
					else
						throw new ErrorGotta("Acción no definida");
					}
				catch (ErrorConexionPerdida e) {
					ret=sesionFinalizada(usr, request, response, e);
					}
				catch (ErrorGotta e) {
					if (usr.getMotor()!=null && usr.getMotor().tramActivo()!=null){
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
						}
					if (usr!=null && usr.getMotor()!=null && usr.getMotor().lote!=null) 
						jsret.put("idxBloqueo",usr.getMotor().lote.idxBloqueo);
					jsret.put("tipo", "error");
					jsret.put("msg", e.toString());
					} 
				}
			}
		catch (JSONException e) {
			throw new IOException(e.toString());
			}
		if(ret==null)
			ret=new JSONObject();
		hazFlush(response, ret);
	}
	private JSONObject generaTramite(Usuario usr, HttpServletRequest request) throws ErrorArrancandoAplicacion, JSONException, ErrorDiccionario, ErrorNumeroIncorrecto, ErrorGotta {
		String accCompleta = request.getParameter("accion").toLowerCase();//depurador.generaTramite
		String accion=accCompleta.substring("depurador.generaTramite.".length());
		
		String str_camino, cd_tramite, str_operacion; 
		
		str_camino=Util.obtenValor(request, "camino");
		Long cd_camino=Long.parseLong(str_camino);
		
		cd_tramite=Util.obtenValor(request, "tramite");
		
		str_operacion=Util.obtenValor(request, "op");
		int cd_operacionOriginal=Integer.parseInt(str_operacion);
		int cd_operacion=cd_operacionOriginal;
		
		boolean previsualizar=Util.obtenValorOpcionalBoolean(request, "previsualizar");		
	//////////////////
		ITabla acciones=usr.getMotor().sacaTablaDef("TRA_Acciones").newTabla(usr);
		ITabla accionesAUX=null;
		
		if (Util.en(accion, "duplicar", "ExtraerSUB")){
			String CD_CaminoDestino=Util.obtenValor(request, "cd_caminoDestino");
			if (CD_CaminoDestino!=null && CD_CaminoDestino.equals(Constantes.CAD_VACIA))
				CD_CaminoDestino=null;
			
			String CD_TramiteDestino=Util.obtenValorOpcional(request, "CD_TramiteDestino");
			if (CD_TramiteDestino==null || CD_TramiteDestino.equals(Constantes.CAD_VACIA)){
				CD_TramiteDestino=Util.obtenValorOpcional(request, "CD_TramiteDestinoNuevo");
			
				String DS_TramiteDestino=Util.obtenValorOpcional(request, "DS_TramiteDestinoNuevo");
				GestorMetaDatos.guardaTRA_TramitesObjetos(usr.getConexion(), usr.getApli().getEsquema(), Long.parseLong(CD_CaminoDestino), CD_TramiteDestino, DS_TramiteDestino, CD_TramiteDestino, false);
				}
			
			ITabla origen=usr.getMotor().sacaTablaDef("TRA_Acciones").newTabla(usr);
			origen.cargarRegistros(acciones.tdef.getCampo("cd_cam_tra"), Util.creaLista(cd_camino, cd_tramite));
			
			if (accion.equalsIgnoreCase("duplicar")){
				acciones.cargarRegistros(acciones.tdef.getCampo("cd_cam_tra"), Util.creaLista(CD_CaminoDestino, CD_TramiteDestino));	
				for (Fila f:origen.datos)
					Util.creaNuevaFila(acciones, Long.parseLong(CD_CaminoDestino), CD_TramiteDestino, Integer.parseInt(f.gets("cd_operacion")), f.gets("condicion"), f.gets("cd_accion"), 
													f.gets("parametro1"), f.gets("parametro2"), f.gets("parametro3"), f.gets("parametro4"));
				}
			else {
				accionesAUX=usr.getMotor().sacaTablaDef("TRA_Acciones").newTabla(usr);
				acciones.cargarRegistros(acciones.tdef.getCampo("cd_cam_tra"), Util.creaLista(cd_camino, cd_tramite));
				
				String tlistaAcciones=Util.obtenValorOpcional(request, "filasSeleccionadas");
				ArrayList<String> listaAcciones=Util.split(tlistaAcciones, Constantes.SEPARADOR);
				for (int i=0; i<listaAcciones.size(); i++){
					String op=listaAcciones.get(i);
					op=op.substring(0, op.indexOf("_"));
					
					origen.setRegistroActual(0);
					int iOrigen=origen.findFirst("CD_Operacion", op);
					Util.creaNuevaFila(accionesAUX, Long.parseLong(CD_CaminoDestino), CD_TramiteDestino, Integer.parseInt(origen.getValorCol("CD_Operacion").toString()), 
												Util.noVacía(origen.getValorCol("condicion")), origen.getValorCol("CD_Accion").toString(), Util.noVacía(origen.getValorCol("parametro1")), 
												Util.noVacía(origen.getValorCol("parametro2")), Util.noVacía(origen.getValorCol("parametro3")), Util.noVacía(origen.getValorCol("parametro4")));
					
					acciones.setRegistroActual(iOrigen);
					if (i==0) {//insertamos el SUB
						acciones.setValorCol("CD_Accion", Constantes.SUB);
						acciones.setValorCol("Parametro1", CD_CaminoDestino); acciones.setValorCol("Parametro2", CD_TramiteDestino);
						acciones.setValorCol("Parametro3", null); acciones.setValorCol("Parametro4", null);
						}
					else {
						 acciones.delete();
						}
					}
				accionesAUX.ordenar("CD_Operacion");
				}
			}
		else {	
			acciones.cargarRegistros(acciones.tdef.getCampo("cd_cam_tra"), Util.creaLista(cd_camino, cd_tramite));

			String tempDespues=Util.obtenValorOpcional(request, "posicion");
			boolean despues=(tempDespues!=null && tempDespues.equals("despues"));
			
			ITabla it=usr.getMotor().tramActivo().tramiteInicial().getTb();
			String tb=null;
			if (it!=null) tb=it.tdef.cd;
			
			if (Util.en(accion, "alta", "modif", "baja")){
				String t=Util.obtenValorOpcional(request, "tabla");
				ITablaDef td=usr.getApli().getEsquema().getTablaDef(t);
				
				if (Util.en(accion, "alta", "modif")){	
					hazHueco(acciones, despues, cd_operacion, td.campos.size()+3 );
					
					cd_operacion+=10;
					Util.creaNuevaFila(acciones, cd_camino, cd_tramite, cd_operacion, null, Constantes.FRM, "Principio", null, null, null);
					
					String a=accion.equals("alta")?Constantes.Xsi:Constantes.Xno;
					
					Util.creaNuevaFila(acciones, cd_camino, cd_tramite, cd_operacion+=10, null, a, asteriscoTabla(tb, td.getCampoClave()), null, null, null);
					for (CampoDef cam:td.campos.values()){
						if (cam != td.getCampoClave())
							Util.creaNuevaFila(acciones, cd_camino, cd_tramite, cd_operacion+=10, null, Constantes.Xsi, asteriscoTabla(tb, cam), null, "opcional", null);
						}
					Util.creaNuevaFila(acciones, cd_camino, cd_tramite, cd_operacion+=10, null, Constantes.FRM, "Fin", null, null, null);
					}
				else if (accion.equalsIgnoreCase("baja")){//generaTramite.Baja
					hazHueco(acciones, despues, cd_operacion, 3 );
					
					Util.creaNuevaFila(acciones, cd_camino, cd_tramite, cd_operacion+=10, null, Constantes.MSG, "¿Quiere eliminar el registro?", "exclamacion|Si\\1|No\\0", "@ret", null);
					Util.creaNuevaFila(acciones, cd_camino, cd_tramite, cd_operacion+=10, "@ret=1", Constantes.DEL, t, null, null, null);
					}
				}
			else if (accion.equalsIgnoreCase("EmitirJasper")){
				hazHueco(acciones, despues, cd_operacion, 10 );
				String nombreInforme=Util.obtenValorOpcional(request, "nombreInforme");
				
				InformeJasper inf=new InformeJasper();
				Coleccion<String>params=inf.sacaListaParametros(usr.getApli(), nombreInforme);
				
				for (String param:params.getOrden())
					Util.creaNuevaFila(acciones, cd_camino, cd_tramite, cd_operacion+=10, null, Constantes.X_X, "'valor'", "@"+param, null, null);
				
				Util.creaNuevaFila(acciones, cd_camino, cd_tramite, cd_operacion+=10, null, Constantes.DLL, "InformeJasper", "EmitirDocumento", "'"+nombreInforme+"'", null);
				}
			else if (accion.equalsIgnoreCase("EmitirWord")){
				hazHueco(acciones, despues, cd_operacion, 10 );
				String nombreInforme=Util.obtenValorOpcional(request, "nombreInforme");
				Util.creaNuevaFila(acciones, cd_camino, cd_tramite, cd_operacion+=10, null, Constantes.DLL, "WordCliente", "EmitirDocumento", "'"+nombreInforme+"'", "@docID");
				}
			else if (accion.equalsIgnoreCase("CapturaError")){
				Fila ultima=acciones.datos.get(acciones.datos.size()-1);
				cd_operacion=Integer.parseInt(ultima.gets("CD_Operacion"));
				
				Util.creaNuevaFila(acciones, cd_camino, cd_tramite, cd_operacion+=10, "@cd_error & '' # ''", Constantes.LBL_AC, "#ERROR", null, null, null);
				Util.creaNuevaFila(acciones, cd_camino, cd_tramite, cd_operacion+=10, "\"", Constantes.MSG, "@ds_error", "exclamacion", null, null);
				}
			
			else if (accion.equalsIgnoreCase("")){}
			}
/////////////		
		if (!previsualizar){
			descargaDatosNuevasAcciones(usr, acciones, accionesAUX);
			return new JSONObject().put("resultado", Motor.Resultado.OK);
			}
		else {
			acciones.ordenar("cd_operacion");
			acciones.setRegistroActual(-1);
			
			Lote l=Lote.montaLoteFicticio(usr, cd_camino, cd_tramite);
			Tramite t=l.tramites.get(0);
			t.acciones=acciones;
			l.setTramActivo(t);
			
			if (accionesAUX!=null){
				Tramite nt=new Tramite(l, Long.parseLong(accionesAUX.getValorCol("CD_Camino").toString()), accionesAUX.getValorCol("CD_Tramite").toString(), 
										new ArrayList<Object>(), new Coleccion<Variable>());
				nt.acciones=accionesAUX;
				l.tramites.add(nt);
				}
			
			l.motor.tablas.clear(); //para aligerar la respuesta
			JSONObject ret= new JSONObject()
				.put("tipo", "generaTramite")
				.put("subtipo", accCompleta)
				.put("accionesNuevas", Util.JSON(acciones.nuevos))
				.put("accionesModif", Util.JSON(acciones.modificados))
				.put("accionesBorradas", Util.JSON(acciones.borrados));
			l.JSON(ret);
			return ret;
			}
		}

	private void descargaDatosNuevasAcciones(Usuario usr, ITabla acciones, ITabla accionesAUX) throws ErrorVolcandoDatos, ErrorCampoNoExiste {
		usr.añadeMSG("Arrancamos transacción desde Depurador.GeneracionAutomáticaDeTrámites", TipoMensajeGotta.info);
		Motor motor=usr.getMotor();
		
		motor.usuario.BeginTrans();
		try {
			DescargaFisica df = new DescargaFisica(motor);
			
			df.descargarTablaBorrados((Tabla)acciones);
			df.descargarTablaActualizados((Tabla)acciones);
			df.descargarTablaNuevos((Tabla)acciones);
			
			if (accionesAUX!=null){
				df.descargarTablaBorrados((Tabla)accionesAUX);
				df.descargarTablaActualizados((Tabla)accionesAUX);
				df.descargarTablaNuevos((Tabla)accionesAUX);
				}
			usr.CommitTrans();
			} 
		catch (ErrorVolcandoDatos e)  {
			usr.añadeMSG("¡Se ha producido un error!", TipoMensajeGotta.errorSQL);
	
			if (usr.permisoDepuracion || usr.permisoMonitorSQL) 
				throw e; 
			else 
				throw new ErrorVolcandoDatos("Se ha producido un error al intentar guardar la información en la base de datos"); 
			} 
		catch (SQLException e) {
			usr.añadeMSG("¡Se ha producido un error!", TipoMensajeGotta.errorSQL);
			
			if (usr.permisoDepuracion || usr.permisoMonitorSQL) 
				throw new ErrorVolcandoDatos(e.getMessage()); 
			else 
				throw new ErrorVolcandoDatos("Se ha producido un error al intentar guardar la información en la base de datos");
			}
		usr.añadeMSG("Transacción desde Depurador.GeneracionAutomáticaDeTrámites finalizada", TipoMensajeGotta.info);	
		}
	private void hazHueco(ITabla acciones, boolean despues, int cd_operacion, int numHuecos) {
		String op="CD_Operacion";
		
		int idx=acciones.findFirst("CD_Operacion", cd_operacion);
		if (despues)
			idx++;
		
		for (int i=idx; i<acciones.datos.size(); i++){
			Fila fila=acciones.datos.get(i);
			fila.__setitem__(op, Long.parseLong(fila.get(op).toString())+(numHuecos*10));
			acciones.modificados.add(i);
			}
		
	}
	private String asteriscoTabla(String tb, CampoDef cam){
		if (cam.getTabla().equalsIgnoreCase(tb))
			return cam.getCd();
		else
			return "*."+cam.getTabla()+"."+cam.getCd();
	}
	private void sacaNotificaciones(Usuario usr, HttpServletResponse response) throws IOException, JSONException {
		//De momomento sólo funciona para firefox 6+
		//	http://hacks.mozilla.org/2011/06/a-wall-powered-by-eventsource-and-server-sent-events/
		IJSONwritable ret=new JSONObject();
		
		for (int i=0; i<100; i++){
			Jsp.setHeadersJson(response);
			response.setHeader("Content-Type", "text/event-stream");
			
			ret=new JSONObject().put("t", new FechaGotta());
			
			PrintWriter out = response.getWriter();
			out.println("event: checkin");
			out.println("data:"+ret.toString());
			out.println("\n\n");
			
		    out.flush();
		    
		    ///////////////////////
		    try {
		    	int t=new Random().nextInt(5000);
				Thread.sleep(t);
				} 
		    catch (InterruptedException e) {
				e.printStackTrace();
				}
			}
	    
	}
	private IJSONwritable vigilaProgresoSubida(Usuario usr) throws JSONException {
		if (usr.getMotor()!=null && usr.getMotor().progresoSubida==null)
			return new JSONObject(); 
		try{
			return usr.getMotor().progresoSubida.JSON();
			}
		catch (NullPointerException e){
			return new JSONObject();
			}
		}
	private JSONObject cambiaIdiomas(HttpServletRequest req, Usuario usr) throws ErrorGotta, JSONException {
		JSONObject ret=new JSONObject();
		
		String idioma=Util.obtenValor(req, "idioma");
		if (! usr.getApli().idiomas.containsKey(idioma)){
			ret.put("tipo", "error").put("msg", "Idioma no soportado en tabla LEN_Idiomas");
			return ret;
			}
		usr.cambiaIdioma(idioma);
		return ret.put("tipo", "ok");
		}
	private JSONObject sacaEstadisticas(Usuario usr) throws JSONException, ErrorConexionPerdida, ErrorArrancandoAplicacion, ErrorGotta {
		JSONObject ret=new JSONObject();
		
		int dic_tablas=0, dic_columnas=0, dic_referencias=0;
			Esquema e=usr.getApli().getEsquema();
			dic_tablas=e.getOrdenTablas().size();
			
			for (String t:e.getOrdenTablas()){
				ITablaDef td=e.getTablaDef(t);
				dic_columnas+=td.getColumnas().size();
				for (CampoDef c:td.campos.values()){
					if (c.getTablaReferencia()!=null)
						dic_referencias++;
					}
				}
		ret.put("dic_tablas", dic_tablas).put("dic_columnas", dic_columnas).put("dic_referencias", dic_referencias);
	
	//////////////////////
		int exp_niveles=0;
			for (INivelDef n: usr.getApli().niveles.values()){
				if (!n.esDeSistema)
					exp_niveles++;
				}
			
		ret	.put("exp_niveles", exp_niveles);
		
		Esquema esq=usr.getApli().getEsquema();
		if (esq.existeTabla("exp_menu")){
			ret.put("vistas", GestorMetaDatos.leeTotalVistasEXP_Menu(usr.getConexion(), esq))
				.put("exp_menu", GestorMetaDatos.leeTotalEXP_Menu(usr.getConexion(), esq));
			}
		
		ret.put("exp_modosdetalle", usr.getApli().detalles.size())
			.put("exp_controlesmododetalle", GestorMetaDatos.leeTotalEXP_ControlesModoDetalle(usr.getConexion(), esq));
	
	//////////////////////
		ret	.put("tra_caminos", GestorMetaDatos.leeTotalTRA_Caminos(usr.getConexion(), esq))
			.put("tra_tramites", GestorMetaDatos.leeTotalTramites(usr.getConexion(), esq))
			.put("tra_acciones", GestorMetaDatos.leeTotalTRA_Acciones(usr.getConexion(), esq))
	//////////////////////		
			.put("usuarios", GestorMetaDatos.leeTotalUSUARIOS(usr.getConexion(), esq))
	//////////////////////
			.put("idiomas", usr.getApli().idiomas.size());
		return ret;
	}

	private IJSONwritable sacaMapaTramitacion(String idcamino, String ob, Usuario usr) throws JSONException, ErrorGotta {
		JSONObject ret=new JSONObject();
		if (idcamino!=null){
			String xcamino="x"+idcamino;

			Aplicacion apli=usr.getApli();
			Camino cam;
			if (!apli.caminos.containsKey(xcamino)) {
				cam=new Camino(usr, new Integer(idcamino) ) ;
				apli.caminos.put(xcamino, cam);
				}
			else
				cam=apli.getCamino(idcamino);

			ret=cam.jsonMapa(usr);
			
			if (ob!=null){
				ArrayList<Object> objetoBase=Util.splitObjetos(ob, Constantes.SEPARADOR);
				JSONObject penEje=DLLMapaTramitacion.tramPENyEJE(Long.parseLong(idcamino), objetoBase, usr);
				
				ret.put("ejecutados", penEje.get("ejecutados")).
					put("pendientes", penEje.get("pendientes"));
				}
			}
		return ret;
	}
	private IJSONwritable montaDESP(HttpServletRequest request, Usuario usr) throws ErrorGotta, JSONException {
		return montaControlConNivel(request, usr, "desp", 0);
	}
	private JSONObject navegarA(HttpServletRequest request, Usuario usr) throws ErrorGotta  {  
		URL_Gotta destino=null;
		String valor = Util.obtenValor(request, "key");
		
		Filtrar.filtrar(request);
		
        if (valor.startsWith("gotta://"))
        	valor=valor.substring(8);
         
		if (!valor.contains("nodo=")) //va en el mismo formato que lo de generarURL
			valor="nodo="+valor;
		destino=URL_Gotta.crea(valor);
		
		if (destino.ml==null)
			destino.ml=usr.arbol.ml;

		if (!destino.flotante){ 
			usr.hayQueRefrescar="total";
			usr.arbol.nodoDestino=new NodoActivo(usr, destino.md, destino.ml, destino.letras);	
			usr.arbol.nodoDestino.controles=destino.controles;	
			}
		
		usr.añadeMSG("Navegación a nodo "+destino.letras+ " (detalle "+destino.md+")", TipoMensajeGotta.info);  

		JSONObject jsret = new JSONObject();  
		try {
			jsret.put("ok", 1); 
           	jsret.put("nodo", destino.letras); 
           	jsret.put("md", destino.md); 
           	jsret.put("ml", destino.ml); 
           	jsret.put("flotante", destino.flotante); 
           	jsret.put("url", valor);
        	}
		catch (JSONException e) {  
			//pass   
           	}
       return jsret;  
       } 
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		IJSONwritable ret;
		Usuario usr=null;
		
		String accion = request.getParameter("accion");
		if (accion.equalsIgnoreCase("diccionarioFisico"))
			try {
				ret=this.diccionarioFisico(request);
				} 
			catch (JSONException e) {
				throw new IOException(e.toString());
				}
		else {
			try {
				usr=Usuario.desdeSesion(request, true);
				if (accion.equalsIgnoreCase("cambiaIdioma")){
					ret=this.cambiaIdiomas(request, usr);
					}
				else if (accion.equalsIgnoreCase("retornoEmisionInformes")){
					String tempError=Util.obtenValorOpcional(request, "error");
					Boolean hayError=tempError!=null && tempError.equals("1");
					String msg=Util.obtenValor(request, "msg");
					String idInforme=Util.obtenValorOpcional(request, "idInforme");
					
					if (idInforme.endsWith("+"))
						idInforme=idInforme.substring(0, idInforme.length()-1);
					
					if (hayError){
						String docid=Util.obtenValorOpcional(request, "docid");
						if (docid!=null && docid.equals(Constantes.CAD_VACIA))
							docid=null;
						
						throw new ErrorEmitiendoInforme("Error emitiendo el informe"+
													(docid!=null?" con docid="+docid:"")+
													". El error que se ha producido ha sido: "+msg);
						}
					else {
						DLLGotta dll=usr.productosEnviados.get(idInforme);
						if (dll instanceof DLLShellOpen){
							String nombreVariable=((DLLShellOpen)dll).getVariableRetorno();
							usr.getMotor().lote.setVariable(nombreVariable, Constantes.STRING, msg);
							}
						}
					ret=continuarLote(usr);
					}
				
				else if ( accion.equalsIgnoreCase("evalFRM") ) 
					ret= evaluaExpresionFormularioDinámico(request, usr);
				else if ( accion.equalsIgnoreCase("continuaTramite") ){
					recogeValoresFRM(request, usr);
					ret=continuarLote(usr);
					}
				else if ( accion.equalsIgnoreCase("inicioTramite") ) {
					NodoActivo na=rellenameNodoActivo(request, usr);
					Filtrar.filtrar(request);
					
					Lote.montaLote(request, usr, na) ;
					ret=continuarLote(usr);
					}
				else if ( accion.equalsIgnoreCase("inicioDisenhoTramite") ) {
					Lote lote=Lote.montaLote(request, usr, new NodoActivo(usr)) ;
					try {
						lote.devolverDepuradorCompleto=true;
						lote.siguienteTramite();
						lote.tramActivo().leeAcciones();
						lote.tramActivo().verificarTramite();
						} 
					catch (ErrorGotta e) {
						//pass
						}
					ret=respuestaMotor(Motor.Resultado.DEPURACION_PAUSA, usr);
					}
				else if ( accion.equalsIgnoreCase("saltaTramite") )
					ret=saltarTramite(usr);
				else if (accion.equalsIgnoreCase("saltarAEtiqueta")){
					recogeValoresFRM(request, usr);
					String etiqueta=Util.obtenValor(request, "etiqueta");
					try{
						usr.getMotor().lote.tramActivo().verificaEtiqueta(etiqueta, true);
						}
					catch (ErrorTramiteActivoHaCambiado e){
						//pass
						}
					ret= continuarLote(usr);
					}
				else if (accion.equalsIgnoreCase("guardaAccion")) {
					String idx=Util.obtenValor(request, "idx");
					try {
						guardarAccion(request, usr) ;
						ret=verificarAccion(request, usr) ;
						} 
					catch (ErrorGotta e) {
						ret=new JSONObject().put("tipo","errorVerificando").put("msg", e.getMessage()).put("idx", idx);
						}
					}
				else if (accion.equalsIgnoreCase("guardarMD")){
					NodoActivo na=rellenameNodoActivo(request, usr);
					usr.arbol.nodoActivo=na.nodoActivo;
					
					ret=guardarMD(request, usr) ;
					}
//				else if (accion.equalsIgnoreCase("guardarModosLista"))
//					ret=guardarML(request, usr) ;
				else if (accion.equalsIgnoreCase("ejecutaSQL") )
					ret=this.ejecutaSQL(request, usr);
				else if (accion.equalsIgnoreCase("ejecutaNivel") )
					ret=this.ejecutaNivel(request, usr);
				else if (accion.equalsIgnoreCase("guardaNivel") ) 
					ret=this.guardaNivel(request, usr) ;
				else if (accion.equalsIgnoreCase("actualizarEXP_Estilos"))
					ret=this.actualizaEXP_Estilos(request, usr);
				else if (accion.equalsIgnoreCase("guardarMenu"))
					ret=this.guardaMenu(request, usr);
					
				//mapa de tramitación
				else if (accion.equalsIgnoreCase("guardaTramite"))
					ret=this.guardaTramite(request, usr);
				else if (accion.equalsIgnoreCase("creaPEN"))
					ret=this.creaPEN(request, usr);
				else if (accion.equalsIgnoreCase("borraPEN"))
					ret=this.borraPEN(request, usr, usr.getApli().getEsquema());
				else if (accion.equalsIgnoreCase("guardaCamino"))
					ret=this.guardaCamino(request, usr);
					
				
				//firma electrónica: peticiones desde el menú contextual de listas tf
				else if (accion.equalsIgnoreCase("datosFirmados") ) 
					ret=usr.getMotor().tramActivo().getFirma().datosFirmados(request, usr) ;
				else if (accion.equalsIgnoreCase("validaFirma") ) 
					ret=usr.getMotor().tramActivo().getFirma().apiValidaFirma(request, usr) ;
				else if (accion.equalsIgnoreCase("infoFirma") ) 
					ret=usr.getMotor().tramActivo().getFirma().apiInfoFirma(request, usr) ;
				
				//firma electrónica: guardar la firma creada después de la ejecución de un trámite
				else if (accion.equalsIgnoreCase("guardaFirmaSeparada") ) {
					
					usr.getMotor().tramActivo().getFirma().guardaArchivosFirmaPorSeparado(request);
					ret=continuarLote(usr);
					}
				else if (accion.equalsIgnoreCase("guardaFirmaJunta") ) {
					usr.getMotor().tramActivo().getFirma().guardaArchivosFirmaJuntos(request);
					ret=continuarLote(usr);
					}
				else if (accion.equalsIgnoreCase("finCaptura") )
					ret=continuarLote(usr);
				else
					throw new ErrorGotta("Acción no definida");
				}
			catch (ErrorTramiteActivoHaCambiado e){
				try {
					ret=continuarLote(usr);
					} 
				catch (ErrorGotta e1) {
					ret=trataError(request, e1, usr);
					}
				}
			catch (ErrorConexionPerdida e) { 
				ret=sesionFinalizada(usr, request, response, e); 
				}
			catch (ErrorVerificandoAccion e){
				ret=trataError(request, e, usr);
				}
			catch (ErrorGotta e) {
				if (usr.getMotor()!=null && usr.getMotor().tramActivo()!=null){
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
					}
				ret=trataError(request, e, usr);
				}
			catch(JSONException e){
				throw new ServletException(e);
				}
			}
		hazFlush(response, ret);
		}
	private IJSONwritable trataError(HttpServletRequest request, Exception e, Usuario usr) throws ServletException{
		try{
			String trozo=null;
			if (usr!=null && usr.getMotor()!=null && usr.getMotor().lote!=null)
				trozo=usr.getMotor().lote.idxBloqueo;
				else if (request.getParameter("idxBloqueo")!=null) 
					trozo=Util.obtenValor(request, "idxBloqueo");
			JSONObject ret2 = new JSONObject().put("tipo","error").put("msg", e.getClass().getName()+": "+ e.toString());
			if(trozo!=null)
				ret2.put("idxBloqueo", trozo);
			return ret2;
			}
		catch (Exception je) {
			throw new ServletException(e);
			}
		}
	private JSONObject sesionFinalizada(Usuario usr, HttpServletRequest request, HttpServletResponse response, ErrorConexionPerdida e) throws IOException {
		//La sesión ha perdido los datos/CAN en autoexec:machacamos info de cookies
		try {
			if (usr!=null){
				Util.resetCookies(usr.getApli().getCd(), response);
				usr.finalize(request);
				}
			JSONObject ret= new JSONObject().put("tipo", "error").put("tipoError", "ErrorSesionCaducada");
			if (e instanceof ErrorUsuarioNoValido)
				ret.put("msg", e.getMessage());
			return ret;
			}
		catch (JSONException e1) {
			throw new IOException(e.toString());
			} 
		catch (ErrorArrancandoAplicacion e2) {
			throw new IOException(e2.toString());
			}
		}

	private IJSONwritable guardaMenu(HttpServletRequest req, Usuario usr) throws ErrorCampoNoExiste, ErrorVolcandoDatos, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorConexionPerdida, JSONException, ErrorTablaNoExiste, NumberFormatException, ErrorArrancandoAplicacion {	
		Boolean hayQueCrearTramite=Util.obtenValorBoolean(req, "hayQueCrearTramite");
		
		String CD_Camino=Util.obtenValor(req, "cd_camino");
		if (CD_Camino!=null && CD_Camino.equals(Constantes.CAD_VACIA))
			CD_Camino=null;
		
		String CD_Tramite=Util.obtenValor(req, "cd_tramite");
		if (CD_Tramite.equals(Constantes.CAD_VACIA))
			CD_Tramite=null;
		
		if (hayQueCrearTramite){
			String DS_Tramite;
			
			DS_Tramite=Util.obtenValor(req, "ds_tramite");
			GestorMetaDatos.guardaTRA_TramitesObjetos(usr.getConexion(), usr.getApli().getEsquema(), Long.parseLong(CD_Camino), CD_Tramite, DS_Tramite, CD_Tramite);	
			}
		Long CD_Boton;
		String CD_BarraHerramientas, CD_MenuPadre, Imagen, texto, titulo;
		String CD_ModoLista = null, disposicion = null, tipoPanel1 = null, CD_NivelInicial = null; Boolean botonera = null;
		String url = null, tipo = null; 
		Boolean esNuevo = false, esModificar = false, esEliminar=false;
		
		CD_Boton=Long.parseLong( Util.obtenValor(req, "cd_boton"));
		CD_BarraHerramientas=Util.obtenValor(req, "cd_barraherramientas");
		CD_MenuPadre=Util.obtenValor(req, "cd_menupadre");
		Imagen=Util.obtenValor(req, "imagen");
		texto=Util.obtenValor(req, "texto");
		titulo=Util.obtenValor(req, "titulo");
		
		CD_ModoLista=Util.obtenValor(req, "cd_modolista");
		disposicion=Util.obtenValor(req, "disposicion");
		tipoPanel1=Util.obtenValor(req, "tipoPanel1");
		CD_NivelInicial=Util.obtenValor(req, "cd_nivelinicial");
		botonera=Util.obtenValorBoolean(req, "botonera");
		
		url=Util.obtenValor(req, "url");
		tipo=Util.obtenValor(req, "tipo");
		
		esNuevo=Util.obtenValorBoolean(req, "esNuevo");
		esModificar=Util.obtenValorBoolean(req, "esModificado");
		esEliminar=Util.obtenValorBoolean(req, "esEliminar");
		
		
		try {
			GestorMetaDatos.guardaMenu(usr.getConexion(), usr.getApli().getEsquema(), CD_Boton,
													CD_BarraHerramientas, CD_MenuPadre, 
													Imagen, texto, titulo, 
													
													CD_Camino, CD_Tramite,
													CD_ModoLista, disposicion, tipoPanel1, CD_NivelInicial, botonera,
													url,
													tipo,
													
													esNuevo, esModificar, esEliminar
													);
			usr.menuGeneral=null;//para forzar la relectura
			return new JSONObject().put("ok", 1);
			}
		catch (ErrorVolcandoDatos e){
			return new JSONObject().put("tipo", "error").put("msg", e.getMessage());
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
		catch (ErrorLBLNoExiste ee){
			//no hay gestión de errores
			} 
		catch (ErrorArrancandoAplicacion ee) {
			//pass
			} 
		catch (ErrorTramiteActivoHaCambiado ee) {
			//pass
			}
		return false;
	}
	private JSONObject actualizaEXP_Estilos(HttpServletRequest request, Usuario usr) throws JSONException, ErrorCampoNoExiste, ErrorVolcandoDatos, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorArrancandoAplicacion, ErrorTablaNoExiste, ErrorConexionPerdida {
		String crear=Util.obtenValorOpcional(request, "crear");
		String eliminar=Util.obtenValorOpcional(request, "eliminar");
		
		if (crear==null) crear=Constantes.CAD_VACIA;
		if (eliminar==null) eliminar=Constantes.CAD_VACIA;
		
		GestorMetaDatos.guardaEXP_Estilos(usr, usr.getApli().getEsquema(), Util.split(eliminar, Constantes.COMA), Util.split(crear, Constantes.COMA));
		return new JSONObject().put("ok", 1);
		}

	private JSONObject guardaTramite(HttpServletRequest req, Usuario usr) throws JSONException, ErrorGotta {
		if(!usr.permisoDisenho)
			throw new ErrorConexionPerdida("No tiene permiso para realizar esta acción");

		String ncd, nds, cd;
		
		ncd=Util.obtenValor(req, "ncd");
		nds=Util.obtenValor(req, "nds");
		cd=Util.obtenValorOpcional(req, "cd");
		
		Long idcamino=Long.parseLong(Util.obtenValor(req, "camino"));
		GestorMetaDatos.guardaTRA_TramitesObjetos(usr.getConexion(), usr.getApli().getEsquema(), idcamino, ncd, nds, cd);

		usr.getApli().recalculaCamino(usr, idcamino);
		return new JSONObject().put("tipo", "refresco");
		}
	private JSONObject guardaCamino(HttpServletRequest req, Usuario usr) throws JSONException, ErrorGotta {
		if(!usr.permisoDisenho)
			throw new ErrorConexionPerdida("No tiene permiso para realizar esta acción");
		String ncd, ds, cd;
		
		ncd=Util.obtenValor(req, "ncd");
		ds=Util.obtenValor(req, "ds");
		cd=Util.obtenValorOpcional(req, "cd");
		
		String tb, tt;
		tb=Util.obtenValorOpcional(req, "tb");
		tt=Util.obtenValorOpcional(req, "tt");
		
		GestorMetaDatos.guardaTRA_Caminos(usr.getConexion(), usr.getApli().getEsquema(), ncd, ds, cd, tb, tt);
		
		Long idcamino=Long.parseLong(ncd!=null?ncd:cd);
		usr.getApli().recalculaCamino(usr, idcamino);
		
		return new JSONObject().put("tipo", "refresco");
	}
	private JSONObject creaPEN(HttpServletRequest req, Usuario usr) throws JSONException, ErrorGotta {
		if(!usr.permisoDisenho)
			throw new ErrorConexionPerdida("No tiene permiso para realizar esta acción");

		String tramite, CD_TramiteExistente=null, CD_TramiteNuevo=null, DS_TramiteNuevo=null;
		Long camino, caminoPEN=null;
		String tramitePEN=null;
		
		camino=Long.parseLong(Util.obtenValor(req, "camino"));
		tramite=Util.obtenValor(req, "tramite");
		
		caminoPEN=Long.parseLong(Util.obtenValor(req, "caminoPEN"));
		
		CD_TramiteExistente=Util.obtenValorOpcional(req, "CD_TramiteExistente");
		if (CD_TramiteExistente==null ){
			CD_TramiteNuevo=Util.obtenValor(req, "CD_TramiteNuevo");
			DS_TramiteNuevo=Util.obtenValor(req, "DS_TramiteNuevo");
			
			tramitePEN=CD_TramiteNuevo;
			}
		else 
			tramitePEN=CD_TramiteExistente;
			
		try {
			GestorMetaDatos.creaPEN(usr.getConexion(), usr.getApli().getEsquema(), camino, tramite, caminoPEN, tramitePEN, CD_TramiteExistente, CD_TramiteNuevo, DS_TramiteNuevo);
			} 
		catch (SQLException e) {
			throw new ErrorGotta("Error generando PEN: "+e.getMessage());
			}
		
		usr.getApli().recalculaCamino(usr, camino);
		return new JSONObject().put("tipo", "refresco");
	}
	private JSONObject borraPEN(HttpServletRequest req, Usuario usr, Esquema esquema) throws JSONException, ErrorGotta {
		if(!usr.permisoDisenho)
			throw new ErrorConexionPerdida("No tiene permiso para realizar esta acción");

		Long camino; String tramite, tramitePEN;
		
		camino=Long.parseLong(Util.obtenValor(req, "camino"));
		tramite=Util.obtenValor(req, "tramite");
		tramitePEN=Util.obtenValor(req, "tramitePEN");
		
		try {
			GestorMetaDatos.eliminaPEN(usr.getConexion(), esquema, camino, tramite, tramitePEN);
			} 
		catch (SQLException e) {
			throw new ErrorGotta("No se ha podido eliminar el PEN: "+e.getMessage());
			}
		
		usr.getApli().recalculaCamino(usr, camino);
		return new JSONObject().put("tipo", "refresco");
	}
	
	private JSONObject verificarAccion(HttpServletRequest request, Usuario usr) throws ErrorGotta, JSONException {
		String op, cond, accion, p1, p2, p3, p4;

		Tramite tram=usr.getMotor().tramActivo();
		String idx=Util.obtenValor(request, "idx");
		String idxTram=idx.substring(idx.indexOf(Constantes.GUIÓN_BAJO));
		
		if (!tram.idxDepuracion.equals(idxTram)) {
			for (Tramite tramB:tram.tramitesSUB.values()){
				if (tramB.idxDepuracion.equals(idxTram)){
					tram=tramB;
					continue;
					}
				}
			}
		accion=Util.obtenValor(request, "acc");
		Accion accver;
		try {
			accver = Accion.getInstancia(accion, tram);
			}
		catch (ErrorAccionDesconocida ee){
			throw new ErrorGotta("¡Acción desconocida!");
			}
		
		op=Util.obtenValor(request, "op");
		cond=Util.vacioANulo(Util.obtenValor(request, "cond"));
		p1=Util.vacioANulo(Util.obtenValor(request, "p1"));
		p2=Util.vacioANulo(Util.obtenValor(request, "p2"));
		p3=Util.vacioANulo(Util.obtenValor(request, "p3"));
		p4=Util.vacioANulo(Util.obtenValor(request, "p4"));
		
		tram.seDebeEjecutar(cond, accver);
		accver.montaTodo(accion, op, p1, p2, p3, p4);
		Fila fila=new Fila(tram.getAcciones().datos);
		accver.verificar(fila);
		
		ArrayList<String> temp=Util.split(idx, Constantes.GUIÓN_BAJO);
		Long CD_Camino; String CD_Tramite;
		temp.remove( temp.size()-1 );
		CD_Camino=Long.parseLong(temp.get( temp.size()-1 )); temp.remove(temp.size()-1);
		op=temp.get(0); temp.remove(0);
		CD_Tramite=Util.join(Constantes.GUIÓN_BAJO, temp); //por si tiene _ en el nombre
		
		return Tramite.AccionJSON(accver, tram, CD_Camino, CD_Tramite, op, cond, accion, p1, p2, p3, p4);
		}
	private void guardarAccion(HttpServletRequest request, Usuario usr) throws ErrorGotta {
		if(!usr.permisoDisenho)
			throw new ErrorConexionPerdida("No tiene permiso para realizar esta acción");

		Long caminoN, opN, caminoV, opV;
		String tramiteV, tramiteN, cond, accion, p1, p2, p3, p4;

		//Tramite tram=usr.getMotor().tramActivo();
		accion=Util.obtenValor(request, "acc");
		
		try {
			Accion.getInstancia(accion, usr.getMotor().tramActivo());
			}
		catch (ErrorAccionDesconocida ee){
			throw new ErrorGotta("¡Acción desconocida!");
			}
		
		try {
			String idx=Util.obtenValorOpcional(request, "idx"); //11_pru_reordenar_-120_1224664
			
			ArrayList<String> temp=Util.split(idx, Constantes.GUIÓN_BAJO);
			
			temp.remove( temp.size()-1 );
			caminoV=Long.parseLong(temp.get( temp.size()-1 )); temp.remove(temp.size()-1);
			opV=Long.parseLong(temp.get(0)); temp.remove(0);
			tramiteV=Util.join(Constantes.GUIÓN_BAJO, temp); //por si tiene _ en el nombre
			
			caminoN=Long.parseLong(Util.obtenValor(request, "camino"));
			tramiteN=Util.obtenValor(request, "tramite");
			opN=Long.parseLong(Util.obtenValor(request, "op"));
			} 
		catch (NumberFormatException e) {
			throw new ErrorGotta(e.getMessage());
			}
		
		cond=Util.vacioANulo(Util.obtenValor(request, "cond"));
		p1=Util.vacioANulo(Util.obtenValor(request, "p1"));
		p2=Util.vacioANulo(Util.obtenValor(request, "p2"));
		p3=Util.vacioANulo(Util.obtenValor(request, "p3"));
		p4=Util.vacioANulo(Util.obtenValor(request, "p4"));
		
		boolean esNueva=Util.obtenValorBoolean(request, "esNueva");
		boolean esBorrar=Util.obtenValorBoolean(request, "esBorrar");
		
		GestorMetaDatos.guardaAccion(usr.getConexion(), usr.getApli().getEsquema(), caminoV, tramiteV, opV, caminoN, tramiteN, opN, cond, accion, p1, p2, p3, p4, esNueva, esBorrar);
		}
	private JSONArray getAplicaciones(Usuario usr) throws ErrorUsuarioNoValido, JSONException {
		if (!usr.permisoMonitorSQL)
			throw new ErrorUsuarioNoValido("El usuario '"+usr.getLogin()+"' no tiene permiso para ver el monitor");
		
		Coleccion<Aplicacion> aplis=PoolAplicaciones.getAplicaciones();
		
		JSONArray ret=new JSONArray();
		for (Aplicacion app:aplis.values())
			ret.put(app.JSON(usr.getIdSesion()));
		return ret;
		}

	private JSONObject eliminarNivel(HttpServletRequest request, Usuario usr) throws ErrorVolcandoDatos, ErrorConexionPerdida, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorDiccionario, JSONException, ErrorArrancandoAplicacion {
		if(!usr.permisoDisenho)
			throw new ErrorConexionPerdida("No tiene permiso para realizar esta acción");

		String idObjeto=Util.obtenValor(request, "nivel");
		String tipoObjeto=Util.obtenValor(request, "tipo"); //admitidos: nivel, guion
		
		if (tipoObjeto.equalsIgnoreCase("nivel")){
			GestorMetaDatos.eliminarNivel(usr.getConexion(), usr.getApli().getEsquema(), idObjeto);
			usr.getApli().leerNiveles();
			}
		else if (tipoObjeto.equalsIgnoreCase("guion")){
			GestorMetaDatos.eliminarGuion(usr.getConexion(), usr.getApli().getEsquema(), idObjeto);
			}
		return new JSONObject().put("tipo", "refresco");
	}
	
	private String cadVaciaANulo(HttpServletRequest request, String clave) {
		String ret=Util.obtenValorOpcional(request, clave);
		if (ret==null)
			return ret;
		else if (ret.equals(""))
			return null;
		else 
			return ret;
	}
	private JSONObject guardarMD(HttpServletRequest request, Usuario usr) throws ErrorConexionPerdida, ErrorDiccionario, ErrorVolcandoDatos, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorArrancandoAplicacion {
		if(!usr.permisoDisenho)
			throw new ErrorConexionPerdida("No tiene permiso para realizar esta acción");

		String json=Util.replaceTodos(Util.obtenValor(request, "datos"),"\\","\\\\");

		try {
			JSONArray ret = new JSONArray(json);
			ArrayList<Coleccion<Object>> datos= ponTiposJavaEXP_Controles( ret, GestorMetaDatos.leeMaxNumControl(usr.getConexion(), usr.getApli().getEsquema()) );

			String md=Util.obtenValor(request, "md");
			String tablaBase=cadVaciaANulo(request,"tb");
			String letras=cadVaciaANulo(request, "letras");

			Aplicacion apli=usr.getApli();
			GestorMetaDatos.guardarMD(usr.getConexion(), apli.getEsquema(), md, letras, tablaBase, datos);

			//si todo ha ido bien, refrescamos el modo detalle:
			apli.leerDetalles();
			for (ModoListaDef ml:usr.vistas.values())
				ml.md=apli.getModoDetalleDef(ml.getCd());
			
			apli.leerNiveles();

			return new JSONObject("{tipo:refresco}");
			} 
		catch (JSONException e) {
			throw new ErrorVolcandoDatos(e.toString());
			}
		}
	private ArrayList<Coleccion<Object>> ponTiposJavaEXP_Controles(JSONArray f, int maxIdControl) throws JSONException{
		ArrayList<Coleccion<Object>> ret = new ArrayList<Coleccion<Object>>();
		HashMap<String, BigDecimal> lista=new HashMap<String, BigDecimal>();
		
		for (int j=0; j<f.length();j++){
			JSONObject pd = f.getJSONObject(j);
			Coleccion<Object> fila=new Coleccion<Object>();
			Iterator<?> claves = pd.keys();
			while (claves.hasNext()){
				String k = (String)claves.next(); 
				Object v=pd.get(k);
				if (v.equals(null))
					v=null;
				k=k.toLowerCase();
				if (v!=null && Util.en(k, "izq", "tope", "ancho", "alto"))
					v=new BigDecimal( Double.parseDouble(v.toString())*15 );
				else if (v!=null && Util.en(k, "numcontrol", "cont"))
					if (v.toString().startsWith("_nuevo_")) {
						if (lista.containsKey(v.toString()))
							v=lista.get(v.toString());
						else {
							maxIdControl++;
							BigDecimal vv=new BigDecimal(maxIdControl);
							lista.put(v.toString(), vv);
							v=vv;
							}
						}
					else
						v=new BigDecimal( v.toString());	
				else if (Util.en(k, "pestanha", "tamletra"))
					if (v==null || v.equals(""))
						v=BigDecimal.ZERO;
					else if (v instanceof Boolean)
						v=1;
					else
						v=new BigDecimal(v.toString());
				else if (Util.en(k, "modificado", "nuevo", "borrado", "borde")) {
					if (v instanceof Boolean) { 
						//v= (Boolean)v;
						}
					else
						v= (v!=null && v.toString().equals("1"));
					}
				fila.put(k, v);
				}
			ret.add( fila );
			
		}
		return ret;
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
	
	void alternarPuntoInterrupcion(boolean poner, String idx, Usuario usr) throws ErrorConexionPerdida{
		if(!usr.permisoDepuracion)
			throw new ErrorConexionPerdida("No tiene permiso para realizar esta acción");

		idx=idx.substring(0, idx.lastIndexOf( Constantes.GUIÓN_BAJO ));
		if (poner)
			usr.puntosDeParo.add(idx) ;
		else
			usr.puntosDeParo.remove(idx) ;
	} 
	private void recargarAcciones(Usuario usr) throws ErrorConexionPerdida, ErrorArrancandoAplicacion, ErrorGotta{
		if(!usr.permisoDepuracion)
			throw new ErrorConexionPerdida("No tiene permiso para realizar esta acción");
	
		Tramite tramActivo=usr.getMotor().lote.tramActivo();
		tramActivo.recargarAcciones();
		for (Tramite tr:tramActivo.tramitesSUB.values()){
			tr.recargarAcciones();
			}
		usr.getMotor().lote.devolverDepuradorCompleto=true;
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
				usr.borraMotores();//al final borramos los motores creados
				return jsret;
				}
			if(respuesta==Motor.Resultado.SALIR){
				jsret.put("direccion", "salir?aplicacion="+usr.getApli().getCd());
				jsret.put("tipo", "redirigir");
				ristraRefresco(usr, jsret);
				usr.borraMotores();//al final borramos los motores creados
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
			if(prod.getClass().getName().equalsIgnoreCase("wordcliente")){
				WordCliente this2 = (WordCliente) prod;			
				jsret.put("accion", this2.accion);
				jsret.put("plantilla", this2.plantilla);
				jsret.put("contadoc", this2.contadoc);
				jsret.put("docid", this2.docid);
				jsret.put("dotweb", usr.getApli().getDatoConfig("dotweb"));
				jsret.put("aplicacion", usr.getApli().getCd());
				jsret.put("JSISSION_ID", usr.getIdSesion());
			}
		}
		if (ristraInforme.length()>0)
			jsret.put("informes",ristraInforme.substring(1));
		
		jsret.put("idxBloqueo", usr.getMotor().lote.idxBloqueo) ;
		if (respuesta==Motor.Resultado.CAPTURA)
			motor.getCaptura().JSON(usr.getIdSesion(),jsret);
		else if (respuesta==Motor.Resultado.FIRMA_ELECTRONICA_DATOS){
			motor.tramActivo().getFirma().firmaDatos(jsret,usr);
			}
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
	
	private  static JSONObject saltarTramite(Usuario usr) throws ErrorGotta {
		usr.getMotor().lote.siguienteTramite();
		return continuarLote(usr);
	}
/////////////////////////////////////////////	
	private static Coleccion<String> _recogeValoresPeticion(HttpServletRequest request, Usuario usr){
		//recogemos los valores del formulario, volvemos a validarlos
		Coleccion<String> valores =new Coleccion<String>();
		Enumeration<String> parametros=request.getParameterNames();
		while (parametros.hasMoreElements()) {
			String param=parametros.nextElement().toString();
			if (!param.equals("continuar")) {
				String tempValor=Util.obtenValor(request, param);
//				if (tempValor.contains(Constantes.SEPARADOR))
//					tempValor=Util.replaceTodos(tempValor, Constantes.SEPARADOR, Constantes.SEPARADOR+Constantes.SEPARADOR);
				valores.put(Util.desdeUTF8(param), tempValor );
				}
			}
		return valores;
		}

	private static void recogeValoresFRM (HttpServletRequest request, Usuario usr) throws ErrorGotta {					
		//recogemos los valores del formulario, volvemos a validarlos
		Coleccion<String> valores =_recogeValoresPeticion(request, usr);
		
		Motor motor = usr.getMotor();
		if (motor.mensaje!=null) {
			motor.mensaje.okMD(valores);
			motor.mensaje=null;
			}
		else if (motor.frmDinamico!=null){
			motor.frmDinamico.okMD( valores);
			motor.frmDinamico=null;
			}
		}
	private static JSONObject continuarLote(Usuario usr) throws ErrorGotta {
		try {
			return respuestaMotor(usr.getMotor().lote.seguir(), usr);
			} 
		catch (JSONException e) {
			throw new ErrorGotta(e);
			}
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
	
	private Buscador sacaBuscDeJS(HttpServletRequest request, Usuario usr, String stablaCampo) throws ErrorTablaNoExiste, ErrorArrancandoAplicacion{
		ArrayList<String> temp=Util.split(stablaCampo);
		String tc=temp.get(0); 
		ArrayList<String> tablaCampo=Util.split(tc, ".");
		
		int bloqueadas=Integer.parseInt(request.getParameter("bloqueadas"));
		return new Buscador(usr, tablaCampo.get(0), bloqueadas); // usuario, String nombre, String nombretabla, String nombrecoldes
	}
	private JSONObject buscar(HttpServletRequest request, Usuario usr) throws ErrorGotta, JSONException {
		String _bsp=Util.obtenValor(request, "bsp");
		Buscador buscador=null;

		int bloqueadas=0;
		if ( _bsp.startsWith("control") ) {
			String tablaCol=Util.obtenValor(request, "tabla");
			buscador=this.sacaBuscDeJS(request, usr, tablaCol);
			}
		else {
			ControlFrm _bsc;
			if (usr.getMotor()!=null && usr.getMotor().frmDinamico !=null) {
				_bsc=usr.getMotor().frmDinamico.getControl(_bsp);
				if (_bsc==null || usr.getMotor().frmDinamico.getControl(_bsp)==null) { 
					buscador=this.sacaBuscDeJS(request, usr, _bsp);
					bloqueadas= buscador.bloqueadas;
					}
				else {
					buscador=_bsc.getBsc().getBuscador();
					bloqueadas= _bsc.colsBloqueadas;
					}
				buscador.bloqueadas=bloqueadas;
				}
			else { // Se trata de un formulario de los creados integramente en javascript: 
				   // 	vendrá aquí en plan "Tabla.ColumnaDescripción 1 0"
				buscador=this.sacaBuscDeJS(request, usr, _bsp);
				bloqueadas= buscador.bloqueadas;
				buscador.rellenaListaCols();
				}
			}
		
		String cadenaBuscar = Util.obtenValor(request,"buscar");

		ArrayList<Object> casillasBloqueadas= new ArrayList<Object>();
		if ( bloqueadas>0 )  {
			int i=0;
			while (request.getParameterMap().containsKey("valor"+i)) {
				casillasBloqueadas.add( Util.obtenValor(request,"valor"+i));
				i++;
				}
			}
		Filas filas = buscador.buscarPorDesc(cadenaBuscar, casillasBloqueadas);
		JSONObject ta = new JSONObject();
		ta.put("cd_tabla", buscador.tabla.getCd());
		ta.put("colBusq", buscador.tabla.getColBúsq());
          ta.put("tabla", buscador.ds_tabla);
		ta.put("numOcultas", buscador.tabla.getCampoClave().numColumnas());
		ta.put("altaDinamica", buscador.tabla.caminoMantenimiento);
//		return filas.JSON(buscador.listaCols, ta,buscador.listaCabeceras );
		return filas.JSON(true, false, ta, buscador.listaCols, buscador.listaCabeceras );
		}

	public static NodoActivo rellenameNodoActivo(HttpServletRequest request, Usuario usr) throws ErrorGotta{
		usr.nodoArranque=null;
		return NodoActivo.desdeJSON(request, usr);
		}
	private JSONObject rellenaMD(HttpServletRequest request, Usuario usr) throws JSONException, ErrorGotta{
		NodoActivo na=rellenameNodoActivo(request, usr);
		
		String cambiarNodoActivo = Util.obtenValorOpcional(request, "cambiarNodoActivo");
		if (cambiarNodoActivo!=null && na.nodoActivo!=null && (usr.arbol.nodoActivo==null || !usr.arbol.nodoActivo.equals(na.nodoActivo)))
			usr.arbol.nodoActivo=na.nodoActivo;
		
		ModoDetalleDef md=na.md;
		if (md == null) 
			md=na.ml.md;
		
		HashMap<String, String> controles=null;
		if (usr.arbol.nodoDestino!=null && usr.arbol.nodoActivo.equals(usr.arbol.nodoDestino.nodoActivo)
				&& usr.arbol.nodoDestino.md!=null && md.equals(usr.arbol.nodoDestino.md)){
			controles=usr.arbol.nodoDestino.controles;
			usr.arbol.nodoDestino=null; //ya pa la siguiente no hace falta
			}
		return new ModoDetalle(usr, md).getDatos(na, controles);
		}
	private JSONObject montaLVW(HttpServletRequest request, Usuario usr) throws ErrorGotta, JSONException{ //monta el lvw
		int cuantos=0;//-->maxElementos
		String cantidad = Util.obtenValor(request, "cuantos");
		if (cantidad!=null)
			cuantos=Integer.parseInt(cantidad);

		return montaControlConNivel(request, usr, "lvw", cuantos);
		}
	private JSONObject paginaLVW(HttpServletRequest request, Usuario usr) throws ErrorGotta, JSONException{ //monta el lvw
		String nivel = Util.obtenValor(request, "nivel");
		String param = request.getParameter("lvw");
		int numPag=Integer.parseInt(request.getParameter("pagina"));
		
		NodoActivo na=rellenameNodoActivo(request, usr);
		na.numPagina=numPag;
			
		ControlDef cdef=new ControlDef(usr.getApli().getCd());
		cdef.numControl=param;
		ControlLVW www= new ControlLVW(cdef, usr);
		return www.JSON(na, nivel, -1);
		}
	private JSONObject montaRD(HttpServletRequest request, Usuario usr) throws ErrorGotta, JSONException{ //monta el lvw
		return montaControlConNivel(request, usr, "rd", -1);
		}
	private JSONObject montaWWW(HttpServletRequest request, Usuario usr) throws ErrorGotta, JSONException{ //monta el lvw
		return montaControlConNivel(request, usr, "www", 1);
		}
	private JSONObject montaControlConNivel(HttpServletRequest request, Usuario usr, String nombreParam, int limite) throws ErrorGotta, JSONException{ //monta el lvw
		String nivel = Util.obtenValor(request, "nivel");
		if (nivel==null) 
			nivel="nivelVacio";
		String param = request.getParameter(nombreParam);
		
		NodoActivo na=rellenameNodoActivo(request, usr);
		
		ControlDef cdef=new ControlDef(usr.getApli().getCd());
		cdef.numControl=param;
		ControlLVW www= new ControlLVW(cdef, usr);
		return www.JSON(na, nivel, limite);
		}
	
	private void activaDepuracion(HttpServletRequest request, Usuario usr) {
		String estado=request.getParameter("estado");
		if (usr.permisoDepuracion && estado != null)
			usr.tipoDepuracion=(estado.equals("true") ? Motor.TDepuracion.PAUSA : Motor.TDepuracion.SIN_DEPURACION);
		}
	private void activaDisenho(HttpServletRequest request, Usuario usr) {
		String estado=request.getParameter("estado");
		if (estado != null)
			usr.disenho= estado.equals("true") && usr.permisoDisenho; 
		}
	
	private JSONObject arbol(HttpServletRequest request, Usuario usr) throws ErrorGotta, JSONException {
		int cuantos=0;//-->maxElementos
		String cantidad = request.getParameter("cuantos");
		String accion = request.getParameter("accion");
		
		if (cantidad!=null)
			cuantos=Integer.parseInt(cantidad);

		if ( Util.en(accion, Constantes.ARB, Constantes.SUBGRID) ){//árbol del detalle
			NodoActivo na=rellenameNodoActivo(request, usr);
			
			String nivel = request.getParameter("nivel");
			if (nivel.equals("") || nivel.equals("*"))
				nivel="nivelVacio";
			
			Arboljson xarb=usr.getArbol( usr.getApli().getNivelDef(nivel) );
			if (usr.arbol==null)
				usr.arbol=new Arboljson(usr, usr.getApli().getNivelDef(nivel));
			
			xarb.nodoActivo=na.nodoActivo;
			if (na.ml!=null)
				xarb.ml=na.ml.getCd();
			
			INivelDef nivelDef=usr.getApli().getNivelDef( nivel );
			
			Nodo nnodoRaiz=Nodo.generaNodoPadreParaARB(xarb, nivelDef, usr.arbol.nodoActivo);
			if (nivelDef.esDeSistema){
				//generamos el nodo padre mágicamente, a partir de lo que venga en nodoFicticio
				String nodoFicticio=Util.obtenValorOpcional(request, "nodoFicticio");
				if (nodoFicticio==null)
					nodoFicticio=Constantes.CAD_VACIA;
				
				String clave="arb-"+nivelDef.getCd()+Constantes.SEPARADOR+nodoFicticio+Constantes.SEPARADOR;
				if (usr.arbol.nodoDestino!=null)
					clave+=usr.arbol.nodoDestino.letras;
				
				nnodoRaiz= Nodo.generaNodoPadreParaARB(usr.arbol, nivelDef, clave);
				}
			
			return xarb.cargar( nivelDef, nnodoRaiz);
			}
		else if (accion.equalsIgnoreCase("arbol")){ //al pinchar un modolista o en refrescar
			String vista = request.getParameter("vista"); //incluido el modolista mágico 'SelectorColumnas'
			ModoListaDef ml= usr.getModoListaDef(vista);
			if (ml==null)
				throw new ErrorGotta("'"+vista+"' no es una vista válida para esta aplicación y usuario");
//			usr.arbol.nodoDestino=new NodoActivo(usr, usr.arbol. usr.arbol.nodoActivo);
			
			usr.arbol=usr.getArbol(ml);
			
			if (usr.nodoArranque!=null) {
				usr.arbol.nodoDestino=new NodoActivo(usr, usr.nodoArranque.md, usr.nodoArranque.ml, usr.nodoArranque.letras);
				usr.arbol.nodoDestino.controles=usr.nodoArranque.controles;
				
				usr.nodoArranque=null;
				}
			else if (usr.arbol.nodoDestino==null) {
				NodoActivo dest=rellenameNodoActivo(request, usr);
				if (dest.nodoActivo==null && dest.ml!=null && dest.ml.getCd().equalsIgnoreCase(usr.arbol.ml))
					dest.nodoActivo=usr.arbol.nodoActivo;
				if (usr.arbol.nodoDestino!=null){
					if (usr.arbol.nodoDestino.ml!=null)
						usr.arbol.nodoDestino=dest;
					}
				}
			
			JSONObject ret= usr.arbol.cargar(ml, null);
			usr.arbol.nodoDestino=null;
			return ret;
			}
		else { //expandir un nodo o pinchar en 'cargar otros 50'
			String nodo= Util.obtenValor(request,"nodo");
			
			usr.arbol.maxElementos=usr.maxElementos;
			String expandir= request.getParameter("expandir");
			if (expandir != null) {
				Boolean _expandir=expandir.equals("true");
				return usr.arbol.expandirContraerNodo(nodo, _expandir, cuantos);
				}
			//ha pinchado en cargar otros n
			return usr.arbol.usarMaxElementos(nodo, cuantos);
			}
		}
	private JSONObject controlesMD(HttpServletRequest request, Usuario usr) throws ErrorGotta, JSONException {
		String cd=Util.obtenValorOpcional(request, "md");
		
		ModoDetalleDef m=usr.getModoDetalleDef(cd);
		if (m != null)
			 return m.JSON(usr);
		else
			return null;
		}
	private JSONObject controlesML(HttpServletRequest request, Usuario usr) throws ErrorGotta, JSONException {
		String cd=Util.obtenValorOpcional(request, "ml");
		
		ModoListaDef m=usr.getModoListaDef(cd);
		if (m != null)
			return m.JSON(usr);
		else
			return null;
		}
	private JSONArray evaluaExpresionMD(HttpServletRequest request, Usuario usr) throws ErrorGotta {
		NodoActivo na=rellenameNodoActivo(request, usr);
		String expresion = Util.obtenValor(request, "exp");
		
		ModoDetalle md=new ModoDetalle(usr, na.md);
		
		na.cargaTablas();
		return new JSONArray().put(md.rellenaTXT(na, expresion));
		}
	private void _asignaValorTemporal(Variables variables, String nombre, String tipo, Object valor){
		Variable v=variables.get(nombre);
		if (v!=null){
			variables.guardaRollback(v);
			v.setValor(valor);
			}
		else
			variables.initVar(nombre, tipo, valor);
		}
	private JSONObject evaluaExpresionFormularioDinámico(HttpServletRequest request, Usuario usr) throws JSONException {
		//formularios dinámicos: al cambiar el valor de un comboBox, aparecen y desaparecen campos
		JSONObject ret=new JSONObject();
		FrmDinamico frm=usr.getMotor().frmDinamico;
		
		Coleccion<String> valoresFRM=_recogeValoresPeticion(request, usr);//ojo, son valores temporales y no hay que validarlos ni guardarlos
		//
		Variables variables=usr.getMotor().lote.variables;
		variables.limpiaRollback();
		String nombre; Object valor;
		for (ControlFrm xctl: frm.controles.values()){
			if (xctl.tipo==null || xctl.tipo.equals(Constantes.CAD_VACIA)){
				
				}
			else if (xctl.cRef!=null && xctl.tRef!=null){
				for (String n: xctl.getBsc()._listaNombresCasillas){
					nombre=n;
					valor=valoresFRM.get(nombre);
					
					this._asignaValorTemporal(variables, nombre, xctl.tipo, valor);
					}
				}
			else {
				nombre=xctl.nombre;
				valor=valoresFRM.get(nombre);
				
				this._asignaValorTemporal(variables, nombre, xctl.tipo, valor);
				if (valor!=null && valor.toString().contains(Constantes.SEPARADOR)){
					ArrayList<String> trozos=Util.split(nombre+valor.toString(), Constantes.SEPARADOR); // p1\c2\d0
					for (int i=0; i<trozos.size(); i++){
						String trozo=trozos.get(i);
						if (!(trozo.startsWith(Constantes.ARROBA))){
							String tnombre=trozo.substring(0, 1);
							String tvalor=trozo.substring(1);
							
							this._asignaValorTemporal(variables, tnombre, Constantes.STRING, tvalor);
							}
						}
					}
				}
			}
		
		try {
			for (BloqueCodigo b: frm.bloquesCodigo.values()){
				Boolean resp=null;
				Object resultadoCondicion = usr.getMotor().eval.evalua(b.condicion, true) ;
				if (resultadoCondicion.equals(true) || resultadoCondicion.equals("1"))
					resp=true;
				else
					resp=false;
				ret.put("expresión "+b.nombre, resp);
				}
			}
		catch (es.burke.gotta.ErrorEvaluador e) {
			//throw e;
			ret.put("error", e.getMessage());
			}
		catch (Throwable e) {
			//throw new ErrorEvaluador(e.getMessage());
			ret.put("error", e.getMessage());
			}
		finally{
			variables.rollback();
			}
		return ret;
		}
	private String evaluaExpresionMotor(HttpServletRequest request, Usuario usr) throws ErrorConexionPerdida {
		//depurador: evalúa los parámetros de las acciones del trámite (te monta un tooltip con el valor)
		if(!usr.permisoDepuracion)
			throw new ErrorConexionPerdida("No tiene permiso para realizar esta acción");

		String expresion = Util.obtenValor(request, "exp");
		try {
			String ret = usr.getMotor().eval.evalua( expresion ).toString() ;
			if (ret.equals(Constantes.CAD_VACIA))
				ret="NULL";
			return ret;
			}
		catch (es.burke.gotta.ErrorEvaluador e) {
			return "null";//ret="{"+e.getMessage()+"}";
			}
		catch (Throwable e) {
			return "null";}
		}
	private JSONObject menuBBB(HttpServletRequest request, Usuario usr) throws JSONException{
		//monta el menú dinámico (normalmente de listados)
		String md=Util.obtenValor(request, "md");
		Filas filas=usr.getMenuBBB(md, usr.arbol.ml);
		
		JSONObject ret=null;
		if (filas!=null)
			ret=filas.JSON();
			
		return new JSONObject()
			.put("menus", ret
			); 
		}
	private JSONArray traeNivel(HttpServletRequest request, Usuario usr) throws ErrorConexionPerdida, ErrorDiccionario, JSONException, ErrorArrancandoAplicacion{
		if(!usr.permisoDisenho)
			throw new ErrorConexionPerdida("No tiene permiso para realizar esta acción");

		JSONArray ret = new JSONArray();
		JSONObject el= new JSONObject();
		
		String idObjeto= Util.obtenValorOpcional(request, "nivel");
		String tipoObjeto=Util.obtenValorOpcional(request, "tipo"); //admitidos: vista, nivel, guion
		
 		if (idObjeto==null) { //palabra mágica que indica el nivel actual del árbol
 			try {
 				idObjeto=usr.arbol.getNodoActivo().rama.nivel;
 				}
 			catch (NullPointerException e){
 				//No hay nodo activo, continuamos para bingo
 				el.put("cd", "Sin título")
 					.put("sql", Constantes.CAD_VACIA)
 					.put("params", Constantes.CAD_VACIA)
 					.put("tipoObjeto", "nivel");
 				ret.put(el);
 				return ret;
 				}
			}
 		
 		ArrayList<Coleccion<Object>> lista;
		if (tipoObjeto.equalsIgnoreCase("vista")){//vista o proc almacenado; generamos un nivel que lo envuelva
			lista=GestorMetaDatos.sacaCodigoProcAlmacenado(usr.getConexion(), idObjeto);
			
			if (lista.size()==0){
				el.put("cd", "");el.put("sql", ""); el.put("params", "");
				ret.put(el);
				}
			else {
				Coleccion<Object> obj=lista.get(0);
				el.put("prop", obj.get("prop"))
					.put("sql",  obj.get("texto"))
					.put("f_modificacion", obj.get("f_modificacion"))
					.put("tipoObjeto", obj.get("tipoObjeto"))
					.put("nombreObjeto", obj.get("nombreObjeto"))
					.put("cd", obj.get("nombreObjeto") )	
					.put("idx", obj.get("tipoObjeto")+" "+idObjeto)
					.put("params", Constantes.CAD_VACIA);
				ret.put(el);
				}
			}
		else if (tipoObjeto.equalsIgnoreCase("nivel")) {
			INivelDef n= usr.getApli().getNivelDef(idObjeto);
			
			JSONArray params= new JSONArray();
			for (Parametro p:n.getColParams())
				params.put(p.JSON());
			
			lista=GestorMetaDatos.sacaCodigoProcAlmacenado(usr.getConexion(), n.getTexto());
			if (lista.size()>0){
				for (int i=0; i<lista.size();i++){
					Coleccion<Object> obj=lista.get(i);
					String nombreObjeto=Util.toString(obj.get("nombreObjeto"));
					
					el.put("prop", obj.get("prop")) //propietario
						.put("sql",  obj.get("texto"))
						.put("f_modificacion", obj.get("f_modificacion"))
						.put("tipoObjeto", obj.get("tipoObjeto"))
						.put("nombreObjeto", obj.get("nombreObjeto"))
						.put("cd", n.getCd())
						.put("idx", (nombreObjeto!=null? obj.get("tipoObjeto")+" "+nombreObjeto: null))
	 					.put("params", params);
	 				ret.put(el);
					}
				}
			else{
				el.put("sql",  n.getTexto())
					.put("cd", n.getCd())
					.put("idx", "nivel "+n.getCd())
					.put("params", params);
				ret.put(el);
				}
				
			}
		else if (tipoObjeto.equalsIgnoreCase("guion")){
			String código=GestorMetaDatos.concatenaResultado(GestorMetaDatos.leeDLL_Guiones(usr.getConexion(), idObjeto), Constantes.vbCr);
			
			el.put("sql",  código)
				.put("tipoObjeto", tipoObjeto)
				.put("nombreObjeto", idObjeto)
				.put("cd", idObjeto)
				.put("idx", tipoObjeto+" "+idObjeto);
				ret.put(el);
			}
		
		return ret;	
		}
	private IJSONwritable diccionarioFisico(HttpServletRequest request) throws JSONException {
		try {
			DiccionarioFisico df = new DiccionarioFisico(request);
			String op= request.getParameter("operacion");
			
			if (op.equalsIgnoreCase("lecturaFisica")) 			return df.leeDiccionarioFisico();
			else if(op.equalsIgnoreCase("lecturaLogica")) 		return df.leeDiccionarioLogico();
			
			else if(op.equalsIgnoreCase("tabla")) 			return df.edDIC_Tablas(request);
			else if(op.equalsIgnoreCase("columna")) 		return df.edDIC_Columnas(request);
			else if(op.equalsIgnoreCase("campo")) 			return df.edDIC_Campos(request);
			else if(op.equalsIgnoreCase("referencia")) 		return df.edDIC_Referencias(request);
			
			else if(op.equalsIgnoreCase("sacaCatalogo")) 		return  df.sacaCatalogos();
//			else if (op.equals("columnasLecturaN"))				return  df.getColumnasPorLecturaN(request);
			else if(op.equalsIgnoreCase("tablacolumnas"))		return  df.getColumnasTabla(request);
			else if(op.equalsIgnoreCase("tablareferencias"))	return  df.getReferenciasTabla(request);
			else
				throw new ErrorGotta("Operación desconocida: diccionarioFisico."+op);
			} 
		catch (ErrorGotta e) {
			return new JSONObject().put("tipo", "error").put("msg", e.getMessage());
		}
		
	}
//	private String imagenes(Usuario usr) throws ErrorGotta{
//		Aplicacion apli= usr.getApli();
//		String icoWeb=apli.getContexto()+apli.getDatoConfig("icoWeb");
//		
//		String[] imgs = Util.imagenes(icoWeb);
//
//		StringBuffer sb = new StringBuffer("{'imagenes':[");
//		for (String img:imgs) {
//			if (!img.contains(Constantes.PUNTO))
//				continue;
//			sb.append("{");
//			sb.append( Util.pareja("nombre", img));
//			sb.append( Util.pareja("ruta",   icoWeb+img, false));
//			sb.append("},");
//			}
//		sb = Util.quita(sb, 1);
//		sb.append("]}");
//		return sb.toString();
//	}
	private JSONObject ejecutaNivel(HttpServletRequest request, Usuario usr) throws ErrorGotta, JSONException{
		if(!usr.permisoDisenho)
			throw new ErrorConexionPerdida("No tiene permiso para realizar esta acción");

		String sql=Util.obtenValor(request, "sql");
		String params=Util.obtenValor(request, "params");
		String cd_nivel=Util.obtenValor(request, "cd");
		
		////////
		Nodo nodo=usr.arbol!=null?usr.arbol.getNodoActivo():null;
		
		String etiquetaCasoDePrueba="#caso de prueba:";
		int indice=sql.toLowerCase().indexOf(etiquetaCasoDePrueba);
		if (indice>1){ //parece que viene un caso de prueba distinto del nodo activo
			String casoDePrueba=sql.substring(indice+etiquetaCasoDePrueba.length(), sql.indexOf("\n", indice+1));
			if (nodo!=null)nodo.cd=casoDePrueba;
			}
		
		if (Util.esNivelJython(sql))
			return ejecutaNivelJython(cd_nivel, sql, params, usr, nodo);
		else {
			if (Util.esProcAlmacenado(sql))
				sql=usr.getApli().getNivelDef(cd_nivel).getTexto();
			return ejecutaNivelSQL(sql, params, usr, nodo);
			}
		}
	
	private JSONObject ejecutaNivelJython(String cd_nivel, String codigo, String params, Usuario usr, Nodo nodo) throws JSONException, ErrorGotta{
		if(!usr.permisoDisenho)
			throw new ErrorConexionPerdida("No tiene permiso para realizar esta acción");

		INivelDef nivelDef=Util.creaNivelJython(cd_nivel, codigo, params, usr.getApli());
		INivel nivel=nivelDef.obtenerNivel();
		nivel.usuario=usr;
		
		List<Object> valores=nivel.getValores(null, Util.nodoAHash(usr.arbol.getNodoActivo()) );
		return nivel.lookUp(usr.getConexion(usr.maxElementos), usr, nodo, -1).JSON(true, true, new JSONObject().put("parametros", valores));
		}
	
	private JSONObject ejecutaNivelSQL(String sql, String params, Usuario usr, Nodo nodo) throws JSONException, ErrorGotta{
		if(!usr.permisoDisenho)
			throw new ErrorConexionPerdida("No tiene permiso para realizar esta acción");

		NivelDef nivelDef= new NivelDef("xx", sql, params);
		Nivel nivel=new Nivel(nivelDef);
		nivel.usuario=usr;
		try {
			List<Object> valores=nivel.getValores(null, Util.nodoAHash(nodo));			
			return nivel.lookUp(usr.getConexion(), usr, nodo, -1).JSON(true, true, new JSONObject().put("parametros", new JSONArray(valores)));
			} 
		catch (ErrorGotta e) {
			String msg=e.toString();
			//probamos a ejecutarlo como SQL sin más por si ak
			try {
				return usr.getConexion().lookUp(sql).JSON();
				} 
			catch (SQLException e1) {
				throw new ErrorGotta(msg+"\n"+e1.getMessage());
				}
			}
	}
	private JSONObject ejecutaSQL(HttpServletRequest request, Usuario usr) throws ErrorGotta, JSONException{
		if(!usr.permisoDisenho)
			throw new ErrorConexionPerdida("No tiene permiso para realizar esta acción");

		String sql=Util.obtenValor(request, "sql");
		try {
			return usr.getConexion().lookUp(sql, null, true).JSON();
			} 
		catch (SQLException e) {
			throw new ErrorGotta(e.getMessage());
			}
		}
	
	private String sacaNombreProcAlmacenado(String sql){
		/*
		 * Create|alter   procedure   nombre
		 *(par1, par2)
		 * */
		sql=sql.toLowerCase();
		ArrayList<String> trozos=xsplit(Util.replaceTodos(sql, "\n", " "));
		for (int i=0; i<trozos.size()-2; i++){
			if (Util.en(trozos.get(i), "create", "replace") && trozos.get(i+1).equals("procedure")){
				//proc oracle
				return quitaCaracteresRaros(trozos.get(i+2));
				}
			else if (Util.en(trozos.get(i), "create", "replace") && 
					trozos.get(i+1).equals("package") &&
					trozos.get(i+2).equals("body")){
				//body package
				return quitaCaracteresRaros(trozos.get(i+3));
				}
			else if (Util.en(trozos.get(i), "create", "replace") && 
					trozos.get(i+1).equals("package")){
				//cabecera package
				return quitaCaracteresRaros(trozos.get(i+2));
				}
			else if (Util.en(trozos.get(i), "create", "alter") && trozos.get(i+1).equals("procedure")){
				return quitaCaracteresRaros(trozos.get(i+2));
				} 
			}
		return null;
	}
	private String quitaCaracteresRaros(String trozo){
		Pattern p = Pattern.compile("[a-z][a-z0-9$#_]*");
		Matcher m = p.matcher(trozo);
		
		String ret="";
		try{
			while (ret!=null){
				m.find();
				ret=m.group();
				} 
			}
		catch (IllegalStateException e){
			//pass
			}
		return ret;
		}
	private String sacaSQLnivelDelProcAlmacenado(String sql, String params){
//		sql=sql.toLowerCase();
		String ristraInterrogantes="";
		if (!params.trim().equals(""))
			ristraInterrogantes=Util.quita( Util.repite("?,", params.split(",").length ), ",");
		return "{call "+sql+"("+ristraInterrogantes+")}";	
	}
	private ArrayList<String> xsplit(String texto){
		ArrayList<String> ret= new ArrayList<String>();
		ArrayList<String> trozos=Util.split(texto);
		for (int i=0; i<trozos.size(); i++){
			if (!trozos.get(i).equals(Constantes.CAD_VACIA))
				ret.add(trozos.get(i));
			}
		return ret;
	}
	private JSONObject guardaNivel(HttpServletRequest request, Usuario usr) throws ErrorGotta, JSONException {
		if(!usr.permisoDisenho)
			throw new ErrorConexionPerdida("No tiene permiso para realizar esta acción");
		
		String params=Util.obtenValor(request, "params");
		String textoEditor=Util.obtenValor(request, "textoEditor");
		String sqlNivel=Util.obtenValor(request, "sqlNivel");
		
		String idObjeto=Util.obtenValor(request, "cd");
		String tipo =Util.obtenValor(request, "tipo"); //admitidos: nivel, guion 
		String tipoObjetoBD=Util.obtenValor(request, "tipoObjeto"); //tipo de objeto de la bd: package, package body...
		
		idObjeto=idObjeto.trim();
		if (tipo.equalsIgnoreCase("nivel") || tipo.equalsIgnoreCase(Constantes.CAD_VACIA) ){
			return _guardaNivel(request, usr, idObjeto, textoEditor, sqlNivel, params, tipoObjetoBD);
			}
		else {//if (tipo.equalsIgnoreCase("guion")){
			return _guardaGuion(usr, idObjeto, textoEditor);
			}
		}
	private JSONObject _guardaGuion(Usuario usr, String idObjeto, String texto) throws ErrorFechaIncorrecta, ErrorConexionPerdida, JSONException, ErrorArrancandoAplicacion, ErrorGotta {
		GestorMetaDatos.guardaGuion(usr.getConexion(), usr.getApli().getEsquema(), idObjeto, texto);
		
		return new JSONObject()
			.put("tipo", "ok")
			.put("msg", "Se ha guardado el guión '"+idObjeto+"' ("+usr.getConexion().fechaServidor()+")")
			.put("fecha", new FechaGotta());
		}
	private JSONObject _guardaNivel(HttpServletRequest request, Usuario usr, String idObjeto, String textoEditor, String sqlNivel, String params, String tipoObjetoBD) throws ErrorConexionPerdida, JSONException, ErrorArrancandoAplicacion, ErrorGotta {
		String msg="Se ha guardado el nivel '"+idObjeto+"' ("+usr.getConexion().fechaServidor()+")";
		FechaGotta ff=null;
		try {
			String strFecha=Util.obtenValorOpcional(request, "f_modificacion");
			if (strFecha!=null)
				ff=new FechaGotta(strFecha);
			}
		catch (ErrorFechaIncorrecta e){
			//pass;
			}
		
		boolean esProc=Util.esProcAlmacenado(textoEditor);
		String nombreObjeto=null;
		if (Util.esNivelJython(textoEditor)){
			msg+=". Asegúrate de que el nombre de la clase que implementa la interfaz INivelDef coincide con el nombre del nivel.";			
			}
		else if (esProc){
			nombreObjeto=this.sacaNombreProcAlmacenado(textoEditor);
			if (Util.en(tipoObjetoBD, "PACKAGE", "PACKAGE BODY") ){
				String nombrePaquete=nombreObjeto;
				sqlNivel=nombrePaquete+Constantes.PUNTO+sqlNivel;
				}
			else 
				sqlNivel=nombreObjeto;
			
			if (ff!=null) {
				String fg=GestorMetaDatos.getF_Modificacion(usr.getConexion(), nombreObjeto, tipoObjetoBD).getString("f");
				FechaGotta nuevaFecha=new FechaGotta(fg);
				if (nuevaFecha!=null && !ff.equals(nuevaFecha))
					throw new ErrorFU_Tramite("¡Atención! Otro usuario ha modificado el objeto que estás intentando guardar. Si guardas el objeto se perderán los cambios que este usuario ha realizado.");
				}
			sqlNivel=this.sacaSQLnivelDelProcAlmacenado(sqlNivel, params);
			msg="Se han guardado el objeto '"+nombreObjeto+"' y el nivel '"+idObjeto+"'";
			}
		
		if (sqlNivel==null ){
			sqlNivel=textoEditor;
			}
		
		if (esProc)
			usr.getConexion().ejecuta(textoEditor);
		GestorMetaDatos.guardaNivel(usr.getConexion(), usr.getApli().getEsquema(), idObjeto, sqlNivel, params);
		if(nombreObjeto!=null)
			ff=new FechaGotta(GestorMetaDatos.getF_Modificacion(usr.getConexion(), nombreObjeto, tipoObjetoBD).getString("f"));
		usr.getApli().leerNiveles();
		
		String sql = usr.getApli().getNivelDef(idObjeto).getTexto().trim();
		if(!sql.equals(sqlNivel) && sql.indexOf(sqlNivel)>-1){
			String diff = sql.substring(0, sql.indexOf(sqlNivel));
			return new JSONObject().put("tipo", "error").put("msg", diff);
			}
		
		return new JSONObject()
			.put("tipo", "ok")
			.put("msg", msg)
			.put("fecha", ff);
		}
}

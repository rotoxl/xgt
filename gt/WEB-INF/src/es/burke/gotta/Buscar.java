package es.burke.gotta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.IJSONwritable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.burke.gotta.Constantes.TipoMensajeGotta;

public class Buscar  extends HttpServlet {
	private static final long serialVersionUID = -4934562084837616250L;
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	    //Jsp.setHeaders(response); 
		Usuario usr;
		usr = Usuario.desdeSesion(request, true);
		IJSONwritable respuesta;
	    try {
	    	String idBsp = Util.obtenValor(request, "bsp");
			String tabla =null;
			if (request.getParameter("tabla")!=null)
				tabla= Util.obtenValor(request, "tabla");
		    
	    	ArrayList<Object> param = new ArrayList<Object>();
		    int i=0;
		    while (request.getParameterMap().containsKey("valor"+i)) {
		    	String p=Util.obtenValor(request, "valor"+i);
		        param.add(p);
		        i++;
		        }
		    boolean filas=Util.obtenValorOpcional(request, "filas")!=null;
		    
		    NodoActivo na=null;
		    if ( idBsp.startsWith("control") )
		    	na=NodoActivo.desdeJSON(request, usr);	
		    
			respuesta = busca(na, usr, idBsp, tabla, param, filas);
			}
		catch (Exception e) {
			try {
				respuesta=new JSONObject()
				.put("tipo","error")
				.put("msg",e.getMessage());
			} catch (JSONException e1) {
				throw new ServletException(e1);
			}
			}	
		JSON.hazFlush(response, respuesta );
		}
	private IJSONwritable busca(NodoActivo na, Usuario usr, String idBsp, String tabla, ArrayList<Object> param, boolean filas) throws ErrorGotta, JSONException {
		int colsQueBloquea=0, colsBloqueadas=0;
	    Buscador buscador=null;
	    Motor motor=usr.getMotor(); //es nulo en navegación
	    ControlBS_ bscx=null;
	    ArrayList<String> listaNombresCasillas=new ArrayList<String>() ;
	    if ( idBsp.startsWith("control") ) {//modo detalle
	    	////////////////////////////////////////////////////////
	    	/*
	    	bscx=(ControlBS_)usr.getApli().getControl(usr, idBsp);
			buscador=bscx.getBuscador() ;
			listaNombresCasillas=bscx.generaListaNombresCasillas();
			*/
	    	ControlDef bsc = usr.getControl(na, idBsp.replace("control", Constantes.CAD_VACIA));
			if (bsc==null)
				throw new ErrorGotta("El control "+ bsc + " no está");
			
			ArrayList<String> temp=Util.split(tabla);
			String tc=temp.get(0);
			String nombreTabla=sacaNombreTabla(tc);
			
			if (bsc.tc.equals("bsp"))
				buscador= new Buscador(usr, nombreTabla, colsQueBloquea);
			else if (bsc.tc.equals("bsm"))
				buscador= new BuscadorMultiple(usr, nombreTabla, colsQueBloquea);
			else
				throw new ErrorGotta("El control "+ bsc + " no es un buscador");
			listaNombresCasillas.add(idBsp);
	    	}
	    else if (motor!=null && motor.frmDinamico !=null) {//tramitación
	    	try{
				bscx=motor.frmDinamico.getControl(idBsp).getBsc();
				buscador= bscx.getBuscador();
				colsQueBloquea=bscx.colsQueBloquea;
				colsBloqueadas=bscx.colsBloqueadas;
				
				buscador.bloqueadas=bscx.colsBloqueadas;//TODO
				
				listaNombresCasillas=bscx.generaListaNombresCasillas();
		    	}
	    	catch (NullPointerException e){
	    		/*Se tratade un formulario de los creados integramente en javascript*/
	    		buscador=null;
	    		}
			}

		if (buscador==null){ // Se trata de un formulario de los creados integramente en javascript: 
						  // 	vendrá aquí en plan "Tabla 1 0"
			String nombreTabla=sacaNombreTabla(idBsp);
			buscador=new Buscador(usr, nombreTabla, 0); // usuario, String nombre, String nombretabla, String nombrecoldes
			
			ITablaDef t = usr.getApli().getEsquema().getTablaDef(nombreTabla);
			listaNombresCasillas=new ArrayList<String>();
			
			for (String c : t.getCampoClave().getColumnas())
				listaNombresCasillas.add(c);
			}
		
		if (filas){
			List<ArrayList<String>> filasRet = ((BuscadorMultiple) buscador).buscarPorClaveMulti(param); 
		    JSONObject ret = new JSONObject();
		    ret.put("filas", filasRet);
		    return ret;
			}
		else{
			Coleccion<String> ret=null;
			try {
				// en ciertas circunstancias permitimos buscar por clave sin indicar todos los valores de la clave.
				//	Sirve para cuando quieres sacar automáticamente el primer registro en la apertura del frm
				int noNulos=Util.cuentaNoNulos(param);
				boolean vieneAlgunoVacío=param.size()!=noNulos;
				
				if (vieneAlgunoVacío){
					usr.añadeMSG("intentando buscar coincidencias parciales", TipoMensajeGotta.buscador, "Tabla "+buscador.tabla.cd);
					Filas f=buscador.buscarPorDesc(null, param);
					if (f.size()==1){
						usr.añadeMSG("1 fila encontrada! sacamos clave para buscar por ella", TipoMensajeGotta.buscador, "Tabla "+buscador.tabla.cd);
						
						//obtengo los valores de las columnas de la clave
						CampoDef clave=buscador.tabla.getCampoClave();
						ArrayList<Object>nuevosParam=new ArrayList<Object>();
						for (int i=0; i<clave.numColumnas(); i++){
							nuevosParam.add( f.get(0).get( clave.getColumna(i).getCd() ) );
							}
						ret=buscador.buscarPorClave(nuevosParam);		
						}
					}
				else
					ret = buscador.buscarPorClave(param); //[2006, 331M, Dirección y servicios generales de Cultura]
				}
			catch (ErrorCargandoTabla e){
				usr.getApli().log.warn(e);
				usr.añadeMSG(e.toString(), TipoMensajeGotta.errorSQL);
				}
		    //actualizamos las columnas NO bloqueadas con el valor de la BD (si teclearon 332m --> 332M) 
		    JSONArray anhadir = new JSONArray(); 
		    	    
		    if (ret==null) {
		    	anhadir.put(montaRetornoCasilla(idBsp, listaNombresCasillas, null, null,null) );
				}
		    else {
		    	anhadir.put(montaRetornoCasilla(idBsp, listaNombresCasillas, ret, ret.get(buscador.tabla.getColDesc()), ret.get(buscador.tabla.getColBúsq())) );
		    	
				//truqui de gotta c/s: cargamos la tabla del buscador (se suele usar luego en tramitación)
				if (motor!=null && motor.lote!=null && !(buscador instanceof BuscadorMultiple)){
					ITabla t=motor.ponTabla( buscador.tabla);
					CampoDef campoClave = t.tdef.getCampoClave(); 
                 	List<Object> clave=new ArrayList<Object>(); 
                 	for(int i=0;i<campoClave.getColumnas().size();i++)  
                 		clave.add(ret.get(i));
                 	
                 	usr.añadeMSG("Dejamos tabla cargada y disponible para tramitación", TipoMensajeGotta.buscador, "Tabla "+t.tdef.cd);
                 	t.cargarRegistros(campoClave, clave);
					motor.lote.devolverDepuradorCompleto=true;
					}
			    }

			if (motor!=null && colsQueBloquea>0)
				anhadir=creaRetorno_BuscadoresDependientes(anhadir, motor, idBsp, ret, colsQueBloquea, colsBloqueadas);
				
			return anhadir;
			}
		}
 	
	private JSONArray creaRetorno_BuscadoresDependientes(JSONArray anhadir, Motor motor, String idBsp, Coleccion<String> ret, int colsQueBloquea, int colsBloqueadas) throws JSONException{
	    boolean encontrado=false;
	    
	    for (String kk : motor.frmDinamico.controles.getOrden()) {
	    	String c=motor.frmDinamico.controles.get(kk).nombre;
	    	if (!encontrado && idBsp.equalsIgnoreCase( c ))
		    	encontrado=true;
	    	else if (encontrado) {
	    		ControlFrm ctl=motor.frmDinamico.controles.get(kk);
	    		
	    		if (ctl.cRef==null)
	    			break;
	    		else if (/*ctl.getBsc().colsBloqueadas>=colsBloqueadas &&*/ ctl.getBsc().colsBloqueadas>=colsQueBloquea) {//
	    			ArrayList<String> nombres=ctl.getBsc().generaListaNombresCasillas();	
	    			int totalColumnas=Math.min(colsQueBloquea, ctl.getBsc().colsBloqueadas);
//	    			for (int j=0; j < totalColumnas ; j++){
//	    				if (ret != null && ret.get(j) != null) {
//	    					/*dato no encontrado*/
//	    					}
//		    			}
	    			
	    			if (ret!=null){ //SI se encontró, pasamos valor a buscadores dependientes
		    			anhadir.put(montaRetornoCasilla(ctl.nombre, nombres.subList(0, totalColumnas), ret, null, null) );
						if (true) // lanzamos la búsqueda del control que hereda el valor arrastrado
							anhadir.put(new JSONObject().put("accion", "buscarPorCodigo").put("id", ctl.nombre));
	    				}
	    			}
	    		
	    		if (ctl.getBsc().colsQueBloquea==colsQueBloquea && ctl.getBsc().colsBloqueadas==colsBloqueadas)
	    			break;
	    		if (ctl.getBsc().colsQueBloquea==0)
	    			break;
	    		}
			}
	    return anhadir;
		}
 	private String sacaNombreTabla(String tc){
		ArrayList<String> tablaCampo=Util.split(tc, Constantes.PUNTO); //TODO liquidar esto dentro de un par de meses...
		return tablaCampo.get(0);
	}
	private JSONObject montaRetornoCasilla(String idBsp, List<String> listaNombresCasillas, Coleccion<String> ret, String descripcion, String código) throws JSONException {
		return new JSONObject()
			.put("accion", "rellenaDescripcion")
			.put("id", idBsp)
			.put("nombresCasillas", Util.JSON(listaNombresCasillas))
			.put("valores", ret!=null?ret.getDatosOrdenados():JSONObject.NULL)
			.put("descripcion", descripcion)
			.put("codigo",código);
		}
}

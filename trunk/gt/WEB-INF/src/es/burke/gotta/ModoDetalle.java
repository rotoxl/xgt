package es.burke.gotta;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import es.burke.gotta.Constantes.TipoMensajeGotta;

public class ModoDetalle {
	private Usuario usu;
	private ModoDetalleDef mdDef;
	
	public ModoDetalle(){
		/**/
	}
	public ModoDetalle(Usuario usr, ModoDetalleDef md){
		this.mdDef=md;
		this.usu=usr;
		}
	
	public JSONObject getDatos(NodoActivo na, HashMap<String, String> controles) throws JSONException, ErrorArrancandoAplicacion, ErrorGotta {
		if (na==null)
			return null;
		
		if (na.md!=null && na.ml!=null && na.md.getCd().equals(na.ml.getCd())){
			//pass
			}
		else {
			String msg="Rellenando ";
			if (na.md!=null)
				msg+=Constantes.COMILLA+na.md.getCd()+"' (detalle), ";
			if (na.ml!=null)
				msg+=Constantes.COMILLA+na.ml.getCd()+"' (vista), ";
			msg+="nodo '"+na.nodoActivo+"'";
			this.usu.añadeMSG(msg, TipoMensajeGotta.info);
			}
		
		JSONObject valores=new JSONObject();
		JSONObject tiposDato=new JSONObject();
		Object dato=null;
		
		if (na.nodoActivo!=null)
			na.cargaTablas();
		
		if (this.mdDef==null)
			return new JSONObject();
		
		ITabla tb =null;
		String nt=this.mdDef.getTB();
		if (nt==null || nt.equals("")){
			/*pass*/
			}
		else 
			tb=na.getTabla( nt );
		
		
		for (ControlDef d : this.mdDef.getControles().values() ) {
			String idCtl="control"+d.numControl;
			String infoControl=(controles!=null && controles.containsKey(idCtl))?controles.get(idCtl):null;
			String caption=d.getCaption(this.usu);
			if (tb!=null) {
				if ( d.tc.equals(Constantes.TXT) ) {
					dato=rellenaTXT(na, caption);
					
					valores.put(d.numControl, dato);
					}
				else if (d.tc.equals(Constantes.PIC)) {
					String tcaption=caption;
					tcaption=Util.split(tcaption, Constantes.SEPARADOR).get(0);
					tcaption=Util.split(tcaption, Constantes.DIVISIÓN).get(0);
					
					dato=rellenaTXT(na, tcaption);
					valores.put(d.numControl, dato);					
					}
				else if (Util.en(d.tc,Constantes.TAB, Constantes.PAG)) {
					// Vemos si viene info de pest visibles, pest activa...
					JSONObject pestañas = rellenaTAB(na, d.numControl, infoControl);
					if (pestañas!=null)
						valores.put(d.numControl, pestañas);
					}
				}
			
			String var=null;
			String tipo=null;
			if (Util.en(d.tc, Constantes.DESP, Constantes.PAR, Constantes.BSP, Constantes.BSM)){
				if (d.tc.equals(Constantes.DESP))
					var=new ControlDESP(d, usu).nombreVariable;
				else if (d.tc.equals(Constantes.PAR)){
					ControlPAR pp=new ControlPAR(d, usu);
					var=pp.nombreVariable ;
					tipo=pp.tipoVariable;
					}
				else if (d.tc.equals(Constantes.BSP)) 
					var=new ControlBSP(d, usu).nombreVariable ;
				else if (d.tc.equals(Constantes.BSM)) 
					var=new ControlBSP(d, usu).nombreVariable ;	
					
				Variable v=usu.getVariable(var);
				if (v != null) {
					Object va = v.getValor();
					if (va==null || va.equals(Constantes.CAD_VACIA)){
						if (tipo!=null && tipo.equals(Constantes.BOOLEAN)){
							v.setValor(0);
							va=false;
							}
						}
					valores.put(d.numControl, va);
					tiposDato.put(d.numControl, v.getTipo());
					}
				else if (tipo !=null && tipo.equals(Constantes.BOOLEAN)){
					usu.setVariable(var, new Variable(var, tipo, false));
					}
				}
			else if (Util.en(d.tc,Constantes.TAB, Constantes.PAG)) {
				// Vemos si viene info de pest visibles, pest activa...
				JSONObject pestañas = rellenaTAB(na, d.numControl, infoControl);
				if (pestañas!=null)
					valores.put(d.numControl, pestañas);
				}
			}
		JSONObject ret=new JSONObject().put("valores", valores);
		return ret;
		}
	
	private JSONObject rellenaTAB(NodoActivo na, String numControl, String infoControl) throws JSONException {
		JSONObject ret=new JSONObject();
		
		String caption="pest_"+numControl;
		String visibles="*";
		try{
			try{
				CampoDef campo = Util.valorSimbolo(na, caption, na.tb, null);
				if (campo != null) {
					ArrayList<Object> valores = Util.dameValorCampo(na, campo);
					visibles= Util.noNulo( valores.get( valores.size()-1 ));
					}
				}
			catch (ErrorCampoNoExiste e) {
				//pass
				}
			ret.put("visibles", visibles);
			ret.put("pestActiva", infoControl);
			}
		catch (Exception e) {
			ret.put("error", "Error: "+caption+ ":"+e.getMessage());
			}
		return ret;
	}
	public Object rellenaTXT(NodoActivo na, String caption)  {
		if (na==null || na.tb==null) return null;
		
		Object valor=null;
		try  {
			if (caption.contains(Constantes.SEPARADOR))//lleva modif
				caption=caption.substring(0, caption.indexOf(Constantes.SEPARADOR));
			CampoDef campo = Util.valorSimbolo(na, caption, na.tb, null);
			if (campo != null) {
				ArrayList<Object> tempvalores = Util.dameValorCampo(na, campo);
				valor=tempvalores.get( tempvalores.size()-1 );
				}
			} 
		catch (Exception e)
			{valor= "Error: "+caption + ":"+e.getMessage();}
		
		return valor;
	}
}

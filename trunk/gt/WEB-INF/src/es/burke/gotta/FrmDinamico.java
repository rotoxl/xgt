package es.burke.gotta;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class BloqueCodigo  {
	public String nombre, condicion;
	public BloqueCodigo(String nombre, String cond){
		if (nombre.startsWith(Constantes.COMILLA) && nombre.endsWith(Constantes.COMILLA))
			nombre=nombre.substring(1, nombre.length()-1);
		this.nombre=nombre;
		this.condicion=cond;
		}
	public JSONObject JSON() throws JSONException{
		return new JSONObject()
			.put("nombre", this.nombre)
			.put("cond", this.condicion);
			}
	}

public class FrmDinamico {
	private final int BTN_ACEPCAN = 1;
	
	public String titulo;
	public int botones;
	public Usuario usuario;
	public String detalle=null;
	public String numControl=null;
	public String etiquetasSalto=null;

	public Coleccion<ControlFrm> controles=new Coleccion<ControlFrm>();
	public String url;
	
	public Coleccion<BloqueCodigo> bloquesCodigo=new Coleccion<BloqueCodigo>();
	private BloqueCodigo bloqueCodigo=null;
	
	public ControlFrm getControl(String idx){
		for (ControlFrm ctl:this.controles.values()){
			if (ctl.nombre.equalsIgnoreCase(idx))
				return ctl;
			}
		return null;
		}
	public FrmDinamico(Usuario usuario) {
		this.usuario=usuario;
		this.botones = BTN_ACEPCAN;
		}
	public FrmDinamico(Usuario usuario, String detalle, String url, String numControl, String titulo, String etiquetasSalto) {
		this.usuario=usuario;
		this.url=url;
		this.titulo=titulo;
		this.detalle=detalle;
		this.numControl=numControl;
		this.botones = BTN_ACEPCAN;
		this.etiquetasSalto=etiquetasSalto;
		}
	public void anhadeControl(ControlFrm ctl) {
		ctl.bloqueCodigo=(this.bloqueCodigo!=null?this.bloqueCodigo.nombre:null);
		this.controles.put(ctl.nombre+"__"+this.controles.size(), ctl);
		}
	public void anhadeBloque(String nombre, String cond){
		if (nombre==null){
			this.bloqueCodigo=null;
			return;
			}
		BloqueCodigo bc=new BloqueCodigo(nombre, cond);
		this.bloquesCodigo.put(nombre, bc);
		this.bloqueCodigo=bc;
		}
	public void JSON(JSONObject jsret) throws ErrorFechaIncorrecta, JSONException{
		Tramite t= this.usuario.getMotor().lote.tramActivo();
		jsret.put("numTramites", t.lote.tramites.size()-t.lote.numTramite-1)
		.put("camino", t.camino)
		.put("tramite", t.tramite)
		.put("acceptCharset", Constantes.UTF8)
		.put("etiquetasSalto", this.etiquetasSalto) // etiquetaSalto1\literal1 | etiquetaSalto2\literal2...
		
		.put("tipo", "frm")
		.put("urlDestino", this.url)
		.put("numcontrol", this.numControl)
		.put("detalle", this.detalle)
		.put("titulo", this.titulo)
		.put("refrescarAlCancelar", this.usuario.getMotor().hayQueRefrescar);
		
		JSONArray c = new JSONArray();
		for (String ctl : this.controles.getOrden())
			c.put( this.controles.get(ctl).JSON() ); 
		jsret.put("controles", c);
		
		JSONArray b = new JSONArray();
		for (String nb: this.bloquesCodigo.getOrden())
			b.put(this.bloquesCodigo.get(nb).JSON());
		jsret.put("bloques", b);
		}
	public void anadirDatoMD(Usuario usu, String nombre, String tipo, int longitud, String infoBloqueo, ArrayList<Object> valorInicial, String etiqueta, boolean bloqueado, boolean opcional, Object dondeLoGuardo, ITablaDef tRef, CampoDef cRef, ColumnaDef colDes, String validacion, JSONObject listaValores) {
		if (etiqueta==null)
			etiqueta=Constantes.CAD_VACIA;
		if (etiqueta.contains("&")) 
			Util.replaceTodos(etiqueta, "&", Constantes.CAD_VACIA);
		
		ControlFrm ctl = new ControlFrm (usu, nombre, tipo, longitud, infoBloqueo, 
								valorInicial, etiqueta, bloqueado, opcional,
								dondeLoGuardo, tRef, cRef, colDes, validacion, listaValores);
		anhadeControl(ctl);					 
	    }
	public void anadirConsejoMD(Usuario usu, String texto, String tipoConsejo) {
		ControlFrm ctl = new ControlFrm (usu, texto, tipoConsejo);
		anhadeControl(ctl);					 
	    }
	
	@SuppressWarnings("unchecked")
	public void okMD(Coleccion<String> retornoFrm) throws ErrorArrancandoAplicacion, ErrorGotta{
		if (this == null)
			return;
		usuario.getApli().println("<<<<<<Resultado FRM");
		Tramite tramActivo = usuario.getMotor().tramActivo();
		tramActivo.getFirma().añadeTítulo(this.titulo);
		
		for (int j=controles.getDatosOrdenados().size()-1;j>=0;j--) {
			ControlFrm ctl=controles.get(j);
			
			if (ctl.tRef != null) {//buscadores
				ArrayList<Object> tvalor=new ArrayList<Object>();
				ArrayList<String> nombresCasillas=ctl.getBsc().generaListaNombresCasillas();
				for ( int i=0; i<nombresCasillas.size();i++  ) {
					String tipo=ctl.getBsc().tablaDef.getCampoClave().getColumna(i).getTipo();
					Object s=this.getNoVacio(retornoFrm, nombresCasillas.get(i)  );
					
					//desformateamos
					if ( Util.esTipoNumerico(tipo) && s instanceof String) 
						s=Util.desformatearNumero(s.toString());
					
//					if (i<ctl.colsBloqueadas)
//						s=ctl.valorInicial.get(i);
					tvalor.add(s);
					}
				if (!ctl.bloqueado){
					ctl.setValor(tvalor);
					}
				ctl.getBsc().valorClave=tvalor;
				
				}
		}
		Boolean hayFirma=tramActivo.getFirma().getFirmaElectrónicaActiva();
		
		for (ControlFrm ctl:controles.getDatosOrdenados()) {
			ArrayList<Object> tvalor=new ArrayList<Object>();
			
			if (ctl.tRef != null) {//buscadores
				ControlBS_ bsp=ctl.getBsc();
				ArrayList<Object> valorClave=(ArrayList<Object>)bsp.valorClave.clone();
				String desc=bsp.getDescripcion();
				
				Buscador buscador=bsp.getBuscador();
//				if (!buscador.esDeColumnaClave()){
					boolean hayNulos=false;
					
					for (int i=0; i<valorClave.size(); i++){
						if (valorClave.get(i)==null){
							hayNulos=true;
							break;
							}
						}
					if (hayNulos){
					 	if (buscador.tabla!=null){ 
					 	 	ITabla t=this.usuario.getMotor().tablas.get(buscador.tabla.getCd());
					 	 	if (t!=null) t.limpiarRegistros(); 
					 	 	}
						}
					else {
						valorClave.add(desc);
						desc=buscador.buscarPorClave(bsp.valorClave).get(buscador.getNombrecoldes());
						}
//					}
					
				if (hayFirma) tramActivo.getFirma().añadeDato(ctl.etiqueta, valorClave, desc);
				tvalor=ctl.getBsc().valorClave;
				}
			else if (ctl.tipo==Constantes.FILE){
				if (hayFirma) tramActivo.getFirma().añadeEtiquetaFirmaArchivos(ctl.etiqueta);
				}
			else if (ctl.dondeLoGuardo == null) { //separadores 
				if (hayFirma) tramActivo.getFirma().añadeSeparador(ctl.etiqueta);
				}
			else if (Util.en(ctl.tipo,Constantes.BOOLEAN, Constantes.OPTION)) {
				Boolean valor;
				if(ctl.bloqueado)
					valor=(Boolean) ctl.valorInicial.get(0);
				else{
					String v=retornoFrm.get(ctl.nombre);
					if (v.equalsIgnoreCase("false"))
						valor=false;
					else
						valor=true;
					
					tvalor.add( valor );
					}
					if (!ctl.bloqueado) ctl.setValor( tvalor );
					if (hayFirma) tramActivo.getFirma().añadeDato(ctl.etiqueta, valor?"Sí":"No" );
					}
			else if(ctl.tipo.equals(Constantes.FILE)){//fileupload
				/*pass*/}
			else {//campos de texto o de opción
				Object s;
				
				if(ctl.bloqueado)
					s=ctl.valorInicial.get(0);
				else{
					s=this.getNoVacio(retornoFrm, ctl.nombre);

					//desformateamos
					if ( Util.esTipoNumerico(ctl.tipo) && s instanceof String) 
						s=Util.desformatearNumero(s.toString());
					}
				tvalor.add(s);
				if (!ctl.bloqueado) ctl.setValor(tvalor);
				if (hayFirma) tramActivo.getFirma().añadeDato(ctl.etiqueta, s);
				}
			if (!ctl.bloqueado) usuario.getApli().println(tvalor+" --> "+ctl.nombre+" ("+ctl.tipo+")");
			}
		usuario.getApli().println("<<<<<<<<<<<<<<<<<<<<<<<<<");
		}
	
	Object getNoVacio(Coleccion<String> col, String clave) {
		Object ret = col.get(clave); 
		if (ret == null || ret.equals(Constantes.CAD_VACIA))
			return null;
		return ret;
		}
}

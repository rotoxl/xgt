package es.burke.gotta;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*los controles del formulario dinámico*/
public class ControlFrm {
	Usuario usuario;
	public String nombre;
	String etiqueta, tipo; 
	int colsQueBloquea=0, colsBloqueadas=0;
	
	int longitud;
	ArrayList<Object> valorInicial;
	boolean bloqueado, opcional;
	ITablaDef tRef;
	CampoDef cRef;
	ColumnaDef colDes;
	Object dondeLoGuardo;
	String validacion;
	public String textoAyuda=null;
	public String globoAyuda=null;
	public String columnaNivel=null; //para vincularla a un nivel y ejecutar el trámite dentro de la propia lista
	
	private ControlBS_ bsc=null;
	private JSONObject listaValores=null;
	
	private String tipoConsejo=null; //bombilla, info, warning... cualquier cosa ya que luego se define en CSS
	public boolean esSubirFicherosMultiple=false;
	public String bloqueCodigo; //para definir bloques que aparecerán y desapareceran en función de eventos
	
	public ControlBS_ getBsc(){
		if (bsc==null) {
			bsc =new ControlBS_();
			bsc.inicializa(this.nombre, null, this.usuario, this.dondeLoGuardo, this.tRef, 
						   this.valorInicial, this.bloqueado, this.colsBloqueadas, this.colsQueBloquea, this.opcional);
			}
		return bsc;
	}
	public ControlFrm (Usuario usuario, String nombre, String tipo, int longitud, String infoBloqueo, 
						ArrayList<Object> valorInicial, 
						String etiqueta, boolean bloqueado, boolean opcional,
						Object dondeLoGuardo, 
						ITablaDef tRef, CampoDef cRef, ColumnaDef colDes, String validacion,
						JSONObject listaValores) {
		this.usuario=usuario;
		this.etiqueta=etiqueta;
		this.tipo=tipo;
		this.nombre=nombre;
		
		if (this.nombre==null)
			this.nombre=generaNombre();
		
		this.colDes=colDes;
		
		if (infoBloqueo!=null && !infoBloqueo.equals("")) {
			ArrayList<String>temp=Util.split(infoBloqueo);
			this.colsBloqueadas=Integer.parseInt(temp.get(0));
			this.colsQueBloquea=Integer.parseInt(temp.get(1));
			}
			
		this.longitud=longitud;
		this.valorInicial=valorInicial;
		this.bloqueado=bloqueado;
		this.opcional=opcional;
		
		this.tRef=tRef;
		this.cRef=cRef;
		this.dondeLoGuardo=dondeLoGuardo;
		if (validacion==null || validacion.equals(Constantes.CAD_VACIA)){
			if (Util.en(tipo,Constantes.tiposEnteros))
				this.validacion="entero";
			else if(Util.en(tipo,Constantes.tiposNumericos))
				this.validacion="numero";
			else
				this.validacion=Constantes.CAD_VACIA;	
			}
		else
			this.validacion=validacion;
		this.listaValores=listaValores;
		
		if (this.tipo!=null && this.tipo.equals(Constantes.BOOLEAN)){
			if (this.valorInicial==null)
				this.valorInicial=Util.creaLista(false);
			else {
				Object v=this.valorInicial.get(0);
				if (v==null || v.equals(Constantes.CAD_VACIA))
					this.valorInicial=Util.creaLista(false);
				}
			}
		}
	
	public ControlFrm (Usuario usuario, String ds, String tipoConsejo){
		this.usuario=usuario;
		this.etiqueta=ds;
		this.nombre=generaNombre();
		
		this.tipo=Constantes.CONSEJO;
		this.tipoConsejo=tipoConsejo;
	}
	public JSONObject JSON() throws ErrorFechaIncorrecta, JSONException {
		if (tRef==null)	{
			Object valor=null;
			if (this.valorInicial != null && this.valorInicial.size()>0)
				valor=this.valorInicial.get(0);
			return JSONcasilla(this.nombre,this.tipo, this.longitud, valor, this.etiqueta, this.bloqueado, this.opcional, 
					this.validacion, this.listaValores, this.textoAyuda, this.globoAyuda, this.tipoConsejo,
					this.esSubirFicherosMultiple, this.columnaNivel, this.bloqueCodigo);			
			}
		JSONObject ret=new JSONObject();
	
		ret.put("bloqueCodigo", bloqueCodigo)
			.put("tipo", "bsc")
			.put("id", this.nombre)
			.put("obligatorio", !this.opcional)
			.put("ds", this.etiqueta)
			
			.put("dsExtendida",	textoAyuda)
			.put("globoAyuda",	globoAyuda)
			.put("columnaNivel",	this.columnaNivel)
			
			.put("colsQueBloquea", this.colsQueBloquea)
			.put("colsBloqueadas", this.colsBloqueadas)
			
			.put("bloqueado", this.bloqueado);
		
		JSONArray retControles = new JSONArray();
		ArrayList<String>listaNombresCasillas=this.getBsc().generaListaNombresCasillas();
		for (int i=0; i<this.cRef.getColumnas().size(); i++) {
			try {
				ColumnaDef col;
				col = this.cRef.getColumna(i);
				retControles.put(JSONcasilla( listaNombresCasillas.get(i), col.getTipo(),
							col.getLongitud(), this.valorInicial.get(i), "", false, false, this.validacion, this.listaValores, null, null, null, false, null, null));
				}
			catch (ErrorCampoNoExiste e)
				{/*pass*/}
			}
		ret.put("controles",retControles);
		return ret;
	}
	private static String generaNombre(){
		return "sinnombre"+new Double(Math.random()*10000);
	}
	private static JSONObject JSONcasilla(String nombre, String tipo, int longitud, Object valorInicial, String etiqueta, boolean bloqueado, boolean opcional, String validacion, 
			JSONObject listaValores, String textoAyuda, String globoAyuda, String tipoConsejo,
			boolean esSubirFicherosMultiple,
			String columnaNivel,
			String bloqueCodigo) throws ErrorFechaIncorrecta, JSONException{
		//{'tipo':'lvwFecha', 'nombre':'fecha', 'maxlength':50, 'valor':'13/03/1975', 'obligatorio':false , 'ds':'Fecha de alta'},
		JSONObject ret=new JSONObject();
		
		String tipoJSON=Util.tipoJSON(tipo);
		
		ret.put("tipo", 		tipoJSON)
			.put("bloqueCodigo", bloqueCodigo)
			.put("id", 			nombre )	
			.put("dsExtendida",	textoAyuda)	
			.put("globoAyuda",	globoAyuda);
		
		if ( valorInicial!=null && tipo!=null &&
			(  valorInicial instanceof BigDecimal || valorInicial instanceof Double ) &&
			(  tipo.equals(Constantes.CURRENCY)   || tipo.equals(Constantes.DOUBLE) )
			) {
			ret.put("maxlength", 	longitud+ Math.floor(longitud/3) );
			String xvalor=""; 
			int numDec= tipo.equals(Constantes.CURRENCY)? 2 : -1;
			
			xvalor=Util.formatearNumero( valorInicial.toString(), numDec);
			ret.put("valor", ""+xvalor) ;
			}
		else if (tipoJSON.equals("lvwFile")){
			ret.put("multiple", esSubirFicherosMultiple);
			}
		else if (tipoJSON.equals("lvwNumero")) {
			ret.put("maxlength", 	longitud+ Math.floor(longitud/3) );	
			if (valorInicial!=null) {
				//	los mandamos como cadena por aquello de que muestre 10.0
				String tempValor=Util.noNulo(ITabla.validaTipoDato(valorInicial, tipo));
				ret.put("valor", Util.formatearNumero(tempValor,-2)) ;
				}
			else
				ret.put("valor", valorInicial )	;
			}
		
		else if (tipoJSON.equals("lvwOption")) {
			if (! (valorInicial instanceof Boolean))
				valorInicial=false;
			ret.put("maxlength", 	longitud 	)	;
			ret.put("valor", 		valorInicial)	;
			}
		else if (tipoJSON.equals("lvwLista")) {
			ret.put( "lista", listaValores)	;
			ret.put("valor", 		valorInicial)	;
			}
		else if (tipoJSON.equals("lvwConsejo")){
			ret.put("tipoConsejo", tipoConsejo);
			}
		else if (tipoJSON.equals("lvwBoolean")){
			ret.put("valor", Boolean.valueOf(valorInicial.toString()));
			}
		else {
			ret.put("maxlength", 	longitud )	;
			ret.put("valor", valorInicial )	;
			}
		ret.put("obligatorio",  !opcional);
		ret.put("columnaNivel", columnaNivel);
		ret.put("bloqueado",  	bloqueado);
		ret.put("validacion", (validacion!=null && validacion.equals("")?null:validacion) ) ;
		ret.put("ds", 			etiqueta) ;
		
		return ret;
	}
	
	/*	actualiza el campo o variable al que este dato apunta con los valores
	 	* 	ya validados que se han tecleado en el formulario automático*/
	@SuppressWarnings("unchecked")
	public void setValor(Object valor) throws ErrorCampoNoExiste, ErrorTablaNoExiste, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorCreandoTabla{
		if (this.dondeLoGuardo == null)
			return;
		Motor motor= this.usuario.getMotor();
		
		if (this.dondeLoGuardo instanceof Variable) {
			Variable va = (Variable) this.dondeLoGuardo;
			Object xvalor=valor;
			if (xvalor instanceof ArrayList<?>)
				xvalor=((ArrayList<Object>)xvalor).get(0);
			
			if (this.tipo!=null && this.tipo.equals(Constantes.OPTION))
				va.setTipo(Constantes.BOOLEAN);			
			motor.lote.setVariable(va.getNombre(), va.getTipo(), valor);
			}
		else if (this.dondeLoGuardo instanceof CampoDef) {
			for (int i=0; i<this.colsBloqueadas; i++){
				ArrayList<Object>temp=(ArrayList<Object>) valor;
				temp.set(i, this.valorInicial.get(i)) ;
				}
			
			CampoDef ca = (CampoDef) this.dondeLoGuardo;
			String tabla = ca.getTabla();	

			ITablaDef tabdef = this.usuario.getApli().getEsquema().getTablaDef(tabla);
			ITabla taux = motor.ponTabla(tabdef);
			ArrayList<Object> tValor=(ArrayList<Object>) valor;
			if (!taux.getValorCam(ca.getCd()).equals(tValor)){
				if (this.bsc!=null) {
					/*
					//si no vienen absolutamente todas rellenas no hay que guardar el dato:
					// MSP No hay manera de borrar datos con esto, lo quito
					for (int i=0; i<tValor.size(); i++){
						if (tValor.get(i)==null)
							return;
						}
					*/
					int j=this.bsc.colsBloqueadas;
					int i=0;
					for(String nombreColumna:ca) {
						j--;
						if (j<=0)
							taux.setValorCol(nombreColumna, tValor.get(i));
						i++;
						}
					}
				else
					taux.setValorCam(ca.getCd(), tValor);
				}
		}
	}
}


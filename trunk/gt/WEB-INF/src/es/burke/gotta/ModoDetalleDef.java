package es.burke.gotta;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ModoDetalleDef {
	private String cd; //nombre modo detalle.
	private String letras; //contiene las letras de la jerarquía de detalles.
	private String tb; //nombre de la tabla base de ese modo detalle
	
	public Coleccion<ControlDef> controles=null;
	
	private String aplicacion;
	public Aplicacion getApli() {
		try 
			{return PoolAplicaciones.sacar(aplicacion);} 
		catch (ErrorArrancandoAplicacion e) 
			{return null;/*pass*/}
	}
	
	public ModoDetalleDef(String aplicacion, String cd){
		this.aplicacion=aplicacion;
		this.cd=cd;
	}
	public ModoDetalleDef(String aplicacion, String cd, String letras, String tb){
		this.aplicacion=aplicacion;
		this.cd=cd;
		this.letras=letras;
		
		if (tb!=null && tb.equals(Constantes.CAD_VACIA))
			tb=null;
		this.tb=tb;
		}
	public String getCd()
		{return this.cd;}
	public String getLetras()
		{return this.letras;}
	
	public String getTB()
		{return this.tb;}
	public ControlDef getControl(String numControl) throws ErrorGotta{
		ControlDef ret = this.getControles().get(numControl);
		return ret;
		}
	
	public Coleccion<ControlDef> getControles() throws ErrorGotta{
		if (controles==null) {
			leeControles();
			leeTraduccionesControles();
			}
		return controles;
		}
	
	private void completaEstructura(Filas filas, String nombre, String tipo){
		if (!filas.containsKey(nombre))
			filas.addColumna(nombre, tipo);
		}
	public void leeControles()  {
		Conexion con;
		Filas filas=null;
		try {
			con=new Conexion(this.getApli());
			filas= GestorMetaDatos.leeEXP_Controles(con, this.cd, this.getApli().getEsquema()); 
			}
		catch (ErrorGotta e){
			throw new ErrorArrancandoAplicacion("Error obteniendo controles del detalle '"+this.cd+"'"+e.getMessage());
			}
		
		this.controles=new Coleccion<ControlDef>();
		
		//¿hay que completar la estructura?
		completaEstructura(filas, "tipoDato", Constantes.STRING); 
		completaEstructura(filas, "subtipoDato", Constantes.STRING);
		completaEstructura(filas, "longitud", Constantes.INTEGER);
		for (String cd_idioma:this.getApli().idiomas.claves){
			completaEstructura(filas, "caption_"+cd_idioma, Constantes.STRING);	
			}
		
		for (Fila fila : filas) {
			String tc= fila.gets("tipoControl");
			
			if (tc.equals(Constantes.TXT) && tb!=null) {// txt dentro de rd sin tb
				try {
					String caption=fila.gets("caption");
					if (caption.contains(Constantes.SEPARADOR))
						caption=Util.split(caption, Constantes.SEPARADOR).get(0);
					
					CampoDef campo = Util.valorSimbolo(this.getApli(), caption, tb);
					ColumnaDef columna = campo.getColumna(campo.getColumnas().size()-1);

					fila.put("tipoDato", Util.tipoJSON(columna.getTipo()));
					fila.put("subtipoDato", columna.getSubtipo());
					} 
				catch (ErrorDiccionario e) {
					// ya cantará por otro lado
					}
				}
			else if (tc.equals(Constantes.BSP) || tc.equals(Constantes.BSM)){
				try {
					String caption=fila.gets("caption"); //dic_tablas @filtro
					ArrayList<String>temp=Util.split(caption, " ");
					ITablaDef t=this.getApli().getEsquema().getTablaDef(temp.get(0));
					ColumnaDef columna=t.getCampoClave().getColumna(0);
					
					fila.put("tipoDato", Util.tipoJSON(columna.getTipo()));
					fila.put("subtipoDato", columna.getSubtipo());
					fila.put("longitud", columna.getLongitud());
					} 
				catch (ErrorDiccionario e) {
					// ya cantará por otro lado
					}
				}
			
			String n=fila.gets("numControl");
			ControlDef nc = new ControlDef(fila, aplicacion);
			
			this.controles.put(n, nc);
			this.getApli().controles.put(n, nc);				
			}
		}
	
	public JSONObject JSON(Usuario usr) throws JSONException, ErrorGotta{
		String err=null;
		ITablaDef t;
		try {
			t=usr.getApli().getEsquema().getTablaDef(this.tb);
			}
		catch (ErrorGotta e) {
			t=null;
			}
		
		if (t!=null && t.getCampoClave().numColumnas() != this.letras.length()){
			err="El detalle '"+this.getCd()+"' tiene "+(t.getCampoClave().numColumnas()>this.letras.length()?"menos":"más")+
				" letras que la clave de su tabla base '"+this.tb+"', por lo el nodo activo puede estar equivocado.";
			}
		JSONObject ta=new JSONObject()
			.put("letras", this.letras)
			.put("tb", this.tb)
			.put("cd", this.getCd())
			.put("errores", err);
		
		return JSON(ta, usr); 	
		}
	
	JSONObject cache=null;
	public JSONObject JSON(JSONObject textoAdicional, Usuario usr) throws JSONException, ErrorGotta {
		getControles();
		if (cache==null){
			JSONArray ctl=new JSONArray();
			
			for (ControlDef xctl:this.controles.getDatosOrdenados())
				ctl.put( xctl.JSON(usr) );
			
			JSONObject ret= textoAdicional
				.put("columnas", ControlDef.JSONColumnas(usr.getApli()))
				.put("filas", ctl);
	
			cache=ret;	
			}
		return cache;
		}
	private void leeTraduccionesControles() throws ErrorGotta{
		Esquema esquema=this.getApli().getEsquema();
		if (!esquema.existeTabla("LEN_ControlesModoDetalle"))
			return;
		Conexion con = new Conexion(this.getApli());
		Filas filas = GestorMetaDatos.leeLEN_Controles(con, this.cd, esquema);	
		for (Fila fila:filas){ 				
			ControlDef ctl=this.getControl( fila.gets("numControl") );
			if (ctl!=null)
				ctl.anhadeLenguaje(fila.gets("CD_Idioma"), fila.gets("caption"));
			}
		}
}

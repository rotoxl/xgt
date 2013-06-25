package es.burke.gotta;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import es.burke.gotta.Constantes.TipoMensajeGotta;

public class Camino {
	public Long cd;
	String ds;
	Boolean tt=false;
	
	private Coleccion<String> dsTramites;
	private ArrayList<String> colFIRMA;
	
	private StringBuffer jer;
	
	private JSONObject retorno=null;//aquí lo guardamos todo montadito, listo para ser devuelto en 0.0001
	public boolean calculado=false;
	public int numTramites;
	
	private Coleccion< Coleccion< ArrayList<String> > > colDLL; //tramite1     ->  {ManteTablas, Jasper, Dot} 
	Coleccion<Coleccion<ArrayList<String>>> colSUB;
	private ArrayList<String> tramitesImplementados;
	
	Usuario usr;
	
	public Camino(Usuario usu, Long camino) {
		ConstructorDeVerdad(usu, camino);
		}
	public Camino(Usuario usu, int camino) {
		ConstructorDeVerdad(usu, new Long(camino));
		}
	public Camino(Long camino, String ds) {
		this.cd=camino;
		this.ds=ds;
		}
	public void ConstructorDeVerdad(Usuario usu, Long camino) {
		this.usr=usu;
		this.cd= new Long(camino);
		}
	public void montaMapa() throws ErrorGotta{
		this.usr.añadeMSG("Generando mapa de tramitación para camino "+cd, TipoMensajeGotta.info);
		
		try {
			if (this.ds==null)
				this.ds=GestorMetaDatos.getDS_Camino(usr.getConexion(), usr.getApli().getEsquema(), cd);
			this.jer=montaJerarquia();
			actualizaDS_Tramites();
			this.colDLL=this.tramitesQueLlamanDLL();
			this.colSUB=this.tramitesSUB();
			this.colFIRMA=this.sacaTramitesConFirma();
			this.tramitesImplementados=this.calculaTramitesImplementados();
			
			this.calculado=true;
			} 
		catch (SQLException e) {
			throw new ErrorGotta(e.getMessage());
			}
		}
	private ArrayList<String> calculaTramitesImplementados() throws SQLException, ErrorTablaNoExiste, ErrorConexionPerdida, ErrorArrancandoAplicacion {
		Filas filas=GestorMetaDatos.leeListaTrámitesImplementados(usr.getConexion(), usr.getApli().getEsquema(), this.cd);
		
		ArrayList<String> ret = new ArrayList<String>();
		for (Fila fila:filas){
			ret.add( fila.gets(0));
			}
		return ret;
	}
	public void actualizaDS_Tramites() throws ErrorGotta {
//		try {
			this.dsTramites=descripcionTramites();
			this.calculado=false;
//			} 
//		catch (SQLException e) {
//			throw new ErrorGotta(e.getMessage());
//			}
		}

	private Coleccion<Coleccion<ArrayList<String>>> tramitesQueLlamanDLL() throws ErrorDiccionario, ErrorConexionPerdida {		
		Filas filas=GestorMetaDatos.mapaTram_tramitesQueLlamanDLL(usr.getConexion(), usr.getApli().getEsquema(), cd);
		Coleccion<Coleccion<ArrayList<String>>> ret = new Coleccion<Coleccion<ArrayList<String>>>();
		  
		for (Fila f : filas) {
			String cdtram=f.gets("CD_Tramite"), dll=f.gets(2);
			String plantilla=quitaComillas(f.gets(3)) ;
				
			if (!ret.containsKey(cdtram))
				ret.put(cdtram, new Coleccion<ArrayList<String>>());
				
			Coleccion<ArrayList<String>> anterior=ret.get(cdtram);
			if (! anterior.contains(dll) )
				anterior.put(dll, new ArrayList<String>());
				
			ArrayList<String> listaPar1=anterior.get(dll);
			listaPar1.add(plantilla);
			}
		return ret;
		}
	private Coleccion<Coleccion<ArrayList<String>>> tramitesSUB() throws SQLException, ErrorTablaNoExiste, ErrorConexionPerdida, ErrorArrancandoAplicacion{
		Coleccion<Coleccion<ArrayList<String>>> ret= new Coleccion<Coleccion<ArrayList<String>>>();
		Filas filas=GestorMetaDatos.mapaTram_tramitesSUB(usr.getConexion(), usr.getApli().getEsquema(), cd);
		
		for (Fila f : filas) {
			String cdtram=f.gets("CD_Tramite").toLowerCase(), 
				sub=f.gets("nombreTramite").toLowerCase(), 
				cd_accion=f.gets("CD_Accion");
			
			if (! ret.containsKey(cdtram) )
				ret.put(cdtram,new Coleccion<ArrayList<String>>());
				
			Coleccion<ArrayList<String>> tipos=ret.get(cdtram);
			if (!tipos.containsKey(cd_accion))
				tipos.put(cd_accion, new ArrayList<String>());
			ArrayList<String> tipo=tipos.get(cd_accion);
			tipo.add(sub);
			}
		return ret;
		}
	private String quitaComillas(String t) {
		String ret=t;
		if ( ret.startsWith("'") && ret.endsWith("'"))
			ret=ret.substring(1, ret.length()-1);
		return ret;
	}
	private StringBuffer montaJerarquia() throws ErrorDiccionario, ErrorConexionPerdida, ErrorArrancandoAplicacion{
		Filas filas=GestorMetaDatos.mapaTram_sacaJerarquia(usr.getConexion(), usr.getApli().getEsquema(), cd);
		
		Coleccion<String> jerarquia=new Coleccion<String>(false);
		for (Fila f : filas) {
			String tram=f.gets("CD_Tramite");
			String acc=f.gets("CD_Accion");
			String tramPen=f.gets("Parametro1");
			
			if (!jerarquia.containsKey(tram))
				jerarquia.put(tram, tram);
			
			if ( acc.equals(Constantes.PEN) )
				jerarquia.put(tram, jerarquia.get(tram) + "/" + tramPen);
			}
		
		StringBuffer ret=new StringBuffer();
		for ( String k : jerarquia.values() )
			ret.append(k + Constantes.PIPE);
		return ret;
		}
	private Coleccion<String> descripcionTramites() throws ErrorDiccionario, ErrorConexionPerdida, ErrorArrancandoAplicacion {
		Filas filas=GestorMetaDatos.mapaTram_sacaDescripcionTramites(usr.getConexion(), usr.getApli().getEsquema(), cd);
		
        Coleccion<String> descripciones = new Coleccion<String>();
        
        for (Fila f : filas) {
        	String cdt=f.gets("CD_Tramite");
        	String dst=f.gets("DS_Tramite");
        	descripciones.put(cdt, dst);
        	}
        
        return descripciones;
		}
	private ArrayList<String> sacaTramitesConFirma(){
		ArrayList<String> ret=new ArrayList<String>();
		
		Aplicacion apli=usr.getApli();
		if (!apli.niveles.containsKey("APP_Tramites"))
			return null;
		INivel nivel=apli.niveles.get("APP_Tramites").obtenerNivel();
		
		try {
			for (String cdtram: this.dsTramites.claves){
				Filas filas=nivel.lookUp(usr.getConexion(), Util.creaLista( usr.getLogin(), this.cd, cdtram));
				if (filas.size()==1){
					String jsFirma=Util.toString(filas.get(0).get("JavascriptFirma"));
					if (jsFirma!=null && !jsFirma.equals(Constantes.CAD_VACIA))
						ret.add(cdtram);
					}
				}
			}
		catch (SQLException e){
			//pass
			}
		catch (ErrorGotta e) {
			//pass
			}
		return ret;
		}
	///////////////////
	public Long getCD(){return this.cd;}
	public String getDS(){return this.ds;}
	public int getNumTramites(){return this.numTramites;}
	public Boolean getTt(){return this.tt;}
	///////////////////
	public JSONObject json() throws JSONException{
		JSONObject ret= new JSONObject();
		 ret.put("cd", this.cd)
			.put("ds", this.ds)
			.put("num_tramites", this.numTramites)
			.put("tt", this.tt);
		return ret;
	}
	public JSONObject jsonMapa() throws JSONException, ErrorGotta{
		if (retorno==null){
			if (!this.calculado)
				this.montaMapa();
			
			if (colFIRMA==null) colFIRMA=new ArrayList<String>();
			this.retorno= this.json()
				.put("ds_tramites", Util.JSON(dsTramites))
				.put("dll", Util.JSON(colDLL))
				.put("sub", Util.JSON(colSUB))
				.put("tramImplementados", this.tramitesImplementados)
				.put("jer", jer.toString())
				.put("firmaTramites", Util.JSON(colFIRMA));
			}
		return this.retorno;
	}
	public void hayQueCalcular(){
		this.retorno=null;
		this.calculado=false;
		}
	public JSONObject jsonMapa(Usuario usu) throws JSONException, ErrorGotta{
		this.usr=usu;
		return jsonMapa();
	}
}

package es.burke.gotta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

public class Nodo implements Serializable {
	private static final long serialVersionUID = 8621606044890791907L;
	
	public String cd, ds, md, mds=null;
	public transient Fila fila;
	
	public boolean expandido=false;
	public transient Nodos rama; //la familia a la que pertenece, son todos hermanos
	public transient Nodos hijos;
	
	public Boolean esError=false;
		
	public INivelDef getNivelSiguiente() throws ErrorDiccionario{
		if (mds!=null)
			return rama.arbol.usuario.getApli().getNivelDef(mds);
		return null;
		}
	public Nodo(Nodos rama, Fila fila, String cdPadre) throws ErrorArrancandoAplicacion{
		this.fila=fila;
		this.rama=rama;
		
		_Nodo(cdPadre);
		if (this.cd!=null)
			rama.put(this.cd, this);
		}
	public Nodo(Nodos rama, String cd, String ds, String mds){//para los nodos "NodoPadre", etc
		this.rama=rama;
		this.cd=cd;
		this.ds=ds;
		this.mds=mds;
		
		if (rama!=null && this.cd!=null)
			rama.put(this.cd, this);
		}
	private void _Nodo(String cdPadre){
		String variableMD="";
		if (fila.get(Constantes.md) !=null) {
			String xmd = fila.gets(Constantes.md);
			
			if (xmd.startsWith("=")){ // =ModoDetalle\a1\b2\c3
				int posBarra=xmd.indexOf(Constantes.SEPARADOR, 0);
				
				if (posBarra>-1){
					variableMD=xmd.substring(posBarra+1);
					xmd=xmd.substring(1, posBarra);
					}
				else {
					variableMD=Constantes.CAD_VACIA;
					xmd=xmd.substring(1);
					}
				}
			else //Si no trae '=', el mododetalle es la primera letra
				variableMD=xmd;
			
			this.md=xmd;
			}
		this.mds=fila.gets(Constantes.mds);
		if (this.mds.equals(Constantes.CAD_VACIA))
			this.mds=null;
		
		this.cd=fila.gets(Constantes.cd);
		String claveNodo = variableMD+fila.gets(Constantes.cd)+fila.gets(Constantes.cdplus);			
		
		if (esControlARB())
			claveNodo+=Constantes.SEPARADOR+this.rama.cdInicio+Constantes.SEPARADOR+cd.substring(cd.indexOf(Constantes.SEPARADOR)+1);
		else if (this.cd.equals(Constantes.CAD_VACIA) || this.esNodoRaiz()) {
			/*pass*/
			}
		else if (this.rama.profundidad>0)
			claveNodo+=Constantes.SEPARADOR+cdPadre;
		
		this.cd=claveNodo;
		}
	///////////////
	public boolean esControlARB(){
		return (cd!=null && cd.startsWith("arb-")) || this.rama.cdInicio.startsWith("=arb-");
		}
	public boolean esNodoRaiz() {
		return this.rama==null;
		}
	///////////////////////////////	
	public void borrarHijos(){
		hijos=null;
		expandido=false;
		}
	public void recargaHijos(Usuario usuario, HashMap<String, Variable> lista) throws ErrorGotta {
		getHijos(usuario, lista, 0);
		for (Nodo n : this.hijos.values()){
			if (!n.expandido)
				n.hijos=null;
			else if (n.hijos!=null && n.hijos.size()>0)
				n.recargaHijos(usuario, lista);			
			}
		}
	public Coleccion<Nodo> getHijos(Usuario usuario, int cuantos)  throws ErrorGotta {
		return getHijos(usuario, new HashMap<String, Variable>(), cuantos);
		}
	public Coleccion<Nodo> getHijos(Usuario usuario, HashMap<String, Variable> lista, int cuantos) throws ErrorGotta{
		if (this.rama==null) //¿sesión recuperada?
			this.rama=new Nodos(usuario.arbol, -1);
		if (hijos==null)
			this.hijos=new Nodos(this.rama.arbol, this.rama.profundidad+1);
		
		//obtener los hijos
		Nodos nuevosHijos=new Nodos(this.rama.arbol, this.rama.profundidad+1, getNivelSiguiente(), this.cd);
		
		if (getNivelSiguiente()==null){
			generaNodoError(nuevosHijos, "ERROR: Nivel "+getNivelSiguiente().getCd()+" no existe");
			}
		else {
			expandido=true;
			/*Cargamos, como mínimo, tantos como había*/
			if (hijos==null){
				//pass
				}
			else if (cuantos==0 && hijos.size()<50){
				/*pass*/
				}
			else if (hijos.size() > cuantos && cuantos!=-1)
				cuantos=hijos.size();
			
			INivel nivel=getNivelSiguiente().obtenerNivel();
			nivel.usuario=usuario;
			
			HashMap<String,String> leVan = Util.nodoAHash(this);
			List<Object> valores=nivel.getValores(lista, leVan);
			Conexion con = usuario.getConexion(usuario.maxElementos);
			
			Filas xfilas;
			try {
				xfilas=nivel.lookUpBase(con, valores, cuantos);
				nuevosHijos.procesa(xfilas);
				}
			catch (Exception e){
				e.printStackTrace();				
				Nodo nodo = generaNodoError(nuevosHijos, "ERROR cargando el nivel "+this.mds+". "+Util.msgError(e));
				nodo.rama.profundidad=this.rama.profundidad+1;
				}
			
			copiaDatos(nuevosHijos);
			}
		usuario.añadeNodos(this.hijos, usuario.arbol.ml);
		
		this.hijos.nodoPadre=this;
		return this.hijos;
		}
	private void copiaDatos(Nodos nuevosHijos) {
		hijos.maxElementosAlcanzado=nuevosHijos.maxElementosAlcanzado;
		hijos.ordenColumnas=nuevosHijos.ordenColumnas;
		hijos.tiposColumnas=nuevosHijos.tiposColumnas;
		hijos.mapaColumnas=nuevosHijos.mapaColumnas;
		hijos.filas=nuevosHijos.filas;
		hijos.nivel=nuevosHijos.nivel;
		hijos.cdInicio=nuevosHijos.cdInicio;
		
		//quitamos los eliminados
		for (int i=hijos.size()-1; i>=0; i--){
			Nodo n=hijos.get(i);
			if ( !nuevosHijos.containsKey(n.cd) )
				hijos.remove(n.cd);					
			}
		for (Nodo n : nuevosHijos.values()){
			if ( !hijos.containsKey( n.cd ) )
				hijos.put(n.cd, n);
			else {
				Nodo nn=hijos.get(n.cd);
				if (n.expandido)
					nn.expandido=true;
				nn.hijos=n.hijos;
				nn.fila=n.fila;
				}
			}
		hijos.reOrdenarClaves( nuevosHijos.getOrden() );
		}
///////////////////////////////	
	public static Nodo generaNodoError(Nodos rama, String ds) {
		Nodo ret=new Nodo(rama, "ErrorSQL", ds, null);
		ret.esError=true;
		return ret;
		}
///////////////////////////////	
	public boolean hayQueAbrirlo(String nodoArranque) {
		// 	cdNodo= /a2003/
		// nodoBuscado = /a2003/b18.20.30/
		String cdNodo=this.cd;
		
		if (nodoArranque==null)
			return false;
		if(cdNodo.equalsIgnoreCase(nodoArranque))
			return false;
		cdNodo=Util.replace(cdNodo, Constantes.SEPARADOR, Constantes.CAD_VACIA, 0);
		ArrayList<String> temp=Util.split(nodoArranque, Constantes.SEPARADOR);
		//1º trozos sueltos
		for (String trozo : temp) {
			if (cdNodo.equalsIgnoreCase(trozo)) 
				return true;
			}
		//2º sumando trozos al derecho
		String cadena=Constantes.CAD_VACIA;
		for (int i=0; i<temp.size(); i++) {
			cadena+=temp.get(i);
			if (cdNodo.equalsIgnoreCase(cadena)) 
				return true;
			}
		//2º sumando trozos al derecho
		String cadenaR=Constantes.CAD_VACIA;
		for (int i=temp.size(); i>0; i--) {
			cadenaR=temp.get(i-1)+cadenaR;
			if (cdNodo.equalsIgnoreCase(cadenaR)) 
				return true;
			}
		return false;
		}
	
	public JSONArray JSON(Boolean conFormato, List<String> listaColumnas) throws ErrorDiccionario, ErrorArrancandoAplicacion{
		JSONArray ret = new JSONArray();		
		ret	//.put(cd) //expandido, ordenColumnas
			.put(this.getNivelSiguiente()==null?"X":this.expandido);
		for (String col:listaColumnas){
			if (col.equalsIgnoreCase(Constantes.md))
				ret.put(this.md);
			else if (col.equalsIgnoreCase(Constantes.cd))
				ret.put(this.cd);
			else
				ret.put( this.fila.get(col) );
			}
		return ret;
		}
//	public JSONObject jsonNodoMaxElementos(int maxElementos) throws JSONException{
//		//{"cd" : "maxElementos","ds" : Constantes.CAD_VACIA, "profundidad" : "10","padre" : "arbolEXP\\a2000"}
//		JSONObject ret = new JSONObject();
//
//		ret.put("cd", "maxElementos")
//			.put("cuantos", this.hijos.size()+maxElementos )
//			.put("maxElementos", maxElementos )
//			.put("padre", this.cd)
//			.put("esUltimo", true);
//		return ret;
//		}	
	@Override
	public String toString(){
		return this.cd;}	
	
	public String generaURL(Usuario usr) {
		//http://localhost:8080/gotta?nodo=b18.01.321M.130-01(07)\a2017&aplicacion=Embla&vista=MAP&detalle=MAPEjercicio
		String url=usr.getApli().getUrlAplicacion()+"?";
		return url+generaURLInterna(usr);
		}
	public String generaURLInterna(Usuario usr) {
		String url = sumaTrozo("nodo", this.cd, false);
		url+=sumaTrozo("aplicacion", usr.getApli().getCd(), true);
		url+=sumaTrozo("vista", usr.arbol.ml, true);
		url+=sumaTrozo("detalle", this.fila.gets(Constantes.md), true);
		return url;
	}
	private static String sumaTrozo(String clave, String valor, boolean conSuma){
		String ret= Constantes.CAD_VACIA;
		if (conSuma)
			ret+="&"; 
		ret+=clave+"="+valor;
		return ret;
	}
////////////////////	
	public static Nodo generaNodoPadre(Arboljson arbol, ModoListaDef ml) {
		String clave=generaClaveNodoParaVista(ml.getCd());
		
		Nodos rama=new Nodos(arbol, -1);
		return new Nodo(rama, clave, null, ml.getNivelInicial()._cd);
		}
	public static Nodo generaNodoPadreParaARB(Arboljson arbol, INivelDef nivel, String nodoActivo) {
		String clave=generaClaveNodoParaARB(nivel.getCd(), nodoActivo);
		
		Nodos rama=new Nodos(arbol, -1);
		return new Nodo(rama, clave, null, nivel._cd);
		}
	//////////////
	public static String generaClaveNodoParaVista(String ml){
		return "=arbol-"+ml;
		}
	public static String generaClaveNodoParaARB(String nivel, String nodoActivo) {
		return "=arb-"+nivel+"\\"+nodoActivo;
		}
	
}
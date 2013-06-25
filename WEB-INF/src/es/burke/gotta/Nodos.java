package es.burke.gotta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Nodos extends Coleccion<Nodo> implements Serializable {
	private static final long serialVersionUID = -1877532274830416175L;
	public transient Arboljson arbol;
	
	public String nivel;
	
	public String cdInicio; //para arb y subgrid: coincide con el cd del nodo del árbol principal 
	public Nodo nodoPadre=null;	//
	
	transient Filas filas;
	transient protected  List<String> ordenColumnas;
	transient protected List<String> tiposColumnas;
	transient HashMap<String,Integer> mapaColumnas=new HashMap<String, Integer>();
	
	int profundidad;
	
	public boolean maxElementosAlcanzado=false;
	
	public Nodos(Arboljson arbol, int profundidad){
		this.arbol=arbol;
		this.profundidad=profundidad;
		}
	public Nodos(Arboljson arbol, int profundidad, INivelDef nivel, String cdInicio){
		this.arbol=arbol;
		this.profundidad=profundidad;
		this.nivel=nivel.getCd();
		
		if (this.profundidad>-1)
			this.cdInicio=cdInicio;
		}
	public Nodos(Arboljson arbol, int profundidad, Filas filas) throws ErrorGotta{
		this.arbol=arbol;
		this.procesa(filas);
		}
	public void procesa(Filas filas) throws ErrorGotta {
		this.filas=filas;
		
		for (Fila fila : filas){
			Nodo nodo=new Nodo(this, fila, this.cdInicio);
			
			if (fila.containsKey(Constantes.expandir)){
				if (Util.en(fila.gets(Constantes.expandir), "1")) {
					//si ya existía, miramos su estado
					if (this.containsKey(nodo.cd) && !this.get(nodo.cd).expandido){
						if (this.get(nodo.cd).mds!=null)
							nodo.getHijos(this.arbol.usuario, filas.size());
						}
					else if (!this.containsKey(nodo.cd) )
						nodo.getHijos(this.arbol.usuario, filas.size());
					}
				else {
					//si ya existía, miramos su estado
					if (this.containsKey(nodo.cd) && this.get(nodo.cd).expandido){
						this.get(nodo.cd).expandido=false;
						nodo.hijos=null;
						}
					}
				}
			if (fila.containsKey(Constantes.activar) && fila.gets(Constantes.activar).equals("1")) 
				this.arbol.nodoActivo=nodo.cd;	
			
			// recuperar el nodo activo
			if (this.arbol.nodoDestino!=null){
				if (this.arbol.nodoDestino.nodoActivo!=null && this.arbol.nodoDestino.nodoActivo.equalsIgnoreCase(nodo.cd)){ 
					this.arbol.nodoDestino.nodoActivo=nodo.cd; //apañamos las mayúsculas y minúsculas
					} 
			 	else if (nodo.hayQueAbrirlo(this.arbol.nodoDestino.nodoActivo))
					nodo.getHijos(this.arbol.usuario, nodo.hijos!=null?nodo.hijos.size():-1);
				}
			}
		this.maxElementosAlcanzado=filas.maxElementosAlcanzado;
		this.ordenColumnas=filas.orden;
		this.mapaColumnas=filas.mapa;
		this.tiposColumnas=filas.tipos;
		}
	public JSONObject JSON(int maxElementos) throws JSONException, ErrorDiccionario, ErrorArrancandoAplicacion{
		return JSON(maxElementos, 0, this.size());
		}
	public JSONObject JSON(int maxElementos, int desde, int hasta) throws JSONException,  ErrorDiccionario, ErrorArrancandoAplicacion{
		JSONObject lista = new JSONObject();
		if (hasta>this.size() || hasta==-1)
			hasta=this.size();
		
//		for (int i=desde; i<hasta; i++){
//			Nodo hijo=this.hijos.get(i);
//			lista.put( hijo.JSON(this.cd) );
//			}
		lista=this._JSON(true, false);
		
		return lista;
		}	
	public JSONObject _JSON(boolean añadirInfoTipos, boolean conFormato) throws JSONException, ErrorDiccionario, ErrorArrancandoAplicacion{
		JSONArray arr= new JSONArray();
		
		Usuario u=arbol.usuario;
		ArrayList<String> cabecerasTraducidas=u.getApli().getNivelDef(this.nodoPadre.mds).cabecera(u.getIdioma());
		
		if (this.size()==1 && this.get(0).esError){
			Nodo nodo=this.get(0);
			
			ArrayList<Object>lista=null;
			if (nodo.esError)
				lista=Util.creaLista("esError", Constantes.cd, Constantes.md, Constantes.ds, Constantes.expandido);
			
			JSONArray n=Util.JSON( Util.creaLista(true, null, this.nivel, nodo.ds, "X") );
			JSONObject ret=new JSONObject()
				.put("filas", arr.put(n))
				.put("columnas", Util.JSON( lista ));
			return ret;	
			}
		
		List<String>xlistaColumnas=this.ordenColumnas;
		List<String>xtiposColumnas=tiposColumnas;
		
		if (this.filas==null){
			xlistaColumnas=new ArrayList<String>();
			xtiposColumnas=new ArrayList<String>();
			}
		
		for (Nodo f : this.getDatosOrdenados())
			arr.put(f.JSON(conFormato, xlistaColumnas)); //expandido, ordenColumnas
		
		xlistaColumnas=cabecerasTraducidas!=null ? cabecerasTraducidas: this.ordenColumnas;
		xlistaColumnas.addAll(0, Util.creaListaString("_expandido"));
		xtiposColumnas.addAll(0, Util.creaListaString(Constantes.STRING));
		
		JSONObject ret=new JSONObject();
		ret.put("filas",arr);
		if(añadirInfoTipos && this.filas!=null){
			ret.put("maxElementosAlcanzado",this.maxElementosAlcanzado);
			ret.put("tipos", Util.JSON(Util.tiposJSON(Filas.arrayFiltrado(this.filas, xtiposColumnas, xlistaColumnas))) );
			}
		ret.put("columnas",Util.JSON(xlistaColumnas)) ;
		
		if (this.ordenColumnas!=null)
			ret.put("cabeceras",Util.JSON(ordenColumnas) );
		return ret;
		}
////////////////////////
	public static Nodos generaRamaRaiz(Arboljson arbol, String mds) {
		Nodos rama=new Nodos(arbol, -1);
		rama.nivel=mds;
		
		return rama;
		}
}

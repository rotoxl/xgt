package es.burke.gotta;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Arboljson implements Serializable{
	private static final long serialVersionUID = -2783930385874665515L;
	Usuario usuario;
	
	int maxElementos=0;
	String nodoActivo;
	transient public NodoActivo nodoDestino;
	
	public String ml;
	public Nodos ramaInicial;
	
	public Nodo getNodoActivo(){
		return getNodo(nodoActivo);
		}
	public Nodo getNodo(String nodo){
		if (	ml!=null && this.usuario.nodos.containsKey(ml) && 
				this.usuario.nodos.get(ml).containsKey(nodo)  )
			
			return this.usuario.nodos.get(ml).get(nodo);
		return null;
		}
	public Arboljson(Usuario usuario, INivelDef nivel) {
		this.usuario=usuario;
		
		this.ramaInicial=Nodos.generaRamaRaiz(this, nivel.getCd());
		
		maxElementos=this.usuario.maxElementos;
		}
/////////////////
	public JSONObject cargar(ModoListaDef modoListaDef, Nodo nodoRaiz) throws JSONException, ErrorGotta{
		//if (!ml.getCd().equalsIgnoreCase("SelectorColumnas"))
		//	this.usuario.setModoLista(ml.getCd());
		return cargar(modoListaDef.getNivelInicial(), nodoRaiz, true);
		}
	public JSONObject cargar(INivelDef n, Nodo nodoRaiz) throws JSONException, ErrorGotta{
		return cargar(n, nodoRaiz, true);
		}
	public JSONObject cargar(INivelDef nivelDef, Nodo nodoRaiz, boolean navegarAlNodo) throws JSONException, ErrorGotta{
		JSONObject rama=_crearRama(nivelDef, nodoRaiz);//ojo, esto antes xq en parteComun se usa el nodoActivo
		apañaNodoActivo(nodoRaiz);
		JSONObject ret = parteComunJSON(navegarAlNodo, nodoRaiz!=null?nodoRaiz.cd:null, nivelDef.getCd());
		ret.put("datos",rama);
		return ret;	
		}
/////////////////
	private void apañaNodoActivo(Nodo nodoRaiz) {
		if (this!=this.usuario.arbol)
			return;
		Nodo nodoInicialML=this.usuario.nodos.get(ml).get("arbol"+ml);
		if (nodoRaiz==null)
			nodoRaiz=nodoInicialML;
		
		if (nodoInicialML==null)
			nodoInicialML=this.usuario.nodos.get(ml).get(0);
		
		Boolean seleccionarElPrimero=false;
		if (this.nodoDestino!=null && this.nodoDestino.nodoActivo!=null) {
			if (buscaNodoActivo(nodoInicialML, this.nodoDestino.nodoActivo) == null){
				//no existe el nodo que estamos buscando, cambiamos el activo al primero, por ejemplo
				seleccionarElPrimero=true;
				}
			else
				this.nodoActivo=this.nodoDestino.nodoActivo;
			}
		//quito esto: sólo estaba buscando en el primer nivel y fallaba cuando
//		else if (buscaNodoActivo(nodoInicialML, this.nodoActivo) == null)
//			seleccionarElPrimero=true;
		else if (this.nodoActivo==null)
			seleccionarElPrimero=true;
		
		if (seleccionarElPrimero){
			if (nodoInicialML.hijos==null || nodoInicialML.hijos.size()==0)
				this.nodoActivo=nodoInicialML.cd;
			else if ( nodoRaiz == null || nodoRaiz.cd.equals(nodoInicialML.cd) )
				this.nodoActivo=nodoInicialML.hijos.get(0).cd;
			else
				this.nodoActivo=nodoRaiz.cd;
			}
		}
	private Nodo buscaNodoActivo(Nodo nodoPadre, String id) {
		if (nodoPadre==null)
			return null;
		if (!nodoPadre.expandido)
			return null;
		if (nodoPadre.hijos==null)
			return null;
		
		for ( Nodo nodo : nodoPadre.hijos.values() ) {
			if ( nodo.cd.equals(id) )
				return nodo;
			else if (nodo.expandido && nodo.hijos!=null) {
				if (nodo.hijos.size()==0 && id.equals(nodo.cd+"\\sinElementos"))
					return nodo;
				Nodo sbuf=buscaNodoActivo(nodo, id);
				if (sbuf!=null)
					return sbuf;
				}
			}
	
		return null;
		}
	public JSONObject usarMaxElementos(String cdNodo, int cuantos) throws JSONException, ErrorGotta {
		//elimina el maxElementos y devuelve el NUEVO TRAMO (con su correspondiente maxElementos) 
		Nodo n = usuario.nodos.get(this.ml).get(cdNodo);
		try {			
			int tamAnterior=n.hijos!=null?n.hijos.size():this.maxElementos;
			n.getHijos(usuario, cuantos);
			JSONObject listaNuevos = n.hijos.JSON(this.maxElementos, tamAnterior, cuantos);		
			JSONObject ret = parteComunJSON(true, cdNodo, null);
			ret.put("datos",listaNuevos);
			return ret; 
			}
		catch (ErrorTablaNoExiste e) 
			{throw new ErrorRellenandoControl(e.getMessage());}
		catch (ErrorSQLoParametrosIncorrectos e)
			{throw new ErrorRellenandoControl(e.getMessage());}
		}
	
	public JSONObject expandirContraerNodo(String cdNodo, boolean expandir) throws JSONException, ErrorGotta {
		return expandirContraerNodo(cdNodo, expandir, 0);
	}
	public JSONObject expandirContraerNodo(String cdNodo, boolean expandir, int cuantos) throws JSONException, ErrorGotta {
		Nodo n = usuario.nodos.get(this.ml).get(cdNodo);
		if (n==null && usuario.disenho)
			n=usuario.nodos.get("SelectorColumnas").get(cdNodo);
		if(n==null)
			throw new ErrorRellenandoControl("nodo nulo");
		
		String cdML=this.ml;
		try {
			if (cdNodo.equals("arbol") ) 
				return cargar(usuario.getModoListaDef(cdML), n);//_crearRama(nivel, 1, n, cuantos);
				
			n.expandido=expandir;
			if (expandir){
				n.getHijos(usuario, cuantos);
				JSONObject listaNuevos = n.hijos.JSON(this.maxElementos);
				JSONObject ret = n.rama.arbol.parteComunJSON(!expandir, cdNodo, n.hijos.nivel);
				ret.put("datos",listaNuevos);
				return ret;
				}
			n.hijos.JSON(maxElementos);
			return new JSONObject();//no utilizaremos el resultado
			}
		catch (ErrorTablaNoExiste e) 
			{throw new ErrorRellenandoControl(e.getMessage());}
		catch (ErrorSQLoParametrosIncorrectos e) 
			{throw new ErrorRellenandoControl(e.getMessage());} 
	}
	private JSONObject parteComunJSON(boolean navegarAlNodo, String nodoPadre, String nombreNivel) throws ErrorRellenandoControl, JSONException{
		try {
			JSONObject ret = new JSONObject()
				.put("nodoPadre", nodoPadre)
				.put("navegarAlNodo", navegarAlNodo )
				.put("nodoActivo", this.nodoActivo)
				.put("nivel", nombreNivel)
				.put("maxElementos", this.usuario.maxElementos);
			
			return ret;
			}
		catch (ErrorArrancandoAplicacion e) {	
			throw new ErrorRellenandoControl(e.getMessage());}
			}
	private JSONObject _crearRama(INivelDef nivelDef, Nodo pNodoPadre) throws JSONException, ErrorGotta {
		try {
			Nodo nodoPadre=pNodoPadre;
				
			//obtener nodos a pintar
			if (nodoPadre==null) {
				String clave;
				INivelDef nivelInicial=null;
				if (ml!=null)
					nivelInicial=this.usuario.getModoListaDef(ml).getNivelInicial();
				
				if (nivelDef.equals(nivelInicial)) {
					clave=Nodo.generaClaveNodoParaVista(ml);
					if (!usuario.nodos.containsKey(ml) || !usuario.nodos.get(ml).containsKey(clave)) {
						nodoPadre = Nodo.generaNodoPadre(this, this.usuario.getModoListaDef(ml));
						usuario.añadeNodos(nodoPadre, ml);
						}
					}
				else {
					nodoPadre = Nodo.generaNodoPadreParaARB(this, nivelDef, this.nodoActivo);
					usuario.añadeNodos(nodoPadre, ml);
					clave=Nodo.generaClaveNodoParaARB(nivelDef.getCd(), this.nodoActivo);
					}
				
				nodoPadre=usuario.nodos.get(ml).get(clave);
				usuario.nodoRaiz=nodoPadre;
				}
			nodoPadre.recargaHijos(usuario, null);//nodoPadre.getHijos(usuario, cuantos);
			//pintando nodos
			return nodoPadre.hijos.JSON(maxElementos);
			}
		catch ( ErrorTablaNoExiste e)  {
			throw new ErrorRellenandoControl(e.getMessage());	}
		catch (ErrorSQLoParametrosIncorrectos e) {
			throw new ErrorRellenandoControl(e.getMessage());	}
		}
}

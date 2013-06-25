package es.burke.gotta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

/* Almacena conjuntos de valores por orden de inserción */
public class Coleccion<T2> extends HashMap<String,T2> implements Iterable<String>{
	private static final long serialVersionUID = -4667919429439217322L;
	public ArrayList<String> claves;
	public boolean sensibleMaysMinus=false;
	public Coleccion(boolean sensibleMaysMinus){
		super();
		this.sensibleMaysMinus=sensibleMaysMinus;
		this.claves = new ArrayList<String>(); //claves ordenadas
	}
	public Coleccion()
	{
		super();
		this.claves = new ArrayList<String>(); //claves ordenadas
	}
	private String minClave(String v) {
		if (this.sensibleMaysMinus){
			return v;
		}
		return v.toLowerCase();
		
	}
/** Introduce clave y valor en una hashtable y la clave en un vector */
	@Override
	public T2 put (String clave, T2 o)
	{	String m=minClave(clave);
		if (!claves.contains( m ))
			claves.add( m );
		return super.put( m , o);
	}

	public T2 get(String clave)
	{
		if (clave!=null)
			return super.get(minClave(clave));
		return null;
	}

	public T2 get(int indx) {
		if (indx<0)
			{indx=claves.size()+indx;}
		String clave = claves.get(indx);
		return super.get(minClave(clave));
		}

	public void remove(String clave)
	{
		claves.remove(minClave(clave));
		super.remove(minClave(clave));
	}

	public Iterator<String> iterator() {
		return claves.iterator(); //claves ordenadas
	}		
	
	public Iterator<T2> elements(){		
		Iterator<String> eOrden=claves.iterator();
		ArrayList<T2> vOrdenado=new ArrayList<T2>();
		String clave = null;
		T2 o;
		while (eOrden.hasNext()) {
			clave=eOrden.next();
			o=super.get(minClave(clave));
			vOrdenado.add(o);
			}
		return vOrdenado.iterator();
		}

	@Override
	public int size()
		{return claves.size();}
	public boolean contains(Object obj)
		{return  (super.containsValue(obj));}
	public boolean containsKey(String clave){
		if (clave==null) return false;
		return super.containsKey(minClave(clave));	
		}
	public ArrayList<String> getOrden() {
		return this.claves;
		}

	@Override
	public String toString()
	{
		return claves.toString();
	}

	public int indexOf(String clave)
		{return claves.indexOf(minClave(clave));	}
	
	public ArrayList<T2> getDatosOrdenados() {
		ArrayList<T2> ret = new ArrayList<T2>();
		Iterator<T2> e = elements();
		T2 o = null;
		while (e.hasNext()) {
			o = e.next();
			ret.add(o);
		 	}
		return ret;
		}
	public void reverse()
		{
		ArrayList<String>  vreverse= new ArrayList<String> ();
		for (String k:claves)
			{vreverse.add(0, k);}
		claves=vreverse;
		}
	
	public JSONObject JSON() throws JSONException{
		JSONObject ret= new JSONObject();
		
		for (String k : this.claves){
			T2 obj=this.get(k);
			ret.put(k, new JSONObject(obj) ); 
			}
		return ret;
	}
	public void reOrdenarClaves(ArrayList<String> nuevasClaves){
		for (int i=0; i<nuevasClaves.size(); i++){
			String clave = nuevasClaves.get(i).toLowerCase();
			if (claves.contains(minClave(clave))){
				claves.remove(minClave(clave));
				if (i<claves.size())
					claves.add(i,minClave(clave));
				else 
					claves.add(minClave(clave));
				}
			}
	}
}

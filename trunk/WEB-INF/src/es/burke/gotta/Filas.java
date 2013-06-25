package es.burke.gotta;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Filas extends ArrayList<Fila>{
	private static final long serialVersionUID = -1526393573782489836L;
	protected List<String> orden;
	protected List<String> tipos;
	HashMap<String,Integer> mapa=new HashMap<String, Integer>();
	public boolean maxElementosAlcanzado=false;  
	protected List<Integer>filasTraducidas=new ArrayList<Integer>();
	
	Filas(List<String> orden, List<String> tipos, ArrayList<Object[]> datos) {
		this.Filas_(orden, tipos, datos);
	}
	@SuppressWarnings("unchecked")
	public Filas(List<String> orden, List<String> tipos, Iterable<?> datos) throws SQLException{
		/* Se usa desde los niveles Jython */
		List<Object[]> ret;
		Iterator<?> it = datos.iterator(); 
		if(it.hasNext()){
			Object ob=it.next();
			if(ob instanceof Object[])
				ret=(List<Object[]>) datos;
			else if (ob instanceof Iterable<?>){
				ret = new ArrayList<Object[]>();
				it = datos.iterator();
				while(it.hasNext()){
					List<?> f = (List<?>) it.next();
					ret.add(f.toArray());
					}
				}
			else
				throw new SQLException("No se ha podido inicializar filas con elemento " + ob.toString());
			}
		else
			ret = new ArrayList<Object[]>();
		this.Filas_(orden, tipos, ret);
		}
	public Filas(List<String> orden, List<String> tipos){
		this.Filas_(orden, tipos,new ArrayList<Object[]>());
		}
	public Filas(){}
	
	private void Filas_(List<String> nombres, List<String> xtipos, Iterable<Object[]> ret) {
		this.orden=new  ArrayList<String>();
		for (String s:nombres)
			this.orden.add(s.intern());
		this.tipos=xtipos;
		int i = 0;
		for (String s:orden)
			mapa.put(s.toLowerCase().intern(), i++);
		for (Object[] x:ret)
			this.add(new Fila(this,x));
	}
	public List<String> getOrden() 
		{return orden;}
	public List<String> getTipos() 
		{return tipos;}
	
	public Integer nombreAPosicion(String s){
		return mapa.get(s.toLowerCase());
	}
	public void cambiaNombreColumna(String nombreAnterior, String nombreNuevo) {
		nombreAnterior=nombreAnterior.toLowerCase();
		
		int pos=mapa.get(nombreAnterior);
		mapa.remove(nombreAnterior);
		mapa.put(nombreNuevo.toLowerCase(), pos);
		orden.set(pos, nombreNuevo);
		}
	public boolean containsKey(String coldes) 
		{return nombreAPosicion(coldes)!=null;}
	@Override
	public String toString(){
		String ret = "";
		for (int i=0; i<this.tipos.size();i++){
			ret+=this.orden.get(i)+"("+this.tipos.get(i)+"), ";
		}
		return "Filas "+ret+"\n"+super.toString();
	}
	
	public JSONObject JSON(ArrayList<String> columnas, JSONObject textoAdicional, ArrayList<String> cabeceras) throws JSONException
		{return JSON(true, true, textoAdicional, columnas, cabeceras);	}
	public JSONObject JSON() throws JSONException
		{return JSON(true, false);	}
	public JSONObject JSON(boolean añadirInfoTipos) throws JSONException
		{return JSON(añadirInfoTipos, true);}
	
	public JSONObject JSON(boolean añadirInfoTipos, boolean conFormato) throws JSONException
		{return JSON(añadirInfoTipos,conFormato, new JSONObject(), null,null);}
	
	public Filas filtrar(String columna, ArrayList<Integer> valoresVálidos){
		if (valoresVálidos==null)
			return this;
		
		Filas ret=new Filas(this.orden, this.tipos);
		for (Fila fila: this) {
			Integer valorActual= new Integer((fila.get(columna)+""));
			if (valoresVálidos.contains(valorActual) )
				ret.add(fila);
			}
		return ret;
		}
	public Filas filtrar(String columna, String valor){
		Filas ret=new Filas(this.orden, this.tipos);
		for (Fila fila: this) {
			if (valor.equalsIgnoreCase(fila.gets(columna)) )
				ret.add(fila);
			}
		return ret;
		}
	public JSONObject JSON(boolean añadirInfoTipos, boolean conFormato, JSONObject textoAdicional) throws JSONException{
		return JSON(añadirInfoTipos , conFormato, textoAdicional, null,null);
	}
	public ArrayList<HashMap<String, Object>> toList(){
		ArrayList<HashMap<String, Object>> ret = new ArrayList<HashMap<String, Object>> ();
		for (Fila f : this){
			HashMap<String, Object> dict = new HashMap<String, Object>();
			for(String col:getOrden()){
				dict.put(col.toLowerCase(), f.get(col));
			}
			ret.add(dict);
		}
		return ret;
	}
	
	public JSONObject JSON(boolean añadirInfoTipos, boolean conFormato, JSONObject textoAdicional, List<String> listaColumnas, ArrayList<String> cabeceras) throws JSONException{
		JSONArray arr= new JSONArray();
		
		if (listaColumnas==null)
			listaColumnas=this.getOrden();
		
		for (Fila f : this)
			arr.put(f.JSON(conFormato, listaColumnas));
		
		JSONObject ret=textoAdicional;
		ret.put("filas",arr);
		if(añadirInfoTipos){
			ret.put("maxElementosAlcanzado",this.maxElementosAlcanzado);
			ret.put("tipos",Util.JSON(Util.tiposJSON(Filas.arrayFiltrado(this, tipos, listaColumnas))) );
			}
		ret.put("columnas",Util.JSON(Filas.arrayFiltrado(this, orden, listaColumnas))) ;
		if (cabeceras!=null)
			ret.put("cabeceras",Util.JSON(cabeceras) );
		return ret;
		}
	
	public static ArrayList<String> arrayFiltrado(Filas filas, List<String> tipos2, List<String> listaColumnas){
		ArrayList<String> ret= new ArrayList<String>();
		for (int i=0; i<listaColumnas.size(); i++){
			String nombreColumna=listaColumnas.get(i) ;
			int posColumna=0;
			if (filas.nombreAPosicion( nombreColumna )!=null)
				posColumna=filas.nombreAPosicion( nombreColumna );
			ret.add( tipos2.get(posColumna) );
			}
		return ret;
		}

	public void addColumna(String nombre, String tipo){
		mapa.put(nombre.toLowerCase(), mapa.size());
		tipos.add(tipo.toLowerCase());
		orden.add(nombre);
		
		for (Fila f: this){
			f.put(nombre, null, false);
			}
		}
	public Filas aplicaFormato() {
		String sepFormato="\\"; //$NON-NLS-1$
		Coleccion<String> modif = new Coleccion<String>(true);

		String modificador;
		for (String col : this.getOrden()) {
			
			if(col.toLowerCase().endsWith("_euro")) { //_euro es equivalente \m en castellana
				Integer integer = mapa.get(col.toLowerCase());
				mapa.remove(col.toLowerCase());
				col=col.substring(0,col.length()-5)+sepFormato+"m";
				mapa.put(col.toLowerCase(), integer);
				}
			
			int ind=col.indexOf(sepFormato) ;
			if ( ind>-1){
				modificador=col.substring(ind+1).toLowerCase();
				if (Util.isNumeric(modificador) || modificador.equals("m") || modificador.equals("%")) 
					modif.put(col.substring(0, ind), modificador);
				}
			}

		
		if (modif.size()>0) {
			boolean hayQueFormatear;
			for (Fila fila : this) {
				for (String nombreCol :  modif.claves) {
					int numDecimales=0;
					boolean euro=false;
					String simbolo=null;
					String valor=modif.get(nombreCol);
					
					String nombreColB=nombreCol+Constantes.SEPARADOR+valor;
					
					hayQueFormatear=false;
					if ( valor.equals("m") ) {
						euro=false;
						numDecimales=2;
						hayQueFormatear=true;
						}
					else if (valor.equals("%")) {//es un porcentaje
						simbolo="%";
						numDecimales=2;
						hayQueFormatear=true;
						}
					else if (Util.isNumeric(valor)){
						numDecimales=Integer.parseInt( valor );
						hayQueFormatear=true;
						}
					
					if (hayQueFormatear){
						Integer index = this.nombreAPosicion(nombreColB);
						this.tipos.set(index, "java.math.BigDecimal");
						fila.put( nombreColB , formatea( fila.get(nombreColB), numDecimales, euro, simbolo ) );
						}
					}
				}
			//cambiamos el nombre a las columnas
			for (String nombreCol :  modif.claves) {
				String valor=modif.get(nombreCol);
				String nombreColB=nombreCol+Constantes.SEPARADOR+valor;
				this.cambiaNombreColumna(nombreColB, nombreCol);
				}
			}
		return this;
	}
	private String formatea(Object o, int numDecimales, boolean simboloMoneda, String simbolo) {
		if (o==null || o.equals(Constantes.CAD_VACIA))
			return null;
		String otroSimbolo = (simbolo==null?Constantes.CAD_VACIA:Constantes.NBSP+simbolo);
		return Util.formatearNumero(o.toString(), numDecimales)+
			   (simboloMoneda?Constantes.NBSP+"€":otroSimbolo);
		}
	
	@SuppressWarnings("unchecked") 
	public Filas ordena(String columna, boolean invertido){
		Collections.sort(this, new ComparaCampo(columna,invertido));		
		return this;
	}
}
@SuppressWarnings("rawtypes")
class ComparaCampo implements java.util.Comparator {
	String columna;
	boolean invertido;
	public ComparaCampo(String columna, boolean invertido){
		this.columna=columna;
		this.invertido=invertido;
		}
	 public int compare(Object of1, Object of2) {
		 Fila f1=(Fila)of1;
		 Fila f2=(Fila)of2;
		 
		 Object o1=f1.get(columna);
		 Object o2=f2.get(columna);
		 
		 int ret;
		 if (o1==null)
			 return +1;
		 else if (o2==null)
			 return -1;
		 else if (o1 instanceof String)
			 ret=o1.toString().toLowerCase().compareTo(o2.toString().toLowerCase());
		 else if (o1 instanceof FechaGotta)
			 ret=((FechaGotta) o1).compareTo( (FechaGotta)o2);
		 else {
			 BigDecimal bd1=new BigDecimal(o1.toString());
			 BigDecimal bd2=new BigDecimal(o2.toString());
			 ret=bd1.compareTo(bd2);
		 	}
		 
		 if (this.invertido)
			 return -ret;
		 else
			 return ret;
	 	}
	 
	} 

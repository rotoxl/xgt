package es.burke.gotta;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class Fila{
	private Filas filas;
	private Object [] datos; 
	public Fila(Filas filas){
		datos=new Object[filas.getOrden().size()];
		this.filas=filas;
	}
	public Fila(Filas filas, Object [] _datos){
		if (_datos.length!=filas.getOrden().size())
			throw new java.lang.AssertionError("Error creando la clase Fila: los datos de entrada no se corresponden con la definición de la tabla");
		this.datos=_datos;
		this.filas=filas;
	}
	public Fila(Filas filas, ArrayList<Object> _datos){
		if (_datos.size()!=filas.getOrden().size())
			throw new java.lang.AssertionError("Error creando la clase Fila: los datos de entrada no se corresponden con la definición de la tabla");
		
		Object[] tempDatos=new Object[filas.getOrden().size()];
		for (int i=0; i<_datos.size(); i++){
			tempDatos[i]=_datos.get(i);
			}
		this.datos=tempDatos;
		this.filas=filas;
	}
	public Fila(Filas filas, int capacidadInicial)
		{
		datos=new Object[capacidadInicial];
		this.filas=filas;
		}

	/**
	 * Devuelve el valor de la columna, convirtiendo las cadenas vacías a nulos.
	 * @param nombrecol El nombre de la columna
	 * @return El valor (nunca una cadena vacía)
	 */
	public String getns(String nombrecol) 
		{return Util.noVacía(this.get(nombrecol) );}
	/**
	 * Devuelve el valor de la columna, convirtiendo los nulos a cadena vacía.
	 * @param nombrecol El nombre de la columna
	 * @return El valor (los nulos se convierten a cadena vacía)
	 */
	public String gets(String nombrecol) 
		{return Util.noNulo( this.get(nombrecol) );}
	/**
	 * Devuelve el valor de la columna, convirtiendo los nulos a cadena vacía.
	 * @param idx El índice de la columna
	 * @return El valor (los nulos se convierten a cadena vacía)
	 */
	public String gets(int idx) 
		{return Util.noNulo( this.get(idx) );}
	
	public Object get(String nombrecol) {
		Integer idx=filas.nombreAPosicion(nombrecol);
		if (idx!=null)
			return this.datos[idx];
		return null;
	}

	public void put(String nombrecol, Object valor, boolean comprobarIntegridad){
		Integer idx=filas.nombreAPosicion(nombrecol);
		if (comprobarIntegridad && (idx>=this.datos.length || idx==null))
			Util.getLog().error("Datos mal formados");
		
		if (idx>=this.datos.length){
			Object [] datosNuevos=new Object[filas.getOrden().size()];
			for (int i=0; i<this.datos.length; i++){
				datosNuevos[i]=this.datos[i];
				}
			this.datos=datosNuevos;
			}
		
		this.datos[idx]=valor;
		}
	
	public void put(String nombrecol, Object valor)	{
		this.put(nombrecol, valor, true);
		}
	public boolean containsKey(String string) 
		{return filas.containsKey(string);}
	@Override
	public String toString(){
		String ret = "[";
		for (String s: filas.getOrden()){
			ret+=s+"="+this.get(s)+", ";
		}
		ret+="]";
		return ret;
	}

	public Object get(int j) {
		return datos[j];
	}

	public int size() 
		{return this.datos.length;}
	public ArrayList<Object> toList(){
		ArrayList<Object>ret=new ArrayList<Object>();
		for (String s:filas.getOrden())
			ret.add(get(s));
		return ret;
	}
	public JSONArray JSON(){
		return JSON(true, null); }
	
	public JSONArray JSON(boolean conFormato, List<String> listaColumnas){
		JSONArray ret=new JSONArray();
		if(listaColumnas==null)
			listaColumnas=this.filas.getOrden();
		for (int i=0; i<listaColumnas.size(); i++){
			String nombreColumna=listaColumnas.get(i) ;
			int posColumna=0;
			if (this.filas.nombreAPosicion( nombreColumna )!=null)
				posColumna=this.filas.nombreAPosicion( nombreColumna );
			Object valor;
			try {
				valor = this.get(posColumna);
				String formato;
				if (conFormato && !nombreColumna.equalsIgnoreCase("key")) //OJO: es muy importante no formatear el campo key, ya que luego es devuelto al motor
					formato=this.filas.getTipos().get(posColumna);
				else
					formato="String";
				
				ret.put( Util.preparaParaRAW(valor, formato )  );
				} 
			catch (IndexOutOfBoundsException e) {
				ret.put("'error obteniendo "+nombreColumna+"',");
				}
			
			}
		return ret;
		}
	public void __setitem__(String clave, Object valor){
		this.put(clave, valor);
	}
}

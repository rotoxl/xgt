package es.burke.gotta;

import java.util.Iterator;

public abstract class ITablaDef {
	protected String cd;
	protected String ds;
	protected String clave;
	
	Integer caminoMantenimiento;
	
	public Coleccion<ColumnaDef> columnas=new Coleccion<ColumnaDef>();
	public Coleccion<CampoDef> campos=new Coleccion<CampoDef>();

/////////////////////////
	public void setCd(String nuevo){
		this.cd=nuevo;
		}
	public void setDs(String nuevo){
		this.ds=nuevo;
		}
	public String getCd(){
		return this.cd;
		}
	public String getDs(){
		return this.ds;
		}	
	public CampoDef getCampoClave(){
		return this.campos.get(this.clave);
		}
/////////////////////////	
	public Iterator<CampoDef> enumCampos() {
		return campos.elements();
		}
	public CampoDef putCampo(String nombre, CampoDef cam) {
		 this.campos.put(nombre, cam);
		 return cam;
		}
	public CampoDef getCampo(String nombre) throws ErrorCampoNoExiste{
		CampoDef ret=this.campos.get(nombre);
		if (ret==null)
			throw new ErrorCampoNoExiste("El campo " + nombre + " no existe");
		return ret;
		}
/////////////////////////
	public ColumnaDef putColumna(String nombre, ColumnaDef col){
		 this.columnas.put(nombre,col);
		 return col;
		}
	public ColumnaDef getColumna(String nombre) throws ErrorCampoNoExiste {
		ColumnaDef ret =this.columnas.get(nombre);
		if (ret==null)
			throw new ErrorCampoNoExiste("La columna " + nombre + " no existe");
		return ret;
		}
	public Coleccion<ColumnaDef> getColumnas(){
		return this.columnas;
		}
	public abstract ITabla newTabla(Usuario usr) throws ErrorCreandoTabla;
/////////////////////////
	@Override
	public String toString(){
		return "ITablaDef "+this.cd;
		}
}

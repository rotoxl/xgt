package es.burke.gotta;
import java.util.Iterator;
  
public final class CampoDef implements Iterable<String>{
	private String cd;
	private	String ds;
	private Coleccion<ColumnaDef> columnas=new Coleccion<ColumnaDef>();
	private String tabla; //tabla a la que pertenece el campo

	private TablaDef tRef; //tabla con la que tiene establecida una relación

	public CampoDef(String cd, String ds, String tabla) {
		this.cd=cd; 
		this.ds=ds;
		this.tabla =tabla;
		}

	public String getTabla(){
		return this.tabla;}
	
	public ColumnaDef putColumna(ColumnaDef col){
		 this.columnas.put(col.getCd(), col);
		 return col;
		}

	public ColumnaDef getColumna(String cd_columna){
		return this.columnas.get(cd_columna);
		}

	public ColumnaDef getColumna(int indx) throws ErrorCampoNoExiste{
		if (indx>=this.columnas.size())
			throw new ErrorCampoNoExiste("La columna de índice " + indx + " no existe (campo "+this.tabla+"."+this.cd+")");
		ColumnaDef ret=this.columnas.get(indx);
		if (ret==null)
			throw new ErrorCampoNoExiste("La columna de índice " + indx + " no existe (campo "+this.tabla+"."+this.cd+")");
		return ret;
		}

	public Iterator<ColumnaDef> enumColumnas()
	{return columnas.elements();}
	
	public Coleccion<ColumnaDef> getColumnas()
	{return columnas;}

	public int numColumnas()
		{return columnas.size();}

	public TablaDef getTablaReferencia(){
		return tRef;
		}
	public TablaDef setTablaReferencia(TablaDef v){
		this.tRef=v;
		return v;
		}
	public CampoDef getCampoReferencia(){ 
		if (this.tRef==null)
			return null;
		return this.tRef.getCampoClave();	}
	public ColumnaDef getColumnaDescripcion() throws ErrorCampoNoExiste {
		if (this.tRef.getColDesc()==null)
			return null;
		return this.tRef.getColumna(this.tRef.getColDesc());
		}
	public String getDs(){
		return ds;
		}
	public String getCd(){
		return cd;
		}

	public Iterator<String> iterator() {
		return columnas.iterator();
	}	
	@Override
	public String toString(){
		return "Campo "+this.tabla+"."+this.cd + " ("+ Util.join(",", this.columnas) +")";
	}
}

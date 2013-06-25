package es.burke.gotta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//es una implementación de tablaDef con referencias débiles,
//		para el asistente del diccionario
public class TablaDefdic {
	public String cd,ds,lectura1,lecturaN,escritura,clave,usp,
		columnaDescripción, columnaBúsqueda;
	
	public String erroresDic="";
	
	public Coleccion<ColumnaDefdic> columnas=new Coleccion<ColumnaDefdic>();
	public Coleccion<CampoDefdic> campos=new Coleccion<CampoDefdic>();
	
	public JSONObject JSON() throws JSONException {
		JSONObject ret=new JSONObject()
			.put("nombre",		this.cd			) 
			.put("id",			this.cd			) 
			.put("lectura1", 	this.lectura1	) 
			.put("lecturaN", 	this.lecturaN	) 
			.put("descripcion",  this.ds			) 
			.put("escritura",	this.escritura  ) 
			.put("columnadescripcion",	this.columnaDescripción) 
			.put("columnabusqueda",	this.columnaBúsqueda) 
			.put("clave",		this.clave		) 
			.put("usp",		this.usp) 
			.put("errores",		this.erroresDic) ;
		
		////////
		JSONArray xcolumnas= new JSONArray();
		ret.put("columnas",xcolumnas);

		for (String n : this.columnas.claves){
			ColumnaDefdic col=this.columnas.get(n);
			xcolumnas.put(new JSONObject()
			.put("nombre",		col.cd) 
			.put("tipo",			col.tipo ) 
			.put("longitud",		col.longitud ) 
			.put("descripcion",	col.ds));
			}
		
		////////
		JSONArray xCampos= new JSONArray();
		ret.put("campos",xCampos);
		for (String n : this.campos){
			CampoDefdic cam=this.campos.get(n);
			if (cam.columnas.size()>1)  
				xCampos.put(new JSONObject()
				.put("nombre",cam.cd) 
				.put("columnas", Util.JSON(cam.columnas.getOrden()))
				);
				
			}
	
		//////////
		JSONArray referencias= new JSONArray();
		ret.put("referencias",referencias);
		for (String n : this.campos){
			CampoDefdic cam=this.campos.get(n);
			if (cam.tRef==null) 
				continue;
			referencias.put(new JSONObject()

			.put("campoPrinc",cam.cd) 
			.put("colsPrinc", Util.join(",", cam.columnas.getOrden() )) 

			.put("tablaRef",cam.tRef ) 
			);
		}
		return ret;
		}
	public TablaDefdic(String cd, String ds, String lectura1, String lecturaN, String escritura, String clave, String usp, String columnadescripcion, String columnabusqueda) { 
		this.cd=cd;
		this.ds=ds;
		
		this.lectura1= lectura1;
		this.escritura=escritura;
		this.lecturaN=lecturaN;
		this.clave=clave;
		this.usp = usp;
		this.columnaDescripción=columnadescripcion;
		this.columnaBúsqueda=columnabusqueda;
	}
	
	public ColumnaDefdic putColumna(String nombre, ColumnaDefdic col){
		 this.columnas.put(nombre,col);
		 return col;
		}
	public ColumnaDefdic getColumna(String nombre)  {
		ColumnaDefdic ret =this.columnas.get(nombre);
		return ret;
		}

	public CampoDefdic putCampo(String nombre, CampoDefdic cam) {
		 this.campos.put(nombre, cam);
		 return cam;
		}
	public CampoDefdic getCampo(String nombre) {
		CampoDefdic ret=this.campos.get(nombre);
		return ret;}
	public Coleccion<ColumnaDefdic> getColumnas() {
		return this.columnas;}
}
class ColumnaDefdic {
	public String cd;
	public	String ds;
	public	String tipo;
	public	int longitud;
	public String subtipo;
	
	public ColumnaDefdic(String cd, String ds, String tipo, int longitud, String subtipo) { 
		this.cd=cd;
		this.ds=ds;
		this.tipo=tipo;
		this.longitud=longitud;
		this.subtipo=subtipo;
		}
}
class CampoDefdic {
	public String cd;
	public String ds;
	
	public String tRef;
	
	public TablaDefdic tablaRef;
	
	public CampoDefdic(String cd, String ds) {
		this.cd=cd; 
		this.ds=ds;
	}
	

	public Coleccion<ColumnaDefdic> columnas=new Coleccion<ColumnaDefdic>();
	public ColumnaDefdic putColumna(String nombre, ColumnaDefdic col){
		 this.columnas.put(nombre,col);
		 return col;
		}
}


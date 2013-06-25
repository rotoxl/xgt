package es.burke.gotta;

import java.util.ArrayList;
import java.util.Iterator;

public class Esquema {
	private Coleccion<ITablaDef> tablas;
	public boolean existeDLL_Guiones=true;
	
	public boolean existeTabla(String nombre){
		return tablas.containsKey(nombre);
	}
	private void OrdenarEsquema(Coleccion<TablaDef> c) throws ErrorTablaNoExiste{
		if (c!=null) {
			Iterator<TablaDef> i = c.elements();
			TablaDef t = null;
			while (i.hasNext()) {
				t = i.next();
				Ordenar(t, c); 
				}
			}
		}
	private void Ordenar(TablaDef t, Coleccion<TablaDef> c) throws ErrorTablaNoExiste  {
		Ordenar(t, c, new Coleccion<TablaDef>());
		
	}
	
	private Coleccion<TablaDef> generaColTablas(Aplicacion apli, Filas rs, Coleccion<TablaDef> tb, ArrayList<String> lecturaLimitada){
		String campoClave=null,lecturaN=null,lectura1=null,escritura=null,descripcion=null,usp=null;
		String nombre, colDesc, colBúsq, clase; 
		Integer caminoMantenimiento;
		TablaDef t;
		
		for (Fila conf:rs) {
			nombre = conf.gets("nombre");
			if (lecturaLimitada !=null && !lecturaLimitada.contains(nombre.toLowerCase()))
				continue;
			try {
				apli.println("leyendo "+nombre+"...");
				campoClave = conf.getns("campoclave");

				lecturaN = conf.getns("lecturaN");
				descripcion = conf.getns("descripcion");
				lectura1 = conf.getns("lectura1");
				escritura = conf.getns("escritura");
				usp = conf.getns("usp");
				clase = conf.gets("clase");
				colDesc = conf.getns("columnaDescripcion");
				colBúsq = conf.getns("columnaBusqueda");
				caminoMantenimiento=( conf.get("CD_Camino")!=null ? Integer.parseInt( conf.gets("CD_Camino")) : null );
				
				if (clase.equalsIgnoreCase("jython"))
					t=new TablaJythonDef(nombre, descripcion, lectura1, lecturaN, escritura, campoClave, usp, caminoMantenimiento,colDesc,colBúsq);
				else
					t=new TablaDef(nombre, descripcion, lectura1, lecturaN, escritura, campoClave, usp, caminoMantenimiento,colDesc,colBúsq);
				tb.put(nombre, t);
				apli.println("OK");
				} 
			catch (Exception e){
				String texto="Error leyendo tablas, tabla:"+nombre+". Descripción del error: "+e.getMessage();
				apli.erroresArranque.append(texto+"\n");
				continue;
				}	
			}
		return tb;
		}
	
	public Esquema (Aplicacion apli) throws ErrorDiccionario {
		tablas = new Coleccion<ITablaDef>();
		Coleccion<TablaDef> tb = new Coleccion<TablaDef>();
		Conexion conex = new Conexion(apli);
		
		apli.erroresArranque=new StringBuffer();
		// tablas
		Filas rs = GestorMetaDatos.leeDIC_Tablas(conex);
		tb=generaColTablas(apli, rs, tb, Util.creaListaString("tra_acciones", "tra_caminos", "dic_tablas"));
		
		//volvemos a leerlas, pero ahora con el camino de mantenimiento
		apli.erroresArranque=new StringBuffer();
		rs = GestorMetaDatos.leeDIC_TablasConCaminoMantenimiento(conex, tb);
		tb=generaColTablas(apli, rs, tb, null);
		
		String descripcion; TablaDef t;
		// columnas
		rs = GestorMetaDatos.leeDIC_Columnas(conex, tb);
		String subtipo;
		for (Fila conf:rs) {
			String nombreColumna=null;
			String nombreTabla=null;
			try {
				nombreTabla = conf.gets("nombretabla").toLowerCase();
				nombreColumna = conf.gets("nombrecolumna").toLowerCase();
				String tipo = conf.gets("tipo");
				String tlongitud=conf.gets("longitud");
				if (tlongitud.equals("")) tlongitud="0";
				int longitud = Integer.parseInt( tlongitud );
				
				descripcion = conf.gets("descripcion") ;
				subtipo = conf.gets("subtipo");

				t = tb.get(nombreTabla);

				ColumnaDef col=new ColumnaDef(nombreColumna,descripcion,tipo,longitud, subtipo);
				t.putColumna(nombreColumna, col);

				CampoDef cam = new CampoDef(nombreColumna,descripcion,t.getCd());
				cam.putColumna(col);
				t.putCampo(nombreColumna,cam);
				}
			catch (Exception e) {
				String texto="Error leyendo columnas, Columna: "+nombreTabla+"."+nombreColumna+". Descripción del error: "+e.getMessage();
				apli.erroresArranque.append(texto+"\n");
				continue;
				 }
			}
		
		// campos
		String nombreTabla=null, nombreCampo=null, nombreColumna;
		rs = GestorMetaDatos.leeDIC_Campos(conex, tb);
		for (Fila conf:rs) {
			try 
				{
				nombreTabla = conf.gets("nombretabla").toLowerCase();
				nombreCampo = conf.gets("nombrecampo").toLowerCase();
				nombreColumna =conf.gets("nombrecolumna").toLowerCase();
				descripcion = Util.toString(conf.get("descripcion") );

				t = tb.get(nombreTabla);
				ColumnaDef col = t.getColumna(nombreColumna);
				CampoDef cam;
				if(t.campos.containsKey(nombreCampo))
					cam=t.getCampo(nombreCampo);
				else
					{
					cam = new CampoDef(nombreCampo,descripcion,t.getCd());
					t.putCampo(nombreCampo, cam);
					}
				cam.putColumna(col);
				}	 
			catch (Exception e) {
				String texto="Error leyendo campos: "+nombreTabla+"."+nombreCampo+". Descripción del error: "+e.getMessage();
				apli.erroresArranque.append(texto+"\n");
				continue;
				}
			}
		
		//comprobamos que el campo clave esté fetén:
		for (TablaDef tt:tb.values()){
			if (tt.getCampoClave()==null) {
				String texto="Error leyendo tablas: "+tt.getCd()+". El campo clave '"+tt.clave+"' no está definido ni en DIC_Columnas ni en DIC_Campos.";
				apli.erroresArranque.append(texto+"\n");
				continue;
				}
			if (tt.getColBúsq()==null){
				CampoDef clave = tt.getCampoClave();
				Coleccion<ColumnaDef> cols = clave.getColumnas();
				tt.setColBúsq(cols.get(cols.size()-1).getCd());
				}
			}
		
		// referencias
		String 	strTabPpal="",strCamPpal="", strTabRef="";
		rs = GestorMetaDatos.leeDIC_Referencias(conex, tb);
		TablaDef tabPpal = null;
		CampoDef camPpal = null;
		TablaDef tabRef = null;
		
		for (Fila conf:rs) {
			try {
				strTabPpal = conf.gets("tablaprincipal").toLowerCase();
				strCamPpal = conf.gets("campoprincipal").toLowerCase();
				strTabRef = conf.gets("tablareferencia").toLowerCase();

				tabPpal = tb.get(strTabPpal);
				if(tabPpal==null)
					{
					apli.erroresArranque.append("La tabla "+strTabPpal+" no está definida en DIC_TABLAS");
					continue;
					}

				camPpal = tabPpal.getCampo(strCamPpal);	
				if(camPpal == null){
					apli.erroresArranque.append("La tabla "+strTabPpal+" no tiene un campo o columna" + strCamPpal + " como se indica en DIC_REFERENCIAS\n");
					continue;
					}
				tabRef = tb.get(strTabRef);
				if(tabRef==null){
					apli.erroresArranque.append("La tabla "+strTabRef+" no está definida en DIC_TABLAS\n");
					continue;
					}
				if(tabRef.getCampoClave().numColumnas()!=camPpal.numColumnas()){
					String texto="Los campos de las tablas "+tabRef.getCampoClave()+" y "+ camPpal +" no tienen el mismo número de columnas";
					apli.erroresArranque.append(texto+"\n");
					continue;
					}
				camPpal.setTablaReferencia(tabRef);
			}
			catch (Exception e) {
				String texto="Error leyendo referencias: "+strTabPpal+"."+strCamPpal+" con "+strTabRef+"."+strTabRef+". Descripción del error: "+e.getMessage();
				apli.erroresArranque.append(texto+"\n");
				}
			}
		
		this.ponTablasSistema(apli);
		for (TablaDef td : tb.values())
			td.arreglaLectura1();
		
		OrdenarEsquema(tb);
		}
	private void Ordenar(TablaDef t, Coleccion<TablaDef> ct, Coleccion<TablaDef> repe) throws ErrorTablaNoExiste{
		Iterator<CampoDef> campos = t.enumCampos();
		String nombretabla = t.getCd();
		
		CampoDef c = null;
		TablaDef tref = null;
		while (campos.hasNext()){
			c = campos.next();
			
			if (c.getTablaReferencia()!=null){
				tref = ct.get(c.getTablaReferencia().getCd());
				if (tref==null)
					throw new ErrorTablaNoExiste("La tabla '"+c.getTablaReferencia()+"' no está definida en el esquema de Gotta (DIC_TABLAS, DIC_COLUMNAS, etc.)");
				if(!tref.getCd().equals(nombretabla)){
					if(repe.containsKey(tref.getCd()))
						throw new ErrorTablaNoExiste("La tabla " + t.cd + "/" + tref.cd + " contiene referencias cruzadas.");
					repe.put(tref.getCd(), tref);
					Ordenar(tref, ct, repe);
					repe.remove(tref.getCd());
					}
				}
			}
		if (!tablas.containsKey(nombretabla))
			tablas.put(nombretabla, t);
		}
	public ITablaDef getTablaDef(String tabla) throws ErrorTablaNoExiste {
		if (tabla==null || tabla.equals(""))
			return null;
		
		ITablaDef ret=this.tablas.get(tabla);
		if (ret==null && !tabla.startsWith("#"))
			throw new ErrorTablaNoExiste("La tabla '"+tabla+"' no está definida en el esquema de Gotta (DIC_TABLAS, DIC_COLUMNAS, etc.)");
		return ret;
		}
	public ArrayList<String> getOrdenTablas(){
		return this.tablas.getOrden();}

	public void ponTablasSistema(Aplicacion apli){
		//lista de vistas y procedimientos almacenados de la bd
		String sql;
		if (apli.getMarcaBaseDatos().equals(Constantes.MarcaBaseDatos.MSSQL))
			sql="select name as Objeto, type_desc as \"Tipo de objeto\", modify_date as \"Fecha de modificación\" from sys.objects where type in ('P','V') ";
		else
			sql="select object_name as Objeto, object_type as \"Tipo de objeto\", last_ddl_time as \"Fecha de modificación\" from user_objects where object_type in ('PROCEDURE', 'VIEW') ";
		
		String nt="gt_listaProcsVistas";
		TablaDef tb=new TablaDef(nt, nt, null, sql, null, "objeto", null, null, "objeto", "objeto");
		this.tablas.put(nt, tb);
		
		String[] nombres={"objeto", "tipoObjeto", "f_modificacion"};
		for (String n:nombres){
			ColumnaDef c=new ColumnaDef(n, n, Constantes.STRING, 200, null);
			tb.columnas.put(n, c);
			
			CampoDef ca=new CampoDef(n, n, nt);
			ca.putColumna(c);
			tb.campos.put(n, ca);
			}
		}
}

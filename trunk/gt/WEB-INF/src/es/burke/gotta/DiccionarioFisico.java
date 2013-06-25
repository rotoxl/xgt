package es.burke.gotta;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.IJSONwritable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DiccionarioFisico {
	static HashMap<String, String> hm = new HashMap<String, String>();
	static{
		hm.put("12", 	Constantes.STRING);
		hm.put("-1",	Constantes.MEMO);
		hm.put("1", 	Constantes.STRING); 	//Char
		hm.put("2", 	Constantes.CURRENCY);
		hm.put("4",		Constantes.INTEGER);
		hm.put("5",		Constantes.LONG);
		hm.put("-6",	Constantes.INTEGER);
		hm.put("91",	Constantes.DATE);
		hm.put("92",	Constantes.DATE);
		hm.put("93",	Constantes.DATE);
		hm.put("8",		Constantes.DOUBLE);
		hm.put("3",		Constantes.CURRENCY);	
		hm.put("6",		"Float"); 				//$NON-NLS-1$
		hm.put("-7",	Constantes.BOOLEAN);	//Bit
		hm.put("7",		Constantes.LONG);		//Real
	}
	String aplicacion = null;
    String prefijo="";
    
    ArrayList<String> listaCatalogos;
    final static String[] names = {"TABLE", "VIEW"};
    
	public DiccionarioFisico(HttpServletRequest request)  {
		this.aplicacion=Util.obtenValor(request, "aplicacion");
		this.prefijo=PoolAplicaciones.getPrefijo(this.aplicacion);
	    
	    int i=0;
	    listaCatalogos=new ArrayList<String>();
	    while (request.getParameterMap().containsKey("bbdd"+i)) {
	    	String p=Util.obtenValor(request, "bbdd"+i);
	        this.listaCatalogos.add(p);
	        i++;
	        }
	}
	public boolean valida(HttpServletRequest request, HttpServletResponse response) throws ErrorUsuarioNoValido {
		ConexionLight conex = creaConn();
		Connection con = null;
		try {
			String login, pas;
			HttpSession sesion = request.getSession(false);
			String idSes="diccionario"+this.aplicacion;
			
			boolean hayQueGuardar=false;
			if (sesion.getAttribute(idSes)!=null){
				Usuario usu=(Usuario)sesion.getAttribute(idSes);
				login=usu.getLogin(); 
				pas=usu.getPassword();
				}
			else {
				login = Util.obtenValor(request, "login");
				pas = Util.obtenValor(request, "password");
				hayQueGuardar=true;
				}
				
			con = conex.obtenerConexionJR();
			DatabaseMetaData md = con.getMetaData();
			String url = md.getURL();
			
			Connection conxxx = DriverManager.getConnection(url, login, pas);
			conxxx.close();
			
			if (hayQueGuardar) {
				Usuario usu=new Usuario();
				usu.setLogin(login); usu.setPassword(pas);
				sesion.setAttribute(idSes, usu);
				Util.sendRedirect(request, response, "diccionario2.jsp");
				}
			
			} 
		catch (SQLException e) { 
			throw new ErrorUsuarioNoValido("Usuario o contraseña no válidos: para entrar en el diccionario debe usar un usuario válido del S.G. de la base de datos. "+e.getMessage());
			} 
		catch (ErrorConexionPerdida e) {
			throw new ErrorUsuarioNoValido("Usuario o contraseña no válidos: para entrar en el diccionario debe usar un usuario válido del S.G. de la base de datos. "+e.getMessage());
			} 
		catch (IOException e) {
			throw new ErrorUsuarioNoValido("Usuario o contraseña no válidos: para entrar en el diccionario debe usar un usuario válido del S.G. de la base de datos. "+e.getMessage());
			}
		finally {
			if (conex!=null) conex.liberarConexionJR(con);
			}
		return true;
	}
	public IJSONwritable leeDiccionarioFisico() throws ErrorGotta, JSONException {
		DatabaseMetaData md = null;
		Connection con=null;
		JSONArray tablas = new JSONArray();
		JSONObject ret = new JSONObject().put("tablas",tablas); 
		ConexionLight conex = creaConn();
		try {
			for (String bbdd : this.listaCatalogos){
				ArrayList<String>temp=Util.split(bbdd, Constantes.PUNTO);
				
				String catalogo=temp.get(1);
				String esquema=temp.get(2);
				System.out.println(catalogo+"------"+esquema);
				if (catalogo.equals("")) 
					catalogo=null;
				if (esquema.equals("")) 
					esquema="%";
				con = conex.obtenerConexionJR();			
				md = con.getMetaData();
				ResultSet tableNames = md.getTables(catalogo, esquema, "%", names);
				
				while (tableNames.next()) {
					String nombretab=tableNames.getString("TABLE_NAME");
					if(nombretab.startsWith("TT_"))continue;
					String tipotab=tableNames.getString("TABLE_TYPE").toLowerCase();
					Object tabla = new JSONObject()
					.put("nombre",nombretab)
					.put("esquema",Util.noNulo(catalogo)+Constantes.PUNTO+esquema)
					.put("id", Util.noNulo(catalogo)+Constantes.PUNTO+esquema+Constantes.PUNTO+nombretab)
					.put("lectura1","")
					.put("lecturaN", "select * from "+nombretab)
					.put("descripcion", reemplazaNomenclatura(nombretab))
					.put("escritura",nombretab)
					.put("tipo",tipotab)
					.put("clave",getPK(catalogo, esquema, nombretab, md))
					.put("columnas", new JSONArray())
					.put("referencias",new JSONArray());

					tablas.put(tabla);
					}
				tableNames.close();
				conex.liberarConexionJR(con);
				}
			return ret;
			}
		catch(ErrorConexionPerdida e) {
			throw new ErrorGotta("Error Leyendo Diccionario Físico: "+e.getMessage());
			} 
		catch (SQLException e) {
			throw new ErrorGotta("Error Leyendo Diccionario Físico: "+e.getMessage());
			}
		finally{
			if (conex!=null)
					conex.liberarConexionJR(con);
			}
	}
	public JSONArray getColumnasTabla(HttpServletRequest request) throws ErrorGotta, JSONException {
		DatabaseMetaData md = null;
		Connection con=null;
		String t = Util.obtenValor(request, "tabla");
		JSONArray sb = new JSONArray();
		ConexionLight conex = creaConn();
		ResultSet cols = null;
		try {
			ArrayList<String>temp=Util.split(t, Constantes.PUNTO);
			
			String catalogo=temp.get(0);
			String esquema=temp.get(1);
			String tabla=temp.get(2);
			
			if (catalogo.equals("")) 
				catalogo=null;
			if (esquema.equals("")) 
				esquema="%";
			con = conex.obtenerConexionJR();			
			md = con.getMetaData();
			cols = md.getColumns(catalogo, esquema, tabla, "%");
			
			while (cols.next()) {
				String ntabla=cols.getString("TABLE_NAME");
				String nombre=cols.getString("COLUMN_NAME");
				if (nombre.equals("ts"))
					continue;
				String tipo=hm.get(cols.getString("DATA_TYPE"));
				tipo=Util.capitalize(tipo);
				String longitud=tipo.equals(Constantes.MEMO)?"1000":cols.getString("COLUMN_SIZE");
				
				JSONObject col = new JSONObject()
					.put("nombre",nombre)
					  .put("tipo", tipo)
					  .put("longitud",longitud)
					  .put("id", catalogo+"."+esquema+"."+ntabla+"."+nombre)
					  .put("descripcion", reemplazaNomenclatura(nombre));
				sb.put(col);
				}
			cols.close();
			conex.liberarConexionJR(con);
			
			return sb;
			}
		catch(ErrorConexionPerdida e) {
			throw new ErrorGotta("Error Leyendo Diccionario Físico: "+e.getMessage());
			} 
		catch (SQLException e) {
			throw new ErrorGotta("Error Leyendo Diccionario Físico: "+e.getMessage());
			}
		finally{
			if (conex!=null) {
				conex.liberarConexionJR(con);}
			}		
	}
	public JSONArray getReferenciasTabla(HttpServletRequest request) throws ErrorGotta, JSONException  {
		DatabaseMetaData md = null;
		Connection con=null;
		String t = Util.obtenValor(request, "tabla");
		ConexionLight conex = creaConn();
		try {
			ArrayList<String>temp=Util.split(t, Constantes.PUNTO);
			
			String catalogo=temp.get(0);
			String esquema=temp.get(1);
			String tabla=temp.get(2);
			
			if (catalogo.equals("")) 
				catalogo=null;
			if (esquema.equals("")) 
				esquema="%";
			
			con = conex.obtenerConexionJR();			
			md = con.getMetaData();
			JSONArray sb = getReferenciasPorTabla(catalogo, esquema, tabla, md);
			conex.liberarConexionJR(con);
		
			return sb;
			}
		catch(ErrorGotta e) {
			throw new ErrorGotta("Error Leyendo Diccionario Físico: "+e.getMessage());
			} 
		catch (SQLException e) {
			throw new ErrorGotta("Error Leyendo Diccionario Físico: "+e.getMessage());
			}
		finally{
			if (conex!=null) {
				conex.liberarConexionJR(con);}
			}				
		}
	private String reemplazaNomenclatura(String nombre) {
		//a la gente que usa una nomenclatura mas o menos estándar de gotta, les ayudamos un poco
		Coleccion<String> re=new Coleccion<String>(true);
		
		re.put("cd_", "Cód. "); re.put("CD_", "Cód. "); 
		re.put("ds_", "Desc. ");re.put("DS_", "Desc. ");
		re.put("f_", "F. ");	re.put("F_", "F. ");
		re.put("ion", "ión"); 	re.put("ION", "IÓN"); //en español, casi siempre
		re.put("_", " ");
		
		String retorno=nombre;
		for (String n:re.claves)
			retorno=Util.replaceTodos(retorno, n, re.get(n));
			
		return retorno;
		
	}
	private JSONArray getReferenciasPorTabla(String catalogo, String esquema, String tabla, DatabaseMetaData md) throws ErrorGotta, JSONException{
		try{
			ResultSet refs = md.getImportedKeys(catalogo, esquema, tabla);
			
			Coleccion<String> colsPK=new Coleccion<String>();
			Coleccion<String> colsFK=new Coleccion<String>();

			String tablaPrinc, tablaRef, campoRef, id;
			JSONArray ret = new JSONArray();

			while (refs.next()){
				tablaPrinc = refs.getString("PKTABLE_NAME");
				tablaRef = refs.getString("FKTABLE_NAME");
				campoRef = refs.getString("FK_NAME");
				
				id=tablaPrinc+Constantes.PUNTO+tablaRef+Constantes.PUNTO+campoRef;
					
				if (!colsPK.containsKey(id)) {
					colsPK.put(id, "");
					colsFK.put(id, "");
					}
				colsPK.put(id, colsPK.get(id)+refs.getString("FKCOLUMN_NAME")+",");				
				colsFK.put(id, colsFK.get(id)+refs.getString("PKCOLUMN_NAME")+",");
				}
			
			ArrayList<String> nombresPillados=new ArrayList<String>();
			for (String i:colsPK.getOrden()) {
				String columnasPK=Util.quita(colsPK.get(i),1);
				String columnasFK=Util.quita(colsFK.get(i),1);
				
				ArrayList<String> temp = Util.split(i, Constantes.PUNTO);
				String nombre = cuantosHay(columnasPK)>1? "CD_"+temp.get(0) : columnasPK;
					
				if (nombresPillados.contains(nombre)){
					int j=1;
					while (nombresPillados.contains(nombre+j)){
						j++;
						}
					nombre+=j;
					}
				nombresPillados.add(nombre);
					ret.put(new JSONObject()
					.put("campoPrinc", nombre)
					.put("id", catalogo+Constantes.PUNTO+esquema+Constantes.PUNTO+tabla+"."+nombre)
					.put("colsPrinc",columnasPK)
					.put("tablaRef", temp.get(0))
					.put("colsRef",  columnasFK)
					);
				}
			refs.close();
			return ret;
			}
		catch (SQLException e){
			throw new ErrorGotta(e.getMessage());
			}
	}
	private int cuantosHay(String s){
		return s.split(",").length;
	}
	private JSONObject getPK(String catalogo, String esquema, String tabla, DatabaseMetaData md) throws JSONException, SQLException{
		JSONObject ret = new JSONObject();
		JSONArray cols = new JSONArray();
		ResultSet pk = md.getPrimaryKeys(catalogo, esquema, tabla);
		while (pk.next()){
			ret.put("nombre",pk.getString("PK_NAME"));
			cols.put(pk.getString("COLUMN_NAME"));
			}
		ret.put("columnas", cols);
		pk.close();
		return ret;
		}
//////////////////////////////////////////////////////
	public JSONObject leeDiccionarioLogico() throws ErrorGotta, JSONException  {
		Coleccion<TablaDefdic> tb = new Coleccion<TablaDefdic>();
		ConexionLight conex = creaConn();
			
		// LAS TABLAS
		String campoClave=null,lecturaN=null,lectura1=null,escritura=null,descripcion=null,usp=null;		
		Filas rs = GestorMetaDatos.leeDIC_Tablas(conex);

		TablaDefdic t = null;
		for (Fila conf:rs) {
			String nombre = conf.gets("nombre");
			campoClave = conf.gets("campoclave");

			lecturaN = conf.getns("lecturaN");
			descripcion = conf.getns("descripcion");
			lectura1 = conf.getns("lectura1");
			escritura = conf.getns("escritura");
			String columnadescripcion = conf.getns("columnadescripcion");
			String columnabusqueda = conf.getns("columnabusqueda");
			usp = conf.gets("usp");
			//Integer caminoMantenimiento=( conf.get("CD_Camino")!=null ? Integer.parseInt( conf.gets("CD_Camino")) : null );
			
			t=new TablaDefdic(nombre, descripcion, lectura1, lecturaN, escritura, campoClave, usp, columnadescripcion, columnabusqueda);
			tb.put(nombre, t);
			}
		
		// LAS COLUMNAS
		String nombreTabla=""; String nombreColumna=""; String tipo=""; descripcion = null; String subtipo=null;
		int longitud = 0;
		
		rs = GestorMetaDatos.leeDIC_Columnas(conex, null);
		
		ColumnaDefdic col = null;
		CampoDefdic cam = null;
		for (Fila conf:rs) {
			nombreTabla = conf.gets("nombretabla");
			nombreColumna = conf.gets("nombrecolumna");
			tipo = conf.gets("tipo");
			
			Object tlongitud=conf.get("longitud");
			if (tlongitud==null) tlongitud=0;
			longitud = Integer.parseInt( tlongitud.toString() );
			
			descripcion = conf.gets("descripcion") ;
			subtipo = conf.gets("subtipo");

			t = tb.get(nombreTabla);

			col=new ColumnaDefdic(nombreColumna,descripcion,tipo,longitud, subtipo);
			t.putColumna(nombreColumna, col);

			cam = new CampoDefdic(nombreColumna,descripcion);
			cam.putColumna(nombreColumna,col);
			t.putCampo(nombreColumna,cam);
			}
			
		// LOS CAMPOS
		nombreTabla=null; String nombreCampo=null; nombreColumna=null;descripcion = null;
		rs = GestorMetaDatos.leeDIC_Campos(conex, null);
		for (Fila conf:rs) {
				nombreTabla = conf.gets("nombretabla");
					
				nombreCampo = conf.gets("nombrecampo");
				nombreColumna =conf.gets("nombrecolumna");
				descripcion = Util.toString(conf.get("descripcion") );

				t = tb.get(nombreTabla);
				col = t.columnas.get(nombreColumna);
				if (t.campos.containsKey(nombreCampo))
					cam=t.getCampo(nombreCampo);
				else
					{
					cam = new CampoDefdic(nombreCampo,descripcion);
					t.putCampo(nombreCampo, cam);
					}
				if(!t.columnas.containsKey(nombreColumna))
					t.erroresDic+="La columna "+nombreColumna+" del campo "+nombreCampo+" no pertenece a la tabla "+nombreTabla+". ";
				cam.putColumna(nombreColumna , col);
				}
			
		//comprobamos que el campo clave esté fetén:
		for (TablaDefdic tt:tb.values()){
			if (tt.clave==null || !tt.campos.containsKey(tt.clave)) 
				tt.erroresDic+="El campo clave '"+tt.clave+"' no está definido ni en DIC_Columnas ni en DIC_Campos.";
			}
		
		// LAS REFERENCIAS
		String 	stPrinc="",scPrinc="", stRef="";
		TablaDefdic tPrinc = null; CampoDefdic cPrinc = null; 
		TablaDefdic tRef = null; 
		
		rs = GestorMetaDatos.leeDIC_Referencias(conex, null);
		for (Fila conf:rs) {
			stPrinc = conf.gets("tablaprincipal");
			scPrinc = conf.gets("campoprincipal");
			stRef = conf.gets("tablareferencia");
			
			tPrinc = tb.get(stPrinc);
			tRef = tb.get(stRef);
			
			if (!tPrinc.campos.containsKey(scPrinc.toLowerCase()))
				tPrinc.erroresDic+="Referencias: el campo o columna '"+scPrinc+"' de la tabla '"+stPrinc+"' no está definido ni en DIC_Columnas ni en DIC_Campos.";
				
			
			if (tRef==null) 
				tPrinc.erroresDic+="Referencias tabla '"+stPrinc+"': la tabla '"+stRef+"' no está definida en DIC_Tablas.";
			
			try {
				cPrinc = tPrinc.campos.get(scPrinc);
				if (cPrinc==null) {
					cPrinc = new CampoDefdic(scPrinc,null);
					tPrinc.campos.put(scPrinc, cPrinc);
					}

				cPrinc.tablaRef=tRef;
				cPrinc.tRef=stRef;
				
				}
			catch (NullPointerException npe){
				//pass
				}
			}
		
		//Una vez leido...
		JSONArray tablas = new JSONArray();
		JSONObject ret = new JSONObject().put("tablas",tablas);
		for (TablaDefdic tt: tb.getDatosOrdenados() ){
			tablas.put( tt.JSON() );
			}
		
		return ret;
		}

	private ConexionLight creaConn(){
		return new ConexionLight(this.aplicacion,this.prefijo);
	}
//////////////////////////////////////////////////////
	private JSONObject respuestaOK() throws JSONException{return new JSONObject().put("tipo","ok");}
	
	private JSONArray evalúa(HttpServletRequest request) throws JSONException{
		String json=Util.obtenValor(request, "datos");
			
		JSONArray ret=new JSONArray(json);
		//ret=quitaNulos(ret);
		return ret;
		}
	public JSONObject edDIC_Tablas(HttpServletRequest request) throws ErrorCampoNoExiste, ErrorVolcandoDatos, ErrorFilaNoExiste, ErrorTiposNoCoinciden, JSONException, ErrorArrancandoAplicacion {
		GestorMetaDatos.edDIC_Tablas(creaConn(), evalúa(request));
		return respuestaOK();
		}
	public JSONObject edDIC_Columnas(HttpServletRequest request) throws ErrorVolcandoDatos, ErrorCampoNoExiste, ErrorFilaNoExiste, ErrorTiposNoCoinciden, JSONException, ErrorArrancandoAplicacion {
		GestorMetaDatos.edDIC_Columnas(creaConn(), evalúa(request));
		return respuestaOK();
		}
	public JSONObject edDIC_Campos(HttpServletRequest request) throws ErrorVolcandoDatos, ErrorCampoNoExiste, ErrorFilaNoExiste, ErrorTiposNoCoinciden, JSONException, ErrorArrancandoAplicacion {
		GestorMetaDatos.edDIC_Campos(creaConn(), evalúa(request));
		return respuestaOK();
		}
	public JSONObject edDIC_Referencias(HttpServletRequest request) throws ErrorVolcandoDatos, ErrorCampoNoExiste, ErrorFilaNoExiste, ErrorTiposNoCoinciden, JSONException, ErrorArrancandoAplicacion {
		GestorMetaDatos.edDIC_Referencias(creaConn(), evalúa(request));
		return respuestaOK();
		}
///////
	public JSONArray sacaCatalogos() {
		String lstAplicaciones=this.aplicacion;		
		DatabaseMetaData md = null;
		
		HashSet<HashMap<String,String>> s=new HashSet<HashMap<String,String>>();
		
		ConexionLight conex = null;
		Connection con = null;
		
		for (String conexionTomcat:Util.split(lstAplicaciones, Constantes.COMA)) {
			try {
				conex = creaConn();
				con = conex.obtenerConexionJR();
				md = con.getMetaData();
				String dpn = md.getDatabaseProductName();
				String catalogo, esquema;
				
				ResultSet catalogs = md.getCatalogs();
                while (catalogs.next()) {
                	catalogo = catalogs.getString(1);
                	esquema = "%";               
                	try {
                		sacaTablas(conexionTomcat, dpn, catalogo, esquema, md, s);
                		}
                	catch (SQLException e) {
                		//pass
                		}
                	catch (NullPointerException e) {
                		//pass
                		} 
					}
                catalogs.close();
                
                ResultSet esquemas = md.getSchemas();
                while (esquemas.next()) {
                	esquema = esquemas.getString(1);
                	catalogo= null;
                	try {
                		sacaTablas(conexionTomcat, dpn, catalogo, esquema, md, s);
                		}
                	catch (SQLException e) {
                		//pass
                		}
                	catch (NullPointerException e) {
                		//pass
                		}
					}
				esquemas.close();
				conex.liberarConexion(con);
				}
			catch (SQLException e) {
				e.printStackTrace();
				}
			finally {
				if (conex!=null) 
					conex.liberarConexion(con);
				}
			}
		return new JSONArray(s);
	}

	public void sacaTablas(String conexionTomcat, 
			String dpn, String catalogo, String esquema, DatabaseMetaData md,
			HashSet<HashMap<String,String>> s) throws SQLException,NullPointerException {
		
		ResultSet tablas = md.getTables(catalogo, esquema, "%", names);
		
		while (tablas.next()) {
			catalogo = tablas.getString(1);
			esquema= tablas.getString(2);
			String id=conexionTomcat+Constantes.PUNTO+Util.noNulo(catalogo)+Constantes.PUNTO+esquema;
			HashMap<String, String> ret = new HashMap<String, String>();
			ret.put("id",  id);
			ret.put("conexion",conexionTomcat); ret.put("catalogo",catalogo);
			ret.put("esquema", esquema); ret.put("tipoBD",  dpn);
			if (conexionTomcat.equalsIgnoreCase(catalogo) 		/*SQL Server*/
					|| conexionTomcat.equalsIgnoreCase(esquema) /*Oracle*/ ) 
				//casi seguro que esta es la buena
				ret.put("_filaseleccionada_", "1");
			s.add(ret);
			}
		tablas.close();		
		}

//////////////////////////////////////////////////////
}

package es.burke.gotta;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.python.util.PythonInterpreter;

import es.burke.gotta.Constantes.MarcaBaseDatos;
import es.burke.gotta.Constantes.TipoMensajeGotta;
import es.burke.gotta.dll.DLLJython;
import es.burke.gotta.trabajos.Trabajos;

public class Aplicacion {
	public transient ArrayList<TiempoSQL> SqlEjecutados=new ArrayList<TiempoSQL>(); 

	JSONArray cacheNombresColumnas=null;
	
	public enum DIC_Configuracion{
		icoWeb,
		rptWeb,
		dotWeb,
		
		segSinLogin,
		usarMilis,
		tipoMantenimientoTablas,
		
		metodoValidacion,
		
		tituloWeb,
		msgLvwSinDatos, 
		
		mli,
		
		smtp_server, smtp_port, smtp_user, smtp_password, 
		
		maxUploadSize, 
		firmasWeb
		}
	
	private String cd;
	private String prefijo;
	private Esquema esquema;
	
	private Boolean permitirUsuariosConcurrentes=true;
	public Coleccion<String> idiomas=new Coleccion<String>();
	
	Coleccion<ModoDetalleDef> detalles;
	Coleccion<INivelDef> niveles=null;
	public Coleccion<ControlDef> controles=new Coleccion<ControlDef>();
	
	public StringBuffer erroresArranque=new StringBuffer();
	private MarcaBaseDatos marcaBaseDatos=null;
	public boolean variablesFortuny=true;
	
	public Logger log; 
	public void sacaError(Object obj){
		this.log.error(obj);
	}
	private Coleccion<Usuario> usuarios=new Coleccion<Usuario>(false);
	private DataSource dataSource;
	private FechaGotta fechaArranque;

	private Trabajos trabajos;
	
	void añadeAListaUsuarios(Usuario usr){
		this.usuarios.put(usr.hashCode()+"", usr);
		}
	
 	public Aplicacion(String cd, DataSource dataSource, String prefijo) {
 		this.fechaArranque=new FechaGotta();
		this.cd = cd;
		this.dataSource=dataSource;
		if (prefijo == null)
			prefijo = Constantes.CAD_VACIA;
		this.prefijo = prefijo;
		log=Util.getLogger(cd);
		this.marcaBaseDatos=this.getConexion().sacaMarcaBD();
		}

	public Conexion getConexion(){
		return new Conexion(this);
	}
	void arranca() throws ErrorGotta {
		this.leerEsquema();
		leerIdiomas();
		leerDatosConfiguracion();
		leerDetalles();
		
		this.leerNiveles();
		if (this.trabajos==null)
			inicializaTrabajos(3);
		}
	public void inicializaTrabajos(int segundos)  {
		if (this.trabajos!=null) trabajos.detenTrabajos();
		
		this.trabajos=new Trabajos(this);
		this.trabajos.cargaTrabajos(segundos);
		}

	public String getCd(){return this.cd;}
	public String getPrefijo(){return this.prefijo;}
	public Esquema getEsquema(){return this.esquema;}
		
	public ModoDetalleDef getModoDetalleDef(String cdModoDetalle){
		if (cdModoDetalle!=null)
			return this.detalles.get(cdModoDetalle);
		return null;
		}
	public INivelDef getNivelDef(String nivel) throws ErrorDiccionario{
		if(this.niveles==null)
			this.leerNiveles();
		return this.niveles.get(nivel);
		}

	public Control getControl(Usuario usu, String id) throws ErrorGotta {
		String idOriginal=id;
		
		if (id.startsWith("control"))
			id=id.substring(7);
		
		if (idOriginal.startsWith("control_____")){//creado dinámicamente para la basura del mantenimineto de tablas que ordenó Ayda
			return Control.getInstancia(this.controles.get(idOriginal), usu);	
			}
		return Control.getInstancia(this.controles.get(id), usu);
		}
	public ControlDef getControlDef(String id) {
		if (id.startsWith("control"))
			id=id.substring(7);
		
		return this.controles.get(id);
		}
	
	private void leerEsquema() throws ErrorDiccionario{
		this.esquema = new Esquema(this);
		}
	public void leerDetalles() throws ErrorDiccionario{
		detalles = new Coleccion<ModoDetalleDef>();
		Conexion con = this.getConexion();
		Filas rs = GestorMetaDatos.leeEXP_ModosDetalle(con, this.esquema);
		
		String cd_mododetalle, cd_tabla, letras;
		for (Fila fila:rs){
			cd_mododetalle = fila.gets("cd_mododetalle");		
			cd_tabla 	   = fila.gets("cd_tabla");
			letras 		   = fila.gets("letras");
			
			ModoDetalleDef modoDetalle=new ModoDetalleDef(this.cd, cd_mododetalle, letras, cd_tabla);		
			this.detalles.put(cd_mododetalle, modoDetalle);
			}
		}
	public void leerNiveles() throws ErrorDiccionario{
		this.niveles = new Coleccion<INivelDef>(false);
		Conexion con = this.getConexion();
		Filas leeEXP_NivelesSQL = GestorMetaDatos.leeEXP_NivelesSQL(con, this.esquema);
		for (Fila h:GestorMetaDatos.leeEXP_Niveles(con, this.esquema)) {
			String cd_nivel = h.gets("cd_nivel").trim();
			String sqlparcial = h.gets("sqlparcial");
			
			Filas esteSQL = leeEXP_NivelesSQL.filtrar("cd_nivel",cd_nivel);
			for(Fila f:esteSQL)
				sqlparcial+=f.gets("sqlparcial");
			
			String parametros = h.gets("params");
			INivelDef nivel;
			if (Util.esNivelJython(sqlparcial)){
				try {
					nivel=Util.creaNivelJython(cd_nivel, sqlparcial, parametros, this);
					} 
				catch (ErrorGotta e) {
					nivel=new NivelDef(cd_nivel, sqlparcial, parametros);
					}
				}
			else
				nivel=new NivelDef(cd_nivel, sqlparcial, parametros);
			
			this.niveles.put(cd_nivel, nivel);
			}
		
		//el de las listas y árboles vacíos
		String sqlNivelVacio=" select 1 as cd,"+
		  "		'No se ha indicado nivel'  as ds,"+
		  "		'No se ha indicado nivel'  as Columna,"+
		  "		'' as Icono,"+
		  "		'p' as ModoDetalle,"+
		  "		'' as ModoDetalleSiguiente"+
		  " from "+this.getPrefijo()+"dic_configuracion "+
		  " where nombre='icoWeb'";
		NivelDef nivelVacio=new NivelDef("nivelVacio", sqlNivelVacio, "");
		nivelVacio.esDeSistema=true;
		this.niveles.put(nivelVacio.getCd(), nivelVacio);
		
		this.generaNivelesDiseño();
		this.getTraduccionesNiveles();
		
	}
	private void getTraduccionesNiveles() throws ErrorDiccionario {
		//traducciones de las columnas
		if (esquema.existeTabla("LEN_Niveles")){
			Filas filas=GestorMetaDatos.leeLEN_Niveles(this.getConexion(), esquema);
			for (Fila fila:filas){
				String idioma=fila.gets("CD_Idioma");
				String columnas=Util.noVacía( fila.get("columnas") );
				if (columnas!=null){
					INivelDef nn=this.niveles.get(fila.gets("CD_Nivel"));
					nn.anhadeCabecera(idioma, Util.split(columnas, Constantes.PIPE));
					}
				}
			}
	}

	private void generaNivelesDiseño() throws ErrorArrancandoAplicacion{
		if (!this.niveles.containsKey("listaTablas")) {
			String sqlListaTablas=" select 1 as \"_Expandir\", Nombre as cd,"+
			  "		Nombre  as ds,"+
			  "		'./fijo/disenhador.tabla' as Icono,"+
			  "		'z\\\\p' as ModoDetalle,"+
			  "		'listaColsCampos' as ModoDetalleSiguiente"+
			  " from "+this.getPrefijo()+"dic_tablas "+
			  " where nombre = ?";

			String sqlListaColumnas=" select "+
			this.getConexion().fnConcat("?", "nombreColumna")+" as cd,"+
				"	nombreColumna as ds,"+
				"	'./fijo/disenhador.columna' as Icono,"+
				"	'c' as ModoDetalle,"+
				"	'' as ModoDetalleSiguiente "+
				" from "+
				"	"+this.getPrefijo()+"dic_columnas "+
				" where "+
				"	NombreTabla=? "+
				" UNION "+
				" select TablaReferencia as cd,"+
				"	TablaReferencia as ds,"+
				"	'./fijo/disenhador.campo' as Icono,"+
				this.getConexion().fnConcat("'z'", "?", "campoPrincipal", "'.'", "'\\p'")+" as ModoDetalle,"+
				"	'listaColsCampos' as ModoDetalleSiguiente "+
				" from "+
				"	"+this.getPrefijo()+"dic_referencias"+
				" where "+
				"	TablaPrincipal=? "+
				" ORDER BY ds"
				;
			
			NivelDef listaTablas=new NivelDef("listaTablas", sqlListaTablas, "String t");
			listaTablas.esDeSistema=true;
			NivelDef listaColsCampos=new NivelDef("listaColsCampos", sqlListaColumnas, "String z, String p, String z, String p");
			listaColsCampos.esDeSistema=true;
			
			this.niveles.put(listaTablas.getCd(), listaTablas);
			this.niveles.put(listaColsCampos.getCd(), listaColsCampos);
			
			//////////////////////////
			String sqlGT_EXP_Menu=""+
			" select CD_Boton as cd,"+ 
			  "	   texto as ds,"+
			  "	   titulo as \"_ds2_\","+
			  "	   imagen as icono,"+
			  "	   0 as \"_activar\","+
			  "	   (select max('gt.EXP_Menu') from exp_menu m2 where m2.cd_menuPadre="+getConexion().fnCastAsChar("m.CD_Boton")+") as mododetallesiguiente,"+
			  "	   'k' as ModoDetalle,"+
			  "    1 as \"_expandir\"     "+
			  "	FROM"+
			  "	    "+this.getPrefijo()+"exp_menu m"+
			  "	WHERE"+
			  "	       tipo=?"+
			  "	       AND cd_menuPadre=? "+
			  "		   AND ? is null"+
			  " UNION "+
			  " select CD_Boton as cd,"+ 
			  "	   texto as ds,"+
			  "	   titulo as \"_ds2_\","+
			  "	   imagen as icono,"+
			  "	   0 as \"_activar\","+
			  "	   (select max('gt.EXP_Menu') from exp_menu m2 where m2.cd_menuPadre="+getConexion().fnCastAsChar("m.CD_Boton")+") as mododetallesiguiente,"+
			  "	   'k' as ModoDetalle,"+
			  "    1 as \"_expandir\"     "+
			  "	FROM"+
			  "	    "+this.getPrefijo()+"exp_menu m"+
			  "	WHERE"+
			  "	       tipo=?"+
			  "	       AND cd_menuPadre=? "+
			  "		   AND CD_Boton != ? "+
			  " UNION "+
			  " SELECT CD_Boton as cd,"+ 
			  "	   texto as ds,"+
			  "	   titulo as \"_ds2_\","+
			  "	   imagen as icono,"+
			  "	   1 as \"_activar\","+
			  "	   (select max('gt.EXP_Menu') from exp_menu m2 where m2.cd_menuPadre="+getConexion().fnCastAsChar("m.CD_Boton")+") as mododetallesiguiente,"+
			  "	   'k' as ModoDetalle,"+
			  "    1 as \"_expandir\"     "+
			  "	FROM "+
			  "	    "+this.getPrefijo()+"exp_menu m"+
			  "	WHERE"+
			  "	       tipo=?"+
			  "	       AND cd_menuPadre=?"+
			  "		   AND CD_Boton = ? "+
			  " ORDER BY 1";
			  
			NivelDef exp_menu=new NivelDef("gt.EXP_Menu", sqlGT_EXP_Menu, "String t, String k, Integer b, String t, String k, Integer b, String t, String k, Integer b");
			exp_menu.esDeSistema=true;
			this.niveles.put(exp_menu.getCd(), exp_menu);
			}
		}
	private void leerIdiomas(){
 		Coleccion<String> ret=new Coleccion<String>();
		try {
			if (!esquema.existeTabla("LEN_IDIOMAS")){
				this.añadeMSG("-1", "No hay idiomas adicionales, ni siquiera existe la tabla LEN_Idiomas", TipoMensajeGotta.idioma);
				throw new ErrorDiccionario("LEN_Idiomas no está en el diccionario");
				}
			Filas filas=GestorMetaDatos.sacaListaIdiomas(this.getConexion(), this.esquema);
			
			if (filas==null)
				throw new ErrorDiccionario("para capturar más abajo");
			
			this.añadeMSG("-1", filas.size()+" idiomas detectados en la tabla LEN_Idiomas", TipoMensajeGotta.idioma);
			for (Fila f: filas)
				ret.put(f.gets("CD_Idioma"), f.gets("DS_Idioma") );
			} 
		catch (ErrorDiccionario e) {
			ret.put("es", "Español");
			}
 			
		this.idiomas=ret;
 		}
	
	public transient Coleccion<Camino> caminos=new Coleccion<Camino>();
	public Camino getCamino(Long idCamino){
		return caminos.get("x"+idCamino);
		}
	public Camino getCamino(String idCamino){
		String temp=idCamino;
		 
		if (!temp.startsWith("x"))
			temp="x"+temp;
		
		return caminos.get(temp);
		}
	public Coleccion<Camino> generaListaCaminos() throws ErrorTablaNoExiste  {
		Filas filas=GestorMetaDatos.leeListaCaminos(this.getConexion(), this.getEsquema());
		
		Camino cam; Long cd_camino; String ds_camino;
		String id;
		for (Fila fila: filas){
			cd_camino=new Long(fila.gets("cd_camino"));
			ds_camino=fila.gets("ds_camino");
			
			id="x"+fila.gets("cd_camino");
			
			if (this.caminos.containsKey(id))
				cam=this.caminos.get(id);
			else {
				cam=new Camino(cd_camino, ds_camino);
				this.caminos.put(id, cam);
				}
			cam.tt=fila.get("tablaTramites")!=null;
			cam.numTramites=new Integer(fila.gets("cuenta"));
			}
		return this.caminos;
	}
	public void recalculaCamino(Usuario usr, Long idcamino)  {
		if (getCamino(idcamino) == null) {
			Camino cam=new Camino(usr, idcamino ) ;
			caminos.put("x"+idcamino, cam);
			}
		getCamino(idcamino).hayQueCalcular();
		}
	
	public Coleccion<String> configuracion;
	public void establecerConfiguracionPorDefecto() {
		configuracion = new Coleccion<String>();
		
		setDatoConfig(DIC_Configuracion.icoWeb, "./Aplicaciones/"+this.cd+"/ico/");
		setDatoConfig(DIC_Configuracion.dotWeb, "./Aplicaciones/"+this.cd+"/dot/");
		setDatoConfig(DIC_Configuracion.rptWeb, "./Aplicaciones/"+this.cd+"/rpt/");
		setDatoConfig(DIC_Configuracion.firmasWeb, null);
		
		setDatoConfig(DIC_Configuracion.metodoValidacion, "ValidacionPorUsuContrasenha");
		}
	public IValidacion getMetodoValidacion(){
		String tipoValidacion=this.getDatoConfig(DIC_Configuracion.metodoValidacion);
		
		IValidacion iv;
		try {
			Class<?> cls = Class.forName("es.burke.gotta."+tipoValidacion);
			Class<? extends IValidacion> IV=cls.asSubclass(IValidacion.class);
			iv = IV.newInstance();
			} 
		catch (ClassNotFoundException e) {
			throw new ErrorArrancandoAplicacion(e.getMessage());
			} 
		catch (InstantiationException e) {
			throw new ErrorArrancandoAplicacion(e.getMessage());
			} 
		catch (IllegalAccessException e) {
			throw new ErrorArrancandoAplicacion(e.getMessage());
			}
		return iv;
		}
	private Boolean esVerdadero(String valor){
		return ( valor.equals("true")  || valor.equals("1") );
		}
	public void leerDatosConfiguracion() throws ErrorDiccionario {
		establecerConfiguracionPorDefecto();
		Conexion conn = this.getConexion();
		
		for (Fila conf:GestorMetaDatos.leeDIC_Configuracion(conn, this.esquema)) {
			String nombre = conf.gets("nombre");
			String valor = conf.gets("valor");
			
			if (nombre.equalsIgnoreCase("variablesSimples"))
				this.variablesFortuny= esVerdadero(valor);
			else if (nombre.equalsIgnoreCase("UsuariosConcurrentes"))
				this.permitirUsuariosConcurrentes=esVerdadero(valor);
			else
				setDatoConfig(nombre, valor);
			}
		}
	public String getDatoConfig(String clave) {
		try {
			if (configuracion==null) leerDatosConfiguracion();
			} 
		catch (ErrorDiccionario e) {
			//pass
			}
		return configuracion.get(clave.toLowerCase());	
		}
	public String getDatoConfig(DIC_Configuracion clave) {
		return getDatoConfig(clave.toString());	
		}
	public void setDatoConfig(String clave, String dato){
		configuracion.put(clave.toLowerCase(), dato);
		}
	public void setDatoConfig(DIC_Configuracion clave, String dato){
		setDatoConfig(clave.toString(), dato);
		}
	
	public void verificaAccesoConcurrente(Usuario nuevoUsuario){
		if (this.permitirUsuariosConcurrentes)
			return ;
		String login, apli;
		for (String key: Util.sesiones.getOrden()){
			String loginSesion=Util.sesiones.get(key);
			int i=loginSesion.indexOf("@");
			login=loginSesion.substring(0, i); apli=loginSesion.substring(i+1);
			
			if (apli.equalsIgnoreCase(this.getCd())){
				Usuario usr=this.usuarios.get(login);
				if (usr!=null && !usr.sesionFinalizada) { //usuario vivo
					if (usr==nuevoUsuario){
						continue;
						}
					else if (usr.getLogin().equalsIgnoreCase(nuevoUsuario.getLogin())){
						//para controlar sesiones colgadas, si la IP coincide y el navegador también nos quedamos con la nueva y elminamos la sesión antigua
						Boolean coincideIP =usr.varios.get("IPCliente").equals( nuevoUsuario.varios.get("IPCliente") );
						Boolean coincideNavegador =usr.varios.get("Navegador").equals( nuevoUsuario.varios.get("Navegador") );
						
						if (coincideIP && coincideNavegador){
							usr.finaliza();
							return ;
							}
						throw new ErrorUsuarioNoValido("Esta cuenta está siendo utilizada para entrar en la aplicación desde la IP "+usr.varios.get("IPCliente")+". \nSin embargo, es posible que haya sesiones de las que no se haya salido.");
						}
					}
				}
			}
		}
	
	public boolean getSegSinLogin() {
		 String datoConfig = this.getDatoConfig(DIC_Configuracion.segSinLogin);
		return datoConfig!=null && datoConfig.equals("1");	
		 }
	public boolean getUsarMilis() {
		 String datoConfig = this.getDatoConfig(DIC_Configuracion.usarMilis);
		return datoConfig!=null && datoConfig.equals("1");	
		 }
	
	public void println(Object texto){
		if (log!=null)
			log.debug(texto);
		else
			System.out.print(texto);
		}
 	
	public void arreglaRutasConfiguracion(){
		String icoWeb=this.getDatoConfig(DIC_Configuracion.icoWeb);
		icoWeb=Util.rutaWebRelativaOAbsoluta(this, icoWeb);
		if (!icoWeb.endsWith("/"))
			icoWeb+="/";
		
		this.setDatoConfig(DIC_Configuracion.icoWeb, icoWeb);
		}
	
	private String urlAplicacion=null; //http://localhost:8080/gotta
	public void setUrlAplicacion(HttpServletRequest req){
		String url = req.getRequestURL().toString();
		String ctx = req.getContextPath();
		int pos = url.lastIndexOf(ctx);
		urlAplicacion=url.substring(0, pos+ctx.length())+"/";
		
		this.arreglaRutasConfiguracion();
		}
	
	public String getUrlAplicacion() {
		return urlAplicacion;
		}
	public String getRutaFisicaAplicacion(){
		return PoolAplicaciones.dirInstalacion;
		}
	
	final int MAX_LOG=4000;
	private void limpiaLog(){
		while(this.SqlEjecutados.size()>MAX_LOG)
			this.SqlEjecutados.remove(0);
		}
	public TiempoSQL añadeMSG(String usr, String msg, TipoMensajeGotta tipo){
		limpiaLog();
		TiempoSQL t=new TiempoSQL(usr, msg, tipo);
		this.SqlEjecutados.add( t );
		return t;
		}
	public TiempoSQL añadeMSG(String usr, String msg, TipoMensajeGotta tipo, String objetoSQL){
		TiempoSQL t=añadeMSG(usr, msg, tipo);
		t.objetoSQL=objetoSQL;
		return t;
		}
	public TiempoSQL añadeMSG(String usr, String msg, TipoMensajeGotta tipo, Long t1, Long t2){
		TiempoSQL t=añadeMSG(usr, msg, tipo);
		if (t1!=null) t.t1=t1; 
		if (t2!=null) t.t2=t2;
		return t;
		}
	public TiempoSQL añadeSQL(String usr, String sql, List<Object> parametros, Long t1, Long t2, String objetoSQL, int numFilas, TipoMensajeGotta tipo){
		limpiaLog();
		this.log.debug(sql+"  "+parametros);
		TiempoSQL t = new TiempoSQL(usr, sql, parametros, t1, t2, objetoSQL, numFilas, tipo);
		this.SqlEjecutados.add( t );
		return t;
		}
	
	@SuppressWarnings("unchecked")
	public JSONArray getMensajesMonitor(String usuario) throws JSONException{
		JSONArray ret = new JSONArray();
		ArrayList<TiempoSQL>  foto = (ArrayList<TiempoSQL>) SqlEjecutados.clone();
		for (TiempoSQL t:foto){
			if (t==null)
				continue;
			if (t.usuario.equalsIgnoreCase(usuario) || usuario==null)
				ret.put(t.JSON());
			}
		return ret;
	}
	public void vaciaMonitor() {
		this.SqlEjecutados.clear();
		
		for (String key: Util.sesiones.getOrden()){
			String loginSesion=Util.sesiones.get(key);
			int i=loginSesion.indexOf("@");
			String login=loginSesion.substring(0, i); String apli=loginSesion.substring(i+1);
			
			if (apli.equalsIgnoreCase(this.getCd())){
				Usuario usr=this.usuarios.get(login);
				if (usr!=null && usr.sesionFinalizada==true) {
					this.usuarios.remove(login);
					usr=null;
					}
				}
			}
		}
	private JSONArray listaUsuarios(String idSesionActiva) throws JSONException{
		JSONArray lista=new JSONArray();
		lista.put(Usuario.xJSON("(gotta)"));
		
		String login, apli;
		for (String key: Util.sesiones.getOrden()){
			String loginSesion=Util.sesiones.get(key);
			int i=loginSesion.indexOf("@");
			login=loginSesion.substring(0, i); apli=loginSesion.substring(i+1);
			
			if (apli.equalsIgnoreCase(this.getCd())){
				Usuario usr=this.usuarios.get(login);
				if (usr!=null) 
					lista.put(usr.JSON( usr.getIdSesion().equals(idSesionActiva) ));
				else 
					System.out.println(login +" -ya ha salido de la apli");
				}
			}
		
		ArrayList<String> listaIdsUsuarios=new ArrayList<String>();
		for (TiempoSQL t : this.SqlEjecutados){
			if (t!=null && !listaIdsUsuarios.contains(t.usuario) )
				listaIdsUsuarios.add(t.usuario);
			}
		return lista;
		}
	public JSONObject JSON(String idSesionActiva) throws JSONException{
		JSONObject ret = new JSONObject()
			.put("cd", this.cd)
			.put("marcaBD", this.getMarcaBaseDatos().toString())
			.put("fechaArranque", this.fechaArranque)
			.put("listaUsuarios",listaUsuarios(idSesionActiva));
		
		return ret;
		}

	public Connection getDataSourceConnection() throws ErrorConexionPerdida {
		try {
			return this.dataSource.getConnection();
			}
		catch (SQLException e){
			//System.out.println("Error conectando a "+aplicacion+" ------------------------------------------------------\n"+e.getMessage());
			throw new ErrorConexionPerdida("Se ha perdido la conexión a la base de datos: "+e.getMessage());
			}
		}
	MarcaBaseDatos getMarcaBaseDatos() {
		return marcaBaseDatos;
	}
	
	transient PythonInterpreter interpPython=null;
	public HashSet<String> cacheJython = new HashSet<String>();
	public PythonInterpreter getJython(){
		if (this.interpPython==null){
			interpPython = DLLJython.nuevoJython(PoolAplicaciones.dirInstalacion, this.cd);
			}
		return interpPython;
		}

	public Usuario getUsuSistema() {
		Usuario usr=new Usuario();
		usr.aplicacion=this.getCd();
		return usr;
	}

}
class TiempoSQL {
	public String usuario;
	public Long t1; public FechaGotta f1;
	public Long t2;
	
	public String msg;
	
	public String objetoSQL;//para guardar el nombre del nivel
	public String sql;
	public String params;
	
	public Object numFilas;
	public TipoMensajeGotta tipo; 
	
	@Override
	public String toString(){
		return usuario+"   "+msg;
	}
	public JSONObject JSON() throws JSONException{
		String xret="";
		if (msg!=null && sql!=null)
			xret=msg+"#######"+sql;
		else if (msg!=null)
			xret=msg;
		else
			xret=sql;
		JSONObject ret = new JSONObject();
				ret.put("sql", xret).
				put("params", params).
				put("tiempo", t1!=null?t2-t1:"").
				put("hora", f1).
				put("numFilas", numFilas).
				put("objetoSQL", objetoSQL).
				put("tipo", this.tipo.toString());
		return ret;
		}
	public TiempoSQL(String usr, String sql, List<Object> parametros, Long t1, Long t2, String objetoSQL, int numFilas, TipoMensajeGotta tipo){
		this.usuario=usr;
		this.sql=sql;
		this.params=this.paramComoTexto(parametros);
		
		this.t1=t1;
		this.t2=t2;
		if (t1!=null)
			this.f1=new FechaGotta(new Date(t1));
		
		this.objetoSQL=objetoSQL;
		this.numFilas=numFilas>-1?numFilas:null;
		
		this.tipo=tipo;
		}
	public TiempoSQL(String usr, String msg, TipoMensajeGotta tipo){
		this.usuario=usr;
		this.tipo=tipo;
		
		
		this.msg=msg;
		}
	private String paramComoTexto(List<Object> parametros){
		if (parametros==null)
			return Constantes.CAD_VACIA;
		ArrayList<String> sb = new ArrayList<String>();
		for (Object o : parametros) {
			if (o==null)
				sb.add( "NULL");
			else
				sb.add(o.toString());
			}
		
		return Util.join(", ",sb).toString();
		}
}
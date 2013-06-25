package es.burke.gotta;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import es.burke.gotta.Constantes.TipoMensajeGotta;
import es.burke.gotta.dll.DLLGotta;

public class Usuario implements Serializable {
	private static final long serialVersionUID = -8833231173441936642L;
	//Datos fáciles de serializar
	private String usuGotta;
	private String password;
	
	public boolean disenho=false;
	public Motor.TDepuracion tipoDepuracion = Motor.TDepuracion.SIN_DEPURACION;
	public ArrayList<String> puntosDeParo= new ArrayList<String>();
	
	String aplicacion;
	private String idsesion;

	public transient boolean sesionInicializada=false; //semáforo para la llamada a putSesionAplicacion
	
	/* los nodos cargados por cada modoLista en PLANO */
	public HashMap<String, Coleccion<Nodo>> nodos = new HashMap<String, Coleccion<Nodo>>();
	
	Nodo nodoRaiz; //nodo raiz del arbol
	public transient Coleccion<ModoListaDef> vistas=null;
	transient Filas menuGeneral=null;
	
	public transient Variables variables;;
	
	private String dsUsuario=Constantes.CAD_VACIA;
	
	String tipoMenu=null; 
	
	transient public URL_Gotta nodoArranque=null; // ModoListaInicial y demás
	int maxElementos =-1; 
	
	private String idioma="es";
	public void cambiaIdioma(String cd_idioma) throws ErrorConexionPerdida, ErrorArrancandoAplicacion, ErrorGotta{
		GestorMetaDatos.actualizaIdiomaUsuario(this.getConexion(), getApli().getEsquema(), this.usuGotta, cd_idioma);
		
		this.reset();
		this.leerVistasUsuario();
		
		this.idioma=cd_idioma;
		this.añadeMSG("Idioma de la interfaz cambiado a '"+cd_idioma+"' ("+getApli().idiomas.get(cd_idioma)+")", TipoMensajeGotta.idioma);
		}
	public Object hayQueRefrescar=null;
	
	//Los chungos de serializar
	public Arboljson arbol=null;
	public Coleccion<Arboljson> arboles=null;
	
	transient private ArrayList<Motor> motores = null;
	
	transient public Coleccion<DLLGotta> productosGenerados; //informes generados en el último trámite
	transient public HashMap<String,DLLGotta> productosEnviados; //informes generados en el último trámite

	transient public ArrayList<SqlParam> SqlPendientes;
	private String mli=null ;
	
	public void ejecutaDML(SqlParam sqlparam) {
		if (SqlPendientes ==null)
			SqlPendientes=new ArrayList<SqlParam>();
		SqlPendientes.add(sqlparam);
		}
	public boolean transaccionEnCurso=false;

	public Coleccion<String> varios = new Coleccion<String>();//IPcliente, Navegador...
	public FechaGotta horaConexion;
	public FechaGotta horaUltimoAcceso;
	
	transient Conexion conexionPreferente;
	private boolean usuarioAnonimo=false;
	
	public void BeginTrans(){
		SqlPendientes=new ArrayList<SqlParam>();
		transaccionEnCurso=true;
		}
	public void RollbackTrans(){
		SqlPendientes=null;
		transaccionEnCurso=false;
		}
	public void CommitTrans() throws ErrorVolcandoDatos {
		boolean hayBicho=SqlPendientes.size()>0;
		try {
			getApli().println("COMMIT... ");
			Conexion con = getConexion(); //ok
			
			if (hayBicho) this.añadeMSG("Arrancamos transacción", TipoMensajeGotta.info);
			con.ejecutaLote(SqlPendientes);
			if (hayBicho) 
				this.añadeMSG("Transacción finalizada", TipoMensajeGotta.info);
			else
				this.añadeMSG("No hay que guardar ningún dato", TipoMensajeGotta.info);
				
			getApli().println("OK");
			RollbackTrans();
			} 
		catch (ErrorCampoNoExiste e) {
			throw new ErrorVolcandoDatos(e.getMessage());
			} 
		catch (ErrorConexionPerdida e) {
			throw new ErrorVolcandoDatos(e.getMessage());
			} 
		catch (ErrorArrancandoAplicacion e)	{
			throw new ErrorVolcandoDatos(e.getMessage());
			} 
		catch (ErrorFilaNoExiste e) {
			throw new ErrorVolcandoDatos(e.getMessage());
			} 
		catch (ErrorTiposNoCoinciden e) {
			throw new ErrorVolcandoDatos(e.getMessage());
			} 
	}
	public void ejecutaLoteSinCommit (Connection conn) throws ErrorVolcandoDatos{
		try {
			getApli().println("ejecutamos lote sin commit (trámites EJE)");
			
			Conexion con = getConexion(); //ok
			con.ejecutaLote(conn, SqlPendientes);
			} 
		catch (ErrorCampoNoExiste e) {
			throw new ErrorVolcandoDatos(e.getMessage());
			} 
		catch (ErrorFilaNoExiste e) {
			throw new ErrorVolcandoDatos(e.getMessage());
			} 
		catch (ErrorTiposNoCoinciden e) {
			throw new ErrorVolcandoDatos(e.getMessage());
			}
		}
	public Usuario() {
		/*Hace falta para des-serializar*/
		try {
			if (this.aplicacion!=null) //en el diccionario no se mete
				this.getApli().añadeAListaUsuarios(this);
			this.idsesion="-1"; //para que las filas del monitor no queden huérfanas
			} 
		catch (ErrorArrancandoAplicacion e) {
			/* el monitor no estará coordinado, por lo demás no pasa nada */
			}
		}
	
	private String validarUsuario(String usu, String pwd, Aplicacion apli, HttpServletRequest req) throws ErrorUsuarioNoValido{
		IValidacion validacion=apli.getMetodoValidacion();
		String ret;
		if (validacion.requiereUsuarioyContrasenha)
			ret=validacion.valida(apli, usu, pwd, req);
		else
			ret=validacion.valida(apli, null, null, req);
		return ret;
		}
	public Usuario(String usu, String pwd, Aplicacion apli, String sesion, HttpServletRequest req) throws ErrorDiccionario, ErrorConexionPerdida, ErrorUsuarioNoValido{
		variables=new Variables();
		this.usuGotta=usu;
		this.password = pwd;
		assert apli != null;
		this.aplicacion=apli.getCd();
		
		this.usuGotta=this.validarUsuario(usu, pwd, apli, req);
		if (this.usuGotta!=null) leerDatosUsuario(apli);
		this.usuarioAnonimo=this.usuGotta==null;
		
		this.idsesion=sesion;
		this.varios.put("IPCliente", req.getRemoteHost());
		this.varios.put("Navegador", req.getHeader("user-agent"));
		this.horaConexion= FechaGotta.fechaActual();
		
		this.arbol=null;//new Arboljson(this);
		
		setVariable("u", new Variable("u", Constantes.STRING, this.usuGotta));
		nodoRaiz=null;
		}
	public boolean esUsuarioAnonimo(){
		return this.usuarioAnonimo;
	}
	@Override
	public String toString()
		{return this.usuGotta+"@"+this.aplicacion;}
	
	public void leerVistasUsuario() throws ErrorGotta {
		if (this.menuGeneral==null || this.vistas==null) {
			this.vistas=new Coleccion<ModoListaDef>();
			Conexion con = this.getConexion(); //ok
			
			Aplicacion apli=this.getApli();
			Filas tempMenu=null;
			if (apli.niveles.containsKey("menuGeneral")) {
				INivelDef n=apli.niveles.get("menuGeneral");
				INivel nivel = n.obtenerNivel();
				ArrayList<Object> param = new ArrayList<Object>(1);
				for (int i=0; i<n.getColParams().size();i++)
					param.add(this.getLogin());
				
				try 
					{tempMenu=nivel.lookUp(con, param);} 
				catch (SQLException e) 
					{throw new ErrorDiccionario("Error al ejecutar el nivel menuGeneral: "+e.getMessage());}
				}
			else {
				this.getApli().erroresArranque.append("Error de acceso: no existe el nivel 'menuGeneral'");
				return;
				}
			
			String nombre, CD_Nivel;
			for (Fila fila:tempMenu){
				nombre= fila.gets("cd_modolista");
				if (nombre!=null && !nombre.equals(Constantes.CAD_VACIA)){
					CD_Nivel= fila.gets("cd_nivelinicial");
					if (CD_Nivel==null || CD_Nivel.equals(Constantes.CAD_VACIA)){
						CD_Nivel="nivelVacio";
						this.getApli().erroresArranque.append("Error leyendo el nivel 'menuGeneral' para sacar el menú y obtener las vistas de la aplicación: la columna CD_NivelInicial debe ir rellena (CD_ModoLista="+nombre+")");
						}
										
					INivelDef nivel=getApli().getNivelDef(CD_Nivel);
					if (nivel==null)						
						throw new ErrorGotta("La vista '"+nombre+"', leída desde el nivel 'menuGeneral', lleva un nivel inicial ('"+nivel+"') que no existe.");
						
					ModoListaDef modoLista=new ModoListaDef(getApli().getCd(), nombre, nivel);
					ModoDetalleDef modoDetalle= new ModoDetalleDef(getApli().getCd(), nombre);
					this.getApli().detalles.put(nombre, modoDetalle);
					
					this.vistas.put(nombre, modoLista);
					}
				}
			this.menuGeneral=tempMenu;
			this.leeTraduccionesMenus(this.menuGeneral, "global");
			}
		}
	private void leeTraduccionesMenus(Filas filas, String tipo) throws ErrorDiccionario, ErrorConexionPerdida, ErrorArrancandoAplicacion{
		//traducciones
		Filas traducciones=GestorMetaDatos.leeLEN_menu(this.getConexion(), this.getApli().getEsquema(), "menuglobal");
		if (traducciones==null) return;
		
		HashMap<String, Integer>indiceTraducciones=new HashMap<String, Integer>();
		for (int numFila=0; numFila<traducciones.size(); numFila++){
			Fila fila=traducciones.get(numFila);
			String idx=fila.gets("CD_Idioma")+Constantes.ESPACIO+fila.gets("CD_Boton");
			indiceTraducciones.put(idx, numFila);
			}
		
		for (String idioma:getApli().idiomas.claves){
			this.menuGeneral.addColumna("titulo_"+idioma, Constantes.STRING);
			this.menuGeneral.addColumna("texto_"+idioma, Constantes.STRING);
			this.menuGeneral.addColumna("imagen_"+idioma, Constantes.STRING);
			}
		
		for (Fila menu:filas){
			String cd_boton=menu.gets("CD_Boton");
			for (String idioma:getApli().idiomas.claves){
				String idx=idioma+Constantes.ESPACIO+cd_boton;
				if (indiceTraducciones.containsKey(idx)){
					int it=indiceTraducciones.get(idx);
					Fila ft=traducciones.get(it);
					
					asigna(menu, ft, "titulo_"+idioma);
					asigna(menu, ft, "texto_"+idioma);
					asigna(menu, ft, "imagen_"+idioma);
					}
				}
			}
		}
	private void asigna(Fila menu, Fila origen, String campo){
		String d=origen.gets(campo);
		if (d!=null && !d.equals(Constantes.CAD_VACIA))
			menu.__setitem__(campo, d);
	}
	private ArrayList<Motor> getMotores(){
		if (this.motores==null)
			this.motores=new ArrayList<Motor>();
		return this.motores;
		}
	public void setMotor (Motor m){
		ArrayList<Motor> _motores=this.getMotores();
		for (int i=0; i<_motores.size(); i++) {
			if (_motores.get(i).finalizado)
				_motores.remove(i);
			}
		_motores.add(m);
		}
	public void borraMotores() {
		this.getMotores().clear();
		}
	public void borraMotor(){
		ArrayList<Motor> _motores=this.getMotores();
		_motores.remove( _motores.size()-1 );
		}
	public Motor getMotor (){
		ArrayList<Motor> _motores=this.getMotores();
		if (_motores.size()==0)
			return null;
		return _motores.get( _motores.size()-1 );}
		
	public String getLogin(){
		return this.usuGotta;}
	public String getIdSesion()
		{return this.idsesion;}
	public void setIdSesion(String ids)
	{this.idsesion = ids;}
	public String getPassword()
		{return this.password;}
	
	public void añadeNodos(Nodo nodo, String ml) {
		Coleccion<Nodo> lista=new Coleccion<Nodo>();
		lista.put(nodo.cd, nodo);
		añadeNodos(lista, ml);
		}
	public void añadeNodos(Coleccion<Nodo> lista, String ml){
		if ( !this.nodos.containsKey(ml) )
			this.nodos.put(ml, new Coleccion<Nodo>() );
		Coleccion<Nodo> listaHijos=this.nodos.get(ml);
		for (Nodo nodo : lista.values()){
			Nodo nodoDest = listaHijos.get(nodo.cd);
			listaHijos.put(nodo.cd, nodo);
			if (nodoDest!=null)
				nodo.expandido=nodoDest.expandido;
			}
		}
	
	
	public void sacaError(Object obj){
		try {
			this.getApli().log.error(obj);
			} 
		catch (ErrorArrancandoAplicacion e) {
			//pass
			}
		}
	public Aplicacion getApli() throws ErrorArrancandoAplicacion{
		return PoolAplicaciones.sacar(this.aplicacion);
		}

	public void borrarInfoNodos(String ml){
		if (ml==null)
			return;
		if (nodos.containsKey(ml))
			this.nodos.remove(ml);
		}
	
	public void añadeMSG(String msg, TipoMensajeGotta tipo) {
		try {
			//anotamos en monitor
			this.getApli().añadeMSG(this.getIdSesion(), msg, tipo);
			} 
		catch (ErrorArrancandoAplicacion e1) {
			/*pass*/
			}
		}
	public void añadeMSG(String msg, TipoMensajeGotta tipo, Long t1) {
		try {
			//anotamos en monitor
			this.getApli().añadeMSG(this.getIdSesion(), msg, tipo, t1, new java.util.Date().getTime());
			} 
		catch (ErrorArrancandoAplicacion e1) {
			/*pass*/
			}
		}
	public void añadeMSG(String msg, TipoMensajeGotta tipo, String objetoSQL) {
		try {
			//anotamos en monitor
			this.getApli().añadeMSG(this.getIdSesion(), msg, tipo, objetoSQL);
			} 
		catch (ErrorArrancandoAplicacion e1) {
			/*pass*/
			}
		}
	public void añadeSQL(String sql, List<Object> parametros, Long t1, Long t2, String objetoSQL, int numFilas, TipoMensajeGotta tipo){
		this.getApli().añadeSQL(this.getIdSesion(), sql, parametros, t1, t2, objetoSQL, numFilas, tipo);
		}
	
 	public ModoDetalleDef getModoDetalleDef(String cd) throws ErrorArrancandoAplicacion{
		return this.getApli().getModoDetalleDef(cd);
		}
	public ModoDetalle getModoDetalle(String cd) throws ErrorArrancandoAplicacion{
		ModoDetalleDef mdd=this.getApli().getModoDetalleDef(cd);
		return new ModoDetalle(this, mdd);
		}
	
	transient Coleccion<Filas> menus=null;//http://buscon.rae.es/dpdI/SrvltGUIBusDPD?clave=menu
	public Filas getMenuBBB(String detalle, String vista){
		String clave=detalle+";"+vista;
		
		if (this.menus==null)
			this.menus=new Coleccion<Filas>();
		
		if (this.menus.get(clave)==null) {
			Filas temp=GestorMetaDatos.leeEXP_menuDetalle(this,detalle,vista);
			this.menus.put(clave, temp);
			}
		return menus.get(clave);
		}

	public Variable setVariable(String clave, String valor){
		Variable ret = new Variable(clave, Constantes.STRING, valor);
		this.setVariable(clave, ret);
		return ret;
		}

	public void setVariable(String clave, Variable var){
		if (clave.startsWith("@") && clave.length()>1)
			clave=clave.substring(1);
		this.variables.put(clave, var);}
	public Variable getVariable(String clave){
		if (clave.startsWith("@") && clave.length()>1)
			clave=clave.substring(1);
		return this.variables.get(clave);
		}
	
	public void leerDatosUsuario(Aplicacion apli) throws ErrorDiccionario, ErrorConexionPerdida {
		Conexion con = new Conexion(apli);
		Filas filas = GestorMetaDatos.leeDatosUsuario(con, usuGotta);
		
		this.dsUsuario=usuGotta; //por defecto
		
		if (filas.size()>0){
			Fila fila=filas.get(0);
			this.usuGotta=	fila.gets("CD_Usuario");
			this.dsUsuario= fila.gets("DS_Usuario");
			
			this.permisoDepuracion=Util.existeyVale(fila, "permisoDepuracion", false);
			this.permisoDisenho=Util.existeyVale(fila, "permisoDisenho", false);
			this.permisoMonitorSQL=Util.existeyVale(fila, "permisoMonitorSQL", false);
			this.permisoRearrancar=Util.existeyVale(fila, "permisoRearrancar", false);
			
			this.idioma=fila.containsKey("CD_Idioma")?fila.gets("CD_Idioma"):"es";
			this.idioma=this.idioma.toLowerCase();
			}
		else{
			this.permisoDepuracion=false;
			this.permisoDisenho=false;
			this.permisoMonitorSQL=false;
			this.permisoRearrancar=false;
			this.idioma="ES";
			}
		this.vistas=null;
		setVariable("u", new Variable("u", Constantes.STRING, usuGotta));
		//datos de configuracion por usuario:
		Filas app_dic_config=GestorMetaDatos.leeAPP_DIC_Configuracion(con, apli.getEsquema(), usuGotta);
		for (Fila f : app_dic_config) {
			String nombre=f.gets("nombre");
			String valor=f.gets("valor");
			
			if (nombre.equalsIgnoreCase("mli"))
				this.mli=valor;
			else if (nombre.equalsIgnoreCase("maxElementos"))
				this.maxElementos=Integer.parseInt( valor );
			else if (nombre.equalsIgnoreCase("tipoMenu"))
				this.tipoMenu=valor;
			}
		}
		
	public String getDsUsuario()
		{return this.dsUsuario;}

	public Conexion getConexion() throws ErrorConexionPerdida {
		if (this.conexionPreferente!=null)
			return this.conexionPreferente;
		try {
			return new Conexion( getApli(), this.getIdSesion() );
			} 
		catch (ErrorArrancandoAplicacion e) {
			throw new ErrorConexionPerdida(e.getMessage());
			}
		}
	
	public Conexion getConexion(int cuantos) throws ErrorArrancandoAplicacion{
		if (this.conexionPreferente!=null)
			return this.conexionPreferente;
		return new Conexion( getApli(), this.getIdSesion(), cuantos );
		}
	
	public boolean tieneVista(String vista) {
		return vistas.containsKey(vista);
		}
	
	public boolean permisoDisenho=false;
	public boolean permisoDepuracion=false;
	public boolean permisoMonitorSQL=false;
	public boolean permisoRearrancar=false;
	public boolean tieneAutoexec=true;
	public boolean sesionFinalizada=false;
	
	public Filas getAutoexec() throws ErrorConexionPerdida, ErrorDiccionario, ErrorArrancandoAplicacion {
		if (!tieneAutoexec)
			return null;
		Conexion conex = this.getConexion();
		Aplicacion apliDef=PoolAplicaciones.sacar(aplicacion);
		Filas autoexec = GestorMetaDatos.leeTramiteEspecial(conex, "Autoexec", apliDef.getEsquema());
		this.tieneAutoexec=autoexec!=null; 
		return autoexec;
	}

	public String getIdioma() 
		{return idioma;}
	public void setIdioma(String idioma) 
		{this.idioma = idioma;}
	
	public ControlDef getControl(NodoActivo na, String numControl) throws ErrorArrancandoAplicacion, ErrorGotta{
		if (na==null || na.md==null) {
			for (ModoDetalleDef xmd: this.getApli().detalles.values()){
				if (xmd.getControles()!=null && xmd.getControles().containsKey(numControl))
					return xmd.getControl(numControl);
				}
			}
		else {
			ControlDef ret=null;
			if (na.md!=null)
				 ret=na.md.getControl(numControl);
			
			if (ret!=null)
				return ret;
			return na.ml.md.getControl(numControl);
			}
		return null;
		}
	///////////////////
	public void borrarDeSesion(HttpServletRequest req){
		HttpSession sesion=req.getSession();
		String aplicacion=req.getParameter("aplicacion");
		assert aplicacion!=null;
		assert aplicacion!=Constantes.CAD_VACIA;
		
		sesion.setAttribute("usuario"+aplicacion,new ErrorSesionCerrada("Sesión cerrada"));
		sesion.setAttribute("errorLogin", "");
//		Util.deleteSesionAplicacion(this.hashCode()+"");
		}
	public void aSesion(HttpServletRequest req){
		HttpSession sesion=req.getSession();
		String apli=req.getParameter("aplicacion");
		if (apli==null)
			apli=req.getAttribute("aplicacion").toString();
		if (apli==null || apli.equals(Constantes.CAD_VACIA) )
			throw new AssertionError("La sesión ha perdido datos");
		
		sesion.setAttribute("usuario"+apli, this);
		}
	public static Usuario desdeSesion(HttpServletRequest req, String aplicacion, HttpSession sesion) throws ErrorConexionPerdida, ErrorArrancandoAplicacion {
		Usuario usuRet;
		if (aplicacion==null || aplicacion.equals(Constantes.CAD_VACIA) )
			throw new ErrorConexionPerdida("La sesión ha perdido datos");
		
		Object ob = sesion.getAttribute("usuario"+aplicacion);
		if (ob!=null && ob instanceof ErrorConexionPerdida)
			throw (ErrorConexionPerdida)ob;
		if (ob!=null && ob instanceof Exception)
			throw new ErrorConexionPerdida(ob.toString());
		if (ob!=null) {
			try{
				usuRet=(Usuario)ob;
				}
			catch (ClassCastException e){
				throw new ErrorConexionPerdida("La sesión ha perdido datos por cambios de la aplicación");
				}
			
			if (usuRet.variables==null)
				usuRet.variables=new Variables();
			usuRet.horaUltimoAcceso=FechaGotta.fechaActual();
			
			if (!usuRet.sesionInicializada){
				usuRet.getApli().verificaAccesoConcurrente(usuRet);
				
				usuRet.sesionInicializada=true;
				Util.putSesionAplicacionYDemás(usuRet);
				if (usuRet.getApli().getUrlAplicacion()==null)
					usuRet.getApli().setUrlAplicacion(req);
				}
			
			return usuRet;
			
			}
		
		if (aplicacion.endsWith(" ")){
			String cd_aplicacion = aplicacion.substring(0,aplicacion.length()-1);
			Usuario usuAClonar = (Usuario)sesion.getAttribute("usuario"+cd_aplicacion);
			if (usuAClonar!=null) {
				usuRet = usuAClonar.clonarUsuario(URL_Gotta.crea(req));
				
				usuRet.aSesion(req);
				Util.putSesionAplicacionYDemás(usuRet);
				
				if (usuRet.getApli().getUrlAplicacion()==null)
					usuRet.getApli().setUrlAplicacion(req);
				return usuRet;
				}
			}
		usuRet=desdeCookies(req, aplicacion);
		if(usuRet!=null){
			return usuRet;
			}
		Enumeration<?> it = sesion.getAttributeNames();
		while (it.hasMoreElements() ){
			String siguienteClave=it.nextElement().toString();
			Object siguienteAtributo = sesion.getAttribute(siguienteClave); 
			if(siguienteAtributo instanceof Usuario){
				Usuario usu = (Usuario)siguienteAtributo;
				try{
					return nuevoUsuario(req, usu, aplicacion);
					}
				catch(Exception e){
					System.out.println(e);
					}
				}
			}
		throw new ErrorConexionPerdida("");
	}
	
	@SuppressWarnings("unchecked")
	private Usuario clonarUsuario(URL_Gotta url) {
		Usuario ret = new Usuario();
		ret.tieneAutoexec=this.tieneAutoexec;
		ret.dsUsuario=this.dsUsuario;
		ret.usuGotta=this.usuGotta;
		ret.password=this.password;
		ret.aplicacion=this.aplicacion;

		ret.arbol=null;//new Arboljson(ret);
//		ret.arbol.ml=this.arbol.ml;
		ret.mli=this.mli;
		ret.disenho=false;
		ret.idioma=this.idioma;
		
		ret.varios= (Coleccion<String>)this.varios.clone();
		
		ret.permisoDisenho=false;
		ret.permisoDepuracion=false;
		ret.permisoMonitorSQL=false;
		ret.permisoRearrancar=false;
		ret.dsUsuario=this.dsUsuario;
		ret.vistas=this.vistas;
		ret.menus=this.menus;
		ret.menuGeneral=this.menuGeneral;
		ret.variables=new Variables();
		for (String v:this.variables){
			Variable vv=this.variables.get(v);
			ret.variables.put(v, new Variable(v,vv.getTipo(),vv.getValor()));
			}
		ret.nodoArranque=url;
		ret.idsesion=""+this.hashCode();
		return ret;
	}
	private static Usuario desdeCookies(HttpServletRequest request, String aplicacion) throws ErrorUsuarioNoValido {
		//emc 25/8/2008 miramos a ver si vienen cookies con apli, usu y contraseña
		if (request.getCookies()!=null && request.getCookies().length>1){
			Cookie[] galletas=request.getCookies();
			
			String usuEmbrollado=null, pswEmbrollada=null;
			for (Cookie g : galletas){
				if (g.getName().startsWith("usu"+aplicacion))
					usuEmbrollado=g.getValue();
				if (g.getName().startsWith("pws"+aplicacion))
					pswEmbrollada=g.getValue();
				}
			
			if (usuEmbrollado!=null && pswEmbrollada!=null ){
				if (usuEmbrollado.equals("null"))//se lo meto para anular la cookie cuando le dan a "no me recuerdes"
					return null;
				String usu=Util.desembrollar(usuEmbrollado);
				String psw=Util.desembrollar(pswEmbrollada);
				if (usu.equals(Constantes.CAD_VACIA))
					return null;
				try {
					return nuevoUsuario(request, usu, psw, aplicacion );
					} 
				catch (ErrorGotta e) {
					throw new ErrorUsuarioNoValido(e.getMessage());
					}
				}
			}
		return null;
	}

	public void finalize(HttpServletRequest req) {
		this.borrarDeSesion(req);
		this.finaliza();
		this.añadeMSG("Fin de la sesión", TipoMensajeGotta.logout);
		this.getApli().añadeMSG("-1", "Fin de la sesión de '"+this.usuGotta+"'", TipoMensajeGotta.logout);
		this.horaUltimoAcceso=FechaGotta.fechaActual();
		}
	protected void finaliza(){
		//borramos todo lo borrable:
		this.puntosDeParo=null;

		this.nodos=null;
		this.vistas=null;

		this.productosGenerados=null; //informes generados en el último trámite
		this.productosEnviados=null; //informes generados en el último trámite

		this.SqlPendientes=null;

		this.motores=null;
		this.reset();
		
		this.conexionPreferente=null;
		this.nodoRaiz=null;
		
		this.variables=null;		
		this.sesionFinalizada=true;
		
		System.gc();
		System.out.println(this.getLogin() + " -- FINALIZADO");
		}
	
	public boolean verificaHoraUltimaConexion() {
		int maxHorasInactividad=2;
		FechaGotta ahora=FechaGotta.fechaActual();
		if (this.horaUltimoAcceso==null){
			this.finaliza();
			return false;
			}
		
		long diff=ahora.getTimeInMillis()-this.horaUltimoAcceso.getTimeInMillis();
		long horas=diff/ (1000 * 60 * 60);
		if (horas>maxHorasInactividad){
			this.finaliza();
			
			this.añadeMSG("Sesión finalizada por "+horas+" hora(s) de inactividad", TipoMensajeGotta.logout);
			this.getApli().añadeMSG("-1", "Sesión de '"+this.usuGotta+"' finalizada por "+horas+" hora(s) de inactividad", TipoMensajeGotta.logout);
			
			return false;
			}
		return true;
		}
	//para hacer login en una aplicación relacionada
	public static Usuario nuevoUsuario(HttpServletRequest req, Usuario usuApli1, String aplicacion) throws ErrorUsuarioNoValido {
		try {
			return nuevoUsuario(req, usuApli1.getLogin(), usuApli1.getPassword(), aplicacion);
			} 
		catch (ErrorGotta e) {
			throw new ErrorUsuarioNoValido(e.getMessage());
			} 
		}
	public static Usuario nuevoUsuario(HttpServletRequest request, String login, String password, String aplicacion) throws ErrorArrancandoAplicacion, ErrorConexionPerdida, ErrorGotta {
		Aplicacion apliDef=PoolAplicaciones.sacar(aplicacion);
		
		if (apliDef.getUrlAplicacion()==null)
			apliDef.setUrlAplicacion(request);
		if (apliDef.getEsquema()==null) {
			String err=apliDef.erroresArranque.toString();
			PoolAplicaciones.borrar(aplicacion);
			throw new ErrorArrancandoAplicacion(err);
			}
		
		HttpSession sesion = request.getSession();
		// Todo este lío es para evitar robo de cookie de sesión
		HashMap<String, Object> bak = new HashMap<String, Object>();
		if (sesion!=null){
			Enumeration<?> names = sesion.getAttributeNames();
				
			while(names.hasMoreElements()){
				String s=names.nextElement().toString();
				bak.put(s,sesion.getAttribute(s));
				}
//			sesion.invalidate();
			}
		sesion = request.getSession(true);
		for(String s:bak.keySet()){
			sesion.setAttribute(s, bak.get(s));
			}
		Usuario usu = new Usuario(login, password, apliDef, sesion.getId(), request);
		apliDef.verificaAccesoConcurrente(usu); 
		
		usu.nodoArranque= URL_Gotta.crea(request);
		if (usu.mli!=null) {
			if (usu.nodoArranque==null) usu.nodoArranque=new URL_Gotta();
			usu.nodoArranque.ml=usu.mli;
			}
		if (usu.nodoArranque!=null){
			ModoListaDef ml=usu.getModoListaDef(usu.nodoArranque.ml);
			usu.arbol=new Arboljson(usu, ml.getNivelInicial());
			}
		Util.putSesionAplicacionYDemás(usu);
		usu.aSesion(request);
		
		apliDef.añadeMSG("-1", "Inicio de la sesión de '"+login+"'", Constantes.TipoMensajeGotta.login);
		usu.añadeMSG("Inicio de la sesión", Constantes.TipoMensajeGotta.login);
		return usu;
		}
	
	public static Usuario desdeSesion(HttpServletRequest req) throws ErrorConexionPerdida, ErrorArrancandoAplicacion{
		return desdeSesion(req, false);
		}
	public static Usuario desdeSesion(HttpServletRequest req, boolean forzarUTF8) throws ErrorConexionPerdida, ErrorUsuarioNoValido, ErrorArrancandoAplicacion{
		HttpSession sesion=req.getSession(false);
		if (sesion==null)
			throw new ErrorConexionPerdida("ErrorSesionCaducada");
		try 
			{
			if (forzarUTF8)
				req.setCharacterEncoding( Constantes.UTF8 );
			else
				req.setCharacterEncoding( Constantes.CODIF );
			} 
		catch (UnsupportedEncodingException e1) 
			{/*pass*/}
		
		return desdeSesion(req, req.getParameter("aplicacion"), sesion);
		}
	
	public boolean existePuntoParo(String idx) {
		String idxPuntoParo=idx.substring(0, idx.lastIndexOf(Constantes.GUIÓN_BAJO));
		return this.puntosDeParo.contains(idxPuntoParo);
		}
	
	public void setPassword(String pas) {
		this.password=pas;
	}
	public void setLogin(String login) {
		this.usuGotta=login;
	}

	public JSONObject JSON(boolean esSesionActiva) throws JSONException{
		return xJSON(this, esSesionActiva);
	}
	
	public static JSONObject xJSON(String texto) throws JSONException{
		return new JSONObject()
			.put("idSesion", -1)
			.put("usuGotta", texto);
		}
	private static JSONObject xJSON(Usuario usr, boolean esSesionActiva) throws JSONException{
		return new JSONObject()
		.put("idSesion", usr.idsesion)
		.put("varios", Util.JSON(usr.varios))
		.put("horaConexion", usr.horaConexion)
		.put("horaUltimoAcceso", usr.horaUltimoAcceso)
		.put("usuGotta", usr.usuGotta)
		.put("esUsuarioActual", esSesionActiva)
		.put("finalizado", usr.sesionFinalizada);	
	}
	
	
	public ModoListaDef getModoListaDef(String vista) throws ErrorGotta {
		if (this.vistas==null)
			leerVistasUsuario();
		return this.vistas.get(vista);
	}
////////////////////
	public Coleccion<ModoListaDef> getVistas() throws ErrorGotta {
		if (vistas==null)
			leerVistasUsuario();
		return vistas;
		}
	public void reset() {
		this.vistas=null;
		this.menuGeneral=null;
		this.menus=null;
		
		this.tieneAutoexec = false;
		}

	// Constructor sobrecargado para llamada desde web services
	public Usuario(String usu, String pwd, String strapli) throws ErrorConexionPerdida, ErrorUsuarioNoValido	{
		Aplicacion apli=PoolAplicaciones.sacar(strapli);
		this.usuGotta=usu;
		this.validarUsuario(usu, pwd, apli, null);
		this.usuGotta+= " (desde Servicio Web)";
		this.idsesion=""+this.hashCode();
	
		//this.varios.put("IPCliente", req.getRemoteHost());
		//this.varios.put("Navegador", req.getHeader("user-agent"));
		
		this.horaConexion= FechaGotta.fechaActual();
		
		//this.arbol=new Arboljson(this);
		//leerDatosUsuario(apli);
		
		//this.password = pwd;
		//assert apli != null;

		this.aplicacion=apli.getCd();
		
		//setVariable("u", new Variable("u", Constantes.STRING, this.usuGotta));
		
		//nodoRaiz=null;
		//Usuario usu = new Usuario(login, password, apliDef, sesion.getId(), request);
		Util.putSesionAplicacionYDemás(this);
	}
	
	public Arboljson getArbol(ModoListaDef vista) {
		if (this.arboles==null)
			this.arboles=new Coleccion<Arboljson>(false);
		
		if (!this.arboles.containsKey(vista.getCd())){ 
			Arboljson a=new Arboljson(this, vista.getNivelInicial());
			this.arboles.put(vista.getCd(), a);
			a.ml=vista.getCd();
			}
		
		NodoActivo ndestino=this.arbol!=null?this.arbol.nodoDestino:null;
		Arboljson ret= this.arboles.get(vista.getCd());
		ret.nodoDestino=ndestino;
		return ret;
		}
	public Arboljson getArbol(INivelDef nivel) {
		if (this.arboles==null)
			this.arboles=new Coleccion<Arboljson>(false);
		
		if (!this.arboles.containsKey(nivel.getCd())){ 
			Arboljson a=new Arboljson(this, nivel);
			this.arboles.put(nivel.getCd(), a);
			}
		return this.arboles.get(nivel.getCd());
		}
}
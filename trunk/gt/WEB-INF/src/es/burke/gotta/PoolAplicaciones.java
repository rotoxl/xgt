package es.burke.gotta;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import es.burke.gotta.Constantes.TipoMensajeGotta;

public class PoolAplicaciones {
	private static final int SEGUNDOS_TARDA_CONEX = 3;

	private static Coleccion<Aplicacion> aplicaciones = null;
	private static Properties properties;
	private static Context context;

	static ServletContext ctx;
	public static String dirInstalacion=null;
	public static String getPrefijo(String idAplicacion) throws ErrorArrancandoAplicacion {
		return Util.noNulo(PoolAplicaciones.properties.getProperty(idAplicacion+".prefijo"));
		}
	public static void init(ServletContext pctx) throws NamingException, IOException, InterruptedException{
		ctx=pctx;
		InitialContext ictx = new InitialContext();
		context = (Context) ictx.lookup(ctx.getInitParameter("pool")); //si peta aquí prueba a editar el contenido (o borrar y regenerar) el miContexto.xml de $tomcat/conf/Catalina/localhost
		properties=new Properties();
		properties.load(ctx.getResourceAsStream("/WEB-INF/gotta.properties"));
		dirInstalacion=ctx.getRealPath("/");
    	
		PoolAplicaciones.getAplicaciones(); //para evitar carreras
		PoolAplicaciones.vListado=new LinkedHashMap<String, Object>();
		String lstAplicaciones = PoolAplicaciones.properties.getProperty("ListaDeAplicaciones");
		ExecutorService pool = Executors.newFixedThreadPool(5);
		for (String apli:es.burke.gotta.Util.split(lstAplicaciones, ",")) {
			PoolAplicaciones.vListado.put(apli, false);
			Rellena rellena = new Rellena(apli);
			pool.execute(rellena);
			}
		pool.shutdown();
		pool.awaitTermination(SEGUNDOS_TARDA_CONEX, TimeUnit.SECONDS);
		List<Runnable> ret = pool.shutdownNow();
		if(ret.size()>0){
			Logger log = Util.getLogger("gotta");
			for (Runnable i:ret){
				log.warn("gotta/"+i.toString()+" sigue activa tras "+SEGUNDOS_TARDA_CONEX+" segundos");			
				}
			}
		}
	public static void meter(String clave, Aplicacion valor) {
		getAplicaciones().put(clave,valor);
		}
	public static ArrayList<String> getAplisConAutoarranque(){
		ArrayList<String> ret=new ArrayList<String>();
		if (PoolAplicaciones.vListado!=null){
			for (String apli:PoolAplicaciones.vListado.keySet()) {
				String v=PoolAplicaciones.properties.getProperty(apli+".autoarranque");
				if (v!=null && v.equals("1"))
					ret.add(apli);
				}
			}
		return ret;
		}
	public static Aplicacion sacar(String idAplicacion) throws ErrorArrancandoAplicacion {
		String cd_aplicacion = idAplicacion.split(" ")[0];
		Coleccion<Aplicacion> _aplicaciones = getAplicaciones();
		Aplicacion apliDef = _aplicaciones.get(cd_aplicacion);
		if (apliDef==null || apliDef.getEsquema()==null)
			synchronized (_aplicaciones)	{
				apliDef = _aplicaciones.get(cd_aplicacion); // por si la han metido en lo que pillábamos cacho
				if (apliDef==null && !(idAplicacion.equals(Constantes.CAD_VACIA))){
					try {
						DataSource ds = getDataSource(idAplicacion);
						String prefijo = getPrefijo(idAplicacion);
						apliDef = new Aplicacion(cd_aplicacion, ds, prefijo);
						meter(idAplicacion, apliDef);
						apliDef.añadeMSG("-1", "Arrancando aplicación ["+idAplicacion+"]", TipoMensajeGotta.info);
						apliDef.arranca(); //la arrancamos, y si todo va bien la metemos
						apliDef.añadeMSG("-1", "Arranque de ["+idAplicacion+"] finalizado", TipoMensajeGotta.info);
						} 
					catch (ErrorGotta e) {
						if (apliDef!=null) {
							borrar(idAplicacion);
							throw new ErrorArrancandoAplicacion("Error al arrancar la aplicación: "+e.getMessage()+"\n"+apliDef.erroresArranque.toString(),e);
							}
						} 
					catch (NamingException e) {
							throw new ErrorArrancandoAplicacion("Error al arrancar la aplicación: "+e.getMessage());
						}
					}
				}
		return apliDef;
	}

	public static void borrar(String clave)
		{getAplicaciones().remove(clave);}
	public static Aplicacion rearrancaAplicacion(String idAplicacion, HttpServletRequest req) throws ErrorGotta, NamingException{	
		DataSource ds = getDataSource(idAplicacion);
			
		String prefijo = getPrefijo(idAplicacion);
		Aplicacion apliDef = new Aplicacion(idAplicacion, ds, prefijo);
		apliDef.arranca();
		apliDef.setUrlAplicacion(req);
		
		
		/*sustituimos la anterior por la nueva*/
		PoolAplicaciones.meter(idAplicacion, apliDef);
		try{
			Usuario usu=Usuario.desdeSesion(req);		
			
			String old_ml=null;
			if (usu.arbol!=null)
				old_ml=usu.arbol.ml;
		
			usu.reset();
		
			usu.leerDatosUsuario(apliDef); //re-leemos APP_Dic_configuracion [maxElementos/mli]
			usu.leerVistasUsuario();
			usu.nodos.clear();
			
			if (old_ml!=null)
				usu.arbol.ml=old_ml;
			}
		catch (ErrorSesionCerrada e) {
			// no hay sesión, puede ser montando diccionario
			}
		return apliDef;
		}
	static Coleccion<Aplicacion> getAplicaciones() {
	if(aplicaciones==null)
		aplicaciones=new Coleccion<Aplicacion>();
	return aplicaciones;
	}
	private static DataSource getDataSource(String idAplicacion) throws NamingException {
		return (DataSource)context.lookup(idAplicacion);	
		}
	static Connection getConnection(String nombreOrigenDatos) throws NamingException, ErrorConexionPerdida{
		Aplicacion apli;
		apli = getAplicaciones().get(nombreOrigenDatos);
		if (apli==null)
			try {
				return getDataSource(nombreOrigenDatos).getConnection();
				} 
			catch (SQLException e) {
				throw new ErrorConexionPerdida(e.getMessage(), e);
				}
		return apli.getDataSourceConnection();	
		}
	public static void valida(String apli) throws NamingException {
		getDataSource(apli);
		}
	public static LinkedHashMap<String, Object> vListado;
}
class Rellena extends Thread{
	private String apli;
	Rellena(String apli){
		this.apli=apli;
		this.setName("prueba jdbc "+apli);
		}
	@Override
	public void run() {
		try {
			PoolAplicaciones.getConnection(apli);
			PoolAplicaciones.vListado.put(apli, true);
			
			Logger log = Util.getLogger(apli);
			log.info("gotta/"+apli + " ok");
			} 
		catch (NameNotFoundException e){
			String msg="No está en gotta/context.xml: "+apli;
			PoolAplicaciones.vListado.put(apli, msg);
			
			Logger log = Util.getLogger(apli);
			log.debug(msg);
			}
		catch (Exception e){
			String msg="Error conectando a gotta/"+apli+": "+e;
			PoolAplicaciones.vListado.put(apli, msg);
			
			Logger log = Util.getLogger(apli);
			log.warn(msg);			
			}
		}
}

package es.burke.gotta.dll;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.burke.alfresco.AlfrescoWSClient;
import es.burke.alfresco.ErrorAlfresco;
import es.burke.alfresco.ErrorAutenticacion;
import es.burke.gotta.Accion;
import es.burke.gotta.Aplicacion;
import es.burke.gotta.Archivo;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorArrancandoAplicacion;
import es.burke.gotta.ErrorConexionPerdida;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Fila;
import es.burke.gotta.Filas;
import es.burke.gotta.Motor;
import es.burke.gotta.Util;

public class Gotta_Alfresco extends DLLGotta {
	String pwdAlfresco = Constantes.CAD_VACIA;
	String usuAlfresco = Constantes.CAD_VACIA;
	String tempDocAlfresco  = Constantes.CAD_VACIA;
	String storeAlfresco 	= Constantes.CAD_VACIA;
	String pathAlfresco 	= Constantes.CAD_VACIA;
	AlfrescoWSClient interfaz = null;
	static String LOG = "AlfrescoWS: ";

	private String nombreFichero;
	private InputStream is;
	
	@Override
	public String accionesValidas() {
		return "copiarContenido crearNodo borrarNodo borrarNodos modificarContenido obtenerWebDavDocURL traerContenido traeryDescargarContenido obtenerVersion obtenerListaVersiones insertarMetadatos obtenerMetadatos buscarDocumentos datosProperties"; //ejecutaConsulta
		}

	@Override
	public void verifica(Motor motor, String accion, String nombre) throws ErrorAccionDLL {
		verificaAccionValida(accion);
		this.mMotor = motor;
		this.usr	= motor.usuario;
		
		compruebaConfiguracion();
		}
	
	private void compruebaConfiguracion() throws ErrorAccionDLL{
		Aplicacion apli=mMotor.getApli();
		usuAlfresco = apli.getDatoConfig("usuAlfresco");
		pwdAlfresco = apli.getDatoConfig("pwdAlfresco");

		tempDocAlfresco  = apli.getDatoConfig("docWeb");
		
		if (esNuloOVacío(usuAlfresco) || 
			esNuloOVacío(pwdAlfresco) ||
			esNuloOVacío(tempDocAlfresco)){
				throw new ErrorAccionDLL("Falta la configuración de Alfresco en DIC_Configuracion. \n"+
										"    obligatorios: usuAlfresco, pwdAlfresco y docWeb\n"+
										"    opcionales: pathAlfresco y storeAlfresco");
			}
		}
	private boolean esNuloOVacío(String param){
		return param==null || param.equals(Constantes.CAD_VACIA);
		}
	@Override
	public Object ejecuta(Accion accion, Object valor) throws ErrorAccionDLL, ErrorConexionPerdida, ErrorArrancandoAplicacion {
		String acc = accion.accion;

		Aplicacion ap=mMotor.getApli();
		JSONObject json = null;
		try {
			if (valor != null && !"".equals(valor)) {
				json = new JSONObject(valor.toString());
				
				storeAlfresco = json.has("store")? json.get("store").toString(): ap.getDatoConfig("storeAlfresco");
				pathAlfresco= json.has("path")?json.get("path").toString(): ap.getDatoConfig("pathAlfresco");
				}
			else{
				storeAlfresco = ap.getDatoConfig("storeAlfresco");
				pathAlfresco=ap.getDatoConfig("pathAlfresco");
				}
			
			storeAlfresco=Util.vacioANulo(storeAlfresco);
			pathAlfresco=Util.vacioANulo(pathAlfresco);
			
			if (storeAlfresco==null) throw new ErrorAccionDLL("No se ha indicado el store con el que se quiere trabajar y tampoco está configurado en DIC_Configuracion.");
			if (pathAlfresco==null)  throw new ErrorAccionDLL("No se ha indicado el path con el que se quiere trabajar y tampoco está configurado en DIC_Configuracion.");
			}
		catch (Exception e1) {
			throw new ErrorAccionDLL(e1);
			}

		try {
			interfaz = new AlfrescoWSClient(usuAlfresco, pwdAlfresco, storeAlfresco, tempDocAlfresco);
			} 
		catch (ErrorAlfresco e2) {
			throw new ErrorAccionDLL(e2);
			} 

		try{
			if (acc.equalsIgnoreCase("crearNodo")) 
				return crearNodo(json);
			else if (acc.equalsIgnoreCase("borrarNodo")) 
				borrarNodo(json);
			else if (acc.equalsIgnoreCase("borrarNodos"))
				borrarNodos(json);
			else if (acc.equalsIgnoreCase("modificarContenido"))
				return modificarContenido(json);
			else if (acc.equalsIgnoreCase("obtenerWebDavDocURL"))
				return obtenerWebDavDocURL(json);
			else if (acc.equalsIgnoreCase("traerContenido"))
				return traerContenido(json);
			else if (acc.equalsIgnoreCase("traeryDescargarContenido")){
				traerYDescargarContenido(json);
				return Motor.Resultado.OK;
				}
			else if (acc.equalsIgnoreCase("obtenerVersion"))
				return obtenerVersion(json);
			else if (acc.equalsIgnoreCase("obtenerListaVersiones"))
				return obtenerListaVersiones(json);
			else if (acc.equalsIgnoreCase("insertarMetadatos"))
				insertarMetadatos(json);
			else if (acc.equalsIgnoreCase("obtenerMetadatos"))
				return obtenerMetadatos(json);
			else if (acc.equalsIgnoreCase("buscarDocumentos"))
				return buscarDocumentos(json);
			else if (acc.equalsIgnoreCase("datosProperties"))
				return datosProperties();
			else
				throw new ErrorAccionDLL("La acción '" + acc + "' no esta implementada.");
			
		/*	}
		 * catch(RepositoryFault e){
			throw new ErrorAccionDLL(e);*/
			}
		catch (Exception e) {
			throw new ErrorAccionDLL(e);
			}
		finally{
			//AuthenticationUtils.endSession();
			}
		return null;
		}
   	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ServletException {
   		String miArchivo=Util.nombreWeb(this.nombreFichero);
		
		Archivo.descargaFichero(res, miArchivo, this.is);
		
		this.añadeMSG("Archivo ["+this.nombreFichero+"] descargado desde Alfresco", Constantes.TipoMensajeGotta.archivos);
		}
   	
   	private static String sacaValor(JSONObject params, String param){
   		try {
			return params.getString(param);
   			} 
   		catch (JSONException e) {
			return null;
			}
   		}
   	private String traerContenido(JSONObject params) throws RemoteException{
		try	{
			String uuid  = sacaValor(params, "uuid");
			String ruta  = sacaValor(params, "ruta"); 

			File f = new File(ruta);
			f.mkdirs();
			ruta = es.burke.gotta.Util.rutaFisicaRelativaOAbsoluta(ruta);
			
			File fichero = interfaz.traerContenido(uuid, ruta);
	        return fichero.getAbsoluteFile().toString();
			}
		catch(MalformedURLException e) {
			throw new RemoteException(e.getMessage());
			} 
		catch (URISyntaxException e) {
			throw new RemoteException(e.getMessage());
			}
		finally{
			//AuthenticationUtils.endSession();
			}
   		}
   	private void traerYDescargarContenido(JSONObject params) throws RemoteException{
		try	{
			String uuid  = sacaValor(params, "uuid");
			this.nombreFichero= sacaValor(params, "nombre"); 
			
			this.is=interfaz.traerContenidoInputStream(uuid);
			
			usr.productosGenerados.put(""+this.hashCode(), this);
			}
		catch(MalformedURLException e) {
			throw new RemoteException(e.getMessage());
			} 
		catch (URISyntaxException e) {
			throw new RemoteException(e.getMessage());
			}
		finally{
			//AuthenticationUtils.endSession();
			}
   	}

	private String modificarContenido(JSONObject params) throws IOException, Exception {
		String uuid       = sacaValor(params, "uuid");
		String nombre 	  = sacaValor(params, "nombre"); //nombre del fichero
		String rutaFich   = sacaValor(params, "ruta"); //ruta fisica temporal del fichero
		
		String tipo = sacaValor(params, "tipo"); //Tipo MIME del fichero
		if (tipo==null) tipo=Constantes.CAD_VACIA;
		
		String cod   	  = sacaValor(params, "codif"); //Codificación del fichero.
		if (cod==null) cod=Constantes.CAD_VACIA;
		
		String comentario = sacaValor(params, "comentario"); //Codificación del fichero.
		
		rutaFich = es.burke.gotta.Util.rutaFisicaRelativaOAbsoluta(rutaFich);
		return interfaz.modificarContenido(uuid, nombre, rutaFich, tipo, cod, comentario);
		}
	private String obtenerWebDavDocURL(JSONObject params) throws RemoteException, JSONException, MalformedURLException, URISyntaxException, ErrorAutenticacion{
		String uuid      	= params.getString("uuid");
		String server    	= params.getString("server"); //p.e.: http://192.168.12.72:18080/alfresco/webdav/
		return interfaz.obtenerWebDavDocURL(uuid, server);
		}
	private Object crearNodo(JSONObject params) throws Exception {
		String uuid			 = null;
		//String path          = params.get("path").toString(); //ruta al home '/app:company_home/cm:moooo'
		String nombrefich    = sacaValor(params, "nombre"); //nombre del fichero
		String rutaFich      = sacaValor(params, "ruta"); //ruta fisica temporal del fichero
		boolean versionable  = sacaValor(params, "versionable").equals("1"); //Tipo de codificación del fichero

		rutaFich = es.burke.gotta.Util.rutaFisicaRelativaOAbsoluta(rutaFich);
		uuid=(String)interfaz.crearNodo(pathAlfresco, nombrefich, rutaFich, versionable);
		return uuid;
		}
	private void borrarNodo(JSONObject params) throws RemoteException, JSONException{
		String uuid 	 = sacaValor(params, "uuid");
		interfaz.borrarNodo(uuid);
		}
////////////
	private String obtenerVersion(JSONObject params) throws RemoteException, JSONException, MalformedURLException, URISyntaxException{
		String uuid 	 = sacaValor(params, "uuid");
		String ruta 	 = sacaValor(params, "ruta");
		String version 	 = sacaValor(params, "version");
		String nombre;

		ruta = es.burke.gotta.Util.rutaFisicaRelativaOAbsoluta(ruta);
		File f = new File(ruta);
		f.mkdirs();

		try {
			nombre = interfaz.obtenerVersion(uuid, Integer.parseInt(version), ruta);
			} 
		catch (Exception e) {
			nombre = interfaz.obtenerVersion(uuid, version, ruta);
			}
		return nombre;
		}
	private Filas obtenerListaVersiones(JSONObject params) throws RemoteException, JSONException, SQLException{
		String uuid 	 = sacaValor(params, "uuid");
		ArrayList<String> v =interfaz.getVersiones(uuid);

		int num_cols=4;
		ArrayList<String> tipos = Util.creaListaString(Constantes.INTEGER, Constantes.STRING, Constantes.STRING, Constantes.STRING);
		ArrayList<String> nombres = Util.creaListaString("key", "mododetalle", "version", "comentario");

		Filas filas = new Filas(nombres, tipos);

		for (int i=0; i<v.size(); i++) {
			ArrayList<String> valores=Util.split(v.get(i), Constantes.PIPE);
			
			Object[] dic=new Object[num_cols];
			dic[0]=i; dic[1]="w"; dic[2]=valores.get(1); dic[3]=valores.get(2);//versión, detalle, versión, comentario
			
			filas.add(new Fila(filas, dic));
			}
	    
	    return filas;
		}

	@SuppressWarnings("unchecked")
	public void insertarMetadatos(JSONObject params) throws RemoteException, JSONException{
		String uuid 	 = sacaValor(params, "uuid");
		Map<String, String> map = new HashMap<String, String>();

		for(Iterator<String> it = params.keys(); it.hasNext();) {
			String nombre = it.next();
			map.put(nombre, params.get(nombre).toString());
			}
		interfaz.insertarMetadatos(uuid, map);
		}
	@SuppressWarnings("unchecked")
	public String obtenerMetadatos(JSONObject params) throws JSONException, RemoteException {
		String uuid 	 = sacaValor(params, "uuid");
		String nombreMd  = Constantes.CAD_VACIA;

		for(Iterator<String> it = params.keys(); it.hasNext();) {
			String nombre = it.next();
			if (nombre.equals("nombre"))
				nombreMd = params.get("nombre").toString();
			}

		if (nombreMd.length() > 0)
			return interfaz.obtenerMetadatos(uuid, nombreMd);
		else {
			Map<String, String> metadatos = new HashMap<String, String>();
			String pares = null;
			String nombre = null;

			metadatos = interfaz.obtenerMetadatos(uuid);

			for(Iterator<String> it = metadatos.keySet().iterator(); it.hasNext();){
				if(pares == null){
					nombre = it.next();
					pares = nombre + ":" + metadatos.get(nombre);
					} 
				else {
					nombre = it.next();
					pares += "|" + nombre + ":" + metadatos.get(nombre);
					}
				}
			return pares;
			}
		}

	/**
	 * Busca documentos que incluyan en su contenido el texto indicado en filtroTexto.
	 * Si se le pasa el map con algún par de tipo <nombre, valor>, devolvera documentos
	 * que tengan metadatos con los nombres y valores indicados.
	 * Se pueden usar una, otra o las dos funcionalidades a la vez. Depende de los parametros que le lleguen.
	 * Pueden usarse operadores para afinar los resultados:
	 * 		· AND: Solo se mostraran resultados que tengan en su contenido el término precedido del signo + (+casa +coche).
	 * 		· NOT: Solo se mostraran resultados que no tengan en su contenido el término precedido del signo - (-casa -coche).
	 * 		· OR: (Por defecto) Se mostrarán resultados que tengan en su contenido algunas de las palabras buscadas (casa coche).
	 * Pueden usarse comillas para buscar frases concretas ("En un lugar de la Mancha").
	 * Pueden usarse carácteres comodín como ? o *.
	 * Los operadores, comillas y carácteres comodín pueden usarse en la misma consulta, auqnue no en los nombres de los metadatos.
	 * Si no quieres buscar por texto y se lo pasas como null se queja, en ese caso se le deberia pasar "".
	 */
	@SuppressWarnings("unchecked")
	public String buscarDocumentos(JSONObject params) throws RemoteException, JSONException{
		String filtroTexto = Constantes.CAD_VACIA;
		String uuids = null;
		Map<String, String> map = new HashMap<String, String>();
		List<String> datosUuid   = new ArrayList<String>();

		for(Iterator<String> it = params.keys(); it.hasNext();) {
			String nombre = it.next();
			if (nombre.equals("filtroTexto")) 
				filtroTexto = params.get(nombre).toString();
			else if (!nombre.equals("store"))
				map.put(nombre, params.get(nombre).toString());
			}

		datosUuid = interfaz.buscarDocumentos(filtroTexto,map);

 		for(Iterator<String> it = datosUuid.iterator(); it.hasNext();){
			if(uuids == null)
				uuids = it.next();
			else
				uuids += "|" + it.next();
 			}
		return uuids;
		}

    public Properties datosProperties() throws IOException {
    	return interfaz.datosProperties();
    }

	private void borrarNodos(JSONObject params) throws RemoteException, JSONException{
		List<String> listauuids = new ArrayList<String>();
		JSONArray array = params.getJSONArray("lista");

		for (int i = 0; i < array.length(); ++i)
			listauuids.add(array.getString(i));

		interfaz.borrarNodos(listauuids);
		}

	public static void copyFile(File in, File out) throws IOException{
		FileChannel inChannel = new
			FileInputStream(in).getChannel();
		FileChannel outChannel = new
			FileOutputStream(out).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(),
					outChannel);
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			if (inChannel != null) inChannel.close();
			if (outChannel != null) outChannel.close();
		}
	}

	public void writeToFile(InputStream is, File file) {
		try {
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			int c;
			while((c = is.read()) != -1) {
				out.writeByte(c);
			}
			is.close();
			out.close();
		}
		catch(IOException e) {
			System.err.println("Error Writing/Reading Streams.");
		}
	}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}

}
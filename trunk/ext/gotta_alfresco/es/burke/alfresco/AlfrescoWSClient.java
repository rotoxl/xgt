package es.burke.alfresco;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.alfresco.webservice.authoring.CheckoutResult;
import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.content.ContentFault;
import org.alfresco.webservice.repository.QueryResult;
import org.alfresco.webservice.repository.RepositoryFault;
import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLCreate;
import org.alfresco.webservice.types.CMLDelete;
import org.alfresco.webservice.types.CMLUpdate;
import org.alfresco.webservice.types.ContentFormat;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.Node;
import org.alfresco.webservice.types.ParentReference;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Query;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.ResultSet;
import org.alfresco.webservice.types.ResultSetRow;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.types.Version;
import org.alfresco.webservice.types.VersionHistory;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.ContentUtils;
import org.alfresco.webservice.util.Utils;
import org.alfresco.webservice.util.WebServiceFactory;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;

import es.burke.alfresco.utils.AlfUtils;
import es.burke.alfresco.utils.Constantes;
import es.burke.alfresco.utils.Util;

public class AlfrescoWSClient {
	
	private String clave= Constantes.CAD_VACIA;
	private String usuario= Constantes.CAD_VACIA;
	private String temporal= Constantes.CAD_VACIA;//Ruta donde se buscan los ficheros de disco a leer o escribir
	private Store store= null;//Almacen	
	
	// Raiz donde se va a crear el contenido. p.e. "/app:company_home"
	// private String			home				= null;
	@SuppressWarnings("unused")	private Boolean isAutenticated 		= false;
    private static final 	URLCodec urlCodec 	= new URLCodec("UTF-8");
    
    public static final String REPOSITORY_PASSWORD = "repository.clave";
    public static final String REPOSITORY_USER = "repository.usuario";
    public static final String REPOSITORY_TEMP = "repository.temporal";
    public static final String REPOSITORY_STORE = "repository.almacen";
    
    // Alfresco carga el valor de location directamente desde el properties
    // public static final String REPOSITORY_URL = "repository.location";

    public AlfrescoWSClient(String _usuario, String _clave, String _almacen, String _temporal) throws ErrorAlfresco {
    	usuario 	= _usuario;
    	clave		= _clave;	
		temporal 	= _temporal;
		
		if ( _almacen.equals(Constantes.CAD_VACIA) ) 
			throw new ErrorAlfresco("El nombre del almacen no es válido");
			
		store 		= new Store(Constants.WORKSPACE_STORE, _almacen);
		Connect();
    	}   
    public AlfrescoWSClient() throws IOException, ErrorAlfresco {
    	Properties props = new Properties();
    	props.load(AlfrescoWSClient.class.getResourceAsStream("/alfresco/webserviceclient.properties"));

    	usuario		= props.getProperty(REPOSITORY_USER);
    	clave		= props.getProperty(REPOSITORY_PASSWORD);
		temporal 	= props.getProperty(REPOSITORY_TEMP);
		
		if (props.getProperty(REPOSITORY_STORE).equals(Constantes.CAD_VACIA) ) 
			throw new ErrorAlfresco("El nombre del almace, obtenido desde \"/alfresco/webserviceclient.properties\", no es válido");
			
		store 		= new Store(Constants.WORKSPACE_STORE, props.getProperty(REPOSITORY_STORE));
		Connect();
    	}
    
    public void Connect() throws ErrorAlfresco{
		if ( temporal.equals(Constantes.CAD_VACIA) )
			throw new ErrorAlfresco("La ruta temporal no es válida");
    	Autenticate(usuario, clave);
    	}
    public void Disconnect() {
    	AuthenticationUtils.endSession();
    	}
    private void Autenticate(String _usuario, String _clave) throws ErrorAutenticacion {
	   	if (usuario.equals(Constantes.CAD_VACIA) || clave.equals(Constantes.CAD_VACIA)) 
	   		throw new ErrorAutenticacion("El usuario o la clave están vacios");
	   	
    	try {	    	   		
    		AuthenticationUtils.startSession(_usuario, _clave);
    		isAutenticated = true;
    		}
    	catch(Throwable e) { 
    		throw new ErrorAutenticacion(e.getMessage()); 
        	}
    }
	
    public void setClave(String _clave) {
		clave = _clave;
		isAutenticated = false;
		}
	public String getClave() {
		return clave;
		}
	public void setUsuario(String _usuario) {
		usuario = _usuario;
		isAutenticated = false;
		}
	public String getUsuario() {
		return usuario;
		}
	public void setTemporal(String _temporal) {
		temporal = _temporal;
		}
	public String getTemporal() {
		return temporal;
	}
   	public void setStore(String _store) {
		this.store = new Store(Constants.WORKSPACE_STORE, _store);
   	}
	public Store getStore() {
		return store;
		}
	
	
	/** Obtiene un objeto contenido rellenado con el contenido del uuid 	 */
	public Contenido obtenerContenido(String _uuid) throws ContentFault, RemoteException, MalformedURLException, URISyntaxException{
		
		Reference ref = new Reference();
		ref.setStore(store);
		ref.setUuid(_uuid); 
		
		Content[] readResult = WebServiceFactory.getContentService().read(new Predicate(new Reference[]{ref}, store, null),Constants.PROP_CONTENT);
		Content content	= readResult[0];
        return new Contenido(content);
		}

	/** Función que copia el contenido de un nodo en un fichero en la ruta dada	 */
	private Contenido _traerContenido(String uuid) throws RemoteException, MalformedURLException, URISyntaxException{
		Reference sourceRef 	 = new Reference();
		sourceRef.setStore(store);
		sourceRef.setUuid(uuid); 
		Predicate 	prd 			= new Predicate(new Reference[]{sourceRef}, store, null);
		Content[] readResult=WebServiceFactory.getContentService().read(prd, Constants.PROP_CONTENT); //WS
		Content   	srcContent 		= readResult[0];	
		Contenido contFuente = new Contenido(srcContent);
		return contFuente;
		}
	public File traerContenido(String uuid, String rutaFich) throws RemoteException, MalformedURLException, URISyntaxException{
		Contenido contFuente=_traerContenido(uuid);
		String		sNombreOrigen	= obtenerMetadatos(uuid, Constants.PROP_NAME);
	
		File fichero = new File(rutaFich+sNombreOrigen);
		contFuente.toFile(fichero);
        return fichero;
		}
	
	public InputStream traerContenidoInputStream(String uuid) throws RemoteException, MalformedURLException, URISyntaxException{
		Contenido contFuente=_traerContenido(uuid);
		return contFuente.toInputStream();
		}
	  	
	/**
	 * Modifica el contenido de un nodo Alf con el contenido del fichero que le pasamos.
	 * Fichero => Alf
	 * @param uuid 		identificador Alf del nodod
	 * @param nombre 	nombre del fichero
	 * @param rutaFich 	ruta fisica del fichero
	 * @param tipo 		Tipo MIME del fichero
	 * @param cod		Codificacion del fichero.
	 * @return
	 * @throws IOException 
	 */
	public String modificarContenido(String uuid, String nombre, String rutaFich, String tipo, String cod, String comentario) throws IOException, Exception{	
		Reference 	ref = new Reference(); //Crear referencia al contenido; 
		ref.setStore(store);
		ref.setUuid(uuid);
		if (cod.equals(Constantes.CAD_VACIA))
			cod = Constantes.UTF8;
		if (tipo.equals(Constantes.CAD_VACIA)) {
			Contenido cont = obtenerContenido(uuid);
			tipo = cont.getTipomime();
			}
		ContentFormat format = new ContentFormat(tipo, cod);  
		rutaFich = Util.rutaFisicaRelativaOAbsoluta(rutaFich)+ "/";	
		byte[] datFich =  Util.leerFichero(nombre, rutaFich);
		
		Predicate predicado = new Predicate(new Reference[]{ref}, null, null);
		CheckoutResult checkOutResult = WebServiceFactory.getAuthoringService().checkout(predicado, null);
		Reference workingCopyReference = checkOutResult.getWorkingCopies()[0];
		WebServiceFactory.getContentService().write(workingCopyReference, Constants.PROP_CONTENT, datFich, format);
			
        Predicate predicate = new Predicate(new Reference[]{workingCopyReference}, null, null);
	    NamedValue[] comments = new NamedValue[]{Utils.createNamedValue("description", comentario)};
        WebServiceFactory.getAuthoringService().checkin(predicate, comments, false);

		Version version =getVersion(uuid, 0); //coge la última versión
		if (version!=null)
			return version.getLabel();

		return "1";   
		}

	public String modificarContenido(String uuid, String nombre, String rutaFich, String tipo, String cod) throws IOException, Exception{
		return modificarContenido(uuid, nombre, rutaFich, tipo, cod, null);   
		}

	@SuppressWarnings("unused")
	public String obtenerWebDavDocURL(String _uuid, String _server) throws RemoteException, MalformedURLException, URISyntaxException{	
		//Se establece la referencia al nodo de contenido.
		Reference ref = new Reference();
		ref.setStore(store);
		ref.setUuid(_uuid); 
		Predicate prd = new Predicate(new Reference[]{ref}, store, null);
		// Se genera la referencia y se obtiene el nodo de contenido
		Content[] 	readResult	= WebServiceFactory.getContentService().read(prd, Constants.PROP_CONTENT);
		Content 	content	 	= readResult[0];
		Contenido 	miCont 		= new Contenido(content);
        if (content == null) 
        	throw new RemoteException("El contenido del documento fuente esta vacio");
        
        String   sNombreOrigen = obtenerMetadatos(_uuid ,Constants.PROP_NAME); 
        String   url 		   = _server;     
		String[] arrayCarpetas = content.getNode().getPath().split("/cm:");
		for (int i=1; i < arrayCarpetas.length; i++) {
			if (i != arrayCarpetas.length-1)
				try {
					url+=urlCodec.encode(arrayCarpetas[i])+"/";
					}
				catch (EncoderException e) {
					url+=arrayCarpetas[i]+"/";
					}
			}
		url+=  sNombreOrigen;
		return url;  
	}

	public Object crearNodo(String alfpath, String nombre, String rutaFich, boolean versionable) throws RepositoryFault, RemoteException {
		return crearNodo(alfpath, nombre, rutaFich, versionable,null);
		}
	public Object crearNodo(String alfpath, String nombre, String rutaFich, boolean versionable, String descripcion) throws RepositoryFault, RemoteException  {			
		rutaFich = Util.rutaFisicaRelativaOAbsoluta(rutaFich)+ "/";
		
		// Put content in the repository
		File filejj = new File(rutaFich + nombre);
		String contentRef = ContentUtils.putContent(filejj); //Nueva función: no se necesita ni codif ni mimetipe
		
		// Construct CML statement to create content node
		NamedValue[] propiedades = new NamedValue[3];
		propiedades[0] = Utils.createNamedValue(Constants.PROP_NAME		, nombre);
		propiedades[1] = Utils.createNamedValue(Constants.PROP_CONTENT	, contentRef);
		propiedades[2] = Utils.createNamedValue(Constants.PROP_DESCRIPTION	, descripcion);
		
	    // Se crea el nodo nuevo en el padre dado en la ruta.
	    Reference refNodoHoja = AlfUtils.getFolderReference(store, alfpath); // Se crea o se obtiene la referencia al destino de almacenamiento.
		ParentReference refPadre = new ParentReference(store, refNodoHoja.getUuid(), alfpath, Constants.ASSOC_CONTAINS, Constants.createQNameString(Constants.NAMESPACE_CONTENT_MODEL, nombre));
		CMLCreate create = new CMLCreate("id1", refPadre, null, null, null, Constants.TYPE_CONTENT, propiedades);
		
		// Construct CML Block
		CML cml = new CML();
		cml.setCreate(new CMLCreate[] { create });
		
		UpdateResult[] result = WebServiceFactory.getRepositoryService().update(cml);
		if (versionable) {
			Reference ref = new Reference();
			ref.setStore(store);
			ref.setUuid(result[0].getDestination().getUuid()); 
			AlfUtils.makeVersionable(ref);
			}
		return result[0].getDestination().getUuid();	
		}
		
	//--------------------------nuevas 
	
	public ArrayList<String> getVersiones (String uuid) throws RemoteException {		
		Reference ref = new Reference();
		ref.setStore(store);
		ref.setUuid(uuid);
		
		String		sNombreOrigen	= obtenerMetadatos(uuid ,Constants.PROP_NAME);
		ArrayList<String> ret=new ArrayList<String>();
		VersionHistory vh = WebServiceFactory.getAuthoringService().getVersionHistory(ref);
	    for (Version v : vh.getVersions()) 
	    	ret.add(sNombreOrigen+"|"+v.getLabel()+"|"+getComentario(v));

		return ret;
		}
	private String getComentario(Version version) {
        String comentario = "";
        for (NamedValue namedValue : version.getCommentaries()) {
            if (namedValue.getName().equals("description") == true) 
                comentario = namedValue.getValue();
        	}
        return comentario;
    	}
	
	public String obtenerVersion(String uuid, int version, String rutaFich) throws RemoteException, MalformedURLException, URISyntaxException {
		Version v = getVersion(uuid, version);
		if (v!=null)
			return traerContenido(uuid, v.getId(), rutaFich);
		else 
			return "";
		}
	public String obtenerVersion(String uuid, String version, String rutaFich) throws RemoteException, MalformedURLException, URISyntaxException {
		Reference ref = new Reference();
		ref.setStore(store);
		ref.setUuid(uuid);
		
		VersionHistory vh = WebServiceFactory.getAuthoringService().getVersionHistory(ref);
	    for (Version v : vh.getVersions()) {
	        // Output the version details
	    	if (v.getLabel().equals(version))
	    		return traerContenido(uuid, v.getId(), rutaFich);
	    	}
		return "";
		}
	
	/**
	 * Nos llegan las palabras separadas por espacios q se van a buscar.
	 * -Una consulta Lucene está compuesta por términos y operadores.
	 * -Existen 2 tipos de términos: individuales y frases. P.e. "test" o "hello" son términos individuales.
	 * -Una frase es un grupo de palabras rodeadas por comillas. P.e. "hello dolly".
	 * -Múltiples términos se pueden combinar con operadores lógicos para formar consultas complejas (AND, "+", OR, NOT,"-").
	 * -El operador NOT no se puede usar con un único término. P.e. La siguiente búsqueda no produce resultados: NOT "jakarta apache"
	 * -Las consultas Lucene aceptan los caracteres comodín ? y *.
	 * -Las consultas ser realizarán sobre el campo text: exclusivamente.
	 * 
	 * @param todasLasPalabras - que contenga todas las palabras dadas en este param.
	 * @param frase -  que contenga la frase completa
	 * @param cualquierPalabra - que contenga cualquiera de las palabras dadas.
	 * @param ningunaPalabra - que NO contenga ninguna de las palabras dadas.
	 * @return
	 */
	public static String construirConsulta(String todasLasPalabras, String frase, String cualquierPalabra, String ningunaPalabra) {
		String termTodasLasPalabras = Constantes.CAD_VACIA; // TP  = (+aa +bb +cc +dd)
		String termFrase 			= Constantes.CAD_VACIA; // TF  = ("aa bb cc de")
		String termCualquierPalabra = Constantes.CAD_VACIA; // TCP = (aa bb cc dd)
		String termNingunaPalabra 	= Constantes.CAD_VACIA; // TNP = (-aa -bb -cc -dd)
		String consulta				= Constantes.CAD_VACIA; // C   = text:( TP AND TF AND TCP AND TNP) 
		String operator				= " "; //El operador entre los términos
		
		todasLasPalabras = AlfUtils.escaparLucene(todasLasPalabras);
		frase 			 = AlfUtils.escaparLucene(frase);
		cualquierPalabra = AlfUtils.escaparLucene(cualquierPalabra);
		ningunaPalabra   = AlfUtils.escaparLucene(ningunaPalabra);
		
		if (todasLasPalabras!=null && !todasLasPalabras.equals(Constantes.CAD_VACIA)) {
			todasLasPalabras = todasLasPalabras.trim();
			String[] pals    = todasLasPalabras.split("\\s+");
			todasLasPalabras = Constantes.CAD_VACIA;
			for (int i=0; i < pals.length; i++) 
				todasLasPalabras+= "+"+pals[i]+" ";		
			termTodasLasPalabras+="("+todasLasPalabras.trim()+")"; 
		}		
		if (frase!=null && !frase.equals(Constantes.CAD_VACIA) ) {
			if (!termTodasLasPalabras.equals(Constantes.CAD_VACIA))
				termFrase+=operator;
			termFrase+="\""+ frase +"\"";
		}	
		if (cualquierPalabra != null && !cualquierPalabra.equals(Constantes.CAD_VACIA)) {
			cualquierPalabra = cualquierPalabra.trim();
			String[] pals    = cualquierPalabra.split("\\s+");
			cualquierPalabra = Constantes.CAD_VACIA;
			for (int i=0; i < pals.length; i++) 
				cualquierPalabra+= pals[i]+" ";			
			if ( (!termTodasLasPalabras.equals(Constantes.CAD_VACIA))|| (!termFrase.equals(Constantes.CAD_VACIA)) )
				termCualquierPalabra+=operator;	
			termCualquierPalabra+="("+cualquierPalabra.trim()+")"; 
		}
		if (ningunaPalabra!=null && !ningunaPalabra.equals(Constantes.CAD_VACIA)) {
			ningunaPalabra = ningunaPalabra.trim();
			String[] pals  = ningunaPalabra.split("\\s+");
			ningunaPalabra = Constantes.CAD_VACIA;
			for (int i=0; i < pals.length; i++) 
				ningunaPalabra+= "-"+pals[i]+" ";
			if ( (!termTodasLasPalabras.equals(Constantes.CAD_VACIA)) || 
				 (!termFrase.equals(Constantes.CAD_VACIA))            || 
				 (!termCualquierPalabra.equals(Constantes.CAD_VACIA)))
				termNingunaPalabra+=operator;
			termNingunaPalabra+="("+ningunaPalabra.trim()+")";
		}		
		consulta = "TEXT:("+ termTodasLasPalabras+termFrase+termCualquierPalabra+termNingunaPalabra +")";
		return consulta;
	}
	
	private Version getVersion(String uuid, int version) throws RemoteException, MalformedURLException, URISyntaxException {
		Reference ref = new Reference();
		ref.setStore(store);
		ref.setUuid(uuid);
		
		VersionHistory vh = WebServiceFactory.getAuthoringService().getVersionHistory(ref);
		Version v = null;
		try { 
			v=vh.getVersions(version);
			} 
		catch(Exception e) {
			//pass
			}
		return v;
		}
	
	public String traerContenido(String uuid, Reference refVersion, String rutaFich) throws RemoteException, MalformedURLException, URISyntaxException{
		rutaFich = Util.rutaFisicaRelativaOAbsoluta(rutaFich);
		if (!rutaFich.endsWith("/")) rutaFich=rutaFich+ "/";
		
		Predicate 	prd 			= new Predicate(new Reference[]{refVersion}, null, null);
		Content[]	readResult 		= WebServiceFactory.getContentService().read(prd, Constants.PROP_CONTENT); //WS
		Content   	srcContent 		= readResult[0];
		String		sNombreOrigen	= obtenerMetadatos(uuid ,Constants.PROP_NAME);
		Contenido contFuente = new Contenido(srcContent);
		File fichero = new File(rutaFich+sNombreOrigen);
        return contFuente.toFile(fichero);
		}
	public void borrarNodo(String uuid) throws RemoteException{
		//Se establece la referencia del elemento a borrar 
		Reference ref = new Reference();
		ref.setStore(store);
		ref.setUuid(uuid);  
		//Borrado
		CMLDelete cmlDelete = new CMLDelete();
		cmlDelete.setWhere( new Predicate(new Reference[]{ref}, store, null));

		CML cml = new CML();
		cml.setDelete(new CMLDelete[]{cmlDelete});
		WebServiceFactory.getRepositoryService().update(cml);
		}

	public void insertarMetadatos(String uuid, Map<String,String> mapMd) throws RemoteException{
		// Añade los metadatos recibidos
		NamedValue[] propiedades = new NamedValue[mapMd.size()];
		int i = 0;
		for(Iterator<String> it = mapMd.keySet().iterator(); it.hasNext();) { 
            String nombreMd = it.next();
            String valorMd = mapMd.get(nombreMd);
            propiedades[i++] = Utils.createNamedValue(nombreMd, valorMd);	
			}
				
		// Apunta al fichero indicado en la uuid
		Reference ref = new Reference();
		ref.setStore(store);
		ref.setUuid(uuid); 

		// Actualiza el archivo generando una nueva versión	
		CMLUpdate cmlUpdate = new CMLUpdate(propiedades,null,"1");
		cmlUpdate.setWhere(new Predicate(new Reference[]{ref}, store, null));
		
		CML cml = new CML();
		cml.setUpdate(new CMLUpdate[]{cmlUpdate});
		WebServiceFactory.getRepositoryService().update(cml);
		
		// Actualizar comentario de la versión generada
		Predicate predicado = new Predicate(new Reference[]{ref}, null, null);
		
		CheckoutResult checkOutResult = WebServiceFactory.getAuthoringService().checkout(predicado, null);
		Reference workingCopyReference = checkOutResult.getWorkingCopies()[0];
	    Predicate predicate = new Predicate(new Reference[]{workingCopyReference}, null, null);
		NamedValue[] comments = new NamedValue[]{Utils.createNamedValue("description", "Modificación metadatos")};
	    WebServiceFactory.getAuthoringService().checkin(predicate, comments, false);		
	}
	public String obtenerMetadatos(String uuid, String nombreMd) throws RemoteException {		
		Reference sourceRef = new Reference();
		sourceRef.setStore(store);
		sourceRef.setUuid(uuid); 
		Predicate prd = new Predicate(new Reference[]{sourceRef}, store, null);
		Node nodo  = WebServiceFactory.getRepositoryService().get(prd)[0];
		for (NamedValue namedValue : nodo.getProperties()) {
			String valueNv 	 = namedValue.getValue();
			String valueNameNv = namedValue.getName();
			//Esto hace que busque en metadatos cuyo contenido ha sido insertado manualmente
			// Funciona para metadatos propios de Alfresco con su namespace y para los nuestros sin namespace
			if ( valueNameNv.equals(nombreMd) || valueNameNv.equals("{}" + nombreMd))
				return valueNv;
			}
		return "";
		}
	public Map<String, String> obtenerMetadatos(String uuid) throws RemoteException{	
		Map<String, String> mapMd = new HashMap<String, String>();
		Node nodo = AlfUtils.obtenerNodo(uuid, store);
		NamedValue[] propiedades = nodo.getProperties();
		
		for (int i = 0; i < propiedades.length; i++) {
			String name = propiedades[i].getName();
			// Eliminamos el "prefijo" que mete Alfresco antes de cada "key" de property. Tipicamente: {htt://servidor/...}
			name = name.substring(name.indexOf('}')+1);
			mapMd.put(name, propiedades[i].getValue());
			}
		return mapMd;
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
	 * Pueden usarse caracteres comodín como ? o *.
	 * Los operadores, comillas y caracteres comodín pueden usarse en la misma consulta, auqnue no en los nombres de los metadatos.
	 * Si no quieres buscar por texto y se lo pasas como null se queja, en ese caso se le deberia pasar "".

	 * @param filtroTexto 	- ej: "Érase una vez"
	 * @param filtroMap 	- ej: "Prueba nombre", "Prueba valor" 
	 * @return		- Una lista de uuids con los documentos que complen la consulta
	  
	 */ 
	public List<String> buscarDocumentos(String filtroTexto, Map<String,String> filtroMap) throws RemoteException{				
		String consulta = "";
		
		// Genera la consulta
		if (filtroTexto.length() > 0)
			consulta = "TEXT:" + AlfUtils.escaparLucene(filtroTexto);	
			
		for(Iterator<String> it = filtroMap.keySet().iterator(); it.hasNext();) {
			String nombre = it.next();
			String valor = filtroMap.get(nombre);
			if (consulta.length() == 0)
				consulta = "@" + nombre + ":" + valor;
			else
				consulta += " AND @" + nombre + ":" + valor;
			}
		
		// Lanza la contulta
		Query qry = new Query(Constants.QUERY_LANG_LUCENE, consulta);
		QueryResult 	  queryResult = WebServiceFactory.getRepositoryService().query(store, qry, false);
		ArrayList<String> datosUuid   = new ArrayList<String>();
		
		// Se recogen resultados
		ResultSet 	   	  resultados  = queryResult.getResultSet();
		ResultSetRow[] 	  rows        = resultados.getRows();
		if (rows != null){
			for (ResultSetRow row:rows)  {
				String  uuid = row.getNode().getId();
				datosUuid.add(uuid);
				}
			}
		else{
			datosUuid.add(""); //para que haya algo.
			}
		return datosUuid;
	}
	
    public Properties datosProperties() throws IOException {
    	Properties props = new Properties();
    	props.load(AlfrescoWSClient.class.getResourceAsStream("/alfresco/webserviceclient.properties"));

    	return props;
    	}
	public void borrarNodos(List<String> listauuids) throws RemoteException{
		Reference ref = new Reference();
		
		// Se recorre la lista
		for(Iterator<String> it = listauuids.iterator(); it.hasNext();) {
			String uuid = it.next();
			
			//Se establece la referencia del elemento a borrar 
			ref.setStore(store);
			ref.setUuid(uuid); 

			//Borrado
			CMLDelete cmlDelete = new CMLDelete();
			cmlDelete.setWhere( new Predicate(new Reference[]{ref}, store, null));
			CML cml = new CML();
			cml.setDelete(new CMLDelete[]{cmlDelete});
			WebServiceFactory.getRepositoryService().update(cml);
			}
		}
}
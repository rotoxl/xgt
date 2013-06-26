package es.burke.alfresco;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;

import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.ContentUtils;
import java.io.InputStream;

import es.burke.alfresco.utils.*;

import org.apache.log4j.Logger;

/**
 * Clase wrapper de un elemento de contenido de Alfresco
 * listo para enviar a un httpServletResponse
 */
public class Contenido {

	private String uuid			= Constantes.CAD_VACIA;
	private String header		= Constantes.CAD_VACIA;
	private String tipomime 	= Constantes.CAD_VACIA;
	private String codificacion = Constantes.CAD_VACIA;	
	private Boolean valido = false;
	private URL    url 			= null;
	private File   fichero		= null;
	private Content contenidoInterno = null;
	
	private final String h1 = "Content-Disposition";
	private final String h2 = "attachment; filename=";
	
	private static final  Logger LOG = Logger.getLogger(Contenido.class);
	/**
	 * Constructor de un contenido. Extrae toda la información necesaria
	 * @param contenido
	 * @throws MalformedURLException 
	 * @throws URISyntaxException 
	 */
    public Contenido(Content contenido) throws RemoteException, MalformedURLException, URISyntaxException { 
		
    	LOG.info("Rellenando un objeto de contenido...");
    	if (contenido == null) {
    		LOG.error("Contenido a NULL");
    		throw new RemoteException("El contenido del documento fuente esta vacio");
    	}
    	
    	uuid = contenido.getNode().getUuid();
    	LOG.debug("uuid= "+ uuid);
    	setTipomime(contenido.getFormat().getMimetype());
    	LOG.debug("tipomime= "+ tipomime);
    	setCodificacion(contenido.getFormat().getEncoding());
    	LOG.debug("codificacion= "+ codificacion);
    	url				= new URL(contenido.getUrl());
    	LOG.debug("url= "+ url.toURI());
    	fichero			= new File(url.getFile());
    	LOG.debug("fichero= "+ fichero.getName());

    	setHeader( fichero.getName());   
    	LOG.debug("header = "+ header);
    	
    	contenidoInterno = contenido;
    	
    	
    	LOG.info("objeto de contenido rellenado correctamente");
    	valido = true;
    }
    
    public String toFile(File ficheroAescribir) {  	
    	Util.writeToFile(toInputStream(), ficheroAescribir);
    	LOG.info("contenido copiado al fichero:" + ficheroAescribir.getName());
    	return ficheroAescribir.getName();
    	}
    public String toFile(String rutaArchivo) throws IOException {
    	Boolean res 	= Util.escribirAFichero(rutaArchivo, toByteArray()); 
    	if (res)
    		return rutaArchivo;
    	else 
    		return "";
    	}
    
    public byte[] toByteArray() throws IOException {
    	LOG.info("Se obtiene el byte array del contenido");
    	InputStream inS = toInputStream();
		byte[] 	bytesDoc = Util.iStream2ByteA(inS); 	//pasamos de un inputStream a un byte array.
        if (inS != null)
        	inS.close();
        return bytesDoc;
    	}

	public InputStream toInputStream(){
    	LOG.info("Se obtiene el stream");
		return ContentUtils.getContentAsInputStream(contenidoInterno);
	}
    
    private void setHeader(String filename) {
    	header += h1 + h2 + Constantes.COMILLAS + filename +Constantes.COMILLAS;
    }
    public String getHeader(){
    	return header;
    }
	public String getTipomime() {
		return tipomime;
	}
	private void setTipomime(String tMime){	
		if (tMime.compareToIgnoreCase(Constantes.CAD_VACIA) == 0)
			tMime = Constants.MIMETYPE_TEXT_PLAIN;
		tipomime = tMime;
	}
	public String getCodificacion() {
		return codificacion;
	}
	private void setCodificacion(String tCodif){
		if (tCodif.compareToIgnoreCase(Constantes.CAD_VACIA) == 0)
			tCodif = Constantes.UTF8;
		codificacion = tCodif;
	}
	public URL getUrl() {
		return url;
	}

	public Boolean esValido(){
		return valido;
	}
}

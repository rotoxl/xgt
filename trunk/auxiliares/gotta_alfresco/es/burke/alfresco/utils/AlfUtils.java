package es.burke.alfresco.utils;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.alfresco.webservice.repository.RepositoryFault;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLAddAspect;
import org.alfresco.webservice.types.CMLCreate;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.Node;
import org.alfresco.webservice.types.ParentReference;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.types.Version;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.Utils;
import org.alfresco.webservice.util.WebServiceFactory;

/**
 * Funciones de utilidad de Alfresco
 */

public final class AlfUtils {
	
	/**
	 * @author vcg482f	     - Crea un elemento FOLDER en Alfresco
	 * @param almacen		 - Almacen Alfresco
	 * @param refCarpeta     - Referencia a la carpeta a crear
	 * @param refPadre   	 - Referencia al padre donde va a colgar la carpeta nueva
	 * @param sNombreCarpeta - Nombre de la carpeta 
	 * @return
	 * @throws RepositoryFault
	 * @throws RemoteException
	 */
	 public static Reference createFolder(Store _almacen, Reference _refCarpeta, ParentReference _refPadre, String _sNombreCarpeta) throws RepositoryFault, RemoteException  {
        try { 
            WebServiceFactory.getRepositoryService().get(new Predicate(new Reference[]{_refCarpeta}, _almacen, null));
        }catch (Exception exception)  { // Se comprueba si se ha creado o no la carpeta. NO=throw.       	
            NamedValue[] properties = new NamedValue[]{Utils.createNamedValue(Constants.PROP_NAME, _sNombreCarpeta )};
            CMLCreate 	 create  	= new CMLCreate("1", _refPadre, null, null, null, Constants.TYPE_FOLDER, properties);
            CML 		 cml 		= new CML();
            cml.setCreate(new CMLCreate[]{create}); 
            UpdateResult[] results   = WebServiceFactory.getRepositoryService().update(cml);                     
            Reference      newFolder = results[0].getDestination();
            return newFolder;     // Se devuelve la referencia a la carpeta nueva
        }
		return _refCarpeta;
	}
	/**
	 * @author vcg482f	     - Obtiene la referencia a la carpeta hoja creandoario la estructura de carpetas si no existen.
	 * @param _almacen		 - Almacen destino.
	 * @param _carpetas 	 - ruta de carpetas : p.e. "/app:company_home/cm:carpetaA/cm:carpetaAB"
	 * @return
	 * @throws RepositoryFault
	 * @throws RemoteException
	 */ 
	 public static Reference getFolderReference(Store _almacen, String _carpetas) throws RepositoryFault, RemoteException  {
		Reference   refCarpetaHoja	= null;
		String[] 	arrayCarpetas   = _carpetas.split("/cm:");
		String      composeFolder   = arrayCarpetas[0];
		
		if (arrayCarpetas.length == 1) {
			return new Reference(_almacen, null, composeFolder);
		}
		for (int i=1; i < arrayCarpetas.length; i++) {
			String sPadre = composeFolder;
			composeFolder+="/cm:"+arrayCarpetas[i];
			Reference refCarpeta = new Reference(_almacen, null, composeFolder); 
            ParentReference parentReference = new ParentReference( // Create parent reference
            		_almacen,
                    null, 
                    sPadre,
                    Constants.ASSOC_CONTAINS, 
                    Constants.createQNameString(Constants.NAMESPACE_CONTENT_MODEL, arrayCarpetas[i]));
            refCarpetaHoja = createFolder(_almacen, refCarpeta, parentReference, arrayCarpetas[i]);
		}
		return refCarpetaHoja;
	}
	
	/**
	 * @author vcg482f   - Obtiene todos los almacenes como pares esquema-almacen
	 * @return			 - Un array de cadenas con los pares esquema-almacen. p.e.:workspace://version2Store
	 * @throws RepositoryFault
	 * @throws RemoteException
	 */
	 public static ArrayList<String> getStores() throws RepositoryFault, RemoteException {			
        RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory.getRepositoryService();
        Store[] stores = repositoryService.getStores();
        if (stores == null)  { // NOTE: un array vacio se devuelve como un null.  
            System.out.println("No hay almacenes disponibles en el repositorio.");
        } else {
        	ArrayList<String> arrayStores = new ArrayList<String>();
            for (Store store : stores)  {
                String sStore = store.getScheme() + "://" + store.getAddress();
                arrayStores.add(sStore);
            }
            return arrayStores;
        }
		return null;
	}
	
	/**
	 * MÈtodo auxiliar que a√±ade la propiedad versionable a la referencia dada.
	 * @param reference
	 * @throws Exception
	 */
	 public static void makeVersionable( Reference reference) throws RepositoryFault, RemoteException {
    	RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory.getRepositoryService();
        Predicate 	 predicate  = new Predicate(new Reference[]{reference}, null, null);
        CMLAddAspect addAspect  = new CMLAddAspect(Constants.ASPECT_VERSIONABLE, null, predicate, null);            
        CML 		 cml 		= new CML();
        cml.setAddAspect(new CMLAddAspect[]{addAspect});
        repositoryService.update(cml); //esta query a√±ade la propiedad versionable al nodo.
    }  
    
    /**
     * M√©todo auxiliar que muestra los detalles de la version.
     * 
     * @param version   -La version
     */
	 public static void showVersion(Version version)
    {
        String description = "none";
        for (NamedValue namedValue : version.getCommentaries()) {
            if (namedValue.getName().equals("description") == true) {
                description = namedValue.getValue();
            }
        }
        System.out.println("Version label = " + version.getLabel() + "; Version description = " + description);
    }
	
		public static String escaparLucene(String consultaAescapar) {
			if (consultaAescapar==null)
				return null;
			String especiales = "+ - && || ! ( ) { } [ ] ^ \" ~ * ? : \\"; 
			String escapeAlf  = "/";
			try {		
				String[] arrayEspecial = especiales.split(" "); //lo pasamos un array.
				//comprobamos la existencia del caracter especial en la consulta
				// y lo escapamos con /.
				for (int j=0; j < arrayEspecial.length; j++)  {	
					consultaAescapar = consultaAescapar.replace(arrayEspecial[j], escapeAlf.concat(arrayEspecial[j]));
				}	
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			return consultaAescapar;
		}
	 
	 /**
	  * MÈtodo que obtiene un nodo a traves de la UUID y el store
	  */
	 public static Node obtenerNodo(String uuid, Store store){
		 RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory.getRepositoryService();

		 Node nodo = null;
		 Reference reference = new Reference(store, uuid, null);
		 Predicate predicate = new Predicate(new Reference[]{reference}, null, null);
		 Node[] nodes;

		 try {
			 nodes = repositoryService.get(predicate);
			 nodo = nodes[0];
		 } catch (RepositoryFault e) {
			 e.printStackTrace();
		 } catch (RemoteException e) {
			 e.printStackTrace();
		 }
		 return nodo;
	 }
}

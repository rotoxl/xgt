package es.burke.alfresco;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.webservice.repository.RepositoryFault;
import org.apache.log4j.Logger;

import es.burke.alfresco.resources.i18n.GeneralProperties;
import es.burke.alfresco.utils.SpringBeanHelper;

@SuppressWarnings("unused")
public class PuntodeEntrada {
	
	private static final   Logger LOG = Logger.getLogger(PuntodeEntrada.class);
	public static void main(String[] args) {
		RunApp();
		System.exit(0);
	}
	private static void RunApp() {
	
		AlfrescoWSClient interfaz = null;
		try {		
			SpringBeanHelper sprHelper = new SpringBeanHelper(GeneralProperties.getProperty("SPRING_CONFIG"));
			//Se obtiene el singleton
			interfaz 	= (AlfrescoWSClient)sprHelper.getBeanInstance("AlfrescoWS");
			//se crea un elemento
			//String uuid = PruebaCrear(interfaz, true);
			String uuid = "852e054f-5a93-426d-ab62-724a0adc383d";
			//Se escribe a fichero a partir de un objeto Contenido. Este método está obsoleto?
			//PruebaObtener(interfaz, uuid);
			
			//Se escribe a fichero.
			//PruebaTraer(interfaz, uuid);
			
			//prueba a modificar un contenido
			//PruebaModificar(interfaz, uuid);
			
			//ver versiones
			//PruebaVerVersiones(interfaz, uuid);
			
			//obtenerVersion
			//PruebaObtenerVersionByLabel(interfaz, uuid, "1.1");
			//PruebaObtenerVersion(interfaz, uuid, 1);
			
			//Se borra el elemento recien creado.
			//PruebaBorrar(interfaz, uuid);

			//Se añaden un metadato al documento
			//PruebaInsertarMetadato(interfaz, uuid, true);
			
			//Se añaden metadatos al documento
			//PruebaInsertarMetadatos(interfaz, uuid, false);
			
			//Se leen los metadatos del documento
			//PruebaObtenerMetadatos(interfaz, uuid);
			
			//Se lee el metadato que se pasa por parametro
			//PruebaObtenerMetadato(interfaz, uuid);
			
			//Busca un documento cuyo nombre y valor coincidan con los parametros
			PruebaBuscarDocumento(interfaz);
		} catch (Exception e) {
			LOG.error("Error", e);
		}finally {
			if (interfaz != null)
				interfaz.Disconnect();
		}
	}
	private static void PruebaTraer(AlfrescoWSClient interfaz, String uuid) throws Exception {
		interfaz.traerContenido(uuid,  "c:\\temp\\copiaTraerAlf");
		//LOG.info("Se ha escrito a el fichero:"+ ruta);		
		}
	private static void PruebaModificar(AlfrescoWSClient interfaz, String uuid) throws Exception {
		Contenido cont = interfaz.obtenerContenido(uuid);
		String ruta 		= "C:\\TEMP\\copiaTraerAlf\\";
		String nombrefich	= "pruebaAlfDoc.doc";

		String s = interfaz.modificarContenido(uuid, nombrefich, ruta, cont.getTipomime(), cont.getCodificacion(), "comentario a la modificación");
		LOG.info("Se ha modificado el fichero:"+ nombrefich+"; version="+s);
		}
	private static void PruebaObtener(AlfrescoWSClient interfaz, String _uuid) throws Exception {
		Contenido cont = interfaz.obtenerContenido(_uuid);
		String ruta = cont.toFile("c:\\temp\\copiaObtenerAlf");
		LOG.info("Se ha escrito a el fichero:"+ ruta);
		}
	private static void PruebaVerVersiones(AlfrescoWSClient interfaz, String uuid) throws Exception {
		ArrayList<String> v =interfaz.getVersiones(uuid);
		for (int i=0; i<v.size(); i++)
			LOG.info("versión "+i+": "+v.get(i));
		
	}

	private static void PruebaObtenerVersion(AlfrescoWSClient interfaz, String uuid, int numVersion) throws Exception {
		String ruta=interfaz.obtenerVersion(uuid, numVersion, "c:\\temp\\copiaTraerAlf");
		LOG.info("Obtenida la version "+numVersion+"; Se ha escrito a el fichero:"+ ruta);
		
	}
	private static void PruebaObtenerVersionByLabel(AlfrescoWSClient interfaz, String uuid, String label) throws Exception {
		//String ruta=interfaz.obtenerVersion(uuid, label, "c:\\temp\\copiaTraerAlf");
		String ruta=interfaz.obtenerVersion(uuid, label, "C:/eclipse/workspace/Gotta8/./Aplicaciones/BibliotecaSQL/doc/");
		LOG.info("Obtenida la version "+label+"; Se ha escrito a el fichero:"+ ruta);		
		}
	private static void PruebaBorrar(AlfrescoWSClient interfaz, String uuid) throws RepositoryFault, RemoteException, ErrorAutenticacion {
		interfaz.borrarNodo(uuid);
		//if (res.compareToIgnoreCase("1") != 0)
		//	LOG.warn("El elemento no ha sido borrado");
		}
	private static String PruebaCrear(AlfrescoWSClient interfaz, boolean versionable) throws Exception {		
		String ruta 		= "C:/eclipse/workspace/Gotta8/Aplicaciones/BibliotecaSQL/doc/"; //E:\\TestWorkspace\\Gotta-Alfresco\\src\\es\\burke\\alfresco\\resources\\
		String nombrefich	= "virginia.doc";
		String rutaAlf		= "/app:company_home";
		
		return (String)interfaz.crearNodo(rutaAlf, nombrefich, ruta, versionable);
		}
	private static void PruebaInsertarMetadatos(AlfrescoWSClient interfaz, String uuid) throws Exception {
	    Map<String, String> par = new HashMap<String, String>();
	    par.put("Prueba Map 1 nombre sin", "Prueba Map 1 valor sin");
	    par.put("Prueba Map 2 nombre sin", "Prueba Map 2 valor sin");
	    par.put("Prueba Map 3 nombre sin", "Prueba Map 3 valor sin");

	    interfaz.insertarMetadatos(uuid, par);
		}
	private static void PruebaObtenerMetadatos(AlfrescoWSClient interfaz, String uuid) throws Exception {
		interfaz.obtenerMetadatos(uuid);
		}
	private static String PruebaObtenerMetadato(AlfrescoWSClient interfaz, String uuid) throws Exception {
		String nombre	= "Buscando";
		return (String)interfaz.obtenerMetadatos(uuid, nombre);
		}
	private static void PruebaBuscarDocumento(AlfrescoWSClient interfaz) throws Exception {
		String texto	= "manchaaa";
		//String texto	= "";
		
		Map<String, String> par = new HashMap<String, String>();
	    //par.put("Prueba", "ValorPrueba");
	    par.put("Buscando", "Refactor");
	    //par.put("2009", "Noviembre");
		interfaz.buscarDocumentos(texto, par);
	}
}
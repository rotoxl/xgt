package sw;

import java.net.MalformedURLException;
import java.net.URL;

import javax.naming.ConfigurationException;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;

public class SWFirmado {
	static ConfigurationContext ctx;
	static Options options;
	static ServiceClient sc;
	
	private static void prepara(String endPoint, String action) throws AxisFault, MalformedURLException, ConfigurationException{
		URL urlAxis2 = SWFirmado.class.getResource("/axis2/axis2.xml");
		URL urlPath =  SWFirmado.class.getResource("/axis2/");
		System.out.println(urlAxis2); System.out.println(urlPath);
		
		if ( urlAxis2==null || urlPath==null)
			throw new ConfigurationException("No se han encontrado la ruta a axis2.xml o a /axis2/");
		
		System.out.println("");
		System.out.println("Iniciamos preparación ws //////////////");
		System.out.println(urlAxis2); System.out.println(urlPath);
		
		System.out.println(" **** creamos ConfigurationContextFactory");
		ctx = ConfigurationContextFactory.createConfigurationContextFromURIs(urlAxis2, urlPath);
		
		System.out.println("...creado.  **** Ahora creamos options");
		options = new Options();
		options.setAction(action);
		options.setTo(new EndpointReference(endPoint));
		
		System.out.println("...creado.  **** Ahora creamos ServiceClient");
		sc = new ServiceClient(ctx, null);
		sc.setOptions(options);
		
		System.out.println("..creado");
		}
	public static OMElement llamaSincrono(String endPoint, String action, OMElement root) throws AxisFault, MalformedURLException, ConfigurationException {
		prepara(endPoint, action);
		return sc.sendReceive(root);
		}
//	public static void llamaAsincrono(String endPoint, String action, OMElement root) throws AxisFault, MalformedURLException, ConfigurationException{
//		prepara(endPoint, action);
//		
//		MyCallBack cb=new MyCallBack();
//		sc.sendReceiveNonBlocking(root, cb);
//		}
}

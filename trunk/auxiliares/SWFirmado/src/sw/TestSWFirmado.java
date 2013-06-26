package sw;


import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMText;

public class TestSWFirmado {
	public static void main(String[] args) throws Exception {
		OMFactory f = OMAbstractFactory.getOMFactory();
		OMElement root = f.createOMElement("root", "http:/test", "test");
		OMText value = f.createOMText("Texto de ejemplo");
		root.addChild(value);	
		
//		ponProxy();
		
		String endPoint="https://publicacion.preprod-contrataciondelestado.es:443/ServiciosPublicacionCODICE2";
		String action = "http://publicacion.b2b.contrataciondelestado.es/PublishContractAwardNotice";

		SWFirmado.llamaSincrono(endPoint, action, root);
		System.out.println("ok síncrono");
		
//		SWFirmado.llamaAsincrono(endPoint, action, root);
//		System.out.println("ok asíncrono");
		}
	private static void ponProxy(){
		System.setProperty("http.proxyHost", "172.21.0.103");
		System.setProperty("http.proxyPort", "8080");
	} 
}

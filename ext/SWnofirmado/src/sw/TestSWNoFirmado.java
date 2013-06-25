package sw;
import java.util.HashMap;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMText;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.auth.AuthScheme;
import org.apache.commons.httpclient.auth.CredentialsNotAvailableException;
import org.apache.commons.httpclient.auth.CredentialsProvider;
import org.apache.commons.httpclient.params.DefaultHttpParams;

public class TestSWNoFirmado {
	static SOAPFactory fac;
	static OMNamespace ns;
	static OMNamespace ns5;
	
	public static void main(String[] args) throws Exception {		
		OMElement root=creaXML();
//		ponProxy();
		
		String endPoint="http://desaregistro.redinterna.age:8080/serviciosWebAFirma/services/ValidarCertificado";
		String action = "";
		SWNoFirmado.llamaSincrono(endPoint, action, root);
		
		System.out.println("ok");
		}
	
//	private static OMElement creaXML(){
//		OMFactory f = OMAbstractFactory.getOMFactory();
//		OMElement root = f.createOMElement("mensajeEntrada", "http:/test", "test");
//		OMText value = f.createOMText("Texto de ejemplo");
//		root.addChild(value);
//		return root;
//	}
	private static OMElement creaXML(){
		String datosCertificadoBase64="MIIIDzCCBvegAwIBAgIJALsAAAAAAAABMA0GCSqGSIb3DQEBBQUAMIHgMQswCQYDVQQGEwJFUzEuMCwGCSqGSIb3DQEJARYfYWNfY2FtZXJmaXJtYV9jY0BjYW1lcmZpcm1hLmNvbTFDMEEGA1UEBxM6TWFkcmlkIChzZWUgY3VycmVudCBhZGRyZXNzIGF0IHd3dy5jYW1lcmZpcm1hLmNvbS9hZGRyZXNzKTESMBAGA1UEBRMJQTgyNzQzMjg3MRkwFwYDVQQKExBBQyBDYW1lcmZpcm1hIFNBMS0wKwYDVQQDEyRBQyBDYW1lcmZpcm1hIENlcnRpZmljYWRvcyBDYW1lcmFsZXMwHhcNMDcxMjEwMTUwMjA0WhcNMTIxMjA4MTUwMjA0WjCCAVsxCzAJBgNVBAYTAkVTMSwwKgYDVQQDFCNDZXJ0aWZpY2FkbyBQcnVlYmFzIFNvZnR3YXJlIFbhbGlkbzEiMCAGCSqGSIb3DQEJARYTaW5mb0BjYW1lcmZpcm1hLmNvbTESMBAGA1UEBRMJMTIzNDU2NzhaMRgwFgYDVQQEFA9Tb2Z0d2FyZSBW4WxpZG8xHDAaBgNVBCoTE0NlcnRpZmljYWRvIFBydWViYXMxGzAZBgorBgEEAYGHLh4DEwtFU0E5OTk5OTk5OTEWMBQGA1UEChMNQUMgQ2FtZXJmaXJtYTEVMBMGA1UECxMMRGVwYXJ0YW1lbnRvMQ4wDAYDVQQMEwVDYXJnbzFSMFAGA1UEDRNJQ2hhbWJlcnMgb2YgQ29tbWVyY2UgUXVhbGlmaWVkIENlcnRpZmljYXRlOiBOYXR1cmFsIFBlcnNvbiBDQU0tUEYtU1ctS1BTQzCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEAn3J774hSvM0Iu4l1aXZsmd2L86fQIBTp3CvBFf0fPlrbQWyn6CAExPDzWdVrMPrsue36sGCjeuwCvBDaC/VoFjVQF4Zm4y0cFaRN191zuCTl1RDnN3UxEilvh4V5vZGOK0/A4JcDKuvzw0OKlS7/qEU6Z6aU/sX6MYV578R6Eg8CAwEAAaOCA9AwggPMMAwGA1UdEwEB/wQCMAAwDgYDVR0PAQH/BAQDAgO4MCkGA1UdJQQiMCAGCCsGAQUFBwMCBggrBgEFBQcDBAYKKwYBBAGCNxQCAjARBglghkgBhvhCAQEEBAMCBaAwQgYJYIZIAYb4QgEDBDUWM1VSSTpodHRwOi8vY3JsLmNhbWVyZmlybWEuY29tL2FjX2NhbWVyZmlybWFfY2MuY2dpPzBGBglghkgBhvhCAQgEORY3VVJJOmh0dHA6Ly9jcHMuY2FtZXJmaXJtYS5jb20vY3BzL2FjX2NhbWVyZmlybWFfY2MuaHRtbDAxBglghkgBhvhCAQ0EJBYiQ0VSVElGSUNBRE8gQ1VBTElGSUNBRE8gLSBSQTogQ0M5OTAdBgNVHQ4EFgQUE1uUj4A0WxHykYacpCrRxvDtQnQweAYIKwYBBQUHAQEEbDBqMEAGCCsGAQUFBzAChjRodHRwOi8vd3d3LmNhbWVyZmlybWEuY29tL2NlcnRzL2FjX2NhbWVyZmlybWFfY2MuY3J0MCYGCCsGAQUFBzABhhpodHRwOi8vb2NzcC5jYW1lcmZpcm1hLmNvbTCBqwYDVR0jBIGjMIGggBS2H06dHGiRLjdyYOFGj1qlKjExuaGBhKSBgTB/MQswCQYDVQQGEwJFVTEnMCUGA1UEChMeQUMgQ2FtZXJmaXJtYSBTQSBDSUYgQTgyNzQzMjg3MSMwIQYDVQQLExpodHRwOi8vd3d3LmNoYW1iZXJzaWduLm9yZzEiMCAGA1UEAxMZQ2hhbWJlcnMgb2YgQ29tbWVyY2UgUm9vdIIBBTB2BgNVHR8EbzBtMDSgMqAwhi5odHRwOi8vY3JsLmNhbWVyZmlybWEuY29tL2FjX2NhbWVyZmlybWFfY2MuY3JsMDWgM6Axhi9odHRwOi8vY3JsMS5jYW1lcmZpcm1hLmNvbS9hY19jYW1lcmZpcm1hX2NjLmNybDAqBgNVHRIEIzAhgR9hY19jYW1lcmZpcm1hX2NjQGNhbWVyZmlybWEuY29tMIGSBgNVHSAEgYowgYcwgYQGDSsGAQQBgYcuCgkCAQEwczA/BggrBgEFBQcCARYzaHR0cDovL2Nwcy5jYW1lcmZpcm1hLmNvbS9jcHMvYWNfY2FtZXJmaXJtYV9jYy5odG1sMDAGCCsGAQUFBwICMCQaIkNFUlRJRklDQURPIENVQUxJRklDQURPIC0gUkE6IENDOTkwLwYIKwYBBQUHAQMEIzAhMAgGBgQAjkYBATAVBgYEAI5GAQIwCxMDRVVSAgEAAgEBMA0GCSqGSIb3DQEBBQUAA4IBAQB+UgF+t54k2gK8Ehe1tz6ivqxB0Gp9GpFtw0B/j1HGyP0OlqtRrPqSS2oLJfyzUlSlYcLcWqzPRqu+5diExaVcUG8ft6EcjEF614EcYhJjA5jGMSpU7S+dG+e/tO+aJf6bd/sfp1nm+TnPh0TbHJPv+gTaeUlH45AGHhTakluf9R8W2nx/HXOF5qD+p/KaV0+nfDh1KPQmPR5ima/AJW4HvvloHdmDAPgY4EUXjuqmmsP0wXhmvVU4eXJTnhMcjlNVZoix31BYB1OhsFeoWsDeHbki7CZYnUST18/cW4s+MGeZ+ajAp0IJGURn9IfRFSNRREqKglHDVN28kes51J0C"; 
		String idAplicacion="micinn.sgtic.re", modoValidacion="1", obtenerInfo="true";
			
		fac = OMAbstractFactory.getSOAP11Factory();
		ns = fac.createOMNamespace("", "");
	    ns5 = fac.createOMNamespace("xmlns", "https://afirmaws/ws/validacion");
	    
	    OMElement doc=_generaNodoXML(null, "mensajeEntrada", null, _creaDic("xmlns", "https://afirmaws/ws/validacion", 
	                          									"xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance",
	                          									"xsi:SchemaLocation","https://localhost/afirmaws/xsd/mvalidacion/ws.xsd"));
	    _generaNodoXML(doc, "peticion", "ValidarCertificado", null);
	    _generaNodoXML(doc, "versionMsg", "1.0", null);
	    
	    OMElement parametros=_generaNodoXML(doc, "parametros", null, null);
	    _generaNodoXML(parametros, "certificado", datosCertificadoBase64, null);
	    _generaNodoXML(parametros, "idAplicacion", idAplicacion, null);
	    _generaNodoXML(parametros, "modoValidacion", modoValidacion, null);
	    _generaNodoXML(parametros, "obtenerInfo",   obtenerInfo, null);
	    
	    return doc;
	}
	public static String _vacioANulo(String valor){
		if (valor==null)
			return null;
		else if (valor.equals(""))
			return null;
		return valor;
	}
	private static OMElement _generaNodoXML(OMElement padre, String tipo, String valor, HashMap<String, Object> dicAtributos){
		OMElement ret= fac.createOMElement(tipo, ns);
		
		if (dicAtributos!=null){
			for (String key:dicAtributos.keySet()){
				key=_vacioANulo(key);
				if (key!=null)
					ret.addAttribute(key, dicAtributos.get(key).toString(), ns);
				}
			}
	    valor=_vacioANulo(valor);
	    if (valor!=null)
	      ret.addChild( fac.createOMText( valor ) );
	      
	    if (padre!=null)
	      padre.addChild(ret);
	      
		return ret;
		}
	public static HashMap<String, Object> _creaDic(Object...p){
		HashMap<String, Object> ret= new HashMap<String, Object>();
		for (int i=0; i<p.length; i+=2)
			ret.put(p[i].toString(), p[i+1]);
		
		return ret;
		}
	
	private static void ponProxy(){
//		System.getProperties().setProperty("http.proxyHost", "10.20.5.72");
//		System.getProperties().setProperty("http.proxyPort", "8080");
//		System.getProperties().setProperty("http.proxyUser", "eduardo.lozano");
//		System.getProperties().setProperty("http.proxyPassword", "Elc2012elc");
		
//		System.setProperty("https.proxyHost", "10.20.5.72");
//		System.setProperty("https.proxyPort", "8080");
//		System.setProperty("https.proxyUser", "eduardo.lozano");
//		System.setProperty("https.proxyPassword", "Elc2012elc");
		
		final NTCredentials nt = new NTCredentials("fernando.verbo", "Fvj2011fvj", "", "redinterna");
    	final CredentialsProvider myCredentialsProvider = new CredentialsProvider() {
    		public Credentials getCredentials(final AuthScheme scheme, final String host, int port, boolean proxy) throws CredentialsNotAvailableException {
    			return nt; 
    		}
    	};
    	DefaultHttpParams.getDefaultParams().setParameter("http.authentication.credential-provider", myCredentialsProvider);
		}
	}

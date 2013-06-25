package es.burke.gotta;

import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

/* Instrucciones para habilitar el intercambio de certificados:
 *		 http://gottalab.com/ValidacionCertificadoElectronico
 * */
public class ValidacionPorCertificadoElectronico extends IValidacion {
//	private static SOAPFactory fac;
//	private static OMNamespace ns;
//	private static OMNamespace ns5;
	
	public ValidacionPorCertificadoElectronico(){
		this.requiereUsuarioyContrasenha=false;
		}
	@Override
	public String valida(Aplicacion apli, String usuGotta, String pwdGotta, HttpServletRequest request) throws ErrorUsuarioNoValido{
		Object obj=request.getAttribute("javax.servlet.request.X509Certificate");
		X509Certificate[] certs = (X509Certificate[]) obj; 
		
		if (certs==null)
			throw new ErrorUsuarioNoValido("No parece haber ningún certificado/dni-e válido en el navegador");
			
//		Util.ponProxy(apli);
//		
//		String datosCertificadoBase64, idAplicacion="micinn.sgtic.re", modoValidacion="1", obtenerInfo="0";
//		
//		try {
//			datosCertificadoBase64=Base64.encode(certs[0].getEncoded());
//			} 
//		catch (CertificateEncodingException e) {
//			throw new ErrorUsuarioNoValido(e.getMessage());
//			}
//		OMElement nodoXML=generaXML(idAplicacion, modoValidacion, obtenerInfo, datosCertificadoBase64);
//		
//		
//		String endPoint=apli.getDatoConfig("endPointValidacion");
//		String action = null;
//		OMElement respuestaSW;
//		try {
//			respuestaSW=SWNoFirmado.llamaSincrono(endPoint, action, nodoXML);
//			} 
//		catch (AxisFault e) {
//			throw new ErrorUsuarioNoValido(e.getMessage());
//			} 
//		catch (MalformedURLException e) {
//			throw new ErrorUsuarioNoValido(e.getMessage());
//			} 
//		catch (ConfigurationException e) {
//			throw new ErrorUsuarioNoValido(e.getMessage());
//			}
//
//		Document doc;
//		try {
//			doc = GottaXML.parseaXML(respuestaSW.toString());
//			} 
//		catch (ErrorAccionDLL e1) {
//			throw new ErrorUsuarioNoValido(e1.getMessage());
//			}
		
		
//		RESPUESTA si todo ha ido bien
//		<?xml version="1.0" encoding="UTF-8"?>
//		<mensajeSalida xmlns="http://afirmaws/ws/validacion" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://afirmaws/ws/validacion https://des-afirma.redinteradministrativa.es/afirmaws/xsd/mvalidacion/ws.xsd ">
//		  <peticion>ValidarCertificado</peticion>
//		  <versionMsg>1.0</versionMsg>
//		  <respuesta>
//		    <ResultadoProcesamiento>
//		      <InfoCertificado>
//		        <Campo>
//		          <idCampo>usoCertificado</idCampo>
//		          <valorCampo>digitalSignature | keyEncipherment</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>ApellidosResponsable</idCampo>
//		          <valorCampo>AAAAAAAA BBBBBBB</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>validoHasta</idCampo>
//		          <valorCampo>2007-11-30 vie 15:56:15 +0100</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>politica</idCampo>
//		          <valorCampo>1.3.6.1.4.1.5734.3.5</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>subject</idCampo>
//		          <valorCampo>CN=NOMBRE AAAAAAAA BBBBBBB CCCCCC - NIF 12345678A,OU=703015241,OU=FNMT Clase 2 CA,O=FNMT,C=ES</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>tipoCertificado</idCampo>
//		          <valorCampo>FNMT PF</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>versionPolitica</idCampo>
//		          <valorCampo>1</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>OrganizacionEmisora</idCampo>
//		          <valorCampo>FNMT</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>idPolitica</idCampo>
//		          <valorCampo>MITyC</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>NIFResponsable</idCampo>
//		          <valorCampo>12345678A</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>numeroSerie</idCampo>
//		          <valorCampo>1014860420</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>nombreResponsable</idCampo>
//		          <valorCampo>CCCCCC</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>idEmisor</idCampo>
//		          <valorCampo>OU=FNMT Clase 2 CA,O=FNMT,C=ES</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>email</idCampo>
//		          <valorCampo/>
//		        </Campo>
//		        <Campo>
//		          <idCampo>clasificacion</idCampo>
//		          <valorCampo>0</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>validoDesde</idCampo>
//		          <valorCampo>2004-11-30 mar 15:56:15 +0100</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>NombreApellidosResponsable</idCampo>
//		          <valorCampo>CCCCCC AAAAAAAA BBBBBBB</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>segundoApellidoResponsable</idCampo>
//		          <valorCampo>BBBBBBB</valorCampo>
//		        </Campo>
//		        <Campo>
//		          <idCampo>primerApellidoResponsable</idCampo>
//		          <valorCampo>AAAAAAAA</valorCampo>
//		        </Campo>
//		      </InfoCertificado>
//		      <ResultadoValidacion>
//		        <resultado>1</resultado>
//		        <descripcion>El certificado no paso la validacion</descripcion>
//		        <ValidacionSimple>
//		          <codigoResultado>1</codigoResultado>
//		          <descResultado>Certificado caducado</descResultado>
//		          <excepcion>java.security.cert.CertificateExpiredException</excepcion>
//		        </ValidacionSimple>
//		      </ResultadoValidacion>
//		    </ResultadoProcesamiento>
//		  </respuesta>
//		</mensajeSalida>

//		String expresionXPath="";
//		try {
//			Node nodo=GottaXML.sacaNodo(doc, expresionXPath);
//			} 
//		catch (ErrorAccionDLL e) {
//			throw new ErrorUsuarioNoValido(e.getMessage());
//			}
		return "xx";
		}
//	private void imprimeInfoCertificados(X509Certificate[] certs){
//		for (int i = 0; i < certs.length; i++) {
//		System.out.println("===========================");
//		X509Certificate cert = certs[i]; 
//		System.out.println("Cert number: " + i); 
//		Principal dn = cert.getSubjectDN(); 
//	    System.out.println("SubjectDN: " + dn.toString() ); 
//			Principal issuer = cert.getIssuerDN(); 
//			System.out.println("IssuerDN: " + issuer.toString()); 
//			try { 
//				cert.checkValidity(); 
//				System.out.println("Validity: certificate is valid"); 
//				} 
//			catch (CertificateExpiredException ex) { 
//	    	System.out.println("Validity: certificate is expired"); 
//				} 
//			catch (CertificateNotYetValidException ex) { 
//				System.out.println("Validity: certificate is not yet valid"); 
//				} 
//			Date from = cert.getNotBefore(); 
//			System.out.println("From " + from); 
//			Date to = cert.getNotAfter(); 
//	    System.out.println(" to " + to);   
//		}
//	private OMElement generaXML(String idAplicacion, String modoValidacion, String obtenerInfo, String datosCertificadoBase64){
//		
//		fac = OMAbstractFactory.getSOAP11Factory();
//		ns = fac.createOMNamespace("", "");
//	    ns5 = fac.createOMNamespace("xmlns", "https://afirmaws/ws/validacion");
//	    
//	    OMElement doc=_generaNodoXML(null, "mensajeEntrada", null, Util.creaDic("xmlns", "https://afirmaws/ws/validacion", 
//	                          									"xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance",
//	                          									"xsi:SchemaLocation","https://localhost/afirmaws/xsd/mvalidacion/ws.xsd"));
//	    _generaNodoXML(doc, "peticion", "ValidarCertificado", null);
//	    _generaNodoXML(doc, "versionMsg", "1.0", null);
//	    
//	    OMElement parametros=_generaNodoXML(doc, "parametros", null, null);
//	    _generaNodoXML(parametros, "certificado", datosCertificadoBase64, null);
//	    _generaNodoXML(parametros, "idAplicacion", idAplicacion, null);
//	    _generaNodoXML(parametros, "modoValidacion", modoValidacion, null);
//	    _generaNodoXML(parametros, "obtenerInfo",   obtenerInfo, null);
//	    
//	    return doc;
//		}
//	private OMElement _generaNodoXML(OMElement padre, String tipo, String valor, HashMap<String, Object> dicAtributos){
//		OMElement ret= fac.createOMElement(tipo, this.ns);
//		
//		if (dicAtributos!=null){
//			for (String key:dicAtributos.keySet()){
//				key=Util.vacioANulo(key);
//				if (key!=null)
//					ret.addAttribute(key, dicAtributos.get(key).toString(), ns);
//				}
//			}
//	    valor=Util.vacioANulo(valor);
//	    if (valor!=null)
//	      ret.addChild( fac.createOMText( valor ) );
//	      
//	    if (padre!=null)
//	      padre.addChild(ret);
//	      
//		return ret;
//		}
}

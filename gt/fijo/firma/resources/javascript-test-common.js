
function setOutput(v) {
   document.getElementById("ta_out").value = v;
}

function copy_OutputToInput() {
   var v = document.getElementById("ta_out").value;
   document.getElementById("ta_in").value = v;
   document.getElementById("ta_out").value = "";
}

function clear() {
   document.getElementById("ta_in").value = "";
   setOutput("");
}
// -----------------------------------------------------------------------------

function cargaFiltros() {
   try {
      var _alias = document.getElementById("inNombre").value;
      var _emisor = document.getElementById("inEmisor").value;
      var _serial = document.getElementById("inNSerie").value;
      var _huella = document.getElementById("inHuella").value;
      var _keyusage = "";
	  document.applet_01.iniciar(_alias, _emisor, _serial, _huella, _keyusage);
      setOutput("Establecido filtro de certificados.");		
   }catch(e) { alert(e); setOutput(""); }
}

// -----------------------------------------------------------------------------

function test_getVersion() {
   try {
      var v = document.applet_01.getVersion();
      document.getElementById("ta_in").value = "";
      setOutput("Applet version: " + v);
   }catch(e) { alert(e); setOutput(""); }
}
function test_getcertificate_login() {
   try {
      var v = document.applet_01.getCertificadoFirma();
      document.getElementById("ta_in").value = "";
      setOutput(v);
   }catch(e) { alert(e); setOutput(""); }
}
function test_setcertificate_login() {
   try {
      var tmp = document.getElementById("ta_in").value;
      setOutput("");
      document.applet_01.setCertificadoFirma(tmp);
      setOutput("Establecido el certificado de usuario");
   }catch(e) { alert(e); setOutput(""); }
}
function test_getcertificates() {
   try {
      certificateList = document.applet_01.getLstCertificados();
      document.getElementById("ta_in").value = "";
      setOutput(certificateList);
   }catch(e) { alert(e); setOutput(""); }
}
// -----------------------------------------------------------------------------
function test_b64() {
   try {
      var txtInput = document.getElementById("ta_in").value;
      var operation = document.getElementById("b64_operation").selectedIndex;   
      var v = operation == 0 ?
         document.applet_01.b64Codificartexto(txtInput) :
         document.applet_01.strDecodificarTextoB64(txtInput);
      setOutput(v);
   } catch(e) { alert(e); setOutput(""); }
}
// -----------------------------------------------------------------------------
function test_b64HashTexto() {
   try {
      var txtInput = document.getElementById("ta_in").value;
      var algIndex = document.getElementById("hash_algorithm_texto").selectedIndex;
      var algorithm = algIndex == 0 ? "MD5" : "SHA1";
      var hash = document.applet_01.b64HashTexto(txtInput, algorithm);
      document.getElementById("ta_out").value = hash;
   }catch(e) { alert(e); setOutput(""); }
}
function test_b64HashDatosB64() {
   try {
      var txtInput = document.getElementById("ta_in").value;
      var algIndex = document.getElementById("hash_algorithm_b64").selectedIndex;
      var algorithm = algIndex == 0 ? "MD5" : "SHA1";
      var hash = document.applet_01.b64HashDatosB64(txtInput, algorithm);
      document.getElementById("ta_out").value = hash;
   }catch(e) { alert(e); setOutput(""); }
}
function test_b64HashFichero() {
   try {
      var algIndex = document.getElementById("hash_algorithm_file").selectedIndex;
      var algorithm = algIndex == 0 ? "MD5" : "SHA1";
      var filename = document.getElementById("ta_in").value;
      var hash = document.applet_01.b64HashFichero(filename, algorithm);
      document.getElementById("ta_out").value = hash;
   }catch(e) { alert(e); setOutput(""); }
}
function test_hash_file_prepare() {
	try {
		document.getElementById("ta_in").value = "c:/windows/notepad.exe";
		document.getElementById("ta_out").value = "";
	}catch(e) { alert(e); setOutput(""); }
}


// -----------------------------------------------------------------------------
function test_xades_xml() {
   try {
      var _xml = document.getElementById("ta_in").value;
      var _idsig = document.getElementById("xades_xml_idsignature").value;
      var _idref = document.getElementById("xades_xml_idreference").value;
      var _role = document.getElementById("xades_xml_role").value;
      var _formato = "XADES_BES";
      var _tipo = "UNDEFINED";
      switch(document.getElementById("xades_xml_type").selectedIndex) {
      case 0: _tipo = "ENVELOPED"; break;
      case 1: _tipo = "ENVELOPING"; break;
      case 2: _tipo = "DETACHED"; break;
      }
      setOutput("Signing document...");
      var sig = document.applet_01.xmlFirmaXDSXML(_xml, _role, _idref, _idsig, _formato, _tipo);
      document.getElementById("ta_out").value = sig;
   }catch(e) { alert(e); setOutput(""); }
}
function prepare_test_xades_xml() {
   var x = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" 
      + "<data id=\"idReference\">\n"
      + "  <name>test</name>\n"
      + "  <dni>70444111</dni>\n"
      + "</data>";
   document.getElementById("ta_in").value = x;
   document.getElementById("xades_xml_idsignature").value = "idSignature";
   document.getElementById("xades_xml_idreference").value = "idReference";
   document.getElementById("xades_xml_role").value = "tester";
}
// -----------------------------------------------------------------------------
function test_xades_text() {
   try {
      var _txt = document.getElementById("ta_in").value;
      var _idsig = document.getElementById("xades_text_idsignature").value;
      var _idref = document.getElementById("xades_text_idreference").value;
      var _role = document.getElementById("xades_text_role").value;
      var _formato = "XADES_BES";
      var _tipo = "UNDEFINED";
      switch(document.getElementById("xades_text_type").selectedIndex) {
      case 0: _tipo = "ENVELOPED"; break;
      case 1: _tipo = "ENVELOPING"; break;
      case 2: _tipo = "DETACHED"; break;
      }
      setOutput("Signing text...");
      var sig = document.applet_01.xmlFirmaXDSTexto(_txt, _role, _idref, _idsig, _formato, _tipo);
      document.getElementById("ta_out").value = sig;
   }catch(e) { alert(e); setOutput(""); }
}
function prepare_test_xades_text() {
   document.getElementById("ta_in").value = "Sample message";
   document.getElementById("xades_text_idsignature").value = "idSignature";
   document.getElementById("xades_text_idreference").value = "idRef";
   document.getElementById("xades_text_role").value = "tester";
}
// -----------------------------------------------------------------------------
function test_xades_b64() {
   try {
      var _txt = document.getElementById("ta_in").value;
      var _idsig = document.getElementById("xades_b64_idsignature").value;
      var _idref = document.getElementById("xades_b64_idreference").value;
      var _role = document.getElementById("xades_b64_role").value;
      var _formato = "XADES_BES";
      setOutput("Signing binary data...");      
      var _tipo = "UNDEFINED";
      switch(document.getElementById("xades_b64_type").selectedIndex) {
      case 0: _tipo = "ENVELOPED"; break;
      case 1: _tipo = "ENVELOPING"; break;
      case 2: _tipo = "DETACHED"; break;
      }
      var sig = document.applet_01.xmlFirmaXDSDatosB64(_txt, _role, _idref, _idsig, _formato, _tipo);
      document.getElementById("ta_out").value = sig;
   }catch(e) { alert(e); setOutput(""); }
}

function prepare_test_xades_b64() {
   document.getElementById("ta_in").value = "VGVzdCBtZXNzYWdlLg==";
   document.getElementById("xades_b64_idsignature").value = "idSignature";
   document.getElementById("xades_b64_idreference").value = "idRef";
   document.getElementById("xades_b64_role").value = "tester";
}
// -----------------------------------------------------------------------------
function test_xades_file() {
   try {
      var _file = document.getElementById("xades_file_file").value;
      var _idsig = document.getElementById("xades_file_idsignature").value;
      var _idref = document.getElementById("xades_file_idreference").value;
      var _role = document.getElementById("xades_file_role").value;
      var _formato = "XADES_BES";
      var _tipo = "UNDEFINED";
      switch(document.getElementById("xades_file_type").selectedIndex) {
      case 0: _tipo = "ENVELOPED"; break;
      case 1: _tipo = "ENVELOPING"; break;
      case 2: _tipo = "DETACHED"; break;
      }
      setOutput("Signing file...");
      var sig = document.applet_01.xmlFirmaXDSFichero(_file, _role, _idref, _idsig, _formato, _tipo);
      document.getElementById("ta_out").value = sig;
   }catch(e) { alert(e); setOutput(""); }
}
// -----------------------------------------------------------------------------
function test_xades_url() {
   try {
      var _idsig = document.getElementById("xades_url_idsignature").value;
      var _idref = document.getElementById("xades_url_idreference").value;
      var _role = document.getElementById("xades_url_role").value;
      var _formato = "XADES_BES";
      var _tipo = "UNDEFINED";
      switch(document.getElementById("xades_url_type").selectedIndex) {
      case 0: _tipo = "ENVELOPED"; break;
      case 1: _tipo = "ENVELOPING"; break;
      case 2: _tipo = "DETACHED"; break;
      }
      var _url = document.getElementById("xades_url_url").value;
      setOutput("Downloading and signing file...");
      var sig = document.applet_01.xmlFirmaXSDXMLUrl(_url, _role, _idref, _idsig, _formato, _tipo);
      document.getElementById("ta_out").value = sig;
   }catch(e) { alert(e); setOutput(""); }
}
function prepare_test_xades_url() {
   document.getElementById("xades_url_url").value = "http://www.facturae.es/NR/rdonlyres/DB04EF8B-301E-459B-8852-6193579E4FEF/0/factura2_ejemplo1.xml";
   document.getElementById("xades_url_idsignature").value = "TEST_ID";
   document.getElementById("xades_url_idreference").value = "Certificate1";
   document.getElementById("xades_url_role").value = "Tester";
   document.getElementById("ta_in").value = "";
   setOutput("");
}
// -----------------------------------------------------------------------------
function test_xades_rawurl() {
   try {
      var _idsig = document.getElementById("xades_rawurl_idsignature").value;
      var _idref = document.getElementById("xades_rawurl_idreference").value;
      var _role = document.getElementById("xades_rawurl_role").value;
      var _formato = "XADES_BES";
      var _tipo = "UNDEFINED";
      switch(document.getElementById("xades_rawurl_type").selectedIndex) {
      case 0: _tipo = "ENVELOPED"; break;
      case 1: _tipo = "ENVELOPING"; break;
      case 2: _tipo = "DETACHED"; break;
      }
      var _url = document.getElementById("xades_rawurl_url").value
      setOutput("Downloading and signing file...");
      var sig = document.applet_01.xmlFirmaXDSUrl(_url, _role, _idref, _idsig, _formato, _tipo);
      document.getElementById("ta_out").value = sig;
   }catch(e) { alert(e); setOutput(""); }
}
function prepare_test_xades_rawurl() {
   document.getElementById("xades_rawurl_url").value = "http://google.es";
   document.getElementById("xades_rawurl_idsignature").value = "IdSignature";
   document.getElementById("xades_rawurl_idreference").value = "IdReference";
   document.getElementById("xades_rawurl_role").value = "Tester";
   document.getElementById("ta_in").value = "";
   setOutput("");
}
// -----------------------------------------------------------------------------
function test_xades_signature() {
   try {
	  var _txt = document.getElementById("ta_in").value;
	  var _idsig = document.getElementById("xades_signature_idsignature").value;
	  var _idref = document.getElementById("xades_signature_idreference").value;
	  var _role = document.getElementById("xades_signature_role").value;
	  var _formato = "XADES_BES";
	  var _tipo = "UNDEFINED";
      switch(document.getElementById("xades_signature_type").selectedIndex) {
      case 0: _tipo = "ENVELOPED"; break;
      case 1: _tipo = "ENVELOPING"; break;
      case 2: _tipo = "DETACHED"; break;
      }
	  setOutput("Signing document...");
	  
	  alert(_formato);
	  
	  
	  var sig = document.applet_01.xmlFirmaXDSSerie(_txt, _role, _idref, _idsig, _formato, _tipo);
	  document.getElementById("ta_out").value = sig;
	} catch(e) { alert(e); setOutput(""); }
}
function prepare_test_xades_signature() {

   var _xml =
      "<data>\n" +
	  "  <user Id=\"target\">\n" +
      "    <name>John Doe</name>\n" +
      "    <roles>\n" +
      "      <role>Admin</role>\n" +
      "      <role>User</role>\n" +
      "    </roles>\n" +
      "    <param name=\"test\" value=\"testvalue\"/>\n" +
      "  </user>\n" +
	  "</data>";
	var signature01 = document.applet_01.xmlFirmaXDSXML(_xml, "signer01", "target", "signature_01", "XADES_BES", "ENVELOPING");
	document.getElementById("ta_in").value = signature01;
	document.getElementById("xades_signature_idsignature").value = "signature_02";
	document.getElementById("xades_signature_idreference").value = "signature_01";
	document.getElementById("xades_signature_role").value = "signer_02";
	setOutput("");
 }
//-----------------------------------------------------------------------------
function test_xades_prefirma() {
	try {
		document.getElementById("ta_out").value = "Signing...";
		var _digest = document.getElementById("ta_in").value
		var _idsig = document.getElementById("xades_prefirma_idsignature").value
		var _idref = document.getElementById("xades_prefirma_idreference").value
		var _role = document.getElementById("xades_prefirma_role").value;
		var _subtype;
		switch(document.getElementById("xades_prefirma_type").selectedIndex) {
	      case 0: _tipo = "ENVELOPED"; break;
	      case 1: _tipo = "ENVELOPING"; break;
	      case 2: _tipo = "DETACHED"; break;
	      }
		var sig = document.applet_01.generarFirmaXades(_digest, _idsig, _role, _idref, _subtype);
		document.getElementById("ta_out").value = sig;
	}catch(e) { alert(e); setOutput("");  }
}
function prepare_test_xades_prefirma() {
	document.getElementById("ta_in").value = "Dgfhjf/qIbFcPOd05ZtX7bYz9V0=";
	document.getElementById("xades_prefirma_idsignature").value = "idsignature";
	document.getElementById("xades_prefirma_role").value = "tester";
}
// -----------------------------------------------------------------------------
function test_xmldsig_xml() {
   try {
      var _xml = document.getElementById("ta_in").value;
      var _idsig = document.getElementById("xmldsig_xml_idsignature").value;
      var _idref = document.getElementById("xmldsig_xml_idreference").value;
      var _role = "";
      var _formato = "XMLDSIG";
      var _tipo = "UNDEFINED";
      switch(document.getElementById("xmldsig_xml_type").selectedIndex) {
      case 0: _tipo = "ENVELOPED"; break;
      case 1: _tipo = "ENVELOPING"; break;
      case 2: _tipo = "DETACHED"; break;
      }
      var sig = document.applet_01.xmlFirmaXDSXML(_xml, _role, _idref, _idsig, _formato, _tipo);
      document.getElementById("ta_out").value = sig;
   } catch(e) { alert(e); setOutput(""); }
}
function prepare_test_xmldsig_xml() {
   var _xml =
      "<data>\n" +
	  "  <user Id=\"target\">\n" +
      "    <name>John Doe</name>\n" +
      "    <roles>\n" +
      "      <role>Admin</role>\n" +
      "      <role>User</role>\n" +
      "    </roles>\n" +
      "    <param name=\"test\" value=\"testvalue\"/>\n" +
      "  </user>\n" +
	  "</data>";
   document.getElementById("ta_in").value = _xml;
   document.getElementById("xmldsig_xml_idsignature").value = "idsignature";
   document.getElementById("xmldsig_xml_idreference").value = "target";
   document.getElementById("xmldsig_xml_type").selectedIndex = 0;
}
// -----------------------------------------------------------------------------
function test_xmldsig_text() {
   try {
      var _xml = document.getElementById("ta_in").value;
      var _idsig = document.getElementById("xmldsig_text_idsignature").value;
      var _idref = document.getElementById("xmldsig_text_idreference").value;
      var _role = "";
      var _formato = "XMLDSIG";
      var _tipo = "UNDEFINED";
      switch(document.getElementById("xmldsig_text_type").selectedIndex) {
      case 0: _tipo = "ENVELOPED"; break;
      case 1: _tipo = "ENVELOPING"; break;
      case 2: _tipo = "DETACHED"; break;
      }
      var sig = document.applet_01.xmlFirmaXDSTexto(_xml, _role, _idref, _idsig, _formato, _tipo);
      document.getElementById("ta_out").value = sig;
   } catch(e) { alert(e); setOutput(""); }
}
function prepare_test_xmldsig_text() {
   var _xml =
      "Este es un mensaje de prueba.";
   document.getElementById("ta_in").value = _xml;
   document.getElementById("xmldsig_text_idsignature").value = "idsignature";
   document.getElementById("xmldsig_text_idreference").value = "idreference";
   document.getElementById("xmldsig_text_type").selectedIndex = 0;
}
// -----------------------------------------------------------------------------
function test_xmldsig_b64() {
   try {
      var _xml = document.getElementById("ta_in").value;
      var _idsig = document.getElementById("xmldsig_b64_idsignature").value;
      var _idref = document.getElementById("xmldsig_b64_idreference").value;
      var _role = "";
      var _formato = "XMLDSIG";
      var _tipo = "UNDEFINED";
      switch(document.getElementById("xmldsig_b64_type").selectedIndex) {
      case 0: _tipo = "ENVELOPED"; break;
      case 1: _tipo = "ENVELOPING"; break;
      case 2: _tipo = "DETACHED"; break;
      }
      var sig = document.applet_01.xmlFirmaXDSDatosB64(_xml, _role, _idref, _idsig, _formato, _tipo);
      document.getElementById("ta_out").value = sig;
   } catch(e) { alert(e); setOutput(""); }
}
function prepare_test_xmldsig_b64() {
   var _xml = "RXN0ZSBlcyB1biBtZW5zYWplIGRlIHBydWViYS4=";
   document.getElementById("ta_in").value = _xml;
   document.getElementById("xmldsig_b64_idsignature").value = "idsignature";
   document.getElementById("xmldsig_b64_idreference").value = "idreference";
   document.getElementById("xmldsig_b64_type").selectedIndex = 0;
}

// -----------------------------------------------------------------------------
function test_xmldsig_signature() {
   try {
      var _xml = document.getElementById("ta_in").value;
      var _idsig = document.getElementById("xmldsig_signature_idsignature").value;
      var _idref = document.getElementById("xmldsig_signature_idreference").value;
      var _role = "";
      var _formato = "XMLDSIG";
      var _tipo = "UNUSED";
      var sig = document.applet_01.xmlFirmaXDSSerie(_xml, _role, _idref, _idsig, _formato, _tipo);
      document.getElementById("ta_out").value = sig;
   } catch(e) { alert(e); setOutput(""); }
}
function prepare_test_xmldsig_signature() {
var _xml =
"<user Id=\"idreference\">\n" +
"  <name>John Doe</name>\n" +
"  <roles>\n" +
"    <role>Admin</role>\n" +
"    <role>User</role>\n" +
"  </roles>\n" +
"  <param name=\"test\" value=\"testvalue\"></param>\n" +
"<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"idsignature\">\n" +
"<ds:SignedInfo>\n" +
"<ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"></ds:CanonicalizationMethod>\n" +
"<ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"></ds:SignatureMethod>\n" +
"<ds:Reference URI=\"\">\n" +
"<ds:Transforms>\n" +
"<ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"></ds:Transform>\n" +
"<ds:Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments\"></ds:Transform>\n" +
"</ds:Transforms>\n" +
"<ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"></ds:DigestMethod>\n" +
"<ds:DigestValue>LvO6BJBinuwmj6JgRedaV/YhzQo=</ds:DigestValue>\n" +
"</ds:Reference>\n" +
"</ds:SignedInfo>\n" +
"<ds:SignatureValue>\n" +
"csggnneabIiopVynkRkAV8ime4aMzm90QAaUTSyGJJAK60LYKYNsKSN2t69gnijkCJJIOj5qrIIa\n" +
"gfM6zPvb2E9Kb2jAh32nl+T6rRlDghg+CjHIvPWVBUXaRFktZQnhoYfvjWZI23PBK4ogoXL4kBqN\n" +
"ZMRaQ2TkmtEzorBG6G26cSjBaIJR4O3P27ITKI1eYSc7vrCyJIagmUWL+NIIaF577ZJlbmqrBTUD\n" +
"QujhobfpblC06uohz6qUNzRJhD/Sk1h3lG4EuNN1FsC/sh16abnLLFV9emgVT5RH+tx1DAHGypzb\n" +
"8C8vd57hAo87cP1/FEx0+pEPCJqn87rPWgljfQ==\n" +
"</ds:SignatureValue>\n" +
"<ds:KeyInfo>\n" +
"<ds:X509Data>\n" +
"<ds:X509Certificate>\n" +
"MIIDszCCApugAwIBAgIBBDANBgkqhkiG9w0BAQUFADCBqTELMAkGA1UEBhMCU1AxDzANBgNVBAgT\n" +
"Bk1hZHJpZDEPMA0GA1UEBxMGTWFkcmlkMRowGAYDVQQKExFQbGF0YWZvcm1hZGVGaXJtYTEaMBgG\n" +
"A1UECxMRUGxhdGFmb3JtYWRlRmlybWExGjAYBgNVBAMTEVBsYXRhZm9ybWFkZUZpcm1hMSQwIgYJ\n" +
"KoZIhvcNAQkBFhVpcGVyZXpAZ3J1cG9idXJrZS5jb20wHhcNMDgwNDE0MTMwMjM0WhcNMDkxMjA1\n" +
"MTMwMjM0WjBLMSEwHwYKCZImiZPyLGQBGRYRUGxhdGFmb3JtYWRlRmlybWExDjAMBgNVBAsTBUxU\n" +
"U3lzMRYwFAYDVQQDEw1NYXJpYmVsIFBlcmV6MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKC\n" +
"AQEA04srtZj2b2fR+YBuKYoaagDfurCR7dwnw7V1AUTaEPeC8wwilQJhQcXfNd9RyKCOs53YvB22\n" +
"13zetPFl7SeLlIQy/AoGJqr/vxLSFl1QywMTPMxRzvPkeo+0ALlVBHzkw/peoNtjjNyMAufYiH4Y\n" +
"CiDO8gXvG8PJWC/SRJu5M3/5jvIj0j6w1F9rqIsFjBdZ+QeNYbvd57wOlAl8QrLmNVLDEkoF9xzb\n" +
"xDxFxpsU5vADDRhyVl+rBj14WAxkqzsBGN+ChKUiAnf7bWEUyQ/RKnj27Y6kWN+9FIn56HDaLb+H\n" +
"l0A/KhGRbGUrin67E+v63Q3n+1clJhOpdFnRMU015wIDAQABo0MwQTAMBgNVHRMBAf8EAjAAMDEG\n" +
"A1UdJQQqMCgGCCsGAQUFBwMCBggrBgEFBQcDBAYIKwYBBQUHAwMGCCsGAQUFBwMIMA0GCSqGSIb3\n" +
"DQEBBQUAA4IBAQBHVfec+0HXZ4R84y/J4fzC9CHKu2biMhReegQg41j4ba+suRItcNvSliivPtOA\n" +
"Zp/Q+Oz0mu4Y3LA/HsYyW+86McJ667b3a61obEHs8tWqTBZ9maQ5eMDIt7vge01j0j9gqBVo9+Du\n" +
"/uSJU8wfTDtdOWnYQdS5r3JDF65m8N+/jBTqRmi2E2XlSHkJFJ/G7h/nfLngLaXUYdFTkfVP60CS\n" +
"W32c0pP3Wh0jj+GA9dgSBDfMohDVN6tjOuuSfoKsFzgmjc+jix/ySGYRLkVfYQlMDoPRiP0TlOCV\n" +
"Xjoy3NfaqjdevEwiO99ni7nWqEn4lboSuuJt0fH7cPhkO+QKOs1d\n" +
"</ds:X509Certificate>\n" +
"</ds:X509Data>\n" +
"</ds:KeyInfo>\n" +
"<ds:Object><ds:Signature Id=\"idcountersignature\">\n" +
"<ds:SignedInfo>\n" +
"<ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"></ds:CanonicalizationMethod>\n" +
"<ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"></ds:SignatureMethod>\n" +
"<ds:Reference URI=\"#idsignature\">\n" +
"<ds:Transforms>\n" +
"<ds:Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments\"></ds:Transform>\n" +
"</ds:Transforms>\n" +
"<ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"></ds:DigestMethod>\n" +
"<ds:DigestValue>+AkdbPbuqoPkA6iEXtmVIKFyjCc=</ds:DigestValue>\n" +
"</ds:Reference>\n" +
"</ds:SignedInfo>\n" +
"<ds:SignatureValue>\n" +
"T9XzG+uXfJ2j7wx/+qBOsIkpvBeu6qwhWZErq6wkwExqPIPPYrXy7iy40IkMQnE8yowceTnr4dTv\n" +
"B6TZVhZSRn4KEK+8VnM7iyLQjL9NFzuDohNHnVETIV64KxElYdfpjI27pq75yPp6lIHiqqb7TgTt\n" +
"AOkC9s8y+v1Gc1QLmsE=\n" +
"</ds:SignatureValue>\n" +
"<ds:KeyInfo>\n" +
"<ds:X509Data>\n" +
"<ds:X509Certificate>\n" +
"MIICgDCCAekCBEgqubYwDQYJKoZIhvcNAQEFBQAwgYYxFTATBgNVBC4TDEROIFFVQUxJRklFUjEO\n" +
"MAwGA1UEDAwFQlVSS0UxCzAJBgNVBAYTAkVTMQ8wDQYDVQQIDAZNQURSSUQxDzANBgNVBAcMBk1B\n" +
"RFJJRDEOMAwGA1UECgwFQlVSS0UxDjAMBgNVBAsMBUJVUktFMQ4wDAYDVQQDDAVCVVJLRTAeFw0w\n" +
"ODA1MTQxMDA2NDZaFw0xMTA1MTQxMDA2NDZaMIGGMRUwEwYDVQQuEwxETiBRVUFMSUZJRVIxDjAM\n" +
"BgNVBAwMBUJVUktFMQswCQYDVQQGEwJFUzEPMA0GA1UECAwGTUFEUklEMQ8wDQYDVQQHDAZNQURS\n" +
"SUQxDjAMBgNVBAoMBUJVUktFMQ4wDAYDVQQLDAVCVVJLRTEOMAwGA1UEAwwFQlVSS0UwgZ8wDQYJ\n" +
"KoZIhvcNAQEBBQADgY0AMIGJAoGBAIc/7n+ti8ImzDR289it0aFoPDg11If5fGyFTBc91TCrgl/c\n" +
"1yDEgFwDIj1vSSPsNrkVrsG14ez5FKiCUp1/l6VITHPcVvfgaamrey5OjDr5vnpMAa7bqdoNfIcf\n" +
"Jc0IgJTrLIeDig4+KP3JkGvo/vYyaUgQpHRyp7AF7DXPom6FAgMBAAEwDQYJKoZIhvcNAQEFBQAD\n" +
"gYEATIkdU+UUGk95vynIKxBVlFz/nc7CSriaG3A/rNu0Qz/Da5+XzlilA07xH+19N/Ul7xMN7vLe\n" +
"yPeWzX6cJKkB0/ohltU5Uzf+djL8MTui15MHWhXDaNJBTe8H9S/r6/wcIgYYG6t+e1YTRpVG/NY9\n" +
"OvZyWHD7Y1/Y/TJ9aqbliz8=\n" +
"</ds:X509Certificate>\n" +
"</ds:X509Data>\n" +
"</ds:KeyInfo>\n" +
"</ds:Signature></ds:Object></ds:Signature>\n" +
"</user>\n";
   document.getElementById("ta_in").value = _xml;
   document.getElementById("xmldsig_signature_idsignature").value = "idcountersignature";
   document.getElementById("xmldsig_signature_idreference").value = "idsignature";
   document.getElementById("xmldsig_signature_type").selectedIndex = 0;
}

// -----------------------------------------------------------------------------
function test_pdf() {
   try {
      document.getElementById("ta_in").value = "";
      document.getElementById("ta_out").value = "Signing...";
	   var _file_in = document.getElementById("pdf_in").value;
	   var _subject = "no utilizado";
	   var _place = document.getElementById("pdf_place").value;
	   var _reason = document.getElementById("pdf_reason").value;
	   var _contact = "no utilizado";
	   var _file_out = document.getElementById("pdf_out").value;
	   var _visible = true;
	   var _coordenadas = "100;100;500;200";
	   var _page = 1;
	   var sig = document.applet_01.firmaPDF(_file_in, _subject, _place, _reason,
	     _contact, _file_out, _visible, _coordenadas, _page);
	   document.getElementById("ta_out").value = "Pdf signed. Response=" + sig;
	}catch(e) { alert(e); setOutput("");  }
}
function prepare_test_pdf() {
   document.getElementById("pdf_in").value = "";
   document.getElementById("pdf_place").value = "Madrid";
   document.getElementById("pdf_reason").value = "No reason";;
   document.getElementById("pdf_out").value = "c:/testing_file.pdf";
}
// -----------------------------------------------------------------------------
function test_pdf_url() {
   try {
      document.getElementById("ta_in").value = "";
      document.getElementById("ta_out").value = "Downloading pdf and signing...";
      var _file_in = document.getElementById("pdf_url_in").value;
      var _subject = "no utilizado";
      var _place = document.getElementById("pdf_url_place").value;
      var _reason = document.getElementById("pdf_url_reason").value;
      var _contact = "no utilizado";
      var _file_out = document.getElementById("pdf_url_out").value;
      var _visible = true;
	  var _coordenadas = "100;100;500;200";
	  var _page = 1;
      var sig = document.applet_01.firmaPDFUrl(_file_in, _subject, _place, _reason,
        _contact, _file_out, _visible, _coordenadas, _page);
      document.getElementById("ta_out").value = "Pdf signed. Response=" + sig;
   }catch(e) { alert(e); setOutput("");  }
}
function prepare_test_pdf_url_http() {
   document.getElementById("pdf_url_in").value = "http://ips.060.es/requisitos_tecnicos.pdf";
   document.getElementById("pdf_url_place").value = "Madrid";
   document.getElementById("pdf_url_reason").value = "No reason";;
   document.getElementById("pdf_url_out").value = "c:/testing_http.pdf";
}
function prepare_test_pdf_url_https() {
   document.getElementById("pdf_url_in").value = "https://jsr311.dev.java.net/drafts/spec20080320.pdf";
   document.getElementById("pdf_url_place").value = "Madrid";
   document.getElementById("pdf_url_reason").value = "No reason";;
   document.getElementById("pdf_url_out").value = "c:/testing_https.pdf";
}
// ---------------------------------------------------------------
function test_upload() {
   try {
      var _content = document.getElementById("ta_in").value;
      var _url = document.getElementById("upload_url").value;
      var _id = document.getElementById("upload_id").value;
      var _idcookie = document.getElementById("upload_idcookie").value;
	  document.getElementById("ta_out").value = "Uploading...";
      document.applet_01.serverUploadB64(_url, _content, _id, _idcookie);
	  document.getElementById("ta_out").value = "Uploaded";
   }catch(e) { alert(e); setOutput("");  }
}
function prepare_test_upload() {
   document.getElementById("ta_in").value = "dGVzdA==";
   document.getElementById("upload_url").value = "http://localhost:8080/AppletFirma_TestServer/save";
   document.getElementById("upload_id").value = "myid";
   document.getElementById("upload_idcookie").value = "myid";
}
// ---------------------------------------------------------------
function test_upload_file() {
   try {
      var _filename = document.getElementById("upload_filename_file").value;
      var _url = document.getElementById("upload_url_file").value;
      var _id = document.getElementById("upload_id_file").value;
      var _idcookie = document.getElementById("upload_idcookie_file").value;
	  document.getElementById("ta_out").value = "Uploading...";
      document.applet_01.serverUploadFile(_url, _filename, _id, _idcookie);
	  document.getElementById("ta_out").value = "Uploaded";
   }catch(e) { alert(e); setOutput("");
   }
}

// ---------------------------------------------------------------
var _display = new Array(false,false,false,false,false);
function display() {
   //Utils
   document.getElementById("tr_test_b64").style.display= _display[0] ? "" : "none";
   document.getElementById("tr_test_b64HashTexto").style.display= _display[0] ? "" : "none";
   document.getElementById("tr_test_b64HashB64").style.display= _display[0] ? "" : "none";
   document.getElementById("tr_test_b64HashFichero").style.display= _display[0] ? "" : "none";
   document.getElementById("tr_test_getcertificate_login").style.display= _display[0] ? "" : "none";
   document.getElementById("tr_test_getcertificate_setlogin").style.display= _display[0] ? "" : "none";
   document.getElementById("tr_test_getcertificates").style.display= _display[0] ? "" : "none";

   document.getElementById("tr_test_cms_b64").style.display= _display[1] ? "" : "none";
   document.getElementById("tr_test_cms_url").style.display= _display[1] ? "" : "none";
   document.getElementById("tr_test_cms_prefirma").style.display= _display[1] ? "" : "none";

   document.getElementById("tr_test_cades_b64").style.display= _display[2] ? "" : "none";
   document.getElementById("tr_test_cades_url").style.display= _display[2] ? "" : "none";
   document.getElementById("tr_test_cades_prefirma").style.display= _display[2] ? "" : "none";

   //XADES
   document.getElementById("tr_test_xades_xml").style.display= _display[3] ? "" : "none";
   document.getElementById("tr_test_xades_text").style.display= _display[3] ? "" : "none";
   document.getElementById("tr_test_xades_b64").style.display= _display[3] ? "" : "none";
   document.getElementById("tr_test_xades_file").style.display= _display[3] ? "" : "none";
   document.getElementById("tr_test_xades_signature").style.display= _display[3] ? "" : "none";
   document.getElementById("tr_test_xades_url").style.display= _display[3] ? "" : "none";
   document.getElementById("tr_test_xades_rawurl").style.display= _display[3] ? "" : "none";
   document.getElementById("tr_test_xades_prefirma").style.display= _display[3] ? "" : "none";
   document.getElementById("tr_test_xades_context").style.display= _display[3] ? "" : "none";

   //XMLDSIG
   document.getElementById("tr_test_xmldsig_xml").style.display= _display[4] ? "" : "none";
   document.getElementById("tr_test_xmldsig_text").style.display= _display[4] ? "" : "none";
   document.getElementById("tr_test_xmldsig_b64").style.display= _display[4] ? "" : "none";
   document.getElementById("tr_test_xmldsig_signature").style.display= _display[4] ? "" : "none";
   //PDF
   document.getElementById("tr_test_pdf").style.display= _display[5] ? "" : "none";
   document.getElementById("tr_test_pdf_url").style.display= _display[5] ? "" : "none";
   //UPLOAD
   document.getElementById("tr_test_upload").style.display= _display[6] ? "" : "none";
   document.getElementById("tr_test_upload_file").style.display= _display[6] ? "" : "none";
   //NOTES
   document.getElementById("div_notes").style.display= _display[7] ? "" : "none";
}
function displayUtils() { _display   = new Array(true,  false, false, false, false, false, false); display(); }
function displayPKCS7() { _display   = new Array(false, true,  false, false, false, false, false); display(); }
function displayCades() { _display   = new Array(false, false, true,  false, false, false, false); display(); }
function displayXAdES() { _display   = new Array(false, false, false, true,  false, false, false); display(); }
function displayXMLDSig() { _display = new Array(false, false, false, false, true,  false, false); display(); }
function displayPDF() { _display     = new Array(false, false, false, false, false, true,  false); display(); }
function displayUpload() { _display  = new Array(false, false, false, false, false, false, true); display(); }


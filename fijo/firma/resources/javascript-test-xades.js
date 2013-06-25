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
   var x = getXMLTest();
   document.getElementById("ta_in").value = x;
   document.getElementById("xades_xml_idsignature").value = "signature_01";
   document.getElementById("xades_xml_idreference").value = "target";
   document.getElementById("xades_xml_role").value = "signer_01";
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
   document.getElementById("xades_text_idsignature").value = "signature_02";
   document.getElementById("xades_text_idreference").value = "anyValue";
   document.getElementById("xades_text_role").value = "signer_01";
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
   document.getElementById("xades_b64_idsignature").value = "signature_01";
   document.getElementById("xades_b64_idreference").value = "anyValue";
   document.getElementById("xades_b64_role").value = "signer_01";
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

// CONTRAFIRMA ----------------------------------------------------------------

function test_xades_signature() {
	try {
		var _txt = document.getElementById("ta_in").value;
		var _idsig = document.getElementById("xades_signature_idsignature").value;
		var _idref = document.getElementById("xades_signature_idreference").value;
		var _role = document.getElementById("xades_signature_role").value;
		setOutput("Signing document...");
		var sig = document.applet_01.xmlFirmaXDSSerie(_txt, _idsig, _idref, _role);
		document.getElementById("ta_out").value = sig;
	} catch(e) { alert(e); setOutput(""); }
}

function prepare_test_xades_signature() {
	var _xml = getXMLTest();
	var signature01 = document.applet_01.xmlFirmaXDSXML(_xml, "signer01", "target", "signature_01", "XADES_BES", "ENVELOPING");
	document.getElementById("ta_in").value = signature01;
	document.getElementById("xades_signature_idsignature").value = "signature_02";
	document.getElementById("xades_signature_idreference").value = "signature_01";
	document.getElementById("xades_signature_role").value = "signer_02";
	setOutput("");
 }

// FIRMA POR ETAPAS -----------------------------------------------------------

function prepare_test_xades_prefirma() {
	document.getElementById("ta_in").value = "Dgfhjf/qIbFcPOd05ZtX7bYz9V0=";
	document.getElementById("xades_prefirma_idsignature").value = "idsignature";
	document.getElementById("xades_prefirma_role").value = "tester";
}

function test_xades_prefirma() {
	try {
		var _digest = document.getElementById("ta_in").value;
		var _idsig = document.getElementById("xades_prefirma_idsignature").value;
		var _idref = document.getElementById("xades_prefirma_idreference").value;
		var _role = document.getElementById("xades_prefirma_role").value;
		document.getElementById("ta_out").value = "Signing...";
		var sig = document.applet_01.prefirmaXades(_digest, _idsig, _idref, _role);
		document.getElementById("ta_out").value = sig;
	}catch(e) { alert(e); setOutput("");  }
}

function test_xades_setXadesContext() {
	try {
		var role = document.getElementById("xades_context_role").value;
		var policy = document.getElementById("xades_context_policy").value;
		var policyDesc = document.getElementById("xades_context_policy").value;
		document.applet_01.setXadesContext(role, policy, policyDesc);
		setOutput("Establecido nuevo contexto de firma XAdES.");
	}catch(e) { alert(e); setOutput(e);  }
}

// COMMON ---------------------------------------------------------------------

function getXMLTest() {
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
	return _xml;
}
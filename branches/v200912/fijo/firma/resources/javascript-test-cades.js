
// ========================== FIRMA CADES B64 ==================================

function test_cades_b64() {
	try{
		var dataB64 = document.getElementById("ta_in").value;
		var attached = document.getElementById("cades_b64_type").selectedIndex == 1;
		var signature = document.applet_01.firmaCadesB64(dataB64, attached);
		document.getElementById("ta_out").value = signature;
   }catch(e) { alert(e); setOutput(""); }
}

function test_cades_b64_prepare() {
	document.getElementById("ta_in").value = "dGVzdA==";
	setOutput("");
}

//========================== FIRMA CADES URL ===================================

function test_cades_url() {
   try {
      var url = document.getElementById("ta_in").value;
      var type = document.getElementById("cades_url_type").selectedIndex == 1;
      var signature = document.applet_01.firmaCadesUrl(url, type);
      document.getElementById("ta_out").value = signature;
   }catch(e) { alert(e); setOutput(""); }
}

function test_cades_url_prepare_url() {
	document.getElementById("ta_in").value = "file:/c:/windows/win.ini";
	setOutput("");
}

function test_cades_url_prepare_file() {
	document.getElementById("ta_in").value = "c:/windows/win.ini";
	setOutput("");
}

//========================== PREFIRMA CADES ====================================

function test_cades_prefirma() {
	try {
		var digest = document.getElementById("ta_in").value;
		var sig = document.applet_01.prefirmaCades(digest);
		document.getElementById("ta_out").value = sig;
	}catch(e) { alert(e); setOutput(""); }
}

function test_cades_prefirma_prepare() {
	try {
		document.getElementById("ta_in").value = "qUqP5cyxm6YcTAhz05Hph5gvu9M=";
		setOutput("");
	}catch(e) { alert(e); setOutput(""); }
}

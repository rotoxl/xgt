
// ========================== FIRMA CMS B64 ====================================

function test_cms_b64() {
	try{
		var dataB64 = document.getElementById("ta_in").value;
		var attached = document.getElementById("cms_b64_type").selectedIndex == 0;
		var signature = document.applet_01.firmaCmsB64(dataB64, attached);
		document.getElementById("ta_out").value = signature;
   }catch(e) { alert(e); setOutput(""); }
}

function test_cms_b64_prepare() {
	document.getElementById("ta_in").value = "dGVzdA==";
	setOutput("");
}

//========================== FIRMA CMS URL =====================================

function test_cms_url() {
   try {
      var url = document.getElementById("ta_in").value;
      var type = document.getElementById("cms_url_type").selectedIndex == 0;
      var signature = document.applet_01.firmaCmsUrl(url, type);
      document.getElementById("ta_out").value = signature;
   }catch(e) { alert(e); setOutput(""); }
}

function test_cms_url_prepare_url() {
	document.getElementById("ta_in").value = "file:/c:/windows/notepad.exe";
	setOutput("");
}

function test_cms_url_prepare_file() {
	document.getElementById("ta_in").value = "c:/windows/notepad.exe";
	setOutput("");
}

//========================== PREFIRMA CMS ======================================

function test_cms_prefirma() {
	try {
		var digest = document.getElementById("ta_in").value;
		var sig = document.applet_01.prefirmaCms(digest);
		document.getElementById("ta_out").value = sig;
	}catch(e) { alert(e); setOutput(""); }
}

function test_cms_prefirma_prepare() {
	try {
		document.getElementById("ta_in").value = "qUqP5cyxm6YcTAhz05Hph5gvu9M=";
		setOutput("");
	}catch(e) { alert(e); setOutput(""); }
}

function instalarComponente() {
	document.write('<applet id="applet" name="applet_01" code="es.burke.plataformafirma.applet.AppletFirma" archive="plataformafirma-applet.jar, plataformafirma-applet-thirdparty.jar" width="0" height="0">');
	document.write('<param name="browser" value="' + whichBrs() +'">');
	document.write('<param name="xx" value="yy">');
	document.write('</applet>');

}
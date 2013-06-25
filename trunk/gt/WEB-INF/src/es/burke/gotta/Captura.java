package es.burke.gotta;

import org.json.JSONException;
import org.json.JSONObject;

public class Captura {
	
	public String DLL_NAME = "AspriseJTwain.dll";
	public String DEBUG="1";
	public String DOWNLOAD="twain/AspriseJTwain.dll";
	public String UPLOAD_URL="subir";
	public String A_ANCHO="1";// pongo 1 porque sino falla en explorer que no permite 0
	public String A_ALTO="1";// idem
	public String CODE="com.burke.util.jtwain.web.UploadAppletSimple";
	public String CODEBASE=".";
	public String ARCHIVE="./twain/NuevoCuspide.jar, ./twain/jai_codec.jar, ./twain/jai_core.jar, ./twain/itext-2.0.6.jar, ./twain/JTwainLicense.jar, ./twain/plugin.jar, ./twain/AspriseJTwain.dll";
	public String JAVASCRIPT="continuarApplet";
	
	Motor motor = null;
	String literal = null;
		
	public Captura(Motor m, String literal) throws ErrorArrancandoAplicacion {
		this.motor=m;
		this.literal=literal;

		this.DOWNLOAD = fixUrl(m.getApli().getUrlAplicacion()) + this.DOWNLOAD;
		this.UPLOAD_URL = fixUrl(m.getApli().getUrlAplicacion()) + this.UPLOAD_URL;
		
	}
	public void JSON(String idsesion, JSONObject jsret) throws JSONException, ErrorArrancandoAplicacion{
		jsret.put("tipo", "captura");
		Object camino=null; Object tramite=null;
		
		if (this.motor.lote.tramActivo()!=null) {
			camino=this.motor.lote.tramActivo().camino;
			tramite=this.motor.lote.tramActivo().tramite;
			}
		
		jsret.put("camino",  camino) ;
		jsret.put("tramite", tramite);
		jsret.put("literal", this.literal);
		//fabrico innerhtml porque dom no me permite meter mayscript
		jsret.put("applet", applet(idsesion,this.motor.getApli().getCd()) );
		
	}
	
	private String fixUrl(String urlApp) {
		
		return urlApp.substring(0, urlApp.lastIndexOf("/")+1);
		
	}
	
	public String applet(String idsesion, String apli) {
		String retorno =" <applet "+ 		
			" code=\""+this.CODE+"\""+
			" codebase=\""+this.CODEBASE+"\""+	
			" archive=\""+this.ARCHIVE+"\""+	
			" width=\""+this.A_ANCHO+"\" height=\""+this.A_ALTO+"\""+
			" MAYSCRIPT=\"MAYSCRIPT\">"+
			"	<param name=\"DOWNLOAD_URL\" value=\""+this.DOWNLOAD+"\">"+
			"	<param name=\"DLL_NAME\" value=\""+this.DLL_NAME+"\">"+
			"	<param name=\"UPLOAD_URL\" value=\""+this.UPLOAD_URL+"\">"+
			"	<param name=\"EXTENSION\" value=\"pdf\">"+
			"	<param name=\"FUNCION_JAVASCRIPT\" value=\""+this.JAVASCRIPT+"\">"+
			"	<param name=\"GOTTA_VERSION\" value=\"X\">"+
			"	<param name=\"APLICACION\" value=\""+apli+"\">"+
			"	<param name=\"JSESSIONID\" value=\""+idsesion+"\">"+
			"</applet>";
		return retorno;
	}
}

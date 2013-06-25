package es.burke.gotta;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

public class Jsp  {
	public static void setHeaders(HttpServletResponse response){
		setHeaders(response,"text/html; charset="+Constantes.CODIF);
	}
	public static void setHeadersJson(HttpServletResponse response){
		setHeaders(response,"text/x-json; charset="+Constantes.CODIF);
	}
	public static void setHeaders(HttpServletResponse response, String contentType) {
	  response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
	  response.setHeader("Content-Type", contentType);

	  // Set standard HTTP/1.1 no-cache headers.
	  response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	  // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
	  response.addHeader("Cache-Control", "post-check=0, pre-check=0");

	  // Set standard HTTP/1.0 no-cache header.
	  response.setHeader("Pragma", "no-cache");
	  
	  // Valores predeterminado de script/css
	  response.setHeader("Content-Script-Type", "text/javascript");
	  response.setHeader("Content-Style-Type", "text/css");
	}
	public static String getTitle(String título)
		{return "	<TITLE>"+título+"</TITLE>";	}
	
	public static String getCSS (String id, String nombre){
		if (nombre == null)
			return "";
		return "<link id=\"css"+id+"\" rel=\"STYLESHEET\" TYPE=\"text/css\" href=\""+nombre+"\" charset=\"UTF-8\"> \n"; //en HTML el tag link no va cerrado, en XHTML va 'autocerrado': http://www.w3schools.com/tags/tag_link.asp	
		}
	public static String getCSS(Usuario usu) throws ErrorArrancandoAplicacion {
		String css=usu.getApli().getDatoConfig("css");
		
		if (css==null){
			String nombreApli=usu.getApli().getCd();
			css="./Aplicaciones/"+nombreApli+"/"+nombreApli+".css";
			}
		return 	getCSS("1", Constantes.icoGotta+"gotta.css")+
				getCSS("3", css);
		}
	public static String getScript(Usuario usr) throws ErrorArrancandoAplicacion{
		String[] lista={
					"jquery.min.js", 
//					"jwerty.js", 			//captura de eventos de teclado
					"firebug/firebugx.js",	//consola de firebug para ie, webkit
					"mover.js",				
					"arbol.js", 
					"xsplitter.js", 
					"detalle.js", "sorttable.js",  
					"menus.js",  
					"frmDinamico.js", //formularios y subida de ficheros
					"calendarPopup.js", 
					"validaciones.js"
					//"ext/adapter/ext/ext-base.js", "ext/ext-all-debug.js", "ext/examples/ux/RowExpander.js", 
					};   
		ArrayList<String> ret = new ArrayList<String>();
		for (String n : lista)
			ret.add(n);
		if(usr.permisoDepuracion){
			ret.add("depurador.js");			
			}
		
		if(usr.permisoDisenho){
			ret.add("disenhador.js");
			ret.add("editorNiveles.js");
			ret.add("codemirror/js/codemirror.js");  //editor de niveles
			}
		ret.add("../Aplicaciones/"+usr.getApli().getCd()+"/"+usr.getApli().getCd()+".js");
			
		return _getScript(ret);
		}
	private static String _getScript(ArrayList<String> ret2) {
		StringBuffer ret=new StringBuffer();
		for (String n : ret2)
			ret.append("	<script type=\"text/javascript\" src=\"fijo/"+n+"\" charset=\"UTF-8\"></script>\n");	
			
		return ret.toString();
		}
	
	public static String getHead(Usuario usu) throws ErrorArrancandoAplicacion {
		return getHead("", usu);}
	public static String getHead(String título, Usuario usu) throws ErrorArrancandoAplicacion{
		StringBuffer ret=new StringBuffer();
		ret.append("<HEAD>");
		ret.append("<meta charset=\"utf-8\">");
		
		ret.append(getTitle(título));
		ret.append(getCSS(usu));
		ret.append(getScript(usu));
		ret.append("<script>");
		ret.append("var aplicacion = '"+usu.getApli().getCd()+"'");
	    ret.append("</script>");

		ret.append("</HEAD>");
		
		return ret.toString();
		}
}

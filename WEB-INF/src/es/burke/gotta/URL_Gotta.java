package es.burke.gotta;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class URL_Gotta {
	public String ml;
	public String md;
	public String letras;
	public HashMap<String,String> controles;
	public Boolean flotante; 
	
	public URL_Gotta(){
		/*pass*/
	}
	public URL_Gotta(String vista, String detalle, String xletras, HashMap<String, String> controles2){
		this.ml=vista ;
		this.md=detalle;
		this.letras=xletras;
		this.controles=controles2;
		if (this.letras!=null && this.letras.equals("")) this.letras=null;
		if (this.md!=null && this.md.equals("") ) this.md=null;
		if (this.ml!=null && this.ml.equals("") ) this.ml=null;
	}
	public static URL_Gotta crea(HttpServletRequest request) {
		String vista=null, detalle=null, nodo=null;	
		HashMap<String,String>controles=new HashMap<String, String>();
		URL_Gotta ret=null;
		if (request.getParameter("nodo")!=null) {
			nodo =  Util.obtenValor(request, "nodo");
			detalle= Util.obtenValorOpcional(request, "detalle");
			vista=   Util.obtenValor(request, "vista");
			
			Enumeration<String> parameterNames;
			parameterNames= request.getParameterNames();
			while(parameterNames.hasMoreElements()){
				String name = parameterNames.nextElement();
				if(name.startsWith("control"))
					controles.put(name, request.getParameter(name));
				}
			ret=new URL_Gotta(vista, detalle, nodo, controles);
			}
		return ret;
		}
	public static URL_Gotta crea(String queryString) {
		HashMap<String, String>valores=Util.sacaDeURL(queryString);
		
		String nodo = valores.get("nodo");
		String detalle = valores.get("detalle");
		String vista = valores.get("vista");
		HashMap<String, String> controles=new HashMap<String, String>();
		Boolean flotante=valores.containsKey("flotante");
		
		for (String k:valores.keySet()){ 
			if (k.startsWith("control"))
				controles.put(k, valores.get(k));
			}
		
		URL_Gotta ret= new URL_Gotta(vista,detalle,nodo, controles);
		ret.flotante=flotante;
		return ret;
		}
	
}

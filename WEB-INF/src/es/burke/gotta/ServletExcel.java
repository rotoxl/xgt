package es.burke.gotta;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONException;
import org.json.JSONObject;

import es.burke.gotta.dll.Excel;

public class ServletExcel extends HttpServlet  {
	private static final long serialVersionUID = -7061971475908658036L;
	public static final int MAX_LONG_NOMBRE_FICHERO_EXCEL = 100;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException {
		req.setCharacterEncoding( Constantes.CODIF );
		Usuario usr=null;
		try {
			usr = Usuario.desdeSesion(req);
			String param = req.getParameter("lvw");

			NodoActivo na=JSON.rellenameNodoActivo(req, usr);
			
			String pagina = req.getParameter("pagina");
			if(!pagina.equals("undefined"))
				na.numPagina=Integer.parseInt(pagina);
			
			Control ctl=usr.getApli().getControl(usr, param);
			ControlLVW lvw= (ControlLVW)ctl;
			Filas filas=lvw.cargaDatos(na, lvw.nombreNivel, -1);
			
			int numCol=-1;
			boolean sortAsc=true;
			try {
				String cookie=getCookieValue(req, "config"+usr.getApli().getCd());
				if (cookie!=null){
					String numCol_sortDir=sacaValorDeCookie(cookie, "c"+param+".s");
				
					numCol=Integer.parseInt(numCol_sortDir.substring(1))-1;
					sortAsc=(numCol_sortDir.substring(0,1).equals("u"));
					if (numCol_sortDir != null){ 
						numCol=Integer.parseInt(numCol_sortDir.substring(1))-1; 
						sortAsc=(numCol_sortDir.substring(0,1).equals("u")); 
						}
					}
				}
			catch (JSONException e) {
				//no está establecida la ordenación para esa lista
				}
			Workbook wb=Excel.exportaFilas(usr, filas, numCol, sortAsc);
			
			res.setContentType("application/vnd.ms-excel");
			
			String apañaNodo=Util.replaceTodos(Util.replaceTodos(na.nodoActivo,"\\", "_"),"/", "_");
			if(apañaNodo!=null && apañaNodo.length()>MAX_LONG_NOMBRE_FICHERO_EXCEL)
				apañaNodo=apañaNodo.substring(0, MAX_LONG_NOMBRE_FICHERO_EXCEL);
			String nombreFichero = "Gotta-"+(apañaNodo!=null?apañaNodo+"-":"")+lvw.def.getCaption(usr)+".xls";
			res.setHeader("Content-disposition", "attachment;filename=" + Constantes.COMILLAS + nombreFichero + Constantes.COMILLAS);

			ServletOutputStream out = res.getOutputStream();
			
		    wb.write(out);
		    out.flush();
			out.close();
			}
		catch (ErrorGotta e){
			if (usr!=null)
				usr.sacaError(e);
			else
				Util.getLog().debug(e);
			}

		}
	 private static String getCookieValue(HttpServletRequest req, String nombreCookie) throws UnsupportedEncodingException {
		 req.setCharacterEncoding( Constantes.UTF8 );
		 Cookie[] cookies=req.getCookies();
		 for(int i=0; i<cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (nombreCookie.equals(cookie.getName()))
				return(cookie.getValue());
			}
		 return null;
		 }
	 private static String sacaValorDeCookie(String json, String nombreClave) throws JSONException, UnsupportedEncodingException{
		if (json==null)
			return null;
		json=URLDecoder.decode(json, Constantes.UTF8);
		JSONObject j=new JSONObject(json);
		return j.get(nombreClave).toString();
	 	}
}

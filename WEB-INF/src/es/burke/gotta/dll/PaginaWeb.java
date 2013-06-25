package es.burke.gotta.dll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.util.URIUtil;

import es.burke.gotta.Constantes;
import es.burke.gotta.Accion;
import es.burke.gotta.Coleccion;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Motor;
import es.burke.gotta.Util;
import es.burke.gotta.Variable;

public final class PaginaWeb extends DLLGotta {
	@Override
	public String accionesValidas() {		
		return "get post";
		}
	@Override
	public Object ejecuta(Accion xAccion, Object valor) throws ErrorGotta  {
		String x=xAccion.accion;
		
		Util.ponProxy(this.usr.getApli());
		if (x.equalsIgnoreCase("get")) 
			return get(valor.toString());
		else if (x.equalsIgnoreCase("post")){
			return post(valor.toString(), this.mMotor.lote.variables);
			}
			
		return Motor.Resultado.OK;
		}
	
	private String get(String url) throws ErrorGotta{
		try {
			ArrayList<String> temp=Util.split(url, "?");
			
			String trozo1, trozo2=Constantes.CAD_VACIA;
			trozo1=temp.get(0);
			if (temp.size()>1){
				trozo2=URIUtil.encodePath(temp.get(1), Constantes.UTF8);
				trozo1+="?";
				}
			
			URL pagina=new URL(trozo1+trozo2 );
			return Stream2String(pagina.openStream());
			}
		catch (MalformedURLException e) {
			throw new ErrorGotta(e.getMessage());
			} 
		catch (IOException e) {
			throw new ErrorGotta(e.getMessage());
			}
		}
	private String post(String url, Coleccion<Variable>datos) throws ErrorGotta{
		try {    
			String data=Constantes.CAD_VACIA;
			for (String key: datos.claves){
				Variable v=datos.get(key);
				
				if (v.esDeSistema)
					continue;
				else if (v.getValor()==null)
					continue;
				
				if (key.startsWith("@"))
					key=key.substring(1);
				
				if (!data.equals(Constantes.CAD_VACIA)) 
					data+="&";
				else
					data="?";
			    data += URLEncoder.encode(key, Constantes.UTF8) + "=" + URLEncoder.encode(v.getValor().toString(), Constantes.UTF8);
				}
			
			URL pagina=new URL(url+data);
			URLConnection conn = pagina.openConnection();
		    conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//		    wr.write(data);
		    wr.flush();
			return Stream2String(pagina.openStream());
			}
		catch (MalformedURLException e) {
			throw new ErrorGotta(e.getMessage());
			} 
		catch (IOException e) {
			throw new ErrorGotta(e.getMessage());
			}
		}
	
	private String Stream2String(InputStream is) throws IOException{	
		if (is != null) {
			Writer writer = new StringWriter();
			 
			char[] buffer = new char[1024];
				try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
					}
				}
			finally {
				is.close();
				}
			return writer.toString();
		}
		else
			return "";
		}
	@Override
	public void verifica(Motor xMotor, String xAccion, String nombre) throws ErrorGotta {
		verificaAccionValida(xAccion);
		this.mMotor=xMotor;
		this.usr=mMotor.usuario;
		}
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta, ServletException {
		//pass	
	}	
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}
}

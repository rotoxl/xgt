package es.burke.gotta.dll;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import es.burke.gotta.FechaGotta;
import es.burke.gotta.Constantes;
import es.burke.gotta.Accion;
import es.burke.gotta.ColumnaDef;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.ITabla;
import es.burke.gotta.ITablaDef;
import es.burke.gotta.Motor;
import es.burke.gotta.TablaTemporalDef;
import es.burke.gotta.Util;

public final class RSS extends DLLGotta {

	@Override
	public String accionesValidas() {
		return "leeRSS";
		}
	@Override
	public void verifica(Motor motor, String accion, String nombre) throws ErrorGotta {
		verificaAccionValida(accion);
		this.mMotor=motor;
		this.usr=motor.usuario;
		}
	@Override
	public Object ejecuta(Accion accion, Object valor) throws ErrorGotta {
		if (accion.accion.equalsIgnoreCase("leeRSS")){ //url|#nombreTabla
			ArrayList<String> temp=Util.split(valor.toString(), Constantes.PIPE);
			String url=temp.get(0), nombreTabla=temp.get(1);
			
			ITablaDef tdef=creaTablaDef(nombreTabla);
			ITabla t=tdef.newTabla(this.usr);
			this.mMotor.tablas.put(nombreTabla, t);
			
			HashMap<String, String> props=new HashMap<String,String>();
			props.put("title", "title"); props.put("pubDate", "pubDate"); props.put("content:encoded", "content"); 
							
			try {
				Util.ponProxy(this.usr.getApli());
				
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();    
				DocumentBuilder builder = factory.newDocumentBuilder();
				
				URL pagina=new URL(url);
				Document dom = builder.parse(pagina.openStream());
				
				NodeList nodes = dom.getElementsByTagName("item");
				for (int i=0; i<nodes.getLength(); i++){
					Element node=(Element)nodes.item(i);
					
					t.addNew();
					for (int j=0; j<node.getChildNodes().getLength(); j++){
						Node prop=node.getChildNodes().item(j);
						
						String p=prop.getNodeName();
						if (props.containsKey(p)){
							Object v=prop.getFirstChild().getNodeValue();
							if (p.equalsIgnoreCase("pubDate")) 
								v=parseDate(v.toString());
								
							t.setValorCol(props.get(p), v);
							}
						}
					}
				} 
			catch (MalformedURLException e) {
				throw new ErrorAccionDLL(e.getMessage());
				} 
			catch (ParserConfigurationException e) {
				throw new ErrorAccionDLL(e.getMessage());
				}  
			catch (SAXException e) {
				throw new ErrorAccionDLL(e.getMessage());
				} 
			catch (IOException e) {
				throw new ErrorAccionDLL(e.getMessage());
				}
			}
		return null;
		}
	
	private static FechaGotta parseDate(String valor) throws ErrorAccionDLL{//Wed, 01 Sep 2010 03:07:41 PDT
		java.text.DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);			
		try {
			Date ret=df.parse(valor);
			return new FechaGotta(ret);
			} 
		catch (ParseException e) {
			throw new ErrorAccionDLL("Error al parsear la fecha \""+valor+"\"");
			}
		}
	private ITablaDef creaTablaDef(String nombreTabla) throws ErrorGotta {
		ArrayList<ColumnaDef> cols= new ArrayList<ColumnaDef>();
		
		ColumnaDef title, pubDate, content;
		title=new ColumnaDef("title", "title", Constantes.STRING, 250, null); 		 cols.add(title);
		pubDate=new ColumnaDef("pubDate", "pubDate", Constantes.DATE, 25, null); 	 cols.add(pubDate);
		content=new ColumnaDef("content", "content", Constantes.STRING, 2500, null); cols.add(content);
		
		ITablaDef tdef= new TablaTemporalDef(nombreTabla, nombreTabla, cols);
		this.mMotor.ponTabla(tdef);
		
		return tdef;
	}
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta, ServletException {
		}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}
	
}

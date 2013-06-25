package es.burke.gotta.dll;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.xpath.*;

import es.burke.gotta.Accion;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Motor;
import es.burke.gotta.Util;

public class GottaXML extends DLLGotta {
	@Override
	public String accionesValidas() {
		return "xpath";
	}
	@Override
	public void verifica(Motor motor, String accion, String nombre) throws ErrorGotta {
		verificaAccionValida(accion);
		this.mMotor=motor;
		this.usr=motor.usuario;
		}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object ejecuta(Accion accion, Object valor) throws ErrorGotta {
		ArrayList<String> temp=Util.split(valor.toString(), Constantes.PIPE); // xml | ruta [| atributo]
		String xml, ruta;
		xml=temp.get(0); 
		ruta=temp.get(1); 
		
		Document doc = parseaXML(xml);
		Node nodo=sacaNodo(doc, ruta);
		return nodo.getTextContent();		
		}
	
	private Node sacaNodo(Document doc, String ruta) throws ErrorAccionDLL {
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		Object result;
		try {// XPath Query for showing all nodes value
			XPathExpression expr = xpath.compile(ruta);
			result = expr.evaluate(doc, XPathConstants.NODESET);
			} 
		catch (XPathExpressionException e) {
			throw new ErrorAccionDLL("Error en expresión XPath:"+e.getMessage());
			}
		
		NodeList nodes = (NodeList) result;
		return nodes.item(0);
		}

	public static Document parseaXML(String xml) throws ErrorAccionDLL {
		InputSource input=null;
		try {
			input = new InputSource(new ByteArrayInputStream(xml.getBytes(Constantes.UTF8)));
			} 
		catch (UnsupportedEncodingException e) {
			throw new ErrorAccionDLL("Se ha producido un error al hacer GottaXML:"+ e.getMessage());
			}
		
		DocumentBuilderFactory domFactory =	DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true); 
		try {
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			return builder.parse(input);
			} 
		catch (ParserConfigurationException e) {
			throw new ErrorAccionDLL("Se ha producido un error al hacer GottaXML:"+ e.getMessage());
			} 
		catch (SAXException e) {
			throw new ErrorAccionDLL("Se ha producido un error al hacer GottaXML:"+ e.getMessage());
			} 
		catch (IOException e) {
			throw new ErrorAccionDLL("Se ha producido un error al hacer GottaXML:"+ e.getMessage());
			}
		}	
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta, ServletException {
		//	
		}
}

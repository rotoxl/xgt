package es.burke.gotta;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.activation.FileTypeMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.python.core.PyModule;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.util.PythonInterpreter;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import es.burke.misc.NumeroTexto;

public final class Util {
	public static void inspeccionar(Object o){
		System.out.println(o);
	}
	public static Logger getLogger(String name){
		return Logger.getLogger(name);
	}
	public static HashMap<String, String> nodoAHash(String tramos){
		HashMap<String, String> hsClaves=new HashMap<String, String>();
		if (tramos==null)
			return hsClaves;
		for (String s : Util.split(tramos, Constantes.SEPARADOR)) {
			if (!s.startsWith("=") &&
				!s.startsWith("arbol") &&
				!s.startsWith("arb-") && 
				!s.equals(Constantes.CAD_VACIA) && 
				!hsClaves.containsKey(s.substring(0,1)))
				hsClaves.put(s.substring(0,1), s.substring(1));
			}
		return hsClaves;
		}
	public static HashMap<String, String> nodoAHash(Nodo nodo){
		HashMap<String, String> hsClaves=new HashMap<String, String>();
		if (nodo==null)
			return hsClaves;
		else if (nodo.esNodoRaiz()) //	else if (nodo.nodoPadre==null && !nodo.cd.startsWith("arb-"))
			return hsClaves;
		return nodoAHash(nodo.cd);
		}

/********************************/
	public static String prettyPrintXML(String unformattedXml) {
        try {
            final Document document = parseXmlFile(unformattedXml);

            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);

            return out.toString();
        	} 
        catch (IOException e) {
            throw new RuntimeException(e);
        	}
		}
	private static Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        	} 
        catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        	} 
        catch (SAXException e) {
            throw new RuntimeException(e);
        	} 
        catch (IOException e) {
            throw new RuntimeException(e);
        	}
    	}
    /** Transforma el valor numérico de la base de datos en un color RGB	*/
	public static String hazColor(long tinta)		{
		String color = Constantes.CAD_VACIA;
		//Si es negativo es color de sistema y no hacemos nada
		if (tinta>=0)
		{
			//Si es positivo lo pasamos a hexadecimal y completamos con ceros hasta 8
			String cadena=Long.toHexString(tinta);
			color="000000"+cadena;
			cadena = color.substring(color.length()-6);

			color=cadena.substring(4,6)+cadena.substring(2,4)+cadena.substring(0,2);
			color="#"+color;
		}
		return color;
		}

	public static Coleccion<String> sesiones=new Coleccion<String>();
	
	public static void putSesionAplicacionYDemás(Usuario usr) throws ErrorArrancandoAplicacion{
		if (usr.sesionFinalizada)
			return;
		
		Aplicacion apli=usr.getApli();
		putSesionAplicacion(usr.hashCode()+"", apli.getCd());
		apli.añadeAListaUsuarios(usr);
		}
	
	private static void putSesionAplicacion(String idSesion, String apli){
		sesiones.put(idSesion, idSesion+"@"+apli);
		}

	public static void deleteSesionAplicacion(String id) {
		sesiones.remove(id);}
	
/* Encripta la cadena que recibe con un algoritmo	*/
	public static String embrollar(String cadena){
		String m = Constantes.CAD_VACIA;
		String embrollo = (cadena==null?"":cadena) + "\\supercalifragilisticoexpialidoso";
		String a = embrollo.substring(0,32);

		for (int n=0;n<a.length();n++){
			int numero = a.charAt(n);
			String octal = Integer.toOctalString(numero * 2);
			if (octal.length()<3)
				{
				for (int j=octal.length();j<3 ;j++)
					{octal="0"+octal;}
				}
			else if (octal.length()>3)
				{octal=octal.substring(octal.length()-3);}
			m=octal+m;
			}

		return m;
		}	
	public static String desembrollar(String valor){
		String salida = "";
		for (int n=valor.length();n>3;n=n-3) {
			String octal = valor.substring(n-3, n);
			int numero = Integer.parseInt(octal, 8)/2;
			char chr=(char) numero;
			salida = salida+chr;
			}
		return Util.split(salida, "\\super").get(0);
		}
	
/* funciones que en otros lenguajes vienen de lata*/	
	public static String chr(int var)
		{return new Character((char)var).toString();}
	public static int asc(String var)
		{return (var.charAt(0));}

	public static ArrayList<Object> getElementos(ArrayList<Object> v, int n){
		ArrayList<Object> ret = new ArrayList<Object>();
		for (int i=0;i<n;i++)
			ret.add(v.get(i));
		return ret;
		}
	public static boolean columnaInvisible(String pcolumna){
		if (pcolumna.startsWith("_")){
			return true;
			}
		String columna=pcolumna;
		columna=columna.toLowerCase();
		return columna.equals("mododetalle")||
				columna.equals("key")||
				columna.equals("icono")||
				columna.equals("cd")||
				columna.equals("ds")||
				columna.equals("mododetallesiguiente")||
				columna.equals("modolista");
		}
	
	public static String join(String token, Iterable<?> lista )	{
		StringBuffer sb = new StringBuffer();
		for(Object s:lista) {
			if (s==null)
				sb.append("");
			else sb.append( s );
			sb.append( token );
			}
		if (sb.length()==0)
			return Constantes.CAD_VACIA;
		return sb.substring(0, sb.length()-token.length());
		}
	public static ArrayList<String> split(String cadena)
		{return splitTrim(cadena, Constantes.ESPACIO, true);}
	public static ArrayList<String> split(String cadena, String sep)
		{return splitTrim(cadena, sep, true);}
	public static ArrayList<Object> splitObjetos(String cadena, String sep) {
		ArrayList<String>pp= splitTrim(cadena, sep, true);
		
		ArrayList<Object> ret=new ArrayList<Object>();
		for (String p : pp)
			{ret.add(p);}
		return ret;
		}
	public static ArrayList<String> splitTrim(String cadena, String sep, boolean trim) {
		int pos1, pos2;
		ArrayList<String> v = new ArrayList<String>();
		StringBuffer sb = new StringBuffer(cadena);				
		if (sep.equals(Constantes.CAD_VACIA)) {
			v.add(cadena);
			return v;
			}
		
		String elemento;
		pos1=0;
		pos2=cadena.indexOf(sep);
		int tam=sep.length();
		if (pos2<0)
			v.add(cadena);
		else {
			boolean fin = false;
			while (!fin) {
				if (pos2>=0)
					elemento = sb.substring(pos1, pos2);
				else {
					elemento = sb.substring(pos1);
					fin = true;
						}
				if (trim)
					v.add(elemento.trim());
				else
					v.add(elemento);
				pos1=pos2+tam;
				pos2=cadena.indexOf(sep, pos1);
				}
			}
		return v;
		}
	
	public static String replaceUltimo(String cadena, String car_a_sustituir, String car) {
		if (cadena.contains(car_a_sustituir)) {
			int inicio=cadena.lastIndexOf(car_a_sustituir);
			int fin=car_a_sustituir.length();
			StringBuffer stb=new StringBuffer(cadena);
			String aux = (stb.replace(inicio,inicio+fin,car)).toString();
			return aux;
			}
		return cadena;
		}
	public static String replaceUno(String cadena, String car_a_sustituir, String car) {
		return replace(cadena, car_a_sustituir, car, 1);
		}
	
	public static String replaceTodos(String cadena, String car_a_sustituir, String car) {
		return replace(cadena, car_a_sustituir, car, 0);
		}

	public static String replace(String cadena, String car_a_sustituir, String car, int pcuantos) {
		int cuantos=pcuantos;
		
		if(cadena==null)
			return cadena;

		String cadenaret = cadena;
		int desde, hasta;
		desde=0;
		hasta= cadenaret.indexOf(car_a_sustituir);
		boolean todos = (cuantos==0);
		int len1 = car_a_sustituir.length();
		int len2 = 0;
		if (car!=null) len2=car.length();

		while (hasta >= 0 && (cuantos > 0 || todos)) {
			cadenaret = cadenaret.substring(0, hasta) + car + cadenaret.substring(hasta+len1);
			desde = hasta+len2;
			hasta=cadenaret.indexOf(car_a_sustituir, desde);
			cuantos -= 1;
			}
	return cadenaret;
	}

	public static boolean isNumeric(String valor) {
		if (valor==null || valor.equals(Constantes.CAD_VACIA))
			return false;
		try {
			new BigDecimal(valor);
 			return true;
			}
		catch(NumberFormatException e)
			{return false;}
		}
	public static boolean en(String nombre, String... param)
		{return en(nombre, (Object[])param);}
	public static boolean en(Object nombre, Object... param) {
		for (Object s  : param) {
			if ( (s instanceof String && nombre instanceof String) && 
				  s.toString().equalsIgnoreCase( nombre.toString() ) )
				return true;
			else if ( s.equals(nombre) )
				return true;
			}
		return false;
		}
	public static int cuentaCadena(String cadena, String trozoBuscar) {
		int i=0;
		int from=0;
		
		while((from=cadena.indexOf(trozoBuscar,from+1))!=-1)
			i++;
		return i;
		}
	
	public static boolean esTipoNumerico(String tipoGotta){
		return en(tipoGotta, Constantes.tiposNumericos); /*Constantes.INTEGER, "float", Constantes.LONG, Constantes.DOUBLE,
									Constantes.CURRENCY,"byte",  Constantes.SINGLE, "money",
									"decimal", "int",   "number");*/
		}
	
	public static ArrayList<?> reverse(ArrayList<?> v) {
		if (v==null||v.size()<=1)
			return v;
		ArrayList<Object> vret = new ArrayList<Object>(v.size());
		for (int i=v.size()-1;i>=0 ;i--)
			vret.add(v.get(i));
		return vret;
		}
	public static String escapaHTML(String cadena){
		String ret=Util.replaceTodos(cadena, "&", "&amp;");
		ret=Util.replaceTodos(ret, "<", "&lt;");
		ret=Util.replaceTodos(ret, ">", "&gt;");
		return ret;
		}
	
/* formatea datos de tipo númerico */
	public static DecimalFormat formatoDecimal=null;
	final static String separadorDecimal=",";
	final static String separadorMiles=Constantes.PUNTO;
	final static String patronMoneda="#"+separadorDecimal+"##0"+separadorMiles+"00"; //###.##0,00
	final static String patronNumeros="#,##0"; //esto es para los miles, hay que indicarselo como si fuéramos guiris

	public static String formatearMoneda(String pvalor)
		{return _formatearNum(pvalor,  patronMoneda);}
	private static String _formatearNum(String pvalor, String pattern) {
		String valor=pvalor;
		if ( valor.equals(Constantes.CAD_VACIA))
			return valor;
		valor = Util.split(valor).get(0);
				
		if (formatoDecimal==null) {
			formatoDecimal= new DecimalFormat();
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setCurrencySymbol(Constantes.CAD_VACIA);
			dfs.setGroupingSeparator(separadorMiles.charAt(0));
			dfs.setDecimalSeparator(separadorDecimal.charAt(0));
			
			formatoDecimal.setDecimalFormatSymbols(dfs);
			}
			
		formatoDecimal.applyPattern(pattern);
		ArrayList<String> aux = Util.split(pattern, ".");
		int decimales  = 0;
		try {
			if (aux.size()>1)
				decimales  = aux.get(1).toString().length();
			}
		catch(Exception e)
			{ /*pass*/}
		try	{
			BigDecimal decimal=redondeo(new BigDecimal(valor),decimales);
			String s=formatoDecimal.format(decimal);
			return s;
			}
		catch (NumberFormatException nfe)
			{getLog().warn("Excepcion en formatearMoneda:"+nfe.getMessage());}

		return valor;
	}
	public static String formatearNumero(BigDecimal num){
		return formatearNumero(num.toString(), num.scale());
		}

	public static String formatearNumero(Integer num, int nPosiciones){
		String ret = num.toString();
		while(ret.length()<nPosiciones)
			ret='0'+ret;
		return ret;
	}
	public static String formatearNumero(String ps, int ndecimales){
		String patron=patronNumeros;
		
		if (ndecimales<0) { //le enchufamos tantos como tenga
			ArrayList<String> temp=split(ps, ".");
			if (temp.size()>1)
				ndecimales=noNulo(temp.get(1)).length();
			else //tiene 0
				ndecimales=0;
			}
		
		patron=patronNumeros+(ndecimales>0?"."+rfill("", "0", ndecimales) : "");
		return _formatearNum(ps, patron);
		}

	public static String desformatearNumeroParaTipo(String ps, String tipo){
		if (!ps.contains(Constantes.COMA) && !ps.contains(Constantes.PUNTO) && !ps.contains(Constantes.ESPACIO))
			return ps;
		ps=ps.trim();
		String s = ps.replaceAll(Constantes.NBSP, Constantes.ESPACIO);
		s=Util.split(s).get(0); //por si tiene símbolo de moneda
		
		if (s.length()>4) {// 1.000 SI se desformatea, pero 1.00 NO
			s=Util.replaceTodos(s, Constantes.PUNTO, Constantes.CAD_VACIA);//quita los puntos
			}
		int numDecimales=0;
		if ( s.contains(Constantes.COMA) ){
			numDecimales=Util.split(s, Constantes.COMA).get(1).length();
			s=Util.replaceUltimo(s, Constantes.COMA , Constantes.PUNTO);
			}
		
		// si no es un número perfectamente formateado en español (1.00 no lo es), no se desformatea
		if (tipo.equals(Constantes.CURRENCY) && ps.equals(formatearMoneda(s)))
			return s;
		else if (en(tipo, Constantes.tiposNumericos) && ps.equals(formatearNumero(s, numDecimales)))
			return s;
		else 
			return ps;
		}
	public static String desformatearNumero(String ps){
		String s = ps.replaceAll(Constantes.NBSP, Constantes.ESPACIO);
		s=Util.split(s).get(0); //por si tiene símbolo de moneda
		
		if (s.length()>4) {// 1.000 SI se desformatea, pero 1.00 NO
			s=Util.replaceTodos(s, Constantes.PUNTO, Constantes.CAD_VACIA);//quita los puntos
			}
		
		if ( s.contains(Constantes.COMA) )
			s=Util.replaceUltimo(s, Constantes.COMA , Constantes.PUNTO);
		
		return s;
		}
	public static BigDecimal redondeo(BigDecimal dato, int ndecimales)
		{
		return dato.setScale(ndecimales, BigDecimal.ROUND_HALF_UP);
		}
	
	private static String rfill(String cadena, String rellenar, int num){
		for (int i=0; i<num;i++)
			cadena+=rellenar;
		return cadena;
	}
/* Para analizar las cadenas tipo *.Tabla.campo  */
	public static CampoDef valorSimbolo(NodoActivo na, String simbolo, ITabla tb, ITabla tt) throws ErrorCampoNoExiste, ErrorFechaIncorrecta, ErrorTablaNoExiste, ErrorArrancandoAplicacion, ErrorUsuarioNoValido, ErrorCreandoTabla {
		int comenzar = 0;
		ArrayList<String> v = Util.split(simbolo, Constantes.PUNTO);
		ITabla t;

		if (v.get(0).equals(Constantes.CAD_VACIA)) {
			comenzar = 1;
			t = tt; //tabla tramites
			}
		else if (v.get(0).equals("*")) {
			comenzar = 2;
			t=na.getTabla(v.get(1));
			}
		else {
			comenzar = 0;
			t = tb; //tabla base
			}

		if (t==null)
			return null;
	
		String nombrecampo = null;
		ArrayList<Object> valor = null;
		CampoDef cRef = null;
		String nombretabla =null;		
		for (int i=comenzar; i<v.size()-1 ;i++ ) {
			nombrecampo = v.get(i).toString();
			valor = t.getValorCam(nombrecampo);
			cRef = t.getCampo(nombrecampo).getCampoReferencia();
			if (cRef != null) {
				nombretabla=t.getCampo(nombrecampo).getTablaReferencia().getCd();
				t=na.getTabla(nombretabla);
				
				if (!t.findKey(valor,cRef.getCd())) {
					try 
						{t.cargarRegistros(cRef, valor);} 
					catch (ErrorCargandoTabla e) 
						{na.usr.getApli().sacaError(e);}
					
					if (na.getTabla(nombretabla)==null)
						na.putTabla(t);
					}
				}
			}
		CampoDef cam = t.getCampo(v.get(v.size()-1));
		return cam;
		}
	public static CampoDef valorSimbolo(Aplicacion aplicacion, String simbolo, String tb) throws ErrorDiccionario 
	//Para el explorador
	{
		if (tb.equals(Constantes.CAD_VACIA))
			return null;
		int comenzar = 0;
		ArrayList<String> v = Util.split(simbolo, Constantes.PUNTO);
		ITablaDef tdef;

		comenzar = 0;
		tdef = aplicacion.getEsquema().getTablaDef(tb);
		String nombrecampo = null;
		CampoDef cRef = null;
		for (int i=comenzar; i<v.size()-1 ;i++ )
		{
			nombrecampo = v.get(i).toString();
			cRef = tdef.getCampo(nombrecampo).getCampoReferencia();
			if (cRef != null)
				tdef =tdef.getCampo(nombrecampo).getTablaReferencia();
		}
		CampoDef cam = tdef.getCampo(v.get(v.size()-1).toString());
		return cam;
	}
	public static ArrayList<Object> dameValorCampo(NodoActivo na, CampoDef campo) throws ErrorCampoNoExiste, ErrorTablaNoExiste, ErrorUsuarioNoValido, ErrorArrancandoAplicacion, ErrorCreandoTabla{
		/* OJO, esto es sólo para el explorador */
		ITabla t = na.getTabla(campo.getTabla());
		if (t==null){
			ArrayList<Object> ret=new ArrayList<Object>();
			for (int i=0; i<campo.numColumnas(); i++)
				ret.add(null);
			return ret;
			}
		ArrayList<Object> valores = t.getValorCam(campo.getCd());
		return valores;
	}
	

/*Devuelve un array de valores formateados */		
	public static ArrayList<Object> mostrarDatoTexto(CampoDef campo, ArrayList<Object> valores) 
	{
		ArrayList<Object> vec = new ArrayList<Object>();

		//estiloCSS=def.getEstiloCSS();
		for (int i=0; i<campo.numColumnas(); i++)
		{
			//ColumnaDef col=campo.getColumna(i);
			Object valor=valores.get(i);
			if (valor!=null)
			{
				vec.add(valor);
				/*
				String tipoCol=col.getTipo();
				if (tipoCol.equals(Constantes.DATE) && !valor.toString().equals(Constantes.CAD_VACIA))
					{
					if (FechaGotta.esFecha(valor.toString()))
						vec.add(FechaGotta.hazFecha(valor.toString()));
					else
						vec.add(valor);
					}
				else if (tipoCol.equalsIgnoreCase(Constantes.CURRENCY) && !valor.toString().equals(Constantes.CAD_VACIA))
					{
					valor=Util.formatearMoneda(valor.toString(), false);
					vec.add(valor);
					}
				else if (Util.esTipoNumerico(tipoCol) && !valor.toString().equals(Constantes.CAD_VACIA))
					{
					if (tipoCol.equals(Constantes.DOUBLE) ) //||tipoCol.equals(Constantes.FLOAT))
						valor=Util.formatearMoneda(valor.toString());
					vec.add(valor);
					}
				else if (tipoCol.equals(Constantes.BOOLEAN))
				{
					String bol="No";
					if (valor.toString().equals("false")){valor="0";}
					if (!valor.toString().equals("0"))
						bol="Sí";
					valor=bol;
					vec.add(valor);
				}
				else
					vec.add(valor);
					*/
			}
			else
				vec.add(Constantes.CAD_VACIA);
		}
		return vec;
	}
	
/* utilidades para generar json */	
	enum formatos{
		HTML, RAW;
		}
	public static String preparaParaRAW(Object valor, String tipoDato){
		return preparaParaFormato(valor, tipoDato, formatos.RAW);
	}
//	public static String preparaParaHTML(Object valor, String tipoDato){
//		return preparaParaFormato(valor, tipoDato, formatos.HTML);
//	}
	
	private static String preparaParaFormato(Object valor, String tipoDato, formatos formato) {
		String ret=Constantes.CAD_VACIA;
		if (valor == null){
			return Constantes.CAD_VACIA;
			}
		if (valor instanceof String || valor instanceof FechaGotta)
			ret=valor.toString();
		else if (!tipoDato.equals(Constantes.CAD_VACIA)){
			String tg=Constantes.tipoGotta(tipoDato);
			if ( tg.equals(Constantes.STRING) )
				ret= valor.toString() ;
			else if (valor instanceof java.math.BigDecimal){
				BigDecimal valorDec=(BigDecimal)valor;
				if (valorDec.scale()==0){
					ret=Util.formatearNumero(valor.toString(), -1);
					tg=Constantes.LONG;
					}
				else
					ret=Util.formatearNumero(valor.toString(), valorDec.scale());
				}
			else if (tg.equals(Constantes.BOOLEAN) && valor instanceof Boolean)
				ret=((Boolean)valor).booleanValue()? "Sí" : "No";
			else if ( tg.equals(Constantes.CURRENCY) )
				ret=Util.formatearMoneda(valor.toString());
			else if ( Util.esTipoNumerico(tg) )
				ret=Util.formatearNumero(valor.toString(), -1);
				}
		else{
			ret=valor.toString();
			}
		if(formato.equals(formatos.RAW))
			return ret;
		return Util.escapaHTML(ret);
	
		}

	public static JSONArray JSON(List<?> datos) {
		JSONArray ret = new JSONArray();
		for (Object f : datos)
			ret.put(f);
		return ret;
		}
	public static JSONArray JSON(HashSet<?> datos) {
		JSONArray ret = new JSONArray();
		for (Object f : datos)
			ret.put(f);
		return ret;
		}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONObject JSON(HashMap<String,?> datos) throws JSONException {
		if (datos==null)
			return null;
		JSONObject ret = new JSONObject();
		for (String f : datos.keySet()){
			Object var= datos.get(f);
			
			if (var instanceof HashMap){
				ret.put(f, JSON((HashMap<String,?>) var) );	
				}
			else if (var instanceof List){
				ret.put(f, JSON((List) var) );
				}
			else
				ret.put(f, var );
			}
		return ret;
		}
	public static String quita(String ret, String letras){
		if (!ret.toString().endsWith(letras))return ret;
		return ret.substring(0, ret.length()-letras.length());
	}
	public static String quita(String ret, int i){
		if(ret.length()<i)return ret;
		return ret.substring(0, ret.length()-i);
	}
	public static String tipoJSON(String tipo){
		if ( tipo!=null && tipo.equals( Constantes.CONSEJO) )
			return "lvwConsejo";
		
		String tt=Constantes.tipoGotta(tipo);
		if ( tt.equals(Constantes.CAD_VACIA) )
			return "etiqueta";
		else if ( tt.equals( Constantes.FILE) )
			return "lvwFile";
		else if ( tt.equals( Constantes.STRING) )
			return "lvw";
		else if ( tt.equals( Constantes.MEMO) )
			return "lvwMemo";
		else if ( tt.equals(Constantes.DATE) )
			return "lvwFecha";
		else if ( tt.equals(Constantes.BOOLEAN) )
			return "lvwBoolean";
		else if (tt.equals(Constantes.CURRENCY))
			return "lvwCurrency";
		else if (Util.en( tt, Constantes.tiposNumericos))
			return "lvwNumero";
		else if ( tt.equals(Constantes.OPTION) )
			return "lvwOption";
		else if ( tt.equals(Constantes.LIST) )
			return "lvwLista";
		
		else
			return "lvwNumero";
		}
	public static ArrayList<String> tiposJSON(ArrayList<String> tipos){
		ArrayList<String> ret= new ArrayList<String>();
		for (String t :  tipos)
			ret.add( tipoJSON(t) );
		return ret;
		}

	public static void sendRedirectSinParametros(HttpServletRequest request, HttpServletResponse res, String url) throws IOException{
		String apli="?aplicacion="+request.getParameter("aplicacion");
		sendRedirect(request, res, url, apli);
		}
	public static void sendRedirect(HttpServletRequest request, HttpServletResponse res, String url) throws IOException{
		String nodo, detalle, vista;
		String redirigir="";
		
		if (!url.contains("nodo")){
			nodo=obtenValorOpcional(request, "nodo");
			vista=obtenValorOpcional(request, "vista");
			detalle=obtenValorOpcional(request, "detalle");
			
			if (nodo!=null || vista!=null) 
				redirigir="&vista="+vista+"&detalle="+detalle+"&nodo="+nodo;
			
			if (!url.contains("aplicacion"))
				url="?aplicacion="+request.getParameter("aplicacion");
			url+=redirigir;
			}
		sendRedirect(request, res, url, null);
		}
	public static void sendRedirect(HttpServletRequest req, HttpServletResponse res, String url, String msgError) throws IOException{		
		res.sendRedirect(req.getContextPath()+"/"+url+(msgError!=null?msgError:""));
	} 
	
	public static Object ponTipoCorrecto(ColumnaDef col, Object valor) throws ErrorFechaIncorrecta, ErrorNumeroIncorrecto {
		if (valor==null)
			return valor;
		if (valor.equals(Constantes.CAD_VACIA) && Util.en(col.getTipo(), Constantes.tiposTexto))
			return valor;
		if (valor.equals(Constantes.CAD_VACIA))
			return null;
		
		String vvalor="";
		if (Util.en(col.getTipo(), Constantes.tiposNumericos)){
			vvalor=Util.desformatearNumeroParaTipo(valor.toString(), col.getTipo());
			if (vvalor.equals(Constantes.CAD_VACIA))
				return null;
			}
	 	if (col.getTipo().equals(Constantes.CURRENCY)) {
			if (valor instanceof BigDecimal)
				return valor;
			else
				return new BigDecimal(vvalor);
			}
		else if (col.getTipo().equals(Constantes.DOUBLE)){
			if (valor instanceof Double)
				return valor;
			else
				return Double.parseDouble(vvalor);
			}
		else if (col.getTipo().equals(Constantes.BOOLEAN)){
			if (valor instanceof Boolean)
				return valor;
			else
				if(isNumeric(vvalor))
					return new BigDecimal(vvalor).compareTo(BigDecimal.ZERO)!=0;
				return new Boolean(vvalor);
			}
		else if (col.getTipo().equals(Constantes.LONG)){
			if (valor instanceof Long)
				return valor;
			else
				return Long.parseLong(vvalor);
			}
		else if (col.getTipo().equals(Constantes.INTEGER)){
			if (valor instanceof Integer)
				return valor;
			else
				return Integer.parseInt(vvalor);
			}
		else if (col.getTipo().equals(Constantes.SINGLE)){
			if (valor instanceof Float)
				return valor;
			else
				return Float.parseFloat(vvalor);
			}
		else if (Util.en(col.getTipo(), Constantes.tiposNumericos) ){
			if (valor instanceof Integer || valor instanceof Long || valor instanceof BigDecimal)
				return valor;
				
			try {
				return new BigDecimal(vvalor );
				}
			catch (NumberFormatException e){
				throw new ErrorNumeroIncorrecto("No se ha podido convertir el valor '"+valor+"' a un número válido");
				}
			}
		else if (col.getTipo().equals(Constantes.DATE)){
			FechaGotta ret = FechaGotta.comoFechaGotta(valor);
			if (ret==null)
				throw new ErrorFechaIncorrecta("No se ha podido convertir el valor '"+valor+"' a fecha");
			return ret;
			}
			
		else if (col.getTipo().equals(Constantes.STRING)){
			return valor.toString();
			}
		return valor;
	}
/* */	
	@SuppressWarnings("rawtypes")
	public static String toString(Object o) {
		if (o instanceof ArrayList) {
			ArrayList<String> sb = new ArrayList<String>();
			for (Object it : (ArrayList)o) {
				if (it==null)
					sb.add( "NULL");
				else
					sb.add(it.toString());
				}
			return "["+Util.join(", ",sb)+"]";
			}
		else if (o instanceof HashMap) {
			ArrayList<String> sb = new ArrayList<String>();
			HashMap hm=(HashMap)o;
			Object[] nombres={"REPORT_PARAMETERS_MAP",
					"REPORT_CONNECTION",
					"IS_IGNORE_PAGINATION",
					"REPORT_FORMAT_FACTORY",
					"REPORT_TIME_ZONE",
					"REPORT_LOCALE",
					"REPORT_SCRIPTLET"};

			for (Object k : hm.keySet()) {
				Object v=hm.get(k);
				if (Util.en(k, nombres)){
					//pass
					}
				else if (v==null)
					sb.add( k+":NULL");
				else
					sb.add(k+":"+v.toString());
				}
			return "{"+Util.join(", ", sb).toString()+"}";
			}
		else
			return toString(o,null);
		}
	public static String noVacía(Object o){
		if (o == null)
			return null;
		String ret = o.toString();
		if(ret.equals(Constantes.CAD_VACIA))
			return null;
		return ret;
		}
	public static String noNulo(Object o)
		{return toString(o, Constantes.CAD_VACIA);}
	public static String toString(Object o, String valorAlternativo)  {
		return o == null?valorAlternativo:o.toString();
		}
	public static String vacioANulo(String valor){
		if (valor==null || valor.equals(Constantes.CAD_VACIA))
			return null;
		return valor;
	}
	public static boolean alinearDerecha(CampoDef campo, int numColumna){
		Coleccion<ColumnaDef> columnas = campo.getColumnas();
		ColumnaDef col=columnas.get(numColumna);
		return Util.esTipoNumerico(col.getTipo());
	}

	public static String formatoColumna(ColumnaDef col, Object valor){
		return formatoDato(col.getTipo(), valor);
	 	}
	public static String formatoDato(String tipoDato,Object valor) {
		String ret=Constantes.CAD_VACIA;
		if (valor==null)
			return Constantes.CAD_VACIA;
		else if (valor instanceof Boolean) {
			if ( ((Boolean)valor)==true) 
				ret="Sí";
			else 
				ret="No";
			}
		else
			ret=Util.noNulo( valor );

		if ( tipoDato.equals(Constantes.BOOLEAN) && valor instanceof Boolean ){
			/*pass*/}
		else if (Util.esTipoNumerico(tipoDato)) {
			if (tipoDato.equals(Constantes.CURRENCY))
				ret=Util.formatearMoneda(valor.toString());
			else
				ret=Util.formatearNumero(valor.toString(), -1);
			}

		return ret;
	}
	
	public static boolean existeyVale(Fila fila, String clave, boolean valorPorDefecto) {
		if (!fila.containsKey(clave))
			return valorPorDefecto;
		
		Object valorClave = fila.get(clave);
		if (valorClave==null)
			return false;
		if (valorClave instanceof Boolean)
			return (Boolean)valorClave;
		if (valorClave instanceof Boolean)
			return (Boolean)valorClave;
		String strClave=valorClave.toString();
		if(strClave.equals(Constantes.CAD_VACIA))
			return false;
		return !(Double.parseDouble(strClave)==0.0);
		}	
	public static String completaPorLaIzquierda(String cadena, String caracter, int n) {
		return Util._completa(cadena, caracter, n, 0);
		}
	public static String completaPorLaDerecha(String cadena, String caracter, int n) {
		return Util._completa(cadena, caracter, n, 1);
		}	
	private static String _completa(String cadena, String caracter, int n, int direccion) {
		final int IZQUIERDA=0;
		final int DERECHA=1;
		
		while(cadena.length()<n) {
			if (direccion==IZQUIERDA)
				cadena=caracter+cadena;
			else if (direccion==DERECHA)
				cadena=cadena+caracter;
		 	}
		return cadena;
		}

	public static byte[] getFileContentsAsByteArray(File f) throws IOException {
        byte data[];
        DataInputStream is = new DataInputStream(new FileInputStream(f));
        try{
	        data = new byte[(int)f.length()];
	        is.readFully(data);
	        return data;
        }
        finally{
        	is.close();
        }
    }
	public static byte[] getMessageHash_SHA(byte[] message) throws NoSuchAlgorithmException {
		return getMessageHash(message, "SHA");
	}
	public static byte[] getMessageHash_MD5(byte[] message) throws NoSuchAlgorithmException {
		return getMessageHash(message, "MD5");
	}
	public static byte[] getMessageHash(byte[] message, String algoritmo) throws NoSuchAlgorithmException {
		byte[] hash = null;
		MessageDigest md = null;
		md = MessageDigest.getInstance(algoritmo);
		md.update(message);
		hash = md.digest();
		md.reset();
		return hash;
	}
    public static String toHexString(byte[] b) {
        char[] hexChar = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };

        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            // look up high nibble char 
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);

            // look up low nibble char 
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }

	public static String obtenValorOpcional(HttpServletRequest request, String param) {
		String valor=request.getParameter(param);
		if (valor==null || valor.equals(Constantes.CAD_VACIA) || valor.equals("undefined") || valor.equals("null"))
				return null;
		return desdeUTF8(valor);
	}
	public static String obtenValor(HttpServletRequest request, String param) throws ErrorConexionPerdida {
		String valor=request.getParameter(param);
		if (valor==null)
			throw new ErrorConexionPerdida("No viene el parámetro "+param);
		else if (valor.equals("null") || valor.equals("undefined"))
			return null;
		return desdeUTF8(valor);
		}
	public static boolean obtenValorOpcionalBoolean(HttpServletRequest request, String param) {
		String valor=request.getParameter(param);
		if (valor==null)
			return false;
		return obtenValorBoolean(request, param);
		}
	public static boolean obtenValorBoolean(HttpServletRequest request, String param) throws ErrorConexionPerdida {
		String valor=request.getParameter(param);
		boolean ret=false;
		if (valor==null)
			throw new ErrorConexionPerdida("No viene el parámetro "+param);
		else if (valor.equals("null") || valor.equals("undefined"))
			ret=false;
		else if (valor.equals("1") || valor.equalsIgnoreCase("true"))
			ret=true;
		else if (valor.equals("0") || valor.equalsIgnoreCase("false"))
			ret=false;
		
		return ret;
		}
	public static String desdeUTF8(String valor){
		try {
			int numInterrogantes=Util.split(valor, "?").size()-1;
			int numInterrogantesDespues;
			String ret=new String(valor.getBytes(Constantes.ISO88591), Constantes.UTF8);
			numInterrogantesDespues=Util.split(ret, "?").size()-1;
			if (ret.contains(Constantes.CARACTER_NO_SOPORTADO) || numInterrogantes!=numInterrogantesDespues)
				return valor;
			return ret;
			} 
		catch (UnsupportedEncodingException e) {
			return valor;
			}
		}
	public static String obtenNombreTabla(String lecturaN) {
		String tabla=null;
		tabla = lecturaN.substring(lecturaN.toLowerCase().indexOf("from")+4).trim();
		int i=tabla.indexOf(Constantes.ESPACIO);
		if (i>-1) 
			tabla = tabla.substring(0,i);
		return tabla;
	}

	public static ArrayList<Object> repiteArrayList(String s, int num){
		ArrayList<Object> ret=new ArrayList<Object>();
		for (int i=0; i<num; i++)
			ret.add(s);
		return ret;
	}
	public static String repite(String s, int num) {
		StringBuilder ret=new StringBuilder();
		for (int i=0; i<num; i++)
			ret.append(s);
		return ret.toString();
	}
	public static String[] imagenes(String ruta){
		File f = new File(ruta);
		return f.list();
	}
	
	public static ArrayList<Object> creaLista(Object...p){
		ArrayList<Object> ret= new ArrayList<Object> ();
		for (Object pp : p)
			ret.add(pp);
		
		return ret;
		}
	public static ArrayList<String> creaListaString(Object...p){
		ArrayList<String> ret= new ArrayList<String>();
		ArrayList<Object> or=creaLista(p);
		
		for (int i=0; i<or.size(); i++)
			ret.add( or.get(i).toString() );
		return ret;
		}
	public static HashMap<String, Object> creaDic(Object...p){
		HashMap<String, Object> ret= new HashMap<String, Object>();
		for (int i=0; i<p.length; i+=2)
			ret.put(p[i].toString(), p[i+1]);
		
		return ret;
		}
	public static String nombreWeb(String nombreFichero)	{
		String ret=nombreFichero;
		String barraWin="\\"; String barra="/";
		
		ret=Util.replaceTodos(ret, barraWin, barra);
		int ultimaBarra=ret.lastIndexOf(barra);
		return ret.substring(ultimaBarra+1);
		}
	
	public static String sustituyePlantilla(String plantilla, String usu){
		String ret= Util.replaceTodos(plantilla, "$1", usu);
		return ret;
		}
	public static ArrayList<ArrayList<String>> cartesiano(ArrayList<ArrayList<String>> listasEntrada){
		ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();
		if (listasEntrada.size()==1){
			for (String s: listasEntrada.get(0)){
				ArrayList<String> elem=new ArrayList<String>();
				elem.add(s);
				ret.add(elem);
				}
			}
		else	{
			ArrayList<String> primero = listasEntrada.get(0);
			listasEntrada.remove(0);
			for (String s: primero){
				for (ArrayList<String> combResto:cartesiano(listasEntrada)){
					combResto.add(0, s);
					ret.add(combResto);
					}
				}
			listasEntrada.add(0, primero);
			}
		return ret;
		}

	public static String arreglaRutaFisica(String ruta){
		String ret=ruta;
		String barraWin="\\"; String barra="/";
		String dosBarras="//"; String barraPuntoBarra="/./";
		
		boolean empieza=false;
		empieza=ruta.startsWith(dosBarras) || ruta.startsWith(barraWin+barraWin);
		ret=Util.replaceTodos(ret, barraWin, barra);
		ret=Util.replaceTodos(ret, dosBarras, barra);
		ret=Util.replaceTodos(ret, barraPuntoBarra, barra);
		
		if (empieza)
			ret=barra+ret;
		return ret;
	}
	public static String sacaDirectorioDeArchivo(String rutaArchivo){
		ArrayList<String> temp=split(rutaArchivo, "/");
		temp.remove( temp.size()-1 );
		return Util.join("/", temp);
		}
	
	public static String rutaFisicaRelativaOAbsoluta(String ruta){
		return rutaFisicaRelativaOAbsoluta(ruta, PoolAplicaciones.ctx.getRealPath("/"));
		}
        public static String rutaFisicaRelativaOAbsoluta(String ruta, String directorioReferencia){
		String barra="/";
		ruta=arreglaRutaFisica(ruta);
		
		if (!directorioReferencia.endsWith(barra))
			directorioReferencia+=barra;
		
		if (ruta.startsWith(Constantes.PUNTO)) {
			ruta=directorioReferencia+ruta;
			ruta=arreglaRutaFisica(ruta);
			}
		else if (ruta.startsWith(barra)){//ruta absoluta unix
			//
			}
		else if (ruta.substring(1,3).equals(":/")){//ruta absoluta win
			//
			}
		else { //nombre de archivo, desde el directorio de referencia (dotWeb para dots, rptWeb para rpt...)
			ruta=directorioReferencia+ruta;
			}
		
		String os=System.getProperty("os.name");
		if (ruta.startsWith(barra) && os.toLowerCase().startsWith("windows"))
			ruta="c:"+ruta;
		else if (ruta.startsWith("./"))
			ruta=arreglaRutaFisica(PoolAplicaciones.ctx.getRealPath("/")+ruta);
		
		return ruta;
		}
 	public static String rutaWebRelativaOAbsoluta(Usuario usr, String ruta) throws ErrorArrancandoAplicacion{ 
 		return rutaWebRelativaOAbsoluta(usr.getApli(), ruta);
 	 	}
 	public static String rutaWebRelativaOAbsoluta(Aplicacion apli, String ruta) throws ErrorArrancandoAplicacion{ 
 		ruta=ruta.trim();
 	 	if (ruta.startsWith(Constantes.PUNTO)){
 	 		/*pass*/
 	 		}
 	 	else if (ruta.startsWith("http:") || ruta.startsWith("ftp:") || ruta.startsWith("https:") || ruta.startsWith("file:") ){
 	 		/*pass*/
	 		}
 	 	else
 	 		ruta=apli.getUrlAplicacion()+ruta.substring(1); 
 	 	return ruta; 
 	 	}
 	public static void resetCookies(String aplicacion, HttpServletResponse response){
		int caducada=0; //An age of 0 is defined to mean "delete cookie"
		Cookie gUsu = new Cookie("usu"+aplicacion, "-"); 
//		gUsu.setSecure(true); 
		gUsu.setMaxAge(caducada);
		response.addCookie(gUsu);
		
		Cookie gPws = new Cookie("pws"+aplicacion, "-"); 
//		gPws.setSecure(true); 
		gPws.setMaxAge(caducada);
		response.addCookie(gPws);
	}
 	public static void setCookies(String aplicacion, String login, String password, HttpServletResponse response){
		String usuEmbrollado= Util.embrollar(login) ;
		String pwsEmbrollado= Util.embrollar(password) ;
		
		int unMes=2628000;
		Cookie gUsu = new Cookie("usu"+aplicacion, usuEmbrollado); 
//		gUsu.setSecure(true); 
		gUsu.setMaxAge(unMes);
		response.addCookie(gUsu);
		
		Cookie gPws = new Cookie("pws"+aplicacion, pwsEmbrollado); 
//		gPws.setSecure(true); 
		gPws.setMaxAge(unMes);
		response.addCookie(gPws);
		}

 	public static Boolean esNivelJython(String fuente){
 		return fuente.startsWith("#!jython");
 		}
 	public static INivelDef creaNivelJython(String cd_nivel, String fuente, String parametros, Aplicacion apli) throws ErrorGotta {
 		INivelDef nivel;
 		apli.log.debug("Creando nivel jython: "+ cd_nivel);
		try{
			PythonInterpreter _jy = es.burke.gotta.dll.DLLJython.getJython(cd_nivel, fuente,apli);
			PyModule pyModule = (PyModule)_jy.get(cd_nivel);
			PyString name = new PyString(cd_nivel);
			PyType nivelDef = (PyType) pyModule.__getattr__(name);
			nivel = (INivelDef) nivelDef.__call__(name, new PyString(parametros)).__tojava__(INivelDef.class);
			nivel.setTexto(fuente);

			return nivel;
			}
		catch(Exception e){
			String texto="Error leyendo nivel "+cd_nivel+". Descripción del error: "+(e.getMessage()!=null?e.getMessage():e.toString()) ;
			apli.erroresArranque.append(texto+"\n");
			
	 		apli.log.warn("ERROR creando nivel jython: "+ cd_nivel);
	 		apli.log.warn(e);
			throw new ErrorGotta("Error creando nivel Jython: "+e.getMessage());
			}
 	}
	static Boolean esProcAlmacenado(String sql){
		if(esNivelJython(sql))
			return false;
		sql=sql.trim().toLowerCase();
		final String[] prefijos={"{call", "select"};
		for (String prefijo : prefijos){
			if (sql.startsWith(prefijo))
				return false;
			}
		return true;
	}
	
	public static void ponMime(HttpServletResponse res, String f){
		FileTypeMap s = FileTypeMap.getDefaultFileTypeMap();
		res.setContentType(s.getContentType(f));
	}
    public static String capitalize(String s) {
    	if (s==null)return Constantes.CAD_VACIA;
        if (s.length() == 0) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
    public static BigInteger parteEntera(Object o){
    	return new BigDecimal(o.toString()).toBigInteger();
    }
    public static HashMap<String, String> sacaDeURL(String url){
		HashMap<String, String> ret=new HashMap<String, String>();
		String[] trozos = url.split("&");
		String nombre, valor;
		for (String s:trozos){
			String[] nomval = s.split("=");
			nombre = nomval[0];
			
			if (nomval.length<2)
				valor=null;
			else
				valor = nomval[1];
			
			ret.put(nombre, valor);
			}
		return ret;
    	}
	public static String formateaWord(Object exp, String formato) {
		if (exp==null || exp.equals(Constantes.CAD_VACIA))
			return Constantes.CAD_VACIA;

		String valor;
		if (	formato.equalsIgnoreCase("DMAL") || //trece de febrero de dos mil uno
				formato.equalsIgnoreCase("DML")  || //trece de febrero de 2001
				formato.equalsIgnoreCase("ML")   ){ //13 de febrero de 2001
			
			FechaGotta fg = FechaGotta.comoFechaGotta(exp);
			valor=NumeroTexto.get(fg, formato);
			}
		
		else if (	formato.equalsIgnoreCase("NLF") || 
					formato.equalsIgnoreCase("NLM")	||
					formato.equalsIgnoreCase("NLE")){
			valor=NumeroTexto.get(exp.toString(), formato);
			}
		else if (formato.equalsIgnoreCase("EXC"))
			valor=Constantes.CAD_VACIA;
		else if (exp instanceof BigDecimal)
			valor=formatearNumero((BigDecimal) exp);
		else if (exp instanceof Long)
			valor=formatearNumero(new BigDecimal((Long)exp));
		else if (exp instanceof Integer)
			valor=formatearNumero((Integer) exp,0);
		else
			valor=exp.toString();
		return valor;
		}
	public static Logger getLog() {
		return getLogger("gotta");
	}
	
	public static String msgError(Exception e){
		String ret=Constantes.CAD_VACIA;
		ret+=e.getMessage();
		if (e.getCause()!=null){
			if (!ret.contains(e.getCause().getMessage()))
				ret+=". "+e.getCause().getMessage();
			}
		if (ret.equals(Constantes.CAD_VACIA) && !(e instanceof ErrorConexionPerdida))
			ret=e.toString();
		return ret;
	}
	public static void ponProxy(Aplicacion apli) {
		String proxy_server, proxy_port, proxy_user, proxy_password;
		
		proxy_server=apli.getDatoConfig("http_proxy_server");
		proxy_port=apli.getDatoConfig("http_proxy_port");
		proxy_user=apli.getDatoConfig("http_proxy_user");
		proxy_password=apli.getDatoConfig("http_proxy_password");
		
		if (proxy_server!=null) System.setProperty("http.proxyHost", proxy_server);
		if (proxy_port!=null) System.setProperty("http.proxyPort", proxy_port);
		if (proxy_user!=null) System.setProperty("http.proxyUser", proxy_user);
		if (proxy_password!=null) System.setProperty("http.proxyPassword", proxy_password);		
		}
	public static int cuentaNoNulos(ArrayList<Object> param) {
		int ret=0;
		for (int i=0; i<param.size(); i++){
			if (param.get(i)==null || param.get(i).equals(Constantes.CAD_VACIA)){
				//pass
				}
			else
				ret++; 
			}
		return ret;
		}

	public static String sacaUrlDeOrigen(Aplicacion apli, HttpServletRequest request, boolean soloParametros){
		String url=Util.toString(request.getHeader("referer"), ".");
		
		if (apli==null)//la apli no arrancó
			return "./login.jsp?";
		
		String soloApli="aplicacion="+apli.getCd();
		if (url.contains(soloApli)){
			if (soloParametros)
				return url.substring(apli.getUrlAplicacion().length());
			else
				return url;
			}
		else
			return "?"+soloApli;
		}

	public static void creaNuevaFila(ITabla acciones, Long CD_Camino, String CD_Tramite, int CD_Operacion,
			String condicion, String accion, String p1, String p2, String p3, String p4) throws ErrorCampoNoExiste, ErrorFilaNoExiste, ErrorTiposNoCoinciden {
		acciones.addNew();
		acciones.setValorCol("cd_camino",  CD_Camino); 
		acciones.setValorCol("cd_tramite",  CD_Tramite);
		acciones.setValorCol("cd_operacion",CD_Operacion);
		acciones.setValorCol("condicion",   condicion);
		acciones.setValorCol("cd_accion",   accion);
		acciones.setValorCol("parametro1",  p1);
		acciones.setValorCol("parametro2",  p2);
		acciones.setValorCol("parametro3",  p3);
		acciones.setValorCol("parametro4",  p4);
		
		}
	public static JSONObject JSONFilas(Coleccion<String> col) throws JSONException {
		JSONObject ret = new JSONObject();
		
		JSONArray cols=new JSONArray().put("cd").put("ds");
		ret.put("columnas", cols);
		
		JSONArray filas=new JSONArray();
		for (String k:col.claves){
			JSONArray fila=new JSONArray().put(k).put(col.get(k));
			filas.put(fila);
			}
		ret.put("filas", filas);
		return ret;
	}	
}

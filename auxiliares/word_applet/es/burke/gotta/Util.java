package es.burke.gotta;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import es.burke.gotta.dll.Constantes;
import es.burke.misc.NumeroTexto;

public class Util {
	public static DecimalFormat formatoDecimal=null;
	public final static String separadorDecimal=",";
	public final static String separadorMiles=Constantes.PUNTO;
	final static String patronMoneda="#"+separadorDecimal+"##0"+separadorMiles+"00"; //###.##0,00
	public final static String patronNumeros="#,##0"; //esto es para los miles, hay que indicarselo como si fuéramos guiris
/////////	
	public static ArrayList<String> split(String cadena){
		return splitTrim(cadena, Constantes.ESPACIO, true);}
	public static ArrayList<String> split(String cadena, String sep){
		return splitTrim(cadena, sep, true);}
	public static ArrayList<String> splitTrim(String cadena, String sep, boolean trim) {
		int pos1, pos2;
		ArrayList<String> v = new ArrayList<String>();
		StringBuffer sb = new StringBuffer(cadena);				
		if (sep.equals(Constantes.CAD_VACIA)){
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
///////////	
	public static BigDecimal redondeo(BigDecimal dato, int ndecimales){
		return dato.setScale(ndecimales, BigDecimal.ROUND_HALF_UP);
		}
	public static String rfill(String cadena, String rellenar, int num){
		for (int i=0; i<num;i++)
			cadena+=rellenar;
		return cadena;
		}
	public static String _formatearNum(String pvalor, String pattern){
		String valor=pvalor;
		if ( valor.equals(Constantes.CAD_VACIA))
			return valor;
		valor = split(valor).get(0);
				
		if (formatoDecimal==null) {
			formatoDecimal= new DecimalFormat();
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setCurrencySymbol(Constantes.CAD_VACIA);
			dfs.setGroupingSeparator(separadorMiles.charAt(0));
			dfs.setDecimalSeparator(separadorDecimal.charAt(0));
			
			formatoDecimal.setDecimalFormatSymbols(dfs);
			}
			
		formatoDecimal.applyPattern(pattern);
		ArrayList<String> aux = split(pattern, ".");
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
			{//pass
			}
	
		return valor;
	}
/////////	
	public static String toString(Object o, String valorAlternativo){
		return o == null?valorAlternativo:o.toString();
		}
	public static String noNulo(Object o){
		return toString(o, Constantes.CAD_VACIA);}
/////////	
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
	public static String formatearNumero(Integer num, int nPosiciones){
		String ret = num.toString();
		while(ret.length()<nPosiciones)
			ret='0'+ret;
		return ret;
		}
	public static String formatearNumero(BigDecimal num){
		return formatearNumero(num.toString(), num.scale());
		}
	public static String formateaWord(Object exp, String formato) {	
		if (exp==null || exp.equals(Constantes.CAD_VACIA))
			return "";
		System.out.println("***");
		System.out.println(exp);
		System.out.println(exp.getClass().getCanonicalName());
		System.out.println(">>>");
	
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
		
		else if (formato.equalsIgnoreCase("EXC")){
			valor="";
			}
		else if (exp instanceof BigDecimal){
			valor=formatearNumero((BigDecimal) exp);
			}
		else if (exp instanceof Long){
			valor=formatearNumero(new BigDecimal((Long)exp));
			}
		else if (exp instanceof Integer){
			valor=formatearNumero((Integer) exp,0);
			}
		else
			valor=exp.toString();
		System.out.println(valor);
		System.out.println();
		return valor;
		}
	public static String join(String token, Iterable<?> lista )	{
		StringBuffer sb = new StringBuffer();
		for(Object s:lista){
			if (s!=null)
				sb.append( s );
			sb.append( token );
			}
		if (sb.length()==0)
			return "";
		return sb.substring(0, sb.length()-token.length());
		}
	public static ArrayList<String> reverse(ArrayList<String> v) {
		if (v==null||v.size()<=1)
			return v;
		ArrayList<String> vret = new ArrayList<String>(v.size());
		for (int i=v.size()-1;i>=0 ;i--)
			vret.add(v.get(i));
		return vret;
		}
	public static String replaceTodos(String cadena, String car_a_sustituir, String car) {
		return replace(cadena, car_a_sustituir, car, 0);
		}
/////////
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
///////	
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
}

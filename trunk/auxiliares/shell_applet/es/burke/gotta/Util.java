package es.burke.gotta;

import java.util.ArrayList;

public class Util {
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
/////////	
	public static String toString(Object o, String valorAlternativo){
		return o == null?valorAlternativo:o.toString();
		}
	public static String noNulo(Object o){
		return toString(o, Constantes.CAD_VACIA);}
/////////	
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

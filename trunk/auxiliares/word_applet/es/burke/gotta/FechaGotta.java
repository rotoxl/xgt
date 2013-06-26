package es.burke.gotta;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import es.burke.gotta.dll.Constantes;

public class FechaGotta extends GregorianCalendar 
{
	final static String formatoFecha = "dd/MM/yyyy";
	final static String formatoFechaYMD = "yyyy/MM/dd";
	
	final static String formatoFechaHora = "dd/MM/yyyy HH:mm:ss";
	final static String formatoFechaHoraMili = "dd/MM/yyyy HH:mm:ss.SSS";
	
	public final static String formatoFechaHoraYMD = "yyyy/MM/dd HH:mm:ss";
	public final static String formatoFechaHoraYMDMili = "yyyy/MM/dd HH:mm:ss.SSS";
	public final static String formatoFechaHoraISO = "yyyy-MM-dd HH:mm:ss";
	public final static String formatoFechaHoraISOMili = "yyyy-MM-dd HH:mm:ss.SSS";
	private static final long serialVersionUID = 5684207581020073472L;

	public Double fechaCrystal()
		{
		double origenJava=25570; // CDbl(CDate("01/01/1970"))
		double dif_dias=origenJava+ ((this.getTimeInMillis()+3600000)/(1000*3600*24));// el 36 es xq Java basa las horas en 0
		return dif_dias;
		}
	@Override
	public boolean equals(Object f) {
		if (f instanceof FechaGotta)
			return this.toString().equals(((FechaGotta)f).toString());
		else
			return false;
	}
	@Override
	public String toString()
	{
		if (this!=null)
		{
			StringBuffer cadena = new StringBuffer();
			int d=this.get(Calendar.DAY_OF_MONTH);
			int m=this.get(Calendar.MONTH)+1;
			int y=this.get(Calendar.YEAR);
			int h=this.get(Calendar.HOUR_OF_DAY);
			int min=this.get(Calendar.MINUTE);
			int sec=this.get(Calendar.SECOND);
			int mili=this.get(Calendar.MILLISECOND);
			
			String dia=completaConCero(d);
			String mes=completaConCero(m);
			String anio=completaConCero(y);
			String hora=completaConCero(h);
			String minutos=completaConCero(min);
			String segundos=completaConCero(sec);

			if (d==31 && m==12 && y==2)
				cadena.append(hora+":"+minutos+":"+segundos);
			else if (h==00 && min==00 && sec==00)
				cadena.append(dia+"/"+mes+"/"+anio);
			else
				{
				cadena.append(dia+"/"+mes+"/"+anio);
				cadena.append(Constantes.ESPACIO);
				cadena.append(hora+":"+minutos+":"+segundos);
				}
			if(mili!=0)
				cadena.append("."+completaConCero(""+mili, 3) );
			return cadena.toString();
		}
		return null;
	}
	private static String completaConCero(int n)
		{return completaConCero(""+n,2);}
	private static String completaConCero(String pvalor,int n)
		{
		String valor=pvalor;
		while(valor.length()<n)
			valor="0"+valor;
		return valor;
		} 
	public FechaGotta (Date valor) throws ErrorFechaIncorrecta
		{
		if (valor == null)
			throw new ErrorFechaIncorrecta("La fecha es nula");
		
		this.setTimeInMillis(valor.getTime());
		}
	public FechaGotta (String valor) throws ErrorFechaIncorrecta{
		int length = valor.length();
		if(length!=formatoFechaHora.length() 
				&& length!=formatoFecha.length() 
				&& length!=formatoFechaHoraMili.length() ){
			throw new ErrorFechaIncorrecta("La fecha es incorrecta");
		}
		try {
			valor=valor.replace("-", "/");
			
			boolean llevaHora=false;
			boolean llevaMilis=false;
			if (valor.contains(Constantes.PUNTO))
				llevaMilis=true;
			else if (valor.contains(Constantes.ESPACIO))
				llevaHora=true;
			
			String formato=formatoFecha;
			if (! valor.substring(2,3).equals("/")){
				if(llevaMilis)
					formato= formatoFechaHoraYMDMili;
				else if(llevaHora)
					formato= formatoFechaHoraYMD;
				else
					formato= formatoFechaYMD;
				}
			else{
				if(llevaMilis)
					formato= formatoFechaHoraMili;
				else if(llevaHora)
					formato= formatoFechaHora;
				else
					formato= formatoFecha;
				}
			java.text.DateFormat df = new SimpleDateFormat(formato);			
			Date d= df.parse(valor);
		    
			this.setTimeInMillis(d.getTime());
			}
		catch (java.text.ParseException e){
			throw new ErrorFechaIncorrecta("La fecha es incorrecta");
			}
		}
	public FechaGotta() {
		//pass
		}
	public static boolean esFecha(String valor){
		try {
			new FechaGotta(valor);
			return true;
			}
		catch (ErrorFechaIncorrecta pe)
			{return false;}
		}
	public String formatoEspecial(String formato) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(formato);
		return formatter.format(new Date(this.getTimeInMillis()));
	}
	
	public static FechaGotta fechaActual(){
		Calendar cal = Calendar.getInstance();
	    try {
			return new FechaGotta(cal.getTime());
			} 
	    catch (ErrorFechaIncorrecta e) {
			return null;
			}
		}
	public static FechaGotta comoFechaGotta(Object v) {
		if(v==null)
			return null;
		if (v instanceof FechaGotta)
			return (FechaGotta) v;
		try {
			if (v instanceof Date)
				return new FechaGotta((Date)v);
			return new FechaGotta(v.toString());
			}
		catch (StringIndexOutOfBoundsException e){
			return null;
			}
		catch (ErrorFechaIncorrecta e) {
			return null;
			}
		}
    @Override
	public Object clone(){
    	return super.clone();
    }

}


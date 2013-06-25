package es.burke.misc;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.burke.gotta.FechaGotta;
import es.burke.gotta.Util;

public class NumeroTexto {

	private static String[] text ={"cero","un*","dos","tres","cuatro","cinco",
		"seis","siete","ocho","nueve","diez","once","doce","trece","catorce",
		"quince","dieci","veinti","veinte","treinta","cuarenta","cincuenta",
		"sesenta","setenta","ochenta","noventa","ciento","doscientos","trescientos",
		"cuatrocientos","quinientos","seiscientos","setecientos","ochocientos",
		"novecientos","cien", "mil"};
	public static String get(int i, boolean uno){
		String acabaEn=uno?"o":"";
		if (i==-1) {
			if (acabaEn.equals("o"))
				return "menos un";
			else
				return "menos una";
			} 
		if (i==1){
			if (acabaEn.equals("o"))
				return "un";
			else
				return "una";
			}
		
		return get(i, acabaEn);
		}
	public static String get(int i, String acabaEn) {		
		if (i<-1) return "menos "+get(-i, acabaEn);
		
		if (i<=15) return text[i].replace("*", acabaEn);
		if (i<20) return (text[16]+text[i-10]).replace("ciseis", "ciséis");
		if (i==20) return text[18];
		if (i<30) return (text[17]+text[i-20])
		                               .replace("*", acabaEn)
		                               .replace("tiun", "tiún")
		                               .replace("tiúno", "tiuno")
		                               .replace("tidos", "tidós")
		                               .replace("titres", "titrés")
		                               .replace("tiseis", "tiséis");
		if (i<100) {
			String decenas = text[i/10+16];
			if (i%10==0)
				return decenas;
			return decenas+" y "+text[i%10].replace("*", acabaEn);
			}
		if (i==100) return text[35];
		if (i<1000) {
			String cientos = text[25+i/100];
			if (i%100==0)
				return cientos;
			return cientos+" "+get(i%100, acabaEn);
		}
		if (i==1000)
			return text[36];
		if (i<2000)
			return text[36]+" "+get(i%1000, acabaEn);

		if (i<1000000) {
			if (i%1000==0)
				return get(i/1000, false)+" mil";
			else
				return get(i/1000, false)+" mil "+get(i%1000, acabaEn);
		}
		if (i<2000000)
			return get(i/1000000, false)+" millón "+(i%1000000==0?"":get(i%1000000, acabaEn));
		return get(i/1000000, false)+" millones "+(i%1000000==0?"":get(i%1000000, acabaEn));
	}
	public static String get(String exp, String formato){
		String valor="";
		if (formato.equalsIgnoreCase("NLF"))
			valor=NumeroTexto.get(Integer.parseInt(exp), "a");
			
		else if (formato.equalsIgnoreCase("NLM"))
			valor=NumeroTexto.get(Integer.parseInt(exp), true);
			
		else if (formato.equalsIgnoreCase("NLE")){
			BigDecimal v = new BigDecimal(exp);
			BigDecimal parteEntera = v.divideToIntegralValue(BigDecimal.ONE);
			BigDecimal parteDecimal = v.subtract(parteEntera).movePointRight(2);
			valor=NumeroTexto.get(parteEntera.intValue(), false)+ " euros";
			
			if (valor.contains("millón  euros"))
				valor=Util.replaceTodos(valor, "millón  euros", "millón de euros");
			else if (valor.contains("millones  euros"))
				valor=Util.replaceTodos(valor, "millones  euros", "millones de euros");
			
			if(!parteDecimal.equals(BigDecimal.ZERO))
				valor += " con "+NumeroTexto.get(parteDecimal.intValue(),false)+" céntimos";
			}
		
		return valor;
		}
	public static String get(FechaGotta fg, String formato){
		String ret="Formato '"+formato+"' desconocido";
		if (formato.equalsIgnoreCase("DMAL")){//trece de febrero de dos mil uno
			SimpleDateFormat formatoFecha = new SimpleDateFormat("' de 'MMMM' de '");
			ret=NumeroTexto.get(fg.get(Calendar.DATE), "o")+formatoFecha.format(fg.getTime())+NumeroTexto.get(fg.get(Calendar.YEAR),true);
			}
		else if (formato.equalsIgnoreCase("DML")){ //trece de febrero de 2001
			SimpleDateFormat formatoFecha = new SimpleDateFormat("' de 'MMMM' de 'yyyy");
			ret=NumeroTexto.get(fg.get(Calendar.DATE), "o")+formatoFecha.format(fg.getTime());
			}
		else if (formato.equalsIgnoreCase("ML")){//13 de febrero de 2001
			SimpleDateFormat formatoFecha = new SimpleDateFormat("d' de 'MMMM' de 'yyyy");
			ret=formatoFecha.format(fg.getTime());
			}
		return ret;
		}

}


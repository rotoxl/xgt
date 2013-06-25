package es.burke.gotta;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EmptyStackException;

import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.burke.gotta.Constantes;

enum TipoToken {OPERADOR,VARIABLE,NUMERO,CADENA,FECHA;}

final class Token  {
	static final long serialVersionUID = -4413529120359848970L;
	
	void setTipo(TipoToken valor) 
		{this.tipoToken=valor;}
	
	TipoToken getTipo()
		{return this.tipoToken;}
	void setValor(String valor)
		{this.obj=valor;}
	String getValor()
		{return this.obj;}
	TipoToken tipoToken;
	String obj;
	@Override
	public String toString(){
		return "Token " + obj + "(" + tipoToken + ")";
	}
}

public final class Evaluador  {
	private static final String REEMPLAZO_ALMOHADA = "ÑñÑ";
	private static final String CERRAR = ")";
	private static final String ABRIR = "(";
	private static final String FALSE = "false";//$NON-NLS-1$
	private static final String TRUE = "true";//$NON-NLS-1$
	private static final String _0 = "0";//$NON-NLS-1$
	private static final String _1 = "1";//$NON-NLS-1$
	private final int STATE_NONE=0;
	private final int ESTADO_OPERANDO=1;
	private final int ESTADO_OPERADOR=2;
	private final int STATE_UNARYOP=3;
	
	private final int PRECISION=16;
	
	private final String UNARY_NEG="(-)";//ojo, esto es un operador y no una operación //$NON-NLS-1$
	private final String LIMITE_STRING="'";				//$NON-NLS-1$
	private final String sepDecimal=",";						//$NON-NLS-1$
	private static HashMap<String,Integer> precedencia=new HashMap<String,Integer>(); {
		precedencia.put(ABRIR, -1);
		precedencia.put(CERRAR, -1);
		precedencia.put(Constantes.IGUAL,  0);
		precedencia.put(Constantes.MAYOR,  0);
		precedencia.put(Constantes.MENOR,  0);
		precedencia.put(Constantes.DISTINTO,  0);
		precedencia.put(Constantes.CONCATENAR,  1);
		precedencia.put(Constantes.SUMA,  2);
		precedencia.put(Constantes.RESTA,  2);
		precedencia.put(Constantes.PORCENTAJE,  2);
		precedencia.put(Constantes.MULTIPLICACIÓN,  3);
		precedencia.put(Constantes.DIVISIÓN,  3);
		precedencia.put(Constantes.POTENCIA,  4);
		precedencia.put(UNARY_NEG, 10);
		}

	Motor motor;
	
	public Evaluador(Motor motor)  {
		this.motor = motor;
	}
	private static String apañaExpresión(String cadena) {
		if (cadena==null)
			return null;
		String code=Util.replaceTodos(cadena,"*.", "$.");
		 Pattern p = Pattern.compile("#[a-zA-Z]");
		 Matcher myMatcher = p.matcher(code);
		 StringBuffer myStringBuffer = new StringBuffer();
		 while (myMatcher.find()) {
			String s = myMatcher.group();
			myMatcher.appendReplacement(myStringBuffer, s.replace("#", REEMPLAZO_ALMOHADA));
		 	}
		 myMatcher.appendTail(myStringBuffer);
		code=myStringBuffer.toString();
		return code;
		}
	private static String desApañaExpresión(String cadena){
		cadena=Util.replaceTodos(cadena,"$.$","*.#");
		cadena=Util.replaceTodos(cadena,"$.","*.");
		 Pattern p = Pattern.compile(REEMPLAZO_ALMOHADA+"[a-zA-Z]");
		 Matcher myMatcher = p.matcher(cadena);
		 StringBuffer myStringBuffer = new StringBuffer();
		 while (myMatcher.find()) {
			String s = myMatcher.group();
			myMatcher.appendReplacement(myStringBuffer, s.replace(REEMPLAZO_ALMOHADA, "#"));
		 	}
		 myMatcher.appendTail(myStringBuffer);
		cadena=myStringBuffer.toString();
		return cadena;
		}
	public Object evalua(String cadena)  throws ErrorEvaluador {
		return evalua(cadena, false);
		}
	public Object evalua(String cadena, boolean opLogica)  throws ErrorEvaluador {
		try  { 
			String code = apañaExpresión(cadena);
			Object ret = doEvaluate(infixToPostfix(code), opLogica);
			return ret;
			}
		catch (NumberFormatException e){
			throw new ErrorEvaluador("Se ha producido un error al evaluar la expresiÃ³n:\n"+cadena+"\n"+ e.getMessage());
			}
		catch (ErrorCampoNoExiste e) { 
			throw new ErrorEvaluador(e); 
			}
		catch (ErrorTablaNoExiste e) { 
			throw new ErrorEvaluador(e); 
			}
		catch (ErrorFechaIncorrecta e){ 
			throw new ErrorEvaluador(e); 
			}
		catch (EmptyStackException e){
			throw new ErrorEvaluador(e); 
			}
		catch (ErrorCreandoTabla e) {
			throw new ErrorEvaluador(e); 
			}
		}
	
	public boolean verifica(String cadena) {
		if(cadena==null)
			return true;
		try {
			String code=apañaExpresión(cadena);
			infixToPostfix(code);
			return true;
			}
		catch (ErrorEvaluador e) 
			{return false;}
		catch (EmptyStackException e)
			{ return false; }
		}
	
	private Stack<Token> infixToPostfix(String sExpresion) throws ErrorEvaluador, EmptyStackException {
		Stack<Token> pilaTksOrdenada=new Stack<Token>();
		if(sExpresion==null)
			return pilaTksOrdenada;
		int estadoActual=STATE_NONE;
		int nParenCount=0; //lleva la cuenta de paréntesis
		int i=0;
		String sTemp="";
		String  m_sErrMsg="";

		Stack<String> pilaBuffer=new Stack<String>();
		while(i<sExpresion.length()) {
			String ch=sExpresion.substring(i,i+1);

			if (ch.equals(ABRIR)) {
				if (estadoActual==ESTADO_OPERANDO) {
					//Un operando no puede venir seguido de un paréntesis
					throw new ErrorEvaluador("Se esperaba un operador: "+sExpresion+" pos:"+i);
					}
				if(estadoActual==STATE_UNARYOP)
					{estadoActual=ESTADO_OPERADOR;}
				pilaBuffer.push(ch);
				nParenCount=nParenCount+1;
				}
			else if(ch.equals(CERRAR)) {
				if(estadoActual!=ESTADO_OPERANDO) {
					//Algo distinto de un operando no puede ir seguido de un paréntesis
					throw new ErrorEvaluador("Se esperaba un operando: "+sExpresion+" pos: "+i);
					}
				if(nParenCount==0) 
					throw new ErrorEvaluador("Se ha cerrado un paréntesis sin abrirlo previamente: "+sExpresion+" pos: "+i);
				
				sTemp=pilaBuffer.pop();
				while(!sTemp.equals(ABRIR)) {
					Token token=new Token();
					token.setTipo(TipoToken.OPERADOR);
					token.setValor(sTemp);
					pilaTksOrdenada.push(token);
					sTemp=pilaBuffer.pop();
					}
				nParenCount=nParenCount-1;
				}
			else if (esOperadorVálido(ch)) {
				if(estadoActual==ESTADO_OPERANDO) {
					while(pilaBuffer.size()>0) {
						int salirDelWhile=0;

						// Comprobamos el orden de precedencia del operador actual con los introducidos en pilaBuffer

						if((getPrecedencia(pilaBuffer.peek()))<(getPrecedencia(ch))) {
							salirDelWhile=1;
							break;
							}
						if(salirDelWhile==0) {
							// Si el operador actual no prevalece sobre los incluidos en pilaBuffer, 
							// se añade como un token a la pila ordenada(pilaTksOrdenada) 

							Token token=new Token();
							token.setTipo(TipoToken.OPERADOR);
							token.setValor(pilaBuffer.pop());
							pilaTksOrdenada.push(token);
							}
						}

					// Si el operador actual prevalece sobre los incluidos en pilaBuffer, se añade a pilaBuffer
					pilaBuffer.push(ch);
					estadoActual=ESTADO_OPERADOR;
					}
				else if(estadoActual==STATE_UNARYOP) 
					throw new ErrorEvaluador("Se esperaba un operando"+sExpresion+" pos: "+i);
				else {
					if (ch.equals(Constantes.RESTA)) {
						pilaBuffer.push(UNARY_NEG);
						estadoActual=STATE_UNARYOP;
						}
					else if(ch.equals(Constantes.SUMA))
						estadoActual=STATE_UNARYOP;
					else 
						throw new ErrorEvaluador("Se esperaba un operando: "+sExpresion+" pos: "+i);
					
					}
				}
			else if(ch.equals(LIMITE_STRING)) {
				// Lo incluido entre comillas('') se trata como un string

				if(estadoActual==ESTADO_OPERANDO)
					throw new ErrorEvaluador("Se esperaba un operando_ "+sExpresion+" pos: "+i);

				sTemp="";
				i=i+1;
				try {
					ch=sExpresion.substring(i,i+1);}
				catch(Exception e) {
					throw new ErrorEvaluador("Se esperaba un operando: "+sExpresion+" pos: "+i);}
				
				while(!LIMITE_STRING.equals(ch)) {
					if (ch.equals(Constantes.POTENCIA))
						ch="'";
					sTemp=sTemp.concat(ch);
					i=i+1;
					if(i>sExpresion.length())
						break;
					try {
						ch=sExpresion.substring(i,i+1);}
					catch(StringIndexOutOfBoundsException e) {
						throw new ErrorEvaluador("error en la expresión: "+sExpresion);}				
					}
				Token token=new Token();
				token.setValor(sTemp);
				token.setTipo(TipoToken.CADENA);
				pilaTksOrdenada.push(token);
				estadoActual=ESTADO_OPERANDO;
				}
			else if(esNúmero(ch)) {
				if(estadoActual==ESTADO_OPERANDO) {
					// Un operando no puede ir seguido de otro operando
					throw new ErrorEvaluador("Se esperaba un operador: "+sExpresion+" pos: "+i);					
					}
				sTemp="";
				int bDecPoint=0;
				while (esNúmero(ch) || ch.equals(".")) {
					if (ch.equals(sepDecimal)) {
						if(bDecPoint!=0)
							throw new ErrorEvaluador("El operando contiene múltiples puntos decimales: "+sExpresion+" pos: "+i);
						bDecPoint=1;
						}
					sTemp=sTemp+ch;
					i=i+1;
					if(i>=sExpresion.length())
						break;
					ch=sExpresion.substring(i,i+1);
					}
				i=i-1;
				if(sTemp.equals(sepDecimal))
					throw new ErrorEvaluador("Operando no válido: "+sExpresion+" pos: "+i);
				
				sTemp=sTemp.replace(',','.');
				Token token=new Token();
				token.setTipo(TipoToken.NUMERO);
				token.setValor(sTemp);
				pilaTksOrdenada.push(token);
				estadoActual=ESTADO_OPERANDO;
			}
			else if(ch.equals(Constantes.ESPACIO)){
				//pass
				}
			else {
				if(estadoActual==ESTADO_OPERANDO)
					throw new ErrorEvaluador("Se esperaba un operador: "+sExpresion+" pos: "+i);
				if (esLetraVálida(ch)) {
					sTemp=ch;
					if(++i<sExpresion.length()) {
						ch=sExpresion.substring(i,i+1);
						while(esCaracterVálido(ch)) {
							sTemp=sTemp+ch;
							if(++i>=sExpresion.length())
								break;
							ch=sExpresion.substring(i,i+1);
							}
						}
					}
				else
					throw new ErrorEvaluador("Encontrado carácter inesperado: "+sExpresion+" pos: "+i+Constantes.ESPACIO+ch);

			
				Token token=new Token();
				token.setTipo(TipoToken.VARIABLE);
				String s = sTemp;

				if (verificaSimbolo(sTemp)) {
					token.setValor(sTemp);
					pilaTksOrdenada.push(token);
					estadoActual=ESTADO_OPERANDO;
					}
				else
					throw new ErrorEvaluador("Error en la expresión. Campo o Variable ("+s+") desconocido: " + m_sErrMsg);				
				i--;
			}
	
			i++;
		}
		if((estadoActual==ESTADO_OPERADOR)||(estadoActual==STATE_UNARYOP))
			throw new ErrorEvaluador("Se esperaba un operando: "+sExpresion+" pos: "+i);
		if(nParenCount>0)
			throw new ErrorEvaluador("Se esperaba cerrar paréntesis_ "+sExpresion+" pos: "+i);
		
		while(pilaBuffer.size()>0) {
			Token token=new Token();
			token.setTipo(TipoToken.OPERADOR);
			token.setValor(pilaBuffer.pop());
			pilaTksOrdenada.push(token);
			}

		return pilaTksOrdenada; //pilaTksOrdenada;
	}

	private Object doEvaluate(Stack<Token> pilaTokens, Object esOpLogica) throws ErrorCampoNoExiste, ErrorTablaNoExiste, ErrorFechaIncorrecta, ErrorEvaluador, ErrorArrancandoAplicacion, ErrorCreandoTabla{
		Stack<String> pilaValores=new Stack<String>();

		String cadena="";
		Boolean opLogica=esOpLogica==null? false : (Boolean)esOpLogica;

		//printPilaTokens(pilaTksOrdenada);

		for(int n=0; n<pilaTokens.size(); n++) {
			/*Recorremos la pila que nos llega de Evaluador(pilaTokens) cargada de objetos de
			tipo Token ordenados, de los cuales sacamos su valor(valorToken) y tipo de valor de token(tipoToken)*/

			Token token=pilaTokens.get(n);
			String valorToken=token.getValor();
			TipoToken tipoToken=token.getTipo();
			//Util.log("token "+n+":"+valorToken+"; tipoToken:"+tipoToken);
			
			if (tipoToken==TipoToken.VARIABLE) {
				//Si es una variable valorToken es el nombre de la variable, y su valor será valorVariable 
				//Comprobamos si el objeto es una variable y obtenemos su valor
				
				ArrayList<Object> vector=this.valorSimbolo(valorToken);
				String valorVariable="";
				
				for (int i=0;i<vector.size();i++ ) {
					if (!valorVariable.equals(""))
						valorVariable=valorVariable+Constantes.SEPARADOR;
					//TODO aquí habría que compararlas con sus tipos correctos
					if (vector.get(i) instanceof Boolean)
						valorVariable+=vector.get(i);
					else
						valorVariable+=concatenarSinFormateoNiNulos( vector.get(i) );
					}
				pilaValores.push(valorVariable);
				}
			else if (tipoToken==TipoToken.NUMERO)//Si no es una variable le asignamos su valor
				pilaValores.push(valorToken);
			else if (tipoToken==TipoToken.CADENA)//Si no es una variable le asignamos su valor
				pilaValores.push(valorToken);
			else if (tipoToken==TipoToken.FECHA)
				pilaValores.push(valorToken);
			else if (tipoToken==TipoToken.OPERADOR) {
				if (valorToken.equals(UNARY_NEG)) {
					/*Comprobamos si el valor es UNARY_NEG "(-)". Si es así, sacamos el último
					elemento de la pila donde almacenamos los valores, le cambiamos el signo
					y lo añadimos otra vez*/
					if ((pilaValores.peek()).contains(Constantes.PUNTO)) {
						BigDecimal num=new BigDecimal(pilaValores.pop());
						num=num.negate();
						valorToken=num.toString();
						}
					else {
						Integer num=new Integer(pilaValores.pop());
						int num1=num.intValue();
						num1=num1*(-1);
						valorToken=String.valueOf(num1);
						}
					pilaValores.push(valorToken);					
				}

				if (valorToken.equals(Constantes.SUMA)) {
					//Util.trace("pilaValores(+):"+pilaValores.toString());
					String op2=pilaValores.pop();
					String op1=pilaValores.pop();

					if (op1.equals(""))	
						op1=_0;
					if (op2.equals(""))	
						op2=_0;

					if (op1.equals(TRUE)) {
						op1=_1;
						opLogica = true;
						}
					else if (op1.equals(FALSE)){
						op1=_0;
						opLogica = true;
						}
					if (op2.equals(TRUE)) {
						op2=_1;
						opLogica = true;
						}
					else if (op2.equals(FALSE)){
						op2=_0;
						opLogica = true;
					}
					
					//son dos números
					if (Util.isNumeric(op1) && Util.isNumeric(op2)) {
						cadena=sumaRestaNumeros(op1, op2, Constantes.SUMA);
						if (cadena.endsWith(".0"))
							cadena = cadena.substring(0, cadena.length()-2);
						}
					//son los dos fechas
					else if (FechaGotta.esFecha(op1) && FechaGotta.esFecha(op2))
						cadena=computaFechas(op1, op2, Constantes.SUMA).toString();
					//numero y fecha
					else if (FechaGotta.esFecha(op1) && Util.isNumeric(op2))
						cadena=sumaRestaFecha(op1, op2,Constantes.SUMA).toString();						
					else if (Util.isNumeric(op1) && FechaGotta.esFecha(op2))
						cadena=sumaRestaFecha(op2, op1,Constantes.SUMA).toString();
					else {//2 cadenas
						cadena=reemplazaRetornoCarro(op1+op2);
						}
					pilaValores.push(cadena);
					}
				else if (valorToken.equals(Constantes.RESTA)) {
					String op2=pilaValores.pop();
					String op1=pilaValores.pop();

					if (op1.equals(""))	
						op1=_0;
					if (op2.equals(""))	
						op2=_0;

					//son dos números
					if (Util.isNumeric(op1) && Util.isNumeric(op2)) {
						cadena=sumaRestaNumeros(op1, op2, Constantes.RESTA);
						if (cadena.endsWith(".0"))
							cadena = cadena.substring(0, cadena.length()-2);
						}
					//son los dos fechas
					else if (FechaGotta.esFecha(op1) && FechaGotta.esFecha(op2))
						cadena=computaFechas(op1, op2, Constantes.RESTA).toString();
					//numero y fecha
					else if (FechaGotta.esFecha(op1))
						cadena=sumaRestaFecha(op1, op2,Constantes.RESTA).toString();						
					else if (FechaGotta.esFecha(op2))
						cadena=sumaRestaFecha(op2, op1,Constantes.RESTA).toString();
					pilaValores.push(cadena);
					}
				else if(valorToken.equals(Constantes.MULTIPLICACIÓN)) {
					//Util.trace("pilaValores(*):"+pilaValores.toString());
					String op1=pilaValores.pop();
					String op2=pilaValores.pop();

					if (op1.equals(""))	{op1=_0;}
					if (op2.equals(""))	{op2=_0;}
					
					
					if (op1.equals(TRUE)) {
						op1=_1;
						opLogica = true;
						}
					else if (op1.equals(FALSE)){
						op1=_0;
						opLogica = true;
						}
					if (op2.equals(TRUE)) {
						op2=_1;
						opLogica = true;
						}
					else if (op2.equals(FALSE)){
						op2=_0;
						opLogica = true;
						}
					
					BigDecimal numero1;
					BigDecimal numero2;
					try {
						numero1 = new BigDecimal(op1);
						numero2 = new BigDecimal (op2);
						} 
					catch (NumberFormatException e) {
						throw new ErrorEvaluador("Error al convertir a número los valores ["+op1+", "+op2+"] para hacer la operación \""+valorToken+"\"");
						}
					BigDecimal resul=numero1.multiply(numero2);
					
					cadena=resul.toString();
					if (cadena.endsWith(".0"))
						cadena = cadena.substring(0, cadena.length()-2);
					pilaValores.push(cadena);
					}
				else if(valorToken.equals(Constantes.DIVISIÓN)) {
					String op2=pilaValores.pop();
					String op1=pilaValores.pop();

					if (op1.equals(""))	{op1=_0;}
					if (op2.equals(""))	{op2=_0;}

					BigDecimal numero1=new BigDecimal(op1);
					BigDecimal numero2=new BigDecimal(op2);
					
					MathContext mc=new MathContext(PRECISION, RoundingMode.HALF_UP);
					BigDecimal resul=numero1.divide(numero2, mc);
					
					cadena=resul.toString();
					if (cadena.endsWith(".0"))
						cadena = cadena.substring(0, cadena.length()-2);

					pilaValores.push(cadena);
					}
				else if(valorToken.equals(Constantes.POTENCIA)) {
					String op2=pilaValores.pop();
					String op1=pilaValores.pop();

					if (op1.equals(""))	{op1=_0;}
					if (op2.equals(""))	{op2=_0;}

					double numero1=hacerDouble(op1);
					double numero2=hacerDouble(op2);
					double resul=Math.pow(numero1,numero2);

					cadena=String.valueOf(resul);
					if (cadena.endsWith(".0"))
						cadena = cadena.substring(0, cadena.length()-2);
					pilaValores.push(cadena);
					}
				else if(valorToken.equals(Constantes.CONCATENAR)){
					//Util.trace("pilaValores(&):"+pilaValores.toString());
					String word2=pilaValores.pop();
					String word1=pilaValores.pop();
					cadena=reemplazaRetornoCarro(word1+word2);
					pilaValores.push(cadena);
					}
				else if (valorToken.equals(Constantes.MAYOR)) {
					opLogica=true;
					String valor2=pilaValores.pop();
					String valor1=pilaValores.pop();

					if(FechaGotta.esFecha(valor1) && FechaGotta.esFecha(valor2)) {
						FechaGotta gc=new FechaGotta(valor1);
						FechaGotta gc2=new FechaGotta(valor2);
						if (gc.after(gc2))
							cadena=_1;
						else
							cadena=_0;
						}
					else if  ((Util.isNumeric(valor1)) && (Util.isNumeric(valor2))) {
						BigDecimal numero1 = hacerBigDecimal(valor1);
						BigDecimal numero2 = hacerBigDecimal(valor2);
						if (numero1.compareTo(numero2)>0)
							cadena=_1;
						else
							cadena=_0;
						}
					else {
						if (valor1.compareTo(valor2)>0)
							cadena=_1;
						else
							cadena=_0;
						}
					pilaValores.push(cadena);
					}
				else if (valorToken.equals(Constantes.MENOR)) {
					opLogica=true;
					String valor2=pilaValores.pop();
					String valor1=pilaValores.pop();


					if (FechaGotta.esFecha(valor1) && FechaGotta.esFecha(valor2)) {
						FechaGotta gc=new FechaGotta(valor1);
						FechaGotta gc2=new FechaGotta(valor2);
						if (gc.after(gc2)||gc.equals(gc2))
							cadena=_0;
						else
							cadena=_1;
						}
					else if ((Util.isNumeric(valor1)) && (Util.isNumeric(valor2))) {
						BigDecimal numero1 = hacerBigDecimal(valor1);
						BigDecimal numero2 = hacerBigDecimal(valor2);
						if (numero1.compareTo(numero2)<0)
							cadena=_1;
						else
							cadena=_0;
						}
					else {
						if (valor1.compareTo(valor2)<0)
							cadena=_1;
						else
							cadena=_0;
						}
					pilaValores.push(cadena);
					}
				else if (valorToken.equals(Constantes.IGUAL)) {
					opLogica=true;
					String valor1=pilaValores.pop();
					String valor2=pilaValores.pop();

					if (valor1.equalsIgnoreCase(TRUE))
							valor1=_1;
					else if (valor1.equalsIgnoreCase(FALSE))	
						valor1=_0;
					
					if (valor2.equalsIgnoreCase(TRUE))	
						valor2=_1;
					if (valor2.equalsIgnoreCase(FALSE))	
						valor2=_0;

					if (Util.isNumeric(valor1) && Util.isNumeric(valor2)) {
						BigDecimal numero1 = hacerBigDecimal(valor1);
						BigDecimal numero2 = hacerBigDecimal(valor2);
						if (numero1.compareTo(numero2)==0)
							cadena=_1;
						else
							cadena=_0;
						}
					else if (FechaGotta.esFecha(valor1) && FechaGotta.esFecha(valor2)) {
						FechaGotta f1 =new FechaGotta(valor1);
						FechaGotta f2 =new FechaGotta(valor2);
						if (f1.equals(f2))
							cadena=_1;
						else
							cadena=_0;
						}
					else {
						if (valor1.equals(valor2))
							cadena=_1;
						else
							cadena=_0;
						}
					pilaValores.push(cadena);
				}

				else if (valorToken.equals(Constantes.DISTINTO)) {
					opLogica=true;					
					String valor1=pilaValores.pop();
					String valor2=pilaValores.pop();
					
					if (valor1.equals(TRUE))	
						valor1=_1;
					else if (valor1.equals(FALSE))	
						valor1=_0;
					if (valor2.equals(TRUE))	
						valor2=_1;
					else if (valor2.equals(FALSE))	
						valor2=_0;
					
					if (Util.isNumeric(valor1) && Util.isNumeric(valor2)) {
						BigDecimal numero1 = hacerBigDecimal(valor1);
						BigDecimal numero2 = hacerBigDecimal(valor2);
						if (numero1.compareTo(numero2)==0)
							cadena=_0;
						else
							cadena=_1;
						}
					else if (FechaGotta.esFecha(valor1) && FechaGotta.esFecha(valor2)) {
						FechaGotta f1 =new FechaGotta(valor1);
						FechaGotta f2 =new FechaGotta(valor2);

						if (!f1.equals(f2))
							cadena=_1;
						else
							cadena=_0;
						}
					else {
						if (valor1.equals(valor2))
							cadena=_0;
						else
							cadena=_1;
						}
					pilaValores.push(cadena);
				} 
			}//else if OPERADOR
		}//for
		
		/*Una vez calculada toda la expresión en la pila pilaValores sólo queda un valor
		que es el resultado*/
		
		Object resp="";
		if (!pilaValores.empty()) {
			resp=pilaValores.get(0);
			if ( (pilaTokens.size()==1) && Util.en(resp.toString(), TRUE, FALSE) )
				opLogica=true;
			if (opLogica) {
				String sresp=resp.toString();
				if (sresp.equals(TRUE))
					resp=true;
				else if (sresp.equals(FALSE))
					resp=false;
				else if (sresp.equals(Constantes.CAD_VACIA))
					resp=false;
				else if (!resp.toString().startsWith(_0))
					resp=true;
				else 
					resp=false;
				}
			}
		if (resp instanceof String)
			return desApañaExpresión(resp.toString());
		return resp;
	}
	private String reemplazaRetornoCarro(String cadena){
		if (cadena.contains("\\n")){
			cadena=Util.replaceTodos(cadena, "\\n", Constantes.vbCr);
			}
		return cadena;
	}
	private String concatenarSinFormateoNiNulos(Object o) {
		String valorAlternativo=Constantes.CAD_VACIA;
		if (o==null)
			return valorAlternativo;
		if ((o instanceof Double ) && 
				o.toString().endsWith(".0")) //EMC ojo porque esto se usa para concatenar muchas historias
			return Util.replaceUltimo(o.toString(), ".0", Constantes.CAD_VACIA);
		if (o instanceof BigDecimal){
			BigDecimal bdo = (BigDecimal)o;
			if(bdo.equals(bdo.longValue())) //EMC ojo porque esto se usa para concatenar muchas historias
			return bdo.toBigIntegerExact().toString();
		}
		return o.toString();
	}

	private double hacerDouble(String valor)
		{return Double.parseDouble(valor);}
	private BigDecimal hacerBigDecimal(String valor)
		{return new BigDecimal(valor);}
	
	private boolean esOperadorVálido(String valor)
		{
		final String operadores="+-*/^><=&#%";
		return operadores.contains(valor);
		}
	private boolean esCaracterVálido(String valor)
		{return (esNúmero(valor) || esLetraVálida(valor) );	}
	private boolean esNúmero(String valor)
		{
		final String secuencia="0123456789,";
		return secuencia.contains(valor);
		}
	private boolean esLetraVálida(String valor)
		{
		final String secuencia="âêîôûäëïöüáéíóúàèìòùabcdefghijklmnñopqrstuvwxyzÂÊÎÔÛÄË�?ÖÜ�?É�?ÓÚÀÈÌÒÙABCDEFGHIJKLMNÑOPQRSTUVWXYZ.$@~_";
		return secuencia.contains(valor);
		}

	private int getPrecedencia(String clave)
		{return precedencia.get(clave);}

	private boolean verificaSimbolo(String x) {
		String s=desApañaExpresión(x);
		
		try {
			return motor.esNombreVariable(s) || motor.existeCampo(s, false);
			} 
		catch (ErrorTablaNoExiste e) {
			return false;
			} 
		}
	private ArrayList<Object> valorSimbolo(String x) throws ErrorCampoNoExiste, ErrorTablaNoExiste, ErrorFechaIncorrecta, ErrorArrancandoAplicacion, ErrorCreandoTabla{
		String s=desApañaExpresión(x);		
		return motor.getValorSimbolo(s);
		}
	
	private FechaGotta sumaRestaFecha(String fecha, String valor, String operador) throws ErrorFechaIncorrecta {
		FechaGotta gC;
		BigDecimal di=new BigDecimal(valor);
		if (operador.equals(Constantes.RESTA)) 
			di=di.multiply( new BigDecimal(-1));
		gC=new FechaGotta(fecha);
		
		//esto lo hago para permitir sumar un segundo a una fecha,  
		//	, espero que no sea una aberración. Menos de un segundo no vamos a permitir
		//	sumar,  y por eso le he puesto aquí precisión 0 en el MathContext
		di=di.multiply(new BigDecimal(24*60*60*1000));
		di=di.setScale(0,BigDecimal.ROUND_HALF_UP);
		
		gC.add(Calendar.MILLISECOND, di.intValue());
		return gC;
		}

	private String sumaRestaNumeros(String op1, String op2, String operador) {
		BigDecimal numero1=new BigDecimal(op1);
		BigDecimal numero2=new BigDecimal(op2);
		BigDecimal resul=operador.equals(Constantes.SUMA)?
				numero1.add(numero2):numero1.subtract(numero2);
		return String.valueOf(resul);
	}

	/* resta o suma fechas dando el resultado en días */
	private Integer computaFechas(String fecha1, String fecha2, String operador) throws ErrorFechaIncorrecta {
		Calendar gregorio1=new FechaGotta(fecha1);
		Calendar gregorio2=new FechaGotta(fecha2);
		double uno=(gregorio1.getTime()).getTime()/(24*60*60*1000);
		double dos=(gregorio2.getTime()).getTime()/(24*60*60*1000);
		double ret;
		if (operador.equals(Constantes.SUMA))
			ret=uno+dos;
		else
			ret=uno-dos;
		
		return new Double(ret).intValue();
	}

}

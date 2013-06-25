package es.burke.gotta.dll;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Accion;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Motor;
import es.burke.gotta.Util;

public class General extends DLLGotta {
	@Override
	public String accionesValidas() {
		return "der izq med mayus minus cadenas cuentacadenas len lpad indexOf trim embrolla desembrolla nodopadre";
		}

	@Override
	public Object ejecuta(Accion accion, Object vvalor) throws ErrorGotta {
		String valor=vvalor.toString();
		String separador=null;
		for(int i=valor.length()-1;i>0;i--){
			if(!Character.isDigit(valor.charAt(i))){
				separador=valor.substring(i,i+1);
				break;
			}
		}
		if (accion.accion.equalsIgnoreCase("der")) {
			ArrayList<String> v=Util.splitTrim(valor, separador, false);
			int num=Integer.parseInt(v.get(1));
			
			if (v.get(0).length() < num)
				return v.get(0);
			return v.get(0).substring(v.get(0).length()-num);
        	}
        else if (accion.accion.equalsIgnoreCase("izq")) {
        	ArrayList<String> v=Util.splitTrim(valor, separador, false);
        	int num=Integer.parseInt(v.get(1));
        	
    		if (v.get(0).length() < num)
    			return v.get(0);
			return v.get(0).substring(0,num);
        	}
        else if (accion.accion.equalsIgnoreCase("med")) {
    		ArrayList<String> v=Util.splitTrim(valor, separador, false);
    		int num1=Integer.parseInt(v.get(1));
    		int num2=Integer.parseInt(v.get(2));
    		
    		if (v.get(0).length() < num1)
    			return "";
    		else if (num1+num2>v.get(0).length())
    			return v.get(0).substring(num1-1);
    		else
    			return v.get(0).substring(num1-1,num1+num2-1);
        	}
        else if (accion.accion.equalsIgnoreCase("mayus")) 
            return  valor.toUpperCase();
        else if (accion.accion.equalsIgnoreCase("minus")) 
            return valor.toLowerCase();
        else if (accion.accion.equalsIgnoreCase("embrolla")) 
    			return Util.embrollar(valor);
        else if (accion.accion.equalsIgnoreCase("desembrolla")) 
    		return Util.desembrollar(valor);
        	
        else if (accion.accion.equalsIgnoreCase("cadenas")) { //cadena¬num¬sep)
    		ArrayList<String> cad=Util.splitTrim(valor,"¬",false);
    		int num=Integer.parseInt(cad.get(1))-1;
    		
    		String cadena, sep;
    		cadena=cad.get(0);
    		if (cad.size()>=3 && !cad.get(2).equals(""))
    			sep=cad.get(2);
    		else
    			sep=Constantes.SEPARADOR;
    		
    		try {
	   			return Util.split(cadena, sep).get(num);
	    		}
    		catch (IndexOutOfBoundsException e){
    			throw new ErrorAccionDLL("Error en DLL.General.Cadenas: "+e.getMessage());
    			}
        	}
        else if (accion.accion.equalsIgnoreCase("CuentaCadenas")) {//cadena¬sep
    		ArrayList<String> cad=Util.split(valor,"¬");
    		String cadena, sep;
    		cadena=cad.get(0);
    		
    		if (cad.size()>0)
    			sep=cad.get(1);
    		else
    			sep=Constantes.SEPARADOR;
    		
    		cad=Util.split(cadena, sep);
    		return cad.size();
        	}
        else if (accion.accion.equalsIgnoreCase("len")) 
            return valor.length();
        	
        else if (accion.accion.equalsIgnoreCase("lpad")) {
    		ArrayList<String> cad = Util.split(valor, Constantes.PUNTO3);
    		return Util.completaPorLaIzquierda(cad.get(0), cad.get(2), new Integer(cad.get(1)).intValue());
        	}
        else if (accion.accion.equalsIgnoreCase("nodopadre")) {
        	ArrayList<String> part = Util.split(mMotor.usuario.arbol.getNodoActivo().rama.cdInicio, Constantes.SEPARADOR);
        	String padre= part.get(0).substring(1);
        	return new Integer(padre);
        	}               
        else if (accion.accion.equalsIgnoreCase("indexOf")) {
    		ArrayList<String> cad=Util.split(valor,"¬");
       		return cad.get(0).indexOf(cad.get(1))+"";
        	}
        else if (accion.accion.equalsIgnoreCase("trim")) 
        	return valor.trim();
        	
		return null;
	}

	@Override
	public void verifica(Motor motor, String accion, String nombre) throws ErrorGotta {
		verificaAccionValida(accion);
		this.mMotor=motor;
		this.usr=motor.usuario;
		}

	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta {
		//
	}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return ejecuta(accion, valor);
	}

}

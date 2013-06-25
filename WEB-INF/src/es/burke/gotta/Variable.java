package es.burke.gotta;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class Variable implements Serializable {
	private static final long serialVersionUID = -648659581789841194L;
	private String nombre;
	private String tipo;
	private Object valor;
	private boolean modificada;
	
	private String variablePadre;

	public Boolean esDeSistema=false;
	public final static String SEPARADOR_VARIABLES_LARGAS="·";
	
	public Variable(Parametro par, Object valor) 
		{this(par.getNombre(), par.getTipo(), valor);}
	public Variable(String nombre, String tipo, Object valor) {	
		this.nombre=nombre;
		if (tipo != null)
			this.tipo=tipo.toLowerCase();
		else
			this.tipo=Constantes.STRING;
		setValor( Variable.getValor(valor, this.tipo) );
		
		if (Util.en(nombre, "@", "~", "$", 
				FirmaTramite.FIRMA_ELECTRÓNICA_ACTIVABLE, FirmaTramite.FIRMA_ELECTRÓNICA_ACTIVA, 
				Motor.CD_ERROR, Motor.DS_ERROR)){
			this.esDeSistema=true;
			this.modificada=false;
			}
		}
	
	public String getNombre(){return this.nombre;}
	public String getTipo(){return this.tipo;}
	public Object getValor(){
		return this.valor;
	}
	static Object getValor(Object valor, String tipo){
		if(valor==null)
			return null;
		if (valor instanceof ArrayList<?>)
			valor=Util.join(Constantes.PUNTO3, (ArrayList<?>)valor);
		
		if ( tipo.equalsIgnoreCase(Constantes.STRING)) // caso más común
			return valor.toString();
		if ( tipo.equalsIgnoreCase(Constantes.MEMO))
			{return valor.toString();	}
		if (tipo.equals(Constantes.BOOLEAN)) {
			if (valor instanceof Boolean)
				return valor;
			if (valor.equals("")) 
				return false;
			if (valor.equals("-1")||valor.equals("1")) 
				return true;
			if (valor.equals("0"))	
				return false;
			if (valor.equals("true"))	
				return true;
			if (valor.equals("false"))	
				return false;
			return valor;
		}
		try{
			if (valor==null || valor.equals("")) 
				return null;
			if (tipo.equals(Constantes.LONG)||tipo.equals(Constantes.AUTO))	{
				if (valor instanceof Long)
					return valor;
				if (valor instanceof Integer)
					return valor;
				return Long.parseLong(valor.toString(),10);
				}
			if ( tipo.equals(Constantes.INTEGER) ){
				if (valor instanceof Integer)
					return valor;
				return Integer.parseInt(valor.toString(),10);
				}
			if (tipo.equalsIgnoreCase(Constantes.DOUBLE)){
				if (valor instanceof Double)
					return valor;
				if (valor instanceof Float)
					return valor;
				return new Double(valor.toString());
				}
			if (tipo.equalsIgnoreCase(Constantes.CURRENCY)){
				if (valor instanceof Integer)
					return valor;
				if (valor instanceof Long)
					return valor;
				BigDecimal ret ;
				if (valor instanceof BigDecimal)
					ret=(BigDecimal)valor;
				else
					ret = new BigDecimal(valor.toString());
				return ret.setScale(2, BigDecimal.ROUND_HALF_UP);
				}
			if (tipo.equals(Constantes.DATE)){
				if (valor instanceof FechaGotta)
					return valor;
				try 
					{return new FechaGotta(valor.toString());} 
				catch (ErrorFechaIncorrecta e) 
					{return valor;}
				}
			}
		catch(NumberFormatException e){
			return valor;
			}
		return valor;
		}
	public void setValor(Object valor) {
		this.valor=getValor(valor, this.tipo);		
		if (this.esDeSistema)
			this.modificada=false;
		}
	@Override
	public String toString(){
		return "Variable ["+nombre+"]: "+valor+Constantes.ESPACIO+tipo;}
	public JSONObject JSON() throws JSONException{
		return new JSONObject()
			.put("nombre", nombre)
			.put("valor", valor)
			.put("tipo", tipo) 
			.put("modificada", modificada)
			.put("esDeSistema", esDeSistema)
			.put("variablePadre", variablePadre)
			;
		}
	public boolean getModificada(){return this.modificada;}
	public void setModificada(boolean m){
		if (this.esDeSistema)
			this.modificada=false;
		else 
			this.modificada=m;
		}
	public void setTipo(String tipo){
		this.tipo=tipo;
		this.setValor(this.valor);
		}

	public void setVariablePadre(String nombreVar) {
		this.variablePadre=nombreVar;
	}
}

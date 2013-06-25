package es.burke.gotta;

import java.io.Serializable;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

public class Parametro implements Serializable
{
	private static final long serialVersionUID = 6626976709143702099L;
	protected String nombre;
	protected String tipo;
	
	public Parametro(String nombre, String tipo)
		{
		this.nombre = nombre;
		this.tipo = tipo.toLowerCase();		
		}
	public String getNombre()
		{return this.nombre;}
	public String getTipo()
		{return this.tipo;}
	@Override
	public String toString()
		{return nombre + " ("+tipo+")";}
	public JSONObject JSON() throws JSONException{
		JSONObject ret = new JSONObject();
		ret.put("nombre", this.nombre);
		ret.put("tipo", this.tipo);
		return ret;
	}
	public String jdbcEncode(Object v){
//TODO
		if (v==null || (v.toString().equals("") && (this.getTipo().equalsIgnoreCase(Constantes.DATE)||Util.en(this.tipo, Constantes.tiposNumericos)) ) ){
			return "NULL";
		}
		else
			{
			if (this.tipo.equals(Constantes.STRING) || this.tipo.equals(Constantes.MEMO))
				return "'"+v.toString().replace("'", "''")+"'";
			else if (this.tipo.equals(Constantes.INTEGER))
				return v.toString() ;
			else if (this.tipo.equals(Constantes.LONG))
				return v.toString() ;
			else if (this.tipo.equals(Constantes.DOUBLE))
				return v.toString() ;
			else if (this.tipo.equals(Constantes.DATE)){
					//{ts `yyyy-mm-dd hh:mm:ss.f...'}
					FechaGotta fg;
					try {
						fg = new FechaGotta(v.toString());}
					catch (ErrorFechaIncorrecta e) {
						return v.toString();
					}
					//System.out.println(d.toLocaleString());
					String yyyy = Util.formatearNumero(fg.get(Calendar.YEAR), 4) ;
					String mm=Util.formatearNumero(fg.get(Calendar.MONTH), 2) ;
					String dd=Util.formatearNumero(fg.get(Calendar.DAY_OF_MONTH), 2) ;
					String hh=Util.formatearNumero(fg.get(Calendar.HOUR_OF_DAY), 2) ;
					String nn=Util.formatearNumero(fg.get(Calendar.MINUTE), 2) ;
					String ss=Util.formatearNumero(fg.get(Calendar.SECOND), 2) ;
					String mili=Util.formatearNumero(fg.get(Calendar.MILLISECOND), 3) ;
					if (!mili.equals("000"))
						ss+="."+mili;
					return "{ts '"+yyyy+"-"+mm+"-"+dd+" "+hh+":"+nn+":"+ss+"'}";
				}
			else if (this.tipo.equals(Constantes.CURRENCY))// || this.tipo.equals("Decimal"))
				return v.toString() ;
			return v.toString() ;
			}
	}
}

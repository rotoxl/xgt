package es.burke.gotta;

import java.util.ArrayList;

public class SqlParam{
	public ArrayList<Object> params;   // (2005, númExpediente)
	public ArrayList<String> listaInt; // (?, ?)
	public String tempSQL;             // "CD_Ejercicio, CD_Expediente"
	public String SQL;           
	
	public String colIdentidad=null;
	public Tabla tabla=null;
	
	public SqlParam(){
		params = new ArrayList<Object>();
		listaInt=new ArrayList<String>();
		}
	public SqlParam(String sql, ArrayList<Object> params ){
		this.SQL=sql;
		this.params=params;
	}
}
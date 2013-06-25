package es.burke.gotta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class INivelDef {
	protected String _cd; //código de nivel, generalmente una letra
	public String texto;
	protected ArrayList<Parametro> colParametros=new ArrayList<Parametro>(); // parametros implicados en la sentencia sql
	public boolean esDeSistema=false;
	
	//cabeceras en los distintos idiomas
	private HashMap<String, ArrayList<String>> cabeceras=new HashMap<String, ArrayList<String>>();//contiene las cabeceras en los distintos idiomas
	public ArrayList<String> cabecera(String idioma){
		if (cabeceras.containsKey(idioma))
			return (ArrayList<String>) cabeceras.get(idioma).clone();
		return null;
		}
	public void anhadeCabecera(String idioma, ArrayList<String> datos){
		cabeceras.put(idioma, datos);
		}
	
	public String getCd() {
		return this._cd;
		}
	public void setCd(String cd) {
		this._cd=cd;
		}
	
	public List<Parametro> getColParams() {
		return this.colParametros;
		}
	protected void rellenaColParametros(String parametros) {
		this.colParametros.clear();
		Iterator<String> iparams = Util.split(parametros, ",").iterator();
		String param;
		String tipoparam;
		String nombreparam;
		Parametro var;
		while (iparams.hasNext()) {
			param = iparams.next();
			ArrayList<String> v = Util.split(param);
			if (v.size()<2)
				return;
			tipoparam = v.get(0);
			nombreparam = v.get(1);
			var = new Parametro(nombreparam, tipoparam);
			this.colParametros.add( var);
		}
	}
	
	public String getTexto(){
		return texto;
		}
	public void setTexto(String sqlparcial) {
		texto=sqlparcial;
		}

	public abstract INivel obtenerNivel();
}

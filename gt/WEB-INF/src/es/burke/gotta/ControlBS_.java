 package es.burke.gotta;

import java.util.ArrayList;

public class ControlBS_ extends Control  {
	ArrayList<Object> valorClave;//=new ArrayList<Object>();

	boolean bloqueado=false; //para hacerlo editable
	boolean opcional=true;

	Object campoVariable;
	ITablaDef tablaDef;
	String nombre;
	public int colsBloqueadas=0, colsQueBloquea=0;
	String primeraCasilla; // ñapa para ponerle un "label for" al html

	public void inicializa( String nombreControl, ControlDef controlDef, Usuario usuario, Object campoVar, 
			ITablaDef tDef,  
			ArrayList<Object> valor, boolean estaBloqueado, int colsBloqueadas, int colsQueBloquea, boolean esOpcional) {
		this.nombre=nombreControl;
		
		this.def=controlDef;
		this.usr=usuario;
		this.campoVariable=campoVar;
		this.tablaDef=tDef;
		this.valorClave=valor;
		this.bloqueado=estaBloqueado;
		this.opcional=esOpcional;
		this.colsBloqueadas = colsBloqueadas;
		this.colsQueBloquea = colsQueBloquea;
	}

	public String getDescripcion() {//throws ErrorCampoNoExiste, ErrorTablaNoExiste
		try {
			Coleccion<String> t=getBuscador().buscarPorClave(this.valorClave); 
			if (t==null)
				return "";
			return Util.noNulo( t.get(t.size()-1) );} 
		catch (NullPointerException e)
			{return "";}
		catch (ErrorCampoNoExiste e) 
			{return "";}
		catch (ErrorTablaNoExiste e) 
			{return "";} 
		catch (ErrorFechaIncorrecta e) 	
			{return "";} 
		catch (ErrorGotta e) 
			{return "ERROR";} 
		}

	public Integer getCaminoMantenimiento(){
		return this.tablaDef.caminoMantenimiento;
		}

	/* devuelve un objeto buscador, y este será el que realice las búsquedas que hagan falta */
	private Buscador _buscador;
	public Buscador getBuscador() throws ErrorTablaNoExiste, ErrorArrancandoAplicacion { 
		if (_buscador == null)
			_buscador = new Buscador(this.usr, tablaDef.getCd(),0);
		return _buscador;
		}

	protected ArrayList<String> _listaNombresCasillas=null;
	public ArrayList<String> generaListaNombresCasillas() {
		if (_listaNombresCasillas == null) {
			_listaNombresCasillas=new ArrayList<String>();

			CampoDef tCampo;
			if (campoVariable instanceof Variable)
				tCampo=this.tablaDef.getCampoClave();
			else
				tCampo=(CampoDef)campoVariable;

			for (String col : tCampo.getColumnas())
				_listaNombresCasillas.add(this.nombre+col);
			}
		return _listaNombresCasillas;
		}
	
	public String JSON(){
		StringBuffer ret = new StringBuffer();
		ret.append( Util.JSON( this.generaListaNombresCasillas() ) );
		return ret.toString();
		}
	@Override
	public String toString(){
		return "Control BS_["+this.nombre+Constantes.ESPACIO+this.valorClave+"]";
		}
}

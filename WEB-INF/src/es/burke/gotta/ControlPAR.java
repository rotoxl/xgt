package es.burke.gotta;

import java.util.ArrayList;
public class ControlPAR extends Control {
	public String nombreVariable;
	public String tipoVariable;
	public ControlPAR(ControlDef con, Usuario usuario){
		this.def=con;
		this.usr=usuario;

		String nomVar = def.getCaption(this.usr); //"tipoDato nombreVar" o "nombreVar" ("tabla.colDesc nombreVar" ya se trata en bsp)
		String tipo = Constantes.STRING;
		ArrayList<String> v = Util.split(nomVar);
		if (v.size()>1){
			nomVar = v.get(1);
			tipo = v.get(0);
			}	
		
		this.tipoVariable = tipo;
		this.nombreVariable = nomVar;
		}
	
	//este constructor se usa sólo para lo que ordenó Ayda de meter un mantetablas con campos de búsqueda por columna
	public ControlPAR(Usuario usr, String nombreVariable){
		this.usr=usr;
		this.tipoVariable=Constantes.STRING;
		this.nombreVariable=nombreVariable;
		}
	
}

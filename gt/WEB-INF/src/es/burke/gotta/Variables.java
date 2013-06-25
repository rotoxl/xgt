package es.burke.gotta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Variables implements Iterable<String>{
	private Coleccion<Variable> variables=new Coleccion<Variable>(); //colección de variables del usuario
	private Coleccion<Variable> variablesRollback=new Coleccion<Variable>(); //colección de variables del usuario
	
	public void rollback() {
		for(String nv:this.variablesRollback){
			Variable v = this.variablesRollback.get(nv);
			if (v.getValor()==null)
				this.variables.remove(nv);
			else
				this.variables.put(nv, v);
			}
		}
	public void limpiaRollback() {
		this.variablesRollback=new Coleccion<Variable>();
		}
	public void guardaRollback(Variable var) {
		this.variablesRollback.put(var.getNombre(), new Variable(var.getNombre(), var.getTipo(), var.getValor()));
		}
//////////
	public void actualiza(Variables vOrigen) {
		Iterator<String> e = vOrigen.iterator();

		//incluir las variables del explorador
		while (e.hasNext()) {
			String nombreVarMotor = e.next();
			boolean esVarExp=false;
			if (nombreVarMotor.toUpperCase().equals(nombreVarMotor) && !nombreVarMotor.equals("$") && !nombreVarMotor.equals("~"))
				esVarExp=true;
			if (nombreVarMotor.length()>1 && !nombreVarMotor.startsWith("@"))
				esVarExp=true;
			
			Variable varm = vOrigen.get(nombreVarMotor);
			if (esVarExp && varm.getModificada()){
				Variable vare = get(varm.getNombre());
				if (vare==null) {
					//EMC 10/10/2006 ¡¡NO HAY QUE CREARLA!! MSP ¿cómo que no?
					this.initVar(varm.getNombre().toLowerCase(), varm.getTipo(), varm.getValor());
					} 
				else { //actualizarla
					if (varm.getValor()!=null && vare.getValor()==null)
						vare.setValor(varm.getValor());
					else if (varm.getValor()!=null && vare.getValor()!=null && !varm.getValor().equals(vare.getValor())) 
						vare.setValor(varm.getValor());
					}
				}
			}
		}
//////////////////
	public Variable initVar(String cd, String tipo) {
		Variable v=get(cd);
		if (v==null) {
			if (tipo==null)
				tipo=Constantes.STRING;
			put(cd, new Variable(cd, tipo, Constantes.CAD_VACIA));
			}
		return get(cd);
		}
	public Variable initVar(String cd, String tipo, Object valor) {
		Variable v=initVar(cd, tipo);
		v.setValor(valor);
		return v;
		}
////////////////////	
	public Variable put(String clave, Variable o){
		return this.variables.put(clave, o);
		}
	public Variable get(String clave){
		return this.variables.get(clave);
		}
	public boolean containsKey(String k){
		return this.variables.containsKey(k);
		}
	
	public Iterator<String> elements(){		
		return variables.iterator();
		}
	public Iterator<String> iterator() {
		return variables.iterator();
		}
	public int size(){
		return variables.size();
		}
	public ArrayList<String> getOrden() {
		return this.variables.getOrden();
		}
	public Collection<Variable> values(){
		return this.variables.values();
		}
	
}

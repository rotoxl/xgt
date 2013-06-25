package es.burke.gotta;

import java.util.ArrayList;

public class ControlDESP extends Control{
		public String nombreVariable;
		public String tipoVariable=Constantes.STRING;
	
		public ControlDESP(ControlDef def, Usuario usuario)  {
			super();
			this.def=def;
			ArrayList<String > a=Util.split(def.getCaption(usuario)); 
			nombreVariable = a.get(0);
			//si la variable no existe la creo			
			if (usuario.getVariable(nombreVariable)==null)
				usuario.setVariable(nombreVariable,new Variable(nombreVariable, Constantes.STRING, null));
		}

}

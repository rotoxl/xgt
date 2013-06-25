package es.burke.gotta;

import java.util.ArrayList;

public class ControlBSP extends ControlBS_ 
{
	public String nombreVariable;
	
	@SuppressWarnings("unchecked")
	public ControlBSP(ControlDef def, Usuario usuario) throws ErrorTablaNoExiste, ErrorCampoNoExiste {
		super();
		
		String caption=def.getCaption(usuario);
		
		ArrayList<String > a=Util.split(caption); 
		nombreVariable = a.get(a.size()-1).toString();
		//si la variable no existe la creo			
		
		ArrayList<String> v=Util.split(caption); //TODO liquidar esto dentro de un par de meses
		String tabla=v.get(0).toString();//tabla.coldes
		v = Util.split(tabla, ".");
		ITablaDef _tablaDef= def.getApli().getEsquema().getTablaDef(v.get(0));
		Variable variable = usuario.getVariable(nombreVariable);
		if (variable==null){
			CampoDef campoClave = _tablaDef.getCampoClave();
			variable=new Variable(nombreVariable, campoClave.getColumna(campoClave.getColumnas().size()-1).getTipo(), null);
			usuario.setVariable(nombreVariable,variable);
			}
		Object tValor=variable.getValor();
		ArrayList<Object> _valorClave;
		if (tValor instanceof ArrayList)
			_valorClave=(ArrayList<Object>)tValor;
		else {
			_valorClave =new ArrayList<Object>();
			_valorClave.add(tValor);
			}
		this.inicializa("control"+def.numControl, def, usuario, variable, _tablaDef, _valorClave, false, 0, 0, true);
	}
	@Override
	public ArrayList<String> generaListaNombresCasillas() {
		if (_listaNombresCasillas == null) {
			_listaNombresCasillas=new ArrayList<String>();
			String prefijo=( (Variable)campoVariable  ).getNombre() ;
			_listaNombresCasillas.add(prefijo);
			}
	
		return _listaNombresCasillas;
		}

}

package es.burke.gotta;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AccionX_X extends Accion {
	public AccionX_X(Tramite tram){
		super(tram);}
	
	@Override
	public void verificar(Fila fila) throws ErrorEvaluador, ErrorAccionX_X, ErrorCampoNoExiste, ErrorTablaNoExiste, ErrorCreandoTabla {
		if (p1!=null) {
			if (! this.verificaExpresion(p1) ) 
				throw new ErrorEvaluador("Expresion no valida X>X: (param1)"+p1);
			}
		
		if (! motor.esCampo_Variable(p2)) 
				throw new ErrorAccionX_X("Expresion no valida X>X: (param2)"+p2);
		}
	
	@Override	
	public Motor.Resultado ejecutar() throws ErrorGotta {
		_AccionX_X_(this, Constantes.X_X, p1, p2, p3, p4);
		return Motor.Resultado.OK;
		}
	
	@SuppressWarnings("unchecked")
	public static void _AccionX_X_(Accion accion, String strAccion, String p1, String p2, String p3, String p4) throws ErrorGotta {
		Object valor;
		String _tipo=null;
		
		Motor motor=accion.motor;
		if (p1==null)
			valor=null;
		else {			
			//primera parte: expresión
			valor=accion.evaluaExpresion(p1);
			
			if (p3!=null && !p3.equals(Constantes.CAD_VACIA)) {
				if (valor==null || valor.equals(Constantes.CAD_VACIA))
					valor=null;
				
				else try {
					if (p3.equalsIgnoreCase("Date")) {
						if (FechaGotta.esFecha(valor.toString()))
							valor=new FechaGotta(valor.toString());
						else
							throw new ErrorAccionX_X("Error ejecutando la línea "+accion.CD_Operacion+" al convertir " + valor.toString() + " a fecha");
						}
					else if (p3.equalsIgnoreCase(Constantes.INTEGER)) {
						if (valor.toString().equals("true"))
							valor="1";
						else if (valor.toString().equals("false"))
							valor="0";
						else
							valor=new Integer(valor.toString());
						}
					else if (p3.equalsIgnoreCase(Constantes.LONG)||p3.equalsIgnoreCase("Auto")) {
						if (!(valor instanceof FechaGotta) && FechaGotta.esFecha(valor.toString()))
							{valor=new FechaGotta(valor.toString());}
						if (valor instanceof FechaGotta)
							valor=((FechaGotta)valor).fechaCrystal();
						else
							valor=new Long(valor.toString());
						}
					else if (Util.esTipoNumerico(p3))
						valor=new BigDecimal(valor.toString());
					else if (p3.equalsIgnoreCase(Constantes.STRING))
						valor=valor.toString();
					_tipo=p3;
					}
				catch (Exception e)
					{throw new ErrorAccionX_X("Error ejecutando la línea "+ accion.CD_Operacion +". "+e.getMessage());}
				}
			else if (motor.esCampo(p1)) {
				_tipo=motor.tablaCampo(p1).getColumna(0).getTipo();
				/*sacamos el valor correcto, si es nulo pues nulo*/
				//valor= motor.getValorSimbolo(p1).get(0);
				}
			else if (motor.esNombreVariable(p1)) {
				_tipo=accion.lote.variables.get(p1).getTipo();
				//valor=accion.lote.variables.get(p1).getValor();
				}
			}
		//segunda parte: Nombre variable o campo
		if (motor.esNombreVariable(p2)) {
			if (p1 != null && p2.equals("@")) 
				motor.hayQueRefrescar = true;
			if (p2.equalsIgnoreCase("@login")){
				if(valor==null)valor="";
				motor.usuario.reset();
				
				motor.usuario.setLogin(valor.toString());
				motor.usuario.leerDatosUsuario(motor.usuario.getApli());
				if(motor.usuario.arbol.nodoActivo!=null)
					motor.usuario.nodoArranque=URL_Gotta.crea(motor.usuario.arbol.getNodoActivo().generaURL(motor.usuario));
				}
			motor.lote.setVariable(p2, _tipo, valor);
			}
		else {
			if (! (valor instanceof ArrayList)) {
				ArrayList<Object> v = new ArrayList<Object>();
				v.add(valor);
				valor=v;
				}
			CampoDef cam = motor.tablaCampo(p2);
			ITabla t = motor.ponTabla(motor.sacaTablaDef(cam.getTabla()));
			t.setValorCam(cam.getCd(), (ArrayList<Object>) valor);
			}
		}
}

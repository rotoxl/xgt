package es.burke.gotta;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Filtrar  extends HttpServlet {
	private static final long serialVersionUID = -7748785128795574321L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		Jsp.setHeaders(response); 
		request.setCharacterEncoding(Constantes.CODIF);
		
		try {
			filtrar(request);
			} 
		catch (ErrorGotta e) {
			//pass
			}
		}
	public static void filtrar(HttpServletRequest request) throws ErrorGotta {
		Usuario usr = Usuario.desdeSesion(request, true);
		usr.variables.limpiaRollback();
		
		// actualiza variables de filtros
		java.util.Enumeration<String> nombres=request.getParameterNames();
		while (nombres.hasMoreElements())  {
			String numControl=nombres.nextElement();
			String nombreVar=null, tipoVar=null, valor=null;
			String tc=Constantes.CAD_VACIA;
			
			valor=Util.obtenValor(request, numControl);	
			if ( numControl.startsWith ("control")  ) {
				Control ctl = usr.getApli().getControl(usr, numControl);
				tc=ctl.def.tc;
				if (tc.equals(Constantes.PAR)) {
					ControlPAR par= (ControlPAR)ctl;
					nombreVar=par.nombreVariable;
					tipoVar=par.tipoVariable;
					}
				else if (tc.equals(Constantes.BSP)){
					ControlBSP bsp=(ControlBSP)ctl;
					nombreVar=bsp.nombreVariable;
					CampoDef campoClave = bsp.tablaDef.getCampoClave();
					tipoVar=campoClave.getColumna(campoClave.getColumnas().size()-1).getTipo();
					}
				else if (tc.equals(Constantes.BSM)){
					ControlBSM bsp=(ControlBSM)ctl;
					nombreVar=bsp.nombreVariable;
					tipoVar=Constantes.STRING;
					}
				else if (tc.equals(Constantes.DESP)){
					ControlDESP desp=(ControlDESP)ctl;
					nombreVar=desp.nombreVariable;
					tipoVar=desp.tipoVariable;
					}
				}
			else if (!Util.en(numControl, "aplicacion") ) {//bsp's creados al vuelo
				nombreVar=numControl; 
				tipoVar=Constantes.STRING;
				}

			if (nombreVar!=null) {
				Variable var=usr.getVariable(nombreVar);
				if (var == null) {
					var=new Variable(nombreVar, tipoVar, null);
					usr.variables.guardaRollback(var);
					usr.setVariable(nombreVar, var);
					}
				else
					usr.variables.guardaRollback(var);
				
				if  (valor==null || valor.equals(Constantes.CAD_VACIA))
					var.setValor(null);
				else  {
					if (tc.equals(Constantes.BSM)){ //formateamos pero por trozos
						ArrayList<String> trozos=Util.split(valor, Constantes.COMA);
						for (int i=0;i<trozos.size(); i++){
							trozos.set(i, Util.desformatearNumero(trozos.get(i)));
							}
						var.setValor(Util.join(Constantes.COMA, trozos));
						}
					else if (Util.esTipoNumerico( tipoVar ) )
						var.setValor( Util.desformatearNumero(valor) );
					else
						var.setValor(valor);					
					}
				}
			}
		}
}

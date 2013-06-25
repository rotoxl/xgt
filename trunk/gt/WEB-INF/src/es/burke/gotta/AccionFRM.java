package es.burke.gotta;

import java.util.ArrayList;

import es.burke.gotta.Tramite.TipoEjecucion;

public class AccionFRM extends Accion {
	public String mensaje;
	
	String tipo=null; //Principio o fin
	String modoDetalle=null; //para los formularios diseñados, para basar el formulario en este detalle
	String numControl=null;			 //para editar dentro de una lista
	String url=null;
	
	public AccionFRM(Tramite tram)
	{super(tram);}
	
	@Override
	public void verificar(Fila fila) throws ErrorArrancandoAplicacion, ErrorGotta{
		p1=(p1+"").toLowerCase();
		if (p1.equals("principio") || p1.equals("fin"))
			tipo=p1;

		else if (this.motor.usuario.getControl(this.tramActivo.tramiteInicial().nodoActivo, p1)!=null){
			numControl=p1;
			tipo="principio";
			}
		else {//03/11/2008 si viene algo distinto, se trata del nombre de un detalle //04/12/2008 si existe
			  //19/10/2009 admitimos sintáxis de navegación, para poder indicar cambios de pestaña, etc: detalle=nombreDetalle&control12=2
			analizaP1();
			}		
		if(tipo==null)
			throw new ErrorAccionFRM("Parametro ''"+p1+"'' no valido en FRM. Se admiten Principio, Fin o un detalle existente.");
		if (motor.esCampo_Variable(p2))
			this.verificaExpresion(p2);

		if (p4!=null && !p4.equals(Constantes.CAD_VACIA) && !motor.esCampo_Variable(p4)){
			String listaEtiquetas=p4; //etiquetaVolver1/literal1 | etiquetaVolver2/literal2|...
			ArrayList<String> temp=Util.split(listaEtiquetas, Constantes.PIPE);
			for (int i=0; i<temp.size(); i++){				
				try {
					String lbl=sacaIndice(temp.get(i),Constantes.SEPARADOR, 0);
					if (!(lbl.equals(Constantes.CAD_VACIA)))
						tramActivo.verificaEtiqueta(lbl, false);
					} 
				catch (ErrorTramiteActivoHaCambiado e) {
					//pass
					}
				}
			}
		}
	private void analizaP1() {
		String tempp1;
		if (!p1.equalsIgnoreCase("principio") && !p1.equalsIgnoreCase("fin")){
			try {
				tempp1=this.motor.getEvaluador().evalua(p1).toString();
				} 
			catch (ErrorEvaluador e){
				tipo="principio";
				this.url=p1;
				this.modoDetalle=p1;
				return;
				}
		
			if (tempp1.equals(Constantes.CAD_VACIA)){
				tipo="principio";
				}
			else if (tempp1.contains("=")){
				ArrayList<String> temp=Util.split(tempp1, "&");
				for (int i=0; i<temp.size(); i++){
					String trozo=temp.get(i);
					if (trozo.toLowerCase().contains("detalle=")){
						this.modoDetalle=trozo.substring(8, trozo.length());
						this.url=tempp1;
						tipo="principio";
						return;
						}
					}
				}
			}
		}		
	private String sacaIndice(String cadena, String sep, int i){
		ArrayList<String> temp=Util.split(cadena, sep);
		return temp.get(i);
		}
	
	@Override
	public Motor.Resultado ejecutar() throws ErrorEvaluador, ErrorCampoNoExiste, ErrorTablaNoExiste, ErrorAccionFRM, ErrorNumeroIncorrecto, ErrorLBLNoExiste, ErrorArrancandoAplicacion, ErrorTramiteActivoHaCambiado {
		String msg = Constantes.CAD_VACIA;
		String param4 = this.p4;
		if (tramActivo.tipoEjecucion == TipoEjecucion.Revivir) {
			param4=Constantes.CAD_VACIA;
			}
		analizaP1();
			
		//Evaluación del título
		if (p2== null)
			msg=Constantes.CAD_VACIA;
		else if (this.motor.esNombreVariable(p2))
			msg=this.motor.getEvaluador().evalua(p2).toString();
		else 
			msg = p2;
		
		if (p4!=null && !p4.equals(Constantes.CAD_VACIA)){
			String listaEtiquetas=p4;
			try {
				p4=this.evaluaExpresion(p4); //etiquetaVolver1\literal1 | etiquetaVolver2|literal2 |...
				}
			catch (ErrorEvaluador e){
				//pass
				}
			ArrayList<String> temp=Util.split(listaEtiquetas, Constantes.PIPE);
			for (int i=0; i<temp.size(); i++)
				tramActivo.verificaEtiqueta( sacaIndice(temp.get(i),Constantes.SEPARADOR, 0), false); 	
			}

		return this.motor.accionFRM(tipo, modoDetalle, this.url, numControl, msg, param4);
	}
}

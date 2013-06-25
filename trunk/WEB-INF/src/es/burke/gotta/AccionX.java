package es.burke.gotta;

import es.burke.gotta.Tramite.TipoEjecucion;

public class AccionX extends Accion {
	public boolean opcional=false; 
	
	public AccionX (Tramite tram)
	{super(tram);}
	
	@Override
	public void verificar(Fila fila) throws ErrorCampoNoExiste, ErrorTablaNoExiste, ErrorAccionX, ErrorArrancandoAplicacion, ErrorCreandoTabla {
		if (p1!=null) {
			if (this.accion.equals(Constantes.Xno) && ! this.verificaExpresion(p1) ) 
				throw new ErrorAccionX("Expresion no valida o campo desconocido en acción X!: (param1)"+p1);
			else if (this.accion.equals(Constantes.Xsi) && !motor.esCampo_Variable(p1))
				throw new ErrorAccionX("Expresion no valida X!?: (param1)"+p1);
			
			//	tabla.Campo 1 0
			//	String*100
			//	Integer.Positivo
			if ( p4!=null){
					if ( p4.contains(Constantes.PUNTO) ) {
						String primerTrozo = Util.split(p4.toLowerCase(), Constantes.PUNTO).get(0);
						if (Util.en(primerTrozo,Constantes.tiposGotta)) { //Tipo.Subtipo (String.CorreoElectronico o Integer.Positivo)
							//assert Constantes.Subtipos.
							}
					}
				}
			}
	}
	
	@Override
	public Motor.Resultado ejecutar() throws ErrorGotta {
		String tipoAccion;
		String param3 = this.p3;
		if (tramActivo.tipoEjecucion == TipoEjecucion.Revivir)
			tipoAccion=Constantes.Xno;
		else
			tipoAccion=accion;
		
		opcional=(param3!=null);
        String xp4;
        try {
        	xp4=this.evaluaExpresion(this.p4);
        	} 
        catch (ErrorEvaluador e) {
            xp4=this.p4;
            }
        return this.motor.presentarPedirCampo(tipoAccion, this.p1, this.p2, this.p3, xp4);
		}

}

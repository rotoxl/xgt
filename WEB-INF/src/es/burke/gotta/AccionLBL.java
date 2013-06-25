package es.burke.gotta;

import es.burke.gotta.Tramite.TipoEjecucion;

public class AccionLBL extends Accion{
	public AccionLBL(Tramite tram) {
		super(tram);}
	@Override
	public void verificar(Fila fila){
		//pass 
		}
	@Override
	public Motor.Resultado ejecutar(){
		if ( p1 != null  && p1.equalsIgnoreCase("DOC") 
				&& (tramActivo.tipoEjecucion.equals(TipoEjecucion.Ejecucion)
						||
						tramActivo.tipoEjecucion.equals(TipoEjecucion.Revivir)
				)){
			this.motor.etiquetaDOC=true;
		}
		return Motor.Resultado.OK;
	}
}

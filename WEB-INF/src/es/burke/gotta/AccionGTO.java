package es.burke.gotta;

public class AccionGTO extends Accion {
	
	public AccionGTO(Tramite tram)
	{super(tram);}
	
	@Override
	public void verificar(Fila fila) throws ErrorLBLNoExiste, ErrorTramiteActivoHaCambiado {
		//un GTO en un SUB que salte a una etiqueta del propio SUB no se verificaría bien.
		tramActivo.verificaEtiqueta(p1, false); 
		}
	
	@Override
	public Motor.Resultado ejecutar() throws ErrorLBLNoExiste, ErrorTramiteActivoHaCambiado{
		tramActivo.verificaEtiqueta(p1, true);
		return Motor.Resultado.OK;
		}
}

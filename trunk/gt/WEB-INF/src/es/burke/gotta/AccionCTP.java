package es.burke.gotta;

public class AccionCTP extends Accion {
	String filtro;
	
	public AccionCTP(Tramite tram)
	{
		super(tram);
		tramActivo=tram.tramiteInicial();
	}
	@Override
	public void montaTodo(Fila fila) throws ErrorGotta {
		super.montaTodo(fila);
		filtro=p1;
		}
	@Override
	public void verificar(Fila fila) throws ErrorAccionCTP {
		if (tramActivo.getTt()==null) 
			throw new ErrorAccionCTP("No es posible cancelar tramites pendientes. No hay tabla de tramitación en el camino "+motor.tramActivo().camino);
		tramActivo.setCargarTodosLosTramites(true);
		}

	@Override
	public Motor.Resultado ejecutar()	{
		ITabla tt=tramActivo.getTt();
				
		if (filtro==null)
			filtro="*";
		
		int volver = tt.getRegistroActual();
		int encontrado = tt.findFirst("F_Ejecucion", null);
		while (encontrado != -1) {
			tt.setRegistroActual(encontrado);
			if (tt.getRegistroActual() != volver) {
				if (tt.getValorCol(Constantes.CD_Tramite).toString().equalsIgnoreCase(filtro) || filtro.equals("*")) 
					tt.delete();
				}
			encontrado = tt.findNext("F_Ejecucion", null);
			}
		tt.setRegistroActual(volver);
		return Motor.Resultado.OK;
		}
}

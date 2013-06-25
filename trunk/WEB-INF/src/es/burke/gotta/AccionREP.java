package es.burke.gotta;


import java.util.ArrayList;

public class AccionREP extends Accion {
	@SuppressWarnings("unused")
	private CampoDef campoEnlace;
	private ArrayList<CampoDef> camposREP=new ArrayList<CampoDef>();
	
	public AccionREP(Tramite tram)
	{super(tram);}
	
	@Override
	public void verificar(Fila fila) throws ErrorCampoNoExiste
	{
		//p1 = campo de enlace
		campoEnlace=tramActivo.getTt().tdef.getCampo(p1);
		
		//p2 = lista de campos a replicar, separados por espacios
		ArrayList<String> v=Util.split(p2);
		for (int i=0;i<v.size() ;i++ ) 
			camposREP.add(tramActivo.getTt().tdef.getCampo(v.get(i)));
		
		tramActivo.setCargarTodosLosTramites(true);

	}
	
	@Override
	public Motor.Resultado ejecutar() throws ErrorNoImplementado
		{
		throw new ErrorNoImplementado("Acción REP no implementada");
//		return Motor.Resultado.OK;
		}
}

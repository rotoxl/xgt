package es.burke.gotta;


public class AccionJPR extends Accion {
	private String tramitesABuscar;
	public AccionJPR(Tramite tram)
	{super(tram);}
	
	@Override
	public Motor.Resultado ejecutar() throws ErrorLBLNoExiste, ErrorTramiteActivoHaCambiado
		{return ejecutar("JPR");}
	@Override
	public void verificar(Fila fila) throws ErrorDiccionario, ErrorLBLNoExiste, ErrorTramiteNoExiste, ErrorTramiteActivoHaCambiado {
		for (String tramo:Util.split(p1,Constantes.SEPARADOR))  {
			if (!GestorMetaDatos.existeTramite(motor.usuario.getConexion(), motor.getEsquema(), tramo)) 
				throw new ErrorTramiteNoExiste("No existe el trámite "+tramo);
			}
		//lo mismo que GTO, en un SUB esto no se verifica bien.
		tramActivo.verificaEtiqueta(p2, false); 
		tramitesABuscar = p1;
		tramActivo.setCargarTodosLosTramites(true);
	}
	protected Motor.Resultado ejecutar(String xAccion) throws ErrorLBLNoExiste, ErrorTramiteActivoHaCambiado {
		boolean hayTramitesEjecutados = false;
		boolean hayTramitesPendientes = false;
		int volver=tramActivo.getTt().getRegistroActual();
		
		tramActivo.getTt().setRegistroActual(0);
//		 ArrayList<String> v = Util.split(tramitesABuscar,Constantes.SEPARADOR);
//		for (int i=0;i<v.size();i++){
			String tramiteABuscar=tramitesABuscar;//v.get(i);
			if (tramActivo.getTt().findFirst(Constantes.CD_Tramite, tramiteABuscar)!=-1 &&
					tramActivo.getTt().getRegistroActual()!=volver){
					do{
						if (tramActivo.getTt().getValorCol("F_Ejecucion")!=null) 
							hayTramitesEjecutados=true;
						else 
							hayTramitesPendientes=true;
					} while (tramActivo.getTt().findNext(Constantes.CD_Tramite, tramiteABuscar)!=-1);
				}
			
//			if (xAccion.equals("JPR") && hayTramitesPendientes)
//				break; //ya hay un trámite que no cumple, salgo del for
//			else if (xAccion.equals("JPP") && hayTramitesEjecutados)
//				break;	
//			}
		
		if (xAccion.equals("JPR") && !hayTramitesPendientes)
			tramActivo.verificaEtiqueta(p2, true);
		else if (xAccion.equals("JPP") && !hayTramitesEjecutados)
			tramActivo.verificaEtiqueta(p2, true);
		
		tramActivo.getTt().setRegistroActual(volver); //dejo el puntero donde estaba			
		return Motor.Resultado.OK;
		}
}

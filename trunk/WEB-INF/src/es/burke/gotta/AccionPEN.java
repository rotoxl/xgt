package es.burke.gotta;


import java.util.ArrayList;

public class AccionPEN extends Accion  {
	private String tramPen; 		//p1
	private String fVencimiento; 	//p2
	private boolean critico;		//p3
	private Long caminoPen; 			//p4
	private ArrayList<String> listaTramites = new ArrayList<String>();//p4: viene una lista de trámites separados por / que 
									//		deben estar ejecutados antes de dejar este
									//		pendiente
	public AccionPEN(Tramite tram)
	{super(tram);}
	
	@Override
	public void verificar(Fila fila) throws ErrorDiccionario, ErrorTramiteNoExiste, ErrorAccionPEN, ErrorEvaluador, NumberFormatException {
		//EMC si estamos en un SUB/FOR hay que utilizar la tabla de trámites del trámite PADRE
		Tramite trOriginal=tramActivo.tramiteInicial();
		
		if(this.motor.esNombreVariable(p1))
			return;
		else if(!GestorMetaDatos.existeTramite(motor.usuario.getConexion(), motor.getEsquema(), p1))
			throw new ErrorTramiteNoExiste("El tramite "+p1+" no esta implementado");
		else if (trOriginal.getTt() == null) 
			throw new ErrorAccionPEN("El camino "+ trOriginal.camino+" no tiene tabla de tramitación, por tanto no se puede ejecutar una acción PEN");
		tramPen=p1;
		
		// camino, lista de trámites ejecutados
		if (p4 == null)
			this.caminoPen=trOriginal.camino;
		else {
			if (Util.isNumeric(p4))
				this.caminoPen=Long.parseLong(p4);
			else {
				this.caminoPen=trOriginal.camino;
				if (this.p4!=Constantes.CAD_VACIA)
					trOriginal.setCargarTodosLosTramites(true);

				this.listaTramites=Util.split(p4, Constantes.SEPARADOR);
				for (String tram:this.listaTramites)  {
					if (!GestorMetaDatos.existeTramite(motor.usuario.getConexion(), motor.getEsquema(), tram)) 
						throw new ErrorTramiteNoExiste("El tramite "+tram+", indicado en el p4 de la acción PEN no esta implementado.");  //$NON-NLS-1$//$NON-NLS-2$
					}
				}
			}
		
		//Fecha de vencimiento
		if (p2 != null) {
			if (! this.verificaExpresion(p2) ) 
				throw new ErrorAccionPEN("Error en p2: "+p2);				
			fVencimiento=p2;
			}
		
		//Crítico
		critico = (p3 != null); 	
	}
	
	@Override
	public Motor.Resultado ejecutar() throws ErrorDiccionario, ErrorEvaluador, ErrorTramiteNoExiste, ErrorTiposNoCoinciden, ErrorFechaIncorrecta, ErrorArrancandoAplicacion {	
		boolean cumple=true;
		Tramite trOriginal=tramActivo.tramiteInicial();
		ITabla tt=trOriginal.getTt();
		
		//Comprobamos que están ejecutados todos los trámites del p4
		int volver=tt.getRegistroActual();
		for (String tram:this.listaTramites)  {
				if ( tt.findFirst(Constantes.CD_Tramite, tram)!=-1) {
					do {
						if (tt.tdef.getColumna("F_Ejecucion")==null && tt.getRegistroActual()!=volver) 
							cumple=false;
						}
					while ( tt.findNext(Constantes.CD_Tramite,tram)!=-1 && cumple);
					
					tt.setRegistroActual(volver);
					break;
					}
				cumple=false;
				break;
			}
		tt.setRegistroActual(volver);
		
		String tramite;
		if (cumple)  {
			if (this.motor.esNombreVariable(tramPen)) {
					tramite=this.evaluaExpresion(tramPen);
					if (!GestorMetaDatos.existeTramite(motor.usuario.getConexion(), motor.getEsquema(), tramite))
						throw new ErrorTramiteNoExiste("El tramite "+tramite+" no esta implementado");
				}
			else
				tramite=tramPen;
			
			if (fVencimiento==null) 
				trOriginal.dejarPendiente(tramite, caminoPen, null, false);
			else  {
				fVencimiento=this.evaluaExpresion(p2).toString();
				FechaGotta fVenci;
				if(fVencimiento.equals(Constantes.CAD_VACIA))
					fVenci=null;
				else if (!FechaGotta.esFecha(fVencimiento))
					throw new ErrorFechaIncorrecta("Error acción PEN: se esperaba una fecha en p2");
				fVenci=new FechaGotta(fVencimiento);
				
				trOriginal.dejarPendiente(tramite, caminoPen, fVenci, critico);
				}
			}
		return Motor.Resultado.OK;
	}
}

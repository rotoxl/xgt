package es.burke.gotta;

import java.util.ArrayList;

public class AccionEJE extends Accion {
	public String mensaje;
	
	private Long camino; //por defecto, camino actual
	private String tramite; //puede ser campo o variable
	
	private String objetoBase; //  CD_CajeroPagador & '\' & CD_Presupuesto
	private String parametros; //  a2005/bARQUI 
	
	public AccionEJE(Tramite tram)
	{super(tram);}
	@Override
	public void montaTodo(Fila fila) throws ErrorGotta {
		super.montaTodo(fila);
		if (p1==null)
			camino=tramActivo.camino;
		else {
			try {
				camino=Long.parseLong(p1.toString());
				} 
			catch (NumberFormatException e) {
				throw new ErrorAccionEJE("El p1 debe ser el camino: "+e.getMessage());
				}
			}
		tramite=p2;
		
		objetoBase=p3;
		parametros=p4;	
	}
	
	@Override
	public void verificar(Fila fila) throws ErrorDiccionario, ErrorTramiteNoExiste, ErrorEvaluador, ErrorAccionEJE, NumberFormatException, ErrorCreandoTabla, ErrorConexionPerdida, ErrorArrancandoAplicacion
	{
		if (p1==null)
			{
			//pass
						}
		else if (!Util.isNumeric(p1))
			throw new ErrorAccionEJE("El parametro1 "+p1+" no es un camino");   //$NON-NLS-2$
		
		if (motor.esCampo_Variable(p2))
			{
//			no se puede verificar
			}  
		else
			{if (! GestorMetaDatos.existeTramite(motor.usuario.getConexion(), motor.getEsquema(), p2))
				throw new ErrorTramiteNoExiste("El tramite "+p2+" no esta implementado"); //$NON-NLS-2$
			}
		
		if (p4!=null) 
			this.verificaExpresion(p4);		
				
	}
	@Override
	public Motor.Resultado ejecutar() throws ErrorDiccionario, ErrorTiposNoCoinciden, ErrorFechaIncorrecta, ErrorNumeroIncorrecto, ErrorGotta {
		Long caminoEJE=camino;
		
		String tramiteEJE;
		if (motor.esCampo_Variable(tramite))
			tramiteEJE= this.evaluaExpresion(tramite);
		else
			tramiteEJE=tramite;
		
		//claveObjetoBase y parámetros del EJE
		ArrayList<Object> claveEJE;
		if (tramActivo.tramiteInicial().getTb()!=null && objetoBase==null){
			claveEJE=tramActivo.getTb().getValorCam(tramActivo.getTb().tdef.getCampoClave().getCd());
			}
		else //if (motor.esCampo_Variable(this.objetoBase))
			claveEJE=Util.splitObjetos(this.evaluaExpresion(objetoBase),Constantes.SEPARADOR);

		
		String paramEJE;
		if (parametros == null)
			paramEJE = parametros;
		else
			paramEJE = this.evaluaExpresion(parametros);

		/*Tramite tramEJE= */ new Tramite(tramActivo.lote, caminoEJE, tramiteEJE, claveEJE, paramEJE, tramActivo);
		return Motor.Resultado.OK;
	}
}

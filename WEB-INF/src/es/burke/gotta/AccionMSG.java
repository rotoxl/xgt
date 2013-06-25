package es.burke.gotta;

public class AccionMSG extends Accion{
	private String mensaje;
	private String retorno;
	private String tipoMensaje; 
//	private TipoIcono icono;
//	
//	public enum TipoIcono {Informacion, Exclamacion, Critico}
	
	public AccionMSG(Tramite tram)
		{super(tram);}
	@Override
	public void verificar(Fila fila) throws ErrorAccionMSG {
		mensaje=this.p1;
		Msg.verificaBotones(p2);
		//	retorno=p3;	
		}
	
	@Override
	public Motor.Resultado ejecutar() throws ErrorCampoNoExiste, ErrorTablaNoExiste, ErrorAccionMSG {
		String msg = "";
		
		try {	
			if ( this.verificaExpresion(this.mensaje) )
				msg=this.evaluaExpresion(this.mensaje).toString();
			else 
				msg=this.mensaje;
			}
		catch (ErrorEvaluador e) {
			msg=this.mensaje;
			}
		tipoMensaje=this.p2;
		retorno=p3;
		
		//param2:botones a mostrar: Aceptar, Aceptar-Cancelar, Si-no_Cancelar, etc.
		this.motor.presentarMensaje(msg, tipoMensaje, retorno);
		return Motor.Resultado.MSG;
	}
}

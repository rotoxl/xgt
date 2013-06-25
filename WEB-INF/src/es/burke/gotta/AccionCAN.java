package es.burke.gotta;
public class AccionCAN extends Accion {
	public String mensaje;
	public AccionCAN(Tramite tram){
		super(tram);}
	@Override
	public void verificar(Fila fila){
		//pass
		}
	@Override
	public Motor.Resultado ejecutar() throws ErrorUsuarioNoValido {
		if (this.p1!=null && !this.p1.equals(Constantes.CAD_VACIA)){
			try {
				mensaje=this.evaluaExpresion(this.p1);
				} 
			catch (ErrorEvaluador e) {
				mensaje=this.p1;
				}
			}
		else 
			mensaje="Tramite cancelado";
		
		String cd_tramite=this.tramActivo.tramite;
		if (cd_tramite.equalsIgnoreCase("gtLogin") || cd_tramite.equalsIgnoreCase("autoexec")){
			throw new ErrorUsuarioNoValido(mensaje);
			}
		
		return Motor.Resultado.SALIR;
		}
}

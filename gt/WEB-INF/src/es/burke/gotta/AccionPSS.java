package es.burke.gotta;

public class AccionPSS extends Accion {
	String tramPen;
	
	public AccionPSS(Tramite tram) {
		super(tram);
		}
	@Override
	public void verificar(Fila fila) throws ErrorDiccionario, ErrorTramiteNoExiste {
		tramPen=p1;
		if (!GestorMetaDatos.existeTramite(motor.usuario.getConexion(), motor.getEsquema(), p1)) 
			throw new ErrorTramiteNoExiste("El tramite "+p1+" no esta implementado");
		}
	@Override
	public Motor.Resultado ejecutar() throws ErrorNoImplementado{
		throw new ErrorNoImplementado("Acción PSS no implementada");
		}
}

package es.burke.gotta;

public class AccionJPP extends AccionJPR {
	public AccionJPP(Tramite tram)
		{super(tram);}
	@Override
	public Motor.Resultado ejecutar() throws ErrorLBLNoExiste, ErrorTramiteActivoHaCambiado
		{return this.ejecutar("JPP");}
}

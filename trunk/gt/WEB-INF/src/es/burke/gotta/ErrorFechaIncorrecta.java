package es.burke.gotta;

public class ErrorFechaIncorrecta extends RuntimeException{
	private static final long serialVersionUID = 3942478070809419506L;
	public ErrorFechaIncorrecta(String msg)
		{super(msg);}
}
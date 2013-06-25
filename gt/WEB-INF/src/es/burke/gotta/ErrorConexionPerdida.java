package es.burke.gotta;

public class ErrorConexionPerdida extends RuntimeException
{
	private static final long serialVersionUID = 3812955126793358810L;
	public ErrorConexionPerdida(String msg)
		{super(msg);}
	public ErrorConexionPerdida(String msg, Exception e) {
		super(msg,e);
	}
}
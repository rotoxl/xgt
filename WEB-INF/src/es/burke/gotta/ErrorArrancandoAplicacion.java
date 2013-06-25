package es.burke.gotta;

public class ErrorArrancandoAplicacion extends RuntimeException{
	private static final long serialVersionUID = -155756323406810347L;
	public ErrorArrancandoAplicacion(String msg)
		{super(msg);}
	public ErrorArrancandoAplicacion(String msg, Exception e) {
		super(msg,e);
		}
	}

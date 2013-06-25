package es.burke.gotta.dll;


import es.burke.gotta.ErrorGotta;

public class ErrorGenerandoProducto extends ErrorGotta
{
	private static final long serialVersionUID = 7905551330004394423L;
	public ErrorGenerandoProducto(String msg)
	{super(msg);}
	public ErrorGenerandoProducto(Throwable e)
	{super(e);}
}

package es.burke.gotta.dll;

import es.burke.gotta.ErrorGotta;

public class ErrorFirmaNoValida extends ErrorGotta{
	private static final long serialVersionUID = -8195410959937481038L;
	public ErrorFirmaNoValida(String msg)
		{super(msg);}
	public ErrorFirmaNoValida(Exception e) {
		super(e);
	}
	}

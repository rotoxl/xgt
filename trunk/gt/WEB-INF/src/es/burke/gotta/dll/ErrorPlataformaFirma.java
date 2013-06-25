package es.burke.gotta.dll;

import es.burke.gotta.ErrorGotta;

public class ErrorPlataformaFirma extends ErrorGotta{
	private static final long serialVersionUID = -5297918055093732671L;
	public ErrorPlataformaFirma(String msg)
		{super(msg);}
	public ErrorPlataformaFirma(Exception e) {
		super(e);
	}
	public ErrorPlataformaFirma(String causa, Exception e) {
		super(causa,e);
	}
	}

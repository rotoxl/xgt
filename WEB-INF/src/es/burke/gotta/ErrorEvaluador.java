package es.burke.gotta;

public class ErrorEvaluador extends ErrorGotta
{
	private static final long serialVersionUID = 7235512874945380741L;
	public ErrorEvaluador(String msg)
		{super(msg);}
	public ErrorEvaluador(Exception e)
		{super(e);}
	}
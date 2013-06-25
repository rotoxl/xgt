package es.burke.gotta;


public class ErrorAccionDLL extends ErrorAccion
{
private static final long serialVersionUID = 7222262469334743269L;
public ErrorAccionDLL(String msg)
{super(msg);}
public ErrorAccionDLL(Throwable e)
{super(e);}
public ErrorAccionDLL(String msg, Throwable e) {
	super(msg,e);
}
}

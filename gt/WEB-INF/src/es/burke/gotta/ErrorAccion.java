package es.burke.gotta;
public class ErrorAccion  extends ErrorGotta {
	private static final long serialVersionUID = 2479041178773460867L;
	public ErrorAccion(String msg)
		{super(msg);}
	public ErrorAccion(Throwable e) {
		super(e);
	}
	public ErrorAccion(String msg, Throwable e) {
		super(msg,e);
		}
	}

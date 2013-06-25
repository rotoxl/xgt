package es.burke.gotta;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.python.core.PyException;

public class ErrorGotta extends Exception {
	private static final long serialVersionUID = 3907443418752074826L;
	public ErrorGotta(String msg)
		{super(msg);}
	public ErrorGotta(Throwable e)
	{super(e);}
	public ErrorGotta(String causa, Throwable e)
	{super(causa,e);}
	@Override
	public String toString(){
		String ret = this.getMessage();
		Throwable cause = this.getCause();
		while (cause!=null && cause!=this ){
			if(cause instanceof PyException){
				PyException pycause=(PyException)cause;
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				pycause.printStackTrace(pw);
				ret+="\n"+sw.toString();
				}
			else
				ret+="\n"+cause.getMessage();
			cause = cause.getCause();
		}
		return ret;
	}
	}

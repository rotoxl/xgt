package es.burke.gotta.dll;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

public abstract class EnvolventeAX {
	protected ActiveXComponent ax;
	public Variant toVariant(){
		return new Variant(ax);
	}
		

}

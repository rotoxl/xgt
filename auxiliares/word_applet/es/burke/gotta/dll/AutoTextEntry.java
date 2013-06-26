package es.burke.gotta.dll;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

public class AutoTextEntry  extends EnvolventeAX{

	public AutoTextEntry(ActiveXComponent ax) {
		this.ax=ax;
	}

	public Range insert(Range newrg, Variant variant) {
		return new Range(this.ax.invokeGetComponent("Insert",newrg.toVariant(),variant));
	}

}

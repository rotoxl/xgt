package es.burke.gotta.dll;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

public class AutoTextEntries extends EnvolventeAX{
	public AutoTextEntries(ActiveXComponent ax) {
		this.ax=ax;
	}
	public AutoTextEntry add(String marcador, Range newrg) {
		return new AutoTextEntry(this.ax.invokeGetComponent("Add",new Variant(marcador),newrg.toVariant()));
	}

}

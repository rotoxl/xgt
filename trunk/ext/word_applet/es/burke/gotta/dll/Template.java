package es.burke.gotta.dll;

import com.jacob.activeX.ActiveXComponent;

public class Template  extends EnvolventeAX{

	public Template(ActiveXComponent ax) {
		this.ax=ax;
}
	

	public AutoTextEntries getAutoTextEntries() {
		return new AutoTextEntries(this.ax.getPropertyAsComponent("AutoTextEntries"));
	}

	public void setSaved(boolean b) {
		this.ax.setProperty("Saved", b);
	}

}

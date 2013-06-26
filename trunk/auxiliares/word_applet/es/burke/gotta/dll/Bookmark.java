package es.burke.gotta.dll;

import com.jacob.activeX.ActiveXComponent;

public class Bookmark  extends EnvolventeAX{
	public Bookmark(ActiveXComponent ax) {
		this.ax=ax;
	}

	public String getName() {
		return this.ax.getPropertyAsString("Name");
	}

	public Range getRange() {
		return new Range(this.ax.getPropertyAsComponent("Range"));
	}

	public void delete() {
		this.ax.invoke("Delete");
	}
	@Override
	public String toString() {
		return "Marcador" + this.getName()+" "+super.toString();
	}
}

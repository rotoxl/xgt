package es.burke.gotta.dll;

import com.jacob.activeX.ActiveXComponent;

public class Fields  extends EnvolventeAX{
	public Fields(ActiveXComponent ax) {
		this.ax=ax;
	}

	public int getCount() {		
		return this.ax.getPropertyAsInt("Count");
	}

	public void update() {
		this.ax.invoke("Update");
	}

}

package es.burke.gotta.dll;

import com.jacob.activeX.ActiveXComponent;

public class Rows  extends EnvolventeAX{

	public Rows(ActiveXComponent ax) {
		this.ax=ax;
	}

	public void delete() {
		this.ax.invoke("Delete");
	}

}

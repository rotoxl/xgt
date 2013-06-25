package es.burke.gotta.dll;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

public class Range extends EnvolventeAX{

	public Range(ActiveXComponent ax) {
		this.ax=ax;
	}

	public void collapse(Variant variant) {
		this.ax.invoke("Collapse", variant);
		
	}

	public Bookmarks getBookmarks() {
		return new Bookmarks(this.ax.getPropertyAsComponent("Bookmarks"));
	}

	public void delete() {
		this.ax.invoke("Delete");
		
	}

	public Variant getInformation(int i) {
		return this.ax.invoke("Information",new Variant(i));
	}

	public Rows getRows() {
		return new Rows(this.ax.getPropertyAsComponent("Rows"));
	}

	public String getText() {
		return this.ax.getPropertyAsString("Text");
	}

	public void setText(String valor) {
		this.ax.setProperty("Text", valor);
	}

}

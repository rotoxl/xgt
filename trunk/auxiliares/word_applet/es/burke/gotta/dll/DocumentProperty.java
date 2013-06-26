package es.burke.gotta.dll;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

public class DocumentProperty  extends EnvolventeAX{
	public DocumentProperty(ActiveXComponent ax) {
		this.ax=ax;
		}
	public Document getParent() {
		return new Document(this.ax.getPropertyAsComponent("Parent"));
		}
	public String getName() {
		return this.ax.getPropertyAsString("Name");
		}
	public String getValue() {
		return this.ax.getPropertyAsString("Value");
		}
	public void setValue(String valor) {
		this.ax.setProperty("Value", new Variant(valor));	
		}
}

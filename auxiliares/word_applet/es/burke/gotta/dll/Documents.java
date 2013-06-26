package es.burke.gotta.dll;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

public class Documents  extends EnvolventeAX{
	public Documents(ActiveXComponent ax) {
		this.ax=ax;
		}
	public Document open(String ruta) {
		return new Document(this.ax.invokeGetComponent("Open", new Variant(ruta)) );
		}
	public Document add(String ruta) {
		return new Document(this.ax.invokeGetComponent("Add", new Variant(ruta) ));
		}
	public int getCount() {
		return ax.getPropertyAsInt("Count");
		}
	public  Document item(int it) {
		return new Document (ax.invokeGetComponent("Item", new Variant(it)));
		}
}

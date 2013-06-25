package es.burke.gotta.dll;


import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

public class CustomDocumentProperties  extends EnvolventeAX{
	public CustomDocumentProperties(ActiveXComponent ax) {
		this.ax=ax;
	}

	public void add(Variant nombre, Variant link, Variant tipo, Variant valor) {		 
		this.ax.invoke("Add", nombre, link, tipo, valor);
	}
		
	public int getCount() {
		return ax.getPropertyAsInt("Count");
	}
	
	public DocumentProperty item(Integer it) {
		return new DocumentProperty (ax.invokeGetComponent("Item", new Variant(it)));
	}
}


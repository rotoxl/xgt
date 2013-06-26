package es.burke.gotta.dll;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

public class Workbooks extends EnvolventeAX {
	public Workbooks(ActiveXComponent ax) {
		this.ax=ax;
		}
	public Workbook open(String ruta) {
		return new Workbook(this.ax.invokeGetComponent("Open", new Variant(ruta)) );
		}
	public Workbook add(String ruta) {
		return new Workbook(this.ax.invokeGetComponent("Add", new Variant(ruta) ));
		}	
	public int getCount() {
		return ax.getPropertyAsInt("Count");
		}
	public Workbook item(int it) {
		return new Workbook(ax.invokeGetComponent("Item", new Variant(it)));
		}
}


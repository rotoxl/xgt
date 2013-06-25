package es.burke.gotta.dll;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

public class Bookmarks  extends EnvolventeAX{


	public Bookmarks(ActiveXComponent property) {
			this.ax=property;
	}

	public boolean exists(String string) {
		return this.ax.invoke("Exists",string).getBoolean();
	}

	public int getCount() {
		return ax.getPropertyAsInt("Count");
	}

	public Bookmark item(Variant variant) {
		return new Bookmark (ax.invokeGetComponent("Item", variant));
	}

}

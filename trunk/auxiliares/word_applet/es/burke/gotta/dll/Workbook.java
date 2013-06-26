package es.burke.gotta.dll;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

public class Workbook extends EnvolventeAX{
	public Workbook(ActiveXComponent ax) {
		this.ax=ax;
		}
	public void close(boolean guardarCambios) {
		this.ax.invoke("Close",guardarCambios);
		}
	public Variant saveAs(Variant variant) {
		return ax.invoke("SaveAs",variant);
		}
	public void setSaved(boolean b) {
		ax.setProperty("Saved", b);
		}
	public void saveAs(String absolutePath) {
		this.saveAs(new Variant(absolutePath));
		}
	public void Activate() {
		ax.invoke("Activate");
		}
	public CustomDocumentProperties getCustomDocumentProperties() {
		return new CustomDocumentProperties(this.ax.getPropertyAsComponent("CustomDocumentProperties"));
		}
	public void añadeProp(String contadoc) {		
		CustomDocumentProperties props =	this.getCustomDocumentProperties();
		DocumentProperty dp = null;
		
		for(int j=1; j<=props.getCount();j++) {
			dp=props.item(j);
			
			try {
				if (dp.getName().equals("docid")){ 
					dp.setValue(contadoc);
					return;
					}
				}
			catch (Exception e){
				//pass
				}
			}
		
		//si no la hemos encontrado, la metemos nueva
		props.add(new Variant("docid"), new Variant("False"), new Variant("4"), new Variant(contadoc));
	}
}

package es.burke.gotta.dll;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Application {

	public static final String WORD_APPLICATION = "Word.Application",
							   EXCEL_APPLICATION = "Excel.Application";
	private String tipoApp; 
	private ActiveXComponent ax;
	public String extensionPreferida;

	Application(String id){
		this.tipoApp=id;
		this.extensionPreferida=(id.equals(EXCEL_APPLICATION)?".xls":".dot");
		
		this.ax=ActiveXComponent.connectToActiveInstance(id);
		if(this.ax!=null)
			return;
		this.ax=new ActiveXComponent(id);		
		}
	public Documents getDocuments() {		
		return new Documents(this.ax.getPropertyAsComponent("Documents"));
		}
	public Workbooks getWorkbooks() {		
		return new Workbooks(this.ax.getPropertyAsComponent("Workbooks"));
		}
	public int getBackgroundPrintingStatus() {
		return this.ax.invoke("getBackgroundPrintingStatus").getInt();
		}
	public void quit() {
		this.ax.invoke("Quit");
		}
	public void setActivePrinter(String impresora) throws ErrorGenerandoProducto {
		boolean impresoraCambiada=false;
		String msgError=null;
		try {
			this.ax.setProperty("ActivePrinter", impresora);
			impresoraCambiada=true;
			}
		catch (Exception e){
			msgError=e.getMessage();
			}
		
		try {
			this.ax.setProperty("ActivePrinter", impresora);
			impresoraCambiada=true;
			}
		catch (Exception e){
			msgError=e.getMessage();
			}
		
		if (impresoraCambiada==false)
			throw new ErrorGenerandoProducto("Error de WordCliente al activar la Impresora:"+impresora+": "+msgError);
		}
	public void setVisible(boolean b) {
		this.ax.setProperty("Visible", b);
		
		if (!this.tipoApp.equals(EXCEL_APPLICATION))
			this.ax.invoke("Activate");		
		}
	public void oculta() {
		this.ax.setProperty("Visible", false);		
		}
	public void displayAlerts(boolean b) {//para que no saque el diálogo "El archivo que está intentando guardar ya existe, ¿desea sobreescribir?"
		this.ax.setProperty("DisplayAlerts", b);		
		}
	public void printOut(Variant... args) {
		this.ax.invoke("PrintOut", args);
		}
	public Document getActiveDocument() {
		return new Document(ax.getPropertyAsComponent("ActiveDocument"));
		}
	public Workbook getActiveWorkbook() {
		return new Workbook(ax.getPropertyAsComponent("ActiveWorkbook"));
		}

	public Document getDocument(String codigo) throws ErrorGenerandoProducto {
		Document d = null;
		
		try {
			if (codigo!=null){
				Documents ds = new Documents(this.ax.getPropertyAsComponent("Documents"));
				CustomDocumentProperties props = null;
				DocumentProperty dp = null;
				
				for (int i=1;i<=ds.getCount();i++) {		
					d=ds.item(i);		
					
					props = d.getCustomDocumentProperties();
					for(int j=1; j<=props.getCount();j++) {
						dp=props.item(j);
						
						try {
							if (dp.getValue().equals(codigo)) 
								return d;
							}
						catch (Exception e){
							//pass
							}
						}
					}
				 
				throw new ErrorGenerandoProducto("Documento con id='"+codigo+"' no encontrado");
				}
			else
				d=this.getActiveDocument();
			
			return d;
			} 
		catch (Exception e) {
			throw new ErrorGenerandoProducto(e.getMessage());
			}
		}
	public Workbook getWorkbook(String codigo) throws ErrorGenerandoProducto {
		Workbook wb = null;
		
		try {
			if (codigo!=null){
				Workbooks wbs = new Workbooks(this.ax.getPropertyAsComponent("Workbooks"));
				CustomDocumentProperties props = null;
				DocumentProperty dp = null;
				
				for (int i=1;i<=wbs.getCount();i++) {		
					wb=wbs.item(i);
					
					props = wb.getCustomDocumentProperties();
					for(int j=1; j<=props.getCount();j++) {
						dp=props.item(j);
						
						try {
							if (dp.getValue().equals(codigo)) 
								return wb;
							}
						catch (Exception e){
							//pass
							}
						}
					}
				 
				throw new ErrorGenerandoProducto("Documento con id='"+codigo+"' no encontrado");
				}
			else
				wb=this.getActiveWorkbook();
			
			return wb;
			} 
		catch (Exception e) {
			throw new ErrorGenerandoProducto(e.getMessage());
			}
		}
	public void CallMacro(){//en esa macro, si existe, se conecta a webDAV con usuario y contraseña
		try {
			System.out.print("Buscando la macro Normal.gotta.Autoexec");
			Dispatch.call(this.ax, "Run", new Variant("Normal.gotta.Autoexec"));
			System.out.print("...Macro Normal.gotta.Autoexec encontrada y ejecutada");
			}
		catch (Throwable e){
			System.out.print("...Macro Normal.gotta.Autoexec no encontrada");
			}		
		}
}


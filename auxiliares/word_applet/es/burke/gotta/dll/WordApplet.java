/*
-Xdebug -Xrunjdwp:transport=dt_socket,address=127.0.0.1:9000,suspend=y
* */

package es.burke.gotta.dll;
import java.applet.Applet;
import java.awt.GridLayout;
import java.awt.Label;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import com.jacob.com.ComFailException;
import com.jacob.com.ComThread;
import com.jacob.com.Variant;

import netscape.javascript.JSObject;

import es.burke.gotta.Util;

public class WordApplet extends Applet {
	private static final long serialVersionUID = -5295563618912182933L;

	private String nombrePlantilla;
	private String rutaGuardar;
	private String accion;

	private String aplicacionGotta;
	HashMap<String, ArrayList<HashMap<String,Object>>> cacheSubConsultas= new HashMap<String, ArrayList<HashMap<String,Object>>>();

	private int copias=1;
	private String dotweb;
	private String impresora;

	private static final String PARAM_JSISSION_ID = "JSISSION_ID";

	private static final String PARAM_APLICACION = "aplicacion";
	private static boolean libraryLoaded = false;

	private Application apli;
	private String contadoc;	
	private String docid;;

	
	void log(String s){
		this.add(new Label(s));
		super.repaint();
		}
	
	@Override
	public void init() {
		this.setLayout(new GridLayout(0,1));
		}
	
	@Override
	public void start() {
		this.log("WordApplet");
		this.nombrePlantilla=this.getParameter("plantilla");
		this.accion=this.getParameter("accion");

		String tempCopias=this.getParameter("copias");
		if (tempCopias!=null)
			copias=Integer.parseInt(tempCopias);
		this.dotweb=	this.getParameter("dotweb");
		this.aplicacionGotta= this.getParameter(PARAM_APLICACION);
		this.contadoc= this.getParameter("contadoc");
		this.impresora = this.getParameter("impresora");
		String sesion = this.getParameter("sesion");
		
		this.docid = this.getParameter("docid");
		if (this.docid.equals("null"))
			this.docid=null;
		
		if(sesion!=null)
			aplicacionGotta+="&sesion="+sesion;
		try {
			this.log("descargando dll...");
			downloadDLL();
			this.log("Inicializando COM");
			ComThread.doCoInitialize(0);
			this.log("COM iniciando...");
			
			if (Util.en(this.accion, Constantes.accionesWord))
				gottaWord();
			else 
				gottaExcel();
			
			this.log("Finalizando...");
			}
		catch (IOException e) {
			e.printStackTrace();
			log(e.toString());
			retornarError(e.toString());
			}
		catch (Throwable e) {
			e.printStackTrace();
			log(e.toString());
			retornarError(e.toString());
			}
		finally{
			ComThread.doCoUninitialize();
			}
		}

	public void gottaWord() throws IOException, ErrorGenerandoProducto {
		apli = new Application(Application.WORD_APPLICATION);
		
		this.log("Ejecutando"+ accion +"...");
		//EmitirDocumento CerrarDocumento ImprimirDocumento GuardarDocumento
		if (accion.equalsIgnoreCase(Constantes.EmitirImprimirCerrarInvisible)){ //en plantilla tiene que venir un número de copias
			apli.setVisible(true);
			apli.oculta();
			Document doc=emitirDocumento();
			imprimir();
			doc.close(false);
			retornar(0, "Documento enviado a impresión");
			}
		else if (accion.equalsIgnoreCase(Constantes.ImprimirDocumento)) { 
			apli.setVisible(true);
			imprimir();
			}
		else if (accion.equalsIgnoreCase(Constantes.CerrarDocumento)) {
			Document doc = apli.getDocument(docid); //apli.getActiveDocument();
			doc.close(false);
			retornar(0, "Documento cerrado");
			}
		else if (accion.equalsIgnoreCase(Constantes.ImprimiryCerrar)) {
			apli.setVisible(true);
			imprimir();
			Document doc = apli.getDocument(docid); 
			doc.close(false);
			retornar(0, "Documento cerrado");
			}
		else if (accion.equalsIgnoreCase(Constantes.GuardarDocumento)) {
			Document doc = apli.getDocument(docid); //apli.getActiveDocument();
			File tmpDoc = File.createTempFile("tmp", ".doc");
	        String fileName=tmpDoc.getAbsolutePath();
			doc.saveAs(fileName);
			
			subirDocumento(fileName);
			}
		else if (accion.equalsIgnoreCase(Constantes.AbrirDocumento)) {
			apli.setVisible(true);
			Document ret = abrirDocumentoWord(nombrePlantilla);
			log(ret.toString());
			retornar(0, "Documento abierto");
			}
//		else if (accion.equalsIgnoreCase("CerrarWord")) {
//			apli.quit();
//			retornar(0, "Word cerrado");
//			}
		else if (accion.equalsIgnoreCase(Constantes.EmitirDocumento)) {
			apli.setVisible(true);
			emitirDocumento();
			retornar(0, "Documento emitido");					
			}
		else if (accion.equalsIgnoreCase(Constantes.EmitiryGuardar)) {
			ArrayList <String> strtmp = Util.split(this.nombrePlantilla, "|");
			this.nombrePlantilla = strtmp.get(0);
			this.rutaGuardar = strtmp.get(1);
			Document ret = emitirDocumento();
			ret.saveAs(this.rutaGuardar);			
			apli.setVisible(true);
			ret.Activate();
			retornar(0, "Documento emitido y guardado");					
			}
		}
	public void gottaExcel() throws IOException, ErrorGenerandoProducto{
		apli = new Application(Application.EXCEL_APPLICATION);
		
		this.log("Ejecutando"+ accion +"...");
		if (accion.equalsIgnoreCase(Constantes.AbrirDocumentoExcel)) {
			apli.setVisible(true);
			Workbook ret = abrirDocumentoExcel(nombrePlantilla);
			log(ret.toString());
			retornar(0, "Documento abierto");
			}
		else if (accion.equalsIgnoreCase(Constantes.GuardarDocumentoExcel)) {
			Workbook wb = apli.getWorkbook(docid); //apli.getActiveDocument();
			File tmpDoc = File.createTempFile("tmp", ".xls");
			
	        String fileName=tmpDoc.getAbsolutePath();
	        
	        apli.displayAlerts(false);
			wb.saveAs(fileName);
			apli.displayAlerts(true);
	        
			subirDocumento(fileName);
			}
		}
	
	private Document abrirDocumentoWord(String url) throws IOException {
		apli.CallMacro();		
		
		Document doc=null;
		try {
			doc=apli.getDocuments().open(url);
			}
		catch (ComFailException e){
			System.out.println("ERROR al intentar obtener el archivo desde la url="+url);
			e.printStackTrace();
			System.out.println("-------------------------------------");
			throw e;
			}
		
		doc.añadeProp(contadoc);
		
		doc.Activate();
		return doc;
		}
	
//	private Document abrir10veces(String url){
//		Document doc=null;
//		
//		for (int i=0;i<10; i++){
//			System.out.println("\n ... INTENTO "+i+ " de 10...");
//			doc=abrirSinDarError(url);
//			if (doc==null){
//				espera(100L);
//				System.out.println("... falló");
//				}
//			else {
//				System.out.println("... se abrió!");
//				break;
//				}
//			}
//		return doc;
//		}
//	private void espera(Long ms){
//		try {
//			Thread.sleep(ms);
//			} 
//		catch (InterruptedException e) {
//			//pass
//			}
//	}
//	private Document abrirSinDarError(String url){
//		try{
//			return apli.getDocuments().open(url);
//			}
//		catch (ComFailException e){
//			return null;
//			}
//		}
	private Workbook abrirDocumentoExcel(String url) throws IOException {
		apli.CallMacro();		
		
		Workbook sh=null;
		try{
			sh = apli.getWorkbooks().open(url);
			}
		catch (ComFailException e){
			File xplantillaCliente = descargaArchivo();
			sh = apli.getWorkbooks().open(xplantillaCliente.getAbsolutePath());
			}
		
		sh.añadeProp(contadoc);
		sh.Activate();
		return sh;
		}
	
	private void subirDocumento(String rutaArchivo) throws IOException, ErrorGenerandoProducto {
        // Añadimos el parámetro jsesionID si lo hay
        String jsesionid = null;
        if (getParameter(PARAM_JSISSION_ID) != null) 
        	jsesionid = getParameter(PARAM_JSISSION_ID);
        System.out.println("PARAM_JSISSION_ID"+jsesionid+" \n");
        String url=this.getCodeBase()+"subir?aplicacion="+aplicacionGotta+"&contadoc="+contadoc;
        System.out.println("url="+url+" \n");
        URL u = new URL(url);
        URLConnection c = u.openConnection();
        c.setDoOutput(true);
        c.setDoInput(true);
        c.setUseCaches(false);
        c.setRequestProperty("Connection", "Keep-Alive");
        c.setRequestProperty("Content-Type", "multipart/form-data; boundary=****4353");
        if (jsesionid != null) c.setRequestProperty("Cookie", "JSESSIONID="+jsesionid);
        DataOutputStream dstream = new DataOutputStream(c.getOutputStream());
        // write content to the server, begin with the tag that says a content element is comming
        dstream.writeBytes("--****4353\r\n");
        // discribe the content, (in this case it's a file)

		dstream.writeBytes("Content-Disposition: form-data; name=\"myfile\"; " +
                       "filename=\"" +generaNombrePlantilla(this.nombrePlantilla)+
                       "\"\r\nContent-Type: application/octet-stream\r\n\r\n");
        System.out.print("Cabecera completada \n");
        // open a file                                
        FileInputStream fi = new FileInputStream(rutaArchivo);
        // keep reading 1000 bytes from the file
        byte[] bt = new byte[1000];
        int cnt = fi.read(bt);
        while(cnt==bt.length){
               dstream.write(bt,0,cnt);
               cnt = fi.read(bt);
        }
        fi.close();
        System.out.print("Fichero insertado... \n");
        // send the last bit to the server
        dstream.write(bt,0,cnt);
        System.out.print("Fichero insertado \n");
        // now close the file and let the web server know this is the end of this form part
        dstream.writeBytes("\r\n--****4353\r\n");
        // send a form part named TargetURL with the value: /IntranetContent/TelephoneGuide/Upload/
        dstream.writeBytes("Content-Disposition: form-data; name=\"TargetURL\"\r\n\r\n");
        dstream.writeBytes("/IntranetContent/TelephoneGuide/Upload/");
        // let the web server know this is the end of this form part
        dstream.writeBytes("\r\n--****4353\r\n");
        // send a form part named redirectURL with the value: http://none/none
        dstream.writeBytes("Content-Disposition: form-data; name=\"redirectURL\"\r\n\r\n");
        dstream.writeBytes("http://none/none");
        // this is the last information part of the multi part request, close the request
        // close the multipart form request
        dstream.writeBytes("\r\n--****4353--\r\n\r\n");
        dstream.flush();
        dstream.close();
        System.out.print("Fichero enviado \n");
        try{
               System.out.print("Recibiendo cabecera \n");
               DataInputStream in = new DataInputStream( new BufferedInputStream(c.getInputStream()));
               String sIn = in.readUTF();
               System.out.print("Mostrnado respuesta: \n");
               while(sIn!=null){
                   sIn = in.readUTF();
               }
        }catch (EOFException e) {
        	retornar(0, "OK"); 
        }
        catch(Exception e){
            e.printStackTrace();
        	retornar(666, "KO"); 
     }                      
	}

	private String generaNombrePlantilla(String n) {
		ArrayList<String> temp=Util.split(n, Constantes.SEPARADOR);
		return temp.get(temp.size()-1);
	}

	private void imprimir() throws ErrorGenerandoProducto {
		if (impresora!=null && !impresora.equals("")) {
			apli.setActivePrinter(impresora);	
			}
		
		try {
			apli.printOut(new Variant(true), new Variant(false), new Variant(0), new Variant(""), new Variant(""), new Variant(""), new Variant(0), new Variant(copias));
			}
		catch (Exception e){//Por ejemplo, cuando no tenemos ninguna impresora asociada
			throw new ErrorGenerandoProducto("Error al imprimir el documento con docid='"+docid+"'");
			}
		}

	private Document emitirDocumento() throws IOException {
		File xplantillaCliente = descargaArchivo();
		Document doc = apli.getDocuments().add(xplantillaCliente.getAbsolutePath());
		doc.añadeProp(contadoc);
		
		fusionaDoc(doc, null, "", -1);		
		if (doc.getFields().getCount() > 0)
			doc.getFields().update();
		//doc.setSaved(true);
		return doc;
	}

	private File descargaArchivo() throws IOException {
		File dirTemp=null;
		File xplantillaCliente=null;
		for(String x:this.nombrePlantilla.split(",")){
			x=x.trim();
			if (x.startsWith("./"))
				x=getCodeBase()+x;
			else if (!x.startsWith("http://")&& !x.startsWith("https://")&& !x.startsWith("ftp://"))
				x=getCodeBase()+dotweb+"/"+x;
				
			x=x.replaceFirst("/./", "/");
			
			System.out.println("DESCARGAMOS PLANTILLA URL="+x);
			File xx = copiarArchivoACliente(x, dirTemp);
			if(xplantillaCliente==null) // El nombre de la plantilla es el 1º, el resto son archivos aun
				xplantillaCliente=xx;
				dirTemp=xx.getParentFile(); // El resto de archivos en el mismo sitio
			}
		if(xplantillaCliente==null)
			throw new IOException();
		return xplantillaCliente;
	}

	private void fusionaDoc(Document doc, Range r, String excluir, int regActivo) {
		Template attachedTemplate = doc.getAttachedTemplate();
		ArrayList<String> marcadores = new ArrayList<String>();
		Bookmark mar = null;
		Bookmarks bmk=null;

		if (r==null)
			bmk = doc.getBookmarks(); 
		else 
			bmk = r.getBookmarks();

		for (int i=1;i<=bmk.getCount();i++) {
			mar=bmk.item(new Variant(i));
			String nombre = mar.getName();
			if (nombre.toLowerCase().startsWith("pbs"))
				marcadores.add(nombre);
			}

		marcadores = Util.reverse(marcadores);

		String contenido=null;
		String marcador= null; //String marcadorMay=null;

		for (int i=marcadores.size(); i>0; i--) {
			if (!bmk.exists(marcadores.get(i-1).toString())) 
				continue;
			mar=bmk.item(new Variant(marcadores.get(i-1)));
			marcador = mar.getName().toUpperCase();
			contenido=mar.getRange().getText();

			if (marcador.endsWith("_SEC")) {//And Not Espadre(d, mar.Name, Excluir) Then 'Repetición de secciones
				//tratamos la sección y su consulta
				if ( !marcador.equals(excluir)){
					if (bmk.exists(marcador+"_")){
						Bookmark nombreNivel=bmk.item(new Variant(marcador+"_"));
						String nivel=nombreNivel.getRange().getText().trim();
						nombreNivel.getRange().delete();//borro la consulta del documento
						
						if (nivel.contains(" "))
							this.enviaLog("ADVERTENCIA:: el marcador '"+marcador+"_' debería contener el nombre del nivel, pero contiene '"+nivel+"'");
						ArrayList<HashMap<String,Object>> rs =realizarConsultaNivel(nivel);
						if (nivel.startsWith("*"))
							nivel=nivel.split("[.]")[1].toUpperCase();
						cacheSubConsultas.put(nivel, rs);//guarda consulta
						//////////
						
						Range newrg=mar.getRange();

						AutoTextEntries autoTextEntries = attachedTemplate.getAutoTextEntries();
						AutoTextEntry entry=autoTextEntries.add(marcador, newrg);
						newrg.collapse(new Variant("0"));
						int wdWithInTable = 12;

						if (mar.getRange().getInformation(wdWithInTable).getBoolean()){
							try	{
								mar.getRange().getRows().delete();
								}
							catch (Exception e){
								System.out.println("Error DLLWord.emitirDocumento(): "+e.getMessage());}
							}
						else 
							mar.getRange().delete();

						for (int j=rs.size()-1;j>=0;j--){//por que los inserta como una pila
							Range ran=entry.insert(newrg, new Variant("True"));
							fusionaDoc(doc, ran, mar.getName(),j) ;
							mar.delete();
							}
						}
					}
				continue;
				}
			else if (marcador.endsWith("_SEC_")) 
				continue;
			else {
				if (marcador.contains("_SEC")){//¿marcadores mal escritos?
					this.enviaLog("ADVERTENCIA: Se ha encontrado el marcador '"+mar.getName()+"', que parece de sección pero no es válido: recuerda que los nombres admitidos son pbs_[nombreSeccion]_SEC y pbs_[nombreSeccion]_SEC_. Se va a intentar pintar como marcador de dato normal.");
					}
				
				//Buscamos en tabla cargada
				String[] tramos = contenido.split("[.]");
				
				if (contenido.toUpperCase().contains("RPT") || (tramos[0].equals("*") && cacheSubConsultas.containsKey(tramos[1].toUpperCase())) ) {
					ArrayList<HashMap<String, Object>> datos;
					if (contenido.contains(".")) {
						//campo de una consulta, que puede existir o no
						ArrayList<String> v=Util.splitTrim(contenido, ".", true);		
						String nivel;
						String columna;
						if (tramos[0].equals("*")){
							nivel=v.get(1).toUpperCase();
							columna=v.get(2).toLowerCase();
							}
						else {
							nivel=v.get(0);
							columna=v.get(1).toLowerCase();
							}
						
						if (cacheSubConsultas.containsKey(nivel)) 
							datos=cacheSubConsultas.get(nivel);
						else {
							datos=realizarConsultaNivel(nivel);
							cacheSubConsultas.put(nivel, datos);
							}
						
						try {
							HashMap<String, Object> reg=null;
							if (regActivo>=0) 
								reg = datos.get(regActivo);
							else if (!datos.isEmpty()) 
								reg=datos.get(0);
							
							sustituirContenido(columna, mar, reg);
							}
						catch (Exception e) {
							this.log(e.toString());
							}
						}
					}
				else if (contenido.contains("FORMCHECKBOX")){
					//no hago nada.
					}
				else
					sustituirContenido(contenido, mar);
				}
		}
		attachedTemplate.setSaved(true);//pongo la plantilla como salvada
	}

	private void sustituirContenido(String contenido, Bookmark donde)  {
		sustituirContenido(contenido, donde, null);
		}
	private void sustituirContenido(String contenido, Bookmark donde, HashMap<String, Object> h) {
		String[] par = contenido.split(" ");	
		String columna=par[0];
		String formato="";
		String retorno=null;

		if (par.length==2)//con formato
			formato=par[1];

		System.out.println("Formato: " + formato + ". empieza por PAR: " + (formato.toUpperCase().startsWith("PAR") ? "Sí" : "No"));
		
		if (h !=null) {
			if (h.isEmpty())
				retorno="";
			else 
				retorno=Util.formateaWord(h.get(columna), formato);
			
			this.enviaLog(contenido+":: Valor devuelto (desde caché de niveles): "+retorno);
			System.out.println(contenido);
			}
		else {
			ArrayList<HashMap<String, Object>> datos = this.realizarConsultaDato(columna, formato);
			retorno=Util.join("\\", datos);
			}

		if (formato.toUpperCase().startsWith("PAR"))
			mandarParámetro(formato.substring(3), retorno);

		if (retorno != null)
			donde.getRange().setText(retorno);
		}
///////////////////
	protected ArrayList<HashMap<String, Object>> realizarConsultaNivel(String nivel){
		String url = parteFija()+"&nivel="+nivel;
		return _realizarConsulta(url);
		}
	protected ArrayList<HashMap<String, Object>> realizarConsultaDato(String expresion, String formato){
		String url = parteFija()+"&expresion="+expresion+"&formato="+formato;
		return _realizarConsulta(url);
		}
	protected ArrayList<HashMap<String, Object>> enviaLog(String msg){
		//para enviar texto al Monitor de la aplicación
		String url = parteFija()+"&log="+encode(msg);
		return _realizarConsulta(url);
		}
	protected ArrayList<HashMap<String, Object>> mandarParámetro(String parametro, String valor){
		String url = parteFija()+"&parametro="+parametro+"&valor="+valor;
		return _realizarConsulta(url);
		}
	private String encode(String trozo){
		try {
			return URLEncoder.encode(trozo, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "error en la codificación";
		}
	}
	
	private String parteFija(){
		String codeBase=getCodeBase().toString();
		if (codeBase.startsWith("file"))
			codeBase="http://localhost:8080/gotta/fijo/applets";
		return codeBase+"datoFusion?aplicacion="+aplicacionGotta+"&contadoc="+contadoc;
		}
///////////////////
	protected ArrayList<HashMap<String, Object>> _realizarConsulta(String url)  {
		URL servletDatos;
		URLConnection servletConnection;
		ObjectInputStream inputFromServlet;
		String err;
		try {
			servletDatos = new URL(url);
	        String jsesionid = null;
	        if (getParameter(PARAM_JSISSION_ID) != null) 
	        	jsesionid = getParameter(PARAM_JSISSION_ID);
			servletConnection = servletDatos.openConnection();
	        if (jsesionid != null) servletConnection.setRequestProperty("Cookie", "JSESSIONID="+jsesionid);
			inputFromServlet = new ObjectInputStream(servletConnection.getInputStream());
			return leeDatosConsulta(inputFromServlet);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			err=e.toString();
		} catch (IOException e) {
			e.printStackTrace();
			err=e.toString();
		}
		ArrayList<HashMap<String, Object>> ret = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> msg = new HashMap<String, Object>();
		msg.put("msg", err);
		ret.add(msg);
		return ret;
	}

	@SuppressWarnings("unchecked")
	protected ArrayList<HashMap<String, Object>> leeDatosConsulta(ObjectInputStream theInputFromServlet) {

		try {	        
			ArrayList<HashMap<String, Object>> datos = (ArrayList<HashMap<String, Object>>) theInputFromServlet.readObject();
			theInputFromServlet.close();
			return datos;
		}
		catch (IOException e) {
			System.out.println("IOException:"+e.toString());
		}
		catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException:"+e.toString());                
		}

		return null;
	}

	private static boolean loadLibrary(String rutaDLL) {
		try {
			java.net.URL url = WordApplet.class.getResource(rutaDLL);

			InputStream inputStream = url.openStream();
			File temporaryDll = File.createTempFile("jacob", ".dll");
			FileOutputStream outputStream = new FileOutputStream(temporaryDll);
			byte[] array = new byte[8192];
			for (int i = inputStream.read(array); i != -1; i = inputStream.read(array)) 
				outputStream.write(array, 0, i);

			outputStream.close();
			temporaryDll.deleteOnExit();
			System.setProperty("jacob.dll.path", temporaryDll.getAbsolutePath());
			return true;
		}
		catch (Throwable e) {
			System.out.println("ERROR en loadLibrary:"+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	private void downloadDLL() {
		String JACOB_DLL;
		if(System.getProperty("os.arch").contains("64"))
			JACOB_DLL = "jacob-1.14.1-x64.dll";
		else
			JACOB_DLL = "jacob-1.14.1-x86.dll";
		if (!libraryLoaded) 
			libraryLoaded = loadLibrary("/WEB-INF/lib/"+JACOB_DLL);			
	}
	private File copiarArchivoACliente(String urlOrigen, File dir) throws IOException {
		class MyAuthenticator extends Authenticator {
		    @Override
			protected PasswordAuthentication getPasswordAuthentication() {
		        URL url = this.getRequestingURL();
		        String[] usuPwd = url.getUserInfo().split(":");
		        String username = usuPwd[0];
		        String password = usuPwd[1];
		        return new PasswordAuthentication(username, password.toCharArray());
		    }
		}
		URL url = new URL(urlOrigen);
		url.openConnection();
		String userInfo = url.getUserInfo();

		if(userInfo!=null) // contiene usu:pass
			Authenticator.setDefault(new MyAuthenticator());
		
		InputStream inputStream = url.openStream();
		String nombreFich = url.getFile().substring(url.getFile().lastIndexOf("/"));
		File salida= dir==null?File.createTempFile("tmp", this.apli.extensionPreferida):new File(dir,nombreFich);
		salida.deleteOnExit();
		FileOutputStream fos =new FileOutputStream(salida) ;
		byte[] array = new byte[8192];
		for (int i = inputStream.read(array); i != -1; i = inputStream.read(array)) 
			fos.write(array, 0, i);
		fos.close();
		return salida;
		}
	
	private void _retornar(String funcionjs, String msg){
		//nuevo método para cerrar la página del applet sin usar una redirección
		//	(hay algunos firefox y chromes que, al ser redireccionados, pierden la referencia del opener)
		JSObject jso = (JSObject) JSObject.getWindow(this);
		try {
			jso.call(funcionjs, new String[] {msg, docid});
       		}
       	catch (Exception ex) {
           ex.printStackTrace();
       		}
		}
	private void retornar(int retorno, String msg) {
		if(retorno==0){
			_retornar("devuelveOK", msg);
			}
		else
			this.log(msg+"("+retorno+")");
		}
	private void retornarError(String msg) {
		_retornar("devuelveKO", msg);
		}
}


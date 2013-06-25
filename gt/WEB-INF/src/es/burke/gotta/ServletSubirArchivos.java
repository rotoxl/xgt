package es.burke.gotta;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import es.burke.gotta.Aplicacion.DIC_Configuracion;
import es.burke.gotta.Constantes.ColisionFicheros;
import es.burke.gotta.dll.DLLSubirFicheros;
import es.burke.gotta.dll.IDLLSubirFicheros;
import es.burke.gotta.dll.WordCliente;
import org.apache.commons.fileupload.ProgressListener;
import org.json.JSONException;
import org.json.JSONObject;

public class ServletSubirArchivos extends HttpServlet {
	private static final long serialVersionUID = -5120710938516380624L;
	private final String sep="/";
	
	public long fileSizeMax=-1L;
	ServletFileUpload upload;
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException {
		req.setCharacterEncoding( Constantes.CODIF );

		try {
			String ret = subirFichero(req);
			hazFlush(res, ret);
			} 
		catch (ErrorGotta e) {
			hazFlush(res, generaMSGError(e));
			} 
		catch (FileSizeLimitExceededException e){
			hazFlush(res, generaJSON("ErrorGotta", "Se ha excedido el límite del tamaño de archivo subido: debe ser menos de "+megas()));
			}
		catch (Exception e){
			hazFlush(res, generaMSGError(e));
			}
		}
	private String generaMSGError(Exception e) {
		return generaJSON(e.getClass().getName(), e.getMessage());
		}
	public String megas(){
		String bs=fileSizeMax+" bytes";
		int unMega=1024*1024;
		if (fileSizeMax>unMega)
			bs=(fileSizeMax/unMega)+ " MB";
		return bs;
		}
	public JSONObject _generaJSON(String id, String msg) throws JSONException{
		return new JSONObject()
			.put("tipo", "error")
			.put("id", id)
			.put("msg", msg);
		}
	public String generaJSON(String id, String msg){
		try {
			return _generaJSON(id, msg).toString();
			}
		catch (JSONException e1) {
			return msg;
			}
		}
	protected void hazFlush(HttpServletResponse response, String text) throws IOException{
		Jsp.setHeaders(response,"text/plain");
		PrintWriter out = response.getWriter();
		out.write(text);
	    out.flush();
		}

	@SuppressWarnings("unchecked")
	public String subirFichero(HttpServletRequest req) throws Exception {
		//http://www.avajava.com/tutorials/lessons/how-do-i-monitor-the-progress-of-a-file-upload-to-a-servlet.html
		
		String aplicacion=req.getParameter("aplicacion");
		Usuario usr = Usuario.desdeSesion(req, aplicacion, req.getSession(false));
		if(usr==null )
			throw new ErrorConexionPerdida("error: No se ha recuperado el usuario de la petición");

		// Create a factory for disk-based file items
		FileItemFactory factory = new DiskFileItemFactory();
		
		// Create a new file upload handler
		this.upload = new ServletFileUpload(factory);
		ServletSubirArchivosProgress pl = new ServletSubirArchivosProgress();
		pl.servlet=this;
		
		this.upload.setProgressListener(pl);
		usr.getMotor().progresoSubida=pl;
		
		try {
			String s=usr.getApli().getDatoConfig(DIC_Configuracion.maxUploadSize);
			if (s!=null){
				fileSizeMax=Long.parseLong(s);
				upload.setFileSizeMax(fileSizeMax);
				}
			}
		catch (Exception e){
			//pass
			}		
		
		// Parse the request
		List<FileItem> files = upload.parseRequest(req);
		
		//sacamos el código de aplicacion del formulario multipart. Este código de aplicación, si existe será el utilizado
		//para recuperar el objeto usuario.
		String contadoc = null;
		for (int i=0;i<files.size();i++) {
			FileItem item=files.get(i);

			if (item.isFormField() && item.getFieldName().equals("contadoc"))
				contadoc = item.getString();
			}

		if (contadoc==null) 
			contadoc=req.getParameter("contadoc");
			
		for (int i=0;i<files.size();i++) {
			FileItem item=files.get(i);
			if (item.isFormField()) continue;
			
			IDLLSubirFicheros accion=null;
			ArrayList<IDLLSubirFicheros> listaFicheros = usr.getMotor().tramActivo().getListaFicheros();
			if (contadoc!=null){
				for(IDLLSubirFicheros f:listaFicheros){
					if(f instanceof WordCliente){
						WordCliente fw = (WordCliente)f;
						if(fw.contadoc.equals(contadoc)){
							accion=f;
							break;
							}
						}
					}
				}
			else if (item.getFieldName().startsWith("archivo")){
				//vendrá archivo0, archivo1... o archivo0_0, archivo0_1 si es subida múltiple
				String na=item.getFieldName();
				int j=Integer.parseInt(na.substring("archivo".length(), na.contains("_") ?na.indexOf("_"):na.length() ));
				accion=listaFicheros.get(j);
				}
			else {//viene de la DLL de escaneo, supongo que aquí no tiene sentido que haya varias llamadas a escanear
				accion=listaFicheros.get(0);
				}
			
			if (accion==null)
				return "ok";
			
			//	por un bug de ie ¿6? en el nombre nos llega la ruta física de origen (en el cliente)
			//habrá que apañarla:
			String nombreOriginalFichero = new File(item.getName().replaceAll("\\\\", "/")).getName();
			
			String ruta = accion.getRuta();
			String nuevaRuta;
			File fichNuevo;
			
			if(ruta==null){
				fichNuevo = File.createTempFile("laa", ".tmp");
				nuevaRuta=fichNuevo.getAbsolutePath();
				}
			else{
				ruta=Util.arreglaRutaFisica(ruta);
				String rutaDir=ruta;
				if (!rutaDir.endsWith(sep))
					rutaDir=rutaDir.substring(0, rutaDir.lastIndexOf(sep));
				Archivo.generaEstructuraDirectorios(rutaDir);	
				File fichAnterior=new File(ruta);
				nuevaRuta=accion.getRuta();
				if (fichAnterior.isDirectory())
					nuevaRuta+=(nuevaRuta.endsWith(sep)?"":sep)+nombreOriginalFichero;
				fichNuevo=new File(nuevaRuta);
				}
			
			String nombre=nombreOriginalFichero;
			Boolean haSidoRenombrado=false;
			if (fichNuevo.exists()){
				if (accion.getColision()==ColisionFicheros.sobreEscribir){
					item.write(fichNuevo);
					nombre=getNombre(nuevaRuta)+this.getExtension(nuevaRuta);
					marcaComoSobreescrito(usr, accion, nombre);
					}
				else if (accion.getColision()==ColisionFicheros.renombrar){
					haSidoRenombrado=true;
					
					String prenombre=this.getNombre(nuevaRuta)+"_";
					String ext=this.getExtension(nuevaRuta);
					int j=1;
					while (new File(this.getDir(nuevaRuta)+prenombre+j+ext).exists()){
						j++;
						}
					nombre=prenombre+j+ext;
					nuevaRuta=this.getDir(nuevaRuta)+nombre;
					fichNuevo=new File(nuevaRuta);
					item.write(fichNuevo);
					}
				else if (accion.getColision()==ColisionFicheros.descartar){
					nombre="-1";
					}	 	
				}
			else
				item.write(fichNuevo);
			
		    // retorno de las dll
			retorno(usr, accion, nombre, nuevaRuta, nombreOriginalFichero, haSidoRenombrado);
			}
		return "ok";
		}
	
	private void retorno(Usuario usr, IDLLSubirFicheros accion, String nombre, String ruta, String nombreOriginalFichero, boolean haSidoRenombrado) throws ErrorGotta{
		if (accion instanceof DLLSubirFicheros && ((DLLSubirFicheros)accion).getSubidaMultiple()) {
			ITabla ret=((DLLSubirFicheros) accion).getTablaRetorno();
			ret.addNew();
			
			ret.setValorCol("nombre", nombre);
			ret.setValorCol("ruta", ruta);
			ret.setValorCol("nombreOriginal", nombreOriginalFichero);
			ret.setValorCol("haSidoRenombrado", haSidoRenombrado);
			ret.setValorCol("haSidoSobreescrito", false);
			}
		else {
			asignaVariable(usr, accion.getRetornoNombre(), nombre);
			asignaVariable(usr, accion.getRetornoRuta(), ruta);
			}
		usr.añadeMSG("Archivo subido a '"+ruta+"'", Constantes.TipoMensajeGotta.archivos);
		}
	private void marcaComoSobreescrito(Usuario usr, IDLLSubirFicheros accion, String nombre){
		DLLSubirFicheros xaccion=(DLLSubirFicheros)accion;
		
		if (xaccion.getTablaRetorno()!=null){
			ITabla ret=xaccion.getTablaRetorno();
			
			int posAnterior=ret.registroActual;
			for (int i=posAnterior-1; i>=0; i--){
				Fila fila=ret.datos.get(i);
				if (fila.gets("nombre").equalsIgnoreCase(nombre))
					fila.__setitem__("haSidoSobreescrito", true);
				}
			
			ret.registroActual=posAnterior;
			}
		}
	private void asignaVariable(Usuario usr, String campoVariable, String valor) throws ErrorCampoNoExiste, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorTablaNoExiste, ErrorCargandoTabla, ErrorCreandoTabla, ErrorArrancandoAplicacion {
		usr.getMotor().setValorSimbolo(campoVariable, valor);
		}
	
	private String getDir(String r){
		int barra=r.lastIndexOf(sep);
		return r.substring(0,barra)+sep;
	}
	private String getNombre(String r){
		int barra=r.lastIndexOf(sep);
		int punto=r.lastIndexOf(Constantes.PUNTO);
		if (punto<0)
			return r.substring(barra+1);
		return r.substring(barra+1,punto);
		}
	private String getExtension(String r){
		int punto=r.lastIndexOf(Constantes.PUNTO);
		if (punto<0)
			return r;
		return r.substring(punto);
		}
}

class ServletSubirArchivosProgress implements ProgressListener {
	public ServletSubirArchivos servlet;
	public long fileSizeMax=-1L;
	
	private long bytesRead = 0;
	private long contentLength = -1;
	
	public void update(long bytesRead, long contentLength, int items) {
		this.bytesRead = bytesRead;
		this.contentLength= contentLength;
		}
	public JSONObject JSON() throws JSONException{
		if (servlet.fileSizeMax>-1L && contentLength>servlet.fileSizeMax){
			servlet.upload.setFileSizeMax(contentLength); //intentamos provocar el error cuanto antes
			
			return servlet._generaJSON("ErrorGotta", "El archivo (o archivos) que se está intentando subir excede el tamaño máximo permitido: debe ser menos de "+servlet.megas());
			}
		
		String percentDone="<desconocido>";
		if (contentLength > -1) 
			percentDone = ""+Math.round(100.00 * bytesRead / contentLength);

		return new JSONObject().put("leidos", bytesRead).put("total", contentLength).put("porcentaje", percentDone);
		}
	}

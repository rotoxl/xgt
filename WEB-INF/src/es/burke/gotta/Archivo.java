package es.burke.gotta;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;
import es.burke.gotta.dll.DLLGotta;
import es.burke.gotta.dll.IFirma;


public class Archivo {
	public static enum Tipo {PDF, XML, JPG, PNG, OTROS;}
	
	public Usuario usr;
	
	public String nombreFichero;
	private String rutaCarpeta;
	public String sufijo;
	public String rutaFisica;
	public Tipo tipo;
	
	public DLLGotta accion=null; 
	
	public HashMap<String, Object>otrosDatos=new HashMap<String, Object>();
	
	public String sacaExtension(){
		return nombreFichero.substring( nombreFichero.lastIndexOf(Constantes.PUNTO)+1);
		}
	public Archivo(Usuario usr, String rutaFichero, HashMap<String, Object> otrosDatos){
		ArrayList<String> r=Archivo.separaNombreyRuta(rutaFichero);		
		constructor(usr, r.get(1), r.get(0), otrosDatos);
		}
	public Archivo(Usuario usr, String rutaFichero){
		ArrayList<String> r=Archivo.separaNombreyRuta(rutaFichero);		
		constructor(usr, r.get(1), r.get(0), null);
		}
	public Archivo(Usuario usr, String rutaCarpeta, String nombreFichero, HashMap<String, Object> otrosDatos){
		constructor(usr, rutaCarpeta, nombreFichero, otrosDatos );
		}
	private void constructor(Usuario usuario, String xrutaCarpeta, String xnombreFichero, HashMap<String, Object> otrosDatos) {
		this.usr=usuario;
		
		this.rutaCarpeta=Util.rutaFisicaRelativaOAbsoluta(xrutaCarpeta);
		this.nombreFichero=xnombreFichero;
		
		this.otrosDatos=otrosDatos;
		generaRutas();
		}
	public static ArrayList<String> separaNombreyRuta(String ruta){
		String rutaInicio=Util.rutaFisicaRelativaOAbsoluta(ruta);
		
		ArrayList<String> temp=Util.split(rutaInicio, "/");
		String nombre=temp.get(temp.size()-1);
		
		temp.remove(temp.size()-1);
		
		String rutaDir=Util.join("/", temp); 
		if (temp.size()==1)//C:
			rutaDir+= "/";
		
		return Util.creaListaString(nombre, rutaDir);
		}
	private void generaRutas(){
		this.rutaFisica=Util.rutaFisicaRelativaOAbsoluta(this.rutaCarpeta+Constantes.SEPARADOR+this.nombreFichero);
		}
	public String url(){ 
		return "file://"+this.rutaFisica; 
		} 
	public Boolean existeArchivo(){
		return existeArchivo(this.rutaFisica);
		}
	public static Boolean existeArchivo(String ruta){
		File inf = new File(ruta);
		return (inf.exists()); 
	}
	public static File dameArchivoFisico(String ruta) throws ErrorGotta{
		File inf = new File(ruta);
		if (!inf.exists()) 
			throw new ErrorGotta("Error: No se encuentra el fichero: "+ruta);
		return inf;
		}

//	public File dameArchivoFisico() {
//		File inf = new File(this.rutaFisica);
//		return inf;
//		}
	public void escribeArchivoTexto(String texto, String xSufijo) throws ErrorGotta{
		String nomArch;
		if (xSufijo!=null){
			this.sufijo=xSufijo;
			nomArch=this.rutaFisica+xSufijo;
		}
		else
			nomArch=this.rutaFisica;
		File file = new File(nomArch);
		generaEstructuraDirectorios();
		
		try {
			FileOutputStream fos = new FileOutputStream(file);
			OutputStream bout = new BufferedOutputStream(fos);
			OutputStreamWriter out =  new OutputStreamWriter(bout,"UTF-8");

			out.write(texto);
			out.flush();
			out.close();
			} 
		catch (FileNotFoundException e) {
			throw new ErrorGotta(e.getMessage());
			} 
		catch (IOException e) {
			throw new ErrorGotta(e.getMessage());
			}
	}

	public String leeArchivoTexto() throws ErrorGotta{
		StringBuffer str=new StringBuffer();
		try {
			File file = new File(this.rutaFisica);
			FileReader fr= new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String s;
			while((s = br.readLine()) != null) { 
				str.append(s+Constantes.vbCrLf);
				}
			br.close();
			} 
		catch (IOException e) {
			throw new ErrorGotta(e.getMessage());
			}
		return str.toString();
	}
	
	public static void borraArchivo(Aplicacion apli, String ruta) throws ErrorGotta{
		Cifs CIFSorigen = rutaCIFS(apli, ruta);
		if (CIFSorigen!=null){
			try {
				SmbFile inf = new SmbFile(CIFSorigen.rutaCIFS,CIFSorigen.auth);
				inf.delete();
				return;
				} 
			catch (Exception e) {
				throw new ErrorAccionDLL("Error al borrar archivo... "+CIFSorigen.rutaCIFS,e);			
				}
			}
		else {
			ruta=Util.rutaFisicaRelativaOAbsoluta(ruta);
			File f=dameArchivoFisico(ruta);
			f.delete();
			}
		}
	@SuppressWarnings({ "resource" })
	public static void copiaArchivo(Aplicacion apli, String rutaOrigen, String rutaDestino) throws ErrorGotta {
		// Novedad CIFS
		InputStream fis=null; 
		OutputStream fos=null;
		String name;
		Cifs CIFSorigen = rutaCIFS(apli, rutaOrigen);
		Cifs CIFSdestino = rutaCIFS(apli, rutaDestino);
		FileLock fisl=null;
		if(CIFSorigen!=null){
			SmbFile inf;
			try {
				inf = new SmbFile(CIFSorigen.rutaCIFS,CIFSorigen.auth);
				name=inf.getName();
				fis=inf.getInputStream();
				} 
			catch (MalformedURLException e) {
				throw new ErrorAccionDLL("Error en archivo de origen... "+CIFSorigen.rutaCIFS,e);			
				}
			catch (IOException e) {
				throw new ErrorAccionDLL("Error en archivo de origen... "+CIFSorigen.rutaCIFS,e);
				}
			}
		else{
			try {
				rutaOrigen=Util.rutaFisicaRelativaOAbsoluta(rutaOrigen);
				File inf=dameArchivoFisico(rutaOrigen);
				fis = new FileInputStream(inf);
				name=inf.getName();
				} 
			catch (FileNotFoundException e) {
				throw new ErrorAccionDLL("Error en archivo de origen... "+rutaOrigen, e);
				}
			}
		if(CIFSdestino!=null){
			try {
				SmbFile outf = new SmbFile(CIFSdestino.rutaCIFS,CIFSdestino.auth);
				generaEstructuraDirectoriosCIFS(outf);
				if (outf.isDirectory())
					outf=new SmbFile(CIFSdestino.rutaCIFS+"/"+name,CIFSdestino.auth);
				fos=outf.getOutputStream();
				} 
			catch (Exception e) {
				throw new ErrorAccionDLL("Error en archivo de destino... "+CIFSdestino.rutaCIFS,e);
				}
			}
		else{
			rutaDestino=Util.rutaFisicaRelativaOAbsoluta(rutaDestino);
			generaEstructuraDirectorios(rutaDestino);
			File rutaDest=new File(rutaDestino);
			if (rutaDest.isDirectory())
				rutaDest=new File(rutaDestino+"/"+name);
			FileOutputStream fosx;
			try {
				fosx = new FileOutputStream(rutaDest);
				fisl = fosx.getChannel().tryLock();
				}
			catch (Exception e1) {
				throw new ErrorAccionDLL("Error en archivo de destino... "+rutaDestino,e1);			
				} 
			fos=fosx;
			if(fisl==null)
				throw new ErrorAccionDLL("No se ha podido bloquear el fichero" +rutaOrigen);
		}
		
		
		try {
			byte[] buf = new byte[1024];
			int i = 0;
			while((i=fis.read(buf))!=-1)
				fos.write(buf, 0, i);
			fos.flush();
			fos.close();
			fis.close();
			}
		catch (IOException e) {
			throw new ErrorGotta(e);
			}
		finally {
			try{
				if(fisl!=null) fisl.release();
				if(fos!=null) fos.close();
				if(fis!=null) fis.close();
				}
			catch(IOException e){
				//pass
				}
			}
		}
	public static Cifs rutaCIFS(Aplicacion apli, String rutaOrigen) throws ErrorAccionDLL, ErrorArrancandoAplicacion{
		rutaOrigen=rutaOrigen.replace("\\", "/");
		if (!rutaOrigen.startsWith("//"))
			return null;
		String servidor=rutaOrigen.split("/")[2];
		String usu = apli.getDatoConfig("cifs_"+servidor+"_user");
		if(usu==null)
			return null;
		String pwd=apli.getDatoConfig("cifs_"+servidor+"_password");
		String domain=apli.getDatoConfig("cifs_"+servidor+"_domain");
		Cifs ret = new Cifs();
		ret.ruta=rutaOrigen;
		ret.servidor=servidor;
		try {
			ret.auth=logon(servidor, domain, usu, pwd);
			} 
		catch (SmbException e) {
			throw new ErrorAccionDLL("Error abriendo "+rutaOrigen,e);
			} 
		catch (UnknownHostException e) {
			throw new ErrorAccionDLL("Error abriendo "+rutaOrigen,e);
			}
		ret.rutaCIFS="smb:"+rutaOrigen;
		return ret;

	}
	
	public void generaEstructuraDirectorios() throws ErrorGotta{
		generaEstructuraDirectorios(this.rutaFisica);
	}
	public static void generaEstructuraDirectorios(String nombreFich) throws ErrorGotta {
		nombreFich=Util.arreglaRutaFisica(nombreFich);
		File fich = new File(nombreFich);
		if (fich.exists()) {
			return;
			}
		if(!nombreFich.endsWith("/"))
			fich = fich.getParentFile();
		if (!fich.mkdirs()) {
			if (!fich.exists()) 
				throw new ErrorGotta("Error al generar la ruta '"+fich.getAbsolutePath()+"'");
			}
		}
	public static void generaEstructuraDirectoriosCIFS(SmbFile outf) throws ErrorGotta {
		try{
			if (outf.exists()) {
				return;
				}
			if(!outf.getName().endsWith("/"))
				outf = new SmbFile( outf.getParent());
			if(!outf.exists())
				outf.mkdirs();
			}
		catch (Exception e) {
			throw new ErrorAccionDLL("Error generando directorio "+ outf.getPath(),e);
			}
		}
	public void escribeArchivoTexto(String texto) throws ErrorGotta {
		escribeArchivoTexto(texto, null);
	}

	public static void descargaFichero(HttpServletResponse response, String ruta) throws IOException{
		descargaFichero(response, new File(ruta));
	}
	public static void descargaFichero(HttpServletResponse response, File archivo) throws IOException{
		String ruta=archivo.getAbsolutePath();
		InputStream fis=new FileInputStream(ruta);
		descargaFichero(response, ruta, fis);
		}
	public static void descargaFichero(HttpServletResponse response, String ruta, InputStream fis) throws IOException{
		response.setHeader("Content-Disposition","attachment; filename="+Constantes.COMILLAS+ruta+Constantes.COMILLAS);
		Util.ponMime(response, ruta);
		ServletOutputStream salida = response.getOutputStream();
		
		BufferedOutputStream bos = new BufferedOutputStream(salida);
		int iLen =0;
		byte[] buffer = new byte[4096];
		while ((iLen = fis.read(buffer)) != -1) {
			bos.write(buffer,0,iLen);
			}
		fis.close();
		bos.close();
		salida.flush();
		salida.close();	
	}
	static HashMap<String,NtlmPasswordAuthentication> sesionesCIFS=new HashMap<String,NtlmPasswordAuthentication>();
	private static NtlmPasswordAuthentication logon(String host,String domain,String usu,String pwd) throws SmbException, UnknownHostException{
		if(sesionesCIFS.containsKey(host))
			return sesionesCIFS.get(host);
		UniAddress dc = UniAddress.getByName(host);
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domain, usu,pwd);
		SmbSession.logon( dc, auth );
		sesionesCIFS.put(host, auth);
		return auth;
	}
	public String obtenerDigest(Usuario usr) throws ErrorGotta {
		IFirma firma = IFirma.getClassFirma(this.usr.getApli());
		try {
			return firma.obtenerDigest(usr, this.rutaFisica);
			} 
		catch (Exception e) {
			throw new ErrorGotta(e);
			}
		}
		static HashMap<String, String> _mimeType=new HashMap<String, String>();
	static {
		_mimeType.put(".doc", "application/msword");
		_mimeType.put(".docx", "application/msword");
		_mimeType.put(".pdf", "application/pdf");
		_mimeType.put(".xls", "application/vnd.ms-excel");
		_mimeType.put(".xlsx", "application/vnd.ms-excel");
		
		_mimeType.put("default", "text/plain");
		}
	public static Object mimeType(String nombreFichero)  {
		String ext=nombreFichero.substring( nombreFichero.lastIndexOf(Constantes.PUNTO));
		if (_mimeType.containsKey(ext))
			return _mimeType.get(ext);
		else 
			return _mimeType.get("default");
		}
}

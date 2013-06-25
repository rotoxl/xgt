package es.burke.gotta.dll;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jcifs.smb.SmbFile;

//import com.lowbagie.text.pdf.PdfWriter;

import es.burke.gotta.Accion;
import es.burke.gotta.Archivo;
import es.burke.gotta.Cifs;
import es.burke.gotta.Constantes;
//import es.burke.gotta.ErrorAccionDLL;
//import es.burke.gotta.ErrorArrancandoAplicacion;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.FechaGotta;
import es.burke.gotta.Motor;
import es.burke.gotta.PoolAplicaciones;
import es.burke.gotta.Util;
import es.burke.gotta.Constantes.TipoMensajeGotta;

public class GottaWeb extends DLLGotta{

	String nombredoc; 
	@Override
	public String accionesValidas() {
		return "enviarmensaje enviardocumento zip "+
		"hashdocumento hashMD5 hashSHA "+
		" traer llevar "+
		"moverdocumento replace replace2 formatfecha "+
		"ejecutarcmd shell tifapdf "+
		"encripta desencripta mostrardocumento borrardocumento download";
		
//		return "fileupload fileupload2 enviarmensaje enviardocumento zip "+
//				"validarfirma hashdocumento hashMD5 hashSHA validarfichero "+
//				"appletfirma traer llevar mostrardocumento download borrardocumento "+
//				"moverdocumento replace replace2 setnodo asociardocumento formatfecha "+
//				"jms webservice ejecutarcmd shell ira ip tifapdf excelatabla idioma "+
//				"encripta desencripta SubirDocumento BajarDocumento reservajasper";
	}
	private String getConfigDocu() {
		return this.mMotor.getApli().getDatoConfig("documentos");
	}
	public String dirDocu() {
			return PoolAplicaciones.dirInstalacion+getConfigDocu();
		}
	
	public GottaWeb(){ // NO BORRAR
		 		/**/
		 		}
	private GottaWeb(String ruta, Motor motor){
		this.nombredoc=ruta;
		this.mMotor=motor;
		}
	@Override
	public Object ejecuta(Accion aaccion, Object oValor) throws ErrorGotta {
		String valor=oValor.toString();
		
		String accion = aaccion.accion;
		if(accion.equalsIgnoreCase("zip")) {			
			GottaWeb otraDistinta=new GottaWeb(this.zip(valor), mMotor);
			usr.productosGenerados.put(""+otraDistinta.hashCode(), otraDistinta);
			}		
		else if (accion.equalsIgnoreCase("traer")) 
			return traerArchivo(valor);
		
		else if(accion.equalsIgnoreCase("llevar")) 
			return llevarArchivo(valor);

		
		else if(accion.equalsIgnoreCase("moverdocumento")) 
			return moverDocumento(valor);

		else if(accion.equalsIgnoreCase("borrardocumento")) 
			return borrarDocumento(valor);

		else if(accion.equalsIgnoreCase("replace")) 
			return replace(valor);
		
		else if(accion.equalsIgnoreCase("replace2")) {
			String cs=valor.substring(0,1);
			String cadena=valor.substring(1);
			return replace(cadena, cs);
			}
		
		else if(accion.equalsIgnoreCase("formatfecha")) {
			ArrayList<String> v = Util.split(valor, "¬");
			String fecha = v.get(0);
			String formato = v.get(1);
			
			return new FechaGotta(fecha).formatoEspecial(formato);
			}
		
		else if(accion.equalsIgnoreCase("ejecutarCmd")||accion.equalsIgnoreCase("Shell")) {
			String cmd=valor;
			  try{  	
			  	java.lang.Runtime.getRuntime().exec(cmd);
			  	return Constantes.CERO;
			  	}
			  catch (IOException error){
			  	throw new ErrorGotta("Error ejecutando "+ cmd +":"+ error.getMessage());
			  	}
			}
//		else if(accion.equalsIgnoreCase("tifapdf")) 
//			return tiffAPdf(valor);
		
		else if(accion.equalsIgnoreCase("encripta")) {
			ArrayList<String> t = Util.split(valor,Constantes.PIPE);
        	return encriptar(t.get(0), t.get(1), t.get(2));
			}
		else if(accion.equalsIgnoreCase("desencripta")) {
        	ArrayList<String> aaux = Util.split(valor,Constantes.PIPE);
        	return desencriptar(aaux.get(0), aaux.get(1), aaux.get(2));
			}
		else if (accion.equalsIgnoreCase("mostrardocumento")) {
			GottaWeb otraDistinta=new GottaWeb(valor, mMotor);
			otraDistinta.mMotor=this.mMotor;
			
			String ruta = Util.rutaFisicaRelativaOAbsoluta(otraDistinta.nombredoc);			
			if (!Archivo.existeArchivo(ruta))
				throw new ErrorGotta("No existe el archivo '"+ruta+"'");
			
			usr.productosGenerados.put(""+otraDistinta.hashCode(), otraDistinta);
			return null;
			}
		else if (accion.equalsIgnoreCase("download")) {
			GottaWeb otraDistinta=new GottaWeb(valor, mMotor);
			usr.productosGenerados.put(""+otraDistinta.hashCode(), otraDistinta); 
			return null;
			}
		return accion;
		}

	private String zip(String valor) throws ErrorGotta {
		ArrayList<String> ar = Util.split(valor, ",");
		int count = ar.size();
		byte[] buf = new byte[1024];
		
		try {
			String outFilename = this.dirDocu()+mMotor.getUsuario().getIdSesion()+".zip";
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));
					
			for (int i=0; i<count; i++) {
				FileInputStream in = new FileInputStream(ar.get(i).toString());
				out.putNextEntry(new ZipEntry(ar.get(i).toString()));
						
				int len;
				while ((len = in.read(buf)) > 0) 
					out.write(buf, 0, len);
						
				out.closeEntry();
				in.close();
				}
			
			// Complete the ZIP file
			out.close();
			return mMotor.getUsuario().getIdSesion()+".zip";
			} 
		catch (IOException e) {
			throw new ErrorGotta(e.getMessage());
			}
		}


	@Override
	public void verifica(Motor motor, String accion, String nombre) throws ErrorGotta {
		verificaAccionValida(accion);
		this.mMotor=motor;
		this.usr=motor.usuario;		
	}

	public String traerArchivo(String ficheroOrigenBorrar) throws ErrorGotta {
		ArrayList<String> param = Util.split(ficheroOrigenBorrar,"¬");
		String nombre = param.get(0);
		String origen = param.get(1);
		
		if (origen.endsWith("\\") || origen.endsWith("/"))
			origen+=nombre;
		else 
			origen+="/"+nombre;
		
		boolean borrarDocumento = false;
		if (param.size()>2)
			borrarDocumento = param.get(2).equals("1");
		Archivo.copiaArchivo(mMotor.getApli(), origen, dirDocu()+nombre);
		if(borrarDocumento)
			borrarArchivo(origen);
		return Constantes.CERO;
	}
	public String borrarArchivo(String path){
		File file = new File(path);
		boolean resultado = file.delete();
		String s = "Borrado";
		if (!resultado)
			s = "No Borrado";
		return  s;	
	}
	public String llevarArchivo(String ficheroDestinoBorrar) throws ErrorGotta{
		ArrayList<String> par = Util.split(ficheroDestinoBorrar,"¬");
		if (par.size()>2)
			if(par.get(2).equals("1")){
				this.moverDocumento(ficheroDestinoBorrar);
				return Constantes.CERO;
			}
		String origen=par.get(0);
		String destino=par.get(1);
		
		Archivo.copiaArchivo(mMotor.getApli(), origen, destino);
		return Constantes.CERO;
	}
	public void cerrarTodo(FileInputStream fis, FileOutputStream fos) {
			try {
				if (fos!=null)fos.close();
				if (fis!=null) fis.close();
				}
			catch (IOException e) {
				/**/
			}
		}
	
	public String borrarDocumento(String fichero)  {
		String documentos;
		ArrayList<String>v = Util.split(fichero,Constantes.PIPE);
		String s = "Borrado";
		if(v.size()==2)
			documentos=v.get(0).toString();
		else
			documentos=dirDocu()+fichero;
		
		File file = new File(documentos);
		boolean resultado = file.delete();
		
		if (!resultado)
			s = "No Borrado";
		return s;
	}
	public String moverDocumento(String origendestino) throws ErrorGotta {
		//Movemos el documento desde DOC hasta la ruta especificada desde DOC.
		String s="Movido";
		if (origendestino.contains("¬")) {
			ArrayList<String> par=Util.split(origendestino,"¬");
			String origen=par.get(0);
			String destino=par.get(1);
			
			Archivo.copiaArchivo(mMotor.getApli(), origen, destino);
			borrarDocumento(origen);
		}			 		
		return  s;
	}

	public String replace(String cadena){
		return replace(cadena,null);
		}

	public String replace(String cadena, String separador){//cadena·cadena a sustituir·nueva cadena 
		if(separador==null)
			separador=Constantes.PUNTO3;
		ArrayList<String> cad=Util.splitTrim(cadena,separador,false);
		
		String cade=cad.get(0);
		String car=cad.get(1);
		String carSust=cad.get(2);
		cade=Util.replaceTodos(cade,car,carSust);
		return cade;
	}
//	public String tiffAPdf(String param3) throws ErrorAccionDLL, ErrorArrancandoAplicacion { 
//		try {
//			ArrayList<String> partes = Util.split(param3, ".");
//			String nombretif = partes.get(0);
//			String guardar = dirDocu() + nombretif + ".pdf";
//			String leer = dirDocu() + param3;
//	
//			com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4, 50, 50, 50, 50);
//			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(guardar));
//			int pages = 0;
//			document.open();
//			PdfContentByte cb = writer.getDirectContent();
//			com.lowagie.text.pdf.RandomAccessFileOrArray ra = null;
//			int comps = 0;
//	
//			ra = new com.lowagie.text.pdf.RandomAccessFileOrArray(leer);
//			comps = com.lowagie.text.pdf.codec.TiffImage.getNumberOfPages(ra);
//	
//			for (int c = 0; c < comps; ++c) {
//				Image img = com.lowagie.text.pdf.codec.TiffImage.getTiffImage(ra,c+1);
//				if (img != null) {
//					System.out.println("page " + (c + 1));
//					if (img.scaledWidth() > 500 || img.scaledHeight() > 700)
//						img.scaleToFit(500, 700);
//	
//					img.setAbsolutePosition(20, 20);
//					img.scalePercent(72f / 200f * 100);
//					img.scaleToFit(595, 842);
//					document.add(new com.lowagie.text.Paragraph(leer+" - page "+(c+1)));
//					cb.addImage(img);
//					document.newPage();
//					++pages;
//					}
//				}
//			ra.close();
//			document.close();
//			return nombretif + ".pdf";
//			}
//		catch (DocumentException e) {
//			throw new ErrorAccionDLL(e.getMessage());
//			}
//		catch (IOException e) {
//			throw new ErrorAccionDLL(e.getMessage());
//			}
//		}
	public String encriptar(String a,String p,String t){
		String temp = a+p;
		ArrayList<String> key = hacerArrayKey(temp);
		ArrayList<String> original = hacerArrayOriginal(t);
		return hacerPAN(original,key);
		}
		
	public String desencriptar(String a, String p, String t){
		String temp = a+p;
		ArrayList<String> key = hacerArrayKey(temp);
		ArrayList<String> original = hacerArrayOriginal(t);
		return deshacerPAN(original,key);
	}

	private String hacerPAN(ArrayList<String> o, ArrayList<String> k){
		ArrayList<String> saco = new ArrayList<String>();
		for (int i=0; i<k.size(); i++){
			String aux = k.get(i).toString();
			String item = o.get(new Integer(aux).intValue()-1).toString();
			o.remove(new Integer(aux).intValue()-1);
			saco.add(item);
		}
		o.addAll(saco);
		return hacerString(o);
	}
	
	private String deshacerPAN(ArrayList<String> o, ArrayList<String> k){
		ArrayList<String> a = new ArrayList<String>();
		ArrayList<String> b = new ArrayList<String>();
		int lenkey = k.size();
		int lena = o.size()-lenkey;
		for (int i=0; i<lena; i++){
			a.add(o.get(i));
		}
		for (int j = lena; j<o.size(); j++){
			b.add(o.get(j));
		}
		for (int l=b.size(); l>0; l--){
			String valor = b.get(l-1).toString();
			int indice = new Integer(k.get(l-1).toString()).intValue()-1;
			a.add(indice, valor);
		}
		return hacerString(a);
	}

	private String hacerString(ArrayList<String> a){
		String resultado = "";
		for (int n=0; n<a.size(); n++){
			resultado = resultado + a.get(n).toString();
		}
		return resultado;
	}

	private ArrayList<String> hacerArrayOriginal(String s){
		int len = s.length();
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i<len; i++)	{
			StringBuffer aux = new StringBuffer();
			aux.append(s.charAt(i));
			temp.add(aux.toString());
		}
		return temp;
	}
		
	private ArrayList<String> hacerArrayKey(String s){
		int len = s.length();
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i<len; i++){
			StringBuffer aux = new StringBuffer( s.charAt(i) );
			if (!temp.contains(aux))
				temp.add(aux.toString());
			}
		
		while(temp.contains(Constantes.CERO))
			temp.remove(Constantes.CERO);
			
		return temp;
		}
	
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta {
		//numprod= "29140907" --> //casiopea/minilic/repo/2008/0000000109_20080829100511_ai_pub_bocm_req.20060504095626.rtf
		Cifs CIFSorigen = Archivo.rutaCIFS(this.mMotor.getApli(), this.nombredoc);
		String ruta = Util.rutaFisicaRelativaOAbsoluta(this.nombredoc);
		Util.ponMime(res, ruta);
		String miArchivo=Util.nombreWeb(nombredoc);
		res.setHeader("Content-Disposition","attachment; filename="+Constantes.COMILLAS+miArchivo+Constantes.COMILLAS );
		
		this.mMotor.getUsuario().añadeMSG("Archivo \""+miArchivo+"\" servido al cliente", TipoMensajeGotta.archivos);
		InputStream fis;
		if(CIFSorigen!=null)
			fis=new SmbFile(CIFSorigen.rutaCIFS,CIFSorigen.auth).getInputStream();
		else
			fis = new FileInputStream(ruta);
		Archivo.descargaFichero(res, miArchivo, fis);
	}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}
}

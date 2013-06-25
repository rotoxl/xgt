package es.burke.gotta;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.burke.gotta.dll.IFirma;

public class ObtenerFichero extends HttpServlet {
	private static final long serialVersionUID = 2297243550914330716L;
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException {
		try {
			Jsp.setHeaders(res);
			Usuario usr=Usuario.desdeSesion(req);
			String nombreFich=null;
				
			String plantilla=Util.obtenValorOpcional(req, "plantilla");
			if (plantilla!=null){
				Archivo ar=usr.getMotor().tramActivo().tramiteInicial().archivosDescargables.get( Integer.parseInt(plantilla) );
				nombreFich=ar.rutaFisica;
				
				File fich = new File(nombreFich);
				Util.ponMime(res, nombreFich);
				res.setHeader("Content-Disposition", "attachment; filename="+Constantes.COMILLAS+fich.getName()+Constantes.COMILLAS);
				Archivo.descargaFichero(res, nombreFich);
				return;
				}
			else {
				String hash = req.getParameter("hash");
				String firmaFichero = req.getParameter("firma");
				int numerador=Integer.parseInt(req.getParameter("numerador"));
				
				if (firmaFichero==null){
					//Archivos antes de firmar
					Archivo ar = usr.getMotor().lote.tramActivo().getFirma().archivosFirmar.get(numerador);
					if(hash==null){
						nombreFich = ar.rutaFisica;
						if(ar.tipo.equals(Archivo.Tipo.PDF))
							res.setContentType("application/pdf");
						else{
							File fich = new File(nombreFich);
							Util.ponMime(res, nombreFich);
							res.setHeader("Content-Disposition", "attachment; filename="+Constantes.COMILLAS+fich.getName()+Constantes.COMILLAS);
							}
						}
					else{
						//Sacamos el hash
						nombreFich = ar.rutaFisica;
						IFirma firma = IFirma.getClassFirma(usr.getApli()); 
						String digest = firma.obtenerDigest(usr, nombreFich);
						res.setContentType("text/plain");
						res.setCharacterEncoding(Constantes.UTF8);
						BufferedOutputStream bos = new BufferedOutputStream(res.getOutputStream());
						bos.write(digest.getBytes(Constantes.UTF8));
						bos.close();
						return;
						}
					}
				else if(firmaFichero.equals("0")){
					//Datos de formularios
					Fila f = obtenerFilaBuena(usr.getMotor().lote.archivosFirmados,numerador);
					nombreFich = Util.rutaFisicaRelativaOAbsoluta(f.gets("ruta"));
					if(numerador==0){
						res.setContentType("text/plain");
						res.setCharacterEncoding(Constantes.UTF8);
						}
					else{
						Util.ponMime(res, nombreFich);
						File fich = new File(nombreFich);
						res.setHeader("Content-Disposition", "attachment; filename="+Constantes.COMILLAS+fich.getName()+Constantes.COMILLAS);
						}
					}
				else{
					//Firma
					Fila f = obtenerFilaBuena(usr.getMotor().lote.archivosFirmados, numerador);
					nombreFich = Util.rutaFisicaRelativaOAbsoluta(f.gets("rutaFirma"));
					Util.ponMime(res, nombreFich);
					File fich = new File(nombreFich);
					res.setHeader("Content-Disposition", "attachment; filename="+Constantes.COMILLAS+fich.getName()+Constantes.COMILLAS);
					}
				Archivo.descargaFichero(res, nombreFich);		
				} 
			}
		catch (Exception e) {
			throw new ServletException(e);
			}
		}

	private Fila obtenerFilaBuena(Filas filas, Integer numerador){
		for (Fila f:filas){
			if (f.gets("numerador").equals(numerador.toString()))
				return f;
			}
		throw new IndexOutOfBoundsException("No existe el producto "+numerador);
		}
}

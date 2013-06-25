package es.burke.gotta.dll;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Accion;
import es.burke.gotta.Archivo;
import es.burke.gotta.Constantes;
import es.burke.gotta.Constantes.ColisionFicheros;
import es.burke.gotta.Constantes.TipoMensajeGotta;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Motor;
import es.burke.gotta.Tramite;
import es.burke.gotta.Util;

public class WordCliente extends DLLGotta implements IDLLSubirFicheros{
	public Accion accion;
	public String plantilla;
	public String contadoc;
	private String ruta=null;
	public String docid=null;
	private String impresora=null;
	private String numCopias=null;
	
	public String getdocid(){
		return docid;
		}
	
    @Override
	public String accionesValidas(){
    	 return "AbrirDocumentoExcel GuardarDocumentoExcel "+
    	 		"ImprimiryCerrar EmitirDocumento CerrarDocumento ImprimirDocumento GuardarDocumento AbrirDocumento EmitiryGuardar EmitirImprimirCerrarInvisible";}

	@Override
	public Object ejecuta(Accion xaccion, Object xvalor) throws ErrorGotta {
		String claveDocu = ""+this.hashCode();
		
		String valor=Util.replaceTodos(xvalor.toString(), "¬", Constantes.PIPE);
		
		ArrayList<String> valores = Util.split(valor, Constantes.PIPE);
		WordCliente this2;
		if (usr.productosGenerados.containsKey(claveDocu)) {
			// si está dentro de un bucle no queremos reciclar esto
			this2=new WordCliente();
			claveDocu = ""+this2.hashCode();
			this2.usr=this.usr;
			this2.mMotor=this.mMotor;
			this2.síncrono=this.síncrono;
		}
		else
			this2=this;	
		
		this2.accion=xaccion;
		String strAccion=xaccion.accion;
		
		/*
		AbrirDocumentoExcel rutaDocumento (devuelve id_documento)
		GuardarDocumento
		
		EmitirImprimirCerrarInvisible 	nombrePlantilla|númeroDeCopias|impresora
		AbrirDocumento 					rutaDocumento
		EmitirDocumento 				nombrePlantilla, archivoAuxiliar1, archivoAuxiliar2
		GuardarDocumento 				ruta|ID_Documento
		CerrarDocumento 				ID_Documento
		ImprimirDocumento 				ID_Documento
		ImprimiryCerrar 				nombrePlantilla|númeroDeCopias|ID_Documento
		EmitiryGuardar 					nombrePlantilla|rutaDeGuardado
		*/
		if (strAccion.equalsIgnoreCase("EmitirImprimirCerrarInvisible")){
			this2.plantilla=compruebaRuta(valores.get(0));
			this2.numCopias=sacaSiExiste(valores, 1);
			this2.impresora=sacaSiExiste(valores, 2);
			}
		else if (strAccion.equalsIgnoreCase("AbrirDocumento") || strAccion.equalsIgnoreCase("AbrirDocumentoExcel")){
			this2.plantilla=compruebaRuta(valores.get(0));
			this2.docid=claveDocu;
			}
		else if (strAccion.equalsIgnoreCase("EmitirDocumento")){
			this2.plantilla=compruebaRuta(valores.get(0));
			this2.docid=claveDocu;
			}
		else if (Util.en(strAccion, "GuardarDocumento", "GuardarDocumentoExcel")){
			this2.plantilla=valores.get(0); //ruta donde se guarda el documento activo
			this2.docid=sacaSiExiste(valores, 1);
			}
		else if (strAccion.equalsIgnoreCase("CerrarDocumento")){
			this2.docid = sacaSiExiste(valores, 0);
			}
		else if (strAccion.equalsIgnoreCase("ImprimirDocumento")){
			this2.docid = sacaSiExiste(valores, 0);
			}
		else if (strAccion.equalsIgnoreCase("ImprimiryCerrar")){
			this2.plantilla=compruebaRuta(valores.get(0));
			this2.numCopias=sacaSiExiste(valores, 1);
			this2.docid=sacaSiExiste(valores, 2);
			}
		else if (strAccion.equalsIgnoreCase("EmitiryGuardar")){
			this2.plantilla=compruebaRuta(valores.get(0))+Constantes.PIPE+valores.get(1);
			this2.docid=claveDocu;
			}
		
		this2.contadoc=claveDocu;
		usr.productosGenerados.put(this2.contadoc, this2);
		if (Util.en(this.accion.accion, "GuardarDocumento", "GuardarDocumentoExcel")){
			accion.motor.lote.tramActivo().getListaFicheros().add(this2);
			this2.ruta=Util.rutaFisicaRelativaOAbsoluta(this.plantilla);
			}
		return this2.docid;
	}
	private String compruebaRuta(String rutaFich) throws ErrorAccionDLL {
		if (rutaFich.startsWith("http://") || rutaFich.startsWith("https://"))
			return rutaFich;
		
		String dotWeb =this.mMotor.getApli().getDatoConfig("dotWeb");
		
		//comprueba si la ruta está fuera del contexto, y, si es así, prepara el fichero para ser servido
		String rutaFisica=Util.rutaFisicaRelativaOAbsoluta(rutaFich, dotWeb);
		
		if (rutaFisica.startsWith(this.mMotor.getApli().getRutaFisicaAplicacion())){
			this.usr.añadeMSG("Preparando informe desde '"+rutaFich+"' (obtenido como ruta relativa para dotWeb='"+dotWeb+"')", TipoMensajeGotta.informe);
			//parece que está dentro del contexto
			return rutaFich;
			}
		else {//está fuera del contexto, hay que servirlo
			Archivo ar=new Archivo(this.usr, rutaFisica);
			ar.tipo=Archivo.Tipo.OTROS;
			
			if (!ar.existeArchivo())
				throw new ErrorAccionDLL("No se encuentra la plantilla '"+rutaFisica+"'");

			Tramite t=this.mMotor.tramActivo().tramiteInicial();
			t.archivosDescargables.add(ar);
			
			this.usr.añadeMSG("Preparando informe desde '"+rutaFisica+"' (obtenido como ruta absoluta para dotWeb='"+dotWeb+"')", TipoMensajeGotta.informe);
			return "./obtenerFichero?aplicacion="+mMotor.getApli().getCd()+"&plantilla="+(t.archivosDescargables.size()-1);
			}
		}
	
	private String sacaSiExiste(ArrayList<String> valores, int id){
		if (valores.size()>id)
			return valores.get(id);
		return null;
		}

	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta, ServletException {	
		WordCliente this2 = (WordCliente) usr.productosEnviados.get(numprod);
		RequestDispatcher rd = inf2.getServletContext().getRequestDispatcher("/AppletWord.jsp");
		req.setAttribute("accion", this2.accion);
		req.setAttribute("plantilla", this2.plantilla);
		req.setAttribute("contadoc", this2.contadoc);
		req.setAttribute("docid", this2.docid);
		req.setAttribute("impresora", this2.impresora);
		req.setAttribute("copias", this2.numCopias);
		rd.forward(req, res);
		}
	
     @Override
	public void verifica(Motor motor, String xaccion, String nombre) throws ErrorGotta 	{
		verificaAccionValida(xaccion);
		this.usr=motor.usuario;
		this.mMotor=motor;
		this.síncrono=true;
	}
	public ColisionFicheros getColision() {
		return ColisionFicheros.sobreEscribir;
	}
	public String getRetornoNombre() {
		return "@word_nombre";
	}
	public String getRetornoRuta() {
		return "@word_ruta";
	}
	public String getRuta() {
		return this.ruta;
	}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}

}

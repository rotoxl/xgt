package es.burke.gotta.dll;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRSubreport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseFrame;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.query.JRQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.jasperforge.jaspersoft.demo.PlSqlQueryExecuterFactory;

import es.burke.gotta.Accion;
import es.burke.gotta.Aplicacion;
import es.burke.gotta.Archivo;
import es.burke.gotta.Coleccion;
import es.burke.gotta.Conexion;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.FechaGotta;
import es.burke.gotta.Motor;
import es.burke.gotta.PoolAplicaciones;
import es.burke.gotta.Util;
import es.burke.gotta.Variable;
import es.burke.gotta.Aplicacion.DIC_Configuracion;
import es.burke.gotta.Constantes.TipoMensajeGotta;
public class InformeJasper extends DLLGotta {
	final String defaultPDFFont = "Arial";
	{
	 JRProperties.setProperty(JRQueryExecuterFactory.QUERY_EXECUTER_FACTORY_PREFIX+"PLSQL",
			 PlSqlQueryExecuterFactory.class.getCanonicalName());
	 
	 JRProperties.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
	 JRProperties.setProperty("net.sf.jasperreports.default.font.name", defaultPDFFont);
	 
	}
	JasperReport jasperReport;
	String exportoA = "pdf";
	Map<String,Object> parameters = new HashMap<String, Object>();
	String nombre;
	private String rutaVerificada; //-->se rellena al compilar el informe
	private String rptWeb;
	private String rutaGuardar=null;
	Aplicacion apli;

	@Override
	public Object ejecuta(Accion accion, Object xvalor) throws ErrorGotta {
		String acc=accion.accion;
		String nombreInforme;
		
		if (acc.equalsIgnoreCase("EmitirGuardar")||acc.equalsIgnoreCase("Guardar")){
			ArrayList<String> temp=Util.split(xvalor.toString(), Constantes.PIPE);
			if(temp.size()<2)
				throw new ErrorAccionDLL("Para guardar el informe, debe indicar rutaPlantilla|rutaFichero");
			nombreInforme=temp.get(0);
			this.rutaGuardar  =temp.get(1);
			}
		else
			nombreInforme=xvalor.toString();
		
		
		try {	
			apli.println (" **** preparando informe "+nombreInforme);
			if ( nombreInforme.toLowerCase().endsWith(".dot") )
				exportoA="rtf";
			else if ( nombreInforme.toLowerCase().endsWith(".odt") )
				exportoA="odt";
			else if ( nombreInforme.toLowerCase().endsWith(".xls") )
				exportoA="xls";
			else
				exportoA="pdf";
			this.rutaVerificada = verificaNombre(nombreInforme);
			
			apli.println(" --> "+this.rutaVerificada);
			// First, load JasperDesign from XML and compile it into JasperReport
			this.cargaInforme();
			
			JRParameter[] jrparams = jasperReport.getParameters();
			
			String[] letrasVálidas= {"p","q","r","s","t","u","v","w","x","y","z"};
			
			// Second, create a map of parameters to pass to the report.
			ArrayList<String> ordenParam=new ArrayList<String>();
			boolean porNombre=true;
			
			
			if ( mMotor.lote.variables.containsKey("o") ) { //nombres de param del jasper en orden: capa, ejercicio
				String o=Util.toString(mMotor.lote.getVariable("o").getValor());
				if (o.contains(",")){
					for (String s : Util.split(o.toString(),",") )
						ordenParam.add( s.toLowerCase().trim() );
					
					porNombre=false;
					}
				}
						
			int i=0; Object valor=null;
			for (JRParameter jp : jrparams) {
				if (! jp.isSystemDefined() && !jp.getValueClassName().equals(java.sql.ResultSet.class.getCanonicalName()))  { 
					String nombreRealParam = jp.getName();
					String nombreParamMinus=nombreRealParam.toLowerCase();
					if (nombreParamMinus.startsWith("subreport_rutaimg"))
			    		parameters.put( nombreRealParam,  Util.rutaFisicaRelativaOAbsoluta(mMotor.getApli().getDatoConfig("icoWeb") ) );
					else if (nombreParamMinus.startsWith("subreport_ruta"))
			    		parameters.put( nombreRealParam, dirRutaVerificada());
			    	else {
					if (acc.equalsIgnoreCase("EmitirDocumentoParamsDefecto")) {
			    			String expresion=jp.getDefaultValueExpression().getText();
			    			valor = evaluacionMuyCutre(expresion, jp.getValueClassName());
			    			}
			    		else {
				    		Variable v=null;
				    		
				    		if (porNombre && Util.en(nombreParamMinus,  letrasVálidas))
					    		v=mMotor.lote.getVariable(nombreParamMinus);		    			
				    		else {
				    			int pos;
				    			if (ordenParam.size()>0)
				    				pos=ordenParam.indexOf(nombreParamMinus);
				    			else
				    				pos=i;
				    			
							v=mMotor.lote.getVariable(nombreParamMinus);
				    			if (v!=null){
					    			//pass	
				    				}
				    			else if (pos<letrasVálidas.length){
				    				String letra=letrasVálidas[pos];
				    				v = mMotor.lote.getVariable(letra);	
				    				}
				    			}
				    		i++;
				    		
				    		try
								{ valor = v.getValor(); }
				    		catch (NullPointerException e)
				    			{ throw new ErrorGenerandoProducto("Error al generar el informe '"+nombreInforme+"': no existe la variable '"+nombreParamMinus+"'."); }
				    		
							//le metemos un poco de vista para pasar las fechas como en Gotta_Crystal (tipo double)
							//		o como fechas de verdad
				    		if (valor==null){
				    			//pass
				    			} 
				    		else {
								String clase = jp.getValueClassName().toLowerCase();
								if (valor instanceof FechaGotta) {
									if (clase.endsWith("timestamp"))				    				
										valor = new java.sql.Timestamp(((FechaGotta)valor).getTimeInMillis());
									else if (clase.endsWith("date"))
										valor=new java.util.Date( ( (FechaGotta)valor ).getTimeInMillis() );
									else if (clase.endsWith("float"))
										valor= new Float( ((FechaGotta)valor).fechaCrystal());
									else
										valor=valor.toString();
									}
								else if (clase.endsWith("integer") && !(valor instanceof Integer)){
									if (valor instanceof Double){
										Double dblValor=(Double)valor;
										valor=dblValor.intValue();
										}
									else
										valor  = Integer.parseInt(Util.desformatearNumero( valor.toString( )));
										}	
								else if (clase.endsWith("float") && !(valor instanceof Float)) {
									if (valor instanceof Double){
										Double dblValor=(Double)valor;
										valor=dblValor.floatValue();
										}
									else
										valor  = Float.parseFloat(valor.toString());
									}
				    			}
			    			}
			    		parameters.put(nombreRealParam, valor);
			    		apli.println(valor+" => "+nombreRealParam);
			    		}
		    		}
				}
			if(acc.equalsIgnoreCase("Guardar"))
				try {
					this.sacaProducto(null, null, null, null, null);
				} catch (IOException e) {
					throw new ErrorGenerandoProducto(e);
				}
			else
				usr.productosGenerados.put(""+this.hashCode(), this);
		}
		catch (JRException e)
			{throw new ErrorGenerandoProducto(e.getMessage());}
		return null;
	}
//	private String apañaRuta(String ruta) {
//		ruta=Util.replaceTodos(ruta, "\\", "/");
//		ruta=Util.replaceTodos(ruta, "//", "/");
//		return ruta;
//	}
	private Object evaluacionMuyCutre(String expresion, String tipo) {
		tipo=tipo.toLowerCase();
		
		String pref; 
		if (tipo.endsWith("string")){
			pref="new String(\""; //OJO este es un poco distinto porque lleva 2 comillas
			if (expresion.startsWith(pref)) 
				expresion= expresion.substring(pref.length(), expresion.length()-1-1);
			return expresion;
			}
		else if (tipo.endsWith("integer")) {
			pref="new Integer(";
			if (expresion.startsWith(pref)) 
				expresion= expresion.substring(pref.length(), expresion.length()-1);
			return new Integer(expresion);	
			}
		else if (tipo.endsWith("double")){
			pref="new Double(";
			if (expresion.startsWith(pref)) 
				expresion= expresion.substring(pref.length(), expresion.length()-1);
			return new Double(expresion);
			}
		else if (tipo.endsWith("float")){
			pref="new float(";
			if (expresion.startsWith(pref)) 
				expresion= expresion.substring(pref.length(), expresion.length()-1);
			return new Float(expresion);
			}
		else
			return expresion;
			
	}
	private String dirRutaVerificada() {
		return new File(this.rutaVerificada).getParent()+File.separator;
		}

	@Override
	public void verifica(Motor motor, String accion, String nombreInforme) throws ErrorGotta {
		this.mMotor=motor;
		this.usr=motor.usuario;
		this.apli = motor.getApli();
		this.rptWeb=apli.getDatoConfig(DIC_Configuracion.rptWeb);
		if (rptWeb==null)
			throw new ErrorAccionDLL("No se ha indicado la ruta del directorio de informes 'rptWeb' en DIC_Configuracion");
		verificaAccionValida(accion);
	}

	private String verificaNombre(String valorParam)throws ErrorGotta{
		this.nombre=valorParam;
		this.rptWeb=apli.getDatoConfig(DIC_Configuracion.rptWeb);
		
//		if (new File("/tmp/").canRead())
//			rptWeb=Util.replaceUno(rptWeb, "g:/proyectos", PoolAplicaciones.dirInstalacion+"Aplicaciones");

		try 
			{
			String ruta=averiguaRuta(rptWeb);
			return compilaInforme(ruta);
			} 
		catch (ErrorGenerandoProducto e) 
			{throw e;} 
		catch (JRException e) 
			{throw new ErrorGenerandoProducto(e.getMessage());}
		}
	
	/*en nombre viene 'L005.rpt' o 'L005.jrxml' o 'L005.xml' o 'L005.jasper' */
	private String compilaInforme(String pruta) throws JRException {	
		String extension=pruta.substring(pruta.toLowerCase().lastIndexOf("."));
		String ruta = pruta.substring(0,pruta.length() - extension.length());
		
		//compilamos
		if (! extension.equals(".jasper") ) {
			apli.println("-------Compilando informe "+ruta+extension+"...");
			JasperDesign jasperDesign=JRXmlLoader.load(ruta+extension);
			/*
			String sc=jasperDesign.getScriptletClass();
			if (sc!=null && sc!=Constantes.CAD_VACIA){
				String path = new File(ruta+extension).getParentFile().getAbsolutePath();
				path+=File.separator;
				System.setProperty("jasper.reports.compile.class.path", application.getRealPath("/WEB-INF/lib/jasperreports-1.2.3.jar")
						 + System.getProperty("path.separator") + application.getRealPath("/WEB-INF/classes/"));


				System.setProperty("jasper.reports.compile.class.path",Systempath);
			}
			*/
			JasperCompileManager.compileReportToFile(jasperDesign, ruta+".jasper");
			}
		
		return ruta+".jasper";
		}
	
	private String averiguaRuta(String directorioRptWeb) throws ErrorGenerandoProducto {

		int punto;
		
		if (nombre.contains(Constantes.PUNTO)){
			if ((punto=nombre.toLowerCase().lastIndexOf("."))>0) {
				String extension=nombre.substring(punto);
				if (! Util.en(extension, ".xls", ".rpt", ".jrxml", ".xml", ".jasper", ".dot", ".odt"))
					throw new ErrorGenerandoProducto("La extensión '"+extension+"' del informe no es válida." );
				nombre=nombre.substring(0, nombre.length()-extension.length());
				}
			}
		//buscamos el archivo, si hay .jasper (ya compilado) nos quedamos con él y 
		//		así nos saltamos el paso de la compilación
		String rutaRelativa=PoolAplicaciones.dirInstalacion+ directorioRptWeb;
		String rutaAbsoluta=directorioRptWeb;
		
		ArrayList<String> rutasPosibles= new ArrayList<String>();
		rutasPosibles.add(rutaRelativa+nombre+".jasper");	rutasPosibles.add(rutaAbsoluta+nombre+".jasper");
		rutasPosibles.add(rutaRelativa+nombre+".xml"); 		rutasPosibles.add(rutaAbsoluta+nombre+".xml");
		rutasPosibles.add(rutaRelativa+nombre+".jrxml");	rutasPosibles.add(rutaAbsoluta+nombre+".jrxml");
		
		for (String r : rutasPosibles) {
			String nombreOK=sacaNombreArchivo(r);
			if ( nombreOK != null ) {
				try {
					return new File(nombreOK).getCanonicalPath();} 
				catch (IOException e) {
					this.mMotor.sacaError(e.getStackTrace());
					}
				}
			}
		throw new ErrorGenerandoProducto("No se encuentra el informe:"+nombre); 
	}

	/*saca el nombre del archivo con las mayúsculas y minúsculas correctas*/
	private String sacaNombreArchivo(String nr) {
		String barra="/";//$NON-NLS-1$
		String r=Util.replaceTodos(nr, File.separator, barra);
		r=Util.replaceTodos(r, barra+barra, barra);
		if (new File(r).exists())
			return r;
		int ultimaBarra = r.lastIndexOf(barra);
		String dirName = r.substring(0, ultimaBarra);
		String archivo=r.substring(ultimaBarra+1);
		String patrón="*.*";
		File f = new File(dirName);
		String[] theFiles = f.list(new PatternFilter(patrón, archivo));
		if (theFiles != null && theFiles.length>0)
			{return dirName+barra+theFiles[0];}
		return null;
		}
		
	private void cargaInforme() throws JRException {
		jasperReport=(JasperReport)JRLoader.loadObject(this.rutaVerificada);
		final String constante = "$P{subreport_ruta}";
		for (JRSubreport sub:subinformesInforme()){
			JRExpression ex = sub.getExpression();
			if (ex==null)
				continue;			
			String valorExp = ex.getText();

			if (!valorExp.contains(constante))
				throw new JRException("El informe "+this.rutaVerificada+" contiene subinformes cuya ruta no ha sido indicada por completo, sólo se ha indicado el nombre. Hay que añadirla usando la variable de Jasper $P{subreport_ruta} (que deberá estar en el informe padre) e indicar la ruta del subinforme como $P{subreport_ruta}="+valorExp);
			
			valorExp=Util.replaceTodos(valorExp, constante, "\""+dirRutaVerificada());
			ArrayList<String> trozos = Util.split(valorExp, "\"");
			
			String nombreSub=valorExp;
			nombreSub=Util.replaceUltimo(/*dirRutaVerificada()+*/trozos.get(1), ".jasper","");

			try
				{JRLoader.loadObject(nombreSub+".jasper");}
			catch(Exception e)
				{
				apli.println("-------Compilando subinforme "+nombreSub+"...");
				if (new File(nombreSub+".jrxml").canRead())
					JasperCompileManager.compileReportToFile(nombreSub+ ".jrxml");
				else if (new File(nombreSub+".xml").canRead())
					JasperCompileManager.compileReportToFile(nombreSub+ ".xml");
				}
		}
	}

	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws ErrorGenerandoProducto, IOException, ErrorGotta {
		Conexion con = usr.getConexion();
		Connection conex = con.obtenerConexionJR();
		String nombreFicheroSalida=null;
		File rutaDest=null;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// Fourth, create JasperPrint using fillReport() method
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conex);
			if( jasperPrint.getPages().size()==0 || ((JRPrintPage)jasperPrint.getPages().get(0)).getElements().size()==0) {
				JREmptyDataSource conexVacía= new JREmptyDataSource();
				jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conexVacía);
				}
			
			JRAbstractExporter exporter;
			if (this.exportoA.equalsIgnoreCase("rtf") )
				exporter = new JRRtfExporter();
			else if (this.exportoA.equalsIgnoreCase("xls") )
				exporter = new JRXlsExporter();
			else if (this.exportoA.equalsIgnoreCase("odt") )
				exporter = new JROdtExporter();
			else
				exporter = new JRPdfExporter();
 
			////////////////////////
			nombreFicheroSalida=this.nombre;
			if (nombreFicheroSalida.contains("."))
				nombreFicheroSalida=nombreFicheroSalida.substring(0,nombreFicheroSalida.indexOf("."));
			nombreFicheroSalida+="."+this.exportoA;
			
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			
			if (this.rutaGuardar==null){//directamente en memoria
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
				}
			else{//creamos el archivo vacío
				rutaGuardar=Util.rutaFisicaRelativaOAbsoluta(rutaGuardar);
				Archivo.generaEstructuraDirectorios(rutaGuardar);
				rutaDest=new File(rutaGuardar);
				if (rutaDest.isDirectory())
					rutaDest=new File(rutaGuardar+"/"+nombreFicheroSalida);
				
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, rutaDest.getAbsolutePath());
				}

			//Todo ok, pasamos a servir el pdf
			exporter.exportReport();
			usr.añadeMSG("Informe "+this.nombre+" ("+this.rutaVerificada+") con los params "+Util.toString(this.parameters)+" emitido con éxito", TipoMensajeGotta.info);
			}
		catch (JRException e) {
			String msg="Error generando el informe "+this.nombre+" ("+this.rutaVerificada+") con los params "+Util.toString(this.parameters)+": "+e.getMessage()+(e.getCause()!=null?" ("+e.getCause().getMessage()+")":"") + ": " +e.getStackTrace(); 
		 	usr.añadeMSG(msg , TipoMensajeGotta.alerta); 
		 	throw new ErrorGenerandoProducto(msg); 
			 }
		finally {
			con.liberarConexionJR(conex);
			}
		if(res==null)
			return;
		if (this.exportoA.equalsIgnoreCase("pdf"))
			res.setContentType("application/pdf");
		else if (this.exportoA.equalsIgnoreCase("rtf") )
			res.setContentType("application/rtf");
		else if (this.exportoA.equalsIgnoreCase("odt") )
			res.setContentType("application/vnd.oasis.opendocument.text");
		
		
		if (this.rutaGuardar==null){
			res.setHeader("Content-Disposition","attachment; filename="+Constantes.COMILLAS+nombreFicheroSalida +Constantes.COMILLAS);
			ServletOutputStream salida = res.getOutputStream();
			baos.writeTo(salida);
			salida.flush();
			baos.close();
			}
		else 
			Archivo.descargaFichero(res, rutaDest);
	}

	@Override
	public String accionesValidas() 
		{return "EmitirDocumento EmitirDocumentoParamsDefecto EmitirGuardar Guardar";}

	private ArrayList<JRSubreport> subinformesInforme(){
		ArrayList<JRSubreport> ret= new ArrayList<JRSubreport>();
		analiza(bandasInforme(), ret);
		return ret;
		}
	private void analiza(ArrayList<Object> lista, ArrayList<JRSubreport> ret){
		for (Object b:lista){
			if (b==null)
				{/*pass*/}
			else if (b instanceof JRSubreport)
				ret.add( (JRSubreport) b );
			else if (b instanceof JRElementGroup){
				for (JRElement ch: ((JRElementGroup)b).getElements()){
					
					if (ch instanceof JRSubreport)
						ret.add( (JRSubreport) ch );
					else if (ch instanceof JRBaseFrame)
						analiza( _hazArray( ( (JRElementGroup)ch ).getElements()), ret );
					else if (ch instanceof JRElementGroup)
						analiza( _hazArray( ( (JRElementGroup)ch ).getElements()), ret );
					}
				}	
			}
		}
	private ArrayList<Object> _hazArray(JRElement[] lista)
		{
		ArrayList<Object> retorno = new ArrayList<Object>();
		for (JRElement o :  lista)
			{
			if (o instanceof JRElementGroup)
				retorno.add(o);
			else if  (o instanceof JRSubreport)
				retorno.add(o);
			}
		return retorno;
		}
	
	private ArrayList<Object> bandasInforme() {
		ArrayList<Object> ret=new ArrayList<Object>();
		ret.add( this.jasperReport.getBackground());
		ret.add( this.jasperReport.getColumnFooter());
		ret.add( this.jasperReport.getColumnHeader());
		ret.add( this.jasperReport.getDetailSection());
		ret.add( this.jasperReport.getLastPageFooter());
		ret.add( this.jasperReport.getPageFooter());
		ret.add( this.jasperReport.getColumnFooter());
		ret.add( this.jasperReport.getPageHeader());
		ret.add( this.jasperReport.getSummary());
		ret.add( this.jasperReport.getTitle());
		return ret;
	}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}
///////////////////	
	public Coleccion<String> sacaListaParametros(Aplicacion apli, String nombreInforme) throws ErrorGotta {
		this.apli=apli;
		this.rutaVerificada = verificaNombre(nombreInforme);
	
		try {
			this.cargaInforme();
			} 
		catch (JRException e) {
			throw new ErrorAccionDLL(e.getMessage());
			}
		
		JRParameter[] jrparams = jasperReport.getParameters();
		Coleccion<String>ret=new Coleccion<String>();
		for (JRParameter jrp: jrparams){
			String nombreParam=jrp.getName();
			String nombreParamMinus=nombreParam.toLowerCase();
			if (nombreParamMinus.startsWith("subreport_rutaimg") || nombreParamMinus.startsWith("subreport_ruta")){
				}
			else if (!jrp.isSystemDefined())
				ret.put(nombreParam, jrp.getValueClassName());
			}
		return ret;
		}
}
class PatternFilter implements FilenameFilter {
	//	String[] patrones={".jrxml", ".jasper", ".xml"}; 
	String pattern;
	String nombreArchivo;
	
	public PatternFilter (String thePattern, String elNombre){
		pattern = thePattern.toLowerCase();
		nombreArchivo=elNombre;
		}
	public boolean accept(File dir, String name){
		return nombreArchivo.equalsIgnoreCase(name);
		}
}

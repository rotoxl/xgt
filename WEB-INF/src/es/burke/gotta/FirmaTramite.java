package es.burke.gotta;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import es.burke.gotta.Archivo.Tipo;
import es.burke.gotta.Constantes.TipoMensajeGotta;
import es.burke.gotta.dll.ErrorFirmaNoValida;
import es.burke.gotta.dll.IFirma;

public class FirmaTramite {
	IFirma firma;
	
	Tramite tramite;
	Lote lote;
	Motor motor;
	ITabla _tb, _tt, _tf;
	
	public String javaScriptFirmaElectronica;//Javascript de invocación del componente que firma
	public String rutaFirmaElectronica=null; //plantilla de la ruta donde se guardarán los XML y PDF de los elementos firmados
	
	static final String FIRMA_ELECTRÓNICA_ACTIVA = "@firmaElectrónicaActiva";
	static final String FIRMA_ELECTRÓNICA_ACTIVABLE = "@firmaElectrónicaActivable";

	public ArrayList<Archivo> archivosFirmar=new ArrayList<Archivo>();;
	
	private DatoFirmado etiquetaAdjuntosFirma=null;
	private Coleccion<DatoFirmado> datosFirmaElectronica=new Coleccion<DatoFirmado>();
	
	public ArrayList<String> idFirmasIncluidas=null;
	public FirmaTramite(Tramite tramite){
		this.tramite=tramite.tramiteInicial();
		this.lote=tramite.lote;
		this.motor=tramite.motor;
		
		this._tb=this.tramite.getTb();
		this._tt=this.tramite.getTt();
		this._tf=this.tramite.getTf();
		
		this.javaScriptFirmaElectronica="{}";
		
		this.firma=IFirma.getClassFirma(this.motor.getApli());
		}
	
	/*proceso inverso a la generación del xml: desde el archivo de firma se obtienen datos, adjuntos, etc para verificar la firma*/
	public FirmaTramite(Usuario usr, Archivo ar) throws ErrorFirmaNoValida {
        try {
        	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(ar.rutaFisica));

            doc.getDocumentElement().normalize(); //normalize text representation
			
            //datos
            NodeList datos = doc.getElementsByTagName("dato");
            String tipo, valor, dato;
            for(int s=0; s<datos.getLength() ; s++){
                Node nodo=datos.item(s);
                if (nodo.getNodeType() == Node.ELEMENT_NODE){
                	NamedNodeMap n=nodo.getAttributes();

                	tipo=n.getNamedItem("tipo").getNodeValue();
                	valor=n.getNamedItem("valor").getNodeValue();
                	dato=n.getNamedItem("texto").getNodeValue();
                	
                	this.añadeCosasAFirma(TipoDato.valueOf(tipo), dato, valor);
                	}
                }

            //adjuntos
            NodeList archivos = doc.getElementsByTagName("archivo");
            String ruta, hash;
            for(int s=0; s<archivos.getLength() ; s++){
                Node nodo= archivos.item(s);
                if (nodo.getNodeType() == Node.ELEMENT_NODE){
                	NamedNodeMap n=nodo.getAttributes();

                	ruta=n.getNamedItem("ruta").getNodeValue();
                	hash=n.getNamedItem("hash").getNodeValue();
                	
                	this.archivosFirmar.add( new Archivo(usr, ruta, Util.creaDic("hash", hash)) );
                	}
                }

            //adjuntos
            NodeList firmasIncluidas= doc.getElementsByTagName("ds:Signature");
            String id;
            this.idFirmasIncluidas=new ArrayList<String>();
            for(int s=0; s<firmasIncluidas.getLength() ; s++){
                Node nodo= firmasIncluidas.item(s);
                if (nodo.getNodeType() == Node.ELEMENT_NODE){
                	NamedNodeMap n=nodo.getAttributes();

                	id=n.getNamedItem("Id").getNodeValue();
                	
                	this.idFirmasIncluidas.add(id);
                	}
                }

			} 
        catch (ParserConfigurationException e) {
			throw new ErrorFirmaNoValida("Error recuperando datos del archivo de firma: "+e.getMessage());
			} 
		catch (SAXException e) {
			throw new ErrorFirmaNoValida("Error recuperando datos del archivo de firma: "+e.getMessage());
			} 
		catch (IOException e) {
			throw new ErrorFirmaNoValida("Error recuperando datos del archivo de firma: "+e.getMessage());
			}
	}
	
	public Boolean getFirmaElectrónicaActiva(){
		if (this.motor.getApli().getDatoConfig("firmasWeb")==null)
			return false;
		return (Boolean)this.lote.getVariable(FIRMA_ELECTRÓNICA_ACTIVA).getValor();
		}
	public void setFirmaElectrónicaActiva(Boolean valor) throws ErrorArrancandoAplicacion{
		if (!this.getFirmaElectrónicaActivable())
			valor=false;
		this.lote.setVariable(FIRMA_ELECTRÓNICA_ACTIVA, Constantes.BOOLEAN,valor);
		}
	public Boolean getFirmaElectrónicaActivable(){
		if (this.motor.getApli().getDatoConfig("firmasWeb")==null)
			return false;
		return (Boolean)this.lote.getVariable(FIRMA_ELECTRÓNICA_ACTIVABLE).getValor();
		}
	public void setFirmaElectrónicaActivable(Boolean valor) throws ErrorArrancandoAplicacion{
		this.lote.setVariable(FIRMA_ELECTRÓNICA_ACTIVABLE, Constantes.BOOLEAN,valor);
		this.setFirmaElectrónicaActiva(valor);
		}
	void activaFirmaSiProcede(Long camino1, String tramite1, Aplicacion apli) throws ErrorGotta {
		/* no hay que mirarlo para SUB/FOR/EJE */
		String firmasWeb = apli.getDatoConfig("firmasWeb");
		
		if (firmasWeb!=null)
			this.setFirmaElectrónicaActivable(false);
		
		if (apli.niveles.containsKey("APP_Tramites") ){
			 try {
				Filas filas=tramite.APP_Tramites(camino1, tramite1, apli);
				if (filas.size()==1){
					Fila fila=filas.get(0);
					// Hacemos este magreo para que llegue un objeto correcto.
					String jsFirma=Util.toString(fila.get("JavascriptFirma"), Constantes.objetoJSVacio);
					if (jsFirma.equals(Constantes.CAD_VACIA))
						jsFirma=Constantes.objetoJSVacio;
					if (!jsFirma.equals(Constantes.objetoJSVacio)){
						JSONObject ss = new JSONObject(jsFirma);
						this.javaScriptFirmaElectronica=ss.toString();
						}
					
					this.rutaFirmaElectronica="'"+firmasWeb+"'+"+fila.gets("ruta");
					if (firmasWeb!=null && !jsFirma.equals(Constantes.objetoJSVacio)){
						this.setFirmaElectrónicaActivable(true);
						this.añadeNombreTrámite(null);
						if (_tf == null)
							throw new ErrorDiccionario("Se ha especificado que este trámite debe firmarse, pero en TRA_Caminos no se ha rellenado la columna TablaFirmas"); 
						}
					}
			 	}
			catch (JSONException e) {
				throw new ErrorGotta("Al ejecutar APP_Tramites: ",e);
				}
			}
			
	}
//////////////////////////////////////////	
	public JSONObject JSONfirmaElectronica() throws JSONException, ErrorGotta{
		JSONObject ret = new JSONObject();
		ret.put("idx", this.tramite.idxDepuracion);
		ret.put("tramite", this.tramite.tramite);
		ret.put("camino", this.tramite.camino);
		ret.put("jsFirma", this.javaScriptFirmaElectronica) ;
		
		JSONArray datosJSON=this.jsonDatosFirmaElectrónica();
		if (this.getFirmaElectrónicaActivable()){
			ret.put("datos", datosJSON);
			
			ret.put("xmldatos", this.xmlDatosFirmaElectrónica());
			ret.put("jsondatos", datosJSON.toString(4));
			}
		else if (this.archivosFirmar.size()==1){
			Archivo ar=this.archivosFirmar.get(0);
			ret.put("xmldatos", ar.leeArchivoTexto());
			}
		IFirma firma = IFirma.getClassFirma(this.motor.getApli());
		ret.put("productos",firma.JSONlistaObj(this.archivosFirmar)) ;
		return ret;
	}
	public String xmlDatosFirmaElectrónica() throws ErrorGotta{
//		http://java.sun.com/developer/technicalArticles/WebServices/jaxb/#crtree
//		generación de xml: http://java.sun.com/developer/technicalArticles/WebServices/jaxb/UsingJAXPTest2.java
//		lectura de xml: http://java.sun.com/developer/technicalArticles/WebServices/jaxb/UsingJAXBTest3.java
		try {
			DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
	        documentBuilderFactory.setValidating(true);
	        documentBuilderFactory.setNamespaceAware(true);
	        documentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage","http://www.w3.org/2001/XMLSchema");
	        DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
	        
	        Document document = documentBuilder.newDocument();
	        
	        Element firma= document.createElement("firma");
			document.appendChild(firma);
	        
			Element datos= document.createElement("datos");
			firma.appendChild(datos);
	        
			for (String s:this.datosFirmaElectronica.getOrden()){
				DatoFirmado dato = this.datosFirmaElectronica.get(s);
				
				Element datoFirmado=document.createElement("dato");
				datos.appendChild(datoFirmado);
				
				datoFirmado.setAttribute("tipo", Util.noNulo(dato.getStrTipo()));
				datoFirmado.setAttribute("texto", Util.noNulo(dato.getTexto()));
				datoFirmado.setAttribute("valor", Util.noNulo(dato.getValor()));	
				}
			
	        Element archivos=document.createElement("archivos");
	        firma.appendChild(archivos);
			
			for (Archivo ar:this.archivosFirmar){
				Element archivo=document.createElement("archivo");
				archivos.appendChild(archivo);
				
				archivo.setAttribute("ruta", ar.rutaFisica);
				archivo.setAttribute("hash", ar.obtenerDigest(this.motor.usuario));
				}
			
			DOMSource domSource = new DOMSource(document);
			StringWriter outWriter= new StringWriter();  
			StreamResult streamResult = new StreamResult( outWriter );  
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
//			tFactory.setAttribute("indent-number", 4);
			
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT , "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, Constantes.UTF8);
			transformer.transform(domSource, streamResult);
					
			return streamResult.getWriter().toString();
			}
		catch (Exception e){
			e.printStackTrace();
			throw new ErrorGotta("Error al generar el xml de los datos a firmar:" + e.getMessage());
			}
	}
	
	JSONArray jsonDatosFirmaElectrónica() throws JSONException{
		JSONArray ret = new JSONArray();
		for (String s:this.datosFirmaElectronica.getOrden()){
			DatoFirmado dato = this.datosFirmaElectronica.get(s);
			ret.put(new JSONObject(dato));
			}
		if (this.etiquetaAdjuntosFirma!=null){
			ret.put( new JSONObject(this.etiquetaAdjuntosFirma) ) ;
			}
		return ret;
	}
/////////////////////////////	
	void añadeDato(String literal, ArrayList<Object> valor, String ds){
		String sValor="["+Util.join(" - ", valor)+"] "+ds;
		this.añadeDato(literal, sValor);
		}
	public void añadeDato(String literal, Object valor){
		this.añadeCosasAFirma(TipoDato.Dato, literal, valor);
		}
	public void añadeSeparador(String literal){
		this.añadeCosasAFirma(TipoDato.Separador, literal, null);
	}
	public void añadeTítulo(String literal){
		this.añadeCosasAFirma(TipoDato.TituloFormulario, literal, null);
	}
	public void añadeNombreTrámite(String literal){
		this.añadeCosasAFirma(TipoDato.NombreTramite, literal, null);
	}
	public void añadeEtiquetaFirmaArchivos(String literal){
		DatoFirmado df=new DatoFirmado();
		df.setTexto(literal);
		df.setTipo(TipoDato.Separador);
		this.etiquetaAdjuntosFirma=df;
	}
	private void añadeCosasAFirma(TipoDato td, String literal, Object valor){
		if (valor==null)
			valor="";
		
		DatoFirmado df=new DatoFirmado();
		df.setTipo(td);
		df.setValor(Util.toString(valor));
		df.setTexto(literal);
		
		Coleccion<DatoFirmado> datosFirmaElectronica2 = this.datosFirmaElectronica;
		int num;
		if (td==TipoDato.NombreTramite)
			num=0;
		else
			num=datosFirmaElectronica2.size();
		datosFirmaElectronica2.put(""+num, df);
		}
	private String rutaFirmaElectronica() throws ErrorEvaluador{
		String exp=this.rutaFirmaElectronica;
		String ruta=this.motor.eval.evalua(exp).toString();
		return ruta;
		}
	public void guardaArchivosFirmaPorSeparado_aux(String datosFirmados, int num, String datosPreFirma, String id_firma) throws ErrorGotta, IOException {
		Archivo archivo;
		
		if (num==0) {
			archivo=new Archivo(this.motor.usuario, rutaFirmaElectronica(), "datos."+ this.lote.hashCode() +".txt", Util.creaDic("cd","datos") );
			
			if (datosPreFirma!=null){
				archivo.escribeArchivoTexto(datosPreFirma);
				this.motor.usuario.añadeMSG("Archivo pre-firma guardado en '"+archivo.rutaFisica+"'", TipoMensajeGotta.archivos);
				}
			}
		else {
			archivo=this.archivosFirmar.get(num-1);
						
			if (archivo.tipo.equals(Tipo.OTROS)){//Binarios
//				HashMap<String, Object> opcionales = new HashMap<String, Object>();
//				opcionales.put("idsignature", "FIRMA_1");
//				opcionales.put("idreference", "D0");
				datosFirmados = firma.completarFirma(this.motor.usuario, archivo.url(), datosFirmados);
				}
			}
		
		archivo.escribeArchivoTexto(datosFirmados,"adjuntos."+num+"."+ this.hashCode() +".xml");		
		if (num==0)
			this.motor.usuario.añadeMSG("Archivo post-firma (de los datos) guardado en '"+archivo.rutaFisica+"'", TipoMensajeGotta.archivos);
		else
			this.motor.usuario.añadeMSG("Archivo post-firma (del adjunto) guardado en '"+archivo.rutaFisica+"'", TipoMensajeGotta.archivos);

		
		if (_tf==null)
			return; //No guarda traza
		_tf.addNew();
			
		CampoDef camAux=_tb.tdef.getCampoClave();
		for (int i=0; i<camAux.numColumnas(); i++){
			ColumnaDef col = _tb.tdef.getCampoClave().getColumna(i);
			String nombrecol =  col.getCd();
			Object valorcol =  _tb.getValorCol(nombrecol);
			_tf.setValorCol(nombrecol, valorcol);
			}
		
		FechaGotta f_pendiente= (_tt!=null?(FechaGotta)_tt.getValorCol("f_pendiente"):this.motor.fechaHoy());
		_tf.setValorCol("f_pendiente",f_pendiente);	
		
		_tf.setValorCol("Numerador", num);
		
		if (datosPreFirma!=null)
			_tf.setValorCol("ruta", archivo.rutaFisica);
		else
			_tf.setValorCol("ruta", archivo.rutaFisica + archivo.sufijo);
		
		_tf.setValorCol("cd_documento", archivo.otrosDatos.get("cd"));
		_tf.setValorCol("ds_documento", archivo.otrosDatos.get("ds"));
		_tf.setValorCol("rutaFirma", archivo.rutaFisica + archivo.sufijo);
		_tf.setValorCol("F_Firma", new FechaGotta());
		_tf.setValorCol("CD_Firma", id_firma); //el id de la firma dentro del xml
		_tf.setValorCol("tipoFirma", this.javaScriptFirmaElectronica);
		_tf.setValorCol("CD_UsuarioFirma", this.motor.usuario.getLogin());
		}	
	
	public void guardaArchivosFirmaJuntos_aux(String datosFirmados, String id_firma) throws ErrorGotta, IOException {
		Archivo archivo;
		if (this.getFirmaElectrónicaActiva())//firma de trámites
			archivo=new Archivo(this.motor.usuario, rutaFirmaElectronica(), "datos."+ this.lote.hashCode() +".xml", Util.creaDic("cd", "datos") );
		else
			archivo=this.archivosFirmar.get(0);
		archivo.escribeArchivoTexto(datosFirmados);
		
		this.motor.usuario.añadeMSG("Archivo post-firma (de datos y adjuntos) guardado en '"+archivo.rutaFisica+"'", TipoMensajeGotta.archivos);
		
		if (_tf==null)
			return; //No guarda traza
		
		FechaGotta f_firma=new FechaGotta();
		CampoDef camAux=_tb.tdef.getCampoClave();
		FechaGotta f_pendiente= (_tt!=null?(FechaGotta)_tt.getValorCol("f_pendiente"):this.motor.fechaHoy());
		
		//fila de datos
		_tf.addNew();
			
		for (int i=0; i<camAux.numColumnas(); i++){
			ColumnaDef col = _tb.tdef.getCampoClave().getColumna(i);
			String nombrecol =  col.getCd();
			Object valorcol =  _tb.getValorCol(nombrecol);
			_tf.setValorCol(nombrecol, valorcol);
			}
		_tf.setValorCol("f_pendiente",f_pendiente);	
		
		_tf.setValorCol("Numerador", 0);
		
		_tf.setValorCol("ruta", archivo.rutaFisica);
		_tf.setValorCol("cd_documento", archivo.otrosDatos.get("cd"));
		_tf.setValorCol("ds_documento", archivo.otrosDatos.get("ds"));
		
		_tf.setValorCol("rutaFirma", archivo.rutaFisica);
	
		_tf.setValorCol("F_Firma", f_firma);
		_tf.setValorCol("CD_Firma", id_firma); //el id de la firma dentro del xml
		_tf.setValorCol("tipoFirma", this.javaScriptFirmaElectronica);
		_tf.setValorCol("CD_UsuarioFirma", this.motor.usuario.getLogin());
		
		//filas de adjuntos
		int numAdjunto=1;
		for (Archivo adjunto:this.archivosFirmar){
			_tf.addNew();
			
			for (int i=0; i<camAux.numColumnas(); i++){
				ColumnaDef col = _tb.tdef.getCampoClave().getColumna(i);
				String nombrecol =  col.getCd();
				Object valorcol =  _tb.getValorCol(nombrecol);
				_tf.setValorCol(nombrecol, valorcol);
				}
			_tf.setValorCol("f_pendiente",f_pendiente);	
			
			_tf.setValorCol("Numerador", numAdjunto++);
			
			_tf.setValorCol("ruta", adjunto.rutaFisica);
			_tf.setValorCol("cd_documento", adjunto.otrosDatos.get("cd") );
			_tf.setValorCol("ds_documento", adjunto.otrosDatos.get("ds") );
			
			_tf.setValorCol("rutaFirma", archivo.rutaFisica);
			
			_tf.setValorCol("F_Firma", f_firma);
			_tf.setValorCol("CD_Firma", id_firma); //el id de la firma dentro del xml
			_tf.setValorCol("tipoFirma", this.javaScriptFirmaElectronica);
			_tf.setValorCol("CD_UsuarioFirma", this.motor.usuario.getLogin());
			}
		}	
	
	JSONObject apiValidaFirma(HttpServletRequest request, Usuario usr) throws ErrorDiccionario, ErrorFirmaNoValida, ErrorArrancandoAplicacion, ErrorCreandoTabla {
		return apiFirma(request,usr,true);
	}
	JSONObject apiInfoFirma(HttpServletRequest request, Usuario usr) throws ErrorDiccionario, ErrorFirmaNoValida, ErrorArrancandoAplicacion, ErrorCreandoTabla {
		return apiFirma(request,usr,false);
	}
	private JSONObject apiFirma(HttpServletRequest request, Usuario usr, boolean validar) throws ErrorDiccionario, ErrorFirmaNoValida, ErrorArrancandoAplicacion, ErrorCreandoTabla {
    	ITabla t = cargaTablaFirmas(request, usr);
		Map<String, Object> ret = null;
		for(Fila f:t.datos){
			/*
			 Map<String, Object>  pfirma. validarXadesOriginal ( byte[] originalData, String xmlB64, false, null, FormatosFirma. XADES_BES)
                        donde: 
                                    originalData: Array de bytes del fichero original .
                                    xmlB64: fichero xml en B64 .
						boolean validateCert: Fuerza validación de certificados “true”
						Date dateRef: Fecha de referencia en la validación. Si va a null valida para la fecha del sistema.
						String xadesType: formato de firma “XADES-BES”
			 */
			String rutaFirma=f.gets("rutaFirma");
			String id_signature=f.gets("CD_Firma");
			
			ret=firma.validarFirma(usr, rutaFirma, id_signature);
			}
        if (ret==null)
        	return null;
        if(validar)
        	ret.put("tipo","validarFirma");
         else
        	ret.put("tipo","infoFirma");
		return new JSONObject(ret);
		}

	private ITabla cargaTablaFirmas(HttpServletRequest request, Usuario usr) throws ErrorDiccionario, ErrorArrancandoAplicacion, ErrorCreandoTabla{
		String qs= Util.obtenValor(request, "firma"); //Camino · clave te
		String[] q=qs.split(Constantes.PUNTO3);
		
		Long camino=Long.parseLong(q[0]);
		Filas filas=GestorMetaDatos.leeTRA_Caminos(usr.getConexion(), usr.getApli().getEsquema(), camino);
		String tablaFirmas=filas.get(0).gets("TablaFirmas");
		
		if (tablaFirmas.equals(Constantes.CAD_VACIA))
			throw new ErrorDiccionario("El camino " + camino + " no tiene definida una tabla de firmas.");
		
		ITablaDef td=usr.getApli().getEsquema().getTablaDef(tablaFirmas);
		ITabla tf=td.newTabla(usr);
		
		ArrayList<Object> claveTF=new ArrayList<Object>();
		for (int i=1;i<q.length;i++)
			claveTF.add(q[i]);
		
		tf.cargarRegistros(null,claveTF);
		return tf;
	}
	JSONObject datosFirmados(HttpServletRequest request, Usuario usr) throws ErrorDiccionario, JSONException, ErrorArrancandoAplicacion, ErrorCreandoTabla {
		JSONObject ret = new JSONObject();
		JSONArray jsonDatos = new JSONArray();
		
		ITabla t = cargaTablaFirmas(request, usr);
		usr.getMotor().lote.archivosFirmados=t.datos;
		for(Fila f: t.datos){
			JSONObject a = new JSONObject();
			a.put("numerador", f.get("numerador"));
			a.put("cd_documento", f.get("cd_documento"));
			a.put("ds_documento", f.get("ds_documento"));
			jsonDatos.put(a);
			}
		ret.put("productos",jsonDatos);
		ret.put("tipo","datosFirmados");
		
		return ret;
		}
    
	@SuppressWarnings("unchecked")
	void guardaArchivosFirmaPorSeparado(HttpServletRequest request) throws ErrorGotta, IOException {
	java.util.Enumeration<String> nombres=request.getParameterNames();
		while (nombres.hasMoreElements())  {
			String nombre=nombres.nextElement();
			if (nombre.startsWith("datosFirmados")){
				int num=Integer.parseInt(nombre.replace("datosFirmados", es.burke.gotta.Constantes.CAD_VACIA));
				String datosFirmados=Util.obtenValor(request, nombre);
				
				String texto=null;
				try {
					if (num==0)
						texto = jsonDatosFirmaElectrónica().toString(4);
					}
				catch (JSONException e) {
					throw new ErrorGotta("Error guardando firma");
					}
				guardaArchivosFirmaPorSeparado_aux(datosFirmados, num, texto, nombre);
				}
			}
		}
	void guardaArchivosFirmaJuntos(HttpServletRequest request) throws ErrorGotta, IOException {
		String datosFirmados=Util.obtenValor(request, "datosFirmados0");
		String idSignature=Util.obtenValor(request, "idSignature");
		
		guardaArchivosFirmaJuntos_aux(datosFirmados, idSignature);
		}
	public void firmaDatos(JSONObject jsret, Usuario usr) throws JSONException, ErrorGotta {
		jsret.put("tipo", "firmaDatos");
		jsret.put("sysdate", usr.getConexion().getHoraActual());
		jsret.put("datos",JSONfirmaElectronica());
		}

	public boolean hayFirma() {
		return this.getFirmaElectrónicaActivable() && this.datosFirmaElectronica.size()>0;
		}
}

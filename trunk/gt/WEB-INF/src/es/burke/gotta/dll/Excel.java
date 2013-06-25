package es.burke.gotta.dll;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import es.burke.gotta.Accion;
import es.burke.gotta.Archivo;
import es.burke.gotta.ColumnaDef;
import es.burke.gotta.Constantes;
import es.burke.gotta.Constantes.TipoMensajeGotta;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorArrancandoAplicacion;
import es.burke.gotta.ErrorConexionPerdida;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.FechaGotta;
import es.burke.gotta.Fila;
import es.burke.gotta.Filas;
import es.burke.gotta.INivelDef;
import es.burke.gotta.ITablaDef;
import es.burke.gotta.Motor;
import es.burke.gotta.NodoActivo;
import es.burke.gotta.PoolAplicaciones;
import es.burke.gotta.ServletExcel;
import es.burke.gotta.TablaTemporal;
import es.burke.gotta.TablaTemporalDef;
import es.burke.gotta.Usuario;
import es.burke.gotta.Util;
import es.burke.gotta.Variable;

public final class Excel extends DLLGotta {
	String nivelExportar;
	NodoActivo nodoActivo;
//	private String hojaFilaColumna;
	private String rutaOrigen;
	private Accion accion;
	private String valor;

	private String nombreFichero="datos";
	private String rutaDestino=null;
	private List<String> listaFusiones=null;
	
	@Override
	public String accionesValidas() {		
		return "exportaExcel leeExcel insertaDatos";
		}
	@Override
	public Object ejecuta(Accion xAccion, Object valor) throws ErrorGotta  {
		this.accion=xAccion;
		this.valor=valor.toString();
		
		String x=xAccion.accion;
		if (x.equalsIgnoreCase("leeExcel")) {// tablaDestino | rutaOrigen | numHoja
			ArrayList<String>temp=Util.split(valor.toString(), Constantes.PIPE);
			try {
				creaTablaDesdeExcel(temp.get(0), temp.get(1), temp.get(2));
				} 
			catch (NumberFormatException e) {
				throw new ErrorAccionDLL(e.getMessage());
				} 
			catch (IOException e) {
				throw new ErrorAccionDLL(e.getMessage());
				}
			
			mMotor.lote.devolverDepuradorCompleto=true;
			}
		else if (x.equalsIgnoreCase("exportaExcel")){// nivel
			usr.productosGenerados.put(""+this.hashCode(), this);
			
			this.nodoActivo=xAccion.lote.tramiteInicial().nodoActivo;
			this.nivelExportar=valor.toString();
			}
		else if (x.equalsIgnoreCase("insertaDatos")){// rutaOrigen | nivel_o_dato : hoja : fila : columna [| nivel_o_dato:hoja:fila:columna] |...
			ArrayList<String>temp=Util.split(valor.toString(), Constantes.PIPE);

			this.rutaOrigen=temp.get(0);
			if (Util.cuentaCadena(temp.get(1), ":")<=1){
				this.rutaDestino=Util.rutaFisicaRelativaOAbsoluta(temp.get(1));
				
				this.listaFusiones=temp.subList(2, temp.size());
				}
			else
				this.listaFusiones=temp.subList(1, temp.size());
			
			this.nodoActivo=xAccion.lote.tramiteInicial().nodoActivo;
			
			if (this.rutaDestino==null)
				usr.productosGenerados.put(""+this.hashCode(), this);
			else {
				Workbook wb=insertaDatos(this.usr, this.valor);
				
				FileOutputStream out;
				try {
					out = new FileOutputStream(this.rutaDestino);
					wb.write(out);
					out.close();
					return this.rutaDestino;
					} 
				catch (FileNotFoundException e) {
					throw new ErrorAccionDLL("Error en DLL Excel"+e.getMessage());
					} 
				catch (IOException e) {
					throw new ErrorAccionDLL("Error en DLL Excel"+e.getMessage());
					}
				}
			}
			
		return Motor.Resultado.OK;
		}
	@Override
	public void verifica(Motor xMotor, String xAccion, String nombre) throws ErrorGotta {
		verificaAccionValida(xAccion);
		this.mMotor=xMotor;
		this.usr=mMotor.usuario;
		}
	
	private Filas ejecutaNivel(String nombreNivel) throws ErrorGotta{
		INivelDef nivel=this.usr.getApli().getNivelDef(nombreNivel);
		Filas filas = nivel.obtenerNivel().lookUp(this.usr.getConexion(), this.mMotor.tramActivo().nodoActivo, -1);
		return filas;
		}
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta, ServletException {
		Workbook wb=null;
		
		if (this.accion.accion.equalsIgnoreCase("exportaExcel")){
			Filas filas=ejecutaNivel(this.nivelExportar);
			
			int numCol=-1;
			boolean sortAsc=false;
//			try {
//				String numCol_sortDir=sacaValor(getCookieValue(req, "config"+usr.getApli().getCd()), "c"+param+".s");
//				numCol=Integer.parseInt(numCol_sortDir.substring(1))-1;
//				sortAsc=(numCol_sortDir.substring(0,1).equals("u"));
//				} 
//			catch (JSONException e) {
//				//pass
//				usr.getApli().println(e.getMessage());
//				}
			wb=exportaFilas(this.usr, filas, numCol, sortAsc);
			
					
			String apañaNodo=Util.replaceTodos(Util.replaceTodos(this.nodoActivo.nodoActivo,"\\", "_"),"/", "_");
			if (apañaNodo!=null && apañaNodo.length()>ServletExcel.MAX_LONG_NOMBRE_FICHERO_EXCEL)
				apañaNodo=apañaNodo.substring(0, ServletExcel.MAX_LONG_NOMBRE_FICHERO_EXCEL);
			nombreFichero = "Gotta-"+(apañaNodo!=null?apañaNodo+"-":"")+this.nivelExportar+".xls";
			}
		else {
			wb=insertaDatos(this.usr, this.valor);
			}
		
		res.setContentType("application/vnd.ms-excel");

		if (!nombreFichero.endsWith(".xls"))
			nombreFichero+=".xls";
		
		res.setHeader("Content-disposition", "attachment;filename=" + Constantes.COMILLAS + nombreFichero + Constantes.COMILLAS);

		ServletOutputStream out = res.getOutputStream();

	    wb.write(out);
	    out.flush();
		out.close();
		}
	
	 
	private Workbook insertaDatos(Usuario usr, String p2) throws ErrorGotta {//p2=// rutaOrigen | nivel_o_dato : hoja : fila : columna [| nivel_o_dato:hoja:fila:columna] |...
		Archivo ar=new Archivo(usr, rutaOrigen);
		nombreFichero=ar.nombreFichero;
		
		Workbook wb;
		try {
			wb = ssWorkbook(ar.rutaFisica);
			}
		catch (FileNotFoundException e) {
			throw new ErrorGotta("Archivo excel de origen no encontrado: "+ar.rutaFisica +"\n"+e.getMessage());
			} 
		catch (IOException e) {
			throw new ErrorGotta("Archivo excel de origen no encontrado: "+ar.rutaFisica+"\n"+e.getMessage());
			}
		
		generaFormatos(wb);
		
		for (int i=0; i<this.listaFusiones.size(); i++){
			String op=this.listaFusiones.get(i);
			if (op.equals(Constantes.CAD_VACIA))
				continue;
			
			ArrayList<String> temp=Util.split(op, ":");
			
			String nivelOCampo=temp.get(0);
			Boolean esNivel=true;
			
			esNivel = this.usr.getApli().getNivelDef(nivelOCampo)!=null;
				
			String hoja; int numFila, numCol;
			hoja=temp.get(1); numFila=Integer.parseInt(temp.get(2)); numCol=Integer.parseInt(temp.get(3));
			numFila--;//para que coincida con la fila que se muestra en excel, que empieza en 1
			
			if (esNivel)
				insertaNivelEnPosicion(this.usr, wb, nivelOCampo, hoja, numFila, numCol);
			else
				insertaValorEnPosicion(this.usr, wb, nivelOCampo, hoja, numFila, numCol);
			}
			
		for (int i=0; i<wb.getNumberOfSheets(); i++){
			Sheet sheet=wb.getSheetAt(i);
			sssetForceFormulaRecalculation(sheet);
			}
			
		return wb;
		}
	
	private void insertaNivelEnPosicion(Usuario usr, Workbook wb, String nivel, String hoja, int numFila, int numCol) throws ErrorGotta {
		Sheet sheet=getSheet(wb, hoja);
		
		Filas filas=this.ejecutaNivel(nivel);
		HashMap<String, String> modif=sacaModificadoresNivel(filas.getOrden());
		for (int i=0; i<filas.size(); i++){
			Row row=getRow(sheet, hoja, numFila);
			exportaFila(usr, wb, sheet, null, filas, i, modif, row, numCol, false);
			numFila++;
			}
		}
	private void insertaValorEnPosicion(Usuario usr, Workbook wb, String campoOVariable, String hoja, int numFila, int numCol) throws ErrorGotta {	
		Sheet sheet=getSheet(wb, hoja);
		
		String tipo; Object valor;
		if (this.mMotor.esNombreVariable(campoOVariable)){
			Variable v=this.mMotor.lote.getVariable(campoOVariable);
			
			tipo=v.getTipo();
			valor=v.getValor();
			}
		else if (this.mMotor.esCampo(campoOVariable)){
			ColumnaDef c=this.mMotor.tablaCampo(campoOVariable).getColumna(0);
			
			tipo=c.getTipo();
			valor=this.mMotor.getValorSimbolo(campoOVariable).get(0);
			}
		else {//expresión
			valor=this.mMotor.getValorSimbolo(campoOVariable);
			tipo=Constantes.STRING;
			}
		
		Filas filas= new Filas( Util.creaListaString("col0"), 
								Util.creaListaString(tipo));
		filas.add( new Fila(filas, Util.creaLista(valor)));
		
		HashMap<String, String> modif=sacaModificadoresNivel(filas.getOrden());
		for (int i=0; i<filas.size(); i++){
			Row row=getRow(sheet, hoja, numFila);
			exportaFila(usr, wb, sheet, null, filas, i, modif, row, numCol, false);
			numFila++;
			}
	    
		}
	private static Sheet getSheet(Workbook wb, String hoja){
		if (Util.isNumeric(hoja))
			return wb.getSheetAt( Integer.parseInt(hoja) );
		else
			return wb.getSheet(hoja);
		}
	private static Row getRow(Sheet sheet, String hoja, int numFila){
		Row row = sheet.getRow(numFila);
		if (row==null) 
			row=sheet.createRow(numFila);
		return row;
		}
	static short formatoFecha, formatoFechaCorta, formatoNum, formatoNumSinDecimales, formatoMoneda;
	
	private static void generaFormatos(Workbook wb){
		DataFormat formato = wb.createDataFormat();
		formatoFecha = formato.getFormat("dd/mm/yyyy hh:mm:ss");
		formatoFechaCorta=formato.getFormat("dd/mm/yyyy");
		
		formatoNum = formato.getFormat("#,##0");
		formatoNumSinDecimales=formato.getFormat("#,##");
		formatoMoneda = formato.getFormat("#,##0.00");
		}
	
	@SuppressWarnings("unused")
	private static Row copyRow(HSSFWorkbook workbook, HSSFSheet worksheet, int sourceRowNum, int destinationRowNum) {
        // Get the source / new row
		Row newRow = worksheet.getRow(destinationRowNum);
        Row sourceRow = worksheet.getRow(sourceRowNum);

        // If the row exist in destination, push down all rows by 1 else create a new row
        if (newRow != null) {
            worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
        } else {
            newRow = worksheet.createRow(destinationRowNum);
        }

        // Loop through source columns to add to new row
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            // Grab a copy of the old/new cell
            Cell oldCell = sourceRow.getCell(i);
            Cell newCell = newRow.createCell(i);

            // If the old cell is null jump to next cell
            if (oldCell == null) {
                newCell = null;
                continue;
            }

            // Copy style from old cell and apply to new cell
            CellStyle newCellStyle = workbook.createCellStyle();
            newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
            ;
            newCell.setCellStyle(newCellStyle);

            // If there is a cell comment, copy
            if (newCell.getCellComment() != null) {
                newCell.setCellComment(oldCell.getCellComment());
            }

            // If there is a cell hyperlink, copy
            if (oldCell.getHyperlink() != null) {
                newCell.setHyperlink(oldCell.getHyperlink());
            }

            // Set the cell data type
            newCell.setCellType(oldCell.getCellType());

            // Set the cell data value
            switch (oldCell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    newCell.setCellValue(oldCell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    newCell.setCellValue(oldCell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_ERROR:
                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    newCell.setCellFormula(oldCell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    newCell.setCellValue(oldCell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                    newCell.setCellValue(oldCell.getRichStringCellValue());
                    break;
            }
        }

        // If there are are any merged regions in the source row, copy to new row
        for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
                        (newRow.getRowNum() +
                                (cellRangeAddress.getFirstRow() -
                                        cellRangeAddress.getLastRow())),
                        cellRangeAddress.getFirstColumn(),
                        cellRangeAddress.getLastColumn());
                worksheet.addMergedRegion(newCellRangeAddress);
            	}
        	}
        return newRow;
    	}

        
	private static void exportaFila(Usuario usr, 
									Workbook wb, Sheet sheet, Drawing patriarch, 
									Filas filasOrigen, Integer idFilaOrigen, HashMap<String, String> modif, 
									Row row, int columnaInsercion, boolean mostrarIconos){
		Fila fila=filasOrigen.get(idFilaOrigen);
		List<String> cols = filasOrigen.getOrden();
		
		int j=0;
		
		if (mostrarIconos){
			row.createCell(columnaInsercion);
			if (filasOrigen.containsKey("Icono")){
				try {
					ClientAnchor anchor= ssClientAnchor(wb);
				    anchor.setCol1((short)0); anchor.setCol2((short)0);
				    anchor.setRow1(row.getRowNum()); anchor.setRow2(row.getRowNum());
				    anchor.setDx1(0); anchor.setDy1(0);
				    anchor.setDx2(240); anchor.setDy2(240);
				    anchor.setAnchorType( 2 );
				     
				    //je lis l'image et le mais dans un byte array
				    String path=dirIco(usr)+fila.gets("Icono");
				    if (!path.endsWith(".png"))
				    	path+=".png";
				    File img=new File(path);
				    if (img.exists()){
					    byte[] s = Util.getFileContentsAsByteArray(new File(path));
					    //j'ajoute cette image dans le workbook et recupere un index l'identifiant
					    int index = wb.addPicture(s,HSSFWorkbook.PICTURE_TYPE_PNG);
					     
					    // finalement j'ajoute l'image au patriarch
					    patriarch.createPicture(anchor, index);
					    }
				    columnaInsercion++;
				    
					}
				catch (IOException e) //FileNotFoundException
					{/*pass*/}
				}
			}
		boolean hayQueAplicarEstilo=false;
		for (String c:cols) {
			if (!Util.columnaInvisible(c)){
				Cell cell = row.getCell(columnaInsercion);
				if (cell==null) {
					cell=row.createCell(columnaInsercion);
					hayQueAplicarEstilo = true;
					}
				CellStyle cellStyle=null;
				
				short formatoNumQueAplicar=formatoNum;
				if (modif.get(0)==null){
					//pass
					}
				else if (modif.get(c).equals("0"))
					formatoNumQueAplicar=formatoNumSinDecimales;
				else if (modif.get(c).equalsIgnoreCase("m"))
					formatoNumQueAplicar=formatoMoneda;
				
				String tipo=filasOrigen.getTipos().get(j);
				if (tipo==null) 
					cell.setCellValue(ssRichStringText(wb, Util.preparaParaRAW(fila.get(c), Constantes.STRING)  )); 
				else if (fila.get(c)==null)
					cell.setCellValue(ssRichStringText(wb, ""));	
				else if (tipo.equals("java.lang.String")){
					String valor=fila.get(c).toString();
					if (valor.startsWith("gotta://")){
						//convertimos la url a absoluta
						String r=usr.getApli().getUrlAplicacion()+"?";
						if (!valor.contains("aplicacion="+usr.getApli().getCd()))
							r+="aplicacion="+usr.getApli().getCd()+"&";
						valor=Util.replaceUno(valor, "gotta://", r);
						
						int posEspacio=-1;
						posEspacio=valor.indexOf(Constantes.ESPACIO);
						
						Hyperlink lnk=ssHyperlink(wb, Hyperlink.LINK_URL);
						lnk.setAddress(valor.substring(0, posEspacio)); 
						cell.setHyperlink(lnk);
						
						cellStyle=creaEstiloLink(wb);
						valor=valor.substring(posEspacio+1);
						}
					cell.setCellValue(ssRichStringText(wb, Util.preparaParaRAW(valor, tipo)));
					}	
				else if (tipo.equals("java.lang.Integer")){
					cell.setCellValue((Integer)fila.get(c));
					cellStyle=creaEstilo(wb, CellStyle.ALIGN_RIGHT,formatoNumQueAplicar);
					}
				else if (tipo.equals("java.lang.Long")){
					cell.setCellValue((Long)fila.get(c));	
					cellStyle=creaEstilo(wb, CellStyle.ALIGN_RIGHT,formatoNumQueAplicar);
					}
				else if (tipo.equals("java.sql.Timestamp")){
					FechaGotta value = (FechaGotta)fila.get(c);
					cell.setCellValue(new Date(value.getTimeInMillis()));
					
					short formatoFechaAAplicar=formatoFecha;
					if (c.endsWith("\\fc"))//fecha corta
						formatoFechaAAplicar=formatoFechaCorta;
					cellStyle=creaEstilo(wb, CellStyle.ALIGN_RIGHT,formatoFechaAAplicar);
					
					}
				else if (tipo.equals("java.math.BigDecimal") || Util.esTipoNumerico(tipo)){
					Object obj=fila.get(c);
					if (obj instanceof BigDecimal){
						BigDecimal bd = (BigDecimal)fila.get(c);
						if (bd.scale()==0){
							cell.setCellValue(bd.longValueExact());
							cellStyle=creaEstilo(wb, CellStyle.ALIGN_RIGHT, formatoNumQueAplicar);
							}
						else{
							cell.setCellValue(bd.doubleValue());
							cellStyle=creaEstilo(wb, CellStyle.ALIGN_RIGHT, formatoMoneda);
							}
						}
					else {
						//poi exige un double ahí :-(
						cell.setCellValue(Double.parseDouble( Util.desformatearNumero(fila.gets(c)) ));
						cellStyle=creaEstilo(wb, CellStyle.ALIGN_RIGHT,formatoMoneda);
						}
					
					}
				else {
					cell.setCellValue(ssRichStringText(wb,Util.preparaParaRAW(fila.get(c), tipo)));
					}
				
				if (hayQueAplicarEstilo && cellStyle!=null){
					try {
						cell.setCellStyle(cellStyle);
						} 
					catch (IllegalArgumentException e) {
						// This Style does not belong to the supplied Workbook. Are you trying to assign a style from one workbook to the cell of a differnt workbook?
						}
					}
				columnaInsercion++;
				}
			j++;
			}
	}
	private static HashMap<String, String> sacaModificadoresNivel(List<String>cols){
		HashMap<String, String> modif=new HashMap<String, String>();

		for (String c: cols){
			if (!Util.columnaInvisible(c)){
				if (c.contains(Constantes.SEPARADOR)){
					ArrayList<String> temp=Util.split(c, Constantes.SEPARADOR);
					modif.put(c, temp.get(1));
					}
				else
					modif.put(c, null);
				}
			}
		return modif;
	}
	public static Workbook exportaFilas(Usuario usr, Filas filas, int columnaOrden, boolean desc) throws ErrorConexionPerdida, ErrorArrancandoAplicacion, ErrorGenerandoProducto{
		cache.clear();
		
		Workbook wb;
		try {
			wb = ssWorkbook();
			} 
		catch (IOException e1) {
			throw new ErrorGenerandoProducto(e1.getMessage()); 
			}
		Sheet sheet = wb.createSheet("volcado gotta");
		Drawing patriarch = sheet.createDrawingPatriarch();
		
		CellStyle estiloCabecera = wb.createCellStyle();
		estiloCabecera.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		estiloCabecera.setAlignment(CellStyle.ALIGN_CENTER);
		estiloCabecera.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		String tmostrarIconos=usr.getApli().getDatoConfig("Excel_exportarIconos");
		Boolean mostrarIconos=(tmostrarIconos==null || tmostrarIconos.equals("1"));
	    
		usr.añadeMSG("Exportando "+filas.size()+ " filas a excel", TipoMensajeGotta.info);

		List<String> cols = filas.getOrden();
		Row row = sheet.createRow(0);
		int numCol=0;
		
		if (mostrarIconos && filas.containsKey("Icono")){
			Cell cell = row.createCell(numCol++);
			cell.setCellStyle(estiloCabecera);
			}
		
		HashMap<String, String> modif=sacaModificadoresNivel(cols);
		
		int jj=0;

		for (String c: cols){
			if (!Util.columnaInvisible(c)){
				String literal=c;
				if (c.contains(Constantes.SEPARADOR)){
					ArrayList<String> temp=Util.split(c, Constantes.SEPARADOR);
					literal=temp.get(0);
					}
				Cell cell = row.createCell(numCol++);
				cell.setCellValue(ssRichStringText(wb, literal));
				cell.setCellStyle(estiloCabecera);
				jj++;				
				}
			else if (c.equalsIgnoreCase("icono"))
				jj++;
			
			if (jj==columnaOrden){
				String a=filas.getOrden().get(jj+1);
				filas.ordena(a, desc);
				
				columnaOrden=-1;
				}
			}
		
		generaFormatos(wb);
		for (int i=0; i<filas.size(); i++) {
			row = sheet.createRow(i+1);
			exportaFila(usr, 
						wb, sheet, patriarch, 
						filas, i, modif, //filasOrigen, idFilaOrigen
						row, 0, //posición de inserción 
						mostrarIconos);			
			}
		usr.añadeMSG("Exportación a excel finalizada", TipoMensajeGotta.info);
		
		numCol=(short) (mostrarIconos?0:1);
		for (String c: cols) {
			if (!Util.columnaInvisible(c)){
				try	{
					sheet.autoSizeColumn(numCol++);
					}
				catch (Exception e) {
					/*
					 * Warning
					 * 
					 * To calculate column width HSSFSheet.autoSizeColumn
					 * uses Java2D classes that throw exception if graphical
					 * environment is not available. In case if graphical
					 * environment is not available, you must tell Java that
					 * you are running in headless mode and set the
					 * following system property: java.awt.headless=true
					 * (either via -Djava.awt.headless=true startup
					 * parameter or via
					 * System.setProperty("java.awt.headless", "true")).
					 * 
					 */
					}
				}
			}
		return wb;
		}
////////////////////////
	private static ClientAnchor ssClientAnchor(Workbook wb){
		return wb.getCreationHelper().createClientAnchor();
		}
	private static RichTextString ssRichStringText(Workbook wb, String literal) {
		return wb.getCreationHelper().createRichTextString(literal);
		}
	private static Hyperlink ssHyperlink(Workbook wb, int type){
		return wb.getCreationHelper().createHyperlink(type);
		}
	private static void sssetForceFormulaRecalculation(Sheet sheet){
		if (sheet instanceof HSSFSheet)
			((HSSFSheet)sheet).setForceFormulaRecalculation(true);
		else if (sheet instanceof XSSFSheet)
			((XSSFSheet)sheet).setForceFormulaRecalculation(true);
		}
	
	private static Workbook ssWorkbook() throws IOException {
		return ssWorkbook(null);
		}
	private static Workbook ssWorkbook(String ruta) throws IOException {
		if (ruta==null){
			return new HSSFWorkbook();
			}	 
		else {
			InputStream inp = new FileInputStream(ruta);
			if (ruta.endsWith(".xls")){
				POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inp);
				return new HSSFWorkbook(poifsFileSystem);
				}
			else 
				return new XSSFWorkbook(inp);
			}
		}
	static HashMap<String, CellStyle> cache=new HashMap<String, CellStyle>(); 
	private static CellStyle creaEstilo(Workbook wb, short alignRight, short formatoNum) {
		CellStyle cellStyle;
		
		String key = ""+alignRight+"_"+formatoNum;
		if (cache.containsKey(key))
			return cache.get(key);
		cellStyle=wb.createCellStyle();
        cellStyle.setAlignment(alignRight);
        cellStyle.setDataFormat(formatoNum);
		cache.put(key, cellStyle);
		return cellStyle;
		}
	private static CellStyle creaEstiloLink(Workbook wb){
		String key = "link";
		if (cache.containsKey(key))
			return cache.get(key);
		
		//cell style for hyperlinks, by default hyperlinks are blue and underlined
		CellStyle hlink_style = wb.createCellStyle();
		Font hlink_font = wb.createFont();
		hlink_font.setUnderline(Font.U_SINGLE);
	 	hlink_font.setColor(IndexedColors.BLUE.getIndex());
	 	hlink_style.setFont(hlink_font);
	 	
	 	cache.put(key, hlink_style);
	 	return hlink_style;
		}
	private static String dirIco(Usuario usr) throws ErrorConexionPerdida, ErrorArrancandoAplicacion {
		String rptWeb= usr.getApli().getDatoConfig("icoweb");
		if (new File("/tmp/").canRead())
			rptWeb = Util.replaceUno(rptWeb, "g:/proyectos", PoolAplicaciones.dirInstalacion+"Aplicaciones");
		
		return PoolAplicaciones.dirInstalacion+rptWeb;
		}
////////////////////////////////////////////////////////	
	private TablaTemporalDef tdef; 
	private TablaTemporal t;
	
	private void creaTablaDesdeExcel(String nombreTabla, String ruta, String sHoja) throws ErrorGotta, NumberFormatException, IOException{
		int numHoja=0;
		if (sHoja!=null)
			numHoja=Integer.parseInt(sHoja);
		
		FileInputStream archivo=abrirArchivo(ruta);
		Sheet hoja=this.getHoja(archivo, ruta, numHoja);
		
		this.tdef=new TablaTemporalDef(nombreTabla, ruta);
		montaEstructura(hoja, this.tdef);
		
		this.t = (TablaTemporal)tdef.newTabla(usr);
		
		leerFilas(hoja, ruta, numHoja);
		cerrarArchivo(archivo);
		this.mMotor.tablas.put(nombreTabla, t);
		}
	
	private void montaEstructura(Sheet hoja, ITablaDef tabla) throws ErrorAccionDLL{
		//columnas especiales
		String cruta = "_ruta", numhoja = "_numhoja", numfila = "_numfila";
		ColumnaDef colRuta=new ColumnaDef(cruta, cruta, Constantes.STRING, 250, null); 
		ColumnaDef colHoja=new ColumnaDef(numhoja,  numhoja,  Constantes.INTEGER,  5, null);
		ColumnaDef colFila=new ColumnaDef(numfila,  numfila,  Constantes.INTEGER,  5, null);
		tabla.columnas.put(colRuta.getCd(), colRuta); tabla.columnas.put(colHoja.getCd(), colHoja);
		tabla.columnas.put(colFila.getCd(), colFila);

		Row fila0=hoja.getRow(0); //en la fila 0 van los nombres de columnna
		Row fila1=hoja.getRow(1);

		if (fila0==null || fila1==null)
			throw new ErrorAccionDLL("Esta hoja de cálculo no tiene datos");
		for (int j=0; j<fila0.getLastCellNum(); j++){
			Cell celda0=fila0.getCell(j);
			Cell celda1=fila1.getCell(j);

			if (celda0==null)
				break;
			String nombre=celda0.getRichStringCellValue().getString();
			if (nombre.equals(Constantes.CAD_VACIA))
				nombre="columna_"+j;
			String tipoDato=Constantes.STRING;

			if (celda1==null){
				//pass
				}
			else if (celda1.getCellType()==Cell.CELL_TYPE_BOOLEAN)
				tipoDato=Constantes.BOOLEAN;
			else if (celda1.getCellType()==Cell.CELL_TYPE_NUMERIC){
				tipoDato=Constantes.CURRENCY;
				if (DateUtil.isCellDateFormatted(celda1))
					tipoDato=Constantes.DATE;
				}
			ColumnaDef col=new ColumnaDef(nombre, nombre, tipoDato, 250, null);
			tabla.columnas.put(col.getCd(), col);
			}
		
		tdef.generaCamposDesdeColumnas();
		}
	private Sheet getHoja(FileInputStream archivo, String rutaArchivo, int numHoja) throws IOException, ErrorAccionDLL{
		try {
			Workbook wb = ssWorkbook(rutaArchivo);
			return wb.getSheetAt(numHoja);
			}
		catch (OfficeXmlFileException e){
			throw new ErrorAccionDLL("Parece que está intentando leer un excel en formato Office 2007+XML: actualmente sólo podemos leer el formato Excel 2003. El mensaje completo del error es:"+e.getMessage());
			}
		}
	private FileInputStream abrirArchivo(String rutaArchivo) throws FileNotFoundException{
		return new FileInputStream(rutaArchivo);
		}
	private void cerrarArchivo(FileInputStream archivo) throws IOException{
		archivo.close();
		archivo=null;
		}

	private void leerFilas(Sheet hoja, String rutaArchivo, int numHoja) {		
		ArrayList<String> orden=new ArrayList<String>();
		for (ColumnaDef col : tdef.columnas.values())
			orden.add(col.getTipo());

		Filas filas= this.t.datos;

		int numColumnasFijas=3;//_ruta, _numHoja, _numFila
		int numColumnas=filas.getOrden().size()-numColumnasFijas;
		for (int i=1; i< hoja.getPhysicalNumberOfRows(); i++){
			Row row=hoja.getRow(i);
			
			Fila fila=new Fila(filas);

			fila.put("_ruta", rutaArchivo);
			fila.put("_numhoja", numHoja);
			fila.put("_numfila", i);
			
			boolean todasVacias=true;
			for (int j=0; j<numColumnas; j++){
//				if (j+2 >= filas.getOrden().size())
//					break;
				String nombreCol=filas.getOrden().get(j+numColumnasFijas);

				Cell celda=row.getCell(j);

				Object valor=null;
				if (celda==null)
					continue;
				else if (celda.getCellType()==Cell.CELL_TYPE_BOOLEAN)
					valor=celda.getBooleanCellValue();
				else if (celda.getCellType()==Cell.CELL_TYPE_STRING)
					valor=celda.getRichStringCellValue().getString();
				else if (celda.getCellType()==Cell.CELL_TYPE_NUMERIC){
					if (DateUtil.isCellDateFormatted(celda))
						valor=FechaGotta.comoFechaGotta( celda.getDateCellValue());
					else {
						valor=celda.getNumericCellValue();
						
						try{
							valor=new BigDecimal(valor.toString()).setScale(0);
							}
						catch (ArithmeticException e){
							try {
								valor=new BigDecimal(valor.toString());
								}
							catch (ArithmeticException ee){
								ColumnaDef col=this.tdef.columnas.get(nombreCol);
								
								if (DateUtil.isValidExcelDate(celda.getNumericCellValue())){
									valor=FechaGotta.comoFechaGotta( celda.getDateCellValue());
									
									if (valor!=null && !col.getTipo().equals(Constantes.DATE))
										col.setTipo(Constantes.DATE);
									}
								else
									throw new ArithmeticException("Se ha producido un error al convertir el valor "+valor.toString()+" de la columna "+nombreCol+":"+e.getMessage());
								}
							
							}
						}
					}

				if (valor!=null && !valor.equals(Constantes.CAD_VACIA))
					todasVacias=false;

				fila.put( nombreCol, valor);
				}
			if (todasVacias)
				break; //hemos llegado al final
			filas.add(fila);
			}
		Integer totalFilasInicial=filas.size();
		t.setRegistroActual(totalFilasInicial);
		}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}
}
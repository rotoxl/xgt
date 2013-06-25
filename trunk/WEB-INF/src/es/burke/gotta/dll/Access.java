package es.burke.gotta.dll;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.DataType;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

import es.burke.gotta.Accion;
import es.burke.gotta.CampoDef;
import es.burke.gotta.Coleccion;
import es.burke.gotta.ColumnaDef;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Fila;
import es.burke.gotta.Filas;
import es.burke.gotta.INivelDef;
import es.burke.gotta.ITabla;
import es.burke.gotta.Motor;
import es.burke.gotta.Parametro;
import es.burke.gotta.TablaTemporalDef;
import es.burke.gotta.Util;

public final class Access extends DLLGotta {
	@Override
	public String accionesValidas() {		
		return "exportar listaTablas tablaTablas importarTabla anexarTabla";
		}
	@Override
	public Object ejecuta(Accion xAccion, Object valor) throws ErrorGotta  {
		String x=xAccion.accion;
		if (x.equalsIgnoreCase("exportar")) {
			ArrayList<String>temp=Util.split(valor.toString(), Constantes.PIPE);
			exportarTabla(temp.get(0), temp.get(1), temp.get(2));			
			} 
		else if (x.equalsIgnoreCase("anexarTabla")) {
			ArrayList<String>temp=Util.split(valor.toString(), Constantes.PIPE);
			anexarTabla(temp.get(0), temp.get(1), temp.get(2));			
			} 
		else if (x.equalsIgnoreCase("listaTablas"))  
			return listaTablas(valor.toString());
		else if (x.equalsIgnoreCase("tablaTablas"))  
			tablaTablas(valor.toString());
		else if (x.equalsIgnoreCase("importarTabla")) {
			ArrayList<String>temp=Util.split(valor.toString(), Constantes.PIPE);
			String tercerPar=null;
			if (temp.size()==3) tercerPar=temp.get(2);
			return importarTabla(temp.get(0), temp.get(1), tercerPar);
			}
		mMotor.lote.devolverDepuradorCompleto=true;
		return Motor.Resultado.OK;
		}
	@Override
	public void verifica(Motor xMotor, String xAccion, String nombre) throws ErrorGotta {
		verificaAccionValida(xAccion);
		this.mMotor=xMotor;
		this.usr=mMotor.usuario;
		}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta, ServletException {
	}
	
	private Filas ejecutaNivel(String nombreNivel) throws ErrorGotta{
		INivelDef nivelDef = mMotor.getApli().getNivelDef(nombreNivel);
		ArrayList<Object> params = new ArrayList<Object>();
		for (Parametro par : nivelDef.getColParams()){
			String nombre = par.getNombre();
			params.add(mMotor.getValorSimbolo(nombre).get(0));
			}
		Filas filas;
		try {
			filas = nivelDef.obtenerNivel().lookUp(this.usr.getConexion(), params);
			}
		catch (SQLException e) {
			throw new ErrorGotta(e);
			}
		
		return filas;
		}
//////////////////
	private void cierraDB(Database db, File mdb) {
		try {
    		if (db!=null) db.close();
    		
    		db=null;
    		mdb=null;
    		}
    	catch (IOException e){
    		//pass
    		}
	}
	
	private void exportarTabla(String nombreMDB, String nombreTabla, String nombreNivel) throws ErrorGotta{
		Database db=null; File mdb=null;
		try {
			Filas filas=ejecutaNivel(nombreNivel);
			mdb = new File(Util.rutaFisicaRelativaOAbsoluta(nombreMDB));	
			 
		    if (!mdb.exists())     
		    	db = Database.create(mdb, false);
		    else
		        db = Database.open(mdb, false, false);
		    
		    ColumnBuilder cb;
		    TableBuilder tb = new TableBuilder(nombreTabla);
		    for(int n=0; n<filas.getOrden().size(); n++) {
		        cb = new ColumnBuilder(filterNonAscii(filas.getOrden().get(n)), mapear(filas.getTipos().get(n)));
		        tb.addColumn(cb.toColumn());
		    	}
		    
		    Table newTable = tb.toTable(db);
		    for(Fila d : filas){
		    	HashMap<String, Object> fila = new HashMap<String, Object>();
		    	for(int n=0; n<filas.getOrden().size(); n++) {
		    		String nombre = filterNonAscii(filas.getOrden().get(n));
		    		fila.put(nombre, d.get(filas.getOrden().get(n)));
		    	}		    	
		    	newTable.addRow(newTable.asRow(fila));
		    }
		    
	    } 
	    catch (IOException e){
	    	throw new ErrorGotta(e.getMessage());
	    }
	    finally{
	    	cierraDB(db, mdb);
	    	}
    }
	private String importarTabla(String nombreMDB, String nombreTabla, String tablaTemp) throws ErrorGotta{
		Database db=null;
		File mdb=null;
		if (tablaTemp==null) tablaTemp=nombreTabla;
		String nombreSeguro = filterNonAscii(tablaTemp);
		if (!nombreSeguro.startsWith("#")) nombreSeguro = "#" + nombreSeguro;

		try {
			mdb=new File(Util.rutaFisicaRelativaOAbsoluta(nombreMDB));
			db=Database.open(mdb);
		    Table tb = db.getTable(nombreTabla);

		    ArrayList<String> tipos = new ArrayList<String>();
		    
			TablaTemporalDef td= new TablaTemporalDef(nombreSeguro, nombreMDB);
		    
		    for(Column c : tb.getColumns()) {
		    	String colName = filterNonAscii(c.getName());
		    	String tipo = mapear(c.getType());
		    	ColumnaDef col = new ColumnaDef(colName, c.getName(), tipo, c.getLength(), null);
		    	td.putColumna(colName, col);
		        tipos.add(tipo);
		        CampoDef cam = new CampoDef(colName, colName, td.getCd());
		        cam.putColumna(col);
		        td.putCampo(cam.getCd(), cam);
		    }
			
			ITabla t = td.newTabla(usr);
		    
		    Filas filas = new Filas(td.getColumnas().getOrden(), tipos);	    
		    

		    for(Map<String, Object> row : tb) {
		    	Fila fila = new Fila(filas);
		    	for (String k : row.keySet())
		    		fila.put(filterNonAscii(k), row.get(k));
		    	filas.add(fila);
		    	}
		    
		    t.datos = filas;
		    t.registroActual = 0;
		    t.untouch();
		    
		    mMotor.tablas.put(nombreSeguro, t);
		    return nombreSeguro;
			} 
	    catch (IOException e){
	    	throw new ErrorGotta(e.getMessage());
	    	}
	    finally {
	    	cierraDB(db, mdb);
	    }
	}
	@SuppressWarnings("resource")
	private void anexarTabla(String nombreMDB, String nombreTabla, String nombreNivel) throws ErrorGotta{
		String nombreOrigen=null, nombreDestino;
		Database db=null; 
		File mdb=null;
		try {
			Filas filas=ejecutaNivel(nombreNivel);
		    mdb = new File(Util.rutaFisicaRelativaOAbsoluta(nombreMDB));	
			        
		    db= Database.open(mdb, false, false);
		    db.setUseBigIndex(true);
		    
		    Table tabla = db.getTable(nombreTabla);
		    if (tabla==null)
		    	throw new ErrorAccionDLL("La tabla '"+nombreTabla+"' no existe en el mdb '"+nombreMDB+"'");
		    
		    //parece que el método tabla.getColumn(*) distingue entre mayúsculas y minúsculas 
		    Coleccion<Column> nombresColumnasDestino=new Coleccion<Column>(false);
		    for (Column c:tabla.getColumns())
		    	nombresColumnasDestino.put(c.getName(), c);
		    	
		    for(Fila d : filas){
		    	HashMap<String, Object> fila = new HashMap<String, Object>();
		    	for(int n=0; n<filas.getOrden().size(); n++) {
		    		nombreOrigen=filas.getOrden().get(n);
		    		nombreDestino = filterNonAscii(nombreOrigen);
		    		
		    		if (!nombresColumnasDestino.containsKey(nombreDestino))
		    			throw new ErrorAccionDLL("La columna '"+nombreDestino+"' (sacada del nivel) no existe en la tabla destino");
		    		String tipoOrigen=filas.getTipos().get(n);
		    		String tipoMapeadoDestino=mapear(nombresColumnasDestino.get(nombreDestino).getType());
		    		if (tipoMapeadoDestino.equals(Constantes.BOOLEAN) && Util.esTipoNumerico(Constantes.tipoGotta(tipoOrigen))){
		    			String valor=d.get(nombreOrigen).toString();
		    			fila.put(nombreDestino, !valor.equals("0"));
		    			}
		    		else
		    			fila.put(nombreDestino, d.get(nombreOrigen));
		    		}
		    	tabla.addRow(tabla.asRow(fila));
		    	}
	    	} 
	    catch (IOException e){
	    	throw new ErrorGotta(e.getMessage()+"\nEl error se produjo al intentar copiar el valor de '"+nombreOrigen+"'");
	    	}
	    finally{
	    	cierraDB(db, mdb);
	    	}
    	}

	private ArrayList<String> sacaNombresTabla(String fn) throws ErrorGotta{
		try {
		    File fich = new File(Util.rutaFisicaRelativaOAbsoluta(fn));
		    Database db = Database.open(fich);
		    
		    ArrayList<String>ret= new ArrayList<String>();
		    for (String f : db.getTableNames()) 
		        ret.add(f);
		    
		    db.close();
		    return ret;
			} 
	    catch (IOException e){
	    	throw new ErrorGotta(e.getMessage());
	    	}
		}
	private String listaTablas(String fn) throws ErrorGotta{
		String res = "";
		for (String f : sacaNombresTabla(fn)) 
			res += f + ":" + f + ",";
		return "{" + res.substring(0, res.length()-1) + "}";
    	}
	private void tablaTablas(String fn) throws ErrorGotta{
		try {		    
		    ArrayList<ColumnaDef> cols=new ArrayList<ColumnaDef>();						
		    ColumnaDef col = new ColumnaDef("nombre", "nombre", "string", 100, null);			
			cols.add(col);
			
			TablaTemporalDef td= new TablaTemporalDef("nombre",fn , cols);
			
		    CampoDef cam = new CampoDef("nombre", "nombre", "#listaTablas");
		    cam.putColumna(col);
		    td.putCampo(cam.getCd(), cam);		    
				
			ITabla t = td.newTabla(usr);
			
		    ArrayList<String> tipos= new ArrayList<String>();
		    tipos.add("string");
		    Filas filas = new Filas(td.getColumnas().getOrden(), tipos);
		    
		    for (String f : sacaNombresTabla(fn)) {
		        Fila fila = new Fila(filas);
		        fila.put("nombre", f);
		        filas.add(fila);
		    	}
		    
		    t.datos = filas;
		    t.registroActual = 0;
		    t.untouch();
		    
		    mMotor.tablas.put("#listaTablas", t);
			} 
	    catch (Exception e){
	    	throw new ErrorGotta(e.getMessage());
	    	}
    	}
////////////		
	private String mapear(DataType j){
		String res ="";
		if(j == DataType.SHORT_DATE_TIME) 
			res = Constantes.DATE;
		else if (j == DataType.MEMO)
			res = Constantes.MEMO;
		else if (j == DataType.BOOLEAN)
			res = Constantes.BOOLEAN;
		else if (j == DataType.LONG)
			res = Constantes.LONG;
		else if (j == DataType.DOUBLE)
			res = Constantes.DOUBLE;
		else if (j == DataType.INT)
			res = Constantes.INTEGER;
		else if (j == DataType.TEXT)
			res = Constantes.STRING;
		else if (j == DataType.MONEY || j == DataType.NUMERIC)  
			res = Constantes.CURRENCY;
		return res;
		}
	private DataType mapear(String j) {
	    if (j == "java.math.BigDecimal")
	        return DataType.NUMERIC;
	    else if (j == "java.lang.String")
	        return DataType.MEMO;
	    else if (j == "java.sql.Timestamp")
	        return DataType.SHORT_DATE_TIME;
	    else
	        return DataType.MEMO;
	}
	
	private static String filterNonAscii(String inString) throws ErrorGotta {
		// Create the encoder and decoder for the character encoding
		Charset charset = Charset.forName("US-ASCII");
		CharsetDecoder decoder = charset.newDecoder();
		CharsetEncoder encoder = charset.newEncoder();
		// This line is the key to removing "unmappable" characters.
		encoder.onUnmappableCharacter(CodingErrorAction.IGNORE);
		String result = inString;

		try {
			// Convert a string to bytes in a ByteBuffer
			ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(inString));

			// Convert bytes in a ByteBuffer to a character ByteBuffer and then to a string.
			CharBuffer cbuf = decoder.decode(bbuf);
			result = cbuf.toString();
			} 
		catch (Exception cce) {
			throw new ErrorGotta(cce.getMessage());
			}

		return result;	
		}
}
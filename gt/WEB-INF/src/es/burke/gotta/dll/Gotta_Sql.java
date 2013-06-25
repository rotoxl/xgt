package es.burke.gotta.dll;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Accion;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorConexionPerdida;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.FechaGotta;
import es.burke.gotta.Fila;
import es.burke.gotta.Filas;
import es.burke.gotta.INivel;
import es.burke.gotta.Motor;
import es.burke.gotta.NivelDef;
import es.burke.gotta.Parametro;
import es.burke.gotta.Util;

public class Gotta_Sql extends DLLGotta{
	private StringBuffer fichero = new StringBuffer();
	private String nombreFichero;
	
	@Override
	public String accionesValidas() {
		return "ejecutaSql ejecutaSqlOut generaInserts";
		}

	@Override
	public Object ejecuta(Accion accion, Object valor) throws ErrorGotta  {
		String sql=valor.toString();
        if (accion.p2.equalsIgnoreCase("ejecutasql")) {
        	try{
	        	mMotor.getUsuario().getConexion().ejecuta(sql);
	        	}
        	catch (ErrorGotta e){
        		throw new ErrorGotta(e.getMessage()+". Si estás intentando devolver registros deberías usar el método ejecutaSqlOut");
        		}
        	}
        else if (accion.p2.equalsIgnoreCase("ejecutaSqlOut")) {
        	try {
				return mMotor.getUsuario().getConexion().lookUpSimple(sql);
				} 
        	catch (SQLException e) {
				throw new ErrorGotta(e.getMessage());
				}
        	}
        else if (accion.p2.equalsIgnoreCase("generaInserts")){
        	String texto;
			try {
				texto = generaInsertsNivel(accion, sql);
				} 
			catch (ErrorConexionPerdida e) {
				throw new ErrorGotta(e);
				} 
			catch (SQLException e) {
				throw new ErrorGotta(e);
				}
			
        	if (texto!=null)
        		return texto;
        	}
        return null;
	}

	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta {
		res.setContentType("text/plain");
		res.setHeader("Content-Disposition","attachment; filename="+Constantes.COMILLAS+Util.nombreWeb(nombreFichero)+Constantes.COMILLAS);
		
		ServletOutputStream salida = res.getOutputStream();
		salida.write(fichero.toString().getBytes());
		salida.flush();
		}

	@Override
	public void verifica(Motor motor, String accion, String nombre) throws ErrorGotta {
		verificaAccionValida(accion);
		this.mMotor=motor;
		this.usr=motor.usuario;
		}
	
	private String generaInsertsNivel(Accion acc, String valor) throws ErrorGotta, ErrorConexionPerdida, SQLException{ //nombreTabla|nombreNivel
		ArrayList<String> temp=Util.split(valor, Constantes.PIPE);
		
		NivelDef niveldef=(NivelDef) usr.getApli().getNivelDef(temp.get(1));
		if (niveldef==null)
				throw new ErrorAccionDLL("No existe el nivel '"+temp.get(1)+"'");
		INivel nivel=niveldef.obtenerNivel();
		
		HashMap<String, String> xv=Util.nodoAHash(usr.arbol.getNodoActivo());
		for (Parametro p:nivel.nivelDef.getColParams()){
			String n=p.getNombre();
			if (acc.lote.variables.containsKey(n))
				xv.put(n, acc.lote.variables.get(n).getValor().toString());
			}
		List<Object> valores=nivel.getValores(null, xv);
		
		Filas filas=nivel.lookUp(usr.getConexion(), valores);
		String texto=generaInsertsFilas(temp.get(0), filas);
		
		if (acc.p4!=null)
    		return texto;
    	else {
    		fichero.append( texto);
    		nombreFichero=temp.get(0);
    		usr.productosGenerados.put(""+this.hashCode(), this);
    		return null;
    		}
		}

	private String generaInsertsFilas(String nombreTabla, Filas filas)  {
		String columnas="";
		String COMILLAS="\"";
		for (String nombre : filas.getOrden()){
			columnas+=COMILLAS+nombre+COMILLAS+Constantes.COMA;	
			}
		
		String sql0="insert into "+nombreTabla+ " ("+columnas+") values (";
		String sql2=") ;";
		ArrayList<String> retorno = new ArrayList<String>();
//		retorno.add("-- ALTER SESSION SET NLS_DATE_FORMAT = 'SYYYY-MM-DD HH24:MI:SS';");
//		retorno.add("-- ALTER SESSION SET NLS_TIMESTAMP_FORMAT = 'SYYYY-MM-DD HH24:MI:SS';");
//		retorno.add("-- SET DATEFORMAT ymd;");
		for (Fila fila : filas){
			retorno.add (sql0+valoresFila(fila, filas)+sql2);
			}
		return Util.join(Constantes.vbCrLf, retorno);
		}
	private String valoresFila(Fila fila, Filas filas) {
		ArrayList<String> ret= new ArrayList<String>();
		for (int i=0; i<filas.getOrden().size(); i++){
			ret.add( valorColumna(filas.getTipos().get(i), fila.get(i)) );
			}
		return Util.join(Constantes.COMA, ret);
		}
	private String valorColumna(String tipoJava, Object valor) {
		if (valor==null)
			return "NULL";
		else if (Util.en(tipoJava, "java.lang.String")){
			//duplicamos los ', escapamos los vbCrLf y toda la pesca
			String ret=valor.toString();
			ret=Util.replaceTodos(ret, Constantes.COMILLA, Constantes.COMILLA+Constantes.COMILLA);
			if (ret.contains(Constantes.vbCrLf))
				ret=Util.replaceTodos(ret, Constantes.vbCrLf, Constantes.COMILLA+"+chr(13)+"+Constantes.COMILLA);
			if (ret.contains(Constantes.vbCr))
				ret=Util.replaceTodos(ret, Constantes.vbCr, Constantes.COMILLA+"+chr(13)+"+Constantes.COMILLA);
			
			return Constantes.COMILLA+ret+Constantes.COMILLA;
			}
		else if (tipoJava.equalsIgnoreCase("java.sql.Timestamp") || tipoJava.equalsIgnoreCase("datetime") || tipoJava.equalsIgnoreCase("date")) {
			FechaGotta fg=(FechaGotta)valor;
			return Constantes.COMILLA+fg.formatoEspecial(FechaGotta.formatoFechaHoraISO)+Constantes.COMILLA;
			}
		else { //tal cual, sin formatear 
			return valor.toString(); //Util.desformatearNumero(valor.toString());
			}
//		else throw new ErrorAccionDLL("Error en DLL Gotta_Sql: error al generar insert para el tipo "+tipoGotta+" y el valor "+valor.toString());
		}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}
}

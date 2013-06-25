package es.burke.gotta.dll;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Accion;
import es.burke.gotta.Archivo;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Fila;
import es.burke.gotta.Filas;
import es.burke.gotta.Motor;
import es.burke.gotta.Util;

/* DLL generarFichero, de Embla. 
 * Para generar los ficheros del Banco de España, Banco Particular y Tesoro */
public class GenerarFichero extends DLLGotta {
	private StringBuffer fichero = new StringBuffer();
	private String nombreFichero="";
	private String sql="";
	private String rutaFichero=null;
	
	@Override
	public String accionesValidas() {
		return "Fichero";}

	/* en valor vendrá 'sql | nombreFichero [|rutaGuardado en servidor opcional]'		*/
	@Override
	public Object ejecuta(Accion accion, Object valor) throws ErrorGotta  {
		ArrayList<String> v=Util.split(valor.toString(), Constantes.PIPE);
		this.sql=Util.replaceTodos(v.get(0), "\"", "'");
		this.nombreFichero=v.get(1);
		if (v.size()>2)
			this.rutaFichero=Util.rutaFisicaRelativaOAbsoluta(v.get(2));
			
		Filas filas;
		try 
			{filas=this.usr.getConexion().lookUp(this.sql);} 
		catch (SQLException e) {
			throw new ErrorAccionDLL("Error en DLL GenerarFichero:"+accion.accion+"("+valor.toString()+"). El error que se produjo fue:"+e.getMessage());
			}
		int retorno= ( filas.size()>0 ? 1 : 0 );
		if (filas.size()>0) {
			for (Fila f : filas)
				fichero.append(""+f.get(0)+Constantes.vbCrLf);
			
			if (this.rutaFichero==null)
				usr.productosGenerados.put(""+this.hashCode(), this);
			else {
				Archivo ar=new Archivo(this.usr, this.rutaFichero);
				ar.escribeArchivoTexto(fichero.toString(), null);
				return ar.rutaFisica;
				}
			}
		return retorno;
		}

	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta {
		res.setContentType("text/plain");
		res.setCharacterEncoding(Constantes.ISO88591);
		res.setHeader("Content-Disposition","attachment; filename="+Constantes.COMILLAS +Util.nombreWeb(nombreFichero)+Constantes.COMILLAS);
		
		ServletOutputStream salida = res.getOutputStream();
		salida.write(fichero.toString().getBytes(Constantes.ISO88591));
		salida.flush();
		}
	
	@Override
	public void verifica(Motor motor, String accion, String nombre) throws ErrorGotta {
		verificaAccionValida(accion);
		this.mMotor=motor;
		this.usr=motor.usuario;
		}

	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}
}

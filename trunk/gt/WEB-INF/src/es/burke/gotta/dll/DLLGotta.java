package es.burke.gotta.dll;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.*;
import es.burke.gotta.Constantes.TipoMensajeGotta;
public abstract class DLLGotta {
	public Usuario usr;
	public Motor mMotor;
	public boolean síncrono=false;
	public abstract String accionesValidas();//acciones separadas por un espacio
	
	public void verificaAccionValida(String accion) throws ErrorAccionDLL {
		if(accion==null)
			throw new ErrorAccionDLL("Accion nula");
		if(!(Constantes.ESPACIO+accionesValidas().toLowerCase()+Constantes.ESPACIO).contains(Constantes.ESPACIO+accion.toLowerCase()+Constantes.ESPACIO))
			throw new ErrorAccionDLL("No existe la accion "+accion+ " en la clase "+this.getClass().toString()+". Las acciones disponibles son: "+accionesValidas());
		}
	
	public abstract void verifica(Motor motor, String accion, String nombre) throws ErrorGotta;
	public abstract Object revivir(Accion accion, Object valor) throws ErrorGotta;
	public abstract Object ejecuta(Accion accion, Object valor) throws ErrorGotta;
	public abstract void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta,ServletException;
	
	public void añadeMSG(String msg, TipoMensajeGotta tipo){
		this.usr.añadeMSG(msg, tipo);
		}
	protected Long getHoraActual(){
		return new java.util.Date().getTime();
		}
}

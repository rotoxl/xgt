package es.burke.gotta.dll;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Constantes;
import es.burke.gotta.Accion;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.ITabla;
import es.burke.gotta.Motor;
import es.burke.gotta.Util;

public final class GottaBDExterna extends DLLGotta {
	Accion accion;
		
	@Override
	public Object ejecuta(Accion xAccion, Object valor) throws ErrorGotta  {
		this.accion=xAccion;
		
		ArrayList<String> temp=Util.split(valor.toString(), Constantes.PIPE); //prefijo|tabla
		
		String prefijo; //prefijo para buscar en dic_configuracion
		String nombreTabla;
		prefijo=temp.get(0);
		nombreTabla=temp.get(1);
		
		lanzaSQLDesdeTabla(prefijo, nombreTabla);
		return Motor.Resultado.OK;
		}
	public void lanzaSQLDesdeTabla(String prefijo, String nombreTabla) throws ErrorGotta {
		String driverClassName, url, usu, pwd;	
		driverClassName=this.accion.motor.getApli().getDatoConfig(prefijo+"_driverClassName");
		url=this.accion.motor.getApli().getDatoConfig(prefijo+"_url");
		usu=this.accion.motor.getApli().getDatoConfig(prefijo+"_user");
		pwd=this.accion.motor.getApli().getDatoConfig(prefijo+"_password");
		
		ConexionExterna conn=new ConexionExterna(driverClassName, url, usu, pwd);
		ITabla t=this.accion.motor.tablas.get(nombreTabla);
		
		String nombreCol=t.tdef.getColumnas().get(0).getCd();
		try {
		for (int i=0; i<t.getNumRegistrosCargados(); i++){
			t.setRegistroActual(i);
			String sql=t.getValorCol(nombreCol).toString();
			
				conn.ejecuta(sql);
				}
			}
		catch (SQLException e) {
			throw new ErrorGotta(e.getMessage());
			}
		finally {
			conn.cierra();
			}
		}
	
	@Override
	public void verifica(Motor xMotor, String xAccion, String nombre) throws ErrorGotta {
		verificaAccionValida(xAccion);
		this.mMotor=xMotor;
		this.usr=mMotor.usuario;
		}
	@Override
	public String accionesValidas() {
		return "lanzarSQL";
		}		
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) 
			throws IOException, ErrorGenerandoProducto  {
		/**/
		}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}
			
}
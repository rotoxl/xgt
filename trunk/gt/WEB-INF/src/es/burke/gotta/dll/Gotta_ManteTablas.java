package es.burke.gotta.dll;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Accion;
import es.burke.gotta.ColumnaDef;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.ITabla;
import es.burke.gotta.ITablaDef;
import es.burke.gotta.Motor;
import es.burke.gotta.TablaDef;
import es.burke.gotta.Util;

public class Gotta_ManteTablas extends DLLGotta {
	private ITabla tabla;
	private ITablaDef tdef;

	@Override
	public String accionesValidas() {
		return "registro eliminar editar modificar";
		}
	@Override
	public Object ejecuta(Accion accion, Object valor) throws ErrorGotta {
		ITabla td=mMotor.tramActivo().getTb();
		if (td==null)
			throw new ErrorAccionDLL("Para utilizar el mantenimiento de tablas es necesario que indiques una tabla base en el camino.");
		if (td.getValorCol("nombre")==null)
			throw new ErrorGotta("Para utilizar el mantenimiento de tablas es necesario que indiques una tabla base y sus respectivas letras para el detalle");
			
		String nombreTabla=td.getValorCol("nombre").toString();
		tabla = mMotor.ponTabla(nombreTabla);
		tdef = tabla.tdef;
		try{
			if (accion.accion.equalsIgnoreCase("registro"))
				return registro();
			if (accion.accion.equalsIgnoreCase("eliminar"))
				return eliminar((String)valor);
			if (accion.accion.equalsIgnoreCase("editar"))
				return editar((String)valor);
			}
		catch(SQLException e){
			throw new ErrorGotta(e);
			}
		return null;
	}
	
	private Motor.Resultado eliminar(String valor) throws ErrorGotta {
		tabla.cargarRegistros(null, Util.splitObjetos(valor.substring(1), Constantes.PUNTO3));
		tabla.delete();
		return Motor.Resultado.OK;
		}
	private Motor.Resultado registro() throws ErrorGotta, SQLException{
		tabla.addNew();
		return this.montaFRM(false);
		}
	private Motor.Resultado editar(String valor) throws ErrorGotta, SQLException{
		tabla.cargarRegistros(null, Util.splitObjetos(valor.substring(1), Constantes.PUNTO3));
		return this.montaFRM(true);
		}
	private Motor.Resultado montaFRM(boolean bloquearClave) throws ErrorGotta, SQLException{ 
		mMotor.accionFRM("principio", tdef.getDs());
		
		ArrayList<String> columnas=tdef.columnas.claves;
		if (tdef instanceof TablaDef)
			columnas=((TablaDef)tdef).columnasFisicas(mMotor.usuario.getConexion());
		for (String col: columnas){
			ColumnaDef colDef=tdef.getColumna(col);
			String ds=colDef.getDs();
			if (ds==null || ds.equals(""))
				ds=colDef.getCd();
			
			String asteriscoTablaCampo="*."+tdef.getCd()+"."+col;
			if (tdef.getCampoClave().getColumna(col)!=null) {
				if ( tdef.getCampoClave().getColumna(col).getTipo().equals(Constantes.AUTO) ) {
					//pass
					}
				else if (bloquearClave)
					mMotor.presentarPedirCampo("X!", asteriscoTablaCampo, ds, "", null);
				else
					mMotor.presentarPedirCampo("X!?", asteriscoTablaCampo, ds, "", null);
				}
			else
				mMotor.presentarPedirCampo("X!?", asteriscoTablaCampo, ds, "*", null);
			}
		return mMotor.accionFRM("fin", null);
	}
	
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException {
		// pass
	}

	@Override
	public void verifica(Motor motor, String accion, String nombre) throws ErrorGotta {
		this.mMotor=motor;
		this.usr=motor.usuario;
		this.verificaAccionValida(accion);
	}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}
	
}


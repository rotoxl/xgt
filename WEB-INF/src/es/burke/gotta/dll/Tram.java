package es.burke.gotta.dll;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Accion;
import es.burke.gotta.Coleccion;
import es.burke.gotta.Conexion;
import es.burke.gotta.Constantes;
import es.burke.gotta.ControlFrm;
import es.burke.gotta.ErrorArrancandoAplicacion;
import es.burke.gotta.ErrorConexionPerdida;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.FechaGotta;
import es.burke.gotta.GestorMetaDatos;
import es.burke.gotta.Motor;
import es.burke.gotta.Tabla;
import es.burke.gotta.TablaDef;
import es.burke.gotta.Tramite;
import es.burke.gotta.Util;
import es.burke.gotta.Constantes.TipoMensajeGotta;

public final class Tram extends DLLGotta {
	@Override
	public String accionesValidas() {		
		return "recargaTrabajosProgramados bloque textoAyuda globoAyuda consejo commit lanzaTramite lanzaTramitePendiente lanzaTramiteEjecutado renumerar refrescaControl";//;"lanzaTramite Commit infoAyuda";
		}
	
	@Override
	public Object ejecuta(Accion xAccion, Object valor) throws ErrorGotta  {
		String x=xAccion.accion;
		if (x.equalsIgnoreCase("recargaTrabajosProgramados")){
			this.mMotor.getApli().inicializaTrabajos(2);
			}
		else if (x.equalsIgnoreCase("bloque")){
			this.mMotor.frmDinamico.anhadeBloque(xAccion.p3, xAccion.p4);
			}
		else if (x.equalsIgnoreCase("refrescaControl")){
			String ctl=valor.toString();// esto debería ser un número de control de lista/rd o algo que tire de nivel 
			this.mMotor.hayQueRefrescar=ctl;
			}
		else if (x.equalsIgnoreCase("textoAyuda") || x.equalsIgnoreCase("globoAyuda")){
			Coleccion<ControlFrm> controles=this.mMotor.frmDinamico.controles;
			String clave=controles.claves.get(controles.size()-1);
			ControlFrm ctl=this.mMotor.frmDinamico.controles.get(clave);
			if (x.equalsIgnoreCase("globoAyuda"))
				ctl.globoAyuda=valor.toString();
			else
				ctl.textoAyuda=valor.toString();
			}
		else if (x.equalsIgnoreCase("consejo")){ //texto|claseCSS
			ArrayList<String> temp=Util.split(valor.toString(), Constantes.PIPE);
			if (temp.size()<2)
				temp.add("informacion");
			this.mMotor.frmDinamico.anadirConsejoMD(this.mMotor.usuario, temp.get(0), temp.get(1));
			}
		else if (x.equalsIgnoreCase("renumerar")){
			try {
				this.renumerar(valor.toString());
				} 
			catch (ErrorGotta e) {
				this.mMotor.sacaError(e);
				throw new ErrorGotta("Error renumerando",e);
				}
			}
		else if (x.equalsIgnoreCase("commit")){
			mMotor.usuario.añadeMSG("Arrancamos transacción desde DLL", TipoMensajeGotta.info);
			this.mMotor.tramActivo().hazCommit();
			mMotor.usuario.añadeMSG("Transacción desde DLL finalizada", TipoMensajeGotta.info);
			}
		else if (x.equalsIgnoreCase("lanzaTramite")){
			// camino|tramite|objetoBase
			ArrayList<String>temp=Util.split(valor.toString(), Constantes.PIPE);
			String strCamino, tramite, strObjetoBase=Constantes.CAD_VACIA;
			
			strCamino=temp.get(0);
			tramite=temp.get(1);
			if (temp.size()>2)strObjetoBase=temp.get(2);
			
			long camino= strCamino!=null? Integer.parseInt(strCamino):this.mMotor.tramActivo().camino;
			ArrayList<Object> objetoBase=Util.splitObjetos(strObjetoBase, Constantes.SEPARADOR);
			
			Tramite tram= new Tramite(mMotor.lote, camino, tramite, objetoBase, null);
			tram.nodoActivo=xAccion.motor.tramActivo().nodoActivo;
			mMotor.lote.añadeTramite(tram);
			mMotor.lote.devolverDepuradorCompleto=true;
			}
		else if (x.equalsIgnoreCase("lanzaTramitePendiente") || x.equalsIgnoreCase("lanzaTramiteEjecutado")){
			// camino|tramite|objetoBase|F_Pendiente
			ArrayList<String>temp=Util.split(valor.toString(), Constantes.PIPE);
			String strCamino, tramite, strObjetoBase=Constantes.CAD_VACIA;
			
			strCamino=temp.get(0);
			tramite=temp.get(1);
			strObjetoBase=temp.get(2);
			FechaGotta f_pendiente=new FechaGotta(temp.get(3));
			long camino= strCamino!=null? Integer.parseInt(strCamino):this.mMotor.tramActivo().camino;
			
			ArrayList<Object> objetoBase=Util.splitObjetos(strObjetoBase, Constantes.SEPARADOR);
			//
			ArrayList<Object> objetoTT=Util.splitObjetos(strObjetoBase, Constantes.SEPARADOR);
			objetoTT.add(f_pendiente);
			
			String modo= x.equalsIgnoreCase("lanzaTramitePendiente") ? "tp" : "te";
			Tramite tram= new Tramite(mMotor.lote, camino, tramite, objetoBase, objetoTT, null, modo);


			//cargamos la tabla base como si estuvieramos en una lista de tp/te 
		 	String tb; 
		 	try { 
			 	tb = GestorMetaDatos.getTablaBase(usr.getConexion(), usr.getApli().getEsquema(), camino); 
			 	}  
		 	catch (ErrorConexionPerdida e) { 
			 	throw new ErrorGotta(e.getMessage()); 
			 	}  
		 	catch (ErrorArrancandoAplicacion e) { 
			 	throw new ErrorGotta(e.getMessage()); 
			 	}  
		 	catch (SQLException e) { 
		 		throw new ErrorGotta(e.getMessage()); 
		 		} 
		 	tram.nodoActivo.nombretb=tb; 
		 	Tabla t=(Tabla)tram.nodoActivo.getTabla(tb); 
		 	t.cargarRegistros(t.tdef.getCampoClave(), objetoBase); 
		 	tram.nodoActivo.putTabla(t); 
		 	
			tram.nodoActivo=xAccion.motor.tramActivo().nodoActivo;
			mMotor.lote.añadeTramite(tram);
			mMotor.lote.devolverDepuradorCompleto=true;
			}
			
		return Motor.Resultado.OK;
		}
	@Override
	public void verifica(Motor xMotor, String xAccion, String nombre) throws ErrorGotta {
		verificaAccionValida(xAccion);
		this.mMotor=xMotor;
		this.usr=mMotor.usuario;
		}

	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta, ServletException {
		//pass
		}
	
	private void renumerar(String parametro) throws ErrorGotta {
		String[] c_t=parametro.split("\\|");
		String c=c_t[0];
		String t=c_t[1];
		renumerar(this.mMotor, c, t);
		}
	
	public static void renumerar(Motor motor, String c, String t) throws ErrorGotta {
		final int CM = 1;
		final int TR = 2;
		final int OP = 3;

		
		Conexion conexion = motor.getUsuario().getConexion();
		Connection conn = conexion.obtenerConexionJR();
		try{
			conn.setAutoCommit(false);
			TablaDef tablaDef = (TablaDef) motor.getEsquema().getTablaDef("tra_acciones");
			String tra_acciones = tablaDef.getEscritura();
			String sql = "select cd_camino, cd_tramite, cd_operacion FROM "+tra_acciones+" WHERE CD_Camino=? AND CD_Tramite=? order by cd_operacion";
			PreparedStatement stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,	ResultSet.CONCUR_UPDATABLE);
			stmt.setLong(CM, Long.parseLong(c));
			stmt.setString(TR, t);
			ResultSet srs = stmt.executeQuery();
			boolean seguir = srs.next();
			long n=srs.getInt(OP)-1000;
			long cuenta=0;
			while (seguir){
				srs.updateLong(OP,n);
				srs.updateRow();
				n++;
				cuenta++;
				seguir = srs.next();
				}
			conn.commit();
			srs.close();
			stmt.close();
			
			
			stmt = conn.prepareStatement(sql+" desc",ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
			stmt.setLong(CM, Long.parseLong(c));
			stmt.setString(TR, t);
			srs = stmt.executeQuery();
			n=cuenta*10;
			while (srs.next()){
				srs.updateLong(OP,n);
				srs.updateRow();
				n-=10;
				}
			srs.close();
			stmt.close();
			conn.commit();
			}
		catch (SQLException e){
			throw new ErrorGotta(e.getMessage());
			}
		finally{
			conexion.liberarConexionJR(conn);
			}
		}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}

}

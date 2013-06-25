package es.burke.gotta.dll;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.burke.gotta.Accion;
import es.burke.gotta.Aplicacion;
import es.burke.gotta.Camino;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Fila;
import es.burke.gotta.Filas;
import es.burke.gotta.GestorMetaDatos;
import es.burke.gotta.ITabla;
import es.burke.gotta.ITablaDef;
import es.burke.gotta.Motor;
import es.burke.gotta.Usuario;
import es.burke.gotta.Util;

public final class DLLMapaTramitacion extends DLLGotta {
	private ArrayList<Long> listaCaminos=new ArrayList<Long>();
	
	public ArrayList<Object> objetoBase;
	public String tramCentrado=null;
	
	public String pen="", eje="";
	public ArrayList<Camino> caminos= null;
	
	@Override
	public Object ejecuta(Accion accion, Object valor) throws ErrorGotta  {
		ArrayList<String> v=Util.split(valor.toString()+"|||",Constantes.PIPE); // 610|ObjetoBase|TrámiteCentral'
	
		this.listaCaminos.add( Long.parseLong(v.get(0)) );
		objetoBase=Util.splitObjetos(v.get(1), Constantes.SEPARADOR);
		tramCentrado=v.get(2);
		
		montaTodo();
		return null;
		}
	public String ejecutaPorCamino(Usuario usu, Long camino, ArrayList<Object> xobjetoBase) {
		this.usr=usu;
		this.listaCaminos.add(camino);
		this.objetoBase=xobjetoBase;
		montaTodo();
		return this.hashCode()+"";
		}
	private void montaTodo() {
		this.caminos= new ArrayList<Camino>();
		
		Aplicacion apli=this.usr.getApli();
		
		for (Long idcamino:listaCaminos) {
			String nombre="x"+idcamino;
			if (!apli.caminos.containsKey(idcamino)) {
				Camino cam=new Camino(usr, idcamino) ;
				apli.caminos.put(nombre, cam);
				}
			this.caminos.add(apli.caminos.get(nombre));
		
//			if (objetoBase != null && objetoBase.size()>0) 
//				rellenatramPENyEJE(idcamino, objetoBase);
			}
		
		usr.productosGenerados.put(""+this.hashCode(), this);
		}
	
	public  static JSONObject tramPENyEJE(Long camino, ArrayList<Object> ob, Usuario usr) throws ErrorGotta, JSONException {
		Filas f=GestorMetaDatos.leeTRA_Caminos(usr.getConexion(), usr.getApli().getEsquema(), camino);
		String _tt=null;
		if (f.size()>0)
			_tt=Util.toString( f.get(0).get("TablaTramites") );
		
		if (_tt==null)
			return null;
		
		ITablaDef td=usr.getApli().getEsquema().getTablaDef(_tt);
		ITabla t= td.newTabla(usr);
		
		t.cargarRegistros(null, ob);
		JSONArray ejecutados=new JSONArray(), pendientes=new JSONArray();
		
		for (Fila fila : t.datos) {
			if ( fila.get("F_Ejecucion") == null || fila.get("F_Ejecucion").equals("")	){
				JSONObject resumen=new JSONObject()
					.put("cd", fila.gets("CD_Tramite"))
					.put("cd_usuario", fila.gets("CD_Usuario_Pendiente"))
					.put("fecha", fila.gets("F_Pendiente"));		
				
				pendientes.put(resumen);
				}
			else {
				JSONObject resumen=new JSONObject()
					.put("cd", fila.gets("CD_Tramite"))
					.put("cd_usuario", fila.gets("CD_Usuario_Ejecucion"))
					.put("fecha", fila.gets("F_Ejecucion"));		
			
				ejecutados.put(resumen);
				}
			}
		JSONObject ret =new JSONObject();
		ret.put("pendientes", pendientes)
			.put("ejecutados", ejecutados);
		
		return ret;
		}
//	private void rellenatramPENyEJE(Long camino, ArrayList<Object> ob) throws ErrorGotta {
//		HashMap<String, String> ret=tramPENyEJE(camino, ob, this.usr);
//		
//		this.pen+=ret.get("pendientes").toString();
//		this.eje+=ret.get("ejecutados").toString();
//		}
	@Override
	public void verifica(Motor motor, String accion, String nombre) throws ErrorGotta {
		verificaAccionValida(accion);
		this.mMotor=motor;
		this.usr=motor.usuario;
		}
	
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) 
		throws IOException, ErrorGenerandoProducto {
		try 
			{inf2.getServletContext ().getRequestDispatcher ("/mapa.jsp?prod="+numprod).forward (req, res);} 
		catch (ServletException e) 
			{this.mMotor.sacaError(e);}
		}

	@Override
	public String accionesValidas() 
		{return "mostrar";}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}
}


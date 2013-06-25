package es.burke.gotta;

import java.util.List;

import es.burke.gotta.Constantes.TipoMensajeGotta;

public class Conexion extends ConexionLight {
	public Conexion(Aplicacion ap, int maxElementos){
		super(ap.getCd(), ap.getPrefijo());
		this.apañaConexion(ap, maxElementos);
		}
	public Conexion(Aplicacion ap) {
		super(ap.getCd(), ap.getPrefijo());
		this.apañaConexion(ap, -1);
		}
	public Conexion(Aplicacion ap, String idUSuario) {
		super(ap.getCd(), ap.getPrefijo());
		this.apañaConexion(ap, -1);
		this.idUsuario=idUSuario;
		}
	public Conexion(Aplicacion ap, String idUSuario, int maxElementos) {
		super(ap.getCd(), ap.getPrefijo());
		this.apañaConexion(ap, maxElementos);
		this.idUsuario=idUSuario;
		}
	private void apañaConexion(Aplicacion ap, int MaxElementos){
		this.maxElementos=MaxElementos;
		this.tipoBD=ap.getMarcaBaseDatos();
		}
	@Override
	protected void println(String texto) {
			this.getApli().println(texto);
		}
	@Override
	protected void añadeSQL(String sql, List<Object> parametros, Long t1, String objetoSQL, int numFilas, TipoMensajeGotta tipo) {
		try {
			if (tipo==null) tipo=TipoMensajeGotta.sql;
			Aplicacion ap=this.getApli();
			if (ap!=null) ap.añadeSQL(this.getUsr(), sql, parametros, t1, getHoraActual(), objetoSQL, numFilas, tipo);
			} 
		catch (ErrorArrancandoAplicacion e) {
			/* aquí no puede fallar nada*/
			}
		}

	@Override
	protected void añadeSQL(String sql, List<Object> parametros, Long t1, String objetoSQL, int numFilas) {
		this.añadeSQL(sql, parametros, t1, objetoSQL, numFilas, TipoMensajeGotta.sql);
		}
	
	@Override
	protected void añadeSQL(String msg, TipoMensajeGotta tipo) {
		try {
			if (tipo==null) tipo=TipoMensajeGotta.sql;
			Aplicacion ap=this.getApli();
			if (ap!=null) ap.añadeMSG(this.getUsr(), msg, tipo);
			} 
		catch (ErrorArrancandoAplicacion e) {
			/* aquí no puede fallar nada*/
			}
		}
	}

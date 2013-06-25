package es.burke.gotta;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;

import es.burke.gotta.Constantes.MarcaBaseDatos;
import es.burke.gotta.Constantes.TipoMensajeGotta;
	
public class ConexionLight {
	static public class NuloConTipo  {
		String tipo;
		public NuloConTipo(String tipo)
		{this.tipo=tipo;}
		@Override
		public boolean equals(Object otroObjeto) {
			if (otroObjeto==null || otroObjeto instanceof NuloConTipo)
				return true;
			return false;
			}
		@Override
		public String toString()
		{return Constantes.CAD_VACIA;}
	}

	public String idUsuario="-1"; //en este idSesion falso guardamos los datos del monitor del usuario "Gotta"
	
	public Connection conexionPreferente=null;
		
	int maxElementos=-1;
	protected String prefijo="";
	protected String nombreOrigenDatos;
	protected final String ret_cursor="(resultset 0,ret_cursor)";
	
	public Constantes.MarcaBaseDatos tipoBD=MarcaBaseDatos.SinEspecificar;
	
	public ConexionLight(String _nombreOrigenDatos, String prefijo) throws ErrorConexionPerdida{
		this.nombreOrigenDatos=_nombreOrigenDatos;
		this.prefijo=prefijo;
		
		this.tipoBD=this.sacaMarcaBD();
		}
	
	protected MarcaBaseDatos sacaMarcaBD() throws ErrorConexionPerdida{
		Connection con=null;
		try {
			con=this.obtenerConexion();
			DatabaseMetaData md = con.getMetaData();
			String dpn = md.getDatabaseProductName().toLowerCase();
			
			if (dpn.contains("oracle"))
				return Constantes.MarcaBaseDatos.Oracle;
			else if (dpn.contains("mysql"))
				return Constantes.MarcaBaseDatos.MySQL;
			else if (dpn.contains("access"))
				return Constantes.MarcaBaseDatos.MSAccess;
			else if (dpn.contains("sql server"))
				return Constantes.MarcaBaseDatos.MSSQL;
			else if (dpn.contains("informix"))
				return Constantes.MarcaBaseDatos.Informix;
			else if (dpn.contains("sqlite"))
				return Constantes.MarcaBaseDatos.sqlite;
			else
				return Constantes.MarcaBaseDatos.SinEspecificar;
			} 
		catch (SQLException e) {
			return Constantes.MarcaBaseDatos.SinEspecificar;
			}
		catch (ErrorConexionPerdida e) {
			throw e;
			}
		finally {
			this.liberarConexion(con);
			}
		}
	public String getPrefijo(){
		return this.prefijo;
		}
	
	public String fnCastAsChar(String s){
		return fnCastAsChar(s, Constantes.CAD_VACIA);	
		}
	public String fnCastAsChar(String s, String tipoDato){
		if (this.tipoBD==Constantes.MarcaBaseDatos.Oracle){
			if (tipoDato.equals(Constantes.DATE))
				return "to_char("+s+", 'dd/mm/yyyy hh24:mi:ss')";
			return "to_char("+s+")";
			}
		return "{fn convert("+s+", SQL_VARCHAR)}";	
		}
	public String ucase(String s){
		if(this.tipoBD==Constantes.MarcaBaseDatos.Informix)
			return "(UPPER("+s+"))";
		
		if(this.tipoBD==Constantes.MarcaBaseDatos.sqlite)
			return "(UPPER("+s+"))";
		
		return "{fn UCASE("+s+")}";
		}
	public Connection obtenerConexionJR() throws ErrorConexionPerdida{
		return obtenerConexion();}
	
	protected Connection obtenerConexion() throws ErrorConexionPerdida {
		try  	{
			if (this.conexionPreferente!=null)
				return this.conexionPreferente;
				
			return PoolAplicaciones.getConnection(nombreOrigenDatos);} 
		catch (NamingException e) {
			throw new ErrorConexionPerdida(e.getExplanation());}
//		catch (SQLException e) {
//			throw new ErrorConexionPerdida("Error al obtener conexión", e);
//			}
		}
	public void liberarConexionJR(Connection conex){
		liberarConexion(conex);
		}
	protected void liberarConexion(Connection conex){
		//si hay una transaccion en curso no libero
		if (conex!=null && !(conex==this.conexionPreferente) ) {
			try
				{
				if (!conex.isClosed())				
					conex.close();
				}
			catch(SQLException sqle){
				System.err.println("LiberarConexion: "+sqle.getMessage());
				}
			}
		}
	private void ejecutaSQLs(Connection conn, ArrayList<SqlParam> SqlPendientes)
			throws SQLException, ErrorCampoNoExiste,
			ErrorFilaNoExiste, ErrorTiposNoCoinciden {
		for (SqlParam sqlparam : SqlPendientes){
			Boolean identity_rellena=null;
			if (sqlparam.colIdentidad!=null)
				identity_rellena=sqlparam.tabla.getValorCol(sqlparam.colIdentidad)!=null;
			if (identity_rellena!=null && identity_rellena && this.tipoBD==MarcaBaseDatos.MSSQL){
				String setIdentityInsert = "set identity_insert "+sqlparam.tabla.getTablaDef().getEscritura();
				sqlparam.SQL = setIdentityInsert+" ON "+ sqlparam.SQL + " " + setIdentityInsert+" OFF ";
			}
			lookUpResultSetInterno(conn, sqlparam.SQL, sqlparam.params,true);
			if (identity_rellena!=null && !identity_rellena){
				ResultSet rs=(ResultSet)lookUpResultSetInterno(conn, "SELECT @@identity", null, false);
				Filas filas=montaDiccionarios(rs, 1);
				Object valorIdentidad=filas.get(0).get(0);
				sqlparam.tabla.setValorCol(sqlparam.colIdentidad, valorIdentidad);
				}
			}
	}
	public void ejecutaLote(ArrayList<SqlParam> SqlPendientes) throws ErrorVolcandoDatos, ErrorCampoNoExiste, ErrorFilaNoExiste, ErrorTiposNoCoinciden{
		ejecutaLote(null,SqlPendientes);
	}

	public void ejecutaLote(Connection pConn, ArrayList<SqlParam> SqlPendientes) throws ErrorVolcandoDatos, ErrorCampoNoExiste, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorArrancandoAplicacion{
		if (SqlPendientes.size()==0)
			return;
		Connection conn;
		if(pConn==null)
			conn=this.obtenerConexion();
		else
			conn=pConn;
		//añadeSQL("Inicio transacción", TipoMensajeGotta.info);
		try {
			conn.setAutoCommit(false);
			ejecutaSQLs(conn, SqlPendientes);
			if(pConn==null)
				conn.commit(); 
			}
		catch (SQLException e) {
			añadeSQL("Error: "+e.getMessage(), TipoMensajeGotta.alerta);
			throw new ErrorVolcandoDatos(e.getMessage());
			}
		finally{
			try {
				if(pConn==null)
					conn.rollback();
			} 
			catch (SQLException e) {
				this.getApli().println("ERROR muy grave"+e.getMessage());}
			if(pConn==null)
				liberarConexion(conn);
			}
		}
	public void ejecutaLote(ArrayList<String> SqlPendientes,ArrayList<ArrayList<Object>> ParamsPendientes) throws ErrorVolcandoDatos, ErrorConexionPerdida, ErrorCampoNoExiste, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorArrancandoAplicacion {
		ArrayList<SqlParam> ret= new ArrayList<SqlParam>();
		for (int i=0; i<SqlPendientes.size(); i++){
			SqlParam sqlparam=new SqlParam( SqlPendientes.get(i), ParamsPendientes.get(i));
			ret.add(sqlparam);
			}
		ejecutaLote(ret);
		}
	
	public void ejecuta(String sql) throws ErrorVolcandoDatos{
		this.ejecuta(sql, null);
		}
	public void ejecuta(String sql, ArrayList<Object> params) throws ErrorVolcandoDatos{
		Connection conn=this.obtenerConexion();
		try {
			lookUpResultSetInterno(conn, sql, params, true);
			añadeSQL(sql, params, getHoraActual());
			}
		catch (SQLException e) { 
			añadeSQL("Error: "+e.getMessage(), TipoMensajeGotta.alerta);
			throw new ErrorVolcandoDatos(e.getMessage());
			}
		finally {
			liberarConexion(conn);
			}
		}
	
	protected Long getHoraActual(){
		return new java.util.Date().getTime();}
	
	public Filas lookUp(String sql, List<Object> parametros) throws SQLException{
		return lookUp(sql, parametros, false); 
		}
	public Filas lookUp(String sql, List<Object> parametros, boolean usarTope) throws SQLException{
		Connection conn=this.obtenerConexion();
		try {
			Long t=getHoraActual();
			this.println("ejecutando "+sql+parametros);
			Object rs=lookUpResultSetInterno(conn,sql, parametros,false);
			Filas ret;
			
			int tope=-1;
			if (usarTope)
				tope=getTope();
			
			if (rs instanceof ResultSet)
				ret= montaDiccionarios((ResultSet)rs, tope);
			else
				ret= montaDiccionarios(rs, tope);
				
			añadeSQL(sql, parametros, t, null, ret.size(), TipoMensajeGotta.sql);
			return ret;
			}
		catch(SQLException e){
			añadeSQL("Error: "+e.getMessage() + ". El error se produjo al ejecutar "+sql, parametros, null, null, -1, TipoMensajeGotta.alerta);
			throw(e);
			}
		finally {
			this.liberarConexion(conn);
			}
	}
	
	public Object lookUpSimple(String sql) throws SQLException
		{return lookUpSimple(sql, new ArrayList<Object>());}
	public Object lookUpSimple(String sql, List<Object> parametros) throws SQLException{
		Filas rs = lookUp(sql, parametros);
		if (rs.size()>0){
			Fila fila= rs.get(0);
			return fila.get(0);
			}
		return null;
	}
	
	public Filas lookUp(String sql) throws SQLException{
		return lookUp(sql, new ArrayList<Object>());
		}
	
	public Object lookUpResultSetInterno(Connection conn, String sql, List<Object> parametros, boolean dml) throws SQLException {	
		boolean DIOS_MIO_ES_PROC_DE_ORACLE=false, 
				LLEVA_RESULTSET=false;

		if (conn==null)
			throw new ErrorConexionPerdida("Ha habido un problema con la conexión a la base de datos");
		
		
		if (sql.contains(ret_cursor)){
			sql=Util.replaceUno(sql, ret_cursor, "?");
			DIOS_MIO_ES_PROC_DE_ORACLE=true;
			LLEVA_RESULTSET=true;
			}
		else if (tipoBD.equals(MarcaBaseDatos.Oracle)){
			if (sql.toLowerCase().startsWith("{call")) {
				sql = insertarParametroOracle(sql);
				DIOS_MIO_ES_PROC_DE_ORACLE=true;
				LLEVA_RESULTSET=true;
				}
			else if (sql.toLowerCase().startsWith("{")){
				sql = insertarParametroOracle(sql);
				DIOS_MIO_ES_PROC_DE_ORACLE=true;
				}
			}
		PreparedStatement stmt ;
		if(tipoBD.equals(MarcaBaseDatos.Oracle))
			stmt=conn.prepareCall(sql);
		else
			stmt=conn.prepareStatement(sql);

		if (parametros==null)
			parametros=new ArrayList<Object>();

		int i =0;
		for (Object p:parametros) {
			i++;
			if (p instanceof NuloConTipo) {
				NuloConTipo t=(NuloConTipo)p;
				if ( t.tipo.equals(Constantes.STRING) )
					stmt.setNull(i, java.sql.Types.VARCHAR);
				else if ( t.tipo.equals(Constantes.BOOLEAN) )
					stmt.setNull(i, java.sql.Types.BOOLEAN);
				
				else if ( t.tipo.equals(Constantes.CURRENCY) )
					stmt.setNull(i, java.sql.Types.DECIMAL);
				
				else if ( t.tipo.equals(Constantes.DOUBLE) )
					stmt.setNull(i, java.sql.Types.DOUBLE);
				
				else if ( t.tipo.equals(Constantes.LONG) )
					stmt.setNull(i, java.sql.Types.DECIMAL);
				
				else if ( t.tipo.equals(Constantes.INTEGER) )
					stmt.setNull(i, java.sql.Types.INTEGER);
				
				else if ( t.tipo.equals(Constantes.DATE) )
					stmt.setNull(i, java.sql.Types.DATE);
				
				else if ( t.tipo.equals(Constantes.MEMO) )
					stmt.setNull(i, java.sql.Types.LONGVARCHAR);
				
				else
					throw new SQLException(t.tipo+" no está contemplado en los tipos anulables");
				}
			else if (p == null)
				stmt.setNull(i, java.sql.Types.VARCHAR);
			else if (p instanceof Boolean)
				stmt.setBoolean(i, (Boolean)p);
			else if (p instanceof String)
				stmt.setString(i, (String)p);
			else if (p instanceof Short)
				stmt.setShort(i, (Short)p);
			else if (p instanceof Long)
				stmt.setLong(i, (Long)p);	
			else if (p instanceof Integer)
				stmt.setInt(i, (Integer)p);
			else if (p instanceof BigInteger)
				stmt.setLong(i, ((BigInteger)p).longValue() );	
			else if (p instanceof BigDecimal && this.tipoBD==MarcaBaseDatos.sqlite)
				stmt.setLong(i, ((BigDecimal)p).longValue());
			else if (p instanceof BigDecimal)
				stmt.setBigDecimal(i, (BigDecimal)p);
			else if (p instanceof Double)
				stmt.setDouble(i, (Double)p);
			else if (p instanceof FechaGotta) {
				FechaGotta gc=(FechaGotta)p;
				java.sql.Timestamp t=new java.sql.Timestamp(gc.getTimeInMillis());
				stmt.setTimestamp(i, t);						
				}
			else
				throw new SQLException("Este tipo no está: "+p+" ("+p.getClass().getSimpleName()+")");
			}
			
		if (dml) {
			try {
				stmt.executeUpdate();
				añadeSQL(sql, parametros, null, null, -1, TipoMensajeGotta.sqlEscribir);
				stmt.close();
				}
			catch(SQLException e){
				añadeSQL(e.getMessage()+" El error se produjo al ejecutar "+sql, parametros, null, null, -1, TipoMensajeGotta.errorSQL);
				throw(e);
				}
			
			return null;
			}
		try {
			ResultSet rs ;
			if (DIOS_MIO_ES_PROC_DE_ORACLE){
				CallableStatement cstmt = (CallableStatement) stmt;
				
				int tipoParam;
				if (LLEVA_RESULTSET)
					tipoParam=-10;//OracleTypes.CURSOR
				else
					tipoParam=Types.VARCHAR;
					
				cstmt.registerOutParameter(1+parametros.size(), tipoParam);
				cstmt.executeQuery();
				
				if (LLEVA_RESULTSET)
					return cstmt.getObject(1+parametros.size());
				else
					return cstmt.getObject(1+parametros.size());
				}
			else 
				rs = stmt.executeQuery();
					
			return rs;
			}
		catch(SQLException e){
			throw(e);
			}
		}
	protected static String insertarParametroOracle(String sql) {
		
		 StringBuffer sqloracle = new StringBuffer(sql);
		 
		 if (sql.contains("()")||sql.contains("( )"))
			sqloracle = sqloracle.insert(sql.lastIndexOf(')'), "?");
		 else
			sqloracle = sqloracle.insert(sql.lastIndexOf(')'), ",?");
		 
		 return sqloracle.toString();
		}
	public String fnConcat(String... lista){
		String ret=lista[0];
		for (int i=1; i<lista.length; i++){
			String exp=lista[i];
			ret=_fnConcat(ret, exp);
			}
		return "("+ret+")";
		}
	private String _fnConcat(String exp1, String exp2){
		if (this.tipoBD.equals(Constantes.MarcaBaseDatos.Informix) 
				|| this.tipoBD.equals(Constantes.MarcaBaseDatos.Oracle)
				|| this.tipoBD.equals(Constantes.MarcaBaseDatos.sqlite)
				)
			return "("+decideFnCastAsChar(exp1)+"||"+decideFnCastAsChar(exp2)+")";
		else if (this.tipoBD.equals(Constantes.MarcaBaseDatos.MSSQL))
			return decideFnCastAsChar(exp1)+"+"+decideFnCastAsChar(exp2);
		
		return "{fn concat("+exp1+","+exp2+")}";
		}
	private String decideFnCastAsChar(String exp){
		if (exp.startsWith(Constantes.COMILLA))
			return exp;
		return fnCastAsChar(exp);
		}
	protected void añadeSQL(String sql, List<Object> parametros, Long t1, String objetoSQL, int numFilas,TipoMensajeGotta tipo) {
		//this.añadeSQL(sql, parametros, t1, objetoSQL, numFilas, TipoMensajeGotta.sql);
		}
	protected void añadeSQL(String sql, List<Object> parametros, Long t1) {
		this.añadeSQL(sql, parametros, t1, null, -1, TipoMensajeGotta.sql);
		}
	protected void añadeSQL(String sql, List<Object> parametros, Long t1, String objetoSQL, int numFilas) {
		this.añadeSQL(sql, parametros, t1, objetoSQL, numFilas, TipoMensajeGotta.sql);
		}
	protected void añadeSQL(String sql, List<Object> parametros, Long t1, TipoMensajeGotta tipo) {
		this.añadeSQL(sql, parametros, t1, null, -1, tipo);
		}
	protected void añadeSQL(String msg, TipoMensajeGotta tipo) {
		//pass
		}
	
	protected String getUsr() {
		return this.idUsuario;
		}

	public Aplicacion getApli() throws ErrorArrancandoAplicacion{
		return PoolAplicaciones.sacar(this.nombreOrigenDatos);
		}
	
	protected Filas montaDiccionarios (Object rs, int tope) {
		Filas filas=new Filas(Util.creaListaString("retorno"), Util.creaListaString(Constantes.STRING));
		
		Fila fila=new Fila(filas);
		fila.__setitem__("retorno", rs);
		filas.add( fila);
		
		return filas;
		}
	
	protected Filas montaDiccionarios (ResultSet rs, int tope) throws SQLException{	
		ResultSetMetaData rsmd=rs.getMetaData();
		int num_cols = rsmd.getColumnCount();
		ArrayList<String> tipos = new ArrayList<String>(num_cols);
		ArrayList<String> nombres = new ArrayList<String>(num_cols);
		String nombrecol;
		for (int i = 1; i <= num_cols; i++) {
			nombrecol = rsmd.getColumnLabel(i);
			nombres.add(nombrecol);
			tipos.add(rsmd.getColumnClassName(i));
		}
		ArrayList<Object[]> datos=new ArrayList<Object[]>();
		
		int contador=0;
		boolean todos=false;
		if (tope==0)
			tope=this.maxElementos;
		if (tope<0)
			todos=true;
		
		while (rs.next() && (todos||contador<tope)) {	
			//Fila dic;
			//dic = new Fila(ret, num_cols);
			Object[] dic=new Object[num_cols];
			for (int i = 1; i <= num_cols ; i++) {
				nombrecol = rsmd.getColumnLabel(i).toLowerCase();
	
				//OJO las fechas se formatean en cristiano SIEMPRE
				String tipoCol=rsmd.getColumnTypeName(i);
				if(tipoCol==null)
					tipoCol=Constantes.CAD_VACIA;
				Object valor;
				if (tipoCol.equalsIgnoreCase("datetime") || tipoCol.equalsIgnoreCase("date")) {
					java.sql.Timestamp d = rs.getTimestamp(i);
					if (d==null)
						valor=null;
					else{
						try 
							{valor=new FechaGotta(d);} 
						catch (ErrorFechaIncorrecta e) 
							{throw new SQLException("La bd tiene fechas que no son correctas");}
						}
					}
				else if (tipoCol.equalsIgnoreCase("CLOB")) {
					valor=rs.getString(i);	
					}
				else if (tipoCol.equalsIgnoreCase("money")) {
					valor=rs.getBigDecimal(i);
					if (valor!=null) { 
						try {
							valor=((BigDecimal)valor).setScale(2);
							}
						catch (ArithmeticException e) {
							//pass
							}
						}
					}
				else
					valor=rs.getObject(i);	
	
				dic[i-1]=valor;
				//dic.put(nombrecol, valor);
			}
			datos.add(dic);
			//ret.add(dic);
			contador++;
			}
		Filas ret = new Filas(nombres,tipos,datos);
		if (rs.next())
			ret.maxElementosAlcanzado=true;
		
		this.println("Recuperadas "+ contador + " filas");
		if (ret.maxElementosAlcanzado)
			this.println("maxElementosAlcanzado");
	
		rs.close();
		return ret;
		}
	
	protected void println(String texto){
		Util.getLog().debug(texto);
	}
	private int _tope=-17;
	private int getTope() {
		if (_tope==-17)
			_tope=this.maxElementos;
		return _tope;
	}

	public FechaGotta fechaServidor() throws ErrorFechaIncorrecta {
		String sql;
		if (this.tipoBD==Constantes.MarcaBaseDatos.MSAccess) 
			sql="select Now() as f";
		else if (this.tipoBD==Constantes.MarcaBaseDatos.Informix) 
			sql="select first 1 current from "+prefijo+"dic_tiposcontrol";
		else if (this.tipoBD==Constantes.MarcaBaseDatos.Oracle)
			sql="select sysdate from dual";
		else if (this.tipoBD==Constantes.MarcaBaseDatos.sqlite)
			sql="SELECT datetime('now')	";
		else
			sql="select {fn now()}";
		try {
			Filas rs = lookUp(sql);
			Object f=rs.get(0).get(0);
			FechaGotta ret=FechaGotta.comoFechaGotta(f);
			if (ret==null)
				throw new ErrorFechaIncorrecta("No se ha podido recuperar la fecha del servidor");
			if (!this.getApli().getUsarMilis())
				ret.set(Calendar.MILLISECOND, 0);
			return ret;
			} 
		catch (SQLException e) {
			throw new ErrorFechaIncorrecta("No se ha podido recuperar la fecha del servidor");
			}
	}
}


package es.burke.gotta.dll;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorFechaIncorrecta;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.FechaGotta;
import es.burke.gotta.Filas;

public class ConexionExterna {
	String url;
	String usuBD;
	Connection con;
	String driverClassName;
	
	public ConexionExterna(String driverClassName, String url, String usu, String pass) throws ErrorGotta {
		this.url=url;
		this.usuBD=usu;
		this.driverClassName=driverClassName;
		
		try {
			Class.forName(this.driverClassName);
			con=DriverManager.getConnection(url, usu, pass);
			} 
		catch (ClassNotFoundException e) {
			throw new ErrorGotta(e.getMessage());
			}
		catch (SQLException e) {
			throw new ErrorGotta(e.getMessage());
			}
		}
	public Filas lookup(String sql) throws SQLException {
		return lookup(sql, null);
		}
	public Filas lookup(String sql, ArrayList<Object> parametros) throws SQLException {
		Filas resultado;
		ResultSet rs = null;
		CallableStatement stmt = null;

		try {
			stmt = this.lookupResultsetInterno(sql, parametros, false);
			rs = stmt.executeQuery();
			resultado=montaDiccionarios(rs, -1);
			} 
		finally {
			cierraTodo(stmt, rs);
			}
		return resultado;
		}
	public void ejecuta(String sql) throws SQLException {
		this.ejecuta(sql, null);
		}
	public void ejecuta(String sql, ArrayList<Object> parametros) throws SQLException {
		ResultSet rs = null;
		CallableStatement stmt = null;

		try {
			stmt = this.lookupResultsetInterno(sql, parametros, false);
			stmt.executeQuery();
			} 
		finally {
			cierraTodo(stmt, rs);
			}
		}

	private CallableStatement lookupResultsetInterno(String sql, ArrayList<Object> parametros, boolean dml) throws SQLException {
		CallableStatement stmt = this.con.prepareCall(sql);

		if (parametros==null)
			parametros=new ArrayList<Object>();

		int i =0;
		for (Object p:parametros) {
			i++;
			if (p == null)
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
			else if (p instanceof Double)
				stmt.setDouble(i, (Double)p);
			else
				throw new SQLException("ESTE tipo no está");
			}
		if (dml) {
			stmt.executeUpdate();
			return null;
			}
	
						
		return stmt;			
		} 
	private Filas montaDiccionarios (ResultSet rs, int tope) throws SQLException{	
		ResultSetMetaData rsmd=rs.getMetaData();
		int num_cols = rsmd.getColumnCount();
		ArrayList<String> tipos = new ArrayList<String>(num_cols);
		ArrayList<String> nombres = new ArrayList<String>(num_cols);
		String nombrecol;
		for (int i = 1; i <= num_cols; i++) {
			nombrecol = rsmd.getColumnName(i);
			nombres.add(nombrecol);
			tipos.add(rsmd.getColumnClassName(i));
		}
		ArrayList<Object[]> datos=new ArrayList<Object[]>();
		
		int contador=0;
		boolean todos=true;
		while (rs.next() && (todos||contador<tope)) {	
			//Fila dic;
			//dic = new Fila(ret, num_cols);
			Object[] dic=new Object[num_cols];
			for (int i = 1; i <= num_cols ; i++) {
				nombrecol = rsmd.getColumnName(i).toLowerCase();
	
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
		rs.close();
		Filas ret = new Filas(nombres,tipos,datos);	
		return ret;
		}
	
	private void cierraTodo(Statement s, ResultSet resultados) {
		try {
			if (resultados != null) resultados.close();
			if (s != null) s.close();
			
				try  {
					
					if ((resultados != null) && (resultados.getStatement()!=null) )
						resultados.getStatement().close();
					}
				catch (SQLException e)
					{/*pass*/}
			}
		catch (SQLException e) {
			e.printStackTrace();
			}
	}

	public void cierra()  {
		try {
			if (con != null && !con.isClosed()) con.close();
			} 
		catch (SQLException e) {
			e.printStackTrace();
			}
		}
	public void commit(){
		try {
			if (con != null && !con.isClosed()) con.commit();
			} 
		catch (SQLException e) {
			e.printStackTrace();
			cierra();
			}
		}
}

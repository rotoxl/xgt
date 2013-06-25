package es.burke.gotta;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import es.burke.gotta.Constantes.MarcaBaseDatos;
import es.burke.gotta.Constantes.TipoMensajeGotta;

public class Nivel extends INivel{
	public Nivel (NivelDef nivelDef){
		this.cd=nivelDef.getCd();
		this.nivelDef=nivelDef;
		}
	
	//función genérica
	@Override
	public Filas lookUpBase(ConexionLight con, List<Object> valores, int limite) throws SQLException, ErrorSQLoParametrosIncorrectos, ErrorArrancandoAplicacion{
		boolean esOracleConParamRetorno=false;
		ResultSet rs = null;			
		PreparedStatement stmt = null;
		List<Parametro> parametros=nivelDef.getColParams();
		String sql=nivelDef.getTexto();
		con.getApli().println("...ejecutando nivel " + nivelDef.getCd());
		if (sql.contains(con.ret_cursor)){
			sql=Util.replaceUno(sql, con.ret_cursor, "?");
			esOracleConParamRetorno=true;
			}
		else if (con.tipoBD.equals(MarcaBaseDatos.Oracle) && sql.toLowerCase().startsWith("{call")) {
			sql = ConexionLight.insertarParametroOracle(sql);
			esOracleConParamRetorno=true;
			}
		else if (con.tipoBD.equals(MarcaBaseDatos.Informix) 
				&& sql.toLowerCase().startsWith("{call")
				&& valores != null
				&& valores.size()>0) {
			sql = insertarParametros(nivelDef, valores, sql); // X-(
			parametros=new ArrayList<Parametro>();
			}

		Connection conn=con.obtenerConexion();
		try {
			if (con.tipoBD.equals(MarcaBaseDatos.Oracle))
				stmt=conn.prepareCall(sql);
			else
				stmt=conn.prepareStatement(sql);
			Object v;
			for (int i=0; i<parametros.size(); i++){
				Parametro p=parametros.get(i);
				v=valores.get(i);
				
				if (v==null || (v.toString().equals("") && (p.getTipo().equalsIgnoreCase(Constantes.DATE)||Util.en(p.getTipo(), Constantes.tiposNumericos)) ) ){
					if (p.getTipo().equals(Constantes.STRING) )
						stmt.setNull(i+1, Types.VARCHAR );
					else if (p.getTipo().equals(Constantes.MEMO))
						stmt.setNull(i+1, Types.LONGVARCHAR );
					else if (p.getTipo().equals(Constantes.INTEGER))
						stmt.setNull(i+1, Types.INTEGER );
					else if (p.getTipo().equals(Constantes.LONG) && con.tipoBD.equals(MarcaBaseDatos.MSAccess))
						stmt.setNull(i+1, Types.INTEGER );
					else if (p.getTipo().equals(Constantes.LONG))
						stmt.setNull(i+1, Types.BIGINT );
					else if (p.getTipo().equals(Constantes.DOUBLE))
						stmt.setNull(i+1, Types.DOUBLE );
					else if (p.getTipo().equals(Constantes.DATE))
						stmt.setNull(i+1, Types.DATE );
					else if (p.getTipo().equals(Constantes.CURRENCY))// || p.getTipo().equals("Decimal"))
						stmt.setNull(i+1, Types.DECIMAL ) ;
					else if (p.getTipo().equals(Constantes.BOOLEAN))
						stmt.setNull(i+1, Types.BOOLEAN );
					}
				else
					{
					if (p.getTipo().equals(Constantes.STRING) || p.getTipo().equals(Constantes.MEMO))
						stmt.setString(i+1, v.toString() ) ;
					else if (p.getTipo().equals(Constantes.INTEGER)) {
						int xvalor;
						if (v instanceof Boolean)
							xvalor=(Boolean)v ? -1 : 0;
						else
							xvalor=Integer.parseInt(Util.desformatearNumero(v.toString()));
						
						stmt.setInt(i+1,  xvalor ) ;
						}
					else if (p.getTipo().equals(Constantes.LONG) || p.getTipo().equals(Constantes.DOUBLE)){
						BigDecimal bdv;
						if (v instanceof Double)
							bdv=new BigDecimal (((Double)v).doubleValue());
						else
							bdv=new BigDecimal(Util.desformatearNumero(v.toString()));
						v=bdv.toString();
						
						if (p.getTipo().equals(Constantes.LONG) && con.tipoBD.equals(MarcaBaseDatos.MSAccess)){
							Integer iv=Integer.parseInt(v.toString());
							stmt.setInt(i+1, iv);
							}
						else if (p.getTipo().equals(Constantes.LONG)){
							Long lv=Long.parseLong(v.toString());
							stmt.setLong(i+1, lv);
							}
						else if (p.getTipo().equals(Constantes.DOUBLE)){
							Double dv=Double.parseDouble(v.toString());
							stmt.setDouble(i+1, dv);
							}
						
						}
					else if (p.getTipo().equals(Constantes.DATE)){
						if (v instanceof java.sql.Timestamp)
							stmt.setTimestamp(i+1, (java.sql.Timestamp) v) ;
						else{
							FechaGotta fg;
							try {
								fg = new FechaGotta(v.toString());}
							catch (ErrorFechaIncorrecta e) {
								throw new ErrorSQLoParametrosIncorrectos(e.getMessage());}
							
							java.sql.Timestamp d = new java.sql.Timestamp(fg.getTimeInMillis());
							//System.out.println(d.toLocaleString());				
							stmt.setTimestamp(i+1, d );
							}
						}
					else if (p.getTipo().equals(Constantes.CURRENCY) ){
						if (v instanceof BigDecimal) {
							stmt.setBigDecimal(i+1,(BigDecimal)v) ;
						}
						else
							stmt.setBigDecimal(i+1, new BigDecimal(Util.desformatearNumero(v.toString()))) ;
					}
					else if (p.getTipo().equals(Constantes.BOOLEAN))
						if(v instanceof Boolean)
							stmt.setBoolean(i+1, (Boolean)v) ;
						else
							stmt.setBoolean(i+1, Integer.parseInt(v.toString()) != 0 ) ;
					}
			}
			Long t=con.getHoraActual();
			if (esOracleConParamRetorno){
				CallableStatement cstmt = (CallableStatement) stmt;
				cstmt.registerOutParameter(1+parametros.size(), -10);//OracleTypes.CURSOR
				cstmt.execute();
	            rs = (ResultSet) cstmt.getObject(1+parametros.size());
				}
			else{
				if(!stmt.execute()){
					while (!stmt.getMoreResults()){
						/*pass;*/}
					}
				rs = stmt.getResultSet();
				}
			con.getApli().println(nivelDef.getCd()+"  --> OK");
			Filas ret=con.montaDiccionarios(rs, limite);
			con.añadeSQL(sql, valores, t, "Nivel "+nivelDef.getCd(), ret.size());
			
			return ret;
		}
		catch (SQLException e) {
			
			con.getApli().log.error(sql);
			con.getApli().log.error(valores);
			
			con.añadeSQL("ERROR en SQL\n"+sql, valores, 0L, TipoMensajeGotta.critico);
			throw e;
			}
		finally {
			if (stmt != null)
				stmt.close();
			con.liberarConexion(conn);
			}
		}
	private String insertarParametros(INivelDef xnivelDef, List<Object> valores, String sql) {
		List<Parametro> parametros=xnivelDef.getColParams();
		int i=0;
		for(Parametro p:parametros)
			sql=Util.replaceUno(sql, "?", p.jdbcEncode(valores.get(i++)));
		return sql;
		}
}

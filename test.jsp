<%@page pageEncoding="UTF-8"%>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%
		String apli = request.getParameter("apli");
		if (apli==null)
			throw new Exception("Falta parámetro apli!");
				
		InitialContext cxt = new InitialContext();
		Object lu = cxt.lookup( "java:/comp/env/jdbc/Burke/"+apli );
		DataSource ds = (DataSource) lu;

		if ( ds == null ) {
			throw new Exception("Data source not found!");
		}
		Connection con = ds.getConnection();
		Statement st = con.createStatement();
		st.execute("select * from dic_configuracion");
%>



Todo OK

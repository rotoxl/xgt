<%@ page
 isErrorPage="true" 
 language="java" 
 contentType="text/plain; charset=UTF-8"
 pageEncoding="UTF-8" 
 import="java.io.*" 
 import="org.json.JSONObject"
%>
<%
	String acc=request.getHeader("Accept");

	String texto;
	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	exception.printStackTrace(pw);
	texto = sw.toString();
	sw.close();
	pw.close();
	if(acc!=null && !acc.startsWith("text/html"))
		new JSONObject()
		.put("error", texto)
		.write(out);
	else
		out.write(texto);
%>
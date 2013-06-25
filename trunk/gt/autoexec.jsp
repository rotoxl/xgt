<!DOCTYPE html>
<%@page pageEncoding="UTF-8"%>
<%@ page import="es.burke.gotta.*"%>
<%@ page import="org.json.JSONObject"%>
<% 
Jsp.setHeaders(response); 
Usuario usr = null;
try {
	usr = Usuario.desdeSesion(request);
	if (usr==null)
		throw new ErrorConexionPerdida(Constantes.SesionCaducadaCookiesRaw);
	} 
catch (ErrorConexionPerdida e) {
	Util.sendRedirect(request, response, ".");
	return;
	}
String apli=usr.getApli().getCd();
%>
<html>
<head>
	<%=Jsp.getTitle(usr.getLogin()+"@"+apli+"      -      ..:: Gotta|* ::.. ")%>
	<link rel="shortcut icon" href="./fijo/g3.ico" type="image/x-icon">
	<link rel="icon" href="./fijo/g3.ico" type="image/x-icon">
	
	<%=Jsp.getCSS(usr)%>
	<%=Jsp.getScript(usr)%>
	<script type="text/javascript">
		var icoGotta='./fijo/'
		var aplicacion = <%=JSONObject.quote(apli)%>
		var exp={'aplicacion':aplicacion}
		var pendiente={}
	</script>
</head>
<body style="overflow:hidden;" onload="setTimeout(function(){lanzaTramite('', 'autoexec')},100)"> 
<p>&nbsp;iniciando...</p>

<div style="display:none;"><form id="ifr_subirArchivos" enctype="multipart/form-data" method="post"> </form></div>
</body>

</html>

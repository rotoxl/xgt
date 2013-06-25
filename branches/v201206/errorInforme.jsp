<!DOCTYPE html>
<%@page pageEncoding="UTF-8"%>
<%@ page import="es.burke.gotta.*"%>
<%@ page isErrorPage="true" %>
<% 
Jsp.setHeaders(response); 
Usuario usr=null;
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
String msg=session.getAttribute("mensaje").toString();
%>
<html>
<head>
	<%=Jsp.getTitle(usr.getLogin()+"@"+apli+"      -      Informe de error en la emisión de un informe ")%>
	<%=Jsp.getCSS(usr)%>
	<%=Jsp.getScript(usr)%>
	<script type="text/javascript">
		var exp={'aplicacion':'<%=apli%>'}
		var pendiente={}
	</script>
</head>
<body style="overflow:hidden;" class="informeError"> 
	<div class="informeError"><%=msg%>
	<button id="cerrarInformeError" class="cmdgt" onclick="window.close()" >Cerrar ventana</button>
	</div>
</body>
</html>
<!DOCTYPE html>
<%@page pageEncoding="UTF-8"%>
<%@ page import="es.burke.gotta.*"%>
<%@ page import="es.burke.gotta.dll.*"%>
<%
Jsp.setHeaders(response); 
request.setCharacterEncoding( Constantes.CODIF );

if (session==null) 
	{Util.sendRedirect(request, response, ".");}

Usuario usr=null; String msg;
try 	{
	usr = Usuario.desdeSesion(request);
	}
catch (ErrorUsuarioNoValido e) {
	msg=e.getMessage();
	return;
	}
catch (ErrorConexionPerdida e) {
	msg=e.getMessage();
	return;
	}
	
String numProd = request.getParameter("prod");

String idCamino; String zoom=".5";
Boolean tienePermiso=false;
if ( numProd != null ) {
	DLLMapaTramitacion mapa=(DLLMapaTramitacion ) usr.productosEnviados.get(numProd);
	Camino cam=mapa.caminos.get(0);
	idCamino=""+cam.cd;
	}
else	{
	idCamino=request.getParameter("camino");
	tienePermiso=usr.permisoDepuracion;
	}
zoom=Util.obtenValorOpcional(request, "zoom");
String ob=Util.obtenValorOpcional(request, "ob");
String tramCentrado=Util.obtenValorOpcional(request, "tramCentrado");
String apli=usr.getApli().getCd();
%>
<HTML>
<HEAD>
	<%=Jsp.getTitle("Mapa de tramitación | Gotta")%>
	
	<link rel="STYLESHEET" TYPE="text/css" href="./fijo/mapatram.css">
	<%=Jsp.getCSS(usr)%>
	
	<%=Jsp.getScript(usr)%>
	<script type="text/javascript" src="./fijo/mapatram.js"></script>
	<script type="text/javascript" src="./fijo/jwerty.js"></script>
	
	<link rel="shortcut icon" href="./fijo/exp.mapatram.on.png" type="image/x-icon">
	<link rel="icon" href="./fijo/exp.mapatram.on.png" type="image/x-icon">
</HEAD>

<BODY class="mapatram" onLoad="montaPorCamino(<%=idCamino%>, 'cuerpo', '<%=apli%>', <%=zoom%>, '<%=ob%>', '<%=tramCentrado%>',<%=tienePermiso%>)" style="overflow:hidden">
	<div id="cuerpo" style="height:100%; width:100%; left:0px; top:0px; overflow:auto; position:absolute;">
	</div>
</BODY>
</HTML>


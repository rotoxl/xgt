<!DOCTYPE html>
<%@page pageEncoding="UTF-8"%>
<%@ page import="es.burke.gotta.*"%>
<%
String aplicacion=request.getParameter("aplicacion");
String msg="";
try	{
	DiccionarioFisico df = new DiccionarioFisico(request);
	df.valida(request, response);
	}
catch (ErrorUsuarioNoValido e){
	Util.sendRedirect(request, response, ".");
	return;
	}
catch (ErrorConexionPerdida e){
	Util.sendRedirect(request, response, ".");
	return;
	}
%>
<html> 
<head>
	<title>Diccionario de Gotta: <%=aplicacion%> </title>
	<link rel="shortcut icon" href="./fijo/dic.ico" type="image/x-icon">
	<link rel="icon" href="./fijo/dic.ico" type="image/x-icon">
	
	<link type="text/css" rel="stylesheet" href="./fijo/gotta.css" /> 
	<link type="text/css" rel="stylesheet" href="./fijo/asistentes.css" /> 
	<script DEFER="DEFER" type="text/javascript" src="./fijo/jquery.min.js"></script>
	<script DEFER="DEFER" type="text/javascript" src="./fijo/firebug/firebugx.js"></script>
	<script DEFER="DEFER" type="text/javascript" src="./fijo/arbol.js"></script>
	<script DEFER="DEFER" type="text/javascript" src="./fijo/xsplitter.js"></script>
	<script DEFER="DEFER" type="text/javascript" src="./fijo/menus.js"></script>
	<script DEFER="DEFER" type="text/javascript" src="./fijo/calendarPopup.js"></script>
	<script DEFER="DEFER" type="text/javascript" src="./fijo/mover.js"></script>
	<script DEFER="DEFER" type="text/javascript" src="./fijo/detalle.js"></script>
	<script DEFER="DEFER" type="text/javascript" src="./fijo/frmDinamico.js"></script>
	<script DEFER="DEFER" type="text/javascript" src="./fijo/disenhador.js"></script>
	<script DEFER="DEFER" type="text/javascript" src="./fijo/sorttable.js"></script>
	<script DEFER="DEFER" type="text/javascript" src="./fijo/depurador.js"></script>
	<script DEFER="DEFER" type="text/javascript" src="./fijo/webtoolkit.aim.js"></script>
	<script DEFER="DEFER" type="text/javascript" src="./fijo/validaciones.js"></script>
	<script DEFER="DEFER" type="text/javascript" src="./fijo/diccionario2.js"></script>
		<script DEFER="DEFER" type="text/javascript" src="./fijo/diccionarioV.js"></script>
		<script DEFER="DEFER" type="text/javascript" src="./fijo/diccionarioF.js"></script>
	<script DEFER="DEFER" type="text/javascript" src="./fijo/editorNiveles.js"></script>
	<script DEFER="DEFER" type="text/javascript" src="./fijo/jwerty.js"></script>
	<script type="text/javascript" >
		var aplicacion = '<%=aplicacion%>'
	</script>
	
</head>
<body style="overflow:hidden;" onload="javascript:editaDiccionario()"> <!-- dietas.embla_dietas.dbo//-->

</body>

<!DOCTYPE html>
<%@page pageEncoding="UTF-8"%>
<%@ page import="es.burke.gotta.*"%>
<%@ page import="org.json.JSONObject"%>
<% 
Jsp.setHeaders(response); 
Usuario usr = null;
String apli=null;
String md, nodo;
try {
	usr = Usuario.desdeSesion(request);
	if (usr==null) {
		throw new ErrorConexionPerdida(Constantes.SesionCaducadaRaw);
		}
	apli = usr.getApli().getCd();
	
	md=request.getParameter("md");	//Util.obtenValor(request, "md");
	nodo=request.getParameter("nodo");	//nodo=Util.obtenValor(request, "nodo");
	} 
catch (ErrorConexionPerdida e) {
	Util.sendRedirect(request, response, ".");
	return;
	}

String ico = request.getContextPath() + usr.getApli().getDatoConfig("icoWeb");

%>
<html>
<head>
	<meta charset="utf-8"/>
	<%=Jsp.getTitle( apli )%>
	<link rel="shortcut icon" href="./fijo/imprimiron.png" type="image/x-icon">
	<link rel="icon" href="./fijo/imprimiron.png" type="image/x-icon">
	
	<%=Jsp.getScript(usr)%>
	<script type="text/javascript" src="fijo/print.js" charset="UTF-8"></script>
	
	<%=Jsp.getCSS(usr)%>
	<link id="print" rel="STYLESHEET" TYPE="text/css" href="./fijo/print.css"> 
	<script type="text/javascript">
		var exp
		var pendiente={}
		var disenhador=null
		
		var aplicacion=<%=JSONObject.quote(apli)%>
		var msgLvwSinDatos = <%=JSONObject.quote(usr.getApli().getDatoConfig("msgLvwSinDatos"))%>
		
		var icoWeb=<%=JSONObject.quote(ico)%>
		var icoGotta='./fijo/'
		
		function init_print(){
			var url=<%=JSONObject.quote(nodo)%>
			var md=<%=JSONObject.quote(md)%>
			
			exp=new Explorador()
			
			exp.aplicacion=aplicacion
			exp.icoWeb="<%=ico%>"
			exp.acceptCharset="<%=Constantes.CODIF%>"
			
			exp.cambioNodoActivo(url, md)
			}
	</script>
</head>

<body onload="init_print();" style="overflow: visible;">
	<div class="clsPane" id="panelDer">  </div>
</body>
</html>

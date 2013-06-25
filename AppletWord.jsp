<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Strict//EN" "http://www.w3.org/TR/html4/strict.dtd"><%@page pageEncoding="UTF-8"%>
<%@ page import="es.burke.gotta.*"%>
<%
	
	Jsp.setHeaders(response);
	Usuario usr = null;
	usr = Usuario.desdeSesion(request);
	if (usr == null) {
		throw new ErrorConexionPerdida(Constantes.SesionCaducadaRaw);
		}
	Aplicacion apli = usr.getApli();
	Accion accion = (Accion) request.getAttribute("accion");
	String plantilla = (String) request.getAttribute("plantilla");
	String contadoc = (String) request.getAttribute("contadoc");
	String docid = (String) request.getAttribute("docid");
	
	String impresora = (String) request.getAttribute("impresora");
	if (impresora==null) impresora=Constantes.CAD_VACIA;
	String copias = (String) request.getAttribute("copias");
	if (copias==null) copias="1";
	
	usr.añadeMSG("Informe '"+plantilla+"' solicitado", Constantes.TipoMensajeGotta.informe);
%>
<html>
<head>
	<meta charset="utf-8"/>
	<title>GottaWord</title>
	<script type="text/javascript" charset="UTF-8">	
		function devuelveOK(msg, docid){ 
			_cierra(true, {error:0, msg:msg, docid:docid})
			}
		function devuelveKO(msg, docid){ 
			_cierra(false, {error:1, msg:msg, docid:docid}) 
			}
		function _cierra(v, dic){
			window.opener.exp.fnSeguir(v, dic)
			
			setTimeout(function(){//si no se hace con un setTimeout cierra el navegador entero
				self.close()
				},500)
			}
		
	</script>
</head>
<body>
<p>
Iniciando tareas; espere, por favor...
</p>	
<object type="application/x-java-applet" width="640" height="480">	
	<param name="archive" value="fijo/applets/word/word_applet.jar">
	<param name="code" value="es.burke.gotta.dll.WordApplet.class">
	<param name="mayscript" value="yes">
			
	<param name="accion" value="<%=accion.accion%>">
	<param name="aplicacion" value="<%=apli.getCd()%>">
	<param name="contadoc" value="<%=contadoc%>">
	<param name="docid" value="<%=docid%>">
	<param name="dotweb" value="<%= apli.getDatoConfig("dotweb") %>">
	<param name="plantilla" value="<%= plantilla %>">
	
	<param name="impresora" value="<%= impresora %>">
	<param name="copias" value="<%= copias %>">
	
	<param name="JSISSION_ID" value='<%=session.getId()%>'>
</object>
</body>
</html>
<!DOCTYPE html>
<%@page pageEncoding="UTF-8"%>
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
	String comando = (String) request.getAttribute("comando");
	usr.añadeMSG("Shell en cliente '"+comando+"' solicitado", Constantes.TipoMensajeGotta.informe);
	
	comando=Util.replaceTodos(comando, "\\", "/");
	comando=Util.replaceTodos(comando, "\"", "**COMILLAS**");
%>
<html>
<head>
	<meta charset="utf-8"/>
	<title>GottaWord</title>
	<script type="text/javascript" charset="UTF-8">	
		function devuelveOK(msg){ 
			_cierra(false, {error:0, msg:msg})
			}
		function devuelveKO(msg){ 
			_cierra(false, {error:1, msg:msg})
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
	<param name="archive" value="fijo/applets/shell/shell_applet.jar">
	<param name="code" value="es.burke.gotta.dll.ShellApplet.class">
	<param name="mayscript" value="yes">
			
	<param name="aplicacion" value="<%=apli.getCd()%>">
	<param name="accion" value="<%=accion.accion%>">
	<param name="comando" value="<%=comando%>">
	
	<param name="JSISSION_ID" value='<%=session.getId()%>'>
</object>
</body>
</html>
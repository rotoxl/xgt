<!DOCTYPE HTML>
<%@page pageEncoding="UTF-8"%>
<%@ page import="es.burke.gotta.*"%>
<% 
Jsp.setHeaders(response); 
Usuario usu=null; String msg="";
try 	{
	usu = Usuario.desdeSesion(request);
	}
catch (ErrorUsuarioNoValido e) {
	msg=e.getMessage();
	}
catch (ErrorConexionPerdida e) {
	msg=e.getMessage();
	}

if (usu==null || !usu.permisoRearrancar){
	%>
<HTML>
	<BODY><%=msg%>
	</BODY>
</HTML>
<%	
	return;
	}
Aplicacion ap, apliDef;

ap = usu.getApli();
apliDef=null;
try 	{
	apliDef=PoolAplicaciones.rearrancaAplicacion(ap.getCd(), request);
	}
catch (ErrorGotta e){
	//pass
	}
	
String tipo="info";
if (apliDef!=null) {
	if (apliDef.erroresArranque.length()>0){
		msg="La aplicación se ha recargado, pero se han detectado errores en el diccionario";
		tipo="alerta";
		}
	else
		msg="La aplicación se ha recargado correctamente";
	}
else	{
	msg="No se ha podido recargar la aplicación";
	tipo="critico";
	}
%>
<HTML>
<head>
<link rel="shortcut icon" href="./fijo/exp.recargar.on.png" type="image/x-icon">
<link rel="icon" 		 href="./fijo/<%=(apliDef!=null?"exp.recargar.on.png":"recarga.errorArranque.png")%>" type="image/x-icon">
<%=Jsp.getTitle("Recarga de "+ap.getCd())%>
<%=Jsp.getCSS("1", "./fijo/gotta.css")%>
<%=Jsp.getCSS("2", "./fijo/asistentes.css")%>
<%=Jsp.getScript(usu)%>
 
<SCRIPT TYPE="text/javascript">
	var aplicacion="<%=ap.getCd()%>"
	var icoGotta='./fijo/', icoWeb=''
	var exp=null	
	
	var tipoMsg='<%=tipo%>'
	var estadisticas=null
	function montaTodo(){
		control('erroresArranque').style.display=tipoMsg!='info'?'block':'none'
		ponEstilo(control('mensaje'), tipoMsg)
		loadJSONDoc('json', {'accion':'sacaEstadisticas','aplicacion': aplicacion} ,
				function(req){
					var est=xeval(req)
					
					est.vistas = est.vistas || '?'
					est.exp_menu = est.exp_menu || '?'
					
					anhade(est.dic_tablas+' tablas / '+est.dic_columnas+' columas / '+est.dic_referencias+' relaciones', 'est dic')
					anhade(est.exp_niveles+' niveles / '+est.vistas+' vistas / '+est.exp_modosdetalle+' detalles / '+est.exp_controlesmododetalle+' controles', 'est exp')
					anhade(est.exp_menu+' entradas en menú', 'est menu')
					anhade(est.tra_caminos+' caminos / '+est.tra_tramites+' trámites / '+est.tra_acciones+' acciones', 'est tra')
					anhade(est.usuarios+' usuario(s)', 'est usu')
					anhade(est.idiomas+' idioma(s)', 'est idiomas')
					}
				)
		function anhade(texto, clase){
			var ctlEst=control('estadisticas')
			ctlEst.appendChild(creaObjProp('span', {className:clase, texto:texto }))
			}
		}
	
	
</SCRIPT>
</head>
<BODY id="recarga" onLoad="montaTodo()">
	<div id="aplicacion"><%=ap.getCd()%></div>
	<div class="mensaje info" id="mensaje"> 	<span class="texto"><%=msg%></span> </div>
	
	<div class="est" id="erroresArranque"> 	<span class="texto"> Errores detectados</span>
								<span class="errores"><%=apliDef.erroresArranque.toString()%> </span> 
								</div>
	
	<div class="est" id="estadisticas"> 		<span class="texto"> Información estadística </span></div>
</BODY>
</HTML>


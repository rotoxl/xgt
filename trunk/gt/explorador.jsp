<!DOCTYPE html> 
<%@page import="es.burke.gotta.Aplicacion.DIC_Configuracion"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="es.burke.gotta.*"%>
<%@ page import="org.json.JSONObject"%>
<%
Jsp.setHeaders(response); 
Usuario usr = null;
String refApli=null;
String apli = request.getParameter("aplicacion");
if (apli==null){
	application.getRequestDispatcher("/login.jsp").forward(request,response);
	return;
}
try {
	usr = Usuario.desdeSesion(request);
	if (usr==null) {
		throw new ErrorConexionPerdida(Constantes.SesionCaducadaRaw);
		}
	if (usr.tieneAutoexec && usr.getAutoexec()!=null){
		application.getRequestDispatcher("/autoexec.jsp").forward(request,response);
		return;
		}
	usr.leerVistasUsuario();
	refApli = apli.replaceAll(" ","+"); // En realidad habría que codificarlo bien
	
	if (request.getParameter("nodo")!=null || request.getParameter("vista")!=null) { //se rellenará aquí cuando, estando ya dentro de la aplicación, pulsen un enlace externo
		usr.nodoArranque=URL_Gotta.crea(request);
		}
	} 
catch (ErrorSesionCerrada e) {//se ha pulsado en "salir", a la pantalla de login
	session.setAttribute("errorLogin", Util.msgError(e));
	session.setAttribute("usuario"+apli, new ErrorSesionCerrada("")); // no damos más la brasa
	
	application.getRequestDispatcher("/login.jsp").forward(request,response);
	return;
	}
catch (ErrorConexionPerdida e) {//se ha tecleado directamente la url
	Aplicacion app;
	try {
		app=PoolAplicaciones.sacar(apli);
		}
	catch (ErrorConexionPerdida ee){
		session.setAttribute("errorLogin", Util.msgError(ee));
		session.setAttribute("usuario"+apli, ee);
		
		application.getRequestDispatcher("/login.jsp").forward(request,response);
		return;
		}
	catch (ErrorArrancandoAplicacion ee){
		session.setAttribute("errorLogin", Util.msgError(ee));
		session.setAttribute("usuario"+apli, ee);
		
		application.getRequestDispatcher("/login.jsp").forward(request,response);
		return;
		
		}
	
	IValidacion validacion=app.getMetodoValidacion(); 
	if (validacion.requiereUsuarioyContrasenha){
		session.setAttribute("errorLogin", Util.msgError(e));
		session.setAttribute("usuario"+apli, new ErrorSesionCerrada("")); // no damos más la brasa
		
		application.getRequestDispatcher("/login.jsp").forward(request,response);
		return;	
		}
	else {//no hace falta ir al login
		application.getRequestDispatcher("/valida?aplicacion="+apli+"&login=&password=").forward(request,response);
		return;
		}
	}
catch (Exception e) {
	session.setAttribute("errorLogin", Util.msgError(e));
	session.setAttribute("usuario"+apli, e);
	
	application.getRequestDispatcher("/login.jsp").forward(request,response);
	return;
	}

String ico = usr.getApli().getDatoConfig(DIC_Configuracion.icoWeb);
String mlArbol= (usr.arbol != null? usr.arbol.ml : null);
String ml= usr.nodoArranque != null ? usr.nodoArranque.ml : mlArbol;
if (ml==null || ml.equals(Constantes.CAD_VACIA))
	ml=usr.getApli().getDatoConfig(DIC_Configuracion.mli);

String tempTitulo=usr.getApli().getDatoConfig(DIC_Configuracion.tituloWeb);
String titulo= tempTitulo;
if (tempTitulo==null) 
	titulo=(usr.getLogin()!=null? usr.getLogin()+"@": "")+apli+"      -      ..:: Gotta|* ::.. ";

Boolean llevaDepuracion= !usr.tipoDepuracion.equals(Motor.TDepuracion.SIN_DEPURACION);
%>
<html>
<head>
	<meta charset="utf-8"/>
	<%=Jsp.getTitle( titulo )%>
	<meta name="application-name" content="<%=titulo%>"/>
	<link rel="shortcut icon" href="./fijo/g3.ico" type="image/x-icon">
	<link rel="icon" href="./fijo/g3.ico" type="image/x-icon">
	<meta http-equiv="content-language" content="<%=usr.getIdioma()%>">
	<%=Jsp.getCSS(usr)%>
	<%=Jsp.getScript(usr)%>
	<script>
		var pendiente={}
		var aplicacion = <%=JSONObject.quote(apli)%>
		var msgLvwSinDatos = <%=JSONObject.quote(usr.getApli().getDatoConfig(DIC_Configuracion.msgLvwSinDatos))%>
		var icoWeb=<%=JSONObject.quote(ico)%>
		var icoGotta='./fijo/'
		//~ window.onbeforeunload=confirmarSalir
		function init_gotta(estadoDisenho, estadoDepuracion){
			exp=new Explorador(aplicacion, icoWeb, icoGotta, <%=JSONObject.quote(Constantes.CODIF)%> )
			exp.idioma='<%=usr.getIdioma()%>'
			exp.permisoDisenho=<%=usr.permisoDisenho%>
			exp.inicializa(<%=usr.disenho%>, <%=llevaDepuracion %>, '<%=ml%>')
			}
			
		var srcjwerty='jwerty.js'
		if ($.browser.msie && $.browser.version<'9.0')
			srcjwerty='jwerty.ie8.js'
			
		$('head')[0].appendChild(creaObjProp('script', {'src':'fijo/'+srcjwerty, 'type':'text/javascript', 'charset':'UTF-8'}))
	</script>
</head>

<body id="explorador"  class="<%=apli%> <%=usr.getIdioma()%>" onload="init_gotta()" style="overflow:hidden;">
	<div class="header panelSup">
		<div id="logo" 
		<%if (ml!=null) {%>
			onclick="javascript:cambiaML('<%=usr.getApli().getDatoConfig(DIC_Configuracion.mli)%>')"
			alt="Inicio"
		<%}%>
		></div>
			
		<div id="botoneraIzq" class="nav botonera jquerycssmenu" tabindex=0> 
			<a id="throbberVistas" class="botonera" href="#" ><img src="./fijo/throbber.gif" alt="..."><span>Cargando...</span></a>
		</div>
		<div id="botoneraAdmin" class="nav botonera jquerycssmenu" tabindex=0> 
			<ul>
			<%if (usr.permisoDisenho) {%>
			<li>
				<a tabindex="-1" rel="nofollow" href="javascript:activarDisenho()" title="(dinámico) [Dise&ntilde;o inactivo] Activar dise&ntilde;o" class="botonera imagen texto" id="btnDisenhador"><span></span>&nbsp;</a>
			</li>
			<%}%>
			<%if (usr.permisoDepuracion) {%>
				<li>
					<a tabindex="-1" rel="nofollow" href="javascript:activarDepuracion()" title="(dinámico) [Depuraci&oacute;n inactiva] Activar depuraci&oacute;n" class="botonera imagen texto" id="btnDepurador"><span></span>&nbsp;</a>
				</li>
				<li>
					<a tabindex="-1" href="mapa.jsp?aplicacion=<%=refApli%>" target="_blank" rel="noreferrer" title="(dinámico) Mapa de tramitación" class="botonera imagen texto" id="btnConstructor"><span></span>&nbsp;</a>
				</li>
				<li>
					<span class="botonera separador" >&nbsp;</span>
				</li>
			<%}%>
			<%if (usr.permisoMonitorSQL) {%>
				<li>
					<a tabindex="-1" rel="nofollow" href="monitor.jsp?aplicacion=<%=refApli%>" target="_blank" rel="noreferrer" title="(dinámico) Monitor de la aplicación" class="botonera imagen texto" id="btnMonitorSQL"><span></span>&nbsp;</a>
				</li>
			<%}%>
			<%if (usr.permisoRearrancar) {%>
				<li>
					<a tabindex="-1" rel="nofollow" href="recarga.jsp?aplicacion=<%=refApli%>" target="_blank" rel="noreferrer" title="(dinámico) Recargar aplicaci&oacute;n" class="botonera imagen texto" id="btnRecargarAplicacion"><span></span>&nbsp;</a>
				</li>
				<li>
					<span class="botonera separador" >&nbsp;</span>
				</li>
			<%}%>
			</ul>
		</div>
		<% if (usr.getApli().idiomas.size()>1){%>
		<div id="botoneraIdiomas" class="nav botonera" tabIndex=0>
			<select id="idiomas">
			</select>
		</div>
		<%}%>
		<div id="botoneraDra" class="nav botonera jquerycssmenu" tabIndex=0>
			<ul>
				<li>
					<a tabindex="-1" rel="nofollow" id="btnRefrescar" class="botonera imagen texto" title="(dinámico) Actualizar (F5)" href="?aplicacion=<%=refApli%>&amp;refrescoCompleto=1" target="_parent"><span></span></a> 
				</li><li>
				<span class="botonera separador" >&nbsp;</span>
				</li><li>
					<a tabindex="-1" rel="nofollow" id="btnVersionParaImprimir" class="botonera imagen texto" href="imprimir.jsp?aplicacion=<%=refApli%>" target="_blank" rel="noreferrer" title="(dinámico) Versión para imprimir"><span></span></a>
				</li><li>
					<a id="btnNuevaVentana" class="botonera imagen texto" tabindex="-1" href="?aplicacion=<%=refApli%>+" title="(dinámico) Nueva ventana: inicia una nueva sesión con filtros de búsqueda independientes" target="_blank" rel="noreferrer"><span></span></a>
				</li>
				<%if (!usr.esUsuarioAnonimo()) {%>
					<li>
						<span class="botonera separador" >&nbsp;</span>
					</li><li>
					<a tabindex="-1" rel="nofollow" id="btnCambiarContrasenha" class="botonera imagen texto" title="<%=usr.getDsUsuario()%>" href="javascript:lanzaTramite( '', 'gtUsuario')"><span></span></a>
						</li>
				<%}%>
				<li>
				<span class="botonera separador" >&nbsp;</span>
				</li><li>
				<a tabindex="-1" rel="nofollow" id="btnSalir" class="botonera imagen texto" href="salir?aplicacion=<%=refApli%>" target="_parent" title="(dinámico) Salir"><span></span></a>
				</li>
			</ul>
		</div>
	</div>
	<div id="splitterV" class="clsSplitter" >
		<div tabindex="-1" class="section clsPane" style="overflow:hidden;" id="panelIzqNombrePanel">
			<div class="section" id="listaPanelesIzq" ></div>
			<div tabindex="1" class="nav" id="divarbol"></div>
		</div>
		<div tabindex="-1" class="section clsPane" id="panelDer">
			<div id="rubberBand" class="rubberBand" style="display:none;" onmousemove="disenhador.mueveRubberBand(event)"  onmousedown="disenhador.creaRubberBand(event)" onmouseup="disenhador.cancelaRubberBand(event)"></div>
		</div>
		<div tabindex="-1" class="clsDragBar"></div>
	</div> 
	<div class="aside botonera" id="botoneraDisenho"></div>
	<div style="display:none;"><form id="ifr_subirArchivos" enctype="multipart/form-data" method="post"> </form></div>
</body>
</html>

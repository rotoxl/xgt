<!DOCTYPE html>
<%@page pageEncoding="UTF-8"%>
<%@ page import="es.burke.gotta.*" %>
<%@ page import="org.json.JSONObject"%>
<%
String usu = "";
String CAS_USER= "edu.yale.its.tp.cas.client.filter.user";
if(session.getAttribute(CAS_USER) != null) //Cliente CAS 2.1.1
    usu = session.getAttribute(CAS_USER).toString();
if (request.getRemoteUser() != null) //Cliente CAS 3.1.1
	usu = request.getRemoteUser();

Jsp.setHeaders(response); 

String apliFija=null;
if (request.getParameter("aplicacion")!=null)
	apliFija=request.getParameter("aplicacion");
if (apliFija!=null && !PoolAplicaciones.vListado.containsKey(apliFija))
	apliFija=null;
String css2=Constantes.CAD_VACIA;
if (apliFija!=null) 
	css2=Jsp.getCSS("2", "Aplicaciones/"+apliFija+"/"+apliFija+".css");

apliFija=Util.noNulo(apliFija);
usu=Util.noNulo(usu);

String listaErrores="";//lista de errores para sacar en firebug
String errorLogin=""; //error al hacer login
if (!apliFija.equals(Constantes.CAD_VACIA)){
	Aplicacion apli=null;//=PoolAplicaciones.sacar(apliFija);
	try {
		apli=PoolAplicaciones.sacar(apliFija);
		}
	catch (ErrorConexionPerdida ee){
		//pass
		}
	catch (ErrorArrancandoAplicacion ee){
		//pass
		}
	
	Object sesAnterior=session.getAttribute("usuario"+apliFija);
	if (sesAnterior!=null && (sesAnterior instanceof RuntimeException || sesAnterior instanceof ErrorGotta || sesAnterior instanceof ErrorSesionCerrada)){
		//no se entra automáticamente
		errorLogin=Util.noNulo(session.getAttribute("errorLogin"));
		
		session.removeAttribute("usuario"+apliFija);
		session.removeAttribute("errorLogin");
		}
	else if (apli!=null){
		IValidacion validacion=apli.getMetodoValidacion(); 
		if (!validacion.requiereUsuarioyContrasenha){
			application.getRequestDispatcher("/valida?aplicacion="+apliFija+"&login=&password=").forward(request,response);
			}
		}
	}


%>
<HTML > 
	<HEAD>
		<%=Jsp.getCSS("1", "./fijo/gotta.css")%>
		<%=css2%>
			<TITLE>..:: Gotta ::.. <%=apliFija%></TITLE>
		<link rel="shortcut icon" href="./fijo/g3.ico" type="image/x-icon">
		<link rel="icon" href="./fijo/g3.ico" type="image/x-icon">	
	<script type="text/javascript" src="./fijo/firebug/firebugx.js"></script>

		<script type="text/javascript">
			function ocuparVentana(){
				if (window.parent.location!=window.location)
					window.parent.location=window.location
				}
			
			function control(id){
				return document.getElementById(id)
				}
			function abre(action){
				var cmd=control('cmdLogin')
				if (cmd.disabled) return
				
				cmd.disabled=true
				cmd.style.opacity=.5
				
				control('throbber').style.visibility='visible'
				control('cmdDic').style.opacity=.5
				var msg=control('msgError')
				if (msg) msg.style.visibility='hidden'
				
				var frm=control("frmLogin") //$NON-NLS-1$
				frm.action=action
				frm.submit()
				return false
				}
			function abreDiccionario(){
				abre('diccionario2.jsp'  )
				}
			function abreExplorador(){
				abre('valida')
			}
		</script>
	</HEAD>
<BODY id="bodyLogin" onLoad="ocuparVentana();document.frmLogin.login.focus();" >

	<FORM accept-charset="<%=Constantes.CODIF%>" name="frmLogin" id="frmLogin" method="post" action="valida" onsubmit="abreExplorador();return false">
		
		<div class="spacer"></div>
		<div class="fondoLogin" >
			<div class="cuadroLogin"
			<%if (!usu.equals(Constantes.CAD_VACIA)){%> style="display: none" <%}%>
			>
                                        <div id="controlAccesoLogin"> 
					<label id="literalControlAccesoLogin">Control de acceso</label>
					<label id="literalListaAplicacionesLogin" for="listaAplicacionesLogin">Aplicaci&oacute;n</label>
					<select id="listaAplicacionesLogin" name="aplicacion" >
						<%    
						if (PoolAplicaciones.vListado!=null){
						for (String aplicacion : PoolAplicaciones.vListado.keySet()) {
							Object valor=PoolAplicaciones.vListado.get(aplicacion);
							if (! (valor instanceof Boolean)) {
listaErrores+="console.warn("+JSONObject.quote(valor.toString())+");";
								continue;
								}
							String sel=Constantes.CAD_VACIA;
							if (aplicacion.equals(apliFija)) 
								{sel="selected";}%>
							<option value="<%=aplicacion%>" <%=sel%> ><%=aplicacion%> </option>
							<%}
							}%>
					</select>
                                        </div>
                                        
                                        <label id="literalUsuLogin" for="usuLogin">Usuario</label> 
					<input id="usuLogin" type="text" name="login" size="16" value="<%if (usu!=null) {%><%=usu%><%}%>"/>
                                        <label id="literalPasswordLogin" for="passwordLogin">Contrase&ntilde;a</label>  
					<input id="passwordLogin" type="password" name="password" size="16"/>
                                        
				<label id="literalRecordarmeLogin" for="recordarmeLogin">Recordarme en este equipo</label>  
					<input id="recordarmeLogin" type="checkbox" name="recordarme" >
					
				<input type="submit" id="cmdLogin" title="Acceso a la aplicaci&oacute;n" value="Acceder" onclick="abreExplorador()"/>
					<div id="throbber"></div>
				<div id="cmdDic" title="Acceso al diccionario de la aplicaci&oacute;n" onclick="abreDiccionario()"></div>
			</div>                              			
			<%if ( !errorLogin.equals(Constantes.CAD_VACIA) ) {%>
				<DIV class="msgErrorLogin" id="msgError"> <%=errorLogin%> </DIV>
			<%}%>		
       </div>
	</FORM>
<%if ((!usu.equals(Constantes.CAD_VACIA))&&(errorLogin.equals(Constantes.CAD_VACIA))){%>
	<script type="text/javascript">abreExplorador()</script>
<%}%>

<script type="text/javascript"><%=listaErrores%></script>
</BODY>
</HTML> 

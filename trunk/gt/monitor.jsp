<!DOCTYPE html>
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
	return;
	}
catch (ErrorConexionPerdida e) {
	msg=e.getMessage();
	return;
	}

if (usu==null || !usu.permisoMonitorSQL){
	%>
<HTML>
	<BODY><%=msg%>
	</BODY>
</HTML>
<%	
	return;
	}

Aplicacion ap = usu.getApli();
%>
<HTML>
<head>
<%=Jsp.getTitle("Monitor")%>
<link id="css1" charset="UTF-8" href="./fijo/gotta.css" type="text/css" rel="STYLESHEET">
<link id="css2" charset="UTF-8" href="./fijo/asistentes.css" type="text/css" rel="STYLESHEET">
<%=Jsp.getScript(usu)%>
 <link rel="shortcut icon" href="./fijo/exp.monitor.on.png" type="image/x-icon">
<SCRIPT TYPE="text/javascript">
	var aplicacion="<%=ap.getCd()%>"
	var icoGotta='./fijo/', icoWeb=''
	var exp={dispositivoMovil: esDispositivoMovil()}
	var jwerty=null
	
	function oculta(id) {
		var tr=document.getElementById(id)
		tr.style.display=(tr.style.display == 'none' ? '' : 'none')	
		}
	function montaTodo(){
		loadJSONDoc('json', {'accion':'getAplicaciones','aplicacion': aplicacion} ,
				function(req){
					monitor=new MonitorSQL(xeval(req))
					monitor.pintaAplicaciones()
					}
				)
		}
	var monitor
	function MonitorSQL(datos){
		this.aplicaciones=datos
		this.cont=control('monitor')
		this.lvw=new Object()
		this.cargados=[]
		}
	MonitorSQL.prototype.pintaAplicaciones=function () {
		borraHijos(this.cont)
		for (var i=0; i<this.aplicaciones.length; i++){
			var app=this.aplicaciones[i]
			this.pintaAplicacion(app.cd, app.listaUsuarios, app.marcaBD)
			}
		}
	function fnOculta(id){
		return function(event){
			event=event || window.event
			var src=event.target||event.srcElement
			if ( ['DIV','SPAN'].contains( src.tagName ) ) {
				if (src.parentNode.tagName!='TD')
					oculta(id)
				}
			}
		}
	MonitorSQL.prototype.pintaAplicacion=function(cdApli, listaUsuarios, marcabd){
		var divApli=creaObj('div', 'aplicacion', cdApli, cdApli + ' ('+marcabd+')')
		divApli.title='[Click aquí para mostrar/ocultar]'
		divApli.onclick=fnOculta('usu'+cdApli)
		this.cont.appendChild( divApli )
		
		//un botón de vaciar el log por aplicación
		var botonVaciaLog=creaObj('button', 'btnVaciaLog')
		botonVaciaLog.title='Vaciar registro'
		botonVaciaLog.onclick=function(){monitor.vaciaLog(cdApli)}
		divApli.appendChild(botonVaciaLog)
		
		this.botonVaciaLog=botonVaciaLog
		
		var texto= listaUsuarios.length==0?'(sin usuarios)':null
		var divApli2=creaObj('div', 'aplicacionUsuarios', 'usu'+cdApli, texto)
		this.cont.appendChild( divApli2 )
		
		var temp, usuario, idSesion
		for (var j=0; j<listaUsuarios.length; j++) 
			this.montaDivUsu(divApli2, cdApli, listaUsuarios[j])
		}
	MonitorSQL.prototype.getNavegador=function(cadena){
		cadena=(cadena+'').toLowerCase()
		return this.buscaCacho(cadena, 'flock') ||
			this.buscaCacho(cadena, 'chrome') ||
			this.buscaCacho(cadena, 'firefox') || 
			this.buscaCacho(cadena, 'msie') || 
			this.buscaCacho(cadena, 'applewebkit')
			cadena
		}
	MonitorSQL.prototype.getDispositivo=function(cadena){ 
		cadena=(cadena+'').toLowerCase()
		return this.buscaCacho(cadena, 'iphone') || this.buscaCacho(cadena, 'ipad') || 
			this.buscaCacho(cadena, 'maemo') ||
			this.buscaCacho(cadena, 'android') ||
			this.buscaCacho(cadena, 'linux') ||
			this.buscaCacho(cadena, 'macintosh') ||
			'pc'
		}
	MonitorSQL.prototype.quitaNumVersion=function(cadena){
		if(cadena==null)
			return null
		var sep=' ';
		if (cadena.contains('/'))	
			sep='/'
		return cadena.split(sep)[0]
	}
	MonitorSQL.prototype.buscaCacho=function(cadena, buscar){
		var pos=cadena.indexOf(buscar)
		if (pos>-1){
			var resto=cadena.substring(pos)
			
			var sumar= buscar=='msie'?8:resto.indexOf(' ')
			if(sumar==-1)
				sumar=resto.length
			var ret=resto.substring(0, sumar)
			if (ret.contains(';'))
				return ret.substring(0, ret.indexOf(';'))
			else
				return ret
			}
		
		return null
	}
	MonitorSQL.prototype.montaDivUsu=function (cont, cdApli, usu){
		var idSesion=usu.idSesion
		var cdUsuario=usu.usuGotta
		var horaConexion=usu.horaConexion
		var horaDesconexion=usu.horaUltimoAcceso
		
		var idControl=idSesion+'@'+cdApli
		if (control('x'+idControl))
			return
		
		var divUsu=creaObj('div', 'usuario', 'x'+idControl, cdUsuario || '(anónimo)')
		
		if (usu.finalizado) ponEstilo(divUsu, 'finalizado')
		
		if (idSesion!='-1'){
			var spanHoraConexion=creaObjProp('span', {className:'horaConexion', title:'Inicio de sesión', texto:formatearFechaRelativaConSegundos(horaConexion) })
			var spanHoraDesconexion=creaObjProp('span', {className:'horaConexion desconexion', title:'Último acceso', texto:formatearFechaRelativaConSegundos(horaDesconexion) })
			
			divUsu.appendChild( spanHoraConexion ); divUsu.appendChild( spanHoraDesconexion )
			
			for (var k in usu.varios){
				var valor=usu.varios[k]
				var clase=null
				var dispositivo=null
				if (k=='navegador') {
					valor=this.getNavegador(valor)
					clase=this.quitaNumVersion(valor)
					}
				else if (k=='ipcliente'){ //saco también el dispositivo
					dispositivo=this.getDispositivo( usu.varios['navegador'])
					}
				var xspan= creaObj('span', k+(dispositivo?' '+dispositivo:''), null, valor) 
				if (clase)
					ponEstilo(xspan, clase)
				if (k=='navegador') 
					xspan.title=usu.varios[k]
				divUsu.appendChild(xspan)
				}
			}
		divUsu.title='[Click aquí para mostrar/ocultar]'
		cont.appendChild(divUsu)
		
		var divSQL=creaObj('div', 'usuarioSQL', idControl) 
		divUsu.appendChild( divSQL )
		
		var self=this 
		$(divUsu).click(function(event){
			var clave=idControl+'-'+cdApli+'-'+idSesion 
			if (!monitor.cargados.contains(clave)){ 
				monitor.cargados.push(clave) 
				ponEstilo(control('x'+idControl), 'cargando')
				self.creaTablaSQL(idControl, cdApli, idSesion) 
				}                
			var fn=fnOculta(idSesion+'@'+cdApli)
			fn(event || window.event)
			})
			
		if (usu.esUsuarioActual) {
			ponEstilo(divUsu, 'usuarioActual')
			
			var clave=idControl+'-'+cdApli+'-'+idSesion 
			monitor.cargados.push(clave) 
			self.creaTablaSQL(idControl, cdApli, idSesion) 
			}
		else
			divSQL.style.display='none'
		}
	MonitorSQL.prototype.creaTablaSQL=function (idControl, idApli, idSesion){
		this.lvw[idControl]=new Lista( idControl, true)
		this.cargaSQL( idControl, idApli, idSesion)
		}
	MonitorSQL.prototype.actualizaAplicaciones=function(){
		for (var ap=0; ap<this.aplicaciones.length; ap++){
			var apli = this.aplicaciones[ap]
			var idApli=apli.cd
			for (var u=0; u<apli.listaUsuarios.length; u++){
				var usuario=apli.listaUsuarios[u]
				
				var idSesion=usuario.idSesion
				var idControl=idSesion+'@'+idApli
				this.actualizaTablaSQL(idControl, idApli, idSesion)
				}
			}
		}
	MonitorSQL.prototype.actualizaTablaSQL=function (idControl, idApli, idSesion){
		if (this.lvw[idControl]){
			this.lvw[idControl].borraHijos()
			this.cargaSQL( idControl, idApli, idSesion)
			}
		}
	MonitorSQL.prototype.vaciaLog=function(cdApli){
		ponEstilo(this.botonVaciaLog, 'cargando')
		loadJSONDoc('json', {'accion':'vaciaFilasMonitor','aplicacion':cdApli} ,
				function(req){ 
					monitor.actualizaAplicaciones()
					quitaEstilo(monitor.botonVaciaLog, 'cargando')
					})
				
		}
	MonitorSQL.prototype.cargaSQL=function(idControl, cdApli, cdUsuario){
		loadJSONDoc('json', {'accion':'getFilasMonitor','aplicacion':aplicacion, 'apliCargar':cdApli, 'usuario':cdUsuario} ,
				function(req){
					
					var datos=xeval(req)
					monitor.lvw[idControl].rellenaLista(['Hora\\hh', 't (ms)','SQL', 'Parámetros', 'Núm filas'], ['hora', 'tiempo', ['objetoSQL', 'sql'], 'params', 'numFilas'], datos, null, null, null, true, null, null, ['lvwFecha', 'lvwNumero','lvw multidato','lvw','lvwNumero'], 'tipo')
					quitaEstilo(control('x'+idControl), 'cargando')
					}
				)
		}
</SCRIPT>
</head>
<BODY id="monitorSQL" style="overflow:auto;" onLoad="montaTodo()"><div id="monitor"></div>
</BODY>
</HTML>


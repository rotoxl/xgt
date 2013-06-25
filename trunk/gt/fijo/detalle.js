var espacioDuro='\u00a0'
var espacioDuro3=espacioDuro+espacioDuro+espacioDuro
var espacioDuro4=espacioDuro+espacioDuro+espacioDuro+espacioDuro+espacioDuro

var listaProps=null
var escala=15, limAltoPanel=300 // para diseño del panel izquierdo
var sep='\\', pipe='|', COMILLADOBLE='"', COMILLASIMPLE="'"

var exp, disenhador, depuradorActivo, editorNivelesActivo=null
var splitterV

var cartaColoresApli=[]
function anhadeACartaColores(colorFinal){
	if (!cartaColoresApli.contains(colorFinal))
		cartaColoresApli.push(colorFinal)
	divSet=false
}
function borraHijos(padre){
	if (!padre)
		return
	while (padre.firstChild!=null)
		padre.removeChild( padre.firstChild )
}
function borra(ctl){
	ctl.parentNode.removeChild(ctl)
	}
////////////////////////////////////////
function datoQueSeMuestra(id, evt){
	evt = new Evt(evt)
	evt.consume()
	disenhador.md.getControl(id).datoQueSeMuestra()
	return false
}
function cambiaML(ml, urlDestino){
	try {
		exp.cambiaML(ml, urlDestino)
		}
	catch (e){
		console.error('ERROR '+e.message + '\nen '+e.fileName+' linea '+e.lineNumber)
		}
}
////////////////////////
function confirmarSalir() {
	//~ var advertir=false
	//~ try {
		//~ if (listaFrm!=null && listaFrm[0]!=null)
			//~ advertir=true
		//~ else if (disenhador!=null && disenhador.hayQueGuardar() )
			//~ advertir=true
		//~ else if (editorNivelesActivo!=null)
			//~ advertir=true
		//~ }
	//~ catch (e) {
		//~ advertir=true
		//~ }
	//~ if (advertir)
		//~ return "¡Atención! Esta acción refrescará la aplicación entera, y cualquier dato no salvado se perderá."
	}
///////////// splitter del explorador
var dif=6
function sacaW(){
	return $('html').innerWidth()- (exp.modoDisenho? $('#botoneraDisenho').outerWidth():0) +dif
	}
function sacaH(){
	return xClientHeight()-$('.header.panelSup').outerHeight()+dif
	}
//////////////////////////////////////////////
function esDispositivoMovil(){
	return navigator.userAgent.containsIgnoreCase('mobile')
	}
//////////////////////////////////////////////
function Explorador(aplicacion, icoWeb, icoGotta, acceptCharset){
	this.modoDisenho=false
	this.modoDepuracion=false

	this.listaBotonesIzq=[
		creaObjProp('span',{className:'botonera separador', src:'./fijo/blank0.png', id:'menuVer',			texto:espacioDuro, 'style.display':'none'}),
		creaObjProp('span',{className:'botonera separador', src:'./fijo/blank0.png', id:'menuEjecutar', 		texto:espacioDuro, 'style.display':'none'}), 
		creaObjProp('span',{className:'botonera separador', src:'./fijo/blank0.png', id:'menuMantenimiento', 	texto:espacioDuro, 'style.display':'none'})
		]
		
	this.idMlActivo=null
	this.idMdActivo=null
	
	this.listaModosDetalle=new Object()
	this.listaModosLista=null
	
	this.aplicacion=aplicacion 	//se rellena en explorador.jsp
	this.icoWeb=icoWeb 		//se rellena en explorador.jsp con lo que venga en dic_configuracion
	this.icoGotta=icoGotta
	this.acceptCharset=acceptCharset
	
	this.internetExplorer= esInternetExplorer()
	this.internetExplorer6=esInternetExplorer6()
	this.dispositivoMovil=esDispositivoMovil()
		
	var nav=$.browser
	var navStr
	if (nav.mozilla)
		navStr='mozilla'
	else if (nav.msie)
		navStr='ie'+Math.floor(nav.version)
	else if (nav.safari || nav.webkit)
		navStr='webkit'
	
	$('body#explorador').addClass(navStr)
	
	if (this.dispositivoMovil)
		$('body#explorador').addClass('movil')
	
	this.arbol=null
	
	this.permisoDisenho=false
	
	this.filasSeleccionadas=[]
	this.scrollVertical=true
	
	if(this.internetExplorer)
		document.documentElement.style.overflow='hidden'
}
////////
Explorador.prototype.textoSeleccionarTodos='Seleccionar todos'
Explorador.prototype.textoInvertirSeleccion='Invertir selección'
Explorador.prototype.textoEjecutar='Ejecutar'
Explorador.prototype.textoRevivir='Consultar datos del trámite'
Explorador.prototype.textoReemitir='Volver a emitir documentación asociada'
Explorador.prototype.textoDejarPendiente='Añadir a la lista de tareas pendientes'

Explorador.prototype.textoVerInformacionFirmada='Ver información firmada'
Explorador.prototype.textoVerInformacionFirma='Ver información de la firma'
Explorador.prototype.textoValidarFirma='Validar firma'
//
Explorador.prototype.ttpBtnDisenhadorON='[Diseño activo] Desactivar diseño'
Explorador.prototype.ttpBtnDisenhadorOFF='[Diseño inactivo] Activar diseño'
	
Explorador.prototype.ttpBtnDepuradorON='[Depuración activa] Desactivar depuración'
Explorador.prototype.ttpBtnDepuradorOFF='[Depuración inactiva] Activar depuración'
	
Explorador.prototype.ttpBtnConstructor='Mapa de tramitación'
	
Explorador.prototype.ttpBtnMonitorSQL='Monitor de la aplicación'
Explorador.prototype.ttpBtnRecargarAplicacion='Recargar aplicación'
	
Explorador.prototype.ttpBtnRefrescar='Actualizar (F5)'
Explorador.prototype.ttpBtnNuevaVentana='Nueva ventana: inicia una nueva sesión con filtros de búsqueda independientes'
Explorador.prototype.ttpBtnVersionParaImprimir='Versión para imprimir'
//~ Explorador.prototype.ttpBtnCambiarContrasenha
Explorador.prototype.ttpBtnSalir='Salir'
Explorador.prototype.cambiaHistorial=function(nodo){
	if (history.pushState)
		history.pushState(nodo, 
				'Vista '+nodo.ml+(nodo.url?', nodo '+nodo.url:'')  ,
				'?aplicacion='+exp.aplicacion+'&vista='+nodo.ml+ (nodo.nodo!=null?'&nodo='+nodo.nodo: '')
				) //obj, texto, url
	}
Explorador.prototype.habilitaHistorial=function(){
	try {
		window.addEventListener('popstate', function(event) {
			var nodo=event.state
			
			if (nodo==null)
				return
				
			var id=Arbol.prototype.arreglaCDfila(nodo.nodo)
			
			if (nodo.ml!=exp.idMlActivo)
				exp.cambiaML(nodo.ml)
			
			var dom=$('#divarbol tr#fila_'+id+' td.contenido')
			if (dom.size()==0){
				console.info('nodo no encontrado')
				}
			else {
				$('#divarbol tr.arbol td.contenidoS').removeClass('contenidoS')
				$('#divarbol tr#fila_'+id+' td.contenido').addClass('contenidoS')
				
				exp.cambioNodoActivo(nodo.nodo, nodo.md, true)
				}
			})
		}
	catch (e){
		}
	}
Explorador.prototype.onResize=function(inicializar){
	var h = sacaH()
	var w = sacaW()
	
	var posBarraV	
	var mdActivo=null
	if (exp!=null && exp.idMdActivo && exp.getMD(exp.idMdActivo)!=null)
		mdActivo=exp.getMD(exp.idMdActivo)
		
	if (inicializar==true || splitterV==null){
		posBarraV=Number(recuperaDato('splitter', 'left'))
		if (isNaN(posBarraV) || posBarraV<=0) 
			posBarraV=w/6
		
						   // sSplId,    X, Y, 					 W, H,  Horz , 		BarW, BarPos, 		BarLim,    BarEn, BorW, oSplChild1, oSplChild2				       
		splitterV = new xSplitter( 'splitterV', 0, $('.header.panelSup').outerHeight(), w, h, exp.scrollVertical, 7,    posBarraV,    	0,         true,  3);	

		var self=this
		xAddEventListener(window, 'resize',  function(){self.onResize()}, false)
			
		var badm=control('botoneraAdmin')
		var bd=control('botoneraDra')
		
		var recoloca=function(){ badm.style.right=bd.offsetWidth+5+'px'}
		if (versionInternetExplorer()==7)
			window.setTimeout(recoloca,2000)
		else
			recoloca()
		}
		
	else  {
		var mlActual=exp.getML(exp.idMlActivo)
		
		var posBarraV=splitterV.getPosBarra()
		if (mlActual && mlActual.tipoPanel1==0 && (!disenhador || !exp.modoDisenho))
			splitterV.ocultarPanelIzq()
		else {
			var esVertical=splitterV.getScrollVertical()
			if ( esVertical != exp.scrollVertical) 
				splitterV.setScrollVertical(exp.scrollVertical)
				
			posBarraV=posBarraV>0?posBarraV : Number(recuperaDato('splitter', 'left'))
			if (isNaN(posBarraV) || posBarraV<=0) 
				posBarraV=w/6
			
			}
		salvaDato('splitter', 'left', posBarraV)
		splitterV.paint(w, h, posBarraV, 0);  // w, h, BarPos, BarLim
		}
	var xdivarbol=control('divarbol')
	if (xdivarbol.clientHeight) //sólo si se trata de una vista con árbol
		xdivarbol.style.height = xdivarbol.parentNode.clientHeight-xdivarbol.offsetTop+'px'	
	
	}
Explorador.prototype.inicializa=function(estadoDisenho, estadoDepuracion, ml){
	if (ml=='null') ml=null
	exp.cargaVistas(ml)
	
	this.onResize(true)
	
	if (estadoDepuracion) 
		activarDepuracion()
		
	if (exp.internetExplorer && versionInternetExplorer()<9)
		borra(control('btnDisenhador'))
	else if (estadoDisenho) 
		activarDisenho()
	
	this.tareasPostCarga()
	this.traduceBarraHerramientasAdmin()
	this.habilitaHistorial()
	this.ponEventosTeclado()
}
Explorador.prototype.traduceBarraHerramientasAdmin=function(){
	$('#btnDisenhador').attr('title', exp.modoDisenho?Explorador.prototype.ttpBtnDisenhadorON:Explorador.prototype.ttpBtnDisenhadorOFF)
	$('#btnDepurador').attr('title', (exp.modoDepuracion)?Explorador.prototype.ttpBtnDepuradorON:Explorador.prototype.ttpBtnDepuradorOFF)
		
	$('#btnConstructor').attr('title', Explorador.prototype.ttpBtnConstructor)
		
	$('#btnMonitorSQL').attr('title', Explorador.prototype.ttpBtnMonitorSQL)
	$('#btnRecargarAplicacion').attr('title', Explorador.prototype.ttpBtnRecargarAplicacion)
		
	$('#btnRefrescar').attr('title', Explorador.prototype.ttpBtnRefrescar)
	$('#btnNuevaVentana').attr('title', Explorador.prototype.ttpBtnNuevaVentana)
	$('#btnVersionParaImprimir').attr('title', Explorador.prototype.ttpBtnVersionParaImprimir)
	
	$('#btnSalir').attr('title', Explorador.prototype.ttpBtnSalir)
	}
Explorador.prototype.cargaVistas=function(mlInicial){
	loadJSONDoc ( 'json', { 'accion':'vistasEXP', aplicacion:this.aplicacion},
				function(req) {
					var x=xeval(req)
					if (!x.menu || x.menu.filas.length==0){
						var literal
						if (!x.menu)
							literal='No hay datos o no existe el nivel menuGeneral'
						else
							literal='No hay datos en EXP_Menu'
						x={menu:{
							filas:[	[null, literal, null,'1','menuVer','0','carpeta','','','','','','','v','1',null,'1','menuglobal']],
							columnas:['imagen','texto','titulo','CD_Boton','CD_BarraHerramientas','CD_MenuPadre','Imagen','Texto','Titulo','CD_Camino','CD_Tramite','CD_ModoLista','URL','disposicion','tipoPanel1','cd_nivelInicial','botonera','tipo'],
							tipos:['lvw','lvw','lvw','lvw','lvw','lvw','lvw','lvw','lvw','lvwNumero','lvw','lvwNumero','lvw','lvw','lvw','lvw','lvw','lvw','lvw','lvw','lvw','lvw','lvw','lvw']}}
						}
					exp.listaModosLista=exp.extraeSoloVistas(x.menu)
					if (mlInicial) exp.cambiaML(mlInicial)
					
					var filas=x.menu
					//~ filas=exp.completaInfoParaMenu(x.menu)
					
					exp.cache=exp.cache || {}
					exp.cache['menuGlobal']=filas
					exp.montaEstructuraMenu()
					exp.montaMenu(filas, 'menuGlobalDeLaAplicacion')						
					if (mlInicial) resaltaCaminoMenuPinchado(control('ml'+mlInicial))	
					exp.montaMenuIdiomas()
					})
}
function sacaPosxId(filas, id){
	return filas.columnas.indexOfIgnoreCase(id)
}
function rellenaPosxId(filas){
	var ret={}
	
	for (var i=0;i<filas.columnas.length; i++){
		var nombre=filas.columnas[i]
		ret[nombre.toLowerCase()]=sacaPosxId(filas, nombre)
		}
	
	return ret
	}
Explorador.prototype.extraeSoloVistas=function(filas){
	var ret={}
	
	var p=rellenaPosxId(filas)
	
	for (var i=0; i<filas.filas.length; i++){
		var menu=filas.filas[i]
		if  (menu==null)
			continue
		var cd=menu[p.cd_modolista]
		if (cd) {
			ret[cd]={	'cd':cd, 
					'ds':menu[p.texto], 
					'botonera':	toBool(menu[p.botonera]),
					'tipoPanel1': Number(menu[p.tipopanel1]),
					'disposicion': toNull(menu[p.disposicion]) || 'v'}
			}
		}
	return ret
}
function toBool(v){
	var temp=toNull(v)
	if (temp)
		switch (typeof(temp)){
			case 'string': 
				if (temp=='0' || temp=='false')
					return false
				else
					return true
			case 'number':
				return temp==1
			default:
				return temp
		}
	return false
	}
function toNull(v){
	if (v && v=='')
		return null
	return v
	}
Explorador.prototype.montaMenuIdiomas=function(){
	if (!control('idiomas')) 
		return
	
	cargarLista(control('idiomas'), 'idiomasAplicacion', 'cd', 'ds', true, true, false, this.idioma)
	
	var self=this
	$('#idiomas').change(function(){self.cambiaIdioma(this.value)})
	}
Explorador.prototype.cambiaIdioma=function(cd_idioma){
	if (this.idioma==cd_idioma)	return
	
	$('body').removeClass(this.idioma).addClass(cd_idioma)
	this.idioma=cd_idioma.toLowerCase()
	
	loadJSONDocPost('json', {accion:'cambiaIdioma', idioma:cd_idioma, aplicacion:this.aplicacion},
				function(req){
					var datos=xeval(req, true)
					if (datos.tipo=='ok') {
						listaProps=null
						exp.cargaVistas() //refrescamos menús y detalles
						
						for (var id in exp.listaModosDetalle){
							var md=exp.listaModosDetalle[id]
							md.limpiaRestos()
							delete exp.listaModosDetalle[ id.toLowerCase() ]
							}
						$('#botoneraIzq ul').remove()
						$('meta')[0].content=cd_idioma
						
						var xidML=exp.idMlActivo
						exp.idMlActivo=null
						exp.refrescaTodo(xidML)
						
						exp.tareasPostCarga()
						}
					else if (datos.tipo=='error')
						alert('No se ha podido cambiar el idioma. Se ha producido el siguiente error: '+datos.msg)
					})
	}
Explorador.prototype.montaEstructuraMenu=function(){
	var lista=jQuery.map(this.listaBotonesIzq, function(btn){return envuelve(btn)})
	$('#botoneraIzq').append( creaObjProp('ul', {hijos:lista}) )
}
Explorador.prototype.montaMenu=function(filas, claseAdicional){//N menus desplegables, con lanzamiento de trámites etc. 
	var p=rellenaPosxId(filas)
	
	var listaBarras=[]
	var xxul={}
	var xul={}
		
	//3 casos especiales con los idiomas
	p.imagen_LAN	= p['imagen_'+this.idioma] 	|| p.imagen
	p.texto_LAN	= p['texto_'+this.idioma]	|| p.texto
	p.titulo_LAN	= p['titulo_'+this.idioma]	|| p.titulo
		
	for (var i=0; i<filas.filas.length; i++){	
		var ml=filas.filas[i]
		
		if (ml==null) break
		var cd		=toNull(ml[p.cd_modolista] )
		
		var cdNivelInicial	=toNull(ml[p.cd_nivelinicial] )
		
		var cd_camino	=toNull(ml[p.cd_camino] )
		var cd_tramite	=toNull(ml[p.cd_tramite])
		var url		=toNull(ml[p.url])
		var imagen		=toNull(ml[p.imagen_LAN]) 	|| toNull(ml[p.imagen])
		var titulo 		=toNull(ml[p.titulo_LAN]) 	|| toNull(ml[p.titulo])
		var ds		=toNull(ml[p.texto_LAN]) 	|| toNull(ml[p.texto]) 	|| espacioDuro4
		
		var cd_menupadre=ml[p.cd_menupadre]
		var cd_barraherramientas=ml[p.cd_barraherramientas]
		var cd_boton=ml[p.cd_boton]
		
		if (!xxul[cd_barraherramientas]) {
			var insertar=$('#'+cd_barraherramientas)
			
			if (insertar.hasClass('dinamico'))
				insertar=$('#'+cd_barraherramientas +' > span')
			
			if (insertar.length==0){
				var nuevaBarra=creaObjProp('div', {tabIndex:0, className:'nav botonera jquerycssmenu dinamico', id:cd_barraherramientas})
				control('explorador').appendChild(nuevaBarra)
				
				var el0=envuelve(creaObjProp('span', {className:'botonera separador', 'style.display':'none'}))
				var ul0=creaObjProp('ul', {hijo:el0})
				
				nuevaBarra.appendChild(ul0)
				insertar=$(el0)
				}
			xxul[cd_barraherramientas]=insertar
			insertar.css({display:'none'})
			
			var barra=insertar.parents('.nav.botonera')
			if (! (barra in listaBarras))
				listaBarras.push(barra)
			}
			
		var idxul='menu'+cd_boton
		var idMenu='menu__'+cd_boton		
		var idxulpadre='menu'+cd_menupadre
		if (cd_menupadre=='nivel0' || cd_menupadre=='0'){
			var listaHijos=null
			
			if (ds.startsWith('-')){
				ds=ds.substring(1)
				var hijo=creaObjProp('a', {id:idMenu, title:titulo, className:'botonera separador '+claseAdicional, texto: ds})
				listaHijos=[hijo]
				}
			else {
				var enlace=creaObjProp('a', {id:idMenu, title:titulo, className:'botonera '+claseAdicional, title:titulo, texto:ds, 'style.backgroundImage':completaRutaImg(imagen, true)}) 
				
				if (imagen) ponEstilo(enlace, 'imagen')
				if (ds!= espacioDuro4) ponEstilo(enlace, 'texto')
				
				enlace=this.enchufaOnClick(enlace, cd, url, cd_camino, cd_tramite)
				listaHijos=[enlace]
				
				if (!cd && !cd_tramite && !url){
					ponEstilo(enlace, 'dropdownmenu')
					xul[idxul]=jQuery(creaObjProp('ul', {id:'submenu'+cd_boton, className:claseAdicional}))
					listaHijos.push(xul[idxul][0])
					}
				}
			var xpp=xxul[cd_barraherramientas]
			if (xpp[0].tagName=='UL'){
				xpp.append( creaObjProp('li', {id:'menu'+cd_boton, hijos:listaHijos}) )
				}
			else	{
				if (xpp[0].tagName!='LI')
					xpp=xpp.parents('li')
				var siguiente=xpp.next('li.botonera.separador')
				var trozo=creaObjProp('li', {id:'menu'+cd_boton, hijos:listaHijos})
				
				if (siguiente.length==0)
					xpp.parent().append(trozo)
				else
					$(trozo).insertBefore(siguiente)
				}
			}
		else if (!cdNivelInicial && !cd && !cd_tramite && !url){ //una opción con submenús
			var xobj, listaHijos
			
			if (ds.startsWith('-')) {
				ds=ds.substring(1)
				var hijo=creaObjProp('a', {id:idMenu, title:titulo, className:'botonera menuML separador '+claseAdicional, texto: ds})
				listaHijos=envuelve(hijo)
				}
			else 	{
				xul[idxul]=jQuery(creaObjProp('ul', {id:'submenu'+cd_boton, className:claseAdicional}))
				listaHijos=[creaObjProp('a', {id:idMenu, title:titulo, className:'botonera menuML droprightmenu '+claseAdicional, texto: ds, 'style.backgroundImage':completaRutaImg(imagen, true)}), xul[idxul][0] ]
				}
			var xp=xul[idxulpadre]
			if (xp==null)
				alert('El menu #'+cd_boton+' cuelga de un padre que no existe o que tiene nivel inicial, trámite o url, por lo que no puede ser contenedor (#'+cd_menupadre+') y no puede ser mostrado')
			else
				xp.append( envuelve(listaHijos) )
			}
		else { 
			var enlace, listaHijos
			if (ds.startsWith('-')) {
				ds=ds.substring(1)
				enlace=creaObjProp('a', {id:idMenu, title:titulo, className:'botonera menuML separador '+claseAdicional, texto: ds})
				}
			else  {
				enlace=creaObjProp('a', {id:idMenu, title:titulo, className:'botonera menuML '+claseAdicional, texto:ds, 'style.backgroundImage':completaRutaImg(imagen, true)})
				enlace=this.enchufaOnClick(enlace, cd, url, cd_camino, cd_tramite)
				}
				
			listaHijos=[envuelve(enlace)]
			var xp=xul[idxulpadre]
			if (xp==null)
				alert('El menu #'+cd_boton+' cuelga de un padre que no existe (#'+cd_menupadre+') y no puede ser mostrado')
			else
				xp.append( listaHijos )
			}
		}
	$('#throbberVistas').css({display:'none'})
	
	var mlActual=this.getML(this.idMlActivo)
	for (var b=0;b<listaBarras.length;b++){
		var barra=listaBarras[b]
		
		if (barra[0]){
			var barra0=barra[0]
			if (!mlActual || mlActual.botonera)  {
				if (barra.hasClass('dinamico'))
					barra.css({display:'block'})
				else
					barra.css({display:'inline'})
				}
			
			var ulVacios=$('#'+barra0.id+' ul ul:empty')
			var li=ulVacios.parent('li')
				li.children('a').removeClass('droprightmenu').addClass('prohibido')
				li.removeClass('droprightmenu')
			ulVacios.remove()
				
			jquerycssmenu.buildmenu(barra)
			}
		}
}
Explorador.prototype.enchufaOnClick=function(enlace, cd_modolista, url, cd_camino, cd_tramite){
	if (url) {
		enlace.href=url
		if (esUrlExterna(url) )
			enlace.target='_blank'
		else
			enlace.target='_self'
		enlace.onclick=ocultaMenusPanSup
		}
	else if (cd_tramite) {
		enlace.onclick=fnClickMenuPersonalizado(cd_camino, cd_tramite) 
		}
	else if (cd_modolista){
		enlace.onclick=function(){cambiaML(cd_modolista); ocultaMenusPanSup(); resaltaCaminoMenuPinchado(enlace)}
		enlace.id='ml'+cd_modolista
		}
		 
	return enlace
}
function sumaTrozo(clave, valor, conSuma){
	var ret= ''
	if (conSuma)
		ret+='&'
	ret+=clave+'='+valor
	return ret
}
Explorador.prototype.cargaMD=function(idMd, url, fnPostCarga, noCargarControles){
	loadJSONDoc ( 'json', { 'accion':'md', 'md':idMd, aplicacion:this.aplicacion},
			function(req) {
					var md=new ModoDetalle(idMd, xeval(req), null, null, noCargarControles ) 
					md.montaMenuPersonalBBB()
					if (url) {
						md.nodoActivo=url
						if (!noCargarControles){
							md.rellenaControles(idMd)
							md.rellenaControles(exp.idMlActivo)
							}
						}
					else if (fnPostCarga)
						fnPostCarga()
					if (exp.modoDisenho) disenhador.cambioMD(md, exp.getMD(exp.idMlActivo))
					}, 
			function()
				{alert('Error cargando el modo detalle "'+idMd+'"')})
	}
Explorador.prototype.cambioNodoActivo=function(url, idMd, vieneDeHistory){
	if (vieneDeHistory==undefined)
		this.cambiaHistorial({nodo:url, md:idMd, ml:exp.idMlActivo})
	
	var md=this.getMD(idMd)
	
	var xImprimir=control('btnVersionParaImprimir')
	if (xImprimir) xImprimir.href="imprimir.jsp?aplicacion="+aplicacion+'&nodo='+url+'&md='+idMd

	//ocultamos el detalle anterior
	if (this.idMdActivo!=null && this.idMdActivo != idMd) {
		var mdAnterior=this.getMD(this.idMdActivo)
		if (mdAnterior!=null) {
			var divMdAnterior=control(mdAnterior.idx)
			
			if (divMdAnterior && divMdAnterior.parentNode.id=='panelDer')
				divMdAnterior.style.display='none'
			
			mdAnterior.ocultaMenuPersonalBBB()
			}
		}
	if (md && this.idMdActivo != idMd && !md.esPanelIzq) 
		md.montaMenuPersonalBBB()

	this.idMdActivo=idMd
	
	//cargamos el modo detalle
	if (md==null) 
		this.cargaMD(idMd, url)
	else	{ //ya existe
		
		if (!md.div) md.montaPanelYControles()
		
		var divMd=control(md.idx)
		divMd.style.display='block'
		
		if (url) {
			md.nodoActivo=url
			md.rellenaControles(idMd)
			md.rellenaControles(exp.idMlActivo)
			}
		}
	if (exp.modoDisenho)
		disenhador.cambioMD(this.getMD(idMd), this.getMD(exp.idMlActivo))
	}
Explorador.prototype.mdActivo=function(){
	if (!this.idMdActivo)
		return
	
	if (this.listaFormulariosModosDetalle) {
		var hay=saca1DeDic(this.listaFormulariosModosDetalle)
		if (hay)return hay
		}
		
	var id=this.idMdActivo.toLowerCase()
	return this.listaModosDetalle[id]
}
Explorador.prototype.listaControlesPorTipo=function(tipos){ 
	//* este método no puede estar en la clase ModoDetalle xq se envían todas las casillas del ModoLista y el ModoDetalle
	var retorno=[], listaMD=[], listaML=[]
	
	var hay
	if (this.listaFormulariosModosDetalle)
		hay=saca1DeDic(this.listaFormulariosModosDetalle)

	if (hay){
		return hay.listaControlesPorTipo(tipos)
		}
	else {
		if (this.idMlActivo){
			var md=this.listaModosDetalle[ this.idMlActivo.toLowerCase()]
			if (md) listaMD=md.listaControlesPorTipo(tipos)
			}
		if (this.idMdActivo){
			var ml=this.listaModosDetalle[ this.idMdActivo.toLowerCase()]
			if (ml) listaML=ml.listaControlesPorTipo(tipos)
			}
		return listaMD.concat(listaML)
		}
}
Explorador.prototype.colocaArbol=function(ml){
	if (ml==null) ml=this.getMD(this.idMlActivo)
	
	var alturaBotonera=$('.header.panelSup').outerHeight()
	var altura=0
	if (ml!=null){
		ml.div.style.display='block'
		
		var altura=ml.maxAltura
		//~ if (altura>0)
			//~ altura+=20
		if (exp.modoDisenho)
			altura=Math.max(ml.maxAltura, limAltoPanel)
		
		ml.div.style.height=altura+'px'
		}
	
	//~ var lista=control('listaPanelesIzq')
	//~ lista.style.top=alturaBotonera+'px' //73px
	
	var xdivarbol=control('divarbol')
	
	// Si el arbol es position:fixed
		//~ xdivarbol.style.top= alturaBotonera+altura+'px'
	
	// Si el arbol es position:absolute
	xdivarbol.style.top= altura+'px' 
	this.onResize(false)
	if (exp.internetExplorer){
		xdivarbol.parentNode.scrollLeft=0 	
		xdivarbol.scrollLeft=0 
		
		if (exp.internetExplorer6) {
			setTimeout(function (){
				xdivarbol.parentNode.parentNode.scrollLeft=0 	
				xdivarbol.parentNode.parentNode.parentNode.scrollLeft=0 	
				xdivarbol.parentNode.parentNode.parentNode.parentNode.scrollLeft=0 	
				xdivarbol.parentNode.parentNode.parentNode.parentNode.parentNode.scrollLeft=0 	
				},2000)
			}
		}
	
	}
Explorador.prototype.getMD=function(id){
	if (id==null) return 
	return this.listaModosDetalle[ id.toLowerCase() ]
}
Explorador.prototype.getML=function(id){
	if (id==null) return 
	return this.listaModosLista[ id ]
}
Explorador.prototype.cambiaMnuVer=function(idMl){
	var imgsrc, ds
	
	if (idMl==null) {
		imgsrc=icoGotta+'blank.png'
		ds='Ver'
		}
	else {
		if (!(idMl in this.listaModosLista))
			return false
		var ml=this.listaModosLista[idMl]
		 
		//~ imgsrc=this.icoWeb+ml.cd+'.png'	
		//~ ds=ml.ds	
		}
	//~ // imagen, texto, flechita
	//~ this.mnuVer.replaceChild(creaT(ds) , this.mnuVer.childNodes[1])
	//~ this.mnuVer.firstChild.src=imgsrc
	return true
}
Explorador.prototype.cambiaML=function(idMl, urlDestino){
	if (!this.cambiaMnuVer(idMl))
		return false
	
	var ocultarBotonera=function(){$('div.nav.jquerycssmenu').css({display:'none'})}
	var mostrarBotonera=function(){$('div.nav.jquerycssmenu').css({display:'inline'})	}
		
	//cargamos el modo lista
	var modoLista=this.getMD(idMl)
	if (modoLista==null){
		loadJSONDoc ( 'json', { 'accion':'ml', 'ml':idMl, aplicacion:this.aplicacion},
				function(req) {new ModoDetalle(idMl, null, xeval(req) ) }, 
				function(){alert('Error cargando el modo lista "'+idMl+'"')})
		}
	else  {
		var divML=control(modoLista.idx)
		if (divML.parentNode.id != 'listaPanelesIzq') {
			divML.parentNode.removeChild( divML )
			control('listaPanelesIzq').appendChild( divML )
			}
		divML.style.display='block'
		}
		
	if (this.idMlActivo!=null && this.idMlActivo != idMl ){
		var md_mlAnterior=this.getMD(this.idMlActivo)
		if (md_mlAnterior!=null){
			var divMlAnterior=control(md_mlAnterior.idx)
			divMlAnterior.style.display='none'
			}
		}
	
	//cambiamos el modolista seleccionado de la lista desplegable
	var antiguo=control('ml'+this.idMlActivo)
	if (antiguo) quitaEstilo(antiguo, 'estado_on')
		
	resaltaCaminoMenuPinchado(control('ml'+idMl))
	this.idMlActivo=idMl
	
	var mlActual=this.getML(this.idMlActivo)
	
	if (mlActual) {
		mlActual.tipoPanel1=Number(mlActual.tipoPanel1)
		
		//ocultamos la botonera y el panel izquierdo	
		if (mlActual.botonera)
			mostrarBotonera()
		else
			ocultarBotonera()
		
		var xdiv=control('divarbol').parentNode
		xdiv.id='panelIzq__'+idMl
		//y cargamos el árbol
		this.cargarArbol(idMl, mlActual.tipoPanel1==2, urlDestino)
		
		if (mlActual.tipoPanel1==0) 
			splitterV.ocultarPanelIzq()
		else {	
			if (mlActual.disposicion=='v')
				this.scrollVertical=true
			else if (mlActual.disposicion=='h')
				this.scrollVertical=false
			this.onResize(false)
			}
		}
	exp.colocaArbol()
	}
Explorador.prototype.cargarArbol=function(idMl, comoLista, urlDestino){
	var ml=exp.listaModosLista[idMl]
	if (ml) {
		var xcont='divarbol'
		var arb
		
		if (comoLista)
			arb=new ArbolComoLista(xcont)
		else {
			arb=new Arbol(xcont)
			arb.onClickNodo=function(url, md){exp.cambioNodoActivo(url, md)}
			}
		if (exp.arbol)  {
			arb.eventosTeclado=exp.arbol.eventosTeclado
			exp.arbol.aa_invalidado=true //traza
			delete exp.arbol
			}
		arb.urlDestino=urlDestino
		exp.arbol=arb
		arb.cargar(idMl, 0)
		}
}
Explorador.prototype.tareasPostCarga=function(){ 
}	
Explorador.prototype.refrescaTodo=function(modolista, idMdAutoexec){
	if (exp.listaFormulariosModosDetalle){
		//refrescamos flotantes
		for (var id in exp.listaFormulariosModosDetalle){
			var flotante=exp.listaFormulariosModosDetalle[id]
			if (flotante) 
				flotante.actualiza(id==idMdAutoexec)
			}
		}
	
	if (modolista && exp.idMlActivo!=modolista)
		cambiaML(modolista)
	else if (exp && exp.arbol)
		exp.arbol.cargar( exp.idMlActivo, 0)
	}
Explorador.prototype.ponEventosTeclado=function(){
	jquerycssmenu.ponEventosTeclado()
}
//////////////////
function ModoDetalle(idx, md, ml, nodoActivo, noCargarControles){
	if (idx==null)
		return
	
	var yaExiste=exp.listaModosDetalle[idx.toLowerCase()]
	if (yaExiste)//ya existe otro md con el mismo nombre: si el otro es un ml y este un md, avisamos que puede haber errores		
		return yaExiste
		
	var padre=null
	this.id=idx // EXPejercicio
	
	this.modificado=false

	this.tb=null
	this.letras=null
	this.nodoActivo=nodoActivo
	
	var tempErrores
	if (md)
		tempErrores=md.errores
	else if (ml)
		tempErrores=ml.errores
	
	this.errores=tempErrores?[tempErrores]:[]
	
	var datos=null
	this.esPanelIzq=null
	if (md){
		this.esPanelIzq=false
		datos=md
		this.idx='mododetalle__'+idx
		
		if (datos) {
			this.tb=md.tb
			this.letras=md.letras
			}
		this.filas=md.filas
		}
	else {
		this.esPanelIzq=true
		datos=ml
		this.idx='modolista__'+idx
			
		this.filas=ml.filas
		}
	
	this.filas=this.filas || []
	this.nombreDetalle=datos.cd || idx
	
	if (listaProps==null)
		listaProps=this.montaObjColumnas(datos.columnas)
	
	this.datosDetalle=datos
	this.listaControles=[]
	this.dicControles={}
	
	exp.listaModosDetalle[this.id.toLowerCase()]=this
	
	this.botonAutoexec=null
	this.formularioBSC=null
	
	if (noCargarControles==null)
		this.montaPanelYControles()
	else if (noCargarControles==false)
		this.montaPanelYControles()
}
ModoDetalle.prototype.montaObjColumnas=function(arr){
	if (!arr)
		return null
		
	var col=new Object()
	col.numControl=arr.indexOfIgnoreCase( 'NUMCONTROL' )

	col.ml=arr.indexOfIgnoreCase( 'ml' )
	col.md=arr.indexOfIgnoreCase( 'md' )

	col.tc=arr.indexOfIgnoreCase( 'TIPOCONTROL' )

	col.izq=arr.indexOfIgnoreCase( 'IZQ' )
	col.tope=arr.indexOfIgnoreCase( 'TOPE' )
	col.ancho=arr.indexOfIgnoreCase( 'ANCHO' )
	col.alto=arr.indexOfIgnoreCase( 'ALTO' )

	col.caption=arr.indexOfIgnoreCase( 'CAPTION_'+exp.idioma )
	if (col.caption==-1){
		if (arr.indexOfIgnoreCase('caption_es')>-1)
			col.caption=arr.indexOfIgnoreCase('caption_es')
		else {
			for (var i=0; i<arr.length; i++){
				if (arr[i].startsWith('caption_')){
					col.caption=i
					break
					}
				}
			}
		}

	col.cont=arr.indexOfIgnoreCase( 'CONTENEDOR' )
	col.pestanha=arr.indexOfIgnoreCase( 'PESTANHA' )

	col.nomLetra=arr.indexOfIgnoreCase( 'TIPOLETRA' )
	col.tamLetra=arr.indexOfIgnoreCase( 'TAMANHOLETRA' )
	col.estLetra=arr.indexOfIgnoreCase( 'NEG_CUR_SUB_TACH' )

	col.color=arr.indexOfIgnoreCase( 'COLORLETRA' )

	col.borde=arr.indexOfIgnoreCase( 'BORDE' )
	
	col.tipoDato=arr.indexOfIgnoreCase( 'TIPODATO' )
	col.subtipoDato=arr.indexOfIgnoreCase( 'SUBTIPODATO' )
	col.longitud=arr.indexOfIgnoreCase('LONGITUD')
	
	col.claseCSS=arr.indexOfIgnoreCase( 'CLASECSS' )
	
	return col
	}
ModoDetalle.prototype.hayQueAbrirTab=function(numControl, pestActiva){ //en la urlDestino debe venir &control1885=2&control19=0
	if (exp.arbol.urlDestino){
		var temp=exp.arbol.urlDestino.split('&')
		for (var i=0; i<temp.length; i++){
			var cab='control'+numControl+'='
			if ( temp[i].startsWith(cab) ){
				var ret=temp[i].substring( cab.length, temp[i].length )
				
				exp.arbol.urlDestino=null
				return Number(ret)
				}
			}
		}
	return pestActiva
	}
ModoDetalle.prototype.montaPanelYControles=function(){
	if (this.esPanelIzq)
		padre=control('listaPanelesIzq')
	else
		padre=control('panelDer')
		
	var existe=control(this.idx)
	if (existe)
		this.div=existe
	else  
		this.div=creaObjProp('div', {className:'article md', id:this.idx, tabIndex:-1})
	
	if (this.esPanelIzq){
		var alturaBotonera=$('.header.panelSup').outerHeight()
		this.div.style.top=alturaBotonera+'px'
		}
		
	padre.appendChild(this.div)	
	this.ponNombreMD(this.div)
	this.tareasPreCarga()
	this.cargarControles(this.div)
	this.calculaAlturaPanel()
		
	this.repasaControlesErroneos()
	if (this.esPanelIzq) exp.colocaArbol(this)
}
ModoDetalle.prototype.repasaControlesErroneos=function(){
	var contNoExiste, contIncorrecto, contNumPestIncorrecto
	contNoExiste=contIncorrecto=contNumPestIncorrecto=''
	
	for (var i=0; i<this.filas.length; i++) {
		var c=this.filas[i]
		if (!c) 
			continue
		
		var borrar=false
		
		var xcont=c[listaProps.cont]
		if ( xcont != 0 && xcont!='') {
			var ctlcont= this.getControl(xcont)
			this.errores=this.errores || []
			if (ctlcont==null) {//se trata de un huérfano, lo eliminamos 
				contNoExiste+=c[listaProps.numControl]+', '
				borrar=true
				}
			else if (! Control.prototype.controlesContenedores.contains(ctlcont.tc)) {// un no contenedor que contiene controles?
				contIncorrecto+=c[listaProps.numControl]+', '
				borrar=true
				}
			else if (Control.prototype.controlesConPestanhas.contains(ctlcont.tc) && Number(c[listaProps.pestanha])>=ctlcont.pestanhas.length){// se encuentra en la pestña 26 de un contenedor que sólo tiene 2 pestañas
				contNumPestIncorrecto+=c[listaProps.numControl]+', '
				borrar=true
				}
			if (borrar) {
				if (this instanceof ModoDetalle2) {
					var xctl=this.getControl( c[listaProps.numControl])
					if (xctl)
						delete this.listaControles[ xctl.id  ]  
					}
				delete this.filas[i]  //~ delete this.listaControles('control'+ c.id)
				}
			}
		}
		
	if (contNoExiste!='') this.errores.push('Hay controles cuyo contenedor no existe ('+  contNoExiste.substring(0, contNoExiste.length-1) +')')
	if (contIncorrecto!='') this.errores.push('Hay controles cuyo contenedor es de tipo incorrecto ('+contIncorrecto.substring(0, contIncorrecto.length-1)+'). Sólo los tab y rd pueden contener controles')
	if (contNumPestIncorrecto!='') this.errores.push('Hay controles cuyo contenedor o número de pestaña es incorrecto ('+contNumPestIncorrecto.substring(0, contNumPestIncorrecto.length-1)+')')
		
	this.modNombreMD()
	
}
function retornoAutoexec(respuesta, md){
	if (respuesta.tipo=='refresco'){
		if (exp.modoDepuracion)
			depuradorActivo.cancela()
				
		exp.refrescaTodo(respuesta.idml, md.id)
		quitaBloqueo(respuesta.idxBloqueo)
		}
	else if (respuesta.tipo=='refrescodetalle'||respuesta.tipo=='cancelar'){
		if (exp.modoDepuracion)
			depuradorActivo.cancela()		

		// cancelar en autoexec deja rellenar controles
		md.actualiza(true)
		quitaBloqueo(respuesta.idxBloqueo)
		
		//abrimos los nuevos flotantes 
		if (respuesta.flotantes){ 
			for (var i=0;i<respuesta.flotantes.length; i++){ 
				var url=respuesta.flotantes[i] 
				url+='&flotante=1' // por si acaso 
				navegarA(url, null, null, false) 
				} 
			} 
		}
	else {
		retornoTramite(respuesta)
		}
	}
ModoDetalle.prototype.ejecutaAutoexec=function(idMd,md){
	var self=this
	var paramsAdicionales={modoDetalle:idMd, nodoActivo: md.nodoActivo, tablaBase:md.tb, letras:md.letras}
	lanzaTramite(md.botonAutoexec.camino, md.botonAutoexec.tramite, 
		retornoAutoexec,
		false,
		paramsAdicionales,
		md.botonAutoexec
		)
	
}
ModoDetalle.prototype.rellenaControles=function(idMd, sinAutoexec){
	if (idMd==null)
		return
	var md = exp.listaModosDetalle[idMd.toLowerCase()] //||  exp.listaModosLista[idMd.toUpperCase()]
	
	if (md) {
		try {md.vaciaControles()}
		catch (e) {/*pass*/}
		
		if (sinAutoexec==null)
			sinAutoexec=false
		
		if (md.botonAutoexec && !sinAutoexec){
			this.ejecutaAutoexec(idMd,md)
			}
		else  {
			md._rellenaControles(idMd, md.nodoActivo, md.letras, md.tb)
			md._rellenaControlesNuevos(md.nodoActivo)
			md.tareasPostCarga()
			}
		}
}
ModoDetalle.prototype._rellenaControles=function(idMd, url, letras, tb, idDetalleCargar){
	idDetalleCargar=idDetalleCargar || idMd
	var param={ 'accion':'rellenaMD', 'md':idMd, 'aplicacion':exp.aplicacion, 'nodo':this.nodoActivo}
	
	//peticiones mas limpias
	if (letras) param['letras']=letras
	if (tb) param['tb']=tb
	if (url) param['nodo']=url
	
	//si se trata de un nodo pinchado en el árbol, actualizamos el nodo activo del usuario
	if (idMd==exp.idMdActivo)
		param['cambiarNodoActivo']=1
	
	loadJSONDoc ( 'json', param,
			function(req) {
                var respuesta=xeval(req)
                if (respuesta.tipo	=='SesionPerdida'){
                    try{
                        window.location=exp.getURL()
                        }
                    catch (e){
                		alert('La sesión ha finalizado por falta de actividad')
                        window.location.reload()    
                        }
            		return null
		            }
				var md = exp.listaModosDetalle[idDetalleCargar.toLowerCase()]
				if (md){
					md.nodoActivo=url
					md.datos=respuesta
					md.rellenarControlesEST(0, 0)
					md.rellenarControlesDIN(0, 0)
					}
				},
			function()
				{alert('Error rellenando el modo detalle "'+idMd+'"')})
	}
ModoDetalle.prototype._rellenaControlesNuevos=function(url){ //para los controles que creamos al vuelo en tiempo de diseño
	for (var i in this.listaControles) {
		var ctl=this.listaControles[i]
		if (ctl.nuevo && ctl.tc=='txt') 
			ctl.rellena2(url)
		}
}
ModoDetalle.prototype.vaciaControles=function (){
	for (var i in this.listaControles) {
		var ctl=this.listaControles[i]
		
		if (typeof ctl == 'function') {
			//pass
			}
		else if (ctl.esCopiaDeRDFlotante){
			//pass
			}
		else 	{
			ctl.relleno=false
			ctl.vacia()
			}
		}
}
//carga controles SIN rellenar TXT, LVW, etc. Tan sólo los estáticos
ModoDetalle.prototype.cargarControles=function (divContenedor, contenedor, pestActiva){
    var ret=[]
    var relativo=contenedor && contenedor.tc=='pag'
	for (var i=0; i<this.filas.length; i++) {
		var ctl=this.cargar1Control(divContenedor, contenedor, pestActiva, this.filas[i])
		if(ctl)
		    ret.push(ctl)
		}
    return ret
}
ModoDetalle.prototype.cargar1Control=function(divContenedor, contenedor, pestActiva, datosControl){
	var tipoControl=datosControl[listaProps.tc]
	if (!(tipoControl in Control)) 
		return 
	
	var ctl=null
			
	var id='control'+datosControl[listaProps.numControl]
	if (! (id in this.dicControles) ){
		ctl = new Control[tipoControl](datosControl, this)
		
		this.dicControles[id] = ctl 
		}
	else // está repetido, se debe a que damos 1 vuelta para cada contenedor
		ctl=this.dicControles[id]	

	var cargar=false		
	if (ctl.cont==0 && contenedor==null)
		cargar=true //los del modo detalle
	else if (ctl.cont==contenedor && ctl.pestanha==pestActiva ) {
		cargar=true // TAB
		var xcont=this.getControl(contenedor)
		xcont.anhadirControlContenido(ctl)
		}
	else if (ctl.cont==contenedor && pestActiva==null) {
		// rd o exp
		var xcont=this.getControl(contenedor) //rd o exp
		xcont.anhadirControlContenido(ctl)
		
		cargar=(xcont.tc!='rd')
		}
		
	if (cargar) {
		this.listaControles.push(ctl)
		var dom=ctl.toDOM()
		divContenedor.appendChild ( dom )
			
		if (ctl.tc=='cmd' && ctl.texto.toLowerCase()=='autoexec') {
			this.botonAutoexec=ctl
			ponEstilo(dom, 'autoexec')
			}
			
		if (ctl.esContenedor)
			ctl.cargarControles()
		if (ctl.tc=='tab') //parece que ni chrome ni ie son capaces de sacar el getComputedStyle o similares hasta que el control no está en el dom
			ctl.cambiaTamanhoPestanhas()
	    return ctl	
		}
        
	}
ModoDetalle.prototype.ponNombreMD=function(div){
	if (div) {
		var span=creaObjProp('div', {className:'footer nombreMD', 'hijo':creaObjProp('span', {texto:'detalle '+this.id})}) 
		this.nombreMD=span
		this.modNombreMD()
			
		div.appendChild(span)
				
		if (this.esPanelIzq==true) {
			var spanAlternarPanel=creaObjProp('div', {className:'footer nombreMD alternar', hijo:creaObjProp('span'), onclick:function(){disenhador.disenharModoLista()}, title:'[Click aquí para diseñar este panel]'}) 
			div.appendChild(spanAlternarPanel)
			}
		}
	}
ModoDetalle.prototype.modNombreMD=function(){
	if (!disenhador) return

	if (this instanceof ModoDetalle2)
		return 
		
	if (this.errores.length) {
		ponEstilo(this.nombreMD, 'error')
		
		var texto=this.errores.join(vbCrLf)
		this.nombreMD.title= texto
		this.nombreMD.onclick=function(){alert(texto)}
		}
	else {
		quitaEstilo(this.nombreMD, 'error')
		this.nombreMD.title=''
		this.nombreMD.onclick=fnVacia
		}
}
ModoDetalle.prototype.ocultaMenuPersonalBBB=function(){
	var lista=$('div.nav.jquerycssmenu *.menu_'+this.id)
	
	var listaDIV=lista.parents('.jquerycssmenu')
	
	var listaLI=lista.parent()
	listaLI.remove()
	
	listaDIV.each( function(x){
			var barra=$(listaDIV[x])
			jquerycssmenu.buildmenu(barra)
			jquerycssmenu.ponEventosTeclado(barra)
		})
	
}
ModoDetalle.prototype.montaMenuPersonalBBB=function(){
	var md=this.id; var ml=exp.idMlActivo
	
	this.ocultaMenuPersonalBBB() //por si aca	
	if (this.menu!=null) {
		exp.montaMenu(this.menu, 'menu_'+md)
		return 
		}
	
	var self=this
	loadJSONDoc( 	'json',  {'accion':'menuBBB', aplicacion:exp.aplicacion, 'md':md,'ml':ml}, 
				function(req){
						var obj=xeval(req)
						if (obj && obj.menus){
							self.menu=obj.menus
							exp.montaMenu(self.menu, 'menu_'+md)
							} 
					    }
			)
}
ModoDetalle.prototype.rellenarControlesEST=function(contenedor, pestActiva){ 
	if (contenedor!=null){
		if (!isNaN(contenedor))
			contenedor=Number(contenedor)
		
		if (pestActiva==null){
			if (Control.prototype.controlesConPestanhas.contains(this.getControl(contenedor).tc)){
				pestActiva=0
				activaPestanha(this.id, contenedor, 0)
				}
			}
		}
	else {
		contenedor=0
		pestActiva=0
		}

	//los estáticos se cargan SIEMPRE en esta fase, da igual si están en una pestaña activa o no
	///////////////////////////////////////
	for (var i in this.listaControles) {
		var ctl=this.listaControles[i]
		
		if ( !ctl.refresco) 
			continue
		
		ctl.arreglaDatos()
		
		if (ctl.numControl == contenedor)
			continue
		
		////////////////////////////
		try 	{//Estáticos
			var valor=null
			
			if (ctl.esCopiaDeRDFlotante){
				//~ pass
				}			
			else if (!ctl.usaNivel){//txt, par, bsc, bsp 
				valor=this.sacaDato(ctl)
				
				if (ctl.esContenedor)
					ctl.rellena(valor, false) //para ocultar ciertas pestañas, cambiar la pestaña activa...
				else
					ctl.rellena(valor)
				}
			}
		catch (e){
			console.debug('Error rellenando control %s: %s', ctl.numControl, e)
			}
		}
	}
ModoDetalle.prototype.rellenarControlesDIN=function(contenedor, pestActiva){
	if (contenedor!=null){
		if (!isNaN(contenedor))
			contenedor=Number(contenedor)
		
		if (pestActiva==null){
			if (Control.prototype.controlesConPestanhas.contains(this.getControl(contenedor).tc)){
				pestActiva=0
				activaPestanha(this.id, contenedor, 0)
				}
			}
		}
	else {
		contenedor=0
		pestActiva=0
		}
	
	///////////////////////////////////////	
	for (var i in this.listaControles) {
		var ctl=this.listaControles[i]
		
		if ( !ctl.refresco) 
			continue
		
		if (ctl.numControl == contenedor)
			continue
		else if (ctl.cont != contenedor || ctl.pestanha!=pestActiva)
			continue
		
		////////////////////////////
		try {
			valor=this.sacaDato(ctl)

			if (ctl.esCopiaDeRDFlotante){
				//~ pass
				}
			else if (ctl.usaNivel){
				if (ctl.estaVisible()) //www, lvw, rd, arb y gra: sólo si la pestaña está activada
					ctl.rellena(valor)
				}
			else if (ctl.esContenedor){//exp, tab: ojo que el RD también es contenedor y además usa nivel
				ctl.rellena(valor, true)
				}
			}
		catch (e){
			console.debug('Error rellenando control %s: %s', ctl.numControl, e)
			}		
		}
	}
ModoDetalle.prototype.sacaDato=function(ctl){
	var valor=null
	if (this.datos)  {
		if (Control.prototype.controlesConPestanhas.contains(ctl.tc)){
			var d=this.datos.valores[ctl.numControl]
			if (d != null) {// "0 1 2" o "visibles: '0 1 2', pestActiva:'3'"
				var visibles=this.datos.valores[ctl.numControl].visibles
				if ( visibles == '*' )
					d.visibles=ControlTAB.prototype.TODAS_LAS_PESTAÑAS.visibles
				valor=d
				}
			}
		else if (this.datos.valores){
			valor=this.datos.valores[ctl.numControl]
			if (valor==null)
				valor=''
			}
		}
	return valor
	}
ModoDetalle.prototype.getControl=function (id){
	id=this.sacaId(id)
	return this.dicControles[ id ]
}
ModoDetalle.prototype.sacaId=function(id){
	id=id+''
	if (id.startsWith('input_control')) id=id.substring(6)
	id=(id.startsWith('control') ? '' : 'control')+id
	return id
}
ModoDetalle.prototype.listaTXT=function (id){
	return this.getControl(id).listaTXT()
}
ModoDetalle.prototype.listaTXT_DOM=function (id){
	return this.getControl(id).listaTXT_DOM()
}
ModoDetalle.prototype.datoQueSeMuestra=function(){
	return _datoQueSeMuestra(this.datosFRM(), this)}
ModoDetalle.prototype.datosFRM=function(){
	return {tramite:'Propiedades del detalle', tipo: 'frm', titulo: 'Propiedades del detalle "'+this.id+'"', controles:[
			{tipo : 'etiqueta', id: 'sinnombre9782880035098282', ds: 'Origen de los datos'},
			{tipo:'bsc', id:'DIC_Tablas.Nombre', ds:'Tabla',controles:[
					{tipo: 'lvw',id:'nombre', maxlength: -25, valor:this.tb}
					]},
			{tipo:'lvw', id: 'letras', maxlength : -10, valor: this.letras, ds:'Letras', dsExtendida:'* Hay que indicar tantas letras como columnas tenga la clave de la tabla indicada, y éstas deben ir en el mismo orden que las columnas.'}
			]}
}
ModoDetalle.prototype.retornoFRM=function(param){
	this.tb=param['nombre']
	this.letras=param['letras']
	
	this.rellenaControles(this.id)
	
	this.modificado=true
}
ModoDetalle.prototype.maxTope=function(){
	var max=0
	for (var i in this.listaControles) {
		var ctl=this.listaControles[i]
		if (ctl.cont==0)
			max=Math.max( ctl.tope+ ctl.alto, max)
		}
	return max
}
ModoDetalle.prototype.listaControlesPorTipo=function(tipos){
	var retorno=[]
	for (var i in this.listaControles) {
		var ctl = this.listaControles[i]
		if (tipos.contains(ctl.tc)) retorno.push( ctl )
		}
	return retorno
}
ModoDetalle.prototype.cambiaModoDisenho=function(modoDisenho){
	var md=this.div
	
	var panelDer=control('panelDer')
	var panelIzq=control('listaPanelesIzq')
	
	if (modoDisenho) {	
		
		if (this.esPanelIzq) 
			exp.colocaArbol()
		
		panelDer.onmousedown=function(event){try{window.creaRubberBand(event)}catch(e){}}
		panelDer.onmousemove=function(event){try{window.mueveRubberBand(event)}catch(e){}}
		panelDer.onmouseup=function(event){try{window.cancelaRubberBand(event)}catch(e){}}
		panelDer.oncontextmenu=function(event){
			if (exp.modoDisenho){
				disenhador.md.datoQueSeMuestra()
				return false
				}
			}
		}
	else {
		if (this.esPanelIzq)
			exp.colocaArbol()

		this.quitaTiradores()
		
		panelDer.onmousedown=null
		panelDer.onmousemove=null
		panelDer.onmouseup=null
		panelDer.oncontextmenu=null
		}

	var listaRD=this.listaControlesPorTipo(['rd', 'www'])
	for (var i=0; i<listaRD.length; i++){
		var rd=listaRD[i]
		if (rd.estaVisible()) {
			if (rd.tc=='rd') rd.vacia()
			rd.relleno=false
			rd.rellena()
			}
		}
	var listaTAB=this.listaControlesPorTipo(['tab','pag'])
	for (var i=0; i<listaTAB.length; i++){
		var tab=listaTAB[i]
		tab.rellena(ControlTAB.prototype.TODAS_LAS_PESTAÑAS)
		}
}
ModoDetalle.prototype.quitaTiradores=function(){
	for (var i in this.dicControles) {
		var ctl=this.dicControles[ i ]
		
		if (typeof ctl == 'function') continue
		
		if (ctl != null) 
			ctl.quitaTiradores()
		}
}
ModoDetalle.prototype.actualiza=function(sinAutoexec){
	exp.mdActivo().rellenaControles(this.id, sinAutoexec)
}
ModoDetalle.prototype.limpiaRestos=function(){
	//limpiamos cualquier rastro para eliminar y refrescar por completo
	
	//formularios basados en este detalle
	if (exp.listaFormulariosModosDetalle) 
		delete exp.listaFormulariosModosDetalle[this.id]
	
	//restos del menú personalizado
	$('ul > li.'+this.id).remove()
	
	$(this.div).remove()		
}
ModoDetalle.prototype.eliminaControlesContenedor=function(idCont, idPest){
	if (!isNaN(idCont))
		idCont=Number(idCont)
		
	for (var i in this.listaControles) {
		var ctl=this.listaControles[ i ]
		if (typeof ctl == 'function') break
		if (ctl && ctl.cont==idCont && (idPest==null || idPest==ctl.pestanha)) {
			ctl.borrado=true
			ctl.modificado=true
			}
		}
}
ModoDetalle.prototype.anhadeControl=function(ctl){
	this.listaControles[ctl.id]=ctl
	this.dicControles[ctl.id]=ctl
}
ModoDetalle.prototype.calculaAlturaPanel=function(){
	if (this.maxAltura!=null)
		return this.maxAltura
	
	this.maxAltura= this.calculaAlturaListaControles(this.listaControles)
	return this.maxAltura
	}
ModoDetalle.prototype.calculaAlturaListaControles=function(lista){
	if (lista==null)
		return 0
	var maxAltura=0
	
	for (var i=0; i<lista.length; i++){
		var ctl=lista[i]
		var alturaContenido=ctl.alto
		if (ctl.esContenedor){
			if (ctl.titulo) 
				alturaContenido=ctl.titulo.offsetHeight+ctl.alto
			alturaContenido=Math.max(alturaContenido, this.calculaAlturaListaControles(ctl.listaControles) )
			}
		maxAltura=Math.max(maxAltura, ctl.tope+alturaContenido)
		}
	return maxAltura
	}
ModoDetalle.prototype.marcaCampoErroneo=function(ctl){
	ctl.marcaCampoErroneo()
	}
ModoDetalle.prototype.marcaCampoCorrecto=function(ctl){
	ctl.marcaCampoCorrecto()
	}
//////////
ModoDetalle.prototype.tareasPostCarga=function(){
	//para meterle plugins
	// hay que tener en cuenta que aquí, todas las llamadas son asíncronas, así que ojo con las condiciones de carrera
	}
ModoDetalle.prototype.tareasPreCarga=function(){
	//para meterle plugins
	// hay que tener en cuenta que aquí, todas las llamadas son asíncronas, así que ojo con las condiciones de carrera
	
	/* Este es el sitio donde tenemos que meter nuestras implementaciones dinámicas de Control.prototype.toDOM_88
	var listaBloqueos=[ ['70', ['71', '77']] ] // al cambiar 70, lanzamos la carga de 71 y 77
	ModoDetalle.prototype.tareasPreCarga=function(){
		for (var l=0; l<listaBloqueos.length; l++){
			var lista=listaBloqueos[l]
			
			var lanzador=lista[0]
			var temp_recargar=lista[1]
			var recargar=temp_recargar
			if (! (temp_recargar instanceof Array)){
				recargar=[temp_recargar]
				}
			
			Control.prototype['toDOM_'+lanzador]=function(){
				for (var i=0; i<recargar.length; i++){
					var id=recargar[i]
					$(this.dom).change( lanzaCargaRelacionado(id) )
					}
				}
			}
		}
	function lanzaCargaRelacionado(id){
		return function(){cargaRelacionado('control'+id)}
		}
	///////////////////////////		
	function cargaRelacionado(id){
		filtrarYRefrescarControl(id)
		}
	function filtrarYRefrescarControl(id){
		var param=_filtrar()
		if (!param)
			return
		loadJSONDocPost('filtrar', param, function(){
				exp.mdActivo().getControl(id).vacia()
				})
		}
	
	*/
	}
/////////////////////////////////////////////////
function offset(tipo, ctl){
	if (tipo=='l' || tipo=='t'){
		var $ctl=$(ctl)
		var padre=$ctl.parents('#panelDer')
		
		if (tipo=='t')
			return $ctl.offset().top-padre.offset().top+padre[0].scrollTop
		else if (tipo=='l')
			return $ctl.offset().left-padre.offset().left+padre[0].scrollLeft 
		}
	else if (tipo=='w')
		return ctl.offsetWidth
	else if (tipo=='h')
		return ctl.offsetHeight
	}
function Control(c, md){
	if (c===undefined)
		return
	for (var i in listaProps)
		this[i]=c[listaProps[i]]
	this.color=this.color!=null?this.color+'':''
	this.borde=(this.borde!='0')
	
	this.modificado=c.modificado
	this.nuevo=c.nuevo
	this.eliminado=c.eliminado
	
	this.def=c
	this.estLetra=(this.estLetra?this.estLetra:'').toLowerCase()
	if (this.nomLetra)
		this.nomLetra=this.nomLetra.replace('Small Fonts', 'sans-serif')
	
	this.colores=new Array()
	var temp=(this.color).split(/ /)
	
	if (temp.length>2){//se trata de un color en rgb con espacios en blanco
		var pos
		if (this.color.toUpperCase().startsWith('RGB'))
			var pos=this.color.indexOf(')')
		else 
			var pos=this.color.indexOf(' ')	
		temp=[this.color.substring(0,pos+1).trim(), this.color.substring(pos+1).trim()]
		}
	for (var i=0 ; i<temp.length; i++) {
		var tinta=temp[i]
		if (tinta.startsWith('-') ){
			if (this.tc=='cmd' ) {
				if (i==0) this.colores[0]='rgb(226,223,214)' //gris
				if (i==1) this.colores[1]='rgb(0, 0, 0)' //negro
				}
			else if (this.tc=='txt' ) {
				if (i==0)this.colores[0]='rgb(0, 0, 0)' //negro
				if (i==1) this.colores[1]='rgb(255,255,255)' //gris
				}
			else
				if (i==0) this.colores[0]=''
			}
		else
			this.colores[i]=colorTinta( tinta )
		}
	
	if (this.colores[0]=='NaN' || this.colores[0]=='null')
		this.colores[0]=null
	if (this.colores[1]=='NaN' || this.colores[1]=='null')
		this.colores[1]=null
	
	if ( !exp.internetExplorer6 ){
		this.colores[0]=this.colores[0] || 'transparent'
		this.colores[1]=this.colores[1] || 'transparent'
		}
	
	this.colorFondo=this.colores[0]
	if (this.colores.length>1)
		this.colorFrente=this.colores[1]
	else
		this.colorFrente=null
		
	this.tamLetra=this.tamLetra
	
	this.ajusteAncho=0
	this.ajusteAlto=0
	this.ajusteTope=0
	
	this.md=md
	
	this.izq/=escala
	this.tope/=escala
	this.ancho/=escala
	this.alto/=escala

	if (this.claseCSS && this.claseCSS=='-')
		this.claseCSS=null
		
	if (this.cont!=null && isNaN(this.cont))
		this.cont=0
	this.cont=Number(this.cont)
	this.pestanha=(this.pestanha!=null?Number(this.pestanha):0 )
		
	this.id='control'+this.numControl

	this.refresco=this.controlesConRefresco.contains(this.tc) 
	this.usaNivel=this.controlesConNivel.contains(this.tc) 	
	this.esContenedor=this.controlesContenedores.contains(this.tc)
	
	if (this.modificado==null) this.modificado=false
	if (this.borrado ==null) this.borrado=false
	if (this.borrado ==null) this.nuevo=this.nuevo || false
	}
Control.prototype.controlesConRefresco=['subgrid', 'txt', 'par', 'exp', 'lvw', 'arb', 'rd', 'gra', 'www', 'bsc', 'bsp', 'bsm', 'tab', 'pag', 'pic', 'desp'] //los que cambian al cambiar de nodo
Control.prototype.controlesConNivel=['subgrid', 'lvw', 'arb', 'rd', 'gra', 'www']	//considerados pesados de cargar, sólo se cargan cuando están en la pestaña activa de un tab
Control.prototype.controlesConFilas=['lvw', 'rd', 'arb', 'subgrid']
Control.prototype.controlesConFiltro=['par','bsp','bsm','desp'] 	//se envían en cada petición de trámite
Control.prototype.controlesContenedores=['pag','rd','tab','exp'] 	
Control.prototype.controlesConPestanhas=['pag','tab'] 	
Control.prototype.controlesExpansibles=['txt', 'lvw', 'arb', 'rd', 'pag', 'pic', 'subgrid'] //los que pueden expandirse para alojar su contenido

Control.prototype.toDOM=function(){
	if ('toDOM_'+this.numControl in this)
		return this['toDOM_'+this.numControl].call(this)
	/*
	Al final de todo esto se devuelve lo que queda almacenado en this.dom, así que para reemplazar completamente lo devuelto sólo tenemos que rellenar esa propiedad.
	Ejemplo:
	Control.prototype.toDOM_9=function(){
		this.dom= creaObjProp('img', {className:'img', id:this.id, 
						'style.zIndex':0, 
						'style.left':this.izq,
						'style.top':this.tope,
						'style.width':this.ancho,
						'style.height':this.alto,
						src:'http://www.simpsoncrazy.com/apps/gadgets/simpquotes/images/homer.gif'})
		}
	*/
}
Control.prototype.rellena=function(valor){
	if ('rellena_'+this.numControl in this)
		return this['rellena_'+this.numControl].call(this)
	}
Control.prototype.valida=function(){return true}
Control.prototype.creaDIV=function(tipo, ajustar){
	var xancho=this.ancho+'px'
	var xalto=this.alto+'px'
	if (ajustar!=null && ajustar==false) {
		xancho='auto'
		xalto='auto'
		}
	
	if (xalto!=null && xalto<1 ) 
		xalto='1px'
	
	var d=control(this.id)
	var xizq=this.izq+'px', xtope=this.tope+'px'
		
	if (d == null) 
		d=creaObjProp(tipo || 'DIV', {className:'ctlGotta '+this.tc, id:this.id, 'style.left':xizq, 'style.top':xtope, 'style.width':xancho, 'style.height':xalto})
	else
		mueve(d, xizq, xtope, xancho, xalto)
		
	this.ponFuente(d)
	return d
	}
Control.prototype.aplanaControl=function(txt){
	txt.style.backgroundColor=''
	txt.style.borderColor=''
	txt.style.color=''
	
	txt.style.fontWeight=''
	txt.style.fontSize=''
	txt.style.textDecoration=''
	txt.style.fontStyle=''
	
	txt.style.align=''
}
Control.prototype.ponThrobber=function(){
	//~ return creaThrobber('throbber'+this.id)
	ponEstilo(this.dom, 'cargando')
}
Control.prototype.quitaThrobber=function(){ 
	//~ var ctl=control('throbber'+this.id)
	//~ if (ctl)
		//~ ctl.parentNode.removeChild(ctl)
	quitaEstilo(this.dom, 'cargando')
}
Control.prototype.ponFuente=function(nodoDOM) {
	if (this.tamLetra)
		nodoDOM.style.fontSize=this.tamLetra+'pt'
	if (this.nomLetra)
		nodoDOM.style.fontFamily=this.nomLetra
	
	try	{
		nodoDOM.style.textDecoration=''
		nodoDOM.style.fontWeight=''
		nodoDOM.style.fontStyle=''
		
		if (this.estLetra.contains('n')) {nodoDOM.style.fontWeight='bold'}
		if (this.estLetra.contains('c')) {nodoDOM.style.fontStyle='italic'}

		//~ if (this.estLetra.contains('s')) {nodoDOM.style.textDecoration='underline'}
		if (this.estLetra.contains('t')) {nodoDOM.style.textDecoration='line-through'}
		}
	catch (e)
		{/*pass*/}
}
Control.prototype.vacia=function(){
	this.relleno=false
	}
Control.prototype.anhadeTextos=function(lbl, s){
	s+=''
	var temp=separaURL(s)
	for (var j=0; j<temp.length; j++){
		var texto=temp[j]
		
		var trozos=texto.split('\n')
		for (var i=0; i<trozos.length; i++){
			var trozo=trozos[i]
			trozo=ponVinculo(trozo)
			
			if (lbl) lbl.appendChild(trozo)
			
			if (i<trozos.length-1)
				lbl.appendChild(creaObj('BR'))
			}
		}
}
Control.prototype.borraTodo=function(control){
    if (control==null)
        return
	while (control.firstChild)
		{ control.removeChild( control.firstChild ) }
}
Control.prototype.arreglaDatos=function(){
	if (this.cont!=null && isNaN(this.cont))
		this.cont=Number(this.cont)
	this.pestanha=Number(this.pestanha) 
	}
Control.prototype.refresca=function(){
	disenhador.cambiaHayQueGuardar()
	if (this.claseCSS=='-')
		this.claseCSS=null
		
	this.init()
	this.toDOM()
}
Control.prototype.init=function(){
}
function anhadirExtension(nombreImg, extension){
	if (nombreImg==null) return
	var anhadir=true
	var extensionesConocidas=['.png', '.gif', '.jpg', '.bmp']
	var src=nombreImg.toLowerCase()
	for (var i=0; i<extensionesConocidas.length; i++) {
		if (src.endsWith( extensionesConocidas[i] )){
			anhadir=false
			break
			}
		}
	if (anhadir)
		return extension
	else
		return ""
}
Control.prototype.anhadirExtension=function(nombreImg, extension){
	return anhadirExtension(nombreImg, extension)
}
Control.prototype.datoQueSeMuestra=function(){
	return _datoQueSeMuestra(this.datosFRM(), this)}
function _datoQueSeMuestra(datosFRM, ctl){
	if (!exp.modoDisenho)return false
	
	disenhador.idxBloqueo=ponNuevoBloqueo()
	
	if (ctl.tc!=null) //controles
		datosFRM.titulo='Propiedades del '+ctl.tc+' ('+ctl.numControl+')'
			
	var xfrm=new Formulario( datosFRM, disenhador.idxBloqueo )
	exp.mdActivo().controlActivo=ctl
	
	xfrm.onAceptar=function(){
		var param = xfrm.recogeValoresFRM()
		ctl.retornoFRM(param)
		xfrm.cierraFormulario()
		}
	
	xfrm.toDOM()
	return false
}
Control.prototype.estaDentroDeRD=function(){
	return this.estaDentroDe('rd')
}
Control.prototype.estaVisible=function(){
	// Si no está en un contenedor es visible
	if (this.cont==0)
		return true
	var cont=this.md.getControl(this.cont)
	if (!cont)
		return true
	// Si está en una pestaña oculta, no es visible
	if (cont && cont.tc=='tab' && this.pestanha!=cont.pestActiva)
		return false
	// Es visible si su contenedor es visible
	return cont.estaVisible()
}
Control.prototype.estaDentroDe=function(tipo){
	return (this.cont!=0 && this.md.getControl(this.cont).tc==tipo)
}
Control.prototype.fnDatoQueSeMuestra=function(idControl) {
	return function(event){if (exp.modoDisenho) return datoQueSeMuestra(idControl, event)}
}
Control.prototype.JSON=function(){
	var ret=''
	var lista=['claseCSS', 'numControl', 'tc', 'izq', 'tope', 'ancho', 'alto', 'caption', 'cont', 'pestanha', 'nomLetra', 'tamLetra', 'estLetra','borde', 'modificado','nuevo','borrado']
	
	for (var i in lista) {
		var prop=lista[i]
		if (typeof prop == 'function'){
			/*pass*/}
		//~ else if (prop=='caption')
			//~ ret+=pareja(prop, this.caption)
		else if (prop=='alto')
			ret+=pareja(prop, this[prop]-this.ajusteAlto)
		else if (prop=='ancho')
			ret+=pareja(prop, this[prop]-this.ajusteAncho)
		else if (prop=='tope')
			ret+=pareja(prop, this[prop]-this.ajusteTope)
		else if (prop=='numControl'){
			if (isNaN(this[prop]) && !this[prop].startsWith('_nuevo_'))
				this[prop]='_nuevo_'+new Date().getTime()
			ret+=pareja(prop, this[prop])
			}
		else
			ret+=pareja(prop, this[prop])
		}
		
	if (this.tc=='cmd')
		ret+=pareja('colores', this.colorFondo+' '+ this.colorFrente)
	else if (['par', 'bsp', 'bsm', 'lbl', 'txt', 'exp'].contains(this.tc))
		ret+=pareja('colores', this.colorFrente+' '+ this.colorFondo)
	else if (this.tc=='rd')
		ret+=pareja('colores', this.colorFondo)
	else
		ret+=pareja('colores', '')
	
	if (disenhador && disenhador.panelesInvertidos)
		ret+=pareja('ml', this.md.id,false)
	else	
		ret+=pareja('md', this.md.id, false)
	
	return '{'+ret+'}'
}
function pareja(clave, valor, ponerComa){
	var v
	if (valor==null)
		v='null'
	else if (typeof valor == 'number')
		v=valor
	else if (typeof valor == 'boolean')
		v=valor
	else {
		v=replaceTodos(valor, '\n', '<BR>')
		if (v.indexOf(COMILLASIMPLE))
			v=COMILLADOBLE+v+COMILLADOBLE
		else
			v=COMILLASIMPLE+v+COMILLASIMPLE
		}
	return "'"+clave+"':"+v+(ponerComa==null || ponerComa==true? ',' : '')
}
Control.prototype.copiaExacta=function(destino){
	for (var prop in this) 
		destino[prop]=this[prop]
	return destino
}
Control.prototype.onblur=function(){
    //pass
    }

Control.prototype.idOriginal=function() {
	if (this.md instanceof ModoDetalle2) // se trata de un flotante
		return 'control'+this.numControl
	return this.id
}
Control.prototype.propsPorDefecto={ancho:2100, alto:990, izq:300, caption:''}
/////////////////////////////////
Control.prototype.salvaDato=function(propiedad, valor){
	var xid='c'+this.numControl
	salvaDato(xid, propiedad, valor)
}
Control.prototype.recuperaDato=function(propiedad){
	var xid='c'+this.numControl
	return recuperaDato(xid, propiedad)
}
/////////////////////////////
function ControlPAR(c, md){ 
	Control.call(this,c,md) 
	this.colorFondo=this.colores[1]
	this.colorFrente=this.colores[0]
	
	this.div=null
	this.par=null
	this.validacion=null
	
	this.init() 
	}
ControlPAR.prototype= new Control
Control['par']=ControlPAR
var dicTipos={'string':'lvw', 'date':'lvwFecha', 'integer':'lvwNumero', 'currency':'lvwCurrency', 'double':'lvwDouble', 'long':'lvwLong', 'boolean':'lvwCheck'}
ControlPAR.prototype.init = function() {
	var temp=this.caption.split(' ') // @variable String 25 textoEjemplo
	
	if (temp.length==1) {
		this.nombreVar=temp[0]
		this.tipoDato='string'
		this.longitud=null
		}
	else {
		this.nombreVar=temp[1]
		this.tipoDato=temp[0].toLowerCase()
		
		if (temp[2])
			this.longitud=Number(temp[2])
		else
			this.longitud= this.tipoDato=='date'? 10 : null
		
		if (temp[3]){
			delete temp[0]; delete temp[1]; delete temp[2];
			this.textoEjemplo=temp.join(' ')
			}
		}
}
ControlPAR.prototype.toDOM = function() {
	this.dom=this.creaDIV() // div.par#control1981
	
	var idInput='input_'+this.id
	
	var ctlAnterior=control(idInput)
	var textoAnterior= ctlAnterior?ctlAnterior.value : null
	
	borraHijos(this.dom)
	
	this.validacion={'date':new Fecha(), 'string':null, 
				'integer':new Entero(),
				'long':new Numero(),
				'currency':new Numero()}[this.tipoDato]
	var type=this.tipoDato=='boolean'?'checkbox': (exp.internetExplorer?'text':'search')
	this.par=creaObjProp('input',{id:idInput,
						type:type, 
						className:dicTipos[this.tipoDato] || this.tipoDato, 
						maxLength:this.longitud,
						value:textoAnterior
						})	
	
	if (this.textoEjemplo!=null)
		this.par.placeholder=this.textoEjemplo
						
	if (this.tipoDato=='currency'	|| this.tipoDato=='single')
		jwerty.key('.', fnSustituyePuntoPorComa())
	
	jwerty.key('enter', function(){filtrar(); return false}, null, this.par)
						
	var xthis=this
	this.par.onblur=function(){xthis.valida()}
	this.par.oncontextmenu=this.fnDatoQueSeMuestra(this.id)
	
	var izq=1,der=0
	if (this.tipoDato=='date') {
		var cont=creaObjProp('table', {className:'campoCompuesto calendario'})
		
		var td1=creaObjProp('td', {className:'boton'}); var td2=creaObjProp('td', {className:'campos'}); var td3=creaObjProp('td', {className:'ds'})
		cont.appendChild(creaObjProp('tr', {hijos:[td1, td2, td3]}))
		
		var boton=creaObjProp('a', {className:'cmd', tabIndex:-1, href:'#', onclick:fnOpenCalendar(idInput), text:espacioDuro4})
		td1.appendChild(boton)
	
		td2.appendChild( this.par)
		
		this.dom.appendChild(cont)
		izq=18
		der=5
		}
	else
		this.dom.appendChild(this.par)
	
	this.par.onblur=fnValida(this)
		
	if(this.tipoDato!=='boolean'){
		mueve(this.par, izq, 0, quitaPx(this.dom.style.width)-der-izq, quitaPx(this.dom.style.height)-4 )
		}
		
	if (this.claseCSS) {
		this.aplanaControl(this.dom)
		ponEstilo(this.dom, this.claseCSS)
		}
	else {
		if(this.tipoDato!=='boolean')
			this.aplicaEstilo(this.par)
		}
	
	Control.prototype.toDOM.call(this)
	return this.dom
}
ControlPAR.prototype.valida=function(){
	var valor=this.par.value
	if (valor=='' || valor==null) {
		this.marcaCampoCorrecto()
		return true
		}
	
	if (this.validacion==null)
		return true
	
	var esValido=this.validacion.esValido(valor)
	
	if (esValido) {
		if (this.validacion.clase=='Fecha')
			this.par.value=_formatearFecha(this.par.value)
		this.marcaCampoCorrecto()
		}
	else 
		this.marcaCampoErroneo()
	
	return esValido
}
ControlPAR.prototype.marcaCampoErroneo=function(){ponEstilo(this.par, 'errorjsON')}
ControlPAR.prototype.marcaCampoCorrecto=function(ctl){quitaEstilo(this.par, 'errorjsON')}
function fnValida(ctl){
	return function(event){
		ctl.valida()
		}
}
ControlPAR.prototype.aplicaEstilo=function(){
	if (this.claseCSS) {
		this.aplanaControl(this.par)
		}
	else {
		try{
			$(this.dom).find('td.campos').css({backgroundColor:this.colorFondo, color:this.colorFrente})
			this.par.style.backgroundColor=this.colorFondo
			this.par.style.color=this.colorFrente
			this.ponFuente(this.par)
			
			if (this.tipoDato=='lvwFecha')
				$(this.dom).find(table).css('border', '1px solid '+this.colorFondo) //si es una tabla (input:lvwFecha)
			else
				this.par.style.border='1px solid '+this.colorFondo//si es un cuadro de texto //this.par.style.border='1px solid '+this.par.style.backgroundColor
			}
		catch(e){
			//console.log(e)
			}
		}
}
ControlPAR.prototype.formatea=function(valor){
	var modif
	var xtipo=dicTipos[this.tipoDato]||''
	if ( tieneTipo(xtipo, 'lvwCurrency') ) //TODO aquí lo tenemos con tipos nativos y en otros sitios con lo de lvwCurrency...
		modif='m'
	else if (esTipoNumericoConDecimales(xtipo))
		modif=2
	else if (esTipoNumerico(xtipo))
		modif=0
	else
		return valor	
	return formatoSalida(valor, modif)
	}

ControlPAR.prototype.rellena=function(valor){
	if (this.relleno===undefined)
		return
	if (this.relleno)
		return
		
	//~ if (valor==null || valor=='') {
	if (valor==null || (valor instanceof String && valor=='')) {
		this.vacia()
		return
		}
	
	this.relleno=true
	if (this.tipoDato==='boolean')
		this.par.checked=false || valor=='true' || valor==1 
	else {
		this.par.value=this.formatea(valor)
		quitaEstilo(this.par, 'ejemplo')
		}
	
}
ControlPAR.prototype.getValue=function(){
	if(this.tipoDato==='boolean')
		return this.par.checked?1:0
	
	return this.par.value
	}
ControlPAR.prototype.onblur=function(){
    this.par.onblur()
    }
ControlPAR.prototype.vacia=function(){
	if(this.className==='lvwCheck')
		this.par.checked=false
	else
		this.par.value=''
	Control.prototype.vacia.call(this)
}
ControlPAR.prototype.datosFRM=function(){
	var xtipoDato=this.tipoDato.substring(0, 1).toUpperCase()+this.tipoDato.substring(1,20)
	return {numTramites:0, camino:0, tramite:'Propiedades del parámetro', tipo:'frm', controles:[
			{tipo:'lvw', id: 'a', maxlength : -25, valor:this.nombreVar, obligatorio: true, ds:'Variable'},
			{tipo:'bsc', id:'dic_tiposdatos.TipoDato', ds:'Tipo de dato', controles:[
					{tipo: 'lvw', id:'TipoDato', maxlength : -15, valor:xtipoDato, ds:''},
					]},
			{tipo:'lvwNumero', id:'longitud', maxlength:3, valor: this.longitud, ds:'Número máximo de caracteres'},
			{tipo:'lvw', id: 'ejemplo', maxlength:-50, valor:this.textoEjemplo, ds:'Texto de ejemplo'},
			{tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS', controles:[{tipo:'lvw', id:'cd_estilo', maxlength:-15, valor:this.claseCSS}]}

			]
			}
}		
ControlPAR.prototype.retornoFRM=function(param){
	var tipoDato=param['TipoDato']
	if (tipoDato!=null)
		tipoDato+=' '
    else
    	tipoDato=' '
    
	var longitud= param['longitud'] !=null? ' '+param['longitud']:''
	var ejemplo= param['ejemplo'] !=null? ' '+param['ejemplo']:''
	
	this.caption=tipoDato.toLowerCase()+param['a']+longitud+ejemplo
	
	this.modificado=true
	this.claseCSS=param['cd_estilo']
	this.refresca()
}
ControlPAR.prototype.propsPorDefecto={ancho:1500, alto:255, izq:300, caption:'', colorFondo:'#F1F0F0'}
/////////////////////////////
function ControlREC(c, md){
        Control.call(this,c,md)
	
	this.ajusteAlto=-6
	this.ajusteAncho=-1
	this.ajusteTope=+3
	
	if (exp.internetExplorer) {
		this.ancho-=2
		this.alto=this.alto-5
		}
	else {
		this.alto+=this.ajusteAlto
		this.ancho+=this.ajusteAncho
		}
	this.tope=this.tope+this.ajusteTope
        }
ControlREC.prototype= new Control
Control['rec']=ControlREC
ControlREC.prototype.ponFuente=function(nodoDOM) {}

ControlREC.prototype.toDOM = function() {
	var rec=this.creaDIV()
	
	rec.className='ctlGotta '+this.tc
	if (this.claseCSS) {
		this.aplanaControl(rec)
		ponEstilo(rec, this.claseCSS)
		}
	else if (this.alto==0) {
		rec.style.borderWidth='0px'
		rec.style.borderTopWidth='1px'
		}
		
	rec.appendChild( creaObj('span') )
	
	this.dom=rec
	Control.prototype.toDOM.call(this)
	return this.dom
	}
ControlREC.prototype.datosFRM=function(){
	return {numTramites:0, camino:0, tramite:'Propiedades de la etiqueta', tipo:'frm', controles:[
			{tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS',controles:[{tipo: 'lvw',id:'cd_estilo',maxlength : -15, valor:this.claseCSS, ds:''}]}
			]}
}		
ControlREC.prototype.retornoFRM=function(param){
	this.modificado=	this.claseCSS!=param['cd_estilo']
	this.claseCSS=param['cd_estilo']
	this.refresca()
}
/////////////////////////////
function ControlCMD(c, md){
	Control.call(this,c,md)
	this.init()
	this.colorFondo=this.colores[0]
	this.colorFrente=this.colores[1]
	}
ControlCMD.prototype= new Control
Control['cmd']=ControlCMD
ControlCMD.prototype.propsPorDefecto={ancho:1500, alto:360, izq:300, caption:'0\\Registro\\Ejecutar\\./fijo/formulario.ampliarMemo.png\\1'}
ControlCMD.prototype.init=function() {
	var props=(this.caption+sep+sep).split(sep)
	var camino_js=props[0]
	
	this.js=null; this.camino=null; this.tramite=null
	
	if (camino_js=='')
		this.js=props[1]
	else {
		this.camino=props[0]
		this.tramite=props[1]
		}
		
	this.texto=props[2]
	
	this.img=props[3]
	this.mostrarTexto=props[4]?props[4]=='1':false
	}
ControlCMD.prototype.toDOM = function() {
	var cmd=this.creaDIV('button')
	this.borraTodo(cmd)
	cmd.title=this.texto
	
	if (this.claseCSS) {
		this.aplanaControl(cmd)
		ponEstilo(cmd, this.claseCSS)
		}
	else {
		cmd.className='ctlGotta '+this.tc
		cmd.style.backgroundColor=this.colorFondo
		
		if (this.borde) {
			if (!exp.internetExplorer)
				cmd.style.border=''
			}
		else  
			cmd.style.border='none'
		}
	 
	if (this.img){
		var img=cmd.appendChild(document.createElement('img'))
		
		if (this.img.startsWith(exp.icoGotta))
			img.src= this.img+this.anhadirExtension(this.img, '.png')
		else
			img.src= exp.icoWeb+this.img+this.anhadirExtension(this.img, '.png')
		if (!this.mostrarTexto) 
			img.alt=this.texto
		
		if (!this.claseCSS && !this.borde && !exp.internetExplorer) {
			img.style.marginLeft='-3px'
			img.style.marginTop='-3px'
			}
		}
	if (!this.img || this.mostrarTexto)
		cmd.appendChild(creaT(this.texto))
	cmd.title=this.texto
	
	var tramiteVinculado=false//para lanzarlo sin borrar los motores actuales, casi casi como alta dinámica pero no exáctamente igual
	if (this.md instanceof ModoDetalle2)
		tramiteVinculado=true
	
	cmd.onclick=this.js?
				this.fnLanzaJS(): 
				fnLanzaTramite(this.camino, this.tramite, null, null, tramiteVinculado)
					
	cmd.oncontextmenu=this.fnDatoQueSeMuestra(this.id)
	
	if (exp.internetExplorer6)
		cmd.onfocus=function(){exp.colocaArbol()}
	
	this.dom=cmd
	Control.prototype.toDOM.call(this)
	return this.dom
	}
ControlCMD.prototype.fnLanzaJS=function(){
	var xthis=this
	return function(){
		if (exp.modoDisenho) return
		eval(xthis.js+'(xthis)')
		}
	}
function fnLanzaTramite(camino, tramite, rd, deseleccionarAntes, tramiteVinculado){
	if (exp.modoDisenho) return
	if (rd != null){
		return function(event){
			if (deseleccionarAntes)
				desSeleccionarTodos(rd)
			seleccionarFila(rd, (event || window.event).ctrlKey) ; 
			lanzaTramite(camino, tramite);
			
			new Evt(event).consume()
			return false
			}
		}
	else
		return function(){
			lanzaTramite(camino, tramite, null, null, null, null, tramiteVinculado); return false;
			}
}
ControlCMD.prototype.datosFRM=function(){
	return {numTramites: 0,camino: 0,tramite: 'Propiedades del botón', tipo: 'frm', controles:[
			{tipo:'bsc', id:'TRA_Caminos.DS_Camino', ds: 'Camino', controles:[
					{tipo: 'lvwNumero',id:'cd_camino',maxlength: 20,valor: this.camino, ds: ''}
					]},
			{tipo: 'etiqueta',id: 'sinnombre92z82','ds': 'Trámite existente'},		
				{tipo: 'bsc',id:'TRA_TramitesObjetos.DS_Tramite', ds: 'Trámite',colsQueBloquea: 0,colsBloqueadas: 0,controles:[
						{tipo: 'lvw',id:'cd_tramite',maxlength: 20,valor: this.tramite, ds: ''}
						]},
			{tipo: 'etiqueta', id: 'sinnombre9zz282',  ds: 'Trámite nuevo (se incluirá en TRA_TramitesObjetos y en TRA_Acciones)'},
				{tipo: 'lvw', id: 'CD_TramiteNuevo',maxlength: -20,ds: 'Código'},
				{tipo: 'lvw', id:'DS_TramiteNuevo', maxlength:250,ds: 'Descripción'},
			
			{tipo: 'etiqueta', id: 'sinnombre911zz282', ds: 'Javascript'},
				{tipo: 'lvw', id: 'js','maxlength': -100, valor: this.js, ds: 'Código js', dsExtendida:'La función js deberá estar definida en "'+exp.aplicacion+'.js". Ejemplo: miFuncion'},
			
			{tipo: 'etiqueta',id: 'sinnombre9282','maxlength': 0,'valor': '','bloqueado': false,'validacion': '','ds': 'Estética del botón'},
				{tipo: 'lvw',id:'texto',maxlength: 150,valor: this.texto, ds: 'Texto'},
				{tipo: 'lvw',id:'imagen',maxlength: 150,valor: this.img, ds: 'Imagen'},
				{tipo: 'lvwBoolean',id:'borde',maxlength: 150,valor: this.borde, ds: 'Borde'},
				{tipo: 'lvwBoolean',id:'mostrarTexto',maxlength: 150,valor: this.mostrarTexto, ds: 'Mostrar texto en botón', 'dsExtendida':'* Activar para mostrar imagen+texto dentro del botón'}			,
			{tipo: 'bsc',id:'exp_estilos.ds_estilo', ds: 'Clase CSS',colsQueBloquea: 0,colsBloqueadas: 0,controles:[{tipo: 'lvw',id:'cd_estilo',maxlength: -15,valor: this.claseCSS, ds: ''}]}

				
			]}
}
ControlCMD.prototype.guardaTramite=function(cd_tramite, ds_tramite, cd_camino){
	var param={'aplicacion':aplicacion, 'accion':'guardaTramite', 'ncd':cd_tramite, 'nds':ds_tramite, 'camino':cd_camino}
	loadJSONDocPost('json', param, function(req){
		var resp=xeval(req)
		if (resp.tipo=='error'){
			alert(resp.msg)
			}
		})
}
ControlCMD.prototype.retornoFRM=function(param){
	var camSinPuntos= param.cd_camino!=null?param.cd_camino.replace('.', '') : ''
	var cd_tramite=param['cd_tramite']
	if (cd_tramite==null){
		if (param['CD_TramiteNuevo']==null || param['js']==null){
			alert('Faltan datos: hay que indicar un trámite (existente o nuevo) o una función javascript')
			return
			}
		if (param['js'] ){
			cd_tramite=param['js']
			camSinPuntos=''
			}
		else {
			cd_tramite=param['CD_TramiteNuevo']			
			
			var ds_tramite=param['DS_TramiteNuevo']
			this.guardaTramite(cd_tramite, ds_tramite, camSinPuntos)
			}
		}
	
	this.caption=camSinPuntos+sep+cd_tramite+sep+param["texto"]+sep+param["imagen"]+sep+(param['mostrarTexto']?'1':'0')
	this.modificado=true
	this.claseCSS=param['cd_estilo']
	this.borde=param['borde']
	this.refresca()
}
/////////////////////////////
function ControlLBL(c, md){
        Control.call(this,c,md)
	
	this.ajusteAncho=-1
	this.ajusteAlto=-2
	
	if (exp.internetExplorer) {
		this.ancho-=1
		}
	else {
		this.alto+=this.ajusteAlto
		this.ancho+=this.ajusteAncho
		}
		
	
	this.colorFondo=this.colores[1] ||  '#FFFFFF'
	this.colorFrente=this.colores[0]  || '#000000'
	
	this.init()
	}
ControlLBL.prototype= new Control
Control['lbl']=ControlLBL
ControlLBL.prototype.init=function(){
	this.align = 'right'
	
	this.caption=replaceTodos(this.caption, '<BR>', '\n')
	if ( this.caption.startsWith(pipe) || this.caption.startsWith('<') || this.caption.startsWith('>') ) {
		var temp=this.caption.substring(0,1)
		this.align= {'|':'center', '<':'left', '>':'right' }[temp]

		this.texto=this.caption.substring(1)
		}
	else
		this.texto=this.caption
	}
ControlLBL.prototype.toDOM = function() {
	var lbl=this.creaDIV()
	this.borraTodo(lbl)
	
	if (this.claseCSS) {
		this.aplanaControl(lbl)
		ponEstilo(lbl, this.claseCSS)
		}
	else {
		lbl.className='ctlGotta '+this.tc
		try{
			lbl.style.backgroundColor=this.colorFondo
			lbl.style.borderColor=this.colorFondo
			lbl.style.color=this.colorFrente
			}
		catch(e){
			//pass
			}
		
		if (this.colorFondo == null)
			lbl.style.border='0px'
		else 
			lbl.style.border='1px solid '+this.colorFondo
			
		lbl.style.textAlign = this.align
		lbl.style.overflow='hidden' //el puto ie, que no lo coge de la hoja de estilos
		}
			
	if (this.texto==null || this.texto.trim()=='')
		lbl.style.zIndex=0
	else	
		this.anhadeTextos(lbl, this.texto)
		
	lbl.oncontextmenu=this.fnDatoQueSeMuestra(this.id)
	
	this.dom=lbl
	Control.prototype.toDOM.call(this)
	return this.dom
	}
ControlLBL.prototype.datosFRM=function(){
	return {numTramites:0, camino:0, tramite:'Propiedades de la etiqueta', tipo:'frm',controles:[
			//~ {"tipo" : 'lvwConsejo', id: 'xasas121a',  tipoConsejo:'bombilla', 'ds':'¿Podría Wolfram|Alpha suponer una revolución como la que supuso en su día Google? Ciertamente, sabiendo de quien viene y leyendo las explicaciones de su autor sobre su funcionamiento, uno podría pensar que así será, aunque desde luego con este tipo de proyectos es necesario probarlos antes de poder juzgarlos. Wolfram|Alpha llega de la mano de Stephen Wolfram, creador de Mathematica (cualquiera que haya estudiado una ingeniería conoce esta aplicación) y autor del libro A New Kind of Science, un...'},
			{"tipo" : 'lvwMemo', id: 'a',maxlength : -75, valor:this.caption, ds:'Texto', 'dsExtendida':'<Texto para alinear a la izquierda o |Texto para centrar'},
			{tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS',controles:[{tipo: 'lvw',id:'cd_estilo',maxlength : -15, valor:this.claseCSS, ds:''}]}
			]}
}		
ControlLBL.prototype.retornoFRM=function(param){
	this.modificado=true
	this.caption=param['a']
	this.claseCSS=param['cd_estilo']
	this.refresca()
}
ControlLBL.prototype.propsPorDefecto={ancho:1500, alto:225, izq:300, caption:'Texto de muestra'}
////////////////////////////
function ControlTXT(c, md){
	Control.call(this,c,md)
    if (c===undefined)
    	return
	this.ajusteAncho=-1
	this.ajusteAlto=-2
	
	if (exp.internetExplorer) {
		this.ancho-=1
		}
	else {
		this.alto+=this.ajusteAlto
		this.ancho+=this.ajusteAncho
		}
		
	this.colorFondo=this.colores[1] || '#FFFFFF'
	this.colorFrente=this.colores[0] || '#000000'
	
	this.init()
	}
ControlTXT.prototype= new Control
Control['txt']=ControlTXT
ControlTXT.prototype.init=function(){
	var temp=this.caption.split(sep)
	this.campo=temp[0]
	this.modif=null
	if (temp[1])
		this.modif=temp[1].toLowerCase()
	}
ControlTXT.prototype.toDOM = function() {
	var txt=this.creaDIV()
	
	if (this.claseCSS) {
		this.aplanaControl(txt)
		ponEstilo(txt, this.claseCSS)
		}
	else {
		txt.className='ctlGotta '+this.tc
		txt.style.backgroundColor=this.colorFondo
		txt.style.overflow='auto'
		if (this.colorFondo == null)
			txt.style.border='0px'
		else
			txt.style.borderColor=this.colorFondo
			
		txt.style.color= this.colorFrente
		}
	if (this.tipoDato && this.tipoDato!='lvw') {
		var t=this.tipoDato
		if (esTipoNumerico(t) && !tieneTipo(t, 'lvwNumero'))
			t+=' lvwNumero'
		ponEstilo(txt, t)
		}
		
	txt.oncontextmenu=this.fnDatoQueSeMuestra(this.id)
	if (this.alto<25)txt.style.overflow='hidden' //el puto ie, que no lo coge de la hoja de estilos
	
	this.dom=txt
	Control.prototype.toDOM.call(this)
	return this.dom
	}
ControlTXT.prototype.rellena=function(valor, idControl){	
	if (this.relleno)
		return
	
	if (idControl==null) //para que nos sirva también en los rd
		idControl=this.id 
	this.relleno=true
	var txt=control( idControl )
	this.vacia( this.id )

	try {
		valor=this.formatea(valor)
		}
	catch (e) {
		// q no se pare, poddió
		valor=e+' '+valor
		}	
		
	this.anhadeTextos(txt, valor)
}
ControlTXT.prototype.formatea=function(valor){
	var modif=this.modif
	
	if (!modif && this.tipoDato) { //analizamos el tipo de dato 
		if ( tieneTipo(this.tipoDato, 'lvwCurrency') )
			modif='m'
		else if (esTipoNumericoConDecimales(this.tipoDato))
			modif=2
		else if (esTipoNumerico(this.tipoDato))
			modif=0
		else if (tieneTipo(this.tipoDato, 'lvwBoolean'))
			return valor? Frm.prototype.textoSi: Frm.prototype.textoNo
		else if (this.tipoDato=='lvw')
			return valor
		}
		
	try 	{
		return formatoSalida(valor, modif)
		}
	catch (e){
		return valor
		}
	}
ControlTXT.prototype.rellena2=function(url) {//controles nuevos
	var ctl=this
	loadJSONDoc( 'json',  { 'accion':'evalMD', 'exp': this.campo, aplicacion: exp.aplicacion, 'letras':this.md.letras ,'tb':this.md.tb, 'md':this.md.id, 'nodo':url }, 
				function(req){ 
						ctl.relleno=false
						var res=xeval(req)
						if (res && res[0])
							ctl.rellena(res[0])
						}, 
				function(){/*pass*/} 
				)
	}
ControlTXT.prototype.vacia=function( idControl ){
	if (idControl==null) //para que nos sirva también en los rd
		idControl=this.id 
		
	var txt=control(this.id)
	borraHijos(txt)
	Control.prototype.vacia.call(this)
	}
ControlTXT.prototype.datosFRM=function(){
	var xlista={'':'Ninguna', 
			'fr' : 'Fecha.FechaRelativa (ejemplo: Ayer, 12:14)', 
			'fc':'Fecha.FechaCorta (ejemplo: 30/08/2008)', 
			'0':'Número, sin decimales (ejemplo: 10)',
			'1':'Número, 1 decimal (ejemplo: 10.0)',
			'2':'Número, 2 decimales (ejemplo: 10.01)',
			'%':'Porcentaje',
			'm':'Moneda'
			}
	
	if (this.estaDentroDeRD())
		return {numTramites:0, camino:0, tramite:'Propiedades del texto', tipo:'frm', controles:[
				{tipo: 'lvw',id:'nombrecolumna',maxlength : 30, valor:this.campo,obligatorio: true, ds:'Columna (del nivel)'},
				//~ {tipo: 'lvw',id:'modificador',maxlength : 30, valor:this.modif, ds:'Modificador', 'dsExtendida':'En ciertos tipos de datos se permiten modificadores: por ejemplo, para las fechas admitimos fc (fecha corta) o fr (fecha relativa)'},
				{tipo: 'lvwLista', id:'modificador', valor:this.modif,  "ds":'Modificador', 'lista':xlista},
				
				{tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS',controles:[{tipo: 'lvw',id:'cd_estilo',maxlength : -15, valor:this.claseCSS, ds:''}]}
				]}
	else  {
		var campotb=null, campot=null
		
		if (this.campo==this.propsPorDefecto.caption){
			//pass
			}
		else if (!this.campo.contains('.')) //no estamos en la tabla base
			campotb=this.campo
		else 
			campot=this.campo
			
		return {numTramites:0, camino:0, tramite:'Propiedades del texto', tipo:'frm', controles:[
				{"tipo" : 'etiqueta',id:'sinnombre0111',maxlength : 0, valor:'',"bloqueado" : true, ds:'Columna'},
				{tipo:'bsc', id:'dic_columnas.nombrecolumna', ds:'de la tabla base',"colsQueBloquea" : 0,"colsBloqueadas" : 1,controles:[
					{tipo: 'lvw',id:'nombretabla',maxlength : -30, valor:this.md.tb, ds:''},
					{tipo: 'lvw',id:'nombrecolumna',maxlength : -30, valor:campotb, ds:''}
					]},
				{tipo: 'lvw',id:'campocolumna',maxlength : -30, valor:campot, ds:'de cualquier tabla relacionada', 'dsExtendida':'Se permite aquí navegar por toda la jerarquía, partiendo de la tabla base. Ejemplo: CD_DAT_Expedientes.CD_Expediente'},
				
				{"tipo" : 'etiqueta',id:'sinnombre0112',maxlength : 0, valor:'',"bloqueado" : true, ds:'Otra información'},
				{tipo: 'lvwLista',id:'modificador', valor:this.modif, ds:'Modificador', 'lista':xlista},
				
				{tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS',controles:[{tipo: 'lvw',id:'cd_estilo',maxlength : -15, valor:this.claseCSS, ds:''}]}
				]}
				
		}
}	
ControlTXT.prototype.retornoFRM=function(param){
	var tempCaption
	
	if (param.nombrecolumna==null && param.campocolumna==null){
		alert('Hay que indicar obligatoriamente una columna')
		return
		}
		
	if (param.nombrecolumna!=null)
		tempCaption=param.nombrecolumna
	else
		tempCaption=param.campocolumna
		
	if(param.modificador)
		tempCaption+=sep+param.modificador
		
	this.modificado=this.caption!=tempCaption
	if (!this.modificado)
		this.modificado=	this.claseCSS!=param['cd_estilo']
	this.claseCSS=param['cd_estilo']
	
	this.caption=tempCaption
	this.init()
	this.rellena2(this.md.nodoActivo)
}
ControlTXT.prototype.propsPorDefecto={ancho:1500, alto:225, izq:300, caption:'Dato de muestra'}
////////////////////////////
function ControlIMG(c, md){
	Control.call(this,c,md)
	this.init()
	}
ControlIMG.prototype= new Control
ControlIMG.prototype.ponFuente=function(nodoDOM) {}

Control['img']=ControlIMG
ControlIMG.prototype.init=function(){
	if (this.caption==this.propsPorDefecto.caption) {
		this.img=this.caption
		return
		}
	if (this.caption.contains(pipe)) {
		var temp=this.caption.split(pipe)
		this.nombreImg=temp[0]
		
		this.url=temp[1] //para lo típico de que al pinchar en el logo del ministerio te lleva a su página 
		if (this.url=='')
			this.url=null
		
		if (temp.length>=3)
			this.alt=temp[2]
		}
	else  {
		this.nombreImg=this.caption
		this.url=null
		}
	this.img= exp.icoWeb+this.nombreImg+this.anhadirExtension(this.nombreImg, '.png')
	}
ControlIMG.prototype.toDOM = function() {
	var ret=this.creaDIV('a')
	ret.href=this.url || '#'
	if (ret.href=='#') {
		ret.onclick='return false'
		ret.style.cursor='hand'
		}
	else  {
		ret.onclick=null
		ret.style.cursor='default'
		}
	ret.tabIndex=-1
		
	borraHijos(ret)
	var img=creaObjProp('IMG', {tabIndex:-1, src:this.img, alt:this.alt})
	
	ret.appendChild(img)	
	if (this.claseCSS)  {
		this.aplanaControl(ret)
		ponEstilo(ret, this.claseCSS)
		}
	else
		ret.className='ctlGotta '+this.tc
		
	ret.oncontextmenu=this.fnDatoQueSeMuestra(this.id)
	
	this.dom=ret
	Control.prototype.toDOM.call(this)
	return this.dom
	}
ControlIMG.prototype.datosFRM=function(){
	return {numTramites:0, camino:0, tramite:'Propiedades de la imagen', tipo:'frm', controles:[
			{tipo:'lvw', id: 'imagen',maxlength : -50, valor:this.nombreImg,obligatorio: true, ds:'Imagen', 'dsExtendida':'* Si no se indica extensión se intentará cargar un png'},
			{tipo:'lvw', id: 'url',maxlength : -85, valor:this.url, ds:'Enlace'},
			{tipo: 'lvw', id:'alt', valor:this.alt, maxlength : -85, ds:'Descripción', dsExtendida:'Descripción del contenido de la imagen que será ofrecida a personas con discapacidad sensorial'},
			
			{tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS',controles:[{tipo: 'lvw',id:'cd_estilo',maxlength : -15, valor:this.claseCSS, ds:''}]}
			]}
}			
ControlIMG.prototype.retornoFRM=function(param){
	this.caption=param['imagen'] + pipe+param['url']+pipe+param['alt'] 
	this.modificado=true
	this.claseCSS=param['cd_estilo']
	this.refresca()
}
ControlIMG.prototype.propsPorDefecto={ancho:1920, alto:1920, izq:300, caption:'./fijo/disenhador.imgVacia.png'}
////////////////////////////
function ControlPIC(c, md){
        if (c) {
		Control.call(this,c,md) 
		this.init()
		}
	}
ControlPIC.prototype= new Control
ControlPIC.prototype.ponFuente=function(nodoDOM) {}
ControlPIC.prototype.propsPorDefecto={ancho:1920, alto:1920, izq:300, caption:'./fijo/disenhador.imgVacia.png'}
Control['pic']=ControlPIC
ControlPIC.prototype.init=function(){	
	if (this.caption==this.propsPorDefecto.caption) {
		this.campo=null
		this.ajustar=true
		this.borde=false
		}
	else {
		var temp=this.caption.split(sep) //caption = campo/ajustar/borde
		this.campo=temp[0]
		this.ajustar=(temp[1]==1)
		this.borde=(temp[2]==1)
		}
	}
ControlPIC.prototype.toDOM = function() {
	var img=this.creaDIV('IMG') 
	
	if (this.claseCSS) {
		this.aplanaControl(img)
		ponEstilo(img, this.claseCSS)
		}
	else {
		img.className='ctlGotta '+this.tc
		if (this.borde)
			img.style.border='1px silver solid'
		else
			img.style.border='none'
		}
	if (img.src=='')
		img.src='./fijo/blank0.png'
	
	this.dom=img
	Control.prototype.toDOM.call(this)
	return this.dom
	}
ControlPIC.prototype.rellena=function(src, idControl, rutaCompleta){
	if (idControl==null) //para que nos sirva también en los rd
		idControl=this.id 
	var img=control(idControl)
	
	if (!this.ajustar) {
		img.style.width='auto'
		img.style.height='auto'
		}
		
	if (this.caption==this.propsPorDefecto.caption) 
		img.src=this.caption
	else if (src==null || src==''){
		}
	else if (src.startsWith(exp.icoGotta))
		img.src=src+this.anhadirExtension(src, '.png')
	else
		img.src=(rutaCompleta? '' : exp.icoWeb+src)+this.anhadirExtension(src, '.png')
}
ControlPIC.prototype.datosFRM=function(){
	if (this.estaDentroDeRD())
		return {numTramites:0, camino:0, tramite:'Propiedades de la imagen variable', tipo:'frm', controles:[
			{tipo: 'lvw',id:'nombrecolumna',maxlength : -30, valor:this.campo,obligatorio: true, ds:'Columna (del nivel)'},
			{tipo: 'lvwBoolean',id:'ajustar',maxlength : 30, valor:this.ajustar, ds:'Zoom', dsExtendida: 'Zoom en imagen hasta ajustarse al tamaño del control'},
			{tipo: 'lvwBoolean',id:'borde',  maxlength : 30, valor:this.borde,  ds:'Borde'},
			{tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS',controles:[{tipo: 'lvw',id:'cd_estilo',maxlength : -15, valor:this.claseCSS, ds:''}]}
			]}
	else 	{
		var campotb=null, campot=null
		if (this.campo==null || this.campo==this.propsPorDefecto.caption){
			//pass
			}
		else if (!this.campo.contains('.')) //no estamos en la tabla base
			campotb=this.campo
		else 
			campot=this.campo
			
		return {numTramites:0, camino:0, tramite:'Propiedades del texto', tipo:'frm', controles:[
				{"tipo" : 'etiqueta',id:'sinnombre0111',maxlength : 0, valor:'',"bloqueado" : true, ds:'Columna'},
				{tipo:'bsc', id:'dic_columnas.nombrecolumna', ds:'de la tabla base',"colsQueBloquea" : 0,"colsBloqueadas" : 1,controles:[
					{tipo: 'lvw',id:'nombretabla',maxlength : -30, valor:this.md.tb, ds:''},
					{tipo: 'lvw',id:'nombrecolumna',maxlength : -30, valor:campotb, ds:''}
					]},
				{tipo: 'lvw',id:'campocolumna',maxlength : -30, valor:campot, ds:'de cualquier tabla relacionada', 'dsExtendida':'Se permite aquí navegar por toda la jerarquía, partiendo de la tabla base. Ejemplo: CD_DAT_Expedientes.CD_Expediente'},
				
				{"tipo" : 'etiqueta',id:'sinnombre0112',maxlength : 0, valor:'',"bloqueado" : true, ds:'Estética'},

					{tipo: 'lvwBoolean',id:'ajustar',maxlength : 30, valor:this.ajustar, ds:'Zoom en imagen hasta ajustarse al tamaño del control'},
					{tipo: 'lvwBoolean',id:'borde',  maxlength : 30, valor:this.borde,  ds:'Borde'},
				{tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS',controles:[{tipo: 'lvw',id:'cd_estilo',maxlength : -15, valor:this.claseCSS, ds:''}]}

				]}
		}
}	
ControlPIC.prototype.retornoFRM=function(param){
	var tempCaption
	
	if (param.nombrecolumna==null && param.campocolumna==null){
		alert('Hay que indicar obligatoriamente una columna')
		return
		}
		
	if (param.nombrecolumna!=null)
		tempCaption=param.nombrecolumna
	else
		tempCaption=param.campocolumna
		
	this.caption=tempCaption+sep+(param['ajustar']?'1':'0')+sep+(param['borde']?'1':'0')
	this.claseCSS=param['cd_estilo']

	this.refresca()
	this.modificado=true
	this.relleno=false
	this.rellena('./fijo/imagenVacia.png', null, true)
}
////////////////////////////
function ControlWWW(c, md){
        if (c) {
		Control.call(this,c,md) 
		this.init()
		}
	}
ControlWWW.prototype= new Control
ControlWWW.prototype.ponFuente=function(nodoDOM) {}
Control['www']=ControlWWW
ControlWWW.prototype.propsPorDefecto={ancho:3000, alto:3000, izq:300, caption:'nivelVacio'}
ControlWWW.prototype.init=function(){	
	this.relleno=false
	this.nivel=this.caption
	}
ControlWWW.prototype.toDOM = function() {
	var div=this.creaDIV('div', this.ajustar) //creamos el iframe dentro de un div, que será el que manipulemos en diseño
	this.dom=div
	
	if (this.claseCSS) {
		this.aplanaControl(div)
		ponEstilo(div, this.claseCSS)
		}
	else {
		div.className='ctlGotta '+this.tc
		}
		
	var iframe=div.firstChild
	if (iframe==null) {
		iframe=this.creaDIV('IFRAME', this.ajustar)	
		iframe.id='_'+this.id
		iframe.style.top='0px'
		iframe.frameBorder='none'
		iframe.style.left='0px'
		div.appendChild(iframe)
		}
	else {
		iframe.style.width=this.ancho+'px'
		iframe.style.height=this.alto+'px'
		}
	
	this.iframe=iframe
	if (this.borde)
		this.dom.style.border='1px silver solid'
	else
		this.dom.style.border='none'
	
	if (exp.modoDisenho) 
		ocultaWWW(this.iframe, this.dom)
	else  
		muestraWWW(this.iframe, this.dom)
	
	this.vacia()
		
	Control.prototype.toDOM.call(this)
	return this.dom
	}
function ocultaWWW(iframe, div){
	iframe.style.display='none'
	if (div){
		div.style.background='url(disenhador.trama.gif) repeat'
		div.style.opacity='.7'
		}
}
function muestraWWW(iframe, div){
	iframe.style.display='block'
	if (div){
		div.style.backgroundColor=''
		div.style.opacity='1'
		}
}
ControlWWW.prototype.rellena=function(){
	if (this.relleno) return
	if (exp.modoDisenho){
		ocultaWWW(this.iframe, this.dom)
		return
		}
	var www=this
	var fnPostCarga=function(req){
						var respuesta=xeval(req)
						
						if (respuesta.tipo=='error') {
							alert(respuesta.msg)
							www.vacia()
							return
							}
						if (respuesta.datos.filas!=null && respuesta.datos.filas[0]!=null)
							www._rellena(respuesta.datos.filas[0][0])
						else 
							www.vacia()
						}
						
	loadJSONDoc( 'json', { 	'accion':'www', 
					'www': this.numControl, 
					'nivel':this.caption, 
					'aplicacion':exp.aplicacion,
					'md':this.md.nombreDetalle,
					'nodo':this.md.nodoActivo},  
					fnPostCarga)
}
ControlWWW.prototype.vacia=function(){
	this._rellena('blank.html')
	Control.prototype.vacia.call(this)
}
ControlWWW.prototype._rellena=function(valor){
	this.relleno=true
	
	var iframe=this.iframe
	if (iframe.src!=null && iframe.src.toLowerCase()!=valor.toLowerCase() && iframe.src.toLowerCase()!=(valor.toLowerCase()+'/')) //sólo si ha cambiado
		iframe.src=valor
	}
ControlWWW.prototype.datosFRM=function(){
	return {numTramites:0, camino:0, tramite:'Propiedades del control www', tipo:'frm', controles:[
				{tipo:'bsc', id:'exp_niveles.cd_nivel',obligatorio: true, ds:'Nivel','dsExtendida':'Se cargará el control con la URL de la primera fila que se obtenga como resultado.', controles:[
					{tipo: 'lvw',id:'cd_nivel',maxlength : 30, valor:this.caption, ds:''},
					]},
				{tipo: 'lvwBoolean',id:'borde',  maxlength : 30, valor:this.borde,  ds:'Borde'},
				{tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS',controles:[{tipo: 'lvw',id:'cd_estilo',maxlength : -15, valor:this.claseCSS, ds:''}]}

				]}
}	
ControlWWW.prototype.retornoFRM=function(param){
	this.setNivel( param['cd_nivel'] )
	this.borde=param['borde']
	this.claseCSS=param['cd_estilo']
	
	this.modificado=true
	this.relleno=false
	
	this.refresca()
}
ControlWWW.prototype.setNivel=function(idNivel){
	this.caption=idNivel
	this.modificado=true
	}
////////////////////////////
function Pest(texto, imagen, tab){
	this.tab=tab
	this.texto=texto
	this.imagen=imagen
	
	if (imagen=='')
		this.img=null
	else
		this.img=exp.icoWeb+imagen+this.tab.anhadirExtension(imagen, '.png')
	}
Pest.prototype.toDOM  = function(idMd, tr, num) {
	var activa= (this.tab.pestActiva==num)
	
	var id=this.tab.numControl
	var idPest=' pest'+num
	var td1=creaObjProp('td', {className:(activa?'pesInicioOn':'pesInicioOff')+idPest} )
	var td2=creaObjProp('td', {className:(activa?'cambiaPestanha pesFondoOn':'cambiaPestanha pesFondoOff')+idPest, onclick:ControlTAB.prototype.fnActivaPestanha(idMd, id, num)} )
	var td3=creaObjProp('td', {className:(activa? 'pesFinOn' : 'pesFinOff')+idPest} )
	
	var a=creaObjProp('A', {className:'tab',  href:'#', id:id, tabIndex:-1})
	if (this.img!= null)
		a.appendChild(creaObjProp('IMG', {src:this.img, className:'tab'   }))	
	a.appendChild( creaT( ' ' + this.texto ) )
	td2.appendChild(a)
	
	tr.appendChild(td1); tr.appendChild(td2);	tr.appendChild(td3)
		
	this.td1=td1; this.td2=td2; this.td3=td3
	}
Pest.prototype.togglePestanha=function(visible){
	var estado=visible?'':'none'
	
	this.td1.parentNode.style.display=estado
	}
function ControlTAB(c, md){
	Control.call(this,c,md)
    if(c===undefined)
        return
	this.init()
	this.listaControles=[]
	}
ControlTAB.prototype= new Control
ControlTAB.prototype.visibles='' //pestañas visibles
ControlTAB.prototype.ponFuente=function(nodoDOM) {}
Control['tab']=ControlTAB
ControlTAB.prototype.propsPorDefecto={ancho:4500, alto:3000, izq:300, caption:'Pestaña0\\|Pestaña1\\'}
ControlTAB.prototype.init=function(){
	this.pestActiva=this.recuperaDato('p')
	if (this.pestActiva)
		this.pestActiva=Number(this.pestActiva)
	else
		this.pestActiva=0
	
	if (!this.oldCaption || this.oldCaption!=this.caption){
		this.pestanhas=new Array()

		//Resumen\\Resumen|Movimientos\\Licitacion|Desglose\\desglose
		var sp=this.caption.split(pipe)
		for (var i=0; i<sp.length; i++) {
			var trozo=sp[i]
			if (trozo.contains('\\')){
				var temp=trozo.split('\\')
				this.pestanhas.push( new Pest(temp[0], temp[1], this) )
				}
			}
		
		var tempDisposicion=sp[sp.length-1]
		this.disposicion=isNaN(tempDisposicion) ? 0 : Number(tempDisposicion)
		}
}
ControlTAB.prototype.muestraPestanha = function(i, mostrar){
    var pest = this.pestanhas[i]
    pest.togglePestanha(mostrar)
    if(!mostrar)pest.div.style.display = 'none'
    }
ControlTAB.prototype.activaPestanha=function(idPest, noRellenar) {
	noRellenar=noRellenar || false
	if (this.pestActiva==idPest)
		return
	
	this.pestActiva=idPest
	for (var i=0; i<this.pestanhas.length; i++) {
		var activa= (i==this.pestActiva)
		var pest=this.pestanhas[i]
		if (activa)
			ponEstilo(pest.div, 'activa')	
		else
			quitaEstilo(pest.div, 'activa')	
		
		if (activa){
			ponEstilo(pest.td1, 'pesInicioOn')
			ponEstilo(pest.td2, 'pesFondoOn')
			ponEstilo(pest.td3, 'pesFinOn')
			
			quitaEstilo(pest.td1, 'pesInicioOff')
			quitaEstilo(pest.td2, 'pesFondoOff')
			quitaEstilo(pest.td3, 'pesFinOff')
			}
		else {
			ponEstilo(pest.td1, 'pesInicioOff')
			ponEstilo(pest.td2, 'pesFondoOff')
			ponEstilo(pest.td3, 'pesFinOff')
			
			quitaEstilo(pest.td1, 'pesInicioOn')
			quitaEstilo(pest.td2, 'pesFondoOn')
			quitaEstilo(pest.td3, 'pesFinOn')
			}
		}
	if (!noRellenar )
		this.md.rellenarControlesDIN(this.numControl, this.pestActiva)
	this.salvaDato('p', idPest)
	$(this.dom).focus()
}
ControlTAB.prototype.toDOM = function() {
	var ret=this.creaDIV()
	this.dom=ret
	
	this.ponEventosTeclado()
	
	if (this.claseCSS) {
		this.aplanaControl(ret)
		ponEstilo(ret, this.claseCSS)
		}
	if (this.disposicion)
		ponEstilo(ret, 'pestanhasIzquierda')
	
	if (!this.oldCaption || this.oldCaption!=this.caption){
		if (ret.firstChild!=null) //le quitamos la cabecera, que normalmente es lo que cambia
			ret.removeChild(ret.lastChild)
		//cabecera
		var tabla=creaObjProp('table', {className:'tab'})
		var tbody=creaObjProp('tbody')
		var tr=creaObjProp('tr')
		tbody.appendChild(tr)
		for (var i=0; i<this.pestanhas.length; i++) {
			var pest=this.pestanhas[i]
			pest.toDOM(this.md.id, tr, i)
			
			var divPest = control(this.id+'pest'+i)
			if (divPest==null) {
				divPest=creaObjProp('DIV', {className:'pestanha',id:this.id+'pest'+i, 'style.left':0, 'style.top':0} )
				ret.appendChild( divPest )

				pest.div=divPest
				var nombrePest=creaObjProp('DIV', {	className:'cambiaPestanha nombrepestanha', 
											onmouseover:ControlTAB.prototype.fnActivaPestanha(this.md.id, this.numControl, i), 
											hijos:[creaObjProp('span', {	title:'Eliminar la pestaña y todo su contenido', 
																className:'cerrar', 
																onclick:fnBorra(divPest)}),
											creaT(pest.texto)] } )
				divPest.appendChild( nombrePest)
				
				if (i==this.pestActiva) 
					ponEstilo(divPest, 'activa')
				else	
					quitaEstilo(divPest, 'activa')
				}
			else
				pest.div=divPest
				
			if (this.disposicion) {
				tbody.appendChild(tr)	
				tr=creaObjProp('tr')
				}
			}
		
		tabla.appendChild(tbody)
		ret.appendChild(tabla)
			
		this.titulo=tabla
		ret.oncontextmenu=this.fnDatoQueSeMuestra(this.id)
		}
		
	this.cambiaTamanhoPestanhas()
	this.oldCaption=this.caption
	
	if (this.pestanhas.length>1)
		this.dom.tabIndex=0
	this.dom=ret
	Control.prototype.toDOM.call(this)
	return this.dom
	}
ControlTAB.prototype.onkeydown=function(tecla, ctrlKey, altKey, event){
	if (event.target!==event.currentTarget)
		return
	if ([btnIzq, btnDer, btnArriba, btnAbajo].contains(tecla)) {
		var nuevaPest=this.pestActiva
		
		if (tecla==btnDer || tecla==btnAbajo)
			nuevaPest+=1
		else
			nuevaPest-=1
		
		if (nuevaPest==-1)
			nuevaPest=this.pestanhas.length-1
		else if (nuevaPest==this.pestanhas.length)
			nuevaPest=0
		
		this.activaPestanha(nuevaPest)		
		return false
		}
}
ControlTAB.prototype.ponEventosTeclado=function(){
	var self=this
	if (!this.eventosTeclado)
		jwerty.key(teclasDireccion, function(event){return self.onkeydown(event.keyCode, event.ctrlKey, event.altKey, event)}, null, this.dom)
	this.eventosTeclado=true
}
ControlTAB.prototype.fnActivaPestanha=function(idMd, id, num){
	return function(){
		activaPestanha(idMd, id, num);
		return false
		}
	}
ControlTAB.prototype.cambiaTamanhoPestanhas=function(){
	for (var i=0; i<this.pestanhas.length; i++) {
		var pest=this.pestanhas[i]
		this.cambiaTamanhoPestanha(pest.div)
		}
	}
ControlTAB.prototype.cambiaTamanhoPestanha=function(divPest){
	if (divPest){//ya existe: le cambiamos el tamaño
		var marginLeft
		if (exp.internetExplorer) 
			marginLeft=xGetComputedStyle(divPest, 'marginLeft', 1)
		else
			marginLeft=quitaPx(window.getComputedStyle(divPest,null).marginLeft)
		
		divPest.style.width=this.ancho-marginLeft-2+'px'
		divPest.style.height=this.alto-3+'px'
		}
	}
ControlTAB.prototype.maxPestanhas=20
ControlTAB.prototype.TODAS_LAS_PESTAÑAS=function(){
    var rango=[]
    for (var i=0;i<ControlTAB.prototype.maxPestanhas;i++)
    rango.push(i)
    return {visibles:rango.join(' ')}
}()
ControlTAB.prototype.cargarControlesPestanha=function(){
    var ret=[]
	for (var i=0; i<this.pestanhas.length; i++) {
        var retPestaña=this.md.cargarControles(this.pestanhas[i].div, this.numControl, i )
        ret.push(retPestaña)
		forzarRepintado()
		}
    return ret
}
ControlTAB.prototype.cargarControles = function(){
    var ret=[]
    var retPestaña=this.cargarControlesPestanha()
    for (var i=0;i<retPestaña.length;i++){
        ret=ret.concat(retPestaña[i])
    }
    return ret
}

ControlTAB.prototype.rellena=function(valores, dinamicos){
	var tempPestActiva=this.pestActiva
	
	if (valores==null)
		valores={pestActiva:null, visibles:'*'}
	
	if (valores.pestActiva!=null){
		valores.pestActiva=Number(valores.pestActiva)
		if (!isNaN(valores.pestActiva))
			tempPestActiva=valores.pestActiva
		}
		
	//Ocultado de pestañas
	var modoDisenho=exp.modoDisenho
	
	if (valores.visibles=='*'){
		valores.visibles=''
		for (var i=0; i<this.maxPestanhas; i++){
			valores.visibles+=i+' '
			}
		}
		
	var tvisibles=valores.visibles
	if (tvisibles==null)
		tvisibles=''
	this.visibles=tvisibles.split(' ')
	
	if (!this.visibles.contains('' + this.pestActiva)) {
		if (this.visibles[0]!=null) {
			this.activaPestanha(this.visibles[0], true)
			tempPestActiva=null
			}
		else if(this.pestActiva!=null && this.pestActiva!=NaN) {
				this.pestanhas[this.pestActiva].parentNode.div.style.display='none'
				this.pestActiva = null
				}
		}
	for (var p=0;p<this.pestanhas.length;p++)
		this.muestraPestanha(p, modoDisenho || this.visibles.contains(''+p))
		
	tempPestActiva=this.md.hayQueAbrirTab(this.numControl, tempPestActiva)
	if (tempPestActiva != null && tempPestActiva != this.pestActiva) 
		this.activaPestanha(tempPestActiva, true)
	if (this.pestActiva!=null && this.pestActiva!=NaN){
		if (dinamicos)
			this.md.rellenarControlesDIN(this.numControl, this.pestActiva)
		}
}
ControlTAB.prototype.datosFRM=function(){
	var texto=new Array()
	var imagen=new Array()
	
	for (var i=0; i<this.maxPestanhas; i++) {
		texto[i]=''
		imagen[i]=''
		
		if (this.pestanhas[i]!=null) {
			texto[i]=this.pestanhas[i].texto
			imagen[i]=this.pestanhas[i].imagen
			}
		}
		
	var datos= {numTramites:0, camino:0, tramite:'Propiedades de la pestaña', tipo:'frm', alto:null, controles:[]}
	
	for (var i=0; i<this.maxPestanhas; i++) {
		datos.controles[i*3+0]={"tipo" : 'etiqueta',id:'sinnombre'+i,maxlength:0, valor:'', bloqueado:true, ds:'Pestaña '+i}
		datos.controles[i*3+1]={tipo:'lvw', id: 'texto'+i,maxlength : 50,valor:texto[i],obligatorio: i==0, ds:'Texto'}
		datos.controles[i*3+2]={tipo:'lvw', id: 'imagen'+i,maxlength : 50,valor:imagen[i], ds:'Imagen', dsExtendida:'Si no se indica extensión, se entiende que es ".png"'}
		}
	
	var xlista={'arriba' : 'lista de pestañas arriba (clásica)', 
			'izquierda' : 'lista de pestañas a la izquierda'}
	
	datos.controles.push({"tipo" : 'etiqueta',id:'sinnombrexxxx98',maxlength : 0, valor:'',"bloqueado" : true, ds:'Estética'})
	datos.controles.push({tipo: 'lvwLista', id:'disposicion', valor:this.disposicion==0?'arriba':'izquierda',  "ds":'Disposición', 'lista':xlista})
	datos.controles.push({tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS',controles:[{tipo: 'lvw',id:'cd_estilo',maxlength : -15, valor:this.claseCSS, ds:''}]})
	
	return datos
}
ControlTAB.prototype.retornoFRM=function(param){	
	var buf=''
	for (var i=0; i<this.maxPestanhas; i++) {
		var texto=param['texto'+i]
		var imagen=param['imagen'+i]
		if (texto=='')break
		buf+=texto+'\\'+imagen+pipe
		}
	buf+=param['disposicion']=='arriba'?'0':'1'
		
	if (i<this.pestanhas.length) {
		var tab=control(this.id)
		var totalControles=0
		for (var j=i; j<this.pestanhas.length; j++) 
			totalControles+=(tab.childNodes[j].childNodes.length-2) //no cuentan ni el palote ni el nombrepestanha que ponemos para la versión imprimible de la página
		
		if (totalControles>0) {
			var advertencia=confirm("Se van a eliminar "+totalControles+" controles que están contenidos en alguna de las pestañas a eliminar. ¿Desea continuar?")		
			if (!advertencia) {
				xfrm.cierraFormulario()
				return
				}
			else { //ahora deberíamos eliminar los controles
				for (var j=i; j<this.pestanhas.length; j++) 
					this.md.eliminaControlesContenedor(this.numControl, j)
				}
			}
		}
	var captionFinal=buf
	this.modificado= this.caption != captionFinal
	if (!this.modificado)
		this.modificado=	this.claseCSS!=param['cd_estilo']
	
	this.claseCSS=param['cd_estilo']
	
	this.caption= captionFinal
	this.refresca()
	this.refrescaTiradores()
}
ControlTAB.prototype.getPanelContenedor=function(){
	return this.pestanhas[this.pestActiva].div
}
ControlTAB.prototype.quitarControlContenido=function(ctl){
	//~ var idx=this.listaControles.indexOf(ctl)
	//~ delete this.listaControles[idx]
}
ControlTAB.prototype.anhadirControlContenido=function(nuevo){
	this.listaControles.push(nuevo)
}
///////////////////////////
function ControlPAG(c, md){
	ControlTAB.call(this,c,md)
	this.pestActiva=0
	this.numPestanhas=this.pestanhas.length
	}
ControlPAG.prototype= new ControlTAB
Control['pag']=ControlPAG
ControlPAG.prototype.propsPorDefecto={ancho:4500, alto:3000, izq:300, caption:'#1\\|#2\\|#3'}
ControlPAG.prototype.muestraPestanha = function(i, mostrar){
    var divPest = this.pestanhas[i].div
    divPest.style.display = mostrar?'':'none'
    }
ControlPAG.prototype.rellena=function(valores, dinamicos){
	//Ocultado de pestañas
	var modoDisenho=exp.modoDisenho
	if (valores.visibles=='*'){
		valores.visibles=''
		for (var i=0; i<this.maxPestanhas; i++){
			valores.visibles+=i+' '
			}
		}
	this.visibles=(valores.visibles || '').split(' ')
	for (var p = 0; p < this.pestanhas.length; p++) {
		var mostrar=this.visibles.contains('' + p)
		this.muestraPestanha(p, modoDisenho || mostrar)
		if (mostrar){
			if (dinamicos)
				this.md.rellenarControlesDIN(this.numControl, p)
			}
		}
	}
function cargarControlesRelativo(divPest, x){
	var contenedorLista=creaObj('DIV','contenedorLista')
	divPest.appendChild(contenedorLista)
	var alto=0
	var desfaseY=0
	var desfaseX=0
	
	var corte=null
	var yaHayExpansible=false
	for (var j=0;j<x.length;j++){
		var ctl=x[j]
		var ctlDOM=divPest.childNodes[1]
		if (corte && ctl.tope>corte){
			contenedorLista.style.minHeight=alto+'px'
			contenedorLista=creaObj('DIV','contenedorLista')
			divPest.appendChild(contenedorLista)
			desfaseY=corte
			corte=null
			alto=ctl.tope+ctl.alto-desfaseY
			yaHayExpansible=false
			desfaseX=0
			}
		if (Control.prototype.controlesExpansibles.contains(ctl.tc)){
			ponEstilo(ctlDOM,'expansible')
			if (!yaHayExpansible) {
				var desplazaY = creaObj('DIV')
				desplazaY.style.height = ctl.tope - desfaseY + 'px'
				desplazaY.style.width = '100%'
				contenedorLista.appendChild(desplazaY)
				yaHayExpansible = true
				}
			else {
				ctlDOM.style.top=0
				ctlDOM.style.left=ctl.izq-desfaseX+'px'    
				}
			contenedorLista.appendChild(ctlDOM)
			if(corte<ctl.tope + ctl.alto)
				corte=ctl.tope + ctl.alto
			desfaseX=ctl.ancho
			}
		else{
			ctlDOM.style.top=ctl.tope-desfaseY+'px'
			contenedorLista.appendChild(ctlDOM)
    		if (alto<(ctl.alto+ctl.tope-desfaseY))
    			alto=ctl.alto+ctl.tope-desfaseY
			}
		}
	contenedorLista.style.minHeight=alto+'px'

}
ControlPAG.prototype.cargarControles = function(){
	var ret=[]
	var retPestaña=this.cargarControlesPestanha()
	for (var i=0;i<retPestaña.length;i++){
		var divPest = this.pestanhas[i].div
        var x=retPestaña[i]
        cargarControlesRelativo(divPest, x)
		ret=ret.concat(x)
		}
	return ret
	}
ControlPAG.prototype.cambiaTamanhoPestanha=function(divPest){
	if (divPest){//ya existe: le cambiamos el tamaño
		var marginLeft
		if (exp.internetExplorer) 
			marginLeft=xGetComputedStyle(divPest, 'marginLeft', 1)
		else
			marginLeft=quitaPx(window.getComputedStyle(divPest,null).marginLeft)
		
		divPest.style.width=this.ancho-marginLeft-2+'px'
		//~ divPest.style.height=this.alto-3+'px'
		}
	}
ControlPAG.prototype.activaPestanha=function(idPest) {
	if (this.pestActiva==idPest)
		return
	var pestActivaAnterior=this.pestActiva
	
	this.pestActiva=idPest
	for (var i=0; i<this.pestanhas.length; i++) {
		var activa= (i==this.pestActiva)
		var pest=this.pestanhas[i]
		
		if (activa)
			ponEstilo(pest.div, 'activa')	
		else
			quitaEstilo(pest.div, 'activa')	
		}
}
ControlPAG.prototype.datosFRM=function(){
	return {numTramites:0, camino:0, tramite:'Propiedades del pag', tipo:'frm', controles:[
			{tipo:'lvw', id: 'numPestanhas', maxlength : 5,  valor:this.numPestanhas, obligatorio: true, ds:'Número de secciones'},
			{tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS', controles:[{tipo: 'lvw',id:'cd_estilo',maxlength : -15, valor:this.claseCSS, ds:''}]}
			]}
}
ControlPAG.prototype.retornoFRM=function(param){
	var x=Number(param.numPestanhas)
	var caption=''
	for (var i=0; i<x; i++){
		caption+='#'+(i+1)+'\\|'
		}
	this.caption=caption
	this.claseCSS=param.cd_estilo
	
	this.modificado=true
	this.refresca()
}
////////////////////////////
function ControlLVW(c, md){ 
	if (c) {
		Control.call(this,c,md)
		this.init()
		
		if (this.tc=='lvw')
			this.xcontrol=new LVW(this.numControl, this.caption)
		}
}
ControlLVW.prototype= new Control
ControlLVW.prototype.ponFuente=function(nodoDOM) {}
ControlLVW.prototype.propsPorDefecto={ancho:4500, alto:3000, izq:300, caption:'nivelVacio'}
Control['lvw']=ControlLVW
ControlLVW.prototype.init = function() {
	var temp=this.caption.split(pipe)
	this.nivel=temp[0]
	
	if (temp.length>1)this.mostrarTotales=temp[1]=='1'
	if (temp.length>2)this.mostrarNumFila=temp[2]=='1'
	if (temp.length>3)this.mostrarPaginacion=temp[3]=='1'
	if (temp.length>4)this.summary=temp[4]
}
ControlLVW.prototype.toDOM = function() {
	var lvw=this.creaDIV()
	
	if (this.claseCSS) {
		this.aplanaControl(lvw)
		ponEstilo(lvw, this.claseCSS)
		}
	else
		lvw.className='ctlGotta '+this.tc
	this.dom=lvw
	
	lvw.tabIndex=0
	lvw.oncontextmenu=this.fnDatoQueSeMuestra(this.id)
	
	Control.prototype.toDOM.call(this)
	return this.dom
	}
ControlLVW.prototype.rellena=function(key){
	// LVW DENTRO DE UN RD: key es la clave de la fila en la que nos encontramos (vacío cuando es una lista fuera de un rd)
	if (this.relleno)
		return
	
	this.ponThrobber()	
	this.relleno=true
	
	if (this.tc=='lvw') {
		//la instancia de LVW que utilizamos es todo el rato la misma. La siguiente línea es la que produce la carga en un sitio u otro
		this.xcontrol.xcontrol=this
		this.xcontrol.idControlDestino=this.id
		
		this.xcontrol.mostrarTotales=this.mostrarTotales
		this.xcontrol.mostrarNumFila=this.mostrarNumFila
		this.xcontrol.mostrarPaginacion=this.mostrarPaginacion
		this.xcontrol.sortable=!this.mostrarPaginacion
		this.xcontrol.summary=this.summary
		
		this.xcontrol.nivel=this.nivel
		this.xcontrol.md=this.md //le pasamos una referencia a su modo detalle
		
		this.xcontrol.keyContenedor=key
		this.xcontrol.cargar(0)
		}
	else if (this.tc=='arb') {
		//~ this.xcontrol.idControlDestino=idContenedor
		this.xcontrol.xcontrol=this
		
		this.xcontrol.nivel=this.nivel
		this.xcontrol.md=this.md
		
		this.xcontrol.keyContenedor=key
		this.xcontrol.cargar(0)
		}
}
ControlLVW.prototype.vacia=function(){
	var lvw=this.dom
	this.borraTodo(lvw)
	Control.prototype.vacia.call(this)
	this.ponThrobber()
}
ControlLVW.prototype.datosFRM=function(){
	var ret={numTramites:0, camino:0, tramite:'Propiedades de la lista', tipo:'frm', controles:[
			{tipo:'bsc', id:'exp_niveles.cd_nivel', obligatorio: true, ds:'Nivel',controles:[
					{tipo: 'lvw',id:'cd_nivel', maxlength : -30, valor:this.nivel, ds:''}]}
			
			]}
	if (this.tc=='lvw') {
		ret.controles.push({tipo: 'lvw', id:'summary', maxlength : -85, valor:this.summary, ds:'Descripción', dsExtendida:'Descripción del contenido de la lista que será ofrecida a personas con discapacidad sensorial'})
		ret.controles.push({tipo: 'lvwBoolean', id:'totales', 	valor:this.mostrarTotales, ds:'Mostrar fila de totales'})
		ret.controles.push({tipo: 'lvwBoolean', id:'numfila', 	valor:this.mostrarNumFila, ds:'Mostrar número de fila'})
		ret.controles.push({tipo: 'lvwBoolean', id:'paginacion', valor:this.mostrarPaginacion, ds:'Mostrar controles de paginación'})
		}
	ret.controles.push({tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS',controles:[{tipo: 'lvw',id:'cd_estilo',maxlength : -15, valor:this.claseCSS, ds:''}]})
	return ret
}
ControlLVW.prototype.retornoFRM=function(param){
	this.setNivel(param['cd_nivel'])
	var tempCaption=this.nivel+
			pipe+(param['totales']?'1':'0')+
			pipe+(param['numfila']?'1':'0')+
			pipe+(param['paginacion']?'1':'0')+
			pipe+param['summary']
	
	this.modificado = this.caption!=tempCaption
	if (!this.modificado)
		this.modificado=	this.claseCSS!=param['cd_estilo']
	this.claseCSS=param['cd_estilo']
	
	this.caption=tempCaption
	this.refresca()
	this.relleno=false
	this.rellena()
}
ControlLVW.prototype.setNivel=function(idNivel){
	this.nivel=idNivel
	
	this.caption=	this.nivel+pipe+
				this.mostrarTotales+pipe+
				this.mostrarNumFila+pipe+
				this.mostrarPaginacion
	this.modificado=true
	}
ControlLVW.prototype.montaObjColumnas=function(arr){
	var cols={
		css:this.monta1ObjColumnas(arr, '_css_'),
		activar:this.monta1ObjColumnas(arr, '_activar'),
		md:this.monta1ObjColumnas(arr, 'mododetalle'),
		mds:this.monta1ObjColumnas(arr, 'mododetallesiguiente'),
		ico:this.monta1ObjColumnas(arr, 'icono'),
		}
		
	for (var i=0; i<arr.length; i++){
		cols[ arr[i] ]=i
		}
	return cols
	}
ControlLVW.prototype.monta1ObjColumnas=function(arr, clave){
	var ret=arr.indexOfIgnoreCase(clave)
	if (ret==-1)
		ret=null
	return ret
	}
////////////////////////////
function ControlSUBGRID(c, md){
	ControlLVW.call(this,c,md)
	this.xcontrol=new SubGrid(this.numControl, this.caption)
	this.xcontrol.sortable=false
	this.xcontrol.xcontrol=this
	this.subgrids={}
	}
ControlSUBGRID.prototype= new ControlLVW
Control['subgrid']=ControlSUBGRID
ControlSUBGRID.prototype.vacia=function(){
	this.subgrids={}
	this.relleno=false
	}
ControlSUBGRID.prototype.rellena=function(){
	if (this.relleno)
		return
	this.relleno=true
	var self=this
	var fnCargar=function(req){ self._rellena( xeval(req) ) }

	var param={'accion':'subgrid', 'subgrid':this.numControl, 'nivel': this.nivel, 'cuantos': -1, 'aplicacion': exp.aplicacion}
	if (this.md){
		this.xcontrol.md=this.md //le pasamos una referencia a su modo detalle 
		param['md']=this.md.nombreDetalle
		param['nodo']=this.md.nodoActivo
		}
	loadJSONDoc( 'json', param, fnCargar )
	}
ControlSUBGRID.prototype._rellena=function(xdatos, tbody, rowIndex){
	var datos=xdatos.datos
	this.xcontrol.rellenaLVW(datos.filas, datos.columnas, datos.tipos, null, null, null, xdatos, true)
	}
ControlSUBGRID.prototype.expandir=function(xfila, xcolumnasOcultas, xtr) {
	var self=this
	var fila=xfila
	var columnasOcultas=xcolumnasOcultas
	var tr=xtr
	return function(event){
		new Evt(event).consume()
		
		var tbody=tr.parentNode
		if (tieneEstilo(tbody, 'menos')){
			quitaEstilo(tbody, 'menos')
			ponEstilo(tbody, 'mas')
			quitaEstilo(tbody, 'cargando')
			}
		else {
			ponEstilo(tbody, 'cargando')
			quitaEstilo(tbody, 'mas')
			ponEstilo(tbody, 'menos')
			}
			
		var nivel, md, cd
		nivel=fila[ columnasOcultas.mododetallesiguiente ]
		md=fila[ columnasOcultas.mododetalle ]
		cd=fila[ columnasOcultas.cd ]
		
		if ('x'+cd in self.subgrids){
			var param={accion:'expandir', expandir:false, nodo:cd, aplicacion: exp.aplicacion, 'md':self.md?self.md.nombreDetalle:null}
			loadJSONDoc( 'json', param, fnCargar )
			quitaEstilo(tbody, 'cargando')
			return
			}
			
		var td=tr.nextSibling.lastChild
		var xdiv=creaObjProp('div', {className:'rowExpander'})
		td.appendChild( xdiv )
		
		var xid=self.numControl+'_'+cd
		var subRejilla=new SubGrid(xid, nivel) 
		subRejilla.idControlDestino=xid
		subRejilla.xcontrol=self 
		subRejilla.cont=xdiv
		subRejilla.md=self.md
		subRejilla.keyContenedor=cd
		self.subgrids['x'+cd]=subRejilla
		
		var fnCargar=function(req){ 
			var xdatos=xeval(req)
			var datos=xdatos.datos
			subRejilla.rellenaLVW(datos.filas, datos.columnas, datos.tipos, null, null, null, xdatos, true)
			quitaEstilo(tbody, 'cargando')
			}
		
		var param={	accion:'expandir', 
					expandir:true, 
					nodo:cd, 
					cuantos: 0, 
					aplicacion: exp.aplicacion, 
					md:self.md?self.md.nombreDetalle:null}
		loadJSONDoc( 'json', param, fnCargar )
		
		return false
		}
	}
ControlSUBGRID.prototype.usarMaxElementos=function(cd, numActual, numDeseado){
	var subRejilla=this.subgrids['x'+cd]
	var fnCargar=function(req){ 
		var xdatos=xeval(req)
		var datos=xdatos.datos
		subRejilla.rellenaLVW(datos.filas, datos.columnas, datos.tipos, null, null, null, xdatos, true, numActual)
		}
	var param={	accion:'expandir', 
					expandir:true, 
					nodo:cd, 
					cuantos: numDeseado, 
					aplicacion: exp.aplicacion, 
					md:self.md?self.md.nombreDetalle:null}
	loadJSONDoc( 'json', param, fnCargar )
	}
ControlSUBGRID.prototype.toDOM=function(){
	var lvw=this.creaDIV()
	
	if (this.claseCSS) {
		this.aplanaControl(lvw)
		ponEstilo(lvw, this.claseCSS)
		}
	else
		lvw.className='ctlGotta '+this.tc

	this.xcontrol.contenedor=this.id
	lvw.tabIndex=0
	lvw.oncontextmenu=this.fnDatoQueSeMuestra(this.id)

	this.dom=lvw
	Control.prototype.toDOM.call(this)
	return this.dom
	}
///////////////////////////
function ControlRD(c, md){ 
	Control.call(this,c,md) 
	this.colorFondo=this.colores[0]
	this.init()
	this.listaControles=[]
}
ControlRD.prototype= new ControlLVW
Control['rd']=ControlRD
ControlRD.prototype.init = function() {
	var temp=this.caption.split(pipe)
	this.nivel=temp[0]
}
ControlRD.prototype.toDOM = function() {
	var rd=this.creaDIV()
	
	if (this.claseCSS) {
		this.aplanaControl(rd)
		ponEstilo(rd, this.claseCSS, true)
		}
	else {
		rd.style.backgroundColor= this.colorFondo
		rd.className='ctlGotta '+this.tc
		}
	rd.tabIndex=0
	rd.oncontextmenu=this.fnDatoQueSeMuestra(this.id)
	
	this.dom=rd
	this.ponEventosTeclado()
	this.ponThrobber()
	Control.prototype.toDOM.call(this)
	return this.dom
	}
ControlRD.prototype.procesaCacheFilas=function(temp){
	var ret=[]
	for (var i=0;i<temp.length;i++){
		var fila=temp[i]
		if (tieneEstilo(fila, 'filaRD'))
			ret.push(fila)
		}
	return ret
}
ControlRD.prototype.onkeydown=function(tecla, ctrlKey, altKey){
	if ([btnAbajo, btnArriba, btnIzq, btnDer].contains(tecla)) {
		this.cacheFilas=this.cacheFilas || this.procesaCacheFilas(this.dom.getElementsByTagName('DIV'))
	
		var iActivo=null
		for (var i=this.cacheFilas.length-1; i>=0; i--){
			var fila=this.cacheFilas[i]
			if (fila && tieneEstilo(fila,'seleccionada') ) {
				iActivo=i
				break
				}
			}
		/////////////////////
		if (tecla==btnAbajo || tecla==btnDer) {
			if (iActivo==null) iActivo=-1
			
			for (var i=iActivo+1; i<this.cacheFilas.length; i++){
				var tr=this.cacheFilas[i]
				if (tr){
					seleccionarFila(tr, ctrlKey, null, null)
					this.hazScroll(tr)
					break
					}
				}
			}
		else if (tecla==btnArriba || tecla==btnIzq){
			if (iActivo==null) iActivo=this.cacheFilas.length
			
			for (var i=iActivo-1; i>=0; i--){
				var tr=this.cacheFilas[i]
				if (tr){
					seleccionarFila(tr, ctrlKey, null, null)
					this.hazScroll(tr)
					break
					}
				}
			}
		return false
		}
}
ControlRD.prototype.ponEventosTeclado=function(){
	var self=this
	if (!this.eventosTeclado)
		jwerty.key(teclasDireccion, function(event){return self.onkeydown(event.keyCode, event.ctrlKey, event.altKey)}, null, this.dom)
	this.eventosTeclado=true
}
ControlRD.prototype.hazScroll=function(tr){
	tr.scrollIntoView(false)
}
ControlRD.prototype.rellena=function(){
	if (this.relleno) return
	this.relleno=true
	if (exp.modoDisenho)
		this.rellenaParaDisenhar()
	else
		loadJSONDoc( 'json', { 'accion':'rd', 'rd': this.numControl, 
						'nivel':this.nivel, 
						'aplicacion':exp.aplicacion,
						'md':this.md.nombreDetalle,
						'nodo':this.md.nodoActivo},  this.fnMontaRD(this.md, this.numControl)	 ) 
}
ControlRD.prototype.fnMontaRD=function (md, numControl){
	return function(req){
			var datos=xeval(req)
			if(datos){
				var rd=md.getControl(numControl)
				rd.rellenaTabla(datos.datos)
				this.cacheFilas=null
				}
			}
}
ControlRD.prototype.rellenaTabla=function(datos, paraDisenhar){
	var desfaseX=0
	var desfaseY=0
	
	//~ if (this.relleno)
		//~ return
	var primeraFilaSeleccionada=null
	paraDisenhar = paraDisenhar || false
	
	var cols=this.montaObjColumnas(datos.columnas)
	
	for (var fila=0; fila<datos.filas.length; fila++){
		var datosfila=datos.filas[fila]
		
		var ctlFila2=creaObjProp('DIV', {className:'xfilaRD'})
		var ctlFila=creaObjProp('DIV', {	className:'filaRD fila', 
								id:'tr_'+this.numControl+'_'+fila,
								onclick:function(event) {seleccionarFila(this, (event || window.event).ctrlKey)}
								})
		
		if (cols.css!=null)
			ponEstilo(ctlFila2, datosfila[ cols.css ])
		if (cols.activar!=null){
			var hayQueActivar=datosfila[cols.activar]
			if (hayQueActivar!='0') ponEstilo(ctlFila, 'seleccionada')
			}
			
		var key=datosfila[cols.md ]+datosfila[cols.key] 
		ctlFila.appendChild( creaCheck( 'checkbox_tr_'+this.numControl+'_'+fila, key) )
		
		ctlFila2.appendChild( ctlFila )
		this.dom.appendChild( ctlFila2 )	
		
		for (var i=0; i<this.listaControles.length ; i++){
			var def=this.listaControles[i]
			//var defAnt=i>0?this.listaControles[i-1]:null
			if (def!=null) {
				var id=def.id
				var tc=def.tc
				if (Control[tc]){
					def.top=Math.min(def.top, 1000)
					var ctl=new Control[ tc ](def.def, this.md)
					ctl=def.copiaExacta(ctl)

					if (tc=='txt') {
						var tipo=datos.tipos[ cols[def.caption] ]
						if (tipo=='lvwNumero')
							ctl.align='right'
						else
							ctl.align='left'
						}
					ctl.id=id+(paraDisenhar? '' : ctlFila.id)
					ctl.filaContenedor=ctlFila
					var ctlDOM=ctl.toDOM()
						
					if ( fila==0 ){
						if (ctl.tope<0) ctl.tope=0
						if ( (ctl.tope+ctl.alto)>desfaseY) desfaseY=ctl.tope+ctl.alto
						if ( (ctl.izq+ctl.ancho)>desfaseX) desfaseX=ctl.izq+ctl.ancho
						}
									
					ctlFila.appendChild( ctlDOM )
					try	{
						if (tc=='pic') {
							var dato= this.sacaDato(datos, fila, def.caption)
							if (dato==null)
								dato=ctl.propsPorDefecto.caption
							ctl.rellena( dato, ctlDOM.id) 
							}
						else if (tc=='txt') {
							var dato= this.sacaDato(datos, fila, def.caption)
							ctl.rellena(dato, ctlDOM.id)
							}
						else if (tc=='lvw'){
							ctl.rellena(key)
							}
						else if (tc=='desp'){
							ctl.rellena(null, key)
							}
						else if ( tc=='cmd' ){
							ctlDOM.onclick=fnLanzaTramite(def.camino, def.tramite, ctlFila, true)
							ctlDOM.tabIndex=-1
							}
						}
					catch(e){
						console.debug('Error rellenando control %s: %s', id, e)
						}
					}
				}
			}
		ctlFila.style.height=desfaseY +'px'
		if(desfaseX<this.ancho/2)
			ctlFila2.style.width=desfaseX+'px'
		else{
			ctlFila2.style.width='100%'
			this.dom.style.overflowX='hidden'
			}
			
		if (key && exp.filasSeleccionadas && exp.filasSeleccionadas.contains(key)){
			seleccionarFila(ctlFila, true)
			primeraFilaSeleccionada=ctlFila
			}
	}
	
	if (primeraFilaSeleccionada)  this.hazScroll(primeraFilaSeleccionada)
	this.quitaThrobber()
	//~ this.relleno=true
}
ControlRD.prototype.sacaDato=function(datos, fila, campo){
	if ( campo.contains( sep ) )
		campo=campo.split( sep )[0]
	
	return datos.filas[fila][ datos.columnas.indexOfIgnoreCase(campo) ]
	}
ControlRD.prototype.rellenaParaDisenhar=function(disenho){
	this.relleno=false
	this.vacia()
		
	var rdVacio={'filas':[], 'maxElementosAlcanzado':false, 'tipos':[], 'columnas':['ModoDetalle', 'key']}
	var fila=['x', '0']
	for (var id=0; id<this.listaControles.length ; id++){
		var def=this.listaControles[id]
		if (def==null) {
			/*pass*/}
		else if (def.tc=='pic') {
			rdVacio.columnas.push( def.caption )
			fila.push(def.caption)
			}
		else 	{
			rdVacio.columnas.push( def.caption )
			fila.push(def.caption)
			}
		rdVacio.tipos.push('lvw')
		}
	rdVacio.filas.push(fila)
	this.rellenaTabla(rdVacio, true)
}
ControlRD.prototype.vacia=function() { 
	borraHijos(this.dom)
	Control.prototype.vacia.call(this)
	this.ponThrobber()
	
}
ControlRD.prototype.cargarControles=function(){
	return this.md.cargarControles( this.dom, this.numControl )
}
ControlRD.prototype.getPanelContenedor=function(){
	return this.dom
}
ControlRD.prototype.quitarControlContenido=function(ctl){
	var idx=this.listaControles.indexOf(ctl)
	delete this.listaControles[idx]
}
ControlRD.prototype.anhadirControlContenido=function(nuevo){
	nuevo.refresco=false //para que no intente refrescarlos cada vez que cambiamos de nodo
	//MSP quito la siguiente línea (ya se añade al clonar el control)
	//nuevo.id=nuevo.id+'tr_'+nuevo.cont+'_0' //el id en los controles del RD debe indicar la fila en la que está
	this.listaControles.push(nuevo)
}
////////////////////////////
function ControlARB(c, md){ 
	Control.call(this,c,md) 
	this.init()
	this.xcontrol=new ArbolModoDetalle(this.numControl, this.caption)
}
ControlARB.prototype= new ControlLVW
Control['arb']=ControlARB
ControlARB.prototype.propsPorDefecto={alto:4500, ancho:3000, izq:300, caption:'nivelVacio'}
////////////////////////////
function ControlGRA(c, md){ 
	Control.call(this,c,md) 
	this.init()
}
ControlGRA.prototype= new Control
ControlGRA.prototype.ponFuente=function(nodoDOM) {}

Control['gra']=ControlGRA
ControlGRA.prototype.propsPorDefecto={alto:3000, ancho:3000, izq:300, caption:'./fijo/disenhador.graVacia.png'}
ControlGRA.prototype.init = function() {
	var temp=this.caption.split(pipe)
	this.nivel=temp[0]
	this.titulo=temp[1]
	this.tipo=temp[2]
	this.leyenda=parseInt(temp[3])==1
	this.restoInfo=temp[4]
}
ControlGRA.prototype.toDOM = function() {
	var ret=this.creaDIV('img')
	ret.oncontextmenu=this.fnDatoQueSeMuestra(this.id)
	
	this.dom=ret
	Control.prototype.toDOM.call(this)
	return this.dom
	}
ControlGRA.prototype.rellena=function(){
	if (this.relleno) return
	this.relleno=true
	this.vacia()
	
	var gra=control(this.id)	
	if (this.caption==this.propsPorDefecto.caption) 
		gra.src=this.caption
	else
		gra.src='grafica?aplicacion='+exp.aplicacion+'&nodo='+this.md.nodoActivo+'&md='+this.md.id+"&numControl="+this.numControl+
				'&nivel='+this.nivel+'&leyenda='+(this.leyenda?1:0)+'&titulo='+this.titulo+'&tipo='+this.tipo +
				"&restoInfo="+this.restoInfo+
				"&h="+new Date() //lo de la hora es para evitar el cacheo
}
ControlGRA.prototype.vacia=function(){
	try {
		var gra=control(this.id)
		while (gra.firstChild!=null)
			gra.removeChild( gra.firstChild )
		}
	catch (e)
		{/*el control está vacío*/}
	Control.prototype.vacia.call(this)

}

ControlGRA.prototype.datosFRM=function(){
	return {numTramites:0, camino:0, tramite:'Propiedades de la lista', tipo:'frm', controles:[
			{tipo:'lvw', "id": 'titulo',maxlength : 150, valor:this.titulo, ds:'Título'},
			{tipo:'bsc', id:'exp_niveles.cd_nivel',obligatorio: true, ds:'Nivel',controles:[
					{tipo: 'lvw',id:'cd_nivel',maxlength : 30, valor:this.nivel, ds:''},
					]},
			{"tipo" : 'lvwOption',id:'a1',maxlength : 250, valor:this.tipo=='1', obligatorio: true, ds:'Barras 2D'},
			{"tipo" : 'lvwOption',id:'a0',maxlength : 250, valor:this.tipo=='0', obligatorio: true, ds:'Barras 3D'},
			{"tipo" : 'lvwOption',id:'a3',maxlength : 250, valor:this.tipo=='3', obligatorio: true, ds:'Línea 2D'},

			{"tipo" : 'lvwOption',id:'a5',maxlength : 250, valor:this.tipo=='5', obligatorio: true, ds:'Area 2D'},
			{"tipo" : 'lvwOption',id:'a4',maxlength : 250, valor:this.tipo=='4', obligatorio: true, ds:'Area 3D'},

			{"tipo" : 'lvwOption',id:'a13',maxlength : 250, valor:this.tipo=='13', obligatorio: true, ds:'Tarta 2D'},
			{"tipo" : 'lvwOption',id:'a14',maxlength : 250, valor:this.tipo=='14', obligatorio: true, ds:'Tarta 3D'},
			{"tipo" : 'lvwOption',id:'agantt',maxlength : 250, valor:this.tipo=='gantt', obligatorio: true, ds:'Gantt'},
			
			{"tipo" : 'lvwBoolean',id:'leyenda',maxlength : 250, valor:this.leyenda,obligatorio: this.leyenda, ds:'¿Mostrar leyenda?'},
			
			{tipo:'lvw', "id": 'resto',maxlength : -100, valor:this.restoInfo, ds:'Colores', globoAyuda:'Lista de colores en notación hexadecimal, sin # y separados por coma.\n\nEjemplo escala de verdes: 66ff33,66cc00,66cc33,669900,669933,666600,666633\nEjemplo escala de morados: ba55d3,9370db,8a2be2,9400d3,9932cc,8b008b,800080,4b0082,6a5acd,483d8b'},
			{tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS',controles:[{tipo: 'lvw',id:'cd_estilo',maxlength : -15, valor:this.claseCSS, ds:''}]}

			]}
}
ControlGRA.prototype.retornoFRM=function(param){
	var tipoGrafica='1'
	
	if (param['a0']==true)
		tipoGrafica='0'
	else if (param['a1']==true)
		tipoGrafica='1'
	else if (param['a3']==true)
		tipoGrafica='3'
	else if (param['a5']==true)
		tipoGrafica='5'
	else if (param['a4']==true)
		tipoGrafica='4'
	else if (param['a13']==true)
		tipoGrafica='13'
	else if (param['a14']==true)
		tipoGrafica='14'
	else if (param['agantt']==true)
		tipoGrafica='gantt'
	
	this.setNivel(param['cd_nivel'])
	var captionFinal=this.nivel+pipe+param['titulo']+pipe+tipoGrafica+pipe+(param['leyenda']?1:0)+pipe+param['resto']
	
	this.modificado = this.caption!=captionFinal
	if (!this.modificado)
		this.modificado=	this.claseCSS!=param['cd_estilo']
	this.claseCSS=param['cd_estilo']
	
	this.caption=captionFinal
	this.refresca() 
	
	this.relleno=false
	this.rellena()
}
ControlGRA.prototype.setNivel=function(idNivel){
	this.nivel=idNivel
	this.modificado=true
	}
////////////////////////////
function ControlBSP(c, md){
	if(!c) return
	Control.call(this,c,md)
	this.colorFondo=this.colores[1]
	this.colorFrente=this.colores[0]
	this.init()
	}
ControlBSP.prototype= new Control
Control['bsp']=ControlBSP
ControlBSP.prototype.onblur=function(){
	this.buscador.buscarPorCodigo(false)
	}
ControlBSP.prototype.getValue=function(){
	if (!this.buscador.yaInicializado)
		this.buscador.miraSihayCambios()
	if (this.buscador.nulo)
		return null
	else
		return this.valor0
	}
ControlBSP.prototype.init = function() {
	var temp=this.caption.split(' ')
	this.tabla=null
	
	if (this.longitud)
		this.longitud=Number(this.longitud)
	
	if (temp[1]) {
		this.nombreVar=temp[1].toLowerCase()
		this.tabla=temp[0]
		}
	else
		this.nombreVar=temp[0].toLowerCase()
	
	if (this.nombreVar=='')
		this.nombreVar=null
	}
ControlBSP.prototype.listaTXT=function(){
	return [this.nombreVar]
	}
ControlBSP.prototype.listaTXT_DOM=function(){
	return this.txts
	}
function calculaTamanhoControl(tipoDato, maxlength){
	var longControl=maxlength
	if (tipoDato=='lvwFecha'){
		longControl=19
		}
	else if (tipoDato=='lvwFile'){
		longControl=50
		}
	else  {
		longControl=min(maxlength, 100)
		if (longControl==0)
			longControl=4
		}
	return longControl
	}
ControlBSP.prototype.toDOM = function() {
	var txt, des
	var bsp=this.creaDIV()
	if (bsp.childNodes.length) {
		txt=$(bsp).find('input')[0]
		td2=$(bsp).find('td.campos')[0]
   		des=$(bsp).find('span.des_bsc')[0]
		}
	else{
		var cont=creaObjProp('table', {className:'campoCompuesto bsp'})
		bsp.appendChild(cont)

		var td1=creaObjProp('td', {className:'boton'}); var td2=creaObjProp('td', {className:'campos'}); var td3=creaObjProp('td', {className:'ds'})
		cont.appendChild(creaObjProp('tr', {hijos:[td1, td2, td3]}))
		
		var self=this
		var boton=creaObjProp('a', {className:'cmd', href:'#', onclick:function(){abreVentanaBuscador(self, self.md); return false;}, text:espacioDuro4})
	
		var fnBuscar=function(event){b.buscarPorCodigo(false)}
		var fnEsperarYBuscar=function(event){b.esperarYBuscarPorCodigo(false)}
						
		// casilla pa escribir
		txt=creaObjProp('input:text',{className:'txt_'+ this.tc, 
							name:this.nombreVar, 
							maxLength:this.longitud || 50, 
							size:calculaTamanhoControl(this.tipo, this.longitud),
							onblur:fnBuscar,
							//~ onkeydown:fnOnKeyDown,
							onkeypress:fnEsperarYBuscar}) 
		jwerty.key('enter', filtrar, null, txt)
		txt.setAttribute('autocomplete','off')
		
		// y descripción
		des=creaObjProp('span', {className:'des_bsc', id:'des_'+this.id})
		td1.appendChild(boton); td2.appendChild(txt); td3.appendChild(des)
		}
		
	this.txts=[txt]
	this.txt=this.txt || txt
	this.des=this.des || des
	if (this.claseCSS) {
		this.aplanaControl(bsp)
		ponEstilo(bsp, this.claseCSS)
		}
	else {
		bsp.className='ctlGotta '+this.tc

		if (this.colorFondo){
			txt.style.backgroundColor=this.colorFondo
			td2.style.backgroundColor=this.colorFondo
			}
		if (this.colorFrente) { 
			txt.style.color= this.colorFrente
			des.style.color= this.colorFrente
			}
		}
	
	this.dom=bsp
	Control.prototype.toDOM.call(this)
		
	var b=new Buscador(this)
	buscadores[this.idOriginal()]=b
	this.buscador=b

	return this.dom
	}
ControlBSP.prototype.rellena=function(valor){
	if (this.relleno) 
		return
	this.relleno=true
	this.vacia()
	
	var txt=this.txt
	txt.value=valor
	this.valor0=valor
	this.valorAnterior=[valor]
	
	if (valor)
		this.buscador.buscarPorCodigo()
	}
ControlBSP.prototype.vacia=function() {
	Control.prototype.vacia.call(this)
    this.des.innerHTML=''
    this.txt.value=''
	this.valida()
}
ControlBSP.prototype.datosFRM=function(){
	return {numTramites:0, camino:0, tramite:'Propiedades del parámetro', tipo:'frm', controles:[
			{tipo:'lvw', id:'variable', maxlength:-50, valor:this.nombreVar, obligatorio: true, ds:'Variable'},
			//~ {tipo:'lvw', id: 'tabla',maxlength : -50, valor:this.tabla,obligatorio: true, 'ds':'Origen de los datos', "dsExtendida" : 'Nombre de la tabla'},
			{tipo:'bsc', id:'DIC_Tablas.Nombre', ds:'Origen de los datos', colsQueBloquea:0, colsBloqueadas:0, controles:[
					{tipo:'lvw', id:'tabla', maxlength:-25, valor:this.tabla}
					]},
			
			{tipo:'bsc', id:'exp_estilos.ds_estilo',  ds:'Clase CSS', colsQueBloquea:0, colsBloqueadas:0, controles:[{tipo:'lvw', id:'cd_estilo', maxlength:-15, valor:this.claseCSS, obligatorio: false}]}
			
			]
		}
}		
ControlBSP.prototype.retornoFRM=function(param){
	this.tabla=param['tabla']
	this.nombreVar=param['variable']
	this.caption=this.tabla+' '+this.nombreVar
	this.claseCSS=param['cd_estilo']
	this.modificado = true
	this.refresca() 
}
ControlBSP.prototype.valida=function(){
	return this.buscador.valido
	}
ControlBSP.prototype.propsPorDefecto={ancho:3000, alto:225, izq:300, caption:'dic_tablas filtro'}
ControlBSP.prototype.marcaCampoErroneo=function(){
	try {
		this.txt.selectionStart=0
		this.txt.selectionEnd=this.txt.value.length
		}
	catch (e){
		}
	$(this.txt).fadeOut(jquerycssmenu.fadesettings.overduration)
	$(this.txt).fadeIn(jquerycssmenu.fadesettings.overduration)
	this.txt.focus()

	ponEstilo(this.txt, 'errorjsON')
	}
ControlBSP.prototype.marcaCampoCorrecto=function(ctl){quitaEstilo(this.txt, 'errorjsON')}
//////////////
function ControlBSM(c, md){
	ControlBSP.call(this,c,md)
}
ControlBSM.prototype= new ControlBSP
Control['bsm']=ControlBSM
ControlBSM.prototype.toDOM = function() {
	var ret=ControlBSP.prototype.toDOM.call(this)
	this.txt.size=this.txt.size*5
	return this.dom
}
///////////////////////////
function ControlDESP(c, md){
	if (c){
		Control.call(this,c,md)	
		
		this.colorFondo=this.colores[1] ||  '#FFFFFF'
		this.colorFrente=this.colores[0]  || '#000000'
		
		this.init()
		}
	}
ControlDESP.prototype= new Control
Control['desp']=ControlDESP
ControlDESP.prototype.init=function(){
	var temp=this.caption.split(' ')
	this.nombreVar=temp[0]
	this.nivel=temp[1]
	}
ControlDESP.prototype.toDOM=function(){
	this.dom=this.creaDIV('select')
	jwerty.key('enter', function(){filtrar()}, null, this.dom)
	
	var self=this
	$(this.dom).click(
			function(event){
				if (self.estaDentroDeRD()){ //si está dentro de un rd, nos aseguramos de que la fila está pinchada
					seleccionarFila(self.filaContenedor, event.ctrlKey, false, event.shiftKey, true)
					event.stopPropagation()
					}
				}
			)
	this.vacia()
	
	Control.prototype.toDOM.call(this)
	return this.dom
}
ControlDESP.prototype.lanzaCargar=function(){
	if (this.relleno)
		return
	if (this.estaDentroDeRD() && !this.keyContenedor)
		return

	ponEstilo(this.dom, 'cargando')
	var self=this
	var fnCargar=function(req){ self.cargar( xeval(req) ) }
	
	var param={'accion':'desp', 'lvw':this.numControl, 'nivel': this.nivel, 'aplicacion': exp.aplicacion}
	if (this.md){
		param['md']=this.md.nombreDetalle
		param['nodo']=this.md.nodoActivo
		
		if (this.keyContenedor)
			param['nodo']+='\\'+this.keyContenedor
		}
	loadJSONDoc( 	'json', param, fnCargar )
	}
ControlDESP.prototype.getValue=function(){
	return this.dom.value
}
ControlDESP.prototype.cargar= function(xlista) {
	quitaEstilo(this.dom, 'cargando')
	if (this.datosCargados)
		return
	this.datosCargados=true
	
	var lista=xlista.datos
	var p=rellenaPosxId(lista)
	for (var idfila=0; idfila<lista.filas.length; idfila++){
		var fila=lista.filas[idfila]
		
		var dic={value:fila[p.cd], texto: fila[p.ds], className:'ctlGotta desp'}
		if (p.icono)
			dic['style.backgroundImage']=completaRutaImg( fila[p.icono], true)
		
		var opt=creaObjProp('option', dic)
		if (p._css_) 
			ponEstilo(opt, fila[p._css_])
		
		this.dom.appendChild( opt )
		}
	if (this.valor!=null)
		this.dom.value=this.valor
	}
ControlDESP.prototype.datosFRM=function(){
	return {numTramites:0, camino:0, tramite:'Propiedades de la lista', tipo:'frm', controles:[
			{tipo:'lvw', id: 'variable',maxlength : 150, valor:this.nombreVar,obligatorio: true, ds:'Variable'},
			{tipo:'bsc', id:'exp_niveles.cd_nivel', obligatorio:true, ds:'Nivel', controles:[{tipo: 'lvw', id:'cd_nivel',maxlength : -30, valor:this.nivel, ds:''}]},
			{tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS', controles:[{tipo: 'lvw', id:'cd_estilo',maxlength:-15, valor:this.claseCSS, ds:''}]}			
			]}
	}
ControlDESP.prototype.retornoFRM=function(param){
	this.setNivel(param['cd_nivel'])
	var tempCaption=param['variable']+' '+this.nivel
	this.modificado=this.caption!=tempCaption
	if (!this.modificado)
		this.modificado=	this.claseCSS!=param['cd_estilo']
	this.claseCSS=param['cd_estilo']

	this.caption=tempCaption
	this.refresca() 
	}
ControlDESP.prototype.setNivel=function(idNivel){
	this.nivel=idNivel
	this.modificado=true
	}
ControlDESP.prototype.propsPorDefecto={ancho:3500, alto:300, izq:300, caption:'@var nivelVacio'}
ControlDESP.prototype.rellena=function(valor, key){
	this.valor=valor
	this.keyContenedor=key
	if (!this.datosCargados){
		borraHijos(this.dom)
		this.lanzaCargar()
		}
	else if (valor=="" || valor==null){
		//pass
		}
	else
		this.dom.value=valor
	}
ControlDESP.prototype.vacia=function(){
	Control.prototype.vacia.call(this)
	borraHijos(this.dom)
	this.datosCargados=false
	this.valor=null
	this.lanzaCargar()
	}
ControlDESP.prototype.marcaCampoErroneo=function(){}
ControlDESP.prototype.marcaCampoCorrecto=function(){}
////////////////////////////
function ControlEXP(c, md){
        Control.call(this,c,md)
	
	this.colorFondo=this.colores[1]
	if (this.colorFondo=='transparent')
		this.colorFondo='#F0F2FF'
	this.colorFrente=this.colores[0]  || '#000000'
	
	this.listaControles=[]
	this.init()
	}
ControlEXP.prototype= new Control
Control['exp']=ControlEXP
ControlEXP.prototype.init=function(){
	var temp=this.caption.split(pipe)
	this.literalTitulo=temp[0]
	this.evento=temp[1] || 'onclick'
	}
ControlEXP.prototype.quitarControlContenido=function(ctl){
	var idx=this.listaControles.indexOf(ctl)
	delete this.listaControles[idx]
}
ControlEXP.prototype.anhadirControlContenido=function(nuevo){
	this.listaControles.push(nuevo)
	//aprovechamos para redimensionar el panel contenedor
	var maxTope=0
	var maxIzq=this.ancho
	for (var i in this.listaControles){
		var ctl=this.listaControles[i]
		
		if (typeof ctl=='function')
			continue
			
		maxTope=Math.max(maxTope, ctl.alto+ctl.tope)
		maxIzq=Math.max(maxIzq, ctl.ancho+ctl.izq)
		}
	mueve(this.panel, null,null, maxIzq, maxTope)
}
ControlEXP.prototype.toDOM=function(){
	this.dom=this.creaDIV()
	ponEstilo(this.dom, 'section')
	
	if (!this.dom.childNodes.length){
		this.panel=creaObjProp('div', {className:'article panelAcordeon', 'style.top':this.alto+'px', tabIndex:-1})
		this.titulo=creaObjProp('div', {className:'header', 'style.height':this.alto+'px', tabIndex:0})

		this.dom.appendChild(this.panel)
		this.dom.appendChild(this.titulo)
		
		var panelAcordeon=$(this.panel)
		var exp=$(this.dom)
		
		if (this.evento=='onclick'){
			var fnOnClick=function(){exp.toggleClass('cerrado')	}		
			exp.click(fnOnClick)
			}
		else if (this.evento=='onmouseover'){	
			var fnOnMouseOver=function(){exp.removeClass('cerrado')}
			var fnOnMouseOut=function(){ exp.addClass('cerrado')}
			exp.hover(fnOnMouseOver, fnOnMouseOut)
			}
		this.ponEventosTeclado()
		}
	
	$titulo=$(this.titulo).text(this.literalTitulo)
	
	this.dom.className='section ctlGotta exp cerrado'
	if (this.claseCSS){
		this.aplanaControl(this.dom)
		ponEstilo(this.dom, this.claseCSS)
		}
	else {
		this.ponFuente(this.titulo)
		$(this.dom).css({color:this.colorFrente, backgroundColor:this.colorFondo})		
		}
	
	Control.prototype.toDOM.call(this)
	return this.dom
}
ControlEXP.prototype.onkeydown=function(tecla, ctrlKey, altKey, event){
	//~ if (event.target!==event.currentTarget)
		//~ return
	if ([btnAbajo, btnArriba].contains(tecla)) {
		var d=$(this.dom)
		if (tecla==btnAbajo)
			d.removeClass('cerrado')
		else
			d.addClass('cerrado')
		return false
		}
}
ControlEXP.prototype.ponEventosTeclado=function(){
	var self=this
	if (!this.eventosTeclado)
		jwerty.key(teclasArribaAbajo, function(event){return self.onkeydown(event.keyCode, event.ctrlKey, event.altKey, event)}, null, this.dom)
	this.eventosTeclado=true
}
ControlEXP.prototype.datosFRM=function(){
	var xlista={'onmouseover' : 'al pasar el ratón por encima', 
			'onclick' : 'al hacer click'}
	
	return {numTramites:0, camino:0, tramite:'Propiedades de la lista', tipo:'frm', controles:[
			{tipo:'lvw', id: 'caption',maxlength : -75, valor:this.literalTitulo, obligatorio:true, ds:'Texto', dsExtendida:'Texto que se mostrará con el botón contraido'},
			{tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS', controles:[{tipo: 'lvw', id:'cd_estilo', maxlength:-15, valor:this.claseCSS, ds:''}]},

			{tipo:'etiqueta', id:'sinnombre97828808282', ds:'Comportamiento'},
			{tipo:'lvwLista', id:'comportamiento', valor:this.evento=='onmouseover'?'onmouseover':'onclick',  ds:'El desplegable aparece', lista:xlista}
				
			]}
	}		
ControlEXP.prototype.retornoFRM=function(param){
	var tempCaption=param['caption']+pipe+param['comportamiento']
	this.modificado=tempCaption!=this.caption
	if (this.modificado==false)
		this.modificado=	this.claseCSS!=param['cd_estilo']
	this.claseCSS=param['cd_estilo']
	this.caption=tempCaption
	this.refresca() 
	}
ControlEXP.prototype.propsPorDefecto={ancho:3500, alto:300, izq:300, caption:'Más'}
ControlEXP.prototype.cargarControles=function(){
	return this.md.cargarControles(this.panel, this.numControl )
}
ControlEXP.prototype.getPanelContenedor=function(){
	return this.panel
}
ControlEXP.prototype.rellena=function(valores, dinamicos){
	if (dinamicos)
		this.md.rellenarControlesDIN(this.numControl, 0)
	}
////////////////////////////
function ControlPER(c, md){//Control personalizado
	if (c)
		ControlDESP.call(this,c,md)
	}
ControlPER.prototype=new ControlDESP
Control['per']=ControlPER
ControlPER.prototype.init=function(){
	this.nivel=this.caption
	}
ControlPER.prototype.datosFRM=function(){
	return {numTramites:0, camino:0, tramite:'Propiedades del control', tipo:'frm', controles:[
			{tipo:'bsc', id:'exp_niveles.cd_nivel', obligatorio:true, ds:'Nivel', controles:[{tipo:'lvw', id:'cd_nivel', maxlength:-30, valor:this.nivel}]},
			{tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS', controles:[{tipo:'lvw', id:'cd_estilo', maxlength:-15, valor:this.claseCSS}]}
			]}
	}
ControlPER.prototype.retornoFRM=function(param){
	this.setNivel(param['cd_nivel'])
	
	var tempCaption=this.nivel
	this.modificado=this.caption!=tempCaption
	if (!this.modificado)
		this.modificado=	this.claseCSS!=param['cd_estilo']
	
	this.claseCSS=param['cd_estilo']
	this.caption=tempCaption
	this.refresca() 
	}
ControlPER.prototype.cargar= function(xlista) {
	//~ if (this.relleno)
		//~ return
	quitaEstilo(this.dom, 'cargando')

	this.cargarFilas(xlista.datos)
	this.relleno=true
	if (this.valor!=null)
		this.establecerValorActivo(this.valor)
	}
////////////////////////////
function creaT(t){
	return document.createTextNode(t) 
}
function creaHTML(t){
	var ret=document.createElement('span')
	ret.innerHTML=t
	return ret
	}
function creaTConElipsis(texto, d){
	if (texto!=null && texto.length>25) {
		if (d) d.title=texto
		return creaT(texto.substring(0, 25)+'...')
		}
	else
		return creaT(texto)
}
if (Array.prototype.indexOf==null){
	Array.prototype.indexOf = function(s) {
		for (var x=0;x<this.length;x++) if (this[x] == s) return x
		return -1;
		}
	}
if (Array.prototype.remove==null){
	Array.prototype.remove = function(s) {
		var pos=this.indexOf(s)
		if (pos==-1 && !isNaN(s))
			pos=Number(s)
		if (pos>-1) this.splice(pos,1)
		}
	}
if (Array.prototype.indexOfIgnoreCase==null){
	Array.prototype.indexOfIgnoreCase = function(s) {
		s=s.toLowerCase()
		for (var x=0;x<this.length;x++) if (this[x] && this[x].toLowerCase() == s) return x
		return -1;
		}
	}
if (Array.prototype.lastIndexOf==null){
	Array.prototype.lastIndexOf = function(s) {
		for (var x=length;x>-1;x--) if ( this[x] == s ) return x;
		return -1;
		}
	}
if (Array.prototype.contains==null){
	Array.prototype.contains = function(s) {
		return this.indexOf(s)>-1
		}
	}
if (Array.prototype.containsIgnoreCase==null){	
	Array.prototype.containsIgnoreCase = function(s) {
		return this.indexOfIgnoreCase(s)>-1
		}
	}
////////////////////////////
if (String.prototype.lpad==null){
	String.prototype.lpad=function (carRelleno, numPosiciones){
		var temp=this + ''
		while (temp.length < numPosiciones)
			temp = carRelleno +  temp
		return temp
		}
	}
if (String.prototype.startsWith==null){
	String.prototype.startsWith = function(s) {
		return (this.indexOf(s)==0)
		}
	}
if (String.prototype.endsWith==null){
	String.prototype.endsWith = function(s) {
		return (this.lastIndexOf(s)>-1 && this.lastIndexOf(s)==this.length-s.length)
		}
	}
if (String.prototype.contains==null){
	String.prototype.contains = function(s) {
		return this.indexOf(s)>-1
		}
	}
if (String.prototype.containsIgnoreCase==null){
	String.prototype.containsIgnoreCase = function(s) {
		return this.toLowerCase().indexOf(s.toLowerCase())>-1
		}
	}
if (String.prototype.trim==null){
	String.prototype.trim=function() {
		var v=this
		if (v.length < 1)
		    return ""
		v = v.rtrim();
		v = v.ltrim();
		if (v=="")
			return "";
		else
			return v;
			}
	
	String.prototype.rtrim=function() {
		var VALUE=this
		var w_space = String.fromCharCode(32);
		var v_length = VALUE.length;
		var strTemp = "";
		if(v_length < 0)
		    {return "";}
		var iTemp = v_length -1;

		while(iTemp > -1){
		    if (VALUE.charAt(iTemp) == w_space)
			{}
		    else
			{
			strTemp = VALUE.substring(0,iTemp +1);
			break;
			}
		    iTemp = iTemp-1;
		    }
		return strTemp;
		}

	String.prototype.ltrim=function() {
		var VALUE=this
		var w_space = String.fromCharCode(32);
		if(v_length < 1)
			{return "";}
		var v_length = VALUE.length;
		var strTemp = "";

		var iTemp = 0;

		while(iTemp < v_length) {
			if (VALUE.charAt(iTemp) == w_space)
				{}
			else {
				strTemp = VALUE.substring(iTemp,v_length);
				break;
				}
			iTemp = iTemp + 1;
			} 
		return strTemp;
		}
	}
if (String.prototype.replaceAll==null){
	String.prototype.replaceAll=function(b, r){
		var t=this.toString()
		while (t.indexOf(b) > -1)
			t= t.replace(b,r)
		return t
		}
	}
if (String.prototype.capitalize==null){
	String.prototype.capitalize=function(){
		var t=this.toString()
		return t.charAt(0).toUpperCase() + t.slice(1)
		}
	}
////////////////////////////////
function envuelve(obj){
	if (!obj)
		return
	var clave= obj instanceof Array?'hijos':'hijo'
	var dic={}; dic[clave]=obj
	
	if (obj.id && obj.id.startsWith('menu__'))
		dic['id']='menu'+obj.id.substring(6)
		
	if (!obj){
		}
	else if (obj instanceof Array){
		for (var i=0; i<obj.length; i++){
			if (tieneEstilo(obj[i], 'droprightmenu')){
				dic['className']='droprightmenu' //la flechita negra
				break
				}
			}
		}
	else if (obj.className)
		dic['className']=obj.className	
	
	return creaObjProp('li', dic)
	}
////////////////////////////////
function colorTinta(tinta) {
	if (tinta.startsWith('#'))
		return tinta
	try {
		var prueba = Number(tinta)
		if ( isNaN(prueba) )
			return tinta
		}
	catch (e) {
		return tinta
		}
	var temp="000000"+prueba.toString(16)
	temp = temp.substring(temp.length-6)
	var ret=temp.substring(4,6)+temp.substring(2,4)+temp.substring(0,2)
	
	var colorFinal="#"+ret
	if (disenhador)
		anhadeACartaColores(colorFinal)
		
	return colorFinal
}
function colorRGB(color){
	var r = HexToR(color);
	var g = HexToG(color);
	var b = HexToB(color);
	return 'rgb('+r+","+g+","+b+')'
}
function HexToR(h) {return parseInt((cutHex(h)).substring(0,2),16)}
function HexToG(h) {return parseInt((cutHex(h)).substring(2,4),16)}
function HexToB(h) {return parseInt((cutHex(h)).substring(4,6),16)}
function cutHex(h) {return (h.charAt(0)=="#") ? h.substring(1,7):h}
function colorVB(color){
	if (color==null)
		return null
	else if (color.startsWith('#'))
		color=colorRGB(color)
	
	//rgb(128, 128, 128)
	var colores=color.substring(4, color.length-1).split(",")
	var r,g,b
	r=colores[0]; g=colores[1]; b=colores[2]
	return 256*256*b+256*g+1*r
}
////////////////////////////////
function activaPestanha(idDetalle, idTab, idPest){
	var md=idDetalle?exp.getMD(idDetalle) || exp.listaFormulariosModosDetalle[idDetalle]:exp.mdActivo()
	var ctab=md.getControl(idTab)
	if (ctab)
		ctab.activaPestanha(idPest || 0)	
	}
function _filtrar(){
	var lista=exp.listaControlesPorTipo( Control.prototype.controlesConFiltro)
	var param={aplicacion:exp.aplicacion}
	var md=exp.mdActivo()
	if (md){
		param['md']=md.nombreDetalle
		param['nodo']=md.nodoActivo
		}
	
	var hayQueLanzar=true
	for (var i = 0; i < lista.length; i++) {
		var ctl = lista[i]
		if (!ctl.estaVisible()) 
		    continue
		if (ctl.nuevo) 
		    continue
		var literal = ctl.idOriginal()
		ctl.onblur()
		param[literal] = ctl.getValue()
		if (!ctl.valida()) {
			try {
				ctl.md.marcaCampoErroneo(ctl)
				} 
			catch (e) {
				}
			hayQueLanzar = false
			}
		else
			ctl.md.marcaCampoCorrecto(ctl)
		}
	if (hayQueLanzar)
		return param
	return null	
}
function filtrar(reintentos){
	if(reintentos!==0)
		for (var x in pendiente){
			if (pendiente[x]){
				if(!reintentos)
					reintentos=20
				else
					reintentos--
				setTimeout( function(){filtrar(reintentos)}, 200)
				return
				}
			}
	if(reintentos===0)
		pendiente={} // a veces se queda algo trabado...
	var param=_filtrar()
			
	//añadimos los filtros del mantenimiento de tablas que Ayda ha ordenado meterle
	var lista=exp.mdActivo().listaControlesPorTipo(['lvw'])
	for (var i=0; i<lista.length; i++){
		var ctl=lista[i]
		var listaFiltros=$(ctl.dom).find('.filaFiltros.param')
		
		for (var f=0; f<listaFiltros.length; f++){
			var inp=listaFiltros[f]
			param[inp.id]=inp.value
			}
		}
			
	if (param)
		loadJSONDocPost('filtrar', param, function(){ exp.refrescaTodo() })
}
var JSONparse
if ('JSON' in window && 'parse' in JSON){
	JSONparse=JSON.parse
	JSONstringify=JSON.stringify
	}
else{
	JSONparse=function(q){
		if (q.startsWith('{'))  q='('+q+')'	
		return eval(q)
		}
	JSONstringify=function(o){
		var ret=[]
		for (var i in o)
			ret.push('\''+i+'\':'+'\''+o[i]+'\'')
		return '{'+ret.join(',')+'}'
		}
	}
function xeval(q, noSacarError){
	var ret
	if (typeof q=='object')
		ret=q
	else {
		if (q==='')
			return null
		if (q==null)
			return null

		ret=JSONparse(q)
		}
	
		
	if (ret && ret.tipo=='error' ) {
		if (ret.tipoError==='ErrorSesionCaducada') {
			alert(ret.msg || 'La sesión ha finalizado por falta de actividad')			
			try 	{
				window.location=exp.getURL()
				}
			catch (e){
				alert('La sesión ha finalizado por falta de actividad')
				window.location.reload()    
				}
			}
		else {
			var info=ret.msg || 'Ha ocurrido un error al obtener la información. Por favor, intente refrescar la página o consulte con su administrador.'
			if (noSacarError==undefined || noSacarError==false)
				alert(info)
			console.log(ret.msg)
			}
		}
	return ret
	}
function replacePrimerHijo(padre, hijoNuevo){
	if (padre.firstChild)
		padre.removeChild( padre.firstChild)
	padre.appendChild( hijoNuevo )
}
////////////////////////////////////////
function saca1DeDic(dic){ //sacamos el último válido
	var ret
	for (var id in dic){
		if (dic[id] && typeof(dic[id])!='function')
			ret=dic[id]
		}
	return ret
}
////////////////////////////////////////
function creaSpan(texto){
	var sp=document.createElement('span')
	sp.appendChild(creaT(texto))
	return sp
}
function replaceTodos(texto,s1,s2){
	return texto.split(s1).join(s2);
}
function fnBorra(obj){
	return function(){borra(obj)}
	}
function fnSustituyePuntoPorComa(){
	return function(e){
		// cambiamos . por ,
		e=e || window.event
		try {
			var t=e.currentTarget || e.srcElement
			
			var pst=getSelectionStart(t)
			var pen=getSelectionEnd(t)
			
			if (pst>pen && $.browser=='msie' && $.browser.version=='8.0'){
				if (t.value.contains(','))
					return new Evt(e).consume()
				}
			
			var string_start = t.value.substring(0,pst)
			var string_end = t.value.substring(pen)
			t.value = string_start+ ','+ string_end
			var l=string_start.length+1
			if (!exp.internetExplorer)
				t.selectionEnd =t.selectionStart = l
			else {
				var range = t.createTextRange()
				range.collapse(true)
				range.moveEnd('character', l)
				range.moveStart('character', l)
				range.select()
				}
			}
		catch (err){
			}

		return false
		}
	}
////////////////////////////////////////
function mueveRubberBand(event){
	try 	{
		if (exp.modoDisenho)
			disenhador.mueveRubberBand(event)
		}
	catch (e) {/*pass*/}
		
	}
function cancelaRubberBand(event){
	if (exp.modoDisenho)
		disenhador.cancelaRubberBand(event)
	}
function creaRubberBand(event){
	if (exp.modoDisenho)
		disenhador.creaRubberBand(event)
	}

/*
 * jQuery Asynchronous Plugin 1.0
 *
 * Copyright (c) 2008 Vincent Robert (genezys.net)
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 */
(function($){

// opts.delay : (default 10) delay between async call in ms
// opts.bulk : (default 500) delay during which the loop can continue synchronously without yielding the CPU
// opts.test : (default true) function to test in the while test part
// opts.loop : (default empty) function to call in the while loop part
// opts.end : (default empty) function to call at the end of the while loop
$.whileAsync = function(opts){
	var delay = Math.abs(opts.delay) || 10,
		bulk = isNaN(opts.bulk) ? 500 : Math.abs(opts.bulk),
		test = opts.test || function(){ return true; },
		loop = opts.loop || function(){},
		end  = opts.end  || function(){};
	
	(function(){

		var t = false, 
			begin = new Date();
			
		while( t = test() )
		{
			loop();
			if( bulk === 0 || (new Date() - begin) > bulk )
			{
				break;
			}
		}
		if( t ) 
		{
			setTimeout(arguments.callee, delay);
		}
		else
		{
			end();
		}
		
	})();
}
// opts.delay : (default 10) delay between async call in ms
// opts.bulk : (default 500) delay during which the loop can continue synchronously without yielding the CPU
// opts.loop : (default empty) function to call in the each loop part, signature: function(index, value) this = value
// opts.end : (default empty) function to call at the end of the each loop
$.eachAsync = function(array, opts){
	var i = 0, 
		l = array.length, 
		loop = opts.loop || function(){};
	
	$.whileAsync(
		$.extend(opts, {
			test: function(){ return i < l; },
			loop: function()
			{ 
				var val = array[i];
				return loop.call(val, i++, val);
			}
		})
	);
}

$.fn.eachAsync = function(opts){
	$.eachAsync(this, opts);
	return this;
}
})(jQuery)
/**
* jQuery.ScrollTo - Easy element scrolling using jQuery.
* Copyright (c) 2007-2009 Ariel Flesler - aflesler(at)gmail(dot)com | http://flesler.blogspot.com
* Dual licensed under MIT and GPL.
* Date: 5/25/2009
* @author Ariel Flesler
* @version 1.4.2
*
* http://flesler.blogspot.com/2007/10/jqueryscrollto.html
*/
;(function(d){var k=d.scrollTo=function(a,i,e){d(window).scrollTo(a,i,e)};k.defaults={axis:'xy',duration:parseFloat(d.fn.jquery)>=1.3?0:1};k.window=function(a){return d(window)._scrollable()};d.fn._scrollable=function(){return this.map(function(){var a=this,i=!a.nodeName||d.inArray(a.nodeName.toLowerCase(),['iframe','#document','html','body'])!=-1;if(!i)return a;var e=(a.contentWindow||a).document||a.ownerDocument||a;return d.browser.safari||e.compatMode=='BackCompat'?e.body:e.documentElement})};d.fn.scrollTo=function(n,j,b){if(typeof j=='object'){b=j;j=0}if(typeof b=='function')b={onAfter:b};if(n=='max')n=9e9;b=d.extend({},k.defaults,b);j=j||b.speed||b.duration;b.queue=b.queue&&b.axis.length>1;if(b.queue)j/=2;b.offset=p(b.offset);b.over=p(b.over);return this._scrollable().each(function(){var q=this,r=d(q),f=n,s,g={},u=r.is('html,body');switch(typeof f){case'number':case'string':if(/^([+-]=)?\d+(\.\d+)?(px|%)?$/.test(f)){f=p(f);break}f=d(f,this);case'object':if(f.is||f.style)s=(f=d(f)).offset()}d.each(b.axis.split(''),function(a,i){var e=i=='x'?'Left':'Top',h=e.toLowerCase(),c='scroll'+e,l=q[c],m=k.max(q,i);if(s){g[c]=s[h]+(u?0:l-r.offset()[h]);if(b.margin){g[c]-=parseInt(f.css('margin'+e))||0;g[c]-=parseInt(f.css('border'+e+'Width'))||0}g[c]+=b.offset[h]||0;if(b.over[h])g[c]+=f[i=='x'?'width':'height']()*b.over[h]}else{var o=f[h];g[c]=o.slice&&o.slice(-1)=='%'?parseFloat(o)/100*m:o}if(/^\d+$/.test(g[c]))g[c]=g[c]<=0?0:Math.min(g[c],m);if(!a&&b.queue){if(l!=g[c])t(b.onAfterFirst);delete g[c]}});t(b.onAfter);function t(a){r.animate(g,j,b.easing,a&&function(){a.call(this,n,b)})}}).end()};k.max=function(a,i){var e=i=='x'?'Width':'Height',h='scroll'+e;if(!d(a).is('html,body'))return a[h]-d(a)[e.toLowerCase()]();var c='client'+e,l=a.ownerDocument.documentElement,m=a.ownerDocument.body;return Math.max(l[h],m[h])-Math.min(l[c],m[c])};function p(a){return typeof a=='object'?a:{top:a,left:a}}})(jQuery);
////////////////////////////////////////
function activarDisenho(){
	var estado=!exp.modoDisenho
	loadJSONDoc( 'json',  
			{'accion':'disenho', aplicacion: exp.aplicacion, 'estado':estado}, 
			function() {
				var ttp, enlace
				enlace=control('btnDisenhador')
				
				ponEstilo(enlace, 'cargando')
				if (estado){
					_anhadeCSS('./fijo/asistentes.css', 'asistentes')
					if (!existeDisenhador()){
						_anhadeJS('http://localhost:8080/gotta/fijo/disenhador.js', 'disenhador')
						_anhadeJS('./fijo/editorNiveles.js', 'editorNiveles')
						_anhadeJS('./fijo/codemirror/js/codemirror.js', 'codemirror')	
						}
					ttp=Explorador.prototype.ttpBtnDisenhadorON
					ponEstilo(enlace, 'estado_on')
					$('body').addClass('disenho_on')
					}
				else {
					ttp=Explorador.prototype.ttpBtnDisenhadorOFF
					quitaEstilo(enlace, 'estado_on')
					$('body').removeClass('disenho_on')
					}
				quitaEstilo(enlace, 'cargando')
				enlace.title=ttp
				exp.modoDisenho=estado
				disenhador=disenhador || new Disenhador()
				disenhador.cambiaModoDisenho(estado)
				
					
				},
			function () {alert("Se ha producido un error al intentar activar el diseño")}
			)
	}
function _anhadeJS(r, id){
	var js = creaObjProp('script' , {id:id, type: 'text/javascript', src: r})
	$('head').append(js)
	}
function _anhadeCSS(r, id){
	if ($('head #'+id).length) return
	var css = creaObjProp('link', {id:id, rel:'stylesheet', type: 'text/css', href: r})
	$('head').append(css)
	}
function activarDepuracion(){
	var estado=!exp.modoDepuracion
	loadJSONDoc( 'json', 
			{'accion':'depuracion', aplicacion: exp.aplicacion, 'estado':estado}, 
			function() {
				var ttp, enlace
				enlace=control('btnDepurador')
				ttp=Explorador.prototype.ttpBtnDepuradorON
				
				ponEstilo(enlace, 'cargando')
				if (estado) {
					_anhadeCSS('./fijo/asistentes.css', 'asistentes')
					if (!existeDepurador()){
						_anhadeJS('http://localhost:8080/gotta/fijo/depurador.js', 'depurador')
						}
					ttp=Explorador.prototype.ttpBtnDepuradorON
					ponEstilo(enlace, 'estado_on')
					}
				else {
					ttp=Explorador.prototype.ttpBtnDepuradorOFF
					quitaEstilo(enlace, 'estado_on')
					}
				quitaEstilo(enlace, 'cargando')
				enlace.title=ttp
				exp.modoDepuracion=estado
				FormularioDepurador.modoDisenho=estado
				},
			function ()
				{alert("Se ha producido un error al intentar activar la depuración")}
			)
	}
function existeDisenhador(){
	try	{
		return Disenhador
		return true
		}
	catch (e){return false}	
	}
function existeDepurador(){
	try	{
		return FormularioDepurador
		return true
		}
	catch (e){return false}	
	}
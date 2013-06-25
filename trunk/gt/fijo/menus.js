//////////////////////////////////////////////////////
function completaRutaImg(icono, esParaBackgroundImage){
	if ( icono && icono!='' ) {
		exp=exp || {'icoGotta':icoGotta, 'icoWeb':icoWeb}
		var ruta
		icono+=''
		if (icono.startsWith(exp.icoGotta))
			ruta=icono+anhadirExtension(icono, '.png')
		else
			ruta=exp.icoWeb+icono+anhadirExtension(icono, '.png')
			
		if (esParaBackgroundImage)
			ruta='url('+ruta+')'
		return ruta
		}
}
function fnClickMenuPersonalizado(camino, tramite){
	return function(){lanzaTramite(camino, tramite); ocultaMenusPanSup()}
}
function ocultaMenusPanSup(){
	$('div.nav.jquerycssmenu > ul > li ul').fadeOut(jquerycssmenu.fadesettings.outduration)
}

function ocultaMenus(){
	menuContextual.ocultaMenus()
	}
/////////////////////////////////
var menuContextual=new MenuContextual()
function MenuContextual(event, cual, parametros){	}
//~ MenuContextual.prototype.seguirMostrando=false
//~ function ocultaMenu(cual) {
	//~ if (cual=='')
		//~ return
	//~ seguirMostrando=false; 
	//~ setTimeout(function(){_ocultaMenu(control(cual))},800);
	//~ }
//~ function sigueMostrandoMenu(){
	//~ seguirMostrando=true;
	//~ }
MenuContextual.prototype.listaActiva=null // el número del lvw o arb
MenuContextual.prototype.ocultaMenus=function() {
	var menus=new Array()
	menus=['tp','tf','te','tl','lt','lvw','dic_alta','dic_altabaja']

	for (var i=0;i<menus.length;i++)
		this._ocultaMenu(menus[i])
	}
MenuContextual.prototype._ocultaMenu=function(cual) {
	$('div.menuTramitacion').css({display:'none'})
	}
MenuContextual.prototype.muestraMenu=function(event, cual, parametros)  {//donde parametros = clave, camino, tramite, fechaPendiente
	//controlamos el contextMenu y el longPress (ipad)
	if (disenhador && disenhador.modoDisenho)
		return false

	if (event==null) event=window.event
	var botonValido
	var objeto
	try  {//mozilla
		objeto=event.currentTarget
		botonValido=2
		var s=objeto.id;//esta línea es sólo para provocar el error en ie
		}
	catch (e) {
		objeto=event.srcElement.parentElement
		botonValido=0
		}
	
	if (event.type=='contextmenu' && event.button != botonValido)
		return true
		
	if (objeto.tagName=='TD')
		objeto=objeto.parentElement
	cual=cual||objeto.getAttribute('tipoLista')	

	var temp=$(objeto).parents('div.ctlGotta')
	if (temp[0])
		this.listaActiva = temp[0].id  /* anotamos la lista que está activa */
	var contextMenu = control(cual)
	if (contextMenu==null)
		contextMenu=this.montaMenuLVW(cual)
	
	this.colocarMenu(contextMenu, event, objeto)
}
MenuContextual.prototype.colocarMenu=function(contextMenu, event, objeto){
	var xleft=event.pageX
	var xtop=event.pageY

	//~ if (1){//en ipad viene así 
	if (xleft==undefined){//en ipad viene así
		var o=$(objeto).offset()
		xleft=o.left+$(objeto).innerWidth()/2
		xtop=o.top+$(objeto).innerHeight()/2
		}
		
	var wHeight= window.innerHeight || document.documentElement.offsetHeight
	if( $(contextMenu).outerHeight()+xtop> wHeight)
		xtop= wHeight-$(contextMenu).outerHeight()-10
	
	$(contextMenu).css({left:xleft, top:xtop, display:'block'})
	return false
}
MenuContextual.prototype.creaM=function(id, tipo, clase, texto, onclick){
	var d=document.createElement(tipo)
	
	d.id=id
	if (clase!=null) d.className=clase	
	if (onclick != null)
		d.onclick=onclick
	if (texto!=null) d.appendChild( document.createTextNode(texto) )
		
	return d
}
MenuContextual.prototype.montaMenuLVW=function(cual,tipoOperacion,id) {//tp, te, tf, tl, lt
	if (control(cual)!=null){
		var mm=control(cual)
		mm.parentNode.removeChild(mm)
		}
	
	var menu=creaObjProp('div', {className:'menuTramitacion', id:cual})
	var fnOnMouseOver=function(){}
	var fnOnMouseOut=function(){menuContextual._ocultaMenu(cual)}
	$(menu).hover(fnOnMouseOver, fnOnMouseOut)
	
	var todos=		this.creaM('btnSeleccionarTodos', 'div', 'menuitems', Explorador.prototype.textoSeleccionarTodos, function(){seleccionarTodos(); })
	var invertir= 	this.creaM('btnInvertirSeleccion', 'div', 'menuitems', Explorador.prototype.textoInvertirSeleccion,  function(){ invertirSeleccion()})
	var sep=		creaObjProp('HR', {'style.width':'90%', size:1})
	var ejecutar=	this.creaM('btnEjecuta', 'div', 'menuitems', Explorador.prototype.textoEjecutar, function(){ejecuta()})
	var mRevivir=	this.creaM('btnRevivir', 'div', 'menuitems', Explorador.prototype.textoRevivir, function(){revivir()})
	var mReemitir=	this.creaM('btnReemitir', 'div', 'menuitems', Explorador.prototype.textoReemitir, function(){reemitir()})
		
	if (cual=='lvw') {//menú para las listas normales y corrientes, nada de tramitación
		menu.appendChild( todos )
		menu.appendChild( invertir )
		}
	else if (cual=='tp') {
		menu.appendChild( ejecutar )
		menu.appendChild( sep )
		menu.appendChild( todos )
		menu.appendChild( invertir )
		}
	else if (cual=='te'||cual=='tf') {
		menu.appendChild( mRevivir )
		menu.appendChild( mReemitir )
		menu.appendChild( sep )
		menu.appendChild( todos )
		menu.appendChild( invertir )
		if (cual=='tf') {
			var mVerDatos	=this.creaM('btnVerDatosOriginales', 	'div', 'menuitems', Explorador.prototype.textoVerInformacionFirmada, datosFirmados)
			var mVerFirma	=this.creaM('btnVerFirmas', 		'div', 'menuitems', Explorador.prototype.textoVerInformacionFirma, jsonInfoFirma)
			var mValidarFirma=this.creaM('btnValidarFirma', 		'div', 'menuitems', Explorador.prototype.textoValidarFirma, jsonValidaFirma)
			
			menu.appendChild( sep )
			menu.appendChild( mVerDatos )
			menu.appendChild( mVerFirma )
			menu.appendChild( mValidarFirma )
			}
		}
	else if (cual=='tl') {
		var pendiente=this.creaM('btnDejarPendiente', 'div', 'menuitems', Explorador.prototype.textoDejarPendiente, function(){ejecuta('pendiente')})
		
		menu.appendChild( ejecutar )
		menu.appendChild( pendiente )
		menu.appendChild( sep )
		menu.appendChild( todos )
		menu.appendChild( invertir )
		}
	else if (cual=='lt') {
		var ejecutarLote=this.creaM('btnEjecutarLote', 'div', 'menuitems', Explorador.prototype.textoEjecutar, function(){ejecuta('lotes')})
		
		menu.appendChild( ejecutarLote )
		menu.appendChild( sep )
		menu.appendChild( todos )
		menu.appendChild( invertir )
		}
	else if (cual=='pb') {
		var ejecutarLote=this.creaM('btnEjecutarLote', 'div', 'menuitems', Explorador.prototype.textoEjecutar, function(){ejecuta('lotes')})
		
		menu.appendChild( ejecutarLote )
		menu.appendChild( sep )
		menu.appendChild( mRevivir )
		menu.appendChild( mReemitir )
		menu.appendChild( sep )
		menu.appendChild( todos )
		menu.appendChild( invertir )
		}
	else if (cual=='dic_altabaja') {
		var borrar = this.creaM('btnBorrar','div','menuitems','Borrar',function(){editorDiccionario.borrarVirtual(tipoOperacion, id)})
		var alta = this.creaM('btnAlta','div','menuitems','Alta',function(){editorDiccionario.nuevoVirtual(tipoOperacion, id)})
		menu.appendChild( borrar )
		menu.appendChild( alta )
		}
	else if (cual=='dic_alta') {
		var alta = this.creaM('btnAlta','div','menuitems','Alta',function(){editorDiccionario.nuevoVirtual(tipoOperacion, id)})
		menu.appendChild( alta )
		}
	else if (cual=='depuracion.menuTablas'){
		var abrirTablas=	  this.creaM('btnDepuradorAbrirTablas', 'div', 'menuitems', 'Abrir todas', function(){depuradorActivo.abrirTablas(); })
		var abrirTablasConFilas=this.creaM('btnDepuradorAbrirTablasConFilas', 'div', 'menuitems', 'Abrir sólo las que tienen filas', function(){depuradorActivo.abrirTablasConFilas(); })
		var cerrarTablas=this.creaM('btnDepuradorCerrarTablas', 'div', 'menuitems', 'Cerrar todas', function(){depuradorActivo.cerrarTablas(); })
		
		var ordenarA=this.creaM('btnDepuradorOrdenarTablasAZ', 'div', 'menuitems', 'Ordenar alfabéticamente', function(){depuradorActivo.ordenarTablasAlfab(); })
		var ordenarB=this.creaM('btnDepuradorOrdenarTablasBloques', 'div', 'menuitems', 'Ordenar según el tipo (tabla base y de trámites primero, tablas con datos después)', function(){depuradorActivo.ordenarTablasConDatos(); })
		
		menu.appendChild(abrirTablas); menu.appendChild(abrirTablasConFilas); menu.appendChild(sep); menu.appendChild(cerrarTablas);
		menu.appendChild(sep);
		menu.appendChild(ordenarA); menu.appendChild(ordenarB);
		}
	document.body.appendChild( menu )
	
	return menu
	}
	
////////////////////////////////////////
function invertirSeleccion() {
	_seleccionarTodos(false)
	}
function seleccionarTodos(lista) {
	_seleccionarTodos(true)
	}
function _seleccionarTodos(todos) {
	ocultaMenus()

	if (todos){//seleccionar todos
		$('#'+menuContextual.listaActiva+' table .fila').addClass('seleccionada')
		$('#'+menuContextual.listaActiva+' input:checkbox:not(:checked)').attr('checked', true)
		}
	else {//invertir selección
		$('#'+menuContextual.listaActiva+' table .fila').toggleClass('seleccionada')
		$('#'+menuContextual.listaActiva+' input:checkbox').each( function(){
			this.checked=!this.checked
			})
		}
	}
function desSeleccionarTodos(lst) {
	ocultaMenus()
	
	var lista	
	if (lst.parentNode.parentNode.id=='divarbol') //para deseleccionar filas del árbol principal cuando este va como lista
		lista=$(lst).parents('div#divarbol').find('.fila')
	else if (lst.tagName=='DIV') //controles del detalle: lvw, subgrid, rd
		lista=$(lst).find('.fila')
	else //listas de sistema: diccionario, parámetros...
		lista=$(lst).parents('div.datos').find('.fila')
	
	lista.removeClass('seleccionada')
	lista.find('.lvw_chk:checkbox').attr('checked', false)
}
function dobleClick(fila) {
	desSeleccionarTodos(fila)
	seleccionarFila(fila)
	var tipoLista=fila.getAttribute('tipoLista')
	if(['tp','lt','pb','tl'].contains(tipoLista))
		ejecuta()
	else if(tipoLista=='te')
		revivir()
	else if(tipoLista=='tf')
		datosFirmados()
	}
function estaSeleccionada(fila) {
	var chk=fila.getElementsByTagName('input')[0]
	if (chk!=null)
		return chk.checked
	else if (fila.className.endsWith('S') )
		return true
	else 
		return false
	}
function ejecuta() {
	ocultaMenus();	
	lanzaTramite('', '')
	}
function reemitir() {
	ocultaMenus();	
	lanzaTramite('', '', undefined, undefined, {'tipoEjecucion':'Reemitir'})
	}
function revivir() {
	ocultaMenus();	
	lanzaTramite('', '', undefined, undefined, {'tipoEjecucion':'Revivir'})
	}
function jsonInfoFirma() {
	jsonFirmas('infoFirma','Obteniendo información...')
	}
function jsonValidaFirma() {
	jsonFirmas('validaFirma','Validando...')
	}
function datosFirmados(){
	jsonFirmas('datosFirmados','Obteniendo datos...')
}
function jsonFirmas(accion,msg){
	ocultaMenus()
	var md=exp.mdActivo()
	var mm=md.getControl(menuContextual.listaActiva)
	var fp=	mm.xcontrol.getFilasPinchadas()
	for(var i=0;i<fp.length;i++){
		var f=fp[i]
		var chk=control(f)
		var idxBloqueo=ponNuevoBloqueo(true, msg)
		var param={}
		param['aplicacion']= exp.aplicacion
		param['accion']=accion
		param['firma']=chk.parentNode.parentNode.getAttribute('firma')
		loadJSONDocPost('json', param, function(req){
			quitaBloqueo(idxBloqueo)
			retornoTramite( xeval(req)  )
			}
		)
		}
}



/*********************
//* jQuery Multi Level CSS Menu (horizontal)- By Dynamic Drive DHTML code library: http://www.dynamicdrive.com
//* Menu instructions page: http://www.dynamicdrive.com/dynamicindex1/ddlevelsmenu/
//* Last modified: Sept 6th, 08'. Usage Terms: http://www.dynamicdrive.com/style/csslibrary/tos/
*********************/

function sacaID(jq){
	var ret=[]
	for (var i=0; i<jq.length-1; i++)//OJO, el último no
		ret.push(jq[i].id)
	return ret
	}
var jquerycssmenu={
fadesettings: {overduration: 350, outduration: 100}, //duration of fade in/ out animation, in milliseconds
buildmenu:function(barra){
		this.barra=barra
		barra.addClass('jquerycssmenu')
		
		var $mainmenu= barra.find('>ul') // $('#'+barra[0].id+'>ul')
		var $headers=$mainmenu.find("ul").parent()
		
		$mainmenu.find('ul').css({display:'block', visibility:'visible'})
		
		jquerycssmenu.fadesettings.overduration=Math.max(jquerycssmenu.fadesettings.overduration, 1)
		jquerycssmenu.fadesettings.outduration=Math.max(jquerycssmenu.fadesettings.outduration, 1)
		
		$headers.each(function(i){
			var $curobj=$(this)
			var $subul=$(this).find('ul:eq(0)')
			this._dimensions={w:this.offsetWidth, h:this.offsetHeight, subulw:$subul.outerWidth(), subulh:$subul.outerHeight()}
			
			this.istopheader=$curobj.parents('ul').length==1? true : false
			$subul.addClass('nivel'+$curobj.parents('ul').length)
			
			$subul.css({top:this.istopheader? this._dimensions.h+'px' : 0})
			$curobj.hover(
				function(e, inicializando){
					var enlaceAResaltar=$(this).children('a:eq(0)')
					enlaceAResaltar.addClass('estado_hover')
					
					//para ocultar los que quedaron abiertos en el anterior menú
					var $ULsAbiertos=$(this).children().find('li:eq(0)').parents('ul')
					var ulsAbiertos=sacaID($ULsAbiertos)
					
					var $restoULs=$mainmenu.children().find("ul")
					
					$restoULs.each(function(i){
						if (! (ulsAbiertos.contains(this.id)))
							$(this).css({visibility:'hidden'})
						})
					/////////////////////////////////////////
					
					var $targetul=$(this).children('ul:eq(0)')
					this._offsets={left:$(this).offset().left, top:$(this).offset().top}
					var menuleft=this.istopheader? 0 : this._dimensions.w
					var menutop=this.istopheader? 0 : this._dimensions.h
										
					menuleft=(this._offsets.left+menuleft+this._dimensions.subulw>$(window).width())? (this.istopheader? -this._dimensions.subulw+this._dimensions.w : -this._dimensions.w) : menuleft
					
					var diff=this._offsets.top+menutop+this._dimensions.subulh-$(window).height()
					if (diff>0)//si no cabe en vertical, lo desplazamos hacia arriba
						menutop=-diff+25
					else {
						if (this.istopheader)
							menutop=this._dimensions.h
						else
							menutop=0
						}
					
					if (inicializando) 
						$targetul.css({visibility:'hidden'})
					else
						$targetul.css({visibility:'visible'})
					
					$targetul.css({left:menuleft+"px", top:menutop+'px'}).fadeIn(jquerycssmenu.fadesettings.overduration)
				},
				function(e){
					var enlaceAResaltar=$(this).children('a:eq(0)')
					enlaceAResaltar.removeClass('estado_hover')
					
					$(this).children('ul:eq(0)').fadeOut(jquerycssmenu.fadesettings.outduration)
					}
			) //end hover
		}) //end $headers.each()
		$mainmenu.find('ul').css({display:'none', visibility:'visible'})
		if (exp && exp.internetExplorer){/*fallo en ie7: aparecían los menús todos montados*/
			var lista=$mainmenu.find('ul li ul')
			lista.trigger('mouseenter', [true]).trigger('mouseleave')
			$mainmenu.find('ul').css({display:'none', visibility:'visible'})
			}
		
		},
getFrmActivo:function(){
	if (listaFrm.length)
		return listaFrm[ listaFrm.length-1 ]
	},
onkeydown:function (tecla, ctrlKey, altKey, event){
	if (depuradorActivo || editorNivelesActivo || this.getFrmActivo())
		return
	if (jwerty.is('alt+shift+f', event)){
		$('li.estado_hover').removeClass('estado_hover')	//para mover en horizontal
		$('li.estado_on').removeClass('estado_on')//para mover en vertical
		
		var primero=$('div.header div.nav.botonera.jquerycssmenu#botoneraIzq ul li:not(.separador)').find('a:eq(0)')
		primero=$(primero[0])
		primero.parents('div.nav.botonera.jquerycssmenu').focus()
		
		var lipadre=primero.parents('li')
		lipadre.addClass('estado_hover')
		
		var li=lipadre.children('ul').find('li:eq(0)')
		if (li.length)
			li.addClass('estado_on')
		else
			lipadre.addClass('estado_on')
		
		primero.mouseenter()		
		}
	else {
		var li=$('div.nav.botonera.jquerycssmenu li.estado_hover')
		if (li.length==0)
			li=$('div.nav.botonera.jquerycssmenu a.botonera.dropdownmenu:eq(0)').parent()
		var lipadre=li.parents('li')
		if (lipadre.length==0)
			lipadre=li
		
		if (tecla==btnIzq || tecla==btnDer){
			$('li.estado_on').removeClass('estado_on')
			var ant=lipadre
			if (tecla==btnIzq){
				lipadre=lipadre.prev()
				while (lipadre.hasClass('separador'))
					lipadre=lipadre.prev()
				}
			else {
				lipadre=lipadre.next()
				while (lipadre.hasClass('separador'))
					lipadre=lipadre.next()
				}
			if (lipadre.length==0)
				lipadre=ant
			
			ant.removeClass('estado_hover')
			lipadre.addClass('estado_hover')
			
			lipadre.children('ul').find('li:eq(0)').addClass('estado_on')
			ant.mouseleave()
			lipadre.mouseenter()
			}
		else if (tecla==btnArriba|| tecla==btnAbajo){
			li=$('li.estado_on')
			if (li.length==0)	return
			
			var ant=li
			if (tecla==btnAbajo){
				li=li.next()
				while (li.hasClass('separador'))
					li=li.prev()
				}
			else {
				li=li.prev()
				while (li.hasClass('separador'))
					li=li.prev()
				}
			if (li.length==0)
				li=ant
			
			ant.removeClass('estado_on')
			li.addClass('estado_on')
			
			//~ ant.mouseleave()//si se pone esto hace un parpadeo
			li.mouseenter()
			}
		else if (jwerty.is('enter', event)){
			var lihijo=$('li.estado_on')
			if (lihijo.length==0)
				lihijo=$('li.estado_hover')
			
			lihijo.children('a').click()
			
			this.quitaFoco()
			}
		else if (jwerty.is('esc', event)){
			this.quitaFoco()
			}
		}
	return false
	},
quitaFoco:function(){
	$('li.estado_hover').removeClass('estado_hover')
	$('a.estado_hover').removeClass('estado_hover')
	$('li.estado_on').removeClass('estado_on')
	$('.botonera.jquerycssmenu ul li ul').css({display:'none'})
	},
ponEventosTeclado:function (){
	if (!this.eventosTeclado){
		var menu=this
		jwerty.key('alt+shift+f', function(event){return jquerycssmenu.onkeydown(event.keyCode, event.ctrlKey, event.altKey, event)}, null, $('body'))
		
		if (!this.eventosTeclado)
			jwerty.key(teclasDireccion+'/enter/esc', function(event){return menu.onkeydown(event.keyCode, event.ctrlKey, event.altKey, event)}, null, '#botoneraIzq')
			
		$('div.header div.nav.botonera.jquerycssmenu#botoneraIzq').focusout(function(){menu.quitaFoco()})
			
		this.eventosTeclado=true
		}
	}
}
function resaltaCaminoMenuPinchado(enlace){
	$('div.botonera:not(div#botoneraAdmin) a.estado_on ').removeClass('estado_on')
	$(enlace).parents('li').children('a').addClass('estado_on')
}
var cartaColores=[
	'#000000','#993300','#333300','#003300','#003300','#000080','#333399','#333333',
	'#800000','#ff6600','#808000','#008000','#008080','#0000ff','#666699','#808080',
	'#ff0000','#ff9900','#99cc00','#339966','#33cccc','#3366ff','#800080','#969696',
	'#ff00ff','#ffcc00','#ffff00','#00ff00','#00ffff','#00ccff','#993366','#c0c0c0',
	'#ff99cc','#ffcc99','#ffff99','#ccffcc','#ccffff','#99ccff','#cc99ff','#ffffff'
	] // 8*5
var cartaColoresApli=[]
function dimensiones(ctl, respectoA){
	var $ctl=$(ctl)
	var ret= $ctl.offset()
	
	var offsetPanelDer=$(respectoA).offset()
	
	ret.left=ret.left-offsetPanelDer.left
	ret.top=ret.top-offsetPanelDer.top
	ret.width=$ctl.outerWidth(false)
	ret.height=$ctl.outerHeight(false)
	
	return ret
	}
Control.prototype.dimensiones=function(){
	//~ incluirPanelContenedor = incluirPanelContenedor || false
	var ctl=control(this.id)
	
	var ll, tt, ww, hh
	if (ctl) {
		if (!this.esContenedor) {
			ll=offset('l', ctl)
			tt=offset('t', ctl)
			
			var $ctl=$(ctl)
			ww=$ctl.innerWidth()
			hh=$ctl.innerHeight()
			}
		else {
			var llpanel1, ttpanel1, wwpanel1, hhpanel1
			var llpanel2, ttpanel2, wwpanel2, hhpanel2
			var dimPanel1, dimPanel2
			
			var panel1=this.titulo
			var panel2=this.getPanelContenedor()
			
			dimPanel2=dimensiones(panel2, this.md.div)
			dimPanel1= (panel1 && panel1.offsetHeight)? dimensiones(panel1, this.md.div) : dimPanel2			
				
			ll=	Math.min(dimPanel1.left, 	dimPanel2.left)
			tt=	Math.min(dimPanel1.top, 	dimPanel2.top)
			
			var maxBottom=Math.max(dimPanel1.top+dimPanel1.height, dimPanel2.top+dimPanel2.height)
			var maxRight=Math.max(dimPanel1.left+dimPanel1.width, dimPanel2.left+dimPanel2.width)
			
			hh=	maxBottom-tt
			ww=	maxRight-ll
			}
		}
	else	{
		ww=ctl.ancho
		hh=ctl.alto
		}
	
	return {'left':ll, 'top':tt, 'height':hh, 'width':ww}
	}
Control.prototype.ponTiradores=function(esPrimero){
	if (control(this.id+'central') )
		return
	
	var ll, tt, ww, hh
	
	var temp=this.dimensiones(false)
	var central=creaObjProp('DIV', {className:'grip', id:this.id+'central', 
							'style.left':temp.left+'px',  'style.top':temp.top+'px',  'style.width':temp.width+'px', 'style.height':temp.height+'px',
							title:'[Click derecho para editar el control]'})
	if (esPrimero)
		ponEstilo(central, 'gripPrincipal')
							
	var dra=creaObjProp('DIV', {className:'gripdra', id:this.id+'dra', onmousedown:function(event){
															resizePress(event); 
															xPreventDefault(event)
															return false}})
	central.appendChild( dra )
	
	var obj=this.id
	central.onmousedown=function(event){
								if (event.button==0){
									var ct=event.originalTarget || event.srcElement
									if (ct && tieneEstilo(ct, 'grip')){
										dragPress(obj, event)
										xPreventDefault(event)
										return false
										}
									}
							}	
	central.oncontextmenu=this.fnDatoQueSeMuestra(this.id)
	
	if (this.usaNivel) {
		var xthis=this
		var btn=creaObjProp('div', { title:'[Click para editar el nivel]',className:'gripNivel', onmousedown:function(event){editaNivel(xthis.nivel, xthis)} })
		central.appendChild(btn)
		}
	
	var panel=control( this.md.idx)
	panel.appendChild( central )
}
Control.prototype.refrescaTiradores=function(){
	var temp=this.dimensiones(false)
	var grip=control(this.id+'central')
	if (grip)
		mueve(grip, temp.left, temp.top,temp.width,temp.height)
}
Control.prototype.quitaTiradores=function(){
	var quitar=control(this.id+'central')
	if (quitar != null)  {
		quitar.parentNode.removeChild ( quitar )
		}
}

////////////////////////////////////////////////////////////////////////////
function Disenhador(md, ml){
	this.panelesInvertidos=false
	this.modoDisenho=false
	this.listaSeleccionados=[]
		
	this.pintandoCuadro=false
	this.moviendoControles=false
	this.dimensionandoControles=false
			
	this.rb=control("rubberBand")
	
	this.md=md
	this.ml=ml
	
	this.rbx0=0
	this.rby0=0
	
	this.sc=new ArbolModoDetalle('SelectorColumnas', 'listaTablas')
	this.sc.contenedor='SelectorColumnas'
	this.sc.onDblClickNodo=nuevoControl
	
	this.listaControlesCopiados=new Array()
	
	this.rejilla=5
}
function nuevoControl(cd){
	cd=cd.substring(1, cd.indexOf('\\'))
	disenhador.nuevoControl(cd)
}
////////////////////////////////////////////////////////////////////////////
Disenhador.prototype.cambioMD=function(md, ml){
	if (!this.modoDisenho){
		if (this.md )   this.md.cambiaModoDisenho(false)
		if (this.ml ) this.ml.cambiaModoDisenho(false)
		
		this.md=null
		this.ml=null
		
		return
		}
	
	var cambiaMD= md!=this.md
	var cambiaML=this.ml!=ml
	
	if (this.md && cambiaMD )   	this.md.cambiaModoDisenho(false)
	if (this.ml && cambiaML) 	this.ml.cambiaModoDisenho(false)
		
	this.ml=ml
	this.md=md
	
	if (this.modoDisenho){
		if (cambiaMD) {
			this.sc.nodoFicticio=null
			if (this.md && this.md.tb)
				this.sc.nodoFicticio='t'+this.md.tb
			this.sc.cargar(-1) //cargamos el selector de columnas
			}
			
		ocultaIFRAMES()
			
		if (cambiaMD && this.md) this.md.cambiaModoDisenho(true)
		if (cambiaML && this.ml) this.ml.cambiaModoDisenho(true)
		}
}
Disenhador.prototype.cambiaModoDisenho=function(estado){
	if (this.modoDisenho==estado)
		return
	
	if (this.panelesInvertidos) this.disenharModoLista()
		
	if (estado!=null)
		this.modoDisenho=estado
	else
		this.modoDisenho=!this.modoDisenho

	var picker=control('colorpicker')
	if (picker!=null) picker.style.display = 'none'
		
	if (!control('botoneraDisenho').firstChild) //la montamos
		this.montaBarraHerramientas()

	var expmd=exp.getMD(exp.idMdActivo)
	var expml=exp.getMD(exp.idMlActivo)
	this.cambioMD(expmd, expml)
	
	if (this.modoDisenho){
		//mostramos la barra de herramientas
		$('#botoneraDisenho').slideDown('slow')
		var btn=control('mlEditarVistas')
		if (btn) 
			btn.style.display='block'
		
		alternaVisible()//para el acordeón de la barra de herramientas
		}
	else {
		$('#botoneraDisenho').slideUp('slow')
		var btn=control('mlEditarVistas')
		if (btn) btn.style.display='none'
		
		exp.cambiaML(exp.idMlActivo)
		muestraIFRAMES()
		
		$('.dragMove').removeClass('dragMove')
		}
	exp.onResize(false)
}
Disenhador.prototype.montaBarraHerramientas=function(){
	var dis=control('botoneraDisenho')
	var divHerramientas, divDetalle, divControles, divFormato, divTamanho, divCoordenadas, divSelector//, divFicheros
		
	divHerramientas	=this.creaH3yDIV('divHerramientas', 'Herramientas', dis)
	divDetalle	=this.creaH3yDIV('divDetalle', 'Detalle', dis)
	divControles	=this.creaH3yDIV('divControles', 'Controles', dis)
	divFormato	=this.creaH3yDIV('divFormato', 'Formato', dis)
	divTamanho	=this.creaH3yDIV('divPosicion', 'Tamaño y posición', dis)
	divCoordenadas=this.creaH3yDIV('divCoordenadas', 'Coordenadas', dis)
	//~ divFicheros=this.creaH3yDIV('divFicheros', 'Recursos de la aplicación', dis)
	
	var btnGuardar=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnGuardarDetalle', 'texto':'Guardar', 'title':'Guardar los cambios', 	'onclick':function(){disenhador.guardar();return false;} })
	
	var btnEditarNiveles=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnEditarNiveles',	'texto':'Niveles', 	   'title':'Editar nivel activo del árbol/nivel del control seleccionado', 	'onclick':function(){editaNivel();return false;} })
	//~ var btnEditarModosLista=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnEditarModosLista',	'texto':'Editar vistas','title':'Editar vistas', 	'onclick':function(){editarVistas();return false;} })
	var btnEditarMenu=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnEditarMenu',	'texto':'Menú','title':'Editar menú', 	'onclick':function(){editarMenu();return false;} })
	
	var fnNuevoControl=function(){disenhador.nuevoControl();return false;}
	var btnNuevoControl=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnNuevoControl', 'title':'Crear nuevo control del tipo seleccionado', 'onclick':fnNuevoControl })
	
	var listaControles=creaObjProp('select',{'id':'tipoControl','onchange':fnNuevoControl});  cargarLista(listaControles, 'tiposControl', 'cd_tipocontrol', 'ds_tipocontrol', false, true)
	var btnEliminarControl=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnEliminarControl', 'title':'Eliminar controles', 'onclick':function(){disenhador.borrarControl();return false;}})
	
	var btnCopiarControles=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnCopiarControles','texto':'Copiar','title':'Copiar controles', 'onclick':function(){disenhador.copiarControles();return false;} })
	var btnPegarControles=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnPegarControles', 'texto':'Pegar','title':'Pegar controles',  'onclick':function(){disenhador.pegarControles();return false;} })
	
	var listaValores
	var selectNombreFuente=creaObjProp('select', {'id':'nombreFuente', 'onchange':function(){disenhador.ponTipoLetra(this.value)}})
	listaValores=['Arial', 'Arial Narrow', 'Century Gothic', 'Lucida Calligraphy', 'MS Sans Serif', 'Tahoma', 'Trebuchet MS', 'Verdana' ]
	for (var i=0; i<listaValores.length; i++){
		var valor=listaValores[i]
		var opt=creaObjProp('option', {'texto':valor, 'value':valor, 'style.fontFamily':valor})
		selectNombreFuente.appendChild(opt)
		}
	selectNombreFuente.value='Tahoma'
	
	var selectTamFuente=creaObjProp('select', {'id':'tamFuente', 'onchange':function(){disenhador.ponTamLetra(this.value)}})
	listaValores=[6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]
	for (var i=0; i<listaValores.length; i++){
		var valor=listaValores[i]
		var opt=creaObjProp('option', {'texto':valor, 'value':valor})
		selectTamFuente.appendChild(opt)
		}
	selectTamFuente.value=8
	
	var selectEstFuente=creaObjProp('select', {'id':'estFuente', 'onchange':function(){disenhador.ponEstLetra(this.value)}})
	listaValores=[ 	['Normal', 	'normal', 	'fontWeight', 		'normal'],
				['Negrita', 	'n', 		'fontWeight', 		'bold'], 
				['Cursiva', 	  'c', 		'fontStyle', 		'italic'], 
				['Subrayado', 's', 		'textDecoration', 	'underline'],
				['Tachado',	't', 		'textDecoration', 	'line-through'] ]
	for (var i=0; i<listaValores.length; i++){
		var valor=listaValores[i]
		var opt=creaObjProp('option', {'texto':valor[0], 'value':valor[1]})
		opt.style[valor[2]]=valor[3]
		selectEstFuente.appendChild(opt)
		}
		
	var selectEstilos=creaObjProp('select', {'id':'estiloFuente', onchange:function(){disenhador.ponClassName(this.value)}})
	var btnMantenimientoEstilos=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnMantenimientoEstilos', 'title':'[Click aquí para añadir o eliminar estilos de esta lista]', 'onclick':function(){disenhador.lanzaMantenimientoEstilos();return false;} })
	
	var btnMoverIzq	=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnMoverIzq', 'title':'Mover 1px hacia la izquierda', 	'onclick':function(){disenhador.mover1px('l');return false;} })
	var btnMoverDer	=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnMoverDer', 'title':'Mover 1px hacia la derecha',  	'onclick':function(){disenhador.mover1px('r');return false;} })
	var btnMoverArriba	=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnMoverArriba', 'title':'Mover 1px hacia arriba', 'onclick':function(){disenhador.mover1px('u');return false;} })
	var btnMoverAbajo	=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnMoverAbajo', 'title':'Mover 1px hacia abajo', 'onclick':function(){disenhador.mover1px('d');return false;} })
	
	var btnJuntarIzq	=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnJuntarIzq', 'title':'Juntar al de la izquierda', 	'onclick':function(){disenhador.juntar('l');return false;} })
	var btnJuntarDer	=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnJuntarDer', 'title':'Juntar al de la derecha',  	'onclick':function(){disenhador.juntar('r');return false;} })
	var btnJuntarArriba	=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnJuntarArriba', 'title':'Juntar al de arriba', 		'onclick':function(){disenhador.juntar('u');return false;} })
	var btnJuntarAbajo	=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnJuntarAbajo', 'title':'Juntar al de abajo', 		'onclick':function(){disenhador.juntar('d');return false;} })
	
	var btnIgualarIzq	=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnIgualarIzq', 		'title':'Alinear por la izquierda', 'onclick':function(){disenhador.igualar('l');return false;} })
	var btnIgualarDer	=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnIgualarDer', 	'title':'Alinear por la derecha',  'onclick':function(){disenhador.igualar('r');return false;} })
	var btnIgualarArriba=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnIgualarArriba', 	'title':'Alinear por arriba', 	'onclick':function(){disenhador.igualar('u');return false;} })
	var btnIgualarAbajo=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnIgualarAbajo', 	'title':'Alinear por abajo', 	'onclick':function(){disenhador.igualar('d');return false;} })

	var btnCrecer1pxIzq=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnCrecer1pxIzq', 	'title':'Reducir 1px de ancho', 		'onclick':function(){disenhador.crecer1px('l');return false;} })
	var btnCrecer1pxDer=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnCrecer1pxDer', 	'title':'Aumentar 1px de ancho',  	'onclick':function(){disenhador.crecer1px('r');return false;} })
	var btnCrecer1pxArriba=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnCrecer1pxArriba','title':'Reducir 1px de alto', 	'onclick':function(){disenhador.crecer1px('u');return false;} })
	var btnCrecer1pxAbajo=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnCrecer1pxAbajo', 'title':'Aumentar 1px de alto', 	'onclick':function(){disenhador.crecer1px('d');return false;} })
	
	var btnIgualarAncho=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnIgualarAncho', 'title':'Igualar anchura', 	'onclick':function(){disenhador.igualar('w');return false;} })
	var btnIgualarAlto=creaObjProp('a',    {'href':'#', 'className':'botonera', 'id':'btnIgualarAlto', 'title':'Igualar altura', 	'onclick':function(){disenhador.igualar('h');return false;} })
	
	var btnRepartirAncho=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnRepartirAncho', 'title':'Repartir separación horizontal', 	'onclick':function(){disenhador.repartir('w');return false;} })
	var btnRepartirAlto=creaObjProp('a',    {'href':'#', 'className':'botonera', 'id':'btnRepartirAlto', 	'title':'Repartir separación vertical', 		'onclick':function(){disenhador.repartir('h');return false;} })
	
	var btnRepartirAnchoN=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnRepartirAnchoN', 'title':'Especificar separación horizontal (n píxeles)', 	'onclick':function(){disenhador.repartirN('w');return false;} })
	var btnRepartirAltoN=creaObjProp('a',    {'href':'#', 'className':'botonera', 'id':'btnRepartirAltoN', 	'title':'Especificar separación vertical (n píxeles)', 		'onclick':function(){disenhador.repartirN('h');return false;} })
	
	var btnCrecerIzq=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnCrecerIzq', 	'title':'Hacer crecer hasta alinear al de la izquierda', 	'onclick':function(){disenhador.crecer('l');return false;} })
	var btnCrecerDer=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnCrecerDer', 	'title':'Hacer crecer hasta alinear al de la derecha', 	'onclick':function(){disenhador.crecer('r');return false;} })
	
	var btnCopiarEstilo=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnCopiarEstilo', 	'title':'Copiar estilo', 	'onclick':function(){disenhador.copiarEstilo();return false;} })
	var btnPegarEstilo=creaObjProp('a', {'href':'#', 'className':'botonera', 'id':'btnPegarEstilo', 	'title':'Pegar estilo', 	'onclick':function(){disenhador.pegarEstilo();return false;} })
	
	var colorFondo=creaObjProp('a', {	'href':'#', 
							'className':'colorPicker', 
							'id':'colorFondo',  
							'title':'Seleccionar el color de fondo', 
							'style.background':'#FFFFFF',
							'style.color':'#FFFFFF',
							'texto':espacioDuro3,
							'onclick':function(){pickColor('colorFondo');return false;}
							})
	var fnColorFondo=function(){disenhador.ponColorFondo(this.value)}
	var fnColorFrente=function(){disenhador.ponColorFrente(this.value)}
	
	var txtcolorFondo=creaObjProp('input', {type:'text', id:'txtcolorFondo',className:'colorPicker',value:'#FFFFFF', onkeypress:fnColorFondo, onchange:fnColorFondo})
	
	var colorFrente=creaObjProp('a', {	'href':'#',
							'className':'colorPicker',
							'id':'colorFrente',
							'title':'Seleccionar el color de la letra',
							'style.background':'#000000',
							'style.color':'#000000',
							'texto':espacioDuro3,
							'onclick':function(){pickColor('colorFrente');return false;} })
	var txtcolorFrente=creaObjProp('input', {type:'text', id:'txtcolorFrente',className:'colorPicker',value:'#000000', onkeypress:fnColorFrente, onchange:fnColorFrente})
	
	// panel herramientas
	var t=this.creaTabla(divHerramientas)
	this.creaFila2celdas(t, [btnEditarNiveles], [btnEditarMenu])
	
	// panel detalle
	var t=this.creaTabla(divDetalle)
	this.creaFila2celdas(t, [btnGuardar])
	
	// panel controles
	this.meteElementos(divControles, [btnEliminarControl, btnNuevoControl, listaControles]) //btnCopiarControles, btnPegarControles
	var t=this.creaTabla(divControles)
	this.creaFila2celdas(t, [btnEliminarControl, btnNuevoControl, listaControles])
	this.creaFila2celdas(t, [btnCopiarControles, btnPegarControles])
	
	// panel formato
	var t=this.creaTabla(divFormato)
	this.creaFila2celdas(t, [selectNombreFuente, selectTamFuente, selectEstFuente])
	this.creaFila2celdas(t, [selectEstilos, btnMantenimientoEstilos])
	this.creaFila2celdas(t, [creaT('Colores')], [colorFondo, txtcolorFondo, colorFrente, txtcolorFrente], true)
	this.creaFila2celdas(t, [creaT('Estilo')], [btnCopiarEstilo, btnPegarEstilo], true)
	
	// panel tamaño y posición
	var t=this.creaTabla(divTamanho)
	this.creaFila2celdas(t, [creaT('Mover')], [btnMoverIzq, btnMoverDer, btnMoverArriba, btnMoverAbajo], true)
	this.creaFila2celdas(t, [creaT('Juntar')], [btnJuntarIzq, btnJuntarDer, btnJuntarArriba, btnJuntarAbajo], true)
	this.creaFila2celdas(t, [creaT('Alinear')],[btnIgualarIzq, btnIgualarDer, btnIgualarArriba, btnIgualarAbajo], true)
	this.creaFila2celdas(t, [creaT('Repartir sep')],[btnRepartirAncho,btnRepartirAlto, btnRepartirAnchoN, btnRepartirAltoN], true)
	
	this.creaFila2celdas(t, [creaT('Tamaño')],[btnCrecer1pxIzq, btnCrecer1pxDer, btnCrecer1pxArriba, btnCrecer1pxAbajo], true)
	this.creaFila2celdas(t, [creaT('Mismo tamaño')],[btnIgualarAncho,btnIgualarAlto ], true)
	this.creaFila2celdas(t, [creaT('Hacer crecer')], [btnCrecerIzq, btnCrecerDer], true)
	
	// panel coordenadas
	this.meteElementos(divCoordenadas, [
		creaObjProp('img', {'className':'botonera', 'title':'Coordenadas', alt:'x, y', src:'./fijo/disenhador.coordenadas.png'}),
		creaObjProp('input', {'type':'text', 'id':'coordenadaX', readonly:true}),
		creaObjProp('input', {'type':'text', 'id':'coordenadaY', readonly:true}),
		
		creaObjProp('img', {'className':'botonera', 'title':'Dimensiones (alto x ancho)', alt:'alto x ancho', src:'./fijo/disenhador.dimensiones.png'}),
		creaObjProp('input', {'type':'text', 'id':'dimensionX', readonly:true}),
		creaObjProp('input', {'type':'text', 'id':'dimensionY', readonly:true})
	])
	
	divSelector	=this.creaH3yDIV('divSelectorColumnas', 'Lista de tablas y campos', dis)
	divSelector.id='SelectorColumnas'
	ponEstilo(divSelector, 'arb')
		
	this.cargaEXP_Estilos()
}
Disenhador.prototype.creaFila2celdas=function (tbody, contCelda1, contCelda2, izq){
	var tr=creaObjProp('tr', {'className':'disenhador'})
	
	var td1=creaObj('td')
	this.meteElementos(td1, contCelda1)
	tr.appendChild(td1)
	if (izq) td1.className='r'
	
	if (contCelda2) {
		var td2=creaObj('td')
		this.meteElementos(td2, contCelda2)
		tr.appendChild(td2)
		}
	else 
		td1.colSpan=2
		
	
	tbody.appendChild(tr)
}
Disenhador.prototype.meteElementos=function(cont, botones){
	for (var i=0; i<botones.length; i++)
		cont.appendChild( botones[i] )
}
Disenhador.prototype.creaH3yDIV=function(idDiv, ds, cont){
	var h3=creaObjProp('div', {className:'header', 'texto':ds, title:'[Click para mostrar u ocultar el panel "'+ds+'"]', onclick:function(){alternaVisible(idDiv)}}) 
	var div=creaObjProp('div', {className:'panelAcordeon'})
	
	var article=creaObjProp('div', {id:idDiv, className:'article acordeon'})
	
	cont.appendChild(article)
	
	article.appendChild(h3)
	article.appendChild(div)
	
	var disp=recuperaDato('disenhador.panel.'+idDiv, 'visible')
	if (disp==false) 
		alternaVisible(idDiv, false)
	
	return div
}
Disenhador.prototype.creaTabla=function(cont){
	var tbody=creaObj('tbody')
	var t=creaObj('table')
	
	cont.appendChild(t)
	t.appendChild(tbody)
	return tbody
}
/////////////////////////////////////////////////////////////////////////////////////////////
Disenhador.prototype.puntoEstaIncluido=function(puntoX, puntoY, x0, y0, x, y) {
	return (puntoX>x0 && puntoX<x && puntoY>y0 && puntoY<y)
}
Disenhador.prototype.quitaTiradores=function(){
	if (this.md==null)
		return 
	this.md.quitaTiradores()
}
Disenhador.prototype.ponTiradores=function(){
	var esPrimero=true
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		if (typeof ctl == 'function') continue
		if (ctl && !ctl.borrado) {
			ctl.ponTiradores(esPrimero)
			esPrimero=false
			}
		}
}
Disenhador.prototype.refrescaTiradores=function(){
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		
		if (typeof ctl == 'function') continue
		if (ctl) ctl.refrescaTiradores()
		}
}
Disenhador.prototype.actualizaSeleccion=function(xx0, yy0, xx, yy, ctrlKey){
	//buscamos sólo en el contenedor del primer punto donde se inicie el cuadro
	var contInicial=null
	for (var i in this.md.listaControles) { // OJO no usar .length, que no tiene en cuenta los ctl nuevos
		var cctl=this.md.listaControles[i]
		if (typeof cctl == 'function') continue
		if (cctl.estaVisible() && cctl.esContenedor) {	
			var dim=cctl.dimensiones()
			
			var left=dim.left
			var top=dim.top
			var right=dim.left+dim.width
			var bottom=dim.top+dim.height
			
			if (this.puntoEstaIncluido(xx0, yy0, left, top, right, bottom))
				contInicial=cctl
			}
		}
	
	var x0=Math.min(xx0, xx)
	var x=Math.max(xx0, xx)
	
	var y0=Math.min(yy0, yy)
	var y=Math.max(yy0, yy)
	
	this.hayAlguno=false	
	var lista=new Array()
	lista=this.recorreControles(this.md.listaControles, lista, contInicial, x0, y0, x, y)
	if (!this.hayAlguno && contInicial!=null) 
		this.anhadeOQuita(true, contInicial.id, lista)
	
	// y lo pasamos a la lista definitiva
	if (!ctrlKey)  this.vaciaSeleccion()
	//si el rubberband ocupa menos de 10x10 entiendo que ha intentado pinchar en un control, así que nos quedamos con el más interno de los que tenemos
	var lim=10
	if ( (x-x0)<lim && (y-y0)<lim ) {
		var interno=this.cogeElMasInterno(lista)
		if (interno) this.anhadeOQuita(ctrlKey, interno.id, this.listaSeleccionados)
		}
	else {
		for (var i =0 ; i<lista.length; i++) {
			var ctl=this.md.getControl( lista[i] )
			if (ctl && typeof ctl != 'function') {
				if (ctl) this.anhadeOQuita(ctrlKey, ctl.id, this.listaSeleccionados)
				}
			}
		}
	
	this.preparaSeleccion()	
	this.ponTiradores()
}
Disenhador.prototype.recorreControles=function(listaControles, listaRet, contInicial, x0, y0, x, y){
	var ctl, cctl
	for (var idx in listaControles) {
		if (listaControles instanceof Array){
			cctl=listaControles[idx]
			ctl=control(cctl.id)
			}
		else {
			ctl=control(idx)
			cctl=listaControles[idx]
			}
		
		//vuelvo a poner esto	
		var listaRet_RD=[]
		if (cctl.tc=='rd'){
			listaRet_RD=this.recorreControles(cctl.listaControles, listaRet, contInicial, x0, y0, x, y)
			}
		if (listaRet_RD.length>0){
			listaRet=listaRet_RD
			}
		else
		//fin
		if ( (contInicial==null && cctl.cont==0) ||
		     (contInicial != null && (cctl.cont==contInicial.numControl )) ){
			
			var left, top, right, bottom
			if (!cctl.esContenedor) {
				left=offset('l',ctl)
				top=offset('t',ctl)
				right=left+getWidth(ctl)
				bottom=top+getHeight(ctl)
				}
			else {
				var panelDelContenedor=cctl.getPanelContenedor()
				
				left=offset('l',panelDelContenedor)
				top=offset('t',panelDelContenedor)
				right=left+getWidth(panelDelContenedor)
				bottom=top+getHeight(panelDelContenedor)
				}
			
			//si las esquinas del control están dentro del rubberband 
			var entra=this.puntoEstaIncluido(left, 	top,		x0, y0, x, y) ||
					this.puntoEstaIncluido(left, 	bottom, 	x0, y0, x, y) ||
					this.puntoEstaIncluido(right, 	top,		x0, y0, x, y) ||
					this.puntoEstaIncluido(right, 	bottom,	x0, y0, x, y)
			//si las esquinas del rubberband están dentro del control
			var entraAlReves=this.puntoEstaIncluido(x0, 	y0,	left, top, right, bottom) ||
						this.puntoEstaIncluido(x, 	y0, 	left, top, right, bottom) ||
						this.puntoEstaIncluido(x0, 	y, 	left, top, right, bottom) ||
						this.puntoEstaIncluido(x, 	y,	left, top, right, bottom)
			if (entra || entraAlReves  ) {
				this.hayAlguno=true
				this.anhadeOQuita(true, cctl.id, listaRet)
				}
			}
		}
	return listaRet
	}
Disenhador.prototype.cogeElMasInterno=function(lista){
	var minH=10000
	var ret
	for (var i =0 ; i<lista.length; i++)  {	
		var ctl=this.md.getControl( lista[i] )
		if (ctl && ctl.ancho<minH ) {
			ret=ctl
			minH=Math.min(minH, ctl.ancho)
			}
		}
	return ret
}
Disenhador.prototype.anhadeOQuita=function(ctrlKey, ctlid, lista){
	var anhadir=false
	if (!ctrlKey) 
		anhadir=true
	else if ( lista.contains(ctlid) ) 
		anhadir=false
	else	
		anhadir=true
	
	if (anhadir) {
		lista.push(ctlid)
		}
	else  {
		lista.splice(lista.indexOf(ctlid),1) 
		}
}
Disenhador.prototype.preparaSeleccion=function(){
	var numSeleccionados=0
	var unicoCtlSeleccionado=null
	
	var minX=100000
	var minY=100000
	var maxX=0
	var maxY=0
	
	this.controlIzq=null
	this.controlTope=null
	this.controlDer=null
	this.controlBottom=null
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		
		if (typeof ctl == 'function') continue
		if (ctl)  {
			var izq = 	ctl.izq 
			var der= 	ctl.izq +ctl.ancho
			var tope= 	ctl.tope
			var bottom= ctl.tope +ctl.alto
			
			if ( izq <minX ) {
				minX=izq
				this.controlIzq=ctl
				}
			if ( der>maxX ) {
				maxX=der
				this.controlDer=ctl
				}
			if ( tope<minY) {
				minY=tope
				this.controlTope=ctl
				}
			if ( bottom>maxY ) {
				maxY=bottom
				this.controlBottom=ctl
				}
				
			unicoCtlSeleccionado=ctl
			numSeleccionados++
			}
		}
		
	if (numSeleccionados==1) {
		this.muestraCoordenadas( unicoCtlSeleccionado.izq, unicoCtlSeleccionado.tope, unicoCtlSeleccionado.ancho, unicoCtlSeleccionado.alto )
		}
	else
		this.muestraCoordenadas( '-', '-', '-', '-' )
}
Disenhador.prototype.alternaControlEnSeleccion=function(id){
	var ctl=this.md.getControl(id)
	var cctl=control(id)
	
	if (id in this.listaSeleccionados ){
		this.listaSeleccionados.remove(cctl.id)
		ctl.quitaTiradores()
		}
	else	{
		this.listaSeleccionados.push(cctl.id)
		ctl.ponTiradores()
		}
	this.preparaSeleccion()
	return false
}
Disenhador.prototype.vaciaSeleccion=function(){
	this.quitaTiradores()
	this.listaSeleccionados=[]
}
////////////////////////////////////////////////////////////////////////////
Disenhador.prototype.suma=function(ctlInicio, prop){
	if (ctlInicio==0)
		return 0
	else {
		var ctl=this.md.getControl(ctlInicio)
		return ctl[prop]+ (ctl? this.suma( ctl.cont , prop) :0) 
		}
}
////////////////////////////////////////////////////////////////////////////
Disenhador.prototype.creaCopia=function(lista, paraRedimensionar){
	var copiaPadre=creaObjProp('div', {className:'moverControles'})
	
	var x, y, w, h
	for (var i=0; i<lista.length; i++) {
		var ctl=lista[ i ]

		if (ctl && typeof ctl != 'function') {
			var xctl=control(ctl)
			x=offset('l', xctl)
			y=offset('t', xctl )
			
			var $ctl=$(xctl)
			w=$ctl.innerWidth()
			h=$ctl.innerHeight()
			
			var copia=creaObjProp('div', {id:'fantasma'+ctl, 'style.left':x+'px', 'style.top':y+'px', 'style.width':w+'px', 'style.height':h+'px'})
			copiaPadre.appendChild( copia )
			
			if (paraRedimensionar) copia.style.cursor='se-resize'
			}
		}
	
	control('panelDer').appendChild(copiaPadre)
	return copiaPadre	
	}
Disenhador.prototype.enrejaFantasmas=function(paraDrag){
	if (disenhador.fantasmasEnrejados)
		return
	
	var x, y
	for (var i=0; i<disenhador.listaSeleccionados.length; i++) {
		var id=disenhador.listaSeleccionados[ i ]
		var cctl=disenhador.md.getControl(id)
		var ctl=control(id)
		
		if (id && typeof id != 'function') {
			var f=control('fantasma'+id)
			
			if (paraDrag){
				x=enReja(quitaPx(f.style.left))
				y=enReja(quitaPx(f.style.top))
				
				modObjProp(f, {'style.left':x, 'style.top':y})
				}
			else 	{
				w=enReja(quitaPx(f.style.width))
				h=enReja(quitaPx(f.style.height))
				
				modObjProp(f, {'style.width':w, 'style.height':h})
				}
			}
		}
	disenhador.fantasmasEnrejados=true
	}
Disenhador.prototype.creaRubberBand=function(event) {
	this.rb.style.display='none'
	try {
		if (tieneEstilo(event.currentTarget, 'gripNivel'))
			return false
		else if (tieneEstilo((event.originalTarget || event.srcElement).parentNode, 'alternar') || tieneEstilo((event.originalTarget || event.srcElement), 'alternar'))
			return false
		else if (tieneEstilo(event.currentTarget, 'alternar')) /*el botón de "diseñar este panel" */
			return false
		
		}
	catch (e) 
		{/*pass*/}
		
	var evt = new Evt(event)
	Evt.removeEventListener(document, "mousemove", resizeMove, false)
	Evt.removeEventListener(document, "mouseup", resizeRelease, false)
	
	Evt.removeEventListener(document, "mousemove", dragMove, false)
	Evt.removeEventListener(document, "mouseup", dragRelease, false)
	
	var target=$(event.target).parents('td.cambiaPestanha')[0]
	if (target) {
		target.onclick() //activamos la pestaña
		return 
		}

	if (this.modoDisenho)  {
		if (esPanelIzq(event)) {
			//pass
			}
		else if ( event.button==0 && !this.moviendoControles && !this.dimensionandoControles) {	
			var coords=getCoordenadasRaton(event)
			var x = coords.left
			var y = coords.top
			var panelDer=control('panelDer')
			if (x>panelDer.clientWidth)
				return
			
			this.rbx0=x
			this.rby0=y
			this.pintandoCuadro=true
			
			mueve( this.rb, x, y, 0, 0)
			this.rb.style.display=''		
			}
		}
}
function esPanelIzq(event){
	var obj=event.srcElement || event.currentTarget
	if (obj.id=='panelDer' ) //Moz
		return false
	
	var pd=$(obj).parents('div.panelDer')//Webkit
	if (pd.length)
		return false
	return false
}
Disenhador.prototype.mueveRubberBand=function(event) {
	if (!this.modoDisenho) 
		return
		
	if (this.pintandoCuadro && event.button==0) {
		var coords=getCoordenadasRaton(event)
		var x = coords.left
		var y = coords.top
					
		var minX, difX, minY, difY
		minX=Math.min(this.rbx0, x); difX=Math.abs(x-this.rbx0)
		minY=Math.min(this.rby0, y); difY=Math.abs(y-this.rby0)
		mueve(this.rb, minX, minY, difX, difY)
		}
}
Disenhador.prototype.cancelaRubberBand=function(event) {
	if (!this.modoDisenho || !this.pintandoCuadro) 
		this.rb.style.display='none'
		
	if (this.pintandoCuadro && event != null && event.button==0 && !this.moviendoControles && !this.dimensionandoControles) {
		this.pintandoCuadro=false
		var coords=getCoordenadasRaton(event)
		var x = coords.left
		var y = coords.top
		this.actualizaSeleccion(this.rbx0, this.rby0, x, y, event.ctrlKey)
		}
		
	try 	{
		this.rb.style.display='none'
		}
	catch (e)
		{}
	}
Disenhador.prototype.muestraCoordenadas=function(x, y, ancho, alto){
	if (x != null) control('coordenadaX').value=x
	if (y != null) control('coordenadaY').value=y
	if (ancho != null) control('dimensionX').value=ancho
	if (alto != null) control('dimensionY').value=alto
}
////////////////////////////////////////////////////////////////////////////
Disenhador.prototype.sacaPrimerControl=function(){
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		
		if (typeof ctl != 'function') 
			return ctl
		}
}
Disenhador.prototype.igualar=function(tipo){
	var ctlPrimero=this.sacaPrimerControl()
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		
		if (typeof ctl == 'function' || ctl==null) 
			continue
		if (ctl !== ctlPrimero)  {
			var cctl=control(ctl.id)
			if (tipo=='w') {
				ctl.ancho= ctlPrimero.ancho
				ctl.refresca()
				}
			else if (tipo=='h') { //altura
				ctl.alto= ctlPrimero.alto
				ctl.refresca()
				}
				
			else if (tipo=='l') { //izq
				ctl.izq= ctlPrimero.izq
				ctl.refresca()
				}
			else if (tipo=='r') {
				ctl.izq= ctlPrimero.izq+ctlPrimero.ancho-ctl.ancho
				ctl.refresca()
				}
			else if (tipo=='u') {
				var desfase=this.sacaDesfase(ctlPrimero)
				ctl.tope= ctlPrimero.tope
				ctl.refresca()
				}
			else if (tipo=='d') {
				var desfase=this.sacaDesfase(ctlPrimero)
				ctl.tope= ctlPrimero.tope+desfase+ctlPrimero.alto-ctl.alto
				ctl.refresca()
				}
			ctl.modificado=true
			}
		}
	this.refrescaTiradores()
}
Disenhador.prototype.sacaDesfase=function(controlContenido){
	if (controlContenido.cont != 0)
		return this.md.getControl(this.controlTope.cont).desfaseY
	else
		return 0
}
Disenhador.prototype.mover1px=function(tipo){
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		if (typeof ctl == 'function') break
		if (ctl)  {
			var cctl=control(ctl.id)
			if (tipo=='l') { //izq
				ctl.izq--
				ctl.refresca()
				}
			else if (tipo=='r') {
				ctl.izq++
				ctl.refresca()
				}
			else if (tipo=='u') {
				ctl.tope--
				ctl.refresca()
				}
			else if (tipo=='d') {
				ctl.tope++
				ctl.refresca()
				}
			ctl.modificado=true
			}
		}
	var ctl=this.controlIzq
	this.muestraCoordenadas(ctl.izq, ctl.tope, ctl.ancho, ctl.alto)
	this.refrescaTiradores()
}
Disenhador.prototype.crecer1px=function(tipo){
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		if (typeof ctl == 'function') break
		if (ctl)  {
			var cctl=control(ctl.id)
			if (tipo=='l') { //izq
				ctl.ancho--
				ctl.refresca()
				}
			else if (tipo=='r') {
				ctl.ancho++
				ctl.refresca()
				}
			else if (tipo=='u') {
				ctl.alto--
				ctl.refresca()
				}
			else if (tipo=='d') {
				ctl.alto++
				ctl.refresca()
				}
			ctl.modificado=true
			}
		}
	var ctl=this.controlIzq
	this.muestraCoordenadas(ctl.izq, ctl.tope, ctl.ancho, ctl.alto)
	this.refrescaTiradores()
}
Disenhador.prototype.juntar=function(tipo){
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		if (typeof ctl == 'function') break
		if (ctl)  {
			var cctl=control(ctl.id)
			if (tipo=='l') { //izq
				if (ctl.id != this.controlIzq.id) {				
					ctl.izq= this.controlIzq.izq+this.controlIzq.ancho
					ctl.refresca()
					}
				}
			else if (tipo=='r') {
				if (ctl.id != this.controlDer.id) {			
					ctl.izq= this.controlDer.izq-ctl.ancho
					ctl.refresca()
					}
				}
			else if (tipo=='u') {
				if (ctl.id != this.controlTope.id) {
					ctl.tope= this.controlTope.tope+this.controlTope.alto
					ctl.refresca()
					}
				}
			else if (tipo=='d') {
				if (ctl.id != this.controlBottom.id) {
					ctl.tope= this.controlBottom.tope-ctl.alto
					ctl.refresca()
					}
				}
			ctl.modificado=true
			}
		}
	this.refrescaTiradores()
}
Disenhador.prototype.crecer=function(tipo){
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		if (typeof ctl == 'function') break
		if (ctl)  {
			var cctl=control(ctl.id)
			if (tipo=='l') { //izq
				if (ctl.id != this.controlIzq.id) {				
					ctl.ancho=ctl.ancho+ctl.izq-this.controlIzq.izq
					ctl.izq= this.controlIzq.izq
					ctl.refresca()
					}
				}
			else if (tipo=='r') {
				if (ctl.id != this.controlDer.id) {			
					ctl.ancho= this.controlDer.izq+this.controlDer.ancho-ctl.izq
					ctl.refresca()
					}
				}
			ctl.modificado=true
			}
		}
	this.refrescaTiradores()
}
function sortTope(a, b){
	return a.tope-b.tope
}
function sortIzq(a, b){
	return a.izq-b.izq
}
Disenhador.prototype.repartir=function(tipo){
	var totalA=0
	
	var xlistaSeleccionados=[]
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		if (typeof ctl == 'function') break
		if (ctl)  xlistaSeleccionados.push(ctl)
		}
		
	if (tipo=='h')
		xlistaSeleccionados=xlistaSeleccionados.sort(sortTope)
	else
		xlistaSeleccionados=xlistaSeleccionados.sort(sortIzq)
		
	for (var i=1; i<xlistaSeleccionados.length-1; i++) {
		var ctl=xlistaSeleccionados[ i ]
		if (typeof ctl == 'function') break
		if (ctl)  {
			if (tipo=='h') 
				totalA+=ctl.alto
			else 
				totalA+=ctl.ancho
			}
		}
		
	var ctl0=xlistaSeleccionados[0], ctlUltimo=xlistaSeleccionados[xlistaSeleccionados.length-1]	
	var sep
	if (tipo=='h')
		sep=(ctlUltimo.tope-(ctl0.tope+ctl0.alto)-totalA)/(1+xlistaSeleccionados.length-2)
	else
		sep=(ctlUltimo.izq-(ctl0.izq+ctl0.ancho)-totalA)/(1+xlistaSeleccionados.length-2)
	
	for (var i=1; i<xlistaSeleccionados.length-1; i++) {
		var ctl=xlistaSeleccionados[ i ]
		if (typeof ctl == 'function') break
		if (ctl)  {
			var anterior=xlistaSeleccionados[i-1]
			
			if (tipo=='w') 
				ctl.izq=anterior.izq+anterior.ancho+sep
			else if (tipo=='h')  
				ctl.tope=anterior.tope+anterior.alto+sep
			
			
			ctl.refresca()
			ctl.modificado=true
			}
		}
	this.refrescaTiradores()
}
Disenhador.prototype.repartirN=function(tipo){
	var xlistaSeleccionados=[]
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		if (typeof ctl == 'function') break
		if (ctl)  xlistaSeleccionados.push(ctl)
		}
		
	if (tipo=='h')
		xlistaSeleccionados=xlistaSeleccionados.sort(sortTope)
	else
		xlistaSeleccionados=xlistaSeleccionados.sort(sortIzq)

	var ctl0=xlistaSeleccionados[0], ctlUltimo=xlistaSeleccionados[xlistaSeleccionados.length-1]	
	var sep=Number(prompt('Separación entre controles (píxeles)', 6))
	
	if ( isNaN(sep) )
		return
	
	for (var i=1; i<xlistaSeleccionados.length; i++) {
		var ctl=xlistaSeleccionados[ i ]
		if (typeof ctl == 'function') break
		if (ctl)  {
			var anterior=xlistaSeleccionados[i-1]
			
			if (tipo=='w') 
				ctl.izq=anterior.izq+anterior.ancho+sep
			else if (tipo=='h')  
				ctl.tope=anterior.tope+anterior.alto+sep
			
			ctl.refresca()
			ctl.modificado=true
			}
		}
	this.refrescaTiradores()
}
Disenhador.prototype.ponColorFondo=function(color){
	this._ponColor(color, true)
}
Disenhador.prototype.ponColorFrente=function(color){
	this._ponColor(color, false)
}
Disenhador.prototype._ponColor=function(color, fondo){
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		if (typeof ctl == 'function') break
		if (ctl)  {
			if (fondo) 
				ctl.colorFondo=color
			else	
				ctl.colorFrente=color
			ctl.refresca()
			ctl.modificado=true
			}
		}
	anhadeACartaColores(color)
}
Disenhador.prototype.ponTipoLetra=function(nombre){
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		if (typeof ctl == 'function') break
		if (ctl){
			ctl.nomLetra=nombre
			ctl.refresca()
			ctl.modificado=true
			}
		}
}
Disenhador.prototype.ponTamLetra=function(tam){
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		if (typeof ctl == 'function') break
		if (ctl)  {
			ctl.tamLetra=tam
			ctl.refresca()
			ctl.modificado=true
			}
		}
}
Disenhador.prototype.ponEstLetra=function(estilo){
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		if (typeof ctl == 'function') break
		if (ctl)  {
			if (estilo=='normal')
				ctl.estLetra=''
			else
				ctl.estLetra=estilo
			ctl.refresca()
			ctl.modificado=true
			}
		}
}
Disenhador.prototype.ponClassName=function(clase){
	if (clase==null) return
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		if (typeof ctl == 'function') break
		if (ctl)  {
			if (clase=='-') 
				clase=null
				
			ctl.claseCSS=clase
			ctl.refresca()
			ctl.modificado=true
			}
		}
}
Disenhador.prototype.copiarEstilo=function () {
var ctl=this.controlIzq
if (ctl != null) {
	control('nombreFuente').value=ctl.nomLetra
	control('tamFuente').value = toInt(ctl.tamLetra)
	control("estFuente").value = ctl.estLetra==''? 'normal':ctl.estLetra
	control("estiloFuente").value = ctl.claseCSS?ctl.claseCSS:'-'
	
	muestraEnSelector('colorFondo', ctl.colorFondo )
	muestraEnSelector('colorFrente', ctl.colorFrente )
	}
}
Disenhador.prototype.pegarEstilo=function () {
	disenhador.ponColorFondo( control('colorFondo').style.backgroundColor)
	disenhador.ponColorFrente(control('colorFrente').style.backgroundColor)
	
	disenhador.ponTipoLetra(control('nombreFuente').value)
	disenhador.ponTamLetra(control('tamFuente').value)
	
	
	var estLetra=control('estFuente').value
	if (estLetra=='')
		estLetra='normal'
	disenhador.ponEstLetra(estLetra)
	
	disenhador.ponClassName(control('estiloFuente').value)
}
/////////////////////////////////////
Disenhador.prototype.copiarControles=function(){
	this.listaControlesCopiados=new Array()
	
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		if (typeof ctl == 'function') break
		if (ctl) 
			this.listaControlesCopiados.push(ctl)
		}
}
Disenhador.prototype.pegarControles=function(){
	this.vaciaSeleccion()
	for (var i in this.listaControlesCopiados) {
		var ctl=this.listaControlesCopiados[i]
		if (typeof ctl == 'function') break
		this.pegar1control(ctl)
		}
}
Disenhador.prototype.pegar1control=function(original) {
	var divContenedor=control(this.md.idx)
	
	var tc=original.tc
	
	var c=[]
	var dd=new Date()
	c[listaProps.numControl]='_nuevo_'+dd.getTime()

	c[listaProps.ml]=''
	c[listaProps.md]=this.md.cd

	c[listaProps.tc]=			tc

	c[listaProps.izq]=			10
	c[listaProps.tope]=		10
	c[listaProps.ancho]=		10
	c[listaProps.alto]=		10

	c[listaProps.caption]=		original.caption

	c[listaProps.cont]=''
	c[listaProps.pestanha]=''

	c[listaProps.nomLetra]=	original.nomLetra
	c[listaProps.tamLetra]=	original.tamLetra
	c[listaProps.estLetra]=	original.estLetra

	c[listaProps.color]=original.color

	c[listaProps.borde]=original.borde

	c[listaProps.align]=original.align
	c[listaProps.claseCSS]=original.claseCSS && original.claseCSS!=''?original.claseCSS:null
	
	var ctl=new Control[tc](c, this.md)
	this.md.anhadeControl(ctl)
	
	ctl.colorFondo=original.colorFondo
	ctl.colorFrente=original.colorFrente
	
	ctl.izq=	original.izq
	ctl.tope=	original.tope
	ctl.ancho=	original.ancho
	ctl.alto=	original.alto
	divContenedor.appendChild ( ctl.toDOM() )
	
	this.alternaControlEnSeleccion(ctl.id)
	
	ctl.nuevo=true; ctl.modificado=true
	if (tc=='txt') 
		ctl.rellena2( this.md.nodoActivo )
	else
		ctl.rellena()
	ctl.refresca()
}
/////////////////////////////////////
Disenhador.prototype.disenharModoLista=function(){ //muestra en el panel derecho los controles del modo lista pa que lo diseñes ahí
	this.vaciaSeleccion()
	
	this.panelesInvertidos=!this.panelesInvertidos //invertimos
	
	var padre
	if (this.panelesInvertidos) {
		this.ml=exp.getMD(exp.idMdActivo)
		this.md=exp.getMD(exp.idMlActivo)
		
		//oculto el panel derecho
		control(this.ml.idx).style.display='none'
		
		padre='panelDer'
		var divML=control(this.md.idx)
		divML.parentNode.removeChild( divML )
		control( padre ).appendChild( divML )
		
		}
	else	{
		this.md= exp.getMD(exp.idMdActivo)
		this.ml=exp.getMD(exp.idMlActivo)
		
		padre='listaPanelesIzq'
		var divML=control(this.ml.idx)
		divML.parentNode.removeChild( divML )
		control( padre ).appendChild( divML )
		
		exp.cambioNodoActivo(null, exp.idMdActivo) //lo dejamos todo como al principio
		}
}
Disenhador.prototype.borrarControl=function(){
	for (var i =0; i<this.listaSeleccionados.length; i++) {
		var ctl=this.md.getControl( this.listaSeleccionados[i] )
		if (typeof ctl == 'function') break
		if (ctl) {
			ctl.borrado=true
			ctl.modificado=true
			
			var cctl=control(ctl.id)
			cctl.style.opacity='0.3'
			cctl.title='El objeto se borrará al guardar el detalle'
			}
		}
	this.quitaTiradores()
}
Disenhador.prototype.nuevoControl=function(caption) {
	var tc=(caption==null? control("tipoControl").value : 'txt') //viene del selector de columnas
	
	var propsPorDefecto=Control[tc].prototype.propsPorDefecto
	caption=caption || propsPorDefecto.caption
		
	var divContenedor=control(this.md.idx)
	
	var c=[]
	c[listaProps.numControl]='_nuevo_'+new Date().getTime()

	//~ c[listaProps.ml]=null
	c[listaProps.md]=this.md.id

	c[listaProps.tc]=tc

	c[listaProps.izq]=propsPorDefecto.izq
	c[listaProps.tope]=Math.min(this.md.maxTope(), $('#panelDer').innerHeight() -50)*15+60
	c[listaProps.ancho]=propsPorDefecto.ancho
	c[listaProps.alto]=propsPorDefecto.alto

	c[listaProps.caption]=caption

	c[listaProps.cont]=''
	c[listaProps.pestanha]=''

	c[listaProps.nomLetra]=''
	c[listaProps.tamLetra]='10.0000'
	c[listaProps.estLetra]=''

	c[listaProps.color]='-2147483640 16744448'

	c[listaProps.borde]='0'

	c[listaProps.align]='right'
	c[listaProps.claseCSS]=null
	
	var ctl=new Control[tc](c, this.md)
	this.md.anhadeControl(ctl)
	ctl.align='left'
	divContenedor.appendChild ( ctl.toDOM() )
	
	this.vaciaSeleccion()
	this.alternaControlEnSeleccion(ctl.id)
	
	this.pegarEstilo()
	ctl.nuevo=true
	
	if (tc=='txt') 
		ctl.rellena2( this.md.nodoActivo )
	else if (Control.prototype.controlesConPestanhas.contains(ctl.tc)) {
		//~ ctl.rellena( {pestActiva:0,visibles:'*'})
		}
	else
		ctl.rellena()
}
Disenhador.prototype.guardar=function() {
	var lista=''
	
	disenhador.idxBloqueo=ponNuevoBloqueo(true, 'guardando...')
	for (var i in this.md.dicControles) {
		var ctl=this.md.getControl( i )
		if (ctl && ctl.modificado)  
			lista+= ctl.JSON()+','
		}
	
	var idMd=this.md.id
	if ( lista != '' || this.md.modificado)  {
		lista='['+lista.substring(0, lista.length-1)+']'
		loadJSONDocPost ( 'json', { 	'accion':'guardarMD', 
							'md':idMd, 
							'menu':this.md.menuMantenimiento?1:0, 
							'tb':this.md.tb, 
							'nodo':this.md.nodoActivo,
							'letras':this.md.letras, 
							'aplicacion': exp.aplicacion, 
							'datos':lista},
				function(req) {
						var respuesta=xeval(req)
						if (respuesta.tipo!='error') {
							var panelesEstabanInvertidos=disenhador.panelesInvertidos
							if (panelesEstabanInvertidos)
								disenhador.panelesInvertidos=false//lo ponemos todo del derecho
							
							//borramos el md
							var md=exp.getMD(idMd)
							md.limpiaRestos()
							delete exp.listaModosDetalle[ md.id.toLowerCase() ]
							
							if (panelesEstabanInvertidos)
								exp.cambiaML(idMd)
							else		
								exp.arbol.cargar( exp.idMlActivo, 0)
							}
						quitaBloqueo(disenhador.idxBloqueo)
						}, 
				function(){
						alert('Error guardando el modo detalle "'+idMd+'"')
						quitaBloqueo(disenhador.idxBloqueo)
						}
					)
		}
	else
		quitaBloqueo(disenhador.idxBloqueo)
}
Disenhador.prototype.cambiaHayQueGuardar=function(){
	if (this.modoDisenho && this.hayQueGuardar) {
		ponEstilo(this.md.nombreMD, 'guardar')
		this.md.nombreMD.title='El detalle ha sido modificado y ha de ser guardado'
		}
}
Disenhador.prototype.hayQueGuardar=function(){
	for (var i in this.md.dicControles) {
		var ctl=this.md.getControl( i )
		if (ctl && ctl.modificado)  
			return true
		}
	if (this.md.modificado)
		return true
		
	return false
}
Disenhador.prototype.copiarDetalle=function(){
	//preguntamos cual es el detalle que se desea copiar
	var datosFRM={"numTramites" : 0,"camino" : 0,"tramite" : 'Crear detalle basado en otro',"tipo" : 'frm',"titulo" : 'Crear detalle basado en otro',"alto" : 100,'controles':[
				{"tipo" : 'bsc',"id" : 'exp_mododetalle.cd_mododetalle',"obligatorio" : true,"ds" : 'Detalle',"colsQueBloquea" : 0,"colsBloqueadas" : 0,bloqueado : false,'controles':[
					{"tipo": 'lvw',"id" : 'cd_mododetalle',"maxlength" : -30,"valor" : '',"obligatorio" : false,bloqueado : false,"ds" : ''},
					]}]}
	var xfrm=new Formulario( datosFRM, ponNuevoBloqueo() )
	xfrm.onAceptar=function(){
		//var xfrm=getFrmActivo()
		var ret = xfrm.recogeValoresFRM()
		
		var detalle=ret['cd_mododetalle']
		xfrm.ocultaFormulario()
		
		disenhador._copiarDetalle(detalle, disenhador.md.id)
		}
	xfrm.toDOM()
}
Disenhador.prototype._copiarDetalle=function(original, nombreCopia){
	loadJSONDoc ( 'json', { 'accion':'md', 'md':original, aplicacion: exp.aplicacion},
			function(req) {
					var md=exp.listaModosDetalle[nombreCopia.toLowerCase()]
					var datos=xeval( req )
					
					var filas=datos.filas
					
					var idNumControl=datos.columnas.indexOfIgnoreCase('numControl') , idContenedor=datos.columnas.indexOfIgnoreCase('contenedor')
					
					var id='_nuevo_'
					for (var i=0; i<filas.length; i++) {
						var fila=filas[i]
						
						fila['modificado']=true
						fila['nuevo']=true
						//cambiamos el id
						fila[idNumControl]=id+'_'+fila[idNumControl]
						if (fila[idContenedor]!='')
							fila[idContenedor]=id+'_'+fila[idContenedor]
						
						md.filas.push( filas[i] )
						}
						
					md.cargarControles(md.div)
					})
	}
///////////////////
Disenhador.prototype.cargaEXP_Estilos=function(){
	cargarLista(control('estiloFuente'), 'estilosControl', 'cd_estilo', 'ds_estilo', true, true, true)
}
Disenhador.prototype.lanzaMantenimientoEstilos=function(){
	var lista=this.estilosControl
	
	var datosFRM={"numTramites" : 0,"camino" : 0,"tramite" : 'Clases incluidas en la tabla "EXP_Estilos"',"tipo" : 'frm',"titulo" : 'Clases incluidas en la tabla "EXP_Estilos"',"alto" : 375,'controles':[]}
	datosFRM.controles.push( {'tipo' : 'etiqueta','id' : 'sinnombre92z82','maxlength' : 0,'valor' : '','obligatorio': false,'bloqueado' : false,'validacion' : '','ds' : 'Clases a eliminar'})
	
	var posCD=lista.columnas.indexOfIgnoreCase('CD_Estilo')
	for (var i=0; i<lista.filas.length; i++){
		var cd=lista.filas[i][posCD]
		if (cd != '-')//el guión es aparte, lo reservamos para controles sin clase especializada
			datosFRM.controles.push( {"tipo":'lvwBoolean',"id": cd, "maxlength" : -25, "valor" : false,"obligatorio" : true, bloqueado : false,"ds" : cd} )
		}
	datosFRM.controles.push( {'tipo' : 'etiqueta','id' : 'sinnombre9211z82x','maxlength' : 0,'valor' : '','obligatorio': false,'bloqueado' : false,'validacion' : '','ds' : 'Clases a crear'})	
	
	datosFRM.controles.push( {"tipo":'lvw',"id": '_nueva_'+0, "maxlength" : -25, "valor" : '',"obligatorio" : false, bloqueado : false,"ds" : 'Nueva clase '+1, globoAyuda:' * Únicamente caracteres alfanuméricos y _ (ni espacios ni caracteres extraños).\n * Debe estar coordinado con la hoja de estilos secundaria de la aplicación.'} )
	for (var i=1; i<10; i++){
		datosFRM.controles.push( {"tipo":'lvw',"id": '_nueva_'+i, "maxlength" : -25, "valor" : '',"obligatorio" : false, bloqueado : false,"ds" : 'Nueva clase '+(i+1)} )
		}
		
	this.idxBloqueo=ponNuevoBloqueo()
	var xfrm=new Formulario( datosFRM, this.idxBloqueo )
	xfrm.onAceptar=function(){
		//var xfrm=getFrmActivo()
		var param = xfrm.recogeValoresFRM()
		
		var eliminar=[], crear=[]
		
		for (var nombre in param){
			var valor=param[nombre]
			
			if (nombre.startsWith('_nueva_') && valor!='')
				crear.push(valor)
			else if (valor)
				eliminar.push(nombre)
			}
		
		var paramPeticion={ 'accion':'actualizarEXP_Estilos', aplicacion:exp.aplicacion, 'crear':crear.join(','), 'eliminar':eliminar.join(',')}
		loadJSONDocPost('json', paramPeticion, function(){
								xfrm.cierraFormulario()
								disenhador.cargaEXP_Estilos()
								})
		}
	xfrm.toDOM()
}
////////////////////////////////////////////////////////////////////////////
function alternaVisible(idPanel, forzar){
	var ctl=control(idPanel)
	if (ctl) {
		var abrir
		if (forzar==null)
			abrir=tieneEstilo(ctl, 'cerrado')? true: false
		else
			abrir=forzar
		
		if (abrir) 
			quitaEstilo(ctl, 'cerrado')	
		else
			ponEstilo(ctl, 'cerrado')
		 
		if (forzar==null)
			salvaDato('disenhador.panel.'+idPanel, 'visible', abrir)
		}
		
	var sc=control('SelectorColumnas')
	if (sc){
		var l=sc.previousSibling
		sc.style.top=l.offsetTop+l.offsetHeight+5+'px'
		}
}
function toInt ( x ) {
	return ( x > 0 ? Math.floor( x ) : Math.ceil ( x ) ) }
function activarDisenho(){
	var estado=!disenhador.modoDisenho
	loadJSONDoc( 'json',  {'accion':'disenho', aplicacion: exp.aplicacion, 'estado':estado}, 
		function() {
			var enlace=control('btnDisenhador')
			var ttp
			if (!estado) {
				ttp="[Diseño inactivo] Activar diseño"
				quitaEstilo(enlace, 'estado_on')
				}
			else  {
				ttp="[Diseño activo] Desactivar diseño"
				ponEstilo(enlace, 'estado_on')
				}
			enlace.title=ttp
			disenhador.cambiaModoDisenho(estado)
			},
		function ()
			{alert("Se ha producido un error al intentar activar la depuración")}
		)
	}
function getCoordenadasRaton(event){
	if (disenhador.md==null) return
	var panelMD=disenhador.md.div
	var offsetpanelMD=$(panelMD).offset()
	return {	'left': event.pageX-offsetpanelMD.left+panelMD.scrollLeft, 
			'top': event.pageY-offsetpanelMD.top+panelMD.scrollTop}
	}
function muestraOcultaIFRAMES(visibles){
	//ocultamos los iframes
	var lista=document.getElementsByTagName('iframe')
	for (var i=0; i<lista.length; i++){
		var iframe=lista[i]
		if (iframe && typeof iframe != 'function') {
			var div=iframe.parentNode
			if (!visibles) 
				ocultaWWW(iframe, div)
			else 
				muestraWWW(iframe, div)
			}
		}
	}
function muestraIFRAMES(){
	muestraOcultaIFRAMES(true)
	}
function ocultaIFRAMES(){
	muestraOcultaIFRAMES(false)
	}
///////////////////////////////////////////////
// Color Picker Script from Flooble.com: For more information, visit http://www.flooble.com/scripts/colorpicker.php
// Copyright 2003 Animus Pactum Consulting inc.
// You may use and distribute this code freely, as long as you keep this copyright notice and the link to flooble.com
// if you chose to remove them, you must link to the page listed above from every web page where you use the color picker code.
//---------------------------------------------------------
var divSet = false
var curId
var nocolor = 'none'
function setColor(color) {
	muestraEnSelector(curId, color)
	var picker = control('colorpicker')
	if (picker!=null)
		picker.style.display = 'none'
	
	if (curId=='colorFondo') 
		disenhador.ponColorFondo(color)
	else
		disenhador.ponColorFrente(color)
}
function muestraEnSelector(curId, color){
	var link = control(curId)
	var field = control('txt'+curId)
	field.value = color
	
	if (color == '') {
		link.style.background = nocolor
		link.style.color = nocolor
		} 
	else {
		link.style.background = color
		link.style.color = color
		}
	}
function setDiv() {     
	var ctl=control('colorpicker')
	if (ctl) ctl.parentNode.removeChild(ctl)
	
	var elemDiv = creaObjProp('div', {id:'colorpicker', className:'colorPicker'})	
	elemDiv.innerHTML = '<span >'+getColorTable(cartaColores, 'cartaColores')+getColorTable(cartaColoresApli, 'cartaColoresApli')+'</span>'

	document.body.appendChild(elemDiv)
	divSet = true
}
function anhadeACartaColores(colorFinal){
	if (!cartaColoresApli.contains(colorFinal))
		cartaColoresApli.push(colorFinal)
	divSet=false
}
function pickColor(id) {
	if (!divSet) 
		setDiv()
	var picker = control('colorpicker');     	
	if (id == curId && picker.style.display == 'block') {
		picker.style.display = 'none'
		return
		}
	curId = id
	var thelink = control(id)
	
	var pos=$(thelink).offset()
	picker.style.top = pos.top +14+'px'
	picker.style.left = pos.left - 45+'px'
	picker.style.display = 'block'
	}
function getColorTable(colors, id) {
	var perline=8
	var tableCode = ''
	tableCode += '<table id="'+id+'">'
	for (i = 0; i < colors.length; i++) {
		if (i % perline == 0) 
			tableCode += '<tr>'
		tableCode += '<td class="colorPicker" style="background: ' + colors[i] + '" title="'+colors[i]+'" onclick="javascript:setColor(\'' + colors[i] + '\');"> </td>'
		if (i % perline == perline - 1) 
			tableCode += '</tr>'
		}
	if (i % perline != 0) { tableCode += '</tr>' }
	tableCode += '</table>'
	return tableCode
}
function relateColor(id, color) {
	var link = control(id)
	if (color == '') {
		link.style.background = nocolor
		link.style.color = nocolor
		color = nocolor
		} 
	else {
		link.style.background = color
		link.style.color = color
		}
	    eval(control(id + 'field').title)
	}
//////////////////////////////////////////////////////////////////////////
var editorMenu=null
function editarMenu(){
	ocultaMenusPanSup()
	editorMenu=new FormularioEditorMenu( ponNuevoBloqueo())
	editorMenu.toDOM()
	}
function FormularioEditorMenu(idxBloqueo){
	Frm.call(this, 'frmMenu', idxBloqueo)
	
	this.altoBotonera=25
	this.altoFrm=450
	this.anchoFrm=800
	
	this.titulo='Menú de la aplicación'
	this.rutaIcono='./fijo/lved22.png'
	
	var cmdAceptar=creaObj('button', 'cmdgt', null, 'Aceptar', function(){editorMenu.acepta()})
	var cmdCancelar=creaObj('button', 'cmdgt', null, 'Cancelar', function(){ editorMenu.cancela() }) 
	
	this.botones=[cmdAceptar, cmdCancelar]
	
	this.dsDisposiciones={'v':'Vertical', 'h':'Horizontal'}
	this.dsTipos={0:'Sin panel 1', 1:'Árbol', 2:'Lista'}
	this.dsBotonera={'true':'Sí', 'false':'No'}
	
	this.grupoMenuActivo=null
}
FormularioEditorMenu.prototype=new Frm
FormularioEditorMenu.prototype.acepta=function(){
	var cambios=this.JSON()
	if (cambios!=null) {
		var params={	'accion':'guardarMenu', 
					'datos': cambios,
					'aplicacion':exp.aplicacion}
		loadJSONDocPost('json', params, function(req){
									siHayError(req)
									}
							)
		}
	this.ocultaFormulario()
	}
FormularioEditorMenu.prototype.JSON=function(){
	var ret=''
	for (var m in this.menu){
		var ml=this.menu[m]
		if (ml.esNuevo || ml.esModificado || ml.esBorrado) {
			var retml=''
			for (var c in ml){
				if (typeof ml[c]!='function')
					retml+=pareja(c , ml[c] )
				}
			ret+='{'+retml+'},'
			}
		}
	if (ret!='')
		return '['+ret+']'
	else 
		return null
}
FormularioEditorMenu.prototype.cancela=function(){
	this.ocultaFormulario()
}
FormularioEditorMenu.prototype.pintaCuerpo=function() {
	//botonera
	var btn=this.creaObj('nav', 'botonera',null, 'botoneraEditorMenu') //tipo, clase, hijo, id, texto, onClick
	
	var btnNuevo= creaObjProp('a', {className:'botonera texto imagen gris', hijo:creaSpan('Nuevo') , id:'btnRegistro', 			onclick:function(){editorMenu.nuevo();return false} })
	var btnModificar = creaObjProp('a', {className:'botonera texto imagen gris', hijo:creaSpan('Modificar') , id:'btnModificar', 	onclick:function(){editorMenu.modificar();return false}})
	var btnEliminar = creaObjProp('a', {className:'botonera texto imagen gris', hijo:creaSpan('Eliminar') , id:'btnEliminar', 		onclick:function(){editorMenu.eliminar();return false}})
	
	//~ var btnNuevoHijo = creaObjProp('a', {className:'botonera gris', hijo:creaSpan('Nuevo hijo') , id:'btnNuevoHijo', 		onclick:function(){editorMenu.nuevoHijo();return false}})
	
	//~ var literalListaGrupos=creaObjProp('span', {className:'botonera gris separador derecha', texto:'Grupo activo'})
		var btnListaGrupos = creaObjProp('select', {className:'botonera imagen gris derecha', id:'btnListaGrupos', 	onchange:function(){editorMenu.cambiaGrupo();return false}})
		this.btnListaGrupos=btnListaGrupos
	
	
	var botonera=this.creaBarraHerramientas([btnNuevo, btnModificar, btnEliminar, this.creaSeparadorBotonera(), btnListaGrupos])
	
	var ventana=creaObjProp('div', {hijo:botonera})
	
	var texto=this.creaObj('div', null, creaT('Atención: desde aquí tan sólo es posible editar el contenido de la tabla EXP_Menu. Recuerda que luego habrás de habilitar el acceso a estos menús en el nivel menuGeneral'))
	var aviso=this.creaObj('div','bombilla', texto)
	
	var id='arbolEditorMenu'
	var div0=creaObjProp('div', {className:'arb', id:id})
		
	this.arb=new ArbolModoDetalle('menu', 'gt.EXP_Menu')
	this.arb.contenedor=id
	
	ventana.appendChild(aviso)
	ventana.appendChild(div0)
	
	//~ ventana.style.height=this.altoFrm+'px'
	ventana.style.width=this.anchoFrm+'px'
	
	return ventana
}
FormularioEditorMenu.prototype.activate=function(){
	//~ this.arb.ponThrobber()
	this.arb.onClickNodo=function(cd){// k22\k21\k18\null
		editorMenu.cargarNodo(cd)
		}
	this.arb.onDblClickNodo=function(cd){
		editorMenu.cargarNodo(cd)
		editorMenu.editar()
		}
	this.refresca()
	}
FormularioEditorMenu.prototype.refresca=function(){
	if (this.grupoMenuActivo!=null){
		this.arb.nodoFicticio='t'+this.grupoMenuActivo+'\\k0\\b'+(this.menuActivo?this.menuActivo.cd_boton:'')
		this.arb.cargar(-1)
		}
	this.cacheaMenu()
	}
FormularioEditorMenu.prototype.cacheaMenu=function(){
	if (this.cacheado)
		return
	loadJSONDoc( 'json',  {'accion':'EXP_Menu', aplicacion: aplicacion},
		function(req) {
				editorMenu.cacheado=true
				editorMenu.cache=xeval(req)
				editorMenu.cargarListaGrupos()
				})
	}
FormularioEditorMenu.prototype.cargarListaGrupos=function(){
	this.listaGrupos=this.sacaOpciones('tipo')
	
	borraHijos(this.btnListaGrupos)
	for (var i=0; i<this.listaGrupos.length; i++){
		var el=this.listaGrupos[i]
		this.btnListaGrupos.appendChild( creaObjProp('option', {texto:el, id:el}))
		}
	if (this.grupoMenuActivo)
		this.btnListaGrupos.value=this.grupoMenuActivo
	this.cambiaGrupo()
}
FormularioEditorMenu.prototype.cambiaGrupo=function(){
	this.grupoMenuActivo=this.btnListaGrupos.value
	this.activate()
	}
FormularioEditorMenu.prototype.nodoPorID=function(cd){
	this.pos=this.pos ||rellenaPosxId(this.cache)
	
	var cd_boton=this.pos['cd_boton']
	for (var i=0; i<this.cache.filas.length; i++){
		var m=this.cache.filas[i]
		if ('k'+m[cd_boton]==cd)
			return m
		}
	return null
	}
FormularioEditorMenu.prototype.cargarNodo=function(cd){
	cd=cd.split('\\')[0]
	var menu=this.nodoPorID(cd)
	//generamos un diccionario
	var ret={}
	for (var k in this.pos){
		ret[k]=menu[this.pos[k]]
		}
	this.menuActivo=ret
	}
FormularioEditorMenu.prototype.nuevo=function(){
	//copio el actual como punto de partida
	var m={}
	
	for (var k in this.menuActivo)
		m[k]=this.menuActivo[k]
	
	m.esNuevo=true
	m.texto=null
	m.cd_camino=null; m.cd_tramite=null; m.cd_modolista=null; m.url=null
	m.cd_boton=null
	this.menuActivo=m
	this.editar()
	}
FormularioEditorMenu.prototype.modificar=function(){
	this.editar()
	}
FormularioEditorMenu.prototype.eliminar=function(){
	this.menuActivo.esEliminar=true
	this.menuActivo.esModificado=true
	this.guardar()
	}
FormularioEditorMenu.prototype.editar=function(){
	var xfrm=new Formulario( this.datosFRM(), ponNuevoBloqueo() )
	
	var self=this
	xfrm.onAceptar=function(){
		var param = xfrm.recogeValoresFRM()
		self.retornoFRM(param)
		xfrm.cierraFormulario()
		}
	
	xfrm.toDOM()
	return false
	}
FormularioEditorMenu.prototype.sacaOpciones=function(campo, anhadirFilaVacia, valoresPorDefecto){
	var ret=[]
	if (anhadirFilaVacia)
		ret.push('')
	var pos=sacaPosxId(this.cache, campo)
	
	for (var i=0;i<this.cache.filas.length; i++){
		var v=this.cache.filas[i]
		if (! (ret.contains( v[pos] ) ) )
			ret.push(v[pos])
		}
	if (valoresPorDefecto){
		for (var i=0; i<valoresPorDefecto.length; i++){
			var texto=valoresPorDefecto[i]
			if (! ret.contains(texto))
				ret.push( texto )
			}
		}
	return ret
}
FormularioEditorMenu.prototype.datosFRM=function(){

	if (this.pos==null)
		this.pos=rellenaPosxId(this.cache)
	
	var menu=this.menuActivo//un diccionario!!
	var listaPadres={}
	
	var poscd, posds, posurl, posvista, poscam, postram
		
	var poscd=this.pos['cd_boton']
	var posds=this.pos['texto']
	var posurl=this.pos['url']
	var posvista=this.pos['cd_modolista']
	var poscam=this.pos['cd_camino']
	var postram=this.pos['cd_tramite']
		
	for (var i=0; i<this.cache.filas.length;i++){
		var fila=this.cache.filas[i]
		var xcd=fila[poscd]
		var xds=fila[posds]
		
		var accion=null
		var xcam=fila[poscam];  var xtram=fila[postram]
		var xurl=fila[posurl]; var xvista=fila[posvista]
		if (xcam!='')
			accion=xcam+ ' - ' +xtram
		else if (xvista)
			accion=xcam
		else if (xurl)
			accion=xurl
		
		listaPadres[xcd]=xcd+'#'+(xds?' - ['+xds+']':'') + (accion?' - '+accion:'')
		}
	listaPadres['0']='(ninguno, se trata de un nodo de primer nivel)'
	
	return {numTramites:0, camino : 0, tramite:0, titulo: 'Propiedades del menú #'+this.menuActivo.cd_boton, tipo : 'frm', controles:[
				this.menuActivo.cd_boton==null?{tipo: 'lvw',id : 'cd_boton', maxlength : -30, valor:'',obligatorio : true,bloqueado : false, ds:'ID del botón'}:null,
				{tipo : 'lvwConsejo',id : 'sinnombre1', ds : 'Posición', tipoConsejo:'titulo1'},
					{tipo: 'lvwLista',id : 'cd_barraherramientas.existente', valor : menu.cd_barraherramientas, bloqueado : false,ds : 'Barra de herramientas existente', 'lista': this.sacaOpciones('cd_barraherramientas', true, ['menuVer', 'menuEjecutar', 'menuMantenimiento']), dsExtendida:' * Este es el dato que tenemos que emplear para situar la barra mediante CSS. Ejemplo: div.botonera.jquerycssmenu#MI_MENU'},
					{tipo: 'lvw',id : 'cd_barraherramientas.nueva',	maxlength : -30,	valor:'', bloqueado : false, ds:'Barra de herramientas nueva'},
					
					{tipo: 'lvwLista',id : 'cd_menupadre', valor : menu.cd_menupadre, bloqueado : false,ds : 'Desciende de', 'lista':listaPadres},
				
				{tipo : 'lvwConsejo',id : 'sinnombre2', ds : 'Estética', tipoConsejo:'titulo1'},
					{tipo: 'lvw',id : 'texto',	maxlength : -30,	valor:menu.texto,	bloqueado : false, ds:'Texto', dsExtendida:'-Texto para crear un separador'},
					{tipo: 'lvw',id : 'imagen',maxlength : -80,	valor:menu.imagen,	bloqueado : false, ds:'Imagen'},
					{tipo: 'lvw',id : 'titulo',	maxlength : -30,	valor:menu.titulo,	bloqueado : false, ds:'Título/texto de ayuda'},
					
				{tipo : 'lvwConsejo',id : 'sinnombre3', ds : 'Comportamiento', tipoConsejo:'titulo1'},
					{tipo : 'lvwConsejo',id : 'sinnombre31', ds : 'Tarea', tipoConsejo:'titulo2'},
						{tipo : 'bsc',id : 'TRA_Caminos.DS_Camino',ds : 'Camino',"colsQueBloquea" : 0,"colsBloqueadas" : 0,bloqueado : false,'controles':[
								{tipo: 'lvwNumero',id : 'cd_camino',maxlength : 20,valor : menu.cd_camino,bloqueado : false,ds : ''}
								]},
						{'tipo' : 'lvwConsejo','id' : 'sinnombre92z82','maxlength' : 0,'valor' : '','bloqueado' : false,'validacion' : '','ds' : 'Trámite existente', tipoConsejo:'titulo3'},
							{tipo : 'bsc',id : 'TRA_TramitesObjetos.DS_Tramite', ds : 'Trámite',"colsQueBloquea" : 0,"colsBloqueadas" : 0,bloqueado : false,'controles':[
									{tipo: 'lvwNumero',id : 'cd_tramite',maxlength : 20,valor : menu.cd_tramite, bloqueado : false,ds : ''}
									]},
						{'tipo' : 'lvwConsejo','id' : 'sinnombre9zz282','maxlength' : 0,'valor' : '', 'bloqueado' : false,'validacion' : '','ds' : 'Trámite nuevo (se incluirá en TRA_TramitesObjetos y en TRA_Acciones)', tipoConsejo:'titulo3'},
							{'tipo' : 'lvw','id' : 'cd_tramitenuevo','maxlength' : -20,'valor' : '', 'bloqueado' : false, 'ds' : 'Código'},
							{tipo : 'lvw',id: 'ds_tramitenuevo', maxlength:250,valor:'', bloqueado : false,ds : 'Descripción'},
					{tipo : 'lvwConsejo',id : 'sinnombre32', ds : 'Vista', tipoConsejo:'titulo2'},
						{'tipo' : 'lvw','id' : 'cd_modolista','maxlength' : -20,'valor' : menu.cd_modolista,'bloqueado' : false, 'ds' : 'Detalle asociado', dsExtendida:'Nombre del detalle global de la vista, normalmente aparece encima del árbol'},
						{"tipo" : 'bsc',"id" : 'exp_niveles.cd_nivel',"ds" : 'Nivel',"colsQueBloquea" : 0,"colsBloqueadas" : 0,bloqueado : false, dsExtendida:'Nivel que se cargará en el árbol',
										'controles':[{"tipo": 'lvw',"id" : 'cd_nivel',"maxlength" : -30,"valor" : menu.cd_nivelinicial, bloqueado : false,"ds" : ''}]},
						
						{tipo: 'lvwLista',id : 'disposicion', valor   :menu.disposicion, bloqueado : false,ds : 'Disposición', 'lista':{'v':'Vertical', 'h':'Horizontal'}},
						{tipo: 'lvwLista',id : 'tipoPanel1', valor   :menu.tipopanel1, bloqueado : false,ds : 'Tipo de panel izquierdo', 'lista':{'0':'Ninguno', '1':'Árbol', '2':'Lista'}},
						{tipo : 'lvwBoolean',id: 'mostrarBotonera', valor :menu.botonera,bloqueado : false,ds : 'Botoneras visibles'},
						
					{tipo : 'lvwConsejo',id : 'sinnombre33', ds : 'Enlace a página Web', tipoConsejo:'titulo2'},
						{tipo: 'lvw','id' : 'url', 'maxlength' : -100,'valor':menu.url,'bloqueado' : false, 'ds' : 'URL'},
						
				{tipo : 'lvwConsejo',id : 'sinnombre4', ds : 'Información de grupo o perfil', tipoConsejo:'titulo1'},
						{tipo: 'lvwLista',id : 'tipo.existente', valor : menu.tipo, bloqueado : false,ds : 'Grupo/perfil existente', 'lista':this.sacaOpciones('tipo', true), dsExtendida:' * Este es el dato que tenemos que emplear para generar distintos menús para cada vista, perfil, etc'},
						{tipo:'lvw', id:'tipo.nuevo', maxlength:-30, valor:'', bloqueado:false, 'ds':'Grupo/perfil nuevo'},
			]}
	}
FormularioEditorMenu.prototype.retornoFRM=function(param){
	if (param.cd_boton!=null)
		this.menuActivo.cd_boton=param.cd_boton
	
	this.menuActivo.cd_barraherramientas=param['cd_barraherramientas.nueva'] || param['cd_barraherramientas.existente']
	this.menuActivo.cd_menupadre=param.cd_menupadre
	
	this.menuActivo.texto=param.texto
	this.menuActivo.imagen=param.imagen
	this.menuActivo.titulo=param.titulo
	
	this.menuActivo.cd_camino=param.cd_camino
	
	this.menuActivo.cd_tramite=param.cd_tramite || param.cd_tramitenuevo
	this.menuActivo.ds_tramite=param.ds_tramitenuevo
	this.menuActivo.hayQueCrearTramite=(param.cd_tramitenuevo && param.cd_tramitenuevo!='')
	
	this.menuActivo.cd_modolista=param.cd_modolista
	this.menuActivo.cd_nivelinicial=param.cd_nivel
	
	this.menuActivo.disposicion=param.disposicion
	this.menuActivo.tipoPanel1=param.tipoPanel1
	this.menuActivo.botonera=param.mostrarBotonera
	
	this.menuActivo.url=param.url
	
	this.menuActivo.tipo=param['tipo.existente'] || param['tipo.nuevo']
	this.menuActivo.esModificado=true
	
	this.guardar()
	}
FormularioEditorMenu.prototype.guardar=function(){
	var lista=''
	
	var idxBloqueo=null
	if (this.menuActivo.esModificado)  {
		var self=this
		idxBloqueo=ponNuevoBloqueo(true, 'guardando menú...')
		loadJSONDocPost ( 'json', { 	accion:'guardarMenu', 
							aplicacion: exp.aplicacion,
			
							cd_boton:this.menuActivo.cd_boton,
							cd_barraherramientas:this.menuActivo.cd_barraherramientas,
							cd_menupadre:this.menuActivo.cd_menupadre,

							texto:this.menuActivo.texto,
							imagen:this.menuActivo.imagen,
							titulo:this.menuActivo.titulo,

							cd_camino:this.menuActivo.cd_camino,

							cd_tramite:this.menuActivo.cd_tramite,
							ds_tramite:this.menuActivo.ds_tramite,
							hayQueCrearTramite:this.menuActivo.hayQueCrearTramite,

							cd_modolista:this.menuActivo.cd_modolista,
							cd_nivelinicial:this.menuActivo.cd_nivelinicial,

							disposicion:this.menuActivo.disposicion,
							tipoPanel1:this.menuActivo.tipoPanel1,
							botonera:this.menuActivo.botonera,

							url:this.menuActivo.url,

							tipo:this.menuActivo.tipo,
							
							esModificado:this.menuActivo.esModificado,
							esNuevo:this.menuActivo.esNuevo,
							esEliminar:this.menuActivo.esEliminar
							},
				function(req) {
						var resp=xeval(req)
					
						//refrescamos árbol
						self.cacheado=false
						self.refresca()
						
						quitaBloqueo(idxBloqueo)
						}, 
				function(){
						alert('Error guardando el menú')
						quitaBloqueo(idxBloqueo)
						}
					)
		}
	
	if (idxBloqueo)	
		quitaBloqueo(idxBloqueo)
	}
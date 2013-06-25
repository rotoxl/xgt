var fantasma
var deltaX, deltaY, deltaW, deltaH

function getX(node) {
	return node.offsetLeft
	}
function getY(node) {
	return node.offsetTop
	}
function getWidth(node) {
	return node.offsetWidth
	}
function getHeight(node) {
	return node.offsetHeight
	}
//////////////////////
function ponPx(v){
	if (!(v+'').endsWith('px'))
		return v+'px'
	return v
	}
function setX(node,v) {
	node.style.left = ponPx(v)
	}
function setY(node,v) {
	node.style.top = ponPx(v)
	}
function setWidth(node, v) {
	node.style.width=ponPx(v)
}
function setHeight(node, v) {
	node.style.height= ponPx(v)
}
////////////////////////////////////////////////////////////////////
function Evt(evt) {
	if (evt!=null)
		this.evt = evt
	else
		this.evt=window.event
	
	this.source = this.evt.target ? this.evt.target : this.evt.srcElement
	this.x = this.evt.pageX ? this.evt.pageX : this.evt.clientX
	this.y = this.evt.pageY ? this.evt.pageY : this.evt.clientY
	}
Evt.prototype.toString = function () {
	return "Evt [ x = " + this.x + ", y = " + this.y + " ]";
}

Evt.prototype.consume = function () {
	if (this.evt.stopPropagation) {
		this.evt.stopPropagation();
		this.evt.preventDefault();
		} 
	else {//if (this.evt.cancelBubble) {
		this.evt.cancelBubble = true;
		this.evt.returnValue = false;
		}
	}
Evt.addEventListener = function (target,type,func,bubbles) {
	if (document.addEventListener) 
		target.addEventListener(type,func,bubbles);
	 else if (document.attachEvent) 
		target.attachEvent("on"+type,func,bubbles);
	 else 
		target["on"+type] = func;
	}
Evt.removeEventListener = function (target,type,func,bubbles) {
	if (document.removeEventListener) 
		target.removeEventListener(type,func,bubbles);
	 else if (document.detachEvent) 
		target.detachEvent("on"+type,func,bubbles);
	 else 
		target["on"+type] = null;
}
////////////////////////////////////////////////////////////////////
function ponEventosResize(){
	Evt.addEventListener(document,"mousemove",resizeMove,true)
	Evt.addEventListener(document,"mouseup",resizeRelease,true)
	Evt.addEventListener(document,"keypress",resizeKeyPress,true)
}
function quitaEventosResize(){
	Evt.removeEventListener(document, "mousemove", resizeMove, true)
	Evt.removeEventListener(document, "mouseup", resizeRelease, true)
	Evt.removeEventListener(document, "keypress", resizeKeyPress, true)
}
function ponEventosDrag(){
	Evt.addEventListener(document,"mousemove",dragMove,true)
	Evt.addEventListener(document,"mouseup",dragRelease,true)
	Evt.addEventListener(document,"keypress",dragKeyPress,true)
}
function quitaEventosDrag(){
	Evt.removeEventListener(document, "mousemove", dragMove, true)
	Evt.removeEventListener(document, "mouseup", dragRelease, true)
	Evt.removeEventListener(document, "keypress", dragKeyPress, true)
}
function hayQueHacerDrag(){
	if (editorNivelesActivo){
		disenhador.pintandoCuadro=false
		disenhador.cancelaRubberBand()
		return false
		}
	//~ else if (!disenhador.moviendoControles){
		//~ return false
		//~ }
	return true
	}
function dragPress(idCtl, evt) {
	if (!hayQueHacerDrag()){
		return 
		}
	if (evt.ctrlKey) {
		disenhador.alternaControlEnSeleccion(idCtl)
		return
		}
	
	disenhador.moviendoControles=true
	disenhador.fantasmasEnrejados=false
	evt = new Evt(evt)

	fantasma = disenhador.creaCopia( disenhador.listaSeleccionados )
	deltaX = evt.x - getX(fantasma)
	deltaY = evt.y - getY(fantasma)
	
	quitaEventosResize()
	ponEventosDrag()
	
	return false
}
function enReja(x){
	return Math.floor(x/disenhador.rejilla)*disenhador.rejilla
	}
function dragMove(evt) {
	if (!hayQueHacerDrag()){
		return 
		}
		
	disenhador.enrejaFantasmas(true)
		
	evt = new Evt(evt)
	setX(fantasma, enReja(evt.x - deltaX))
	setY(fantasma, enReja(evt.y - deltaY))
	
	var fi=control('fantasma'+disenhador.controlIzq.id)
	var xfi=offset('l', fi)
	var yfi=offset('t', fi )
	
	if (disenhador.sobreContenedor)
		$('.dragMove').removeClass('dragMove')
			
	var hayCont=sobreContenedor(xfi, yfi)
	if (hayCont){
		ponEstilo( hayCont.getPanelContenedor(), 'dragMove')
		}
	disenhador.sobreContenedor=hayCont
	
	var xx=getX( fi )+evt.x - deltaX
	var yy=getY( fi )+evt.y - deltaY
	disenhador.muestraCoordenadas(xx,yy)
	
	evt.consume()
}
function sobreContenedor(xfi, yfi){
	var contenedor
	
	var listaContenedores=disenhador.md.listaControlesPorTipo(Control.prototype.controlesContenedores)
	for (var i in listaContenedores){
		var cont=listaContenedores[i]
		//para que no caiga dentro de sí mismo
		if (cont && typeof cont == 'function') {
			continue
			}
		else if (disenhador.listaSeleccionados.contains(cont.id ) ){ 
			continue
			}
		else if (disenhador.listaSeleccionados.contains('control'+cont.cont) ){
			continue
			}
		else if (!cont.estaVisible()){
			continue
			}
		else if (estaContenido(xfi, yfi, cont)) {
			if (contenedor==null) 
				contenedor=cont
			else { //si ya le habíamos encontrado un contenedor, nos quedamos con el más interno
				if (cont.estaVisible()){					
					if ((contenedor.tc=='rd' || contenedor.tc=='exp') && cont.tc=='tab') {
						/* pass */
						}
					else
						contenedor=cont
					}
				}
			}
		}
	return contenedor
}
function dragRelease(evt) {
	if (!disenhador.moviendoControles)
		return
	evt = new Evt(evt)
	quitaEventosDrag()
	
	if (disenhador.pintandoCuadro) return
	//Con su esquina superior izquierda del controlIzq buscamos el contenedor
	
	var fi=control('fantasma'+disenhador.controlIzq.id)
	if (fi==null)
		return
	var xfi=offset('l', fi)
	var yfi=offset('t', fi )
	
	var contenedor=sobreContenedor(xfi, yfi)
	for (var i=0; i<disenhador.listaSeleccionados.length; i++) {
		var id=disenhador.listaSeleccionados[ i ]
		var cctl=disenhador.md.getControl(id)
		var ctl=control(id)
		
		if (id && typeof id != 'function') {
			var f=control('fantasma'+id)
			var izq=offset('l', f )
			var top=offset('t', f )
			var cont= 0
			var pest=0
						
			if (contenedor) {
				cont=contenedor.numControl
				var panelContenedor=contenedor.getPanelContenedor()
				
				var contOffset=$(panelContenedor).offset()
				var mdOffset=$(contenedor.md.div).offset()
				
				izq -= (contOffset.left-mdOffset.left)
				top -= (contOffset.top-mdOffset.top)
				
				ctl.parentNode.removeChild(ctl)
				panelContenedor.appendChild(ctl)
				
				if (contenedor.tc=='tab' || contenedor.tc=='pag') 
					pest=contenedor.pestActiva
				}
			else {
				cont=0
				ctl.parentNode.removeChild(ctl)
				disenhador.md.div.appendChild( ctl )
				}
				
			mueve( ctl, izq, top)
			
			var cambiaContenedor=cont!=cctl.cont
			if (cambiaContenedor && cctl.cont!=0) {
				disenhador.md.getControl(cctl.cont).quitarControlContenido(cctl)
				}
			
			cctl.cont=cont
			cctl.pestanha=pest
			cctl.izq=izq
			cctl.tope=top
			cctl.modificado=true
			
			if (cambiaContenedor){ 
				if (cont!=0) 
					disenhador.md.getControl(cctl.cont).anhadirControlContenido(cctl)
				else
					disenhador.md.anhadeControl(cctl)
				}
			}
		}
	fantasma.parentNode.removeChild(fantasma)
	$('.dragMove').removeClass('dragMove')
	disenhador.moviendoControles=false
	
	disenhador.refrescaTiradores()
	disenhador.cambiaHayQueGuardar()
}
function dragKeyPress(evt){
	if (evt.keyCode==vbKeyCancel){
		disenhador.dimensionandoControles=false
		disenhador.moviendoControles=false
		fantasma.parentNode.removeChild(fantasma)
		disenhador.refrescaTiradores()
		
		evt = new Evt(evt)
		
		quitaEventosDrag()
		quitaEventosResize()
		}
}
function estaContenido(x, y, cont) {
	var panelContenedor=cont.getPanelContenedor()
	
	var poffset=$(panelContenedor).offset()
	var paneloffset=$(cont.md.div).offset()
	
	var izq=poffset.left-paneloffset.left
	var tope=poffset.top-paneloffset.top
	
	var ancho=getWidth(panelContenedor)
	var alto=getHeight(panelContenedor)
	
	return _estaContenido(x, y, 	izq, tope, ancho, alto)
}
function _estaContenido(x, y, xCont, yCont, anchoCont, altoCont) {
	if (x>xCont && x<(xCont+anchoCont) ) {
		if (y>yCont && y<(yCont+altoCont) ) 
			return true
		}
	
	return false
}
////////////////////////////////////////////////////////////////////
var gripQueMueve
function resizePress(evt, ctl) {
	disenhador.dimensionandoControles=true
	evt = new Evt(evt)
	
	fantasma = disenhador.creaCopia( disenhador.listaSeleccionados, true )
	gripQueMueve=evt.source
	disenhador.controlW=gripQueMueve.parentNode //para mostrar las coordenadas
	
	var xoffset=$(gripQueMueve).offset()
	var panelOffset=$('#'+disenhador.md.idx).offset()
	
	deltaW=xoffset.left-panelOffset.left
	deltaH =xoffset.top-panelOffset.top
	
	quitaEventosDrag()
	ponEventosResize()
}
function resizeMove(evt) {
	if (!disenhador.dimensionandoControles || editorNivelesActivo)
		return
	
	disenhador.enrejaFantasmas(false)
	
	var coords=getCoordenadasRaton(evt)
		var cx = coords.left
		var cy = coords.top
	
	var difX=cx-deltaW-9
	var difY=cy-deltaH-8
	
	evt = new Evt(evt)
	
	var x=getX(disenhador.controlW)
	var y=getY(disenhador.controlW)
	var ancho=difX+getWidth(disenhador.controlW)
	var alto=difY+getHeight(disenhador.controlW)
	disenhador.muestraCoordenadas(x, y, ancho, alto )
	
	for (var i=0; i<disenhador.listaSeleccionados.length; i++) {
		var id=disenhador.listaSeleccionados[ i ]
		if (typeof id != 'function') {
			var f=control('fantasma'+id)
			var ctl=control(id)
			if (difX!=0)
				setWidth(f,  enReja(getWidth(ctl)+difX))
			if (difY!=0)
				setHeight(f, enReja(getHeight(ctl)+difY))
			}
		}
	evt.consume()
	disenhador.cambiaHayQueGuardar()
}
function resizeRelease(evt) {
	disenhador.dimensionandoControles=false
	disenhador.moviendoControles=false

	evt = new Evt(evt)
	quitaEventosResize()

	for (var i=0; i<disenhador.listaSeleccionados.length; i++) {
		var id=disenhador.listaSeleccionados[ i ]
		if (typeof ctl != 'function') {		
			var f=control('fantasma'+id)
			
			var cctl=disenhador.md.getControl(id)
			cctl.ancho=getWidth(f)-3
			cctl.alto=getHeight(f)-3
			cctl.refresca()
			cctl.modificado=true
			}
		}
	
	fantasma.parentNode.removeChild(fantasma)
	disenhador.refrescaTiradores()
}
function resizeKeyPress(evt){
	dragKeyPress(evt)
}
////////////////////////////////////////////////////////////////////
function mueve(ctl, izq, tope, ancho, alto) {
	if (izq!=null)	setX(ctl, izq)
	if (tope!=null) 	setY(ctl, tope)
	if (ancho!=null) 	setWidth(ctl, ancho)
	if (alto!=null) 	setHeight(ctl, alto)
}

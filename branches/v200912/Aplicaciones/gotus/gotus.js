function ControlResumen(c, md){
	ControlPER.call(this,c,md)
	}
ControlResumen.prototype=new ControlPER
Control['resumen']=ControlResumen
ControlResumen.prototype.init=function(){
	var temp=this.caption.split('|')
	this.nivel=temp[0]
	this.numElementos=temp[1]==null?0:Number(temp[1])
	
	if (isNaN(this.numElementos))
		this.numElementos=0
	}
ControlResumen.prototype.toDOM=function(){
	this.dom=this.creaDIV()
	
	if (this.claseCSS){
		this.aplanaControl(this.dom)
		ponEstilo(this.dom, this.claseCSS)
		}
	else {
		$(this.dom).css({color:this.colorFrente, backgroundColor:this.colorFondo})		
		}
	
	Control.prototype.toDOM.call(this)
	return this.dom
}

Control.prototype.controlesConRefresco.push('resumen') //los que cambian al cambiar de nodo
Control.prototype.controlesConNivel.push('resumen') //considerados pesados de cargar, sólo se cargan cuando están en la pestaña activa de un tab
ControlResumen.prototype.datosFRM=function(){
	return {numTramites:0, camino:0, tramite:'Propiedades del control', tipo:'frm', 'controles':[
			{tipo:'bsc', id:'exp_niveles.cd_nivel', obligatorio:true,ds:'Nivel', 'controles':[{tipo:'lvw', id:'cd_nivel', maxlength:-30, valor:this.nivel}]},
			{tipo:'lvwNumero', id:'numElementos', maxlength:-3, valor: this.numElementos,  ds:'Número de elementos a mostrar', dsExtendida: '0 para mostrarlos todos. Ejemplo: para un valor de 2 el control mostrará "Pedro, Juan y otros 3 más"'},
			{tipo:'bsc', id:'exp_estilos.ds_estilo', ds:'Clase CSS', controles:[{tipo:'lvw', id:'cd_estilo', maxlength:-15, valor:this.claseCSS}]}
			]}
	}
ControlResumen.prototype.retornoFRM=function(param){
	this.setNivel(param['cd_nivel'])
	
	var tempCaption=this.nivel+'|'+param.numElementos
	this.modificado=this.caption!=tempCaption
	if (!this.modificado)
		this.modificado=	this.claseCSS!=param['cd_estilo']
	
	this.claseCSS=param['cd_estilo']
	this.caption=tempCaption
	this.refresca() 
	}
ControlResumen.prototype.rellena=function(valor, key){
	this.keyContenedor=key
	
	this.vacia()
	this.lanzaCargar()
	} 
ControlResumen.prototype.cargarFilas=function(filas){
	var tempNumElementos=this.numElementos
	if (tempNumElementos<=0)
		tempNumElementos=1000

	for (var idfila=0; idfila<filas.filas.length; idfila++){
		var dato=filas.filas[idfila][0]
		
		if (idfila < tempNumElementos){
			if (idfila==filas.filas.length)
				this.dom.appendChild(creaTexto('y'))
			if (idfila>0)
				this.dom.appendChild(creaTexto(','))
			this.dom.appendChild( creaObjProp('span', {texto:dato}))
			
			}
		else if (idfila==(filas.filas.length-1) ){
			var diff=(filas.filas.length-tempNumElementos)			
			this.dom.appendChild(creaTexto('y '+diff+' más'))
			}
		}
	}
ControlResumen.prototype.vacia=function(){
	borraHijos(this.dom)
	this.dom.title=null
	Control.prototype.vacia.call(this)
	}
ControlResumen.prototype.establecerValorActivo=function(valor){}
///////////////////////////////////////////////////
/*
NOTIFICACIONES DESDE EL SERVIDOR
http://hacks.mozilla.org/2011/06/a-wall-powered-by-eventsource-and-server-sent-events/

los eventos o mensajes recibidos
	msg:
		data: {diccionario js}

	eventos:
		event: tipoEvento
		data: {diccionario js}
*/
function Notificaciones(){
	var xthis=this
	this.eventos={
		'checkin': function(e){xthis.addMessage('checkin', e.data)}
		}
	this.start()
	}
Notificaciones.prototype.start=function(){
	// Create an EventSource object, passing it the URL of the server sccript
	var evtSrc = new EventSource( "./json?aplicacion="+exp.aplicacion+'&accion=experimentos.notificaciones' );
	this.evtSrc=evtSrc
	this.registraListener()
	
	console.warn('Notificaciones activadas')
	}
Notificaciones.prototype.registraListener=function(){
	// Listen for messages/events on the EventSource
	this.evtSrc.onmessage = function ( e ) {
		addMessage( "status", e.data );
		}
	
	for (k in this.eventos){
		this._registra1Listener(k, this.eventos[k])
		}
	}
Notificaciones.prototype.desregistraListener=function(){
	this.evtSrc.onmessage=void(0)
	for (k in this.eventos){
		this._desregistra1Listener(k, this.eventos[k])
		}
	}	
Notificaciones.prototype._registra1Listener=function(evento, func){
	//~ evtSrc.addEventListener("forward", function( e ) {
		//~ addMessage( "forward", e.data );
		//~ }, false);
	
	this.evtSrc.addEventListener(evento, func, false);
	}
Notificaciones.prototype._desregistra1Listener=function(evento, func){
	this.evtSrc.removeEventListener(evento, func, false);
	}
// Parse the data and insert the message in the timeline with the appropriate className
Notificaciones.prototype.addMessage=function( className, data ) {
	console.info(className+' recibido: '+data)
	}
Notificaciones.prototype.stop=function(){
	this.desregistraListener()
	this.evtSrc=null
	
	console.warn('Notificaciones desactivadas')
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
var notificaciones=null
function activarNotificaciones(){
	if (notificaciones!=null){
		alert('ya están activadas')
		}
	else
		notificaciones=new Notificaciones()
	}
function desactivarNotificaciones(){
	notificaciones.stop()
	notificaciones=null
	}
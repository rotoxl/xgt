var sep="\\", pipe="|"
var mt
var exp={mapatram:true}
var mostrarLibres=true
var agruparSUB=true //poniendolo a true transfiere las propiedades de los SUBs de un trámite (tanto hijos como dlls). El efecto es como si leyera las acciones de un trám incrustando dentro las acciones de los subs
var zoom=1
var icoGotta='./fijo/'
var xsep='_666_', xpunto='_PUNTO_'
function print(q){
	//~ console.log(q)
	}
function length(arr){
    var i=0
    for (var a in arr)
        i++
    return i
    }
function Exists(arr, k){
    return !(typeof arr[k] == "undefined")
    }
/////////////////////////////////////////////
function Tramite(cd, ds){
	this.cd=cd
	this.ds=ds
	
	this.cdOriginal=cdOrig(cd)
	
	this.sub=[]
	this.dll={}
	
	this.xcd=[]
	this.xds=[]
	
	this.ejecutado=false
	this.pendiente=false
	
	this.hijos={}
	this.padre=null
	this.implementado=false
}

Tramite.prototype.pinta=function(cont, esCopia, id, i, j, padre){
	var todo=creaObjProp('div', {id:arreglaNombre(id), className:'tram'})
	
	var xcd=creaObjProp('div', {className:'cd', 'texto':this.cdOriginal})
	var xds=creaObjProp('div', {className:'ds', 'texto':this.ds})
	
	todo.appendChild(xcd); todo.appendChild(xds)
	$(todo).hover(this.fnHoverIN(this.cdOriginal), this.fnHoverOUT(this.cdOriginal))
	
	var contTram=creaObjProp('div', {title:'['+this.cdOriginal+'] '+this.ds, className:'contTram', 'style.left':i*Tramite.prototype.ancho*1.6  - cont.childNodes.length*Tramite.prototype.ancho})
	contTram.appendChild(todo)
	var divInfoEjecucion=null
	
	if (this.firmaTramite)
		ponEstilo(contTram, 'firma firmatramite')
	else if (this.firmaArchivo)
		ponEstilo(contTram, 'firma firmaarchivo')
		
	if (this.ejecutado){		
		var divEstado=creaObjProp('div', {className:'estado', hijo:creaObjProp('span', {className:'estado', texto:'Realizado por '+this.uEjecucion+' el día '+this.fEjecucion})})
		contTram.appendChild(divEstado)
		ponEstilo(todo, 'ejecutado')
		}
	else if (this.pendiente){
		var divEstado=creaObjProp('div', {className:'estado', hijo: creaObjProp('span', {className:'estado', texto:'Pendiente por '+this.uPendiente+' el día '+this.fPendiente})})
		contTram.appendChild(divEstado)
		ponEstilo(todo, 'pendiente')
		}
		
	if (mt.tienePermiso){
		todo.appendChild( creaObjProp('a', {className:'operaciones anhadirHijo',onclick:fnAnhadirHijo(this), 			title:'Añadir trámite al circuito' }) )
		todo.appendChild( creaObjProp('a', {className:'operaciones borrarHijo',  onclick:fnQuitarDelCircuito(this, padre), title:'Quitar este trámite del circuito (el trámite no será eliminado, sólo su posición en el flujo)' }) )
		todo.appendChild( creaObjProp('a', {className:'operaciones editar', 	 onclick:fnEditarTram(this), 			title:'Editar' }) )

		var clase, titulo=''
		if (!this.implementado){
			clase='implementado no_implementado'
			titulo='El trámite no está implementado.'
			}
		else
			clase='implementado'
		
		var estrella=creaObjProp('a',{className: clase, 
							title:titulo+' [Click para editar sus acciones]', 
							onclick:this.clickEnlaceEditarAcciones(this.cdOriginal, mt.idCamino),
							target:'vprinc'}) 
		todo.appendChild(estrella)
		}
		
	cont.appendChild(contTram)
	
	this.incrustaInfo(contTram, todo, id)
	
	if (esCopia) {
		ponEstilo(todo, 'copia')
		todo.onclick=fnCentrar(this.cdOriginal)
		}
		
	this.xcd.push(xcd); this.xds.push(xds)
	}
Tramite.prototype.incrustaInfo=function(contTram, cont, id)   {
	var ret=creaObjProp('div', {className:'info'})
	
	var listaDLL=creaObjProp('ul', {className:'dll', 'style.width':this.ancho})
	for (var dll in this.dll) {
		var lista=this.dll[dll]
		if (typeof lista == 'function') continue
		listaDLL.appendChild( creaObjProp('li', {className:dll+' dll', 'texto':dll/*+'>>'+lista*/} ))
		}
	
	var listaTramites=creaObjProp('div', {className:'listaSubs', 'style.width':this.ancho})
	for (var tipoSub in this.sub){ //aquí vienen SUB, FOR, EJE
		var sub=this.sub[tipoSub]
		if (typeof sub =='function') 
			break
		
		var lt=this.sub[tipoSub]
		for (var i=0; i<lt.length; i++){
			var nombresub=lt[i].toLowerCase()
			
			if (nombresub=='') continue
			var tramsub=mt.tramites[ nombresub ]
			if (tramsub==null){
				tramsub=new Tramite(nombresub, nombresub)
				mt.tramites[nombresub]=tramsub //normalmente viene el nombre de una variable
				}
				
			var bolicheSub=creaObjProp('div',{className:'tram', id:tramsub.cdOriginal+xsep+Math.floor(Math.random()*3000), title:tramsub.cdOriginal })
			listaTramites.appendChild(bolicheSub)
			
			$(bolicheSub).hover(this.fnHoverIN(tramsub.cdOriginal+xsep), this.fnHoverOUT(tramsub.cdOriginal+xsep))
			
			var xcd=creaObjProp('div', {className:'cd', 'texto':tramsub.cdOriginal})
			var xds=creaObjProp('div', {className:'ds', 'texto':tramsub.ds})
			
			bolicheSub.appendChild(xcd); bolicheSub.appendChild(xds)
			}
		}
		
	if (listaTramites.firstChild) contTram.appendChild(listaTramites)
	if (listaDLL.firstChild) ret.appendChild(listaDLL)
	
	if (ret.firstChild)
		cont.appendChild(ret)
	}
Tramite.prototype.fnHoverIN=function(cd){
	var self=this
	return function(){self.fnHoverIN_OUT(cd, true)}
	}
Tramite.prototype.fnHoverOUT=function(cd){
	var self=this
	return function(){self.fnHoverIN_OUT(cd, false)}
	}
Tramite.prototype.fnHoverIN_OUT=function(cd, dentro){
	var lista=$('div.tram[id^='+arreglaNombre(cd)+']')
	if (dentro)
		lista.addClass('resaltado')
	else
		lista.removeClass('resaltado')
	}
function fnCentrar(tram){
	return function(){mt.centrar(tram)}
}
Tramite.prototype.clickEnlaceEditarAcciones=function(tramite, camino){
	return function(event){
		var idx= ('tramite'+tramite + camino).toLowerCase()
		var param = { 'accion':'inicioDisenhoTramite', 'camino': camino, 'tramite':tramite, 'aplicacion': exp.aplicacion, 'idxBloqueo':idx}
		
		var objetivo=event.target || event.srcElement
		ponEstilo(objetivo, 'cargando')
		loadJSONDocPost('json', param,
				   function(req){
					var ret=xeval(req)
					retornoTramite( ret )
					depuradorActivo.activaBotones(false)
					depuradorActivo.ocultarPanelInferior()
					quitaEstilo(objetivo, 'cargando')
					},
				   function(req){
					errorAjax(req)
					quitaBloqueo(idx)
					}
				)
			}
}
/////////////////////////////
Tramite.prototype.editar=function(){
	var tram=this
	tram.idxBloqueo=ponNuevoBloqueo()
	var xfrm=new Formulario( this.datosFRM(), tram.idxBloqueo )
	xfrm.onAceptar=function(){
		var xfrm=getFrmActivo()
		var param = xfrm.recogeValoresFRM()
		tram.retornoFRM(param)
		xfrm.cierraFormulario()
		}
	xfrm.toDOM()
}
Tramite.prototype.datosFRM=function(){
	return {"numTramites" : 0,"camino" : 0,"tramite" : 'Propiedades del trámite',"tipo" : 'frm',"titulo":this.esNuevo?'Nuevo trámite libre':'Modificar trámite "'+this.cd+'"', "alto" : 200,'controles':[
			{'tipo' : 'lvw','id' : 'cd','maxlength' : -20,'valor' : this.cd,'obligatorio': true,'bloqueado' : !this.esNuevo, 'ds' : 'Código'},
			{"tipo" : 'lvw',"id": 'ds', "maxlength" : 250,"valor" : this.ds,"obligatorio" : true,"bloqueado" : false,"ds" : 'Descripción'}
			]}
}
Tramite.prototype.retornoFRM=function(datos){
	this.ncd=datos.cd
	this.nds=datos.ds
	
	var tram=this
	var param={'aplicacion':aplicacion, 'accion':'guardaTramite', 'ncd':this.ncd, 'nds':this.nds, 'camino':mt.idCamino}
	if (!this.esNuevo) param['cd']=this.cd
	loadJSONDocPost('json', param, function(req){
								if (!trataHayError(req)) {
									tram.cd=tram.ncd
									tram.ds=tram.nds
									tram.actualizar()
									}
								})
}
Tramite.prototype.actualizar=function(){
	for (var i=0; i<this.xcd.length; i++) {
		var xcd=this.xcd[i]
		xcd.replaceChild(creaT(this.ncd), xcd.firstChild)
		}
	for (var i=0; i<this.xds.length; i++) {
		var xds=this.xds[i]
		xds.replaceChild(creaT(this.nds), xds.firstChild)
		}
	
	mt.actualizar(this.cd)
}
/////////////////////////////
function trataHayError(req){
	var resp=xeval(req)
	if (resp.tipo=='error'){
		alert(resp.msg)
		return true
		}
	return false
}
function fnQuitarDelCircuito(tram, idPadre){
	return function(){tram.quitarDelCircuito(idPadre)}
	}
Tramite.prototype.quitarDelCircuito=function(idPadre){
	var tram=this
	
	var param={'aplicacion':aplicacion, 'accion':'borraPEN', 'tramite':idPadre, 'camino':mt.idCamino, 'tramitePEN':this.cdOriginal}
	loadJSONDocPost('json', param, function(){mt.actualizar(idPadre)})
}
Tramite.prototype.anhadirHijo=function(){
	var tram=this
	nuevoFormulario(this.datosFRMPen(), function(ret){tram.retornoFRMPen(ret)} )
}
function nuevoFormulario(datosFRM, fnRetorno){
	var xfrm=new Formulario( datosFRM, ponNuevoBloqueo() )
	xfrm.onAceptar=function(){
		var xfrm=getFrmActivo()
		
		var param = xfrm.recogeValoresFRM()
		fnRetorno(param)
		
		xfrm.cierraFormulario()
		}
	xfrm.toDOM()
}
Tramite.prototype.retornoFRMPen=function(datos){
	if (datos.CD_TramiteExistente==null && datos.CD_TramiteNuevo==null )
		return
	
	var param={'aplicacion':aplicacion, 
			'accion':'creaPEN', 
			'camino':mt.idCamino,
			'tramite':this.cdOriginal, 
			'caminoPEN':datos.CD_Camino || mt.idCamino,
			'CD_TramiteExistente':datos.CD_TramiteExistente,
			'CD_TramiteNuevo':datos.CD_TramiteNuevo,
			'DS_TramiteNuevo':datos.DS_TramiteNuevo}
	
	var centrar=datos.CD_TramiteNuevo || this.cd
	loadJSONDocPost('json', param, function(req){
						if (!trataHayError(req)) 
							mt.actualizar(centrar)})
}
Tramite.prototype.datosFRMPen=function(){
	return {"numTramites" : 0,"camino" : 0,"tramite" : 'Propiedades del trámite',"tipo" : 'frm',"titulo":'Nuevo trámite pendiente', "alto" : 250,'controles':[
			{"tipo" : 'bsc',"id" : 'TRA_Caminos.DS_Camino',"obligatorio" : false,"ds" : 'Camino',"colsQueBloquea" : 0,"colsBloqueadas" : 0,"bloqueado" : false,'controles':[
				{"tipo": 'lvw',"id" : 'caminoPEN',"maxlength" : 8,"valor" : mt.idCamino,"obligatorio" : false,"bloqueado" : false,"ds" : ''}
				]},
			{'tipo' : 'etiqueta','id' : 'sinnombre9782880035098282','maxlength' : 0,'valor' : '','obligatorio': false,'bloqueado' : false,'validacion' : '','ds' : 'Trámite existente'},
				{"tipo" : 'bsc',"id" : 'TRA_TramitesObjetos.DS_Tramite',"obligatorio" : false,"ds" : 'Trámite',"colsQueBloquea" : 0,"colsBloqueadas" : 0,"bloqueado" : false,'controles':[
					{"tipo": 'lvw',"id" : 'CD_TramiteExistente',"maxlength" : -25,"valor" : '',"obligatorio" : false,"bloqueado" : false,"ds" : ''}
					]},
			{'tipo' : 'etiqueta','id' : 'sinnombre9782880035098212','maxlength' : 0,'valor' : '','obligatorio': false,'bloqueado' : false,'validacion' : '','ds' : 'Trámite nuevo'},
				{'tipo' : 'lvw','id' : 'CD_TramiteNuevo','maxlength' : -20,'valor' : '','obligatorio': false,'bloqueado' : false, 'ds' : 'Código'},
				{"tipo" : 'lvw',"id": 'DS_TramiteNuevo', "maxlength":250,"valor":'',"obligatorio":false,"bloqueado" : false,"ds" : 'Descripción'}
			]}
}
///////////////////////
function fnAnhadirHijo(tram){return function(){tram.anhadirHijo()}}
function fnEditarTram(tram){return function(){tram.editar()}}
///////////////////////
var aplicacion
function MapaTramitacion(idCamino, xcontenedor, apli, zoom, objetoBase, tramCentrado, tienePermiso){
	this.divFilas=[]
	this.tienePermiso=tienePermiso
	
	this.mostrarLibres=mostrarLibres
	
	this.objetoBase=objetoBase
	this.tramCentrado=tramCentrado
	
	this.idCamino=idCamino
	aplicacion=apli; exp.aplicacion=apli
	
	this.contenedor=control(xcontenedor)
	this.totalLibresPorLinea=3//Math.max(window.innerHeight || document.body.offsetHeight, 600)/Tramite.prototype.ancho
	
	this.pideDatos()
	
	this.cambiaEscala(zoom || 1)
	
	var xsep='/'
	var temp=window.location.toString().split(xsep)
	temp.remove(temp.length-1)
	this.contexto=temp.join(xsep)+xsep
	}
MapaTramitacion.prototype.totalLibresPorLinea=0
MapaTramitacion.prototype.actualizar=function(tramCentrado){
	this.tramCentrado=tramCentrado
	this.pideDatos(this, exp.aplicacion)
}
MapaTramitacion.prototype.centrar=function(nombre){
	if (nombre==null) 
		return
	nombre=nombre.toLowerCase()
	var tram=$('#'+nombre)

	if (!this.estaVisible(tram))
		//~ tram[0].scrollIntoView(false)
		$('#cuerpo').scrollTo(tram, 500)	
	
	$('div.contTram div.tram.foco').removeClass('foco')
	tram.addClass('foco')
	tram.click(function(){tram.removeClass('foco')})
}
MapaTramitacion.prototype.estaVisible=function(cual){
	var xcont=$( '#cuerpo' )
	cont=xcont[0]
	
	var posNodo=cual.offset()
	if (! (posNodo.left>cont.offsetLeft && (posNodo.left<cont.offsetLeft+xcont.innerWidth()) ) )
		return false
	else if (! (posNodo.top>cont.offsetTop && (posNodo.top<cont.offsetTop+xcont.innerHeight()) ) )
		return false
	return true
	}
////////////////////
MapaTramitacion.prototype.pideDatos= function(){
	var mapa=this
	loadJSONDoc( 	'json',
				{ 'accion':'mapaTram', 'camino': this.idCamino, aplicacion: aplicacion, ob:this.objetoBase } ,
				function(req){
					var datos=xeval(req)
					if (datos.jer) {
						var jer=new Jerarquia(datos.jer, mapa.totalLibresPorLinea, datos)
						mapa.mastica(jer, datos)
						}
					mapa.pintar()
					mapa.centrar(mapa.tramCentrado)
					}
				)
}
MapaTramitacion.prototype.sacaCD=function(cdmin){
	cdmin=cdmin.split('/')[0]
	var temp=this.xjer.datos.jer.replaceAll('/', pipe).split(pipe)//var n=str.replace(/blue/gi, "red"); 
	return temp[temp.indexOfIgnoreCase(cdmin)]
	}
MapaTramitacion.prototype.mastica=function(jer, datos){
	this.copias=jer.copias
	this.relaciones=jer.relaciones
	this.posiciones=jer.posiciones
	this.md=jer.md
	this.totalFilasLibres=jer.totalFilasLibres
	this.xjer=jer
	
	this.ejecutados=datos.ejecutados
	this.pendientes=datos.pendientes

	this.tramites={}
	
	for (var i=0; i<this.md.length; i++){
		var fila=this.md[i]
		for (var j=0; j<fila.length; j++){
			var cd=fila[j]
			if (cd)
				this.enchufa1(cd, datos.ds_tramites, datos.sub, datos.dll, jer.nodosHijos, jer.nodosPadres, datos.firmaTramites)
			}
		}
	
	for (var cd in jer.nodos)
		this.enchufa1(cd, datos.ds_tramites, datos.sub, datos.dll, jer.nodosHijos, jer.nodosPadres, datos.firmaTramites)
		
	for (var cd in datos.ds_tramites)
		this.enchufa1(cd, datos.ds_tramites, datos.sub, datos.dll, jer.nodosHijos, jer.nodosPadres, datos.firmaTramites)
		
	
	for (var i in datos.tramImplementados){
		var cd=datos.tramImplementados[i]
		if (typeof cd =='function') 
			break
		
		cd=cd.toLowerCase()
		var tr=this.tramites[cd]
		if (!tr) 
			tr=this.enchufa1(cd, datos.ds_tramites, datos.sub, datos.dll, jer.nodosHijos, jer.nodosPadres, datos.firmaTramites)
		tr.implementado=true
		}
	
	for (var i in this.copias){
		var cd=this.copias[i]
		if (typeof cd =='function') 
			break
	
		var tram=this.tramites[cd]
		var tramOriginal=this.tramites[ cdOrig(cd) ]
		
		tram.implementado=tramOriginal.implementado
		tram.ds=tramOriginal.ds
		}
		
	
	var cd, fecha, usu
	for (var i in this.ejecutados){
		var fila=this.ejecutados[i]
		if (typeof fila =='function')
			break
		
		cd=fila['cd'].toLowerCase()
		var tr=this.tramites[cd]
		if (tr) {
			tr.ejecutado=true
			tr.uEjecucion=fila['cd_usuario']
			tr.fEjecucion=fila['fecha']
			}
		}
	for (var i in this.pendientes){
		var fila=this.pendientes[i]
		if (typeof fila =='function')
			break
		
		cd=fila['cd'].toLowerCase()
		var tr=this.tramites[cd]
		if (tr) {
			tr.pendiente=true
			tr.uPendiente=fila['cd_usuario']
			tr.fPendiente=fila['fecha']
			}
		}
}
function cdOrig(cd){
	if (cd==null) 
		return
	return cd.split('/')[0]
}
MapaTramitacion.prototype.enchufa1=function(cd, ds_tramites, sub, dll, hijos, padres, firmaTramites){
	var cdLiteral=this.sacaCD(cd)
	
	var tram
	
	if (cd in this.tramites)
		tram=this.tramites[cd]
	else {
		var cdOriginal=cdOrig(cd)
		var ds=ds_tramites[cdOriginal]
		
		tram=new Tramite(cdLiteral, ds)
		this.tramites[cd]=tram
		}
		
	tram.sub=sub && sub[cd]? sub[cd] : null
	
	tram.dll=dll[cd]
	if (tram.dll && tram.dll['firma']){
		delete tram.dll['firma']
		tram.firmaArchivo=true
		}
	
	if (firmaTramites.contains(cdOriginal) )
		tram.firmaTramite=true
		
	tram.hijos=hijos[cd]
	
	for (var cdpadre in padres[cd])
		tram.padre=cdpadre.toLowerCase()
		
	return tram
}
///////
MapaTramitacion.prototype.cambiaEscala=function(z){
	this.zoom=z
	var escala=1/(z==null?1:z)
	
	this.divFilas=[]
	this.contenedor.style.overflow='auto'
	this.contenedor.className='escala'+z.toString().replace('.', '')
}
MapaTramitacion.prototype.pintaLeyenda=function() {
	if ( control('leyenda') || !this.tienePermiso)
		return
	
	var literalCamino=creaObjProp('a', {className:'texto gris', 'texto':'Camino'})
	
	var self=this
	this.ctlListaCaminos=creaObjProp('span', {texto:'(Click para seleccionar un camino)', minWidth:400, id:'listaCaminos', onclick:function(){self.muestraListaCaminos()} })
	this.cargaListaCaminos(this)
	
	var btnNuevoCamino=creaObjProp('a', {className:'botonera imagen', onclick:fnNuevoCamino(), title:'Nuevo camino', id:'nuevoCamino'} )
	
	var literalTramite=creaObjProp('a', {className:'texto gris', 'texto':'Trámite'})
	var btnNuevoTramite=creaObjProp('a', {className:'botonera imagen', onclick:fnNuevoTramite(), title:'Nuevo trámite', id:'nuevoTramite'} )	
	
	var btnTam1=creaObjProp('a', {className:'botonera imagen', id:'tamanho1', onclick:fnCambiaEscala(.25), title:'Tamaño pequeño'})
	var btnTam2=creaObjProp('a', {className:'botonera imagen', id:'tamanho2', onclick:fnCambiaEscala(.5), title:'Tamaño mediano'})
	var btnTam3=creaObjProp('a', {className:'botonera imagen', id:'tamanho3', onclick:fnCambiaEscala(1), title:'Tamaño grande'})
	
	var btnMostrarLibres=creaObjProp('a', {className:'botonera', onclick:fnMostrarLibres(), title:'Mostrar libres', id:'libres'} )
	
	var leyenda=Frm.prototype.creaBarraHerramientas([
				literalCamino, this.ctlListaCaminos, btnNuevoCamino,
					Frm.prototype.creaSeparadorBotonera(),
				literalTramite, btnNuevoTramite, 
					Frm.prototype.creaSeparadorBotonera(),
				btnTam1, btnTam2, btnTam3,
					Frm.prototype.creaSeparadorBotonera(),
				btnMostrarLibres])
	leyenda.id='leyenda'
	this.contenedor.appendChild(leyenda)

	//lo colocamos en la esquina superior izquierda del contenedor:
	if (this.contenedor.style.top!=''){
		leyenda.style.left=this.contenedor.style.left
		leyenda.style.top=this.contenedor.style.top
		}
	}
MapaTramitacion.prototype.pintar=function(){
	borraHijos(this.contenedor)
	
	this.pintaLeyenda()
	if (this.idCamino==null)
		this.contenedor.appendChild(creaObjProp('div', {className:'noHayFilas', texto:'Selecciona o crea algún camino para empezar'}))
	else if (this.tramites==null)
		this.contenedor.appendChild(creaObjProp('div', {className:'noHayFilas', texto:'No hay ningún trámite en este camino'}))
	//Nodos
	if (!this.md)
		return
	var restar= (this.mostrarLibres? 0 : this.totalFilasLibres-1)
	
	//pintamos un boliche para averiguar sus dimensiones y hacer los calculos en base a ellos
	var boliche=creaObjProp('div', {className:'tram', 'style.left': -1000, 'style.top':-1000})		
		this.contenedor.appendChild(boliche)
		var bb = $(boliche)

		Tramite.prototype.ancho=bb.outerWidth()
		Tramite.prototype.alto=bb.outerHeight()
		borra(boliche)
	this.cambiaEscala(this.zoom)
						
        for (i=0; i<this.md.length; i++) {
		for (j = 0; j<this.md[0].length; j++) {
			k = this.md[i][j] + ""
			if (k != "" && typeof k != 'function') {
				if (this.mostrarLibres || j>=this.totalFilasLibres){
					this.pintaRec(i - 1, j - 1 - restar, k, Exists(this.copias,k))				
					}
				}
			}
		}
		
	
	//Relaciones 
	for (var rel in this.relaciones) {//.keys
		var temp=rel.split(sep)
		
		var n = temp[0]
		var m = temp[1]
		
		this.pintaRelacion (rel, n, m)
		}
}
function arreglaNombre(n){
	return n.replace('/', xsep).replace('.', xpunto)
	}
MapaTramitacion.prototype.pintaRelacion=function(id, n, m)  {
	n=arreglaNombre(n)
	m=arreglaNombre(m)
	
	var $origen=$('#'+n), $destino=$('#'+m)
	var origen=$origen.offset(), destino=$destino.offset()
	
	var cont=$('#cuerpo')[0]
	var raya
	var y=$origen.innerHeight()/2
	if (origen.top == destino.top ) 
		raya=creaObjProp('div', {id:id, className:'raya rayah', 'style.left':origen.left+cont.scrollLeft, 'style.top':origen.top+y+cont.scrollTop, 'style.width':destino.left-origen.left, 'style.height':1} )
	else {
		//___
		//  |
		//  |___
		var x = parseInt( destino.left-origen.left+$origen.innerWidth()  ) / 2
		
		raya=creaObjProp('div', {id:id, className:'raya rayav', 'style.left':origen.left+x+cont.scrollLeft, 'style.top':origen.top+y+cont.scrollTop, 'style.width':destino.left-origen.left-x, 'style.height':destino.top-origen.top})
		}
		
	this.contenedor.appendChild(raya)
	}
MapaTramitacion.prototype.pintaRec=function(i, j, cdOriginal, esCopia) {//pinta el boliche
	var divFilas
	while (!this.divFilas[j]){
		divFilas=creaObjProp('div', {className:'filaMapa', id:'fila'+j})
		this.divFilas.push( divFilas)
		this.contenedor.appendChild(divFilas)
		}
	divFilas=this.divFilas[j]
		
	var desc

	var cdOriginal=cdOriginal.toLowerCase()
	
	var tramite=this.tramites[cdOriginal]
	var idPadre=tramite.padre
	tramite.pinta(divFilas, esCopia, cdOriginal, i, j, idPadre)
	}
////////////////
MapaTramitacion.prototype.nuevoCamino=function(){
	nuevoFormulario(this.caminoDatosFRM(), function(ret){mt.retornoCamino(ret)} )
}
MapaTramitacion.prototype.retornoCamino=function(datos){
	var param={	'aplicacion':aplicacion, 
				'accion':'guardaCamino', 
				'ncd':datos.CD_Camino, 
				'ds':datos.DS_Camino,
				'tb':datos.tb, 'tt':datos.tt}
				
	loadJSONDocPost('json', param, function(){
							mt.idCamino=datos.CD_Camino
							mt.actualizar()
							})
}
MapaTramitacion.prototype.caminoDatosFRM=function(cd, ds, tb, tt){
	return {"numTramites" : 0,"camino" : 0,"tramite" : 'Nuevo camino',"tipo" : 'frm',"titulo":'Nuevo camino de tramitación', "alto" : 250,'controles':[
		{'tipo' : 'lvwNumero','id' : 'CD_Camino','maxlength' : 10,'valor' : '','obligatorio': true,'bloqueado': cd!=null, 'ds' : 'Código'},
			{"tipo" : 'lvw',"id": 'DS_Camino', "maxlength":250,"valor":'',"obligatorio":true,"bloqueado": false,"ds" : 'Descripción'},
		
		{"tipo" : 'bsc',"id" : 'DIC_Tablas.nombre',"obligatorio" : false,"ds" : 'Tabla base',"colsQueBloquea" : 0,"colsBloqueadas" : 0,"bloqueado" : false,'controles':[
			{"tipo": 'lvw',"id" : 'tb',"maxlength" : -20,"valor" : null,"obligatorio" : false,"bloqueado" : false,"ds" : ''}
			]},
		{"tipo" : 'bsc',"id" : 'DIC_Tablas.nombre',"obligatorio" : false,"ds" : 'Tabla de trámites',"colsQueBloquea" : 0,"colsBloqueadas" : 0,"bloqueado" : false,'controles':[
			{"tipo": 'lvw',"id" : 'tt',"maxlength" : -20,"valor" : null,"obligatorio" : false,"bloqueado" : false,"ds" : ''}
			]}
		]}
}
////////////////
function fnCambiaEscala(z){
	return function(){
		zoom=z
		mt.cambiaEscala(zoom)
		mt.pintar()
		}
}
function fnMostrarLibres(){
	return function(){
		mt.mostrarLibres=!mt.mostrarLibres
		mt.pintar()
		}
}
function fnNuevoTramite(){
	return function(){
		var tt=new Tramite()
		tt.esNuevo=true
		tt.editar()
		}
	}
function fnNuevoCamino(){
	return function(){mt.nuevoCamino()}
}
////////////////
Jerarquia.prototype.colocaHijos=function(i, j, kpadre){
	//si falla algo es porque hace falta pasar i como valor, no como referencia
	var n, k, temp, nk, rel, padreSinPos, p
	i++
	if (Exists(this.nodosHijos, kpadre)==true) {
		if (length(this.nodosHijos[kpadre]))  {
			//1er bucle para replicar si hiciera falta
			for (k in this.nodosHijos[kpadre]) {
				print (kpadre + "/" + k)
				if (Exists(this.posiciones,k)) {
					print ('hay que replicar ' + k)
					//hay que replicar k
					nk = k + '/' + length(this.copias)
					this.replicaNodo (k, nk, kpadre)
					}
				}
			//2º bucle para colocarlos en su sitio
			for (k in this.nodosHijos[kpadre]){
				j = this.siguienteJ(this.md,i, j)

				rel = kpadre + sep + k
				if (Exists(this.relaciones, rel)==false)
					this.relaciones[rel]=rel
				this.md[i][j] = k
				this.posiciones[k]= i + sep + j //key, item
				temp = this.colocaHijos(i, j, k)
				if (typeof temp != 'undefined') 
					j = temp
				}
			
			//bucle 2a: hay que hacerlo así porque el número de hijos puede cambiar en el dinámicamente en el bucle anterior y el bucle no se entera en ese caso
			for (k in this.nodosHijos[kpadre]){
				if (this.posiciones[k]==null) {
					print ('>> '+k+' NO tiene posición ¿será una duplicado?')
					
					j = this.siguienteJ(this.md,i, j)

					rel = kpadre + sep + k
					if (Exists(this.relaciones, rel)==false)
						this.relaciones[rel]=rel
					this.md[i][j] = k
					this.posiciones[k]= i + sep + j //key, item
					temp = this.colocaHijos(i, j, k)
					if (typeof temp != 'undefined')
						j = temp
					}
				}
					
			//3ª bucle para replicar:cuando uno tiene varios padres, replicamos sobre aquél padre q no tiene posición
			for (k in this.nodosHijos[kpadre]) {
				while (length(this.nodosPadres[k])>1) {
					padreSinPos=null
					for (p in this.nodosPadres[k]){
						if (Exists(this.posiciones, k)==false){
							padreSinPos=p
							print (p + ' NO tiene pos')
							}
						else 
							print (p + ' si tiene pos')
						}
					if (padreSinPos==null){
						print ('ERROR: habría que replicar '+k)
						for (p in this.nodosPadres[k]){
							if (p!=kpadre)
								padreSinPos=p
							}
						}
					    print ('-->como hijo de '+padreSinPos)
					    //hay que replicar k
					    nk = k + '/' + length(this.copias)
					    this.replicaNodo (k, nk, padreSinPos)
					    //~ this.colocaHijos(i, this.siguienteJ(this.md,i, j) , kpadre)
					    }
				}
			}
		return j
		}
}
Jerarquia.prototype.siguienteJ=function(xmd, i, j){
    // Para decidir la posición del nodo en el array
    var ok = true
    for (var jj = j; jj<this.limite; jj++) {
        for (ii = i; ii<this.limite; ii++) {
            if ( xmd[ii] != ''){
                if ( xmd[ii][jj]  != '')  {
                    ok = false
                    break
                    }
                }
            }
        
        if (ok==true) 
            return jj
        ok = true
        }
    }
Jerarquia.prototype.clave=function(k, arr){
    var i
    for (i in arr) {
        if (i == k) 
            return i
        }
    //si llega hasta aquí, es que no lo ha encontrado
    return -1
    }

Jerarquia.prototype.buscaPadres=function(cl){
    var n
    var m
    var ret=''
    var k
    
    for (k in this.nodosHijos) {//keys
        if (length(this.nodosHijos[k])>0) {
            for (m in this.nodosHijos[k])
                if (m == cl) 
                    ret += '/' + k
            }
        }
    if (ret != '')
        return ret.split('/')
    else 
	return -1
}
Jerarquia.prototype.replicaNodo=function(k, nk, padrePreferido) {
	var kpadre
	var col

	print ('duplicamos ' + k + '->' + nk)
	//nos quedamos con la copla de los copiados
        this.copias[nk]=nk
	//duplicamos
        this.nodos[nk]= nk
	//establecemos la nueva paternidad   
        var temp = this.nodosPadres[k]
        
        if (padrePreferido != '' ) 	
		kpadre = padrePreferido
        else if (clave(k, temp) != -1) //si es padre de sí mismo, lo dejamos
		kpadre = temp[clave(k, this.nodosPadres[k])]
        else
		kpadre = temp[claveDelIndice(temp, 0)]
            
	//a) al antiguo padre le quitamos este hijo
        delete this.nodosHijos[kpadre][k] //this.nodosHijos[kpadre].Remove (k)
        this.nodosHijos[kpadre][nk]= nk
	//b) al hijo le quitamos este padre
        delete this.nodosPadres[k][kpadre] //this.nodosPadres(k).Remove (kpadre)
        this.nodosPadres[nk]=new Object() 
        this.nodosPadres[nk][kpadre]= kpadre
}
Jerarquia.prototype.claveDelIndice=function(arr, indice){
    var i=0
    for (var k in arr) {
        if (i==indice) 
		return k
        i++
        }
    }
Jerarquia.prototype.inicializaVariables=function(){ 
	this.limite=1 //límite iteraciones: se calcula 
	
	////////////////////////
	this.copias=new Object() //los replicados
	this.nodosPadres=new Object()
	this.nodosHijos=new Object()
	this.nodos=new Object()

	this.md = new Array() //matriz con los datos de las posiciones de los nodos
	this.posiciones=new Object()
	this.relaciones=new Object()
	
}
function Jerarquia(jer, totalLibresPorLinea, datos){
	this.sub=datos.sub
	this.datos=datos
	
	this.inicializaVariables()
	this.totalLibresPorLinea=totalLibresPorLinea
	
	var i, j,par,temp
	//1º todos como nodos
	var col
	var jer_split=jer.toLowerCase().split(pipe)
	var rama_split
	var k
	
	for (j=0; j< jer_split.length; j++) {
		rama=jer_split[j]
		rama_split=rama.split('/')
		for (i=0; i<rama_split.length; i++ ) {
			k=rama_split[i]
			if (Exists(this.nodos, k)==false && k != '') 
				this.nodos[k]=k
			}
		}
	this.limite=length(this.nodos)+1
	//2º rellenamos la lista de hijos de cada nodo
	var padre
    
	for (var i_rama=0; i_rama<jer_split.length; i_rama++) {
		rama=jer_split[i_rama]

		temp = rama.split('/')
		if (temp.length > 1 )  {
			this.nodosHijos[temp[0]]=new Object() 

			padre = this.nodosHijos[temp[0]]
			for (i = 1; i<temp.length; i++){//empezamos en 1
				if (temp[i] != '')
				    padre[temp[i]]=temp[i]
				}
			}
		}
    
	//3º rellenamos la lista de padres de cada nodo
	var k
	for (k in this.nodos){//.keys
		temp = this.buscaPadres(k)
		if (temp != -1 )  {
			col = new Object() 
			this.nodosPadres[k]= col

			for (i=0;i<temp.length;i++) {
				if (temp[i]!="")
					this.nodosPadres[k][temp[i]]=temp[i]
				}
			}
		}
	
	////////////
	//3b
	if (agruparSUB)
		this.agruparSUB()
	//////////		
	
	//limpiamos la colección
	for (k in this.nodosHijos) {
		if (length(this.nodosHijos[k]) == 0) 
			delete this.nodosHijos[k]
		}
	for (k in this.nodosPadres) {
		if (length(this.nodosPadres[k]) == 0)
			delete this.nodosPadres[k]
		}

	// duplicamos los nodos que tienen varios padres
	i = 1
	var nk
	var kpadre

	//apañamos el array para poder usarlo
	for (i=0;i<this.limite;i++) {
		this.md[i]=new Array()
		for (j=0;j<this.limite;j++)
			this.md[i][j]=''
		}
    
	//4º buscamos los que no tienen padres ni hijos (para empezar a dibujar por ellos)
	i = 0
	j = 1
	for (k in this.nodos){
		if (this.noTienePadresniHijos(k)) {	
			if (k != '' ) {
				i++
				delete this.nodos[k]	
				this.md[i][j] = k
				if (i >= this.totalLibresPorLinea )  {
					j++
					i = 0
					}
				}
			}
		}
	this.totalFilasLibres=j
	//5º al lío:
	this.posiciones = new Object() 
	this.relaciones = new Object() 
	i = 1
	j = this.siguienteJ(this.md, i, j)
    
	for (k in this.nodos){
		if (Exists(this.posiciones, k) == false && this.noTienePadres(k) ) {
			this.posiciones[k]=i + sep + j
			this.md[i][j] = k
			this.colocaHijos (i, j, k)

			j = this.siguienteJ(this.md,i, j)
			}
	}
	//y el resto
	for (k in this.nodosHijos) {
		if (Exists(this.posiciones,k)==false) {
			this.posiciones[k]=i + sep + j
			this.md[i][j] = k
			this.colocaHijos (i, j, k)
			j = this.siguienteJ(this.md,i, j)
			}
		}
}
Jerarquia.prototype.noTienePadresniHijos=function(k){ //no tiene padre, hijos, ni aparece como SUB de otro trámite
	return 	(Exists(this.nodosPadres, k) == false) 	&& 
			(Exists(this.nodosHijos, k) == false)  	&& 
			(this.esSUB(k)==false)
	}
Jerarquia.prototype.esSUB=function(k){ //no aparece como SUB de otro trámite	
	for (var i in this.sub){
		for (var tipoSub in this.sub[i]){
			for (var ihijo in this.sub[i][tipoSub]){
				var hijo=this.sub[i][tipoSub][ihijo] 
				if (hijo==k)
					return true
				}
			}
		}
	return false
	}
Jerarquia.prototype.noTienePadres=function(k){ //no tiene padre, ni aparece como SUB de otro trámite
	return 	(Exists(this.nodosPadres, k) == false) 	&& 
			(this.esSUB(k)==false)
	}
Jerarquia.prototype.transfiereHijos=function(desde, hasta){
	this.nodosHijos[hasta]=this.nodosHijos[hasta] || {}			
	for (var xx in this.nodosHijos[desde]){
		this.nodosHijos[hasta][xx]=this.nodosHijos[desde][xx]
		
		this.nodosPadres[xx]=this.nodosPadres[xx] || {}
		this.nodosPadres[xx][hasta]=hasta
		}
	}
Jerarquia.prototype.transfiereSUBs=function (desde, hasta){
	for (var tipo in this.sub[desde]){		
		this.sub[hasta][tipo]=this.sub[hasta][tipo] || []
		
		for (var id in this.sub[desde][tipo]){
			var p=this.sub[desde][tipo][id]
			if (typeof p == 'function') continue
			
			this.sub[hasta][tipo].push(p)
			this.transfiereDLLs(p, hasta)
			}
		}
	}
Jerarquia.prototype.transfiereDLLs=function(desde, hasta){
	this.datos.dll[hasta] = this.datos.dll[hasta] || []
	for (var id in this.datos.dll[desde]){
		this.datos.dll[hasta][id]=this.datos.dll[hasta][id] || []
		for (var i in this.datos.dll[desde][id]){
			var p=this.datos.dll[desde][id][i]
			if (typeof p == 'function') continue
			
			this.datos.dll[hasta][id].push( p )
			}
		}
	}
Jerarquia.prototype.agruparSUB=function(){
	var borrarHijosAlAcabar=[]
	for (var idpadre in this.sub){
		if (this.sub[idpadre].sub==null)
			continue
		for (var k=0; k<this.sub[idpadre].sub.length; k++){
			var idhijo=this.sub[idpadre].sub[k]
			
			this.transfiereHijos(idhijo, idpadre)
			this.transfiereDLLs(idhijo, idpadre)
			borrarHijosAlAcabar.push(idhijo)
			}
		}	
	for (var k=0; k<borrarHijosAlAcabar.length;k++){
		var id=borrarHijosAlAcabar[k]
		delete this.nodosHijos[id]
		delete this.nodos[id]
		}
		
	for (var kk in this.nodosPadres){
		for (var k=0; k<borrarHijosAlAcabar.length;k++){
			var id=borrarHijosAlAcabar[k]
			delete this.nodosPadres[kk][id]
			}
		}
	
	//y ahora transferimos los SUB/FOR de los SUBs
	for (var id in this.sub){
		if (!this.nodosPadres[id]){
			var idPadre=this.buscaPadreDeSUB(id)
			if (idPadre) 
				this.transfiereSUBs(id, idPadre)
			}
		}
	
	}
Jerarquia.prototype.buscaPadreDeSUB=function(id){
	for (var idPadre in this.sub){
		var padre=this.sub[idPadre]
		for (var tipoSub in padre){
			for (var idSub in padre[tipoSub]){
				if (padre[tipoSub][idSub]==id)
					return idPadre
				}
			}
		}
	}
///////////////////////////////////////////////////////
function montaPorCamino(idCamino, cont, aplicacion, zoom, objetoBase, tramCentrado, tienePermiso) {
	zoom= zoom || 0.5
	if (tramCentrado=='null')
		tramCentrado=null
	if (objetoBase=='null')
		objetoBase=null
	mt=new MapaTramitacion(idCamino, cont, aplicacion, zoom, objetoBase, tramCentrado, tienePermiso)
	}
///////////////////////////////////////////////////////
MapaTramitacion.prototype.cargaListaCaminos=function(mapa){
	if (this.divCaminos!=null)
		borra(this.divCaminos)
	loadJSONDoc( 	'json',
				{ 'accion':'listaCaminos', aplicacion: aplicacion } ,
				function(req){
					var datos=xeval(req)				
					mapa.setListaCaminos(datos)
					}
				)
}
MapaTramitacion.prototype.muestraListaCaminos=function(){
	$(this.divCaminos).toggle(jquerycssmenu.fadesettings.overduration)		
	}
MapaTramitacion.prototype.setListaCaminos=function(datos){
	this.divCaminos=creaObjProp('div', {className:'divCaminos', 'style.display':'none'})
	control('leyenda').appendChild( this.divCaminos )
	
	this.lvw=new Lista('lvwCaminos', true)
	var xcont=this.lvw.pintaContenedor(true)
	this.divCaminos.appendChild( xcont )
	
	this.preparaTodoParaGuardarCookie()
	
	var self=this
	var onclick=function(fila){self.clickListaCaminos(fila.id)}
	this.lvw.rellenaLista(	['Camino', 'Descripción', 'Tramitación reglada', 'Núm. de trámites'], 
							['cd', 'ds','tt','num_tramites'], 
							datos, 'cd', onclick, onclick, false /*multiselect*/, null, null, ['lvwNumero', 'lvw', 'lvwBoolean', 'lvwNumero'])
	
	this.caminos=datos
	
	if (this.idCamino!=null) {
		for (var i=0; i<datos.length; i++){
			var cam=datos[i]
			if (cam.cd==this.idCamino)
				$('span#listaCaminos').text( '['+cam.cd+'] '+cam.ds )
			}
		}
	}
MapaTramitacion.prototype.clickListaCaminos=function(cam){//(cam.CD, aplicacion)
//	var cam=this.ctlListaCaminos.value
	this.cambiaCamino(cam)
}
MapaTramitacion.prototype.cambiaCamino=function(nc){
	window.location='mapa.jsp?aplicacion='+aplicacion+'&camino='+nc+'&zoom='+mt.zoom
}
MapaTramitacion.prototype.preparaTodoParaGuardarCookie=function(){
	var self=this
	exp.mdActivo=function(){return self}
	}
MapaTramitacion.prototype.getControl=function(){
	var thead=$('body.mapatram div#lvwCaminos thead')[0]
	return new X(thead)
	}
/////////////
function X(thead){
	this.thead=thead
	}
X.prototype.id='mapatram.combo'
X.prototype.salvaDato=function(propiedad, valor){
	salvaDato(this.id, propiedad, valor)
	}
X.prototype.recuperaDato=function(propiedad){
	return recuperaDato(this.id, propiedad)
	}

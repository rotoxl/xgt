var __panelSQL__='__panelSQL__', __editor__='__editor__'
var sinNivel='nivelVacio', sinTitulo='Sin título '
var _ifrVisible=true

function range(n){
	var ret=[]
	for (var i=0; i<n;i++)
		ret.push(i)
	return ret
}
////////////////////////////////////////////
function FormularioEditorNiveles(idxBloqueo, idNivel){
	Frm.call(this, 'editorNiveles', idxBloqueo)
	
	this.porcentajeAltoFrm=95
	this.porcentajeAnchoFrm=88 // %
	
	this.altoPie=0//este no lleva pie
	
	this.titulo='Editor de SQL y Jython'
	this.rutaIcono='./fijo/lved22.png'
	this.idNivel=idNivel
	this.nivel=this.nivelVacio()
	this.niveles={}
	this.niveles[idNivel]=this.nivel
}
FormularioEditorNiveles.prototype= new Frm
FormularioEditorNiveles.prototype.nivelVacio=function(){
	return {'cd':null, sql:'--escriba aquí el texto sql del nivel\n', 'params':[], tipoObjeto:''}
	}
FormularioEditorNiveles.prototype.onResize=function(inicializar){
	if (editorNivelesActivo==null) {
		xRemoveEventListener(window, 'resize')
		return
		}
	
	var w=$('#'+this.idx).innerWidth()
	var h=this.altoCuerpo
	var limite=h/8
		
	if (inicializar==true || this.splitter==null){
		var posVertical=Number(recuperaDato('editorNiveles.splitter', 'top'))
		if (isNaN(posVertical) || posVertical>(h-limite))
			posVertical=3*h/5 
		
		this.splitter = new xSplitter('splitterH',  0, 0, w, h, false, 7,     posVertical,    limite,     true, 0)
		
		var self=this
		var fnOnResize=function(){self.onResize(false)}
		
		this.splitter.setFnOnBarDrag(fnOnResize)
		xAddEventListener(window, 'resize', fnOnResize, false)
		}
	else {
		var posVertical=control('sepHorizontal').offsetTop
		this.splitter.paint(w, h, posVertical, limite)
		
		salvaDato('editorNiveles.splitter', 'top', posVertical)
		}
}
//~ FormularioEditorNiveles.prototype.confirmarSalir=function(){
	//~ this.salvaNivelesAbiertos()
	//~ return null //;var event=window.event
//~ }
FormularioEditorNiveles.prototype.preactivate=function(){
	this.onResize(true)
	
	var panel=control(__panelSQL__)
	this.xeditor = new CodeMirror(panel, 
							{parserfile: "parsesparql.js",
							stylesheet: "fijo/codemirror/css/sparqlcolors.css",
							path: "fijo/codemirror/js/",
							
							initCallback:function(){
								//~ editorNivelesActivo.recuperaNivelesAbiertos()
								editorNivelesActivo.cargaNivel(editorNivelesActivo.idNivel)
								},
							onChange:function(){editorNivelesActivo.hayCambios()},
							saveFunction:function(){editorNivelesActivo.guardarNivel();return false},
							height:'100%',
							lineNumbers:true
							})
	this.xeditor.setTabMode('shift')
	this.xeditor.setIndentUnit(4)
							
	var datosInicio={columnas:["cd"], tipos:["lvw"], parametros:["Aún no se ha ejecutado nada"], filas:[ ['Ejecuta el SQL para mostrar aquí los resultados'] ]}
	this.mostrarResultados(datosInicio)
}
FormularioEditorNiveles.prototype.anhadeAsistentes=function(ul, div ) {
	var asistentes=this.asistentes()
	
	for (var k in asistentes) {
		var ml=asistentes[k]
		if (!ml) 
			continue
		
		var enlace
		if (ml.ds.startsWith('-'))
			enlace=creaObjProp('a', {className:'menuML botonera separador', texto:ml.ds.substring(1)})
		else
			enlace=creaObjProp('a', {onclick:fnEnchufaAsistente(ml.id), className:'menuML botonera '+ml.css, texto:ml.ds, hijo:creaObjProp('span', {className:'dsExtendida', texto:ml.dsExtendida})})

		ul.appendChild( creaObjProp('li', {hijo:enlace}))
		}
}
FormularioEditorNiveles.prototype.pintaPie=function() {return creaObjProp('div')}
FormularioEditorNiveles.prototype.pintaCuerpo=function() {
	//botonera
	
	var btnSalir= this.creaObj('a', 'botonera texto imagen gris', creaSpan('Salir') , 'btnSalir', null, function(){editorNivelesActivo.cancelar();return false})
	//--------------------
	var btnNuevo= this.creaObj('a', 'botonera texto imagen gris', creaSpan('Nuevo') , 'btnRegistro', null, function(){editorNivelesActivo.nuevoNivel();return false})
	
	var btnNuevoAsistido=this.creaObj('a', 'botonera texto imagen gris dropdownmenu', creaSpan('Asistente') , 'btnAsistente', null)
	
	var btnAbrirNivel = this.creaObj('a', 'botonera texto imagen gris', creaSpan('Abrir') , 'btnAbrir', null, function(){editorNivelesActivo.abrirNivel();return false})
	var btnRecuperaHistorial = this.creaObj('a', 'botonera texto imagen gris', creaSpan('Recientes') , 'btnHistorial', 'Recuperar los recientes', function(){editorNivelesActivo.recuperaNivelesAbiertos();return false})
	
	var btnEliminarNivel = this.creaObj('a', 'botonera texto imagen gris', creaSpan('Eliminar') , 'btnEliminar', null, function(){editorNivelesActivo.eliminarNivel();return false})	
	//--------------------
	var btnGuardar = this.creaObj('a', 'botonera texto imagen gris', creaSpan('Guardar') , 'btnGuardar', null, function(){editorNivelesActivo.guardar();return false})	
	var btnGuardarComo = this.creaObj('a', 'botonera texto imagen gris', creaSpan('Guardar como') , 'btnGuardarComo', null, function(){editorNivelesActivo.guardarComo();return false})	
	//--------------------
	var btnEjecutarSQL = this.creaObj('a', 'botonera texto imagen gris', creaSpan('Ejecutar') , 'btnEjecutarSQL', 'Ejecutar el trozo de texto seleccionado (si no hay texto seleccionado, se ejecutará el total del texto)', function(){editorNivelesActivo.ejecutarSQL();return false})	
	var btnEjecutarNivel = this.creaObj('a', 'botonera texto imagen gris', creaSpan('Probar nivel') , 'btnEjecutarNivel', 'Probar nivel (se ejecuta el nivel definido en memoria, con los parámetros del nodo activo)', function(){editorNivelesActivo.ejecutarNivel();return false})	
	
	var btnVerParametros = this.creaObj('a', 'botonera texto imagen gris', creaSpan('Parámetros') , 'btnParametros', null, function(){editorNivelesActivo.verParametros();return false})	
	
	this.btnEjecutarSQL=btnEjecutarSQL
	this.btnEjecutarNivel=btnEjecutarNivel
	this.btnVerParametros=btnVerParametros
	//--------------------
	var sublista=creaObjProp('ul')
	this.anhadeAsistentes(sublista, btn)
	var btn=this.creaBarraHerramientas( [btnSalir, this.creaSeparadorBotonera(), 
							btnNuevo, btnAbrirNivel, 
							this.creaSeparadorBotonera(), btnRecuperaHistorial,
							this.creaSeparadorBotonera(), 
							btnEliminarNivel, btnGuardar, btnGuardarComo, this.creaSeparadorBotonera(), 
							btnEjecutarSQL, btnEjecutarNivel, 
							this.creaSeparadorBotonera(),
							btnVerParametros,
							[btnNuevoAsistido, sublista]] )
	ponEstilo(btn, 'gris')
	//splitter
	var splitter=creaObjProp('div', {id:'splitterH', className:'clsSplitter horizontal', 'style.height':this.altoCuerpo})
	
	this.panelSQL=creaObjProp('div', {className:'section clsPane cargando', 	id:__panelSQL__})
	this.panelResultados=creaObjProp('div', {className:'section clsPane', id:'__panelResultados__'} )
	
	splitter.appendChild( this.panelSQL )
	splitter.appendChild( this.panelResultados )
	splitter.appendChild( creaObjProp('div', {className:'clsDragBar', id:'sepHorizontal'} ) )
	
	this.lvw=new Lista('__panelResultados__', false)
	
	this.listaNivelesAbiertos=creaObjProp('div', {className:'nav botonera', id:'listaNivelesAbiertos'})
	
	var cuerpo=creaObjProp('div', {'style.width': this.anchoFrm, className:'cuerpo sinPie', hijos:[btn, this.listaNivelesAbiertos, splitter]})
	return cuerpo
}
function fnEnchufaAsistente(id){
	return function(){editorNivelesActivo.enchufaAsistente(id)}
}
FormularioEditorNiveles.prototype.enchufaAsistente=function(id){
	ocultaMenusPanSup()
	
	var as=this.asistentes()[id]
	
	this.nivel=this.nivelVacio()
	this.nivel.params=as.params
	this.nivel.sql=as.sql
	this.nivel.cd=as.id
	
	var textoActual=this.xeditor.selection() || this.xeditor.getCode()
	if (textoActual==this.nivelVacio().sql)
		this.xeditor.setCode(as.sql)
	else
		this.xeditor.replaceSelection(as.sql)
}
FormularioEditorNiveles.prototype.activarNivel=function(idx){
	var nuevoNivel=this.niveles[idx]
	if (!nuevoNivel || !nuevoNivel.idx)
		return
		
	if (this.nivel){
		if (this.nivel.pestanha) quitaEstilo(this.nivel.pestanha, 'on')
		
		this.nivel.textoModifEditor=this.xeditor.getCode()
		}
	//cambiamos de nivel
	this.nivel=this.niveles[idx]
	this.idNivel=this.nivel.idx
	
	ponEstilo(this.nivel.pestanha, 'on')
	this.cambiaTitulo(this.idNivel)
	
	if (this.nivel.tipoObjeto=='guion') {
		ponEstilo(this.btnEjecutarSQL, 'estado_disabled')
		ponEstilo(this.btnEjecutarNivel, 'estado_disabled')
		ponEstilo(this.btnVerParametros, 'estado_disabled')
		}
	else {
		quitaEstilo(this.btnEjecutarSQL, 'estado_disabled')
		quitaEstilo(this.btnEjecutarNivel, 'estado_disabled')
		quitaEstilo(this.btnVerParametros, 'estado_disabled')
		}
		
	this.xeditor.setCode(this.nivel.textoModifEditor)
}
FormularioEditorNiveles.prototype.cargaNivel=function(idNivel, tipo, segundoPlano) {
	if (tipo==undefined) tipo='nivel'
	ponEstilo(this.panelSQL, 'cargando')
	var params= {accion:'traeNivel', tipo:tipo, aplicacion:exp.aplicacion, nivel:idNivel}
	loadJSONDoc ( 'json', params, function(req){
							editorNivelesActivo._cargaNivel( xeval(req), segundoPlano )
						} )
}
FormularioEditorNiveles.prototype._cargaNivel=function(nuevos, dejarEnSegundoPlano) { //sql, params
	for (var i=0; i<nuevos.length;i++){
		var nivel=nuevos[i]
		
		nivel.idx=nivel.idx || nivel.cd
		
		if (nivel.cd==sinNivel){
			nivel.sql=''
			nivel.cd=sinTitulo+numNiveles++
			}
		else
			nivel.sql=nivel.sql || ''
		
		nivel.tipoObjeto=nivel.tipoObjeto || 'nivel'
		
		this.niveles[nivel.idx]=nivel
		nivel.textoModifEditor=nivel.sql
		
		var ultimaEdicion=(nivel.f_modificacion && nivel.f_modificacion!='')?' (modificado: '+nivel.f_modificacion+')':''
		
		var botonCerrar=creaObjProp('img', {className:'cerrarNivel', onclick:fnCerrarNivel(nivel.idx)})
		var hijos=[]
		if (nivel.tipoObjeto.toLowerCase() != 'nivel')
			hijos.push(creaObjProp('span', {className:'marron', texto:nivel.idx}))
		hijos.push(botonCerrar)
		
		nivel.pestanha=creaObjProp('span', {className:'nivel', id:'span'+nivel.idx, texto:nivel.cd, 'hijos':hijos, onclick:fnActivarNivel(nivel.idx)})
		this.listaNivelesAbiertos.appendChild(nivel.pestanha)
		}
	// activamos el 1º
	if (!dejarEnSegundoPlano) this.activarNivel( nuevos[0].idx )
	quitaEstilo(this.panelSQL, 'cargando')
}
function fnActivarNivel(idx){
	return function(){editorNivelesActivo.activarNivel(idx)}
	}
function fnCerrarNivel(idx){
	return function(){editorNivelesActivo.cerrarNivel(idx)}
	}
////////////////////
FormularioEditorNiveles.prototype.hayCambios=function(){
	this.cambiaTitulo(this.niveles[this.idNivel].cd, true)
	}
FormularioEditorNiveles.prototype.cambiaTitulo=function(idNivel, modif){
	var nuevoTitulo=creaObj('p', null, null, (modif?'* ': '')+idNivel + ' - Editor de SQL y Jython')
	this.pTitulo.parentNode.replaceChild(nuevoTitulo, this.pTitulo)
	this.pTitulo=nuevoTitulo
}
FormularioEditorNiveles.prototype.cancelar=function(){
	this.salvaNivelesAbiertos()
	this.ocultaFormulario(true)
	
	if (this.ctlOrigen){
		if (this.ctlOrigen.nivel==sinNivel) {
			this.ctlOrigen.setNivel(this.nivel.cd)
			disenhador.cambiaHayQueGuardar()
			}
		this.ctlOrigen.vacia()
		this.ctlOrigen.rellena()
		}
	
	editorNivelesActivo=null
	disenhador.pintandoCuadro=false
}
////////////////////
var numNiveles=1
FormularioEditorNiveles.prototype.nuevoNivel=function(){
	try {
		var nuevoNivel=this.nivelVacio()
		nuevoNivel.cd=sinTitulo+numNiveles++
		nuevoNivel.idx=nuevoNivel.cd
		
		this._cargaNivel([nuevoNivel])
		}
	catch (e) {
		}
}
FormularioEditorNiveles.prototype.abrirNivel=function(){
	var frm={tipo:'frm', titulo:'Abrir objeto', controles:[
			{tipo:'bsc', id:'EXP_niveles.CD_Nivel', ds:'Nivel', controles:[
				{tipo:'lvw', id:'cd_nivel', maxlength:40, valor:this.nivel.cd}
				]},
			{tipo:'bsc', id:'gt_listaProcsVistas', ds:'Vista o proc almacenado de la bd', controles:[
				{tipo:'lvw', id:'vista', maxlength:40}
				]},
			{tipo:'bsc', id:'DLL_GuionesWeb.CD_Guion', ds:'Guión de DLL_GuionesWeb', controles:[
				{tipo:'lvw', id:'cd_guion', maxlength:40}
				]},
			]}
	
	var xfrm=new Formulario( frm, ponNuevoBloqueo())
	xfrm.onAceptar=function(){
		var param = xfrm.recogeValoresFRM()
		
		var nombreObjeto=param['cd_nivel'] || param['vista'] || param['cd_guion']
		var tipoObjeto=editorNivelesActivo.sacaTipoObjeto(param)
		
		editorNivelesActivo.cargaNivel(nombreObjeto, tipoObjeto)
		
		xfrm.cierraFormulario()
		quitaBloqueo(xfrm.idxBloqueo)
		}
	xfrm.toDOM()
	}
FormularioEditorNiveles.prototype.salvaNivelesAbiertos=function(){
	var abiertos=''//idx|idx
	for (var id in this.niveles){
		var nivel=this.niveles[id]
		if (nivel.cd != null)
			abiertos+=nivel.idx+'|'
		}
	salvaDato('editorNiveles', 'abiertos', abiertos)
	salvaDato('editorNiveles', 'activo', this.nivel.cd)	
	}
FormularioEditorNiveles.prototype.recuperaNivelesAbiertos=function(){
	var abiertos=recuperaDato('editorNiveles', 'abiertos')
	var activo=recuperaDato('editorNiveles', 'activo')
	
	var tmp=abiertos.split('|')
	for (var i=0; i<tmp.length; i++){
		var idx=tmp[i]
		
		var nombreObjeto, tipoObjeto='nivel'
		var tn=idx.split(' ')
		if (tn[1]){
			nombreObjeto=tn[1]
			//~ tipoObjeto=tn[0]
			//~ if (tipoObjecto=='procedure')
			tipoObjeto='vista'
			}
		else
			nombreObjeto=tn[0]
			
		if (nombreObjeto && !this.estaAbierto(nombreObjeto, tipoObjeto))
			this.cargaNivel(nombreObjeto, tipoObjeto, nombreObjeto != activo)
		}
	}
FormularioEditorNiveles.prototype.estaAbierto=function(id, tipoObjeto){
	return (id in this.niveles) || (tipoObjeto+' '+id in this.niveles)
	}
FormularioEditorNiveles.prototype.sacaTipoObjeto=function(param){
	var tipo
	if (param['cd_nivel'] )
		tipo='nivel'
	else if (param['vista'] )
		tipo='vista'
	else if (param['cd_guion'] )
		tipo='guion'
	return tipo	
	}
FormularioEditorNiveles.prototype.eliminarNivel=function(){
	var frm={tramite:'Niveles', tipo:'frm', titulo:'Eliminar objeto', 'controles':[
			{tipo:'bsc', id:'EXP_niveles.CD_Nivel', ds:'Nivel', 'controles':[
				{tipo:'lvw', id:'cd_nivel', maxlength:40, valor:this.cd, bloqueado:false, ds:''}
				]},
			{tipo:'bsc', id:'DLL_GuionesWeb.CD_Guion', ds:'Guión de DLL_GuionesWeb', controles:[
				{tipo:'lvw', id:'cd_guion', maxlength:40}
				]}
			]}
	
	var xfrm=new Formulario( frm, ponNuevoBloqueo())
	xfrm.onAceptar=function(){
		var param = xfrm.recogeValoresFRM()
		
		var idNivel=param['cd_nivel'] || param['cd_guion']
		var tipoObjeto=editorNivelesActivo.sacaTipoObjeto(param)
		
		editorNivelesActivo._eliminarNivel(idNivel, tipoObjeto)
		
		xfrm.cierraFormulario()
		quitaBloqueo(xfrm.idxBloqueo)
		}
	xfrm.toDOM()
}
FormularioEditorNiveles.prototype._eliminarNivel=function(idNivel, tipo) {
	var params= {accion:'eliminarNivel', tipo:tipo, aplicacion:exp.aplicacion, nivel:idNivel}
	loadJSONDoc ( 'json', params, function(req){ siHayError(req); editorNivelesActivo.mostrarResultados((tipo=='guion'?'Guión':'Nivel')+" '"+idNivel+" eliminado con éxito")} )
}
////////////////////
FormularioEditorNiveles.prototype.datosFrmPackageOracle=function(){
	var titulo=this.nivel.tipoObjeto=='PACKAGE'?'Guardar nivel y cabecera de paquete de Oracle' :'Guardar nivel y cuerpo de paquete de Oracle'
	return {tramite:'Propiedades del botón', tipo:'frm', titulo:titulo, 'controles':[
			{tipo:'lvw', id:'nombre', 	maxlength:-75, valor:this.nivel.cd, 		obligatorio:true, bloqueado:false, 	ds:'Nombre del nivel'},
			{tipo:'lvw', id:'sql', 	maxlength:-75, valor:this.nivel.sqlNivel, 	obligatorio:true, bloqueado:false,	ds:'Nombre del procedimiento a ejecutar',
			dsExtendida:'* Únicamente hay que indicar el nombre del proc (sin paquete ni parámetros)'}
			]}
}
FormularioEditorNiveles.prototype.guardar=function(){
	if (this.nivel.cd.startsWith(sinTitulo) )
		this.guardarComo()
	else
		this._guardarNivel(this.nivel.cd, null, this.nivel.tipoObjeto)
	}
FormularioEditorNiveles.prototype.guardarComo=function(){
	var textoEditor=this.xeditor.getCode()
	if ( this.nivel.tipoObjeto=='nivel'  && textoEditor.contains('def verifica'))
		this.nivel.tipoObjeto='guion'
	
	var nombre=null
	if (this.nivel.tipoObjeto=='PACKAGE' || this.nivel.tipoObjeto=='PACKAGE BODY'){
		var xfrm=new Formulario( this.datosFrmPackageOracle(), ponNuevoBloqueo() )
			
		xfrm.onAceptar=function(){
			var param = xfrm.recogeValoresFRM()
			xfrm.cierraFormulario()
			editorNivelesActivo._guardarNivel(param['nombre'], param['sql'])
			}
		
		xfrm.toDOM()
		}
	else {
		nombre=prompt("Se va a guardar el "+ (this.nivel.tipoObjeto=='guion'? 'guión': this.nivel.tipoObjeto)+" con el nombre:", this.nivel.cd)
		this._guardarNivel(nombre, null, this.nivel.tipoObjeto)
		}
	}
FormularioEditorNiveles.prototype._guardarNivel=function(nombre, sql, tipo){
	if (nombre!=null) {
		this.muestraProcesando('guardando...')
		
		this.nivel.cd=nombre
		var textoEditor=this.xeditor.getCode()
		
		
		var params={	accion:'guardaNivel', 
					cd:this.nivel.cd, 
					tipo:tipo, // nivel o guion
					sqlNivel:sql, 
					textoEditor:textoEditor, 
					tipoObjeto:this.nivel.tipoObjeto, //tipoObjetoBD: package, package body..
					f_modificacion:this.nivel.f_modificacion,
					aplicacion:exp.aplicacion, 
					params:this.juntaParams() }
		var cd=this.nivel.cd
		var fn=function(req){ editorNivelesActivo.guardadoOK( xeval(req, true),cd )}
		loadJSONDocPost('json', params, fn, fn)
		}
	this.salvaNivelesAbiertos()
}
FormularioEditorNiveles.prototype.guardadoOK=function(respuesta, cd){
	if (respuesta.tipo!=null && respuesta.tipo=='error'){
		alert(respuesta.msg)
		}
	else{
		this.nivel.f_modificacion=respuesta.fecha
		var ultimaEdicion=(this.nivel.f_modificacion)?' (modificado:'+this.nivel.f_modificacion+')':''

		this.cambiaTitulo(cd+ultimaEdicion, false)
		this.activarNivel(this.nivel.idx)
		}
	this.mostrarResultados(respuesta.msg)
}
FormularioEditorNiveles.prototype.JSONparams=function(){
	var ret=''
	for (var i=0;i<this.nivel.params.length;i++) {
		var param=this.nivel.params[i]
		ret+="{"+pareja('nombre', param.nombre)+pareja('tipo', param.tipo,false)+"},"
		}
	if (ret!='') 
		ret="["+ret+"]"
		
	return ret
}
FormularioEditorNiveles.prototype.juntaParams=function(){
	if (this.nivel.params==null)
		return ''
	
	var ret=[]
	for (var i=0;i<this.nivel.params.length;i++) {
		if (typeof this.nivel.params[i] != 'function' ){
			var param=this.nivel.params[i]
			ret.push(param.tipo+' '+param.nombre)
			}
		}
	return ret.join(',')
}
FormularioEditorNiveles.prototype.cerrarNivel=function(idx){
	var nivelCerrar=this.niveles[idx]
	borra(nivelCerrar.pestanha)
	delete this.niveles[idx]
	this.nivel=null
	
	var nuevoNivelActivo=sacaUltimo(this.niveles)
	if (nuevoNivelActivo && nuevoNivelActivo.idx)
		this.activarNivel( nuevoNivelActivo.idx )
	else
		this.nuevoNivel()
	
	this.salvaNivelesAbiertos()
	}
////////////////////////////////////////////
FormularioEditorNiveles.prototype.ejecutarNivel=function(){
	var sql=this.xeditor.getCode()
	var params={accion:'ejecutaNivel', cd:this.nivel.cd, sql:sql, aplicacion:exp.aplicacion, params:this.juntaParams()}
	
	this.muestraProcesando('ejecutando el nivel...')
	
	loadJSONDocPost('json', params, function(req){editorNivelesActivo.mostrarResultados( xeval(req, true) )} )
}
FormularioEditorNiveles.prototype.ejecutarSQL=function(){
	this.muestraProcesando('ejecutando...')
	
	var sql=this.xeditor.selection() || this.xeditor.getCode()
	if ( sql.contains('?') ) {
		this.ejecutarNivel()
		return
		}
	var params={accion:'ejecutaSQL', sql:sql, aplicacion:exp.aplicacion}
	
	
	loadJSONDocPost('json', params, function(req){editorNivelesActivo.mostrarResultados( xeval(req, true) )} )
}
/////////////////////
FormularioEditorNiveles.prototype.muestraProcesando=function(msg){
	borraHijos(this.panelResultados)
	this.panelResultados.appendChild ( creaObjProp('div', {className:'cargando nivelAdvertencia', texto:msg}))
	}
FormularioEditorNiveles.prototype.mostrarResultados=function(datosFilas){
	if (datosFilas.tipo && datosFilas.tipo=='error'){
		alert(datosFilas.msg)
		}
	if (datosFilas.tipo && datosFilas.tipo=='ok'){
		var docActivo=editAreaLoader.getCurrentFile(__editor__)
		editAreaLoader.setFileEditedMode(__editor__, docActivo.id, false)
		}
	
	borraHijos(this.panelResultados)
	
	if (datosFilas.columnas==null) {
		var aviso=creaObjProp('div', {innerHTML:datosFilas.msg || datosFilas, className:'nivelAdvertencia nivelGuardado'})
		this.panelResultados.appendChild(aviso)
		$(aviso).fadeOut('normal').fadeIn('normal')
		}
	else {
		this.lvw.rellenaLista( datosFilas.columnas, range(datosFilas.columnas.length), datosFilas.filas)
		this.anhadeAdvertencias(datosFilas.parametros||[], datosFilas.columnas)
		}
}
FormularioEditorNiveles.prototype.compruebaCol=function(obligatorias, listaCols){
	var msg=''
	
	for (var i=0; i<obligatorias.length; i++) {
		if (!listaCols.containsIgnoreCase( obligatorias[i] ))
			msg=dicSugerencias[obligatorias[i]]+', '
		}
	if (msg!='') {
		msg=msg.substring(0, msg.length-2)
		
		return msg
		}
	return null
}
var dicSugerencias={
		'cd' : 				'<abbr title="Clave completa del elemento dentro de la jerarquía">cd</abbr>',
		'key' : 			'<abbr title="Clave completa del elemento dentro de la lista">key</abbr>',
		'ds' : 				'<abbr title="Descripción del elemento">ds</abbr>',
		
		'icono' : 			'<abbr title="Nombre del icono del nodo (sensible a mayúsculas; por defecto png)">icono</abbr>',
		'modoDetalle' : 		'<abbr title="Nombre del detalle en sintaxis =NombreDetalle\\x">modoDetalle</abbr>',
		'modoDetalleSiguiente' : '<abbr title="Nombre del nivel que cuelga de este nodo">modoDetalleSiguiente</abbr>'
		}
FormularioEditorNiveles.prototype.anhadeAdvertencias=function(param, listaCols){
	var listaAdvertencias=creaObjProp('div')
	
	if (exp.idMdActivo){
		var nodoPinchado=exp.getMD(exp.idMdActivo).nodoActivo
		
		var obj=creaObjProp('div', {	textos:	'El nodo activo es: '+nodoPinchado+'. Los parámetros empleados son: ['+param.join(',')+']\n'+
									'Para probar el nivel con otros parámetros añade al principio un comentario con los valores deseados (formato "//#Caso de prueba: y2002\\e8000" si el nivel es SQL o "#Caso de prueba: y2002\\e8000" si el nivel es Jython, sin las comillas)',
							className:'nivelAdvertencia parametros'})
		listaAdvertencias.appendChild( obj)	
		}
	//   /!\ Si se está creando un nivel del árbol, faltan las columnas @.#. Opcionales las columnas Icono y ModoDetalleSiguiente. Se ignora la columna key
	
	var msg=this.compruebaCol(['cd', 'ds', 'modoDetalle'], listaCols)
	if (msg) {
		msg='Si se está creando un nivel del árbol, falta la columna o columnas <b>'+msg+'</b>. Las columnas '+dicSugerencias['icono']+' y '+dicSugerencias['modoDetalleSiguiente']+' son opcionales. Se ignora la columna '+dicSugerencias['key']+'.'
		listaAdvertencias.appendChild( creaObjProp('div', {innerHTML:msg, className:'nivelAdvertencia arbol'}))
		}
	
	var msg=this.compruebaCol(['key', 'modoDetalle'], listaCols)
	if (msg) {
		msg='Si se está creando una lista, falta la columna o columnas <b>'+msg+'</b>. La columna '+dicSugerencias['icono']+' es opcional. Se ignoran las columnas '+dicSugerencias['cd']+', '+dicSugerencias['ds']+' y '+dicSugerencias['modoDetalleSiguiente']+'.'
		listaAdvertencias.appendChild( creaObjProp('div', {innerHTML:msg, className:'nivelAdvertencia lista'}))
		}
	
	var msg="Si se está creando una gráfica, la primera columna indica la categoría, la segunda la serie y la última el valor numérico a representar. El resto de columnas se ignoran, y como mínimo hay que indicar una serie y el valor a representar."
	listaAdvertencias.appendChild( creaObjProp('div', {innerHTML:msg, className:'nivelAdvertencia grafica'}))
	
	if (this.panelResultados.childNodes.length)
		this.panelResultados.insertBefore( listaAdvertencias, this.lvw.contDatos)
	else
		this.panelResultados.appendChild(listaAdvertencias)
		
}
FormularioEditorNiveles.prototype.verParametros=function(){
	editorParametros=new FormularioParametros( ponNuevoBloqueo())
	editorParametros.toDOM()
}
function editaNivel(idNivel, ctlOrigen){
	if (!disenhador.modoDisenho)
		return
	if (idNivel==null && disenhador.md!=null) {
		for (var id in disenhador.listaSeleccionados){
			var ctl=disenhador.md.getControl( disenhador.listaSeleccionados[id] )
			if (typeof ctl == 'function' || ctl ==null) {
				/*pass*/}
			else if (ctl.usaNivel) {
				idNivel=ctl.nivel
				break
				}
			}
		}
		
	var idxBloqueo=ponNuevoBloqueo()
	editorNivelesActivo=new FormularioEditorNiveles(idxBloqueo, idNivel)
	editorNivelesActivo.ctlOrigen=ctlOrigen
	editorNivelesActivo.toDOM()
	
	disenhador.moviendoControles=false
}
//////////////////////////////////////////////////////////
FormularioEditorNiveles.prototype.asistentes=function(){
	var vbCrLf='\n\t'
	var vbCr='\n'

	var tl_mssql='SELECT '+vbCrLf+
				'tt.CD_Tramite AS "Trámite",  '+vbCrLf+'tt.DS_Tramite AS "Descripción",  '+vbCrLf+'\'tl\' AS ModoDetalle,  '+vbCrLf+
				'tt.CD_Tramite + \'·\' + cast(ta.CD_Camino as varchar) AS [key] '+vbCr+'FROM   '+vbCrLf+'TRA_TRAMITESOBJETOS tt,  '+vbCrLf+
				'TRA_Acciones ta '+vbCr+'WHERE   '+vbCrLf+'tt.CD_Tramite=ta.CD_Tramite AND  '+vbCrLf+
				'ta.CD_Accion = \'LBL\' AND  '+vbCrLf+'ta.Parametro1 = \'libre\' and '+vbCrLf+'ta.CD_Camino=$0 '

	var tp_mssql='SELECT '+vbCrLf+'o.CD_Tramite AS "Trámite",  '+vbCrLf+'o.DS_Tramite AS "Descripción",  '+vbCrLf+'e.CD_Usuario_Ejecucion as "Usuario", '+vbCrLf+
				'e.F_Ejecucion AS "Fecha",  '+vbCrLf+'\'tp\' AS ModoDetalle,'+vbCrLf+'\'tp\' as Icono, '+vbCrLf+
				'e.CD_Tramite + \'·\' + Convert(varchar(255), F_Pendiente, 120) +  \'·\' + CAST(CD_Camino AS varchar(50)) AS [key] '+vbCr+
			'FROM  '+vbCrLf+'$0 T /*tabla de tramitación */'+vbCrLf+'TRA_TramitesObjetos o  '+vbCr+
			'WHERE '+vbCrLf+'e.CD_Tramite = o.CD_Tramite AND '+vbCrLf+'e.F_Ejecucion Is Null '+ vbCrLf +
				'/*aquí falta añadir la clave del objeto base*/'+vbCr+'ORDER BY '+vbCrLf+
				'e.F_Ejecucion '
				
	var te_mssql='SELECT '+vbCrLf+'o.CD_Tramite AS "Trámite",  '+vbCrLf+'o.DS_Tramite AS "Descripción",  '+vbCrLf+'e.CD_Usuario_Ejecucion as "Usuario", '+vbCrLf+
				'e.F_Ejecucion AS "Fecha",  '+vbCrLf+'\'te\' AS ModoDetalle,'+vbCrLf+'\'te\' as Icono, '+vbCrLf+
				'e.CD_Tramite + \'·\' + Convert(varchar(255), F_Pendiente, 120) +  \'·\' + CAST(CD_Camino AS varchar(50)) AS [key] '+vbCr+
			'FROM  '+vbCrLf+'$0 e, /*tabla de tramitación*/'+vbCrLf+'TRA_TramitesObjetos o  '+vbCr+
			'WHERE '+vbCrLf+'e.CD_Tramite = o.CD_Tramite AND '+vbCrLf+'e.F_Ejecucion Is Not Null '+ vbCrLf +'/*aquí falta añadir la clave del objeto base*/'+vbCr+
			'ORDER BY '+vbCrLf+'e.F_Ejecucion '
			
	var app_tramites='SELECT '+vbCr+
				'	? as CD_Usuario,  '	+vbCr+
				'	c.CD_Camino,  	'	+vbCr+
				'	t.CD_Tramite, 	'	+vbCr+
				'	t.DS_Tramite,  	'	+vbCr+
				'	null as ruta, 	'	+vbCr+
				'	null as javascriptfirma'	+vbCr+
			'FROM  '+vbCr+
			'	TRA_Caminos c, TRA_TramitesObjetos t'+vbCr+
			'WHERE '+vbCr+
			'	c.CD_Camino=? and CD_Tramite=?'
	var app_documentos=	'select '+vbCr+
					'	? as CD_Usuario,'+vbCr+
					'	? as CD_Documento,'+vbCr+
					'	   null as ruta,'+vbCr+
					'	null as jsFirma'
	    
	var app_menugeneral='SELECT '+vbCrLf+'e.*'+vbCr+
					'FROM '+vbCrLf+
						'exp_menu e,'+vbCrLf+'Usuarios u'+vbCr+
					'WHERE'+vbCrLf+	
					'	u.CD_Usuario=? AND'+vbCrLf+
					"	tipo='menuglobal' "+vbCr+
					'ORDER BY'+vbCrLf+
					'	cd_menupadre, cd_boton'

	var app_menudetalle='SELECT '+vbCrLf+'e.*'+vbCr+
					'FROM '+vbCrLf+
						'exp_menu e, '+vbCrLf+'Usuarios u'+vbCr+
					'WHERE'+vbCrLf+	
					"	u.CD_Usuario=? AND"+vbCrLf+
					"	tipo=? AND"+vbCrLf+
					"	? IS NOT NULL "+vbCr+
					'ORDER BY'+vbCrLf+
					'	cd_menupadre, cd_boton'

	var proc_oracle='CREATE OR REPLACE PROCEDURE <APP_NOMBRE_PROCEDIMIENTO>('+vbCr+
	'    u IN VARCHAR2,'+vbCr+
	'    --pf1 IN DATE,'+vbCr+
	'    --pi1 IN NUMBER,'+vbCr+
	'    '+vbCr+
	'    cursorOut OUT SYS_REFCURSOR)'+vbCr+
	'IS'+vbCr+
	'	/*'+vbCr+
	'	pf2_p varchar2(15);'+vbCr+
	'	pf1_p varchar2(15);'+vbCr+
	'	*/'+vbCr+
	'BEGIN'+vbCr+
	"	--DBMS_OUTPUT.PUT_LINE('Mensaje de estado');"+vbCr+
	'	OPEN cursorOut FOR select * from dual;'+vbCr+
	'       '+vbCr+
	'/*EXCEPTION'+vbCr+
	'	WHEN NO_DATA_FOUND THEN'+vbCr+
	'		OPEN cursorOut FOR '+vbCr+
	'			select * from dual;'+vbCr+
	'	WHEN OTHERS THEN'+vbCr+
	'		OPEN cursorOut FOR'+vbCr+
	'			select * from dual;'+vbCr+
	'	*/'+vbCr+
	'END; '
	var proc_sql='CREATE PROCEDURE <Procedure_Name, sysname, ProcedureName> '+vbCr+
	'	-- Add the parameters for the stored procedure here'+vbCr+
	'	<@Param1, sysname, @p1> <Datatype_For_Param1, , int> = <Default_Value_For_Param1, , 0>, '+vbCr+
	'	<@Param2, sysname, @p2> <Datatype_For_Param2, , int> = <Default_Value_For_Param2, , 0>'+vbCr+
	'AS'+vbCr+
	'BEGIN'+vbCr+
	'	-- SET NOCOUNT ON added to prevent extra result sets from'+vbCr+
	'	-- interfering with SELECT statements.'+vbCr+
	'	SET NOCOUNT ON;'+vbCr+
	''
	'    -- Insert statements for procedure here'+vbCr+
	'	SELECT <@Param1, sysname, @p1>, <@Param2, sysname, @p2>'+vbCr+
	'END'

	var nivel_jython='#!jython'+vbCr+
		'# -*- coding: utf-8 -*-'+vbCr+
		'#caso de prueba: aValorA\bValorB '+vbCr+
		''+vbCr+
		'from es.burke.gotta import *'+vbCr+
		'import math'+vbCr+
		''+vbCr+
		'class pruebaNivelJython(INivelDef): # OJO: El nombre de la clase debe ser el del nivel'+vbCr+
		'    def __init__(self, cd_nivel, parametros): # OJO: Hay que definir este constructor'+vbCr+
		'        self.cd=cd_nivel'+vbCr+
		'        self.rellenaColParametros(parametros)'+vbCr+
		'    def obtenerNivel(self):'+vbCr+
		'        return pruebaNivelJython_(self)'+vbCr+
		''+vbCr+
		'class pruebaNivelJython_(INivel):'+vbCr+
		'    def __init__(self,nivelDef): # OJO: Hay que definir este constructor'+vbCr+
		'        self.cd=nivelDef.cd'+vbCr+
		'        self.nivelDef=nivelDef'+vbCr+
		'    def lookUpBase(self, con, valores, limite):'+vbCr+
		'        #con es una instancia de ConexionLight, con la que podemos consultar a la BD'+vbCr+
		'        #valores es la lista de parámetros'+vbCr+
		'        cols=   ["cat","ser","val"]'+vbCr+
		'        tipos=["String","Single","Single"]'+vbCr+
		'        ret=Filas(cols,tipos) # Se ha de devolver una instancia de es.burke.gotta.Filas'+vbCr+
		'        pasos=100'+vbCr+
		'        for x in range(pasos):'+vbCr+
		'            ang=x*math.pi/pasos'+vbCr+
		''+vbCr+
		'            f=Fila(ret, (ang,1,math.sin(ang)))'+vbCr+
		'            ret.add(f)'+vbCr+
		''+vbCr+
		'            f=Fila(ret, (ang,2,math.cos(ang)))'+vbCr+
		'            ret.add(f)'+vbCr+
		''+vbCr+
		'        return ret'
	var nivel_gantt='select  '+vbCr+
		"	'Previsión' as Serie,  "+vbCr+
		"	'Planificación' as tarea,  "+vbCr+
		"	getdate()-15 as f1,  "+vbCr+
		"	getdate()-5 as f2,  "+vbCr+
		"	null as porcentaje "+vbCr+
		"union 	"+vbCr+
		"select  "+vbCr+
		"	'Previsión' as Serie,  "+vbCr+
		"	'Documentación' as tarea,  "+vbCr+
		"	getdate()-5 as f1,  "+vbCr+
		"	getdate()+10 as f2,  "+vbCr+
		"	null as porcentaje "+vbCr+
		"union "+vbCr+
		"select  "+vbCr+
		"	'Real' as Serie,  "+vbCr+
		"	'Planificación' as tarea,  "+vbCr+
		"	getdate()-15 as f1,  "+vbCr+
		"	getdate() as f2,  "+vbCr+
		"	null as porcentaje "+vbCr+
		"union "+vbCr+
		"select  "+vbCr+
		"	'Real' as Serie,  "+vbCr+
		"	'Documentación' as tarea,"+vbCr+  
		"	getdate() as f1,  "+vbCr+
		"	getdate()+20 as f2,"+vbCr+
		"	null as porcentaje" 
	var nivel_sql_lvw='--#caso de prueba: aValorA\\bValorB'+vbCr+
		"select"+vbCr+
		"	'campo1' as campo1,"+vbCr+
		"	'campo2' as campo2,"+vbCr+
		vbCr+
		"	'campoKey' as \"key\","+vbCr+
		"	'campoIcono' as icono,"+vbCr+
		"	'a' as mododetalle"+vbCr+
		"from"+vbCr+
		"	dic_tablas"+vbCr+
		"where"+vbCr+
		"	nombre='exp_niveles'"
	var nivel_sql_arbol='--#caso de prueba: aValorA\\bValorB'+vbCr+
		"select"+vbCr+
		"	'campoCD' as cd,"+vbCr+
		"	'campoDS' as ds,"+vbCr+
		vbCr+
		"	'descripciónAdicional1' as _ds_descripcionAdicional1,"+vbCr+
		"	'descripciónAdicional2' as _ds_descripcionAdicional2,"+vbCr+
		vbCr+
		"	'campoIcono' as icono,"+vbCr+
		"	'=nombreDetalle\a' as mododetalle,"+vbCr+
		"	'nombreNivelSiguiente' as mododetallesiguiente"+vbCr+
		"from"+vbCr+
		"	dic_tablas"+vbCr+
		"where"+vbCr+
		"	nombre='exp_niveles'"
		
	var asistentes= {	'-sep00':{ds:'-Árboles y listas'}
					,  'a':  	{css:'lvw', id:'a',  ds:'Árbol', 		dsExtendida:'Árbol de datos',					sql:nivel_sql_arbol, params:[]}
					,  'l':  	{css:'lvw', id:'l',  	ds:'Lista', 		dsExtendida:'Lista de datos',				sql:nivel_sql_lvw, params:[]}
					,'-sep01':{ds:'-Listas de tramitación'}
					,  'tl':  	{css:'lvw tl', id:'tl',  	ds:'Lista de trámites libres', 		dsExtendida:'Lista de tareas libres, no dependientes de ningún estado',				sql:tl_mssql, params:[]}
					, 'tp': 	{css:'lvw tp', id:'tp', 	ds:'Lista de trámites pendientes', 	dsExtendida:'Lista de tareas pendientes de realizar', 							sql:tp_mssql, params:[]}
					, 'te': 	{css:'lvw te', id:'te', 	ds:'Lista de trámites ejecutados', 	dsExtendida:'Historial de tareas realizadas',								sql:te_mssql,  params:[]}
					
					,'-sep02':{ds:'-Gráficas'}
					, 'gra': 	{css:'gra', id:'gra', 	ds:'Gráfica', 				dsExtendida:'Creación de una gráfica',			instrucciones:'', 	sql:'select nombreTabla as literal, count(*) as total from dic_columnas group by nombreTabla' , params:[]}
					, 'gantt': 	{css:'gantt', id:'gantt', 	ds:'Diagrama de Gantt', 				dsExtendida:'Creación de un diagrama de Gantt',	instrucciones:'', 	sql:nivel_gantt, params:[]}
					
					,'-sep03':{ds:'-Permisos sobre trámites y firma electrónica'}
					, 'app_tramites': {css:'gotta app_tramites', id:'app_tramites', 		ds:'APP_Tramites', 	dsExtendida:'Plantilla para implementar permisos y/o para firmar electrónicamente los trámites', 	params:[{tipo:'string', nombre:'u'}, {tipo:'integer', nombre:'CD_Camino'}, {tipo:'string', nombre:'CD_Tramite'}], 	sql:app_tramites}
					, 'app_documentos': {css:'gotta app_tramites', id:'app_documentos', 	ds:'APP_Documentos', 	dsExtendida:'Plantilla para implementar la firma electrónica de documentos', 		params:[{tipo:'string', nombre:'u'}, {tipo:'string', nombre:'CD_Documento'}], 	sql:app_documentos}
					
					,'-sep4':{ds:'-Menús y botoneras'}
					, 'menugeneral': {css:'gotta app_menugeneral', id:'menugeneral', 	ds:'APP_MenuGeneral', 	dsExtendida:'Plantilla para implementar un menú de opciones para toda la aplicación', 	params:[{tipo:'string', nombre:'u'}], 								sql:app_menugeneral}
					, 'menudetalle': {css:'gotta app_menugeneral', id:'menudetalle', 	ds:'APP_MenuDetalle', 	dsExtendida:'Plantilla para implementar un menú de opciones para un detalle', 			params:[{tipo:'string', nombre:'u'}, {tipo:'string', nombre:'detalle'}, {tipo:'string', nombre:'vista'}], 			sql:app_menudetalle}
						
					,'-sep05':{ds:'-Procedimientos almacenados'}
					, 'oracle.procedimiento': 	{css:'oracle procedimiento', id:'oracle.procedimiento', 	ds:'Nivel (Oracle)', 	dsExtendida:'Plantilla para generar un nivel basado en un procedimiento almacenado de Oracle',	sql:proc_oracle, params:[{tipo:'string', nombre:'u'}]}
					, 'sql.procedimiento': 	{css:'sqlserver procedimiento', id:'sql.procedimiento', 	ds:'Nivel (SQL-Server)', 	dsExtendida:'Plantilla para generar un nivel basado en un procedimiento almacenado de SQL-Server',	sql:proc_sql, params:[]}
					
					,'-sep06':{ds:'-Niveles no basados en SQL'}
					, 'nivel.jython': 	{css:'nivel jython', id:'nivel.jython', 	ds:'Nivel (Jython)', 	dsExtendida:'Plantilla para generar un nivel en Jython',	sql:nivel_jython, params:[]}
					}
	return asistentes
	}
//////////////////////////////////////////////////////////
function siHayError(req){
	var respuesta=xeval(req) 
	if (respuesta.tipo!=null && respuesta.tipo=='error')
		alert(respuesta.msg)	
}
//////////////////////////////////////////////////////////
var editorParametros=null
function FormularioParametros(idxBloqueo){
	Frm.call(this, 'frmParametros', idxBloqueo)
	
	this.altoFrm=400
	this.anchoFrm=300
	
	this.titulo='Parámetros'
	this.rutaIcono='./fijo/lved22.png'
	
	this.params=editorNivelesActivo.nivel.params
	
	var cmdAceptar=creaObj('button', 'cmdgt aceptar', null, 'Aceptar', function(){editorParametros.acepta()})
	var cmdCancelar=creaObj('button', 'cmdgt cancelar', null, 'Cancelar', function(){ editorParametros.cancela() }) 
	
	this.botones=[cmdAceptar, cmdCancelar]
}
FormularioParametros.prototype=new Frm
FormularioParametros.prototype.acepta=function(){
	//devolvemos una colección perfecta, sin huecos
	var ret=[]
	for (var i in this.params) {
		if (this.params[i])
			ret.push( this.params[i] )
		}
	editorNivelesActivo.nivel.params=ret
	this.ocultaFormulario()
}
FormularioParametros.prototype.cancela=function(){
	this.ocultaFormulario()
}
FormularioParametros.prototype.pintaCuerpo=function() {
	//botonera
	var btNuevo=	  this.creaObj('a', 'botonera texto imagen gris', creaSpan('Nuevo') , 	'btnRegistro',     null, function(){editorParametros.nuevo(); return false})
	var btnModificar = this.creaObj('a', 'botonera texto imagen gris', creaSpan('Modificar') , 'btnModificar', null, function(){editorParametros.modificar(); return false})
	var btnEliminar =   this.creaObj('a', 'botonera texto imagen gris', creaSpan('Eliminar') , 	'btnEliminar',   null, function(){editorParametros.eliminar();return false})	
	
	var nodoPinchado='[No hay ninguno]'
	if (exp.idMdActivo)
		nodoPinchado=exp.getMD(exp.idMdActivo).nodoActivo
	var advertencia=creaObjProp('div', {className:'consejo informacion', texto:'El nodo activo es '+nodoPinchado})
	
	var btn=this.creaBarraHerramientas([btNuevo, btnModificar, btnEliminar])
	var ventana=creaObjProp('div',{className:'cuerpo cuerpoParametros', hijos:[btn, advertencia]})
	
	var id='tablaParametros'
	ventana.appendChild( creaObjProp('div', {className:'lvw', id:id})  )	
	
	this.lvw=new Lista(id, false)
	this.lvw.contenedor=id
	
	return ventana
}
FormularioParametros.prototype.activate=function(){
	this.rellenaLista()
}
FormularioParametros.prototype.fnEditar=function(){
	var self=this
	return function(fila){
		var id=fila.id.substring(1)
		if (id){
			self.editar(Number(id))
			}
		}
	}
FormularioParametros.prototype.rellenaLista=function(){
	this.lvw.rellenaLista(['Parámetro', 'Tipo de dato'], ['nombre', 'tipo'], this.params, null, null, this.fnEditar(), null, null, './fijo/parametro.png')
}
FormularioParametros.prototype.datosFRM=function(nombre, tipo){
	return  {tramite: 'pr', tipo:'frm',"titulo" : 'Parámetros',"alto" : 150, 'controles':[
			{tipo:'lvw', id:'nombre', maxlength: 50, valor:nombre,obligatorio : true,bloqueado : false,ds : 'Nombre'},
			{tipo:'bsc',id : 'dic_tiposdatos.tipodato',obligatorio : true,ds : 'Tipo de dato',colsQueBloquea : 0,colsBloqueadas : 0,bloqueado : false,'controles':[
				{tipo: 'lvw',id : 'tipodato', maxlength: 30, valor:tipo,obligatorio : false,bloqueado : false,ds : ''}
				]}
			]}
}
FormularioParametros.prototype.nuevo=function(){
	this.editar(-1)
}
FormularioParametros.prototype.modificar=function(){
	var i=this.lvw.getFilasPinchadas()[0]
	var id=this.lvw.sacaIdFila(i)
	
	this.editar(id)
}
FormularioParametros.prototype.editar=function(cual){
	this.editando=cual
	
	var nombre='', tipo='String'
	if (cual>-1){
		nombre=this.params[cual].nombre
		tipo=this.params[cual].tipo
		}
	
	var datosFRM=this.datosFRM( nombre, tipo )
	
	var xfrm=new Formulario( datosFRM, ponNuevoBloqueo() )
	xfrm.onAceptar=function(){
		//var xfrm=getFrmActivo()
		var ret = xfrm.recogeValoresFRM()
		
		var nombre=ret['nombre']
		var tipo=ret['tipodato']
		
		var nuevoPar={'nombre':nombre, 'tipo':tipo}
		
		if (editorParametros.params==null || editorParametros.params=='' )
			editorParametros.params=[]
		if (editorParametros.editando==-1)
			editorParametros.params.push( nuevoPar )
		else
			editorParametros.params[editorParametros.editando]=nuevoPar
			
		xfrm.cierraFormulario()
		editorParametros.rellenaLista()
		}
	xfrm.toDOM()
}
FormularioParametros.prototype.eliminar=function(){
	var i=this.lvw.getFilasPinchadas()[0]
	var id=this.lvw.sacaIdFila(i)
	
	this.params[id]=null
	this.rellenaLista()
}


////////////////////////////////////////

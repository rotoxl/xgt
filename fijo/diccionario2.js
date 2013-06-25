var exp=null, icoWeb=null
var pendiente={}
var tiposDatos={	'Boolean':'Boolean',
				'Byte':'Byte',	
				'Currency':'Currency',
				'Date':'Date',	
				'Double':'Double',
				'Integer':'Integer',
				'Long'	:'Long',
				'Memo':'Memo',
				'Option':'Option',
				'Single':'Single',
				'String':'String',
				'Auto'	:'Auto'}
              
var editorDiccionario = null
var editorConexiones = null
var dicV=null, dicF=null

var icoGotta='./fijo/'

function editaConexiones(){
	if (editorConexiones==null) 
		editorConexiones=new FormularioConexiones(ponNuevoBloqueo())
	else
		editorConexiones.idxBloqueo=ponNuevoBloqueo()
		
	editorConexiones.toDOM()
}
function editaDiccionario(conexion){
	editorDiccionario=new FormularioEditorDiccionario(ponNuevoBloqueo())
	if (conexion!=null)
		editorDiccionario.catalogos=[conexion]
	editorDiccionario.toDOM()
}
////////////////////////////////////////////////////////
function sacaUltimo(dato){
	var temp=dato.split('.')
	return temp[temp.length-1]
}
////////////////////////////////////////////////////////
function FormularioConexiones(idxBloqueo){
	Frm.call(this, 'frmConexiones', idxBloqueo)
	this.altoBotonera=0
	this.altoFrm=500
	this.anchoFrm=500
	
	this.titulo='Conexiones'
	this.rutaIcono='./fijo/dic22.png'
	
	this.lista=null
	this.botones=[	
			creaObj('button', 'cmdgt aceptar', null, 'Aceptar', function(){editorConexiones.aceptaCatalogos()} ) , 
			creaObj('button', 'cmdgt cancelar', null, 'Cancelar', function(){ editorConexiones.ocultaFormulario() })
			]
}
FormularioConexiones.prototype= new Frm
FormularioConexiones.prototype.pintaCuerpo=function() {
	var ventana=creaObj('div')
	
	var texto=this.creaObj('div', null, creaT('Señale el catálogo o catálogos que poseen datos que quiera utilizar en su aplicación'))
	var aviso=this.creaObj('div','bombilla', texto)
	
	ventana.appendChild(aviso)
	
	this.lvw=new Lista('lvwConexiones', true)
	var xcont=this.lvw.pintaContenedor(true)
	ventana.appendChild( xcont )
	
	ventana.style.height=this.altoFrm-72+'px'
	ventana.style.width=this.anchoFrm+'px'
	return ventana
}
FormularioConexiones.prototype.activate=function(){
	if (this.lista==null) 
		this.cargaListaCatalogos()
	else
		this.muestraListaCatalogos() 
	
}
FormularioConexiones.prototype.cargaListaCatalogos=function(){
	loadJSONDoc( 	'json', 
				{'accion':'diccionarioFisico', 'operacion':'sacaCatalogo', 'aplicacion': aplicacion },
				function(req){
						editorConexiones.lista=xeval(req)
						editorConexiones.muestraListaCatalogos() 
						}
				)
}
FormularioConexiones.prototype.muestraListaCatalogos=function(){
	this.lvw.rellenaLista(['Origen', 'Catálogo', 'Esquema', 'SGBDR'], ['conexion', 'catalogo','esquema','tipoBD'], this.lista, 'id', null, null, true)
}
FormularioConexiones.prototype.aceptaCatalogos=function(){
	editorDiccionario.catalogos=this.lvw.getFilasPinchadas()
	this.ocultaFormulario()
	
	var idxBloqueo=ponNuevoBloqueo(true, 'Cargando')
	editorDiccionario.cargaDiccionarioF() //recarga lo físico
	
	salvaDato('diccionario', 'catalogos', editorDiccionario.catalogos.join(',') )
	
	quitaBloqueo(idxBloqueo)
}
//////////////////////////////////////////////////////////////////////
function FormularioEditorDiccionario(idxBloqueo){
	this.catalogos=null
	
	this.diccionarioFisico=null
	this.diccionario = null
	
	Frm.call(this, 'frmDiccionario', idxBloqueo)
	
	this.altoBotonera=25
	this.altoPie=40
	
	this.porcentajeAnchoFrm=99
	
	this.titulo='Diccionario'
	this.rutaIcono='./fijo/dic22.png'
	
	this.ocultarTablasDeSistema=true
	
	var catalogos=recuperaDato('diccionario', 'catalogos')
	if (catalogos)
		this.catalogos=catalogos.split(',')
		
}
FormularioEditorDiccionario.prototype= new Frm
FormularioEditorDiccionario.prototype.sacaH=function(){
	var x=$('#frmDiccionario')
	return x.innerHeight()-this.altoBotonera-this.altoCabecera-this.altoPie
	}
FormularioEditorDiccionario.prototype.sacaW=function(){
	var x=$('#frmDiccionario')
	return x.innerWidth()
	}
FormularioEditorDiccionario.prototype.onResize=function(dep){
	if (!dep) return
	
	var anchoBarra=7
	var w=this.sacaW()
	var h=this.sacaH()

	
						// sSplId,  	  X,  Y,  W,   H,  Horz ,  BarW, BarPos, BarLim,    BarEn, BorW, oSplChild1, oSplChild2				       
	var splitterH = new xSplitter('splitterDic',  0, 0, w, h, true,   anchoBarra,  w/3,    h/8,     true, 0)
	try{
		xAddEventListener(window, 'resize', function(){editorDiccionario.onResize(dep)}, false)
		}
	catch(e){}
	splitterH.paint(w, h, w/3, h/4)
}
FormularioEditorDiccionario.prototype.pintaCuerpo=function() {
	//botonera
	var btnSalir= creaObjProp('a', {className:'botonera gris imagen texto', hijo:creaSpan('Salir') , id:'btnSalir', onclick:function(){editorDiccionario.cancelar(); return false}})

	var btnConexiones= creaObjProp('a', {className:'botonera gris imagen texto', hijo:creaSpan('Seleccionar orígenes de datos') , id:'btnConexion', onclick:function(){editaConexiones(); return false}})
	
	var btnOcultarTablas= creaObjProp('a', {className:'botonera gris imagen texto estado_on', hijo:creaSpan('Ocultar tablas de sistema') , id:'btnOcultarTablas'})
	this.btnOcultarTablas=btnOcultarTablas
	btnOcultarTablas.onclick=function(){
				var ed=editorDiccionario
				ed.ocultarTablasDeSistema=!ed.ocultarTablasDeSistema
				
				if (ed.ocultarTablasDeSistema)
					ponEstilo(btnOcultarTablas, 'estado_on')
				else
					quitaEstilo(btnOcultarTablas, 'estado_on')
		
				ed.pintaTablasFisicas()
				ed.pintaTablasVirtuales()
				return false
				}
	var btnRearrancar=creaObjProp('a', {'style.backgroundPosition':'left center', 'texto':'Recargar aplicación', className:'botonera gris imagen texto', id:'btnRecargarAplicacion', href:'recarga.jsp?aplicacion='+aplicacion, target:'_blank', title:'Recargar aplicación'})
	
	var btn=this.creaBarraHerramientas([	btnSalir, this.creaSeparadorBotonera(), 
								btnConexiones, this.creaSeparadorBotonera(), 
								btnOcultarTablas, this.creaSeparadorBotonera(), 
								btnRearrancar])
	
	//splitter
	var splitter=this.creaObj('div', 'clsSplitter', null, 'splitterDic') 
	
	var panFisico=creaObjProp('div', {className:'section clsPane', id: '__panelfisico__'})
	var panLogico=creaObjProp('div', {className:'section clsPane', id:'__panellogico__'})
	
	splitter.appendChild( panFisico )
	splitter.appendChild( panLogico )
	splitter.appendChild( this.creaObj('div', 'clsDragBar', null, 'sepVertical')  )
		
	this.anhadeEtiquetaYTabla(panFisico, 'Tablas Físicas', 'tablasFisicas', 'fisicas')
	this.anhadeEtiquetaYTabla(panFisico, 'Columnas Físicas', 'columnasFisicas', 'fisicas')
	this.anhadeEtiquetaYTabla(panFisico, 'Referencias Físicas', 'referenciasFisicas', 'fisicas')
	
	this.anhadeEtiquetaYTabla(panLogico, 'Tablas Virtuales', 'tablasVirtuales', 'virtuales')
	this.anhadeEtiquetaYTabla(panLogico, 'Columnas Virtuales', 'columnasVirtuales', 'virtuales')
	this.anhadeEtiquetaYTabla(panLogico, 'Campos Virtuales', 'camposVirtuales', 'virtuales')
	this.anhadeEtiquetaYTabla(panLogico, 'Referencias Virtuales', 'referenciasVirtuales', 'virtuales')
	
	var ventana=creaObj('div')
	ventana.appendChild( btn )
	ventana.appendChild( splitter )
	
	ventana.style.height=this.sacaH()+'px'
	ventana.style.width=this.anchoFrm+'px'

	return ventana
}
FormularioEditorDiccionario.prototype.cancelar=function(){
	//http://$GOTTA/diccionario.jsp?aplicacion=XX
	var t=window.location.pathname.split('/')
	t[t.length-1]=t[t.length-1].split('?')[1]
	
	window.location=t.join('/')
}
FormularioEditorDiccionario.prototype.activate=function(){
	this.onResize(this)
	this.cargaDiccionarioV(aplicacion)
	
	if (this.catalogos==null) {
		editaConexiones()
		return
		}
	
	this.cargaDiccionarioF()
}
FormularioEditorDiccionario.prototype.anhadeEtiquetaYTabla=function(cont, etiqueta, idTabla, tipo){
	var et=creaObj('div', 'literal '+tipo)
	et.appendChild( creaT(etiqueta) )
	var tabla=creaObj('div', 'lvw', idTabla)
	
	cont.appendChild( et)
	cont.appendChild( tabla)
	
	this[idTabla]=new Lista(idTabla, true)
}
FormularioEditorDiccionario.prototype.cargaDiccionarioV=function(conexion) {
	this.tablasVirtuales.borraHijos()
	this.tablasVirtuales.ponThrobber()
	var params={'accion':'diccionarioFisico', 'aplicacion':aplicacion, 'operacion':'lecturaLogica'}
	loadJSONDoc( 'json', params, function(req){
							var datos=xeval(req)
							dicV=new DiccionarioV(datos.tablas)
							editorDiccionario.pintaTablasVirtuales()
							})
}
FormularioEditorDiccionario.prototype.cargaDiccionarioF=function() {
	this.tablasFisicas.borraHijos()
	this.tablasFisicas.ponThrobber()
	var params= {'accion': 'diccionarioFisico', 'aplicacion':aplicacion, 'operacion':'lecturafisica'}	
	for (var i=0; i<this.catalogos.length; i++)
		params['bbdd'+i]=this.catalogos[i]

	loadJSONDoc( 'json', params, function(req){
							var datos=xeval(req)
							dicF=new DiccionarioF(datos.tablas)
							editorDiccionario.pintaTablasFisicas()
							})
}
FormularioEditorDiccionario.prototype.pintaTablasFisicas=function(){
	var fnClick=function(fila){ 	
				var idLista=$(fila).parents('div.lvw')[0].id
				var tabla=dicF.tablas.get(fila.id)
						editorDiccionario.cargaTablasFV(fila.id, idLista) 
						}
	var fnDobleClick=function(fila){	var tf=dicF.tablas.get(fila.id)
							tf.altaVirtual()
							}
	if (dicF) { //apañamos los iconos
		for (var i=0; i<dicF.tablas.length; i++) {
			var tf=dicF.tablas.get(i)
			var tv=dicV.tablas.get(tf.nombre)
			tf.icono=(tv !=null ? './fijo/dic.hayTabla' : null )
			}
			
		this.tablasFisicas.rellenaLista(['Catálogo.Esquema', 'Nombre', 'Clave'], 
							['esquema', 'nombre', 'columnasClave'], 
							this.quitaTablasGotta(dicF.tablas.datos()), 
							'id', fnClick, fnDobleClick, true, null, 'icono')
		}
}
FormularioEditorDiccionario.prototype.pintaTablasVirtuales=function(){
	var fnClick=function(fila){
		var idLista=$(fila).parents('div.lvw')[0].id
		editorDiccionario.cargaTablasFV(fila.id, idLista)
		}
	var fnDobleClick=function(fila){editorDiccionario.editarTabla(fila.id)}
	this.tablasVirtuales.rellenaLista(	['Nombre','Clave', 'Col. Búsqueda', 'Col. Descripción (para buscadores)', 'Errores'], 
							['nombre','clave', 'columnabusqueda', 'columnadescripcion', 'errores'], 
							this.quitaTablasGotta(dicV.tablas.datos()), 
							'id', fnClick, fnDobleClick, true, fnContext('tab'), 'icono')
}
FormularioEditorDiccionario.prototype.quitaTablasGotta=function(col){
	if (!this.ocultarTablasDeSistema) return col
	
	var ret=[]
	for (var i=0; i<col.length; i++){
		var t=col[i]
		if (t!=null && !t.esDeGotta)
			ret.push(t)
		}
	return ret
	}
FormularioEditorDiccionario.prototype.cargaTablasFV=function(esquemaTabla, quienLlama){	
	if (esquemaTabla==null)
		return
		
	var nombreTabla=sacaUltimo(esquemaTabla)
	if (dicF){
		if (quienLlama!='tablasFisicas') this.hazScroll('tablasFisicas', nombreTabla)
		this.cargaTablaF(esquemaTabla)
		}
	if (dicV){
		if (quienLlama!='tablasVirtuales') this.hazScroll('tablasVirtuales', nombreTabla)
		this.cargaTablaV(nombreTabla)
		}
}
FormularioEditorDiccionario.prototype.cargaTablaF= function (esquemaTabla) {	
	var t
	if (esquemaTabla.contains(punto))
		t=dicF.tablas.get(esquemaTabla)
	else
		t=dicF.getTablaPorNombre(esquemaTabla)
		
	if (t) {
		if (!t.cargada)
			t.cargar()
		else {
			t.pintaColumnas(this.columnasFisicas)
			t.pintaReferencias(this.referenciasFisicas	)
			}
		}
	else { 
		this.columnasFisicas.borraHijos() 
		this.referenciasFisicas.borraHijos() 
		} 
}
FormularioEditorDiccionario.prototype.cargaTablaV= function (nombreTabla) {	
	var t=dicV.tablas.get(nombreTabla)
	if (t) {
		t.pintaColumnas(this.columnasVirtuales)
		t.pintaCampos(this.camposVirtuales)
		t.pintaReferencias(this.referenciasVirtuales)
		}
	else {
		this.columnasVirtuales.borraHijos() 
		this.camposVirtuales.borraHijos() 
		this.referenciasVirtuales.borraHijos() 
		}
}
//////
FormularioEditorDiccionario.prototype.editarTabla=function (id){
	var x	= dicV.tablas.get(id)
	x.editar(x.nombre)
}
FormularioEditorDiccionario.prototype.editarColumna=function (id){
	var x = dicV.getColumna(id)
	x.editar(x.nombre)
}
FormularioEditorDiccionario.prototype.editarCampo=function (id){
	var x = dicV.getCampo(id)
	x.editar(x.nombre)
}
FormularioEditorDiccionario.prototype.editarReferencia=function (id){
	var x = dicV.getCampo(id)
	x.editarReferencia(x.nombre)
}
///////
FormularioEditorDiccionario.prototype.nuevoVirtual=function(tipo, id){
	ocultaMenus()
	
	if (['col', 'cam', 'ref'].contains(tipo)){
		var nt=this.tablasVirtuales.getFilasPinchadas()[0]
		var tabla=dicV.tablas.get(nt)
		if (tipo=='col') {
			var n=new ColumnaV(ColumnaV.prototype.vacia, tabla)
			n.editar(null)
			}
		else if (tipo=='cam') {
			var n=new CampoV(CampoV.prototype.vacio, tabla)
			n.editar(null)
			}
		else if (tipo=='ref') {
			var n=new CampoV(CampoV.prototype.referenciaVacia, tabla)
			n.editarReferencia(null)
			}
		}
	else {
		var n=new TablaV(TablaV.prototype.vacia)
		n.editar(null)
		}
}
FormularioEditorDiccionario.prototype.borrarVirtual=function(tipo, id){
	ocultaMenus()
	
	if (tipo=='col') {
		var n=dicV.getColumna(id)
		n.borrar()
		}
	else if (tipo=='cam') {
		var n=dicV.getCampo(id)
		n.borrar()
		}
	else if (tipo=='ref') {
		var n=dicV.getCampo(id)
		n.borrar()
		}
	else {
		var n=dicV.tablas.get(id)
		n.borrar()
		editorDiccionario.cargaTablasFV(id, 'tablasVirtuales')
		}
}
///////
FormularioEditorDiccionario.prototype.hazScroll=function(idTabla, idBuscado){
	var div=control(idTabla)
	if (div==null) return //mientras está leyendo el dic físico, parece que falla esto
	
	var filas=this[idTabla].tbody.childNodes

	idBuscado=idBuscado.toLowerCase()
	for (var i=0; i<filas.length; i++) {
		var fila=filas[i]
		var valor=sacaUltimo(fila.id)
		if (valor.toLowerCase()==idBuscado) {
			fila.scrollIntoView(false)
			if (!tieneEstilo(fila, 'seleccionada')) seleccionarFila(fila, false)
			return
			}
		}
}
///////////////////////////////////////////////////////////////////////////
function fnContext(tipoObjeto) {
	return function(fila, event){//menú contextual filas
		var tipoMenu='dic_altabaja'
		if (event==null){
			tipoMenu='dic_alta'
			event=fila
			}
		var contextMenu=montaMenuLVW(tipoMenu, tipoObjeto, (fila!=null?fila.id:null) )
		colocarMenu(contextMenu, event)
		return false
		}
	}
function marcaTablaGotta(t){
	var prefijos=['dic_', 'exp_', 'tra_', 'ast_', 'dll_', 'rel_usuvsper', 'usuarios',
			'ext_',
			'tablabasemodelo','tablatramitesmodelo',
			'dtproperties', 'sysconstraints', 'syssegments'
			]
	
	t.esDeGotta=false
	for (var j=0; j<prefijos.length; j++){
		if ( t.nombre.toLowerCase().startsWith( prefijos[j] ) )
			t.esDeGotta=true
		}	
}
///////////////////////////////////////////////////////////////////////////

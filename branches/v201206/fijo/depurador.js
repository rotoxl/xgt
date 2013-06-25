var cdColumnas=[null, 	null, 	null,  'condicion',	'resultCond',	'accion',  	'p1', 			'p2', 			'p3', 			'p4']
var dsColumnas=[null, 	null, 	null,  'Condición',	'Valor', 	  	'Acción',  	'Parámetro 1', 	'Parámetro 2', 	'Parámetro 3', 	'Parámetro 4']
var wColumnas=[18,  	18]

var sepIdx='_'
var maxProfundidad=0

var dicAcciones={'ADD':'accion_ADD','CAN':'accion_CAN','CAR':'accion_CAR','CTP':'accion_CTP','DEL':'accion_DEL','DLL':'accion_DLL','EJE':'accion_EJE','FOR':'accion_FOR','FRM':'accion_FRM','GTO':'accion_GTO','JPP':'accion_JPP',
'JPR':'accion_JPR','LBL':'accion_LBL','MOV':'accion_MOV','MSG':'accion_MSG','PEN':'accion_PEN','PSS':'accion_PSS','REP':'accion_REP','SUB':'accion_SUB','X!':'accion_Xno','X!?':'accion_Xsi','X>X':'accion_X_X'}

function creaSpan(texto){
	var sp=document.createElement('span')
	sp.appendChild(creaT(texto))
	return sp
}

//////////////////////////////////////////////////////////////
function FormularioDepurador(datos, idxBloqueo, autoexec){
	Frm.call(this, 'depurador', idxBloqueo)
	this.modoDisenho=false
	
	this.datos=datos
	this.datosEvolucion=datos
	
	this.altoBotonera=25
	this.porcentajeAltoFrm=95
	this.porcentajeAnchoFrm=99 // %
	this.altoPie=0//este no lleva pie
	
	this.titulo='Lote'
	this.rutaIcono='./fijo/debug22.png'
	this.playPausaActivos=true
	this.permitirEditar=false
	this.autoexec=autoexec
}
FormularioDepurador.prototype=new Frm
FormularioDepurador.prototype.espacioSobrante=0
FormularioDepurador.prototype.onResize=function(inicializar){
	if (depuradorActivo==null ) {//el depurador también aparece en mapatram, y ahí no queremos que se redimensione
		xRemoveEventListener(window, 'resize')
		return
		}
	
	var w=control(this.idx).offsetWidth
	var h=this.altoCuerpo
	var limite=h/8
	
	if (inicializar==true || this.splitter==null){
		var posVertical=Number(recuperaDato('depurador.splitter', 'top'))
		if (isNaN(posVertical) || posVertical>(h-limite))
			posVertical=3*h/5 
		
		this.splitter = new xSplitter('splitterH',  0, 0, w, h, false, 7,     posVertical,    limite,     true, 0)
		
		var self=this
		var fnOnResize=function(){self.onResize(false)}
		
		this.splitter.setFnOnBarDrag(fnOnResize)
		xAddEventListener(window, 'resize', fnOnResize, false)
		}
	else if (!exp.mapatram){
		var posVertical=this.splitter.getPosBarra()
		this.splitter.paint(w, h, posVertical, limite)
		
		salvaDato('depurador.splitter', 'top', posVertical)
		}
}
FormularioDepurador.prototype.preactivate=function(){
	this.onResize(true)
	this.actualizaDatosCompletos()
	if (recuperaDato('depurador', 'edicionDeLinea'))
		this.iniciaEdicion(true)
}
FormularioDepurador.prototype.pintaCuerpo=function(){
	//botonera
	var btn=this.creaObj('div', 'botonera',null, 'botoneraDepuracion') //tipo, clase, hijo, id, texto, onClick
	
	var btnPlay = this.creaObj('a', 'botonera gris imagen texto estado_off', creaSpan('Continuar') , 'btnPlay', null, function(){depuradorActivo.clickPlay();return false})
	var btnPausa = this.creaObj('a', 'botonera gris imagen texto estado_off', creaSpan('Pausa') , 'btnPausa', null, function(){depuradorActivo.clickPausa();return false})	
	var btnStop = this.creaObj('a', 'botonera gris imagen texto estado_off', creaSpan('Detener'), 'btnStop', null, function(){depuradorActivo.cancela()})
	
	var btnIrA = this.creaObj('a', 'botonera gris imagen texto estado_off', creaSpan('Ir a'), 'btnIrA', null, function(){depuradorActivo.clickIrA()})
	
	var btnEditar= this.creaObj('a', 'botonera gris imagen texto estado_off', creaSpan('Editar'), 'btnEditar', null, function(){ depuradorActivo.iniciaEdicion()})
	var btnInsertarAntes = creaObjProp('a',{className:'botonera gris imagen texto estado_disabled _editar_', title:'Insertar una nueva fila en la parte superior',
						texto:'Insertar antes', id:'btnInsertarAntes', onclick:function(){depuradorActivo.insertarAntes()}})
	var btnInsertarDespues = creaObjProp('a',{className:'botonera gris imagen texto estado_disabled _editar_', title:'Insertar una nueva fila en la parte inferior',
						texto:'Insertar después', id:'btnInsertarDespues', onclick:function(){depuradorActivo.insertarDespues()}})
						
	var btnEliminarFila= creaObjProp('a',{className:'botonera gris imagen texto estado_disabled _editar_', title:'Eliminar la fila seleccionada',
						texto:'Eliminar fila', id:'btnEliminarFila', onclick:function(){depuradorActivo.eliminarFila()}})
	//~ var btnNovedades= creaObjProp('a',{className:'botonera gris imagen texto estado_off conSeparador', title:'Novedades',
						//~ texto:'Novedades', id:'btnNovedades', onclick:function(){depuradorActivo.novedades()}})
	var btnRenumerarAcciones= creaObjProp('a',{className:'botonera gris imagen texto estado_disabled _editar_', title:'Renumerar las acciones dejando un hueco de 10 entre una y otra',
						texto:'Renumerar', id:'btnRenumerarAcciones', onclick:function(){depuradorActivo.renumerarAcciones()}})

	var btnRecargarAcciones= creaObjProp('a',{className:'botonera gris imagen texto estado_disabled _editar_', title:'Recargar las acciones',
						texto:'Recargar', id:'btnRecargarAcciones', onclick:function(){depuradorActivo.recargarAcciones()}})
					
	var btnNuevoAsistido=this.creaObj('a', 'botonera texto imagen gris dropdownmenu estado_disabled _editar_', creaSpan('Asistente') , 'btnAsistente', null)
	var sublista=creaObjProp('ul')
	this.anhadeAsistentes(sublista, btn)
						
	var btn=this.creaBarraHerramientas(	[btnPlay, btnPausa, btnStop, this.creaSeparadorBotonera(), 
								btnIrA, btnEditar, this.creaSeparadorBotonera(), 
								btnInsertarAntes, btnInsertarDespues, btnEliminarFila, 
								this.creaSeparadorBotonera(), 
								btnRenumerarAcciones, btnRecargarAcciones,
								[btnNuevoAsistido, sublista]] )
	ponEstilo(btn, 'gris')
	
	this.btnPlay=btnPlay
	this.btnPausa=btnPausa
	this.btn
	
	//splitter
	var splitter=creaObjProp('div', {className:'clsSplitter horizontal', id:'splitterH'}) 
	
	this.panAcciones=creaObjProp('div', {className:'section clsPane', 	id:'__panelAcciones__'})
	this.panTablas=creaObjProp('div', {className:'section clsPane', 	id:'__panelTablas__'})
	
	splitter.appendChild( this.panAcciones )
	splitter.appendChild( this.panTablas )
	splitter.appendChild( this.creaObj('div', 'clsDragBar')  )
		
	var cuerpo=creaObjProp('div', {className:'cuerpo sinPie', hijos:[btn, splitter]})
	return cuerpo
}
FormularioDepurador.prototype.anhadeAsistentes=function(ul, div ) {
	var asistentes=this.asistentes()
	
	for (var k in asistentes) {
		var ml=asistentes[k]
		if (!ml) 
			continue
		
		var enlace
		if (ml.ds.startsWith('-'))
			enlace=creaObjProp('a', {className:'menuML separador _editar_ estado_disabled', texto:ml.ds.substring(1)})
		else
			enlace=creaObjProp('a', {onclick:fnGeneraCodigo(ml.id), className:'_editar_ estado_disabled menuML '+ml.css, texto:ml.ds, hijo:creaObjProp('span', {className:'dsExtendida', texto:ml.dsExtendida})})
		
		if (ml.dsExtendida)
			ponEstilo(enlace, 'dsExtendida')
		
		ul.appendChild( creaObjProp('li', {hijo:enlace}))
		}
}
function fnGeneraCodigo(id){
	return function(){depuradorActivo.generaCodigo(id)}
	}
FormularioDepurador.prototype._generaCodigo=function(asistente, respuestafrm){
	var idxActual=this.filaSel || this.tramites[0].acciones[0].idx
	var tram=this.getTramite(idxActual)
	var accion=tram.acciones[this.getPosicionAccion(idxActual)]
	
	var params= {accion:'depurador.'+asistente.id, 
						camino:accion.camino, 
						tramite:accion.tramite, 
						op:accion.op,
						previsualizar:true,
						aplicacion:exp.aplicacion}
	params= $.extend(params, respuestafrm)
						
	loadJSONDoc ( 'json', params, function(req){
					var previsualizarTramite=new FormularioPrevisualizar(req, 'idxBloqueo', params)
					previsualizarTramite.toDOM()
					previsualizarTramite.resaltaFilasNuevas(req)
					})
	}
FormularioDepurador.prototype.generaCodigo=function(idExtensor){
	if (!this.permitirEditar)
		return
	this.finEditarValor()
	
	var idxActual=this.filaSel || this.tramites[0].acciones[0].idx
	var tram=this.getTramite(idxActual)
	var accion=tram.acciones[this.getPosicionAccion(idxActual)]
	
	//si tiene "param" generamos un formulario alrededor
	var asistente=this.asistentes(accion.op)[idExtensor]
	if (asistente.param){
		this.montaFrmDelAsistente(asistente, accion.op)
		return
		}
	this._generaCodigo(asistente, null)
	ocultaMenusPanSup()
	}
FormularioDepurador.prototype.montaFrmDelAsistente=function(asistente){
	var datosFRM=asistente.param
	
	disenhador.idxBloqueo=ponNuevoBloqueo()
	datosFRM.titulo='Generar código 1/2: parámetros'
			
	var xfrm=new Formulario( datosFRM, disenhador.idxBloqueo )
	
	xfrm.onAceptar=function(){
		var param = xfrm.recogeValoresFRM()
		depuradorActivo._generaCodigo(asistente, param)
		xfrm.cierraFormulario()
		}
	
	xfrm.toDOM()
	return false
	}
FormularioDepurador.prototype.asistentes=function(cd_operacion){
	var dic_tablas={tipo:'bsc', id:'DIC_Tablas.Nombre', ds:'Tabla', controles:[{tipo:'lvw', id:'tabla', maxlength:-25, valor:this.tb}]}
	
	var p0=this._generaFRM([dic_tablas])
	var pjasper=this._generaFRM([{tipo:'lvw', ds:'Nombre del informe', id:'nombreInforme', maxlength:-25, dsExtendida:'El informe debe estar en la carpeta "rptWeb" configurada en DIC_Configuracion (por defecto, Aplicaciones/MI_APLI/rpt/)'}])
	
	var asistentes= {	'-sep00':{ds:'-Edición de tablas'}
						,  'generaTramite.Alta': 		{css:'lvw', id:'generaTramite.Alta',  		ds:'Alta de una tabla', 		param:p0}
						,  'generaTramite.Baja':  	{css:'lvw', id:'generaTramite.Baja',  	ds:'Baja de una tabla', 		param:p0}
						,  'generaTramite.Modif':  	{css:'lvw', id:'generaTramite.Modif',  	ds:'Modificación de una tabla',param:p0}
					,  '-sep01':{ds:'-Emisión de informes'}
						,  'generaTramite.EmitirJasper': {css:'lvw', id:'generaTramite.EmitirJasper',  	ds:'Jasper', param:pjasper, dsExtendida:'Emisión de informe Jasper, incluyendo la asignación de parámetros'}
						,  'generaTramite.EmitirWord':  {css:'lvw', id:'generaTramite.EmitirWord',  	ds:'Word (próximamente)', dsExtendida:null}
					,  '-sep02':{ds:'-Otros'}
						,  'generaTramite.CapturaError':{css:'lvw', id:'generaTramite.CapturaError',  	ds:'Bloque de captura de errores (próximamente)', dsExtendida:null}
						,  'generaTramite.ExtraerSUB':  {css:'lvw', id:'generaTramite.ExtraerSUB',  	ds:'Extraer SUB (próximamente)', dsExtendida:'Extrae una sección del trámite actual a un nuevo trámite SUB para ser reutilizada desde otros trámites. En el trámite actual se inserta la llamada al nuevo SUB'}
				}
	return asistentes	
	}
FormularioDepurador.prototype._generaFRM=function(controles){
	var antesODespues={tipo:'lvwLista', id:'posicion', ds:'Insertar', valor:'despues', 'lista':{'antes' : 'antes de la fila seleccionada', 'despues' : 'después de la fila seleccionada'}}
	
	if (controles==null)
		controles=[]
	
	controles.splice(0,0,antesODespues)
	return {numTramites:0, camino:0, tramite:'_asistente', tipo:'frm', 'controles':controles}
	}
FormularioDepurador.prototype.pintaPie=function(){
	return creaObj('div')
}
FormularioDepurador.prototype.creaObjConElipsis=function(tipo, clase, texto, id, title, onclick){
	var d=this.creaObj(tipo, clase, null, id, title, onclick)
	
	var xt
	if (texto==null) {
		xt=creaT('(Nulo)')
		ponEstilo(d, 'NULO')
		}
	else if (texto==='') {
		xt=creaT('(Cadena vacía)')
		ponEstilo(d, 'NULO')
		}
	else {
		xt=creaTConElipsis(texto, d)
		}
		
	d.appendChild( xt )
	
	return d
}
FormularioDepurador.prototype.getTramite=function(idx, lista){
	if (lista==null)
		lista=this.datos.tramites
	if (!idx.startsWith(sepIdx)) //por si viene el id de una acción, nos quedamos sólo con el trozo que interesa
		idx=idx.substring(idx.indexOf(sepIdx))
		
	for (var j=0; j<lista.length; j++) {
		if ( lista[ j ].idx==idx )
			return lista[ j ]
	}
}
FormularioDepurador.prototype.getAccion=function(idx){//2500|TraspasoNOMINAS_AUX|80|4156180
	return this._getPosicionAccion(idx, false)
}
FormularioDepurador.prototype.getPosicionAccion=function(idx){//2500|TraspasoNOMINAS_AUX|80|4156180
	return this._getPosicionAccion(idx, true)
}
FormularioDepurador.prototype._getPosicionAccion=function(idx, devolverPosicion){//2500|TraspasoNOMINAS_AUX|80|4156180
	var sep=sepIdx
	var temp=idx.split(sep)
	
	var idAccion=temp[0]
	
	temp[0]=null
	var idTram=temp.join(sep)
	
	var tram=this.getTramite(idTram)
	
	for (var i=0; i<tram.acciones.length; i++){
		var acc=tram.acciones[i]
		if (acc.op==idAccion){
			if (devolverPosicion)
				return i
			else
				return acc
			}
		}
	return null
}
FormularioDepurador.prototype.montaTablaAcciones=function(tram, idTram, esActivo, otroTexto) {
	var yaExiste=control(tram.idx)
	if (yaExiste)
		yaExiste.parentNode.removeChild(yaExiste)
	
	var panel=this.montaTablaContenedora(this.panAcciones, tram.idx, tram.tramite+', '+tram.camino, tram.infoExtendida, esActivo, 'tramite', otroTexto)
		
	var arbol=new ArbolColumnas(panel, 't_'+idTram, cdColumnas, dsColumnas, wColumnas)
	arbol.dibujaArbol(this.datos, tram.acciones )
	this.arboles[idTram]=arbol
	
	this.montaPieTablaAcciones(panel)
	
	//preparamos el resalte de formularios
	var tbody=arbol.tabla
	
	this.resaltaFRM(tbody)
}
FormularioDepurador.prototype.montaPieTablaAcciones=function(panel){
	panel.parentNode.appendChild(creaObjProp('div', {className:'footer tramFinalizado datosListosParaVolcar'}))
	panel.parentNode.appendChild(creaObjProp('div', {className:'footer tramFinalizado datosVolcados', }))
	}
FormularioDepurador.prototype.strAccion=function(acc){
	if (acc=='X>X')
		return 'X_X'
	else if (acc=='X!?')
		return 'Xsi'
	else if (acc=='X>X')
		return 'Xno'
	else	
		return acc
	}
FormularioDepurador.prototype.resaltaFRM=function(tbody){
	//marca los FRM en naranja
	var inicio
	for (var i=0;i<tbody.rows.length; i++) {
		var fila=tbody.rows[i]
		if (valorCeldaAccion(fila).endsWith('FRM') && !valorCeldaP1(fila).toLowerCase().endsWith('fin')) {
			var inicio=i
			var encontrada=false
			while (!encontrada && i<(tbody.rows.length-1)){
				i++
				var fila2=tbody.rows[i]
				encontrada=(valorCeldaAccion(fila2)=='FRM' && valorCeldaP1(fila2).toLowerCase()=='fin' )
				}
			if (encontrada)
				this._resaltaFRM(tbody, inicio, i)
			}
		}
}
FormularioDepurador.prototype._resaltaFRM=function(tbody, inicio, fin){
	var estiloBorde='2px solid #F97D6D'

	var colInicio=cdColumnas.indexOf('accion')
	var colFin=cdColumnas.indexOf('p4')
	
	for (var j=inicio; j<tbody.rows.length && j<=fin; j++) {
		tbody.rows[j].childNodes[colInicio].style.borderLeft= estiloBorde
		tbody.rows[j].childNodes[colFin].style.borderRight= estiloBorde
		
		ponEstilo(tbody.rows[j], 'accion_FRM', true)
		}
	for (var c=colInicio; c<colFin+1; c++) {
		tbody.rows[inicio].childNodes[c].style.borderTop=estiloBorde
		tbody.rows[fin].childNodes[c].style.borderBottom=estiloBorde
		}
		
	//~ for (var j=inicio; j<tbody.rows.length && j<=fin; j++) {
		//~ for (var c=colInicio; c<colFin+1; c++) 
			//~ tbody.rows[j].childNodes[c].style.background=estiloFondo
		//~ }
}
FormularioDepurador.prototype.fnOnClick=function(){
	return function(event) {
		if (depuradorActivo.filaSel==this.id)
			return
		
		var ant=depuradorActivo.filaSel
		
		var filas=$(this.parentNode).find('tr')
		if (event!=null && event.shiftKey){
			var filasid=[]
			$.each(filas, function(key,value){filasid.push(value.id)})
			
			var _filaA=filasid.indexOfIgnoreCase(ant)
			var _filaZ=filasid.indexOfIgnoreCase(this.id)
			
			var filaA=Math.min(_filaA, _filaZ)
			var filaZ=Math.max(_filaA, _filaZ)
			
			for (var i=filaA; i<=filaZ; i++){
				ponEstilo(filas[i], 'seleccionada')	
				}
				
			depuradorActivo.filaSel=this.id
			}
		else {
			depuradorActivo.filaSel=this.id
			
			if (event==null){
				filas.removeClass('seleccionada')
				}
			else if (ant && !event.ctrlKey){
				//~ var ctl= control(ant)
				filas.removeClass('seleccionada')
				}
			ponEstilo(control(depuradorActivo.filaSel), 'seleccionada')
			}
		}
	}
FormularioDepurador.prototype.getAccion=function(idAccion){
	//idAccion=440|EnviarAviso|220|3451272
	var idTramite=idAccion.substring(idAccion.indexOf(sepIdx))
	var tram=this.getTramite(idTramite)
	for (var f=0; f<tram.acciones.length; f++) {
		var acc=tram.acciones[f]
		if (acc.idx==idAccion)
			return acc
		}
}
function valorCeldaAccion(fila){
	return valorCelda(fila.childNodes[ cdColumnas.indexOf('accion') ])}
function valorCeldaP1(fila){
	return valorCelda(fila.childNodes[cdColumnas.indexOf('p1')])}
function valorCelda(td){
	try {
		var ret=td.firstChild.nodeValue
		if (ret){
			if (ret.startsWith(espacioDuro))
				return ret.substring(1)
			}
		else
			return ''
		}
	catch (e){
		return '' 
		}
	}
FormularioDepurador.prototype.creaT=function(texto){
	if (texto==null || texto=='')
		texto=String.fromCharCode(160)
	return document.createTextNode( texto )
}

FormularioDepurador.prototype.montaTablaTablas = function (tabla) {
	var t = this.creaObj('table', 'lvw', null, 't_'+tabla.idx)//tipo, clase, hijo, id
	var tbody=this.creaObj('tbody')
	t.appendChild(tbody)

	var idxTabla='t_'+tabla.idx
	var panel=control(idxTabla)
	
	var dsExtendida='('+tabla.datos.filas.length+(tabla.datos.filas.length!=1?' filas cargadas':' fila cargada')+')'
	if (panel==null){
		panel=this.montaTablaContenedora( this.panTablas, tabla.idx, tabla.idx, dsExtendida, false, 'tabla')
		}
	else  {
		borraHijos(panel)	
		$('div#'+tabla.idx + ' span.sp1').text(dsExtendida)
		}
	panel.appendChild(t)
		
	
	var cab=this.creaObj('tr', 'lvw')
	cab.appendChild ( this.creaObj('th', 'especial', null, null, null, 'center' ))
	
	var clave=tabla.clave || []
	//1º las de la clave
	for (var i=0;i<clave.length; i++)
		cab.appendChild ( this.creaObj('th', 'lvw', creaT( clave[i] + '*') ))
	//luego, el resto	
	for (var i=0;i<tabla.datos.columnas.length; i++) {
		if ( clave.indexOfIgnoreCase( tabla.datos.columnas[i].toLowerCase() )==-1 )
			cab.appendChild ( this.creaObj('th', 'lvw', creaT( tabla.datos.columnas[i] ) ))
		}
	
	t.appendChild( this.creaObj('thead', null, cab) )
	
	for (var j=0; j<tabla.datos.filas.length; j++) {
		tr=this.creaObj('tr', (j==tabla.filaActiva ? 'fila seleccionada' : 'fila' ), null, tabla.idx+'_'+j )
			
		var accion=null
		var img=null
		var existe=tabla.nuevos.indexOf(j)
		if ( tabla.nuevos.indexOf(j)>-1 ) { 
			img='depurador.regNuevo'
			accion='añadido'
			}
		else if ( tabla.modificados.indexOf(j)>-1 ) { 
			img='depurador.regModificado'
			accion='modificado'
			}
		else if ( tabla.borrados.indexOf(j)>-1 ) { 
			img='depurador.regEliminado'
			accion='eliminado'
			}
		
		tr.appendChild ( this.creaObj('td', 'especial', (accion!=null ? creaImg('fijo/'+img+'.png', null, null, 'El registro ha sido '+accion ) : null) ))
	
		var filaModif=tabla.nuevos.contains(j) || tabla.modificados.contains(j)
		//1º las de la clave
		for (var jj=0; jj<clave.length; jj++) {
			var colModif=tabla.colsModif.indexOf( clave[jj].toLowerCase() )
			i=tabla.datos.columnas.indexOfIgnoreCase( clave[jj])
			var dato=tabla.datos.filas[j][i]
			tr.appendChild ( this.creaObjConElipsis( 	'td', 'lvw' + (colModif >-1 && filaModif ? ' comentario' : ''),  dato ) )
			}
		//luego, el resto
		for (var i=0;i<tabla.datos.columnas.length; i++) {
			if ( clave.indexOfIgnoreCase( tabla.datos.columnas[i] )==-1 ){
				var colModif=tabla.colsModif.indexOf( tabla.datos.columnas[i].toLowerCase() )
				
				var dato=tabla.datos.filas[j][i]
				tr.appendChild ( this.creaObjConElipsis( 	'td', 'lvw' + (colModif >-1 && filaModif ? ' comentario' : ''),  dato ) )
				}
			}
		tbody.appendChild( tr )
		}
	
	var xp=panel.parentNode
	if (tieneEstilo(xp, 'panelAcordeon'))
		xp=xp.parentNode
		
	if ( (tabla.nuevos.length+tabla.modificados.length+tabla.borrados.length) >0)
		ponEstilo(xp, 'tablaNoEditada')
	else
		quitaEstilo(xp, 'tablaNoEditada')
		
	if (tabla.datos.filas.length==0)
		ponEstilo(xp, 'tablaVacia')
	else
		quitaEstilo(xp, 'tablaVacia')
	
	var tRaiz=this.getTramite(this.datos.tramRaiz)
	if (tRaiz.tablaBase==tabla.idx) ponEstilo(xp, 'tablaBase')
	if (tRaiz.tablaTramites==tabla.idx) ponEstilo(xp, 'tablaTramites')
	if (tRaiz.tablaFirmas==tabla.idx) ponEstilo(xp, 'tablaFirmas')
	
	}
FormularioDepurador.prototype.montaTablaVariables=function(variables) {
	var idx='variables'
	var idxTabla='t_'+idx
	
	//cambio para sacarlas ordenadas alfabéticamente
	var fnOrdenar=function(var1,var2){return var1.nombre.toLowerCase()>var2.nombre.toLowerCase()}
	variables.sort(fnOrdenar)
	//ponemos las de sistema primero
	var vEspeciales=['@', '~', '$']
	for (var l=0; l<vEspeciales.length; l++){
		for (var i=0;i<variables.length; i++) {
			if (variables[i].nombre==vEspeciales[l]){
				var variable=variables[i]
				variables.remove(i)
				variables.splice(0, 0, variable)
				}
			}
		}
		
	var panel=control('x'+idxTabla)
	if (panel==null)
		panel=this.montaTablaContenedora( this.panTablas, idx, idx, null, false, 'variables')
	else 
		borraHijos(panel)	
	
	var variablesDeSistema=[], variablesDeUsuario=[]
	for (var i=0;i<variables.length; i++){
		var v=variables[i]
		
		if (v.nombre == '=')
			continue
		else if (v.esDeSistema)
			variablesDeSistema.push(v)
		else
			variablesDeUsuario.push(v)
		}
	this.montaBloqueVariables(panel, 't_vSistema', 'Control del lote y errores', variablesDeSistema)
	this.montaBloqueVariables(panel, 't_vUsuario', 'Variables de usuario', variablesDeUsuario)
}
FormularioDepurador.prototype._montaTablaParaBloqueVariables = function(panel, idBloque) {
	var thead=creaObj('thead'); var tbody=creaObj('tbody')
	var filas=[]
	var columnas=['Variable', 'Valor', 'Tipo de dato']
	for (var i=0; i<columnas.length; i++){
		var fila=creaObjProp('tr', {className:'lvw', hijo:creaObjProp('th', {className:'lvw cabecera', 'style.textAlign':'right', texto:columnas[i] }) })
		
		if (i==0)
			thead.appendChild(fila)
		else
			tbody.appendChild(fila)
		
		filas.push(fila)
		}
	var t = creaObjProp('table', {className:'lvw', hijo:thead, id:idBloque})
	t.appendChild(tbody)
	panel.appendChild(t)
	
	return filas
	}
FormularioDepurador.prototype.montaBloqueVariables = function(panel, idBloque, dsBloque, variables) {
	var filas, filaNombre, filaValor, filaTipo
	var ultimoPadre
	
	var ttp={'@':'Modo lista activo', '$':'Número de trámite en ejecución', '~':'Total de trámites del lote'}
	for (var i=0;i<variables.length; i++) {
		var v=variables[i]
		
		if (filaNombre==null || ultimoPadre!=v.variablePadre){
			filas=this._montaTablaParaBloqueVariables(panel)
			filaNombre=filas[0]; filaValor=filas[1]; filaTipo=filas[2]
			}
		
		//~ var hijo=ttp[v.nombre]? creaObjProp('abbr', {texto:v.nombre, title:ttp[v.nombre]}) : creaT(v.nombre)
		filaNombre.appendChild( creaObjProp('th', {className:'lvw cabecera'+(ttp[v.nombre]? ' comentario' : ''), texto:v.nombre, title:(ttp[v.nombre]?  ttp[v.nombre]: '')}) )
		
		var d=this.creaObj('td', 'lvw' + (v.modificada? ' comentario' : '') ) 
		var texto=v.valor 
		if (texto==null) { 
			texto='(Nulo)' 
			ponEstilo(d, 'NULO') 
			} 
		else if (texto==='') { 
			texto='(Cadena vacía)' 
			ponEstilo(d, 'NULO') 
			} 
		
		texto=creaT(texto) 
		
		d.appendChild(texto) 
		filaValor.appendChild(d) 
		
		filaTipo.appendChild( creaObjProp('td', {className:'lvw', texto:v.tipo} ) )
		
		//
		ultimoPadre=v.variablePadre
		}
	}
FormularioDepurador.prototype.montaTablaContenedora = function(panel, id, textoObjeto, descObjeto, esActivo, claseAdicional, otroTexto) {
	var padre = creaObjProp('div', {className:'article acordeon '+claseAdicional, 'id':id})
	if (esActivo) ponEstilo(padre, 'seleccionada')
	var h=creaObjProp('div', {	className:'header',
						title:'[Click izquiero aquí para mostrar/ocultar; Click derecho para mostrar el menú]', 
						onclick:function(){ocultarBloqueTabla(id); return false;} ,
						hijos:[ 	creaObjProp('span', {className:'sp0' ,texto:textoObjeto}), 
								creaObjProp('span', {className:'sp1' ,texto:descObjeto}),
								creaObjProp('span', {className:'AdeN', texto:otroTexto || ''})
								],
						oncontextmenu:function(event){return muestraMenu(event, 'depuracion.menuTablas')}
						})
	padre.appendChild(h)
	panel.appendChild(padre)

	var tablaAnidada=creaObjProp('div', {className:'panelAcordeon', id:'xt_'+id})	
	padre.appendChild(tablaAnidada)
	
	return tablaAnidada
	}
FormularioDepurador.prototype.insertarPuntoParo=function(idx){	
	var accion=this.getAccion(idx)
	accion.tienePausa=!accion.tienePausa
	
	var fila=control(idx)
	if (accion.tienePausa)
		ponEstilo(fila, 'puntoParo')
	else
		quitaEstilo(fila, 'puntoParo')
	
	//y le mandamos al servidor la info
	var params= {'accion': (accion.tienePausa ? 'ponerpausa' : 'quitarpausa') ,'aplicacion': exp.aplicacion, 'idx':idx}
	loadJSONDoc ( 'json', params, function(){} )
}
FormularioDepurador.prototype.fnInsertarPuntoParo = function(idx) {
	return function() 
		{ depuradorActivo.insertarPuntoParo(idx) }
	}
FormularioDepurador.prototype.cancela=function(){
	this.finEditarValor()
	depuradorActivo=null
	this.ocultaFormulario(true)
}
function fnRetornoDepuracion(autoexec){
	return function(req){
		var respuesta=xeval(req)
		if(autoexec)
			retornoAutoexec(respuesta, autoexec.md)
		else
			retornoTramite( respuesta )}
}
FormularioDepurador.prototype.clickPausa=function(){
	if (this.playPausaActivos) {
		var params= {'accion':'pausa','aplicacion': exp.aplicacion}
		loadJSONDoc ( 'json', params, fnRetornoDepuracion(this.autoexec))
		}
}
FormularioDepurador.prototype.actualizaFilaAccion=function(filaNueva, filaVieja, idTramite){
	var idx = filaNueva.idx
	
	var xfila=control(idx)
	if (xfila) {
		ponEstilo(xfila, filaNueva.ejecutada? 'tipo_ejecutada' : 'tipo_')
		//~ xfila.className=filaNueva.ejecutada? 'arbolColumnasE' : 'arbolColumnas'
		}
		
	if (filaNueva.resultCond!=null && filaNueva.resultCond.toString() != ''){
		var cond=control('cond_'+filaNueva.idx)
		if (cond)//puede que esté en un trámite anidado
			replacePrimerHijo(cond, creaT( filaNueva.resultCond ) )
		}
	if (filaVieja.comprime != null ) {
		var tramViejo = this.getTramite( filaVieja.comprime )
		var tramNuevo = this.getTramite(filaVieja.comprime, this.tramitesModificados  )
		if (tramViejo!=null && tramNuevo!=null) {
			for (var i=0;i<tramViejo.acciones.length; i++) {
				if (tramNuevo.acciones[i])
					this.actualizaFilaAccion(tramNuevo.acciones[i], tramViejo.acciones[i], idTramite)
				}
			}
		}
}
FormularioDepurador.prototype.retornoTramitacion = function (nuevosDatos){
	if (nuevosDatos.tipo=='pausa') {
		this.datosEvolucion=nuevosDatos
		this.actualizaDatosTrasPausa(nuevosDatos)
		}
	else	{
		this.datos=nuevosDatos
		this.actualizaDatosCompletos() //porque a veces, si hay [SUB camino @tramite] cambia la lista de trámites
		this.datosEvolucion=nuevosDatos
		this.actualizaDatosTrasPausa(nuevosDatos)
		}
	this.getHayQueAbrir()
}
FormularioDepurador.prototype.hazScroll=function(obj){
	//scrollIntoView no vale porque me mueve el formulario entero
	var offsetTop=0
	while (obj.tagName.toUpperCase()!='DIV') {
		offsetTop+=obj.offsetTop
		obj=obj.parentNode
		}
	this.panAcciones.scrollTop = offsetTop-50
	obj.focus()
}
FormularioDepurador.prototype.activaBotones=function(activos){
	this.playPausaActivos=activos
	if (this.playPausaActivos==true) {
		quitaEstilo(this.btnPlay, 'estado_disabled')
		quitaEstilo(this.btnPausa, 'estado_disabled')
		}
	else 	{
		ponEstilo(this.btnPlay, 'estado_disabled')
		ponEstilo(this.btnPausa, 'estado_disabled')
		}
}
////////////////////////////////////////////////////////////////////
FormularioDepurador.prototype._insertarAccion=function(sumar){
	if (!this.permitirEditar)
		return
	
	this.finEditarValor()
	var idxActual=this.filaSel
	var tram=this.getTramite(idxActual)
	
	var pos=this.getPosicionAccion(idxActual)
	var op1=0,op2=0
	var accion=this.getAccion(idxActual)
	op2=accion.op
	
	var nuevaOperacion, nuevoIndice
	if ((pos>0 && sumar<0) || (sumar>0)){
		var acc1=tram.acciones[pos+sumar]
		if (pos+1==tram.acciones.length) {
			acc1=tram.acciones[pos]
			op2=acc1.op+10
			nuevaOperacion=Number(acc1.op)+1
			}
		else if (acc1!=null)
			op1= acc1.op
		else {
			nuevaOperacion=op1
			op1=Number(op2)+sumar*500
			}
		}
		
	//nos situaremos en ella: 
	op2=Number(op2); op1=Number(op1)
	if (op2<op1) {
		var temp=op2
		op2=op1
		op1=temp
		}
	
	var hayHueco=(op2-1>op1)
	if (!hayHueco){
		alert('No hay hueco para meter una acción ahí')
		return 
		}
	
	
	if (sumar<1) {
		if (nuevaOperacion==null)
			nuevaOperacion=op2-1
		nuevoIndice=pos+1+sumar
		}
	else	{
		if (nuevaOperacion==null)
			nuevaOperacion=op1+1
		nuevoIndice=pos+sumar
		}
		
	var nuevoIdx=nuevaOperacion+tram.idx
	var nuevaAccion={'op': ''+nuevaOperacion, 'camino':tram.camino, 'tramite':tram.tramite, 'tienePausa':false, 'ejecutada':false, 'condicion':'',
				'accion':accion.accion, 'p1':accion.p1,'p2':accion.p2,'p3':accion.p3,'p4':accion.p4, 
				'idx':nuevoIdx, 'resultCond':'', 'esNueva':true}
	
	tram.acciones.splice(nuevoIndice, 0, nuevaAccion)
	//y creamos la fila en la tabla con esta información
	this.quitaInfoProfundidad()
	this.actualizaDatosCompletos()
	
	var tr=control(nuevoIdx)
	tr.onclick()//para poner el foco
	this.guardaFila(nuevaAccion, tr)
	}
FormularioDepurador.prototype.insertarDespues=function(){
	this._insertarAccion(+1)
}
FormularioDepurador.prototype.insertarAntes=function(){
	this._insertarAccion(-1)
}
FormularioDepurador.prototype.eliminarFila=function(){
	if (!this.permitirEditar || !this.filaSel)
		return
	this.finEditarValor()
	var accion=this.getAccion(this.filaSel)
	accion.esBorrar=true
	this.guardaFila(accion, control(this.filaSel))
}
FormularioDepurador.prototype.novedades=function(){
	
}
FormularioDepurador.prototype.recargarAcciones=function(){
	if (!this.permitirEditar)
		return
	
	this.finEditarValor()
	var params= {'accion':'recargarAcciones','aplicacion': exp.aplicacion}
	var idx=ponNuevoBloqueo(true, 'recargando...')
	loadJSONDoc('json', params, function(req){
		depuradorActivo.retornoTramitacion(xeval(req))
		quitaBloqueo(idx)
		})

	}
FormularioDepurador.prototype.renumerarAcciones=function(){
	if (!this.permitirEditar || !this.filaSel)
		return
	
	this.finEditarValor()
	
	if (!this.filaSel)
		this.filaSel=this.tramites[0].acciones[0].idx
	var accion=this.getAccion(this.filaSel)
	
	var idx=ponNuevoBloqueo(true, 'recargando...')
	var params= {'accion':'renumerarAcciones','aplicacion': exp.aplicacion, camino:accion.camino, tramite:accion.tramite}
	loadJSONDoc('json', params, function(req){
		depuradorActivo.retornoTramitacion(xeval(req))
		quitaBloqueo(idx)
		})
	
	}
FormularioDepurador.prototype.ocultarPanelInferior=function(){
	borra(this.panTablas)
	var dragBar=$('div.clsDragBar')[0]
	borra(dragBar)
	
	this.panAcciones.style.height=$(this.panAcciones.parentNode).innerHeight()+'px'
	}
////////////////////////////////////////////////////////////////////
FormularioDepurador.prototype.getHayQueAbrir=function(){
	var idTramActivo=this.datosEvolucion.tramActivo
	var tramActivo=this.getTramite(idTramActivo)
	
	var listaPadres=[]
	this.listaAccExpandir=[]
	
	var padre=this.buscaPadre(idTramActivo)
	while (padre!=null) {
		listaPadres.push(padre)
		padre=this.buscaPadre(padre)
		}
	
	for (var i=this.listaAccExpandir.length-1; i>-1; i--) {
		this.arboles[this.datos.tramRaiz].expandirTVW(this.listaAccExpandir[i]) 
		}
}
FormularioDepurador.prototype.buscaPadre=function(idx){
	for (var i=0; i<this.datosEvolucion.tramites.length; i++){
		var tram=this.datos.tramites[i]
		for (var f=0; f<tram.acciones.length; f++)  {
			var acc=tram.acciones[f]
			if (acc.comprime && acc.comprime==idx){
				this.listaAccExpandir.push(acc.cd)
				return tram.idx
				}
			}
		}
}
FormularioDepurador.prototype.actualizaDatosCompletos=function(){
	this.tramites=this.datos.tramites
	
	for (var i=0; i<this.datos.tramitesDelLote.length; i++) {
		var idx=this.datos.tramitesDelLote[i]
		
		var tram=getTramite(idx, this.datos.tramites)
		//tram.hayQueExpandir=[] //para ser capaces de expandir un SUB cuando lleguemos a él
		
		arreglaDatosParaArbol(this.datos, idx, '')
		}
	wColumnas[2]=18*maxProfundidad+55 //para ajustar al máximo el ancho de la columna que lleva el árbol
	
	this.tramitesModificados=new Array()
	this.tramitesDelLote=this.datos.tramitesDelLote
	this.arboles={} //idTram: árbol
	
	this.tablas=this.datos.tablas
	this.variables=this.datos.variables
	this.tramActivo=this.datos.tramActivo
	
	/////////////////
	this.montaTablaVariables(this.variables, this.panTablas)
	
	for (var nt=0; nt< this.tramitesDelLote.length; nt++) {
		var idTram=this.tramitesDelLote[nt]
		var t=this.getTramite( idTram , this.datos.tramites)
		
		this.montaTablaAcciones( t, idTram, t.idx==this.datos.tramRaiz, 'trámite '+(nt+1)+' de '+this.tramitesDelLote.length )
		}
	
	//mostramos la fila activa
	var idx=this.getTramite(this.tramActivo, this.datos.tramites).filaActiva
	var xfila=control( idx )
	if (xfila) {
		ponEstilo(xfila, 'tipo_activa')//xfila.className='arbolColumnasS'
		this.hazScroll(xfila)
		}
	
	for (var i=0; i<this.tablas.length; i++) {
		if ( this.tablas[i] ) this.montaTablaTablas( this.tablas[i] )
		}
}
FormularioDepurador.prototype.hazScroll2=function(obj){
	obj.scrollIntoView(false)
	}
FormularioDepurador.prototype.actualizaDatosTrasPausa = function (nuevosDatos, conScroll){
	//empezamos a modificar todo lo que tenemos pintado
	this.tramitesModificados=nuevosDatos.tramites
	
	var tactivo=control(nuevosDatos.tramRaiz)
	
	var puntoDeScroll
	for (var t=0; t<nuevosDatos.tramitesDelLote.length; t++)  {
		var tramNuevo=this.tramitesModificados[t]
		var tramViejo=this.datos.tramites[ t ]
		
		var xtn=$('.article#'+tramNuevo.idx)
		if (tramNuevo.datosDescargados) {
			xtn.removeClass('datosListosParaVolcar').addClass('datosVolcados')
			puntoDeScroll=xtn.find('.footer.tramFinalizado')
			}
		else if (tramNuevo.datosListosParaDescarga) {
			xtn.removeClass('datosVolcados').addClass('datosListosParaVolcar')
			puntoDeScroll=xtn.find('.footer.tramFinalizado')
			}
		
		if (control(tramNuevo.idx)){
			var $obj=$(tramNuevo.idx)
			if (tramNuevo.idx==nuevosDatos.tramRaiz) 
				$obj.addClass('seleccionada')
			else
				$obj.removeClass('seleccionada')
			}
			
		//1º las filas que han sido ejecutadas
		for (var f=0; f<tramNuevo.acciones.length; f++)  
			this.actualizaFilaAccion(tramNuevo.acciones[f], tramViejo.acciones[f], t)
		}
	
	//2º la fila activa del trámite o SUBtrámite activo
	if (nuevosDatos.tramActivo.contains(sepIdx) ) {
		var SUBactivo=nuevosDatos.tramActivo
		var tSUBactivo=this.getTramite(SUBactivo, nuevosDatos.tramites)
		var fila=control(tSUBactivo.filaActiva)
		if (fila)  {
			ponEstilo(fila, 'tipo_activa')//fila.className='arbolColumnasS'
			if (conScroll==null || conScroll) 
				puntoDeScroll=$(fila)
			}
		}
	else {
		var idx = this.getTramite(nuevosDatos.tramActivo, nuevosDatos.tramites).filaActiva
		var xfila=control(idx)
		ponEstilo(xfila, 'tipo_activa')//xfila.className='arbolColumnasS'
		puntoDeScroll=$(fila)
		}
	
	if (puntoDeScroll) {
		var padre=$(this.panAcciones)
		var ptop=padre.offset().top
		var pheight=padre.innerHeight()
		
		var xtop=puntoDeScroll.offset().top
		var xheight=puntoDeScroll.innerHeight()
		
		if ( (ptop<xtop) && ((xtop+xheight)<(ptop+pheight)) ){
			//pass
			}
		else
			$(this.panAcciones).scrollTo(puntoDeScroll, 0)
		}
		
	// 3º tablas: nuevas y modificadas
	this.tablas=nuevosDatos.tablas
	for (var i=0; i<nuevosDatos.tablas.length; i++) {	
		if ( nuevosDatos.tablas[i] ) 
			this.montaTablaTablas( nuevosDatos.tablas[i] )
		}
	
	//4º variables: nuevas y modificadas
	this.variables=nuevosDatos.variables
	this.montaTablaVariables(this.variables)
}
////////////////////////////////////////////////////////////////////
FormularioDepurador.prototype.quitaInfoProfundidad=function(){
	for (var i=0; i<this.datos.tramites.length; i++) {
		var tram=this.datos.tramites[i]
		quitaInfoProfundidad(tram)
		}
}
////////////////////////////////////////////////////////////////////
FormularioDepurador.prototype.clickPlay=function(){
	if (this.playPausaActivos) {
		var params= {'accion':'play','aplicacion': exp.aplicacion}
		loadJSONDoc('json', params, fnRetornoDepuracion(this.autoexec))
		}
}
FormularioDepurador.prototype.clickIrA=function(){
	var idTram=this.datos.tramActivo
	var idx=this.arboles[idTram].getFilasPinchadas()[0]
	idx=idx.substring(idx.indexOf('_')+1)
	var params= {'accion':'irA','aplicacion': exp.aplicacion, 'idx':idx}
	loadJSONDoc ( 'json', params, fnRetornoDepuracion(this.autoexec))
}
FormularioDepurador.prototype.guardaFila=function(accion, tr){
	var params= {'accion':'verificaAccion', 'aplicacion': exp.aplicacion,
				'idx':accion.idx, 
				'camino':accion.camino, 'tramite':accion.tramite, 'op':accion.op, 
				'cond':accion.condicion,
				'acc':accion.accion, 'p1':accion.p1, 'p2':accion.p2, 'p3':accion.p3, 'p4':accion.p4,
				'esNueva':accion.esNueva? 1:0,
				'esBorrar':accion.esBorrar? 1:0}
	loadJSONDocPost ( 'json', params, function(req){
						var obj=xeval(req) 
						var td=tr.childNodes[1]
						
						if (obj.tipo=='error'){//error guardando
							quitaEstilo(tr, 'errorVerificandoAccion')
							ponEstilo(tr, 'errorGuardandoAccion')
							td.title=obj.msg
							}
						else {
							if (obj.tipo=='errorVerificando'){//guardada pero que sepas que lleva error
								ponEstilo(tr, 'errorVerificandoAccion')
								td.title=obj.msg
								}
							else {
								quitaEstilo(tr, 'errorVerificandoAccion')
								td.title=null
								}
							td.parentNode.id=obj.idx
							if (accion.esBorrar){
								var pos=depuradorActivo.getPosicionAccion(accion.idx)
								var tram=depuradorActivo.getTramite(accion.idx)
								tram.acciones.remove(pos)
								
								var siguiente=tr.previousSibling || tr.nextSibling
								tr.parentNode.removeChild(tr)
								siguiente.onclick()
								}
							else {
								accion.idx=obj.idx
								accion.esNueva=false
								accion.esBorrar=false
								}
							}
						} )
	}
////////////////////////////////////////////////////////////////////
FormularioDepurador.prototype.cerrarTablas=function(){
	ocultaMenus()
	$('#__panelTablas__ div.article.tabla').addClass('cerrado')
	}
FormularioDepurador.prototype.abrirTablasConFilas=function(){
	ocultaMenus()
	$('#__panelTablas__ div.article.tabla:not(.tablaVacia)').removeClass('cerrado')
	}
FormularioDepurador.prototype.abrirTablas=function(){
	ocultaMenus()
	$('#__panelTablas__ div.article.tabla').removeClass('cerrado')
	}
////////////////////////////////////////////////////////////////////
FormularioDepurador.prototype.ordenarTablasAlfab=function(){
	ocultaMenus()
	
	var listaTablas=$('#__panelTablas__ div.article.tabla')
	var sortNombre=function(a,b){return a.id.toLowerCase() > b.id.toLowerCase()}
	
	listaTablas.sort(sortNombre)
	listaTablas.each(function(i, t){
		var p=t.parentNode
		p.removeChild(t)
		p.appendChild(t)
		})	
	}
FormularioDepurador.prototype.ordenarTablasConDatos=function(){
	ocultaMenus()

	var listaTablas=$('#__panelTablas__ div.article.tabla')
	var sortxx=function(a,b){
		if (tieneEstilo(a, 'tablaBase')) 
			return 0
		else if (tieneEstilo(b, 'tablaBase'))
			return 1
		
		if (tieneEstilo(a, 'tablaTramites'))
			return 0
		else if (tieneEstilo(b, 'tablaTramites'))
			return 1
		
		//~ if (!tieneEstilo(a, 'tablaVacia'))
		
		return a.id.toLowerCase() > b.id.toLowerCase()
		}
	
	listaTablas.sort(sortxx)
	listaTablas.each(function(i, t){
		var p=t.parentNode
		p.removeChild(t)
		p.appendChild(t)
		})
		
	}
////////////////////////////////////////////////////////////////////
FormularioDepurador.prototype.fnEditaValor=function(nodo, columna, celda){
	return function() {
		depuradorActivo.editaValor(nodo, columna, celda) }
}

FormularioDepurador.prototype.editaValor=function(nodo, columna, celda){
	if (!this.permitirEditar){
		this.iniciaEdicion()
		}
	if (!editBox) 
		editBox=new EditBox(this.panAcciones)
	editBox.editaValor(nodo, columna, celda)
}
FormularioDepurador.prototype.fnFinEditarValor=function(){
	return function() {
		depuradorActivo.finEditarValor() }
}
FormularioDepurador.prototype.finEditarValor=function(){
	if (editBox ) {
		var hayCambios=editBox.guardaValor() || editBox.accion.hayQueGuardar
		if (hayCambios)
			depuradorActivo.guardaFila(editBox.accion, editBox.celda.parentNode)
		editBox.oculta()
		}
}
FormularioDepurador.prototype.iniciaEdicion=function(activarSinSacarCuadro){
	var tempPermitirEditar=!this.permitirEditar
	
	var btn=control('btnEditar')
	if (tempPermitirEditar) {
		ponEstilo(btn, 'estado_on')
		
		$('a._editar_').removeClass('estado_disabled')
		
		//~ quitaEstilo(control('btnInsertarAntes'), 		'estado_disabled')
		//~ quitaEstilo(control('btnInsertarDespues'), 	'estado_disabled')
		//~ quitaEstilo(control('btnEliminarFila'), 		'estado_disabled')
		//~ quitaEstilo(control('btnRenumerarAcciones'), 	'estado_disabled')
		//~ quitaEstilo(control('btnRecargarAcciones'), 	'estado_disabled')
		//~ quitaEstilo(control('btnAsistente'), 			'estado_disabled')
		
		salvaDato('depurador', 'edicionDeLinea', true)
		}
	else	{
		ponEstilo(btn, 'estado_off')
		
		$('a._editar_').addClass('estado_disabled')
	
		//~ ponEstilo(control('btnInsertarAntes'), 		'estado_disabled')
		//~ ponEstilo(control('btnInsertarDespues'), 		'estado_disabled')
		//~ ponEstilo(control('btnEliminarFila'), 			'estado_disabled')
		//~ ponEstilo(control('btnRenumerarAcciones'), 	'estado_disabled')
		//~ ponEstilo(control('btnRecargarAcciones'), 	'estado_disabled')
		//~ ponEstilo(control('btnAsistente'), 			'estado_disabled')
		
		salvaDato('depurador', 'edicionDeLinea', false)
		}
	
	this.permitirEditar=tempPermitirEditar
		
	if (activarSinSacarCuadro!=undefined)
		return
		
	if (tempPermitirEditar){
		var idx=this.tramitesDelLote[0]
		var fila=this.filaSel!=null? control(this.filaSel) : this.primeraFilaVisible()
		var acc= (fila!=null?this.getAccion(fila.id):this.getTramite(idx).acciones[0])
		
		this.editaValor(acc, 'p1', fila.childNodes[6])
		}
	else  {
		this.finEditarValor()
		if (editBox) editBox.eliminaCaja()
		}
	}
//////////////////////////////////////////////////////
FormularioDepurador.prototype.primeraFilaVisible=function(){
	var altoFila=18
	var altoTrozoResto=19
	var pan=this.panAcciones
	
	var filas=control('t_'+this.tramitesDelLote[0]).rows
	var totalFilas=filas.length
	
	var menosFila=(pan.clientHeight+pan.scrollHeight-(pan.clientHeight+pan.scrollTop)-altoTrozoResto)/altoFila
	var numFila= totalFilas-menosFila	
	numFila=Math.round(numFila)-1
	return filas[ Math.max(numFila, 0) ]
}
/////////////////////////////////////////////////////
function ocultarBloqueTabla(idTram) {
	var tram=$( control(idTram) ) //parece que si no es así no tira, debe ser que los ids no pueden empezar por |
	
	tram.toggleClass('cerrado')
	
	//~ var t=tram.find('div.acordeon')
	//~ var a=tram.find('> h3')
	//~ var cerrado=tram.hasClass('cerrado')
	//~ if (cerrado) {
		//~ a.removeClass('cerrado')
		//~ t.slideDown(jquerycssmenu.fadesettings.overduration)
		//~ }
	//~ else {
		//~ a.addClass('cerrado')
		//~ t.slideUp(jquerycssmenu.fadesettings.overduration)
		//~ }
	}
function activarDepuracion(){
	var estado=!FormularioDepurador.modoDisenho
	loadJSONDoc( 'json', 
			{'accion':'depuracion', aplicacion: exp.aplicacion, 'estado':estado}, 
			function() {
				var enlace=control('btnDepurador')
				var ttp="[Depuración activa] Desactivar depuración"
				
				if (!estado) {
					ttp="[Depuración inactiva] Activar depuración"
					quitaEstilo(enlace, 'estado_on')
					}
				else
					ponEstilo(enlace, 'estado_on')
				
				enlace.title=ttp
				FormularioDepurador.modoDisenho=estado
				},
			function ()
				{alert("Se ha producido un error al intentar activar la depuración")}
			)
	}
//////////////////////////////////////////////////////////
function fnMontaCuadroEval(){
	return function(){montaCuadroEval(this)}
	}
function montaCuadroEval(td) {
	var expresion = td.textContent
	if (expresion==null || expresion.trim()=='' || expresion.trim()==espacioDuro) {
		return
		}	
	else
		expresion=expresion.substring(1)
	loadJSONDoc( 'json',  { 'accion':'eval', 'exp': expresion, aplicacion: exp.aplicacion }, 
				function(req){ 
						//~ if (req.trim()!=''){
							//~ var valor=xeval(req)
							td.title='['+expresion+'] = '+req[0]
							//~ td.title= '<b>'+td.textContent+" = </b>"+req 
							//~ tooltip.show(td)
							//~ td.onmouseout=function(){tooltip.hide(this)}
							//~ console.log(td.title)
							//~ }
						}, 
				function(){/*pass*/} 
				)
	}
//////////////////////////////////////////////////////////
var editBox
var vbKeyIntro=13, vbKeyCancel=27
var vbKeyLeft=37, vbKeyUp=38, vbKeyRight=39, vbKeyDown=40
var vbKeyF2=113, vbKeyTab=9
var teclasCapturadas=teclasDireccion+'/tab/shift+tab/enter/escape/f2'

EditBox.prototype.listaProps=['-1','-2', 'op', 'condicion', '-3', 'accion', 'p1', 'p2', 'p3', 'p4']//OJO: las que empiezan por - son NO EDITABLES
function EditBox(contenedor){
	this.cont=contenedor
	
	this.creaCaja()
	this.accion=null
	this.prop=null
	
	this.fila=null
	this.celda=null
	
	this.editando=false
}
EditBox.prototype.creaCaja=function(){
	this.cont=depuradorActivo.panAcciones
	this.ed=creaObjProp('input:text', {id:'editBox', className:'editBox' } )
	this.ed.onblur=function(){editBox.guardaValor()}
	this.cont.appendChild(this.ed)
	
	this.ponEventos()
	}
EditBox.prototype.ponEventos=function(){
	var self=this
	jwerty.key(teclasCapturadas, 
			function(event){
				return self.onKeyDown(event)
				}, 
			null, 
			$(this.ed))
	}
EditBox.prototype.eliminaCaja=function(){
	var caja=control('editBox')
	if (caja) 
		caja.parentNode.removeChild(caja)
}
EditBox.prototype.sustituyeCaja=function(valor){
	var ed2=creaObjProp('input:text', {id:'editBox', className:'editBox'/*, 'style.display':'none'*/ } )
	
	mueve(ed2, quitaPx(this.ed.style.left), quitaPx(this.ed.style.top), quitaPx(this.ed.style.width), quitaPx(this.ed.style.height))
	ed2.value=valor
	
	this.ed.parentNode.replaceChild(ed2, this.ed)
	
	try {ed2.selectionStart=0; ed2.selectionEnd=valor.length}
	catch (e) {}	
	
	ed2.onblur=function(){editBox.guardaValor()}
	this.ed=ed2
}
EditBox.prototype.editaValor=function(accion, prop, celda){
	if (depuradorActivo && !depuradorActivo.permitirEditar)
		return
	
	var fila=celda.parentNode
	
	if (!control('editBox'))
		this.creaCaja()
	var ed=this.ed
	
	ed.style.display='none'
	fila.onclick()//para cambiar el foco
	
	var tope, izq, ancho, alto
	var $celda=$(celda)
	var offset=$celda.offset()
	tope=offset.top; izq=offset.left; ancho=$celda.outerWidth()-3
	alto=$celda.innerHeight()-2
	
	mueve(ed, izq, tope, ancho, alto)
	
	if (prop.startsWith('-')) {//no editable
		ponEstilo(ed, 'noEditable')
		this.editando=false
		
		ed.readonly=true
		ed.value=''
		}
	else 	{
		quitaEstilo(ed, 'noEditable')
		this.editando=true
		
		ed.readonly=false
		ed.value=accion[prop]
		this.ed.style.display='block'
		}	
	
	this.activa()
	
	this.accion=accion
	this.prop=prop
	this.celda=celda
	this.fila=celda.parentNode
}
EditBox.prototype.activa=function(){
	this.ed.style.display='block'
	try {this.ed.selectionStart=0; this.ed.selectionEnd=this.ed.value.length }
	catch (e) {}
	this.ed.focus()
}
EditBox.prototype.oculta=function(){
	this.editando=false
	this.ed.readonly=true
	this.ed.value=''
	this.ed.style.display='none'
}
EditBox.prototype.guardaValor=function(){
	if (!depuradorActivo || !depuradorActivo.permitirEditar)
		return
	if (this.editando) {
		this.editando=false
		var nuevoValor=this.ed.value
		
		if (this.accion[this.prop]==nuevoValor)
			return false
			
		this.accion[this.prop]=nuevoValor
		this.accion.hayQueGuardar=true
		
		var css=dicAcciones[nuevoValor]
		if (this.prop=='accion' && css) ponEstilo(this.fila, css)
		
		if (nuevoValor)  nuevoValor=espacioDuro+nuevoValor
		
		if (this.prop=='op')
			replacePrimerHijo(this.celda.lastChild, creaT( nuevoValor ) )
		else
			replacePrimerHijo(this.celda, creaT( nuevoValor ) )
		}
	return this.accion.hayQueGuardar
}
EditBox.prototype.onKeyDown=function(event){
	var evt = event || window.event
	var keyCode=evt.keyCode, shiftKey=evt.shiftKey 
	
	var ed=editBox
	
	if (keyCode==vbKeyUp || keyCode==vbKeyDown){
		var hayCambios=ed.guardaValor()
		var fila=ed.celda.parentNode
		if (hayCambios) {
			depuradorActivo.guardaFila(ed.accion, fila)
			ed.accion.hayQueGuardar=false
			}
		
		var sigFila=keyCode==vbKeyDown? fila.nextSibling : fila.previousSibling
		if (sigFila){
			var acc=depuradorActivo.getAccion(sigFila.id)
			
			var idProp=ed.listaProps.indexOfIgnoreCase(ed.prop)
			ed.editaValor(acc, ed.prop, sigFila.childNodes[idProp] )
			}
		}
	else if (keyCode==vbKeyCancel){
		this.editando=false
		ed.sustituyeCaja( ed.accion[ed.prop] )
		
		ed.ed.style.display='block'
		ed.ed.focus()
		this.ponEventos() //por alguna razón aquí dejan de funcionar
		}
	else if (keyCode==vbKeyF2){
		if (ed.ed.selectionStart!=0 || ed.ed.selectionEnd!=ed.ed.value.length){
			ed.ed.selectionStart=0
			ed.ed.selectionEnd=ed.ed.value.length
			}
		else 	{
			ed.ed.selectionStart=0
			ed.ed.selectionEnd=0
			}
		}
	else if (	keyCode==vbKeyLeft ||
			keyCode==vbKeyRight ||
			keyCode==vbKeyUp ||
			keyCode==vbKeyDown || 
			keyCode==vbKeyTab){
			
		ed.guardaValor()
		
		var nuevaAccion=ed.accion, nuevaProp=ed.prop, nuevaCelda=ed.celda
		if (keyCode==vbKeyLeft || keyCode==vbKeyRight || keyCode==vbKeyTab) {
			if ((keyCode==vbKeyLeft || keyCode==vbKeyRight) && (ed.ed.selectionStart!=0 || ed.ed.selectionEnd!=ed.ed.value.length))
				return true // si no está todo el texto seleccionado, se supone que es para moverte dentro del cuadro, como en Access
			
			if (keyCode==vbKeyTab){
				keyCode=shiftKey ? vbKeyLeft : vbKeyRight
				}
			var idx=ed.listaProps.indexOfIgnoreCase(ed.prop)
			
			if (keyCode==vbKeyLeft && idx==0){
				}
			else if (keyCode==vbKeyRight && idx==ed.listaProps.length-1 ){
				}
			else {
				var suma=+1
				nuevaCelda=ed.celda.nextSibling
				if (keyCode==vbKeyLeft){
					suma=-1
					nuevaCelda=ed.celda.previousSibling
					}
				var nuevaProp=ed.listaProps[idx+suma]
				}
			}
		else if (keyCode==vbKeyUp || keyCode==vbKeyDown){
			}
		ed.editaValor(nuevaAccion, nuevaProp, nuevaCelda)
		return false
		}
	ed.editando=true
	return false
}
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
//////////
function ArbolColumnas(contenedor, nombre, cdColumnas, dsColumnas, wColumnas){
	this.contenedor=contenedor
	this.control=null
	this.idControl=nombre
	
	this.clase='arbolColumnas'
	
	this.cdColumnas=cdColumnas
	this.dsColumnas=dsColumnas
	this.wColumnas=wColumnas
}
ArbolColumnas.prototype.montaTabla=function(){
	var tabla=creaObj('table', this.clase)
	var thead=creaObj('thead', 'lvw')
	tabla.appendChild(thead)
	
	var tr=creaObj('tr', this.clase)
		thead.appendChild( tr )
	
	for (var i=0; i<this.dsColumnas.length; i++) {
		var texto=this.dsColumnas[i]
		if (texto!=null) 
			texto=espacioDuro+texto
		var th=creaObjProp('th', {className:'lvw', texto:texto/*, ondblclick:fnAjustaAnchoColAuto(th) */})
		if (this.wColumnas[i])
			th.style.width=this.wColumnas[i]+'px'
	
		tr.appendChild( th )
		}
		
	var tbody=creaObj('tbody', this.clase, this.idControl)
		tabla.appendChild(tbody)
		
	this.contenedor.appendChild(tabla)
	//-------------------------------------------
	this.tabla=tbody
	this.t=tabla
}
ArbolColumnas.prototype.recuperaDato=function(){return ''}
ArbolColumnas.prototype.salvaDato=function(){}
ArbolColumnas.prototype.dibujaArbol=function(datos, ramaInicial){
	this.montaTabla()
	this.datos=datos
	this.montaRama(ramaInicial)
	this.habilitaRedimensionado()
}
ArbolColumnas.prototype.habilitaRedimensionado=function(){
	new ColumnResize(this, this.t, 2)
	//~ $(this.t).find('th').each(
		//~ function(index, th){
			//~ var xth=$(th)
			//~ xth.children('div.resizableColumn').width(xth.width() )
			//~ xth.width(0)
			//~ xth[0].style.width=null
			//~ }
		//~ )
	
	}
ArbolColumnas.prototype.montaRama=function (acciones, idControlDestino) {
	idControlDestino=idControlDestino || this.idControl
	
	var puntoInsercion=null
	if (idControlDestino!=this.idControl)
		puntoInsercion=control(idControlDestino).nextSibling

	var t=this.tabla
	
	if (this.datos!=null && acciones.length>0) {
		for (var i=0; i<acciones.length; i++) {
			var nodo=acciones[i]
			if (nodo!=undefined) {
				var tr=this.creaFila(nodo, t.rows.length ) //crea la fila y la 1ª celda
				this.ponRestoColumnas(tr, nodo, cdColumnas)
				
				if (puntoInsercion==null)
					t.appendChild(tr)
				else
					t.insertBefore(tr, puntoInsercion )
				}
			}
		}
	depuradorActivo.resaltaFRM(t)
}
ArbolColumnas.prototype.getFilasPinchadas=function(){
	var l=this.tabla.getElementsByTagName('input')
	var ret=[]
	for (var i=0; i<l.length;i++){
		if (l[i].checked) 
			ret.push( l[i].id )
		}
	return ret
}
ArbolColumnas.prototype.creaFila=function(nodo, num) {
	var tr=creaObj('tr', 	'fila accion_'+FormularioDepurador.prototype.strAccion(nodo.accion)+
					(nodo.tienePausa?' puntoParo':'') , 
					nodo.cd)
	tr.onclick=depuradorActivo.fnOnClick()
	
	var td1=creaObjProp('td', {	className:'puntoParo', 
						title:'[Click aquí para insertar o eliminar un punto de paro]',
						onclick:depuradorActivo.fnInsertarPuntoParo(nodo.idx) })//puntoParoSin, puntoParoCon
	
	td1.appendChild(creaObjProp('input',{type:'checkbox', className:'lvw_chk', id:'checkbox_'+'tr'+nodo.idx, value:'depurador', checked:false}))
	tr.appendChild(td1) 
	
	var td2=creaObj('td', 'tipoAccion')
	if (nodo.msgError) { //si la línea tiene errores, ni puntos de paro ni nada de eso
		ponEstilo(tr, 'errorVerificandoAccion')
		td1.title='Error: ['+nodo.msgError+']'
		}
	tr.appendChild(td2) 
	
	var td=creaObj('td')
	tr.appendChild(td)
	
	for (var i=0; i<nodo.profundidad.length;i++) {
		var letra=nodo.profundidad.substr(i,1)
		td.appendChild( creaObjProp('div', {className:'icoarbol '+(letra=='0' ? 'medio0' : 'blank')} ) )
		}
        
	var js
	
        td.className='arbolColumnas' 
	var clase
        if (nodo.mds != null && nodo.mds != "")  {
		var enlaceIzq
		var titulo, img, alt	
		if (nodo.expandido) {
			titulo = "[Click aquí para contraer]"
			js = fnContraerTVW(this, nodo.cd)
			//~ img = icoGotta+(nodo.esUltimo?"menosfin.png":"menos1.png")
			clase=(nodo.esUltimo ? 'menosfin' : 'menos1')
			alt = "-"
			}
		else {
			titulo = "[Click aquí para expandir]"
			js = fnExpandirTVW(this, nodo.cd)
			//~ img = icoGotta+ (nodo.esUltimo?"masfin.png":"mas1.png")
			clase = (nodo.esUltimo ? 'masfin' : 'mas1')
			alt = "+"
			}

		//~ var img=creaImg(img, alt, 'icoarbol') 
		var img=creaObjProp('div', {alt:alt, className:'icoarbol '+clase, onclick:js}) 
		td.appendChild(img)
		}
        else {//le ponemos el |
		//~ td.appendChild( creaImg(icoGotta+ (nodo.esUltimo?"fin1.png":"medio1.png"), "", "icoarbol" ) ) 
		var clase=(nodo.esUltimo? 'fin1' : 'medio1')
		td.appendChild( creaObjProp('div', {className:'icoarbol '+clase} )) 
		}
	td.appendChild( creaObj('span', null, null, nodo.ds) )
	td.ondblclick=depuradorActivo.fnEditaValor(nodo, 'condicion', td)

	return tr
	}
ArbolColumnas.prototype.ponRestoColumnas=function(tr, nodo){	
	for (var i=3; i<this.cdColumnas.length; i++)  {
		var xcd=this.cdColumnas[i]
		
		var texto=nodo[ xcd ]
		if (texto!=null)  //si no se hace así, aparece el texto cortado por la izquierda: parece un fallo de firefox
			texto=espacioDuro+texto
		
		var td=creaObj('td', xcd, null, texto)
		if (xcd=='resultCond')
			td.id='cond_'+nodo.idx
		
		if (['condicion', 'p1', 'p2', 'p3','p4'].contains(xcd)) 
			td.onmouseover=fnMontaCuadroEval()

		td.ondblclick=depuradorActivo.fnEditaValor(nodo, xcd, td)
		td.onmousedown=depuradorActivo.fnFinEditarValor()
		
		tr.appendChild(td)
		}
	return tr
	}
ArbolColumnas.prototype.getImagen=function(enlacePadre){
	return $('#'+enlacePadre+' td.arbolColumnas div.icoarbol') 
	}
/////////////////////
ArbolColumnas.prototype.expandirTVW=function(enlacePadre) {
	this.expandirContraerTVW(true, enlacePadre) 
	depuradorActivo.actualizaDatosTrasPausa(depuradorActivo.datosEvolucion, false)
	}
ArbolColumnas.prototype.contraerTVW=function(enlacePadre) {
	this.expandirContraerTVW(false, enlacePadre) 
	}
ArbolColumnas.prototype.expandirContraerTVW=function(expandir, enlacePadre) {
	var div=this.getImagen(enlacePadre)
	
	if (expandir) {
		//~ //nos aseguramos de que no está abierto ya
		//~ if (div.hasClass('menos1') || div.hasClass('menosfin'))
			//~ return
		var tram=getTramite(this.datos.comprimidos[enlacePadre], this.datos.tramites)
		var acciones=tram?tram.acciones : []
		
		if ($(this.tabla).find('tr#'+acciones[0].idx).length>0)//¿ya existe?
			return
		
		this.montaRama(acciones, enlacePadre)
		div[0].onclick=fnContraerTVW(this, enlacePadre)
		}
	else {
		var tram=getTramite(this.datos.comprimidos[enlacePadre], this.datos.tramites)
		this.quitaFilas(tram)
		div[0].onclick=fnExpandirTVW(this, enlacePadre)
		}
		
	var cambioClase={menosfin:'masfin', menos1:'mas1', mas1:'menos1', masfin:'menosfin'}	
	for (var c in cambioClase){
		if (div.hasClass(c) ){
			div.removeClass(c).addClass(cambioClase[c])
			return
			}
		}
}
ArbolColumnas.prototype.quitaFilas=function(tram){
	var acciones=tram.acciones
	for (var i=0; i<acciones.length; i++) {
		var nodo=acciones[i]
		if (nodo.comprime!=null){
			this.quitaFilas( getTramite(nodo.comprime, this.datos.tramites) )
			}
		
		var fila=control(nodo.cd)
		if (fila)
			fila.parentNode.removeChild(fila)
		}
}
////////////////////////////////
function fnContraerTVW(p, enlacePadre){
	return function(){p.contraerTVW(enlacePadre);return false;}
	}
function fnExpandirTVW(p, enlacePadre){
	return function(){p.expandirTVW(enlacePadre);return false;}
	}
////////////////////////////////
function getTramite(idx, lista){
	/// esta es la de FormularioDepurador.prototype.getTramite
	if (lista==null)
		lista=this.tramites
	for (var j=0; j<lista.length; j++) {
		if ( lista[ j ].idx==idx )
			return lista[ j ]
		}
	}
function arreglaDatosParaArbol(datos, idx, profundidad, padreEsUltimo) {
	if (datos.comprimidos==null)
		datos.comprimidos={}
	var tram=getTramite(idx, datos.tramites)
	if (tram) {
		var acciones=tram.acciones
		for (var i=0; i<acciones.length; i++) { //le metemos 'profundidad' y 'esUltimo'
			var dato=acciones[i]
			arreglaDatosParaArbol_1linea(datos, acciones, dato, i, profundidad)
			}
		}
	else 	{
		var xidx='10'+idx
		datos.tramites.push({"idx":idx, 'acciones':[
			{"idx" : xidx, 'profundidad': profundidad, 'esUltimo':true, 
			'cd':xidx,
			'ds': '(No cargado)'
			}
			]} )
		}
	}
function arreglaDatosParaArbol_1linea(datos, acciones, dato, i, profundidad){
	if (dato==null) return
	if (dato.profundidad != null) 
		console.error ("¡¡ERROR: el SUB-trámite '"+dato.idx+"' es llamado por varios padres!!")
		
	if ( profundidad.length > maxProfundidad ) maxProfundidad = profundidad.length 
		
	dato.profundidad=profundidad
	dato.esUltimo= (i==acciones.length-1)
	dato.cd=dato.idx
	dato.ds=dato.op
	
	dato.expandido=false
	if ( (dato.accion=='SUB' || dato.accion=='FOR') && dato.comprime) {
		dato.mds=dato.comprime
		
		datos.comprimidos[dato.idx]=dato.comprime
		arreglaDatosParaArbol(datos, dato.comprime, profundidad+(dato.esUltimo?'x':'0'), dato.esUltimo)
		}
}
function quitaInfoProfundidad(tram){
	for (var i=0; i<tram.acciones.length; i++) { //le metemos 'profundidad' y 'esUltimo'
		var dato=tram.acciones[i]
		dato.profundidad=null
		}
}
//////////////////////////////////////
function FormularioPrevisualizar(datos, idxBloqueo, paramsPeticion){
	FormularioDepurador.call(this, datos, idxBloqueo, null)
	this.paramsPeticion=paramsPeticion
	this.idx='previsualizar'
	
	this.porcentajeAnchoFrm=null
	//~ this.anchoFrm=1000
	this.altoFrm=1000
	this.altoPie=33
	
	this.titulo='Generar código 2/2: previsualizar datos a generar'
	this.rutaIcono=null
	
	var self=this
	var cmdAceptar=creaObj('button', 'cmdgt', null, 'Aceptar', function(){self.acepta()})
	var cmdCancelar=creaObj('button', 'cmdgt', null, 'Cancelar', function(){ self.cancela() }) 
	
	this.botones=[cmdAceptar, cmdCancelar]
	}
FormularioPrevisualizar.prototype=new FormularioDepurador
FormularioPrevisualizar.prototype.pintaCuerpo=function(){
	this.panAcciones=creaObjProp('div', {className:'section clsPane autosize'})	
	var cuerpo=creaObjProp('div', {className:'cuerpo', hijos:[this.panAcciones]})
	
	return cuerpo
	}
FormularioPrevisualizar.prototype.onResize=function(){}
FormularioPrevisualizar.prototype.montaTablaContenedora = function(panel, id, textoObjeto, descObjeto, esActivo, claseAdicional, otroTexto) {
	var tablaAnidada=creaObjProp('div', {className:'panelAcordeon', id:'previsualizar_'+id})	
	panel.appendChild(tablaAnidada)
	
	return tablaAnidada
	}
FormularioPrevisualizar.prototype.montaPieTablaAcciones=function(){
	//para que no pinte los pies de "trámite finalizo pero no guardado", etc
	}
FormularioPrevisualizar.prototype.pintaPie=function(){
	return Frm.prototype.pintaPie.call(this)
	}
FormularioPrevisualizar.prototype.acepta=function(){
	this.paramsPeticion.previsualizar=false 
	var self=this
	loadJSONDoc ( 'json', this.paramsPeticion, function(req){
					self.ocultaFormulario(true)
					depuradorActivo.recargarAcciones()
					})
	}
FormularioPrevisualizar.prototype.cancela=function(){
	this.ocultaFormulario(true)
}
FormularioPrevisualizar.prototype.resaltaFilasNuevas=function(datos){
	this._resaltarFilas(datos.accionesNuevas, 'accion_nueva')
	this._resaltarFilas(datos.accionesModif, 'accion_modif')
	}
FormularioPrevisualizar.prototype._resaltarFilas=function(col, clase){
	var tram=this.datos.tramites[0]
	for (var i in col){
		var accion=tram.acciones[ col[i] ]
		if (accion)
			$('#previsualizar tr#'+accion.idx).addClass(clase)
		}
	}
var ENVELOPED='ENVELOPED', ENVELOPING='ENVELOPING', DETACHED='DETACHED'
var ATTACHED='ATTACHED'
var XADES_BES='XADES_BES', XADES_T='XADES_T', XADES_C='XADES_C', XADES_X='XADES_X', XADES_X_L='XADES_X_L', XADES_A='XADES_A'

var listaFrm=[], pendiente={}, buscadores={}

function init_firma(){
	var attrs={
			"code":"es.burke.plataformafirma.applet.AppletFirma",
			"archive":"plataformafirma-applet.jar, plataformafirma-applet-thirdparty.jar",
			"codebase":"./fijo/firma/",
			'width':1,
			'height': 1
			}
	var params={
			'scriptable':'true',
			'browser': ComponenteFirmaElectronica.prototype.whichBrs(),
			'enable.visual.alerts':'false' 
			}
	return init_applet('ControlFirma',attrs,params)
}
function init_applet(nombre,attrs,params){
	var of=control(nombre)
	if (of==null){
	  	of = document.createElement('applet')
	  	of.id=nombre
	  	for (var attr in attrs){
	  		of.setAttribute(attr,attrs[attr])
	  	}
	  	for (var nparam in params){
		  	var param = document.createElement('param')
		  	param.setAttribute('name', nparam)
		  	param.setAttribute('value', params[nparam])
		  	param.setAttribute('encoding', 'UTF-8')
		  	of.appendChild(param)
	  	}
	  	document.body.appendChild(of)
		}
	return of
}
function ComponenteFirmaElectronica(lista, jsInicializacion, sysdate){
	this.lst=lista
	this.applet=init_firma()
	this.jsInicializacion=jsInicializacion
	this.rellenaListaCertificadosDisponibles(sysdate)
}
ComponenteFirmaElectronica.prototype.whichBrs=function() {
	var agt=navigator.userAgent.toLowerCase()

	if (agt.contains("firefox") ) return 'Firefox'
	else if (agt.contains("msie") ) return 'Internet Explorer'
	else if (agt.contains("opera") ) return 'Opera'
	else if (agt.contains("safari") ) return 'Safari'
	else if (agt.contains("chrome") ) return 'Internet Explorer' //no es un error: los certificados están en el mismo almacén que en ie
	else if (agt.contains(' ') )
		return navigator.userAgent.substr(0,agt.indexOf(' '))
	return navigator.userAgent
}
ComponenteFirmaElectronica.prototype.rellenaListaCertificadosDisponibles=function(sysdate){
	if (this.lst.offsetHeight)
		$(this.lst).fadeOut(jquerycssmenu.fadesettings.overduration)
	
	while(this.lst.options.length)
		this.lst.options[0]=null
	var dicFiltro=this.jsInicializacion // vendrá algo como {'nombre': 'ernesto'};...

	var lista=['nombre', 'nserie', 'emisor', 'huella']
	for (var i=0; i<lista.length; i++){
		var nombre=lista[i]
		if (dicFiltro[nombre]==null)
			dicFiltro[nombre]=''
		}
	
	var xml
	try	{
		if (dicFiltro.filtroCertificados)
			this.applet.setCertificateFilter(dicFiltro.filtroCertificados)
		xml=this.applet.getLstCertificados()
		}
	catch (e){
		return
		}
		
	this.certificados=cargaXML(xml).getElementsByTagName('Certificate')
	for (var i=0; i<this.certificados.length; i++) {
		var cert=this.certificados[i]
		
		var dsCert=cert.getElementsByTagName('NotAfter')[0].firstChild.nodeValue
		
		var yyyy=dsCert.substring(0,4)
		var mm=dsCert.substring(4,6)
		var dd=dsCert.substring(6,8)

		var fechaCad=new Date(parseInt(yyyy,10),parseInt(mm,10)-1,parseInt(dd,10))
		var caducado=fechaCad<sysdate
		dsCert='Cad: '+dd+'-'+mm+'-'+yyyy
		var trozo=cert.getElementsByTagName('Subject')[0].firstChild.nodeValue
		var COMA="C-O_M-A"
		while (-1!=trozo.search(/"[^,]*,[^,]*"/)){
			trozo=trozo.replace(/("[^,]*),([^,]*")/,"$1"+COMA+"$2")
			}
		trozo=trozo.split(',')

		for (var j=0; j<trozo.length; j++) {
			var nomval=trozo[j].replace(COMA,',').split('=')
			dsCert+='/'+nomval[1]
			}
		var opt=creaObj('option')

		if (caducado){
			opt.disabled=true
			opt.text='CADUCADO: ' + dsCert
			}
		else{
			opt.value=cert.getElementsByTagName('Alias')[0].firstChild.nodeValue
			opt.text=dsCert
			}
		opt.title=opt.text
		this.lst.options[this.lst.options.length]=opt
		}
	
	$(this.lst).fadeIn(jquerycssmenu.fadesettings.overduration)
	return this.certificados
}
ComponenteFirmaElectronica.prototype.activaCertificadoSeleccionado=function(){
	this.applet.setCertificadoFirma( this.lst.value )
	//console.log('FIRMA: seleccionamos certificado '+this.lst.value)
}
ComponenteFirmaElectronica.prototype.firmarTexto=function(textoAFirmar){
	return this.firmarTextoOFichero(textoAFirmar,null)
}
ComponenteFirmaElectronica.prototype.firmarUrl=function(digest, url){
	return this.firmarTextoOFichero(digest,url)
}
ComponenteFirmaElectronica.prototype.firmarXML=function(texto, url){
	return this.firmarTextoOFichero(texto, url, true)
}
ComponenteFirmaElectronica.prototype.firmarTextoOFichero=function(textoAFirmar, url, xml){
	this.activaCertificadoSeleccionado()

	var dicParams=this.jsInicializacion // vendrá relleno  {'formatoFirma': ENVELOPED, ...}
	var formatoFirma=dicParams.formatoFirma
	var tipoFirma=dicParams.tipoFirma

	var respuesta
	var tiposFirmaXDS=[ENVELOPED,ENVELOPING,DETACHED]
   	var tiposFirmaPKC7=[ATTACHED, DETACHED]
 	var formatosFirmaXades=[XADES_BES, XADES_T, XADES_C, XADES_X, XADES_X_L, XADES_A]

	if (formatosFirmaXades.contains(formatoFirma)) {
		if (!tiposFirmaXDS.contains(tipoFirma) ) {
			alert('El tipo de firma indicado no es válido para el formato '+formatoFirma+'.\nSe indicó: '+this.jsInicializacion)
			return
			}
		
		var idFirma='FIRMA_'+Math.floor(Math.random()*1000)
			
		if (url)
			respuesta = this.applet.xmlFirmaXDSUrl(url, 'user', 'D0', idFirma, formatoFirma, tipoFirma)
		else if (xml){
			try {
				if (dicParams.rol || dicParams.id_politica || dicParams.ds_politica || dicParams.hash_politica)
					this.applet.setXadesContext(dicParams.rol, dicParams.id_politica, dicParams.ds_politica, dicParams.hash_politica)
				}
			catch (e){
				/*pass*/
				}
			respuesta=this.applet.xmlFirmaXDSXML(textoAFirmar, 'user', 'D0', idFirma, formatoFirma, tipoFirma) 
			}
		else
			respuesta=this.applet.xmlFirmaXDSTexto(textoAFirmar, 'user', 'D0', idFirma, formatoFirma, tipoFirma)
		
		return {idSignature:idFirma, texto:respuesta}
		}
	else if (formatoFirma=='PKCS7'){
		if (!tiposFirmaPKC7.contains(tipoFirma) ) {
			alert('El tipo de firma indicado no es válido para el formato PKCS7.'+vbCrLf+'Se indicó: '+this.jsInicializacion)
			return
			}
		if(url)
			respuesta=this.applet.b64FirmaPKCS7Url(url, tipoFirma)
		else
			respuesta=this.applet.b64FirmaPKCS7Texto(textoAFirmar, tipoFirma)
		}
	else {
		alert('El formato de firma indicado no es válido.\nSe indicó: '+this.jsInicializacion)
		return
		}
	if (respuesta==null)
		throw 'No se ha podido firmar. Verifique la consola de Java o consulte con el personal técnico.'
	return respuesta
	}
ComponenteFirmaElectronica.prototype.firmarPDFServidor=function(urlFichero, asunto, lugar, razon, contacto, urlSubida, numerador, idcookie){
	this.activaCertificadoSeleccionado()

	asunto=asunto || '<No se ha indicado asunto>'
	lugar=lugar || '<No se ha indicado lugar>'
	razon=razon || '<No se ha indicado asunto>'
	contacto=contacto || '<No se ha indicado contacto>'

	//De momento no pintamos la firma en el docu
	var ret= this.applet.firmaPDFUrlAndUpload(urlFichero, asunto,
			lugar, razon, contacto, 
			false,"", 0,
			window.location.href.replace(window.location.search,'').replace('#','')+urlSubida, 
			numerador, null)
	//console.log('FIRMA: pdf firmado ('+ret+') en '+rutaSalida)
	return ret
}
////////////////////////////////////////////////////////////
function creaTextArea(clase, id, texto, maxlength, bloqueado){
	var d=document.createElement('textarea')

	if (clase!=null) d.className=clase
	if (id!=null) d.id=id
	if (texto!=null) d.value=texto

	d.cols=100
	if (maxlength>0) {
		d.rows=4
		d.setAttribute('maxlength', maxlength)
		$(d).maxlength()
		}
	else
		d.rows=10
	return d
}
function min(v1, v2){
	return (v1<v2 ? v1 : v2)
}
////////////////////////////////////////////////////////////
function getFrmActivo(){
	if (listaFrm.length)
		return listaFrm[ listaFrm.length-1 ]
	if(exp.idMdActivo)
		return exp.listaModosDetalle[ exp.idMdActivo.toLowerCase() ]
}
function getBscActivo(){
	var ret=getFrmActivo()
	if (ret && ret instanceof DetalleFlotante)
		return ret.detalle.formularioBSC
		
	if(ret)
		ret=ret.formularioBSC
	if(ret)
		return ret
	if(exp.idMlActivo && exp.idMlActivo.toLowerCase() in exp.listaModosDetalle)
		return exp.listaModosDetalle[ exp.idMlActivo.toLowerCase() ].formularioBSC
	return null
}
////////////////////////////////////////////////////////////
function Frm(id, idxBloqueo){
	this.idx=id

	this.idxBloqueo=idxBloqueo

	this.altoFrm=null
	this.anchoFrm=null
	this.porcentajeAnchoFrm=null
	this.porcentajeAltoFrm=null

	this.altoCabecera=33
	this.altoPie=33

	this.altoPantalla=getAltoPantalla()
	this.anchoPantalla=getAnchoPantalla()

	this.titulo=this.titulo || null
	this.botones=[]
	this.acceptCharset='UTF8'
}
Frm.prototype.preactivate=function(){}
Frm.prototype.creaInput=function (tipo, clase, id, valor, maxlength, valorControl, grupoActivo, etiqueta, nombreInputRadio){
	var d
	if (tipo=='text' || tipo=='password') {
		d=document.createElement('INPUT')
		d.type=tipo
		if (valor) d.value=valor
		}
	else if (tipo=='radio') {
		//http://cf-bill.blogspot.com/2006/03/another-ie-gotcha-dynamiclly-created.html
		nombreInputRadio=nombreInputRadio || 'grupoOpciones'+grupoActivo
		try	{ //el *uto ie
			d = document.createElement('<input name='+nombreInputRadio+'" id="'+id+'" type="radio" '+(valorControl?'CHECKED':'')+'/>')
			}
		catch(err){
			d=document.createElement('INPUT')
			d.name=nombreInputRadio
			d.type=tipo
			d.checked= valorControl
			d.id=id
			}
		}
	else if (tipo=='checkbox'){
		d=document.createElement('INPUT')
		d.type=tipo

		if (valorControl) {
			if (!exp.internetExplorer)
				d.checked= valorControl
			else
				d.defaultChecked = true
			}
		}
	else if (tipo=='file'){
		d=document.createElement('INPUT')
		d.size=50
		d.type=tipo

		}

	if (clase) d.className=clase
	if (id) d.id=id
	if (maxlength && maxlength>0) {
		d.maxLength=maxlength
		d.size=maxlength //firefox6: <form> elements’ text <input> fields no longer support the XUL maxwidth property; this was never intentional, and is in violation of the HTML specification. You should instead use the size attribute to set the maximum width of input fields.
		}
	return d
}
Frm.prototype.creaA=function(className, href, onClick, title, texto){
	var a=creaObj('DIV', className)
	if (texto) {
		a.appendChild(creaT( texto )) //si no, no se ve en ie
		}
	if (onClick)
		a.onclick=onClick

	a.title=title
	a.href=href
	a.tabIndex=-1
	return a
}
////////////////////////////////////////////////////////////
function getAltoPantalla(){
	if(window.innerHeight)
		return window.innerHeight
	if(document.documentElement && document.documentElement.clientHeight)
		return document.documentElement.clientHeight
	return window.screen.availHeight
}
function getAnchoPantalla(){
	if(window.innerWidth)
		return window.innerWidth
	if(document.documentElement && document.documentElement.clientWidth)
		return document.documentElement.clientWidth
	return window.screen.availwidth
}
Frm.prototype.calculaAnchoyAlto=function(){
	//rellena this.alto, this.altoPantalla, this.ancho, this.anchoPantalla, this.altoCuerpo
	var alto, altoCuerpo

	var maxAlto=0.90*this.altoPantalla

	if (this.porcentajeAltoFrm!=null)
		this.altoFrm=(this.porcentajeAltoFrm/100)*this.altoPantalla
	alto=Math.min(this.altoFrm || 10000, maxAlto)

	this.alto=alto
	this.altoCuerpo=alto-this.altoCabecera-this.altoPie

	///////////////////
	var ancho
	this.anchoPantalla=this.anchoPantalla || getAnchoPantalla()
	if (this.porcentajeAnchoFrm != null)
		ancho=(this.porcentajeAnchoFrm/100)*this.anchoPantalla
	else if (this.anchoFrm!=null)
		ancho=this.anchoFrm

	this.ancho=ancho
}
Frm.prototype.creaSeparadorBotonera=function(claseAdicional){
	return creaObjProp('span', {className:'botonera gris separador '+(claseAdicional?claseAdicional:''), texto:espacioDuro})
	}
Frm.prototype.creaBarraHerramientas=function(hijos){	
	hijos=jQuery.map(hijos, function(btn){return envuelve(btn)})
		
	var ul=creaObjProp('ul', {hijos:hijos})
	var botonera=creaObjProp('div', {className:'nav botonera', hijo:ul})
	
	jquerycssmenu.buildmenu($(botonera))
	
	return botonera
	}
Frm.prototype.espacioSobrante=0
Frm.prototype.toDOM=function() {
	this.calculaAnchoyAlto()

	//var tope=this.alto? (this.altoPantalla-this.alto)/2.8 : ''
	var izq=this.ancho? (this.anchoPantalla-this.ancho)/2 : ''
	
	var ventana=creaObjProp('div', {className:'ventana', 'id':this.idx, 'style.left':izq, 'style.width':this.ancho})	
	this.ventana=ventana

	ventana.appendChild( this.pintaCabecera() )
	var cuerpo=this.pintaCuerpo(this.altoCuerpo)
	ventana.appendChild( cuerpo )
	ventana.appendChild( this.pintaPie() )

	var ventana0=control(this.idxBloqueo)
	if (ventana0==null) //algo raro pasa en ie
		ventana0=ponBloqueo(this.idxBloqueo)
	ventana0.appendChild(ventana)
	this.preactivate()
	
	var tope=(this.altoPantalla-ventana.clientHeight)/2
	if(tope<0 ){//se sale de la ventana
		tope=0
		cuerpo.style.height=(this.altoPantalla-this.altoPie-this.altoCabecera-this.espacioSobrante)+'px'
		}
		
	ventana.style.top=Math.round(tope/2)+10+'px'

	quitaCargandoBloqueo(this.idxBloqueo)
	this.activate()
	if (exp && exp.internetExplorer6) {
		setTimeout(function (){
			ventana.style.zoom=1
			},500)
		}
}
Frm.prototype.pintaCabecera= function (){
	this.icono=creaObjProp('div', {className:'icono'})
	this.pTitulo=creaObjProp('p', {texto:this.titulo || this.textoTitulo} )
		
	var cabecerafrm=creaObjProp('div', {className:'header', onmousedown:this.fnOnMouseDown( this.idx ), hijos: [this.icono, this.pTitulo]})
	return cabecerafrm
}
Frm.prototype.fnOnMouseDown=function(idx){
	return function(event){dragFRMPress(event, control(idx).firstChild);return false;}
}
Frm.prototype.pintaPie=function(){
	var piefrm=creaObjProp('div', {className:'footer'})

	if (this.botonesIzq){
		var pieIzq=creaObjProp('span', {className:'pieIzq'})
		piefrm.appendChild(pieIzq)

		for (var i=0; i<this.botonesIzq.length; i++) {
			var btn=this.botonesIzq[i]
			pieIzq.appendChild( btn)
			}
		}

	var pieDerecha=creaObjProp('span', {className:'pieDer'})
	piefrm.appendChild(pieDerecha)

	for (var i=0; i<this.botones.length; i++) {
		var btn=this.botones[i]
		pieDerecha.appendChild( btn)
		}
	
	if (this.botones.length)
		ponFoco(this.botones[0])

	return piefrm
}
Frm.prototype.activate=function(){
	/*para realizar las tareas de poner foco, cargar unas cuantas filas en los buscadores, etc*/
}
Frm.prototype.creaObj=function(tipo, clase, hijo, id, title, onclick, onmouseover){
	return xCreaObj(tipo, clase, hijo, id, title, onclick, onmouseover)
}
Frm.prototype.ponReadOnly=function(control){
	if (control.tagName.toLowerCase()=='select'||
			(control.tagName.toLowerCase()=='input' && 
					(control.type.toLowerCase()=='checkbox'||
				     control.type.toLowerCase()=='radio')	
			) 
	)
		control.disabled=true
	else{
		control.readOnly='readonly'
		if (exp && exp.internetExplorer6)
			control.style.backgroundColor="#E7E7E7"
		}
	ponEstilo(control, 'readonly')
	}
function xCreaObj(tipo, clase, hijo, id, title, onclick, onmouseover){
	var d=document.createElement(tipo)
	if (clase) d.className=clase
	if (id) d.id=id
	if (title) d.title=title
	if (hijo) d.appendChild(hijo)
	if (onclick) d.onclick=onclick
	if (onmouseover) d.onmouseover=onmouseover
	return d
	}
Frm.prototype.ocultaFormulario=function(quitarBloqueo){
	if (quitarBloqueo==null)
		quitarBloqueo=true

	var ventana
	if (quitarBloqueo)
		ventana=control( this.idxBloqueo )
	else
		ventana=control( this.idx )
	
	if (ventana!=null)
		ventana.parentNode.removeChild(ventana)
	}
Frm.prototype.notificaCancelarTram=function(){
	// notificamos al servidor que se ha cancelado el trámite en curso
	var param={'aplicacion': aplicacion, 'accion':'cerrarFormulario'}
	loadJSONDoc('json', param, function(req){ 
					retornoTramite( xeval(req) ) 
					})
	}
Frm.prototype.cancelar=function(){
    var self=this
	if (this.tramite==null || this.tramite.toLowerCase()!='autoexec')
		return function(){ self.cancela() }
	else
		return function(){ window.location="salir?aplicacion="+aplicacion }
	}

Frm.prototype.ponFocoTXT=function(txt){
	if (!exp || !exp.dispositivoMovil) //en android/ios evitamos que salga el teclado en pantalla de primeras
		ponFoco(txt)
	}
////////////////////////////////////////////////////////////
var tiposValidos=['lvwOption', 'bsc', 'lvwBoolean', 'lvwMemo', 'lvw', 'lvwFecha', 'lvwNumero','lvwCurrency', 'etiqueta', 'lvwConsejo', 'lvwFile', 'lvwLista']
function Formulario(datos, idxBloqueo, notificarCierre){
	if (datos==null){
		Frm.call(this, null, null)
		}
	else{
		if (idxBloqueo==null) idxBloqueo=datos.idxBloqueo
		Frm.call(this, 'tramite'+datos.tramite +'-'+datos.camino, idxBloqueo)
		listaFrm.push( this )

		this.refrescarAlCancelar=datos.refrescarAlCancelar
		this.notificarCierre=notificarCierre
		this.formularioBSC=null //metemos el activo
		this.frm=datos

		this.camino=datos.camino
		this.tramite=datos.tramite

		this.altoFrm=datos.alto
		this.porcentajeAnchoFrm=65
		
		if (datos.acceptCharset)
			this.acceptCharset=datos.acceptCharset

		this.titulo=this.titulo || datos.titulo|| Frm.prototype.textoTitulo
		this.controles=datos.controles
                
                this.frm.bloquesActivos=[]
                this.frm.bloques=datos.bloques

		this.onAceptar=null //para los trámites un poco mágicos, tipo cambiar contraseña, le indicamos aquí la func a la que llamar al pulsar aceptar

		this.numTramites=datos.numTramites //los que quedan por detrás
		this.botones=[
					creaObjProp('button', {className:'cmdgt aceptar',  texto:Frm.prototype.textoAceptar, onclick:aceptaFormulario} ) ,
					creaObjProp('button', {className:'cmdgt cancelar', texto:Frm.prototype.textoCancelar, onclick:cancelaFormulario})
					]
		
		if (this.frm.etiquetasSalto){
			this.botonesIzq=[]
			var listaBotones=this.frm.etiquetasSalto.split('|')
			for (var i=0; i<listaBotones.length;i++){
				var boton=listaBotones[i]
				var tboton=boton.split('\\')

				var etiqueta=tboton[0], literal=tboton[0]
				if (tboton.length>1)
					literal=tboton[1]
				this.botonesIzq.push( creaObjProp('button', {className:'cmdgt saltar '+etiqueta, texto:literal, onclick:fnGuardaYSalta(etiqueta)} ) )
				}
			}
		}
	}
Formulario.prototype= new Frm
Formulario.prototype.espacioSobrante=50
Formulario.prototype.cambiaNodoActivo=function(){}
Formulario.prototype.textoCancelarLote='¿Desea ejecutar el resto del lote? Queda(n) %s trámite(s) por ejecutar.\nPulse "Aceptar" para continuar o "Cancelar" para detener la ejecución del resto del lote'
Formulario.prototype.cancela=function(){
	ocultaCalendario()

	if ( !this.numTramites ) {
		this.cierraFormulario(true)

		if (this.notificarCierre) this.notificaCancelarTram()
		if (this.refrescarAlCancelar) exp.refrescaTodo()
		}
	else {
		var resp=confirm(mensaje(Formulario.prototype.textoCancelarLote, [this.numTramites]))
		if (resp==false) {
			this.cierraFormulario()
			//y refrescamos
			exp.refrescaTodo()
			}
		else
			this.saltarEsteTramite()
		}
	if (depuradorActivo) depuradorActivo.cancela()
}
Formulario.prototype.cierraFormulario=function(quitarBloqueo){
	this.ocultaFormulario(quitarBloqueo)
	var frmBorrar=listaFrm.pop()
	if (this instanceof FormularioD) {
		delete exp.listaFormulariosModosDetalle[frmBorrar.detalle.id]
		}
	if (depuradorActivo) depuradorActivo.activaBotones(true)
}
Formulario.prototype.primeraCasilla=function(ctl){
	if (ctl.bloqueado)
		return null
	if (ctl.tipo=='bsc') {
		var cc
		if (ctl.colsBloqueadas<ctl.controles.length)
			cc=ctl.controles[ctl.colsBloqueadas]
		else
			cc=ctl.controles[ctl.controles.length-1]

		return cc.id
		}
	else
		return ctl.id
}
Formulario.prototype._pintaContenedorControles=function(cuerpofrm, altoCuerpo){
	var tabla=creaObjProp('table', {className:'formulario'})

		var tbody=creaObj('tbody')
		tabla.appendChild( tbody )

		var form=creaObjProp('form', {hijo:tabla, 
							acceptCharset:this.acceptCharset, 
							encoding:'application/x-www-form-urlencoded', 
							onsubmit:function(){return false} })
		cuerpofrm.appendChild( form )
	return tbody
}
Formulario.prototype.toDOM= function (){
	Frm.prototype.toDOM.call(this)
	ponEstilo(this.ventana, 'formulario')
	ponEstilo(this.ventana, 'formularioAutomatico')
	if (this.onAceptar==null)//se trata de un formulario convencional de tramitación, definido por el programador en TRA_Acciones
		ponEstilo(this.ventana, 'tramitacion')
	
	this.spanGloboAyuda=creaObjProp('span', {className:'globoAyuda formulario'})
	this.icono.parentNode.appendChild( this.spanGloboAyuda )
}
Formulario.prototype.pintaCuerpo= function (altoCuerpo){
	var cuerpofrm=creaObj('div', 'cuerpo')
	var tbody=this._pintaContenedorControles(cuerpofrm, altoCuerpo)
	
	var esPrimeraOpcion=true
	var grupoActivo=0
	var yaHayOptionVerdadero

	console.debug('Pintamos frm y lanzamos las búsquedas de los bsc que toquen')
	this.pintaFilaVacia(tbody) //cuando hay una etiqueta en primer lugar, se te rompen los anchos de las columnas
	var filaActiva
	for (var i=0; i<this.controles.length; i++) {
		var ctl=this.controles[i]
		if (ctl==null) continue
		if (tiposValidos.indexOf(ctl.tipo)==-1)
			alert('Ha llegado un tipo de control no válido: "'+ctl.tipo+'"')
		if (ctl.tipo=='lvwOption'){
			var opt
			if (esPrimeraOpcion) {//ponemos la fila
				grupoActivo++
				yaHayOptionVerdadero=false
				opt=this.pintaControl(ctl,null,null,null,null, grupoActivo)
				filaActiva = this.pintaFila (tbody, '', ctl.obligatorio, opt, null, ctl.id, ctl)
				}
			else{
				if (yaHayOptionVerdadero)
					ctl.valor=false
				opt=this.pintaControl(ctl, null,null,null,null, grupoActivo)
				filaActiva.lastChild.appendChild(opt)
				}
			opt.firstChild.name=opt.firstChild.name||grupoActivo
			yaHayOptionVerdadero|=ctl.valor
			ctl.grupo=grupoActivo
			}
		else if (ctl.tipo=='lvwLista')
			this.pintaFila (tbody, ctl.ds, ctl.obligatorio, this.pintaControl(ctl), ctl.id, ctl.id, ctl)
		else if (ctl.tipo=='bsc')
			this.pintaFila (tbody, ctl.ds, ctl.obligatorio, this.pintaControl(ctl), this.primeraCasilla(ctl), ctl.id, ctl)
		else if (ctl.tipo=='etiqueta')
			this.pintaFila (tbody, ctl.ds, null, null, null, null, ctl)
		else if (ctl.tipo=='lvwConsejo')
			this.pintaFila(tbody, null, null, this.pintaConsejo(ctl), null, null, ctl)
		else if (ctl.tipo=='lvwFecha') {
			var input=this.pintaControl(ctl )
			this.pintaFila (tbody, ctl.ds, ctl.obligatorio, input, ctl.id, ctl.id, ctl)
			}
		else if (ctl.tipo=='lvwFile')
			this.pintaFila (tbody, ctl.ds, ctl.obligatorio, this.pintaControl(ctl), ctl.id, ctl.id, ctl)
		else // ( ctl.tipo== 'lvwNumero' || ctl.tipo=='lvw' ||  || ctl.tipo=='lvwBoolean')
			this.pintaFila (tbody, ctl.ds, ctl.obligatorio, this.pintaControl(ctl), ctl.id, ctl.id, ctl)

		esPrimeraOpcion=(ctl.tipo!='lvwOption')
		}
	return cuerpofrm
}
Formulario.prototype.activate=function(){
	var primerInput=$(this.ventana).find(':input:not(.readonly), ')[0]
	if (primerInput)
		this.ponFocoTXT( primerInput )
	if (depuradorActivo) depuradorActivo.activaBotones(false)
	
	jwerty.key('escape', function(){cancelaFormulario()}, null, 'div.ventana#'+this.idx+' input')
	jwerty.key('enter',   function(){aceptaFormulario()},  null,  'div.ventana#'+this.idx+' input')
        
        if (this.frm.bloques) this.actualizaBloques()
}
Formulario.prototype._pintaBSC = function ( ctl ){
	var div=creaObjProp('table', {className:'campoCompuesto bsc', id:ctl.id})
	if(ctl.colsBloqueadas)
		ponEstilo(div, 'colsBloqueadas')
	ponEstilo(div, ctl.bloqueado? 'Xno':'Xsi')
	
	var self=this
	var fnAbreBuscador=function(){abreVentanaBuscador(ctl, null, self); return false}
	var fnBuscar=function(event){b.buscarPorCodigo(false)}
	var fnEsperarYBuscar=function(event){b.esperarYBuscarPorCodigo(false)}
		
	var td1=creaObjProp('td', {className:'boton'}); var td2=creaObjProp('td', {className:'campos'}); var td3=creaObjProp('td', {className:'ds'})
	var tr=creaObjProp('tr', {hijo:td1}); div.appendChild(tr)
	
	//1º el botón de buscar
	var boton=creaObjProp('a', {className:'cmd', tabIndex:-1, href:'#', onclick:(ctl.bloqueado?function(){}:fnAbreBuscador), title:ctl.bloqueado?'':'[Click aquí o F2 para buscar]', text:espacioDuro4})
	td1.appendChild(boton)
	
	//2º las casillas
	ctl.txts=[]
	for (var i=0; i<ctl.controles.length; i++) {
		var cc=ctl.controles[i]
		cc.ds=cc.ds || ''
		ctl['valor'+i]=cc.valor

		var txt=this._pintaTXT(	cc,
							fnBuscar, 			//onblur
							null,
							fnEsperarYBuscar, //onkeypress
							true) //en los buscadores no pintamos Textareas, sólo inputs
		
		jwerty.key('f2', fnAbreBuscador, null, txt)
								
		if (ctl.obligatorio)
			ponEstilo(txt, 'obligatorio')
			
		if (ctl.bloqueado)	{
			this.ponReadOnly(txt)
			txt.tabIndex=-1
			}
		
		var xclase=''
		if (i<ctl.colsBloqueadas ){
			this.ponReadOnly(txt)
			ponEstilo(txt, 'colBloqueada')
			xclase='colBloqueada'
			txt.tabIndex=-1
			}
			
		txt.setAttribute('autocomplete','off')
		
		var td2=creaObjProp('td', {className:'campos '+xclase, hijo:txt } )
		tr.appendChild(td2)
		ctl.txts.push(txt)
		}
	//3º la descripción
	ctl.des=creaObjProp('span', {className:'des_bsc', id:'des_'+ctl.id})
	td3.appendChild( ctl.des )
	tr.appendChild(td3)
		
	var b=new Buscador(ctl)
	buscadores[ctl.idOriginal]=b
	ctl.buscador=b
	
	ctl.buscador.lanzaPrimeraBusqueda(false)
	
	return div
}
function idOriginal(ctl){
	if (typeof(ctl.idOriginal)=='function')
		return ctl.idOriginal()
	return ctl.idOriginal
}
Formulario.prototype.pintaConsejo=function(ctl){
	var divInterno=creaObjProp('div', {texto:ctl.ds})
	var div=creaObjProp('div', {className : 'consejo '+ctl.tipoConsejo, hijo:divInterno})
	return div
}
Formulario.prototype.pintaControl = function ( ctl, onblur, onkeyup, onkeypress, esBsc, grupoActivo){
	if (!ctl.idOriginal){
		ctl.idOriginal=ctl.id
		ctl.id=ctl.id.replace('*', '').replace('.', '')
		}
		
	if (ctl.tipo=='bsc')
		return this._pintaBSC(ctl)
	else
		return this._pintaTXT(ctl, onblur, onkeyup, onkeypress, esBsc, grupoActivo)
	}
function getSelectionStart(input){
	if (input.setSelectionRange)// Mozilla
		return input.selectionStart
	else if (document.selection){// IE
		var textRange = document.selection.createRange().duplicate()
		var pos

		if (textRange.text.length > 0)// selection is not collapsed
			pos = input.value.indexOf(textRange.text)
		else // selection is collapsed
			pos = input.value.length // How do I compute this?

		return pos
		}
	else 
		return 0
	}
function getSelectionEnd(input){
	if (input.setSelectionRange) // Mozilla
	return input.selectionEnd
	else if (document.selection) {// IE
		var selectedRange = document.selection.createRange().duplicate()
		selectedRange.moveStart("character", -input.value.length)
		return selectedRange.text.length
		}
	else
		return 0
	}
Formulario.prototype._pintaTXT = function ( ctl, onblur, onkeyup, onkeypress, esBsc, grupoActivo){	
	var clase=this.validacion(ctl)
	if (clase){
		var obj=new clase()
		if (obj.maxlength && obj.maxlength>0)
			ctl.maxlength=obj.maxlength
		}
	
	var onkeydown

	var claseObligatorio=ctl.obligatorio?' obligatorio':''
	if (ctl.tipo=='lvwBoolean'){
		var ret=creaObj('LABEL','checkbox')
		var chk=this.creaInput('checkbox', null, ctl.id, ctl.valor, null, ctl.valor)
		
		var fnCambia=function(){ponEstilo(ret, chk.checked?'valor_true':'valor_false')}
		chk.onchange=fnCambia
		
		if (exp.internetExplorer){
			chk.onclick=fnCambia
			ret.onclick=fnCambia
			}
			
		if (ctl.bloqueado) this.ponReadOnly(chk)
		
		ret.appendChild(chk)
		ret.appendChild(creaObjProp('span',{className:'valor_true', texto:this.textoSi}))
		ret.appendChild(creaObjProp('span',{className:'valor_false', texto:this.textoNo}))
		
		chk.onchange()
                
                if (this.frm.bloques!=null){
                        var self=this
                        $(chk).change(function(){self.actualizaBloques()})
                        }
		
		return ret
		}
	if (ctl.tipo=='lvwLista'){
		var lista=creaObj('select', 'lvw', ctl.id)
		var xvalor=ctl.valor?(ctl.valor+'').toLowerCase():''
		
		if (ctl.lista instanceof Array){
			var templista=ctl.lista
			ctl.lista={}
			for (var i=0; i<templista.length; i++)
				ctl.lista[ templista[i] ]=templista[i]
			}
                //~ if(!ctl.obligatorio)
                        //~ ctl.lista[ '' ]='--' ¿qué te hace suponer que no he metido un valor vacío, o que el valor vacío no tiene un código asociado distinto?
		//vamos a ordenar por la DESCRIPCIÓN que siempre será mejor que el orden que javascript le ponga (no es posible mostrarlo tal cual lo metió el usuario)
		var conOrden=[]
		for (var k in ctl.lista){ //lista={'texto':valor }
			if (typeof ctl.lista[k]=='function')
				continue
			conOrden.push([k, ctl.lista[k]])
			}
		conOrden=conOrden.sort(function(a,b){return a[1]>b[1]})
		for (var i=0;i<conOrden.length; i++){ //lista={'texto':valor }
			var k=conOrden[i][0]; var v=conOrden[i][1]
			var opt=creaObjProp('option', {value:k, text:v})
			lista.options[lista.options.length]=opt

			if ( (k+'').toLowerCase() == xvalor) xvalor=k
			}
		lista.value=xvalor
		if (ctl.bloqueado) this.ponReadOnly(lista)
                
                if (this.frm.bloques!=null){
                        var self=this
                        $(lista).change(function(){self.actualizaBloques()})
                        }
		return lista
		}
	if (ctl.tipo=='lvwOption') {
		var div=creaObj('div', 'radio')
		var lbl=creaObj('label', 'des_bsc')//; lbl.htmlFor=ctl.id
		var etiqueta=ctl.ds
	
		var resto=null
		if(etiqueta.contains('$$$')){
			var temp=etiqueta.split('$$$')
			etiqueta=temp[0].trim()
			resto=temp[1].trim()
			if(resto.startsWith('#')) //color
				lbl.style.color=resto
			else
				lbl.className= lbl.className+' '+resto
			}
			
		var radio =this.creaInput('radio', null, ctl.id, ctl.valor, null, ctl.valor, grupoActivo, etiqueta, resto)
		if (ctl.bloqueado) this.ponReadOnly(radio)
		
		lbl.appendChild( radio )
		
		lbl.appendChild( creaT(etiqueta) )
		div.appendChild( lbl )
		var globo=this.pintaGloboAyuda(ctl)
		if (globo)
			div.appendChild( globo )
                
                if (this.frm.bloques!=null){
                        var self=this
                        $(radio).change(function(){self.actualizaBloques()})
                        }
		
		return div
		}
	if (!ctl.ds.toLowerCase().contains('contraseña') && !esBsc && (ctl.tipo=='lvwMemo' || (ctl.tipo=='lvw' && Math.abs(ctl.maxlength)>=200 ))) {
		var ta=creaTextArea(ctl.tipo+claseObligatorio, ctl.id, ctl.valor, ctl.maxlength, ctl.bloqueado)
		if (ctl.bloqueado) this.ponReadOnly(ta)

		return ta
		}
	
	var input, maxlength, longControl
	if (ctl.tipo=='lvwFecha'){
		maxlength=19
		longControl=19
		}
	else if (ctl.tipo=='lvwFile'){
		longControl=50
		}
	else  {
		maxlength=ctl.maxlength
		longControl=min(ctl.maxlength, 100)
		if (longControl==0)
			longControl=4
		}

	var tipoInput='text'
	if (ctl.ds.toLowerCase().contains('contraseña'))
		tipoInput='password'
	else if (ctl.tipo=='lvwFile')
		tipoInput='file'

	input=this.creaInput(tipoInput, ctl.tipo+claseObligatorio, ctl.id, ctl.valor, maxlength)

	input.display='inline'
	input.size= Math.abs(longControl) //si viene como maxlength= -30, se usa el 30 para indicar el tamaño del control pero SIN MAXLENGTH
	if (ctl.bloqueado) this.ponReadOnly(input)

	if (onblur)
		input.onblur=onblur
	else if (ctl.tipo=='lvwFecha')
		input.onblur=fnFormatearFecha()

	if (onkeyup)  
		input.onkeyup=onkeyup
	if (onkeypress)  
		input.onkeypress=onkeypress
		
	if (ctl.tipo=='lvwNumero'||ctl.tipo=='lvwCurrency') 
		jwerty.key('.', fnSustituyePuntoPorComa(), null, input)
		
	if (ctl.tipo=='lvwFecha'){
		var cont=creaObjProp('table', {className:'campoCompuesto calendario'})
		
		var td1=creaObjProp('td', {className:'boton'}); var td2=creaObjProp('td', {className:'campos'}); var td3=creaObjProp('td', {className:'ds'})
		cont.appendChild(creaObjProp('tr', {hijos:[td1, td2, td3]}))
		
		var boton=creaObjProp('a', {className:'cmd', tabIndex:-1, href:'#', onclick:ctl.bloqueado?function(){}:fnOpenCalendar(ctl.id), text:espacioDuro4})
		td1.appendChild(boton)
	
		td2.appendChild(input)
		
		return cont
		}
	else if (ctl.tipo=='lvwFile'){
		var div=creaObj('div', 'file')
		
		if (!ctl.obligatorio && !ctl.bloqueado) //añadimos un botón para 'resetear' el campo
			div.appendChild( this.creaA('cmd_file', '#', fnBorraYCreaFile(ctl, ctl.id), '[Click aquí para vaciar el campo]', espacioDuro4) )
		
		div.appendChild( input )		
		if (ctl.multiple){
			var btnSubirOtro=creaObjProp('div', {className:'cmd_subirOtro', texto:'Incluir otro archivo', onclick:fnCreaFileMultiple(ctl, div)})
			div.appendChild( btnSubirOtro )
			}
		return div
		}
	else
		return input
}
Formulario.prototype.actualizaBloques=function(){
        if (this.frm.bloques==null || this.frm.bloques.length==0)
                return
        var param=this.recogeValoresFRM()
        
        param['aplicacion']=exp.aplicacion
        param['accion']='evalFRM'
        
        var self=this
        loadJSONDocPost('json', param, function(resp){self._actualizaBloques(resp)})
        }
Formulario.prototype._actualizaBloques=function(respuesta){
	this.frm.bloquesActivos=[]
        for (var ib in this.frm.bloques){
                var b=this.frm.bloques[ib]
                if (typeof b == 'function') continue
                var filas=$('tr._bloque.'+b.nombre.replace(' ', '.'))
                
                if (respuesta['expresión '+b.nombre]==true){
                        this.frm.bloquesActivos.push(b.nombre)
                        filas.css({display:'table-row'})
                        }
                else
                        filas.css({display:'none'})
                }
        }
Formulario.prototype.evaluaCond=function(nombre, condicion){
        return $( control('@td') ).val()==nombre
        }
function fnFormatearFecha(){
	return function(event){formatearFecha(event)}
}
Formulario.prototype.pintaGloboAyuda = function (ctl){
	var idcasilla, etiqueta, dsExtendida, globoAyuda

	idcasilla=ctl.id
	dsExtendida=ctl.dsExtendida
	globoAyuda=ctl.globoAyuda

	if (dsExtendida){
		return creaObj('span', 'textoAyuda', null, dsExtendida)
		}
	else if (globoAyuda){
		etiqueta=ctl.ds.split('$$$')[0]
		var sp=creaObjProp('span', {className:'globoAyuda', 'texto':espacioDuro4})
		ctl.spanGloboAyuda=sp
		
		var self=this
		$(sp).click(
			function(){
				self.montaGlobo(ctl, null, 'izq', ctl.ds, ctl.globoAyuda)
				}
			)
			
		return sp
		}
}
Formulario.prototype.pintaFila = function (tabla, etiqueta, obligatorio, casilla, idCasilla, idCtl, ctl) {
	var tr=creaObj('tr')
        if (ctl && ctl.bloqueCodigo!=null){
                ponEstilo(tr, '_bloque'); ponEstilo(tr, ctl.bloqueCodigo)
                }
	var color=null
	if  (etiqueta && etiqueta.contains('$$$') ) {
		var temp=etiqueta.split('$$$')
		etiqueta=temp[0].trim()
		var _temp1=temp[1].trim()
		if(_temp1.startsWith('.'))
			tr.className=_temp1.substring(1)
		else
			color=colorTinta( _temp1 )
		}

	
	if (casilla==null) {
		var td=creaObj('td', 'separador', null, etiqueta)
		td.colSpan=3
		if (color!=null) td.style.color=color
		tr.appendChild (td)
		}
	else if (ctl.tipo=='lvwConsejo') {
		var td=creaObjProp('td', {colSpan:3, 'hijo':casilla})
		tr.appendChild (td)
		}
	else {
		var td1=creaObj('td', obligatorio ? 'obligatorio' : 'noObligatorio', null, etiqueta, null, idCasilla)
		if (color!=null) {
			try {td1.style.color=color}
			catch (e){}
			}
		
		var td2=creaObj('td', 'casilla')
		td2.appendChild( casilla )
		if (ctl.tipo!='lvwOption'){
			var globo=this.pintaGloboAyuda(ctl)
			if (globo)
				td2.appendChild(globo)	
			}
		tr.appendChild (td1)

		var tdError=creaObj('td', 'infoError', 'error'+idCtl)
		tr.appendChild (  tdError )

		tr.appendChild (td2)
		}
		

	tabla.appendChild (tr)
	return tr
}
Formulario.prototype.pintaFilaVacia=function(tbody) {
	var tr=creaObj('tr')
	tr.appendChild( creaObj('td', 'noObligatorio' ) )
	tr.appendChild( creaObj('td', 'infoError') )
	tr.appendChild( creaObj('td', 'casilla') )
	tbody.appendChild(tr)
}
Formulario.prototype.valida=function(){
// datos obligatorios , tipos correctos (fechas y números)
	var retorno=true
	var esPrimeraOpcion=true
	var cambiarFoco=true
	for (var i=0;i< this.controles.length;i++){
		var ctl=this.controles[i]
		if (ctl==null)
			continue
                else if (ctl.bloqueCodigo && !(this.frm.bloquesActivos.containsIgnoreCase(ctl.bloqueCodigo)))
                        continue
		else if (ctl.bloqueado){
			esPrimeraOpcion=true
			continue
			}
                        
		if (ctl.tipo=='lvwOption'){
			if (esPrimeraOpcion){
				if (!this.validaEstaRelleno(ctl)){
					this.marcaCampoErroneo(ctl, "Debe seleccionar alguna opción", cambiarFoco)
					retorno=false
					}
				else
					this.marcaCampoCorrecto(ctl)
				}
			esPrimeraOpcion=false
			}
		else {
			esPrimeraOpcion=true
			if (ctl.tipo=='bsc'){
				if (!this.validaEstaRelleno(ctl)) {
					this.marcaCampoErroneo(ctl, "El campo debe rellenarse con un valor válido", cambiarFoco)
					retorno=false
					}
				else if (!this.validaDatoCorrecto(ctl)) {
					this.marcaCampoErroneo(ctl, "El campo debe rellenarse con un valor válido", cambiarFoco)
					retorno=false
					}
				else
					this.marcaCampoCorrecto(ctl)
				}
			else if (ctl.tipo=='lvwBoolean'){
				/*pass*/}
			else if ( ['lvwFile', 'lvwMemo','lvw', 'lvwNumero','lvwCurrency','lvwFecha'].indexOf(ctl.tipo)>-1 ){
				var input=control(ctl.id)
				var estaRelleno=input.value!=''
				
				if (!this.validaEstaRelleno(ctl)) {
					this.marcaCampoErroneo(ctl, "El campo no puede estar vacío", cambiarFoco)
					retorno=false
					}
				else if ( estaRelleno && this.validacion(ctl)!=null ){
					var clase=this.validacion(ctl)
					var obj=new clase()
					var ctlHTML=control(ctl.id)
					
					if(ctlHTML.onblur){
						var evento={}
						evento.target=ctlHTML
						ctlHTML.onblur(evento)
						}
					var dato=ctlHTML.value
					if (obj.esValido(dato, ctlHTML) ) {
						this.marcaCampoCorrecto(ctl) //TODO Esto puede ser redundante
						}
					else {
						this.marcaCampoErroneo(ctl, obj.msgError(dato), cambiarFoco) //TODO Esto puede ser redundante
						retorno=false
						}
					}
				else if (ctl.maxlength<=0){
					//esto es un caso especial: le meto el maxlength negativo cuando quiero un control (estéticamente) de ese tamaño pero sin validación
					}
				else if (input.value.length>ctl.maxlength){
					this.marcaCampoErroneo(ctl, 'Se ha excedido la longitud máxima permitida para este campo', cambiarFoco)
					retorno=false
					}
				else
					this.marcaCampoCorrecto(ctl)
				}
			}
		if (retorno==false)
			cambiarFoco=false
	}
	return retorno
}
Formulario.prototype.validaDatoCorrecto=function(ctl){
	if (ctl.tipo=='bsc') { //si hay alguna casilla no bloqueada que esté rellena...
		if (!ctl.buscador.yaInicializado)
			ctl.buscador.miraSihayCambios(false, true)
		
		if (!ctl.buscador.nulo)
			return ctl.buscador.valido
		else
			return !ctl.obligatorio
		}
	return true
}
Formulario.prototype.validaEstaRelleno=function(ctl, forzar){
	if (forzar || (ctl.obligatorio && !ctl.bloqueado)){
		if ( ['lvwFile', 'lvwMemo','lvw', 'lvwFecha','lvwNumero','lvwCurrency'].indexOf(ctl.tipo)>-1 ){
			var input=control(ctl.id)
			return input.value != ""
			}
		else if (ctl.tipo=='lvwOption'){
			var lista=this.listaOPT(ctl.grupo)
			for (var i=0;i< lista.length;i++){
				var id=lista[i].id
				if (control(id).checked)
					return true
				}
			return false
			}
		else if (ctl.tipo=='bsc') 
			return !ctl.buscador.nulo
		}
	return true
}
Formulario.prototype.validacion=function(ctl){
	ctl.validacion=ctl.validacion || ''
	var tipo=ctl.tipo+"."+ctl.validacion.toLowerCase() // lvwNumero.positivo
	return validacionesEspeciales[tipo]
}
Formulario.prototype.saltarEsteTramite=function(){
	var idxBloqueo=ponNuevoBloqueo(true, 'procesando...', this.idxBloqueo)
	var param={'aplicacion': exp.aplicacion, 'accion':'saltaTramite'}
	this.cierraFormulario(false)
	loadJSONDocPost('json', param, function(req){
		quitaBloqueo(idxBloqueo)
		retornoTramite( xeval(req) )
		}
	)
}
Formulario.prototype._enviaArchivos=function(){
	var frmaim=control('ifr_subirArchivos')
	borraHijos(frmaim)

	var hayArchivos=false
	var j=0
	for (var i=0; i<this.controles.length; i++) {
		var ctl=this.controles[i]
		if (ctl.tipo == 'lvwFile') {
			var ctlFile=control(ctl.id)
			if (ctlFile.value!=''){
				if (!hayArchivos) this.idxBloqueoSubir=ponNuevoBloqueo(true, 'subiendo')
				
				var frmOrigen=ctlFile.parentNode
				
				ctlFile.name="archivo"+j
				ctlFile.id=ctlFile.name
				frmaim.appendChild(ctlFile)
				
				frmOrigen.appendChild(creaObjProp('INPUT', {type:'file', size:50, id:ctl.id, className:'lvwFile oculto'}))
				frmOrigen.appendChild(creaObjProp('span', {className:'frm enviandoArchivo', texto:'enviando archivo...'}))
				
				j++
				hayArchivos=true
				}
			if (ctl.multiple){
				ctlFile=control(ctl.id+'_'+j)
				var z=0
				while (ctlFile!=null){
					if (ctlFile.value!=''){
						if (!hayArchivos) this.idxBloqueoSubir=ponNuevoBloqueo(true, 'Subiendo')
						
						var frmOrigen=ctlFile.parentNode
						
						ctlFile.name="archivo"+(z++)+ '_' + j
						ctlFile.id=ctlFile.name
						frmaim.appendChild(ctlFile)
						
						frmOrigen.appendChild(creaObjProp('INPUT', {type:'file', size:50, id:ctl.id+'_'+j,	 className:'lvwFile oculto'}))
						frmOrigen.appendChild(creaObjProp('span', {className:'frm enviandoArchivo'}))
						hayArchivos=true
						}
						
					j++
					ctlFile=control(ctl.id)
					}
				}
			}
		}
	if (hayArchivos){
		var self=this
		AIM.submit(frmaim, {	onStart:function(){self.inicioSubirFicheros()}, 
						onComplete: function(html){self.finSubirFicheros(html)},
						onError: function(){self.errorSubirFicheros()} } )
		frmaim.submit()
		return true
		}
	else  {
		quitaBloqueo(this.idxBloqueoSubir)
		return false
		}
}
Formulario.prototype.inicioSubirFicheros=function(){
	var self=this
	self.progresoSubidaArchivos=setTimeout(function(){self.consultaProgresoSubirFicheros()}, 1000)
	}
Formulario.prototype.consultaProgresoSubirFicheros=function(){
	var self=this
	loadJSONDoc('json', 
				{aplicacion:aplicacion, accion:'progresoSubida'}, 
				function(d){
					if (d.tipo=='error'){
						AIM.cancel()
						self.finSubirFicheros(d)
						return 
						}
						
					if (d.porcentaje!=null)
						cambiaTextoBloqueo(self.idxBloqueoSubir, d.porcentaje+'% subido'+(d.total!=null?' ('+formatNumber(d.leidos, 0)+' de '+formatNumber(d.total, 0)+' bytes)':'') )
					else
						cambiaTextoBloqueo(self.idxBloqueoSubir, 'subiendo...')
					self.inicioSubirFicheros()
					})
}
Formulario.prototype.errorSubirFicheros=function(){
	//dejamos el formulario tal y como estaba
	$('span.frm.enviandoArchivo').remove()
	$('input.lvwFile.oculto').removeClass('oculto')
	}
Formulario.prototype.finSubirFicheros=function(texto){
	clearTimeout(this.progresoSubidaArchivos)
	borraHijos(control('ifr_subirArchivos'))
	
	quitaBloqueo(this.idxBloqueoSubir)
	if (texto.tipo=='error' || texto.contains('error')){
		var ret
		if (texto instanceof String){
			if (texto.toLowerCase().startsWith('<pre'))
				texto=texto.substring(texto.indexOf('>')+1, texto.length-'</pre>'.length)
			ret=JSONparse(texto)
			}
		else
			ret=texto
		
		if (ret && ret.tipo=='error') 
			alert(ret.msg)
		
		clearTimeout(self.progresoSubidaArchivos) 
		}
	else
		this._enviaDatos()
}
Formulario.prototype.envia=function(reintentos){
	//Espera a que se resuelvan las búsquedas para aceptar el form
	if (reintentos!==0)
		for (var x in pendiente){
			if (pendiente[x]){
				var self=this
				if(!reintentos)
					reintentos=20
				else
					reintentos--
				setTimeout( function(){self.envia.call(self,reintentos)}, 200)
				console.debug('intento '+reintentos)
				return
				}
			}
	if(reintentos===0)
		pendiente={} // por si se queda colgado algo...
	if (this.valida()) {
		if (this.onAceptar!=null)
			this.onAceptar()
		else {
			var hayQueSubirArchivos=this._enviaArchivos()
			if (!hayQueSubirArchivos) this._enviaDatos()
			}
		}
	else{
		// Aviso de datos incompletos o incorrectos
		var self=this
		this.montaAyuda(this, 'Datos incompletos', 'Por favor, rellene o corrija los datos resaltados')
		}
}
Formulario.prototype.saltaAEtiqueta=function(etiqueta){
	var idxBloqueo=ponNuevoBloqueo(true, 'procesando...', this.idxBloqueo)
	// Esta línea estaba comentada!
	var param=this.recogeValoresFRM()
	param.etiqueta=etiqueta
	param.aplicacion=exp.aplicacion
	param.accion='saltarAEtiqueta'
	
	this.cierraFormulario(false)
	loadJSONDocPost('json', param, function(req){
		if (req.tipo=='depuracion' && depuradorActivo){
			depuradorActivo.actualizaDatosTrasPausa(req)
			}
		else {
			for (var i in pendiente){
				delete pendiente[i]
				clearTimeout(pendiente[i])
				}
			quitaBloqueo(idxBloqueo)		
			retornoTramite( xeval(req)  )
			}
		}
	)
}
Formulario.prototype._enviaDatos=function(){
	cambiaTextoBloqueo(this.idxBloqueo, 'procesando...')
	
	var param=this.recogeValoresFRM()
	this.cierraFormulario(false)
	
	param['aplicacion']= exp.aplicacion
	param['accion']='continuaTramite'
	loadJSONDocPost('json', param, function(req){
		retornoTramite( xeval(req)  )
		}
	)
}
Formulario.prototype.recogeValoresFRM=function(){
	var param={}
	for (var i=0; i<this.controles.length; i++) {
		var ctl=this.controles[i]
		if (ctl == null)
			continue
		else if (ctl.bloqueado) {
			if (ctl.tipo!='bsc')
				param[ctl.idOriginal]=ctl.valor //los bloqueados se devuelven tal cual
			else {
				for (var j=0; j< ctl.controles.length; j++){
					var subctl=ctl.controles[ j ]
					param[ subctl.idOriginal || subctl.id ]=ctl['valor'+j]
					}
				}	
			}
		else if (ctl.tipo == 'etiqueta'){
			/*pass*/}
		else if (ctl.tipo=='lvwFile'){
			/*pass*/}
		else if ( ['lvwMemo','lvw', 'lvwFecha', 'lvwNumero', 'lvwCurrency', 'lvwLista'].indexOf(ctl.tipo)>-1 )
			param[ctl.idOriginal]=control(ctl.id).value
		else if ( ['lvwBoolean','lvwOption'].indexOf(ctl.tipo)>-1 )
			param[ctl.idOriginal]=control(ctl.id).checked
		else if (ctl.tipo=='bsc'){
			if(this.validaEstaRelleno(ctl,true)){
				for (var j=0; j< ctl.controles.length; j++){
					var subctl=ctl.controles[ j ]
					param[ subctl.idOriginal || subctl.id ]=ctl['valor'+j]
					}
				}
			}
		}
	return param
}
Formulario.prototype.getControl=function(id){
	var xctl
	for (var i=0;i< this.controles.length;i++){
		xctl=this.controles[i]
		if (xctl==null) continue
		if (id==xctl.id || (xctl.idOriginal && id==xctl.idOriginal))
			return this.controles[i]
	}
	return null
}
Formulario.prototype.listaTXT=function(bsp){
	var x= this.getControl(bsp)
	return x.txts
}
Formulario.prototype.listaTXT_DOM=function(bsp){
	var x=this.getControl(bsp)
	var ret=[]
	for (var i=0; i< x.controles.length; i++)
		ret.push( control(x.controles[i].id) )
	return ret
}
Formulario.prototype.primerTXT=function(ctl){
	if (ctl.tipo=='bsc'){
		var xtxts=this.listaTXT(ctl.id)
		return xtxts[ctl.colsBloqueadas]
		}
	else
		return control(ctl.id)
}
Formulario.prototype.listaOPT=function(grupo){
	var ret=new Array()
	for (var i=0;i< this.controles.length;i++){
		if (this.controles[i].tipo=='lvwOption' && this.controles[i].grupo==grupo)
			ret.push( this.controles[i])
		}
	return ret
}
Formulario.prototype.marcaCampoErroneo=function(ctl, texto, ponerFoco){ //marcar como erroneo
	var td=control('error'+ctl.id)

	if (td) {
		td.title=texto
		
		var self=this
		ctl.spanGloboAyuda=td
		td.onclick=function(){self.montaGlobo(ctl, null, 'arriba', null, texto)}
		
		var tr=td.parentNode
		ponEstilo(tr, 'errorjsON')
		}

	var txt=this.primerTXT(ctl)
	ponEstilo(txt, 'errorjsON')

	if (ponerFoco)
		ponFoco( txt )
}
Formulario.prototype.marcaCampoCorrecto=function(ctl){
	var td=control('error'+ctl.id)
	
	if (td) {
		td.title=null
		td.onclick=function(){}
			
		var tr=td.parentNode
		quitaEstilo(tr, 'errorjsON')
		}

	var txt=this.primerTXT(ctl)
	if (txt) quitaEstilo(txt, 'errorjsON')
}
////////////////////////////////////////////////////////////
function quitaMarcaErroneo(id){
	quitaYPonMarcaErroneo(id, false)
}
function ponMarcaErroneo(id){
	quitaYPonMarcaErroneo(id, true)
}
function quitaYPonMarcaErroneo(id, erroneo){
	try { //si es un frm le quitamos la marca de 'erroneo'
		var frm=xSacaMD(id)

		//~ if (id.startsWith('input_control')) {
			//~ //se trata de un filtro del detalle
			//~ }
		//~ else
		if (frm==null || frm.numTramites==null) //se trata de un detalle y no de un frm
			return

		var ctl=frm.getControl(id)
		if (ctl==null)
			return

		if (erroneo)
			frm.marcaCampoErroneo(ctl)
		else
			frm.marcaCampoCorrecto(ctl)
		}
	catch (e)
		{/*pass*/}
}
///////////////////////////////////////////////////////////
Formulario.prototype.montaAyuda=function(casilla, titulo, texto){
	this.montaGlobo(casilla, null, null, titulo || casilla.ds, texto || casilla.globoAyuda)
}
Formulario.prototype.ocultaAyuda=function(){
	if (this.ventanaAyuda)
		$(this.ventanaAyuda).fadeOut(jquerycssmenu.fadesettings.overduration); 
}
Formulario.prototype.montaGlobo=function(padre, tipoGlobo, tipoFlecha, tituloGlobo, textoGlobo){
	var self=this
	var fnoculta=function(){self.ocultaAyuda()}
	
	if (! this.ventanaAyuda){ //1 globo activo por formulario
		var ventanaAyuda=creaObjProp('div', {className:'globoAyuda', 'style.display':'none', onclick:fnoculta})
		if (tipoGlobo) ponEstilo(ventanaAyuda, tipoGlobo)
		if (tipoFlecha) ponEstilo(ventanaAyuda, tipoFlecha)
		
		this.ventana.parentNode.appendChild(ventanaAyuda)
			
		ventanaAyuda.appendChild( creaObjProp('span', {className:'flecha', 'texto':espacioDuro4}))
		ventanaAyuda.appendChild( creaObjProp('span', {className:'titulo', 'texto':tituloGlobo}) )
		ventanaAyuda.appendChild( creaObjProp('span', {className:'texto', 'texto':textoGlobo}) )
		
		this.ventanaAyuda=ventanaAyuda
		}
	else {
		this.ventanaAyuda.className='globoAyuda'
		if (tipoGlobo) ponEstilo(this.ventanaAyuda, tipoGlobo)
		if (tipoFlecha) ponEstilo(this.ventanaAyuda, tipoFlecha)
		
		if (tituloGlobo!=null) $(this.ventanaAyuda).find('span.titulo').text(tituloGlobo)
		$(this.ventanaAyuda).find('span.texto').text(textoGlobo)
		}
		
	var v=$(this.ventanaAyuda)
	var o=$(padre.spanGloboAyuda).offset()
	v.css({left: o.left+'px', top: Math.max(o.top, 10)+'px'})		
	v.fadeIn(jquerycssmenu.fadesettings.overduration)
	
	/////////
	v.hover(function(){},  function(){
		clearTimeout(self.timeoutGloboAyuda)
		self.timeoutGloboAyuda=setTimeout(fnoculta, 1000)
		})
	
	}
////////////////////////////////////////////////////////////
function ModoDetalle2(id, md, formulario, nodoActivo, urlDestino){
	var yaExiste=exp.listaModosDetalle[id.toLowerCase()]
	if (!yaExiste) 
		ModoDetalle.call(this, id, md, null, nodoActivo, true)
	else
		this.filas=yaExiste.filas
	this.urlDestino=urlDestino //se usa para cambiar tabs y demás
	this.frm=formulario
	this.tipo='ModoDetalle2'
}
ModoDetalle2.prototype=new ModoDetalle
ModoDetalle2.prototype.hayQueAbrirTab=function(numControl){ //en la urlDestino debe venir &control1885=2&control19=0
	var temp=this.urlDestino
	if (!temp) return
	if (temp.startsWith('gotta://'))
		temp=temp.substring(8, 1000)
	var x=temp.split('&')
	for (var i=0; i<x.length; i++){
		var posibleControl=x[i]
		if (posibleControl.startsWith('control'+numControl)){
			var simbolo=posibleControl.indexOf('=')
			var valor=posibleControl.substring(simbolo+1, 1000)
            this.urlDestino=this.urlDestino.replace(posibleControl,'_'+posibleControl)
			return Number(valor)
			}
		}
	return null
}
ModoDetalle2.prototype.cargarControles=function(divContenedor, contenedor, pestActiva){
    var ret=[]
	for (var idCtl=0; idCtl<this.listaControles.length; idCtl++) {
		var ctl=this.listaControles[idCtl]
		if (typeof ctl=='function') continue
		
		var cargar=false

		if (ctl.cont==0 && contenedor==null)
			cargar=true //los del modo detalle
		else if (ctl.cont==contenedor && ctl.pestanha==pestActiva )
			cargar=true // pestañas
		else if (ctl.cont==contenedor && pestActiva==null) {
			var xcont=this.getControl(contenedor) //rd o exp
			
			if (!ctl.id.contains('tr_')){ //este ya es copia!
				if (xcont.tc=='rd'){
					ctl.id=ctl.id+'tr_'+ctl.cont+'_0' //el id en los controles del RD debe indicar la fila en la que está
					ctl.esCopiaDeRDFlotante=true
					}
				xcont.anhadirControlContenido(ctl)
				}
				
			cargar=(xcont.tc!='rd')
			}

		if (cargar) {
			ret.push(ctl)
			if (ctl.tc in Control) {
				var dom=null
				
				if ( ['txt', 'bsp', 'par'].contains(ctl.tc) ){
					var caption=ctl.caption
					if (ctl.tc=='txt'){
						/*pass*/
						}
					else	{
						var temp=caption.split(' ')
						caption=temp.length==1?temp[0]:temp[1]
						}
					
					var ctlFrm=this.frm.getControlPorCaption(caption)
					if ( ctlFrm)
						dom=this.frm.montaCasilla(ctl, ctlFrm)
					else {
						dom=ctl.toDOM()
						dom.tabIndex=-1
						}
					}
				else if (ctl.tc=='tab'){
					var pest = this.hayQueAbrirTab(ctl.numControl)
					if (pest!=null)
						ctl.pestActiva=pest
					dom=ctl.toDOM()
					}
				else  {
					dom=ctl.toDOM()
					dom.tabIndex=-1
					}

				if (dom) {
					divContenedor.appendChild (dom)
					var globo=this.getGloboAyuda(ctl)
					if (globo){
						divContenedor.appendChild(globo)
						mueve(globo, quitaPx(dom.style.left)+quitaPx(dom.style.width), quitaPx(dom.style.top))
						}
					}
				if (ctl.tc=='cmd' && ctl.texto.toLowerCase()=='autoexec') {
					this.botonAutoexec=ctl
					ponEstilo(dom, 'autoexec')
					}
				else if (ctl.esContenedor)
					ctl.cargarControles()
				
				if (ctl.tc=='tab') //parece que ni chrome ni ie son capaces de sacar el getComputedStyle o similares hasta que el control no está en el dom
					ctl.cambiaTamanhoPestanhas()
				}
			}
		}
        return ret
}
ModoDetalle2.prototype.getControl=function(id){
	id=id+''
	id=id.startsWith('cc_') ? id : this.generaIdControl(id)
	return this.dicControles[id]
}
ModoDetalle2.prototype.generaIdControl=function(idCtl){
	if (!idCtl.startsWith('control'))
		idCtl='control'+idCtl
	return 'cc_'+this.idx+'_'+idCtl
}
ModoDetalle2.prototype.getAncho=function(){
	var maximo=0
	for (var idCtl in this.listaControles) {
		var ctl=this.listaControles[idCtl]
		if (typeof ctl=='function') continue
		maximo=Math.max(ctl.izq+ctl.ancho, maximo)
		}
	return maximo
}
ModoDetalle2.prototype.getGloboAyuda=function(ctlDetalle){
	var ctlFrm=this.frm.getControlPorCaption(ctlDetalle.caption)
	if (ctlFrm)
		return this.frm.pintaGloboAyuda(ctlFrm)
}
ModoDetalle2.prototype.inicializa=function(frm){
	this.frm=frm
	for (var idCtl in this.listaControles) {
		var ctl=this.listaControles[idCtl]
		ctl.relleno=false

		if (ctl.tc=='lvw')
			ctl.xcontrol.cont=null
		}
//TODO HE QUITADO ESTA LÍNEA PQ DA ERROR ¿es necesaria en flotantes?
	//this.repasaControlesErroneos()
}
ModoDetalle2.prototype.actualiza=function(sinAutoexec){
	this.vaciaControles()
	if(!sinAutoexec && this.botonAutoexec)
		this.ejecutaAutoexec(this.nombreDetalle,this)
	else
		this._rellenaControles(this.nombreDetalle, this.nodoActivo, this.letras, this.tb, this.id)
}
////////////////////////////////////////////////////////////
function FormularioD(datos, idxDetalle, idxBloqueo, notificarCierre){
	if (!datos)
		return
	Formulario.call(this, datos, idxBloqueo, notificarCierre)
	this.idxDetalle=idxDetalle
	
	this.nodoActivo=datos.nodo//nodoActivo para cargar los controles dinámicos 
	this.urlDestino=datos.urlDestino 
	
	if (this.nodoActivo==null && this.urlDestino!=null){
		var dic=explotaURL(this.urlDestino)
		this.nodoActivo=dic['nodo']
		}
	
	exp.listaFormulariosModosDetalle=exp.listaFormulariosModosDetalle || {}
	this.tipo='FormularioD'
}
FormularioD.prototype= new Formulario
FormularioD.prototype.toDOM=function(){
	var existe=exp.getMD(this.idxDetalle)
	if (!existe){
		var self=this
		exp.cargaMD(this.idxDetalle, null, function(){self.toDOM()}, true) // noCargarControles:=true
		}
	else	{
		Formulario.prototype.toDOM.call(this)//cargamos los controles
		ponEstilo(this.ventana, 'formulario_detalle')
		ponEstilo(this.ventana, this.idxDetalle)
		
		this.detalle.rellenarControlesDIN()
		}
}
FormularioD.prototype.montaCasilla=function(ctlDetalle, ctlFrm){
	var ret
	ret=this.pintaControl(ctlFrm)

	mueve(ret, ctlDetalle.izq, ctlDetalle.tope, ctlDetalle.ancho, Math.max(ctlDetalle.alto,13) )

	return ret
	}
FormularioD.prototype.getControlPorCaption=function(caption){
	var ret
	for (var i=0;i<this.controles.length;i++){
		var ctl=this.controles[i]
		if (ctl.id.toLowerCase()==caption.toLowerCase())
			return ctl
		}
	return ret
}
FormularioD.prototype.getFrmMD=function(idxDetalle){
	var idx='frm_'+this.idx
	var ret=exp.listaFormulariosModosDetalle[idx]
	if (ret==null){ //lo 'clonamos'
		var md=exp.getMD(idxDetalle)
		ret=new ModoDetalle2(idx, md.datosDetalle, this, this.nodoActivo, this.urlDestino)
		
		//cambiamos los ids, para que no se líe
		ret.listaControles=[]
		ret.dicControles={}

			
		for (var i=0; i<ret.filas.length; i++) {
			var c=ret.filas[i]
			if (!c) continue
			var tipoControl=c[listaProps.tc]
			var idCtl=c[listaProps.numControl]

			var nuevo=new Control[tipoControl](c, ret)

			var nuevoid=ret.generaIdControl(idCtl)
			nuevo.id=nuevoid
			
			ret.listaControles.push(nuevo)
			ret.dicControles[nuevoid]=nuevo
			
			if (tipoControl=='lvw'){
				nuevo.xcontrol.contenedor=ret.generaIdControl(nuevo.xcontrol.contenedor)
				nuevo.xcontrol.cont=null
				}

			}

		exp.listaFormulariosModosDetalle[idx]=ret
		}
	ret.inicializa(this)
	return ret
}
FormularioD.prototype.calculaAnchoyAlto=function(){
	if (!this.detalle)
		this.detalle=this.getFrmMD(this.idxDetalle)

	var diff=0
	this.altoCuerpo=this.detalle.calculaAlturaPanel()+diff
	this.alto=this.altoCuerpo+68
	this.ancho=this.detalle.getAncho()+diff
}
var numFrm=0
FormularioD.prototype._pintaContenedorControles=function(cuerpofrm, altoCuerpo){
	this.detalle=this.getFrmMD(this.idxDetalle)
	var div=creaObjProp('div', {className:'article md', 'style.height':this.altoCuerpo, id:this.idxDetalle+'_'+(numFrm++)} )
	
	cuerpofrm.appendChild(div)
	
	this.detalle.div=div
	this.detalle.cargarControles(this.detalle.div)
}
FormularioD.prototype.pintaFila=function(){}
FormularioD.pintaControl=function(){}
FormularioD.prototype.pintaCuerpo=function(altoCuerpo){
	var cuerpofrm=creaObj('div', 'cuerpo')
	var tbody=this._pintaContenedorControles(cuerpofrm, altoCuerpo)
	return cuerpofrm
	}
FormularioD.prototype.pintaFilaVacia=function(){}
FormularioD.prototype.activate=function(){
	if (depuradorActivo) depuradorActivo.activaBotones(false)

	for (var idCtl in this.detalle.listaControles) {
		var ctl=this.detalle.listaControles[idCtl]
		if (ctl.tc=='tab'){ //para activar la pestaña 0
			activaPestanha(this.detalle.id, ctl.numControl, 0)
			}
		else if (ctl.tc=='lvw') {
			if (ctl.xcontrol.cont==null)
				ctl.xcontrol.cont=control(ctl.xcontrol.contenedor)
			}
		}
	if(this.detalle.botonAutoexec)
		this.detalle.ejecutaAutoexec(this.detalle.nombreDetalle,this.detalle)
	else
		this.detalle._rellenaControles(this.detalle.nombreDetalle, this.detalle.nodoActivo, this.detalle.letras, this.detalle.tb, this.detalle.id)
}
//////////////////////////////////////////////////////////////
function DetalleFlotante(datos, idxDetalle, idxBloqueo, notificarCierre){
	FormularioD.call(this, datos, idxDetalle, idxBloqueo, notificarCierre)
	this.tipo='DetalleFlotante'
	
	this.otrosDatos={}
	this.relativoADetalle=datos.urlDestino.contains('&relativo')
	if (datos.urlDestino){
		this.otrosDatos=explotaURL(datos.urlDestino)	
		this.titulo=this.otrosDatos['titulo'] || 'Ampliación de la información'
		}
}
DetalleFlotante.prototype=new FormularioD
DetalleFlotante.prototype.pintaPie=function(){
	var self=this
	return creaObjProp('img', {src:'./fijo/blank.png',alt:'x', className:'equis', title:'Cerrar', onclick:function(){self.cancela()} })
}
DetalleFlotante.prototype.toDOM=function(){
	var existe=exp.getMD(this.idxDetalle)
	if (!existe){
		var self=this
		exp.cargaMD(this.idxDetalle, null, function(){self.toDOM()}, true)
		}
	else	{
		Formulario.prototype.toDOM.call(this)
		ponEstilo(this.ventana, 'formulario_detalleFlotante')
		ponEstilo(this.ventana, this.idxDetalle)
		
		if (this.relativoADetalle) {
			ponEstilo(this.ventana.parentNode, 'relativoADetalle')
			
			var coords=$(existe.div).offset()
			
			this.ventana.parentNode.style.left=coords.left+'px'
			this.ventana.parentNode.style.top=coords.top-this.pTitulo.parentNode.offsetHeight+'px'
			}
		}
	this.tareasPostCarga()
}
DetalleFlotante.prototype.tareasPostCarga=function(){}
DetalleFlotante.prototype.cancela=function(){
	Formulario.prototype.cancela.call(this)
	delete exp.listaModosDetalle[this.detalle.id]
}
//////////////////////////////////////////////////////////////
function FormularioBSC(frmPadre, ctl, colsBloqueadas, idxBloqueo){
	if (!frmPadre)
		return // prototype
	Frm.call(this, 'bsc'+ctl.id, idxBloqueo)
	this.frmPadre=frmPadre
	this.frmPadre.formularioBSC=this

	this.throbber=null

	this.ctl=ctl
	this.colsBloqueadas=colsBloqueadas

	this.titulo=FormularioBSC.prototype.textoBuscar

	this.porcentajeAltoFrm=80
	this.porcentajeAnchoFrm=40 // %

	ocultaCalendario()
	var self=this
	var cmdAceptar=creaObj('button', 'cmdgt aceptar', null, Frm.prototype.textoAceptar, function(){self.aceptarPrimerResultado()})
	var cmdCancelar=creaObj('button', 'cmdgt cancelar', null, Frm.prototype.textoCancelar, function(){self.cancela()})

	this.botones=[cmdAceptar, cmdCancelar]
	this.fnAceptarPrimerResultado=null
}
FormularioBSC.prototype= new Frm
FormularioBSC.prototype.textoBuscar='Buscar'
FormularioBSC.prototype.cancela=function(){
	this.ocultaFormulario()
	this.frmPadre.formularioBSC=null
}
FormularioBSC.prototype.aceptarPrimerResultado=function(){
	//parece que aquí en this viene un objeto ventana
	if (this.acceptCharset == undefined )
		return getFrmActivo().fnAceptarPrimerResultado()
	
	if (this.fnAceptarPrimerResultado!=null) {
		if (pendiente.desc){
			setTimeout(function(){
					this.aceptarPrimerResultado()
				    },100)
			return
			}
		this.fnAceptarPrimerResultado()
		}
}

FormularioBSC.prototype.toDOM=function(){
	Frm.prototype.toDOM.call(this)
	ponEstilo(this.ventana, 'formularioBSC')
}
FormularioBSC.prototype.pintaCuerpo=function(altoCuerpo) {
	var self=this

	var cuerpofrm=creaObjProp('div', {className:'cuerpo cuerpoBS'})
	var fn=function(event){ 
			lanzaBusqueda(self);
			if (jwerty.is('enter', event))
				self.fnAceptarPrimerResultado()
			else if (jwerty.is('escape', event))
				self.cancela()
			}
	var idxInput='input_'+this.idx
	this.cuadroBusqueda=creaObjProp('div', {className:'cuadroBusqueda', hijo:creaObjProp('label', {texto:FormularioBSC.prototype.textoBuscar, 'htmlFor': idxInput})})

	this.texto=creaObjProp('input', {type:'text', className:'lvw', maxlength:80,  onkeyup:fn, id: idxInput})
		this.cuadroBusqueda.appendChild(this.texto)

	this.botonAltaDinamica=creaObj('button', 'cmdgt altaDinamica', 'altaDinamica', 'Nuevo')
		this.botonAltaDinamica.style.visibility='hidden'
		this.cuadroBusqueda.appendChild(this.botonAltaDinamica)

	var div2=creaObj('div', 'tablaBusqueda')
	div2.style.height=altoCuerpo-30  + 'px'

	if (altoCuerpo != null && exp && exp.internetExplorer) {
		cuerpofrm.style.width='100%'
		div2.style.width='100%'
		}

	this.tabla=creaObj('table', 'bsc')
	this.tabla.style.width='100%';this.tabla.cellSpacing=1
	this.tabla.appendChild( creaObj('thead') )
	this.tabla.appendChild( creaObj('tbody', 'bsc') )

	div2.appendChild(this.tabla)

	cuerpofrm.appendChild(this.cuadroBusqueda)
	cuerpofrm.appendChild(div2)
	return cuerpofrm
}
FormularioBSC.prototype.activate=function(){
	this.buscarPorDesc()
	this.ponFocoTXT(this.texto)
}
FormularioBSC.prototype.setTitulo=function(nuevo){
	this.titulo=nuevo
	this.pTitulo.replaceChild(creaT(nuevo), this.pTitulo.firstChild)
}
FormularioBSC.prototype.buscarPorDesc=function(){
	ponEstilo(this.cuadroBusqueda, 'cargando')
	var texto=this.texto.value
	var ctlid=idOriginal(this.ctl) || this.ctl.id
	var params= {'accion':'buscar',
				'bsp':ctlid,
				'buscar':texto,
				'bloqueadas':this.colsBloqueadas.length,
				'aplicacion': exp.aplicacion}
	if (this.ctl!=null && this.ctl.tabla) { //los bsp
		params['tabla']=this.ctl.tabla
		}

	for (var i=0; i < this.colsBloqueadas.length; i++)
		params['valor'+i] = this.ctl['valor'+i]
	
	var self=this
	loadJSONDoc ( 'json', params, function(req) {
			delete pendiente.desc
			
			var ret=xeval(req)
			
			self.rellenaTabla( ret )
			if (ret.tabla && self.titulo!=ret.tabla)
				self.setTitulo(ret.tabla)
			})
}


FormularioBSC.prototype.rellenaTabla=function(datos){
	var tabla=this.tabla
	var thead=tabla.firstChild
	var tbody=tabla.lastChild

	if (datos.tipo=='error'){
		var trh=creaObj('tr', 'bsc')
		trh.appendChild( creaObj('th', 'bsc', null, 'Error'))
		thead.appendChild(trh)

		var trb=creaObj('tr', 'bsc')
		trb.appendChild( creaObj('td', 'bsc', null, datos.msg))
		tbody.appendChild(trb)
		return;
		}
        tbody.id=datos.cd_tabla
	
	$(this.tabla).parents('div.ventana').addClass(datos.cd_tabla.toLowerCase())
	var columnas=datos.columnas
	var cabeceras=datos.cabeceras||columnas
	var tipos=datos.tipos
	var numOcultas=datos.numOcultas

	if (datos.tabla!=null && (titulo!='' && titulo!=null)) {
		this.pTitulo.removeChild(this.pTitulo.firstChild)

		var titulo=datos.tabla
		if (titulo.length>50)
			titulo=datos.tabla.substring(0, 50)+'...'
		this.pTitulo.appendChild(creaT( titulo ))
		}

	if (datos.altaDinamica && datos.altaDinamica != ''){
		this.botonAltaDinamica.style.visibility='visible' //lo mostramos
		this.botonAltaDinamica.onclick=function(){
						var frmbsc=getFrmActivo().formularioBSC

						var param={'valor0' : frmbsc.texto.value }
						for (var i=0; i < frmbsc.colsBloqueadas.length; i++)
							param['valor'+(i+1)] = frmbsc.colsBloqueadas[i]

						lanzaTramite(datos.altaDinamica, 'registro', null, true, param);return false;
						}
		}
	else
		this.botonAltaDinamica.style.visibility='hidden'
	
	if (thead.firstChild==null){
		var tr=creaObj('tr', 'bsc')
		var anhadirCabecera=false
		for (var c=0; c<columnas.length; c++){
			var nombreColumna=cabeceras[c] 
			if (nombreColumna.contains('\\')){ 
				var idx=nombreColumna.indexOf('\\') 
				nombreColumna=nombreColumna.substring(0, idx) 
				} 
				
			var columnaOculta=c<numOcultas?' oculta':''
			tr.appendChild( creaObj('th', 'bsc'+columnaOculta+' '+nombreColumna, null, nombreColumna))
			if (columnas[c] != "") anhadirCabecera=true
			}
		if (anhadirCabecera) thead.appendChild( tr )
		}

	var modificadores=[]
	for (var c=0; c<columnas.length; c++){
		var nombreColumna=cabeceras[c]
		
		var modif=null
		if (nombreColumna.contains('\\')){
			var idx=nombreColumna.indexOf('\\')
			modif=nombreColumna.substring(idx+1).toLowerCase()
			}
		modificadores.push(modif)
		}
		
	while (tbody.firstChild!=null)
		tbody.removeChild( tbody.firstChild )

	this.fnAceptarPrimerResultado=null
	for (var f=0; f<datos.filas.length;f++){
		var oc=new Array()
		var tr=creaObj('tr', 'bsc')

		for (var c=0; c<columnas.length; c++){
			var valorCelda=datos.filas[f][c]
			var valorBruto=valorCelda
			if (modificadores[c])
				valorCelda=formatoSalida(valorCelda, modificadores[c])
				
			var columnaOculta=c<numOcultas?' oculta':''
                
			var xtd=creaObj('td', tipos[c] + columnaOculta + ' '+columnas[c].toLowerCase(), null, valorCelda )
			if (valorBruto!=valorCelda)
				xtd.setAttribute('valorOriginal', valorBruto)
			tr.appendChild( xtd)

			if (c <= columnas.length-1 || columnas.length == 1)
				oc.push( comillasSimples(valorBruto) )
			}
		tr.onclick=this.fnClickFila(this.ctl.id, oc)

		if (this.fnAceptarPrimerResultado == null){
			var self=this
			this.fnAceptarPrimerResultado=self.fnClickFila(this.ctl.id, oc)
			}

		tbody.appendChild( tr )
	}
	quitaEstilo(this.cuadroBusqueda, 'cargando')
}
FormularioBSC.prototype.fnClickFila=function(bsp, arr){
    var self=this
	return function(){self.retorno(bsp, arr)}
}
FormularioBSC.prototype.retorno=function(bsp, valores){
	var ctl=this.frmPadre.getControl(bsp)
	for (var i = 0; i < ctl.txts.length; i++) {
		if (i<ctl.colsBloqueadas)
			continue //para no machacar los códigos amigables
		
		ctl['valor' + i] = valores[i]
		var txt=ctl.txts[i]
		txt.value=valores[i]
		}
	var txt=ctl.txts[ctl.txts.length-1]
	if (!txt.readOnly) {
		try {
			this.ponFocoTXT(txt)
			}
		catch(e){}
		}
	//cambiamos la descripción para que si le das a aceptar antes de traerse la descripción, funcione todo
	ctl.des.innerHTML="..."

	ctl.buscador.buscarPorCodigo(true)
	this.cancela()
}
//////////////////////////////////////////////////////////////
function FormularioBSM(frmPadre, ctl, colsBloqueadas, idxBloqueo, valorInicial){
	FormularioBSC.call(this, frmPadre, ctl, colsBloqueadas, idxBloqueo)
	this.titulo='Buscar (múltiple)'
	this.valorInicial=valorInicial
	this.porcentajeAnchoFrm=70 // %
	var self=this
	var cmdAceptar=creaObj('button', 'cmdgt aceptar', null, Frm.prototype.textoAceptar, function(){self.devolverResultados(self)})
	var cmdCancelar=creaObj('button', 'cmdgt cancelar', null, Frm.prototype.textoCancelar, function(){ self.cancela() })

	this.botones=[cmdAceptar, cmdCancelar]
	}
FormularioBSM.prototype=new FormularioBSC
FormularioBSM.prototype.devolverResultados=function(self) {
	var valor= []
	for (var i=0;i<self._resultados.length;i++)
		valor.push(self._resultados[i].split('\\')[0])
	valor=valor.join()
	
	var txt= this.ctl.txt
	this.ctl.valor0=valor
	txt.value=valor
	this.ctl.valorAnterior=[null]

	if (! txt.readOnly) {
		try
			{ txt.focus();}
		catch (err)
			{/*pass*/}
		}
	//cambiamos la descripción para que si le das a aceptar antes de traerse la descripción, funcione todo
	this.ctl.des.innerHTML="..."

	self.ctl.buscador.buscarPorCodigo(true)
	this.cancela()
}
FormularioBSM.prototype.toDOM=function(){
	Frm.prototype.toDOM.call(this)
	ponEstilo(this.ventana, 'formularioBSC formularioBSM')
}
FormularioBSM.prototype.pintaCuerpo=function(altoCuerpo) {
	var ret=FormularioBSC.prototype.pintaCuerpo.call(this,altoCuerpo)
	this._resultados=[]
	this.resultados=creaObj('table', 'bsc', 'seleccionados')

	this.resultados.appendChild( creaObj('thead') )
	this.resultados.appendChild( creaObj('tbody', 'bsc') )
	var self=this
	this.todos=creaObj('button', 'cmdgt seleccionarTodos', 'todos', '>>', function(){
		for(var i=1;i<self.tabla.rows.length;i++){
			var f=self.tabla.rows[i]
			f.onclick()
			}
		})
	this.todos.title='Seleccionar todos'
	this.ninguno=creaObj('button', 'cmdgt seleccionarNinguno', 'ninguno', '<<', function(){
		self._resultados=[]
		borraHijos(self.resultados.tBodies[0])
		})
	this.ninguno.title='Quitar la selección de todos'

	this.tabla.parentNode.appendChild(this.resultados)
	this.tabla.parentNode.appendChild(this.ninguno)
	this.tabla.parentNode.appendChild(this.todos)

    //buscar valores actuales
	var params={'aplicacion': aplicacion,'bsp':idOriginal(this.ctl) || this.ctl.id}
	params['tabla']=this.ctl.tabla
	params['valor0']=this.valorInicial
	params['filas']=true
	params['usarColBusq']=false
	var ctlid=this.ctl.id
	var clic=this.fnClickFila
	loadJSONDoc('buscar', params,function(req){
		delete pendiente[ctlid]
		var filas=xeval(req).filas
		for(var i=0;i<filas.length;i++)
			clic(ctlid, filas[i])()
		})
	return ret
}
FormularioBSM.prototype.fnClickFila=function(bsp, columnas){
	return function(){
		var xfrm=getFrmActivo()
		var frmbsc
		
		if (xfrm instanceof DetalleFlotante)
			frmbsc=xfrm.detalle.formularioBSC	
		else if (xfrm instanceof Formulario)
			frmbsc=xfrm.formularioBSC
		else if (xfrm instanceof ModoDetalle)
			frmbsc=xfrm.formularioBSC
		var columnasSep=columnas.join('\\')
		if (frmbsc._resultados.contains(columnasSep))
			return

		var tr=creaObj('tr','bsc')
		tr.setAttribute('str',columnasSep)
		for (var c=0; c<columnas.length; c++){
			tr.appendChild( creaObj('td','lvw', null, columnas[c] ))
			tr.onclick=function(){
				frmbsc._resultados.remove(tr.getAttribute('str'))
				this.parentNode.removeChild(this)
				}
			}
		frmbsc.resultados.tBodies[0].appendChild(tr)
		frmbsc._resultados.push(columnasSep)
		}
}
FormularioBSM.prototype.rellenaTabla=function(datos){
	FormularioBSC.prototype.rellenaTabla.call(this,datos)
	$(this.tabla).parents('div.ventana').addClass('formularioBSC').addClass('formularioBSM')
	var tHead=this.resultados.firstChild
	if (!tHead.firstChild){
		var trHead=creaObj('tr','bsc')
		var trHeadOriginal=this.tabla.firstChild.firstChild

		for (var i=0; i<trHeadOriginal.childNodes.length;i++) {
			var th=trHeadOriginal.childNodes[i]
			trHead.appendChild( creaObjProp('th', {clasName:th.className, 'texto':th.textContent || th.innerText} ) )
			}
		tHead.appendChild(trHead)
		}
}

//////////////////////////////////////////////////////////////
function FormularioMSG(datos, idxBloqueo, notificarCierre){
	var idx='msg'+datos.tramite + datos.camino

	if (idxBloqueo==null)
		idxBloqueo=datos.idxBloqueo

	Frm.call(this, idx, idxBloqueo)
	listaFrm.push( this )

	this.refrescarAlCancelar=datos.refrescarAlCancelar
	this.camino=datos.camino
	this.tramite=datos.tramite

	this.porcentajeAnchoFrm=50
	this.altoFrm=264

	this.texto=datos.texto
	this.listaBotones=datos.botones
	this.estilo=datos.clase
	this.varRetorno=datos.retorno

	this.estilo=this.estilo || 'informacion'
	this.titulo=FormularioMSG.prototype.textoTitulo

	var cancelar=this.cancelar()
	for (var i=0; i<this.listaBotones.length; i++) {
		var btn=this.listaBotones[i]
		var clase2=null
		var l=btn.texto.toLowerCase()
		if (l=='no')
			clase2='no'
		else if (l== 'sí' || l=='si')
			clase2='si'
		else 
			clase2=btn.texto.toLowerCase()
		var button=this.montaBoton(btn.texto, clase2, btn.img, btn.valor)
		
		this.botones.push(button)
		}
	if (datos.clase=='comunicacion'){
		//sin botón cancelar
		}
	else {
		var self=this
		jwerty.key('cancel', function(){self.cancela()}, null, '#'+idx)
	
		var cmdCancelar=creaObjProp('button', {className:'cmdgt cancelar', 'texto':this.textoCancelar,	onclick:cancelar})
		this.botones.push(cmdCancelar)
		}
}
FormularioMSG.prototype.textoTitulo='Mensaje de la aplicación'
FormularioMSG.prototype= new Frm
FormularioMSG.prototype.espacioSobrante=90
FormularioMSG.prototype.montaBoton=function(texto, clase2, img, valor){
	var fn=function(){ self.envia(valor)}
	
	var props={onclick:fn, 'texto':texto, className:'cmdgt'+(img?' '+img:'')+(clase2?' '+clase2:null)}
	if (img && img!='') {
		props['style.backgroundImage']=completaRutaImg(img, true)
		props['style.paddingLeft']='16px'
		}
	var btn=creaObjProp('button', props)
	
	
	var self=this
	jwerty.key('enter', fn, null, btn)
	
	return btn
}
FormularioMSG.prototype.cancela=function(){
	depuradorActivo=null
	this.ocultaFormulario()
	listaFrm.pop()

	if (this.notificarCierre) this.notificaCancelarTram()
}
FormularioMSG.prototype.cierraFormulario=function(){
	this.ocultaFormulario(false)
	listaFrm.pop()
	if (depuradorActivo) depuradorActivo.activaBotones(true)
}
FormularioMSG.prototype.envia=function(valor){
	this.cierraFormulario()
	var idxBloqueo=ponNuevoBloqueo(true, 'procesando...')

	var param={'aplicacion': exp.aplicacion, 'accion':'continuaTramite'}
	if (this.varRetorno && valor!=null)
		param[ this.varRetorno ]=valor
	loadJSONDocPost('json', param, function(req){
		quitaBloqueo(idxBloqueo)
		retornoTramite( xeval(req) )
		}
	)
}
FormularioMSG.prototype.toDOM=function(){
	Frm.prototype.toDOM.call(this)
	ponEstilo(this.ventana, 'formularioMSG '+this.estilo)
}
FormularioMSG.prototype.pintaCuerpo=function(){
	var cuerpofrm=creaObjProp('div', {className:'cuerpo'})
	cuerpofrm.appendChild( creaObjProp('p', {'textos':this.texto} ) )
	return cuerpofrm
}
FormularioMSG.prototype.activate=function(){
	this.botones[ 0 ].focus()
	if (depuradorActivo) depuradorActivo.activaBotones(false)
}
//////////////////////////////////////////////////////////////
Frm.prototype.textoTitulo='Introduzca los datos'
Frm.prototype.textoAceptar='Aceptar'
Frm.prototype.textoCancelar='Cancelar'
Frm.prototype.textoSi='Sí'
Frm.prototype.textoNo='No'

Frm.prototype.textoTitulo='Introduzca los datos'
FormularioMSG.prototype.textoTitulo='Mensaje de la aplicación'
Formulario.prototype.textoCancelarLote='¿Desea ejecutar el resto del lote? Queda(n) %s trámite(s) por ejecutar.\nPulse "Aceptar" para continuar o "Cancelar" para detener la ejecución del resto del lote'

FormularioBSC.prototype.textoBuscar='Buscar'
//////////////////////////////////////////////////////////////
/*
 *
 * Textarea Maxlength Setter JQuery Plugin
 * Version 1.0
 *
 * Copyright (c) 2008 Viral Patel
 * website : http://viralpatel.net/blogs
 *
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
*/
var tox
jQuery.fn.maxlength = function(){
	this.keyup(function(event){
		var key = event.which;

		//all keys including return.
		//~ if(key >= 33 || key == 13) {
			var maxLength = Number($(this).attr("maxlength"))
			var length = this.value.length
			if(length >= maxLength) {
				event.preventDefault();
				this.value=this.value.substring(0, maxLength)
				}
			
			length=this.value.length
			var diff=(maxLength-length)
			var tit=''+length+' caracteres ('+(diff==1?'queda ':'quedan ')+diff+')'
			clearTimeout(tox)
			
			var self=this
			tox=setTimeout( function(){$(self).parent('td').find('span.numCaracteres').remove()}, 4500)
			
			$(self).parent('td').find('span.numCaracteres').remove()
			this.parentNode.appendChild (creaObjProp('span',{className:'textoAyuda numCaracteres', texto:tit}))
			//~ }
	});
}
function abreVentanaBuscador(ctl, md, frm) {
	var ctl
	var frmbsc
	var colsBloqueadas=new Array()

	if (md==null){
		for (var i=0;i<ctl.controles.length;i++){
			if ( i < ctl.colsBloqueadas ){
				colsBloqueadas.push(ctl['valor'+i])
				}
			}
		}
	else {
		frm=md
		}

	var idxBloqueo=ponNuevoBloqueo()
	if (ctl.tc=='bsm'){
		frmbsc=new FormularioBSM(frm, ctl, colsBloqueadas, idxBloqueo, ctl.valor0)
		}
	else
		frmbsc=new FormularioBSC(frm, ctl, colsBloqueadas, idxBloqueo)
	frmbsc.toDOM()
	}

function lanzaBusqueda(frm){
	if (pendiente.desc)
		clearTimeout( pendiente.desc )
	pendiente.desc=setTimeout( function(){frm.buscarPorDesc()}, 400)
}
///////////////////////////////////////////////
function fnBorraYCreaFile(ctl, idControl){
	return function(){borraYCreaFile(ctl, idControl)}
	}
function borraYCreaFile(ctl, idControl){
	var nuevo=creaObjProp('INPUT', {type:'file', size:50, id:idControl, className:'lvwFile'})
	var viejo=control(idControl)

	viejo.parentNode.replaceChild(nuevo, viejo)
	}
function fnCreaFileMultiple(ctl, div){return function(){creaFileMultiple(ctl, div, this)}}
function creaFileMultiple(ctl, div, enlace){//cuando hay subida múltiple
	var cuantosHay=div.getElementsByTagName('INPUT').length
	
	var nuevoId=ctl.id+'_'+cuantosHay
	var nuevo=creaObjProp('INPUT', {type:'file', size:50, id:nuevoId , className:'lvwFile'})
	
	var btnVaciar=creaObjProp('div',  {className:'cmd_file', onclick:fnBorraYCreaFile(ctl, nuevoId), title:'[Click aquí para vaciar el campo]', texto:espacioDuro4}) 
	
	div.insertBefore(nuevo, enlace)
	div.insertBefore(btnVaciar, nuevo)
	
	var br=creaObjProp('br',{})
	div.insertBefore(br, btnVaciar)
}
function aceptaFormulario(){
	var frm=getFrmActivo()
	try
		{frm.envia()}
	catch(e) {
		console.error('ERROR '+e.message + '\nen '+e.fileName+' linea '+e.lineNumber)
		console.trace()
		}
}
function cancelaFormulario(){
	var frm=getFrmActivo()
	for (var i in pendiente)
		if(frm.getControl(i)){
			window.clearTimeout(pendiente[i])
			pendiente[i]=null
			}
	frm.cancela()
	}
function fnGuardaYSalta(etiqueta){
	return function(){
		var frm=getFrmActivo()
		frm.saltaAEtiqueta(etiqueta)
		}
}
function quitaBloqueo(idx){
	if (idx==null)
		return
	var bloqueo=control(idx)
	if (bloqueo==null)
		return
	var to=bloqueo.getAttribute('to')
	if(to)
		clearTimeout(to)
	bloqueo.parentNode.removeChild(bloqueo)
}
function quitaCargandoBloqueo(idx){
	var cargando=control('cargando'+idx)
	if (cargando) {
		var bloqueo=cargando.parentNode
		var to=bloqueo.getAttribute('to')
		if(to)
			clearTimeout(to)
		bloqueo.style.visibility="visible"
		cargando.parentNode.removeChild(cargando)
		forzarRepintadoIE()
		}
}
function cambiaTextoBloqueo(idx, texto){
	var s='div.cargandoCuadro#cargando'+idx
	
	$(s+'> div.cargandoIndicador span').text(' '+texto)
	var cuadro=$(s)
	cuadro.css({'left': (cuadro.parent().outerWidth()-cuadro.outerWidth())/2 })
	
	}
function calculaIdBloqueo(){
	return 'bloqueo'+Math.round( Math.random() * 666666 )
}
function ponNuevoBloqueo(conThrobber, textoThrobber, idx){
	var d=idx || calculaIdBloqueo()
	ponBloqueo(d, conThrobber, textoThrobber)
	return d
}
function ponBloqueo(idx, conThrobber, textoThrobber){
	var bloqueo=control(idx)
	if(!bloqueo){
		bloqueo=creaObj('div', 'bloqueo', idx)
		var bloqueoColor=creaObj('div', 'bloqueoColor')
		bloqueo.appendChild(bloqueoColor)
		if(conThrobber)
			bloqueo.style.visibility="hidden"
		}
	if (conThrobber!=null && conThrobber) {
		var cargando=creaObjProp('div',{className:'cargandoCuadro', id:'cargando'+idx})
		var indicador=creaObjProp('div', {className:'cargandoIndicador'})

		var throbber=creaObjProp('img', {src:'./fijo/throbber.gif', className:'throbber'})
		indicador.appendChild(throbber)
		indicador.appendChild(creaObjProp('span', {texto:' '+textoThrobber}))

		cargando.appendChild(indicador)
		bloqueo.appendChild(cargando)
		}
	document.body.appendChild( bloqueo )

	if(!conThrobber)
		ponFoco(idx)
	else{
		var to= setTimeout(function(){
			bloqueo.style.visibility="visible"
			if (exp.internetExplorer)
				forzarRepintadoIE()
			ponFoco(idx)
			},500)
		bloqueo.setAttribute('to',to)
	}
    return bloqueo
}
function lanzaTramite(camino, tramite, fnPostTramite, altaDinamica, paramAdicionales, botonAutoexec, tramiteVinculado, reintentos){
	//Espera a que se resuelvan las búsquedas para aceptar el form
	if (reintentos===undefined || reintentos!==0)
		for (var x in pendiente){
			if (pendiente[x]){
				var self=this
				if (!reintentos)
					reintentos=20
				else
					reintentos--
				setTimeout( function(){lanzaTramite(camino, tramite, fnPostTramite, altaDinamica, paramAdicionales, botonAutoexec, tramiteVinculado, reintentos)}, 200)
				console.debug('intento '+reintentos)
				return
				}
			}
	if(reintentos===0)
		pendiente={} // por si se queda colgado algo...
			
	
	var idx= ('tramite'+tramite + camino).toLowerCase()

	var hayQueMandarFiltros=false
	if (botonAutoexec==null)
		hayQueMandarFiltros=!['autoexec', 'gtusuario'].contains(tramite.toLowerCase())
	else
		tramiteVinculado=true
	
	var param = { 'accion':'inicioTramite', 'camino': camino, 'tramite':tramite, 'aplicacion': exp.aplicacion, 'idxBloqueo':idx}
	if (tramiteVinculado)
		param['tramiteVinculado']=tramiteVinculado
	
	if (altaDinamica) {
		hayQueMandarFiltros=false
		param['altaDinamica']=1
		for (var k in paramAdicionales)
			param[k]=paramAdicionales[k]
		}
	else if (exp.listaControlesPorTipo && hayQueMandarFiltros){ // Es exp normal
		var paramFiltro=_filtrar()
		if(paramFiltro===null)
			return
		for(var k in paramFiltro){
			param[k]=paramFiltro[k]
			}
		}
		
	ponBloqueo(idx, true, "Cargando...")
	if (paramAdicionales && paramAdicionales.tipoEjecucion)
		param.tipoEjecucion=paramAdicionales.tipoEjecucion

	if (paramAdicionales && paramAdicionales.modoDetalle) {
		//trámite auto de botón
		param.md=paramAdicionales.modoDetalle
		param.tb=paramAdicionales.tablaBase
		param.letras=paramAdicionales.letras
		param.nodo=paramAdicionales.nodoActivo
		}
		
	if ( hayQueMandarFiltros ) {
		var detActivo, listaCTL
		if (listaFrm.length>0){
			detActivo=listaFrm[listaFrm.length-1].detalle
			listaCTL=detActivo.listaControlesPorTipo( Control.prototype.controlesConFilas )
			}
		else  {
			detActivo=exp.getMD(exp.idMdActivo)
			listaCTL = exp.listaControlesPorTipo( Control.prototype.controlesConFilas )
			}

		var fila=0
		for (var i=0; i<listaCTL.length; i++){
			var xctl=listaCTL[i]
			
			var ctl=xctl.id
			if (xctl.estaVisible() && !xctl.esCopiaDeRDFlotante){
				var ctl2=xctl.md.getControl(xctl.id)
				var listaCHK=control(ctl2.id).getElementsByTagName('input')

				for (var j=0; j<listaCHK.length; j++ ){
					var chk = listaCHK[j]
					if (chk.checked) {
						if (param[ 'checkbox_'+ctl]==null) param[ 'checkbox_'+ctl]=[]
						param[ 'checkbox_'+ctl].push(chk.value)
						fila++
						}
					}
				var sep='|-|-|' //inicialmente el separador era una coma, pero puede venir como parte del código del usuario
				if ('checkbox_'+ctl in param){
					var cadena=param[ 'checkbox_'+ctl].join(sep)
					param[ 'checkbox_'+ctl]=cadena
					}
				}
			}
		}
	loadJSONDocPost( 'json',
			    param,
			   function(req){
   				var ret=xeval(req)
				ret.botonAutoexec=botonAutoexec

				if (fnPostTramite!=null)
					fnPostTramite( ret, botonAutoexec && botonAutoexec.md )
				else
					retornoTramite( ret )
					},
			   function(req){
					errorAjax(req)
					quitaBloqueo(idx)
					}
			   )
}
function sacaPrimero(dic){
	for (var id in dic){
		return dic[id]
		}
	}
function sacaUltimo(dic){
	var ultimo=null
	for (var id in dic){
		var actual=dic[id]
		if (actual)
			ultimo=actual
		}
		return ultimo
	}
Explorador.prototype.fnSeguir=function(seguir, retorno){
	this.seguir=seguir
        this.errorEmision=retorno
	}
function retornoTramite(respuesta, w, iInicial){
	if (respuesta==null)
		return
	if (w && !w.closed){
		return setTimeout(function(){retornoTramite(respuesta,w,iInicial)},500)
		}
	if (w !== undefined && !exp.seguir) {
		exp.seguir = true
		
		var informes=respuesta.informes.split(/ /)
		var clave=informes[iInicial-1]
		
		var param=$.extend({aplicacion: exp.aplicacion, accion:'retornoEmisionInformes', idInforme:clave}, exp.errorEmision)
		loadJSONDocPost('json', param, function(req){retornoTramite( xeval(req) )})
		return
		}
	iInicial|=0
	if (respuesta.informes) {
		//aquí nos vendrán varios informes...
		var informes=respuesta.informes.split(/ /)
		for (var i=iInicial; i<informes.length; i++){
			var clave=informes[i]
			w=window.open(respuesta['informe_'+clave])
			if(clave.endsWith('+')){
				exp.seguir=false
				return retornoTramite(respuesta,w,i+1) //llamada recursiva hasta q se haya agotado el prod
				}
			}
		}
	//y procesamos la respuesta
	if ( respuesta.tipo=='frm') {
		var frm
		//~ if (respuesta.numcontrol){	//edición de una lista
			//~ var xfrm=sacaPrimero(exp.listaFormulariosModosDetalle) || exp.getMD(exp.idMdActivo)
			//~ var lista=xfrm.getControl(respuesta.numcontrol)
			//~ frm=new FormularioEnLista(respuesta, respuesta.idxBloqueo, true, lista)
			//~ frm.toDOM()
			//~ }
		//~ else 
		if (respuesta.detalle) {  //formulario diseñado
			frm=new FormularioD( respuesta, respuesta.detalle, respuesta.idxBloqueo, true)
			frm.toDOM()
			}
		else  {
			frm=new Formulario( respuesta, respuesta.idxBloqueo, true )
			frm.toDOM()
			}
		}
	else if (respuesta.tipo=='captura'){
		bloqueoCaptura=ponNuevoBloqueo(true, respuesta.literal, respuesta.idxBloqueo)
		meterApplet(respuesta)
		}
	else if ( respuesta.tipo	=='datosFirmados') {
		var frm=new FormularioFirmaDatos( respuesta, null, respuesta.tipo, true )
		frm.toDOM()
		}
	else if ( respuesta.tipo	=='infoFirma') {
		infoFirma(respuesta)
		}
	else if ( respuesta.tipo	=='validarFirma') {
		firmaEsValida(respuesta)
		}
	else if ( respuesta.tipo	=='firmaDatos') {
		init_firma() //damos tiempo a que se cargue el applet
		esperaYCarga(respuesta)
		}
	else if ( respuesta.tipo	=='msg' ) {
		if (respuesta.clase=='progreso'){
			$('#'+respuesta.idxBloqueo + ' div.cargandoIndicador').text(espacioDuro+respuesta.texto)
			
			var param={'aplicacion': exp.aplicacion, 'accion':'continuaTramite'}
			loadJSONDocPost('json', param, function(req){retornoTramite( xeval(req) )}
			)
			return
			}
		else{
			var frm=new FormularioMSG( respuesta, respuesta.idxBloqueo )
			frm.toDOM()
			}
		}
	else if ( (['refrescodetalle', 'refresco', 'refrescocontroles' ].contains(respuesta.tipo))) {
		
		if ( respuesta.tipo=='refrescocontroles' ) {
			quitaBloqueo(respuesta.idxBloqueo)
			var tempControles=respuesta.refrescocontroles_controles.split(',')
			
			for (var id in exp.listaFormulariosModosDetalle){
				var flotante=exp.listaFormulariosModosDetalle[id]
				if (flotante) {
					for (var i=0; i<tempControles.length; i++){
						var idCtl=tempControles[i]
						var ctl=flotante.getControl(idCtl)
						if (ctl){
							ctl.vacia()
							ctl.rellena()		
							}
						}			
					}
				}
			for (var i=0; i<tempControles.length; i++){
				var idCtl=tempControles[i]
				var ctl=exp.getMD(exp.idMdActivo).getControl(idCtl)
				if (ctl){
					ctl.vacia()
					ctl.rellena()		
					}
				}
				
			}
		else if ( (['refrescodetalle', 'refresco'].contains(respuesta.tipo)) && getBscActivo() ){
			//retorno después de un alta dinámica
			quitaBloqueo(respuesta.idxBloqueo)
			lanzaBusqueda(getBscActivo()) //Forzar rebuscar, a ver si ahora se encuentra lo nuevo
			}
		else if ( ['refrescodetalle', 'refresco'].contains(respuesta.tipo) ) {
			if (depuradorActivo!=null)
				depuradorActivo.cancela()

			exp.filasSeleccionadas=respuesta.variables //para seleccionar las filas que estuvieran pinchadas antes de lanzar el trámite
				
			if (respuesta.tipo=='refresco')
				exp.refrescaTodo(respuesta.idml)//detalle y flotantes
			else if (respuesta.tipo=='refrescodetalle'){
				var flotante=sacaPrimero(exp.listaFormulariosModosDetalle)
				if (flotante) 
					flotante.actualiza()								
				else {
					var md=exp.getMD(exp.idMdActivo)
					if (md)
						md.rellenaControles(exp.idMdActivo)
					}
				}
				
			if ('controles' in respuesta){//abrir pestaña
				for(var i in respuesta.controles){
					var num=i.substring(7)
					// Buscamos pestaña en md cargados
					for (var idmd in exp.listaModosDetalle ){
						activaPestanha(idmd,num,respuesta.controles[i])
						}

					}
				}
				
				
			quitaBloqueo(respuesta.idxBloqueo)
			}
		
		////////////////////////
		if (exp.listaFormulariosModosDetalle && respuesta.cerrarFlotantes){
			//cerramos los flotantes que toquen
			if (respuesta.cerrarFlotantes==-1)//cerrar todos
				respuesta.cerrarFlotantes=200
			
			var arrayflotantes=[]
			for (var id in exp.listaFormulariosModosDetalle){
				arrayflotantes.push(id)
				}
			
			arrayflotantes=arrayflotantes.reverse()
			for (var numFlot=0; numFlot<arrayflotantes.length; numFlot++){
				var id=arrayflotantes[numFlot]
				var flotante=exp.listaFormulariosModosDetalle[id]
				if (numFlot<respuesta.cerrarFlotantes)
					flotante.frm.cancela()
				}
			}
		//abrimos los nuevos flotantes 
		if (respuesta.flotantes){ 
			for (var i=0;i<respuesta.flotantes.length; i++){ 
				var url=respuesta.flotantes[i] 
				url+='&flotante=1' // por si acaso 
				navegarA(url, null, null, false) 
				} 
			} 
		}
	else if ( respuesta.tipo	=='cancelar' ) {
		if (depuradorActivo)
			depuradorActivo.cancela()
		quitaBloqueo(respuesta.idxBloqueo)
		}
	else if (respuesta.tipo	=='redirigir') {
		if(respuesta.direccion)
			window.location=respuesta.direccion
		else
			window.location.reload()
		if (respuesta.idml != null)
			cambiaML( respuesta.idml, respuesta.dsml)
		}
	else if ( respuesta.tipo	=='depuracion' ){
		if (depuradorActivo==null) {
			var md=(exp && exp.idMdActivo?exp.getMD(exp.idMdActivo):null)
			
			depuradorActivo=new FormularioDepurador( respuesta, respuesta.idxBloqueo, md?md.botonAutoexec : null )
			depuradorActivo.toDOM()
			}
		else  {
			depuradorActivo.retornoTramitacion(respuesta)
			}
		quitaCargandoBloqueo(respuesta.idxBloqueo)
		}
	else if (respuesta.tipo	=='pausa'){
		quitaCargandoBloqueo(respuesta.idxBloqueo)
		depuradorActivo.retornoTramitacion( respuesta)
		}
	else if (respuesta.tipo	=='error'){
		//~ alert(respuesta.msg)
		if (depuradorActivo)
			depuradorActivo.cancela() //por si acaso
		else
			quitaBloqueo(respuesta.idxBloqueo)
		}
	else if (respuesta.tipo	=='SesionPerdida'){
		try	{
			window.location=exp.getURL()
			}
		catch (e){
			alert('La sesión ha finalizado por falta de actividad')
			window.location.reload()    
			}
		return null
		}
	else
		alert('frmDinamico.retornoTramite:' + respuesta.tipo)
}
/////////////////////////////////////////////
function comillasSimples(exp){
	return exp
}
///////////////////////////
function meterApplet(resp){
	var capa = document.createElement('div')
	capa.id='captura'
	capa.style.visibility="hidden"
	capa.innerHTML=resp.applet

	document.body.appendChild(capa)
}
var bloqueoCaptura = null
function continuarApplet(){
	quitaBloqueo(bloqueoCaptura)
	var param={'aplicacion': exp.aplicacion, 'accion':'finCaptura'}
	loadJSONDocPost('json', param, function(req){
		retornoTramite( xeval(req)  )
		}
	)
}
////////////////////////////
function explotaURL(url){//genera un dic con las claves de  la url
	var ret={}
	if (url.startsWith('gotta://'))
		url=url.substring('gotta://'.length)
	var temp=url.split('&')
	for (var i=0; i<temp.length; i++){
		var trozos=temp[i]
		if (trozos){
			var pos=trozos.indexOf('=')
			var nombre=trozos.substring(0, pos)
			var valor=trozos.substring(pos+1, trozos.length)
			
			ret[nombre]=valor
			}
		}
	return ret
	}
////////////////////////////
function quitaPx(t){
	if ((t+'').endsWith('px') )
		t=t.substring(0,t.length-2)
	return Number(t)
}
function ponFoco(id){
	try{
		var ctl= (typeof id == 'string'? control(id) : id)
		if (!ctl)
			return
		if(ctl.focus)
			ctl.focus()
		if(ctl.select)
			ctl.select()
		}
	catch(e)
		{}
}
function xSacaListaTXT(bsp){
	var xfrm=getFrmActivo()
	var xcontrol
	if (xfrm instanceof DetalleFlotante){
		xcontrol=xfrm.detalle.getControl(bsp)
		if (xcontrol)
			return xcontrol.txts
		}
	else if (xfrm instanceof Formulario)
		return xfrm.listaTXT_DOM(bsp)
	else if (xfrm instanceof ModoDetalle){
		xcontrol=exp.getMD(exp.idMdActivo).getControl(bsp) || exp.getMD(exp.idMlActivo).getControl(bsp)
		if (xcontrol !=null)
			return xcontrol.txts
		}
}
function xSacaMD(bsp){
	var xfrm=getFrmActivo()
	
	if (xfrm instanceof DetalleFlotante){
		xcontrol=xfrm.detalle.getControl(bsp)
		return xcontrol.md
		}
	else
		xcontrol=xfrm.getControl(bsp)
		
	if (xcontrol)
		return xfrm
	else {
		xcontrol=exp.getMD(exp.idMlActivo).getControl(bsp)
		if (xcontrol !=null) md=exp.getMD(exp.idMlActivo)
		return md
		}
}
function dameElemento(lista, id){
	for (var i=0; i<lista.length; i++){
		if (lista[i].id!=null && lista[i].id.toLowerCase() == id.toLowerCase())
			return lista[i]
 		else if (lista[i].id=='' && lista[i].name.toLowerCase() == id.toLowerCase())
			return lista[i]
 		else if (lista[i].id==null && lista[i].toLowerCase() == id.toLowerCase())
			return lista[i]
		}
}
function noNulo(v){
	return (v==null?'':v).trim()
	}
/////////////////////////////////////////
function Buscador(ctl){
	this.ctl=ctl
	//~ this.ctl.dom=ctl.des.parentNode
	this.cmdbsp=$(this.ctl.dom).find('div.cmd_bsp')[0]
	
	this.esPrimeraBusqueda=false	
	this.valido=true
	this.nulo=true
	this.hayCambios=false
	this.yaInicializado=false
	
	this.valorAnterior=[]
	for (var i=0; i<ctl.txts.length;i++)
		this.valorAnterior.push(null)
		
	this.descAnterior=null
	}
Buscador.prototype.idBsp=function(){
	return idOriginal(this.ctl) || this.ctl.id
	}
Buscador.prototype.ponNulo=function(iColumna){
	this.cancelaBusqueda()
	for (var i=this.ctl.colsBloqueadas;i<this.ctl.txts.length;i++) {
		this.ctl['valor'+i]=''
		}
		
	this.valido=true
	this.nulo=true
		
	this.propagaNulo(this.listaHijos(iColumna))
	}
Buscador.prototype.propagaNulo=function(lista, iCodigo){
	for (var i=0; i<lista.length; i++){
		var bsc=lista[i].buscador
		if (!bsc) break	
		
		for (var j=0; j<Math.min(this.ctl.txts.length, bsc.ctl.txts.length); j++){
			if (j==iCodigo)
				bsc.ctl.txts[j].value=''
			else
				bsc.ctl.txts[j].value=this.ctl.txts[j].value
			}
		bsc.ponNulo()
		bsc.vaciaDescripcion()
		}
	}
Buscador.prototype.cancelaBusqueda=function(){
	var idbsp=this.idBsp()
	
	clearTimeout( pendiente[idbsp] )
	delete pendiente[idbsp]
	this.quitaThrobber()
	}
Buscador.prototype.miraSihayCambios=function(forzar, faseValidacion){
	faseValidacion=faseValidacion || false
	var hayCambios=false
	if (!forzar) 
		this.yaInicializado=true
	
	if (forzar)
		this.hayCambios=true
	else	{
		for (var i=0;i<this.ctl.txts.length;i++) {
			/*si el valor ha cambiado, lanzamos la búsqueda*/
			var txt=this.ctl.txts[i]
			
			var vNuevo=noNulo(txt.value)
			var vAnt=noNulo(this.valorAnterior[i])
			
			if (vNuevo=='') {/*si el código no está relleno pasamos del tema*/
				if (!faseValidacion) 
					this.ponNulo(i)
				else
					this.nulo=true
				
				return
				}
			if (hayCambios==false){
				if ( vAnt != vNuevo)
					hayCambios=true
				}
			}
		this.hayCambios=hayCambios
		}
	this.nulo=false
	}
Buscador.prototype.ponThrobber=function(){
	$(this.ctl.dom).find('table').addClass('cargando')
	}
Buscador.prototype.quitaThrobber=function(){
	$(this.ctl.dom).find('table').removeClass('cargando')
	}
Buscador.prototype.hazPeticionBuscarPorCodigo=function(forzar){
	// OJO si se sale de esta función sin buscar, hay que borrar el pendiente para que no se quede colgado luego
	var md=this.ctl.md || getFrmActivo()
	if (md==null)
		return
	
	var params={'aplicacion': aplicacion,'bsp':this.idBsp()}
	if (md.nombreDetalle) {
		params['md']=md.nombreDetalle
		params['nodo']=md.nodoActivo
		}
	
	if (this.ctl.tabla)  //los bsp
		params['tabla']=this.ctl.tabla		
	
	if ( this.ctl.txts==null) {
		this.cancelaBusqueda()
		return
		}

	this.ponThrobber()
	
	var hayVaciosBloqueados=false
	for (var i = 0; i < this.ctl.txts.length; i++) {
		if (i == this.ctl.txts.length - 1) //no bloqueados: el valor del cuadro de texto
			params['valor' + i] = this.ctl.txts[i].value
		else { //bloqueados, se envía lo que hay en this.ctl['valor'+i]
			var v=this.ctl['valor' + i]
			if(v==null)
				v=''
			params['valor' + i] =v 
			}
		if (i<this.ctl.colsBloqueadas && (params['valor'+i]==null || params['valor'+i]=='') ){
			hayVaciosBloqueados=true
			break
			}
		}
	if (hayVaciosBloqueados){
		this.cancelaBusqueda()
		return
		}
	var self=this
	loadJSONDoc('buscar', params, 
		function(req){
			if (!(self.idBsp() in pendiente)){
				console.debug(self.idBsp()+': ha llegado el resultado de la búsqueda pero ya no está pendiente')
				self.quitaThrobber()

				return
				}
			var ret=xeval(req)
			
			self.cancelaBusqueda()
			if (ret.tipo=='error')
				self.valido=false
			else 	
				self.muestraResultados( ret )
			} , 
		function(req){
			self.cancelaBusqueda()
			}
		)
}
Buscador.prototype.muestraResultados=function(ret){
	var md=this.ctl.md || getFrmActivo()
	
	var porDefecto={codigo:null, nombresCasillas:[]}
	for (var i=0;i<ret.length;i++){
		var tramo=ret[i]
		var ctl=md.getControl(tramo.id)
		if (ctl) {
			var tramAnterior= (i>0?ret[i-1]: porDefecto)
			if (tramAnterior.accion != 'rellenaDescripcion')
				tramAnterior=porDefecto
			
			if (tramo.accion == 'rellenaDescripcion') {
				var xbsc=buscadores[tramo.id]
				xbsc.rellenaCasillas(xbsc!=this, tramo.nombresCasillas, tramo.valores, tramo.descripcion, tramo.codigo, tramAnterior.codigo, tramAnterior.nombresCasillas.length)
				
				if (tramo.descripcion==null){
					xbsc.propagaNulo( xbsc.listaHijos(xbsc.ctl.colsBloqueadas), xbsc.ctl.colsBloqueadas )
					}
				}
			else if (tramo.accion == 'buscarPorCodigo') {
				ctl.buscador.esPrimeraBusqueda=this.esPrimeraBusqueda
				ctl.buscador.buscarPorCodigo(false)
				}
			}
		}
	this.esPrimeraBusqueda=false
	}
Buscador.prototype.vaciaDescripcion=function() {
	this.sustituyeDescripcion('')
	}
Buscador.prototype.sustituyeDescripcion=function(texto) {
	if (!this.esPrimeraBusqueda){
		try{getFrmActivo().actualizaBloques()}
		catch(e){}
		}
	var t=noNulo(texto)
	this.ctl.des.innerHTML=t
	this.ctl.des.title=t
	
	this.esPrimeraBusqueda=false
	}
Buscador.prototype.rellenaCasillas=function(sustituirBloqueados, listaCasillas, listaValores, descripcion, codigo, codigoAmigableTramoAnterior, numCasillaTramoAnterior) {
	var hayCambios=false
	
	var listaTXT= this.ctl.txts
	for (var i=0;i<listaCasillas.length;i++) {
		var valorAnterior
		if (i==listaTXT.length-1) {			
			this.rellenaCasilla(i, codigo, descripcion != null)
			
			var xv=listaValores?listaValores[i]:null
			
			if (this.ctl['valor' + i] != xv)  hayCambios=true
			this.ctl['valor' + i] = xv
			
			valorAnterior=codigo
			}
		else {
			var xv=listaValores?listaValores[i]:null
			
			if (!sustituirBloqueados && i<this.ctl.colsBloqueadas){
				//pass //para que no sustituyan los códigos 'amigables'
				}
			else if (listaValores){
				var cod=listaValores[i]
				if (i==numCasillaTramoAnterior-1)
					cod=codigoAmigableTramoAnterior || cod
				this.rellenaCasilla(i, cod, descripcion != null)
				
				if (this.ctl['valor' + i] != xv) hayCambios=true
				this.ctl['valor' + i] = xv
				}
			valorAnterior=xv
			}
		
		if (descripcion!=null)
			this.valorAnterior[i]=this.ctl.txts[i].value
		if (valorAnterior==null)
			console.debug(this.idBsp()+': ha venido un valor vacío para '+listaCasillas[i]+'. No va a poder cachearse para evitar futuras búsquedas')
		}
	//y la descripción
	var destino = this.ctl.des

	if (descripcion!=null && !destino.readOnly) {
		this.descAnterior=descripcion
		this.sustituyeDescripcion(descripcion)
		this.valida()
		this.nulo=false
		}
	else {
		if (hayCambios){
			this.descAnterior=null
			this.vaciaDescripcion()
			if (codigo!='') // se ha buscado algo
				this.invalida()
			else // está vacío
				this.valida()
			}
		}
}
Buscador.prototype.esperarYBuscarPorCodigo=function(forzar){
	//Este es adecuado para ser llamado en eventos keyPress, keyDown: se espera un poco, y si se ha acabado de escribir se busca
	//	Para eventos blur es mejor llamar directamente a buscarPorCodigo
	
	var self=this
	setTimeout(function(){self.aux_buscarPorCodigo(forzar, 500)}, 10)
	}
Buscador.prototype.buscarPorCodigo=function(forzar){	
	//Llamar directamente a este es adecuado en eventos blur: se busca, sin esperar nada
	this.aux_buscarPorCodigo(forzar, 0)
}
Buscador.prototype.lanzaPrimeraBusqueda=function(){
	var bscPadre=this.bscsQueBloquea()
	if (bscPadre==null || this.completamenteLleno()){
		this.esPrimeraBusqueda=true
		this.buscarPorCodigo(false)
		}
	else
		console.debug(this.idBsp()+' / '+this.ctl.ds+': no se lanza la búsqueda porque depende de otro')
	}
Buscador.prototype.completamenteLleno=function(){
	for (var i=0; i<this.ctl.txts.length; i++){
		if (this.ctl.txts[i].value=='')
			return false
		}
	return true
	}
Buscador.prototype.valida=function(){
	this.valido=true
	//~ this.nulo=false
	}
Buscador.prototype.invalida=function(){
	this.valido=false
	}
Buscador.prototype.rellenaCasilla=function(i, valor){
	var casilla=this.ctl.txts[i]

	if (valor!=null && casilla.value != valor){
		//Mantenemos la selección del usuario
		var sels=null
		try 	{
			if (casilla.offsetWidth){
				try{sels=casilla.selectionStart}
				catch(e){}
				}
			}
		catch(e){}
		
		if (sels)
			var texto=casilla.value.substring(0,sels)
		casilla.value=valor
		if (sels){
			var texto2=casilla.value.substring(0,sels)
			if (!casilla.readOnly && texto.toLowerCase()==texto2.toLowerCase()) {
				casilla.selectionStart = sels
				casilla.selectionEnd = valor.length
				}
			}
		}
	}
Buscador.prototype.aux_buscarPorCodigo=function(forzar, tEspera){
	tEspera=tEspera || 10
	var idbsp=this.idBsp()

	var self=this
	if (pendiente[idbsp]){//búsqueda en curso
		clearTimeout( pendiente[idbsp] )
		delete pendiente[idbsp]
		
		console.log(this.idBsp()+': cancelo búsqueda y lanzo otra')
		pendiente[idbsp]=setTimeout( function(){self.hazPeticionBuscarPorCodigo(forzar)}, tEspera)
		return
		}
	
	var eraValido=this.valido
	this.invalida() //de momento lo invalidamos para evitar condiciones de carrera
	
	this.miraSihayCambios(forzar)
	if (this.nulo){
		this.vaciaDescripcion()
		this.valida()
		}
	else if (this.hayCambios==false && eraValido) { //lo dejamos como estaba
		this.sustituyeDescripcion(this.descAnterior)
		this.valida()
		}

	if (this.hayCambios && !this.nulo) {
		this.cancelaBusqueda()
		pendiente[idbsp]=setTimeout( function(){self.hazPeticionBuscarPorCodigo(forzar)}, tEspera)
		}
	else
		this.cancelaBusqueda()
}
Buscador.prototype.bscsQueBloquea=function(){
	var ret=null
	if (!this.frm)
		return ret
	
	//el actual es
	var xid=this.frm.controles.indexOf(this.ctl)
	if (!this.ctl.colsBloqueadas)
		return ret
	for (var id=xid-1; id>=0; id--){
		var ant=this.frm.controles[id]
		if (ant.tipo=='bsc'){ 
			if (ant.colsQueBloquea)
				return ant
			}
		}
	
	return ret
	}
Buscador.prototype.listaHijos=function(iColumna){
	var ret=[]
	if (!this.frm)
		return ret
	
	//el actual es
	var xid=this.frm.controles.indexOf(this.ctl)
	if (!this.ctl.colsQueBloquea)
		return ret
	for (var id=xid+1; id<this.frm.controles.length; id++){
		var sig=this.frm.controles[id]
		if (sig.tipo=='bsc'){ 
			if (sig.colsBloqueadas>iColumna)
				ret.push(sig)
			if (sig.colsQueBloquea==0)
				break
			}
		}
	
	return ret
	}
/////////////////////////////////////////////////////////////////////////////////
/*  AJAX IFRAME METHOD (AIM)  http://www.webtoolkit.info/ */
var AIM = {
	frame : function(c) {
		var n = 'f' + Math.floor(Math.random() * 99999);
		this.idFrame=n
		var d = document.createElement('DIV');
		d.innerHTML = '<iframe style="display:none" src="blank.html" id="'+n+'" name="'+n+'" onload="AIM.loaded(\''+n+'\')"></iframe>'; 
		document.body.appendChild(d);

		var i = document.getElementById(n)
		if (c){ 
			if (typeof(c.onComplete) == 'function')
				i.onComplete = c.onComplete
			if (typeof(c.onError) == 'function')
				i.onError = c.onError
			}
		return n;
		},
	form : function(f, name) {
		f.setAttribute('target', name)
		f.setAttribute('action', 'subir?aplicacion='+exp.aplicacion)
		},
	cancel: function(){
		var i=$('iframe#'+this.idFrame)
		i.attr('src', 'blank.html')
		i[0].onComplete=i[0].onError
		},
	submit : function(f, c) { 
		AIM.form(f, AIM.frame(c));
		if (c && typeof(c.onStart) == 'function')
			return c.onStart();
		else
			return true;
		},
	loaded : function(id) {
		var d
		var i = document.getElementById(id);
		if (i.contentDocument) 
			d = i.contentDocument;
		else if (i.contentWindow) 
			d = i.contentWindow.document;
		else
			d = window.frames[id].document;
		if (typeof(i.onComplete) == 'function') 
			i.onComplete(d.body.innerHTML)
		window.setTimeout(function() {i.parentNode.removeChild(i)},100) //Sin setTimeout se queda pegado el reloj
		}
}
//////////////////////////////////////////////////////////////////////////////////
function infoFirma(datosJson){
	var datos= {numTramites: 0, camino: 0, tramite: 'infoFirma', tipo: 'frm', titulo: 'Información de la firma', alto: 250, controles:[
			{tipo: 'lvw',id: 'a',maxlength: 250, valor: datosJson.subject,obligatorio: false,bloqueado: true, ds: 'Firmante'},
			{tipo: 'lvw',id: 'b',maxlength: 50, valor: datosJson.FechaFirmaLocal,obligatorio: false,bloqueado: true, ds: 'Fecha de firma'}
			]}
	var idxBloqueo=ponNuevoBloqueo()
	var xfrm=new Formulario( datos, idxBloqueo )
	xfrm.botones=[
				creaObj('button', 'cmdgt cancelar', 'cerrarFrmFirma', 'Cerrar', function(){ getFrmActivo().cancela() })
				]
	xfrm.controlConFoco='cerrarFrmFirma'
	xfrm.toDOM()
}
function firmaEsValida(datosJson){
	var titulo
	var tramite
	var icono
	if (datosJson.cod==500){
		titulo='La firma es válida'
		tramite='firmaEsValida'
		icono='./fijo/form22.png'
		}
	else	{
		icono='./fijo/crit22.png'
		titulo='La firma no es válida'
		tramite='firmaNoEsValida'
		}
	var datos= {"numTramites" : 0,"camino" : 0, tramite: tramite,"tipo" : 'frm',"titulo" : titulo,"alto" : 300,'controles':[
				{tipo: 'lvw',id: 'c',maxlength : 200, valor: datosJson.subject,obligatorio: false, bloqueado: true, ds: 'Firmante'},
				{tipo: 'lvw',id: 'd',maxlength : 50, valor: datosJson.FechaFirmaLocal,obligatorio: false, bloqueado: true, ds: 'Fecha de firma'},
				{tipo: 'lvw',id: 'e',maxlength : 300, valor: datosJson.desc,obligatorio: false, bloqueado: true, ds: 'Resultado'}
				]}
	var idxBloqueo=ponNuevoBloqueo()
	var xfrm=new Formulario( datos, idxBloqueo )
	xfrm.botones=[creaObj('button', 'cmdgt cancelar', 'cerrarFrmFirma', 'Cerrar', function(){ getFrmActivo().cancela() })]
	xfrm.controlConFoco='cerrarFrmFirma'
	
	xfrm.toDOM()
	}
// para mover los Formularios /////////////////////////////////
var frmEnMovimiento, fantasma
function dragFRMPress(evt, xfrm) {
	evt = new Evt(evt)
	frmEnMovimiento=xfrm.parentNode
	
	var $frm=$(frmEnMovimiento)
	var pos=$frm.offset()
	
	fantasma = creaObjProp('div', {className:'moverFrm', 'style.left':pos.left, 'style.top':pos.top, 'style.width':$frm.outerWidth(), 'style.height':$frm.outerHeight()})
	
	frmEnMovimiento.style.display='none'
	frmEnMovimiento.parentNode.appendChild( fantasma )

	deltaX = evt.x - getX(fantasma)
	deltaY = evt.y - getY(fantasma)

	Evt.addEventListener(document, "mousemove", dragFRMMove, false)
	Evt.addEventListener(document, "mouseup", dragFRMRelease, false)
}
function dragFRMMove(evt) {
	evt = new Evt(evt)
	setX(fantasma, enPantalla(evt.x - deltaX, 'x') )
	setY(fantasma, enPantalla(evt.y - deltaY, 'y') )
	evt.consume()
}
function enPantalla(num, x){
	var margen=100
	if (x=='x'){
		num=Math.max(0, num)
		num=Math.min(num, getAnchoPantalla()-margen)
		}
	else {
		num=Math.max(0, num)
		num=Math.min(num, getAltoPantalla()-margen)
		}
	return num
}
function dragFRMRelease(evt) {
	evt = new Evt(evt)

	Evt.removeEventListener(document, "mousemove", dragFRMMove, false)
	Evt.removeEventListener(document, "mouseup", dragFRMRelease, false)
	
	mueve(frmEnMovimiento, getX( fantasma ), getY( fantasma ))
	frmEnMovimiento.style.display='block'
	fantasma.parentNode.removeChild(fantasma)
}
function esperaYCarga(respuesta){

	try{
		var frm=new FormularioFirmaDatos(respuesta.datos, respuesta.sysdate, respuesta.idxBloqueo )
		frm.toDOM()
		}
	catch(e){
		while(listaFrm.length)
			listaFrm.pop()
		console.debug(e)
		console.debug('...')
		window.setTimeout(function(){
			esperaYCarga(respuesta)}
			,1000)
		}
}
function cargaXML_ie(text){
	var xmlDoc=new ActiveXObject("Microsoft.XMLDOM")
	xmlDoc.async="false"
	xmlDoc.loadXML(text)
	return xmlDoc
	}
function cargaXML_ff(text){
	parser=new DOMParser();
	var xmlDoc=parser.parseFromString(text,"text/xml")
	return xmlDoc
	}
function cargaXML(text){
	try {//Internet Explorer
		if (esInternetExplorer())
			return cargaXML_ie(text)
		else
			return cargaXML_ff(text)
		}
	catch(e) {
		try 	{//Firefox, Mozilla, Opera, etc.
			return cargaXML_ff(text)
			}
		catch(e){
			alert(e.message);
			return;
			}
		}
}
function forzarRepintado(){
	//~ document.documentElement.scrollLeft=0
	}
function forzarRepintadoIE(){
	if(exp){
		if(exp.internetExplorer)
			document.documentElement.scrollLeft=0
		}
	else{
		if(esInternetExplorer())
			document.documentElement.scrollLeft=0
		}
		
}
function mensaje(texto, v){
	for (var i=0; i<v.length; i++){
		texto=texto.replace('%s', v[i])
		}
	return texto
	}
//////////////////////////////
function fnOpenCalendar(id){return function(){new Calendario( arguments[0], id, control(id).value )} }
//////////////////////////////
function FormularioFirmaDatos(datos, sysdate, idxBloqueo, yaFirmado){
	if (idxBloqueo==null) idxBloqueo=datos.idxBloqueo
	Formulario.call(this, 'firma'+datos.tramite + datos.camino, idxBloqueo)

	this.yaFirmado=yaFirmado
	this.camino=datos.camino
	this.tramite=datos.tramite

	this.datos=datos
	if (this.datos=='')  this.datos=null
	
	if (datos.acceptCharset) this.acceptCharset=datos.acceptCharset

	if (yaFirmado){
		this.titulo='Información firmada:'
		this.botones=[
					creaObj('button', 'cmdgt cancelar', null, 'Cerrar', function(){ getFrmActivo().cierraFormulario() })
					]
		}
	else{
		this.titulo='Atención: se dispone a firmar electrónicamente los siguientes datos:'
		this.listaFirmas=creaObj('select', 'cmdgt', 'listaCertificados')
		
		var self=this
		this.botones=[	creaT('Certificado con el que se va a firmar:'),
					this.listaFirmas ,
					creaObjProp('button', {className:'cmdgt recargarCertificados', texto:'Recargar certs.', title:'Forzar recarga de los certificados instalados, incluyendo dni-e', onclick:function(){
						ponEstilo(this, 'cargando')
						
						try {
							//~ self.oFirma.applet.getLstCertificados()
							self.oFirma.rellenaListaCertificadosDisponibles()
							}
						catch(e){
							alert('Se ha producido un error al recargar la lista de certificados disponibles: '+ e.message)
							}
						quitaEstilo(this, 'cargando')
						}}
					),
					creaObj('button', 'cmdgt aceptar', null, 'Firmar', function(){aceptaFormulario()} ) ,
					creaObj('button', 'cmdgt cancelar', null, Frm.prototype.textoCancelar, function(){ getFrmActivo().cancela() })
					]

		this.oFirma=new ComponenteFirmaElectronica(this.listaFirmas, xeval(datos.jsFirma), new Date(sysdate))
		
		}
	this.controles=datos.controles
	this.onAceptar=null //para los trámites un poco mágicos, tipo cambiar contraseña le indicamos aquí la func a la que llamar al pulsar aceptar
}
FormularioFirmaDatos.prototype= new Formulario
FormularioFirmaDatos.prototype.pintaCasillas= function (datos, tbody){
	this.datos.jsondatos=xeval(this.datos.jsondatos) || []
	for (var i=0; i<this.datos.jsondatos.length; i++) {
		var dato=this.datos.jsondatos[i]
		var ctl={"id": 'a', valor: dato.valor, obligatorio: false, bloqueado: true, "ds" : dato.texto||''}
		ctl.maxlength=5+(ctl.valor && ctl.valor.length) || 5
		
		dato.valor=dato.valor || ''
		if (ctl.maxlength>100)
			ctl.tipo='lvwMemo'
		else if (dato.valor.contains(vbCr))
			ctl.tipo='lvwMemo'
		else
			ctl.tipo='lvw'
		var input=null
		
		if (dato.strTipo=='Dato')
			input=this.pintaControl(ctl)
		else if (dato.strTipo=='NombreTrámite'){
			//~ #color='$$$blue'
			if (dato.texto==null)
				continue
			}
		//~ else if (dato.strTipo=='TítuloFormulario')
			//~ color='$$$black'
		//~ else
			//~ color='$$$gray'

		this.pintaFila (tbody, ctl.ds, false, input, ctl.id, ctl.id, ctl)
		//              tabla, etiqueta, obligatorio, casilla, idCasilla, idCtl, ctl
		}
	}
FormularioFirmaDatos.prototype.fnObtenerFichero=function(frm, params, tbody){
	loadJSONDoc( 'obtenerFichero', params, function(req) {
		var datos=eval( "(" + req + ")")
		frm.pintaCasillas(datos,tbody)
		})
}
function sacaExtension(nombreArchivo){
	if (!nombreArchivo.contains('.'))
		return ''
	return nombreArchivo.substring(nombreArchivo.lastIndexOf('.')+1).toLowerCase()
	}
FormularioFirmaDatos.prototype.pintaTablaDatos= function (){
	if (this.tabla!=null)
		return
	
	this.tabla=creaObjProp('table', {'style.width':'100%'})
	this.tbody=creaObj('tbody')

	this.tabla.appendChild( this.tbody )
	this.cuerpofrm.appendChild(this.tabla)
	}
FormularioFirmaDatos.prototype.pintaCuerpo= function (altoCuerpo){
	var cuerpofrm=creaObj('div', 'cuerpo')
	this.cuerpofrm=cuerpofrm
	
	this.pintaTablaDatos()
	if (this.datos!=null) {
		this.pintaCasillas(this.datos, this.tbody)
		}
		
	if (this.datos.productos.length) {
		var listaProductos=creaObjProp('div', {className:'listaProductosFirmar'})
		cuerpofrm.appendChild ( listaProductos )
		
		for (var d=0; d<this.datos.productos.length; d++) {
			var producto=this.datos.productos[d]
			var docu
			if (producto.tipo=='PDF'){
				var url='./obtenerFichero?aplicacion='+exp.aplicacion+'&numerador='+producto.numerador
				docu=creaObjProp('iframe', {className:'productoFirmar', frameBorder:'none', src:url})
				}
			else {
				docu=creaObjProp('div',{className:'productoFirmar'+' el'+listaProductos.childNodes.length})
				listaProductos.appendChild ( docu )
				
				var url='./obtenerFichero?aplicacion='+exp.aplicacion+'&numerador='+producto.numerador
				
				if (this.yaFirmado){
					if (producto.numerador==0){
						var params={
							'aplicacion':exp.aplicacion,
							'numerador':producto.numerador,
							'firma':0
							}
						this.fnObtenerFichero(this, params, this.tbody)
						}
					
					var pr0=creaObjProp('a', {className:'productoFirmar', href:url+'&firma=0', title:'Abrir el archivo o datos que se firmaron', target:'previsualizar', texto:producto.ds_documento})
					docu.appendChild(pr0)
					
					docu=creaObjProp('div',{className:'productoFirmar'+' el'+listaProductos.childNodes.length})
					listaProductos.appendChild ( docu )
					var pr1=creaObjProp('a', {className:'productoFirmar', href:url+'&firma=1', title:'Abrir la firma', target:'previsualizar', texto:'Firma de "'+producto.ds_documento+'"'})
					docu.appendChild(pr1)
					
					ponEstilo(docu, sacaExtension(producto.ds_documento))
					}
				else{
					var pr1=creaObjProp('a', {className:'productoFirmar', href:url, title:'Abrir el archivo que se va a firmar', target:'previsualizar', texto:producto.ds})
					docu.appendChild(pr1)
					
					ponEstilo(docu, sacaExtension(producto.ds))
					}
				}
			
			}
		}
	return cuerpofrm
}
FormularioFirmaDatos.prototype.cierraFormulario=function(quitarBloqueo){
	this.ocultaFormulario(quitarBloqueo)
	listaFrm.pop()
	}
FormularioFirmaDatos.prototype.cancela=function(){
	this.cierraFormulario(true)
}
FormularioFirmaDatos.prototype.firmaENVELOPED=function(){
	var datosFirmados=this.oFirma.firmarXML( this.datos.xmldatos) // {id:'83829383', texto:'<xml>'}
	return {datosFirmados0:datosFirmados.texto, idSignature:datosFirmados.idSignature}
	}
FormularioFirmaDatos.prototype.firmaENVELOPING=function(){
	// se firman y guardan por separado: por un lado los datos, y por otro cada uno de los archivos adjuntos
	if (this.datos!=null){ //firma de datos
		var datosAFirmar=this.datos.jsondatos
		var datosFirmados=this.oFirma.firmarTexto( datosAFirmar )
		
		var producto={tipo:'TXT', txt:datosAFirmar}
		this.datos.productos.splice(0,0,producto)
		}
	else
		this.datos.productos.splice(0,0,null) // Para que los docus siempre empiecen en 1
		
	//firma de Ficheros
	var datosFirmados=false
	var param={}
		
	var docBase=document.location.href
	docBase=docBase.substring(0,docBase.lastIndexOf('/')+1)
	for (var i=0;i<this.datos.productos.length;i++){
		var prd=this.datos.productos[i]
		if (prd===null)
			continue
		var tipo=prd.tipo

		if (tipo=='PDF'){
			// se firman directamente en el servidor, no hay que enviar el retorno, se supone que el applet lo habrá subido
			
			//urlFichero, asunto, lugar, razon, contacto, urlSubida, numerador, idcookie
			var retorno=this.oFirma.firmarPDFServidor(docBase+'./obtenerFichero?aplicacion='+exp.aplicacion+'&numerador='+prd.numerador,
					null, null, null, null,
					'./subir?aplicacion='+exp.aplicacion,
					prd.numerador,
					null
				)
			
			}
		else if (tipo=='TXT'){ //Éste es para los datos de los frm del trámite
			datosFirmados=true
			param['datosFirmados'+i]=this.oFirma.firmarTexto( prd.txt )
			}
		else if (tipo=='XML'){
			datosFirmados=true
			param['datosFirmados'+i]=this.oFirma.firmarXML( prd.txt )
			}
		else{ // tipo=='OTROS' (archivos binarios)
			datosFirmados=true
			param['datosFirmados'+i]=this.oFirma.firmarUrl(prd.digest, docBase+'./obtenerFichero?aplicacion='+exp.aplicacion+'&numerador='+prd.numerador+'&hash=true')
			}
		}
	if (datosFirmados)
		return param

	}
FormularioFirmaDatos.prototype.envia=function(){
	if (this.oFirma.lst.value==''){
		alert('No se ha seleccionado un certificado válido')
		return false
		}
		
	this.cierraFormulario(false)
	var idxBloqueo=ponNuevoBloqueo(true, 'procesando...', this.idxBloqueo)
	try	{
		var tipoFirma=this.oFirma.jsInicializacion.tipoFirma
		
		var param
		if (tipoFirma==ENVELOPED){//se firma un único xml que contiene los adjuntos como digest
			param=this.firmaENVELOPED()
			}
		else 	{//se firma cada objeto por separado
			param=this.firmaENVELOPING()
			}
		
		if (param) {//Al menos uno no era PDF
			if (tipoFirma==ENVELOPED)
				param['accion']='guardaFirmaJunta'
			else
				param['accion']='guardaFirmaSeparada'
			}
		else
			param={accion:'continuaTramite'}
			
		param.aplicacion=aplicacion
		loadJSONDocPost('json', param, function(req){
								quitaBloqueo(idxBloqueo)
								retornoTramite( xeval(req) )
								})
		}
	catch(e){
		quitaBloqueo(idxBloqueo)
		alert(e)
		}
}
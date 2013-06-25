var tiposLista=['tl', 'te', 'tp', 'lt', 'pb', 'tf', 'ar']
var btnAbajo=40, btnArriba=38, btnIzq=37, btnDer=39
var btnHome=36, btnEnd=35
var teclasDireccion='↑/↓/←/→'
var teclasIzqDer='←/→'
var teclasArribaAbajo='↑/↓'
var btn0=48, btn1=49,btn2=50,btn3=51,btn4=52,btn5=53,btn6=54,btn7=55,btn8=56,btn9=57
var vbCrLf='\n\t', vbCr='\n'
var espacio=' '
	
function control(id) {
	return document.getElementById(id) 
}
function esInternetExplorer(){
	return $.browser.msie
}
function esInternetExplorer6(){
	return versionInternetExplorer()==6
}
function versionInternetExplorer(){
	var nav=$.browser
	if (!nav.msie) 
		valor=-1
	else
		valor= Number(nav.version)
	return valor
	}
//////////////////////////////////////////
function creaObj(tipo, clase, id, texto, onclick, labelFor){
	var d=document.createElement(tipo)

	if (clase!=null) d.className=clase	
	if (onclick != null) d.onclick=onclick
	if (typeof texto=='boolean')
		texto=texto.toString()
	if (texto!=null) {
		if (labelFor){
			var lbl=creaObj('label')
			lbl.htmlFor = labelFor
			
			var trozos=texto.split('\n')
			for (var i=0; i<trozos.length;i++) {
				if (i>0) lbl.appendChild( creaObjProp('br') )
				lbl.appendChild( creaT(trozos[i]) )
				}
			d.appendChild( lbl )
			}
		else if ( texto.startsWith('&') ){ // para mostrar las teclas de acceso directo
			var letra=texto.substring(1,2)
			var resto=texto.substring(2)
			
			var subrayado=creaObj('u')
			subrayado.appendChild( creaT(letra) )
			d.appendChild( subrayado )
			d.appendChild( creaT(resto) )
			}
		else{
			if(texto.indexOf('\n')==-1)
				d.appendChild( creaT(texto) )
			else{
				var tramos=texto.split("\n")
				while(tramos.length){
					var tramo=tramos.shift()
					d.appendChild( creaObj('P',null,null,tramo) )
					}
				}
			}
		}
	if (id!=null) d.id=id
	return d
}
function creaObjProp(tipo, dicPropiedades){
	var subtipo
	if (tipo.contains(':')){
		temp=tipo.split(':')
		tipo=temp[0];subtipo=temp[1]
		}
	var obj=document.createElement(tipo)
	if (subtipo) obj.type=subtipo
	
	modObjProp(obj, dicPropiedades)
	return obj
}
function modObjProp(obj, dicPropiedades){
	for (var prop in dicPropiedades){
		valor=dicPropiedades[prop]
		if (valor==null)
			continue
		try {
			if (prop=='textos') {//separados por \n
				var trozos=valor.split('\n')
				for (var i=0; i<trozos.length; i++){
					var trozo=trozos[i]
					obj.appendChild( creaT(trozo) )
					if (i<trozos.length-1) obj.appendChild(creaObj('BR'))
					}
				}
			else if (prop=='texto' )
				obj.appendChild(creaT(valor))
			else if (prop=='hijos'){
				for (var i=0; i<valor.length; i++){
					var trozo=valor[i]
					obj.appendChild( trozo )
					}
				}
			else if (prop=='hijo')
				obj.appendChild(valor)
			else if (prop.startsWith('style.') ) {//error setting a property that has only a getter
				prop=prop.substring(6)
				if (['left', 'top', 'width', 'height'].contains(prop)){
					valor=valor.toString()
					if (!valor.endsWith('px') && !valor.endsWith('%'))
						valor+='px'
					}
				obj.style[prop]=valor
				}
			else if (prop=='quitaEstilo')
				quitaEstilo(obj, valor)
			else
				obj[prop]=valor
			}
		catch (e) {
			console.error('ERROR '+e.message + '\nen '+e.fileName+' linea '+e.lineNumber)
			}
		}
}
function modListaObjProp(listaObjetos, dicPropiedades){
	for (var idobj=0; idobj<listaObjetos.length; idobj++){
		var obj=listaObjetos[idobj]
		if (obj && typeof obj == 'object') 
			modObjProp(obj, dicPropiedades)
		}
}
function creaImg(src, alt, clase, titulo, id){
	return creaObjProp('img', {src:src, className:clase, title:titulo, alt: (alt && exp && !exp.internetExplorer)?alt:null, id:id})
    }
function creaTexto(t){
    return ponVinculo(''+t)
    }
function creaCheck(nombre, valor) {
	return creaObjProp('input',{	'type':'checkbox', 
						id:nombre, 
						className:'lvw_chk', 
						name:nombre, 
						value:valor, 
						onclick:function(){this.checked=!this.checked} } )
	}
function creaTextoConEnlaces(cont, txt){
	if (txt==null)
		return
	var trozos=separaURL(txt)
	
	for (var i=0; i<trozos.length; i++){
		var trozo=trozos[i]
		
		var xdom
		var enlace=validateEmail(trozo)||encuentraPosPrimerEnlace(trozo)
		if (enlace!=null)
			xdom=ponVinculo(trozo)
		else
			xdom=creaT(trozo)
		cont.appendChild(xdom)
		}
}
function encuentraPosPrimerEnlace(txt){
	var buscamos=['http://', 'https://', 'ftp://', 'gotta://', 'mailto:']
	
	for (var i=0; i<buscamos.length; i++){
		var b=buscamos[i]
		
		var pos=txt.indexOf(b)
		if (pos>-1)
			return pos
		}
	return null
}
function buscaFinalEnlace(texto, inicio){
	var posfin=null
	var simbolosPosibles=':/\\-%#?&=~_.0123456789@'
	for (var i=inicio; i<texto.length-1; i++){
		//~ if (i>texto.length) break 
		var letra=texto.substring(i, i+1)
		if (letra.toUpperCase()!=letra.toLowerCase()){
			//pass
			}
		else if (simbolosPosibles.contains(letra)){
			//pass
			}
		else
			return i
		}
	
	return texto.length
}
function validateEmail(elementValue){      
   var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/
   return emailPattern.test(elementValue)
 }
function separaURL(txt){// a ver si aprendo expresiones regulares de una vez
	var ret=[]
	
	var pos=encuentraPosPrimerEnlace(txt)
	var colapsar=(pos==0)
		
	while (pos!=null){
		if (pos>0)
			ret.push(txt.substring(0, pos))
		var finalEnlace=buscaFinalEnlace(txt,pos+1)
		var trozo=txt.substring(pos, finalEnlace)
		ret.push(trozo)
		txt=txt.substring(finalEnlace)
		
		//buscamos el siguiente espacio
		pos=encuentraPosPrimerEnlace(txt)
		}
	if (txt!='') ret.push(txt)
	if (colapsar && ret[1]!=null){
		ret[0]=ret[0]+ret[1]
		ret[1]=''
		}
	
	return ret

}
function ponVinculo(txt){
	var espacio=' ', pipe='|'
	var href
	
	txt=txt==null?'':txt
	var posArroba=txt.indexOf('@')
	if (posArroba>-1 && posArroba<txt.lastIndexOf('.') && txt.indexOf(' ')==-1){
		return creaObjProp('A', {className:'email', href:"mailto:"+txt, 'texto':txt})
		}
	else if ( txt.startsWith('gotta://') ){ //URL interna
		var href=txt, alt=null
		
		var temp=separaURL_ALT_Texto(txt)
		href=temp[0]; alt=temp[1]; txt=temp[2]
		
		return creaObjProp('A', {className:'web'+(href.contains('flotante')?' flotante':''), texto:txt, href:href, onclick:fnNavegarA(href.substring(8)), alt:alt})
		}
	else if (txt.startsWith('http://') || txt.startsWith('https://') ){
		var href=txt, alt=null
		
		var temp=separaURL_ALT_Texto(txt)
		href=temp[0]; alt=temp[1]; txt=temp[2]
		
		return creaObjProp('A', {className:'web', href:href, target: esUrlExterna(href)?'_blank':null, texto:txt, alt:alt})
		}
	return creaT(txt)
}
function separaURL_ALT_Texto(txt){
	var alt, href, xsep
	if (txt.contains(pipe))
		xsep=pipe
	else if (txt.contains(espacio))
		xsep=espacio
	
	var temp=txt.split(xsep)
	href=temp[0]
	if (temp.length==3){
		alt=temp[1]
		txt=temp[2]
		}
	else if (temp.length==2){
		txt=temp[1]
		}
	else  {
		temp.splice(0,1)
		txt=temp.join(espacio)
		}
	return [href, alt, txt]
	}
function esUrlExterna(href){
	var host=this.location.protocol+'//'+this.location.host
	if (href.substring(0,host.length)==host || href.startsWith('?') || href.startsWith('./'))
		return false
	return true
}
function creaA(href, titulo, onClick, clase, target, cd, md){	
	var xhref=href
	if (href==null){
		xhref='?aplicacion='+exp.aplicacion+'&vista='+exp.idMlActivo
		if (md!=null) xhref+= '&detalle='+md
		if (cd!=null) xhref+= '&nodo='+cd
		}
	
	return creaObjProp('a', {className:clase, href:xhref, target:target, onclick:onClick, title:titulo})
	}
function ponThrobber(enlacePadre) {
	if (enlacePadre==null) alert('fixMe')
	try{
		var fila=control(enlacePadre); //fila.celda.Imagen
		var objeto=fila.firstChild
		while (objeto.tagName!='IMG')
			objeto=objeto.firstChild
		objeto.src=exp.icoGotta+'throbber.gif'
		}
	catch (e) 
		{}
}
////////////////////////////////////////////////////////////////////
function Arbol(contenedor){
	this.busquedaEnProceso=[]
	this.oFila=null
	this.contenedor=contenedor
	this.onClickNodo=null
	
	this.eventos=false
}
Arbol.prototype.ponEventosTeclado=function(){
	if (this.eventosTeclado)
		return
	this.eventosTeclado=true
			
	var self=this
	jwerty.key(teclasDireccion+'/home/end', function(event){self.onkeydown(event.keyCode, event.ctrlKey, event.altKey); return false}, null, '#'+this.contenedor)
	}
Arbol.prototype.onkeydown=function(tecla, ctrlKey, altKey){
	if ([btnAbajo, btnArriba].contains(tecla)) {
		this.cacheFilas=this.cacheFilas || control(this.contenedor).getElementsByTagName('TR')
	
		if (this.oFila.parentNode==null)//el nodo ya no está
			this.oFila=$('#'+this.contenedor+' td.contenidoS')[0]
		var iActivo=null
		for (var i=0; i<this.cacheFilas.length; i++){
			var tr=this.cacheFilas[i]
			if (tr && tr.id==this.oFila.parentNode.id) {
				iActivo=i
				break
				}
			}
		/////////////////////
		if (tecla==btnAbajo) {
			for (var i=iActivo+1; i<this.cacheFilas.length; i++){
				var tr=this.cacheFilas[i]
				if (tr && tr.id != null && tr.id != ''){
					this.retardoSeleccionar(tr.lastChild, true)
					break
					}
				}
			}
		else if (tecla==btnArriba){
			for (var i=iActivo-1; i>=0; i--){
				var tr=this.cacheFilas[i]
				if (tr && tr.id != null && tr.id != ''){
					this.retardoSeleccionar(tr.lastChild, true)
					break
					}
				}
			}
		}
	else if (!altKey &&  (tecla==btnDer || tecla==btnIzq)){ //expandimos
		var tdMasMenos=this.oFila.previousSibling //la imagen del mas/menos
		var img=tdMasMenos.firstChild
		
		if (img!=null){ //estamos en el nodo que hay que cerrar					
			if (img.alt=='+' && tecla==btnIzq) //está cerrado, cerraremos su padre
				img=null
			else if (img.alt=='-' && tecla==btnDer) //está abierto, no hacemos nada
				return
			}
			
		if (img==null){ //estamos en el hijo de una rama que queremos cerrar
			if (this.oFila.offsetParent==null)
				this.oFila=$(divarbol).find('td.contenidoS')
			
			var xtr=$(this.oFila).parents('table.arbol:eq(0)').parents('tr:eq(0)').prev()
			tdMasMenos=xtr.find('td.masMenos:first-child')[0]
			
			img=tdMasMenos.firstChild
			
			xtr.find('a.contenido').click()
			return
			}
		
		if (img==null){
			//estamos en un nodo que no tiene hijos ni padres
			}
		else if (img.alt=='+' && tecla==btnDer)
			tdMasMenos.onclick()
		else if (img.alt=='-' && tecla==btnIzq)
			tdMasMenos.onclick()
		}
	else if (tecla==btnHome || tecla==btnEnd){
		var trs=$('#'+this.contenedor+' > table.arbol tr.arbol')
		var tr=trs [tecla==btnHome?0:trs.length-1]
		this.retardoSeleccionar(tr.lastChild, true)
		}
	control(this.contenedor).focus()
}
Arbol.prototype.hazScroll=function (obj) {
	if (!this.nodoEstaVisible(obj)){
		$('#'+this.contenedor).scrollTo(obj)
		}
		
	$(obj).find('a.contenido').focus()
		
	if (exp.internetExplorer) 
		exp.colocaArbol()
	}
Arbol.prototype.nodoEstaVisible=function(obj){
	var xnodo=$(obj)
	var xcont=$( '#'+this.contenedor )
	
	var posNodo=xnodo.position().top
	if (posNodo<0 || posNodo>xcont.innerHeight())
		return false
	return true
	}
var pendienteArbol
Arbol.prototype.retardoSeleccionar=function(celda, forzarVisible){
	clearTimeout(pendienteArbol)
	
	if (this.oFila!=null)
		quitaEstilo(this.oFila,'contenidoS')
	$('#'+this.contenedor+' td.contenidoS').removeClass('contenidoS')
	
	if (celda!=null) {
		this.oFila=celda
		ponEstilo(this.oFila, 'contenidoS')
		
		var fila=celda.parentNode
		var url=fila.getAttribute('cd')
		var md=fila.getAttribute('md')
		
		//hacemos click en el enlace
		if (forzarVisible)
			this.hazScroll(celda)

		if (url != null && md!=null && this.onClickNodo!=null) {
			var xthis=this
			pendienteArbol=setTimeout(function(){xthis.onClickNodo(url, md)}, 500)
			}
		}	
}
Arbol.prototype.seleccionar=function(celda, forzarVisible, url, md){
	if (this.oFila!=null)
		quitaEstilo(this.oFila,'contenidoS')
	if (celda!=null) {
		ponEstilo(celda,'contenidoS')
		this.oFila=celda
		
		var fila=celda.parentNode
		url=url  || fila.getAttribute('cd')
		md=md || fila.getAttribute('md')
		
		//hacemos click en el enlace
		if (forzarVisible)
			this.hazScroll(celda)

		if (url != null && md!=null && this.onClickNodo!=null)
			this.onClickNodo(url, md)
		}
	}
Arbol.prototype.fnSeleccionar=function(ctl){
	return function()
		{ctl.seleccionar(this.parentNode, false);return false;}
	}
Arbol.prototype.cargar=function (vista, cuantos) {
	this._cargar(vista, cuantos, this)
}
Arbol.prototype.ponThrobber=function(){
	ponEstilo(control(this.contenedor), 'cargando')
}
Arbol.prototype.quitaThrobber=function(){
	quitaEstilo(control(this.contenedor), 'cargando')
}
Arbol.prototype._cargar=function (vista, cuantos, ctl) {
	borraHijos( control( ctl.contenedor ) )
	ctl.ponThrobber()
	
	exp.arbol.busquedaEnProceso=[vista]
	
	var param={ 'accion':'arbol', 'vista': vista, 'cuantos': cuantos, 'aplicacion': exp.aplicacion } 
	if (this.md){
		param['md']=this.md.nombreDetalle
		param['nodo']=this.md.nodoActivo
		}
	loadJSONDoc( 	'json',
				param,
				function(req){
					borraHijos( control( ctl.contenedor ) )
					ctl.monta( xeval(req) )
					exp.arbol.busquedaEnProceso.remove(vista)
					
					if (exp.arbol.busquedaEnProceso.length==0){
						var hayNodo=$('#'+ctl.contenedor+' td.contenidoS').length>0
						if (!hayNodo){
							var primerNodo=$('#'+ctl.contenedor+' td.contenido a.contenido')[0]
							if (primerNodo) 
								primerNodo.onclick()
							}
						}
					}
				)
	}
Arbol.prototype.montaObjColumnas=function(arr){
	if (!arr) return null
	
	var cols={	cd:null, ds:null, ico:null, key:null}
	for (var i=0; i<arr.length; i++){
		cols[ arr[i] ]=i
		}
	cols.cd=arr.indexOfIgnoreCase('cd')
	cols.ds=arr.indexOfIgnoreCase('ds')
	cols.md=arr.indexOfIgnoreCase('mododetalle')
	cols.mds=arr.indexOfIgnoreCase('mododetallesiguiente')
	cols.ico=arr.indexOfIgnoreCase('icono')
	
	delete cols.icono
	delete cols.mododetalle
	delete cols.mododetallesiguiente
		
	for (var i in cols){
		if (cols[i]==-1)
			cols[i]=null
		}
	
	return cols
	}
Arbol.prototype.generaCDfila=function (cols, nodo, nodoPadre){
	var ret
	if (nodo[cols.cd]=='maxElementos')
		ret='maxElementos'+nodo.padre
	else
		ret='fila_'+nodo[cols.cd]
	return this.arreglaCDfila(ret)
}
Arbol.prototype.arreglaCDfila=function(cd){
	var ret=cd
	ret=ret.replaceAll('\\', '_B_')
	ret=ret.replaceAll('.', '_P_')
	return ret
	}
Arbol.prototype.cambiaTabla=function (padre){
	borraHijos(padre)
	
	var t=document.createElement('table')
	t.className='arbol'
	padre.appendChild(t)
	return t
}
Arbol.prototype.monta=function(datos){
	var xcont=this.contenedor
	if (this.md) {
		var temp=this.md.getControl(this.contenedor)
		xcont=temp? temp.id : this.contenedor
		}
	var t=this.cambiaTabla( control( xcont ) )
	this.generaTabla(t, datos, exp.arbol && exp.arbol.busquedaEnProceso.length==0)
	this.ponEventosTeclado()
}
Arbol.prototype.usarMaxElementos=function (fila, enlacePadre, cuantos){
	return this._usarMaxElementos(fila, enlacePadre, cuantos, this)
}
Arbol.prototype._usarMaxElementos=function (fila, enlacePadre, cuantos, ctl){
        var tabla=fila.parentNode
        ponEstilo(fila, 'cargando')
	
	//~ exp.arbol.busquedaEnProceso.push(enlacePadre)
	loadJSONDoc( 	'json',
				{'accion':'expandir','nodo': enlacePadre, 'cuantos': cuantos, aplicacion: exp.aplicacion},
				function(req){
					//~ exp.arbol.busquedaEnProceso.remove(enlacePadre)
					tabla.removeChild(fila)
					ctl.generaTabla( tabla, xeval(req) )
					}
		    )
}
Arbol.prototype.expandir=function(enlacePadre, cd, activarNodo){
	this._expandir(enlacePadre, cd, this, activarNodo)
}
Arbol.prototype._expandir=function(enlacePadre, cd, ctl, activarNodo){
	var throbber=creaImg(exp.icoGotta+'throbber.gif', "...", 'throbber', 'Cargando...')
	enlacePadre.appendChild( throbber )
	
	exp.arbol.busquedaEnProceso.push(enlacePadre)
	loadJSONDoc ( 	'json',
				{ 'accion':'expandir','expandir':true,'nodo':cd,aplicacion:exp.aplicacion},
				function(req){
						var tabla=ctl.cambiaTabla( enlacePadre )
						ctl.generaTabla( tabla, xeval(req), activarNodo && exp.arbol.busquedaEnProceso.length==0 )
						exp.arbol.busquedaEnProceso.remove(enlacePadre)
						}
                )
}
Arbol.prototype.contraer=function(subarbol, cd){
	borraHijos(subarbol)
	loadJSONDoc ( 	'json',
				{ 'accion':'expandir',  'expandir':false,  'nodo':cd, aplicacion: exp.aplicacion},
                                null )
}
Arbol.prototype.onClickExpandirContraer=function(mas,fila2){
	return this._onClickExpandirContraer(mas, fila2, this)
}
Arbol.prototype._onClickExpandirContraer=function(mas, fila2, ctl){
    return function(){
		var subarbol=fila2.cells[1]
		if (mas.alt=='+'){
			mas.src="./fijo/menos"+mas.fin+".png"
			mas.alt='-'
			ctl.expandir(subarbol,mas.cd)
			fila2.style.display=''
			return
			}
		else if (mas.alt=='-'){
			mas.src="./fijo/mas"+mas.fin+".png"
			mas.alt='+'
			fila2.style.display='none'

			ctl.contraer(subarbol,mas.cd)
			return
			}
        alert(mas.alt+'!!')
	}
}
Arbol.prototype.creaCheck=function(cols, nodo){
return null
}
Arbol.prototype.generaTabla=function(t, datos, marcarPrimeroActivo){
	return this._generaTabla(t, datos, marcarPrimeroActivo, this)
}
Arbol.prototype._generaTabla=function(t, datos, marcarPrimeroActivo, ctl){
	if (!datos.datos) 
		return

	var cols=this.montaObjColumnas(datos.datos.columnas)	
	if (datos.datos.filas.length==0)
		datos.datos.filas.push(this.generaNodoSinElementos(datos, cols))
	
	this.cacheFilas=null
	ctl.quitaThrobber()
	
	for (var i=0;i<datos.datos.filas.length;i++){
		var datosfila=datos.datos.filas[i]
		
		var fila=t.insertRow(-1)
		fila.id= this.generaCDfila(cols, datosfila, datos.nodoPadre)
		fila.className='arbol'
		
		// La celda Mas/menos
		var celda=fila.insertCell(-1)
		celda.className='masMenos'
			
		var nofin=i<datos.datos.filas.length-1

		if (datosfila.cd=="maxElementos") {
			var td = fila.insertCell(-1)
			td.className='contenido'
			
			if (nofin)
				celda.style.backgroundImage="url(./fijo/medio1.png)"
			else
				celda.style.backgroundImage="url(./fijo/fin1.png)"

			js1=function(){ctl.usarMaxElementos(fila, datosfila.padre, datosfila.cuantos)}
			js2=function(){ctl.usarMaxElementos(fila, datosfila.padre, -1)}

			var a1=creaA("#", "", js1, 'maxElementos', null)
			var a2=creaA("#", "", js2, 'maxElementos todos', null)

			a1.appendChild(creaT("Cargar otros "+datosfila.maxElementos))
			a2.appendChild(creaT("Cargar todos"))

			td.appendChild( a1 )
			td.appendChild( a2 )
			}
		else {
			if (datosfila[cols._expandido]=='X'){
				if (nofin)
					celda.style.backgroundImage="url(./fijo/medio1.png)"
				else
					celda.style.backgroundImage="url(./fijo/fin1.png)"
				}
			else {
				var mas=document.createElement('img')
				celda.appendChild(mas)
				var fila2=t.insertRow(-1)
				fila2.style.display='none'
				mas.cd=datosfila[cols.cd]
				celda.onclick=this.onClickExpandirContraer(mas,fila2)
				
				//el andamio
				celda=fila2.insertCell(-1)
				celda.className='andamio'

				if(nofin){
					celda.style.backgroundImage="url(./fijo/medio0.png)"
					mas.fin='1'
					}
				else
				    mas.fin='fin'
				
				//subarbol
				celda=fila2.insertCell(-1)
				if (!datosfila[cols._expandido]){
					mas.alt='+'
					mas.src="./fijo/mas"+mas.fin+".png"
					}
				else {
					mas.alt='-'
					mas.src="./fijo/menos"+mas.fin+".png"
					fila2.style.display=''
					this.expandir(celda, datosfila[cols.cd], true)
					}
				}
			
			//La descripcion
			celda=fila.insertCell(-1)
			celda.className='contenido'
			
			/////////////////
			var titulo
			if (cols._title!=null && datosfila[cols._title])
				titulo=datosfila[cols._title]
			else
				titulo=datosfila[cols.ds]
			
			var xcd
			if (cols.esError)
				xcd=datos.nodoPadre
			else
				xcd=datosfila[cols.cd]
				
			var enlaceDer=creaA(	null,
								titulo, 
								this.fnSeleccionar(this), 
								'contenido', 
								null,
								xcd, datosfila[cols.md])
			enlaceDer.tabIndex=-1
			if (cols._css_!=null && datosfila[cols._css_])
				ponEstilo(enlaceDer, datosfila[cols._css_])
			
			fila.setAttribute('cd', xcd)
			fila.setAttribute('md', datosfila[cols.md])
			
			//////////////////
			if (cols.ico!= null && datosfila[cols.ico] != null && datosfila[cols.ico] != '') {
				var ico=creaImg(completaRutaImg(datosfila[cols.ico], false), "("+datosfila[cols.ico]+")", "icono")
				enlaceDer.appendChild( ico )
				}
			var texto=creaTexto(' '+datosfila[cols.ds])
			enlaceDer.appendChild( texto )
			
			/////////////////
			for (var key in datos.datos.columnas){
				if (key.startsWith('_ds')){
					var xv=datosfila[cols[key]]
					if (xv && xv!=''){
						var span=creaObjProp('span', {'className':key,'texto':xv})
						enlaceDer.appendChild(span)
						}
					}
				}		
			if (ctl.onDblClickNodo!=null)
				enlaceDer.ondblclick=this.fnOnDblClickNodo(ctl, datosfila[cols.cd]) // function(){ctl.onDblClickNodo(datosfila[cols.cd])}
			
			var check=this.creaCheck(cols, datosfila)
			celda.appendChild(enlaceDer)
			if (check)
				celda.appendChild( check )
				
			if ((datos.nodoActivo==datosfila[cols.cd])||(marcarPrimeroActivo && i==0 && datos.nodoActivo==null) || (exp.filasSeleccionadas && exp.filasSeleccionadas.contains(fila[cols.cd]))){
				var ponerFoco=(i!=0) //para que al movernos con el teclado del árbol principal no le meta el foco al árbol del detalle
				ctl.seleccionar(celda, ponerFoco, datosfila[cols.cd], datosfila[cols.md])
				}
			}
		}
}
Arbol.prototype.generaNodoSinElementos=function(datos, cols){
	var ret=[]
	for (var k in cols){
		var pos=cols[k]
		k=k.toLowerCase()
		
		if (pos==null) 
			continue
		
		var valor
		if (k=='cd')
			valor=datos.nodoPadre+'\\sinElementos'
		else if (k=='ds')
			valor='Sin elementos'
		else if (k=='md')
			valor=datos.nivel
		else if (k=='_expandido')
			valor='X'
		
		else if (k=='mds')
			valor=null
		else if (k=='ico'|| k=='icono')
			valor=null
		ret[pos]=valor
		}
	return ret
	}
Arbol.prototype.fnOnDblClickNodo=function(ctl, cd){
	return function(){ctl.onDblClickNodo(cd)}
}
///////////////////////////////////////////
function ArbolComoLista(contenedor){ //para mostrar el árbol 
	Lista.call(this, contenedor)
	this.cont= control( this.contenedor )
	this.sortable=true
	this.oFila=null
}
ArbolComoLista.prototype= new Lista
ArbolComoLista.prototype.fnOnClick=function(fila, columnasOcultas){
	return function() {
		seleccionarFila(this, false)
		exp.arbol.oFila=this
		exp.cambioNodoActivo(fila[columnasOcultas.key], fila[columnasOcultas.mododetalle])
		}
}
ArbolComoLista.prototype.retardoSeleccionar=function(celda, forzarVisible){
	clearTimeout(pendienteArbol)
	
	var tr=celda.parentNode
	
	seleccionarFila(tr, false)
	exp.arbol.oFila=tr
	if (forzarVisible) tr.scrollIntoView(false)
	
	var url=tr.getAttribute('cd')
	var md=tr.getAttribute('md')
	
	var xthis=this
	pendienteArbol=setTimeout(function(){exp.cambioNodoActivo(url, md)}, 500)
	return false
}
ArbolComoLista.prototype.fnOnClickB=function(tr, fila, columnasOcultas){
	return function() {
		seleccionarFila(tr, false)
		exp.cambioNodoActivo(fila[columnasOcultas.key], fila[columnasOcultas.mododetalle])
		exp.arbol.oFila=tr
		tr.scrollIntoView(false)
		}
}
ArbolComoLista.prototype.arregla=function(){
	this.cont=control(this.contenedor)
	}
ArbolComoLista.prototype.cargar=function(vista, cuantos){ 
	this._cargar(this, vista, cuantos)
}
ArbolComoLista.prototype._cargar=function(ctl, vista, cuantos){ //calcado de Arbol.prototype._cargar
	borraHijos( ctl.cont )
	ctl.ponThrobber()
	
	loadJSONDoc( 	'json',
				{ 'accion':'arbol', 'vista': vista, 'cuantos': cuantos, aplicacion: exp.aplicacion } ,
				function(req){
					borraHijos( ctl.cont )
					ctl._arreglaParaGeneraTabla( xeval(req) ) 
					}
				)
}
ArbolComoLista.prototype.onkeydown=function(tecla, ctrlKey, altKey){
	if ([btnAbajo, btnArriba].contains(tecla)) {
		this.cacheFilas=this.tbody.getElementsByTagName('TR')
	
		var iActivo=null
		for (var i=0; i<this.cacheFilas.length; i++){
			var tr=this.cacheFilas[i]
			if (tr && tr.id==this.oFila.id) {
				iActivo=i
				break
				}
			}
		/////////////////////
		if (tecla==btnAbajo) {
			for (var i=iActivo+1; i<this.cacheFilas.length; i++){
				var tr=this.cacheFilas[i]
				if (tr && tr.id != null && tr.id != ''){
					this.retardoSeleccionar(tr.lastChild, true)
					break
					}
				}
			}
		else if (tecla==btnArriba){
			for (var i=iActivo-1; i>=0; i--){
				var tr=this.cacheFilas[i]
				if (tr && tr.id != null && tr.id != ''){
					this.retardoSeleccionar(tr.lastChild, true)
					break
					}
				}
			}
		}
}
ArbolComoLista.prototype.generaKey=function(){
	return 'arbol'
}
ArbolComoLista.prototype.generaCDfila=function(nodo, i, columnasOcultas){
	//~ return '_'+this.numControl+'_'+iterador
	if (nodo[columnasOcultas.cd]=='maxElementos')
		return 'maxElementos'+nodo.padre
	else if (columnasOcultas.ds && nodo[columnasOcultas.ds].toLowerCase()=='sin elementos' && nodo[columnasOcultas.key]=='\\')
		return nodo[columnasOcultas.padre]+nodo[columnasOcultas.cd]
	else
		return nodo[columnasOcultas.key]
}
//////////////////////////////////////////
function ArbolModoDetalle(numControl, nivel){
	Arbol.call(this, 'control'+numControl) 
	this.numControl=numControl
	this.nivel=nivel
}
ArbolModoDetalle.prototype= new Arbol
ArbolModoDetalle.prototype.creaCheck=function(cols, nodo){
	return creaCheck( 'checkbox_'+this.generaCDfila(cols, nodo), nodo.cd)
}
ArbolModoDetalle.prototype.cargar=function(cuantos){
	this._cargar(this.nivel, cuantos, this)
}
ArbolModoDetalle.prototype._cargar=function(nivel, cuantos, ctl){
	borraHijos( ctl.cont )
	
	var param={ 'accion':'arb', 'nivel': nivel, 'cuantos': cuantos, 'aplicacion': exp.aplicacion }
	if (this.md){
		param['md']=this.md.nombreDetalle
		param['nodo']=this.md.nodoActivo
		}
	if (this.nodoFicticio){//para los especiales de gotta: selector de columnas, editor de menú...
		param['nodoFicticio']=this.nodoFicticio
		}
	
	loadJSONDoc( 	'json', param , function(req){
						ctl.monta( xeval(req) )
						} )
}
ArbolModoDetalle.prototype.seleccionar=function(fila, forzarVisible, url, md){
	if (this.oFila!=null) {
		quitaEstilo(this.oFila, 'contenidoS')
		chequearBox(this.oFila.parentNode, false)
		}
	if (fila!=null) {
		this.oFila=fila
		ponEstilo(this.oFila, 'contenidoS')
		var tr=fila.parentNode
		chequearBox(tr, true)

		if (forzarVisible)
			this.hazScroll(fila)
		if (this.onClickNodo)
			this.onClickNodo(tr.id.substring(5) /*sólo el cd*/)
		}
}
/////////////////////////////////////////
function Lista(contenedor, sortable){
	this.contenedor=contenedor
	
	if (contenedor) this.arregla()
	this.sortable=sortable || false
	this.throbber=null
	
	this.divMaxElementos=null
	this.tfoot=null
	this.thead=null
	this.tbody=null
	this.t=null
}
Lista.prototype.ponThrobber=function(){
	this.arregla()
	
	//~ this.throbber=creaImg('./fijo/throbber.gif', "...", 'throbber', 'Cargando...')
	//~ this.cont.appendChild(this.throbber)
	ponEstilo(this.cont, 'cargando')
}
Lista.prototype.quitaThrobber=function(){
	if (this.cont) quitaEstilo(this.cont, 'cargando')
}
Lista.prototype.pintaContenedor=function(conThrobber) {
	//pinta el div con el nombre indicado y clase lvw
	this.cont=creaObjProp('div', {className:'lvw', id:this.contenedor})
	if (conThrobber) this.ponThrobber()
	return this.cont
}
Lista.prototype.inicializa=function(){
	this.eventos=false
	
	this.cont=null
	this.contDatos=null
	//~ this.paginacion1=null
	//~ this.paginacion2=null
	this.t=null
	this.tbody=null
	this.thead=null
	this.colGroup=null
	this.colGroupEspeciales=null
	
	this.arregla()
	}
Lista.prototype.montaTabla=function(nombre) {
	this.arregla()
	
	if (this.contDatos!=null) {
		borraHijos(this.cont)
		
		this.inicializa()
		}
	
	if (this.tbody == null){
		this.t=creaObjProp('table', {className:'lvw', 'style.width':'100%', summary:this.xcontrol?this.xcontrol.summary:null})
		
		if (this.sortable)
			ponEstilo(this.t, 'sortable')

		this.colGroupEspeciales=creaObjProp('colGroup'); this.colGroup=creaObjProp('colGroup'); this.thead=creaObjProp('thead'); this.tbody=creaObjProp('tbody')
		this.t.appendChild(this.colGroupEspeciales); this.t.appendChild(this.colGroup); this.t.appendChild(this.thead); this.t.appendChild(this.tbody)
		
		var altura=null
		var alturaPaginacion=(this.mostrarPaginacion?60:0)
		if (!this.cont)
			return
		else if (this instanceof LVW) {
			if (this.cont.style.height)// está vacío en impresión
				altura=quitaPx(this.cont.style.height)-alturaPaginacion
			}
		else 	{//listas 'de sistema': diccionario y demás, donde la altura se suele establecer por css
			if (this.cont.childNodes.length==0)
				altura=$(this.cont).outerHeight(false)-alturaPaginacion
			if (altura==0)
				altura=null
			}
		
		this.contDatos=creaObjProp('div', {className:'datos', hijo:this.t,'style.height':altura, 'style.width':'100%'})
		this.paginacion1=this.paginacion1 || creaObjProp('div', {className:'paginacion p1', 'style.display':'none'})
		this.paginacion2=this.paginacion2 || creaObjProp('div', {className:'paginacion p2', 'style.display':'none'})
		
		if (this instanceof LVW) {
			this.cont.appendChild(this.paginacion1)
			this.cont.appendChild(this.contDatos)
			this.cont.appendChild(this.paginacion2)
			}
		else
			this.cont.appendChild(this.contDatos)	
		}
	else
		borraHijos(this.tbody)
}
Lista.prototype.creaCelda=function(tipo, clase, texto) {
	var thd= document.createElement( tipo )
	if (clase) thd.className=clase
	if (texto) thd.appendChild( texto )

	return thd
	}
Lista.prototype._creaCeldaMozilla=function(fila, clase, texto, colorFondo) {
	var thd= fila.insertCell(-1)
	if (colorFondo || clase)
		thd.className=colorFondo || clase
	if (texto) thd.appendChild( texto )

	return thd
	}
Lista.prototype._creaCeldaInternetExplorer=function(fila, clase, texto, colorFondo) {
	var thd= document.createElement('td')
	if (colorFondo || clase)
		thd.className=colorFondo || clase
	if (texto) thd.appendChild( texto )
	fila.appendChild( thd )
	
	return thd
	}
Lista.prototype.creaCelda2=esInternetExplorer() ? Lista.prototype._creaCeldaInternetExplorer : Lista.prototype._creaCeldaMozilla
Lista.prototype.getFilasPinchadas=function(){
	var l=this.tbody.getElementsByTagName('input')
	var ret=[]
	for (var i=0; i<l.length;i++){
		if (l[i].checked) 
			ret.push( l[i].id )
		}
	return ret
}
Lista.prototype.rellenaLista=function(literales, cols, datos, columnaID, onClick, onDblClick, multiSelect, onContextMenu, columnaImagen, tiposDatos, columnaCSS){
	multiSelect = multiSelect || false
	
	this.montaTabla(this.contenedor)
	this.t.style.display='none' //más rápido?

	var tr_head=xCreaObj('tr', 'lvw')
	tr_head.appendChild( xCreaObj('th', 'especial', creaT(espacioDuro4) ) )
	
	var i=0
	this.colGroupEspeciales.appendChild(creaObjProp('col', {className:'lvw', 'id':'col'+i} )) //icono
	for (var c in literales) {
		if (typeof literales[c]!='function'){
			var nombreColumna=literales[i]
			if (nombreColumna.contains('\\')){
				var idx=nombreColumna.indexOf('\\')
				var modif=nombreColumna.substring(idx)
				nombreColumna=nombreColumna.substring(0, idx)
				}
			tr_head.appendChild( xCreaObj('th', null, creaT( nombreColumna ) ) )
			
			var tipo=(tiposDatos && tiposDatos[c] ? tiposDatos[c] : 'lvw' )
			if (esTipoNumerico(tipo) && !tieneTipo(tipo, 'lvwNumero'))
				tipo+=' lvwNumero'
				
			this.colGroup.appendChild(creaObjProp('col', {className:tipo!='lvw'?tipo:null, 'id':'col'+i} ))
			i++
			}
		}
		
	this.thead.appendChild(tr_head)

	for (var i=0; i<datos.length; i++) {
		var dato=datos[i]
		if (dato != null && typeof dato!='function' && !dato.esBorrado){
			var tr=this.generaFila(dato)
			
			if (columnaCSS) 
				ponEstilo(tr, dato[columnaCSS])
			
			var datoID='_'+i
			if (columnaID!=null) datoID=dato[columnaID]
			tr.id=datoID
				
			tr.onclick=function(event) {	
								event=event || window.event
								seleccionarFila(this, multiSelect && event.ctrlKey, null, multiSelect && event.shiftKey)
								if (onClick!=null) 
									onClick(this)
								}
			if (onDblClick!=null) {
					tr.ondblclick=function(event) {seleccionarFila(this, false)
										onDblClick(this)
										}
							}
			if (onContextMenu!=null){
				var fn=function(event){
					seleccionarFila(this, event.ctrlKey, true)
					onContextMenu(this,event)
					return false
					}
				tr.oncontextmenu=fn
				$(tr).longclick(fn)
				}
			
			//la celda de la imagen y el check
			var td=xCreaObj('td', 'especial')	
			if (columnaImagen!=null && columnaImagen!=''){ 
				var ruta=completaRutaImg( dato[columnaImagen] , false)
				if (ruta) td.appendChild( creaImg( ruta ) )
				}
			
			var check=creaCheck( datoID)
			td.appendChild(check)
			tr.appendChild(td)
			
			for (var c in cols) {
				if (typeof literales[c]!='function') {
					var tipoDato
					if (tiposDatos!=null )
						tipoDato=tiposDatos[c]
					tipoDato=( (tiposDatos && tipoDato!='lvw')?tipoDato : 'lvw')
					
					var literal=literales[c]
					var columna=cols[c]
					var valorOriginal=null
					
					if (columna instanceof Array){
						var columnas=columna
						
						var td=xCreaObj('td',  tipoDato)
						for (var xx=0; xx<columnas.length; xx++){
							var columna=columnas[xx]
							
							var texto=(columna==null ? null : dato[ columna ] )
							if (texto!=null)
								td.appendChild(creaObjProp('span', {className:'el'+xx, texto:texto}))
							}
						}
					else	{
						var td=creaObjProp('td', {className:tipoDato})
						var texto=(columna==null ? '' : dato[ columna ] )
						
						if (texto==null)
							texto=''
						if (tipoDato=='lvwFecha' || esTipoNumerico(tipoDato) )
							valorOriginal=texto
						
						if (literal.contains('\\')){
							var modif=literal.split('\\')[1] //si hay varios modificadores, primero los de \0, \1, \M y luego los de \nt para no sacar totales
							texto=formatoSalida(texto, modif, td)
							}
						else if ( esTipoNumerico(tipoDato) ){
							texto=formatoSalida(texto, numDecimalesPortipo(tipoDato ), td)
							}
							
						if (texto!=null && texto!='')
							td.appendChild(creaT( texto ))
							
						if (valorOriginal) {
							var valorBruto=valorOriginal
							if ( tipoDato =='lvwFecha' )
								valorBruto=creaFechaJavascript(valorOriginal)
							td.setAttribute('valorOriginal', valorBruto)
							}
						}
					
					tr.appendChild(td)
					}
				}
			
			
			if (dato['_filaseleccionada_'] != null && dato['_filaseleccionada_']==true){
				seleccionarFila(tr, true)
				}
			}
		}
	if (this.sortable) this.hazSortable()
	if (exp) this.ponEventosTeclado()
	this.t.style.display='' //más rápido?
	this.quitaThrobber()
}
Lista.prototype.generaFila=function(fila, columnasOcultas){
	var tr=this.tbody.insertRow(-1)
	tr.onclick=this.fnOnClick(fila, columnasOcultas || [], true)
	tr.className='fila'
	return tr
	}
Lista.prototype.generaFilaIE=function(fila, columnasOcultas, numCols){
	var tr=document.createElement('TR')
	tr.onclick=this.fnOnClick(fila, columnasOcultas || [], true)
	tr.className='fila'
    this.tbody.appendChild(tr)
	return tr
	}
Lista.prototype.generaFila=esInternetExplorer() ? Lista.prototype.generaFilaIE : Lista.prototype.generaFila
Lista.prototype._rellenaLVW=function(idCargaPendiente, datosFilas, nombresColumnas, tipos, marcarPrimeroActivo, nodoActivo, ponerAtributos, datosLVW, llevaColumnaExpandir, primeraFila) {
	if (this instanceof LVW && !this.cargaPendiente.contains(idCargaPendiente)) 
		return /*la carga se ha cancelado, probablemente por pinchar en otro nodo*/ 
	
	primeraFila=primeraFila||0
	if (llevaColumnaExpandir==undefined)
		llevaColumnaExpandir=false
	else if (llevaColumnaExpandir) 
		llevaColumnaExpandir=nombresColumnas.containsIgnoreCase('ModoDetalleSiguiente') 
	
	this.nombresColumnasVisibles=[]//el null por la columna del icono y exportar a excel
	this.datosFilas=datosFilas
	
	this.arregla()
	if(primeraFila==0)
		borraHijos(this.contDatos)
	
	if (datosFilas.length==0 && msgLvwSinDatos!=''){
		var sinDatos=creaObjProp('div', {className:'sinDatos', texto:msgLvwSinDatos})
		this.cont.appendChild(sinDatos)
		this.quitaThrobber()
		return
		}
	this.cacheFilas=null
	var contenedor=this.contDatos
		
	var columnasOcultas={ 'ds' : null, 'key' : null, 'icono' : null, 'mododetalle' : null,'mododetallesiguiente' : null, 'cd' : null, 'modolista' : null, '@pag@' : null}
	if (primeraFila == 0) {
		this.montaTabla(this.numControl)
		this.ponEventosTeclado()
		}
	var t = this.tbody
	if (datosFilas==null) return

	if ( this.thead.childNodes.length==0) {//cabecera de la tabla
		var tr=creaObjProp('tr', {id:'cabecera'+ (this.numControl || this.contenedor)})
		
		var numColumnasEspeciales=0
		if (llevaColumnaExpandir && nombresColumnas.containsIgnoreCase('mododetallesiguiente')){
			numColumnasEspeciales++	
			this.nombresColumnasVisibles.push(null)
			}
		if (this.mostrarNumFila){
			numColumnasEspeciales++
			this.nombresColumnasVisibles.push(null)
			}
		//y la del icono
			numColumnasEspeciales++
			this.nombresColumnasVisibles.push(null)
		
		this.thExcel=this.creaCelda('th', 'especial')
		this.thExcel.colSpan=numColumnasEspeciales
		this.numColumnasEspeciales=numColumnasEspeciales
		tr.appendChild(this.thExcel)
		this.colGroupEspeciales.appendChild(creaObjProp('col', {span:numColumnasEspeciales}))
		
		for (var i=0; i<nombresColumnas.length; i++) {
			if (nombresColumnas[i] !=null) {
				var col=nombresColumnas[i].toLowerCase()
				if (col in columnasOcultas || (col.charAt(0)=='_' ))
					columnasOcultas[col]=i
				else	{
					var nombreColumna=nombresColumnas[i]
					var tipo=tipos[i]
					if (nombreColumna.contains('\\')){
						var idx=nombreColumna.indexOf('\\')
						var modif=nombreColumna.substring(idx)
						nombreColumna=nombreColumna.substring(0, idx)
						}
					if (esTipoNumerico(tipo) && !tieneTipo(tipo, 'lvwNumero'))
						tipo+=' lvwNumero'
					this.colGroup.appendChild(creaObjProp('col', {className:tipo!='lvw'?tipo:null, 'id':'col'+i} ))
					var xcelda=this.creaCelda('th', null, creaTexto( nombreColumna ))
					xcelda.id='th'+i
					tr.appendChild( xcelda ) 
					
					this.nombresColumnasVisibles.push(nombreColumna)
					}
				}
			}
		if (this.numControl) {
			var enlaceCSV=creaObjProp('a', {	id:'th0', tabIndex:-1, texto:espacioDuro4, 
									href:'exportar?aplicacion='+exp.aplicacion+'&lvw='+this.numControl+'&pagina='+this.numPagina+ (this.md?'&nodo='+this.md.nodoActivo+'&md='+this.md.nombreDetalle:''), 
									title:'Exportar a hoja de cálculo', className:'volcarExcel', target:'_blank'})
			this.thExcel.appendChild(enlaceCSV)
			ponEstilo(this.thExcel, 'volcarExcel')
			}
		this.thead.appendChild(tr)
		}
	else {
		for (var i=0; i<nombresColumnas.length; i++) {
			if (nombresColumnas[i] !=null) {
				var col=nombresColumnas[i].toLowerCase()
				if (col in columnasOcultas || col.charAt(0)=='_')
					columnasOcultas[col]=i
				else
					{/*pass*/}
				}
			}
		}

	var primeraFilaSeleccionada=null
	
	if (datosLVW.añadirCamposBúsqueda && datosLVW.añadirCamposBúsqueda!=null){// generamos una fila ficticia con los filtros
		var tr=this.tbody.insertRow(-1)
		tr.className='filaFiltros'
		
		if (this.mostrarNumFila) this.creaCelda2(tr, 'especial numFila', null)
		this.creaCelda2(tr, 'especial icono') //ponemos celda para el icono (esta va fija)
		
		var listaCols=datosLVW.añadirCamposBúsqueda
		for (var i=0; i<listaCols.length; i++) {
			var inp=creaObjProp('input', {className:'filaFiltros param' ,id:listaCols[i].n, value:listaCols[i].v})
			jwerty.key('enter', function(){filtrar(); return false}, null, inp)
			this.creaCelda2(tr, 'especial filtro', inp) 
			}
		}
	var crono=new Date().valueOf()+1000 // doEvents cada 1000 ms
	for (var j=primeraFila; j<datosFilas.length; j++) {
		if (this instanceof LVW && new Date().valueOf()>crono){
			var self=this
			console.debug('refrescamos y lanzamos otra petición:'+ idCargaPendiente + ' (crono:'+crono+')') 
			window.setTimeout(function(){self._rellenaLVW(idCargaPendiente, datosFilas, nombresColumnas, tipos, marcarPrimeroActivo, nodoActivo, ponerAtributos, datosLVW, llevaColumnaExpandir, j) },5)
			return
			}
		var fila=datosFilas[j]
		var id=this.generaCDfila(fila, j, columnasOcultas)
		
		var tr=this.generaFila(fila, columnasOcultas, this.nombresColumnasVisibles.length)
		tr.id='tr'+id
		
		if ('_css_' in columnasOcultas){
			var cssFila=fila[ columnasOcultas['_css_'] ]
			if (cssFila!=null && cssFila!='') 
				ponEstilo(tr, cssFila)
			}
		
		var key=null
		if (columnasOcultas.mododetalle!=null && columnasOcultas.key!=null)
			key=this.generaKey(fila,columnasOcultas)
		
		var tipoLista=null
		if (key && key.length>=2 )
			tipoLista=key.substr(0, 2)
		if (! tiposLista.contains(tipoLista) )
			tipoLista=null
			
		if ('_firma' in columnasOcultas && fila[columnasOcultas._firma]){
			tipoLista='tf'
			var firma=fila[columnasOcultas._firma]
			tr.setAttribute('firma',firma)
			}
		
		tipoLista=tipoLista || 'lvw'
		tr.setAttribute('tipoLista',tipoLista)
		if (tipoLista=='tp' || tipoLista=='te' || tipoLista=='tf' || tipoLista=='tl' || tipoLista=='lt' || tipoLista=='pb') {
			tr.ondblclick=function(){dobleClick(this);return false;}
			var fnContextMenu=function (event) {
				seleccionarFila(this, event.ctrlKey, true)
				menuContextual.muestraMenu(event, null, key)
				return false
				}	
			tr.oncontextmenu=fnContextMenu
			$(tr).longclick(fnContextMenu)
			}
		else if (tipoLista=='lvw' ){
			var fnContextMenu=function(event){
				seleccionarFila(this, event.ctrlKey, true)
				menuContextual.muestraMenu(event, 'lvw', key)
				return false
				}
			tr.oncontextmenu=fnContextMenu
			$(tr).longclick(fnContextMenu)
			}
		
		var celdaExpandir
		if (llevaColumnaExpandir && columnasOcultas.mododetallesiguiente){
			var mds=fila[ columnasOcultas.mododetallesiguiente]
			celdaExpandir =this.creaCelda2(tr, 'especial', creaTexto(espacioDuro4))
			celdaExpandir.rowSpan=2
			
			if (mds) {
				ponEstilo(celdaExpandir, 'expandir')
				celdaExpandir.onclick=this.xcontrol.expandir(fila, columnasOcultas, tr)
				}
			}
		if (this.mostrarNumFila)
			this.creaCelda2(tr, 'especial numFila', creaTexto(j+1))
			
		var ico=creaTexto('')
		if (columnasOcultas.icono !== null){
			if (fila[ columnasOcultas.icono ]!=null && fila[ columnasOcultas.icono ]!=''){
				ico=creaImg(completaRutaImg(fila[ columnasOcultas.icono ], false), '', null, '')
				ico.tabIndex=-1
				}
			}
		var celdaIco =this.creaCelda2(tr, 'especial icono', ico) //ponemos celda para el icono (esta va fija)
		if (llevaColumnaExpandir && !columnasOcultas.mododetallesiguiente)
			celdaIco.colSpan=this.numColumnasEspeciales
			
		if (key) celdaIco.appendChild( creaCheck( 'checkbox_'+'tr'+id, key)  )
		if ('_title' in columnasOcultas)
			tr.title=fila[columnasOcultas._title]
		if ('_firma' in columnasOcultas){
			if (fila[columnasOcultas._firma]){
				var imgFirma=creaImg('./fijo/ok.png', '☑', 'firma', 'firmado')
				celdaIco.appendChild(imgFirma)
				}
			}
			
		for (var i=0; i<fila.length; i++) {
			var seleccionarPorDefecto=true
			if (nombresColumnas[i] !=undefined) {
				var col=nombresColumnas[i].toLowerCase()
				if (!(col in columnasOcultas)) {
					var colorCelda=null, navegar=null, textoCelda=null, title=null, icoCelda=null
					if ('_css_'+col in columnasOcultas)
						colorCelda=fila[ columnasOcultas['_css_'+col] ]
					if ('_ico_'+col in columnasOcultas){
						icoCelda=fila[ columnasOcultas['_ico_'+col] ]
						if (icoCelda){
							ico=creaImg(completaRutaImg(icoCelda, false))
							}
						}
					if ('_title_'+col in columnasOcultas)
						title=fila[ columnasOcultas['_title_'+col] ]
					if ('_navegar_'+col in columnasOcultas)
						navegar=fila[ columnasOcultas['_navegar_'+col] ] // url|img, la url puede ser de gotta (gotta://nodo=...) o absoluta (http://...)
					
					var valorOriginal=null						
					var tipo=tipos[i]

					if (esTipoNumerico(tipo) && !tieneTipo(tipo, 'lvwNumero'))
						tipo+=' lvwNumero'
					else if (tipo=='lvw')
						tipo=null
					
					var celda=this.creaCelda2(tr, tipo, textoCelda, colorCelda) 
						
					if (navegar){ // "url|img" o "camino|trámite|img"
						
						var trozoTexto=fila[i]
						if (exp && exp.internetExplorer){
							if (trozoTexto==null || trozoTexto=='')
								trozoTexto=espacioDuro
							}
						var props={}
						
						var temp=(navegar+'||').split('|') 
						var p0, p1, p2
						p0=temp[0]; p1=temp[1]; p2=temp[2]
						
						if (!isNaN(p0)){ //botón para lanzar trámites
							var cam, tram, img
							cam=temp[0]; tram=temp[1]; img=temp[2]
							
							props.hijo=creaObjProp('a', {tabIndex:-1, className:'navegacion', texto:trozoTexto, 'style.backgroundImage':completaRutaImg(img, true)})
							props.onclick=fnLanzaTramite(cam, tram, tr, true)
							props.className='cmd'
							celda.appendChild( creaObjProp('button', props) )
							}
						else { //enlace para navegar
							props.texto=trozoTexto
							props.tabIndex=-1
							
							var url, img
							url=temp[0]; img=temp[1]
							
							if (esURLAbsoluta(p0) ) {
								props.href=url
								props.className='navegacion web'
							
								if (esUrlExterna(url) ) 
									props.target='_blank'
								
								}
							else 	{//URL de gotta
								
								props.href='?'+url
								props.onclick=fnNavegarA(url, tr, true)
								if (url.contains('flotante'))
									props.className='navegacion web flotante'
								else
									props.className='navegacion web'
								}
								
							if (img) 
								props['style.backgroundImage']= completaRutaImg(img, true)
								
							celda.appendChild(creaObjProp('a', props)) // a.navegacion
							}
						}
					else	{
						var texto=fila[i]
						if (tipos[i]=='lvwFecha' || esTipoNumerico(tipos[i]) )
							valorOriginal=texto
						
						if (valorOriginal) {
							var valorBruto=valorOriginal
							if ( tipos[i] =='lvwFecha' )
								valorBruto=creaFechaJavascript(valorOriginal)
							celda.setAttribute('valorOriginal', valorBruto)
							}
						
						if (col.contains('\\')){
							var modif=col.split('\\')[1] //si hay varios modificadores, primero los de \0, \1, \M y luego los de \nt para no sacar totales
							texto=formatoSalida(texto, modif, celda)
							}
						else if ( esTipoNumerico(tipos[i]) ){
							texto=formatoSalida(texto, numDecimalesPortipo(tipos[i] ), celda)
							}
						if (icoCelda)
							celda.appendChild(ico)
						if (texto!=null && texto!='')
							creaTextoConEnlaces(celda, texto)
						}
					if(title)
						celda.title=title
					}
				else if (col=='_activar'){
					var valor=fila[i]
					if (valor!=null){
						if (valor=='0')
							seleccionarPorDefecto=false //para que, cuando se indique aquí que no señale una fila esta sea la que gane sobre las filas que había seleccionadas al lanzar el trámite
						else
							seleccionarFila(tr, null, null, null, true)
						}
					}
				else if (col=='_expandir'){//autoexpandir
					var valor=fila[i]
					if (valor!=null && valor!='0'){
						var event = jQuery.Event('click'); 	event.ctrlKey = true
						$(celdaExpandir).trigger( event )
						}
					}
				else if (col=='_expandido'){//expandido
					var valor=fila[i]
					if (valor!=null && valor==true){
						var event = jQuery.Event('click'); 	event.ctrlKey = true
						$(celdaExpandir).trigger( event )
						}
					}
					
				else if (col=='_colorletra' || col=='_colorfondo') {
					var color=fila[i]
					if (color!='0' ) {
						try {
							tr.style.background=colorTinta( color+'')
							}
						catch (e)
							{/*pass*/}
						}
					}
				else if (ponerAtributos && (col=='key' || col=='mododetalle')) {
					var xcol={'key':'cd', 'mododetalle':'md'}
					tr.setAttribute(xcol[col], fila[i])
					}
				}
			}
				
		if (exp.internetExplorer)
			forzarRepintadoIE()
			
		if (key && exp.filasSeleccionadas && (exp.filasSeleccionadas.contains(key)) ){
			seleccionarFila(tr, null, null, null, seleccionarPorDefecto)
			primeraFilaSeleccionada=tr
			exp.filasSeleccionadas.remove(key)
			}
		
		if ((nodoActivo==fila[columnasOcultas.key])||(marcarPrimeroActivo && j==0 && nodoActivo==null) || (exp.filasSeleccionadas && exp.filasSeleccionadas.contains(fila.id))){
			var fn=this.fnOnClickB(tr, fila, columnasOcultas)
			if (fn)
				fn()
			}
		}
	
	this.cargaPendiente.remove(idCargaPendiente) 
	if (primeraFilaSeleccionada)  this.hazScroll(primeraFilaSeleccionada)
	if (this.sortable)	this.hazSortable()
	if (this.mostrarTotales) this.ponPie(nombresColumnas, tipos, columnasOcultas)
	
	if (this.mostrarPaginacion){
		this.montaPaginacion(this.paginacion1, datosFilas, columnasOcultas)
		this.montaPaginacion(this.paginacion2, datosFilas, columnasOcultas)
		}
		
	this.ponDivMaxElementos(datosFilas, datosLVW)
	this.tareasPostCarga()
	this.quitaThrobber()
		
	this.habilitaRedimensionadoColumnas()
	}
Lista.prototype.habilitaRedimensionadoColumnas=function(){
	if (this.xcontrol.tc=='lvw')
		new ColumnResize(this, this.t, 1)
	}
Lista.prototype.tareasPostCarga=function(){
	
	}
Lista.prototype.ponDivMaxElementos=function(datosFilas, datosLVW){
	if ( datosLVW && !datosLVW.datos.maxElementosAlcanzado ) {
		if (this.divMaxElementos){
			try {borra(this.divMaxElementos)}
			catch(e){}
			this.divMaxElementos=null
			}
		}
	else if (datosLVW){
		var maxElementos=datosLVW.maxElementos
		var maxElementosAnterior=control('maxElementos'+this.numControl+'_enlace1')
		if (maxElementosAnterior!=null) //reutilizamos el anterior.Sólo tenemos que cambiar el enlace 'cargar otros 25'
			maxElementosAnterior.onclick=function(){this.cargar(datosFilas.length+maxElementos); return false;}
		else {
			var div=creaObjProp('div', {className:'maxElementos', id:'maxElementos'+this.numControl})
			
			var enlace1=creaObjProp('a', {href:'#', texto:'Cargar otras '+maxElementos + ' filas', id:'maxElementos'+this.numControl+'_enlace1', onclick:this.usarMaxElementos(this.numControl, datosFilas.length+maxElementos)})
			var enlace2=creaObjProp('a', {href:'#', texto:'Cargar todas', id:'maxElementos'+this.numControl+'_enlace2', onclick:this.usarMaxElementos(this.numControl, -1)})
			
			div.appendChild( creaTexto('Hay más filas que no han sido mostradas ni tenidas en cuenta en el ordenado.') )
			div.appendChild( document.createElement('br') )
			div.appendChild(enlace1)
			div.appendChild(creaTexto(' | '))
			div.appendChild(enlace2)
			
			this.divMaxElementos=div
			this.cont.appendChild(div)
			}
		}
	}
Lista.prototype.rellenaLVW=function(datosFilas, nombresColumnas, tipos, marcarPrimeroActivo, nodoActivo, ponerAtributos, datosLVW, llevaColumnaExpandir, primeraFila) { 
        if (this instanceof LVW && this.cargaPendiente.length>0) 
                console.debug(this.cargaPendiente) 
         
	if (datosFilas.length>0 && datosFilas[0].length!=nombresColumnas.length){
		if (exp.idioma!='es')
			texto='Se ha producido un error: el número de columnas traducidas (tabla LEN_Columnas) no coincide con el número de columnas del nivel. Se esperaban '+datosFilas[0].length+' columnas y han llegado '+nombresColumnas.length
		var sinDatos=creaObjProp('div', {className:'sinDatos', texto:texto})
		var xcont=control(this.contenedor)
		borraHijos(xcont)
		xcont.appendChild(sinDatos)
		this.quitaThrobber()
		return
		}
		
        var idCargaPendiente=this.contadorCargaPendiente++ 
        this.cargaPendiente=[idCargaPendiente] 
        this._rellenaLVW(idCargaPendiente, datosFilas, nombresColumnas, tipos, marcarPrimeroActivo, nodoActivo, ponerAtributos, datosLVW, llevaColumnaExpandir, primeraFila) 
        } 
Lista.prototype.montaPaginacion=function(div, datosFilas, columnasOcultas){
	if (datosFilas.length==0)
		return
	
	borraHijos(div)
	div.style.display='block'
	
	var ultimaFila=datosFilas[datosFilas.length-1]
	var numPagina, totalFilas, filasPorPagina
	
	var primeraPagina=1
	numPagina=	Number(ultimaFila[ columnasOcultas._numpagina ])
	totalFilas=		Number(ultimaFila[ columnasOcultas._totalfilas])
	filasPorPagina=	Number(ultimaFila[ columnasOcultas._filasporpagina])
	var desfase=4
	
	if (totalFilas==0) return
	
	var ultimaPagina=Math.ceil(totalFilas/filasPorPagina)
	
	var enlacePrimera=this.creaEnlacePaginacion(primeraPagina, 'especial primera') 
	var enlaceAnterior=this.creaEnlacePaginacion(numPagina-1, 'especial anterior', 'Anterior')
	var enlaceSiguiente=this.creaEnlacePaginacion(numPagina+1, 'especial siguiente', 'Siguiente')
	var enlaceUltima=this.creaEnlacePaginacion(ultimaPagina, 'especial ultima') 
	
	// enlaces rápidos a las páginas cercanas (la actual+-5)
	var span=creaObjProp('span', {className:'enlacesRapidos'})
	
	span.appendChild(enlaceAnterior)
	if ((numPagina-desfase)>1) {
		span.appendChild( enlacePrimera )
		if ((numPagina-desfase)>2)
			span.appendChild( creaObjProp('span', {texto:'...', className:'puntosSuspensivos'}))
		}
	
	for (var jj=numPagina-desfase; jj<numPagina+desfase; jj++){
		var especial=null
		if (jj==numPagina){
			especial='especial paginaActiva'
			
			if (jj==1) ponEstilo(enlacePrimera, 'paginaActiva')
			if (jj==ultimaPagina) ponEstilo(enlaceUltima, 'paginaActiva')
			}
		else if  (jj==1)
			especial='especial primera'
		else if (jj==ultimaPagina)
			especial='especial ultima'
		
		if (jj>0 && jj<ultimaPagina)
			span.appendChild( this.creaEnlacePaginacion(jj, especial) )
		}
	if (jj<ultimaPagina-1){
		span.appendChild( creaObjProp('span', {texto:'...', className:'puntosSuspensivos'}))
		}
	
	span.appendChild( enlaceUltima)
	span.appendChild(enlaceSiguiente)
	div.appendChild( span)
		
	var primeraFila=(numPagina-1)*filasPorPagina+1
	var ultimaFila=Math.min((numPagina)*filasPorPagina, totalFilas)
	
	//navegación para humanos
	var spanbotones=creaObjProp('span', {className:'botonesNavegacion'})
	
	enlacePrimera=this.creaEnlacePaginacion(primeraPagina, 'especial primera', espacioDuro) 
	enlaceAnterior=this.creaEnlacePaginacion(numPagina>1?numPagina-1:1, 'especial anterior', espacioDuro)
	enlaceSiguiente=this.creaEnlacePaginacion(numPagina+1, 'especial siguiente', espacioDuro)
	enlaceUltima=this.creaEnlacePaginacion(ultimaPagina, 'especial ultima', espacioDuro)
	
	spanbotones.appendChild(enlacePrimera)
	spanbotones.appendChild(enlaceAnterior)
	
	var self=this
	var input=creaObjProp('input' ,{type:'text', className:'lvw lvwNumero', value:numPagina, size:4, maxLength:5})
	jwerty.key('enter', function(event){self.keyPressCuadroNavegacion()}, null, input)
	spanbotones.appendChild(input)
	
	spanbotones.appendChild( creaObjProp('span' ,{texto:' de '+ultimaPagina}))
	spanbotones.appendChild(enlaceSiguiente)
	spanbotones.appendChild(enlaceUltima)
	div.appendChild(spanbotones)
	
	var mostrandoNdeM=creaObjProp('span', {className:'mostrandoNdeM', texto:'Mostrando filas '+primeraFila+'-'+ultimaFila+' de '+totalFilas})
	div.appendChild( mostrandoNdeM)
	
	this.numPagina=numPagina
	this.salvaDato('pag', numPagina)
	quitaEstilo(div, 'cargando')
	}
Lista.prototype.keyPressCuadroNavegacion=function(event){
	var valor=Number(this.value)
	if (!isNaN(valor)){
		this._lanzaCargarConPaginacion(valor, this.numControl, this.nivel, this)
		}
	}
Lista.prototype.creaEnlacePaginacion=function(numPagina, clase, texto){
	return this._creaEnlacePaginacion(numPagina, clase, texto, this.numControl, this.nivel, this)
	}
Lista.prototype._creaEnlacePaginacion=function(numPagina, clase, texto, numControl, nivel, lvw){
	var fnLanzaCargar=function(){lvw._lanzaCargarConPaginacion(numPagina, numControl, nivel, lvw)}
	return creaObjProp('a', {onclick: fnLanzaCargar,
					texto: (texto!=null?texto:numPagina), 
					className:clase, title:'Ir a la página '+numPagina})
	}
Lista.prototype.ponPie=function(nombresColumnas, tipos, columnasOcultas){
	if (this.tbody.rows.length<1)
		return
		
	var tfoot=creaObjProp('tfoot', {className:'lvw'})
	this.tfoot=tfoot
	var trfoot=creaObjProp('tr',{className:'lvw'})
	tfoot.appendChild(trfoot)
	trfoot.appendChild( creaObjProp('th', {className:tipo})) //volcarExcel
	
	var h=1
	var hayNumeros=false
	for (var i=0; i<nombresColumnas.length; i++) {
		if (nombresColumnas[i] !=undefined) {
			var col=nombresColumnas[i].toLowerCase()
			if (col in columnasOcultas || (col.charAt(0)=='_' )) {
				//pass
				}
			else	{
				var tipo=tipos[i]
				var modif=null
								
				var sinTotales=false
				if ( col.contains('\\') ){
					var temp= col.split('\\') //permitimos varios modificadores encadenados \0\nt pero en este orden concreto
					modif=temp[1]
					sinTotales=temp.contains('nt')
					}
				else if (tieneTipo(tipo, 'lvwCurrency')){
					modif=2
					}
				
				var valor=null
				if (sinTotales) //no total
					tipo='lvw'
				else if (esTipoNumerico(tipo)) {
					valor=this.sacaSuma(h, tipo)
					if (!tieneTipo(tipo, 'lvwNumero') )
						tipo+=' lvwNumero'
					}
								
				if (valor!=null) 
					hayNumeros=true
				
				trfoot.appendChild( creaObjProp('th', {id:'th'+(h-1), className:tipo, texto:formatoSalida(valor, modif)}))
				
				h++
				}
			}
		}
	if (hayNumeros) this.t.appendChild(tfoot)
}
Lista.prototype.sacaSuma=function(idColumna, tipo){
	var suma=0
	for (var j=0; j<this.tbody.rows.length; j++){
		var celda=this.tbody.rows[j].childNodes[idColumna]
		var valor=Number(celda.getAttribute('valorOriginal'))
		suma+=valor
		}
	if (tieneTipo(tipo, 'lvwCurrency') )
		suma=Math.round(suma*100)/100
	return suma
}
Lista.prototype.hazSortable=function(){
	var st=new SortTable( this.t )
	//~ var sort=this.recuperaDato('s')
	st.makeSortable()
	
	//~ if (sort!=null){
		//~ var listaEnlaces=this.thead.getElementsByTagName('a')
		
		//~ var numColumnasFijas=this.numColumnasEspeciales
			
		//~ var sortDir=sort.substring(0, 1)
		//~ var sortColumn=Number(sort.substring(1))
		//~ var span=listaEnlaces[sortColumn+numColumnasFijas]
		
		//~ if (span) st.resortTable(span, sortDir=='u')
		//~ }
	}
Lista.prototype.borraHijos=function(){
	this.arregla()
	borraHijos(this.contDatos)
}
Lista.prototype.fnOnClick=function(fila, columnasOcultas, cancelarRestoEventos){
	var xcont=this.contDatos
	var self=this
	return function(event) {
		var m=$('div.menuTramitacion:visible')
		if ( m.length>0 &&  m.css('display')=='block') //se ve el menú contextual. Aunque el longclick debería haber evitado este evento parece que toca evitarlo a mano
			return new Evt(event).consume()
		
		event=event || window.event
		seleccionarFila(this, event.ctrlKey, null, event.shiftKey)
				
		try {self.hazScroll(this)}
		catch (e){}
		}
}
//////////////////////////////
Lista.prototype.recuperaDato=function(propiedad){
	if (exp && exp.mdActivo) {
		try 	{
			var xctl=exp.mdActivo().getControl(this.contenedor)
			if (xctl)
				return xctl.recuperaDato(propiedad)
			}
		catch(e) {
			return recuperaDato(this.contenedor, propiedad)
			}
		}
	return recuperaDato(this.contenedor, propiedad)
}
Lista.prototype.salvaDato=function(propiedad, valor){
	if (exp) {
		try 	{
			var xctl=exp.mdActivo().getControl(this.contenedor)
			xctl.salvaDato(propiedad, valor)
			return 
			}
		catch(e) {
			salvaDato(this.contenedor, propiedad, valor)
			}
		}
	salvaDato(this.contenedor, propiedad, valor)
}
//////////////////////////////
Lista.prototype.arregla=function(){
	if (!this.cont) {
		this.cont=control(this.contenedor)
		}
}
Lista.prototype.sacaIdFila=function(idCheck){
	if (!idCheck) return
	var i=idCheck.lastIndexOf('_')+1
	return Number( idCheck.substring(i) )
}
Lista.prototype.fnOnClickB=function(){
	return null}
Lista.prototype.ponEventosTeclado=function(){
	var self=this
	if (!this.eventosTeclado && jwerty)
		jwerty.key(teclasArribaAbajo, function(event){self.onkeydown(event.keyCode, event.ctrlKey, event.altKey); return false}, null, '#'+this.contenedor)
	this.eventosTeclado=true
}
Lista.prototype.onkeydown=function(tecla, ctrlKey, altKey){	
	this.cacheFilas=this.cacheFilas || this.tbody.getElementsByTagName('TR')
	var iActivo=null
	for (var i=this.cacheFilas.length-1; i>=0; i--){
		var fila=this.cacheFilas[i]
		if (fila && tieneEstilo(fila, 'seleccionada')) {
			iActivo=i
			break
			}
		}
	/////////////////////
	if (tecla==btnAbajo) {
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
	else if (tecla==btnArriba){
		if (iActivo==null) iActivo=this.cacheFilas.length
		for (var i=iActivo-1; i>=0; i--){
			var tr=this.cacheFilas[i]
			if (tr){
				seleccionarFila(tr, ctrlKey, null, null)
				if (i==0) this.hazScroll(this.thead) //se ve la cabecera y mola más
				else this.hazScroll(tr)
				break
				}
			}
		}
}
Lista.prototype.hazScroll=function(obj){
	if (!this.nodoEstaVisible(obj)){
		$('#'+this.contenedor+' div.datos').scrollTo(obj)
		}
	$(obj).focus()
	}
Lista.prototype.nodoEstaVisible=function(obj){
	var xnodo=$(obj)
	var xcont=$( '#'+this.contenedor+' div.datos')
	
	var posNodo=xnodo.position().top+30
	if (posNodo<0 || posNodo>xcont.innerHeight())
		return false
	return true	
	}
Lista.prototype.generaCDfila=function(){}
/////////////////////////////////////////
function LVW(numControl, nivel){
	Lista.call(this,'control'+numControl, true)
	this.numControl=numControl
	this.nivel=nivel
	
	this.cargaPendiente=[]
	this.contadorCargaPendiente=0
}
LVW.prototype= new Lista
LVW.prototype.monta=function(datosLVW, idControlDestino) {
	if (datosLVW.tipo=='error') {
		return
		}
	if (idControlDestino!=undefined){
		this.contenedor=idControlDestino
		this.inicializa()
		
		this.montaTabla()
		}
	this.rellenaLVW(datosLVW.datos.filas, datosLVW.datos.cabeceras || datosLVW.datos.columnas, datosLVW.datos.tipos, null, null, null, datosLVW)
}
LVW.prototype.generaKey=function(fila, columnasOcultas){
	return fila [ columnasOcultas.mododetalle ] + fila [ columnasOcultas.key  ]
}
LVW.prototype.generaCDfila=function(fila, iterador){
	//~ return '_'+this.numControl+'_'+iterador
	return 'x_'+this.contenedor+'_'+iterador
}
LVW.prototype.cargar=function (cuantos, fnCargar) {
	if (this.mostrarPaginacion){
		this.numPagina=this.numPagina || this.recuperaDato('pag') || 0
		if (isNaN(this.numPagina) || this.numPagina<=0)
			this.numPagina=1
		this._lanzaCargarConPaginacion(this.numPagina, this.numControl, this.nivel, this)
		}
	else
		this._lanzaCargar(this.numControl, cuantos, this.nivel, this, fnCargar, this.idControlDestino)
}
LVW.prototype._lanzaCargar=function (numControl, cuantos, nivel, lvw, fnCargar, idControlDestino) {
	fnCargar=fnCargar || function(req){ lvw.monta( xeval(req), idControlDestino) }
	var param={'accion':'lvw', 'lvw':numControl, 'nivel': nivel, 'cuantos': cuantos, 'aplicacion': exp.aplicacion}
	if (this.md){
		param['md']=this.md.nombreDetalle
		param['nodo']=this.md.nodoActivo
		if (this.keyContenedor)
			param['nodo']+='\\'+this.keyContenedor
		}
	loadJSONDoc( 	'json', param, fnCargar )
	}
LVW.prototype._lanzaCargarConPaginacion=function(numPagina, numControl, nivel, lvw){	
	var fnCargar=function(req){
		lvw.monta( xeval(req) )
		}
	if (numPagina<0)
		return
	if (lvw.paginacion1) ponEstilo(lvw.paginacion1, 'cargando')
	if (lvw.paginacion2) ponEstilo(lvw.paginacion2, 'cargando')
	
	var param={'accion':'paginaLvw', 'lvw':numControl, 'nivel': nivel, 'pagina':numPagina, 'aplicacion': exp.aplicacion}
		if (this.md){
		param['md']=this.md.nombreDetalle
		param['nodo']=this.md.nodoActivo
		}
	loadJSONDoc( 	'json', param, fnCargar )
	}
LVW.prototype.usarMaxElementos=function(numControl, cuantos){
	return function(){
		var lista=exp.mdActivo().getControl('control'+numControl).lvw
		lista.cargar(cuantos)
		return false
		}
}
//////////////////////////////////
function SubGrid	(numControl, nivel){
	LVW.call(this, numControl)
	this.sortable=false
	this.nivel=nivel
	}
SubGrid.prototype= new LVW
SubGrid.prototype.generaFila=function(fila, columnasOcultas){
	var tbody=this.t.appendChild( creaObjProp('tbody', {className:'fila subgrid mas'}) )
	tbody.onclick=this.fnOnClick(fila, columnasOcultas, true)
	
	var tr=tbody.insertRow(-1)
	
	var tr2=tbody.insertRow(-1)
	tr2.className='rowExpander'
	
	tr2.appendChild( creaObjProp('td', {colSpan:fila.length-1}))
	return tr
	}	
SubGrid.prototype.usarMaxElementos=function(numControl, cuantos){
	return function(){
		ponEstilo(control('maxElementos'+numControl), 'cargando')
		
		var temp=numControl.split('_')
		var xx=temp[0]; var cd=temp[1]
		var sg=exp.mdActivo().getControl('control'+xx).subgrids['x'+cd]
			
		sg.xcontrol.usarMaxElementos(cd, sg.datosFilas.length, cuantos)
		return false
		}
	}
//////////////////////////////////
function idClaveCookie(cont, propiedad){
	if (cont.startsWith('control'))
		cont='c'+cont.substring(7, 100)
	var punto='.'
	return cont+punto+propiedad
}
function cookieAplicacion(){
	var j
	try{
		j=xeval(xGetCookie('config'+aplicacion))||{}	
	}
	catch(e){ // no queremos que pare si tiene cookies chungas
		j={}
	}
	return j	
}
function recuperaDato(nombre, propiedad){
	var j=cookieAplicacion()
	return j[idClaveCookie(nombre, propiedad)]
}
function salvaDato(nombre, propiedad, valor){
	var j=cookieAplicacion()
	var k=idClaveCookie(nombre, propiedad)
	if (j[k]==valor)
		return
	
	j[k]=valor
		
	if (pathCookie==null)
		pathCookie=document.location.pathname
	var expires = new Date()
	expires.setTime(expires.getTime() + 1000 * 86400 * 60)
	xSetCookie('config'+aplicacion, JSONstringify(j), expires, pathCookie)
}
var pathCookie=null
///////////////////////////////	
function esURLAbsoluta(url){
	return url.startsWith('https://') || url.startsWith('http://') || url.startsWith('ftp://')
}
function fnNavegarA(url, lvw, deseleccionarAntes){
	return function(){
		return navegarA(url, lvw, deseleccionarAntes)
		}
	}
function navegarA(url, lvw, deseleccionarAntes, enviarFiltros) {
	enviarFiltros=enviarFiltros==undefined?true: enviarFiltros
	
	if (disenhador && disenhador.modoDisenho)
		return false
	if (deseleccionarAntes)
		desSeleccionarTodos(lvw)
	//novedad: al abrir un flotante se envían también los filtros
	var param={ accion:'navegarA', 'key':url, 'aplicacion': exp.aplicacion, 'flotante':flotante}
	
	if (enviarFiltros){
		var paramFiltro=_filtrar()
		if(paramFiltro===null)
			return false/*los filtros no validan */
		for(var k in paramFiltro){
			param[k]=paramFiltro[k]
			}
		}
	var flotante=url.contains('&flotante')
	loadJSONDoc('json', param, 
                function(req){ 
			var resp=xeval(req)
			if (resp.ok){
				if (resp.flotante){
					var idxBloqueo=resp.idxBloqueo || calculaIdBloqueo()
					
					var datos={'idxBloqueo':idxBloqueo, tramite:''+idxBloqueo, camino:-1, controles:[], numTramites:0, nodo:resp.nodo, urlDestino:resp.url}
					
					new DetalleFlotante( datos, resp.md, datos.idxBloqueo, false).toDOM() // notificarCierre:=false
					}
				else
					cambiaML(resp.ml || exp.idMlActivo, resp.url) 
				}
                 	}
		)
    return false
    }
function seleccionarFila(fila, ctrlKey, esBotonDerecho, shiftKey, forzarValor) {//se usa también en los RD
	var chk=fila.getElementsByTagName('input')[0]
	var value=chk?chk.value:null
	
	var estado=chk ?chk.checked:null
	
	if (forzarValor)
		estado=!forzarValor
	
	if(esBotonDerecho && chk && chk.checked)
		return
	if (chk && chk.clientWidth) {//es visible, control manual
		chk.checked=!chk.checked
		return chk.value 
		}
	var tipo=null
	if (value) tipo=chk.value.substring(0,2)
	
	if (exp && !exp.dispositivoMovil){
		var mayusCtrl=ctrlKey || shiftKey
		if (!mayusCtrl || (!mayusCtrl && tipo!=='tl')) { 
			try {
				if (exp && exp.mdActivo()){
					var lista=exp.mdActivo().listaControlesPorTipo(['lvw', 'rd'])
					for (var i=0; i<lista.length; i++){
						var ctl=lista[i]
						if (ctl.dom) desSeleccionarTodos(ctl.dom)
						}
					}
				desSeleccionarTodos(fila)
				}
			catch (e) {
				desSeleccionarTodos(fila)
				}
			}
		
		if (!shiftKey) {
			if (chk==null) {//cambiamos el color
				_seleccionarFila(fila, !tieneEstilo(fila, 'seleccionada'))
				}
			else {
				//después de des-seleccionar todas, lo dejamos como estaba (para poder quita la selección)
				if (estado!=null) chk.checked=estado
				chequearBox(fila)
				_seleccionarFila(fila, chk.checked)
				}
			}
		else { //seleccionamos un rango de filas
			//~ var padre=$(fila).parents('div.lvw')
			//~ padre.addClass('no-select')
			var seleccionados=filtrarListaPorValor(fila.parentNode.getElementsByTagName('input'), 'checked', true)
			if (seleccionados.length==1){
				var fila2=seleccionados[0].parentNode.parentNode
				var ini=fila.rowIndex
				var fin=fila2.rowIndex
				seleccionarRango(fila2.parentNode, ini, fin)
				}
			//~ setTimeout(function(){padre.removeClass('no-select'); padre[0].focus()}, 2000)
			}
		}
	else {
		if (chk==null) {//cambiamos el color
			_seleccionarFila(fila, !tieneEstilo(fila, 'seleccionada'))
			}
		else {
			chequearBox(fila)
			_seleccionarFila(fila, chk.checked)
			}
		}
	return value
	}
function seleccionarRango(lista, ini, fin){
	if (ini>fin){
		var temp=ini
		ini=fin
		fin=temp
		}
	else {
		ini--
		fin--
		}
	for (var i=ini; i<fin; i++){
		var fila=lista.rows[i]
		if (fila==null)
			continue
		var chk=fila.getElementsByTagName('input')[0]
		
		if (chk==null) {//cambiamos el color
			_seleccionarFila(fila, !tieneEstilo(fila, 'seleccionada'))
			}
		else {
			chequearBox(fila)
			_seleccionarFila(fila, chk.checked)
			}
		}
}
function filtrarListaPorValor(lista, prop, valor){
	var ret=[]
	for (var i=0; i<lista.length;i++){
		var elemento=lista[i]
		if (elemento[prop]==valor) 
			ret.push( elemento )
		}
	return ret
}
function _seleccionarFila(fila, valor){
	if (tieneEstilo(fila, 'xfilaRD'))
		fila=fila.firstChild
		
	if (valor)
		ponEstilo(fila, 'seleccionada')
	else
		quitaEstilo(fila, 'seleccionada')
	}
function desSeleccionarFila(fila) {
	var chk=fila.getElementsByTagName('input')[0]
	if (chk!=null) chequearBox(fila, false)
	
	_seleccionarFila(fila, false)
	}
function chequearBox(fila, forzar) {  //se usa también en los RD
	if (fila==null)
		return
	
	var chk=fila.getElementsByTagName('input')[0]
	if (chk != null){
		if (forzar!=null)
			chk.checked=forzar
		else
			chk.checked=!chk.checked
		if (chk.checked) console.debug('fila pinchada: '+chk.value)
		}
	}
function getNoVacio(col, clave){
	if (col && col[clave]!=null && col[clave]!='')
		return col[clave]
	return null
}
function fnVacia(){}
function ponEstilo(fila, estilo){// si estilo contiene _, se supone que es una opción excluyente de las de la misma raíz
	if (estilo.contains(' ')){
		var temp=estilo.split(/ /)
		for (var i=0; i<temp.length; i++){
			fila.classList.add(temp[i])
			}
		return
		}
	fila.classList.add(estilo)
}
function quitaEstilo(fila, estilo){
	fila.classList.remove(estilo)
}
function tieneEstilo(fila, estilo){
	return fila.classList.contains(estilo)
}
////////////////////////////////
/*
 Longclick Event
 Copyright (c) 2010 Petr Vostrel (http://petr.vostrel.cz/)
 Dual licensed under the MIT (MIT-LICENSE.txt)
 and GPL (GPL-LICENSE.txt) licenses.

 Version: 0.3.2
 Updated: 2010-06-22
*/
(function(a){function n(b){a.each("touchstart touchmove touchend touchcancel".split(/ /),function(d,e){b.addEventListener(e,function(){a(b).trigger(e)},false)});return a(b)}function j(b){function d(){a(e).data(h,true);b.type=f;jQuery.event.handle.apply(e,o)}if(!a(this).data(g)){var e=this,o=arguments;a(this).data(h,false).data(g,setTimeout(d,a(this).data(i)||a.longclick.duration))}}function k(){a(this).data(g,clearTimeout(a(this).data(g))||null)}function l(b){if(a(this).data(h))return b.stopImmediatePropagation()||
false}var p=a.fn.click;a.fn.click=function(b,d){if(!d)return p.apply(this,arguments);return a(this).data(i,b||null).bind(f,d)};a.fn.longclick=function(){var b=[].splice.call(arguments,0),d=b.pop();b=b.pop();var e=a(this).data(i,b||null);return d?e.click(b,d):e.trigger(f)};a.longclick={duration:500};a.event.special.longclick={setup:function(){/iphone|ipad|ipod/i.test(navigator.userAgent)?n(this).bind(q,j).bind([r,s,t].join(" "),k).bind(m,l).css({WebkitUserSelect:"none"}):a(this).bind(u,j).bind([v,
w,x,y].join(" "),k).bind(m,l)},teardown:function(){a(this).unbind(c)}};var f="longclick",c="."+f,u="mousedown"+c,m="click"+c,v="mousemove"+c,w="mouseup"+c,x="mouseout"+c,y="contextmenu"+c,q="touchstart"+c,r="touchend"+c,s="touchmove"+c,t="touchcancel"+c,i="duration"+c,g="timer"+c,h="fired"+c})(jQuery);
////////////////////////////////
// ColumnResize, Basado en  Resizable Table Columns version: 1.0, (c) 2006, bz
function preventEvent(e) {
	var ev = e || window.event;
	if (ev.preventDefault) ev.preventDefault();
	else ev.returnValue = false;
	if (ev.stopPropagation)
		ev.stopPropagation();
	return false;
	}
// main class prototype
function ColumnResize(lista, table, numColumnasSinTiradores) {
	if (table.tagName != 'TABLE') return;

	this.id = table.id;

	// ============================================================
	// private data
	var self = this;

	var dragColumns  = table.rows[0].cells; // first row columns, used for changing of width
	if (!dragColumns) return; // return if no table exists or no one row exists

	var dragColumn
	var dragColumnNo; // current dragging column
	var dragX;        // last event X mouse coordinate

	var saveOnmouseup;   // save document onmouseup event handler
	var saveOnmousemove; // save document onmousemove event handler
	var saveBodyCursor;  // save body cursor property

	var saveWidth
	// ============================================================
	// methods

	// ============================================================
	// do changes columns widths
	// returns true if success and false otherwise
	this.changeColumnWidth = function(no, w) {
		if (!dragColumns) return false;
		if (no < 0) return false;
		if (dragColumns.length < no)	return false;

		if (parseInt(dragColumns[no].style.width) <= -w) return false;
		if (dragColumns[no+1] && parseInt(dragColumns[no+1].style.width) <= w) return false;

		self.cambiaTamanho(dragColumns[no], w)
		return true;
		}
	this.cambiaTamanho=function(th, w){
		if (!th)
			return

		var c0=$(th)
		c0.css('width', null)//por si ak
		
		var fc=c0.children(':first-child')

		var old=fc.outerWidth()
		fc.css('width', fc.outerWidth()+w )
		}
	// ============================================================
	// do drag column width
	this.columnDrag = function(e) {
		var e = e || window.event;
		var X = e.clientX || e.pageX;
		if (!self.changeColumnWidth(dragColumnNo, X-dragX)) 
		self.stopColumnDrag(e);

		dragX = X;
		// prevent other event handling
		preventEvent(e);
		return false;	
		}
	// ============================================================
	this.getWidth=function(x) {
		return $(x).outerWidth(false)
		}
	// ============================================================
	// stops column dragging
	this.stopColumnDrag = function(e) {
		var e = e || window.event;
		if (!dragColumns) return;
		
		// restore handlers & cursor
		document.onmouseup  = saveOnmouseup;
		document.onmousemove = saveOnmousemove;
		self.ponCursor(saveBodyCursor)
		
		quitaEstilo(dragColumn, 'drag')
		
		self.colocaTiradores()//colocamos los tiradores en su sitio
		self.salvaEnCookies()// remember columns widths in cookies for server side
		preventEvent(e);
		}
	this.colocaTiradores=function(){
		//le ponemos al tirador el mismo ancho de la columna
		var d=$(table).find('th div.resizableColumn')
		$.each(d, function(index, element){ 
		var e=$(element)
		e.css('width', e.parent().width() )
		} )
		}
	this.ponCursor=function(cursor){
		$('body, table.sortable tr, table.lvw tr').css('cursor',  cursor)
		}
	this.salvaEnCookies=function(){
		var colsWidth = '';
		var separator = '';
		for (var i=0; i<dragColumns.length; i++) {
			colsWidth += separator + parseInt( self.getWidth(dragColumns[i].firstChild) );
			separator = '+';
			}
		lista.salvaDato('cw', colsWidth)
		}
	// ============================================================
	// init data and start dragging
	this.startColumnDrag = function(e) {
		var e = e || window.event;

		var th=(e.target || e.srcElement).parentNode.parentNode
		dragColumn=th
		ponEstilo(dragColumn, 'drag')
		
		//~ th.style.width=null
		// remember dragging object
		dragColumnNo = th.cellIndex;
		dragX = e.clientX || e.pageX;

		// set up current columns widths in their particular attributes
		// do it in two steps to avoid jumps on page!
		var colWidth = new Array();
		for (var i=0; i<dragColumns.length; i++)
			colWidth[i] = parseInt( self.getWidth(dragColumns[i]) );
		for (var i=0; i<dragColumns.length; i++) {
			dragColumns[i].width = ""; // for sure
			dragColumns[i].style.width = colWidth[i] + "px";
			}

		saveOnmouseup       = document.onmouseup;
		document.onmouseup  = self.stopColumnDrag;
		saveBodyCursor             = document.body.style.cursor;
		self.ponCursor('w-resize !important')

		// fire!
		saveOnmousemove      = document.onmousemove;
		document.onmousemove = self.columnDrag;

		preventEvent(e);
		}

	// prepare table header to be draggable
	// it runs during class creation
	var colsWidth=(lista.recuperaDato('cw') || '').split('+')
	numColumnasSinTiradores=numColumnasSinTiradores || 0
	for (var i=numColumnasSinTiradores; i<dragColumns.length; i++) {
		var style= (colsWidth[i] ? 'width:'+colsWidth[i]+'px' : '')
		dragColumns[i].innerHTML = "<div class='resizableColumn' style='"+style+"'><div class='resizableColumnI'></div>"+dragColumns[i].innerHTML+"</div>";
		var dr, dri
		dr=dragColumns[i].firstChild; dri=dr.firstChild
		dri.onmousedown = this.startColumnDrag
		if (lista.sortable && i>0)
			dr.onclick=fnClickHijo(dr.lastChild)
		}
}
function fnClickHijo(sortheader){
	return SortTable.prototype.fnOnClick(sortheader)
	//~ return function(){
		//~ $(sortheader).trigger('click')
		//~ $(sortheader).click() 
		//~ SortTable.prototype.fnOnClick(sortheader)()
		//~ }
	}
//////////////////////////////////////
function errorAjax(req, textStatus, errorThrown){
	var texto
	if (errorThrown==500){
		var ret
		ret=eval('('+req+')')
		texto=ret.error
		}
	else
		texto=req.statusText
	
	console.error(texto)
	if (req.responseText!=null)
		console.error(xeval(req.responseText).error)
		
	alert('Error al cargar\nIntente recargar la página o consulte con su administrador.')
}
function ajax(tipo, url, parametros, func, funcError){
	jQuery.ajax({
		type:tipo,
		url: url,
		data:parametros,
		
		success:func, error: funcError || errorAjax
		})
	}
function loadJSONDoc(url, parametros, func, funcError){ajax('GET', url, parametros, func, funcError)}
function loadJSONDocPost(url, parametros, func, funcError){ajax('POST', url, parametros, func, funcError)}
///////////////////////////////////////
function cargarLista(listaDestino, param, campoCD, campoDS, cambiarClase, anhadirDS, anhadirToolTip, valor){
	loadJSONDoc( 'json',  {'accion':param, aplicacion: aplicacion},
		function(req) {
				var datos=xeval(req) //{'filas':[['arb','Arbol'],...['www','Página web']],'columnas':['cd','ds']}
				if (datos.tipo=='error' && param=='estilosControl') {
					listaDestino.style.display='none'
					control('btnMantenimientoEstilos').style.display='none'
					return
					}
				var cd=datos.columnas.indexOfIgnoreCase(campoCD), ds=datos.columnas.indexOfIgnoreCase(campoDS)
				borraHijos(listaDestino)
				
				if (disenhador) disenhador[param]=datos //lo cacheamos
				for (var i=0; i<datos.filas.length; i++){
					var fila=datos.filas[i]
					var opt=creaObjProp('option', {className: cambiarClase?fila[cd]:null, title:anhadirToolTip?fila[ds]:null, value:fila[cd], texto:fila[cd] + (anhadirDS? ' ('+fila[ds]+')':'')} ) 
					listaDestino.appendChild(opt)
					}
				if (valor)
					listaDestino.value=valor
				})
	}
/////////////////////////////////////
ArbolComoLista.prototype._arreglaParaGeneraTabla=function(datosLVW){
	if (!datosLVW.datos)  
		return
	var trans=[]
	var nombresColumnas=['key', 'ds','mododetalle', 'icono', 'Descripción']
	var tipos=['lvw', 'lvw', 'lvw', 'lvw', 'lvw']
	for (var inodo=0; inodo<datosLVW.datos.length; inodo++){
		var nodo=datosLVW.datos[inodo]
		
		var fila=[nodo.cd, nodo.ds, nodo.md, nodo.ico, nodo.ds] //{'key':nodo.cd,'ds':nodo.ds, 'mododetalle':nodo.md, 'icono':nodo.ico, 'Código':nodo.cd}
		for (var k in nodo.restoColumnas){
			if (typeof nodo.restoColumnas[k]=='function')
				continue
			fila.push(nodo.restoColumnas[k])
			
			if (!trans.length)
				nombresColumnas.push(k)
			tipos.push('lvw')
			}
		trans.push(fila)
		}
	this.rellenaLVW(trans, nombresColumnas, tipos, true, datosLVW.nodoActivo, true)
	}
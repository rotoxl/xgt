function mostrarPanelIzq(){	}
function ocultarPanelIzq(){	}
function mostrarBotonera(){	}
function ocultarBotonera(){	}
function crea(tipoType, clase, id, 		izq, tope, ancho, alto){
	var temp=tipoType.split(':')
	var tipo=temp[0]
	
	var d=document.createElement(tipo)
	if (temp[1])
		d.type=temp[1]
	
	if (clase!=null) d.className=clase
	if (id!=null) d.id=id

	if (izq!=null)  d.style.left = izq 
	if (tope!=null) d.style.top = tope
	if (ancho!=null && ancho>-1) d.style.width = ancho
	if (tipo=='DIV' && ['lvw','arb','rd','www'].contains(clase) ) {
		//pass
		}
	else if (alto!=null && alto>-1) 
		d.style.height = alto 
		
	return d
}
function sortTopeAlto(a, b){
	//return Number(a[listaProps.tope])+Number(a[listaProps.alto])-(Number(b[listaProps.tope])+Number(b[listaProps.alto]))
	return a[listaProps.tope]-b[listaProps.tope]
}
ModoDetalle.prototype.cargarControles=function (divContenedor, contenedor, pestActiva){
	var reales=[]
	var lista=0
	
	lista=creaObjProp('DIV', {className:'contenedorlista'} ) 
	divContenedor.appendChild( lista )
	var desfase=0
	var desfaseH=0
	
	var marcaSiguiente=null
	for (var i = 0; i < this.filas.length; i++) {
		var c = this.filas[i]
		var ctlDesfaseH
		var domDesfaseH
		var tipoControl
		var ctl
		if (c[listaProps.cont] == 0 && contenedor == undefined) 
			reales.push(this.filas[i])
		else 	{
			if (c[listaProps.cont] == contenedor && c[listaProps.pestanha] == pestActiva) 
				reales.push(this.filas[i])
			else 
				if (c[listaProps.cont] == contenedor && pestActiva === undefined) {
					var rd = this.getControl(contenedor) //rd
					tipoControl = c[listaProps.tc]
					ctl = new Control[tipoControl](c, this)
					
					ctl.refresco = false //para que no intente refrescarlos cada vez que cambiamos de nodo
					ctl.id = ctl.id + 'tr_' + ctl.cont + '_0' //el id en los controles del RD debe indicar la fila en la que está
					rd.listaControles.push(ctl)
					this.listaControles.push(ctl)
					this.dicControles[ctl.id] = ctl
					
				}
			}
		}
	var xfilas=reales.sort(sortTopeAlto)
	for (var i=0; i<xfilas.length; i++) {
			c = xfilas[i]
			tipoControl=c[listaProps.tc]
			if (tipoControl in Control && tipoControl!='cmd') {
				ctl=new Control[tipoControl](c, this)
				this.listaControles.push(ctl)
				this.dicControles[ctl.id] = ctl
				
				var dom=ctl.toDOM()
				var extensible=['lvw','arb','rd','tab'].contains( tipoControl )
				if (tipoControl=='txt' && c[listaProps.alto]>400){
					extensible=true
					dom.style.position='relative'
				}
				if (tipoControl == 'tab') 
					dom.style.marginLeft = c[listaProps.izq] / escala
				else if (extensible && divContenedor.className == 'pestanha') {
					var divContenedorX = divContenedor
					while (divContenedorX.style) {
						divContenedorX.style.height = null
						divContenedorX=divContenedorX.parentNode
						}
					}
				if( marcaSiguiente && c[listaProps.tope] > marcaSiguiente ){
					desfase=marcaSiguiente/escala
					marcaSiguiente=null
					
					divContenedor.appendChild( creaObjProp('DIV', {className:'clearBoth'}) )
					divContenedor.appendChild( creaObjProp('DIV', {className:'contenedorlista'}) )
					}
				var tope=c[listaProps.tope]/escala-desfase
				if (extensible) {
					if (marcaSiguiente == null) {
						marcaSiguiente=Number(c[listaProps.tope])+Number(c[listaProps.alto])
						desfaseH=Number(c[listaProps.izq])+Number(c[listaProps.ancho])
						ctlDesfaseH=c
						domDesfaseH=dom

						lista.appendChild( creaObjProp('div', {className:'placeholder', id:'placeholder'+ctl.numControl, 'style.top':tope+'px'}) )
						}
					else{
						//miramos si la que ha marcado el desfaseH está más a la derecha
						if (c[listaProps.izq]-desfaseH<0){
							desfaseH=Number(c[listaProps.izq])+Number(c[listaProps.ancho])
							domDesfaseH.style.left=(ctlDesfaseH[listaProps.izq]-desfaseH)/escala
							lista.removeChild(domDesfaseH)
							dom.style.top=0 //TODO Esto sólo va si está a la misma altura
							//var placeHolder=crea('div', 'placeholder', 'placeholder1'+ctl.numControl, null, null, null, tope) 
							//lista.appendChild( placeHolder )
							lista.appendChild ( dom )
							ctlDesfaseH=c
							//intercambiamos los papeles para que float funcione
							var tmp=domDesfaseH
							domDesfaseH=dom
							dom=tmp
							}
						else
							dom.style.top=0 //TODO Esto sólo va si está a la misma altura
							//var placeHolder=crea('div', 'placeholder', 'placeholder2'+ctl.numControl, null, null, null, tope) 
							//lista.appendChild( placeHolder )
							dom.style.left=(c[listaProps.izq]-desfaseH)/escala
					}
					dom.style.top=0
					dom.style.height=null
					//Creamos un palote de altura mínima para que la lista ocupe al
					//menos lo dibujado
					if ( ['lvw','arb','rd','txt'].contains( tipoControl )) 
						lista.appendChild ( creaObjProp('div', {className:'palote', 'style.left':-2, 'style.top':(Number(c[listaProps.alto])/escala)+'px'}) )
					}
				else
					dom.style.top=tope
					
				lista.appendChild ( dom )
					
				if (tipoControl==='tab') 
					ctl.cargarControles()
					
				else if (tipoControl==='rd')
					ctl.cargarControles(dom)
				}
			}
}
ControlTAB.prototype.rellena=function(valor){
	for (var i=0; i<this.pestanhas.length; i++)
		this.md.rellenarControlesDIN(this.numControl, i)
}

ModoDetalle.prototype.repasaControlesErroneos=function(){
}
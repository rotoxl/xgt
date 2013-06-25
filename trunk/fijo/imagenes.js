var editorImagenes = null
var imagenes = {'imagenes':[{'nombre':'nombre1','ruta':'./fijo/debug22.png'},{'nombre':'nombre2','ruta':'./fijo/debug22.png'}]}

function editaImagenes(apli){
	editorImagenes=new FormularioImagenes(ponNuevoBloqueo(),apli)
	editorImagenes.toDOM()
}

function FormularioImagenes(idxBloqueo,apli){
	Frm.call(this, 'Imágenes de la Aplicacion', idxBloqueo)
	this.altoBotonera=0
	this.altoFrm=500
	this.anchoFrm=500
	this.apli=apli
	
	this.titulo='Imágenes'
	this.rutaIcono='./fijo/debug22.png'
	
	this.botones=[	
			creaObj('button', 'cmdgt', null, 'Elegir', function(){editorImagenes.aceptaImagen()} ) , 
			creaObj('button', 'cmdgt', null, 'Cancelar', function(){ editorImagenes.ocultaFormulario() })
			]
}
FormularioImagenes.prototype= new Frm
FormularioImagenes.prototype.pintaCuerpo=function() {
	var ventana=creaObj('div')
	
	var texto=this.creaObj('div', null, creaT('Elija la imagen que desea incluir en su aplicacion'))
	var aviso=this.creaObj('div','bombilla', texto)
	
	ventana.appendChild(aviso)
	
	this.lvw=new Lista('lvwConexiones', true)
	ventana.appendChild( this.lvw.pintaContenedor(true) )
	
	ventana.style.height=this.altoFrm-72 +'px'
	ventana.style.width=this.anchoFrm+'px'
	return ventana
}
FormularioImagenes.prototype.activate=function(){
	this.cargaListaImagenes()
}
FormularioImagenes.prototype.cargaListaImagenes=function(){
	var params= {'accion': 'imagenes' ,'aplicacion': this.apli}	
	loadJSONDoc( 'json', params,function(req){editorImagenes._cargaListaImagenes( xeval(req) )} )
}
FormularioImagenes.prototype._cargaListaImagenes=function(datos){
	this.lvw.rellenaLista(['Nombre', 'Imagen'], ['nombre', 'ruta'], datos.imagenes, 'nombre', null, null, true,null,'ruta')
}
FormularioImagenes.prototype.aceptaImagen=function(){
	alert(this.lvw.getFilasPinchadas())
}

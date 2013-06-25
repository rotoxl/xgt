/*Para evitar que los tabs guarden en cookies la pesta�a activa*/
ControlTAB.prototype.salvaDato=function(propiedad, valor){}
ControlTAB.prototype.recuperaDato=function(propiedad){}

/* Altura del Splitter */
function sacaH(){
     var altoBotoneraArriba=control('botonera').clientHeight
     return (xClientHeight()-altoBotoneraArriba+dif) - 25
} 

function sacaW(){
	//var anchoBotoneraDisenho=(disenhador && disenhador.modoDisenho?control('botoneraDisenho').clientWidth : 0)
     var anchoBotoneraDisenho=0
	// return xClientWidth()+dif-anchoBotoneraDisenho
	return $('#logo').width()+dif-anchoBotoneraDisenho
}

// Los rec se ven m�s estrechos en IE8. Parece que no basta con sobreescribir la funci�n controlREC
/////////////////////////////
function ControlREC(c, md){
        Control.call(this,c,md)
	
	this.ajusteAlto=-6
	this.ajusteAncho=-1
	this.ajusteTope=+3
	
	if (exp.internetExplorer) {
		this.ancho-=1
		this.alto=this.alto-5
		}
	else {
		this.alto+=this.ajusteAlto
		this.ancho+=this.ajusteAncho
		}
	this.tope=this.tope+this.ajusteTope
        }
ControlREC.prototype= new Control
Control['rec']=ControlREC
ControlREC.prototype.ponFuente=function(nodoDOM) {}

ControlREC.prototype.toDOM = function() {
	var rec=this.creaDIV()
	
	rec.className=this.tc
	if (this.claseCSS) {
		this.aplanaControl(rec)
		ponEstilo(rec, this.claseCSS)
		}
	else if (this.alto==0) {
		rec.style.borderWidth='0px'
		rec.style.borderTopWidth='1px'
		}
		
	rec.appendChild( creaObj('span') )
	return rec
	}
ControlREC.prototype.datosFRM=function(){
	return {"numTramites" : 0,"camino" : 0,"tramite" : 'Propiedades de la etiqueta',"tipo" : 'frm', 'controles':[
			{"tipo" : 'bsc',"id" : 'exp_estilos.ds_estilo',"obligatorio" : false,"ds" : 'Clase CSS',"colsQueBloquea" : 0,"colsBloqueadas" : 0,"bloqueado" : false,'controles':[{"tipo": 'lvw',"id" : 'cd_estilo',"maxlength" : -15,"valor" : this.claseCSS,"obligatorio" : false,"bloqueado" : false,"ds" : ''}]}
			]}
}		
ControlREC.prototype.retornoFRM=function(param){
	this.modificado=	this.claseCSS!=param['cd_estilo']
	this.claseCSS=param['cd_estilo']
	this.refresca()
}
/////////////////////////////


jquerycssmenu.fadesettings.overduration = 0
jquerycssmenu.fadesettings.outduration = 0

// El �rbol queda 10px por debajo de lo correcto
Explorador.prototype.colocaArbol=function(ml){
	if (ml==null) ml=this.getMD(this.idMlActivo)
	
	var alturaBotonera=quitaPx(control('botonera').offsetHeight)
	var altura=0
	if (ml!=null){
		ml.div.style.display='block'
		
		var altura=ml.maxAltura
		if (altura>0)
			altura+=20
		if (disenhador && disenhador.modoDisenho)
			altura=Math.max(ml.maxAltura, limAltoPanel)
		
		ml.div.style.height=altura+'px'
		}
	
	var lista=control('listaPanelesIzq')
	lista.style.top=alturaBotonera+'px' //73px
	
	var xdivarbol=control('divarbol')
	
	// Si el arbol es position:fixed
		//~ xdivarbol.style.top= alturaBotonera+altura+'px'
	
	// Si el arbol es position:absolute
	xdivarbol.style.top= altura+(altura==0 ? 0 : -15)+'px' 
		win_onresizeEXP(false)
	if (exp.internetExplorer){
		xdivarbol.parentNode.scrollLeft=0 	
		xdivarbol.scrollLeft=0 
		
		if (exp.internetExplorer6) {
			setTimeout(function (){
				xdivarbol.parentNode.parentNode.scrollLeft=0 	
				xdivarbol.parentNode.parentNode.parentNode.scrollLeft=0 	
				xdivarbol.parentNode.parentNode.parentNode.parentNode.scrollLeft=0 	
				xdivarbol.parentNode.parentNode.parentNode.parentNode.parentNode.scrollLeft=0 	
				},2000)
			}
		}
	
	}

// Formularios dise�ados: m�s aire a los lados
FormularioD.prototype.calculaAnchoyAlto=function(){
	if (!this.detalle)
		this.detalle=this.getFrmMD(this.idxDetalle)

	var diff=0
	this.altoCuerpo=this.detalle.calculaAlturaPanel()+diff
	this.alto=this.altoCuerpo+68
	this.ancho=this.detalle.getAncho()+diff+20
}*/

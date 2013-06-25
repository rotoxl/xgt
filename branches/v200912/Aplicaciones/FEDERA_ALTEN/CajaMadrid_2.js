/*Para evitar que los tabs guarden en cookies la pesta�a activa*/
ControlTAB.prototype.salvaDato=function(propiedad, valor){}
ControlTAB.prototype.recuperaDato=function(propiedad){}
/*
function sacaW(){
	var anchoBotoneraDisenho= 0
	return control('splitterV').clientWidth;
	}
*/
function sacaH(){
	return xClientHeight()-$('.header.panelSup').outerHeight()+dif-25
	}
function sacaW(){
	return $('#botoneraIzq').innerWidth()
}
xSplitter.prototype.getPosBarra=function (){
	if (this.bHorizontal)
		return this.barEle.offsetLeft+(exp.internetExplorer?0:1)
	else
		return this.barEle.offsetTop
	}
xSplitter.prototype.ocultarPanelIzq=function(){
	this.paint(this.splW, this.sqlH, -10, 0)
	}
// Formularios dise�ados: m�s aire a los lados
FormularioD.prototype.calculaAnchoyAlto=function(){
	if (!this.detalle)
		this.detalle=this.getFrmMD(this.idxDetalle)

	var diff=0
	this.altoCuerpo=this.detalle.calculaAlturaPanel()+diff
	this.alto=this.altoCuerpo+68
	this.ancho=this.detalle.getAncho()+diff+20
}
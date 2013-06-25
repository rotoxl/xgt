//~ /*Para evitar que los tabs guarden en cookies la pesta�a activa*/
//~ ControlTAB.prototype.salvaDato=function(propiedad, valor){}
//~ ControlTAB.prototype.recuperaDato=function(propiedad){}

//~ /* Altura del Splitter */
function sacaH(){
     var altoBotoneraArriba=$('div.panelSup').outerHeight()
     return (xClientHeight()-altoBotoneraArriba+dif) -25
} 
function sacaW(){
	//var anchoBotoneraDisenho=(disenhador && disenhador.modoDisenho?control('botoneraDisenho').clientWidth : 0)
     var anchoBotoneraDisenho=8
	// return xClientWidth()+dif-anchoBotoneraDisenho
	return $('#logo').width()+dif-anchoBotoneraDisenho
}



//~ jquerycssmenu.fadesettings.overduration = 0
//~ jquerycssmenu.fadesettings.outduration = 0

// El Ⳣol queda 10px por debajo de lo correcto
Explorador.prototype.colocaArbol=function(ml){
	if (ml==null) ml=this.getMD(this.idMlActivo)
	
	var alturaBotonera=$('.panelSup').outerHeight()
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
		//~ ////~ xdivarbol.style.top= alturaBotonera+altura+'px'
	
	// Si el arbol es position:absolute
	xdivarbol.style.top= altura+(altura==0 ? 0 : -15)+'px' 
		exp.onResize(false)
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

// Formularios diseñados: más aire a los lados
FormularioD.prototype.calculaAnchoyAlto=function(){
	if (!this.detalle)
		this.detalle=this.getFrmMD(this.idxDetalle)

	var diff=0
	this.altoCuerpo=this.detalle.calculaAlturaPanel()+diff
	this.alto=this.altoCuerpo+68
	this.ancho=this.detalle.getAncho()+diff+20
}
xSplitter.prototype.getPosBarra=function (){
	if (this.bHorizontal)
		return this.barEle.offsetLeft+1
	else
		return this.barEle.offsetTop
	}

	/////////////////////////////
//Validación de matrículas según el tipo de matrícula
function Matricula(){
	Texto.call(this, 'Matricula') 
	this._msgError=null
}
Matricula.prototype= new Texto
Matricula.prototype.esValido=function(valor, campo){
	valor=valor.toUpperCase()
	
	var tipo=control('@mascara').value
	
	var ret, error
	try	{
		if (tipo=='T1')
			ret=this.tipo1(valor) 
		else if (tipo=='T2')
			ret=this.tipo2(valor) 
		else if (tipo=='T3')
			ret=this.tipo3(valor) 
		else if (tipo=='X')
			ret=this.tipoMixto(valor) 
		//~ else if (tipo=='EXT')
			//~ return true
		
		campo.value=ret
		return true
		}
	catch (e){
		this._msgError=e.msg
		return false
		}
}
Matricula.prototype.espacio=' '
Matricula.prototype.letras='ABCDEFGHIJKLMNOPRSTUVWXYZ'
Matricula.prototype.numeros='0123456789'
Matricula.prototype.llevaCaracteresNoPermitidosExtendidos=function(valor){
	var letrasNoAdmitidas='AEIOUQ'
	for (var i=0; i<letrasNoAdmitidas.length; i++){
		var letra=letrasNoAdmitidas.substring(i, i+1)
		if (valor.indexOf(letra)>-1)
			throw new Err('No se admite la letra '+letra)
		}
	return this.llevaCaracteresNoPermitidos(valor)
	}
Matricula.prototype.llevaCaracteresNoPermitidos=function(valor){
	var todos=this.letras+this.espacio+this.numeros
	
	for (var i=0;i<valor.length;i++){
		var cr=valor.substring(i, i+1)
		if (todos.indexOf(cr)==-1)
			return true//throw new Err('No se admite el caracter '+cr)
		}
		
	return false
	}
Matricula.prototype.tipo1=function(valor){
	//ZS999999 (la S es opcional, si no se tiene que dejar un espacio, por ejemplo M 999999 o AV999999 ) 
	if (this.llevaCaracteresNoPermitidos(valor))
		throw new Err('Tipo 1: lleva caracteres extraños')
	
	var trozos=this.separaLetrasNumeros(valor)
	if (trozos.length!=2)
		throw new Err('Las matrículas de tipo 1 llevan 2 bloques')
	if (trozos[0].length>2)
		throw new Err('Las matrículas de tipo 1 llevan el primer bloque con 2 letras como máximo')
	
	var letra1, letra2, letras, resto
	letra1=trozos[0].substring(0,1)
	letra2=trozos[0].length>1?trozos[0].substring(1,2):null
	
	if (!this.esLetra(letra1))
		throw new Err('Las matrículas tipo 1 empiezan por una letra')
	
	if (letra2==null){//1 sóla letra
		letras=letra1+this.espacio
		}
	else if (this.esLetra(letra2)){
		if (letra2=='S')
			letra2=this.espacio
		letras=letra1+letra2
		}
	else if (letra2==this.espacio){
		letras=letra1+letra2
		}

	trozos.splice(0,1,letras)
		
	if (!this.sonTodoNumeros(trozos[1]))
		throw new Err('Las matrículas tipo 1 llevan la segunda sección con números')
	if (trozos[1].length!=6)
		throw new Err('Las matrículas tipo 1 llevan 6 números en la segunda sección')
		
	return trozos.join('')
	}
Matricula.prototype.tipo2=function(valor){
	//ZS9999ZS (la S es opcional, si no se tiene que dejar un espacio, por ejemplo M 9999AA o AV9999A )
	if (this.llevaCaracteresNoPermitidos(valor))
		throw new Err('Tipo 2: lleva caracteres extraños')
	
	var trozos=this.separaLetrasNumeros(valor)
	if (trozos.length!=3)
		throw new Err('Las matrículas de tipo 2 llevan 3 bloques')
	if (trozos[0].length>2)
		throw new Err('Las matrículas de tipo 2 llevan el primer bloque con 2 letras como máximo')
		
	var letra1, letra2, letras, resto
	letra1=trozos[0].substring(0,1)
	letra2=trozos[0].length>1?trozos[0].substring(1,2):null
	
	if (!this.esLetra(letra1))
		throw new Err('Las matrículas de tipo 1 empiezan por una letra')
	
	if (letra2==null){//1 sóla letra
		letras=letra1
		}
	else if (this.esLetra(letra2)){
		if (letra2=='S')
			letra2=this.espacio
		letras=letra1+letra2
		}
	else if (letra2==this.espacio){
		letras=letra1+letra2
		}

	trozos.splice(0,1,letras)
		
	if (!this.sonTodoNumeros([trozos[1]]))
		throw new Err('Las matrículas tipo 1 llevan la segunda sección con números')
	if (!this.sonTodoLetras([trozos[3]]))
		throw new Err('Las matrículas tipo 1 llevan la tercera sección con letras')
		
	if (trozos[1].length!=4)
		throw new Err('Las matrículas tipo 1 llevan 6 números en la segunda sección')
	if (trozos[2].length<1 || trozos[2].length>2)
		throw new Err('Las matrículas tipo 1 llevan 1 o 2 letras en la tercera sección')
		
	return trozos.join('')		
	}
Matricula.prototype.tipo3=function(valor){
	//9999ZZZ (en las letras excluyendo Ñ y Q y las vocales)
	if (this.llevaCaracteresNoPermitidosExtendidos(valor))
		throw new Err('Tipo 3: lleva caracteres extraños')
	
	
	var trozos=this.separaLetrasNumeros(valor)
	if (trozos.length!=2)
		throw new Err('Las matrículas de tipo 2 llevan 3 bloques')
	if (!this.sonTodoNumeros([trozos[0]]))
		throw new Err('Las matrículas tipo 3 llevan la primera sección con números')
	if (!this.sonTodoLetras([trozos[1]]))
		throw new Err('Las matrículas tipo 3 llevan la segunda sección con letras')
	
	if (trozos[0].length!=4)
		throw new Err('Las matrículas tipo 3 llevan 4 números en la primera sección')
	if (trozos[1].length!=3)
		throw new Err('Las matrículas tipo 3 llevan 3 letras en la segunda sección')
	
	return trozos.join('')
	}
Matricula.prototype.tipoMixto=function(valor){
	//H/R/E/T/S/V/P/C + Tipo 3 (9999ZZZ en las letras excluyendo Ñ y Q y las vocales)
	var trozos=this.separaLetrasNumeros(valor)
	
	var subtipo=null
	if ('HRETSVPC'.indexOf(trozos[0])==-1){
		throw new Err('No parece una matrícula de tipo especial (H/R/E/T/S/V/P/C)')
		}
	else
		subtipo=trozos[0]
	
	if (this.llevaCaracteresNoPermitidosExtendidos(valor))
		throw new Err('Tipo '+tipo+': lleva caracteres extraños')
	
	if (!this.sonTodoNumeros([trozos[1]]))
		throw new Err('Las matrículas tipo 3 llevan la segunda sección con números')
	if (!this.sonTodoLetras([trozos[3]]))
		throw new Err('Las matrículas tipo 3 llevan la tercera sección con letras')
		
	return valor
	}
Matricula.prototype.msgError=function(){
	return this._msgError || null
	}
Matricula.prototype.separaLetrasNumeros=function(valor){
	var temp=''; var ret=[]
	var anterior=null// admite letra, numero
	
	for (i=0; i<valor.length; i++){
		var cr=valor.substring(i, i+1)
		
		var esLetra=this.esLetra(cr)
		var esNumero=this.esNumero(cr)
		
		if (cr==' '){
			anterior=null
			ret.push(temp)
			temp=''
			}
		else if (esLetra){
			var nuevo='letra'
			
			if (anterior==null){
				temp=cr
				}
			else if (anterior==nuevo)
				temp+=cr
			else 	{
				ret.push(temp)
				temp=cr
				}
			anterior=nuevo
			}
		else {
			var nuevo='numero'
			if (anterior==null){
				temp=cr
				}
			else if (anterior==nuevo)
				temp+=cr
			else 	{
				ret.push(temp)
				temp=cr
				}
			anterior=nuevo			
			}
		}
	ret.push(temp)
	return ret
	}
validacionesEspeciales['lvw.matricula']=Matricula
//###############################
Texto.prototype.esLetra=function(l){
	if (l == 'Ñ')
		return false
	
	return this.letras.indexOf(l)>-1
	}
Texto.prototype.esNumero=function(l){
	return !isNaN(l)&& ((''+Number(l))==l)
	}
Texto.prototype.sonTodoLetras=function(valor){
	valor=''+valor
	for (i=0; i<valor.length; i++){
		var cr=valor.substring(i, i+1)
		if (!this.esLetra(cr))
			return false
		}
	return true
	}
Texto.prototype.sonTodoNumeros=function(valor){
	valor=''+valor
	for (i=0; i<valor.length; i++){
		var cr=valor.substring(i, i+1)
		if (!this.esNumero(cr))
			return false
		}
	return true
	}
//###############################
function Err(msg){
	this.msg=msg
	}
function Bastidor(){	
	Texto.call(this, 'Matricula') 
	this._msgError=null
}
Bastidor.prototype= new Texto
Bastidor.prototype.letras='ABCDEFGHIJKLMNOPQRSTUVWXYZ'
Bastidor.prototype.esValido=function(valor, campo){
	var ret, error
	try	{
		this.lanzaValidacion(valor, campo)
		return true
		}
	catch (e){
		this._msgError=e.msg
		return false
		}
	}
Bastidor.prototype.lanzaValidacion=function(valor, campo){
	valor=valor.toUpperCase()
	var cr9=valor.substring(8,9)
	
	if (valor.length!=17)
		throw new Err('Se esperaban 17 caracteres: hay ' + valor.length)
	else if (!this.esLetra(cr9))
		throw new Err('Se esperaba una letra en la posición 9')
}
validacionesEspeciales['lvw.bastidor']=Bastidor
Bastidor.prototype.msgError=function(){
	return this._msgError
	}
//~ var xconsole=null
//~ function print(l){
	//~ if (xconsole==null){
		//~ try {
			//~ console.info('Probando consola')
			//~ xconsole=true
			//~ }
		//~ catch(e){
			//~ xconsole=false
			//~ }
		//~ }
		
	//~ if (xconsole)
		//~ console.info(l)
	//~ else
		//~ WScript.echo(l)
	//~ }
//~ //###############################
//~ function lanzaPruebas(literal, lista){
	//~ var m=new Matricula()
	//~ print(literal)
	//~ for (var i=0; i<lista.length; i++){
		//~ print(lista[i] + ' -> ' +m.esValido( lista[i]) )
		//~ }
	//~ print('------------------------------------')
	//~ }
//###############################
//~ print (new Matricula().separaLetrasNumeros('M 999999'))
//~ lanzaPruebas('Pruebas Tipo 1: incorrectas', ['Ñ999999'])
//~ lanzaPruebas('Pruebas Tipo 1: CORRECTAS (deben dar todas OK)', ['ZS999999', 'M 999999','MS999999','AV999999', 'Ñ999999'])
//~ lanzaPruebas('Pruebas Tipo 1: INCORRECTAS (deben dar todas errores)', ['999999', 'MST 99999'])

//~ lanzaPruebas('Pruebas Tipo 2: CORRECTAS (deben dar todas OK)', ['ZS9999ZS', 'M 9999AA', 'AV9999A'])
//~ lanzaPruebas('Pruebas Tipo 3: CORRECTAS (deben dar todas OK)', ['9999ZZZ', 'M 9999AA', 'AV9999A'])
//~ lanzaPruebas('Pruebas Tipo 1: INCORRECTAS (deben dar todas errores)', ['9999Ñ', '9999Q'])

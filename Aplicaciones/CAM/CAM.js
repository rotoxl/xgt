ie=(document.all)? true:false 

//Miguel Angel (18/11/2010) Se supone que esto evita que guarde la pesta�a activa
ControlTAB.prototype.salvaDato=function(propiedad, valor){}
ControlTAB.prototype.recuperaDato=function(propiedad){}


//Miguel Angel (19/11/2010) API 1262
Buscador.prototype.aux_buscarPorCodigo=function(forzar, tEspera){
	tEspera=tEspera || 10
	var idbsp=this.idBsp()

	var self=this
	if (pendiente[idbsp]){//b�squeda en curso
		clearTimeout( pendiente[idbsp] )
		delete pendiente[idbsp]
		
		console.log(this.idBsp()+': cancelo b�squeda y lanzo otra')
		pendiente[idbsp]=setTimeout( function(){self.hazPeticionBuscarPorCodigo(forzar)}, tEspera)
		return
		}
	
	var eraValido=this.valido
	this.invalida() //de momento lo invalidamos para evitar condiciones de carrera
	
	this.miraSihayCambios(forzar)
	if (this.esPrimeraBusqueda){
		}
	else if (this.nulo){
		this.vaciaDescripcion()
		this.valida()
		}
	else if (this.hayCambios==false && eraValido) { //lo dejamos como estaba
		this.sustituyeDescripcion(this.descAnterior)
		this.valida()
		}

	if (this.esPrimeraBusqueda || (this.hayCambios && !this.nulo)) {
		this.cancelaBusqueda()
		pendiente[idbsp]=setTimeout( function(){self.hazPeticionBuscarPorCodigo(forzar)}, tEspera)
		}
	else
		this.cancelaBusqueda()
}

//Fin API 1262

//quitamos el efecto de fadding a los menús
jquerycssmenu.fadesettings.outduration=0.5  //ojo, no poner a 0, se estropean  los submen�es
jquerycssmenu.fadesettings.overduration=0.5//ojo, no poner a 0, se estropean  los submen�es


//quitamos que recuerde las pesta�as seleccionadas al volver a entrar en la aplicaci�n
Control.prototype.salvaDato=function(propiedad, valor){}

//quitamos que recuerde el ancho del �rbol
var _salvaDato=salvaDato
var salvaDato=function(nombre, propiedad, valor){
  if (nombre!="splitter") {_salvaDato(nombre, propiedad, valor)}
}
/*
var _recuperaDato=recuperaDato
var  recuperaDato=function(nombre, propiedad){
  if (nombre=="splitter") {return 20 }else{return _recuperaDato(nombre, propiedad)}
}
*/



Buscador.prototype.lanzaPrimeraBusqueda=function(){
	var bscPadre=this.bscsQueBloquea()
	if (this.ctl.obligatorio && bscPadre==null){ 
		//si es obligatorio hay que lanzar la búsqueda para cargarlo automáticamente cuando haya una sóla fila
		this.esPrimeraBusqueda=true
		this.buscarPorCodigo(false)
		}
	else if (this.completamenteLleno() && this.ctl.bloqueado){
		this.esPrimeraBusqueda=true
		this.buscarPorCodigo(false)
		}
	else if (this.completamenteLleno() && bscPadre==null){
		this.esPrimeraBusqueda=true
		this.buscarPorCodigo(false)
		}
	else
		console.debug(this.idBsp()+': no se lanza la bÃºsqueda porque depende de otro:'+this.ctl.ds)
	} 
//****** BUSCADORES. ELIMINAR EL CONTENIDO DE "HIJOS" CUANDO SE ACTUALIZA EL "PADRE"
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
			
			if ( tramo.accion == 'rellenaDescripcion') {
				// if (this.esPrimeraBusqueda) continue
				
				var xbsc=buscadores[tramo.id]
				xbsc.rellenaCasillas(xbsc!=this, tramo.nombresCasillas, tramo.valores, tramo.descripcion, tramo.codigo, tramAnterior.codigo, tramAnterior.nombresCasillas.length)
				
				if (i==0 && !this.esPrimeraBusqueda){
					xbsc.propagaNulo( xbsc.listaHijos(this.ctl.colsBloqueadas), xbsc.ctl.colsBloqueadas )
					}
				}
			else if (tramo.accion == 'buscarPorCodigo') {		
				if (ctl.obligatorio){
					ctl.buscador.esPrimeraBusqueda=this.esPrimeraBusqueda
					ctl.buscador.vaciaEditablesyBuscarPorCodigo(true)
					}
				else if (ctl.buscador.completamenteLleno()){
					ctl.buscador.esPrimeraBusqueda=this.esPrimeraBusqueda
					ctl.buscador.vaciaEditablesyBuscarPorCodigo(true)
					}
				///////////////
				}
			}
		}
	this.esPrimeraBusqueda=false
	}
//Esta func es nueva: cuando un buscador cambia, los que dependen de �l vac�an sus c�digos y recupera por herencia la parte bloqueada. Entonces, se busca
Buscador.prototype.vaciaEditablesyBuscarPorCodigo=function(forzar){
	if (this.ctl.bloqueado){
		}
	//~ else if (this.esPrimeraBusqueda && !this.ctl.txts[0].value==''){
		//~ console.debug('*'+this.ctl.txts[0].value+'*')
		//~ }
	else if (this.esPrimeraBusqueda){
		}
	else {
		for (var i=0; i<this.ctl.txts.length; i++){
			if (i >= this.ctl.colsBloqueadas)
				this.rellenaCasilla(i, '')
			}
		}
	this.buscarPorCodigo(forzar)
	}
//para vaciar no s�lo la columna que se puso a nulo sino tambi�n las siguientes
Buscador.prototype.propagaNulo=function(lista, iCodigo){
	for (var i=0; i<lista.length; i++){
		var bsc=lista[i].buscador
		if (!bsc) break	
		
		for (var j=Math.min(this.ctl.txts.length, bsc.ctl.txts.length)-1; j<bsc.ctl.txts.length; j++){
			bsc.ctl.txts[j].value=''
			}
		bsc.ponNulo()
		bsc.vaciaDescripcion()
		}
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
			if (sig.colsQueBloquea==this.ctl.colsQueBloquea && sig.colsBloqueadas==this.ctl.colsBloqueadas)
				break
			}
		}
	
	return ret
	}
//**********************************************************************************

Formulario.prototype.valida=function(){
// datos obligatorios , tipos correctos (fechas y numeros)
        var retorno=true
        var esPrimeraOpcion=true
        var cambiarFoco=true
        for (var i=0;i< this.controles.length;i++){
                var ctl=this.controles[i]
                if(ctl.bloqueado)
                        continue
                if (ctl.tipo=='lvwOption'){
                        if (esPrimeraOpcion){
                                if (!this.validaEstaRelleno(ctl)){
                                        this.marcaCampoErroneo(ctl, "Debe seleccionar alguna opción", cambiarFoco)
                                        retorno=false}
                                else
                                        this.marcaCampoCorrecto(ctl)
                                }
                        esPrimeraOpcion=false
                        }
                else if (ctl.tipo=='bsc'){
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
				if (obj instanceof Numero)
					dato = dato.replace(/\./g,',');
                                
				if (obj.esValido(dato) ) {
					if (obj instanceof Numero) {
						ctlHTML.value = dato;
					}
                                        this.marcaCampoCorrecto(ctl) //TODO Esto puede ser redundante
                                        }
                                else{
                                        this.marcaCampoErroneo(ctl, obj.msgError(dato), cambiarFoco) //TODO Esto puede ser redundante
                                        retorno=false
                                        }
                                }
                        else
                                this.marcaCampoCorrecto(ctl)
                        }
                if (retorno==false)
                        cambiarFoco=false
        }
        return retorno
}

Frm.prototype.creaInput=function (tipo, clase, id, valor, maxlength, valorControl, grupoActivo, etiqueta, nombreInputRadio){
        var d
        if (tipo=='text' || tipo=='password') {
                d=document.createElement('INPUT')
                d.type=tipo
                if (valor) {
// Roberto
			valor = '' + valor
                        if (valor.indexOf('.') != -1 && ( clase.indexOf("lvwNumero") != -1 || clase.indexOf("lvwCurrency") != -1))
                                valor = valor.replace(/\./g,'');
// FIN Roberto
                        d.value=valor
                }
                }
        else if (tipo=='radio') {
                //http://cf-bill.blogspot.com/2006/03/another-ie-gotcha-dynamiclly-created.html
                nombreInputRadio=nombreInputRadio || 'grupoOpciones'+grupoActivo
                try     { //el *uto ie
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
                }
        return d
}

// Validación de campos numéricos
function SoloNumeros(evento,obj,tipo,validacion){
	var valor=obj.value;
	var codigo = teclaEvento(evento);
	var ctrlKey = evento.ctrlKey;
    
	if (!validacion){
		validacion="";
	}

	if (!ie && codigo==0)
	{
		return true;
	}

	//han pulsado un intro
	if (codigo==13) //han pulsado Intro
	{
		
		if (!chequearNumero(evento, obj,tipo,validacion))
		{
			omitirEvento(evento); //evento.keyCode=0;
			return false;

		}
		else return true;
	}

	//han tecleado un punto
	if (codigo==46)//si me teclean un punto lo sustituyo por la coma en el key up
	{
		if (valor.indexOf(",")!=-1 || validacion.search(/entero|telefono|ejercicio/i)!=-1) // Si ya hay coma o es un entero o un teléfono o un ejercicio no vale
		{
			omitirEvento(evento);
			return false;
		}
		else return true;
	}

	//han tecleado una coma
	if (codigo==44)
	{
		if (valor.indexOf(",")!=-1 || validacion.search(/entero|telefono|ejercicio/i)!=-1) // Si ya hay coma o es un entero o un teléfono o un ejercicio no vale
		{
			omitirEvento(evento);
			return false;
		}
		else return true;
	}

	//han tecleado un signo menos
	if (codigo==45) 
	{
		if (valor.indexOf("-")!=-1 || validacion.search(/positivo|telefono|porcentaje|ejercicio/i)!=-1) //|| valor.length>0 si ya hay o no es el primer caracter o es un positivo no vale
		{
			omitirEvento(evento); 
			return false;
		}
		else return true;
	}

	if (ie){
		var cursorPos=getCursorPos(obj);		
	} else {
		var cursorPos=obj.selectionStart;		
	}

	//han tecleado un signo mas
	if (codigo==43) 
	{
		if (valor.indexOf("+")!=-1 || cursorPos!=0 || validacion.search(/telefono/i)==-1) //|| valor.length>0 si ya hay o no es el primer caracter o no es un teléfono no vale
		{
			omitirEvento(evento); 
			return false;
		}
		else 
		  return true;
	}

	//han tecleado dos puntos (:)
	if (codigo==58) 
	{
		if (valor.indexOf(":")!=-1 || cursorPos!=2 || validacion.search(/hora/i)==-1) //|| valor.length>0 si ya hay o no es el tercer caracter o no es una hora no vale
		{
			omitirEvento(evento); 
			return false;
		}
		return true;
	}

	//si no es un número no lo acepto
	if((codigo!=8 && codigo != 9 && !(ctrlKey && codigo==99) && !(ctrlKey && codigo==118)) && (codigo < 48 || codigo > 57))
	{
		omitirEvento(evento);
		return false;
	}
	return true;

}

function getCursorPos(campo) { 
    if (document.selection) {// IE Support 
        campo.focus();                                        // Set focus on the element 
        var oSel = document.selection.createRange();        // To get cursor position, get empty selection range 
        oSel.moveStart('character', -campo.value.length);    // Move selection start to 0 position 
        campo.selectionEnd = oSel.text.length;                    // The caret position is selection length 
        oSel.setEndPoint('EndToStart', document.selection.createRange() ); 
        campo.selectionStart = oSel.text.length; 
    } 
    //return { start: campo.selectionStart, end: campo.selectionEnd }; 
	return campo.selectionStart; 
}  

function teclaEvento(evento){ 
	if (ie)
	{
		return evento.keyCode;
	}
	else {

		if (evento.keyCode==13)
			return 13;
		else return evento.charCode;
		//return evento.which;
	}

}

function omitirEvento(evento){ 
	if (ie)	evento.keyCode=0;
	else if (evento.preventDefault){
		evento.preventDefault();
		evento.stopPropagation();
	}
}

function chequearNumero(evento, obj,tipo,validacion) {
	var numstr=obj.value

	numstr=numstr.replace("€","");
	numstr=numstr.replace(" ","");
	numstr=numstr.replace(/\./g,"");
	
	if (numstr+"" == "undefined" || numstr+"" == "null" || numstr+"" == "")
		return true;

	if (numstr.match(/^(?:\+|-)?\d+(?:\,|)+\d*$/)!=null || validacion.search(/hora/i)!=null)
	{
		return true;
	}
	else{
		if (evento && evento.type=="keypress"){}
		else {
			//alert("valor no válido: "+numstr);
			obj.focus();
		}
		obj.select();
	}
}

function cambiarPuntoPorComa(obj){
	ultimo=obj.value.substring(obj.value.length-1);
	if (ultimo=='.')
	{
		obj.value=obj.value.substring(0, obj.value.length-1)+",";
		//obj.value=obj.value.split('.').join(',' );
	}
	return true;
}
Formulario.prototype._pintaControl=Formulario.prototype.pintaControl
Formulario.prototype.pintaControl = function ( ctl, onblur, onkeyup, onkeypress, esBsc, grupoActivo){
	var dom=this._pintaControl(ctl, onblur, onkeyup, onkeypress, esBsc, grupoActivo)
	if (dom.tagName.toUpperCase()=='INPUT' && dom.type.toUpperCase()=='TEXT'){
	  //dom.size=dom.maxLength+(dom.maxLength/10)  //Aumentamos un poco el tamaño de los inputs para que se vea todo el contenido
		dom.size=Math.min(dom.maxLength+(dom.maxLength/10), 100)  //Aumentamos un poco el tamaño de los inputs para que se vea todo el contenido
	}
    var x=-1;
	if (ctl.validacion) {
		x=ctl.validacion.search(/entero|telefono|hora|ejercicio/i);
    }
	if (ctl.validacion=='ejercicio'){dom.onblur=function(event){completarEjercicio(event, this)}}
	if (ctl.tipo=='lvwNumero'||ctl.tipo=='lvwCurrency'||x!=-1){
		//dom.onpaste=function(){return false}
		//dom.onkeypress=function(event){SoloNumeros(event, this, ctl.tipo, ctl.validacion)}
		//dom.onkeyup=function(event){cambiarPuntoPorComa(this)}
		//dom.onblur=function(event){chequearNumero(event, this)}
		if (ie){
			dom.attachEvent('onkeypress', function (event){SoloNumeros(event, dom, ctl.tipo, ctl.validacion)});
			dom.attachEvent('onkeyup', function (event){cambiarPuntoPorComa(dom)});
			dom.attachEvent('onblur', function (event){chequearNumero(event, dom, ctl.tipo, ctl.validacion)});
		}
		else {
			dom.addEventListener("keypress", function (event){SoloNumeros(event,  dom, ctl.tipo, ctl.validacion)}, false);
			dom.addEventListener("keyup", function (event){cambiarPuntoPorComa( dom)}, false);
			dom.addEventListener("blur", function (event){chequearNumero(event,  dom, ctl.tipo, ctl.validacion)}, false);
		}
	}
	return dom;
}
	
function msgErrorNumero(valor,digitos,decimales,negativo){
    valor=valor.replace(/\./g,'').replace(/,/,'.')
	if (isNaN(Number(valor))) return 'El dato introducido no es numérico'
    if (!negativo && Number(valor)<0) return 'El dato no admite cantidades negativas'
	if (valor.indexOf('.')!= -1){
	  var trozos=valor.split('.')
	  if (trozos[1].length>decimales) return 'El dato no admite más de ' + decimales + ' decimales'
	}
	//var valMax=Math.pow(10,(digitos-decimales-2))-Math.pow(10,decimales*(-1))
	maxEnteros=''
	maxDecimales='.'
	for(i=1;i<=digitos-decimales;i++){maxEnteros=maxEnteros+'9'}
	for(i=1;i<=decimales;i++){maxDecimales=maxDecimales+'9'}
	valMax=maxEnteros + ((decimales>0)?maxDecimales:'')
	return 'El valor excede el máximo permitido (' + formatNumber(valMax,decimales) + ')' 
}

function esNumValido(obj,valor,digitos,decimales,negativo){
	if(!obj.esNumeroValido(valor))
	    return false
    var signo=''
    if (negativo) signo='-?'
	valor=valor.replace(/\./g,'').replace(/,/,'.')
	var dec=''
//    for (i=1;i<=decimales;i++){dec=dec+i+','}
//    dec='(\\.\\d{' + dec.substring(0,dec.length-1) +'})?'
    if (decimales>1){
      dec='(\\.\\d{1,' + decimales +'})?'    
	}else{
      dec='(\\.\\d{' + decimales +'})?'    	
	}
    var expreg = new RegExp ('^' + signo + '\\d+' + dec + '$')
	return (expreg.test(valor) && valor<Math.pow(10,(digitos-decimales)))
}

function Porcentaje(){}
Porcentaje.prototype=new Numero
Porcentaje.prototype.esValido=function(valor){
	valorNum=valor.replace(/\./g,'').replace(/,/,'.') 
	return (esNumValido(this,valor,5,2) && valorNum>=0 && valorNum<=100)
}

Porcentaje.prototype.msgError=function(valor){
	return 'El valor debe estar comprendido entre 0 y 100 y tener como máximo dos decimales'
}

function Telefono(){}
Telefono.prototype=new Telefono
Telefono.prototype.esValido=function(valor){
    var er_telef=/^$|^\+[0-9]{11,}$|^[0-9]{9}$/
    var esValido=er_telef.test(valor)	
    return esValido
}
Telefono.prototype.msgError=function(valor){
	return 'El número de teléfono no tiene un formato válido. Los números nacionales deben tener nueve cifras y los internacionales hasta 19 cifras precedidos de un +' 
}

function Hora(){}
Hora.prototype=new Hora
Hora.prototype.esValido=function(valor){
    var er_hora=/^([0-1][0-9]|[2][0-3]):([0-5][0-9])$/
    var esValido=er_hora.test(valor)	
    return esValido
}
Hora.prototype.msgError=function(valor){
	return 'La hora introducida no tiene un formato válido (HH:MM)' 
}

function Positivo5_2(){}
Positivo5_2.prototype=new Numero
Positivo5_2.prototype.esValido=function(valor){
    return esNumValido(this,valor,5,2,false)
}

Positivo5_2.prototype.msgError=function(valor){
	return msgErrorNumero(valor,5,2,false)
}

function Positivo17_1(){}
Positivo17_1.prototype=new Numero
Positivo17_1.prototype.esValido=function(valor){
    return esNumValido(this,valor,17,1,false)
}

Positivo17_1.prototype.msgError=function(valor){
	return msgErrorNumero(valor,17,1,false)
}

function Positivo17_2(){}
Positivo17_2.prototype=new Numero
Positivo17_2.prototype.esValido=function(valor){
    return esNumValido(this,valor,17,2,false)
}

Positivo17_2.prototype.msgError=function(valor){
	return msgErrorNumero(valor,17,2,false)
}

function Positivo17_5(){}
Positivo17_5.prototype=new Numero
Positivo17_5.prototype.esValido=function(valor){
    return esNumValido(this,valor,17,5,false)
}

Positivo17_5.prototype.msgError=function(valor){
	return msgErrorNumero(valor,17,5,false)
}

function Positivo4(){}
Positivo4.prototype=new Numero
Positivo4.prototype.esValido=function(valor){
    return esNumValido(this,valor,4,0,false)
}

Positivo4.prototype.msgError=function(valor){
	return msgErrorNumero(valor,4,0,false)
}

function Numero17_2(){}
Numero17_2.prototype=new Numero
Numero17_2.prototype.esValido=function(valor){
    return esNumValido(this,valor,17,2,true)
}

Numero17_2.prototype.msgError=function(valor){
	return msgErrorNumero(valor,17,2,true)
}

function Ejercicio(){}
Ejercicio.prototype=new Ejercicio
Ejercicio.prototype.esValido=function(valor){
	if (valor.length!=4) {return false}
    return true
}
Ejercicio.prototype.msgError=function(valor){
	return 'Debe introducir el año completo o los últimos dos dígitos' 
}

function completarEjercicio(evento,obj){
  var valor=obj.value;
  var fecha=new Date();
  if (valor.length==2) {obj.value=fecha.getFullYear().toString().substring(0,2) + valor}
}
function Email(){
	Texto.call(this, 'E-mail') 
}
Email.prototype= new Texto
Email.prototype.esValido=function(valor){
	var pattern=/^([a-zA-Z0-9._-]+([+][a-zA-Z0-9._-]+){0,1}[@][a-zA-Z0-9._-]+[.][a-zA-Z]{2,6},)*$/
	return valor==='' || ((valor+',').match(pattern)!=null)
}
Email.prototype.msgError=function(valor){
	return 'El texto "' + valor + '" no es un dato válido de tipo E-mail.' 
}

var tipoErrorFecha;
function FechaPasada(){
  Fecha()
}
FechaPasada.prototype=new FechaPasada
FechaPasada.prototype.esValido=function(valor){
    tipoErrorFecha=1
	var retorno=esFechaCorrecta(valor)
    if (retorno) {
	  tipoErrorFecha=2
	  datosFecha=valor.split('/')
	  var fecha= new Date(datosFecha[2],datosFecha[1]-1,datosFecha[0])
	  var fechaHoy= new Date()
	  fechaHoy= new Date(fechaHoy.getFullYear(),fechaHoy.getMonth(),fechaHoy.getDate())
      retorno=fecha<=fechaHoy;
	}
	return retorno;
}

FechaPasada.prototype.msgError=function(valor){
	if (tipoErrorFecha==1) {
	    var fecha = new Fecha
        return  fecha.msgError(valor)
	}else{
	  return 'Debe introducir una fecha igual o anterior a la de hoy'
	}
}

function FechaFutura(){
  Fecha()
}
FechaFutura.prototype=new FechaFutura
FechaFutura.prototype.esValido=function(valor){
    tipoErrorFecha=1
	var retorno=esFechaCorrecta(valor)
    if (retorno) {
	  tipoErrorFecha=2
	  datosFecha=valor.split('/')
	  var fecha= new Date(datosFecha[2],datosFecha[1]-1,datosFecha[0])
	  var fechaHoy= new Date()
	  fechaHoy= new Date(fechaHoy.getFullYear(),fechaHoy.getMonth(),fechaHoy.getDate())
      retorno=fecha>=fechaHoy;
	}
	return retorno;
}

FechaFutura.prototype.msgError=function(valor){
	if (tipoErrorFecha==1) {
	    var fecha = new Fecha
        return  fecha.msgError(valor)
	}else{
	  return 'Debe introducir una fecha igual o posterior a la de hoy'
	}
}
// A�adido MJV705X
function CaracNumericos4(){
	Numero.call(this, 'CaracNumericos') 
}
CaracNumericos4.prototype= new Numero
CaracNumericos4.prototype.esValido=function(valor){
	var pattern=/^([0-9]){4}$/
	return this.esNumeroValido(valor) && this.esPositivo(valor) && this.esEntero(valor) && valor.match(pattern)!=null
	
	// valor==='' || ((valor+',').match(pattern)!=null)
}
CaracNumericos4.prototype.msgError=function(valor){
	return 'Se esperaba un codigo de 4 caracteres numericos'
}

// A�adido ICB 251010
function CaracNumericos16(){
	Numero.call(this, 'CaracNumericos') 
}
CaracNumericos16.prototype= new Numero
CaracNumericos16.prototype.esValido=function(valor){
	var pattern=/^([0-9]){16}$/
	return this.esNumeroValido(valor) && this.esPositivo(valor) && this.esEntero(valor) && valor.match(pattern)!=null
	
	// valor==='' || ((valor+',').match(pattern)!=null)
}
CaracNumericos16.prototype.msgError=function(valor){
	return 'Se esperaba un cédigo de 16 caracteres numéricos'
}





validacionesEspeciales['lvw.email']=Email
validacionesEspeciales['lvwCurrency.porcentaje']=Porcentaje
validacionesEspeciales['lvwNumero.porcentaje']=Porcentaje
validacionesEspeciales['lvw.telefono']=Telefono
validacionesEspeciales['lvw.hora']=Hora
validacionesEspeciales['lvwCurrency.positivo5_2']=Positivo5_2
validacionesEspeciales['lvwCurrency.positivo17_2']=Positivo17_2
validacionesEspeciales['lvwCurrency.positivo17_5']=Positivo17_5
validacionesEspeciales['lvwCurrency.numero17_2']=Numero17_2
validacionesEspeciales['lvwNumero.positivo17_5']=Positivo17_5
validacionesEspeciales['lvwNumero.positivo17_2']=Positivo17_2
validacionesEspeciales['lvwNumero.positivo17_1']=Positivo17_1
validacionesEspeciales['lvw.ejercicio']=Ejercicio
validacionesEspeciales['lvw.positivo4']=Positivo4
validacionesEspeciales['lvwFecha.fechapasada']=FechaPasada
validacionesEspeciales['lvwFecha.fechafutura']=FechaFutura
validacionesEspeciales['lvw.enteropositivo']=EnteroPositivo
validacionesEspeciales['lvw.caracnumericos4']=CaracNumericos4
validacionesEspeciales['lvw.caracnumericos16']=CaracNumericos16
Formulario.prototype.fnSustituyePuntoPorComa=function(){} //ojo: es necesario para que no se pegue con SoloNumeros()

// Fin de Validación de campos numéricos

function toInt(v){
	return Math.floor(Number(v))
	}
function borra(ctl){
	if (ctl) ctl.parentNode.removeChild(ctl)
	}
var validacionesEspeciales={}
function Numero(subclase){
	this.clase='Numero'
	this.msgErrorAdicional=''
	this.subclase=subclase
}
Numero.prototype.esValido=function(valor){
	return this.esNumeroValido(valor)
}
Numero.prototype.esNumeroValido=function(valor) {
	if (valor==null) return true
	
	valor=valor.trim()
	
	if (valor.startsWith('-'))
		valor=valor.substring(1)
	
	if (valor.indexOf('.')!=-1){
		gruposMiles=valor.split(/,/)[0].split(/\./)
		//verificamos que no se ha metido algo del estilo de 99.99 queriendo decir 99,99
		this.msgErrorAdicional='. Debe usar la coma como separador de decimal (y opcionalmente el punto para separador de miles).'
		for (var i=0;i<gruposMiles.length;i++){
		    var grupo=gruposMiles[i]
		    if (i==0){
			if(grupo.length>3 || grupo.length<1)
			    return false
			}
		    else{
			if(grupo.length!=3)
			    return false
			}
		    }

		this.msgErrorAdicional=''
		}
	valor=valor.replace(/\./g, '')
		.replace(/€/g, '')
		.replace(/,/g, '.')
	try 	{
		return ! isNaN( Number(valor) )
		}
	catch (e) { 
		return false
		}
}
Numero.prototype.esPositivo=function(valor){
	if (valor==null) return true
	valor=valor.replace(/\./g, '') 
	    .replace(/,/g, '.')
	return Number(valor)>=0
}
Numero.prototype.esEntero=function(valor){
	if (valor==null) return true
	if (valor.contains(',')) 
		return false

	valor=valor.replace(/\./g, '') 
	    .replace(/,/g, '.')
	valor=Number(valor)
	return Math.floor( valor) == valor
}
Numero.prototype.msgError=function(valor){
	return 'El número "'+valor+'" no es un dato válido del tipo "'+this.clase+ (this.subclase!=null?"."+this.subclase:'')+'"'+this.msgErrorAdicional
}
function formatNumber(num, numDecimales, simboloMoneda){
	numDecimales=numDecimales || 0
	simboloMoneda = simboloMoneda || ''
	if (simboloMoneda!='') simboloMoneda=' '+simboloMoneda
	
	num += '';
	var splitStr = num.split('.');
	var splitLeft = splitStr[0];
	var splitRight = splitStr.length > 1 ? ',' + splitStr[1] : ',00';
	
	if (numDecimales==0){
		splitRight=''
		}
	else {
		splitRight=splitRight.substring(0, numDecimales+1)
		while (splitRight.length<(numDecimales+1) )
			splitRight+='0'
		}
	var regx = /(\d+)(\d{3})/
	while (regx.test(splitLeft)) {
		splitLeft = splitLeft.replace(regx, '$1' + '.' + '$2')
		}
	return splitLeft + splitRight +simboloMoneda
	}
function formatoSalida(valor, formato, dom){
	if (valor==null || valor==='')
		return ''
	else if (typeof valor == 'string' && valor.startsWith('Error:')){//un error de gotta, columna desconocida o lo que sea
		//~ throw valor
		console.warn("'"+valor+"' no es de tipo numérico" )
		return valor
		}
	
	if (formato==undefined){
		if (isNaN(valor))
			return valor
		else
			return formatNumber(valor)
		}
		
	formato+=''
	
	if (formato=='b')//boolean
		valor= (Number(valor)==0?'No' : 'Sí')
	else if (formato=='fb'){ //formato blog
		formatearFechaBlog(valor, dom)
		return null
		}
	else if (formato=='hh')
		valor=formatearHora(valor)
	else if (formato=='fc')
		valor=formatearFechaCorta(valor)
	else if (formato=='fr')
		valor=formatearFechaRelativa(valor)
		
	else if (['m', '%'].contains(formato) || !isNaN(formato)){
		var tempvalor=Number(valor)
		if (isNaN(tempvalor)){
			//~ throw valor
			var msg="ERROR: '"+valor+"' no es de tipo numérico"
			console.warn(msg)
			return msg
			}
		valor=tempvalor
		
		var numDecimales=Number(formato)
		var simbolo=''
		
		if (formato=='m'){
			numDecimales=2
			simbolo='€'
			}
		else if (formato=='%'){
			numDecimales=2
			simbolo='%'
			}
		
		valor=formatNumber(valor, numDecimales, simbolo)
		}
	return valor
	}
function numDecimalesPortipo(tipo){
	if (esTipoNumericoConDecimales(tipo))
		return 2
	else if (esTipoNumerico(tipo))
		return 0
	else
		return 0
	}
//////////////////////////////////////
function Entero(){
	Numero.call(this, 'Entero') 
}
Entero.prototype= new Numero
Entero.prototype.esValido=function(valor){
	return this.esEntero(valor)
}
//////////////////////////////////
function Positivo(){
	Numero.call(this, 'Positivo') 
}
Positivo.prototype= new Numero
Positivo.prototype.esValido=function(valor){
	return this.esNumeroValido(valor) && this.esPositivo(valor)
}
//////////////////////////////////
function EnteroPositivo(){
	Numero.call(this, 'EnteroPositivo') 
}
EnteroPositivo.prototype= new Numero
EnteroPositivo.prototype.esValido=function(valor){
	return this.esNumeroValido(valor) && this.esPositivo(valor) && this.esEntero(valor)
}
//////////////////////////////////////
validacionesEspeciales['lvwNumero.numero']=Numero
validacionesEspeciales['lvwCurrency.numero']=Numero
validacionesEspeciales['lvwNumero.entero']=Entero
validacionesEspeciales['lvwCurrency.entero']=Entero
validacionesEspeciales['lvwNumero.positivo']=Positivo
validacionesEspeciales['lvwCurrency.positivo']=Positivo
validacionesEspeciales['lvwNumero.enteropositivo']=EnteroPositivo
validacionesEspeciales['lvwCurrency.enteropositivo']=EnteroPositivo
//////////////////////////////////////
function Texto(subclase){
	this.clase='String'
	this.subclase=subclase
	this.maxlength=-1 //novedad para permitir indicar la long máxima
}
Texto.prototype.esValido=function(valor){
	return true
}
Texto.prototype.msgError=function(valor){
	return 'El texto "'+valor+'" no es un dato válido del tipo "'+this.clase+(this.subclase!=null?"."+this.subclase:'')+'"'
}
//////////////////////////////////////
function Nif(){
	Texto.call(this, 'Nif') 
}
Nif.prototype= new Texto
Nif.prototype.esValido=function(valor){
	valor=valor.toUpperCase()
	if (valor.length<9){//rellename con ceros.
		var n = 9-valor.length
		for (i=0;i<n ;i++ )
			valor = "0"+valor
		}
	var pattern=/(X|\d{1})\d{7}\D{1}/
	if (valor.match(pattern)){
		var cadena="TRWAGMYFPDXBNJZSQVHLCKET"
		var dni = valor.substring(0,valor.length-1)
		var posicion = dni % 23
		var letra = cadena.substring(posicion,posicion+1)
		var let = valor.substring(valor.length-1);
		if (letra!=let.toUpperCase())
			return false
		else 
			return true
		}
	else 
		return false
}
validacionesEspeciales['lvw.nif']=Nif
////////////////////////////////////////
function Email(){
	Texto.call(this, 'E-mail') 
}
Email.prototype= new Texto
Email.prototype.esValido=function(valor){
	var pattern=/^([a-zA-Z0-9._-]+([+][a-zA-Z0-9._-]+){0,1}[@][a-zA-Z0-9._-]+[.][a-zA-Z]{2,6},)*$/
	return valor==='' || ((valor+',').match(pattern)!=null)
}
validacionesEspeciales['lvw.email']=Email
//////////////////////////////////////
function Nie(){
	Texto.call(this, 'NIE') 
}
Nie.prototype= new Texto
Nie.prototype.esValido=function(valor){
	return (/^[XxTt]{1}[0-9]{7}[a-zA-Z]{1}$/.test(valor))
}
validacionesEspeciales['lvw.nie']=Nie
////////////////////////////////////////
function Fecha(){
	this.clase='Fecha'
}
Fecha.prototype.esValido=function(valor){
	return esFechaCorrecta(valor)
	}
Fecha.prototype.msgError=function(valor){
	return '"'+valor+'" no es una fecha válida (admitidos: "dd/mm/yyyy" o "dd/mm/yyyy hh:mm:ss")'
}
//////////////////////////////////////
validacionesEspeciales['lvwFecha.']=Fecha
///////////////
var separadorfecha="/";
var separadorhora=":";
function esFechaCorrecta(valor){
	try {
		_formatearFecha(valor) 
		return true 
		}
	catch (e) {
		return false
		}
}
function formatearFecha(evento) {
	if (evento==null) evento=window.event
	var campo=evento.target || evento.srcElement
	try {
		var ret = _formatearFecha(campo.value)
		campo.value = ret
		quitaMarcaErroneo(campo.id)
		return true
		}
	catch (err) {
		return false
		}
}
function hazFechaCorrecta(fecha){
	var f=descomponFecha(fecha)
	return (f.dia+'').lpad('0', 2) +separadorfecha +(f.mes+'').lpad('0', 2) +separadorfecha + (f.anio+'').lpad('0', 2)
}
function descomponFecha(fecha){
	var ret
	fecha=fecha.trim()
	var temp=fecha.split(separadorfecha)

	var dia=Number(temp[0])
	var mes=Number(temp[1])
	if (temp[2].contains('.'))
		temp[2]=temp[2].replace('.', '')
	var anio=Number(temp[2])
	
	if (anio==0)
		anio=anioActual()
	else if (anio<61)
		anio=2000+anio
	else if (anio>2100)
		throw "año>2100"
	else if (anio>60 && anio<100)
		anio=1900+anio
	
	var d=new Date();
	d.setFullYear(anio, mes-1, dia)
	
	/* JavaScript admite que le metas 35/03/06 y lo convierte a 4/04/06 */
	if (mes != (d.getMonth()+1))
		throw "mes incorrecto"
	
	return {	'dia':dia,
			'mes':mes,
			'mesNombre':listaMeses[mes-1],
			'anio':anio}
	}
function _formatearFecha(cadena) {
	cadena=cadena.trim()
		.replace(/ /g, separadorfecha)
		.replace(/-/g, separadorfecha)
	if(cadena==='')
		return cadena
	if ( cadena.contains(separadorfecha)) {
		var completa=cadena.split(separadorfecha)
		var tamano=completa.length
		if (tamano==4) {
			var hora=completa.pop()
			var fecha=completa.join(separadorfecha)
			fecha=hazFechaCorrecta(fecha)
			hora=hazHoraCorrecta(hora)
			return fecha+" "+hora
			}
		else if (tamano==3) {
			if (cadena.contains(separadorhora)) {
				var hora=completa.pop()
				completa.push(anioActual())
				
				var fecha=completa.join(separadorfecha)
				fecha=hazFechaCorrecta(fecha)
				hora=hazHoraCorrecta(hora)
				return fecha+" "+hora;
				}
			else {
				var fecha=completa.join(separadorfecha)
				fecha=hazFechaCorrecta(fecha)
				return fecha
				}
			}
		else if (tamano==2) {
			completa.push( anioActual() )
			var fecha=completa.join(separadorfecha)
			fecha=hazFechaCorrecta(fecha)
			return fecha
			}
		else 
			throw new errorFecha()
		}
	else if (cadena.contains(separadorhora)) {
		var hora=hazHoraCorrecta(cadena)
		return hora
		}
	else  //viene cualquier otra cosa "hola"
		throw new errorFecha()
}
function hazHoraCorrecta(hora){
	hora=hora.trim()+':00' // rellenamos segundos a ceros si no vienen
	var ret
	var temp=hora.split(separadorhora)
	
	var h=Number(temp[0]);  var m=Number(temp[1]); var s=Number(temp[2])
	
	var d=new Date();
	d.setHours(h); d.setMinutes(m); d.setSeconds(s)
	
	if (   (h != d.getHours()) || (m != d.getMinutes())	)
		throw new errorFecha()
	
	return pon0(h)+separadorhora+pon0(m)+separadorhora+pon0(s)
	}
function errorFecha(){
	}
function anioActual(){
	var d=new Date();
	return d.getFullYear();
	}
/////////////////////////////////////////////////////
function creaFechaJavascript(sfecha){
	if (sfecha==null) return null
	var temp=sfecha.split(separadorfecha)
	var dia, mes, ano
	dia=temp[0]; mes=temp[1]; ano=temp[2]
	
	ano=ano.split('.')[0] // ¿viene con milisegundos?
	
	return new Date(mes+separadorfecha+dia+separadorfecha+ano)// en js es MES/dia/año
}
function formatearHora(fecha){
	if (fecha==null || fecha=='')
		return null
		
	var soloHora=fecha.split(' ')[1]
	return soloHora
	}
function formatearFechaBlog(fecha, dom){
	var f=descomponFecha(fecha)
	
	dom.appendChild(creaObjProp('div', {	className:'fechaBlog',
								hijos:[creaObjProp('span', {texto:f.dia}),
									//~ creaObjProp('span', {className:'mes', texto:f.mesNombre}), 
									//~ creaObjProp('span', {className:'anio', texto:f.anio})]}
								creaT(f.mesNombre),
								creaObjProp('br'),
								creaT(f.anio)
									]})
				)
	
	}
function formatearFechaCorta(fecha){
	if (fecha==null || fecha=='')
		return null
		
	var soloFecha=fecha.split(' ')[0]
	return hazFechaCorrecta( soloFecha) 
}
var listaMeses=['Ene','Feb','Mar','Abr','May','Jun','Jul','Ago','Sep', 'Oct', 'Nov', 'Dic']
var listaDias=['Dom','Lun','Mar','Mi','Jue','Vie','Sab']
function formatearFechaRelativa(xfecha){	
	if (xfecha==null || xfecha=='')
		return null
		
	var dia, mes, ano
	var hora, minuto, segundo
	
	var fecha=creaFechaJavascript(xfecha)
	
	dia=fecha.getDate()
	mes=fecha.getMonth(); var mesEnCristiano=mes+1
	ano=fecha.getFullYear()
	
	hora=fecha.getHours()
	minuto=fecha.getMinutes()
	segundo=fecha.getSeconds()
	
	var horaCompleta=pon0(hora)+':'+pon0(minuto)
	var hoy=new Date()
	
	var difDias=xdifDias(hoy,fecha)
	var nombreDia=listaDias[fecha.getDay()]
	var nombreMes=listaMeses[mes]
	
	var difAnos=hoy.getFullYear()-ano
	var difMeses=hoy.getMonth()-mes
	
	if (difDias>=-1 && difDias<=2){
		if (hoy.getDate()==dia)
			return 'Hoy, '+horaCompleta
		else
			return 'Ayer, '+horaCompleta
		}
	else if (difDias>0 && difDias<8){	
		return nombreDia+' '+ pon0(dia)+', '+horaCompleta // Martes 18, 22:15
		}
	else if (difAnos==0){
		if (difMeses==0){
			return nombreDia+', '+ pon0(dia) //martes 24
			}
		else {
			return pon0(dia)+' '+nombreMes //18 Abr
			}
		}
	else
		return pon0(dia)+'/'+pon0(mesEnCristiano)+'/'+ano
}
function xdifDias(hoy, fecha){
	return (hoy-fecha)/(1000*3600*24)
	}
function pon0(num){
	if (num<10)
		return '0'+num
	else
		return num
	}
/////////////////////////////////////////////////////
function esTipoNumerico(ttipo){
	var tipo=ttipo.toLowerCase()
	return 	tieneTipo(tipo,'lvwcurrency') 	||
			tieneTipo(tipo,'lvwlong') 	|| 
			tieneTipo(tipo,'lvwdouble') 	||
			tieneTipo(tipo, 'lvwnumero')
	}
function esTipoNumericoConDecimales(ttipo){
	var tipo=ttipo.toLowerCase()
	return 	tieneTipo(tipo,'lvwcurrency') 	||
			tieneTipo(tipo,'lvwdouble')
	}
function tieneTipo(cadena, buscar){
	return (' '+cadena+' ').contains(' '+buscar+' ')
	}
function replaceAll (cadena, car, reemplazo) {
	if (car.length == 0) return cadena
	
	var idx = cadena.indexOf(car)
	while (idx > -1)  {
		cadena = cadena.substring(0, idx)+reemplazo+cadena.substr(idx+car.length)
		idx = cadena.indexOf(car)
		}
	return cadena
	}

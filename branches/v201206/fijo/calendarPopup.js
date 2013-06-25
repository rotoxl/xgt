/*********************************************************************************************************************
Basado en "Date picker written by Mark Wilton-Jones 5-8/10/2002"

Please see http://www.howtocreate.co.uk/jslibs/ for details and a demo of this script
Please see http://www.howtocreate.co.uk/jslibs/termsOfUse.html for terms of use
*********************************************************************************************************************/
function Calendario(evento, controlRetorno, valorInicial){
	this.evento=evento || window.event
	
	new Evt(evento).consume()
	this.controlRetorno=controlRetorno
	
	var curDate=new Date()
	curDate.setDate(1)
	
	this.fechaActual=curDate
	
	if (valorInicial=='' || !esFechaCorrecta(valorInicial)) 
		this.ponFechaDesdeFecha(new Date)
	else
		this.ponFechaDesdeCadena( valorInicial )
	
	this.redibuja()
	}

Calendario.prototype.listaMeses=['Ene','Feb','Mar','Abr','May','Jun','Jul','Ago','Sep','Oct','Nov','Dic']
Calendario.prototype.listaDias=['Do','Lu','Ma','Mi','Ju','Vi','Sa']
Calendario.prototype.semanaEmpieza=1 //0=Domingo, 1=Lunes
Calendario.prototype.textoHoy='Hoy'
Calendario.prototype.sacaDia=function(i){
	if (i>6)
		i-=7
	return Calendario.prototype.listaDias[i]
	}
Calendario.prototype.ponFechaDesdeFecha=function(nuevaFecha){
	this.fechaActual.setYear(nuevaFecha.getFullYear())
	this.fechaActual.setDate(nuevaFecha.getDay())
	this.fechaActual.setMonth(nuevaFecha.getMonth())
	}
Calendario.prototype.ponFechaDesdeCadena=function(cadena){
	if (cadena!='') {
		var nuevaFecha=new Date()
		if (cadena.contains(' '))
			cadena=cadena.split(' ')[0]
		var arr=cadena.split('/')
		nuevaFecha.setYear (arr[2]); nuevaFecha.setMonth (arr[1]-1); nuevaFecha.setDate (arr[0])
		
		if (nuevaFecha != "Invalid Date") 
			this.ponFechaDesdeFecha(nuevaFecha)
		}
	}
Calendario.prototype.cierraCalendario=function(){
	ocultaCalendario()
	}
function ocultaCalendario(){
	var datePickerHtmlNode = control("datePicker")
	if (datePickerHtmlNode) datePickerHtmlNode.style.display='none'
}
//////////////////
Calendario.prototype.restaAnio=function(){this.sumaRestaAnio(-1)}
Calendario.prototype.sumaAnio=function(){this.sumaRestaAnio(+1)}
Calendario.prototype.sumaRestaAnio=function(v){
	this.fechaActual.setYear( this.fechaActual.getFullYear() + v )
	this.redibuja()
	}
Calendario.prototype.restaMes=function(){this.sumaRestaMes(-1)}
Calendario.prototype.sumaMes=function(){this.sumaRestaMes(+1)}
Calendario.prototype.sumaRestaMes=function(v){
	var curDate=this.fechaActual
	curDate.setDate(1) //para evitar problemas con el 30/02, etc
	if (v<0){
		if (curDate.getMonth()>0) 
			curDate.setMonth( curDate.getMonth() + v )
		else { 
			curDate.setMonth(11)
			curDate.setYear( curDate.getFullYear() + v )
			}
		}
	else {
		if (curDate.getMonth() < 11 ) 
			curDate.setMonth( curDate.getMonth() + v )
		else { 
			curDate.setMonth(0)
			curDate.setYear( curDate.getFullYear() + v )
			}
		}
	this.redibuja()
	}
Calendario.prototype.tipoDia=function(d,m,a){
	var x= new Date()
	x.setYear(a); x.setMonth(m-1); x.setDate(d)
	
	var numdia=x.getDay()
	
	return (numdia==0?' festivo':'')+((numdia==0 || numdia==6)?' finDeSemana':'')
	}
Calendario.prototype.redibuja=function() {
	var self=this
	var curDate=this.fechaActual
	
	var tabla=creaObjProp('table')
	var thead=creaObjProp('thead', {className:'calendario', hijos:[
		creaObjProp('tr', {className:'calendario navegacion', hijos:[
			creaObjProp('th', {texto:'«', className:'op', onclick:function(){self.restaAnio()} }),
			creaObjProp('th', {texto:'‹', className:'op', onclick:function(){self.restaMes()} }),
			
			creaObjProp('th', {texto:this.listaMeses[curDate.getMonth()]+' '+curDate.getFullYear(), colSpan:3}),
			
			creaObjProp('th', {texto:'›', className:'op', onclick:function(){self.sumaMes()} }),
			creaObjProp('th', {texto:'»', className:'op', onclick:function(){self.sumaAnio()} })
			]}),
		creaObjProp('tr', {className:'calendario cabSemana', hijos:[
			creaObjProp('th', {texto: 	this.sacaDia(Calendario.prototype.semanaEmpieza+0) }),
			creaObjProp('th', {texto:	this.sacaDia(Calendario.prototype.semanaEmpieza+1) }),
			creaObjProp('th', {texto:	this.sacaDia(Calendario.prototype.semanaEmpieza+2) }),
			creaObjProp('th', {texto:	this.sacaDia(Calendario.prototype.semanaEmpieza+3) }),
			creaObjProp('th', {texto:	this.sacaDia(Calendario.prototype.semanaEmpieza+4) }),
			creaObjProp('th', {texto:	this.sacaDia(Calendario.prototype.semanaEmpieza+5) }),
			creaObjProp('th', {texto:	this.sacaDia(Calendario.prototype.semanaEmpieza+6) })
			]})
			]})
	
	var tbody=creaObjProp('tbody')
	
	var tr=creaObjProp('tr')
	tbody.appendChild(tr)
	
	var mes=curDate.getMonth()+1
	var anio=curDate.getFullYear()

	var numDias=this.numDiasPorMes(curDate.getMonth(), curDate.getFullYear())
	for ( var x = 1; x <= numDias;  x++ ){
		curDate.setDate(x)
		
		var numdia=Number(curDate.getDay())
		var diaSemana=this.sacaDia(numdia)
		
		if ( curDate.getDay()==Calendario.prototype.semanaEmpieza ) {
			tr=creaObjProp('tr')
			tbody.appendChild(tr)
			}
		else if( x == 1){
			this.meteDiasMesAnterior(tr, numdia, curDate.getMonth()-1, curDate.getFullYear())
			}
		
		var casillaDia=creaObjProp('td', {className:'calendario dia'+this.tipoDia(x, mes, anio), texto:x, onclick:this.fnRetorno(x, mes, anio)})
		if (this.esHoy(x))
			ponEstilo(casillaDia, 'today')
		tr.appendChild(casillaDia)
		}
	if (tr.childNodes.length<7)
		this.meteDiasMesPosterior(tr, tr.childNodes.length, curDate.getMonth()+1, curDate.getFullYear())
	tbody.appendChild(tr)
	
	var hoy=new Date()
	tbody.appendChild( creaObjProp('tr', {hijo: creaObjProp('td', {colSpan:7, className:'op', texto:Calendario.prototype.textoHoy, onclick:this.fnRetorno(hoy.getDate(), hoy.getMonth()+1, hoy.getFullYear())})} ))
	
	tabla.appendChild(thead)
	tabla.appendChild(tbody)
	
	var cont=control('datePicker') 
	if (!cont){
		cont=creaObjProp('span', {className:'datePicker', id:'datePicker', onmouseover:function(){sigueMostrandoMenu()},
													 onmouseout:function(){ocultaMenu(this.id)}	})										
		document.body.appendChild(cont)
		}
	borraHijos(cont)
	cont.appendChild(tabla)
		
	this.muestraCalendario()
}
Calendario.prototype.meteDiasMesAnterior=function(tr, cuantos, mes, anio){
	if (mes==-1){
		mes=11
		anio--
		}	
	var numDias=this.numDiasPorMes(mes, anio)
	mes++
	
	var diaInicio=numDias
	var dd=new Date(); dd.setYear(anio); dd.setMonth(mes-1); dd.setDate(diaInicio)
	while (dd.getDay()>Calendario.prototype.semanaEmpieza){
		diaInicio--
		dd.setDate(diaInicio)
		}
		
	for (var x=diaInicio; x<=numDias; x++){
		console.log(x+'-'+mes+'-'+anio + ' -> ' +this.tipoDia(x, mes, anio))
		var casillaDia=creaObjProp('td', {className:'calendario dia otroMes'+this.tipoDia(x, mes, anio), texto:x, onclick:this.fnRetorno(x, mes, anio)})
		tr.appendChild(casillaDia)
		}
	}
Calendario.prototype.meteDiasMesPosterior=function(tr, cuantos, mes, anio){
	if (mes==12){
		mes=0
		anio++
		}	
	var numDias=this.numDiasPorMes(mes, anio)
	mes++
	for (var x=1; x<=7-cuantos; x++){
		var casillaDia=creaObjProp('td', {className:'calendario dia otroMes'+this.tipoDia(x, mes, anio), texto:x, onclick:this.fnRetorno(x, mes, anio)})
		tr.appendChild(casillaDia)
		}
	}
Calendario.prototype.numDiasPorMes=function(mes, anio){
	return [31,((!( anio % 4 ) && ( ( anio % 100 ) || !( anio % 400 ) ))?29:28),31,30,31,30,31,31,30,31,30,31] [mes]
	}
Calendario.prototype.fnRetorno=function(d,m,y){
	var self=this
	return function(){self.retorno(d,m,y)}
	}

Calendario.prototype.muestraCalendario=function (){
	var nodo = control('datePicker') 
	if (nodo.offsetHeight<=0){//para que no se desplaze en ie 	
		var clientX=this.evento.clientX
		var clientY=this.evento.clientY
			
		if (clientX!=null) 	nodo.style.left = clientX-10 + 'px'
		if (clientY!=null) 	nodo.style.top = clientY-10 + 'px'
		nodo.style.display='block'
		}
}
Calendario.prototype.esHoy=function(x){
	var d=new Date()
	return (this.fechaActual.getMonth()==d.getMonth() && this.fechaActual.getYear()==d.getYear() && x==d.getDate())
	}
Calendario.prototype.retorno=function(oDay, oMonth, oYear ) {
	var f=(oDay+'').lpad('0',2)+separadorfecha+(oMonth+'').lpad('0',2)+separadorfecha+oYear
	
	var ret=control(this.controlRetorno)
	ret.value=f
	
	this.cierraCalendario()
	ponFoco(ret)
	}
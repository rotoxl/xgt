/* Altura del Splitter */
/*
function sacaH(){
	var altoBotoneraArriba=control('botonera').clientHeight
	return (xClientHeight()-altoBotoneraArriba+dif) - 125+4
	}
function sacaW(){
	//var anchoBotoneraDisenho=(disenhador && disenhador.modoDisenho?control('botoneraDisenho').clientWidth : 0)
	var anchoBotoneraDisenho=0
	// return xClientWidth()+dif-anchoBotoneraDisenho
	return $('#logo').width()+dif-anchoBotoneraDisenho
	} 
function sacaPosBarraV(){
	var cont=control('sepSplitterV')
	if (exp.scrollVertical)
		return cont.offsetLeft+1
	else
		return cont.offsetTop
	}
////////////////////////////////////////////	
*/

	
	
/*////////////////////////////////////*/
Control.prototype.toDOM_9=function(){
	return
	
	
	//~ console.debug('Control 9 sustituido por una imagen de Homer')
	
	this.dom= creaObjProp('img', {className:'img', id:this.id, 
						'style.zIndex':0, 
						'style.left':this.izq,
						'style.top':this.tope,
						'style.width':this.ancho,
						'style.height':this.alto,
						src:'http://www.simpsoncrazy.com/apps/gadgets/simpquotes/images/homer.gif'})
	
	}
	
////////////////////////////////////////////	
	/*
ModoDetalle.prototype.cargarControles2=ModoDetalle.prototype.cargarControles
ModoDetalle.prototype.cargarControles=function (divContenedor, contenedor, pestActiva){
	//~ if (!this.yaInicializado){
		this.tareasPreCarga()
		//~ this.yaInicializado=true
		//~ }
	this.cargarControles2(divContenedor, contenedor, pestActiva)
	}*/
//////////////////////////////////////////	
var listaBloqueos=[ ['70', ['71', '77']] ] // al cambiar 70, lanzamos la carga de 71 y 77
ModoDetalle.prototype.tareasPreCarga=function(){
	for (var l=0; l<listaBloqueos.length; l++){
		var lista=listaBloqueos[l]
		
		var lanzador=lista[0]
		var temp_recargar=lista[1]
		var recargar=temp_recargar
		if (! (temp_recargar instanceof Array)){
			recargar=[temp_recargar]
			}
		
		Control.prototype['toDOM_'+lanzador]=function(){
			for (var i=0; i<recargar.length; i++){
				var id=recargar[i]
				$(this.dom).change( lanzaCargaRelacionado(id) )
				}
			}
		}
	}
function lanzaCargaRelacionado(id){
	return function(){cargaRelacionado('control'+id)}
	}
///////////////////////////		
function cargaRelacionado(id){
	filtrarYRefrescarControl(id)
	}
function filtrarYRefrescarControl(id){
	var param=_filtrar()
	if (!param)
		return
	loadJSONDocPost('filtrar', param, function(){
			exp.mdActivo().getControl(id).vacia()
			})
	}
	

///////////////////////////
/////  Traducciones  ///////
//////////////////////////

Explorador.prototype.traducciones=function(){
	if (this.idioma=='es'){
		Frm.prototype.textoSi='Si'
		Frm.prototype.textoNo='No'
		Frm.prototype.textoAceptar='Aceptar'
		Frm.prototype.textoCancelar='Cancelar'

		Frm.prototype.textoTitulo='Introduzca los datos'
		FormularioMSG.prototype.textoTitulo='Mensaje de la aplicación'
			
		Formulario.prototype.textoCancelarLote='¿Desea ejecutar el resto del lote? Queda(n) %s trámite(s) por ejecutar.\nPulse "Aceptar" para continuar o "Cancelar" para detener la ejecución del resto del lote'
		
		FormularioBSC.prototype.textoBuscar='Buscar'
		////////
		Explorador.prototype.textoSeleccionarTodos='Seleccionar todos'
		Explorador.prototype.textoInvertirSeleccion='Invertir selección'
		Explorador.prototype.textoEjecutar='Ejecutar'
		Explorador.prototype.textoRevivir='Consultar datos del trámite'
		Explorador.prototype.textoReemitir='Volver a emitir documentación asociada'
		Explorador.prototype.textoDejarPendiente='Añadir a la lista de tareas pendientes'
		
		Explorador.prototype.textoVerInformacionFirmada='Ver información firmada'
		Explorador.prototype.textoVerInformacionFirma='Ver información de la firma'
		Explorador.prototype.textoValidarFirma='Validar firma'

		Calendario.prototype.listaMeses=['Ene','Feb','Mar','Abr','May','Jun','Jul','Ago','Sep','Oct','Nov','Dic']
		Calendario.prototype.listaDias=['Do','Lu','Ma','Mi','Ju','Vi','Sa']
		Calendario.prototype.semanaEmpieza=1 //0=Domingo, 1=Lunes
		Calendario.prototype.textoHoy='Hoy'
		}
	else if (this.idioma=='en'){
		Frm.prototype.textoSi='Yes'
		Frm.prototype.textoNo='No'
		Frm.prototype.textoAceptar='OK'
		Frm.prototype.textoCancelar='Cancel'

		Frm.prototype.textoTitulo='Fill data'
		FormularioMSG.prototype.textoTitulo='App says'
			
		Formulario.prototype.textoCancelarLote='Do you want to continue? There are %s uncompleted actions.\nPress "OK" to go on or "Cancel" to cancel them'
		
		FormularioBSC.prototype.textoBuscar='Search'
		////////
		Explorador.prototype.textoSeleccionarTodos='Select all'
		Explorador.prototype.textoInvertirSeleccion='Invert selection'
		Explorador.prototype.textoEjecutar='Accomplish'
		Explorador.prototype.textoRevivir='View data'
		Explorador.prototype.textoReemitir='Generate report'
		Explorador.prototype.textoDejarPendiente='Add to to-do list'

		Explorador.prototype.textoVerInformacionFirmada='View signed information'
		Explorador.prototype.textoVerInformacionFirma='View digital signature information'
		Explorador.prototype.textoValidarFirma='Verify digital signature'
		
		Calendario.prototype.listaMeses=['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec']
		Calendario.prototype.listaDias=['Su','Mo','Tu','We','Th','Fr','Sa']
		Calendario.prototype.textoHoy="Today"
		Calendario.prototype.semanaEmpieza=0
		}
	else if (this.idioma=='fr'){
		Frm.prototype.textoSi='Oui'
		Frm.prototype.textoNo='Pas'
		Frm.prototype.textoAceptar='OK'
		Frm.prototype.textoCancelar='Annuler'

		Frm.prototype.textoTitulo='Remplir les données'
		FormularioMSG.prototype.textoTitulo='App ditt'
			
		Formulario.prototype.textoCancelarLote='Voulez-vous continuer? Il ya %s actions inachevées.\nCliquez sur "OK" pour continuer ou "Annuler" pour les annuler'
		
		FormularioBSC.prototype.textoBuscar='Recherche'
		////////
		Explorador.prototype.textoSeleccionarTodos="Sélectionner l'ensemble"
		Explorador.prototype.textoInvertirSeleccion='Inverser la sélection'
		Explorador.prototype.textoEjecutar='Acomplir'
		Explorador.prototype.textoRevivir='Voir les données'
		Explorador.prototype.textoReemitir='Générer un rapport'
		Explorador.prototype.textoDejarPendiente='Ajouter à la liste de tâches'

		Explorador.prototype.textoVerInformacionFirmada='Voir signé informations'
		Explorador.prototype.textoVerInformacionFirma='Afficher les informations de signature numérique'
		Explorador.prototype.textoValidarFirma='valider les signatures numériques'
		
		Calendario.prototype.listaMeses=['Jan','Fév','Mar','Avr','Mai','Juin','Jui','Aoû','Sep','Oct','Nov','Déc']
		Calendario.prototype.listaDias=['Di','Lu','Ma','Me','Je','Ve','Sa']
		Calendario.prototype.textoHoy="Aujourd'hui"
		Calendario.prototype.semanaEmpieza=1
		}
	
	Explorador.prototype.textoTTPbtnDisenhadorON='[Diseño activo] Desactivar diseño'; Explorador.prototype.textoTTPbtnDisenhadorOFF='[Diseño inactivo] Activar diseño'
	Explorador.prototype.textoTTPbtnDepuradorON='[Depuración activa] Desactivar depuración'; Explorador.prototype.textoTTPbtnDepuradorOFF='[Depuración inactiva] Activar depuración'
	Explorador.prototype.textoTTPbtnConstructor='Mapa de tramitación'
	Explorador.prototype.textoTTPbtnMonitorSQL='Monitor de la aplicación'
	Explorador.prototype.textoTTPbtnRecargarAplicacion='Recargar aplicación'
		
	Explorador.prototype.textoTTPbtnRefrescar='Actualizar'
	Explorador.prototype.textoTTPbtnSalir='Salir'
	Explorador.prototype.textoTTPbtnCambiarContraseña='Configuración de mi cuenta de usuario'
	}
Explorador.prototype.tareasPostCarga=function(){
	this.traducciones()
	}

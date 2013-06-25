var comilla="'" //$NON-NLS-1$
var key=icoGotta+'dic.key'

function DiccionarioV(datos){
	this.tablas=new Coleccion()
	for (var i=0; i<datos.length; i++){
		var t=new TablaV(datos[i])
		this.tablas.add(t, t.nombre)
		}
	}
DiccionarioV.prototype.getColumna=function(tablaColumna){
	var temp=tablaColumna.split('.')
	var ntabla=temp[temp.length-2]
	var ncolumna=temp[temp.length-1]
	
	var t=this.tablas.get(ntabla)
	try {
		return t.columnas.get(ncolumna)
		}
	catch (e) {
		return null
		}
}
DiccionarioV.prototype.getCampo=function(tablaCampo){
	var temp=tablaCampo.split('.')
	var t=this.tablas.get(temp[0])
	return t.campos.get(temp[1])
}
////////
function Coleccion(){
	this.dic={}
	this.arr=[]
	this.length=0
	}
Coleccion.prototype.add=function(obj, id){
	this.arr.push( obj )
	
	this.dic[id.toLowerCase()]=this.arr.length-1	//por nombre
	this.dic[this.arr.length-1]=this.arr.length-1	//por índice
	
	this.length=this.arr.length
	}
Coleccion.prototype.remove=function(id){
	var indice=this.indice(id)
	
	this.arr[indice]=null //OJO, no se puede hacer splice porque entonces se joden los índices del dic
	this.dic[ indice ]=null
	this.dic[ id.toLowerCase() ]=null
	
	this.length=this.arr.length
	}
Coleccion.prototype.replace=function(id, obj){
	var indice=this.indice(id)
	this.arr[indice]=obj
}
Coleccion.prototype.get=function(id){
	var idx=this.indice(id)
	if (idx==null)
		return null
	return this.arr[ idx ]
	}
Coleccion.prototype.indice=function(id){
	return this.dic[(id+'').toLowerCase()]
	}
Coleccion.prototype.datos=function(){
	return this.arr
}
Coleccion.prototype.keys=function(){
	var keys=[]
	for (var k in this.dic){
		if (this.dic[k]==null || typeof this.dic[k]=='function'){
			/*pass*/}
		else if (!isNumber(k))
			keys.push(k)
		}
	return keys
}
Coleccion.prototype.JSON=function(){
	var ret=[]
	for (var i=0; i<this.arr.length;i++){
		var fila=this.arr[i]
		if (typeof fila!='function' && fila!=null) 
			ret.push( fila.JSON() )
		}
	return ponCorchete(ret.join(','))
}
Coleccion.prototype.toString=function(){
	return '['+this.keys().join(',')+']'
}
////////////
function isNumber(n){
	return Number(n).toString()!="NaN"
}
////////////////////////////////
function TablaV(dic){ //dic={nombre, lectura}
	this.nombre=	dic.nombre
	this.id=		dic.nombre
	this.descripcion=	dic.descripcion
	this.lectura1=	dic.lectura1
	this.lecturaN=	dic.lecturaN
	this.escritura=	dic.escritura
	this.columnabusqueda=	dic.columnabusqueda
	this.columnadescripcion=	dic.columnadescripcion
	this.usp=		dic.usp
	
	this.esNuevo=	dic.esNuevo || false
	this.esModif=	dic.esModif || false
	this.esBorrado= 	false
	
	this.clave=	dic.clave
	
	dic.errores=	dic.errores || ''
	dic.columnas=dic.columnas || []
	dic.campos=dic.campos || []
	dic.referencias=dic.referencias || []
	
	this.icono=(dic.errores!='' ? './fijo/dic.hayError' : null )
	this.errores= dic.errores || ''	
	this.campos=new Coleccion()
	this.columnas=new Coleccion()
	
	for (var i=0; i<dic.columnas.length; i++){
		var col=dic.columnas[i]
		var nombre=col.nombre.toLowerCase()
		col=new ColumnaV( dic.columnas[i], this)
		this.columnas.add(col, nombre)
		
		var campoDeCol=new CampoV(col, this)
		this.campos.add(campoDeCol, nombre)
		}
		
	var lista=[dic.campos, dic.referencias]
	for (var l in lista){
		for (var i=0; i<lista[l].length; i++){
			var fila=lista[l][i]
			if (typeof fila=='function' || fila==null)
				continue
				
			var nombre=fila.nombre || fila.cPrinc || fila.campoPrinc
			
			if (this.campos.get(nombre)!=null) {
				var cam=this.campos.get(nombre)
				cam.completa(fila, this)
				}
			else  {
				var cam=new CampoV( fila, this)
				this.campos.add(cam, cam.nombre)
				}
			}
		}
	marcaTablaGotta(this)
}
TablaV.prototype.getClave=function(){
	if (this.clave==null)
		return null
	else
		return this.campos.get(this.clave)
}
TablaV.prototype.guardar=function(idAnterior){
	var op='Tabla'
	var params= {	'accion': 'diccionarioFisico', 'aplicacion': aplicacion, 'operacion':op, 'datos': ponCorchete(this.JSON()) }
	
	var fnGuardadoOK=function(resp){
		resp=xeval(resp, false)
		if (resp.tipo=='error') {
			alert("Se ha producido un error al añadir la tabla al diccionario. Posiblemente esta tabla tiene relaciones con otras que deben ser añadidas antes.\nEl error que se ha producido ha sido:\n"+resp.msg)
			return
			}
			
		if (c.esNuevo){
			dicV.tablas.add(c, c.nombre)
			c.esNuevo=false
			}
		else if (c.esBorrado) {
			var indice=dicV.tablas.indice(c.nombre)+1
			
			if (dicV.tablas.get(indice)!=null)
				idAnterior=dicV.tablas.get(indice).id //para que me carge alguna y no se me quede la pantalla con los datos de la recién borrada
			
			dicV.tablas.remove(c.nombre)
			}
		else
			dicV.tablas.replace(idAnterior, c)
		
		editorDiccionario.pintaTablasVirtuales()
		editorDiccionario.pintaTablasFisicas()
		editorDiccionario.cargaTablasFV(idAnterior)
		}
	var c=this
	loadJSONDocPost ( 'json', params, fnGuardadoOK )
}
TablaV.prototype.vacia={'esNuevo':true, 'nombre':'', 'descripcion':null, 'lectura1':null, 'lecturaN':null, 'escritura':null, 'columnadescripcion':null, 'columnabusqueda':null, 'clave':null, 'icono':null, 'errores': '', 'columnas':[], 'campos':[], 'referencias':[] }
TablaV.prototype.borrar=function(){
	//marcamos todos sus campos y columnas como borrados
	this.esBorrado=true
	
	for (var i=0; i<this.columnas.length; i++) 
		this.columnas.get(i).esBorrado=true
	
	for (var i=0; i<this.campos.length; i++) 
		this.campos.get(i).esBorrado=true
		
	this.guardar()
}
TablaV.prototype.editar=function(idAnterior){
	var xfrm=new Formulario( this.datosFRM(), ponNuevoBloqueo() )
	
	xfrm.onAceptar=function(){
		var xfrm=getFrmActivo()
		var ret = xfrm.recogeValoresFRMDic()
		
		var t=new TablaV(ret)
		t.esModif=true
		t.esNuevo=idAnterior==null
		
		t.campos=new Coleccion(); t.columnas=new Coleccion()
		
		//calcamos los campos y columnas de la original
		var tAnterior=dicV.tablas.get(idAnterior)
		if (tAnterior) {
			for (var i=0; i<tAnterior.columnas.length; i++) {
				var json=tAnterior.columnas.get(i).JSON()
				var nc=new ColumnaV( evalP(json), t)
				nc.esNuevo=t.esNuevo
				t.columnas.add(nc, nc.nombre)
				
				var campoDeCol=new CampoV(nc, nc.tabla)
				nc.tabla.campos.add(campoDeCol, nc.nombre)
				}
			for (var i=0; i<tAnterior.campos.length; i++) {
				var cam=tAnterior.campos.get(i)
				if (cam.tRef!=null || cam.columnas.length>1){ 
					var json=cam.JSON()
					var nc=new CampoV( evalP(json), t)
					nc.esNuevo=t.esNuevo
					t.campos.add(nc, nc.nombre)
					}
				}
			}
		xfrm.cierraFormulario()
		t.guardar(idAnterior)
		}
	xfrm.toDOM()
}
TablaV.prototype.datosFRM=function(){
	return  {"numTramites" : 0,"camino" : 0,"tramite" : 'pr',tipo: 'frm',"titulo" : 'Editar tabla virtual',"alto" : 340, 'controles':[
			{tipo: 'lvw',	"id": 'nombre',	"maxlength" : 50,		"valor" : this.nombre,		"obligatorio" : true, "bloqueado" : !this.esNuevo,	"ds" : 'Nombre'},
			{tipo: 'lvw',	"id": 'descripcion',	"maxlength" : 100,	"valor" : this.descripcion,	"obligatorio" : true, "bloqueado" : false,	"ds" : 'Descripción'},
			(this.clave!=null?{tipo: 'lvwLista',"id": 'clave',					 "valor" : this.clave,		"obligatorio" : false, "bloqueado": false,	"ds" : 'Campo Clave', 'lista': this.listaCamposColumnas()}:
						{tipo: 'lvw',	"id": 'clave',	"maxlength" : 100,	"valor" : this.clave,	"obligatorio" : false, "bloqueado" : false,	"ds" : 'Campo Clave'}) ,
			
			{tipo: 'lvw',"id": 'lecturaN',		"maxlength" : -250,	"valor" : this.lecturaN,	"obligatorio" : true, "bloqueado" : false,	"ds" : 'LecturaN',
				dsExtendida:"La consulta que devuelve las filas de la tabla (salvo las excepciones que se indican a continuación); sólo se permite poner una consulta sin where  (ya que gotta concatena un where por detrás): sí se permiten subselects  para emular este comportamiento (en vez de poner select * from mitabla where mitabla.campo=1  hay que poner select * from (select * from mitabla where mitabla.campo=1)  para que gotta pueda añadir su propio where)"},
			{tipo: 'lvw',"id": 'lectura1',		"maxlength" : -250,	"valor" : this.lectura1,	"obligatorio" : false, "bloqueado": false,	"ds" : 'Lectura1',
				dsExtendida:"El procedimiento o consulta que devuelve una fila dados los valores de la clave"},
			{tipo: 'lvw',"id": 'usp',			"maxlength" : -250,	"valor" : this.usp,		"obligatorio" : false, "bloqueado": false,	"ds" : 'usp', dsExtendida:'El procedimiento o consulta que devuelve los valores disponibles, filtrando por un valor de texto.'},
			{tipo: 'lvw',"id": 'escritura',		"maxlength" : 70,		"valor" : this.escritura,	"obligatorio" : false, "bloqueado": false,	"ds" : 'Escritura',
				dsExtendida:"La tabla física (o vista actualizable) en donde se escriben los cambios"},
			{tipo: 'lvwLista',"id": 'columnadescripcion',		"maxlength" : 70,		"valor" : this.columnadescripcion,	"obligatorio" : false, "bloqueado": false,	"ds" : 'Columna descripción', 'lista': this.listaCamposColumnas(),
				dsExtendida:"La columna cuyo valor se muestra para describir la fila en el caso de una referencia desde otra tabla"},
			{tipo: 'lvwLista',"id": 'columnabusqueda',		"maxlength" : 70,		"valor" : this.columnabusqueda,	"obligatorio" : false, "bloqueado": false,	"ds" : 'Columna búsqueda', 'lista': this.listaCamposColumnas(),
				dsExtendida:"La columna que actúa de clave alternativa en las búsquedas de usuario cuando la clave no le es relevante al usuario final"}
			]}
}
TablaV.prototype.JSON=function(){
	var ret='{'+JSONObjeto(this, false)
	
	ret+=", 'columnas': "+this.columnas.JSON()
	ret+=", 'campos': "+JSONlista( this.soloCampos() )
	ret+=", 'referencias': "+JSONlista( this.soloReferencias() )
	
	ret+='}'
	return ret
}
TablaV.prototype.pintaColumnas=function(lvw){
	var fnDobleClick=function(fila){editorDiccionario.editarColumna(fila.id)}
	for (var i=0; i<this.columnas.length; i++){
		var columna=this.columnas.get(i)
		if (columna!=null) {
			var xclave=this.getClave()
			if (xclave) {
				var existe=xclave.columnas.get(columna.nombre)!=null
				columna.icono=existe?key:null
				}
			else
				columna.icono=null
			}
		}
	
	lvw.rellenaLista(['Columna', 'Tipo','Longitud','Descripción'], ['nombre', 'tipo','longitud','descripcion'], this.columnas.datos(), 'id', null, fnDobleClick, true, fnContext('col'), 'icono' )
							
	if (lvw.tbody.childNodes.length==0) 
		lvw.t.parentNode.oncontextmenu=fnContext('col')
	else
		lvw.t.parentNode.oncontextmenu=null
}
TablaV.prototype.pintaCampos=function(lvw){
	var fnDobleClick=function(fila){editorDiccionario.editarCampo(fila.id)}
	lvw.rellenaLista(['Campo', 'Columnas'], ['nombre', 'colsPrincTexto'], this.soloCampos(), 'id', null, fnDobleClick, true, fnContext('cam'), 'icono')
	
	if (lvw.tbody.childNodes.length==0)
		lvw.t.parentNode.oncontextmenu=fnContext('cam')
	else
		lvw.t.parentNode.oncontextmenu=null
}
TablaV.prototype.pintaReferencias=function(lvw){
	var fnDobleClick=function(fila){editorDiccionario.editarReferencia(fila.id)}
	lvw.rellenaLista(	['Campo', 'Columnas', 'Tabla ref'], 
								['cPrinc', 'colsPrincTexto', 'tRef'], 
								this.soloReferencias() ,'id', null, fnDobleClick , true, fnContext('ref'))
					
	if (lvw.tbody.childNodes.length==0)
		lvw.t.parentNode.oncontextmenu=fnContext('ref')
	else
		lvw.t.parentNode.oncontextmenu=null
}
TablaV.prototype.soloCampos=function(){
	//filtramos un poco y devolvemos únicamente los campos que tienen mas de 1 columna
	var ret=[]
	for (var i=0; i<this.campos.length; i++){
		var campo=this.campos.get(i)
		if (campo!=null && campo.columnas.length > 1) {
			campo.colsPrincTexto=campo.columnasTexto()
			ret.push(campo)
			}
		this.icono=null
		}
	var xclave=this.getClave()
	if (xclave)
		xclave.icono=key
	return ret
}
TablaV.prototype.soloReferencias=function(){
	//filtramos un poco y devolvemos únicamente los campos que tienen tabla de referencia
	var ret=[]
	for (var i=0; i<this.campos.length; i++){
		var campo=this.campos.get(i)
		if (campo!=null && campo.tRef != null) {
			campo.colsPrincTexto=sacaNombres(campo.columnas)
			ret.push(campo)
			}
		}
	return ret
}
TablaV.prototype.listaCamposColumnas=function(){
	var ret={'':''}
	var campos=this.campos.keys().sort()
	for (var i=0; i<campos.length; i++){
		var el=campos[i]
		if (el!=null && typeof el != 'function') {
			var campo=this.campos.get(el)
			ret[el]=el+(campo.columnas.length>1?' ('+campo.columnasTexto()+')':'')
			}
		}
	return ret
}
///////////////
function CampoV(dic, tabla){
	this.tRef=null; this.cRef=null; this.columnas= new Coleccion()
	this.tabla=tabla
	
	this.esNuevo= dic.esNuevo || false
	this.esModif=  dic.esModif || false
	this.esBorrado=false
	
	this.completa(dic, tabla)
}
CampoV.prototype.vacio={'esNuevo':true, 'nombre':null, 'columnas': []}
CampoV.prototype.referenciaVacia={'esNuevo':true, 'campoPrinc':null, 'campoRef':null, 'tablaRef':null, 'columnaDesc':null}
CampoV.prototype.completa=function(dic, tabla){
	this.cPrinc=dic.campoPrinc || dic.nombre || dic.cPrinc
	this.nombre=this.cPrinc
	
	if (typeof dic.colsPrinc=='string')
		dic.colsPrincTexto=dic.colsPrinc
	
	if ('tipo' in dic){ //"nombre" : 'distancia', tipo: 'Integer', "longitud" : 10, "descripcion" : 'distancia'	
		this.columnas.add(dic, this.nombre)
		}
	else if ('columnas' in dic){ //"nombre" : 'CD_DLL_Guiones','columnas': ['cd_guion','cd_operacion']
		if (dic.columnas.arr != null) { //viene un objeto colección
			for (var i=0; i<dic.columnas.length;i++){
				var columna=dic.columnas.get(i)
				if (typeof columna != 'function') 
					this.columnas.add( columna, columna.nombre )
				}
				
			}
		else { // viene una lista de nombres
			for (var s in dic.columnas){
				if (typeof dic.columnas[s]!='function')
					this.columnas.add( tabla.columnas.get( dic.columnas[s] ), dic.columnas[s])
				}
			}
		}
		
	if ('tRef' in dic || 'tablaRef' in dic){ //"campoPrinc" : 'CD_CajeroPagador', "campoRef" : 'CD_CajeroPagador',"tablaRef" : 'DAT_CajerosPagadores',"columnaDesc" : 'DS_CajeroPagador'
		var temptRef = dic.tRef || dic.tablaRef
		if (temptRef) {
			if (dicV && dicV.tablas.get(temptRef))
				this.tRef=dicV.tablas.get(temptRef).nombre
			else
				this.tRef=temptRef

			this.cRef = dic.cRef || dic.campoRef
			this.cDesc = dic.cDesc || dic.columnaDesc
			
			if (this.columnas.length==0 && this.cPrinc!=null)
				this.columnas.add( tabla.columnas.get( this.cPrinc ), this.cPrinc) //una sóla columna	
			}
		}
	
	this.id=tabla.id+'.'+this.nombre
}

CampoV.prototype.getcRef=function(){
	return this.gettRef().campos.get(this.cRef)
}
CampoV.prototype.gettRef=function(){
	return dicV.tablas.get(this.tRef)
}
CampoV.prototype.guardar=function(idAnterior){
	var op= this.tRef==null?'Campo':'Referencia'
	var params= {'accion':'diccionarioFisico', 'aplicacion':aplicacion, 'operacion':op, 'datos':ponCorchete(this.JSON()) }
	
	var fnGuardadoOK=function(resp){
		resp=xeval(resp)
		if (resp.tipo=='error') {
			alert(resp.msg)
			return
			}
			
		if (c.esNuevo)
			c.tabla.campos.add(c, c.nombre)
		else if (c.esBorrado) 
			c.tabla.campos.remove(c.nombre) 
		else
			c.tabla.campos.replace(idAnterior, c)		
			
		editorDiccionario.cargaTablasFV(c.tabla.id)
		}
	var c=this
	loadJSONDocPost ( 'json', params, fnGuardadoOK )
}
CampoV.prototype.borrar=function(){
	this.esBorrado=true
	this.guardar()
}
CampoV.prototype.editar=function(idAnterior){
	var xfrm=new Formulario( this.datosFRM(), ponNuevoBloqueo() )
	
	var tabla=this.tabla
	xfrm.onAceptar=function(){
		var xfrm=getFrmActivo()
		var ret = xfrm.recogeValoresFRM()
		
		var c=new CampoV(ret, tabla)
		c.esModif=true
		c.esNuevo=idAnterior==null

		c.columnas=new Coleccion()
		for (var i=0;i<10;i++) {
			v=ret['dic.col'+i]
			if (v!='')
				c.columnas.add( tabla.columnas.get(v), v)
			}
		xfrm.cierraFormulario()
		c.guardar(idAnterior)
		}
	xfrm.toDOM()
}
CampoV.prototype.datosFRM=function(){
	var dicColumnas={}
	var columnas=this.tabla.columnas.keys()
	for (var i=0; i<columnas.length;i++)
		dicColumnas[ columnas[i] ]=columnas[i]
	dicColumnas['']=null
	
	var ret={"numTramites" : 0,"camino" : 0,"tramite" : 'pr',tipo: 'frm',"titulo" : 'Editar campo virtual',"alto" : 380, 'controles':[
			{tipo: 'lvw',"id": 'nombre',	"maxlength" : -30, "valor" : this.nombre,	"obligatorio" : true, "bloqueado" : !this.esNuevo, "ds" : 'Campo'}
			]}
	
	for (var i=0; i<10; i++) {
		var col=this.columnas.get(i) 
		var nombre=col!=null?col.nombre.toLowerCase():null
		var control={tipo: 'lvwLista',"id": 'dic.col'+i,"maxlength" : 70,	"valor" : nombre, "obligatorio" : i<2, "bloqueado": false, "ds" : 'Columna '+(i+1), 'lista': dicColumnas}
		ret.controles.push(control)
		}
		
	return ret
}
///////////////
CampoV.prototype.editarReferencia=function (idAnterior){
	var xfrm=new Formulario( this.datosFRMReferencia(), ponNuevoBloqueo() )
	
	var tabla=this.tabla
	xfrm.onAceptar=function(){
		var xfrm=getFrmActivo()
		var ret = xfrm.recogeValoresFRM()
		
		var c
		if (tabla.campos.get(ret.campoPrinc)) {
			c=copiaCampo(tabla.campos.get(ret.campoPrinc))
			c.completa(ret, tabla)
			c.esNuevo=true
			}
		else {
			c= new CampoV(ret, tabla)
			c.esNuevo=true
			}
		
		c.esModif=true
		c.esNuevo=idAnterior==null
		
		xfrm.cierraFormulario()
		c.guardar(idAnterior)
	}
	xfrm.toDOM()
}
function copiaCampo(original){
	var ret=new CampoV(original, original.tabla)
	return ret
}
CampoV.prototype.datosFRMReferencia=function(){
	var tRef=this.gettRef()
		
	return  {"numTramites" : 0,"camino" : 0,"tramite" : 'pr',tipo: 'frm',"titulo" : 'Editar referencia virtual',"alto" : 210, 'controles':[
			{tipo: 'lvw',		"id": 'tablaPrinc',	"maxlength" : 50,	"valor" : this.tabla.nombre,	"obligatorio" : true, "bloqueado" :true,	"ds" : 'Tabla principal'},
			{tipo: 'lvwLista',	"id": 'campoPrinc',	"maxlength" : 70,	"valor" : this.cPrinc,		"obligatorio" : true, "bloqueado" :!this.esNuevo,	"ds" : 'Campo principal', 'lista':this.tabla.listaCamposColumnas() },
			{tipo: 'lvwLista',	"id": 'tablaRef',				"valor" : this.tRef,		"obligatorio" : true, "bloqueado": false,	"ds" : 'Tabla ref', 'lista': hazDic(dicV.tablas.keys())}
			]}
}
CampoV.prototype.JSON=function(){
	return "{'nombre':"+ponComillas(this.cPrinc)+", 'tabla':"+ponComillas(this.tabla.nombre)+
			", 'columnas':"+JSONlista(sacaNombres(this.columnas))+
			", 'campoRef':"+ponComillas(this.cRef)+
			", 'tablaRef':"+ponComillas(this.tRef)+", 'columnaDesc':"+ponComillas(this.cDesc)+
			", 'esBorrado':"+this.esBorrado+", 'esModif':"+this.esModif+", 'esNuevo':"+this.esNuevo
			+"}"
}
CampoV.prototype.columnasTexto=function(){
	return this.columnas.keys().join(', ')
}
///////////////
function ColumnaV(dic, tabla){
	this.nombre	=dic.nombre
	this.id		=tabla.id+'.'+dic.nombre
	this.descripcion	=dic.descripcion
	this.tipo		=dic.tipo
	this.longitud	=dic.longitud
	this.tabla		=tabla
	
	this.esNuevo=dic.esNuevo || false
	this.esModif=  dic.esModif || false
	this.esBorrado=false
}
ColumnaV.prototype.vacia={'esNuevo':true, 'nombre':null, 'descripcion':null, 'tipo':null, 'longitud':null}
ColumnaV.prototype.JSON=function(){
	return '{'+JSONObjeto(this, false)+","+pareja('tabla', this.tabla.nombre, false)+'}'
}
ColumnaV.prototype.toString=function(){
	return this.JSON()
}
ColumnaV.prototype.guardar=function(idAnterior){
	var op='Columna'
	var params= {'accion':'diccionarioFisico','aplicacion':aplicacion, 'operacion':op, 'datos': ponCorchete(this.JSON()) }
	var c=this
	var fnGuardadoOK=function(resp){
		resp=xeval(resp, true)
		if (resp.tipo=='error') {
			alert(resp.msg)
			return
			}
		if (c.esBorrado) {
			c.tabla.columnas.remove(c.nombre)
			c.tabla.campos.remove(c.nombre) 
			}
		else if (c.esNuevo) {
			c.tabla.columnas.add(c, c.nombre)
			
			var campoDeCol=new CampoV(c, c.tabla)
			c.tabla.campos.add(campoDeCol, c.nombre)
			}
		else	{
			c.tabla.columnas.replace(idAnterior, c)
			
			var campoDeCol=new CampoV(c, c.tabla)
			c.tabla.campos.replace(idAnterior, campoDeCol)
			}
		
		editorDiccionario.cargaTablasFV(c.tabla.id)
		}
	loadJSONDocPost ( 'json', params, fnGuardadoOK )
}
ColumnaV.prototype.borrar=function(){
	this.esBorrado=true
	this.guardar()
}
ColumnaV.prototype.editar=function(idAnterior){
	var xfrm=new Formulario( this.datosFRM(), ponNuevoBloqueo() )

	var tabla=this.tabla
	xfrm.onAceptar=function(){
		var xfrm=getFrmActivo()
		var ret = xfrm.recogeValoresFRM()
		
		var c=new ColumnaV(ret, tabla)
		c.esModif=true
		c.esNuevo=idAnterior==null
		
		xfrm.cierraFormulario()
		c.guardar(idAnterior)
		}
	xfrm.toDOM()
}
ColumnaV.prototype.datosFRM=function(){
	return  {"numTramites" : 0,"camino" : 0,"tramite" : 'pr',tipo: 'frm',"titulo" : 'Editar columna virtual',"alto" : 230, 'controles':[
			{tipo: 'lvwLista',"id": 'tabla',	"maxlength" : 50,	"valor" : this.tabla.id.toLowerCase(),	"obligatorio" : true,"bloqueado" : !this.esNuevo, "ds" : 'Tabla', 'lista':hazDic(dicV.tablas.keys())},
			{tipo: 'lvw',"id": 'nombre',		"maxlength" : 70,	"valor" : this.nombre,		"obligatorio" : true,"bloqueado" : !this.esNuevo,"ds" : 'Columna'},
			{tipo: 'lvw',"id": 'descripcion',	"maxlength" :100,	"valor" : this.descripcion,	"obligatorio" : true,"bloqueado" : false,"ds" : 'Descripción'},
			
			{tipo: 'lvwLista',"id": 'tipo',					"valor" : this.tipo,		"obligatorio" : true, "bloqueado": false, "ds" : 'Tipo de dato', 'lista': tiposDatos},
			{tipo: 'lvw',"id": 'longitud',		"maxlength" : 5,	"valor" : this.longitud,		"obligatorio" : true,"bloqueado" : false,"ds" : 'Longitud'}
			]}
}
////////////////////////////////////////////
function ponCorchete(str){
	return '['+str+']'
	}
function hazDic(l){
	var dic={}
	for (var i=0; i<l.length;i++){
		if (typeof l[i] != 'function')
			dic[ l[i] ]=l[i]
		}
	return dic
}
function JSONObjeto(t, ponLlaves){
	if (typeof t=='string')
		return '"'+t+'"'
		
	var ret=''
	if (ponLlaves)
		ret+='{'
	for (var prop in t) {
		if (prop=='prototype' || typeof t[prop]== 'function' || typeof t[prop]== 'object'){
			/* pass */}
		else if (prop!='errores')
			ret+=pareja(prop, t[prop])
		}
	ret=quita(ret, ',')
	if (ponLlaves)
		ret+='}'
	return ret
}
function JSONlista(l){
	var ret=[]
	
	for (var prop in l) {
		if (prop=='prototype' || typeof l[prop]== 'function' ){
			/* pass */}
		else if (typeof l[prop]== 'object')
			ret.push( l[prop].JSON() )
		else
			ret.push( ponComillas(l[prop]))
		}
	return ponCorchete(ret.join(','))
}
function evalP(json){
	return eval( '('+json+')' )
}
function ponComillas(s){
	if (s==null)
		return s
	else
		return comilla+s+comilla
}
////////////////////////////////////////////
if (String.endsWith==null){
	String.prototype.endsWith = function(s) {
		return (this.lastIndexOf(s)>-1 && this.lastIndexOf(s)==this.length-s.length)
		}
	}
function quita(str, caracter){
	if (str.endsWith(caracter))
		str=str.substring(0, str.length-caracter.length)
	return str
}
function eco(t){
	alert(t)
	}
function fnTest(){
	var datos={'tablas':[
		{"nombre" : 'AUX_Distancias',
			"id" : 'AUX_Distancias',
			"lectura1" : '',
			"lecturaN" : 'select * from AUX_Distancias',
			"descripcion" : 'AUX Distancias',
			"escritura" : 'AUX_Distancias',
			"clave" : 'CD_AUX_Distancias',
			"errores" : '',
		'columnas':[
			{"nombre" : 'distancia',		tipo: 'Integer',	"longitud" : 10,	"descripcion" : 'distancia'},
			{"nombre" : 'CD_Destino',	tipo: 'Integer',	"longitud" : 10,	"descripcion" : 'Cód. Destino'},
			{"nombre" : 'CD_Pais_origen',	tipo: 'String',	"longitud" : 3,	"descripcion" : 'Cód. Pais origen'},
			{"nombre" : 'CD_Origen',	tipo: 'Integer',	"longitud" : 10,	"descripcion" : 'Cód. Origen'},
			{"nombre" : 'CD_Pais_destino',tipo: 'String',	"longitud" : 3,	"descripcion" : 'Cód. Pais destino'}],
		'campos':[
			{"nombre" : 'CD_AUX_Distancias', 'columnas': ['cd_destino','cd_origen','cd_pais_destino','cd_pais_origen']},
			{"nombre" : 'CD_aux_distancias2', 'columnas': ['cd_pais_origen','cd_origen','cd_pais_destino','cd_destino']}],
		'referencias':[
			{"campoPrinc" : 'CD_Pais_origen',	"colsPrinc" : 'cd_pais_origen',	"campoRef" : 'CD_Pais', "colsRef" : 'cd_pais', "tablaRef" : 'AUX_Paises', "columnaDesc" : 'CD_Pais'},
			{"campoPrinc" : 'CD_Pais_destino',	"colsPrinc" : 'cd_pais_destino',"campoRef" : 'CD_Pais', "colsRef" : 'cd_pais', "tablaRef" : 'AUX_Paises', "columnaDesc" : 'CD_Pais'}]}]}

	var dic=new DiccionarioV(datos.tablas)
	var t=dic.tablas.get('aux_distancias')

	eco(t.getClave())
	//~ eco(t.columnas['cd_pais_origen'])
	eco(t.campos.get('cd_aUX_DIstancias2').columnas)
	}
/////////////////////////////////////////

Formulario.prototype.recogeValoresFRMDic=function(){
	var param={}
	for (var i=0; i<this.controles.length; i++) {
		var ctl=this.controles[i]
//		if (ctl == null || ctl.bloqueado) Esto no sirve el formulario de edición
		if (ctl == null)
			continue
		if (ctl.tipo == 'etiqueta'){
			/*pass*/}
		else if (ctl.tipo=='lvwFile'){
			/*pass*/}
		else if ( ['lvwMemo','lvw', 'lvwFecha', 'lvwNumero', 'lvwCurrency', 'lvwLista'].indexOf(ctl.tipo)>-1 )
			param[ctl.idOriginal]=control(ctl.id).value
		else if ( ['lvwBoolean','lvwOption'].indexOf(ctl.tipo)>-1 )
			param[ctl.idOriginal]=control(ctl.id).checked
		else if (ctl.tipo=='bsc'){
			if(this.validaEstaRelleno(ctl,true)){
				for (var j=0; j< ctl.controles.length; j++){
					var subctl=ctl.controles[ j ]
					param[ subctl.idOriginal || subctl.id ]=ctl['valor'+j]
					}
				}
			}
		}
	return param
}


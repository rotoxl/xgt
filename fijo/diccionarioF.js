///diccionario Fisico
const punto='.'
const coma=','

function DiccionarioF(datos){
	this.tablas=new Coleccion()
	for (var i=0; i<datos.length; i++){
		var t=new TablaF(datos[i])
		this.tablas.add(t, t.id)
		}
	}
DiccionarioF.prototype.getColumna=function(tablaColumna){
	var temp=tablaColumna.split(punto)
	var t=this.tablas.get(temp[0]+punto+temp[1]+punto+temp[2])
	
	var ncolumna=temp[temp.length-1]
	return t.columnas.get(ncolumna)
}
DiccionarioF.prototype.getReferencia=function(tablaReferencia){
	var temp=tablaReferencia.split(punto)
	var t=this.tablas.get(temp[0]+punto+temp[1]+punto+temp[2])
	
	var ncampo=temp[temp.length-1]
	return t.campos.get(ncampo)
}
DiccionarioF.prototype.getTablaPorNombre=function(nombre){
	nombre=nombre.toLowerCase()
	for (var i=0; i<this.tablas.length; i++) {
		var tt=this.tablas.get(i)
		if (tt.nombre.toLowerCase()==nombre)
			return tt
		}
}
////tabla fisica
function TablaF(dic){ 
	this.nombre=	dic.nombre
	this.id=		dic.id
	this.cargada=false
	this.descripcion=	dic.descripcion
	this.lectura1=	dic.lectura1
	this.lecturaN=	dic.lecturaN
	this.escritura=	dic.escritura
	this.columnadescripcion=dic.columnadescripcion
	this.columnabusqueda=dic.columnabusqueda
	this.esquema=	dic.esquema
	this.columnasClave = ''
	this.tipo = dic.tipo
	
	this.campos=	new Coleccion()
	this.columnas=	new Coleccion()
	
	if (dic.clave.nombre===undefined) 
		this.clave=null
	else {
		if (dic.clave.columnas.length==1) {
			this.clave=dic.clave.columnas[0]
			dic.clave.nombre=this.clave
			}
		var campoClave=new CampoF(dic.clave, this)
		this.campos.add(campoClave, campoClave.cPrinc)
		this.clave=campoClave.cPrinc
		this.columnasClave = campoClave.columnasTexto()
		}
	marcaTablaGotta(this)
}
TablaF.prototype.tipoObjeto='TablaF' //sólo para enterarnos un poco más en depuración
TablaF.prototype.getClave=function(){
	if (this.clave==null)
		return null
	else
		return this.campos.get(this.clave)
}
TablaF.prototype.altaVirtual=function(){
	var campoClave=this.campos.get(this.clave)
	// vamos a intentar ponerle un nombre según la nomenclatura de gotta
	var nombre=null
	if (campoClave!=null)
		nombre=this.sacaNombreCampo(campoClave.columnas.keys())
	
	var t = new TablaV( evalP(this.JSON()) )
	if (campoClave!=null) campoClave.cPrinc=nombre
	if (t.tipo=='view') nombre=t.columnas.get(0).nombre
	t.clave=nombre

	t.esNuevo=true
	
	for (var i=0; i<this.columnas.length; i++) {
		var col=this.columnas.get(i)
		var nc=new ColumnaV( evalP(col.JSON()), t)
		t.columnas.add(nc, nc.nombre)
		nc.esNuevo=true
		
		var campoDeCol=new CampoV(nc, t)
		t.campos.add(campoDeCol, nc.nombre)
		}
		
	for (var i=0; i<this.campos.length; i++) {
		var cam=this.campos.get(i)
		if (cam.tRef!=null || cam.columnas.length>1) {
			var nc=new CampoV( evalP(cam.JSON()), t)
			t.campos.add(nc, nc.nombre)
			nc.esNuevo=true
			}
		}
	
	if (dicV.tablas.get(t.nombre)!=null) {
		var nt = window.prompt('La tabla virtual "'+t.nombre+'" ya existe. Si desea crear una nueva tabla virtual basada en esta, introduzca el nombre de la nueva tabla virtual:', t.nombre)
		if (nt=='' || nt==null || nt==t.nombre)
			return
		t.nombre=nt
		t.id=nt
		}
		
	t.guardar(t.nombre)
	//dicV.tablas.add(t,t.nombre)
}
TablaF.prototype.cargar=function(){
	if ( !this.cargada ){
		var tabla=this
		var params1= {'accion':'diccionarioFisico','aplicacion':aplicacion,'operacion':'tablacolumnas','tabla':tabla.id}
		loadJSONDoc( 'json', params1, function(req){tabla._columnasTabla( xeval(req))} )
		
		var params2= {'accion':'diccionarioFisico','aplicacion':aplicacion,'operacion':'tablareferencias','tabla':tabla.id}
		loadJSONDoc( 'json', params2, function(req){tabla._referenciasTabla( xeval(req) )} )
		this.cargada=true
		}
}
TablaF.prototype._columnasTabla=function(res){
	if (res.tipo=='error'){
		alert(res.msg)
		return
		}
	for (var i=0;i<res.length;i++) {
		var c=new ColumnaF(res[i], this)
		this.columnas.add(c, c.nombre)
		
		if (this.campos.get(c.nombre)==null){
			var campoDeCol=new CampoF(c, this)
			this.campos.add(campoDeCol, c.nombre)
			}
		}
	this.pintaColumnas(editorDiccionario.columnasFisicas)
}
TablaF.prototype._referenciasTabla=function(res){
	if (res.tipo=='error'){
		alert(res.msg)
		return
		}
	for (var i=0;i<res.length;i++) {
		var c=new CampoF(res[i], this)
		this.campos.add(c, c.cPrinc)
		}
	this.pintaReferencias(editorDiccionario.referenciasFisicas)
}
TablaF.prototype.JSON=function(){
	var ret=JSONObjeto(this, true)
	return ret
}
TablaF.prototype.pintaColumnas=function(lvw){
	for (var i=0;i<this.columnas.length;i++) {
		var col=this.columnas.get(i)
		col.icono=null
		if (dicV.getColumna(col.id))
			col.icono='./fijo/dic.hayColumna'
		}
		
	var fnDobleClick=function(fila){
						var col=dicF.getColumna(fila.id)
						var temp = fila.id.split('.')
						var tab=dicV.tablas.get(temp[2])
						col.altaVirtual(col,tab)
					}
	lvw.rellenaLista(['Nombre', 'Tipo'], ['nombre', 'tipo'], this.columnas.datos(), 'id', null, fnDobleClick, true, null, 'icono' )
							
	if (lvw.tbody.childNodes.length==0) 
		lvw.t.parentNode.oncontextmenu=fnContext('col')
	else
		lvw.t.parentNode.oncontextmenu=null
}
TablaF.prototype.pintaReferencias=function(lvw){
	var fnDobleClick=function(fila){ 
						var td=fila.firstChild
						td.style.backgroundImage='url(./fijo/throbber.gif)'
						
						var ref=dicF.getReferencia(fila.id)
						var temp = fila.id.split('.')
						var tab=dicV.tablas.get(temp[2])
						ref.altaVirtual(ref,tab)
					}
	lvw.rellenaLista(['Columnas','Tabla Ref'], ['colsPrincTexto','tRef'], this.soloReferencias(), 'id', null, fnDobleClick)
}
TablaF.prototype.soloReferencias=function(){
	//filtramos un poco y devolvemos únicamente los campos que tienen tabla de referencia
	var ret=[]
	for (var i=0; i<this.campos.length; i++){
		var campo=this.campos.get(i)
		if (campo.tRef != null) {
			campo.colsPrincTexto=campo.columnasTexto()
			ret.push(campo)
			}
		}
	return ret
}
TablaF.prototype.sacaNombreCampo=function(listaColumnas){
	if (listaColumnas.length==1)
		return listaColumnas[0]
	else {
		var semilla='CD_'+this.nombre
		ret=semilla
		i=0
		while (this.campos.get(ret)!=null){
			ret=semilla+i
			i++
			}
		return ret
		}
}
TablaF.prototype.soloCampos=function(){
	//filtramos un poco y devolvemos únicamente los campos que tienen mas de 1 columna
	var ret=[]
	for (var i=0; i<this.campos.length; i++){
		var campo=this.campos.get(i)
		if (campo.columnas.length > 1) {
			campo.colsPrincTexto=campo.columnasTexto()
			ret.push(campo)
			}
		}
	return ret
}
/////columna fisica
function ColumnaF(dic, tabla){
	this.nombre=dic.nombre
	this.tipo=dic.tipo
	this.id=tabla.id+punto+this.nombre
	this.longitud=dic.longitud
	this.descripcion=dic.descripcion
	
	this.tabla=tabla
}
ColumnaF.prototype.tipoObjeto='ColumnaF' //sólo para enterarnos un poco más en depuración
ColumnaF.prototype.altaVirtual=function(col,tab){
	if (tab.columnas.get(col.nombre))return
	col.esNuevo=true
	var colv = new ColumnaV(col,tab)
	colv.guardar()
}
ColumnaF.prototype.JSON=function(){
	return JSONObjeto(this, true)
}
////////
function CampoF(dic, tabla){
	this.tabla=tabla
	this.cPrinc=dic.campoPrinc || dic.nombre
	
	this.id=tabla.id+punto+this.cPrinc
	this.tRef=dic.tablaRef
	
	dic.columnas=dic.columnas || xHazSplit(dic.colsPrinc)
	
	this.columnas=new Coleccion()
	if (dic.columnas!=null) {
		for (var i=0; i<dic.columnas.length; i++){
			var col=new ColumnaF( {'nombre':dic.columnas[i]}, this.tabla)
			this.columnas.add(col, col.nombre)
			}
		}
}
CampoF.prototype.tipoObjeto='CampoF' //sólo para enterarnos un poco más en depuración
function xHazSplit(cadena){
	if (typeof cadena =='string')
		return cadena.split(coma)
	else
		return cadena
}
CampoF.prototype.altaVirtual=function(cam,tab){
	if (tab.campos.get(cam.nombre))return
	cam.esNuevo=true
	var camv=new CampoV(cam,tab)
	camv.guardar()
}
CampoF.prototype.JSON=function(){
	var ret='{'+JSONObjeto(this, false)
	ret+=",'columnas':"+JSONlista( sacaNombres(this.columnas) )
	ret+='}'
	return ret
}
CampoF.prototype.columnasTexto=function(){
	var ret=[]
	for (var i=0; i<this.columnas.length;i++){
		ret.push( this.columnas.get(i).nombre )
		}
	return ret.join(', ')
}
CampoF.prototype.gettRef=function(){
	return dicF.getTablaPorNombre(this.tRef)
}
function sacaNombres(xColColumnas){
	var ret=[]
	for (var c=0; c<xColColumnas.length; c++){
		if (xColColumnas.arr[c])
			ret.push(xColColumnas.arr[c].nombre)
		}
	return ret
}
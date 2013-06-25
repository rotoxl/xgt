var SORT_COLUMN_INDEX
function SortTable(tabla){
	this.idColumna=null
	this.t=tabla
	
	var xcont=this.t.parentNode
	var idCont=xcont.id
	if (!idCont)
		idCont=xcont.parentNode.id
	
	
	this.ctl=null
	if (exp)
		this.ctl=exp.mdActivo().getControl(idCont) || exp.getMD(exp.idMdActivo).getControl(idCont)
	}
SortTable.prototype.makeSortable=function(forzarDesc) {
	var firstRow
	if (this.t.rows && this.t.rows.length > 0) 
		firstRow = this.t.rows[0]
	if (!firstRow) return

	var inicio=1
	if (firstRow.cells[0].className=='lvw')
		inicio=0

	// We have a first row: assume it's the header, and make its contents clickable links
	for (var i=inicio;i<firstRow.cells.length;i++) {
		var cell = firstRow.cells[i]
		var txt = $(cell).text()
		borraHijos(cell)
		
		var lnk=creaObjProp('a', {href:'#', tabIndex:-1, className:'sortheader', title:'[Click aquí para ordenar]', hijos:[creaTexto(txt), creaObjProp('span', {className:'sortarrow', texto:espacioDuro4}) ]})
		cell.appendChild(lnk)
		$(lnk).click(this.fnOnClick(lnk))
		}
		
	this.recuperaDato()
}
SortTable.prototype.fnOnClick=function(lnk){
	return function(){
		var t=$(lnk).parents('table.sortable:first')[0]
		new SortTable(t).resortTable(lnk)
		}
	}
SortTable.prototype.ponColorCol=function(idx){
	if (idx==null) return
	
	var colN=$(this.t).children('colGroup:last').find( 'col:nth-child('+ (idx) +')')
	colN.addClass('seleccionada')
	}
SortTable.prototype.quitaColorCol=function(idx){
	$(this.t).find('col.seleccionada').removeClass('seleccionada')
	}
SortTable.prototype.resortTable=function(link, forzarDesc) {
	var th=$(link).parents('th')[0]
	
	this.col0=$(this.t).find('colGroup:first col')[0]
	this.numColumnasFijas=this.col0?this.col0.span:0
	
	var idCelda= th.cellIndex+this.numColumnasFijas-1
	var idColumna=th.cellIndex+this.numColumnasFijas

	// Work out a type for the column
	if (this.t.rows.length <= 1) 
		return

	//si hay algo raro de colspan y cosas así, pasamos del tema
	if ( this.t.rows[1].cells.length<this.t.rows[0].cells.length) 
		return

	var sortfn = this.ts_sort_caseinsensitive

	/* cambiamos la forma de decidir el tipo de ordenado: se hará por la clase de la celda*/
	var celda=this.t.rows[1].cells[idCelda]
	if (celda==undefined)
		celda=this.t.rows[2].cells[idCelda]

	var nClase=celda.className.toLowerCase()
	if (esTipoNumerico(nClase))
		sortfn = this.ts_sort_numeric
	else if (nClase=='lvwfecha')
		sortfn = this.ts_sort_date
	else if (nClase=='lvw')
		sortfn = this.ts_sort_caseinsensitive
	else	{
		try {
			var itm = $(this.t.rows[1].cells[idCelda]).text()
			if (itm.match(/^\d\d[\/-]\d\d[\/-]\d\d\d\d$/)) sortfn = this.ts_sort_date
			if (itm.match(/^\d\d[\/-]\d\d[\/-]\d\d$/)) sortfn = this.ts_sort_date
			if (itm.match(/^ý$]/)) sortfn = this.ts_sort_currency
			if (itm.match(/^[\d\.]+$/)) sortfn = this.ts_sort_numeric
			}
		catch (e){
			}
		}

	this.quitaColorCol()
	this.idColumna = idCelda
	this.ponColorCol(idCelda)

	var firstRow = new Array()
	var newRows = new Array()
	for (var i=0;i<this.t.tHead.rows.length;i++) { 
		firstRow[i] = this.t.tHead.rows[0][i]
		}
	for (var j=0;j<this.t.tBodies[0].rows.length;j++) { 
		newRows[j] = this.t.tBodies[0].rows[j]
		}

	SORT_COLUMN_INDEX=this.idColumna
	newRows.sort(sortfn)

	// get the span
	var span=$(th).find('span.sortarrow')
	var paraArriba=span.hasClass('down')//esto antes de quitar las clases, claro
	// Delete any other arrows there may be showing
	$(this.t).find('span.sortarrow').removeClass('up').removeClass('down')
	if (paraArriba || forzarDesc) {
		newRows.reverse()
		span.addClass('up')
		this.salvaDato('up')
		} 
	else {
		span.addClass('down')
		this.salvaDato('down')
		}
	
	// We appendChild rows that already exist to the tbody, so it moves them rather than creating new ones
	// don't do sortbottom rows
	for (i=0;i<newRows.length;i++) { if (!newRows[i].className || (newRows[i].className && (newRows[i].className.indexOf('sortbottom') == -1))) this.t.tBodies[0].appendChild(newRows[i]);}
	// do sortbottom rows only
	for (i=0;i<newRows.length;i++) { if (newRows[i].className && (newRows[i].className.indexOf('sortbottom') != -1)) this.t.tBodies[0].appendChild(newRows[i]);}


	//actualizamos los números de fila
	if (tieneEstilo(newRows[0].childNodes[0], 'numFila')) {
		for (i=0;i<newRows.length;i++) {
			var celda=newRows[i].childNodes[0]
			if (celda.textContent=='')
				break
			var nodo=nodoTexto(celda)
			if (nodo)
				celda.replaceChild(creaT(i+1), nodo)
			}
		}
}
SortTable.prototype.recuperaDato=function(){
	if (this.ctl==null)
		return
	
	var sort=this.ctl.recuperaDato('s')
	if (sort!=null){
		var sortDir=sort.substring(0, 1)
		var sortColumn=Number(sort.substring(1))
		
		var listaEnlaces=$(this.thead).find('a.sortheader')
		var link=listaEnlaces[sortColumn]
		
		if (link) this.resortTable(link, sortDir=='u')
		}
	}
SortTable.prototype.salvaDato=function(clase){
	if (this.ctl) {
		var direccion=clase=='up'?'u':'d'
		this.ctl.salvaDato('s', direccion+(this.idColumna-this.numColumnasFijas))
		}
	}
SortTable.prototype.getParent=function(el, pTagName) {
	return $(el).parents( pTagName+':first')[0]
	}
SortTable.prototype.ts_sort_date=function(a,b) {
	// y2k notes: two digit years less than 50 are treated as 20XX, greater than 50 are treated as 19XX
	var celda1=a.cells[SORT_COLUMN_INDEX]
	var celda2=b.cells[SORT_COLUMN_INDEX]
	
	var dt1=new Date(celda1.getAttribute('valorOriginal'))
	var dt2=new Date(celda2.getAttribute('valorOriginal'))
	
	if (dt1==dt2) return 0;
	if (dt1<dt2) return -1;
	return 1;
}
SortTable.prototype.ts_sort_currency=function(a,b) { 
	return ts_sort_numeric(a,b)
	}
SortTable.prototype.ts_sort_numeric=function(a,b) { 
	var celda1=a.cells[SORT_COLUMN_INDEX]
	var celda2=b.cells[SORT_COLUMN_INDEX]
	
	var aa=parseFloat(celda1.getAttribute('valorOriginal'))
	var bb=parseFloat(celda2.getAttribute('valorOriginal'))

	if (isNaN(aa)) aa = parseFloat( $(celda1).text() )
	if (isNaN(bb)) bb = parseFloat( $(celda2).text() )
	
	if (isNaN(aa)) aa = 0
	if (isNaN(bb)) bb = 0
	
	return aa-bb
}
SortTable.prototype.ts_sort_caseinsensitive=function(a,b) {
	var celda1=a.cells[SORT_COLUMN_INDEX]
	var celda2=b.cells[SORT_COLUMN_INDEX]
	
	var aa = $(celda1).text().toLowerCase()
	var bb = $(celda2).text().toLowerCase()
	
	if (aa==bb) return 0
	if (aa<bb) return -1
	return 1
}
SortTable.prototype.ts_sort_casesensitive=function(a,b) {
	var celda1=a.cells[SORT_COLUMN_INDEX]
	var celda2=b.cells[SORT_COLUMN_INDEX]
	
	var aa = $(celda1).text()
	var bb = $(celda2).text()
	
	if (aa==bb) return 0
	if (aa<bb) return -1
	return 1
}
SortTable.prototype.ts_sort_default=function(a,b) {
	return this.ts_sort_casesensitive(a, b)
}
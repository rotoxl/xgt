// Basado en 
// Resizable Table Columns.
//  version: 1.0
//
// (c) 2006, bz
//
// 25.12.2006:  first working prototype
// 26.12.2006:  now works in IE as well but not in Opera (Opera is @#$%!)
// 27.12.2006:  changed initialization, now just make class='resizable' in table and load script
//
function preventEvent(e) {
	var ev = e || window.event;
	if (ev.preventDefault) ev.preventDefault();
	else ev.returnValue = false;
	if (ev.stopPropagation)
		ev.stopPropagation();
	return false;
	}
function getWidth(x) {
	return $(x).outerWidth(false)
	}
// main class prototype
function ColumnResize(table) {
	if (table.tagName != 'TABLE') return;

	this.id = table.id;

	// ============================================================
	// private data
	var self = this;

	var dragColumns  = table.rows[0].cells; // first row columns, used for changing of width
	if (!dragColumns) return; // return if no table exists or no one row exists

	var dragColumn
	var dragColumnNo; // current dragging column
	var dragX;        // last event X mouse coordinate

	var saveOnmouseup;   // save document onmouseup event handler
	var saveOnmousemove; // save document onmousemove event handler
	var saveBodyCursor;  // save body cursor property

	var saveWidth
	// ============================================================
	// methods

	// ============================================================
	// do changes columns widths
	// returns true if success and false otherwise
	this.changeColumnWidth = function(no, w) {
		if (!dragColumns) return false;
		if (no < 0) return false;
		if (dragColumns.length < no)	return false;

		if (parseInt(dragColumns[no].style.width) <= -w) return false;
		if (dragColumns[no+1] && parseInt(dragColumns[no+1].style.width) <= w) return false;

		self.cambiaTamanho(dragColumns[no], w)
		return true;
		}
	this.cambiaTamanho=function(th, w){
		if (!th)
			return

		var c0=$(th)
		c0.css('width', null)//por si ak
		
		var fc=c0.children(':first-child')

		var old=fc.outerWidth()
		fc.css('width', fc.outerWidth()+w )

		console.info(old + '->' + fc.outerWidth())
		}
	// ============================================================
	// do drag column width
	this.columnDrag = function(e) {
		var e = e || window.event;
		var X = e.clientX || e.pageX;
		if (!self.changeColumnWidth(dragColumnNo, X-dragX)) 
		self.stopColumnDrag(e);

		dragX = X;
		// prevent other event handling
		preventEvent(e);
		return false;	
		}
	// ============================================================
	// stops column dragging
	this.stopColumnDrag = function(e) {
		var e = e || window.event;
		if (!dragColumns) return;
		
		// restore handlers & cursor
		document.onmouseup  = saveOnmouseup;
		document.onmousemove = saveOnmousemove;
		self.ponCursor(saveBodyCursor)
		
		self.colocaTiradores()//colocamos los tiradores en su sitio
		self.salvaEnCookies()// remember columns widths in cookies for server side
		preventEvent(e);
		}
	this.colocaTiradores=function(){
		//le ponemos al tirador el mismo ancho de la columna
		var d=$(table).find('th div.resizableColumn')
		$.each(d, function(index, element){ 
		var e=$(element)
		e.css('width', e.parent().innerWidth()-2 )
		} )
		}
	this.ponCursor=function(cursor){
		$('body, table.sortable tr, table.lvw tr').css('cursor',  cursor)
		}
	this.salvaEnCookies=function(){
		var colWidth = '';
		var separator = '';
		for (var i=0; i<dragColumns.length; i++) {
			colWidth += separator + parseInt( getWidth(dragColumns[i]) );
			separator = '+';
			}
		var expire = new Date();
		expire.setDate(expire.getDate() + 365); // year
		document.cookie = self.id + '-width=' + colWidth +'; expires=' + expire.toGMTString();
		}
	// ============================================================
	// init data and start dragging
	this.startColumnDrag = function(e) {
		var e = e || window.event;

		var th=(e.target || e.srcElement).parentNode.parentNode
		dragColumn=th

		// remember dragging object
		dragColumnNo = th.cellIndex;
		dragX = e.clientX || e.pageX;

		// set up current columns widths in their particular attributes
		// do it in two steps to avoid jumps on page!
		var colWidth = new Array();
		for (var i=0; i<dragColumns.length; i++)
			colWidth[i] = parseInt( getWidth(dragColumns[i]) );
		for (var i=0; i<dragColumns.length; i++) {
			dragColumns[i].width = ""; // for sure
			dragColumns[i].style.width = colWidth[i] + "px";
			}

		saveOnmouseup       = document.onmouseup;
		document.onmouseup  = self.stopColumnDrag;
		saveBodyCursor             = document.body.style.cursor;
		self.ponCursor('w-resize !important')

		// fire!
		saveOnmousemove      = document.onmousemove;
		document.onmousemove = self.columnDrag;

		preventEvent(e);
		}

	// prepare table header to be draggable
	// it runs during class creation
	for (var i=0; i<dragColumns.length; i++) {
		dragColumns[i].innerHTML = "<div class='resizableColumn'><div class='resizableColumnI'></div>"+dragColumns[i].innerHTML+"</div>";
		dragColumns[i].firstChild.firstChild.onmousedown = this.startColumnDrag;
		}
}

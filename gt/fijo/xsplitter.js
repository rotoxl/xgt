// xSplitter, xClientWidth, xAddEventListener,xGetElementById,xMoveTo  Copyright 2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

function xSplitter(sSplId, uSplX, uSplY, uSplW, uSplH, bHorizontal, uBarW, uBarPos, uBarLimit, bBarEnabled, uSplBorderW, oSplChild1, oSplChild2)
{
  // Private
this.bHorizontal=bHorizontal
	
  var pane1, pane2, splW, splH;
  var splEle, barPos, barLim, barEle;
  
  var fnOnBarDrag

  function barOnDrag(ele, dx, dy){
    var bp;
    if (bHorizontal)
    {
        bp = barPos + dx;
        if (bp < uBarLimit || bp > splW - uBarLimit) { return; }
        xWidth(pane1, xWidth(pane1) + dx);
        xLeft(barEle, xLeft(barEle) + dx);
        xWidth(pane2, xWidth(pane2) - dx );
        xLeft(pane2, xLeft(pane2) + dx);
        barPos = bp;
    }
    else
    {
        bp = barPos + dy;
        if (bp < uBarLimit || bp > splH - uBarLimit) { return; }
        xHeight(pane1, xHeight(pane1) + dy);
        xTop(barEle, xTop(barEle) + dy);
        xHeight(pane2, xHeight(pane2) - dy);
        xTop(pane2, xTop(pane2) + dy);
        barPos = bp;
    }
    if (oSplChild1) { oSplChild1.paint(xWidth(pane1), xHeight(pane1)); }
    if (oSplChild2) { oSplChild2.paint(xWidth(pane2), xHeight(pane2)); }
    
    ////////////////////////////
    if (exp) exp.colocaArbol()
    if (fnOnBarDrag) 
	fnOnBarDrag()
    ///////////////////////////
  }

  // Public
  this.setFnOnBarDrag=function(fn){
	fnOnBarDrag=fn
	}
  this.getScrollVertical = function(){
	return bHorizontal
  }
  this.setScrollVertical = function(valor){
	bHorizontal=valor
	barEle.className= bHorizontal ? 'clsDragBar' :'clsDragBar horizontal'
	}
  this.paint = function(uNewW, uNewH, uNewBarPos, uNewBarLim) {// uNewBarPos and uNewBarLim are optional
    if (uNewW == 0) { return; }
    var w1, h1, w2, h2;
    splW = uNewW;
    splH = uNewH;
    barPos = uNewBarPos || barPos;
    barLim = uNewBarLim || barLim;
    xMoveTo(splEle, uSplX, uSplY);
    xResizeTo(splEle, uNewW, uNewH);
    if (bHorizontal) {
	w1 = barPos;
	h1 = uNewH - 2 * uSplBorderW;
	w2 = uNewW - w1 - uBarW - 2 * uSplBorderW;
	h2 = h1;
	xMoveTo(pane1, 0, 0);
	xResizeTo(pane1, w1, h1);
	xMoveTo(barEle, w1, 0);
	xResizeTo(barEle, uBarW, h1);
	xMoveTo(pane2, w1 + uBarW, 0);
	xResizeTo(pane2, w2, h2);
	}
    else {
	w1 = uNewW - 2 * uSplBorderW;;
	h1 = barPos;
	w2 = w1;
	h2 = uNewH - h1 - uBarW - 2 * uSplBorderW;
	xMoveTo(pane1, 0, 0);
	xResizeTo(pane1, w1, h1);
	xMoveTo(barEle, 0, h1);
	xResizeTo(barEle, w1, uBarW);
	xMoveTo(pane2, 0, h1 + uBarW);
	xResizeTo(pane2, w2, h2);
	}
    if (oSplChild1) {
	pane1.style.overflow = 'hidden';
	oSplChild1.paint(w1, h1);
	}
    if (oSplChild2) {
	pane2.style.overflow = 'hidden';
	oSplChild2.paint(w2, h2);
	}
}

  // Constructor

  var t= $('#'+sSplId) // we assume the splitter has 3 DIV children and in this order:
  var paneles=t.find('.clsPane, .clsDragBar')
  pane1 = paneles[0]
  pane2 = paneles[1]
  barEle = paneles[2]
this.barEle = barEle
  splEle =t[0]

  //  --- slightly dirty hack
  pane1.style.zIndex = 2;
  pane2.style.zIndex = 2;
  barEle.style.zIndex = 1;
  // ---
  barPos = uBarPos;
  barLim = uBarLimit;
  
  this.paint(uSplW, uSplH);
  if (bBarEnabled) {
	xEnableDrag(barEle, null, barOnDrag, null);	
	}

  splEle.style.visibility = 'visible';
} // end xSplitter
xSplitter.prototype.getPosBarra=function (){
	if (this.bHorizontal)
		return this.barEle.offsetLeft
	else
		return this.barEle.offsetTop
	}
xSplitter.prototype.ocultarPanelIzq=function(){
	this.paint(this.splW, this.sqlH, -10, 0)
	}

function xClientWidth()
{
  var v=0,d=document,w=window;
  if(d.compatMode == 'CSS1Compat' && !w.opera && d.documentElement && d.documentElement.clientWidth)
    {v=d.documentElement.clientWidth;}
  else if(d.body && d.body.clientWidth)
    {v=d.body.clientWidth;}
  else if(xDef(w.innerWidth,w.innerHeight,d.height)) {
    v=w.innerWidth;
    if(d.height>w.innerHeight) v-=16;
  }
  return v;
}
function xAddEventListener(e,eT,eL,cap)
{
  if(!(e=xGetElementById(e)))return;
  eT=eT.toLowerCase();
  if(e.addEventListener)e.addEventListener(eT,eL,cap||false);
  else if(e.attachEvent)e.attachEvent('on'+eT,eL);
  else e['on'+eT]=eL;
}

function xGetElementById(e)
{
  if(typeof(e)=='string') {
    if(document.getElementById) e=document.getElementById(e);
    else if(document.all) e=document.all[e];
    else e=null;
  }
  return e;
}
function xMoveTo(e,x,y)
{
  xLeft(e,x);
  xTop(e,y);
}

// xLeft, Copyright 2001-2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL
function xLeft(e, iX)
{
  if(!(e=xGetElementById(e))) return 0;
  var css=xDef(e.style);
  if (css && xStr(e.style.left)) {
    if(xNum(iX)) e.style.left=iX+'px';
    else {
      iX=parseInt(e.style.left);
      if(isNaN(iX)) iX=xGetComputedStyle(e,'left',1);
      if(isNaN(iX)) iX=0;
    }
  }
  else if(css && xDef(e.style.pixelLeft)) {
    if(xNum(iX)) e.style.pixelLeft=iX;
    else iX=e.style.pixelLeft;
  }
  return iX;
}

// xTop, Copyright 2001-2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL
function xTop(e, iY)
{
  if(!(e=xGetElementById(e))) return 0;
  var css=xDef(e.style);
  if(css && xStr(e.style.top)) {
    if(xNum(iY)) e.style.top=iY+'px';
    else {
      iY=parseInt(e.style.top);
      if(isNaN(iY)) iY=xGetComputedStyle(e,'top',1);
      if(isNaN(iY)) iY=0;
    }
  }
  else if(css && xDef(e.style.pixelTop)) {
    if(xNum(iY)) e.style.pixelTop=iY;
    else iY=e.style.pixelTop;
  }
  return iY;
}
// xDef, Copyright 2001-2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

function xDef()
{
  for(var i=0; i<arguments.length; ++i){
      if (arguments[i]===undefined)
          return false;
  }
  return true;
}

// xStr, Copyright 2001-2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

function xStr(s)
{
  for(var i=0; i<arguments.length; ++i){if(typeof(arguments[i])!='string') return false;}
  return true;
}

// xNum, Copyright 2001-2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

function xNum()
{
  for(var i=0; i<arguments.length; ++i){if(isNaN(arguments[i]) || typeof(arguments[i])!='number') return false;}
  return true;
}

// xResizeTo, Copyright 2001-2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

function xResizeTo(e,w,h)
{
  xWidth(e,w);
  xHeight(e,h);
}

// xHeight, Copyright 2001-2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

function xHeight(e,h)
{
  if(!(e=xGetElementById(e))) return 0;
  if (xNum(h)) {
    if (h<0) h = 0;
    else h=Math.round(h);
  }
  else h=-1;
  var css=xDef(e.style);
  if (e == document || e.tagName.toLowerCase() == 'html' || e.tagName.toLowerCase() == 'body') {
    h = xClientHeight();
  }
  else if(css && xDef(e.offsetHeight) && xStr(e.style.height)) {
    if(h>=0) {
      var pt=0,pb=0,bt=0,bb=0;
      if (document.compatMode=='CSS1Compat') {
        var gcs = xGetComputedStyle;
        pt=gcs(e,'padding-top',1);
        if (pt !== null) {
          pb=gcs(e,'padding-bottom',1);
          bt=gcs(e,'border-top-width',1);
          bb=gcs(e,'border-bottom-width',1);
        }
        // Should we try this as a last resort?
        // At this point getComputedStyle and currentStyle do not exist.
        else if(xDef(e.offsetHeight,e.style.height)){
          e.style.height=h+'px';
          pt=e.offsetHeight-h;
        }
      }
      h-=(pt+pb+bt+bb);
      if(isNaN(h)||h<0) return null;
      else e.style.height=h+'px';
    }
    h=e.offsetHeight;
  }
  else if(css && xDef(e.style.pixelHeight)) {
    if(h>=0) e.style.pixelHeight=h;
    h=e.style.pixelHeight;
  }
  return h;
}

// xWidth, Copyright 2001-2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

function xWidth(e,w)
{
  if(!(e=xGetElementById(e))) return 0;
  if (xNum(w)) {
    if (w<0) w = 0;
    else w=Math.round(w);
  }
  else w=-1;
  var css=xDef(e.style);
  if (e == document || e.tagName.toLowerCase() == 'html' || e.tagName.toLowerCase() == 'body') {
    w = xClientWidth();
  }
  else if(css && xDef(e.offsetWidth) && xStr(e.style.width)) {
    if(w>=0) {
      var pl=0,pr=0,bl=0,br=0;
      if (document.compatMode=='CSS1Compat') {
        var gcs = xGetComputedStyle;
        pl=gcs(e,'padding-left',1);
        if (pl !== null) {
          pr=gcs(e,'padding-right',1);
          bl=gcs(e,'border-left-width',1);
          br=gcs(e,'border-right-width',1);
        }
        // Should we try this as a last resort?
        // At this point getComputedStyle and currentStyle do not exist.
        else if(xDef(e.offsetWidth,e.style.width)){
          e.style.width=w+'px';
          pl=e.offsetWidth-w;
        }
      }
      w-=(pl+pr+bl+br);
      if(isNaN(w)||w<0) return null;
      else e.style.width=w+'px';
    }
    w=e.offsetWidth;
  }
  else if(css && xDef(e.style.pixelWidth)) {
    if(w>=0) e.style.pixelWidth=w;
    w=e.style.pixelWidth;
  }
  return w;
}

// xEnableDrag, Copyright 2002-2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

var _xDrgMgr = {ele:null, mm:false};
function xEnableDrag(id,fS,fD,fE)
{
  var ele = xGetElementById(id);
  if (ele) {
    ele.xDraggable = true;
    ele.xODS = fS;
    ele.xOD = fD;
    ele.xODE = fE;
    xAddEventListener(ele, 'mousedown', _xOMD, false);
    if (!_xDrgMgr.mm) {
      _xDrgMgr.mm = true;
      xAddEventListener(document, 'mousemove', _xOMM, false);
    }
  }
}
function _xOMD(e) // drag start
{
  var evt = new xEvent(e);
  var ele = evt.target;
  while(ele && !ele.xDraggable) {
    ele = xParent(ele);
  }
  if (ele) {
    xPreventDefault(e)
    if(editorNivelesActivo) 
    	ocultaIFRAMES()
    ele.xDPX = evt.pageX;
    ele.xDPY = evt.pageY;
    _xDrgMgr.ele = ele;
    xAddEventListener(document, 'mouseup', _xOMU, false);
    if (ele.xODS) {
      ele.xODS(ele, evt.pageX, evt.pageY);
    }
  }
}
function _xOMM(e) // drag
{
  var evt = new xEvent(e);
  if (_xDrgMgr.ele) {
    xPreventDefault(e); 
    var ele = _xDrgMgr.ele;
    var dx = evt.pageX - ele.xDPX;
    var dy = evt.pageY - ele.xDPY;
    ele.xDPX = evt.pageX;
    ele.xDPY = evt.pageY;
    if (ele.xOD) {
      ele.xOD(ele, dx, dy);
    }
    else {
      xMoveTo(ele, xLeft(ele) + dx, xTop(ele) + dy);
      if(editorNivelesActivo)
    	  ocultaIFRAMES()
    }
  }
}
function muestraOcultaIFRAMES(visibles){
	//ocultamos los iframes
	var lista=document.getElementsByTagName('iframe')
	for (var i=0; i<lista.length; i++){
		var iframe=lista[i]
		if (iframe && typeof iframe != 'function') {
			var div=iframe.parentNode
			if (!visibles) 
				ocultaWWW(iframe, div)
			else 
				muestraWWW(iframe, div)
			}
		}
	}
function muestraIFRAMES(){muestraOcultaIFRAMES(true)}
function ocultaIFRAMES(){muestraOcultaIFRAMES(false)}
function _xOMU(e) // drag end
{
  if (_xDrgMgr.ele) {
    xPreventDefault(e); 
    xRemoveEventListener(document, 'mouseup', _xOMU, false);
    if (_xDrgMgr.ele.xODE) {
      var evt = new xEvent(e);
      _xDrgMgr.ele.xODE(_xDrgMgr.ele, evt.pageX, evt.pageY);
    }
    _xDrgMgr.ele = null;
    
    
	muestraIFRAMES()

  }
}

// xEvent, Copyright 2001-2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

function xEvent(evt) // object prototype
{
  var e = evt || window.event;
  if(!e) return;
  if(e.type) this.type = e.type;
  if(e.target) this.target = e.target;
  else if(e.srcElement) this.target = e.srcElement;
  // Section B
  if (e.relatedTarget) this.relatedTarget = e.relatedTarget;
  else if (e.type == 'mouseover' && e.fromElement) this.relatedTarget = e.fromElement;
  else if (e.type == 'mouseout') this.relatedTarget = e.toElement;
  // End Section B
  if(xDef(e.pageX,e.pageY)) { this.pageX = e.pageX; this.pageY = e.pageY; }
  else if(xDef(e.clientX,e.clientY)) { this.pageX = e.clientX + xScrollLeft(); this.pageY = e.clientY + xScrollTop(); }
  // Section A
  if (xDef(e.offsetX,e.offsetY)) {
    this.offsetX = e.offsetX;
    this.offsetY = e.offsetY;
  }
  else if (xDef(e.layerX,e.layerY)) {
    this.offsetX = e.layerX;
    this.offsetY = e.layerY;
  }
  else {
    this.offsetX = this.pageX - xPageX(this.target);
    this.offsetY = this.pageY - xPageY(this.target);
  }
  // End Section A
  this.keyCode = e.keyCode || e.which || 0;
  this.shiftKey = e.shiftKey;
  this.ctrlKey = e.ctrlKey;
  this.altKey = e.altKey;
}

// xPreventDefault, Copyright 2004-2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

function xPreventDefault(e)
{
  if (e && e.preventDefault) e.preventDefault();
  else if (window.event) window.event.returnValue = false;
}

// xRemoveEventListener, Copyright 2001-2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

function xRemoveEventListener(e,eT,eL,cap)
{
  if(!(e=xGetElementById(e)))return;
  eT=eT.toLowerCase();
  if(e.removeEventListener)e.removeEventListener(eT,eL,cap||false);
  else if(e.detachEvent)e.detachEvent('on'+eT,eL);
  else e['on'+eT]=null;
}

// xClientHeight, Copyright 2001-2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

function xClientHeight()
{
  var v=0,d=document,w=window;
  if(d.compatMode == 'CSS1Compat' && !w.opera && d.documentElement && d.documentElement.clientHeight)
    {v=d.documentElement.clientHeight;}
  else if(d.body && d.body.clientHeight)
    {v=d.body.clientHeight;}
  else if(xDef(w.innerWidth,w.innerHeight,d.width)) {
    v=w.innerHeight;
    if(d.width>w.innerWidth) v-=16;
  }
  return v;
}
// xGetComputedStyle, Copyright 2002-2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

function xGetComputedStyle(oEle, sProp, bInt)
{
  var s, p = 'undefined';
  var dv = document.defaultView;
  if(dv && dv.getComputedStyle){
    s = dv.getComputedStyle(oEle,'');
    if (s) p = s.getPropertyValue(sProp);
  }
  else if(oEle.currentStyle) {
    // convert css property name to object property name for IE
    var i, c, a = sProp.split('-');
    sProp = a[0];
    for (i=1; i<a.length; ++i) {
      c = a[i].charAt(0);
      sProp += a[i].replace(c, c.toUpperCase());
    }
    p = oEle.currentStyle[sProp];
  }
  else return null;
  return bInt ? (parseInt(p) || 0) : p;
}

// xScrollTop, Copyright 2001-2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

function xScrollTop(e, bWin)
{
  var offset=0;
  if (!xDef(e) || bWin || e == document || e.tagName.toLowerCase() == 'html' || e.tagName.toLowerCase() == 'body') {
    var w = window;
    if (bWin && e) w = e;
    if(w.document.documentElement && w.document.documentElement.scrollTop) offset=w.document.documentElement.scrollTop;
    else if(w.document.body && xDef(w.document.body.scrollTop)) offset=w.document.body.scrollTop;
  }
  else {
    e = xGetElementById(e);
    if (e && xNum(e.scrollTop)) offset = e.scrollTop;
  }
  return offset;
}

// xScrollLeft, Copyright 2001-2006 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

function xScrollLeft(e, bWin)
{
  var offset=0;
  if (!xDef(e) || bWin || e == document || e.tagName.toLowerCase() == 'html' || e.tagName.toLowerCase() == 'body') {
    var w = window;
    if (bWin && e) w = e;
    if(w.document.documentElement && w.document.documentElement.scrollLeft) offset=w.document.documentElement.scrollLeft;
    else if(w.document.body && xDef(w.document.body.scrollLeft)) offset=w.document.body.scrollLeft;
  }
  else {
    e = xGetElementById(e);
    if (e && xNum(e.scrollLeft)) offset = e.scrollLeft;
  }
  return offset;
}



// xSetCookie r3, Copyright 2001-2007 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

function xSetCookie(name, value, expire, path)
{
  document.cookie = name + "=" + escape(value) +
                    ((!expire) ? "" : ("; expires=" + expire.toGMTString())) +
                    "; path=" + ((!path) ? "/" : path);
}

// xGetCookie r1, Copyright 2001-2007 Michael Foster (Cross-Browser.com)
// Part of X, a Cross-Browser Javascript Library, Distributed under the terms of the GNU LGPL

function xGetCookie(name)
{
  var value=null, search=name+"=";
  if (document.cookie.length > 0) {
    var offset = document.cookie.indexOf(search);
    if (offset != -1) {
      offset += search.length;
      var end = document.cookie.indexOf(";", offset);
      if (end == -1) end = document.cookie.length;
      value = unescape(document.cookie.substring(offset, end));
    }
  }
  return value;
}

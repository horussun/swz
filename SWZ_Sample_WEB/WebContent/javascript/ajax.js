
function AjaxCalls() {}

var HEIGHT_UNIT = 22;

AjaxCalls.updateContent = function(formId,cmd,val,url,seln,selk,selm,unlock,divId) {
	url += ( ( ( url.indexOf( "?" ) > -1 ) ? "&" : "?" ) + "cmd=" + cmd );
	
	new Ajax.Request(
		url, 
		{
			method: 'post', 
			postBody: Form.serialize( formId ),
			onComplete: processXML
		});
}

AjaxCalls.replace = function( formId, elementId, url, isReplace, doEvalScripts ) {
	new Ajax.Updater(
		elementId,
		url, 
		{
			method: 'post',
			replace: isReplace,
			evalScripts: doEvalScripts,
			postBody: Form.serialize( formId ),
			onComplete: replaceDone,
			onException: function( req, ex ) { return topLoc(); }
		});
}

AjaxCalls.expNotify = function(f, cmd,val,url,seln,selk,selm,unlock,divId) {
	var cIds = '';
	
	for ( i = 0; i < nistglob.cIds.length; i++ ) {
		cIds += nistglob.cIds[i];
		if ( i + 1 < nistglob.cIds.length ) cIds += '|';
	}
	
	if ( cIds.length > 0 ) {
		new Ajax.Request(
			url, 
			{
				method: 'get', 
				parameters: 'cmd=' + cmd + '&tableNum=' + divId + '&thisViewURL=' + $F('thisViewURL') + '&collapsedIds=' + cIds + '&ts=' + new Date().getTime(),
				asynchronous : true,
			  onException: function( req, ex ) { return topLoc(); }
			});
	}
}

AjaxCalls.help = function( url, page ) {
	new Ajax.Request(
		url, 
		{
			method: 'get', 
			parameters: 'cmd=help&page=' + page + '&ts=' + new Date().getTime(),
			onComplete: forwardHelp,
			onException: function( req, ex ) { return topLoc(); }
		} );
}

function forwardHelp( req ) {
	var winId = window.open( //"/web/base/help/testOutput/index.htm?context=TestHelp26&topic=FP_dedicatedFleets2",
		req.responseXML.getElementsByTagName( "helpPage" )[0].firstChild.nodeValue, 
		'_blank' );
	
	winId.focus();
}

AjaxCalls.adjustHeight = function(formId,cmd,val,url,seln,selk,selm,unlock,divId) {
	var adjust = ( cmd == 'increase' ? HEIGHT_UNIT : -HEIGHT_UNIT );
  
	var adjustTo = $('D' + divId).offsetHeight + adjust;
  
	if ( adjustTo < HEIGHT_UNIT * 2 ) {
  		alert( 'The table height cannot be any smaller than it already is!' );
		return;
	}
  
	$('D' + divId).style.height = ( adjustTo + 'px' );

	new Ajax.Request(
		url, 
		{
			method: 'get', 
			parameters: 'cmd=' + cmd + '&tableNum=' + divId + '&thisViewURL=' + $F('thisViewURL') + '&ts=' + new Date().getTime(), 
			onComplete: replaceDone,
			onException: function( req, ex ) { return topLoc(); }
		});
}

var nistglob = new Object();
nistglob.rowCache = [];
var footerUrl;
var footerRepId;
var srcEvent;

AjaxCalls.drill = function(cmd,val,url,seln,selk,selm,unlock,repId) {
  srcEvent = new Object();
  srcEvent.clientX = event.clientX;
  srcEvent.clientY = event.clientY;
  
  lockSubmit = true;
	new Ajax.Request(
		url, 
		{
			method: 'get', 
			parameters: 'cmd=' + cmd + '&sel=' + selk + '&ts=' + new Date().getTime(), 
			onComplete: showDrillMenu,
			onException: function( req, ex ) { return topLoc(); }
		});
}

var ie5=document.all&&document.getElementById;
var ns6=document.getElementById&&!document.all;

function showDrillMenu( req ) {
	var menu = $('drillMenu');
	menu.innerHTML = req.responseText;
	var els = $A( menu.getElementsByTagName( 'DIV' ) );
	
  els.each( function(mi) {
      mi.style.display = 'block';
  } );

  var rightedge=ie5? document.body.clientWidth-srcEvent.clientX : window.innerWidth-srcEvent.clientX;
  var bottomedge=ie5? document.body.clientHeight-srcEvent.clientY : window.innerHeight-srcEvent.clientY;

  if (rightedge<menu.offsetWidth)
    menu.style.left=ie5? document.body.scrollLeft+srcEvent.clientX-menu.offsetWidth : window.pageXOffset+srcEvent.clientX-menu.offsetWidth;
  else
    menu.style.left=ie5? document.body.scrollLeft+srcEvent.clientX : window.pageXOffset+srcEvent.clientX;

  if (bottomedge<menu.offsetHeight)
    menu.style.top=ie5? document.body.scrollTop+srcEvent.clientY-menu.offsetHeight : window.pageYOffset+srcEvent.clientY-menu.offsetHeight;
  else
    menu.style.top=ie5? document.body.scrollTop+srcEvent.clientY : window.pageYOffset+srcEvent.clientY;

	if ( els.length > 20 ) {
		menu.style.overflow = 'auto';
		menu.style.height = '20em';
	} else {
		menu.style.overflow = 'hidden';
		menu.style.height = 'auto';
	}
		
  menu.style.visibility='visible';
  menu.style.display='block';
  
  lockSubmit = false;
  Event.observe(window.document, 'click', hideDrillMenu, true);
  srcEvent = null;
}

function hideDrillMenu() {
   var menu = $('drillMenu');
   if ( !menu ) return;
   menu.style.visibility='hidden';
   menu.style.display='none';
   return true;
}

function hlDrill(e,hl){
  var firingobj=ie5? event.srcElement : e.target;
  
  if (firingobj.className=='popupMenuItem'||ns6&&firingobj.parentNode.className=='popupMenuItem'){
    if (ns6&&firingobj.parentNode.className=='popupMenuItem') firingobj=firingobj.parentNode;
    
    if ( hl ) {
      firingobj.style.backgroundColor='#5e615b';
      firingobj.style.color='#FFF';
    } else {
    	firingobj.style.backgroundColor='';
   		firingobj.style.color='black';
    	window.status='';
    }
  }
}

AjaxCalls.page = function(formId,cmd,val,url,seln,selk,selm,unlock,repId) {
	if (lockSubmit) return false;
	lockedSubmit();
	footerUrl = url;
	footerRepId = repId;
  nistglob.formId = formId;
  
	url += ( ( ( url.indexOf( "?" ) > -1 ) ? "&" : "?" ) + "cmd=" + cmd + "&repId=" + repId );
	
	new Ajax.Updater(
		repId,
		url,
		{
			method: 'post',
			evalScripts: false,
			postBody: Form.serialize( formId ),
			onComplete: replaceFooter,
			onException: function( req, ex ) { return topLoc(); }
		});
			
	var s = eval( formId + '_S' + repId.substring(1) + 'Sel' );

 	if ( s ) { 
		s.sels = [];
		s.allRows = null;
		
		if ( s.cap ) {
			s.rows = [];
			s.selRows = [];
		}	
	  
		$( s.seln ).value = '';
	}
}

function topLoc() {
	var tl = top.location.toString();
	if( tl.indexOf( '?' ) > -1 ) {
		tl += '&' + new Date().getTime();
	} else {
		tl += '?' + new Date().getTime();
	}
	
	top.location = tl;
	
	return true;
}
	
function nopResponse() {
}

function replaceFooter() {
	replaceDone();
	
	new Ajax.Updater(
		'P' + footerRepId,
		footerUrl + '?cmd=apply&repId=' + footerRepId,
		{
			method: 'post',
			evalScripts: false,
			postBody: Form.serialize( nistglob.formId ),
			onComplete: nopResponse,
			onException: function( req, ex ) { return topLoc(); }
		});
			
	var s = eval( nistglob.formId + '_S' + footerRepId.substring(1) + 'Sel' );

	if ( s && s.cap ) {
		var rows =  $(footerRepId).getElementsByTagName( "TR" );
  	
		for ( var i = 1; i < rows.length; i++ ) {
			s.regRow( rows[i].id );
		}
	}

	updateAllSelButtons( 'check' );
}

function replaceDone() {
	if ( window.divMgr ) {
		divMgr.drawHeader();
		divMgr.sizeHeader();
	}
  
	if ( parent.waitFrame ) { 
		parent.waitFrame.hide(4);
	} else if ( window.waitFrame ) {
		window.waitFrame.hide(4);
	}
  
	unLockSubmit();
}

function processXML( req ) {
	var res = req.responseXML;
	var selects = res.getElementsByTagName( "selects" );
	
	if ( selects && selects.length ) {
		processSelects( selects[0].childNodes );
	}
}

function processJavascript( scripts ) {
	for ( var i = 0; i < scripts.length; i++ ) {
		eval( scripts.firstChild.nodeValue );
	}
}


function processSelects( selects ) {
	for ( var i = 0; i < selects.length; i++ ) {
		var current = selects[i];
		var name = current.getAttribute( "name" );
		var select = $(name);
		
		select.options.length = 0;
		select.selectedIndex = 0;
		
		var options = current.getElementsByTagName( "option" );
		
		for ( var j = 0; j < options.length; j++ ) {
			var option = options[j];
			var opt = document.createElement( "OPTION" );
			opt.value = option.getAttribute( "value" );
			opt.text = option.firstChild.nodeValue;
			opt.className = "s" + ( j % 2 == 0 ? "e" : "o" );
			select.add( opt );
	    
			if ( option.getAttribute( "sel" ) ) {
				select.selectedIndex = j;
			}
		}
	}
}

AjaxCalls.expand = function(formId,cmd,val,url,seln,selk,selm,unlock,repId) {
	var arr = repId.split( ',' )
	divId = arr[ 0 ]
	repId = arr[ 1 ]
	
	nistglob.divId = divId;
	nistglob.rowId = repId;
	nistglob.formId = formId;
	
	if ( false && nistglob.rowCache[divId + repId] && nistglob.rowCache[divId + repId][nistglob.ex] ) {
		expandXML();
	} else {
		window.document.body.style.cursor='wait';
		
		if ( parent.waitFrame ) 
			parent.waitFrame.show();
		else if ( window.waitFrame ) 
		    waitFrame.show();
		    
		new Ajax.Request(
			url + '?cmd=' + cmd + '&repId=' + divId + '&rowId=' + repId,
			{
				method: 'post', 
				postBody: Form.serialize( formId ),
				onComplete: expandXML,
				onException: function( req, ex ) { return topLoc(); }
			});
	}
}

function isEx( ex ) {
  if ( ex.src ) {
  	nistglob.ex = ex.src.indexOf( 'collapse' ) > -1;
  }
//	nistglob.ex = ex;
}

function expandXML( req ) {
	var existing = $(nistglob.rowId);
	var swapId = nistglob.divId + 'Swap';
	var swap = $(swapId);
	var responseText;
	
	if ( req ) {
		responseText = req.responseText;
		
		if ( !nistglob.rowCache[nistglob.divId+nistglob.rowId] ) {
			nistglob.rowCache[nistglob.divId+nistglob.rowId] = [];
		}
		
		nistglob.rowCache[nistglob.divId+nistglob.rowId][nistglob.ex] = responseText;
	} else {
		responseText = nistglob.rowCache[nistglob.divId+nistglob.rowId][nistglob.ex];
	}

  Element.replace(swap, "<TABLE ID='" + swapId + "' STYLE='display:none;'>" + responseText + "</TABLE>");
	swap = $(swapId);

	var table = existing;
	
	while ( table.tagName != 'TABLE' )
		table = table.parentNode;

	var srows = swap.rows;
	var body = table.tBodies[0];
  
	for ( var i=0, j=srows.length; i<j; i++ ) {
		var newRow = srows[i].cloneNode(true);
		body.insertBefore(newRow,existing);
	} 
	var expLevel=null;
	if(document.all){
		expLevel = existing.N_LVL;
	}else{
		expLevel = existing.attributes['N_LVL'].nodeValue;
	}
	for (;;) {
		var sibling = existing.nextSibling;
		existing.parentNode.removeChild( existing );
		if(document.all){
			if ( !sibling || !sibling.N_LVL || sibling.N_LVL <= expLevel ) break;
		}else{
			if ( !sibling || !sibling.attributes ||!sibling.attributes['N_LVL'] || sibling.attributes['N_LVL'].nodeValue <= expLevel ) break;
		}
		
		
		existing = sibling;
	}
	
  Element.replace(swap, "<TABLE ID='" + swapId + "' STYLE='display:none;'></TABLE>");
	
	$('esexpid').value = '';
	
	replaceDone();
	
	var sel = eval( nistglob.formId + '_S' + nistglob.divId.substring(1) + 'Sel' );	
	
	if ( sel ) {
	  for ( var i = 0; i < sel.sels.length; i++ ) {
	    if ( nistglob.rowId == sel.sels[i] ) {
	    	sel.aselect( null, $( sel.sels[i] ) );
	    }
	  }
	}
	
	window.document.body.style.cursor='default';
}





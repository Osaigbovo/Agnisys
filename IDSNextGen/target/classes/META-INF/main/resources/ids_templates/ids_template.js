var showProp = "Show Prop";
var hideProp = "Hide Prop";
var showDesc = "Show Desc";
var hideDesc = "Hide Desc";
var curr_row;
var curr_row_signal;
var query="";
var random_Num;
var isTabFocus=false;
var tabToolbar;
var clipboard_tab=null;
var tempTable;
var isrowselected=false;
var curr_row_obj=null;
var isFileSaved=true;

$(document).ready(function(){

	$('.fieldname').keyup(function(){	
		var value=$(this).val();		
		if(value!==""){			
			if(!/^[a-zA-Z][a-z0-9._\-]*$/.test(value)){	
				console.log("Error not valid");			
				return false;			
			}		
		}	
	});	

	$('.descInput').keyup(function(){		
		auto_grow(this);	
	});	

	$('.propInput').keyup(function(){		
		auto_grow(this);	
	});


	/*
	$("#btnprop").html(hideProp);
	$("#btndesc").html(hideDesc);
	*/


	/*
	$('body').bind("DOMSubtreeModified",function(){
		if(isFileSaved){
			alert('changed');
			unSetUnsaved();
			isFileSaved=false;
		}
	});
*/

	/*
	$('.idsTemp').hover(function(){
		tabToolbar=this;
		$( this ).prepend( $( "<a style='position: absolute;margin-left: 472px;margin-top: -15px' href='#'>X</a>" ) );
	}, 
						function () {
		$( this ).find( "a:last" ).remove();
	});*/
	/*
	$('.idsTemp').click(function(){
		tabToolbar=this;
	});*/
});

$(document).bind("click", function(event) {
	/*document.getElementById("rmenu").className = "hide";*/
});



function mouseX(evt) {
	if (evt.pageX) {
		return evt.pageX;
	} else if (evt.clientX) {
		return evt.clientX + (document.documentElement.scrollLeft ?
							  document.documentElement.scrollLeft :
							  document.body.scrollLeft);
	} else {
		return null;
	}
}

function mouseY(evt) {
	if (evt.pageY) {
		return evt.pageY;
	} else if (evt.clientY) {
		return evt.clientY + (document.documentElement.scrollTop ?
							  document.documentElement.scrollTop :
							  document.body.scrollTop);
	} else {
		return null;
	}
}

function insertNewTabRow(evt, obj) {
	if(evt.keyCode===9){
		console.log('n='+obj.tagName.toUpperCase());
		while(obj.tagName.toUpperCase() !== "DIV") {
			console.log('n='+obj.tagName.toUpperCase());
			obj = obj.parentNode;
		}


		var table = document.getElementById(obj.id);
		var row = table.insertRow(parseInt(curr_row)+1);
		row.setAttribute('onkeydown','setCurrRow(this)');
		row.setAttribute('onclick',"setCurrRow(this)");
		row.setAttribute('class','field');		
		var input;
		var cell;

		cell=row.insertCell(0);		
		cell.setAttribute('class', 'bits');     
		input=document.createElement("input");
		input.setAttribute('style','width:88%');
		var rowdel=document.createElement("a");
		rowdel.innerText='x';
		rowdel.setAttribute('title','delete this row');
		rowdel.setAttribute('style','color:red');
		rowdel.setAttribute('onclick','deleteRow(this);');
		cell.appendChild(rowdel);
		cell.appendChild(input);


		cell=row.insertCell(1);
		cell.setAttribute('class', 'fieldname');  
		input=document.createElement("input");
		cell.appendChild(input);


		cell=row.insertCell(2);
		cell.setAttribute('class','sw thirdCell');
		input=document.createElement("input");
		cell.appendChild(input);


		cell=row.insertCell(3);
		cell.setAttribute('class','hw thirdCell');
		input=document.createElement("input");
		cell.appendChild(input);


		cell=row.insertCell(4);
		cell.setAttribute('onkeydown',"insertNewRow(event,this);");
		cell.setAttribute('class','default');
		input=document.createElement("input");
		cell.appendChild(input);

	}
	/*add property row*/
	else if(evt.keyCode==80&&evt.altKey){
		while(obj.tagName.toUpperCase() !== "TABLE") {
			obj = obj.parentNode;
		}

		var table = document.getElementById(obj.id);
		var row = table.insertRow(parseInt(curr_row)+1);
		row.setAttribute('onkeydown','setCurrRow(this)');
		row.setAttribute('onclick',"setCurrRow(this)");
		var input;
		var cell;

		cell=row.insertCell(0);		
		cell.setAttribute('colspan', '5');    
		cell.setAttribute('onkeydown',"insertNewRow(event,this);");
		cell.setAttribute('class','propclass');
		console.log('add prop');
		input=document.createElement("textarea");  
		input.setAttribute('class','propInput');
		cell.appendChild(input);

	}
}


$('.field').click(function(){
	var row_index = $(this).parent().index();
	var col_index = $(this).index();
});

function selectElementContents(el) {
	var body = document.body, range, sel;
	if (document.createRange && window.getSelection) {
		range = document.createRange();
		sel = window.getSelection();
		sel.removeAllRanges();
		try {
			range.selectNodeContents(el);
			sel.addRange(range);
		} catch (e) {
			range.selectNode(el);
			sel.addRange(range);
		}
	} else if (body.createTextRange) {
		range = body.createTextRange();
		range.moveToElementText(el);
		range.select();
	}
}

function selectElementContents2(el) {
	var elemen=document.getElementById(el);
	var body = document.body, range, sel;
	if (document.createRange && window.getSelection) {
		range = document.createRange();
		sel = window.getSelection();
		sel.removeAllRanges();
		try {
			range.selectNodeContents(elemen);
			sel.addRange(range);
		} catch (e) {
			range.selectNode(elemen);
			sel.addRange(range);
		}
	} else if (body.createTextRange) {
		range = body.createTextRange();
		range.moveToElementText(elemen);
		range.select();
	}
}


var isrowselected=false;
var prevTab=null;

function mainbodyclick(){}

function tabClick(ele){
	if(prevTab!=null){
		$(prevTab).removeAttr('style');
	}
	if(isrowselected){
		isrowselected=false;
	}
	else{
		clsTab=ele.id;
		$(ele).attr('style','box-shadow:rgb(67, 134, 239) 2px 2px 1px');

		/*$(this).attr('contenteditable','false');*/
		curr_row_obj=null;


		prevTab=ele;
	}

}

function setCurrRow(currnRow){
	curr_row=currnRow.rowIndex;
	curr_row_obj=currnRow;
	isrowselected=true;
}

function setCurrRowSignal(currnRow){
	curr_row_signal=currnRow.rowIndex;
}

var isCut=false;
function deleteElement(){
	try{
		addIntoHistory();
		if(curr_row_obj){
			deletecurrrow(curr_row_obj);
			curr_row_obj=null;
		}
		else{
			if(clsTab!=null){
				selectElementContents(document.getElementById(clsTab));
				document.execCommand('delete');
			}
		}

	}
	catch(e){alert('Err (delete) : '+e.message);}
}

var copyClipBoardTab;
function copyIDSTab(){	
	isCut=false;
	/*clipboard_tab=document.getElementById(clsTab);*/
	copyClipBoardTab=$(document.getElementById(clsTab)).prop('outerHTML');
}
function pasteIDSTab(){
	try{
		addIntoHistory();
		var tab="<table contenteditable='false' class='reg idsTemp' id='tab1' style='box-shadow: rgb(128, 128, 128) 1px 1px 1px;'><tbody><tr ><td class='header readOnly'>hell</td><td title='egister name' class='name' contenteditable='false'></td><td title='offset' class='offset'></td><td class='address addCell readOnly'><div class='splitVer setBorder' title='Address'><label class='label'>Address |</label><label></label></div><div class='splitVer' title='Default'><label class='label'>Default |</label><label></label></div> </td></tr><tr><td colspan='5' title='add properties' class='propclass prop'></td></tr>";
		document.execCommand("insertHTML", false, copyClipBoardTab);	


		if(isCut==true){
			selectElementContents(document.getElementById(clsTab));
			document.execCommand('delete');
			isCut=false;
		}
		clipboard_tab=null;

		setTabID();
	}
	catch(e){
		alert("Err paste : "+e.message);
	}
}
function cutIDSTab(){
	isCut=true;
	clipboard_tab=clsTab;

	copyClipBoardTab=$(document.getElementById(clsTab)).prop('outerHTML');
}

function copy(){
	var p=document.execCommand('copy'); 
}
function cut(){
	document.execCommand('cut');
}
function CopyStr(){
	var p=document.execCommand('copy'); 
}
function paste(){
	addIntoHistory();
	var p=document.execCommand('paste');
}
function undo(){
	var p=document.execCommand('undo');
}
function redo(){
	document.execCommand('redo');
}
function deleteHtml(){
	addIntoHistory();	
	var p=document.execCommand('delete');
}

function selectAllStr(){
	var p=document.execCommand('selectAll', false, null);
}

function setBold(){
	document.execCommand('bold');
}
function setItalic(){
	document.execCommand('italic');
}
function setUnderline(){
	document.execCommand('underline');
}

function setAlignLeft(){
	document.execCommand('justifyLeft');
}

function setAlignRight(){
	document.execCommand('justifyRight');
}
function setAlignCenter(){
	document.execCommand('justifyCenter');
}
function setAlignFull(){
	document.execCommand('justifyFull');
}


function setOrderedList(){
	document.execCommand('insertOrderedList');
}
function setUnOrderedList(){
	document.execCommand('insertUnorderedList');
}

function setStrikeThrough(){
	document.execCommand('strikeThrough');
}
function setSubscript(){
	document.execCommand('subscript');
}
function setSuperscript(){
	document.execCommand('superscript');
}

function setIncreaseSize(){
	document.execCommand('increaseFontSize');
}
function setDecreaseSize(){
	document.execCommand('decreaseFontSize');
}

function setFontName(fontName){
	document.execCommand("fontName", false, fontName);
}

function setFormatsName(formatsName){
	document.execCommand("formatBlock", false, formatsName);	
}

function setFontSize(fontSize){
	document.execCommand("fontSize", false, fontSize);
}


function insertHTML(img) {
	var id = "rand" + Math.random();
	img = "<img src=\"" + img + "\" id=" + id + ">";
	if(document.all) {
		var range = document.selection.createRange();
		range.pasteHTML(img);
		range.collapse(false);
		range.select();
	} else {
		document.execCommand("insertHTML", false, img);
	}
	return document.getElementById(id);
};

var TRange = null;
function findString(str) {
	if (parseInt(navigator.appVersion) < 4) return;
	var strFound;
	if (window.find) {

		/* CODE FOR BROWSERS THAT SUPPORT window.find*/
		strFound = self.find(str);
		if (!strFound) {
			strFound = self.find(str, 0, 1);
			while (self.find(str, 0, 1)) continue
		} else {
			return true;
		}
	}
	else if (navigator.appName.indexOf("Microsoft") != -1) {

		/* EXPLORER-SPECIFIC CODE*/
		if (TRange != null) {
			TRange.collapse(false);
			strFound = TRange.findText(str);
			if (strFound) {
				TRange.select();
				return true;
			}
		}
		if (TRange == null || strFound == 0) {
			TRange = self.document.body.createTextRange();
			strFound = TRange.findText(str);
			if (strFound) {
				TRange.select();
				return true;
			}
		}
	}
	else if (navigator.appName == "Opera") {
		return false;
	}
	return false;
}

function findReplaceString(str,newStr) {
	document.body.innerText.replace(new RegExp(str, 'g'), newStr);

}


var clsTab=null;


function deleteRow(obj){
	curr_row_obj=obj;	
}

function deletecurrrow(obj){
	$(obj).closest("tr").remove();
}


/*@JAVAFX*/
function setTabID(){
	var idsTables = document.getElementsByClassName("idsTemp");
	for(var i = 0; i < idsTables.length; i++)
	{
		random_Num=Math.random();
		/*idsTables[i].setAttribute('id','tab'+i);*/
		idsTables[i].setAttribute('id','tab'+i+random_Num);
	}
}


function getFirstClass(classArry){
	return classArry.split(' ')[0];
}

function auto_grow(element) {
	element.style.height = "5px";
	element.style.height = (element.scrollHeight)+"px";
}

function insertSystem(imgPath){
	addIntoHistory();
	random_Num=Math.random();
	query="<table contenteditable='false' class='system idsTemp' onclick='tabClick(this)' id='tab_system"+random_Num+"'><tbody><tr><td class='header readOnly' style='width:105px;'></td><td title='system name' class='name wideWidthName'>system_name</td><td class='specImage'><img title='System' alt='System' src="+imgPath+"></td><td class='address addCell readOnly'><label class='label'>address|</label><label class='addrvalue' title='address'></label></td></tr><tr><td colspan='5'  title='add properties' class='propclass'></td></tr><tr><td colspan='5' title='add description here' class='desc descclass'></td></tr></tbody> </table><br>";
	pasteHtmlAtCaret(query);
}

function insertBoard(imgPath){
	addIntoHistory();
	random_Num=Math.random();
	query="<table contenteditable='false' class='board idsTemp' onclick='tabClick(this)' id='tab_board"+random_Num+"'> <tbody><tr><td class='header readOnly' style='width:105px;'></td><td title='board name' class='name wideWidthName'>board_name</td>     <td class='specImage'><img title='Board' alt='Board' src="+imgPath+"></td><td class='address addCell readOnly'><label class='label'>address|</label><label class='addrvalue' title='address'></label></td></tr><tr ><td colspan='5' title='add properties'  class='propclass'></td></tr><tr ><td colspan='5' title='add description here' class='desc descclass'></td></tr></tbody></table><br>";
	pasteHtmlAtCaret(query);
	return "hello board!";
}

function insertChip(imgPath){
	addIntoHistory();
	random_Num=Math.random();
	query="<table contenteditable='false' class='chip idsTemp' onclick='tabClick(this)' id='tab_chip"+random_Num+"'><tbody><tr><td class='header readOnly'></td> <td title='chip name' class='name'>chip_name</td><td title='offset' class='offset'></td><td class='specImage'><img title='Chip' alt='Chip' src="+imgPath+"></td><td class='address addCell readOnly'><label class='label'>address|</label><label class='addrvalue' title='address'></label></td></tr><tr><td colspan='5' title='add properties'  class='propclass'></td></tr><tr><td colspan='5' title='add description here' class='desc descclass'></td></tr></tbody></table><br>";
	pasteHtmlAtCaret(query);
}

function insertBlock(imgPath){
	addIntoHistory();
	random_Num=Math.random();
	query="<br><table contenteditable='false' class='block idsTemp' onclick='tabClick(this)' id='tab_block"+random_Num+"'><tbody><tr><td class='header readOnly'></td><td title='block name' class='name'>block_name</td><td title='offset' class='offset'></td><td class='specImage'><img title='Block' alt='Block' src="+imgPath+"></td><td class='address addCell readOnly'><label class='label'>address|</label><label class='addrvalue' title='address'></label></td></tr><tr><td colspan='5' title='add properties' class='propclass'></td></tr><tr><td colspan='5' title='add description here' class='desc descclass'></td></tr></tbody> </table><br>";
	pasteHtmlAtCaret(query);
}

function insertRegGroup(imgPath){
	addIntoHistory();
	random_Num=Math.random();
	query="<table contenteditable='false' class='section idsTemp' onclick='tabClick(this)' id='tab_section"+random_Num+"'><tbody><tr><td class='header readOnly'></td><td class='name' title='reg group name'>reggroup_name</td><td title='offset' class='offset'></td><td class='specImage'><img title='RegGroup' alt='RegGrou[]' src="+imgPath+"></td><td class='address addCell readOnly'><label class='label'>address|</label><label class='addrvalue' title='address'></label></td></tr><tr><td colspan='5' title='add properties'  class='propclass'></td></tr><tr><td colspan='5' title='add description here' class='desc descclass'></td></tr></tbody> </table><br><br><br><br><table class='endreggroup idsTemp' id='tab1'><tbody><tr class='label'><td>End RegGroup</td></tbody></table> <br><br>";
	pasteHtmlAtCaret(query);
}



function insertReg(imgPath){
	addIntoHistory();
	random_Num=Math.random();
	query="<br><table contenteditable='false' onclick='tabClick(this);' class='reg idsTemp' id='tab_reg"+random_Num+"'><tbody><tr><td class='header readOnly'></td><td title='reg name' class='name'>reg_name</td><td title='offset' class='offset' colspan='2'></td><td class='specImage'><img title='Register' alt='Register' src="+imgPath+"></td><td class='address addCell readOnly' ><div class='splitVer setBorder' title='address'><label class='label'>address|</label><label class='addrvalue'></label></div><div class='splitVer' title='Default'><label class='label'>default |</label><label class='defvalue'></label></div> </td> <td class='regwidth hideWidth'>32</td></tr><tr><td colspan='6' title='add properties' class='propclass' contenteditable='true'></td></tr><tr><td colspan='6' title='add description here' class='desc descclass'></td></tr><tr><td colspan='6' class='border'></td></tr><tr><td colspan='6' class='fieldtd'><table class='fields idsTemp' id='tab_reg_field"+random_Num+"'><tr class='label'><td class='ddregbits'>bits</td><td class='lblfieldname'>name</td><td class='lblsw'>s/w</td><td class='lblhw'>h/w</td><td class='lbldefault'>default</td><td class='lbldesc'>description</td></tr><tr onkeyup='setCurrRow(this);' onclick='setCurrRow(this);' class='field edited'><td class='bits'></td><td class='fieldname' ><br></td><td class='sw'><br></td><td class='hw'></td><td class='default'><br></td><td class='desc fielddesc' onkeydown='insertNewRow(event,this);'></td></tr></table></td></tr></tbody></table><br>";

	pasteHtmlAtCaret(query);
}

function insertReg8(imgPath){	
	random_Num=Math.random();
	query="<br><div><table contenteditable='false' onclick='tabClick(this);' class='reg idsTemp' id='tab_reg"+random_Num+"'><tbody><tr><td class='header readOnly'></td><td title='reg name' class='name'>reg_name</td><td title='offset' class='offset' colspan='2'></td><td class='specImage'><img title='8 bits register' alt='Register' src="+imgPath+"></td><td class='address addCell readOnly' ><div class='splitVer setBorder' title='address'><label class='label'>address|</label><label class='addrvalue'></label></div><div class='splitVer' title='Default'><label class='label'>default |</label><label class='defvalue'></label></div> </td><td class='regwidth hideWidth'>8</td></tr><tr><td colspan='6' title='add properties' class='propclass' contenteditable='true'></td></tr><tr><td colspan='6' title='add description here' class='desc descclass'></td></tr><tr><td colspan='6' class='border'></td></tr><tr><td colspan='6' class='fieldtd'><table class='fields idsTemp' id='tab_reg_field"+random_Num+"'><tr class='label'><td class='ddregbits'>bits</td><td class='lblfieldname'>name</td><td class='lblsw'>s/w</td><td class='lblhw'>h/w</td><td class='lbldefault'>default</td><td class='lbldesc'>description</td></tr><tr onkeyup='setCurrRow(this);' onclick='setCurrRow(this);' class='field edited'><td class='bits'>7:0</td><td class='fieldname' ><br></td><td class='sw'><br></td><td class='hw'></td><td class='default'><br></td><td class='desc fielddesc' onkeydown='insertNewRow(event,this);'></td></tr></table></td></tr></tbody></table></div><br>";
	pasteHtmlAtCaret(query);
}
function insertReg16(imgPath){	
	addIntoHistory();
	random_Num=Math.random();
	query="<br><div><table contenteditable='false' onclick='tabClick(this);' class='reg idsTemp' id='tab_reg"+random_Num+"'><tbody><tr><td class='header readOnly'></td><td title='reg name' class='name'>reg_name</td><td title='offset' class='offset' colspan='2'></td><td class='specImage'><img title='16 bits register' alt='Register' src="+imgPath+"></td><td class='address addCell readOnly' ><div class='splitVer setBorder' title='address'><label class='label'>address|</label><label class='addrvalue'></label></div><div class='splitVer' title='Default'><label class='label'>default |</label><label class='defvalue'></label></div> </td> <td class='regwidth hideWidth'>16</td></tr><tr><td colspan='6' title='add properties' class='propclass' contenteditable='true'></td></tr><tr><td colspan='6' title='add description here' class='desc descclass'></td></tr><tr><td colspan='6' class='border'></td></tr><tr><td colspan='6' class='fieldtd'><table class='fields idsTemp' id='tab_reg_field"+random_Num+"'><tr class='label'><td class='ddregbits'>bits</td><td class='lblfieldname'>name</td><td class='lblsw'>s/w</td><td class='lblhw'>h/w</td><td class='lbldefault'>default</td><td class='lbldesc'>description</td></tr><tr onkeyup='setCurrRow(this);' onclick='setCurrRow(this);' class='field edited'><td class='bits'>15:0</td><td class='fieldname' ><br></td><td class='sw'><br></td><td class='hw'></td><td class='default'><br></td><td class='desc fielddesc' onkeydown='insertNewRow(event,this);'></td></tr></table></td></tr></tbody></table></div><br>";
	pasteHtmlAtCaret(query);
}
function insertReg32(imgPath){	
	addIntoHistory();
	random_Num=Math.random();
	query="<br><div><table contenteditable='false' onclick='tabClick(this);' class='reg idsTemp' id='tab_reg"+random_Num+"'><tbody><tr><td class='header readOnly'></td><td title='reg name' class='name'>reg_name</td><td title='offset' class='offset' colspan='2'></td><td class='specImage'><img title='32 bits register' alt='Register' src="+imgPath+"></td><td class='address addCell readOnly' ><div class='splitVer setBorder' title='address'><label class='label'>address|</label><label class='addrvalue'></label></div><div class='splitVer' title='Default'><label class='label'>default |</label><label class='defvalue'></label></div> </td> <td class='regwidth hideWidth'>32</td></tr><tr><td colspan='6' title='add properties' class='propclass' contenteditable='true'></td></tr><tr><td colspan='6' title='add description here' class='desc descclass'></td></tr><tr><td colspan='6' class='border'></td></tr><tr><td colspan='6' class='fieldtd'><table class='fields idsTemp' id='tab_reg_field"+random_Num+"'><tr class='label'><td class='ddregbits'>bits</td><td class='lblfieldname'>name</td><td class='lblsw'>s/w</td><td class='lblhw'>h/w</td><td class='lbldefault'>default</td><td class='lbldesc'>description</td></tr><tr onkeyup='setCurrRow(this);' onclick='setCurrRow(this);' class='field edited'><td class='bits'>31:0</td><td class='fieldname' ><br></td><td class='sw'><br></td><td class='hw'></td><td class='default'><br></td><td class='desc fielddesc' onkeydown='insertNewRow(event,this);'></td></tr></table></td></tr></tbody></table></div><br>";
	pasteHtmlAtCaret(query);
}
function insertReg64(imgPath){	
	addIntoHistory();
	random_Num=Math.random();
	query="<br><div><table contenteditable='false' onclick='tabClick(this);' class='reg idsTemp' id='tab_reg"+random_Num+"'><tbody><tr><td class='header readOnly'></td><td title='reg name' class='name'>reg_name</td><td title='offset' class='offset' colspan='2'></td><td class='specImage'><img title='64 bits register' alt='Register' src="+imgPath+"></td><td class='address addCell readOnly' ><div class='splitVer setBorder' title='address'><label class='label'>address|</label><label class='addrvalue'></label></div><div class='splitVer' title='Default'><label class='label'>default |</label><label class='defvalue'></label></div> </td> <td class='regwidth hideWidth'>64</td></tr><tr><td colspan='6' title='add properties' class='propclass' contenteditable='true'></td></tr><tr><td colspan='6' title='add description here' class='desc descclass'></td></tr><tr><td colspan='6' class='border'></td></tr><tr><td colspan='6' class='fieldtd'><table class='fields idsTemp' id='tab_reg_field"+random_Num+"'><tr class='label'><td class='ddregbits'>bits</td><td class='lblfieldname'>name</td><td class='lblsw'>s/w</td><td class='lblhw'>h/w</td><td class='lbldefault'>default</td><td class='lbldesc'>description</td></tr><tr onkeyup='setCurrRow(this);' onclick='setCurrRow(this);' class='field edited'><td class='bits'>63:0</td><td class='fieldname' ><br></td><td class='sw'><br></td><td class='hw'></td><td class='default'><br></td><td class='desc fielddesc' onkeydown='insertNewRow(event,this);'></td></tr></table></td></tr></tbody></table></div><br>";
	pasteHtmlAtCaret(query);
}
function insertReg128(imgPath){	
	addIntoHistory();
	random_Num=Math.random();
	query="<br><div><table contenteditable='false' onclick='tabClick(this);' class='reg idsTemp' id='tab_reg"+random_Num+"'><tbody><tr><td class='header readOnly'></td><td title='reg name' class='name'>reg_name</td><td title='offset' class='offset' colspan='2'></td><td class='specImage'><img title='128 bits register' alt='Register' src="+imgPath+"></td><td class='address addCell readOnly' ><div class='splitVer setBorder' title='address'><label class='label'>address|</label><label class='addrvalue'></label></div><div class='splitVer' title='Default'><label class='label'>default |</label><label class='defvalue'></label></div> </td> <td class='regwidth hideWidth'>128</td></tr><tr><td colspan='6' title='add properties' class='propclass' contenteditable='true'></td></tr><tr><td colspan='6' title='add description here' class='desc descclass'></td></tr><tr><td colspan='6' class='border'></td></tr><tr><td colspan='6' class='fieldtd'><table class='fields idsTemp' id='tab_reg_field"+random_Num+"'><tr class='label'><td class='ddregbits'>bits</td><td class='lblfieldname'>name</td><td class='lblsw'>s/w</td><td class='lblhw'>h/w</td><td class='lbldefault'>default</td><td class='lbldesc'>description</td></tr><tr onkeyup='setCurrRow(this);' onclick='setCurrRow(this);' class='field edited'><td class='bits'>127:0</td><td class='fieldname' ><br></td><td class='sw'><br></td><td class='hw'></td><td class='default'><br></td><td class='desc fielddesc' onkeydown='insertNewRow(event,this);'></td></tr></table></td></tr></tbody></table></div><br>";
	pasteHtmlAtCaret(query);
}

function insertRef(imgPath){
	addIntoHistory();
	random_Num=Math.random();
	query="<table class='ref idsTemp' contenteditable='false' onclick='tabClick(this)' id='tab_ref"+random_Num+"'><tbody><tr><td class='header readOnly'></td><td title='ref name' class='name' colspan='2'>ref_name</td><td title='offset' class='offset'></td><td class='specImage'><img title='instance' alt='instance' src="+imgPath+"></td><td class='address addCell readOnly'><label class='label'>address|</label><label class='addrvalue'></label></td></tr><tr><td colspan='6' class='refpathtd'> <table class='refpathtab'> <tbody><tr><td class='label lblrefname'>name</td><td class='refname'></td><td class='label path lblrefname' width='50'>path</td><td class='refpath'></td><td class='label lbltype'>type</td><td class='reftype addCell'></td></tr></tbody></table> </td>      </tr> <tr><td colspan='6' title='add properties' class='propclass'></td></tr><tr><td colspan='6' title='add description here' class='descclass'></td></tr></tbody> </table><br>";
	pasteHtmlAtCaret(query);
}

function insertMemory(imgPath){	
	addIntoHistory();
	random_Num=Math.random();
	query="<table class='mem idsTemp' contenteditable='false' onclick='tabClick(this)' id='tab_mem"+random_Num+"'><tbody><tr><td class='header readOnly'></td><td title='memory name' class='name' colspan='2'>memory_name</td><td title='offset' class='offset'></td><td class='specImage' style='width:36px'><img title='memory' alt='memory' src="+imgPath+"></td><td class='address addCell readOnly'><label class='label'>address|</label><label class='addrvalue'></label></td></tr><tr><td class='label'>depth</td><td class='depth' contenteditable='true'></td><td class='label'>width</td><td class='width' contenteditable='true'></td><td class='label'>default</td><td class='default address addCell' contenteditable='true'></td></tr><tr><td colspan='6' title='add properties' class='propclass'></td></tr><tr><td colspan='6' title='add description here' class='desc descclass'></td></tr></tbody> </table><br>";
	pasteHtmlAtCaret(query);
}

function insertEnum(){
	addIntoHistory();
	random_Num=Math.random();
	query="<table class='enum idsTemp' contenteditable='false' onclick='tabClick(this)' id='tab_enum"+random_Num+"'> <tbody><tr><td class='header readOnly'></td><td title='enum name' class='name'>enum_name</td><td class='address addCell readOnly'><label class='label'>address |</label><label></label></td></tr><tr><td class='label'>mnemonic name</td><td class='label'>value</td><td class='label'>description</td></tr><tr onkeyup='setCurrRow(this);' onclick='setCurrRow(this);' class='edited'><td class='m_name'></td><td class='value'></td><td title='add description here' class='desc enumdesc' onkeydown='insertEnumRow(event,this);'></td></td></tr></tbody> </table><br>";
	pasteHtmlAtCaret(query);
}

function insertDefine(){
	addIntoHistory();
	random_Num=Math.random();
	query="<table class='param idsTemp' contenteditable='false' onclick='tabClick(this)' id='tab_def"+random_Num+"'><tbody><tr><td class='label'>define name</td><td class='label'>value</td><td class='label'>description</td><td class='label'>private</td></tr><tr onkeyup='setCurrRow(this);' onclick='setCurrRow(this);' class='edited'><td class='name'></td><td class='value'></td><td title='add description here' class='desc'></td><td class='private' onkeydown='insertDefineRow(event,this);'></td></td></tr></tbody> </table><br>";
	pasteHtmlAtCaret(query);
}

function insertVariant(){	
	addIntoHistory();
	random_Num=Math.random();
	query="<table class='variant idsTemp' contenteditable='false' onclick='tabClick(this)' id='tab_var"+random_Num+"'> <tbody><tr><td class='label'>variant name</td><td class='label'>value</td><td class='label'>description</td></tr><tr onkeyup='setCurrRow(this);' onclick='setCurrRow(this);' class='edited'><td class='name'></td><td class='value'></td><td title='add description here' class='desc' onkeydown='insertVariantRow(event,this);'></td></td></tr></tbody> </table><br>";
	pasteHtmlAtCaret(query);
}

function insertBusDomain(){	
	addIntoHistory();
	random_Num=Math.random();
	query="<table class='busdomain idsTemp' contenteditable='false' onclick='tabClick(this)' id='tab_bus"+random_Num+"'><tbody><tr><td class='label'>bus domain name</td><td class='label'>address unit</td><td class='label'>description</td><td class='label'>bus</td></tr><tr onkeyup='setCurrRow(this);' onclick='setCurrRow(this);' class='edited'><td class='name'></td><td class='unit'></td><td title='add description here' class='desc'></td><td class='bus' onkeydown='insertBusRow(event,this);'></td></td></tr></tbody> </table><br>";
	pasteHtmlAtCaret(query);
}

function insertSignals(imgPath){
	addIntoHistory();
	random_Num=Math.random();
	query="<table class='signals idsTemp' contenteditable='false' onclick='tabClick(this)' id='tab_bus"+random_Num+"'><tbody><tr><td class='header readOnly'></td><td title='signal name' class='name'>signal_name</td><td class='specImage'><img title='signals' alt='signals' src="+imgPath+"></td><td class='addCell readOnly'></td></tr><tr><td colspan='4' class='border'></td></tr><tr ><td class='label'>name</td><td class='label'>port type</td><td colspan='2' class='label'>description</td></tr><tr onclick='setCurrRow(this);' onkeydown='setCurrRow(this);' class='edited'><td class='name'></td><td class='direction'></td><td colspan='2' title='add description here' class='desc signaldesc' onkeydown='insertNewRowSignals(event,this);'></td></td></tbody> </table><br>";
	pasteHtmlAtCaret(query);
}

function reloadPage(){
	var v=location.reload();
}

function pasteHtmlAtCaret( html ) {
	addIntoHistory();
	var sel, range;
	if ( window.getSelection ) {
		sel = window.getSelection();
		if ( sel.getRangeAt && sel.rangeCount ) {
			range = sel.getRangeAt( 0 );
			range.deleteContents();


			var el = document.createElement( "div" );
			el.innerHTML = html;
			var frag = document.createDocumentFragment(),
				node, lastNode;
			while ( ( node = el.firstChild ) ) {
				lastNode = frag.appendChild( node );
			}
			range.insertNode( frag );

			if ( lastNode ) {
				range = range.cloneRange();
				range.setStartAfter( lastNode );
				range.collapse( true );
				sel.removeAllRanges();
				sel.addRange( range );
			}
		}
	} else if ( document.selection && document.selection.type != "Control" ) {
		document.selection.createRange().pasteHTML( html );
	}
}

function insertBusRow(evt, obj){
	addIntoHistory();
	/* tab press*/
	if(evt.keyCode===9){

		while(obj.tagName.toUpperCase() !== "TABLE") {
			obj = obj.parentNode;
		}



		var table = document.getElementById(obj.id);
		var row = table.insertRow(parseInt(table.getElementsByTagName("tr").length));
		row.setAttribute('onkeyup','setCurrRow(this)');
		row.setAttribute('onclick',"setCurrRow(this)");
		row.setAttribute('class','edited');

		var cell;

		cell=row.insertCell(0);
		cell.setAttribute('class', "name");  


		cell=row.insertCell(1);
		cell.setAttribute('class', "unit"); 



		cell=row.insertCell(2);
		cell.setAttribute('class', "desc"); 


		cell=row.insertCell(3);
		cell.setAttribute('class', "bus"); 
		cell.setAttribute('onkeydown',"insertBusRow(event,this);");


	}
}

function insertVariantRow(evt, obj){
	addIntoHistory();
	/* tab press*/
	if(evt.keyCode===9){

		while(obj.tagName.toUpperCase() !== "TABLE") {
			obj = obj.parentNode;
		}



		var table = document.getElementById(obj.id);
		var row = table.insertRow(parseInt(table.getElementsByTagName("tr").length));
		row.setAttribute('onkeyup','setCurrRow(this)');
		row.setAttribute('onclick',"setCurrRow(this)");
		row.setAttribute('class','edited');
		var cell;

		cell=row.insertCell(0);
		cell.setAttribute('class', "name"); 


		cell=row.insertCell(1);
		cell.setAttribute('class', "value"); 


		cell=row.insertCell(2);
		cell.setAttribute('class', "desc"); 
		cell.setAttribute('onkeydown',"insertEnumRow(event,this);");


	}
}

function insertDefineRow(evt, obj){
	addIntoHistory();
	/* tab press*/
	if(evt.keyCode===9){

		while(obj.tagName.toUpperCase() !== "TABLE") {
			obj = obj.parentNode;
		}



		var table = document.getElementById(obj.id);
		var row = table.insertRow(parseInt(table.getElementsByTagName("tr").length));
		row.setAttribute('onkeyup','setCurrRow(this)');
		row.setAttribute('onclick',"setCurrRow(this)");
		row.setAttribute('class','edited');
		var cell;

		cell=row.insertCell(0);
		cell.setAttribute('class', "name");

		cell=row.insertCell(1);
		cell.setAttribute('class', "value"); 

		cell=row.insertCell(2);
		cell.setAttribute('class', "desc"); 


		cell=row.insertCell(3);
		cell.setAttribute('class', "private"); 
		cell.setAttribute('onkeydown',"insertDefineRow(event,this);");

	}
}

function insertEnumRow(evt,obj){
	addIntoHistory();
	/* tab press*/
	if(evt.keyCode===9){

		while(obj.tagName.toUpperCase() !== "TABLE") {
			obj = obj.parentNode;
		}

		var table = document.getElementById(obj.id);
		var row = table.insertRow(parseInt(table.getElementsByTagName("tr").length));
		row.setAttribute('onkeyup','setCurrRow(this)');
		row.setAttribute('onclick',"setCurrRow(this)");
		row.setAttribute('class','edited');	
		var cell;

		cell=row.insertCell(0);
		cell.setAttribute('class', "name");  

		cell=row.insertCell(1);
		cell.setAttribute('class', "value"); 

		cell=row.insertCell(2);
		cell.setAttribute('class', "desc enumdesc"); 
		cell.setAttribute('onkeydown',"insertEnumRow(event,this);");

	}	
}

function insertNewRowSignals(evt,obj){
	addIntoHistory();
	/* tab press*/
	if(evt.keyCode===9){

		while(obj.tagName.toUpperCase() !== "TABLE") {
			obj = obj.parentNode;
		}



		var table = document.getElementById(obj.id);
		var row = table.insertRow(parseInt(curr_row)+1);
		row.setAttribute('onkeydown','setCurrRow(this)');
		row.setAttribute('onclick',"setCurrRow(this)");
		row.setAttribute('class',"edited");

		var input;
		var cell;

		cell=row.insertCell(0);
		cell.setAttribute('class', "name"); 
		/*input=document.createElement("input");      

		cell.appendChild(input);
		*/

		cell=row.insertCell(1);
		cell.setAttribute('class', "direction");
		/*input=document.createElement("input");
		cell.appendChild(input);
		*/


		cell=row.insertCell(2);
		cell.setAttribute('onkeydown',"insertNewRowSignals(event,this);");
		cell.setAttribute('colspan','2');
		cell.setAttribute('class', "desc signaldesc"); 
		/*input=document.createElement("input");		
		cell.appendChild(input);*/
	}
	/*add property row*/
	else if(evt.keyCode==80&&evt.altKey){
		while(obj.tagName.toUpperCase() !== "TABLE") {
			obj = obj.parentNode;
		}

		var table = document.getElementById(obj.id);
		var row = table.insertRow(parseInt(curr_row)+1);
		/*var row = table.insertRow(parseInt(curr_row_signal)+1);*/
		row.setAttribute('onkeydown','setCurrRow(this)');
		row.setAttribute('onclick',"setCurrRow(this)");
		var input;
		var cell;

		cell=row.insertCell(0);

		cell.setAttribute('colspan', '4');    
		cell.setAttribute('onkeydown',"insertNewRowSignals(event,this);");
		cell.setAttribute('class','propclass');		
	}


}

/*  insert new row for reg field */
function insertNewRow(evt, obj) {
	addIntoHistory();
	console.log('insert new row call');
	if(evt.keyCode===9){

		while(obj.tagName.toUpperCase() !== "TABLE") {
			obj = obj.parentNode;
		}


		console.log('-curr_row='+curr_row);
		var table = document.getElementById(obj.id);
		var row = table.insertRow(parseInt(curr_row)+1);
		row.setAttribute('onkeydown','setCurrRow(this)');
		row.setAttribute('onclick',"setCurrRow(this)");
		row.setAttribute('class','field edited');	

		var input;
		var cell;

		cell=row.insertCell(0);		
		cell.setAttribute('class', 'bits');     
		/*				input=document.createElement("input");
				input.setAttribute('style','width:88%');*/
		/*
				var rowdel=document.createElement("a");
				rowdel.innerText='x';
				rowdel.setAttribute('title','delete this row');
				rowdel.setAttribute('style','color:red');
				rowdel.setAttribute('onclick','deleteRow(this);');
				cell.appendChild(rowdel);
				*/


		cell=row.insertCell(1);
		cell.setAttribute('class', 'fieldname');  
		/*input=document.createElement("input");
				cell.appendChild(input);*/


		cell=row.insertCell(2);
		cell.setAttribute('class','sw thirdCell');
		/*input=document.createElement("input");
				cell.appendChild(input);*/


		cell=row.insertCell(3);
		cell.setAttribute('class','hw thirdCell');
		/*input=document.createElement("input");
				cell.appendChild(input);*/


		cell=row.insertCell(4);
		cell.setAttribute('class','default');

		cell=row.insertCell(5);
		cell.setAttribute('onkeydown',"insertNewRow(event,this);");
		cell.setAttribute('class','desc fielddesc');
		/*input=document.createElement("input");
				cell.appendChild(input);
				*/

	}
	/*add property row*/
	else if(evt.keyCode==80&&evt.altKey){
		while(obj.tagName.toUpperCase() !== "TABLE") {
			obj = obj.parentNode;
		}

		var table = document.getElementById(obj.id);
		var row = table.insertRow(parseInt(curr_row)+1);
		row.setAttribute('onkeydown','setCurrRow(this)');
		row.setAttribute('onclick',"setCurrRow(this)");
		row.setAttribute('class','edited');
		var cell;

		cell=row.insertCell(0);		
		cell.setAttribute('colspan', '6');    
		cell.setAttribute('onkeydown',"insertNewRow(event,this);");
		cell.setAttribute('class','propclass');
		/*
				input=document.createElement("textarea");  
				input.setAttribute('class','propInput');
				cell.appendChild(input);*/

	}

	/*add description row*/
	else if(evt.keyCode==68&&evt.altKey){
		while(obj.tagName.toUpperCase() !== "TABLE") {
			obj = obj.parentNode;
		}


		var table = document.getElementById(obj.id);
		var row = table.insertRow(parseInt(curr_row)+1);
		row.setAttribute('onkeydown','setCurrRow(this)');
		row.setAttribute('onclick',"setCurrRow(this)");
		row.setAttribute('class','edited');
		var cell;

		cell=row.insertCell(0);
		cell.setAttribute('colspan', '6');    
		cell.setAttribute('onkeydown',"insertNewRow(event,this);");
		cell.setAttribute('class','desc descclass');
		/*
				input=document.createElement("textarea");  
				input.setAttribute('class','descInput');
				cell.appendChild(input);*/

	}

}

function setCaretToPos (input, pos) {
	setSelectionRange(input, pos, pos);
}

function setSelectionRange(input, selectionStart, selectionEnd) {
	if (input.setSelectionRange) {
		input.focus();
		input.setSelectionRange(selectionStart, selectionEnd);
	}
	else if (input.createTextRange) {
		var range = input.createTextRange();
		range.collapse(true);
		range.moveEnd('character', selectionEnd);
		range.moveStart('character', selectionStart);
		range.select();
	}
}


function click_hideProp(){
	var cols =     document.getElementsByClassName('propclass');
	var txtVal=document.getElementById('btnPropHide').innerHTML;
	if(txtVal==hideProp){
		for(i=0; i<cols.length; i++) {
			cols[i].style.display =    'none';
		}
		document.getElementById('btnPropHide').innerHTML=showProp;
	}
	else if(txtVal==showProp){
		for(i=0; i<cols.length; i++) {
			cols[i].style.display =    'table-cell';
		}
		document.getElementById('btnPropHide').innerHTML=hideProp;
	}
}

var display="table-cell";
function hideprop(){
	display="none";
}
function showprop(){
	display="table-cell";
}
function resetview(){
	$('.propclass').css("display","table-cell");	
}

function click_btnprop(){
	if($("#btnprop").html()==hideProp){
		$('.propclass').css("display","none");
		$("#btnprop").html(showProp);
	}
	else{
		$('.propclass').css("display","table-cell");
		$("#btnprop").html(hideProp);
	}
}

function click_btndesc(){
	if($("#btndesc").html()==hideDesc){
		$('.descclass').css("display","none");
		$("#btndesc").html(showDesc);
	}
	else{
		$('.descclass').css("display","table-cell");
		$("#btndesc").html(hideDesc);
	}
}

function IDS_PROPHIDE(){
	$('.propclass').css("display","none");
	hideprop();
}

function IDS_PROPSHOW(){
	$('.propclass').css("display","table-cell");
	showprop();
}

function IDS_DESCSHOW(){
	try{		
		var cols =     document.getElementsByClassName('descclass');
		for(i=0; i<cols.length; i++) {
			cols[i].style.display =    'table-cell';
		}
	}
	catch(E){
		console.log('Err '+E.message);
	}
}

function IDS_DESCHIDE(){
	try{
		var cols =     document.getElementsByClassName('descclass');	
		for(i=0; i<cols.length; i++) {
			cols[i].style.display =    'none';
		}
	}
	catch(E){
		console.log("Err hideDesc "+E.message);
	}
}





function click_hideDesc(){
	var cols =     document.getElementsByClassName('descclass');
	var txtVal=document.getElementById('btnDescHide').innerHTML;
	if(txtVal==hideDesc){
		for(i=0; i<cols.length; i++) {
			cols[i].style.display =    'none';
		}
		document.getElementById('btnDescHide').innerHTML=showDesc;
	}
	else if(txtVal==showDesc){
		for(i=0; i<cols.length; i++) {
			cols[i].style.display =    'table-cell';
		}
		document.getElementById('btnDescHide').innerHTML=hideDesc;
	}
}

function printMsg(msg){
	/*document.getElementById('para').innerHTML =msg;*/
}

function fontColorChange(color){
	console.log('change color');
	document.execCommand('foreColor', false, color);
}
function printDoc(){
	window.print();

}

/*key press event listner to catch and handle key event*/
var bodyArr={};
var index=0;
function KeyPress(e) {

	var evtobj = window.event? event : e;

	/*undo*/
	if (evtobj.keyCode === 90 && evtobj.ctrlKey) {				
		if(typeof(bodyArr[index-1])!=='undefined'&&bodyArr[index-1]!==null){
			index--;
			document.body.innerHTML=bodyArr[index];
		}		
		return false;
	}
	/*redo*/
	else  if (evtobj.keyCode === 89 && evtobj.ctrlKey) {
		if(typeof(bodyArr[index+1])!=='undefined'&&bodyArr[index+1]!==null){
			index++;
			document.body.innerHTML=bodyArr[index];
		}
		return false;
	}

	/*paste ctrl+v*/
	else  if (evtobj.keyCode === 86 && evtobj.ctrlKey) {
		console.log("-tab clicked");
		myClick();
		function myClick() {
			setTimeout(
				function() {
					updateEvents();
				}, 500);
		}

	}
	else{
		addIntoHistory();
	}

	/*when copy/cut paste done, html only copies html tag but not event like "onclick.." so we need to update these event whenever copy occures. 
	this is only a temporary solution and we need to find a better solution for this*/
	function updateEvents(){
		console.log('update event call');

		var idsclass = document.getElementsByClassName("idsTemp");
		if(typeof(idsclass!=='undefined'&&idsclass!==null)){
			for(var i = 0; i < idsclass.length; i++)
			{
				idsclass[i].setAttribute("onclick","tabClick(this)");
				random_Num=Math.random();
				idsclass[i].setAttribute('id','tab'+i+random_Num);
			}
		}


		idsclass=document.getElementsByClassName("edited");
		if(typeof(idsclass!=='undefined'&&idsclass!==null)){
			for(var i = 0; i < idsclass.length; i++)
			{
				idsclass[i].setAttribute("onclick","setCurrRow(this)");
				idsclass[i].setAttribute("onkeyup","setCurrRow(this)");
			}
		}

		idsclass=document.getElementsByClassName("fielddesc");
		if(typeof(idsclass!=='undefined'&&idsclass!==null)){
			for(var i = 0; i < idsclass.length; i++)
			{
				idsclass[i].setAttribute("onkeydown","insertNewRow(event,this)");
			}
		}

		var fields= document.getElementsByClassName("fields");
		if(typeof(fields!=='undefined'&&fields!==null)){
			for(var i = 0; i < fields.length; i++){
				random_Num=Math.random();
				fields[i].setAttribute('id','tab'+i+random_Num);
			}
		}

		idsclass= document.getElementsByClassName("private");
		if(typeof(idsclass!=='undefined'&&idsclass!==null)){
			for(var i = 0; i < idsclass.length; i++){
				idsclass[i].setAttribute("onkeydown","insertDefineRow(event,this)");
			}
		}

		idsclass= document.getElementsByClassName("enumdesc");
		if(typeof(idsclass!=='undefined'&&idsclass!==null)){
			for(var i = 0; i < idsclass.length; i++){
				idsclass[i].setAttribute("onkeydown","insertEnumRow(event,this)");
			}
		}

		idsclass= document.getElementsByClassName("signaldesc");
		if(typeof(idsclass!=='undefined'&&idsclass!==null)){
			for(var i = 0; i < idsclass.length; i++){
				idsclass[i].setAttribute("onkeydown","insertNewRowSignals(event,this)");
			}	
		}
	}

}

function testMM(){
	console.log('call testMM');
}

function insertTabSpace(){
	document.execCommand('insertHTML', false, "&emsp;");	
}

function addIntoHistory(){
	bodyArr[index]=document.body.innerHTML;
	index++;
}

function showSearchTab(){
	$('#divSearch').css('display','block');
}
function hideSearchTab(){
	$('#divSearch').css('display','none');
}

function click_findString(){
	var str=$('#inputsearch').val();
	findString(str);
}

function click_replaceString(){
	findReplaceString(document.getElementById('inputsearch'),document.getElementById('inputreplace'));	
}

function walk(el, fn) {
	for (var i = 0, len = el.childNodes.length; i < len; i++) {
		var node = el.childNodes[i];
		if (node.nodeType === 3)
			fn(node);
		else if (node.nodeType === 1 && node.nodeName !== "SCRIPT")
			walk(node, fn);
	}
}
function textNode(txt) {
	return document.createTextNode(txt);
}

function openFindWindow(event){
	try{
		location.reload();
		clickController.openFind();
	}
	catch(e){
		alert('err : '+e.message);
	}
}

function unSetUnsaved(){
	try{

		clickController.setUnsaveSymbol();
	}
	catch(e){
		alert('err unSetUnsaved : '+e.message);
	}
}

function unSetsaved(){
	try{
		clickController.setSaveSymbol();
	}
	catch(e){
		alert('err unSetUnsaved : '+e.message);
	}
}

function test_replace(){
	var val=document.getElementById('testDiv').innerText.replace("input","HELLO");
	document.getElementById('testDiv').innerText=val;
}

document.onkeydown = KeyPress;



/*@JAVAFX*/
function jump(h){
	location.href ='#'+h;                 
}

/*GUI check starts here*/
/*@JAVAFX*/
function runGUICheck(){
	var errorlist="";
	var errorconter=0;
	resetAllChecks();

	checkRegTemplate();
	checkCommon();
	checkMem();
	

	if($.trim(errorlist)!=""){
		var verrorlist="{error: ["+errorlist+"]}";
		alert(verrorlist);
		return true;
	}

	function addErrJson(msg,td){

		var id="target"+errorconter;
		$(td).append("<a class='idscheck' name='"+id+"'></a>");	
		$(td).css('border','2px solid red');

		if(errorlist==""){
			errorlist="{id:"+id+",msg:"+msg+"}";
		}
		else{		
			errorlist=errorlist+",{id:"+id+",msg:"+msg+"}";
		}
		errorconter++;
	}

	function checkRegTemplate(){


		$("table.reg td.fieldname").each(function(i){
			if($(this).text()==""){
				addErrJson("Error-G  field name should not be empty",this);
			}
		});

		$("table.reg td.bits").each(function(i){
			/*
			if($(this).text()==""){
				addErrJson("Error-G register bits should not by empty",this);
			}
			*/
			if(!$(this).text().match(/\s*(^[\d]+$)|(^[\d]+\:[\d])/)){
				addErrJson("Error-G invalid register bits value",this);
			}			
		});

		$("table.reg td.sw").each(function(i){
			if($(this).text()==""){
				addErrJson("Error-G register sw access should not by empty",this);
			}
		});

	}	

	function checkCommon(){
		$("table.idsTemp td.offset").each(function(i){
			/*if(($(this).text()!="")&&(!$(this).text().match(/\s*(^\`h[a-fA-F0-9]+$)|(^[\$][a-zA-Z][a-zA-Z0-9_]*)|(^[\d]+$)|(^0(x|X)([\d]|[a-fA-F])+$)/))){*/
			if(($(this).text()!="")&&(!$(this).text().match(/\s*(^\`h[a-fA-F0-9]+$)|(^[\$][a-zA-Z][a-zA-Z0-9_]*)|(^[\d_]+$)|(^0(x|X)([\d_]|[a-fA-F_])+$)/))){
				addErrJson("Error-G Invalid offset value",this);
			}
		});

		$("table.idsTemp td.default").each(function(i){
			if($(this).text()==""){			
				addErrJson("Error-G  default value should not be empty",this);
			}
		});

		$("table.idsTemp td.name").each(function(i){
			if(!$(this).text().match(/^[\\$]?[a-zA-Z][a-zA-Z0-9 _]*/)){
				addErrJson("Error-G template name should be valid",this);
			}
		});
		
		$("table.ref td.refname").each(function(i){
			if(!$(this).text().match(/^[\\$]?[a-zA-Z][a-zA-Z0-9 _]*/)){
				addErrJson("Error-G ref instance name should be valid",this);
			}
		});
	}

	function checkMem(){
		$("table.mem td.depth").each(function(i){
			if($(this).text()==""){			
				addErrJson("Error-G  depth value should not be empty",this);
			}
		});

		$("table.mem td.width").each(function(i){
			if($(this).text()==""){			
				addErrJson("Error-G  width value should not be empty",this);
			}
		});
	}

	function resetAllChecks(){

		$("table.idsTemp td.default").removeAttr("style");
		$("table.reg td.fieldname").removeAttr("style");
		$("table.reg td.bits").removeAttr("style");
		$("table.reg td.sw").removeAttr("style");
		$("table.idsTemp td.offset").removeAttr("style");

		$("table.mem td.depth").removeAttr("style");
		$("table.mem td.width").removeAttr("style");
		$("table.idsTemp td.name").removeAttr("style");
		$("table.idsTemp td.name").removeAttr("style");
		$("table.ref td.refname").removeAttr("style");
		$(".idscheck").removeAttr();
		errorconter=0;
		errorlist="";
	}

	return false;
}
/*GUI checks ends here*/

				function hideregview(){
					document.getElementById("paramcontainer").style.display="block";
					document.getElementById("regdivcontainer").style.display="none";		   
				}

				function updateParamView(str){
					document.getElementById("regdivcontainer").innerHTML=str;
				}

				function regviewupdate(str){
					document.getElementById("regdivcontainer").innerHTML=str;
					document.getElementById("paramcontainer").style.display="none";
					document.getElementById("regdivcontainer").style.display="block";	
				}

				var menuDisplayed = false;
				var menuBox = null;



				function clearall(){
					document.getElementById("paramcontainer").innerHTML="";
					document.getElementById("regdivcontainer").innerHTML="";

				}

				var bodyArr={};
				var index=0;
				function addIntoHistory(){
					bodyArr[index]=document.body.innerHTML;
					index++;
				}

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
				}

				document.onkeydown = KeyPress;

				function writeparam(str){
					document.getElementById("paramcontainer").innerHTML=str;
					document.getElementById("paramcontainer").style.display="block";
					addIntoHistory();
				}

				/* Events fired on the drag target */

				document.addEventListener("dragstart", function(event) {
					/* The dataTransfer.setData() method sets the data type and the value of the dragged data*/
					event.dataTransfer.setData("Text", event.target.id);
					/* Output some text when starting to drag the p element
					document.getElementById("demo").innerHTML = "Started to drag the p element.";*/

					/* Change the opacity of the draggable element*/
					event.target.style.opacity = "0.4";

					/*event.target.style.backgroundColor="transparent";*/
					/*console.log("drag target="+event.target.id);*/
				});

				/* While dragging the p element, change the color of the output text
				this event fire when drag starts.
				event.target have source element object*/
				var regsource;
				document.addEventListener("drag", function(event) {

					/*document.getElementById("demo").style.color = "red";*/
					event.target.parentElement.style.backgroundColor ="white";
					regsource=event;
					/*console.log("source index="+regsource.target.parentElement.cellIndex);*/
				});

				/* Output some text when finished dragging the p element and reset the opacity*/
				document.addEventListener("dragend", function(event) {
					/*document.getElementById("demo").innerHTML = "Finished dragging the p element.";*/
					event.target.style.opacity = "1";
				});


				/* Events fired on the drop target */

				/* When the draggable p element enters the droptarget, change the DIVS's border style*/
				document.addEventListener("dragenter", function(event) {
					if ( event.target.className == "droptarget" ) {
						event.target.style.border = "2px solid red";					
					}
				});

				/* By default, data/elements cannot be dropped in other elements. To allow a drop, we must prevent the default handling of the element*/
				document.addEventListener("dragover", function(event) {
					event.preventDefault();
				});

				/* When the draggable p element leaves the droptarget, reset the DIVS's border style*/
				document.addEventListener("dragleave", function(event) {
					if ( event.target.className == "droptarget" ) {
						event.target.style.border = "";
					}
				});

				/* On drop - Prevent the browser default handling of the data (default is open as link on drop)
   Reset the color of the output text and DIV's border color
   Get the dragged data with the dataTransfer.getData() method
   The dragged data is the id of the dragged element ("drag1")
   Append the dragged element into the drop element
*/
				document.addEventListener("drop", function(event) {
					event.preventDefault();
					if ( event.target.className == "droptarget" ) {

						try{

							var vali=validateCell(event);
							if(vali){
								console.log("Warning : Space is full");
								event.target.style.border = "";
								regsource.target.style.backgroundColor ="#d4e0e2";
							}
							else{
								/*document.getElementById("demo").style.color = "";*/
								event.target.style.border = "";
								var data = event.dataTransfer.getData("Text");
								event.target.appendChild(document.getElementById(data));
								event.target.style.backgroundColor ="#d4e0e2";



								var cellindex=event.target.cellIndex;							
								var data2 = parseInt(regsource.target.innerHTML);
								var range=data2+cellindex;
								event.target.closest("tr").cells[cellindex].colSpan=data2;
								var temp;
								var reversecell=0;
								for(i=cellindex+1;i<range;i++){
									temp=event.target.closest("tr").cells[cellindex+1];


									if((typeof(temp) === "undefined" || temp === null)){
										reversecell++;
										temp=event.target.closest("tr").cells[cellindex-reversecell].innerHTML.trim();
										if(temp==''){
											event.target.closest("tr").cells[cellindex-reversecell].remove();
										}
									}
									else{
										temp=temp.innerHTML.trim();

										if(temp===""){
											console.log("--delEle empty="+temp);

											event.target.closest("tr").cells[cellindex+1].remove();
										}
										else{
											reversecell++;


											console.log("--delEle=="+temp);
											temp=event.target.closest("tr").cells[cellindex-reversecell+1].getElementsByTagName("p")[0];
											if((typeof(temp) !== "undefined" || temp !== null)){
												var iiid=temp.id;
												console.log("--del id="+iiid+"--reverseID="+reversecell);
												if(iiid===regsource.target.id){
													console.log("--id is same");
													var j;
													var delindex=cellindex-reversecell-1;
	
													var counter=0;
													for(j=i;j<range;j++){
														event.target.closest("tr").cells[delindex-counter].remove();
														counter++;
													}
													break;
												}
											}



											/*
										temp=event.target.closest("tr").cells[cellindex-reversecell].innerHTML.trim();
										console.log("--reverseTtemp="+temp+"--index="+(cellindex-reversecell));
										console.log("--reverseTtemp="+temp+"--index="+event.target.closest("tr").cells[cellindex-reversecell+1].innerHTML.trim());
										console.log("--reverseTtemp="+temp+"--index="+event.target.closest("tr").cells[cellindex-reversecell-2].innerHTML.trim());
*/
											if(temp==''){
												event.target.closest("tr").cells[cellindex-reversecell].remove();
											}
										}
									}







									/*

								if((typeof(temp) === "undefined" || temp === null)||((typeof(temp) === "undefined" || temp === null)&&
																					 temp==='')){
									event.target.closest("tr").cells[cellindex+1].remove();cell index remain same after deletion, hence it is fixed index deletion
								}
								else if(temp!==''){
									reversecell++;
									temp=event.target.closest("tr").cells[cellindex-reversecell].innerHTML.trim();
									if(temp==''){
										event.target.closest("tr").cells[cellindex-reversecell].remove();
									}
								}
								*/
								}
							}
						}
						catch(ex){
							console.log("Error dop event : "+ex.message);
						}
						addIntoHistory();

					}
					else{
						regsource.target.style.backgroundColor ="#d4e0e2";
					}
				});

				function validateCell(src){
					var cellindex=src.target.cellIndex;
					var data = parseInt(regsource.target.innerHTML);
					var range=data+cellindex;


					console.log("--cellindex="+cellindex);
					console.log("--data="+data);				
					console.log("--range="+range);

					var i;
					var innerele;
					var isEleFound=false;
					var srcId=regsource.target.id;
					var descId;
					var para=null;
					var currcell=null;
					var iteratedcell=0;
					var iscellavail=false;
					var iteratebackword=false;
					/*find if cell is availble or not */
					for(i=cellindex;i<range;i++){
						if(!iteratebackword){
							currcell=src.target.closest("tr").cells[i];
							console.log("--i="+i+"--currcell="+currcell);
							if((typeof(currcell) === "undefined" || currcell === null)){
								console.log("cell not exist");
								iscellavail=false;
							}
							else{


								if(currcell.innerHTML.trim()!=""){
									descId=currcell.getElementsByTagName("p")[0];
									console.log("--1.1descID===="+descId);
									if((typeof(descId) !== "undefined" && descId !== null)){
										if(descId.id===srcId){
											console.log("1.1cell avail");
											iscellavail=true;
											break;
										}
										else{
											console.log("2cell not avail");
											iscellavail=false;
											iteratebackword=true;								
										}
									}
									else{
										console.log("cell not avail");
										iscellavail=false;
										iteratebackword=true;								
									}
								}
								else{
									console.log("2.1cell avail");	
									iscellavail=true;
								}
							}

						}

						if(!iscellavail||iteratebackword){
							console.log("---------------------start--------------------------");
							console.log("-----------------------------------------------");
							console.log("-----------------------------------------------");

							iteratedcell++;
							var previndex=cellindex-iteratedcell;
							/*now look prev cell for availbility*/
							console.log("--index="+previndex);
							currcell=src.target.closest("tr").cells[previndex];
							console.log("--i="+i+"--currcell="+currcell);
							if((typeof(currcell) === "undefined" || currcell === null)){
								console.log("cell not exist");
								iscellavail=false;
								break;
							}
							else{


								if(currcell.innerHTML.trim()!=""){
									descId=currcell.getElementsByTagName("p")[0];
									console.log("--descID===="+descId);
									if((typeof(descId) !== "undefined" || descId !== null)){
										if(descId.id===srcId){
											console.log("1cell avail");
											iscellavail=true;
											break;
										}
										else{
											console.log("2cell not avail");
											iscellavail=false;
											break;
										}
									}
									else{
										console.log("cell not avail");
										iscellavail=false;
										break;
									}
								}
								else{
									console.log("2cell avail");	
									iscellavail=true;
								}
							}

							console.log("---------------------end--------------------------");
							console.log("-----------------------------------------------");
							console.log("-----------------------------------------------");


						}






						/*

					if((typeof(currcell) === "undefined" || currcell === null)||((typeof(currcell) !== "undefined" ||
																				  currcell !== null)&&(currcell.innerHTML.trim()!=="")||(currcell.getElementsByTagName("p")[0].id!==srcId))){
						iteratedcell++;
						var previndex=cellindex-iteratedcell;
						console.log("--previndex="+previndex);
						currcell=src.target.closest("tr").cells[previndex];																		
					}


					if((typeof(currcell) === "undefined" || currcell === null)||((typeof(currcell) !== "undefined" ||
																				  currcell !== null)&&(currcell.innerHTML.trim()!=="")||(currcell.getElementsByTagName("p")[0].id!==srcId))){

						isEleFound=true;
					}

*/

						/*
					(typeof(currcell) === "undefined" || currcell === null){
					else{
						iteratedcell++;
						var previndex=cellindex-iteratedcell;
						console.log("--previndex="+previndex);
						currcell=src.target.closest("tr").cells[previndex];						
					}

					if(typeof(currcell) !== "undefined" && currcell !== null){
						innerele=currcell.innerHTML.trim();
					}
					console.log("innerele==="+innerele);
*/




						/*
					try{
						console.log("currcell="+currcell.getElementsByTagName("p")[0]);
						descId=currcell.getElementsByTagName("p")[0].id;

						console.log("descId="+descId);

					}
					catch(ex){}
					console.log("-descID="+descId);
					if(innerele!==''&&descId!==srcId){
						isEleFound=true;
					}
					*/

					}

					if(iscellavail){
						isEleFound=false;
					}
					else{
						isEleFound=true;
					}
					console.log("--isEleFound="+isEleFound);

					/*if cell is availble to insert, then organise new and old cell*/
					if(!isEleFound){

						if(data>1){
							/*in target, merge cell if needed*/	
							try{




								/*re arrange cell after vacant space*/
								var oldcellindex=regsource.target.parentElement.cellIndex;


								console.log("--oldcellindex="+oldcellindex);
								console.log("--data="+data);

								for(i=oldcellindex+1;i<(data+oldcellindex);i++){
									var cell=regsource.target.closest("tr").insertCell(i);
									cell.setAttribute('class','droptarget');							
								}

								regsource.target.parentElement.colSpan=0;


								/*
							src.target.closest("tr").cells[cellindex].colSpan=data;
							for(i=cellindex+1;i<range;i++){
								console.log("--deleted cell="+i);
								src.target.closest("tr").cells[cellindex+1].remove();
							}*/
							}
							catch(ex){
								console.log("Error (validateCell) : "+ex.message);
							}

						}

					}



					return isEleFound;
				}
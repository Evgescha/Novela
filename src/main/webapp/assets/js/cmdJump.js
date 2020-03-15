//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!JUMP
function jump(jmp){
	console.log("Go to label: "+jmp["label"]);
	document.location.href ="/runGame?gameName="+jmp["gameName"]+"&labelName="+jmp["label"];
}
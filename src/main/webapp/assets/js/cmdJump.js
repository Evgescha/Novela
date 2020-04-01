//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!JUMP
function jump(jmp){
	console.log("Go to label: "+jmp["label"]);
	document.location.href ="/runGame?gameId="+jmp["gameId"]+"&labelName="+jmp["label"];
}
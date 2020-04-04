//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!JUMP
function jump(jmp){
	var temp = JSON.stringify([...variables]);
	var temp1=temp.split('","').join('":"').split("],[").join(",").replace("[[","{").replace("]]","}");
	console.log(temp1);
	$(".formPlay .labelName").val(jmp["label"]);
	$(".formPlay .variables").val(temp1);
	$(".formPlay").submit();
	
}
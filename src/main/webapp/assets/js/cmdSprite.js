// !!!!!!!!!!!!РАБОТА С СПРАЙТОМ
// добавляем персонажа на экран
function addChar(chr) {
//	console.log("add char");
	var imageUrl = objectToUrl(chr, "/images/char")+"&gameId="+gameId;
//	console.log("url: "+imageUrl);
	var div = `<img class="sprite ${chr["name"]} ${chr["position"]} ${chr["location"]}"  src="${imageUrl}">`;
	// если персонаж существует, заменить
	if ($("." + chr["name"]).length > 0) {
		$("." + chr["name"]).replaceWith(div);
	}
	// если до этого персонажа не было, просто добавить на экран
	else
		$(".sprites").append(div);
	//setTimeout(() => {  next(); }, 1000);
//	withEffect(chr,chr["name"]. "show");
}

// удаление персонажей с экрана
function hide(hd) {
//	console.log("Char " + hd["name"] + " hide");
//	withEffect(hd,hd["name"], "hide");
//	if ($("." + hd["name"]).length > 0)
	$("." + hd["name"]).remove();
	//setTimeout(() => {  next(); }, 1000);
}

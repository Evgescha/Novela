// !!!!!!!!!!РАБОТА С ФОНОМ
// выводим сцену на экран
function addScene(scn) {
//	console.log("add Scene");
	var url = "url(\"upload/files/"+gameId+"/"+scn["path"]+"/"+scn["name"]+".jpg"+"\")";// + objectToUrl(scn, "/scene/get") + "\")";
//	console.log(url);
	$(".background2").css("background-image", url);
	$(".sprite").remove();
//	withEffect(scn,".background2", "show");
	//setTimeout(() => {  next(); }, 1000);
}
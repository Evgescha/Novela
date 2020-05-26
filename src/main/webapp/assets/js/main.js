//текущая позиция в сценарии
let i = 0;


// кнопка дальше по сценарию
//let dialogCount = 0;
function next() {
	// если не завершили предыдущий диалог
	if (dialogCount > 0) {
		nextDialog(arr[i - 1]);
		return;
	}
	if (i == arr.length)
		return;
	var temp = arr[i];
//	console.log(arr[i]);

	// пропускаем не обработанные комманды
	if (temp == null) {
		while (temp == null) {
			i++;
			temp = arr[i];
		}
	}

	parse(temp);
	i++;
//	if (temp["type"] != "menu" && temp["type"] != "dialog")
//		next();

}

var history123=[];
// !!!!!!!!!!!!!!!!!!!!!!Parse
// в зависимости от комманды вызываем нужный метод-обработчик
function parse(temp) {
	console.log("Command:");
	console.log(temp);
	if (temp["type"] == "char")
		addChar(temp);
	else if (temp["type"] == "dialog"){
		addDialog(temp);
		history123.push(temp);
	}
	else if (temp["type"] == "scene"){
		addScene(temp);setTimeout(next, 3000);}
	else if (temp["type"] == "sound"){
		addSound(temp);
		setTimeout(next, 100);}
	else if (temp["type"] == "window")
		windowww(temp);
	else if (temp["type"] == "hide")
		hide(temp);
	else if (temp["type"] == "variable"){
		addVariable(temp);
		setTimeout(next, 100);}
	else if (temp["type"] == "jump")
		jump(temp);
	else if (temp["type"] == "menu")
		menu(temp);
	else if (temp["type"] == "if")
		ifObject(temp);
}

// !!!!!!!!!!!!!!!!!!!!!!!
// получение пути по entity
//это для получения персонажей
// !!!!позже обновить до передачи параметров не в строке
function objectToUrl(obj, url) {
	var tempSrc = url + "?";
	for ( var i in obj) {
		tempSrc += i + "=" + obj[i] + "&";
	}
	console.log(tempSrc);
	tempSrc = tempSrc.substring(0, tempSrc.length - 1).replace("with=[object%20Object]","");
	return tempSrc;
}
function sleep(milliseconds) {
	console.log("sleep:"+milliseconds);
	  const date = Date.now();
	  let currentDate = null;
	  do {
	    currentDate = Date.now();
	  } while (currentDate - date < milliseconds);
	}
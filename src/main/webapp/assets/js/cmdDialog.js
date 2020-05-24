// !!!!!!!!!!!!!!РАБОТА С ДИАЛОГОМ

// текущие позиции в тексте диалога
let dialogCount = 0;
let dialogCurrent = 0;

// начать вывод диалога на экран
function addDialog(dlg) {
//	console.log("add dialog");	
	for(var indexD=0;indexD<dlg["text"].length;indexD++){
		dlg["text"][indexD]=dlg["text"][indexD].split('{').join('<').split('}').join('>');
	}
	dialogCount = dlg["text"].length;
	dialogCurrent = 0;
	if(dlg["name"].length>0)
		$(".textAuthor").text(names.get(dlg["name"]));
	else
		$(".textAuthor").html(dlg["name"]);
	var text = dlg["text"];
	$(".textContent").html(text[dialogCurrent]);
	dialogCount--;
	dialogCurrent++;
}

// продолжить вывод диалога, если он состоит более чем из одной части
function nextDialog(dlg) {
//	console.log("next dialog");
	var text = dlg["text"];
	$(".textContent").html($(".textContent").html() + text[dialogCurrent]);
	dialogCount--;
	dialogCurrent++;
}
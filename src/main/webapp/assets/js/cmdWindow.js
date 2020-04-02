// !!!!!!!!!!!!!РАБОТА С ДИАЛОГОВЫМ ОКНОМ
// показ и сокрытие окна диалога
function windowww(wnd) {
	if (wnd["show"] == true) {
//		console.log("window show");
		$(".window").removeClass("hide").addClass("show");
		withEffect(wnd,".window","show");
	} else {
//		console.log("window hide");
		$(".textAuthor").text("");
		$(".textContent").text("");
		withEffect(wnd,".window","hide");
//		$(".window").removeClass("show").addClass("hide");
//		sleep(2000);
	}
}
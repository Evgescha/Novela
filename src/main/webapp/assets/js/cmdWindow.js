// !!!!!!!!!!!!!РАБОТА С ДИАЛОГОВЫМ ОКНОМ
// показ и сокрытие окна диалога
function windowww(wnd) {
	if (wnd["show"] == true) {
//		withEffect(wnd,".window","show");
		$(".window").removeClass("hide");
		$(".window").addClass("show");
//		setTimeout(() => {  next(); }, 1000);
	} else {
		$(".textAuthor").text("");
		$(".textContent").text("");
//		withEffect(wnd,".window","hide");
		$(".window").removeClass("show");
		$(".window").addClass("hide");
		 setTimeout(() => {  next(); }, 1000);
//		sleep(2000);
	}
}
// !!!!!!!!!!!!РАБОТА С АУДИО

// счетчики увеличения/уменьшения громкости звука
var intervalUpAmbience, intervalDownAmbience, intervalUpMusic, intervalDownMusic, intervalUpSfx, intervalDownSfx;
function addSound(snd) {
	console.log("add sound");
	console.log(snd);
	if (snd["play"] == true) {
		// var url = objectToUrl(snd, "/sound/get");
		getSound("upload/files/" + gameId + "/" + snd["folder"] + "/"
				+ snd["name"] + ".ogg", snd);
	} else {
		$('.' + snd["folder"]).trigger("stop");
//		let vol = 0;
//		if (snd["fade"] != 0) {
//			vol = 100 / snd["fade"];
//		}
//		clearInterval(intervalDown);
//		intervalDown = setInterval(soundVolumeDown, 500, vol / 2);
	}
}

// получаем музыку
function getSound(urls, snd) {
	$("." + snd["folder"]).attr("src", urls);
	// произведение музыки при заданных условиях
	if (snd["sound_loop"] == true)
		$("." + snd["folder"]).attr("loop",true);
	else
		$("." + snd["folder"]).attr("loop",false);

	let vol = 0;
	$("." + snd["folder"]).prop("volume", 1);
//	if (snd["fade"] != 0) {
//		$("." + snd["folder"]).prop("volume", 0);
//		vol = 100 / snd["fade"];
//	}
	$('.' + snd["folder"]).trigger("play");
//	clearInterval(intervalUp);
//	intervalUp = setInterval(soundVolumeUp, 500, vol / 2);
}

//// увеличиваем громкость музыки
//function soundVolumeUp(vol) {
//	var volume = $(".audio").prop("volume") + vol / 100;
//	if (volume > 1)
//		volume = 1;
//	console.log("Volume sound: " + volume);
//	$(".audio").prop("volume", volume);
//	if (volume > 0.99) {
//		clearInterval(intervalUp);
//
//	}
//}
//
//// уменьшаем громкость и выключаем музыку
//function soundVolumeDown(vol) {
//	var volume = $(".audio").prop("volume") - vol / 100;
//	if (volume < 0)
//		volume = 0;
//	console.log("Volume sound: " + volume);
//	$(".audio").prop("volume", volume);
//	if (volume <= 0.05) {
//		clearInterval(intervalDown);
//		$('audio').trigger("stop");
//	}
//}
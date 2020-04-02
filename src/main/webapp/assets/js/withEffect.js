//создание эффектов
function withEffect(obj, className, isShow){
	if(obj["with"]==null){setTimeout(() => {  next(); }, 1000); return};
	var cls=$(className);
	
// var styles = {
// "opacity":"0",
// "transition": "1s",
// "animation": "show 3s 1",
// "animation-fill-mode": "forwards",};
// sleep(200);
	if(isShow=="hide"){
		$(className).css( {"opacity":"1"});
		
		switch (obj["with"]["effect"]) {
		case "dspr":
			// with dspr - показать плавно, за 0.2 секунды
			$(className).addClass("dsprHide");
			 setTimeout(() => {  next(); }, 200);
		    break;
		case "dissolve":
			// with dissolve - показать плавно, за 1 сек
			$(className).addClass("dissolveHide");
			 setTimeout(() => {  next(); }, 1000);
		    break;
		case "dissolve2":
			// with dissolve2 - показать плавно, за 2 сек
			$(className).addClass("dissolve2Hide");
			setTimeout(() => {  next(); }, 2000);
		    break;
		}
	}
	if(isShow="show"){
		$(className).css( {"opacity":"0"});
		switch (obj["with"]["effect"]) {
			case "dspr":
				// with dspr - показать плавно, за 0.2 секунды
				$(className).addClass("dsprShow");
				 setTimeout(() => { 
					 $(className).css( {"opacity":"1"});
					 $(className).removeClass("dsprShow");
					 next();
					 }, 200);
			    break;
			case "dissolve":
				// with dissolve - показать плавно, за 1 сек
				$(className).addClass("dissolveShow");
				 setTimeout(() => {   $(className).css( {"opacity":"1"});
				 $(className).removeClass("dsprShow");
				 next(); }, 1000);
			    break;
			case "dissolve2":
				// with dissolve2 - показать плавно, за 2 сек
				$(className).addClass("dissolve2Show");
				setTimeout(() => {   $(className).css( {"opacity":"1"});
				 $(className).removeClass("dsprShow");
				 next(); }, 2000);
			    break;
			case "fade":
				// with fade - показать с затемнением, за 1 секунду
			    break;
			case "fade2":
				// with fade2 - показать с затемнением, за 2 сек
			    break;
			case "fade3":
				// with fade3 - показать с затемнением, за 3 сек
			    break;
			case "flash":
				// with flash - показать с засветлением, за 2 сек (fade to white)
			    break;
			case "fiash_red":
				// with fiash_red - показать с закраснением, за 2 сек (fade to red)
			    break;
			case "hpunch":
				// with hpunch - трясёт экран по горизонтали
			    break;
			case "vpunch":
				// with vpunch - трясёт экран по вертикали
			    break;
		}
	}
	
}
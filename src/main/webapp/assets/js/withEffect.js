//создание эффектов
function withEffect(obj, className, isShow){
	var cls=$(className);
	
	if(isShow=="hide"){
		if(obj["with"]==null){cls.css( {"opacity":"0"});setTimeout(() => {  next(); }, 1000); return};
		
//		cls.css( {"opacity":"1"});
		
		switch (obj["with"]["effect"]) {
			case "dspr":
				cls.addClass("dspr");
				 setTimeout(() => {  
					 cls.removeClass("dspr");
					 next(); }, 250);
			    break;
			case "dissolve":
				cls.addClass("dissolve");
				 setTimeout(() => {  
					 cls.removeClass("dissolve");
					 next(); }, 1100);
			    break;
			case "dissolve2":
				cls.addClass("dissolve2");
				setTimeout(() => {  
					cls.removeClass("dissolve2");
				  next(); }, 2100);
			    break;
		}
	}
	if(isShow=="show"){
		if(obj["with"]==null){cls.css( {"opacity":"1"});setTimeout(() => {  next(); }, 1000); return};
		
		cls.css( {"opacity":"0"});
		
		switch (obj["with"]["effect"]) {
		case "dspr":
			cls.addClass("dspr");
			
			 setTimeout(() => {  
//				 cls.css( {"opacity":"1"});
				 cls.removeClass("dspr");
//				 cls.css( {"opacity":"1"});
				 next(); }, 250);
		    break;
		case "dissolve":
			// with dissolve - показать плавно, за 1 сек
			cls.addClass("dissolve2");
			 setTimeout(() => {  
//				 cls.css( {"opacity":"1"});
				 cls.removeClass("dissolve2");
//				 cls.css( {"opacity":"1"});
				 next(); }, 1100);
		    break;
		case "dissolve2":
			// with dissolve2 - показать плавно, за 2 сек
			cls.addClass("dissolve2");
			setTimeout(() => {  
//				cls.css( {"opacity":"1"});
				cls.removeClass("dissolve2");
				cls.css( {"opacity":"1"});
			  next(); }, 2100);
		    break;
		}
	}
//	else if(isShow=="show"){
//		$(className).css( {"opacity":"0"});
//		switch (obj["with"]["effect"]) {
//			case "dspr":
//				// with dspr - показать плавно, за 0.2 секунды
//				$(className).addClass("dsprShow");
//				 setTimeout(() => { 
//					 $(className).css( {"opacity":"1"});
//					 $(className).removeClass("dsprShow");
//					 next();
//					 }, 200);
//			    break;
//			case "dissolve":
//				// with dissolve - показать плавно, за 1 сек
//				$(className).addClass("dissolveShow");
//				 setTimeout(() => {   
//					 $(className).css( {"opacity":"1"});
//				 $(className).removeClass("dissolveShow");
//				 next(); }, 1000);
//			    break;
//			case "dissolve2":
//				// with dissolve2 - показать плавно, за 2 сек
//				$(className).addClass("dissolve2Show");
//				setTimeout(() => {   
//					$(className).css( {"opacity":"1"});
//				 $(className).removeClass("dissolve2Show");
//				 next(); }, 2000);
//			    break;
//			case "fade":
//				// with fade - показать с затемнением, за 1 секунду
//			    break;
//			case "fade2":
//				// with fade2 - показать с затемнением, за 2 сек
//			    break;
//			case "fade3":
//				// with fade3 - показать с затемнением, за 3 сек
//			    break;
//			case "flash":
//				// with flash - показать с засветлением, за 2 сек (fade to white)
//			    break;
//			case "fiash_red":
//				// with fiash_red - показать с закраснением, за 2 сек (fade to red)
//			    break;
//			case "hpunch":
//				// with hpunch - трясёт экран по горизонтали
//			    break;
//			case "vpunch":
//				// with vpunch - трясёт экран по вертикали
//			    break;
//		}
//	}
	
}
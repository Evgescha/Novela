package com.novelasgame.novelas.entity.game;

import lombok.Data;

@Data
public class With {

	private final String type = "with";
	private String effect="";
	/*
	    with dspr - показать плавно, за 0.2 секунды
		with dissolve - за 1 сек
		with dissolve2 - за 2 сек
		with fade - показать с затемнением, за 1 секунду
		with fade2 - 2 сек 
		with fade3 - 3 сек 
		with flash - 2 сек (fade to white)
		with fiash_red - 2 сек (fade to red)
		with hpunch - трясёт экран по горизонтали 
		with vpunch - трясёт экран по вертикали
		Не применяется к конкретному show/scene.
	*/
	
	public With() {
	}
	
	public With(String str) {
		effect=str.split(" ")[1];
	}

}

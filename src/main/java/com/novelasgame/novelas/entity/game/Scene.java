package com.novelasgame.novelas.entity.game;

import com.novelasgame.novelas.service.TypeResources;

import lombok.Data;

@Data
public class Scene {
	private final String type = "scene";
	private String path = "null";
	private String name = "null";

	public Scene() {
	}

	public Scene(String str) {
		String[] arr = str.split(" ");

		if (arr.length == 2) {
			name = arr[1];
			path = TypeResources.BACKGROUND_IMAGES;
		} else if (arr.length == 3) {
			if (arr[1].equals("bg"))
				this.path = TypeResources.BACKGROUND_IMAGES;
			else
				this.path = TypeResources.SPECIAL_IMAGES;
			this.name = arr[2];
		}
	}

//	public static void main(String args[]) {
//		Scene scene;
//		scene = new Scene("scene bg int_dining_hall_day");
//		System.out.println(scene);
//		scene = new Scene("scene cg lvl_4_semen_win");
//		System.out.println(scene);
//	}

}

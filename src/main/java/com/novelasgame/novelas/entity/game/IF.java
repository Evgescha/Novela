package com.novelasgame.novelas.entity.game;

import java.util.ArrayList;

import lombok.Data;

@Data
public class IF {
	private final String type = "if";
	//if elsif else
	 private ArrayList<IFItem> items = new ArrayList<>();
}

package com.novelasgame.novelas.entity.game;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class IFItem {
	@JsonIgnore
	static Logger logger = Logger.getLogger(IFItem.class.getName());
	private final String type = "ifItem";

	private ArrayList<IFCriterion> criterions = new ArrayList<>();
	private ArrayList<Object> commands = new ArrayList<>();
	
}

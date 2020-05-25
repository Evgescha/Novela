package com.novelasgame.novelas.entity.game;

import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class IFCriterion {
	@JsonIgnore
	static Logger logger = Logger.getLogger(IFCriterion.class.getName());
	private final String type = "ifCriterion";

	private String key;
	private boolean equals = true;
	private String value;

	public IFCriterion(String key, String value, boolean equals) {
		this.key = key;
		this.value = value;
		this.equals = equals;
	}

}

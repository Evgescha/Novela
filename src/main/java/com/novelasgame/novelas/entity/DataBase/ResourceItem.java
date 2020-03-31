package com.novelasgame.novelas.entity.DataBase;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table
public class ResourceItem extends AbstractEntity {

	private String type;
	private String fileName;
	private String charName;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id")
	private Game game;

	public ResourceItem(String type, String fileName, String charName, Game game) {
		super();
		this.type = type;
		this.fileName = fileName;
		this.charName = charName;
		this.game = game;
	}

	public ResourceItem() {
		super();
	}

	@Override
	public String toString() {
		String temp = charName != null ? charName + "/" : "";
		return "/upload/files/" + getGame().getId() + "/" + getType() + "/" + temp + getFileName();
	}

}

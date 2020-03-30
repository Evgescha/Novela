package com.novelasgame.novelas.entity.DataBase;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Table
@Entity
@Data
public class Comment extends AbstractEntity {

	@Column
	String text;
	
	@Column
	Date date=new Date(System.currentTimeMillis());

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id")
	private Game game;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Override
	public String toString() {
		return "Comment [text=" + text + ", date=" + date + ", game=" + game.getTitle() + ", user=" + user.getUsername() + "]";
	}
}

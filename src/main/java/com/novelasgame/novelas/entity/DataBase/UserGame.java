package com.novelasgame.novelas.entity.DataBase;


import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
public class UserGame extends AbstractEntity{
    @Column
    private long gameId;
    
    @Column
    private long labelId;
    
    @ElementCollection
    @Column
    private Map<String,String> variables;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


	public UserGame() {
		super();
	}


	public UserGame(long gameId, long labelId, Map<String, String> variables) {
		super();
		this.gameId = gameId;
		this.labelId = labelId;
		this.variables = variables;
	}
    
    
}

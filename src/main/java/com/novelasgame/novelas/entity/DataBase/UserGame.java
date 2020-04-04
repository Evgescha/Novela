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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table
public class UserGame extends AbstractEntity{
    @Column
    private long gameId;
    
    @Column
    private String labelName;
    
    @ElementCollection
    @Column
    private Map<String,String> variables;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


	public UserGame() {
		super();
	}


	public UserGame(long gameId, String labelName, Map<String, String> variables) {
		super();
		this.gameId = gameId;
		this.labelName = labelName;
		this.variables = variables;
	}
    
    
}

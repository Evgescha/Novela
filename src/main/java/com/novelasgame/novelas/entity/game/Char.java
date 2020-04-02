package com.novelasgame.novelas.entity.game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Data
public class Char {
    @JsonIgnore  
    static Logger logger = Logger.getLogger(Char.class.getName());
    private final String type = "char";
    private String name;
    private String body;
    private String dress;
    private String emotion;
    private With with=null;
    
    // left, cleft, center, cright, right.
    private String position;
    
    // близко, далеко/ far/close
    private String location = "normal";
    
    // за спиной кого-то
    private String behind = "null";
    
    //с какой-то вещью в руках
    private String thing = "null";

    public Char() {}

	public Char(String name, String body, String dress, String emotion, String position, String location, String behind,
			String thing) {
		super();
		this.name = name;
		this.body = body;
		this.dress = dress;
		this.emotion = emotion;
		this.position = position;
		this.location = location;
		this.behind = behind;
		this.thing = thing;
	};


}

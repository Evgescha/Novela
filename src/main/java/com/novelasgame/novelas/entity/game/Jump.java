package com.novelasgame.novelas.entity.game;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@Data
public class Jump {
    @JsonIgnore
    static Logger logger = Logger.getLogger(Jump.class.getName());
    private final String type = "jump";
    private String label;
    private long gameId;
    
    
    public Jump() {}
    public Jump(String cmd, long gameId) {
        label=cmd.trim().split(" ")[1];
        this.gameId=gameId;
    }
}

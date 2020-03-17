package com.novelasgame.novelas.entity.game;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.novelasgame.novelas.service.TypeResources;

import lombok.Data;

@Data
public class Scene {
    @JsonIgnore
    static Logger logger = Logger.getLogger(Scene.class.getName());
    private final String type = "scene";
    private String path= "null";
    private String name = "null";
//    private String style = "";

    public Scene(){}
    public Scene(String str) {
        logger.fine("Scene. Line constructor");
        logger.log(Level.FINE, "Arguments: ",str);
        String[] arr = str.split(" ");
        if (arr.length == 2) { 
//            style=arr[1];
            name=arr[1];
            path=TypeResources.BACKGROUND_IMAGES;
        }
        else {
            if (arr[2].contains("black")) {
//                style=arr[2];
                path=TypeResources.BACKGROUND_IMAGES;
            } else {
                this.name = arr[2];
                this.path = TypeResources.SPECIAL_IMAGES;
            }
        }
    }




}

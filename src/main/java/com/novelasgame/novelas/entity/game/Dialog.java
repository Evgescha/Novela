package com.novelasgame.novelas.entity.game;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Dialog {
    @JsonIgnore    
    static Logger logger = Logger.getLogger(Dialog.class.getName());
    private  final String type="dialog";
    private String name="";
    private String fullName="";
    private String[] text;
    public Dialog() {}
    public Dialog(String str) {
        logger.fine("Dialog. Line constructor");
        logger.log(Level.FINE, "Arguments: ",str);
        String[] arr=str.split(" \"");
        
        if (str.charAt(0) == '"') {
            text=str.replace("\"", "").split("\\{w\\}");
        }
        if (arr.length > 1) {
            name=arr[0];
            text=arr[1].replace("\"", "").split("\\{w\\}");
        }    
//        for(int i=0; i<text.length;i++)
//        	text[i]=text[i].replace('{', '<').replace('}', '>');
        logger.log(Level.FINE, "Text for dialog: ",text);
    }

}

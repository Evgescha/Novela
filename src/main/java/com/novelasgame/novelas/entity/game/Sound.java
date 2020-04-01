package com.novelasgame.novelas.entity.game;

import com.novelasgame.novelas.service.TypeResources;

import lombok.Data;

@Data
public class Sound {
    private final String type = "sound";
    private String folder;
    private String name;
    private int fade = 0;
    private boolean play = false;
    private boolean sound_loop = false;

    public Sound() {
    }

    public Sound(String str) {
        String[] arr = str.split(" ");
        // play
        if (arr[0].contains("play")) {
            if (arr[1].contains("music")) {
                this.folder =TypeResources.MUSIC_SOUND;
                this.name = arr[2].split("\"")[1];
                if (arr.length > 3)
                    this.fade = Integer.parseInt(arr[4]);
                sound_loop = false;
            } else if (arr[1].contains("sound_loop")) {
                this.folder = TypeResources.SFX_SOUND;
                this.name = arr[2];
                sound_loop = true;
                fade = arr.length == 5 ? Integer.parseInt(arr[4]) : 0;
            } else if (arr[1].contains("sound")) {
                this.folder = TypeResources.SFX_SOUND;
                this.name = arr[2];
                sound_loop = false;
                fade = arr.length == 5 ? Integer.parseInt(arr[4]) : 0;
            } else if (arr[1].contains("ambience")) {
                this.folder = TypeResources.AMBIENCE_SOUND;
                this.name = arr[2];
                sound_loop = false;
                fade = arr.length == 5 ? Integer.parseInt(arr[4]) : 0;
            }
            play = true;
            this.name = name.replace("ambience_", "").replace("sfx_", "");
        }
        // stop
        else {
            this.play = false;
            if (arr[1].contains("sound") || arr[1].contains("sound_loop"))
                this.folder = TypeResources.SFX_SOUND;
            else if (arr[1].contains("ambience"))
                this.folder = TypeResources.AMBIENCE_SOUND;
            else if (arr[1].contains("music"))
                this.folder = TypeResources.MUSIC_SOUND;
            fade = arr.length == 4 ? Integer.parseInt(arr[3]) : 0;
        }

    }
    public static void main(String args[]) {
    	Sound snd;
    	snd = new Sound("play sound_loop sfx_street_traffic_outside fadein 2");
    	System.out.println(snd);
    	snd = new Sound("play ambience ambience_cold_wind_loop fadein 3");
    	System.out.println(snd);
    	snd = new Sound("play sound sfx_intro_bus_stop_steps");
    	System.out.println(snd);
    	snd = new Sound("play music music_list[\"lightness_radio_bus\"] fadein 7");
    	System.out.println(snd);
    }

}

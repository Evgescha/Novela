package com.novelasgame.novelas.service.Game;

import org.springframework.stereotype.Service;

import com.novelasgame.novelas.entity.game.Sound;
import com.novelasgame.novelas.service.FormatVariables;

@Service
public class SoundService {

    public String getSoundPath(Sound sd) {
        String path="gameRes/summer/sound/"+sd.getFolder()+FormatVariables.SPLITTER+sd.getName()+FormatVariables.OGG;
        return path;
    }
    
}

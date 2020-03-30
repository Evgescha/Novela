package com.novelasgame.novelas.service.Game;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novelasgame.novelas.entity.DataBase.Command;
import com.novelasgame.novelas.entity.DataBase.Game;
import com.novelasgame.novelas.entity.DataBase.Label;
import com.novelasgame.novelas.entity.DataBase.Scenario;
import com.novelasgame.novelas.service.DataBase.GameService;

@Service
public class ScenarioService {
    @Autowired
    GameService gameService;

    public void saveScenario(Scenario sc) {
        Game game = null;
        game = gameService.read(sc.getGameId());
        if (game == null) 
            return;
        

        Label label = null;
        if (game.getLabels() != null)
            for (Label lb : game.getLabels()) {
                if (lb.getName().contains(sc.getGameLabel())) {
                    label = lb;
                    break;
                }
            }
        if (label == null) {
            label = new Label();
            label.setName(sc.getGameLabel());
            label.setCommands(new ArrayList<Command>());
            game.getLabels().add(label);
        }
        List<Command> cmds = new ArrayList<>();
        String[] arr = sc.getGameCommands().split("\n");
        for (String str : arr) {
            Command cmd = new Command();
            cmd.setValue(str);
//            cmds.add(cmd);
            label.getCommands().add(cmd);
        }

        // gameService.create(game);
         gameService.update(game);
//         System.out.println("Добавлено: "+sc);
    }
}

package com.novelasgame.novelas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.novelasgame.novelas.entity.DataBase.Game;
import com.novelasgame.novelas.entity.DataBase.Scenario;
import com.novelasgame.novelas.service.DataBase.GameService;
import com.novelasgame.novelas.service.Game.ScenarioService;

@Controller
@RequestMapping("/scenario")
public class ScenarioController {
    

    @Autowired
    GameService gameService;
    
    @Autowired
    ScenarioService scenarioService;

    @GetMapping()
    private String getAddScenario(Model model) {
    	List<Game> games = gameService.findAll();
        if (!games.isEmpty())
            model.addAttribute("games", games);
        System.out.println("View add scene");
        return "addScenario";
    }

    @PostMapping()
    private void setScenario(@ModelAttribute Scenario scenario) {
        scenarioService.saveScenario(scenario);
    }
}

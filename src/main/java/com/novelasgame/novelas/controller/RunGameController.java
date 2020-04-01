package com.novelasgame.novelas.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.novelasgame.novelas.service.Game.LabelParserService;

@Controller
@RequestMapping("/runGame")
public class RunGameController {
    @Autowired
    LabelParserService labelParserService;

    @GetMapping
    private String getRunScene(@RequestParam(value = "gameId", required = true) long gameId,
            @RequestParam(defaultValue = "default", required = false) String labelName, Model model) {

        ArrayList<Object> parse = labelParserService.Parse(gameId, labelName);
        
        model.addAttribute("gameId", gameId);
        model.addAttribute("scenario", labelParserService.toJson(parse));
        System.out.println("view scene runGame");
        return "runGame";
    }

}

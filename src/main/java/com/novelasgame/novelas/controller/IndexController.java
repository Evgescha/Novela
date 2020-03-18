package com.novelasgame.novelas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.novelasgame.novelas.entity.DataBase.Game;
import com.novelasgame.novelas.service.DataBase.GameService;

@Controller
@RequestMapping("/")
public class IndexController {
	@Autowired
	GameService gameService;

	@GetMapping()
	private String one(Model model) {
		System.out.println("view index scene");

		List<Game> games = gameService.findAll();
		if (!games.isEmpty())
			model.addAttribute("games", games);
		
		return "index";
	}
}

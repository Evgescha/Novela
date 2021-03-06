package com.novelasgame.novelas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.novelasgame.novelas.service.DataBase.CommentService;
import com.novelasgame.novelas.service.DataBase.GameService;

@Controller
@RequestMapping()
public class DescriptionGameController {
	
	@Autowired
	GameService gameService;
	
	@Autowired 
	CommentService commentService;
	
	@GetMapping("/description{gameId}")
	public String getDescriptionGamePage(@PathVariable long gameId, Model model){
		model.addAttribute("game", gameService.read(gameId));
		model.addAttribute("comments", commentService.findByGame( gameService.read(gameId)));
		return "descriptionGame";
	}
}

package com.novelasgame.novelas.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.novelasgame.novelas.entity.DataBase.Game;
import com.novelasgame.novelas.entity.DataBase.User;
import com.novelasgame.novelas.service.DataBase.GameService;
import com.novelasgame.novelas.service.DataBase.UserServiceImpl;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	@Autowired
	GameService gameService;
	@Autowired
	UserServiceImpl userService;
	@GetMapping()
	private String getProfile(Model model, Principal principal) {
		
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		List<Game> games = user.getGames();
		if (!games.isEmpty())
			model.addAttribute("games", games);
		
		return "profile";
	}
	@PostMapping
	private String postProfile(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		userService.delete(user.getId());
		return "redirect:/logout";
	}
}

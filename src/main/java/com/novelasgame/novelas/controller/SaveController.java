package com.novelasgame.novelas.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.novelasgame.novelas.entity.DataBase.User;
import com.novelasgame.novelas.entity.DataBase.UserGame;
import com.novelasgame.novelas.service.DataBase.UserGameServiceImpl;
import com.novelasgame.novelas.service.DataBase.UserServiceImpl;

@Controller
@RequestMapping("/save")
public class SaveController {
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	UserGameServiceImpl userGameService;

	@PostMapping
	@ResponseBody
	private String setSave(@RequestParam Map<String,String> variables, Principal principal,long gameId, long labelId) {
		
		User user = userService.findByUsername(principal.getName());
		UserGame ug=null;
		for(UserGame _ug: user.getUserGames()) {
			if(_ug.getGameId()==gameId) {ug=_ug; break;}
		}
				
		if(ug!=null) {
			ug.getVariables().putAll(variables);
			userGameService.update(ug);
		}else {
			UserGame userGame = new UserGame(gameId, labelId, variables);
			userGameService.create(userGame);
			
			user.getUserGames().add(userGame);
			userService.update(user);
			
			userGame.setUser(user);
			userGameService.update(userGame);
			
		}
		return "Lol";
	}
}

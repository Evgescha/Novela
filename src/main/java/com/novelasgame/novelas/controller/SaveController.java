package com.novelasgame.novelas.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	private String setSave(@RequestParam(value = "gameId", required = true) long gameId,
			@RequestParam(defaultValue = "default", required = true) String labelName,
			@RequestParam(name = "variables", required = true) String variables, Principal principal)
			throws JsonMappingException, JsonProcessingException {
		
		HashMap<String, String> readValue = new HashMap<String, String>();
		if (variables != null && !variables.equals("[]")) {
			variables = variables.replace("[[", "{").replace("]]", "}");
			ObjectMapper mapper = new ObjectMapper();
			readValue = mapper.readValue(variables, HashMap.class);
			System.out.println("variables: "+readValue);
		}
		User user = userService.findByUsername(principal.getName());
		UserGame ug = null;
		for (UserGame _ug : user.getUserGames()) {
			if (_ug.getGameId() == gameId) {
				ug = _ug;
				break;
			}
		}

		if (ug != null) {
			System.out.println("found usergame");
			ug.getVariables().clear();
			userGameService.update(ug);
			System.out.println("clear and update variables");
			ug.getVariables().putAll(readValue);
			userGameService.update(ug);
			System.out.println("update usergame");
		} else {
			System.out.println("not found usergame");
			UserGame userGame = new UserGame(gameId, labelName, readValue);
			userGameService.create(userGame);
			System.out.println("create usergame: "+userGame);
			
			user.getUserGames().add(userGame);
			userService.update(user);
			System.out.println("updated user");

			userGame.setUser(user);
			userGameService.update(userGame);
			System.out.println("updated usergame");
		}
		return "Save created";
	}
}

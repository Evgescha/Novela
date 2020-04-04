package com.novelasgame.novelas.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.novelasgame.novelas.service.Game.LabelParserService;

@Controller
@RequestMapping
public class SaveController {
	@Autowired
	UserServiceImpl userService;

	@Autowired
	UserGameServiceImpl userGameService;
	@Autowired
	LabelParserService labelParserService;

	ObjectMapper mapper = new ObjectMapper();

	@GetMapping("/load")
	private String loadSave(@RequestParam(value = "gameId", required = true) long gameId, Principal principal,
			Model model) throws JsonProcessingException {
//		System.out.println("load game");
		User user = userService.findByUsername(principal.getName());
		UserGame ug = null;
		for (UserGame _ug : user.getUserGames()) {
			if (_ug.getGameId() == gameId) {
				ug = _ug;
				break;
			}
		}
		ArrayList<Object> parse = null;
		if (ug == null) {
//			System.out.println("game not found");
			model.addAttribute("labelName", "default");
			parse = labelParserService.Parse(gameId, "default");
			model.addAttribute("variables", "{}");
		} else {
//			System.out.println("game found");
//			System.out.println("game label: "+ug.getVariables());
//			System.out.println("game valiables: "+mapper.writeValueAsString(ug.getVariables()));
			model.addAttribute("labelName", ug.getLabelName());
			parse = labelParserService.Parse(gameId, ug.getLabelName());
			model.addAttribute("variables", mapper.writeValueAsString(ug.getVariables()));
		}
		model.addAttribute("gameId", gameId);
		model.addAttribute("scenario", labelParserService.toJson(parse));
		return "runGame";
	}

	@PostMapping("/save")
	@ResponseBody
	private String setSave(@RequestParam(value = "gameId", required = true) long gameId,
			@RequestParam(defaultValue = "default", required = true) String labelName,
			@RequestParam(name = "variables", required = true) String variables, Principal principal)
			throws JsonMappingException, JsonProcessingException {

		HashMap<String, String> readValue = new HashMap<String, String>();
		if (variables != null && !variables.equals("[]")) {
			variables = variables.replace("[[", "{").replace("]]", "}");
			readValue = mapper.readValue(variables, HashMap.class);
//			System.out.println("variables: " + readValue);
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
//			System.out.println("found usergame");
			ug.getVariables().clear();
			ug.setLabelName(labelName);
			userGameService.update(ug);
//			System.out.println("clear and update variables");
			ug.getVariables().putAll(readValue);
			userGameService.update(ug);
//			System.out.println("update usergame");
		} else {
//			System.out.println("not found usergame");
			UserGame userGame = new UserGame(gameId, labelName, readValue);
			userGameService.create(userGame);
//			System.out.println("create usergame: " + userGame);

			user.getUserGames().add(userGame);
			userService.update(user);
//			System.out.println("updated user");

			userGame.setUser(user);
			userGameService.update(userGame);
//			System.out.println("updated usergame");
		}
		return "Save created";
	}
}

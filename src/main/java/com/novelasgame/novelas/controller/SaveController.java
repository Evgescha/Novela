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
import com.novelasgame.novelas.service.DataBase.GameService;
import com.novelasgame.novelas.service.DataBase.UserGameServiceImpl;
import com.novelasgame.novelas.service.DataBase.UserServiceImpl;
import com.novelasgame.novelas.service.Game.LabelParserService;

@Controller
@RequestMapping
public class SaveController {
	@Autowired
	UserServiceImpl userService;

	@Autowired
	GameService gameService;
	@Autowired
	UserGameServiceImpl userGameService;
	@Autowired
	LabelParserService labelParserService;

	ObjectMapper mapper = new ObjectMapper();

	@GetMapping("/load")
	private String loadSave(@RequestParam(value = "gameId", required = true) long gameId, Principal principal,
			Model model) throws JsonProcessingException {
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
			model.addAttribute("labelName", "default");
			parse = labelParserService.Parse(gameId, "default");
			model.addAttribute("variables", "{}");
		} else {
			model.addAttribute("labelName", ug.getLabelName());
			parse = labelParserService.Parse(gameId, ug.getLabelName());
			model.addAttribute("variables", mapper.writeValueAsString(ug.getVariables()));
		}
		model.addAttribute("gameId", gameId);
		model.addAttribute("scenario", labelParserService.toJson(parse));
		model.addAttribute("names", mapper.writeValueAsString(gameService.read(ug.getGameId()).getCharNames()));
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
			ug.getVariables().clear();
			ug.setLabelName(labelName);
			userGameService.update(ug);
			ug.getVariables().putAll(readValue);
			userGameService.update(ug);
		} else {
			UserGame userGame = new UserGame(gameId, labelName, readValue);
			userGameService.create(userGame);
			user.getUserGames().add(userGame);
			userService.update(user);
			userGame.setUser(user);
			userGameService.update(userGame);
		}
		return "Save created";
	}
}

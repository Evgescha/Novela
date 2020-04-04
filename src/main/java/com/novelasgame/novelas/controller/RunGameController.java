package com.novelasgame.novelas.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novelasgame.novelas.service.Game.LabelParserService;

@Controller
@RequestMapping("/runGame")
public class RunGameController {
	@Autowired
	LabelParserService labelParserService;

	@GetMapping
	private String getRunScene(@RequestParam(value = "gameId", required = true) long gameId,
			@RequestParam(defaultValue = "default", required = false) String labelName, Model model,
			@RequestParam(name = "variables", required = false) String variables)
			throws JsonMappingException, JsonProcessingException {

		return postRunScene(gameId, labelName, model, variables);
	}

	@PostMapping
	private String postRunScene(@RequestParam(value = "gameId", required = true) long gameId,
			@RequestParam(defaultValue = "default", required = false) String labelName, Model model,
			@RequestParam(name = "variables", required = false) String variables)
			throws JsonMappingException, JsonProcessingException {
		HashMap readValue = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		if (variables != null && !variables.equals(""))
			readValue = mapper.readValue(variables, HashMap.class);

		ArrayList<Object> parse = labelParserService.Parse(gameId, labelName);
		model.addAttribute("gameId", gameId);
		model.addAttribute("labelName", labelName);
		model.addAttribute("variables", mapper.writeValueAsString(readValue));
		model.addAttribute("scenario", labelParserService.toJson(parse));
		return "runGame";
	}

}

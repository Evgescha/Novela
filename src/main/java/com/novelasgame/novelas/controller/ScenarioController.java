package com.novelasgame.novelas.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.novelasgame.novelas.entity.DataBase.Command;
import com.novelasgame.novelas.entity.DataBase.Game;
import com.novelasgame.novelas.entity.DataBase.Label;
import com.novelasgame.novelas.entity.DataBase.Scenario;
import com.novelasgame.novelas.service.DataBase.CommandService;
import com.novelasgame.novelas.service.DataBase.GameService;
import com.novelasgame.novelas.service.DataBase.LabelService;
import com.novelasgame.novelas.service.Game.ScenarioService;

@Controller
@RequestMapping("/scenario")
public class ScenarioController {

	@Autowired
	GameService gameService;

	@Autowired
	ScenarioService scenarioService;

	@Autowired
	CommandService commandService;

	@Autowired
	LabelService labelService;

	@GetMapping()
	private String getAddScenario(Model model) {
		List<Game> games = gameService.findAll();
		if (!games.isEmpty())
			model.addAttribute("games", games);
		System.out.println("View add scene");
		return "scenario";
	}

	@PostMapping()
	private String setScenario(@ModelAttribute Scenario scenario) {
		scenarioService.saveScenario(scenario);
		return "redirect:/scenario";
	}

	@PostMapping("/deleteCommand")
	private String deleteCommand(@RequestParam("labelId") long labelId, @RequestParam("commandId") long[] commandIds) {
		Label label = labelService.read(labelId);
		for (long commandId : commandIds) {
			int id=-1;
			List<Command> commands = label.getCommands();
			System.out.println("size: "+commands.size());
			for (int i = commands.size()-1;i>=0; i--) {
				if (commands.get(i).getId() == commandId) {
					id=i;
				}
				System.out.println("i: "+i+", id: "+id);
			}
			commands.remove(id);
		}
		labelService.update(label);
		for (long commandId : commandIds) {
			commandService.delete(commandId);
		}
		return "redirect:/scenario";
	}

	@PostMapping("/deleteLabel")
	private String deleteCommand(@RequestParam("gameId") long gamelId, @RequestParam("labelId") long labelId) {
		Game game = gameService.read(gamelId);
		for (Label tmplabel : game.getLabels()) {
			if (tmplabel.getId() == labelId) {
				game.getLabels().remove(tmplabel);
				break;
			}
		}
		gameService.update(game);
		
		
		
		Label label = labelService.read(labelId);
		List<Long> forDel = new ArrayList<Long>();
		List<Command> commands = label.getCommands();
		while(commands.size()>0) {
			forDel.add(commands.get(0).getId());
			commands.remove(0);
		}
		labelService.update(label);
		labelService.delete(labelId);
		for (long id:forDel) {
			commandService.delete(id);
		}
		forDel=null;
		return "redirect:/scenario";
	}
}

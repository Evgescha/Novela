package com.novelasgame.novelas.controller;

import java.security.Principal;
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
import com.novelasgame.novelas.entity.DataBase.User;
import com.novelasgame.novelas.service.DataBase.CommandService;
import com.novelasgame.novelas.service.DataBase.GameService;
import com.novelasgame.novelas.service.DataBase.LabelService;
import com.novelasgame.novelas.service.DataBase.UserServiceImpl;
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

	@Autowired
	UserServiceImpl userService;

	private List<Game> getGames(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		if (user != null)
			return user.getGames();
		return null;
	}

	@GetMapping()
	private String getAddScenario(Model model, Principal principal) {
//		List<Game> games = userService.findByUsername(principal.getName()).getGames();
//		if (!games.isEmpty())
		List<Game> games;
		if ((games = getGames(principal)) != null)
			model.addAttribute("games", games);
		return "scenario";
	}

	@PostMapping()
	private String setScenario(@ModelAttribute Scenario scenario, Model model) {
		boolean isSave = scenarioService.saveScenario(scenario);
		model.addAttribute("notification", isSave ? "Scenario success added" : "Can't add scenario.");
		return "redirect:/scenario";
	}

	@PostMapping("/deleteCommand")
	private String deleteCommand(@RequestParam("labelId") long labelId, @RequestParam("commandId") long[] commandIds,
			Model model, Principal principal) {
		Label label = labelService.read(labelId);
		for (long commandId : commandIds) {
			int id = -1;
			List<Command> commands = label.getCommands();
//			System.out.println("size: " + commands.size());
			intro: for (int i = commands.size() - 1; i >= 0; i--) {
				if (commands.get(i).getId() == commandId) {
					id = i;
					break intro;
				}
//				System.out.println("i: " + i + ", id: " + id);
			}
			commands.remove(id);
		}
		labelService.update(label);
		for (long commandId : commandIds) {
			commandService.delete(commandId);
		}
		model.addAttribute("notification", "Commands were deleted");
			model.addAttribute("games", getGames(principal));
		return "scenario";
	}

	@PostMapping("/update")
	private String updateCommand(@RequestParam(name = "commandId", required = true) long[] commandId,
			@RequestParam(name = "newValue", required = true) String[] newValue, Model model, Principal principal) {
		boolean isOk = true;

		List<String> NEW = new ArrayList<String>();
		for (String value : newValue) {
//			System.out.println("value:" + value + "|");
			if (value.length() > 0)
				NEW.add(value);
		}
		isOk = NEW.size() == commandId.length;
		if (isOk)
			for (int i = 0; i < commandId.length; i++) {
				Command read = commandService.read(commandId[i]);
				read.setValue(NEW.get(i));
				isOk = isOk && commandService.update(read);
			}
		model.addAttribute("notification", isOk ? "Commands were updated" : "Can'u update commands.");
			model.addAttribute("games", getGames(principal));
		return "scenario";
	}

	@PostMapping("/deleteLabel")
	private String deleteCommand(@RequestParam("gameId") long gamelId, @RequestParam("labelId") long labelId,
			Principal principal, Model model) {
		boolean isOk = true;
		Game game = gameService.read(gamelId);
		for (Label tmplabel : game.getLabels()) {
			if (tmplabel.getId() == labelId) {
				isOk = isOk && game.getLabels().remove(tmplabel);
				break;
			}
		}
		isOk = isOk && gameService.update(game);

		Label label = labelService.read(labelId);
		List<Long> forDel = new ArrayList<Long>();
		List<Command> commands = label.getCommands();
		while (commands.size() > 0) {
			forDel.add(commands.get(0).getId());
			commands.remove(0);
		}
		isOk = isOk && labelService.update(label);
		isOk = isOk && labelService.delete(labelId);
		for (long id : forDel) {
			isOk = isOk && commandService.delete(id);
		}
		forDel = null;

		List<Game> games;
		if ((games = getGames(principal)) != null)
			model.addAttribute("games", games);
		model.addAttribute("notification", isOk ? "Label was deleted" : "Can'u delete label");
		return "/scenario";
	}
}

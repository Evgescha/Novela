package com.novelasgame.novelas.service.Game;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novelasgame.novelas.entity.DataBase.Command;
import com.novelasgame.novelas.entity.DataBase.Game;
import com.novelasgame.novelas.entity.DataBase.Label;
import com.novelasgame.novelas.entity.DataBase.ResourceItem;
import com.novelasgame.novelas.entity.game.Char;
import com.novelasgame.novelas.entity.game.Dialog;
import com.novelasgame.novelas.entity.game.Hide;
import com.novelasgame.novelas.entity.game.Jump;
import com.novelasgame.novelas.entity.game.Menu;
import com.novelasgame.novelas.entity.game.MenuItem;
import com.novelasgame.novelas.entity.game.Scene;
import com.novelasgame.novelas.entity.game.Sound;
import com.novelasgame.novelas.entity.game.Variables;
import com.novelasgame.novelas.entity.game.Window;
import com.novelasgame.novelas.service.DataBase.GameService;
import com.novelasgame.novelas.service.DataBase.ResourcesItemService;

@Service
public class LabelParserService {
	@Autowired
	private GameService gameService;
	@Autowired
	private ResourcesItemService reItemService;

	private final ObjectMapper mapper = new ObjectMapper();
	public ArrayList<Object> list = new ArrayList<>();
	List<Command> commands = new ArrayList<>();
	List<String> charNames = new ArrayList<>();

	// для цикла
	int i = 0;
	long gameId;
	String cmd;

	public ArrayList<Object> Parse(long gameId, String labelName) {
		this.gameId = gameId;
		list.clear();
		Game game = gameService.read(gameId);
		Label label = null;
		for (Label lbl : game.getLabels()) {
			if (lbl.getName().equalsIgnoreCase(labelName)) {
				label = lbl;
				break;
			}
		}

		commands = label.getCommands();
		cmd = "";

		for (i = 0; i < commands.size(); i++) {
			cmd = commands.get(i).getValue().trim();
			String[] arr = cmd.split(" ");

			if (arr[0].contains("menu")) {
				list.add(getMenu(cmd));
			} else
				list.add(getCommand(cmd));

		}
		return list;
	}

	public Object getMenu(String cmd) {
		i++;
		Menu menu = new Menu();
		cmd = commands.get(i).getValue().replace("\t", "    ");
		while (cmd.charAt(0) == ' ') {
			if (cmd.charAt(4) != ' ') {
				menu.getItems().add(new MenuItem(cmd.trim()));
			} else {
				menu.getItems().get(menu.getItems().size() - 1).getCommands().add(getCommand(cmd.trim()));
				System.out.println("menu chooser: " + getCommand(cmd.trim()));
			}
			i++;
			if (commands.size() > i)
				cmd = commands.get(i).getValue().replace("\t", "    ");
			else
				break;
		}
		i--;
		return menu;
	}

	public Object getCommand(String cmd) {
		String[] arr = cmd.split(" ");
//		System.out.println("command: " + cmd);
		if (cmd.charAt(0) == '"')
			return new Dialog(cmd);

		if (arr.length > 1 && arr[1].charAt(0) == '"')
			return new Dialog(cmd);

		if (arr[0].contains("stop") || arr[0].contains("play"))
			return new Sound(cmd);

		if (arr[0].contains("scene"))
			return new Scene(cmd);

		if (arr[0].contains("window"))
			return new Window(cmd);

		if (arr[0].contains("hide"))
			return new Hide(cmd);

		if (arr[0].contains("show") && arr.length>2)
			return getChar(cmd);

		if (cmd.charAt(0) == '$' && (cmd.contains("=") || cmd.contains("++") || cmd.contains("--")))
			return new Variables(cmd);

		if (arr[0].contains("jump"))
			return new Jump(cmd, gameId);

		return null;
	}

	private Char getChar(String line) {
//		System.out.println("making char");
		Char chr = new Char();
		line = line.trim();
		String[] split = line.split(" ");

		String name, emotion, location = "normal", position = "center", body = "null", dress = "null", behind = "null",
				thing = "null";

		// get name and emotion
		name = split[1];
		emotion = split[2];
		line = line.replace("show " + name + " " + emotion, "");

		// get location
		if (line.contains(" close")) {
			location = "close";
			line = line.replace(" close", "");
		} else if (line.contains(" far")) {
			location = "far";
			line = line.replace(" far", "");
		}

		// get position
		if (line.contains(" at ")) {
			if (line.contains(" at left")) {
				position = "left";
				line = line.replace(" at left", "");
			} else if (line.contains(" at cleft")) {
				position = "cleft";
				line = line.replace(" at cleft", "");
			} else if (line.contains(" at center")) {
				position = "center";
				line = line.replace(" at center", "");
			} else if (line.contains(" at cright")) {
				position = "cright";
				line = line.replace(" at cright", "");
			} else if (line.contains(" at right")) {
				position = "right";
				line = line.replace(" at right", "");
			}
		}

		if (line.contains(" behind")) {
			behind = line.substring(line.lastIndexOf(" behind") + 7, line.length()).trim();
			line = line.substring(0, line.lastIndexOf(" behind "));
		}

		// find item with current emotion
		List<ResourceItem> findByGameAndCharName = reItemService.findByGameAndCharName(gameService.read(gameId),
				split[1]);
		for (ResourceItem item : findByGameAndCharName) {
			if (item.getFileName().contains(emotion)) {
				String tempPose = "";
				
				tempPose = item.getFileName().substring(0, item.getFileName().indexOf(emotion));
				body = tempPose + "body";
				emotion = item.getFileName().substring(0, item.getFileName().indexOf("."));
				dress = tempPose + split[3];

				break;
			}
		}

		return  new Char(name,body, dress,emotion, position, location, behind, thing);
	}

	public String toJson(Object temp) {
		try {
			return mapper.writeValueAsString(temp);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}

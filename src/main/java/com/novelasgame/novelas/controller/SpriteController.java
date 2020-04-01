package com.novelasgame.novelas.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.novelasgame.novelas.entity.DataBase.ResourceItem;
import com.novelasgame.novelas.entity.game.Char;
import com.novelasgame.novelas.service.Game.СharacterService;

@Controller
@RequestMapping("/images")
public class SpriteController {

	@Autowired
	private СharacterService charService;

	@ResponseBody
	@GetMapping(value = "/char", produces = MediaType.IMAGE_PNG_VALUE)
	private byte[] getChar(@RequestParam("gameId") long gameId, @ModelAttribute Char chr) throws IOException {
		return charService.getImageByte(gameId, chr);
	}

	@GetMapping("/resItem{resItem}")
	private String getRes(@ModelAttribute ResourceItem resItem) {
		return "/upload/files/" + resItem.getGame().getId() + "/" + resItem.getType() + "/" + resItem.getFileName();
	}
}

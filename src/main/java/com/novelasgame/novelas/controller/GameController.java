package com.novelasgame.novelas.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.novelasgame.novelas.entity.DataBase.Game;
import com.novelasgame.novelas.entity.DataBase.Genre;
import com.novelasgame.novelas.entity.DataBase.ResourceItem;
import com.novelasgame.novelas.service.TypeResources;
import com.novelasgame.novelas.service.DataBase.GameService;
import com.novelasgame.novelas.service.DataBase.GenreService;
import com.novelasgame.novelas.service.DataBase.ResourcesItemService;
import com.novelasgame.novelas.storage.StorageProperties;
import com.novelasgame.novelas.storage.StorageService;

@Controller
@RequestMapping("/games")
public class GameController {

	@Autowired
	GameService gameService;
	@Autowired
	ResourcesItemService resourcesItemService;
	@Autowired
	StorageProperties storageProps;
	@Autowired
	StorageService storageService;
	@Autowired
	GenreService genreService;

	@GetMapping()
	public String getGameController(Model model, @RequestParam(name = "notification", required = false) String notification) {
		List<Game> games = gameService.findAll();
		if (!games.isEmpty())
			model.addAttribute("games", games);
		if (notification != null)
			model.addAttribute("notification", notification);
		model.addAttribute("genres", genreService.findAll());
		return "addGame";
	}

	@PostMapping("/addGame")
	private String postGameController(@ModelAttribute Game game,
			@RequestParam(name = "ava", required = true) MultipartFile avatar,
			@RequestParam(name = "screen", required = true) MultipartFile[] screens,
			@RequestParam(name = "genre", required = false) String[] genre, RedirectAttributes ra,
			Principal principal) {
		
			if (gameService.findByTitle(game.getTitle())!=null) {
				ra.addAttribute("notification", "A game with this name already exists");
				return "redirect:/games";
			}
		boolean isCreate = false;
		if ((isCreate = gameService.addGame(game, principal.getName()))) {
			// загрузить аватарку
			game = gameService.findByTitle(game.getTitle());
			storageProps.setLocation(game.getId() + "", TypeResources.AVATAR_IMAGE);
			ResourceItem item = new ResourceItem(TypeResources.AVATAR_IMAGE, avatar.getOriginalFilename(), null, game);
//			System.out.println(item);
			storageService.store(avatar);
			resourcesItemService.create(item);
			game.setAvatar("upload/files/" + game.getId() + "/" + TypeResources.AVATAR_IMAGE + "/"
					+ avatar.getOriginalFilename());
			gameService.update(game);
			item = null;

			// загрузить скрины
			storageProps.setLocation(game.getId() + "", TypeResources.SCREEN_IMAGE);
			for (MultipartFile file : screens) {
				item = new ResourceItem(TypeResources.SCREEN_IMAGE, file.getOriginalFilename(), null, game);
//				System.out.println(item);
				storageService.store(file);
				resourcesItemService.create(item);
				item = null;
			}

			// genres
			Genre _genre = null;
			for (String genr : genre) {
				_genre = genreService.findByName(genr);
				game.getGenres().add(_genre);
			}
			_genre = null;
			gameService.update(game);
			return "redirect:/description"+game.getId();
		}

		ra.addAttribute("notification", "Success added - " + isCreate);
		return "redirect:/games";
	}

	@GetMapping("/deleteGame")
//    @PreAuthorize("hasPermission(#gameId, hasRole('ROLE_ADMIN'))")
	private String getDeleteGame(@RequestParam(name = "gameId", required = true) long gameId, RedirectAttributes ra) {
		boolean delete = gameService.delete(gameId);
		ra.addAttribute("notification", delete==true?"The game was successfully deleted":"The game was not deleted");
		return "redirect:/games";
	}

}

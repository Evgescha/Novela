package com.novelasgame.novelas.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.novelasgame.novelas.entity.DataBase.Game;
import com.novelasgame.novelas.entity.DataBase.ResourceItem;
import com.novelasgame.novelas.service.TypeResources;
import com.novelasgame.novelas.service.DataBase.GameService;
import com.novelasgame.novelas.service.DataBase.ResourcesItemService;
import com.novelasgame.novelas.storage.StorageFileNotFoundException;
import com.novelasgame.novelas.storage.StorageProperties;
import com.novelasgame.novelas.storage.StorageService;

@Controller
public class FileUploadController {

	private final StorageService storageService;
	@Autowired
	GameService gameService;
	@Autowired
	ResourcesItemService resourcesItemService;
	@Autowired
	StorageProperties storageProps;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/upload")
	public String listUploadedFiles(Model model) throws IOException {
		List<Game> findAll = gameService.findAll();
		model.addAttribute("games", findAll);
		return "uploadForm";
	}

	@GetMapping("/upload/files/{gameId}/{typeName}/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String gameId, @PathVariable String typeName,
			@PathVariable String filename) {
		storageProps.setLocation();
		System.out.println("location: " + storageProps.getLocation());
		Resource file = storageService.loadAsResource(gameId, typeName, filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(file);
	}
	@GetMapping("/upload/files/{gameName}/{typeName}/{charName}/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String gameName, @PathVariable String typeName,
			@PathVariable String filename,@PathVariable String charName) {
		return serveFile(gameName, typeName+"/"+charName, filename);
	}
	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam(name = "files", required = true) MultipartFile[] files,
			@RequestParam(name = "game", required = true) String gameName,
			@RequestParam(name = "type", required = true) String typeName,
			@RequestParam(name = "charName", required = false, defaultValue = "") String charName,
			RedirectAttributes redirectAttributes) {
		Game game = gameService.findByTitle(gameName);
		if (!typeName.equalsIgnoreCase(TypeResources.CHARACTER_IMAGES)) {
			System.out.println("game is: " + gameName);
			System.out.println("type is: " + typeName);
			storageProps.setLocation(gameName, typeName);
			for (MultipartFile file : files) {
				System.out.println(charName);
				ResourceItem item = new ResourceItem(typeName, file.getOriginalFilename(),null, game);
				storageService.store(file);
				resourcesItemService.create(item);
			}
		} else {
			storageProps.setLocation(gameName, typeName, charName);
			for (MultipartFile file : files) {
				ResourceItem item = new ResourceItem(TypeResources.CHARACTER_IMAGES, file.getOriginalFilename(),charName, game);
				storageService.store(file);
				resourcesItemService.create(item);
			}
		}

		redirectAttributes.addFlashAttribute("message", "You successfully uploaded files!");
		return "redirect:/upload";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}

package com.novelasgame.novelas.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
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
import com.novelasgame.novelas.service.DataBase.UserServiceImpl;
import com.novelasgame.novelas.storage.StorageFileNotFoundException;
import com.novelasgame.novelas.storage.StorageProperties;
import com.novelasgame.novelas.storage.StorageService;

@Controller
public class ResourcesController {

	private final StorageService storageService;
	@Autowired
	GameService gameService;
	@Autowired
	ResourcesItemService resourcesItemService;
	@Autowired
	StorageProperties storageProps;

	@Autowired
	UserServiceImpl userService;

	@Autowired
	public ResourcesController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/upload")
	public String listUploadedFiles(Model model, Principal principal) throws IOException {
		if (principal.getName().equals("admin"))
			model.addAttribute("games", gameService.findAll());
		else
			model.addAttribute("games", userService.findByUsername(principal.getName()).getGames());
		return "resources";
	}

	@ResponseBody
	@GetMapping("/upload/getRes")
	public List<ResourceItem> getUploadedRes(@RequestParam(name = "gameId", required = true) long gameId,
			@RequestParam(name = "type", required = true) String typeName, Model model) throws IOException {
		Game game = gameService.read(gameId);
		List<ResourceItem> findByGameAndType = resourcesItemService.findByGameAndType(game, typeName);
		return findByGameAndType;
	}

	@PostMapping("/upload/updateRes")
	public String updateRes(@RequestParam(name = "newName", required = true) String[] newName,
			@RequestParam(name = "resId", required = true) long[] resId, RedirectAttributes redirectAttributes)
			throws IOException {

		List<String> newNames = new ArrayList<String>();
		for (String name : newName)
			if (name.length() > 1)
				newNames.add(name);

		if (newNames.size() != resId.length) {
			redirectAttributes.addFlashAttribute("notification", "Incorrectly marked resources for editing");
			return "redirect:/upload";
		}
		for (int i = 0; i < resId.length; i++) {
			ResourceItem read = resourcesItemService.read(resId[i]);
			storageService.rename(read, newNames.get(i));
			read.setFileName(newNames.get(i));
			resourcesItemService.update(read);
		}
		redirectAttributes.addFlashAttribute("notification", "File names updated");
		return "redirect:/upload";
	}

	@PostMapping("/upload/deleteRes")
	public String removeRes(@RequestParam(name = "gameId", required = true) long gameId,
			@RequestParam(name = "resId", required = true) long[] resIds, RedirectAttributes redirectAttributes)
			throws IOException {
		Game game = gameService.read(gameId);

		for (long resId : resIds) {
			int id = -1;
			List<ResourceItem> resourceItems = game.getResourceItems();
			System.out.println("size: " + resourceItems.size());
			for (int i = resourceItems.size() - 1; i >= 0; i--) {
				if (resourceItems.get(i).getId() == resId) {
					id = i;
				}
				System.out.println("i: " + i + ", id: " + id);
			}
			resourceItems.remove(id);
		}
		gameService.update(game);
		for (long resId : resIds) {
			storageService.delete(resourcesItemService.read(resId));
			resourcesItemService.delete(resId);
		}

		redirectAttributes.addFlashAttribute("notification", "Files were deleted");
		return "redirect:/upload";
	}

	@PostMapping("/upload/deleteAllResType")
	public String removeAllResType(@RequestParam(name = "gameId", required = true) long gameId,
			@RequestParam(name = "resourcesType", required = true) String resourcesType,
			RedirectAttributes redirectAttributes) throws IOException {

		Game game = gameService.read(gameId);

		List<ResourceItem> findByGameAndType = resourcesItemService.findByGameAndType(game, resourcesType);

		for (ResourceItem res : findByGameAndType) {
			int id = -1;
			List<ResourceItem> resourceItems = game.getResourceItems();
			System.out.println("size: " + resourceItems.size());
			inner: for (int i = resourceItems.size() - 1; i >= 0; i--) {
				if (resourceItems.get(i).getId().equals(res.getId())) {
//					System.out.println("find");
					id = i;
					break inner;
				}
//				System.out.println("resourceItems.get(i).getId(): " + resourceItems.get(i).getId() + ", res.getId(): "+ res.getId() + ", idForDel: " + id);
			}
			resourceItems.remove(id);
		}

		gameService.update(game);

		for (ResourceItem res : findByGameAndType) {
			storageService.delete(res);
			resourcesItemService.delete(res.getId());
		}
		redirectAttributes.addFlashAttribute("notification", "Deleted all files of " + resourcesType + " category!");
		return "redirect:/upload";
	}

	@GetMapping("/upload/files/{gameId}/{typeName}/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String gameId, @PathVariable String typeName,
			@PathVariable String filename) {
		storageProps.setLocation();
		Resource file = storageService.loadAsResource(gameId, typeName, filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(file);
	}

	@GetMapping("/upload/files/{gameName}/{typeName}/{charName}/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String gameName, @PathVariable String typeName,
			@PathVariable String filename, @PathVariable String charName) {
		return serveFile(gameName, typeName + "/" + charName, filename);
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam(name = "files", required = true) MultipartFile[] files,
			@RequestParam(name = "gameId", required = true) long gameId,
			@RequestParam(name = "type", required = true) String typeName,
			@RequestParam(name = "charName", required = false, defaultValue = "") String charName,
			RedirectAttributes redirectAttributes) {
		Game game = gameService.read(gameId);
		if (!typeName.equalsIgnoreCase(TypeResources.CHARACTER_IMAGES)) {
			storageProps.setLocation(gameId + "", typeName);
			for (MultipartFile file : files) {
				ResourceItem item = new ResourceItem(typeName, file.getOriginalFilename(), null, game);
				storageService.store(file);
				resourcesItemService.create(item);
			}
		} else if (charName != "" || charName != null) {
			storageProps.setLocation(gameId + "", typeName, charName);
			for (MultipartFile file : files) {
				ResourceItem item = new ResourceItem(TypeResources.CHARACTER_IMAGES, file.getOriginalFilename(),
						charName, game);
				storageService.store(file);
				resourcesItemService.create(item);
			}
		}

		redirectAttributes.addFlashAttribute("notification", "You successfully uploaded files!");
		return "redirect:/upload";
	}

	@PostMapping("/uploadNames")
	public String handleFileUploadNames(@RequestParam(name = "gameId", required = true) long gameId,
			@RequestParam(name = "type", required = true) String typeName,
			@RequestParam(name = "names", required = true) String names, RedirectAttributes redirectAttributes) {

		Game game = gameService.read(gameId);
		System.out.println("new names: " + names);
		if (typeName.equalsIgnoreCase(TypeResources.CHARACTER_NAMES)) {
			String[] split = names.split("\n");
			for (int i = 0; i < split.length; i++) {
				String[] nameVal = split[i].split("=");
				if (split[i].contains("="))
					game.getCharNames().put(nameVal[0].trim(), nameVal[1].trim());
			}
			gameService.update(game);
			redirectAttributes.addFlashAttribute("notification", "You successfully uploaded names!");
		}

		return "redirect:/upload";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}

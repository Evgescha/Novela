package com.novelasgame.novelas.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.novelasgame.novelas.entity.DataBase.Game;
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

//		model.addAttribute("files",
//				storageService.loadAll()
//						.map(path -> MvcUriComponentsBuilder
//								.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
//								.build().toUri().toString())
//						.collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/upload/files/{gameName}/{typeName}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String gameName, @PathVariable String typeName,
            @PathVariable String filename) {
        storageProps.setLocation();
        System.out.println("location: "+storageProps.getLocation());
        Resource file = storageService.loadAsResource(gameName, typeName, filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(file);
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam(name = "file", required = true) MultipartFile file,
            @RequestParam(name = "game", required = true) String gameName,
            @RequestParam(name = "type", required = true) String typeName,
            @RequestParam(name = "charName") String charName, RedirectAttributes redirectAttributes) {
        if (!typeName.equalsIgnoreCase("characterImages")) {
            System.out.println("game is: " + gameName);
            System.out.println("type is: " + typeName);
            storageProps.setLocation(gameName, typeName);
            storageService.store(file/** , gameName, typeName **/
            );
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
        } else {
            storageProps.setLocation(gameName, typeName, charName);
            storageService.store(file/* , gameName, typeName */);
        }

        return "redirect:/upload";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}

package com.novelasgame.novelas.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.novelasgame.novelas.entity.DataBase.Comment;
import com.novelasgame.novelas.service.DataBase.CommentService;
import com.novelasgame.novelas.service.DataBase.GameService;
import com.novelasgame.novelas.service.DataBase.UserServiceImpl;

@Controller
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	CommentService commentService;

	@Autowired
	GameService gameService;

	@Autowired
	UserServiceImpl userService;

	@PostMapping
	private String setCommentController(@ModelAttribute("text") String text, Principal principal,
			@ModelAttribute("gameId") long gameId, HttpServletRequest request) {
		System.out.println("start add comment");
		Comment comment = new Comment();
		comment.setText(text);
		commentService.create(comment, userService.findByUsername(principal.getName()), gameService.read(gameId));
		System.out.println("comment: " + comment);

		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
}

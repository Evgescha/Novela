package com.novelasgame.novelas.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novelasgame.novelas.entity.DataBase.Comment;
import com.novelasgame.novelas.entity.DataBase.Role;
import com.novelasgame.novelas.entity.DataBase.User;
import com.novelasgame.novelas.service.DataBase.CommentService;
import com.novelasgame.novelas.service.DataBase.GameService;
import com.novelasgame.novelas.service.DataBase.RoleServiceImpl;
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

	@Autowired
	RoleServiceImpl roleService;

	ObjectMapper mapper = new ObjectMapper();

	
	@ResponseBody
	@GetMapping("/get")
	private Object getCommentWithId(@RequestParam("gameId") long gameId,
			@RequestParam("commentId") long commentId) throws JsonProcessingException {
		List<Comment> findByGame = commentService.findByGame(gameService.read(gameId));
		ArrayList<Comment> list = new ArrayList<Comment>();
		for(Comment comment:findByGame)
			if(comment.getId()>commentId)
				list.add(comment);
		
		return mapper.writeValueAsString(list);
	}
	
	@ResponseBody
	@PostMapping
	private boolean addComment(@ModelAttribute("text") String text, Principal principal,
			@ModelAttribute("gameId") long gameId) {
		Comment comment = new Comment();
		comment.setText(text);
		return  commentService.create(comment, userService.findByUsername(principal.getName()), gameService.read(gameId));
	}

	@ResponseBody
	@PostMapping("/delete")
	private boolean deleteComment(@RequestParam("commentId") long commentId, Principal principal) {

		User user = userService.findByUsername(principal.getName());
		Comment comment = commentService.read(commentId);
		
		for (Role role : user.getRoles())
			if (role.getName().equals("ROLE_ADMIN"))
				return commentService.delete(commentId);
		if (user.getId() == comment.getUser().getId())
			return commentService.delete(commentId);

		return false;
	}
}

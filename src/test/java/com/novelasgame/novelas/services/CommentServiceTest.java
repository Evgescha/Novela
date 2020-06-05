package com.novelasgame.novelas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.novelasgame.novelas.entity.DataBase.Comment;
import com.novelasgame.novelas.service.DataBase.CommentService;

@SpringBootTest
public class CommentServiceTest {

	@Autowired
	CommentService service;
	Comment entity;
	Comment read;

	String str = "test" + System.currentTimeMillis();
	String strEdit = "testEdit" + System.currentTimeMillis();

	@Test
	public void testService() throws Exception {
		entity = new Comment();
		entity.setText(str);
		service.create(entity);

		read = service.findByText(str);
		assertEquals(read.getText(), str);

		read.setText(strEdit);
		service.update(read);
		read = service.read(read.getId());
		assertEquals(read.getText(), strEdit);

		service.delete(read.getId());
		Assertions.assertThrows(Exception.class, () -> {
			service.read(read.getId()).getId();
		});
	}
}

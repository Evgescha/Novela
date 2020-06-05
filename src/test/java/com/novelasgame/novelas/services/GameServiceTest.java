package com.novelasgame.novelas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.novelasgame.novelas.entity.DataBase.Comment;
import com.novelasgame.novelas.entity.DataBase.Game;
import com.novelasgame.novelas.service.DataBase.GameService;

@SpringBootTest
public class GameServiceTest {

	@Autowired
	GameService service;
	Game entity;
	Game read;

	String str = "test" + System.currentTimeMillis();
	String strEdit = "testEdit" + System.currentTimeMillis();

	@Test
	public void testService() throws Exception {
		entity = new Game();
		entity.setTitle(str);
		service.create(entity);

		read = service.findByTitle(str);
		assertEquals(read.getTitle(), str);

		read.setTitle(strEdit);
		service.update(read);
		read = service.read(read.getId());
		assertEquals(read.getTitle(), strEdit);

		service.delete(read.getId());
		Assertions.assertThrows(Exception.class, () -> {
			service.read(read.getId()).getId();
		});
	}
}

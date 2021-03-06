package com.novelasgame.novelas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.novelasgame.novelas.entity.DataBase.Genre;
import com.novelasgame.novelas.service.DataBase.GenreService;

@SpringBootTest
public class GenreServiceTest {

	@Autowired
	GenreService service;
	Genre entity;
	Genre read;

	String str = "test" + System.currentTimeMillis();
	String strEdit = "testEdit" + System.currentTimeMillis();

	@Test
	public void testService() throws Exception {
		entity = new Genre();
		entity.setName(str);
		service.create(entity);

		read = service.findByName(str);
		assertEquals(read.getName(), str);

		read.setName(strEdit);
		service.update(read);
		read = service.read(read.getId());
		assertEquals(read.getName(), strEdit);

		service.delete(read.getId());
		Assertions.assertThrows(Exception.class, () -> {
			service.read(read.getId()).getId();
		});
	}
}

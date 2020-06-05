package com.novelasgame.novelas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.novelasgame.novelas.entity.DataBase.Command;
import com.novelasgame.novelas.service.DataBase.CommandService;

@SpringBootTest
public class CommandServiceTest {

	@Autowired
	CommandService service;
	Command entity;
	Command read;

	String str = "test" + System.currentTimeMillis();
	String strEdit = "testEdit" + System.currentTimeMillis();

	@Test
	public void testService() throws Exception {
		entity = new Command(str);
		service.create(entity);

		read = service.findByValue(str);
		assertEquals(read.getValue(), str);

		read.setValue(strEdit);
		service.update(read);
		read = service.read(read.getId());
		assertEquals(read.getValue(), strEdit);

		service.delete(read.getId());
		Assertions.assertThrows(Exception.class, () -> {
			service.read(read.getId()).getId();
		});
	}
}

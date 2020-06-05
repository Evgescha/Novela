package com.novelasgame.novelas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.novelasgame.novelas.entity.DataBase.User;
import com.novelasgame.novelas.service.DataBase.UserServiceImpl;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserServiceImpl service;
	User entity;
	User read;

	String str = "test" + System.currentTimeMillis();
	String strEdit = "testEdit" + System.currentTimeMillis();

	@Test
	public void testService() throws Exception {
		entity = new User();
		entity.setUsername(str);
		entity.setPassword(str);
		entity.setEmail(str);
		service.userRegistration(entity);

		read = service.findByUsername(str);
		assertEquals(read.getUsername(), str);
		assertEquals(read.getEmail(), str);

		read.setUsername(strEdit);
		read.setEmail(strEdit);
		service.update(read);
		read = service.read(read.getId());
		assertEquals(read.getUsername(), strEdit);
		assertEquals(read.getEmail(), strEdit);

		service.delete(read.getId());
		Assertions.assertThrows(Exception.class, () -> {
			service.read(read.getId()).getId();
		});
	}
}

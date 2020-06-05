package com.novelasgame.novelas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.novelasgame.novelas.entity.DataBase.ResourceItem;
import com.novelasgame.novelas.service.DataBase.ResourcesItemService;

@SpringBootTest
public class ResourceItemServiceTest {

	@Autowired
	ResourcesItemService service;
	ResourceItem entity;
	ResourceItem read;

	String str = "test" + System.currentTimeMillis();
	String strEdit = "testEdit" + System.currentTimeMillis();

	@Test
	public void testService() throws Exception {
		entity = new ResourceItem();
		entity.setFileName(str);
		service.create(entity);

		read = service.findByFileName(str);
		assertEquals(read.getFileName(), str);

		read.setFileName(strEdit);
		service.update(read);
		read = service.read(read.getId());
		assertEquals(read.getFileName(), strEdit);

		service.delete(read.getId());
		Assertions.assertThrows(Exception.class, () -> {
			service.read(read.getId()).getId();
		});
	}
}

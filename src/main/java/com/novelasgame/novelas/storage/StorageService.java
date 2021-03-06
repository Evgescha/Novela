package com.novelasgame.novelas.storage;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.novelasgame.novelas.entity.DataBase.ResourceItem;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	String store(MultipartFile file);
	void store(MultipartFile[] files);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String gameName, String typeName, String filename);

	void deleteAll();
	
	void delete(ResourceItem item);
	
	boolean rename(ResourceItem item, String newName);
}

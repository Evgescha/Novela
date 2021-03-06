package com.novelasgame.novelas.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.novelasgame.novelas.entity.DataBase.ResourceItem;

@Service
public class FileSystemStorageService implements StorageService {

	private Path rootLocation;

	@Autowired
	private StorageProperties properties;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public String store(MultipartFile file) {
		this.rootLocation = Paths.get(this.properties.getLocation());
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory " + filename);
			}
			try (InputStream inputStream = file.getInputStream()) {
				this.rootLocation.toFile().mkdirs();
				Files.copy(inputStream, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
		return this.rootLocation.resolve(filename).toString();
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String gameName, String typeName, String filename) {
		try {
			this.rootLocation = Paths.get(this.properties.getLocation());
			Path file = rootLocation.resolve(gameName).resolve(typeName).resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public void store(MultipartFile[] files) {
		for (MultipartFile file : files)
			store(file);
	}

	@Override
	public boolean rename(ResourceItem item, String newName) {
		this.rootLocation = Paths.get(this.properties.getLocation());
		Path file = rootLocation.resolve(item.getGame().getId().toString()).resolve(item.getType())
				.resolve(item.getFileName());
		if (item.getCharName() != null)
			file = rootLocation.resolve(item.getGame().getId().toString()).resolve(item.getType())
					.resolve(item.getCharName()).resolve(item.getFileName());

		System.out.println(file.toString());
		Path resolve = file.subpath(0, file.getNameCount()-1).resolve(newName);
		
		return file.toFile().renameTo(resolve.toFile());
	}

	@Override
	public void delete(ResourceItem item) {
		Path file = rootLocation.resolve(item.getGame().getId().toString()).resolve(item.getType())
				.resolve(item.getFileName());
		if (item.getCharName() != null)
			file = rootLocation.resolve(item.getGame().getId().toString()).resolve(item.getType())
					.resolve(item.getCharName()).resolve(item.getFileName());
		try {
			FileSystemUtils.deleteRecursively(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

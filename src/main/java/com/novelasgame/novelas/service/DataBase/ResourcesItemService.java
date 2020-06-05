package com.novelasgame.novelas.service.DataBase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novelasgame.novelas.entity.DataBase.Game;
import com.novelasgame.novelas.entity.DataBase.ResourceItem;
import com.novelasgame.novelas.repository.ResourcesItemRepository;

@Service
public class ResourcesItemService implements CrudService<ResourceItem> {

	@Autowired
	ResourcesItemRepository repository;

	public ResourceItem findByFileName(String fileName) {
		return repository.findByFileName(fileName);
	}
	@Override
	public boolean create(ResourceItem entity) {
		try {
			repository.saveAndFlush(entity);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public ResourceItem read(long id) {
		return repository.findById(id).isPresent() ? repository.findById(id).get() : null;
	}

	@Override
	public boolean update(ResourceItem entity) {
		try {
			repository.saveAndFlush(entity);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean delete(long id) {
		try {
			repository.deleteById(id);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public List<ResourceItem> findByGameAndType(Game game, String type) {
		return repository.findByGameAndType(game, type);
	}

	public List<ResourceItem> findByGameAndCharName(Game game, String CharName) {
		return repository.findByGameAndCharName(game, CharName);
	}
}

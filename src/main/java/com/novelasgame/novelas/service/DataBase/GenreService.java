package com.novelasgame.novelas.service.DataBase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novelasgame.novelas.entity.DataBase.Game;
import com.novelasgame.novelas.entity.DataBase.Genre;
import com.novelasgame.novelas.entity.DataBase.Label;
import com.novelasgame.novelas.repository.GenreRepository;

@Service
public class GenreService implements CrudService<Genre> {

    @Autowired
    private GenreRepository repository;

    @Override
    public boolean create(Genre entity) {
        try {
            repository.saveAndFlush(entity);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public Genre read(long id) {
        return repository.findById(id).isPresent() ? repository.findById(id).get() : null;
    }

    @Override
    public boolean update(Genre entity) {
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
    public List<Genre> findAll() {
        return repository.findAll();
    }
    public Genre findByName(String name) {
        return repository.findByName(name);
    }
}

package com.novelasgame.novelas.service.DataBase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novelasgame.novelas.entity.DataBase.Comment;
import com.novelasgame.novelas.entity.DataBase.Game;
import com.novelasgame.novelas.entity.DataBase.User;
import com.novelasgame.novelas.repository.CommentRepository;

@Service
public class CommentService implements CrudService<Comment> {

    @Autowired
    private CommentRepository repository;

    public boolean create(Comment entity, User user, Game game) {
        try {
        	entity.setUser(user);
        	entity.setGame(game);
            create(entity);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    @Override
    public boolean create(Comment entity) {
        try {
            repository.saveAndFlush(entity);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public Comment read(long id) {
        return repository.findById(id).isPresent() ? repository.findById(id).get() : null;
    }

    @Override
    public boolean update(Comment entity) {
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
    public List<Comment> findAll() {
        return repository.findAll();
    }
    
    public List<Comment> findByGame(Game game) {
        return repository.findByGame(game);
    }
}

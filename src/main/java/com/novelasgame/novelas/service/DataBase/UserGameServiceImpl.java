package com.novelasgame.novelas.service.DataBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novelasgame.novelas.entity.DataBase.Game;
import com.novelasgame.novelas.entity.DataBase.Label;
import com.novelasgame.novelas.entity.DataBase.User;
import com.novelasgame.novelas.entity.DataBase.UserGame;
import com.novelasgame.novelas.repository.UserGameRepository;
@Service
public class UserGameServiceImpl implements CrudService<UserGame>{

    @Autowired
    UserGameRepository repository;
    @Autowired
    UserServiceImpl userService;
    
    @Override
    public boolean create(UserGame entity) {
        try {
            repository.saveAndFlush(entity);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
    public boolean update(User user, Long gameId, Long labelId, UserGame entity) {
        	
        	user.getUserGames().add(entity);
        	if(!userService.update(user))return false;
        	
        	entity.setGameId(gameId);
        	entity.setLabelId(labelId);
        	
        	
        	return update(entity);
    }

    @Override
    public UserGame read(long id) {
        return repository.findById(id).get();
    }

    @Override
    public boolean update(UserGame entity) {
        try {
            repository.saveAndFlush(entity);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            repository.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}

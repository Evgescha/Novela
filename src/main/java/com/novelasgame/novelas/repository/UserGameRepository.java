package com.novelasgame.novelas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.novelasgame.novelas.entity.DataBase.UserGame;

public interface UserGameRepository extends JpaRepository<UserGame, Long>{

}

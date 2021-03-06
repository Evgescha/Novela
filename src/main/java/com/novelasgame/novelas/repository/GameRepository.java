package com.novelasgame.novelas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.novelasgame.novelas.entity.DataBase.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    
	public Game findByTitleIgnoreCase(String title);
    
}

package com.novelasgame.novelas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.novelasgame.novelas.entity.DataBase.Game;
import com.novelasgame.novelas.entity.DataBase.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

    public Label findByName(String name);
    
}

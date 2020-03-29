package com.novelasgame.novelas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.novelasgame.novelas.entity.DataBase.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    
}

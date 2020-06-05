package com.novelasgame.novelas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.novelasgame.novelas.entity.DataBase.Command;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {
	public Command findByValue(String value);
}

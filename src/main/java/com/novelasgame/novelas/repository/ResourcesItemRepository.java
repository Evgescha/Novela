package com.novelasgame.novelas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.novelasgame.novelas.entity.DataBase.Game;
import com.novelasgame.novelas.entity.DataBase.ResourceItem;

@Repository
public interface ResourcesItemRepository extends JpaRepository<ResourceItem, Long> {
	
    public List<ResourceItem> findByGameAndType(Game game,String type);
    
    public List<ResourceItem> findByGameAndCharName(Game game,String CharName);

}

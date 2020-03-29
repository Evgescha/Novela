package com.novelasgame.novelas.entity.DataBase;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table
@Entity
public class Genre extends AbstractEntity {
    
    @Column(unique = true)
    private String name;
    
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Collection<User> users = new ArrayList<User>();

	public Genre(String name) {
		super();
		this.name = name;
	}

	public Genre() {
		super();
	}

	@Override
	public String toString() {
		return  name + " ";
	}

    
}

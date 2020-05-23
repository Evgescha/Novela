package com.novelasgame.novelas.entity.DataBase;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.novelasgame.novelas.repository.DropEntity;

import lombok.Data;

@Data
@Table
@Entity
public class Game extends AbstractEntity implements DropEntity{
    
//    @Column(unique = true)
//    private String name;
    
    @Column(unique = true)
    private String title;
    
    @Column
    private int year;

    @Column
    private int duration;
    
    @Column
    private String author;
    
    @Column
    private String language;
    
    @Column(length=1000)
    private String description;
    
    @Column(length=255)
    private String avatar="default.png";

    @ElementCollection
    @Column
    private Map<String,String> charNames = new HashMap<String, String>();
    
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "game_label", joinColumns = { @JoinColumn(name = "gameID") }, inverseJoinColumns = {
            @JoinColumn(name = "labelID") })
    private List<Label> labels;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="game", cascade=CascadeType.ALL)
    private List<ResourceItem> resourceItems;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="game", cascade=CascadeType.ALL)
    private Collection<Comment> comments;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    
    @JsonIgnore
    @Fetch(value = FetchMode.SELECT)
    @ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
    @JoinTable(name = "game_genre",
        joinColumns = @JoinColumn(name = "game_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id"),
        uniqueConstraints = @UniqueConstraint(
                name="games_genres",
                columnNames = {"game_id", "genre_id"})
    )
    private Collection<Genre> genres;
    
    
    @Override
    public String getOwnerUsername() {
        return user.getUsername();
    }


	@Override
	public String toString() {
		return "Game [title=" + title + ", year=" + year + ", duration=" + duration + ", language=" + language
				+ ", description=" + description + ", avatar=" + avatar + ", "//screens=" + Arrays.toString(screens)
				+ ", genres=" + genres + "]";
	}

    

}

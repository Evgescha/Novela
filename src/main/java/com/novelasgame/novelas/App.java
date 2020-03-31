package com.novelasgame.novelas;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.novelasgame.novelas.entity.DataBase.Genre;
import com.novelasgame.novelas.service.DataBase.GenreService;
import com.novelasgame.novelas.storage.StorageProperties;

/**
 * Hello world!
 *
 */
@SpringBootApplication

@EnableConfigurationProperties(StorageProperties.class)
public class App {
    private static GenreService genreService;

    @Autowired
    private GenreService genreService0;

    @PostConstruct
    public void init() {
        this.genreService0 = this.genreService;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

        isGenre();
        
        System.out.println("Hello World!");
                
    }
    private static void isGenre() {
        List<Genre> findAll = genreService.findAll();
        if(findAll==null || findAll.size()<2 ) {
        	String[] arr=new String[] {"Detective", "Drama", "Comedy", "Mysticism", "ScienceFiction", "Parody", "Humdrum", "Adventures", "Romance", "Thriller", "Fantasy", "Fantasy", "Hentai", "Horror", "School", "Ekshn", "Ecchi"};
        	for(String str:arr) {
        		genreService.create(new Genre(str));
        	}
        }
    }
    
}

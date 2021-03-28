package com.example.demo.Database.Comics;

import com.example.demo.Database.Creators.Creators;
import com.example.demo.MarvelApi.Comics.Entities.Comic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ComicsService {
    private final ComicsRepository comicsRepository;

    @Autowired
    public ComicsService(ComicsRepository comicsRepository){
        this.comicsRepository = comicsRepository;
    }

    public Comics getComicCreateIfNotExists(Comic comic){
        Optional<Comics> optionalComics = comicsRepository.findByApiId(comic.getId());
        if(optionalComics.isPresent()){
            return updateComic(optionalComics.get().getId(), comic.getTitle());
        }else{
            return addNewComic(comic);
        }
    }

    public List<Comics> getComics(){
        return comicsRepository.findAll();
    }

    public Comics addNewComic(Comic comic){
        Comics comics = new Comics(comic.getId(), comic.getTitle());
        comicsRepository.save(comics);
        return comics;
    }

    public Comics updateComic(Long comicId, String title){
        Comics comic = comicsRepository.findById(comicId)
                .orElseThrow(() -> new IllegalStateException("comic with id " + comicId + " does not exists"));

        if(title != null && title.length() > 0 && !Objects.equals(comic.getTitle(), title)){
            comic.setTitle(title);
        }

        return comic;
    }
}

package com.example.demo.Database.Comics;

import com.example.demo.MarvelApi.Comics.Entities.APIComic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ComicsService {
    private final ComicsRepository comicsRepository;
    private final ComicsCharactersRepository comicsCharactersRepository;

    @Autowired
    public ComicsService(ComicsRepository comicsRepository, ComicsCharactersRepository comicsCharactersRepository){
        this.comicsRepository = comicsRepository;
        this.comicsCharactersRepository = comicsCharactersRepository;
    }

    public boolean isExistsByApiId(Integer comidId){
        Optional<Comics> optionalComics = comicsRepository.findByApiId(comidId);
        return optionalComics.isPresent();
    }

    public Comics getComicCreateIfNotExists(APIComic comic){
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

    public Comics addNewComic(APIComic comic){
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

    public void addComicCharacter(ComicsCharacters comicsCharacters){
        comicsCharactersRepository.save(comicsCharacters);
    }
}

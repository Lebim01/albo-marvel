package com.example.demo.Database.Creators;

import com.example.demo.Database.Comics.ComicsCharacters;
import com.example.demo.Database.Comics.ComicsCreators;
import com.example.demo.Database.Comics.ComicsCreatorsRepository;
import com.example.demo.MarvelApi.Creators.Entities.APICreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CreatorsService {
    private final CreatorsRepository creatorsRepository;
    private final ComicsCreatorsRepository comicsCreatorsRepository;

    @Autowired
    public CreatorsService(CreatorsRepository creatorsRepository, ComicsCreatorsRepository comicsCreatorsRepository) {
        this.creatorsRepository = creatorsRepository;
        this.comicsCreatorsRepository = comicsCreatorsRepository;
    }

    public List<Creators> getCreators(){
        return creatorsRepository.findAll();
    }

    public Creators getCreatorCreateIfNotExists(APICreator creator){
        Optional<Creators> optionalCreators = creatorsRepository.findByApiId(creator.getId());
        if(optionalCreators.isPresent()){
            return updateCreator(optionalCreators.get().getId(), creator.getFullName());
        }else{
            return addNewCreator(creator);
        }
    }

    public Creators addNewCreator(APICreator creator){
        Optional<Creators> creatorsOptional = creatorsRepository.findByApiId(creator.getId());
        if(creatorsOptional.isPresent()){
            throw new IllegalStateException("creator does exists");
        }
        Creators creators = new Creators(creator.getId(), creator.getFullName());
        creatorsRepository.save(creators);
        return creators;
    }

    public Creators updateCreator(Long creatorId, String name){
        Creators creator = creatorsRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalStateException("creator with id " + creatorId + " does not exists"));

        if(name != null && name.length() > 0 && !Objects.equals(creator.getName(), name)){
            creator.setName(name);
        }

        return creator;
    }

    public void addComicCreator(ComicsCreators comicsCreators){
        Optional<ComicsCreators> optionalComicsCreators = comicsCreatorsRepository.findComicCreator(comicsCreators.getComic().getId(), comicsCreators.getCreator().getId());
        if(!optionalComicsCreators.isPresent()) {
            comicsCreatorsRepository.save(comicsCreators);
        }
    }
}

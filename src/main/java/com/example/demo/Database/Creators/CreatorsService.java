package com.example.demo.Database.Creators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreatorsService {
    private final CreatorsRepository creatorsRepository;

    @Autowired
    public CreatorsService(CreatorsRepository creatorsRepository) {
        this.creatorsRepository = creatorsRepository;
    }

    public List<Creators> getCreators(){
        return creatorsRepository.findAll();
    }

    public void addNewCreator(Creators creator){
        Optional<Creators> creatorsOptional = creatorsRepository.findById(creator.getId());
        if(creatorsOptional.isPresent()){
            throw new IllegalStateException("creator does exists");
        }
        creatorsRepository.save(creator);
    }

    public void deleteCreator(Long creatorId){
        if(!creatorsRepository.existsById(creatorId)){
            throw new IllegalStateException("creator with id "+ creatorId + " does not exists");
        }
        creatorsRepository.deleteById(creatorId);
    }
}

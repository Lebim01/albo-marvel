package com.example.demo.Database.Characters;

import com.example.demo.Database.Comics.Comics;
import com.example.demo.Database.Comics.ComicsRepository;
import com.example.demo.Database.Creators.Creators;
import com.example.demo.Database.Creators.CreatorsRepository;
import com.example.demo.MarvelApi.Characters.Entities.Character;
import com.example.demo.MarvelApi.Comics.Entities.Comic;
import com.example.demo.Rest.Characters.CharacterComics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CharactersService {
    private final CharactersRepository charactersRepository;
    private final ComicsRepository comicsRepository;
    private final CreatorsRepository creatorsRepository;

    @Autowired
    public CharactersService(CharactersRepository charactersRepository, ComicsRepository comicsRepository, CreatorsRepository creatorsRepository){
        this.charactersRepository = charactersRepository;
        this.comicsRepository = comicsRepository;
        this.creatorsRepository = creatorsRepository;
    }

    public Optional<Characters> getCharacterByName(String name){
        return charactersRepository.findByName(name);
    }

    public Optional<Characters> getCharacter(Integer id){
        return charactersRepository.findByApiId(id);
    }

    public List<Characters> getCharacters(){
        return charactersRepository.findAll();
    }

    public Characters getCharacterCreateIfNotExists(Character character){
        Optional<Characters> optionalCharacters = charactersRepository.findByApiId(character.getId());
        if(optionalCharacters.isPresent()){
            return updateCharacter(optionalCharacters.get().getId(), character.getName(), LocalDateTime.now());
        }else{
            return addNewCharacter(character);
        }
    }

    public Characters addNewCharacter(Character character){
        Characters characters = new Characters(character.getId(), character.getName(), character.getName());
        charactersRepository.save(characters);
        return characters;
    }

    public void deleteCharacter(Long characterId){
        if(!charactersRepository.existsById(characterId)){
            throw new IllegalStateException("character with id "+ characterId + " does not exists");
        }
        charactersRepository.deleteById(characterId);
    }

    @Transactional
    public Characters updateCharacter(Long characterId, String name, LocalDateTime last_sync){
        Characters character = charactersRepository.findById(characterId)
                .orElseThrow(() -> new IllegalStateException("character with id " + characterId + " does not exists"));

        if(name != null && name.length() > 0 && !Objects.equals(character.getName(), name)){
            character.setName(name);
        }

        if(last_sync != null && !Objects.equals(character.getLast_sync(), last_sync)){
            character.setLast_sync(last_sync);
        }

        return character;
    }

    public List<String> getWriters(Long characterId){
        List<String> writers = new ArrayList<>();

        Optional<Characters> optionalCharacters = charactersRepository.findById(characterId);
        if(!optionalCharacters.isPresent()){
            throw new IllegalStateException("Character does not exists");
        }

        Characters character = optionalCharacters.get();
        List<Comics> comics = comicsRepository.findByCharacterId(character.getId());
        for(Comics comic: comics){
            List<Creators> _writers = creatorsRepository.findWritersByComicId(comic.getId());
            for(Creators creator: _writers){
                if(!writers.contains(creator.getName()))
                    writers.add(creator.getName());
            }
        }

        return writers;
    }

    public List<String> getColorists(Long characterId){
        List<String> colorists = new ArrayList<>();

        Optional<Characters> optionalCharacters = charactersRepository.findById(characterId);
        if(!optionalCharacters.isPresent()){
            throw new IllegalStateException("Character does not exists");
        }

        Characters character = optionalCharacters.get();
        List<Comics> comics = comicsRepository.findByCharacterId(character.getId());
        for(Comics comic: comics){
            List<Creators> _colorists = creatorsRepository.findColoristsByComicId(comic.getId());
            for(Creators creator: _colorists){
                if(!colorists.contains(creator.getName()))
                    colorists.add(creator.getName());
            }
        }

        return colorists;
    }

    public List<String> getEditors(Long characterId){
        List<String> editors = new ArrayList<>();

        Optional<Characters> optionalCharacters = charactersRepository.findById(characterId);
        if(!optionalCharacters.isPresent()){
            throw new IllegalStateException("Character does not exists");
        }

        Characters character = optionalCharacters.get();
        List<Comics> comics = comicsRepository.findByCharacterId(character.getId());
        for(Comics comic: comics){
            List<Creators> _editors = creatorsRepository.findEditorsByComicId(comic.getId());
            for(Creators creator: _editors){
                if(!editors.contains(creator.getName()))
                    editors.add(creator.getName());
            }
        }

        return editors;
    }

    public List<CharacterComics> getCharactersRelated(Long characterId){
        List<CharacterComics> characters = new ArrayList<>();

        Optional<Characters> optionalCharacters = charactersRepository.findById(characterId);
        if(!optionalCharacters.isPresent()){
            throw new IllegalStateException("Character does not exists");
        }

        Characters character = optionalCharacters.get();

        List<Characters> _characters = charactersRepository.findCharactersComicRelated(character.getId());
        for(Characters _character: _characters){
            CharacterComics characterComics = new CharacterComics(_character.getName());
            List<Comics> comics = comicsRepository.findByCharacterId(_character.getId());
            for(Comics comic: comics){
                characterComics.addComic(comic.getTitle());
            }

            characters.add(characterComics);
        }

        return characters;
    }
}

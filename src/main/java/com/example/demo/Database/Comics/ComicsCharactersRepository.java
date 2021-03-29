package com.example.demo.Database.Comics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComicsCharactersRepository extends JpaRepository<ComicsCharacters, Long> {

    @Query(value = "SELECT cc.* FROM comics_characters cc WHERE comics_id = ?1 AND characters_id = ?2", nativeQuery = true)
    Optional<ComicsCharacters> findComicCharacter(Long comic_id, Long character_id);

}

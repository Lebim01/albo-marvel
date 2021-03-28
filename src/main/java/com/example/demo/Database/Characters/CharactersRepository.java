package com.example.demo.Database.Characters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharactersRepository extends JpaRepository<Characters, Long> {

    @Query("SELECT c FROM Characters c WHERE name = ?1")
    Optional<Characters> findByName(String name);

    @Query("SELECT c FROM Characters c WHERE api_id = ?1")
    Optional<Characters> findByApiId(Integer api_id);

    @Query(value =
            "SELECT characters.* " +
            "FROM `comics_characters` cc " +
            "INNER JOIN comics ON cc.comics_id = comics.id " +
            "INNER JOIN comics_characters cc2 ON cc2.comics_id = comics.id " +
            "INNER JOIN characters ON characters.id = cc2.characters_id " +
            "WHERE cc.characters_id = ?1 " +
            "GROUP BY id " +
            "HAVING id != ?1", nativeQuery = true)
    List<Characters> findCharactersComicRelated(Long characterId);

}

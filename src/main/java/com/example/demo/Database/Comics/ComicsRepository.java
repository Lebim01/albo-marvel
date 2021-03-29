package com.example.demo.Database.Comics;

import com.example.demo.Database.Creators.Creators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComicsRepository extends JpaRepository<Comics, Long> {

    @Query("SELECT co FROM Comics co WHERE api_id = ?1")
    Optional<Comics> findByApiId(Integer comicId);

    @Query(value = "SELECT co.* FROM Comics co INNER JOIN comics_characters cc ON cc.characters_id = ?1 AND co.id = cc.comics_id", nativeQuery = true)
    List<Comics> findByCharacterId(Long characterId);

    @Query(value = "SELECT co.* FROM Comics co INNER JOIN comics_characters cc ON cc.characters_id = ?1 AND co.id = cc.comics_id WHERE modified IS NOT NULL ORDER BY modified DESC LIMIT 1", nativeQuery = true)
    Optional<Comics> getLastByCharacter(Long characterId);

}

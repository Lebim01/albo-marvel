package com.example.demo.Database.Creators;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreatorsRepository extends JpaRepository<Creators, Long> {

    @Query(value = "SELECT c FROM Creators c INNER JOIN comics_creators cc ON cc.comics_id = ?1 AND cc.creators_id = c.id WHERE cc.role = 'writer'", nativeQuery = true)
    List<Creators> getWritersComic(Long comicId);

}

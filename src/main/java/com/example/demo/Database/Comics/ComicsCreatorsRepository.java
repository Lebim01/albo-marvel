package com.example.demo.Database.Comics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComicsCreatorsRepository extends JpaRepository<ComicsCreators, Long> {

    @Query(value = "SELECT cc.* FROM comics_creators cc WHERE comics_id = ?1 AND creators_id = ?2", nativeQuery = true)
    Optional<ComicsCreators> findComicCreator(Long comic_id, Long creator_id);

}

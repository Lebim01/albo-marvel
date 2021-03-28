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


}

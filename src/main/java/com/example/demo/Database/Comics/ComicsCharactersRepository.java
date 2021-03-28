package com.example.demo.Database.Comics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicsCharactersRepository extends JpaRepository<ComicsCharacters, Long> {

}

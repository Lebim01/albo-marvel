package com.example.demo.Database.Comics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicsCreatorsRepository extends JpaRepository<ComicsCreators, Long> {


}

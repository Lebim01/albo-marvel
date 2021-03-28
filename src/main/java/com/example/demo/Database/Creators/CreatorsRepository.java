package com.example.demo.Database.Creators;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreatorsRepository extends JpaRepository<Creators, Long> {

    @Query("SELECT co FROM Creators co WHERE api_id = ?1")
    Optional<Creators> findByApiId(Integer creatorId);

    @Query(value = "SELECT cr.* FROM Creators cr INNER JOIN comics_creators cc ON cc.comics_id = ?1 AND cr.id = cc.creators_id AND cc.role = 'writer' GROUP BY cr.id", nativeQuery = true)
    List<Creators> findWritersByComicId(Long comicId);

    @Query(value = "SELECT cr.* FROM Creators cr INNER JOIN comics_creators cc ON cc.comics_id = ?1 AND cr.id = cc.creators_id AND cc.role = 'colorist' GROUP BY cr.id", nativeQuery = true)
    List<Creators> findColoristsByComicId(Long comicId);

    @Query(value = "SELECT cr.* FROM Creators cr INNER JOIN comics_creators cc ON cc.comics_id = ?1 AND cr.id = cc.creators_id AND cc.role = 'editor' GROUP BY cr.id", nativeQuery = true)
    List<Creators> findEditorsByComicId(Long comicId);

}

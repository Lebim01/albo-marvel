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

    @Query(value = "SELECT cr FROM Creators cr INNER JOIN comics_creators cc ON cc.comics_id = ?1 AND cr.id = cc.creators_id AND cc.role = 'writer'", nativeQuery = true)
    List<Creators> findWritersByComicId(Long comicId);

    @Query(value = "SELECT cr FROM Creators cr INNER JOIN comics_creators cc ON cc.comics_id = ?1 AND cr.id = cc.creators_id AND cc.role = 'colorist'", nativeQuery = true)
    List<Creators> findColoristsByComicId(Long comicId);

    @Query(value = "SELECT cr FROM Creators cr INNER JOIN comics_creators cc ON cc.comics_id = ?1 AND cr.id = cc.creators_id AND cc.role = 'editor'", nativeQuery = true)
    List<Creators> findEditorsByComicId(Long comicId);

}

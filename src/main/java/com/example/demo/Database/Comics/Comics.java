package com.example.demo.Database.Comics;

import com.example.demo.Database.Characters.Characters;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="comics")
public class Comics {
    @Id
    @SequenceGenerator(name="comics_sequence", sequenceName="comics_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comics_sequence")
    private Long id;

    private Integer api_id;
    private String title;

    @ManyToMany
    private List<Characters> characters;

    @OneToMany(mappedBy = "comic")
    private List<ComicsCreators> comicsCreators;

    public Comics() {
    }

    public Comics(Long id, Integer api_id, String title) {
        this.id = id;
        this.api_id = api_id;
        this.title = title;
    }

    public Comics(Integer api_id, String title) {
        this.api_id = api_id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getApi_id() {
        return api_id;
    }

    public void setApi_id(Integer api_id) {
        this.api_id = api_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Characters> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Characters> characters) {
        this.characters = characters;
    }

    public List<ComicsCreators> getComicsCreators() {
        return comicsCreators;
    }

    public void setComicsCreators(List<ComicsCreators> comicsCreators) {
        this.comicsCreators = comicsCreators;
    }
}

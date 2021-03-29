package com.example.demo.Database.Comics;

import com.example.demo.Database.Characters.Characters;

import javax.persistence.*;

@Entity
@Table(name="comics_characters", indexes = {
        @Index(unique = true, name = "idx_id_comic_character", columnList = "comics_id, characters_id")
})
public class ComicsCharacters {
    @Id
    @SequenceGenerator(name="comics_characters_sequence", sequenceName="comics_characters_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comics_characters_sequence")
    Long id;

    @ManyToOne
    @JoinColumn(name = "comics_id")
    Comics comic;

    @ManyToOne
    @JoinColumn(name = "characters_id")
    Characters character;

    public ComicsCharacters() {
    }

    public ComicsCharacters(Long id, Comics comic, Characters character) {
        this.id = id;
        this.comic = comic;
        this.character = character;
    }

    public ComicsCharacters(Comics comic, Characters character) {
        this.comic = comic;
        this.character = character;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Comics getComic() {
        return comic;
    }

    public void setComic(Comics comic) {
        this.comic = comic;
    }

    public Characters getCharacter() {
        return character;
    }

    public void setCharacter(Characters character) {
        this.character = character;
    }
}

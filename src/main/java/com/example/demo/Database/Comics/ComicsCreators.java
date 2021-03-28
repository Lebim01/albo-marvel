package com.example.demo.Database.Comics;

import com.example.demo.Database.Characters.Characters;
import com.example.demo.Database.Creators.Creators;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
class ComicsCreatorsKey implements Serializable {

    @Column(name = "comics_id")
    Long comicsId;

    @Column(name = "creators_id")
    Long creatorsId;

    public ComicsCreatorsKey() {
    }

    public ComicsCreatorsKey(Long comicsId, Long creatorsId) {
        this.comicsId = comicsId;
        this.creatorsId = creatorsId;
    }

    public Long getComicsId() {
        return comicsId;
    }

    public void setComicsId(Long comicsId) {
        this.comicsId = comicsId;
    }

    public Long getCreatorsId() {
        return creatorsId;
    }

    public void setCreatorsId(Long creatorsId) {
        this.creatorsId = creatorsId;
    }
}

@Entity
@Table(name="comics_creators")
public class ComicsCreators {

    @EmbeddedId
    ComicsCreatorsKey id;

    @ManyToOne
    @MapsId("comicsId")
    @JoinColumn(name = "comics_id")
    Comics comic;

    @ManyToOne
    @MapsId("creatorsId")
    @JoinColumn(name = "creators_id")
    Creators creator;

    String role;

    public ComicsCreators() {
    }

    public ComicsCreators(ComicsCreatorsKey id, Comics comic, Creators creator, String role) {
        this.id = id;
        this.comic = comic;
        this.creator = creator;
        this.role = role;
    }

    public ComicsCreators(Comics comic, Creators creator, String role) {
        this.comic = comic;
        this.creator = creator;
        this.role = role;
    }

    public ComicsCreatorsKey getId() {
        return id;
    }

    public void setId(ComicsCreatorsKey id) {
        this.id = id;
    }

    public Comics getComic() {
        return comic;
    }

    public void setComic(Comics comic) {
        this.comic = comic;
    }

    public Creators getCreator() {
        return creator;
    }

    public void setCreator(Creators creator) {
        this.creator = creator;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

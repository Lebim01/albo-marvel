package com.example.demo.Database.Comics;

import com.example.demo.Database.Creators.Creators;

import javax.persistence.*;

@Entity
@Table(name="comics_creators")
public class ComicsCreators {

    @Id
    @SequenceGenerator(name="comics_creators_sequence", sequenceName="comics_creators_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comics_creators_sequence")
    Long id;

    @ManyToOne
    @JoinColumn(name = "comics_id")
    Comics comic;

    @ManyToOne
    @JoinColumn(name = "creators_id")
    Creators creator;

    String role;

    public ComicsCreators() {
    }

    public ComicsCreators(Long id, Comics comic, Creators creator, String role) {
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

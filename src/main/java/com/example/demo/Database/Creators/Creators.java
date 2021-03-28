package com.example.demo.Database.Creators;

import com.example.demo.Database.Comics.ComicsCreators;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="creators")
public class Creators {
    @Id
    @SequenceGenerator(name="creators_sequence", sequenceName="creators_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "creators_sequence")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "creator")
    private List<ComicsCreators> comicsCreators;

    public Creators() {
    }

    public Creators(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Creators(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

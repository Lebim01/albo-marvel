package com.example.demo.Database.Characters;

import com.example.demo.Database.Comics.Comics;
import com.example.demo.Database.Comics.ComicsCharacters;
import com.example.demo.Database.Comics.ComicsCreators;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="characters")
public class Characters {
    @Id
    @SequenceGenerator(name="characters_sequence", sequenceName="characters_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "characters_sequence")
    private Long id;

    private Integer api_id;
    private String name;
    private String short_name;
    private LocalDateTime last_sync;

    @OneToMany(mappedBy = "character")
    private List<ComicsCharacters> comicsCharacters;

    public Characters() {
    }

    public Characters(Long id, Integer api_id, String name, String short_name, LocalDateTime last_sync) {
        this.id = id;
        this.api_id = api_id;
        this.name = name;
        this.short_name = short_name;
        this.last_sync = last_sync;
    }

    public Characters(Integer api_id, String name, String short_name) {
        this.api_id = api_id;
        this.name = name;
        this.short_name = short_name;
        this.last_sync = LocalDateTime.now();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public LocalDateTime getLast_sync() {
        return last_sync;
    }

    public void setLast_sync(LocalDateTime last_sync) {
        this.last_sync = last_sync;
    }

    @Override
    public String toString() {
        return "Characters{" +
                "id=" + id +
                ", api_id=" + api_id +
                ", name='" + name + '\'' +
                ", short_name='" + short_name + '\'' +
                ", last_sync=" + last_sync +
                '}';
    }
}

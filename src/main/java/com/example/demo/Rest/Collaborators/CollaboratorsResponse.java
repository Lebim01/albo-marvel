package com.example.demo.Rest.Collaborators;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CollaboratorsResponse {

    private String character;
    private LocalDateTime last_sync;
    private List<String> editors;
    private List<String> writers;
    private List<String> colorists;

    public CollaboratorsResponse(){
        this.last_sync = LocalDateTime.now();
        this.editors = new ArrayList<>();
        this.writers = new ArrayList<>();
        this.colorists = new ArrayList<>();
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getLast_sync() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return last_sync.format(formatter);
    }

    public void setLast_sync(LocalDateTime last_sync) {
        this.last_sync = last_sync;
    }


}

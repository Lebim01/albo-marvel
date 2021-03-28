package com.example.demo.Database.Characters;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CharactersConfig {
    @Bean
    CommandLineRunner commandLineRunner(CharactersRepository repository){
        return args -> {
            Characters ironman = new Characters(
                    1009368,
                    "Iron Man",
                    "ironman"
            );
            Characters capamerica = new Characters(
                    1009220,
                    "Captain America",
                    "capamerica"
            );

            repository.saveAll(List.of(ironman, capamerica));
        };
    }
}

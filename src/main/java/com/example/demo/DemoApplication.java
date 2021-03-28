package com.example.demo;

import com.example.demo.Database.Characters.CharactersService;
import com.example.demo.Database.Comics.ComicsService;
import com.example.demo.Database.Creators.CreatorsService;
import com.example.demo.Rest.SyncCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

@Configuration
@Component
@EnableScheduling
@ConditionalOnProperty(name="scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration {

	private CharactersService charactersService;
	private ComicsService comicsService;
	private CreatorsService creatorsService;

	@Autowired
	SchedulingConfiguration(CharactersService charactersService, ComicsService comicsService, CreatorsService creatorsService){
		this.charactersService = charactersService;
		this.comicsService = comicsService;
		this.creatorsService = creatorsService;
	}

	/**
	 * Exec sync job every day at 01:00 am
	 */
	@Scheduled(cron = "0 */5 * * * *")
	void syncCharacters(){
		System.out.println("Executing cron");
		SyncCharacter syncCharacter = new SyncCharacter(charactersService, comicsService, creatorsService);
		syncCharacter.syncAll();
	}

}
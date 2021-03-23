package com.texoit.worstfilms;

import com.texoit.worstfilms.service.FilmService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackages = "com.texoit.worstfilms.domain")
public class WorstfilmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorstfilmsApplication.class, args);
	}


	@Bean(initMethod="importFilmCSV")
	public FilmService initializeImport() {
		return new FilmService();
	}

}

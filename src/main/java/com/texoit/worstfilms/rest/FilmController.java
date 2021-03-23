package com.texoit.worstfilms.rest;

import com.texoit.worstfilms.domain.Film;
import com.texoit.worstfilms.service.FilmService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/texoit/v1/films", produces = "application/json")
public class FilmController {

    static Logger LOGGER = Logger.getLogger(FilmController.class);

    @Autowired
    private FilmService filmService;

    /**
     *
     * @return all imported films
     */
    @GetMapping
    public HttpEntity all() {
        List<Film> films = filmService.findAll();
        if(films != null){
            return ResponseEntity.ok(films);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/find/winners/interval/min/max")
    public HttpEntity findWinnersIntervallMinMax() {
        ProducerResource producers = filmService.findWinnersIntervallMinMax();
        if(producers != null){
            return ResponseEntity.ok(producers);
        }else{
            return ResponseEntity.noContent().build();
        }
    }


}

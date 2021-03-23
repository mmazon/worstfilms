package com.texoit.worstfilms.service;

import com.texoit.worstfilms.WorstfilmsApplication;
import com.texoit.worstfilms.domain.Film;
import com.texoit.worstfilms.dto.FilmCsvDTO;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WorstfilmsApplication.class)
@Transactional
public class FilmServiceIntegrationTest {


    @Autowired
    FilmService filmService;

    private List<FilmCsvDTO> dtos = new ArrayList<>();

    @Before
    public void init() {
        this.dtos = filmService.readCsvFile();
    }

    @Test
    public void testSizeOfImportedData() {
        List<Film> filmsFromDataBase = filmService.findAll();
        Assertions.assertThat(filmsFromDataBase.size()).isEqualTo(dtos.size());
    }

    @Test
    public void testSomeImportedData() {
        List<Film> filmsFromDataBase = filmService.findAll();
        int index = 0;
        if(filmsFromDataBase != null && filmsFromDataBase.size() > 0)
            index = filmsFromDataBase.size() - 1;

        Assertions.assertThat(filmsFromDataBase.get(index).getProducers()).isEqualTo(dtos.get(index).getProducers());
        Assertions.assertThat(filmsFromDataBase.get(index).getYear()).isEqualTo(dtos.get(index).getYear());
    }

}

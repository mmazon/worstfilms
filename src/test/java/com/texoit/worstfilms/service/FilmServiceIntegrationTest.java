package com.texoit.worstfilms.service;

import com.texoit.worstfilms.WorstfilmsApplication;
import com.texoit.worstfilms.domain.Film;
import com.texoit.worstfilms.dto.FilmCsvDTO;
import com.texoit.worstfilms.dto.ProducerDTO;
import com.texoit.worstfilms.rest.ProducerResource;
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


    /**
     * Teste passará se executado com arquivo disponibilizado com o projeto
     */
    @Test
    public void testFinalImportedData() {
        ProducerDTO dtoMin = ProducerDTO.builder().producer("Joel Silver").interval(1).followingWin(1991).previousWin(1990).build();
        ProducerDTO dtoMax = ProducerDTO.builder().producer("Matthew Vaughn").interval(13).followingWin(2015).previousWin(2002).build();

        ProducerResource producerResourceFromDataBase = filmService.findWinnersIntervallMinMax();

        Assertions.assertThat(producerResourceFromDataBase.getMin().get(0).getProducer()).isEqualTo(dtoMin.getProducer());
        Assertions.assertThat(producerResourceFromDataBase.getMax().get(0).getProducer()).isEqualTo(dtoMax.getProducer());
    }

}

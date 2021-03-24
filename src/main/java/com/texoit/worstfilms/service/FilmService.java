package com.texoit.worstfilms.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.texoit.worstfilms.domain.Film;
import com.texoit.worstfilms.dto.FilmCsvDTO;
import com.texoit.worstfilms.dto.ProducerDTO;
import com.texoit.worstfilms.repository.FilmRepository;
import com.texoit.worstfilms.rest.ProducerResource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class FilmService {

    static Logger LOGGER = Logger.getLogger(FilmService.class);

    public static final String CSV_FILE = "movielist.csv";

    @Autowired
    private FilmRepository filmRepository;

    public void importFilmCSV(){
        filmRepository.saveAll(parseCsvDtoFileToFilm());
    }

    public List<FilmCsvDTO> readCsvFile() {
        try {
            Resource resource = new ClassPathResource(CSV_FILE);
            return new CsvToBeanBuilder<FilmCsvDTO>(new FileReader(resource.getFile())).withType(FilmCsvDTO.class).
                    withIgnoreLeadingWhiteSpace(true).withSeparator(';').build().parse();
        }catch (IOException ex){
            LOGGER.error(ex.getMessage());
        }
        return null;
    }

    private List<Film> parseCsvDtoFileToFilm() {
        final List<FilmCsvDTO> csvDto = readCsvFile();
        List<Film> films = new ArrayList();
        if(csvDto != null){
            csvDto.stream().forEach((dto) -> films.add(Film.builder().
                    year(dto.getYear()).
                    title(dto.getTitle()).
                    studios(dto.getStudios()).
                    producers(dto.getProducers()).
                    isWinner(dto.isWinner()).
                    build()));
        }
        return films;
    }

    public List<Film> findAll(){
        return filmRepository.findAll();
    }



    public ProducerResource findWinnersIntervallMinMax(){
        List<Film> winnersList = filmRepository.findAllByIsWinnerOrderByYearAsc(true);
        Map<String, List<Integer>> producerMapGrouped = new HashMap<>();

        //percorro os filmes ganhadores
        for(Film film : winnersList){

            //vejo se há mais de um produtor no filme
            String[] splittedProducers = film.getProducers().split(",\\s*|\\band\\bs*");

            //percorro os produtores e separo eles com o ano em que ganharam o premio
            for(String producer : splittedProducers){

                //se ja existe adiciona na lista de anos vencedores do produtor, caso nao, apenas cria a lista de anos para o produtor
                if(producerMapGrouped.containsKey(producer.trim())){
                    List<Integer> yearValueList = producerMapGrouped.get(producer.trim());
                    yearValueList.add(film.getYear());
                    producerMapGrouped.put(producer.trim(), yearValueList);
                }else{
                    List<Integer> yearList = new ArrayList<>();
                    yearList.add(film.getYear());
                    producerMapGrouped.put(producer.trim(), yearList);
                }
            }
        }

        ProducerResource resourceProducer = new ProducerResource();
        resourceProducer.setMin(retrieveCalculatedDataProducer(producerMapGrouped, false));
        resourceProducer.setMax(retrieveCalculatedDataProducer(producerMapGrouped, true));

        return resourceProducer;
    }

    private List<ProducerDTO> retrieveCalculatedDataProducer(Map<String, List<Integer>> producerMapGrouped, Boolean isMax){
        int interval = 1;
        List<ProducerDTO> producerDtoList = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : producerMapGrouped.entrySet()) {
            List<Integer> winnerYears = entry.getValue();
            //verifico apenas os que ganharam mais de uma vez
            if(winnerYears != null && winnerYears.size() > 1){
                int currentYear = 0;
                int currentInterval = 0;
                for(Integer year : winnerYears){
                    //primeiro ano
                    if(currentYear == 0){
                        currentYear = year;
                        continue;
                    }

                    currentInterval = year - currentYear;

                    if(handleCalculateInterval(currentInterval, interval, isMax)) {
                        ProducerDTO producerDto = new ProducerDTO();
                        interval = currentInterval;
                        producerDto.setProducer(entry.getKey());
                        producerDto.setInterval(interval);
                        producerDto.setPreviousWin(currentYear);
                        producerDto.setFollowingWin(year);
                        producerDtoList.add(producerDto);
                    }
                    currentYear = year;
                }
                interval = 1;
            }
        }

        return handleFinalData(producerDtoList, isMax);
    }

    private Boolean handleCalculateInterval(int currentInterval, int interval, Boolean isMax){
        if(isMax)
            return currentInterval >= interval;
        else
            return currentInterval <= interval;
    }

    private List<ProducerDTO> handleFinalData(List<ProducerDTO> producerDtoList, Boolean isMax) {
        List<ProducerDTO> producerMax = new ArrayList<>();
        if(producerDtoList != null && producerDtoList.size() > 0) {
            List<ProducerDTO> producersSorted;
            //orderno por interval para pegar o menor valor
            if(isMax)
                producerDtoList.sort(Comparator.comparingInt(ProducerDTO::getInterval).reversed());
            else
                producerDtoList.sort(Comparator.comparingInt(ProducerDTO::getInterval));
            int first = producerDtoList.get(0).getInterval();
            //deixo apenas os intervals com menor valores
            producersSorted = producerDtoList.stream().filter(p -> p.getInterval() == first).collect(Collectors.toList());
            Set<String> nameSet = new HashSet<>();
            //removo producers repetidos com o mesmo interval
            producerMax = producersSorted.stream().filter(e -> nameSet.add(e.getProducer())).collect(Collectors.toList());
            //ordeno pelo menor ano vencedor
            producerMax.sort(Comparator.comparingInt(ProducerDTO::getPreviousWin));
        }
        return producerMax;
    }
}

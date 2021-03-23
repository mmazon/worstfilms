package com.texoit.worstfilms.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmCsvDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @CsvBindByName
    private Integer year;

    @CsvBindByName
    private String title;

    @CsvBindByName
    private String studios;

    @CsvBindByName
    private String producers;

    @CsvBindByName
    private String winner;

    public Boolean isWinner(){
        return (this.getWinner() != null && !this.getWinner().equals("") && this.getWinner().equals("yes"));
    }

}

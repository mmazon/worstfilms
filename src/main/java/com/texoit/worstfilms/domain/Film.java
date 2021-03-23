package com.texoit.worstfilms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FILM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "cdFilm")
public class Film  implements Serializable {

    @Id
    @Column(name = "CD_FILM", length = 20, unique = true, nullable = false)
    @SequenceGenerator(name = "film_seq", sequenceName = "film_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "film_seq")
    @JsonIgnore
    private Long cdFilm;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "STUDIOS")
    private String studios;

    @Column(name = "PRODUCERS")
    private String producers;

    @Column(name = "WINNER")
    @JsonProperty("winner")
    private Boolean isWinner;

}

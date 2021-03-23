package com.texoit.worstfilms.repository;

import com.texoit.worstfilms.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface FilmRepository extends JpaRepository<Film, Long> {

    List<Film> findAllByIsWinnerOrderByYearAsc(Boolean isWinner);
}

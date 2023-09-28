package com.abiyyu.projects.libary.repositories;

import com.abiyyu.projects.libary.dto.DateCountFilm;
import com.abiyyu.projects.libary.dto.FilmSizeDto;
import com.abiyyu.projects.libary.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film,Long> {

    @Query(value = "select new com.abiyyu.projects.libary.dto.FilmSizeDto(f.rating, count(f.id))"+
            " FROM Film f INNER JOIN Schedule s ON f.id = s.film.id "+
            "where s.is_enabled = true group by f.rating")
    List<FilmSizeDto> getCountRateFilm();

    @Query(value = "select f FROM Film f INNER JOIN Schedule s ON f.id = s.film.id "+
            "where s.is_enable_hero = true")
    List<Film> getFilmForHeroes();


    @Query(value = "select f FROM Film f INNER JOIN Schedule s ON f.id = s.film.id "+
            "where s.is_enabled = true")
    List<Film> getFilmEnable();

    long countByRating(Integer rating);

    @Query(value = "select new com.abiyyu.projects.libary.dto.DateCountFilm(s.date,count(f.id))" +
        " FROM Film f INNER JOIN Schedule s ON f.id = s.film.id " +
        "WHERE s.date between :min AND :max AND s.is_enabled = true " +
        "GROUP BY s.date ORDER BY s.date")
    List<DateCountFilm> getDateFilm(@Param("min") LocalDate min, @Param("max") LocalDate max);

}

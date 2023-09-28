package com.abiyyu.projects.libary.service;

import com.abiyyu.projects.libary.dto.DateCountFilm;
import com.abiyyu.projects.libary.dto.DateCountFilmToDays;
import com.abiyyu.projects.libary.dto.FilmDto;
import com.abiyyu.projects.libary.dto.FilmSizeDto;
import com.abiyyu.projects.libary.entity.Film;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FilmService {
    List<FilmDto> getAllFilm();
    Film saveFilm(MultipartFile heroes,MultipartFile posters, FilmDto filmDto);
    Film updateFilm(MultipartFile heroes,MultipartFile posters ,FilmDto filmDto);
    void deleteById(Long id);
    FilmDto getById(Long id);
    List<FilmSizeDto> countRateFilms();

    List<Film> getFilmByHeroes();

    List<Film> getFilmEnable();

    List<DateCountFilmToDays> getDateCountFilm();

    Long countFilm();

    Film getFilmId(Long id);
}

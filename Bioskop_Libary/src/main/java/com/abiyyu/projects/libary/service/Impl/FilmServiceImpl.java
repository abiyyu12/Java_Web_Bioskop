package com.abiyyu.projects.libary.service.Impl;

import com.abiyyu.projects.libary.dto.DateCountFilm;
import com.abiyyu.projects.libary.dto.DateCountFilmToDays;
import com.abiyyu.projects.libary.dto.FilmDto;
import com.abiyyu.projects.libary.dto.FilmSizeDto;
import com.abiyyu.projects.libary.entity.Film;
import com.abiyyu.projects.libary.repositories.FilmRepository;
import com.abiyyu.projects.libary.service.FilmService;
import com.abiyyu.projects.libary.utils.ImageUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    @Autowired
    private final FilmRepository filmRepository;

    @Autowired
    private final ImageUpload imageUpload;

    @Override
    public List<FilmDto> getAllFilm() {
        return transferData(filmRepository.findAll());
    }

    @Override
    public Film saveFilm(MultipartFile heroes,MultipartFile poster, FilmDto filmDto) {
        Film film = new Film();
        try {
            if (heroes == null || poster == null) {
                film.setPosters(null);
                film.setHero(null);
            } else {
                imageUpload.uploadFileHeroes(heroes);
                film.setHero(Base64.getEncoder().encodeToString(heroes.getBytes()));
                imageUpload.uploadFilePosters(poster);
                film.setPosters(Base64.getEncoder().encodeToString(poster.getBytes()));
            }
            film.setName(filmDto.getName());
            film.setRating(filmDto.getRating());
            film.setDuration(filmDto.getDuration());
            film.setStudio(filmDto.getStudio());
            film.setReleaseDate(filmDto.getReleaseDate());
            film.setPremiumPrice(filmDto.getPremiumPrice());
            film.setRegularPrice(filmDto.getRegularPrice());
            film.setDescription(filmDto.getDescription());
            film.setGenre(filmDto.getGenre());
            return filmRepository.save(film);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Film updateFilm(MultipartFile heroes,MultipartFile poster,FilmDto filmDto) {
        try {
            Film filmUpdate = filmRepository.findById(filmDto.getId()).get();
            if (heroes == null) {
                filmUpdate.setHero(filmUpdate.getHero());
            } else {
                if (imageUpload.checkExistHeroes(heroes)) {
                    filmUpdate.setHero(filmUpdate.getHero());
                } else {
                    imageUpload.uploadFileHeroes(heroes);
                    filmUpdate.setHero(Base64.getEncoder().encodeToString(heroes.getBytes()));
                }
            }
            if (poster == null) {
                filmUpdate.setPosters(filmUpdate.getPosters());
            } else {
                if (imageUpload.checkExistPosters(poster)) {
                    filmUpdate.setPosters(filmUpdate.getPosters());
                } else {
                    imageUpload.uploadFilePosters(poster);
                    filmUpdate.setPosters(Base64.getEncoder().encodeToString(poster.getBytes()));
                }
            }
            filmUpdate.setId(filmUpdate.getId());
            filmUpdate.setName(filmDto.getName());
            filmUpdate.setRating(filmDto.getRating());
            filmUpdate.setDuration(filmDto.getDuration());
            filmUpdate.setStudio(filmDto.getStudio());
            filmUpdate.setReleaseDate(filmDto.getReleaseDate());
            filmUpdate.setPremiumPrice(filmDto.getPremiumPrice());
            filmUpdate.setRegularPrice(filmDto.getRegularPrice());
            filmUpdate.setDescription(filmDto.getDescription());
            filmUpdate.setGenre(filmDto.getGenre());
            return filmRepository.save(filmUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        filmRepository.deleteById(id);
    }

    @Override
    public FilmDto getById(Long id) {
        FilmDto filmDto = new FilmDto();
        Film film = filmRepository.findById(id).get();
        filmDto.setId(film.getId());
        filmDto.setName(film.getName());
        filmDto.setDuration(film.getDuration());
        filmDto.setReleaseDate(film.getReleaseDate());
        filmDto.setStudio(film.getStudio());
        filmDto.setPosters(film.getPosters());
        filmDto.setHero(film.getHero());
        filmDto.setRating(film.getRating());
        filmDto.setPremiumPrice(film.getPremiumPrice());
        filmDto.setRegularPrice(film.getRegularPrice());
        filmDto.setDescription(film.getDescription());
        filmDto.setGenre(film.getGenre());
        return filmDto;
    }

    @Override
    public List<FilmSizeDto> countRateFilms() {
        List<FilmSizeDto> sizeDtos = filmRepository.getCountRateFilm();
        System.out.println("Size : "+sizeDtos.size());
        return sizeDtos;
    }

    @Override
    public List<Film> getFilmByHeroes() {
        List<Film> getHeroesFilm = filmRepository.getFilmForHeroes();
        return getHeroesFilm;
    }

    @Override
    public List<Film> getFilmEnable() {
        List<Film> enableFilm = filmRepository.getFilmEnable();
        List<Film> result = toYears(enableFilm);
        return result;
    }

    @Override
    public List<DateCountFilmToDays> getDateCountFilm() {
        Calendar calendar = Calendar.getInstance();
        LocalDate localDateStart = LocalDate.now();
        DayOfWeek dayOfWeek = localDateStart.getDayOfWeek();
        if(dayOfWeek == DayOfWeek.SATURDAY){
            int dayOfMonth = localDateStart.getDayOfMonth()+2;
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        }else if(dayOfWeek == DayOfWeek.SUNDAY){
            int dayOfMonth = localDateStart.getDayOfMonth()+1;
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        }else{
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }
        Date date = calendar.getTime();
        localDateStart = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateEnd = localDateStart.plusDays(5L);
        List<DateCountFilm> dateFilm = filmRepository.getDateFilm(localDateStart, localDateEnd);
        List<DateCountFilmToDays> dateCountFilmToDays = new ArrayList<>();
        for (DateCountFilm dateCountFilm : dateFilm) {
            DateCountFilmToDays dateCountFilmToDaysTemp = new DateCountFilmToDays();
            dateCountFilmToDaysTemp.setDays(dateCountFilm.getDate().getDayOfWeek().toString());
            dateCountFilmToDaysTemp.setSize(dateCountFilm.getSize());
            dateCountFilmToDays.add(dateCountFilmToDaysTemp);
        }
        return dateCountFilmToDays;
    }

    @Override
    public Long countFilm() {
        return filmRepository.count();
    }

    @Override
    public Film getFilmId(Long id) {
        return filmRepository.findById(id).get();
    }

    private List<Film> toYears(List<Film> films){
        for (Film film : films) {
            LocalDate localDate = LocalDate.parse(film.getReleaseDate());
            String years = String.valueOf(localDate.getYear());
            film.setReleaseDate(years);
        }
        return films;
    }

    private List<FilmDto> transferData(List<Film> films){
        List<FilmDto> filmDtos = new ArrayList<>();
        for (Film film : films){
            FilmDto filmDto = new FilmDto();
            filmDto.setId(film.getId());
            filmDto.setName(film.getName());
            filmDto.setDuration(film.getDuration());
            filmDto.setHero(film.getHero());
            filmDto.setPosters(film.getPosters());
            filmDto.setRating(film.getRating());
            filmDto.setStudio(film.getStudio());
            filmDto.setReleaseDate(film.getReleaseDate());
            filmDto.setRegularPrice(film.getRegularPrice());
            filmDto.setPremiumPrice(film.getPremiumPrice());
            filmDto.setDescription(film.getDescription());
            filmDto.setGenre(film.getGenre());
            filmDtos.add(filmDto);
        }
        return filmDtos;
    }
}

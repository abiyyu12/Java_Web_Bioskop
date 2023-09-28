package com.abiyyu.projects.libary.repositories;
import com.abiyyu.projects.libary.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    @Query(value = "SELECT s FROM Schedule s WHERE s.is_enable_hero = true")
    List<Schedule> getScheduleEnableTrue();
//
//    @Query(value = "SELECT s,f FROM Schedule s INNER JOIN Film f ON s.film.id = f.id WHERE s.is_enabled = true")
//    List<Schedule> getFilmSchedule();

    @Query(value = "SELECT s FROM Schedule s INNER JOIN Film f ON s.film.id = f.id WHERE f.id = ?1")
    Schedule getScheduleByFilmId(Long idFilm);


    @Query(value = "SELECT s FROM Schedule s INNER JOIN Film f ON s.film.id = f.id" +
            " WHERE s.date between :min AND :max AND s.is_enabled = true ORDER BY s.date")
    List<Schedule> getDateFilm(@Param("min") LocalDate min, @Param("max") LocalDate max);

}

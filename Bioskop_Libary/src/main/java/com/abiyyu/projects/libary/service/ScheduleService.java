package com.abiyyu.projects.libary.service;


import com.abiyyu.projects.libary.dto.ScheduleDto;
import com.abiyyu.projects.libary.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDto> getAllSchedule();
    Schedule saveSchedule(ScheduleDto scheduleDto);
    Schedule updateSchedule(ScheduleDto scheduleDto);
    void deleteById(Long id);
    ScheduleDto getById(Long id);
    Schedule enableHero(Long id);
    Schedule disableHero(Long id);

    List<ScheduleDto> getFilmBySchedule();

    ScheduleDto getScheduleByFilmId(Long idFilm);

    List<ScheduleDto> getScheduleForIndex();

    List<ScheduleDto> getComingSoonForIndex();

    Schedule getByIdSchedule(Long id);

    public Schedule enableScheduleById(Long id);

    public Schedule disableScheduleById(Long id);
//    For Customer Section
    public Schedule updateScheduleCustomer(ScheduleDto scheduleDto);

    Long countSchedule();

}

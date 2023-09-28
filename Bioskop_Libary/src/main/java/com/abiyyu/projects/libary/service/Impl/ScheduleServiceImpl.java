package com.abiyyu.projects.libary.service.Impl;
import com.abiyyu.projects.libary.dto.ScheduleDto;
import com.abiyyu.projects.libary.entity.Film;
import com.abiyyu.projects.libary.entity.Schedule;
import com.abiyyu.projects.libary.repositories.ScheduleRepository;
import com.abiyyu.projects.libary.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private FilmServiceImpl filmService;

    @Override
    public List<ScheduleDto> getAllSchedule() {
        List<ScheduleDto> allSchedule = transferData(scheduleRepository.findAll());
        return allSchedule;
    }

    @Override
    public Schedule saveSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule();
        schedule.setDate(LocalDate.parse(scheduleDto.getDate()));
        schedule.setOpeningTime(scheduleDto.getOpeningTime());
        schedule.setClosingTime(scheduleDto.getClosingTime());
        schedule.set_enabled(false);
        schedule.set_enable_hero(false);
        schedule.setRegularStock(scheduleDto.getRegularTicket());
        schedule.setPremiumStock(scheduleDto.getPremiumTicket());
        schedule.setPremiumTicket(scheduleDto.getPremiumTicket());
        schedule.setRegularTicket(scheduleDto.getRegularTicket());
        schedule.setFilm(scheduleDto.getFilm());
        Schedule save = scheduleRepository.save(schedule);
        return save;
    }

    @Override
    public Schedule updateSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule();
        schedule.setId(scheduleDto.getId());
        schedule.setDate(LocalDate.parse(scheduleDto.getDate()));
        schedule.setOpeningTime(scheduleDto.getOpeningTime());
        schedule.setClosingTime(scheduleDto.getClosingTime());
        schedule.setRegularStock(scheduleDto.getRegularStock());
        schedule.setPremiumStock(scheduleDto.getPremiumStock());
        schedule.setRegularTicket(scheduleDto.getRegularTicket());
        schedule.setPremiumTicket(scheduleDto.getPremiumTicket());
        schedule.setFilm(scheduleDto.getFilm());
        Schedule save = scheduleRepository.save(schedule);
        return save;
    }

    @Override
    public void deleteById(Long id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    public ScheduleDto getById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).get();
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(schedule.getId());
        scheduleDto.setDate(String.valueOf(schedule.getDate()));
        scheduleDto.setOpeningTime(schedule.getOpeningTime());
        scheduleDto.setClosingTime(schedule.getClosingTime());
        scheduleDto.set_enabled(schedule.is_enabled());
        scheduleDto.set_enable_hero(schedule.is_enable_hero());
        scheduleDto.setRegularStock(schedule.getRegularStock());
        scheduleDto.setPremiumStock(schedule.getPremiumStock());
        scheduleDto.setRegularTicket(schedule.getRegularTicket());
        scheduleDto.setPremiumTicket(schedule.getPremiumTicket());
        scheduleDto.setFilm(schedule.getFilm());
        return scheduleDto;
    }

    @Override
    public Schedule enableHero(Long id) {
        getEnableHero();
        Schedule schedule = scheduleRepository.findById(id).get();
        schedule.set_enable_hero(true);
        scheduleRepository.save(schedule);
        return schedule;
    }

    @Override
    public Schedule disableHero(Long id) {
        Schedule schedule = scheduleRepository.findById(id).get();
        schedule.set_enable_hero(false);
        scheduleRepository.save(schedule);
        return schedule;
    }

    @Override
    public List<ScheduleDto> getFilmBySchedule() {
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
        List<Schedule> filmSchedule = scheduleRepository.getDateFilm(localDateStart,localDateEnd);
        List<ScheduleDto> scheduleDtos = transferForFilm(filmSchedule);
        return scheduleDtos;
    }

    @Override
    public ScheduleDto getScheduleByFilmId(Long idFilm) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date date = c.getTime();
        LocalDate localDateStart = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateEnd = localDateStart.plusDays(5L);
        Schedule sch = scheduleRepository.getScheduleByFilmId(idFilm);
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(sch.getId());
        scheduleDto.setDate(String.valueOf(sch.getDate().toString()));
        scheduleDto.setOpeningTime(sch.getOpeningTime());
        scheduleDto.setClosingTime(sch.getClosingTime());
        scheduleDto.set_enabled(sch.is_enabled());
        scheduleDto.set_enable_hero(sch.is_enable_hero());
        scheduleDto.setRegularStock(sch.getRegularStock());
        scheduleDto.setPremiumStock(sch.getPremiumStock());
        scheduleDto.setRegularTicket(sch.getRegularTicket());
        scheduleDto.setPremiumTicket(sch.getPremiumTicket());
        scheduleDto.setFilm(sch.getFilm());
        return scheduleDto;
    }

    @Override
    public List<ScheduleDto> getScheduleForIndex() {
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
        List<Schedule> schedule = scheduleRepository.getDateFilm(localDateStart,localDateEnd);
        List<ScheduleDto> scheduleDto = transferForFilm(schedule);
        return scheduleDto;
    }

    @Override
    public List<ScheduleDto> getComingSoonForIndex() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,1);
        Date date = calendar.getTime();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateStart = localDate.plusMonths(1);
        LocalDate localDateEnd = localDate.plusMonths(3);
        List<Schedule> schedule = scheduleRepository.getDateFilm(localDateStart, localDateEnd);
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        for (Schedule sch : schedule){
            ScheduleDto scheduleDto = new ScheduleDto();
            scheduleDto.setId(sch.getId());
            // Convert Schedule Date
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MMMM dd", Locale.ENGLISH);
            LocalDate localDateParse = LocalDate.parse(sch.getDate().toString());
            String dateS = localDateParse.format(dateTimeFormat);
            // End Convert
            scheduleDto.setDate(dateS);
            scheduleDto.setOpeningTime(sch.getOpeningTime());
            scheduleDto.setClosingTime(sch.getClosingTime());
            scheduleDto.set_enabled(sch.is_enabled());
            scheduleDto.set_enable_hero(sch.is_enable_hero());
            scheduleDto.setRegularStock(sch.getRegularStock());
            scheduleDto.setPremiumStock(sch.getPremiumStock());
            scheduleDto.setFilm(sch.getFilm());
            scheduleDtos.add(scheduleDto);
        }
        return scheduleDtos;
    }

    @Override
    public Schedule getByIdSchedule(Long id) {
       return scheduleRepository.findById(id).get();
    }


    private List<ScheduleDto> transferForFilm(List<Schedule> schedule){
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        for (Schedule sch : schedule){
            ScheduleDto scheduleDto = new ScheduleDto();
            scheduleDto.setId(sch.getId());
            scheduleDto.setDate(String.valueOf(sch.getDate().getDayOfWeek().toString()));
            scheduleDto.setOpeningTime(sch.getOpeningTime());
            scheduleDto.setClosingTime(sch.getClosingTime());
            scheduleDto.set_enabled(sch.is_enabled());
            scheduleDto.set_enable_hero(sch.is_enable_hero());
            scheduleDto.setRegularStock(sch.getRegularStock());
            scheduleDto.setPremiumStock(sch.getPremiumStock());
            System.out.println(sch.getFilm().getReleaseDate());
            String[] split = sch.getFilm().getReleaseDate().split("-");
            String ofYears = split[0];
            Film film = filmService.getFilmId(sch.getFilm().getId());
            film.setReleaseDate(ofYears);
            scheduleDto.setFilm(film);
            scheduleDtos.add(scheduleDto);
        }
        return scheduleDtos;
    }

    private boolean getEnableHero(){
        List<Schedule> scheduleEnableTrue = scheduleRepository.getScheduleEnableTrue();
        int size = scheduleEnableTrue.size();
        if(scheduleEnableTrue.isEmpty() || size < 3){
            return true;
        }else {
            Schedule schedule = scheduleEnableTrue.get(0);
            schedule.set_enable_hero(false);
            scheduleRepository.save(schedule);
            return false;
        }
    }

    private List<ScheduleDto> transferData(List<Schedule> schedule){
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        for (Schedule sch : schedule){
            ScheduleDto scheduleDto = new ScheduleDto();
            scheduleDto.setId(sch.getId());
            scheduleDto.setDate(String.valueOf(sch.getDate().toString()));
            scheduleDto.setOpeningTime(sch.getOpeningTime());
            scheduleDto.setClosingTime(sch.getClosingTime());
            scheduleDto.set_enabled(sch.is_enabled());
            scheduleDto.set_enable_hero(sch.is_enable_hero());
            scheduleDto.setRegularStock(sch.getRegularStock());
            scheduleDto.setPremiumStock(sch.getPremiumStock());
            scheduleDto.setRegularTicket(sch.getRegularTicket());
            scheduleDto.setPremiumTicket(sch.getPremiumTicket());
            scheduleDto.setFilm(sch.getFilm());
            scheduleDtos.add(scheduleDto);
        }
        return scheduleDtos;
    }

    @Override
    public Schedule enableScheduleById(Long id){
        Schedule schedule = scheduleRepository.findById(id).get();
        schedule.set_enabled(true);
        Schedule save = scheduleRepository.save(schedule);
        return save;
    }

    public Schedule disableScheduleById(Long id){
        Schedule schedule = scheduleRepository.findById(id).get();
        schedule.set_enabled(false);
        Schedule save = scheduleRepository.save(schedule);
        return save;
    }

    @Override
    public Schedule updateScheduleCustomer(ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule();
        schedule.setId(scheduleDto.getId());
        schedule.setDate(LocalDate.parse(scheduleDto.getDate()));
        schedule.setOpeningTime(scheduleDto.getOpeningTime());
        schedule.setClosingTime(scheduleDto.getClosingTime());
        schedule.setRegularStock(scheduleDto.getRegularStock());
        schedule.setPremiumStock(scheduleDto.getPremiumStock());
        schedule.setRegularTicket(scheduleDto.getRegularTicket());
        schedule.setPremiumTicket(scheduleDto.getPremiumTicket());
        schedule.set_enable_hero(scheduleDto.is_enable_hero());
        schedule.set_enabled(scheduleDto.is_enabled());
        schedule.setFilm(scheduleDto.getFilm());
        Schedule save = scheduleRepository.save(schedule);
        return save;
    }

    @Override
    public Long countSchedule() {
        return scheduleRepository.count();
    }
}

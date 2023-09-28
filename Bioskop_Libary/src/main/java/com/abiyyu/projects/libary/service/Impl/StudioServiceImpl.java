package com.abiyyu.projects.libary.service.Impl;

import com.abiyyu.projects.libary.dto.StudioDto;
import com.abiyyu.projects.libary.entity.Film;
import com.abiyyu.projects.libary.entity.Studio;
import com.abiyyu.projects.libary.repositories.FilmRepository;
import com.abiyyu.projects.libary.repositories.StudioRepository;
import com.abiyyu.projects.libary.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudioServiceImpl implements StudioService {

    @Autowired
    StudioRepository studioRepository;

    @Override
    public boolean save(StudioDto studioDto) {
        Studio studio = new Studio();
        studio.setName(studioDto.getName());
        Studio saveStudio = studioRepository.save(studio);
        if(saveStudio != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Studio> getStudio() {
        return studioRepository.findAll();
    }

    @Override
    public boolean editStudio(StudioDto studioDto) {
        Studio studio = new Studio();
        studio.setId(studioDto.getId());
        studio.setName(studioDto.getName());
        Studio saveStudio = studioRepository.save(studio);
        if(saveStudio != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean deleteStudio(Long idStudio) {
        Studio studio = studioRepository.findById(idStudio).get();
        if(studio != null){
            studioRepository.deleteById(idStudio);
            return true;
        }
        return false;
    }

    @Override
    public Long countStudio() {
        return studioRepository.count();
    }

    public StudioDto getStudioById(Long id){
        Studio studio = studioRepository.findById(id).get();
        StudioDto studioDto = new StudioDto();
        studioDto.setId(studio.getId());
        studioDto.setName(studio.getName());
        return studioDto;
    }

}

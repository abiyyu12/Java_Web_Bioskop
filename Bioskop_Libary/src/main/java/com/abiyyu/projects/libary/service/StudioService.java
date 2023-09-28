package com.abiyyu.projects.libary.service;

import com.abiyyu.projects.libary.dto.StudioDto;
import com.abiyyu.projects.libary.entity.Studio;

import java.util.List;

public interface StudioService {
    boolean save(StudioDto studioDto);

    List<Studio> getStudio();

    boolean editStudio(StudioDto studioDto);

    boolean deleteStudio(Long idStudio);

    Long countStudio();
}

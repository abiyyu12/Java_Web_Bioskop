package com.abiyyu.projects.libary.service;

import com.abiyyu.projects.libary.dto.ChairDto;
import com.abiyyu.projects.libary.entity.Chair;

import java.util.List;

public interface ChairService {
    public List<ChairDto> getAllChairByScheduleId(Long id);
    public Chair saveChair(ChairDto chairDto);
    List<String> findChairByOrder(Long idOrder);
}

package com.abiyyu.projects.libary.service.Impl;

import com.abiyyu.projects.libary.dto.ChairDto;
import com.abiyyu.projects.libary.entity.Chair;
import com.abiyyu.projects.libary.repositories.ChairRepository;
import com.abiyyu.projects.libary.service.ChairService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChairServiceImpl implements ChairService {

    @Autowired
    private final ChairRepository chairRepository;

    @Override
    public List<ChairDto> getAllChairByScheduleId(Long id) {
        List<Chair> chairByScheduleId = chairRepository.getChairByScheduleId(id);
        List<ChairDto> chairDtos = transferData(chairByScheduleId);
        return chairDtos;
    }

    @Override
    public Chair saveChair(ChairDto chairDto) {
        Chair chair = new Chair();
        chair.setChairNumber(chairDto.getChairNumber());
        chair.set_booking(true);
        chair.setOrder(chairDto.getOrder());
        return chairRepository.save(chair);
    }

    @Override
    public List<String> findChairByOrder(Long idOrder) {
        List<String> chairsOrder = new ArrayList<>();
        List<Chair> chairs = chairRepository.getChairByOrder(idOrder);
        for (Chair chair : chairs) {
            chairsOrder.add(String.valueOf(chair.getChairNumber()));
        }
        return chairsOrder;
    }

    public List<ChairDto> transferData(List<Chair> chairs){
        List<ChairDto> chairDtos = new ArrayList<>();
        for (Chair chair : chairs) {
            ChairDto chairDto = new ChairDto();
            chairDto.setId(chair.getId());
            chairDto.setOrder(chair.getOrder());
            chairDto.setChairNumber(chair.getChairNumber());
            chairDto.set_booking(chair.is_booking());
            chairDtos.add(chairDto);
        }
        return chairDtos;
    }
}

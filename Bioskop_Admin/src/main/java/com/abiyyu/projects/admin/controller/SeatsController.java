package com.abiyyu.projects.admin.controller;

import com.abiyyu.projects.libary.dto.ChairDto;
import com.abiyyu.projects.libary.dto.ScheduleDto;
import com.abiyyu.projects.libary.service.ChairService;
import com.abiyyu.projects.libary.service.OrderService;
import com.abiyyu.projects.libary.service.ScheduleService;
import com.abiyyu.projects.libary.utils.SeatHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SeatsController {


    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ChairService chairService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/seats/{id}",method = {RequestMethod.PUT,RequestMethod.GET})
    public String getSeats(@PathVariable("id") Long scheduleId, Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        ScheduleDto scheduleDto = scheduleService.getById(scheduleId);
        int totalSeat = scheduleDto.getRegularTicket() + scheduleDto.getPremiumTicket();
        List<ChairDto> chair = chairService.getAllChairByScheduleId(scheduleDto.getId());
        List<SeatHelper> seatHelpers = new ArrayList<>();
        int counter = 0;
        System.out.println(chair.size());
        if(chair.isEmpty() == true){
            for (int i = 1; i <= totalSeat ; i++) {
                SeatHelper seatHelperTemp = new SeatHelper();
                seatHelperTemp.setNumber(i);
                seatHelperTemp.set_booking(false);
                seatHelpers.add(seatHelperTemp);
            }
        }else {
            for (int i = 1; i <= totalSeat; i++) {
                SeatHelper seatHelperTemp = new SeatHelper();
                seatHelperTemp.setNumber(i);
                if(counter < chair.size()){
                    ChairDto chairTemp = chair.get(counter);
                    if(chairTemp.getChairNumber() == i){
                        counter++;
                        seatHelperTemp.set_booking(true);
                    }else {
                        seatHelperTemp.set_booking(false);
                    }
                }
                else {
                    seatHelperTemp.set_booking(false);
                }
                seatHelpers.add(seatHelperTemp);
            }
        }
        int premiumSeat = scheduleDto.getPremiumTicket();
        Long clearValue = orderService.statusClear();
        Long pendingValue = orderService.statusPending();
        Long cancelValue = orderService.statusCancel();
        model.addAttribute("clearValue",clearValue);
        model.addAttribute("pendingValue",pendingValue);
        model.addAttribute("cancelValue",cancelValue);
        model.addAttribute("title","Movie | Seats");
        model.addAttribute("premiumSeat",premiumSeat);
        model.addAttribute("regularSeat",scheduleDto.getRegularTicket());
        model.addAttribute("regularStock",scheduleDto.getRegularStock());
        model.addAttribute("premiumStock",scheduleDto.getPremiumStock());
        model.addAttribute("occupiedSeat",counter);
        model.addAttribute("seats",seatHelpers);
        return "seats";
    }
}

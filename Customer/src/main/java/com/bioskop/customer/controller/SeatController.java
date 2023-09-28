package com.bioskop.customer.controller;

import com.abiyyu.projects.libary.dto.ChairDto;
import com.abiyyu.projects.libary.dto.ScheduleDto;
import com.abiyyu.projects.libary.entity.Order;
import com.abiyyu.projects.libary.service.ChairService;
import com.abiyyu.projects.libary.service.OrderService;
import com.abiyyu.projects.libary.service.ScheduleService;
import com.abiyyu.projects.libary.utils.SeatHelper;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class SeatController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ChairService chairService;

    @GetMapping("/seat")
    public String seat(Model model, HttpSession httpSession,RedirectAttributes redirectAttributes){
        String orderId = httpSession.getAttribute("orderId").toString();
        String scheduleId = httpSession.getAttribute("scheduleId").toString();
        if(orderId == null || scheduleId == null){
            redirectAttributes.addFlashAttribute("error","You Cannot Take Seat Before Order An Film");
            return "redirect:/";
        }
        else {
            ScheduleDto scheduleDto = scheduleService.getById(Long.parseLong(scheduleId));
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
            model.addAttribute("title","Movie | Seats");
            model.addAttribute("premiumSeat",premiumSeat);
            model.addAttribute("seats",seatHelpers);
            model.addAttribute("order",orderService.findOrder(Long.parseLong(orderId)));
            if(httpSession.getAttribute("error") != null){
                model.addAttribute("error",httpSession.getAttribute("error").toString());
                httpSession.removeAttribute("error");
            }else {
                httpSession.removeAttribute("error");
            }
//            model.addAttribute("chairDto",chairService.getAllChairByScheduleId(Long.parseLong(scheduleId)));
            return "seat";
        }
    }

    @PostMapping("/takeseat")
    public String saveSeat(@RequestParam(value = "selectedSeat",required = false) List<String> selectedSeat , HttpSession httpSession,RedirectAttributes redirectAttributes){
        String scheduleId = httpSession.getAttribute("scheduleId").toString();
        ScheduleDto scheduleDto = scheduleService.getById(Long.parseLong(scheduleId));
        String orderId = httpSession.getAttribute("orderId").toString();
        Order order = orderService.findOrder(Long.parseLong(orderId));
        try{
            if(selectedSeat.size() < order.getQuantity()){
                httpSession.setAttribute("error","Please Selected More Seat");
                return "redirect:/seat";
            }else if(selectedSeat.size() > order.getQuantity()){
                httpSession.setAttribute("error","Cannot Selected More Seat");
                return "redirect:/seat";
            }else {
                boolean isAccept = true;
                if(order.getTicketType().equals("REGULAR")){
                    for (String s : selectedSeat) {
                        int parseInt = Integer.parseInt(s);
                        if(parseInt < scheduleDto.getPremiumTicket()){
                            isAccept = false;
                            break;
                        }
                    }
                }
                else{
                    for (String s : selectedSeat) {
                        int parseInt = Integer.parseInt(s);
                        if(parseInt > scheduleDto.getPremiumTicket()){
                            isAccept = false;
                            break;
                        }
                    }
                }
                if(isAccept) {
                    for (String s : selectedSeat) {
                        ChairDto chairDto = new ChairDto();
                        chairDto.setChairNumber(Integer.parseInt(s));
                        chairDto.setOrder(order);
                        chairDto.set_booking(true);
                        chairService.saveChair(chairDto);
                    }
                    httpSession.removeAttribute("scheduleId");
                    httpSession.removeAttribute("orderId");
                    httpSession.removeAttribute("error");
                }else {
                    httpSession.setAttribute("error","Wrong Seat Premium Seat Is (red) Regular is (Blue) Correction Again");
                    return "redirect:/seat";
                }
            }
            redirectAttributes.addFlashAttribute("success",true);
            return "redirect:/";
        }catch (NullPointerException e){
            httpSession.setAttribute("error","Please Selected More Seat");
            return "redirect:/seat";
        }
    }
}
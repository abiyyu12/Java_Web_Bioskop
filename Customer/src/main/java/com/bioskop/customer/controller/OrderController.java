package com.bioskop.customer.controller;
import com.abiyyu.projects.libary.dto.ScheduleDto;
import com.abiyyu.projects.libary.entity.Customer;
import com.abiyyu.projects.libary.entity.Order;
import com.abiyyu.projects.libary.entity.Schedule;
import com.abiyyu.projects.libary.service.CustomerService;
import com.abiyyu.projects.libary.service.FilmService;
import com.abiyyu.projects.libary.service.OrderService;
import com.abiyyu.projects.libary.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TimeZone;

@Controller
public class OrderController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/orders/{id}",method = {RequestMethod.GET,RequestMethod.PUT})
    public String order(@PathVariable("id") Long id, Model model){
        model.addAttribute("scheduleFilm",scheduleService.getById(id));
        model.addAttribute("title","Movie | Orders");
        return "order";
    }

    @PostMapping("/order")
    public String saveOrders(Model model, @RequestParam("scheduleId") String scheduleId,
                             @RequestParam("totalTicket") String totalTicket, @RequestParam("ticketType") String ticketType,
                             HttpSession httpSession){
        ScheduleDto scheduleByFilmId = scheduleService.getById(Long.parseLong(scheduleId));
        LocalDate localDate = LocalDate.now();
        int dayOfMonthsStart = localDate.getDayOfMonth();
        int dayOfMonthsEnd = LocalDate.parse(scheduleByFilmId.getDate()).getDayOfMonth();
        Double price = null;
        Order order = new Order();
        String openingTime = scheduleByFilmId.getOpeningTime();
        LocalTime localTime = LocalTime.now();
        LocalTime localTimeEnd = LocalTime.parse(openingTime);
        int hour = localTime.getHour();
        int hourTime = localTimeEnd.getHour();
//        int minutes = localTime.getMinute();
//        int minutesTime = localTimeStart.getMinute();
        int ttlTicket = Integer.parseInt(totalTicket);
        System.out.println(dayOfMonthsStart);
        System.out.println(dayOfMonthsEnd);
        if(dayOfMonthsStart <= dayOfMonthsEnd){
            if( (hour < hourTime && dayOfMonthsStart == dayOfMonthsEnd)  || dayOfMonthsStart < dayOfMonthsEnd){
                if(ticketType.equals("1") && ttlTicket <= scheduleByFilmId.getRegularStock()){
                    int sisaTicket = scheduleByFilmId.getRegularStock() - ttlTicket;
                    price = scheduleByFilmId.getFilm().getRegularPrice();
                    scheduleByFilmId.setRegularStock(sisaTicket);
                    scheduleService.updateScheduleCustomer(scheduleByFilmId);
                    ticketType = "REGULAR";
                }else if(ticketType.equals("2") && ttlTicket <= scheduleByFilmId.getPremiumStock()){
                    int sisaTicket = scheduleByFilmId.getPremiumStock() - ttlTicket;
                    price = scheduleByFilmId.getFilm().getPremiumPrice();
                    scheduleByFilmId.setPremiumStock(sisaTicket);
                    scheduleService.updateScheduleCustomer(scheduleByFilmId);
                    ticketType = "PREMIUM";
                }else{
                    model.addAttribute("error","Unable to book tickets (Out Of Stock)");
                    model.addAttribute("scheduleFilm",scheduleByFilmId);
                    model.addAttribute("title","Movie | Orders");
                    return "order";
                }
                Double totalPrice = (double) price * Integer.parseInt(totalTicket);
                order.setOrderDate(LocalDate.now());
                Customer customer = customerService.findByUsername(httpSession.getAttribute("username").toString());
                order.setCustomer(customer);
                order.setQuantity(Integer.parseInt(totalTicket));
                order.setTicketType(ticketType);
                order.setTotalPrice(totalPrice);
                order.setStatus("PENDING");
                Schedule byIdSchedule = scheduleService.getByIdSchedule(Long.parseLong(scheduleId));
                order.setSchedule(byIdSchedule);
                Order saveOrder = orderService.saveOrder(order);
                httpSession.setAttribute("orderId",saveOrder.getId());
                httpSession.setAttribute("scheduleId",scheduleByFilmId.getId());
                return "redirect:/seat";
            }
            else {
                model.addAttribute("error","Cannot Order Because Out Of TIme");
                model.addAttribute("scheduleFilm",scheduleByFilmId);
                model.addAttribute("title","Movie | Orders");
                return "order";
            }
        }
        else {
            model.addAttribute("error","Cannot Order Because Out Of Date");
            model.addAttribute("scheduleFilm",scheduleByFilmId);
            model.addAttribute("title","Movie | Orders");
            return "order";
        }
    }
}

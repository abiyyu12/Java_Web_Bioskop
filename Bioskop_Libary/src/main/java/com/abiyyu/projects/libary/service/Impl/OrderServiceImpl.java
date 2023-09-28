package com.abiyyu.projects.libary.service.Impl;

import com.abiyyu.projects.libary.dto.CountOrderDate;
import com.abiyyu.projects.libary.dto.CountOrderStatus;
import com.abiyyu.projects.libary.dto.DateCountFilm;
import com.abiyyu.projects.libary.entity.Order;
import com.abiyyu.projects.libary.repositories.OrderRepositories;
import com.abiyyu.projects.libary.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private final OrderRepositories orderRepositories;


    @Override
    public Order saveOrder(Order order) {
        return orderRepositories.save(order);
    }

    @Override
    public Order findOrder(Long id) {
        return orderRepositories.findById(id).get();
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepositories.findAll();
    }

    @Override
    public boolean successOrders(Long id) {
        Order order = orderRepositories.findById(id).get();
        order.setStatus("CLEAR");
        Order save = orderRepositories.save(order);
        if(save != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void deleteOrders(Long id) {
        orderRepositories.deleteById(id);
    }



    @Override
    public List<String> getLabelOrder() {
        LocalDate localDateEnd = LocalDate.now();
        LocalDate localDateStart = localDateEnd.minusDays(5L);
        List<CountOrderDate> date = orderRepositories.getOrderDate(localDateStart, localDateEnd);
        List<String> labelOrder = new ArrayList<>();
        for(CountOrderDate countOrderDate : date){
            labelOrder.add(countOrderDate.getOrderDate().toString());
        }
        return labelOrder;
    }

    @Override
    public List<String> getValueOrder() {
        LocalDate localDateEnd = LocalDate.now();
        LocalDate localDateStart = localDateEnd.minusDays(5L);
        List<CountOrderDate> date = orderRepositories.getOrderDate(localDateStart, localDateEnd);
        List<String> valueOrder = new ArrayList<>();
        for(CountOrderDate countOrderDate : date){
            valueOrder.add(countOrderDate.getValueDate().toString());
        }
        return valueOrder;
    }

    @Override
    public Long countOrder() {
        return orderRepositories.count();
    }

    @Override
    public List<Order> getOrderByCustomer(Long customerId) {
        return orderRepositories.findOrderByCustomer(customerId);
    }

    @Override
    public Long statusClear() {
        List<CountOrderStatus> orderStatus = orderRepositories.getOrderDateStatus(LocalDate.now());
        Long clearValue = 0L;
        for(CountOrderStatus countOrderStatus : orderStatus){
            if(countOrderStatus.getStatus().equals("CLEAR")){
                clearValue = countOrderStatus.getValueDate();
                break;
            }
        }
        return clearValue;
    }

    @Override
    public Long statusPending() {
        List<CountOrderStatus> orderStatus = orderRepositories.getOrderDateStatus(LocalDate.now());
        Long pendingValue = 0L;
        for(CountOrderStatus countOrderStatus : orderStatus){
            if(countOrderStatus.getStatus().equals("PENDING")){
                pendingValue = countOrderStatus.getValueDate();
                break;
            }
        }
        return pendingValue;
    }

    @Override
    public Long statusCancel() {
        List<CountOrderStatus> orderStatus = orderRepositories.getOrderDateStatus(LocalDate.now());
        Long cancelValue = 0L;
        for(CountOrderStatus countOrderStatus : orderStatus){
            if(countOrderStatus.getStatus().equals("CANCEL")){
                cancelValue = countOrderStatus.getValueDate();
                break;
            }
        }
        return cancelValue;
    }
}

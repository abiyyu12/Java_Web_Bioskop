package com.abiyyu.projects.libary.service;

import com.abiyyu.projects.libary.dto.CountOrderStatus;
import com.abiyyu.projects.libary.entity.Order;

import java.util.List;

public interface OrderService {
    public Order saveOrder(Order order);
    public Order findOrder(Long id);

    public List<Order> getAllOrders();
    boolean successOrders(Long id);
    void deleteOrders(Long id);


    List<String> getLabelOrder();
    List<String> getValueOrder();
    Long countOrder();
    List<Order> getOrderByCustomer(Long customerId);

    Long statusClear();
    Long statusPending();
    Long statusCancel();
}

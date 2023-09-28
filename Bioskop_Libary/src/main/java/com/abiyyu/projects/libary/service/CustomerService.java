package com.abiyyu.projects.libary.service;

import com.abiyyu.projects.libary.dto.CustomerDto;
import com.abiyyu.projects.libary.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {
    Customer save(CustomerDto customerDto);

    Customer findByUsername(String username);

    Customer update(CustomerDto customerDto);

    boolean changePass(Customer customer,String oldPassword,String newPassword);

    CustomerDto getCustomer(String username);

    List<Customer>getAllCustomer();

    Long countCustomer();
}

package com.abiyyu.projects.libary.service.Impl;

import com.abiyyu.projects.libary.dto.CustomerDto;
import com.abiyyu.projects.libary.entity.Customer;
import com.abiyyu.projects.libary.repositories.CustomerRepository;
import com.abiyyu.projects.libary.repositories.RoleRepository;
import com.abiyyu.projects.libary.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public Customer save(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setNoTelp(customerDto.getNoTelp());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customer.setAddress(customerDto.getAddress());
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        Customer save = customerRepository.save(customer);
        return save;
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer update(CustomerDto customerDto) {
        Customer customer = customerRepository.findById(customerDto.getId()).get();
        Customer customerSave = new Customer();
        customerSave.setId(customerDto.getId());
        customerSave.setRoles(customer.getRoles());
        customerSave.setName(customerDto.getName());
        customerSave.setAddress(customerDto.getAddress());
        customerSave.setPassword(customerDto.getPassword());
        customerSave.setNoTelp(customerDto.getNoTelp());
        customerSave.setUsername(customerDto.getUsername());
        return customerRepository.save(customerSave);
    }

    @Override
    public boolean changePass(Customer customer, String oldPassword, String newPassword) {
        boolean isSuccess;
        if(passwordEncoder.matches(oldPassword,customer.getPassword()) == true){
            customer.setPassword(passwordEncoder.encode(newPassword));
            customerRepository.save(customer);
            isSuccess = true;
        }else{
            isSuccess = false;
        }
        return isSuccess;
    }


    @Override
    public CustomerDto getCustomer(String username) {
        Customer customer = customerRepository.findByUsername(username);
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setAddress(customer.getAddress());
        customerDto.setUsername(customer.getUsername());
        customerDto.setPassword(customer.getPassword());
        customerDto.setNoTelp(customer.getNoTelp());
        customerDto.setConfirmPassword(customer.getPassword());
        return customerDto;
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Long countCustomer() {
        return customerRepository.count();
    }
}

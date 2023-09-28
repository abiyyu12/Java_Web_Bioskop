package com.abiyyu.projects.libary.repositories;

import com.abiyyu.projects.libary.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    public Customer findByUsername(String username);
}

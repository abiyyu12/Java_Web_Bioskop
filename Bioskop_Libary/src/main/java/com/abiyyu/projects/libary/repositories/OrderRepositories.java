package com.abiyyu.projects.libary.repositories;

import com.abiyyu.projects.libary.dto.CountOrderDate;
import com.abiyyu.projects.libary.dto.CountOrderStatus;
import com.abiyyu.projects.libary.dto.DateCountFilm;
import com.abiyyu.projects.libary.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepositories extends JpaRepository<Order,Long> {
//    @Query("SELECT o FROM Order o WHERE o.customer.id =?1")
//    Order findOrderByCustomer(Long id);

    @Query(value = "select new com.abiyyu.projects.libary.dto.CountOrderDate(o.orderDate,count(o.id))" +
            " FROM Order o WHERE o.orderDate between :min AND :max GROUP BY o.orderDate ORDER BY o.orderDate")
    List<CountOrderDate> getOrderDate(@Param("min") LocalDate min, @Param("max") LocalDate max);

    @Query("SELECT o FROM Order o WHERE o.customer.id =?1")
    List<Order> findOrderByCustomer(Long id);

    @Query(value = "select new com.abiyyu.projects.libary.dto.CountOrderStatus(o.status,count(o.id))" +
            " FROM Order o WHERE o.orderDate =?1 GROUP BY o.status")
    List<CountOrderStatus> getOrderDateStatus(LocalDate date);
}

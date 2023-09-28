package com.abiyyu.projects.libary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id_customer")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "id_schedule",referencedColumnName = "id")
    private Schedule schedule;

    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Chair> chairs;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(length = 10)
    private String ticketType;

    @Column(name = "total_price")
    private double totalPrice;

    private int quantity;

    @Column(length = 15)
    private String status;
}

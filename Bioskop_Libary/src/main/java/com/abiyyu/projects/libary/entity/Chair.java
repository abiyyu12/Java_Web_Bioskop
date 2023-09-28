package com.abiyyu.projects.libary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Chair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_order",referencedColumnName = "order_id")
    private Order order;

    @Column(name = "chair_number")
    private int chairNumber;

    private boolean is_booking;
}

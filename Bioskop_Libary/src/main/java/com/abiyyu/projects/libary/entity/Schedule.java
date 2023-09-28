package com.abiyyu.projects.libary.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "schedule")
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(length = 10)
    private String openingTime;

    @Column(length = 10)
    private String closingTime;

    private boolean is_enabled;

    private boolean is_enable_hero;

    @OneToMany(mappedBy = "schedule",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Order> orders;

    private Integer regularStock;

    private Integer premiumStock;

    private Integer premiumTicket;

    private Integer regularTicket;

    @ManyToOne
    @JoinColumn(name = "id_film",referencedColumnName = "id")
    private Film film;
}

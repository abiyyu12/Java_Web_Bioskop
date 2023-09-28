package com.abiyyu.projects.libary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountOrderDate {
    private LocalDate orderDate;
    private Long valueDate;
}

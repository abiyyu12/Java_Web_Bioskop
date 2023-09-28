package com.abiyyu.projects.libary.dto;

import com.abiyyu.projects.libary.entity.Order;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChairDto {
    private Long id;

    private Order order;

    @Size(min = 1,message = "Please Select Chair If The Chair N/A")
    private int chairNumber;

    private boolean is_booking;
}

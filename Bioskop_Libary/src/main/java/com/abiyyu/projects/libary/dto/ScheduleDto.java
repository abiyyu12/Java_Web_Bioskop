package com.abiyyu.projects.libary.dto;

import com.abiyyu.projects.libary.entity.Film;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {

    private Long id;

    @NotBlank(message = "Date Cannot Blank")
    private String date;

    @NotBlank(message = "Opening Time Cannot Blank")
    private String openingTime;

    @NotBlank(message = "Closing Time Cannot Blank")
    private String closingTime;

    private boolean is_enabled;

    private boolean is_enable_hero;

    private Integer regularStock;

    private Integer premiumStock;

    @NotNull(message = "Premium Stock Cannot Null")
    @Min(value = 1,message = "Premium Stock Min 1")
    private Integer premiumTicket;

    @NotNull(message = "Regular Stock Cannot Null")
    @Min(value = 1,message = "Regular Stock Min 1")
    private Integer regularTicket;


    private Film film;
}

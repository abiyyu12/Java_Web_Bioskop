package com.abiyyu.projects.libary.dto;


import com.abiyyu.projects.libary.entity.Film;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudioDto {
    private Long id;

    @Size(max = 50,message = "Max Length Size (50 characters)")
    @NotBlank(message = "name Cannot Blank")
    private String name;

    private List<Film> film = new ArrayList<>();
}

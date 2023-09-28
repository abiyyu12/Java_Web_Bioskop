package com.abiyyu.projects.libary.dto;
import com.abiyyu.projects.libary.entity.Studio;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmDto {

    private Long id;

    @NotBlank(message = "Name Cannot Blank")
    @Size(max = 50,message = "Max Character 50")
    private String name;

    @NotNull(message = "Duration Cannot Null")
    @Min(message = "Minimal 1 Minutes Duration",value = 1)
    private Integer duration;

    @NotNull(message = "Rating Cannot Null")
    @Min(message = "Minimal 1 Star Rating",value = 1)
    private Integer rating;

    @NotBlank(message = "Release Date Cannot Blank")
    private String releaseDate;

    private Studio studio;

    private String posters;

    private String hero;

    @NotNull(message = "RegularPrice Cannot Null")
    @Min(message = "Minimal 1 Dollar for RegularPrice",value = 1)
    private Double regularPrice;

    @NotNull(message = "Duration Cannot Null")
    @Min(message = "Minimal 3 Dollar For PremiumPrice",value = 1)
    private Double premiumPrice;

    @NotBlank(message = "Description Cannot Blank")
    private String description;

    @NotBlank(message = "Please Insert 1 Genre")
    private String genre;

    private int filmSize;
}

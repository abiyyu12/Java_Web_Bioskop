package com.abiyyu.projects.libary.dto;

import com.abiyyu.projects.libary.constraint.CheckPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@CheckPassword(message = "Password And Repeat Password Must Be Same")
public class AdminDto {

    private Long id;

    @Size(min = 3,max = 10,message = "Invalid first name (3-10 Characters)")
    private String firstName;

    @Size(min = 3,max = 10,message = "Invalid first name (3-10 Characters)")
    private String lastName;

    @NotBlank(message = "Username Cannot Blank")
    private String username;

    @Size(min = 5,max = 15,message = "Invalid Password (5-15 Characters)")
    private String password;

    @NotBlank(message = "Repeat Password Cannot Blank")
    private String repeatPassword;

    private String image;
}

package com.abiyyu.projects.libary.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Long id;

    @Size(min = 3,message = "Invalid username (3-10 Characters)")
    private String username;

    @NotBlank(message = "Name Cannot Blank")
    private String name;

    @Size(min = 5,message = "Invalid password (5-10 Characters)")
    private String password;

    @Size(min = 8,max = 13,message = "Invalid Number Phone")
    private String noTelp;

    @NotBlank(message = "address Cannot Blank")
    private String address;

    @Size(min = 5,message = "Invalid Repeat Password (5-10 Characters)")
    private String confirmPassword;
}

package com.demo.project.sms.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long id;
    @NotEmpty(message = "Field cannot be empty")
    private String firstName;
    @NotEmpty(message = "Field cannot be empty")
    private String lastName;
    @NotEmpty(message = "Field cannot be empty")
    private String email;

    // getter/setter methods
}

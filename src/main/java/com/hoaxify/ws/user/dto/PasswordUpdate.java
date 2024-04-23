package com.hoaxify.ws.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PasswordUpdate(
        @Size(min = 8, max = 55) 
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{Hoaxify.Constraint.Password.Pattern}") 
        String password) {

}

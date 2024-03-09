package com.hoaxify.ws.user.dto;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.validation.UniqueEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreate(
        @NotBlank(message = "{Hoaxify.Constraint.UserName.NotBlank}") 
        @Size(min = 5, max = 255) 
        String username,

        @NotBlank 
        @Email 
        @UniqueEmail 
        String email,

        @Size(min = 8, max = 55) 
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{Hoaxify.Constraint.Password.Pattern}")
        String password) 
        {

    public User toUser() {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

}

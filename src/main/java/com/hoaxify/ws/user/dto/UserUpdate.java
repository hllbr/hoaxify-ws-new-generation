package com.hoaxify.ws.user.dto;

import com.hoaxify.ws.user.validation.FileType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdate(

        @NotBlank(message = "{Hoaxify.Constraint.UserName.NotBlank}") 
        @Size(min = 5, max = 255) 
        String username,
        
        @FileType(types = {"jpeg", "png", "gif"})
        String image) {

}

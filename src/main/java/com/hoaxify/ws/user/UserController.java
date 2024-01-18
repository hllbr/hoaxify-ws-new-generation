package com.hoaxify.ws.user;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.shared.GenericMessage;
import com.hoaxify.ws.shared.Messages;
import com.hoaxify.ws.user.exception.NotUniqueEmailException;

import jakarta.validation.Valid;

@RestController
public class UserController {

        @Autowired
        UserService userService;

        // @Autowired
        // MessageSource messageSource;

        @PostMapping("/api/v1/users")
        GenericMessage createUser(@Valid @RequestBody User user) {
                userService.save(user);
                String message = Messages.getMeesageForLocale("hoaxify.create.user.success.message",
                                LocaleContextHolder.getLocale());
                return new GenericMessage(message);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        ResponseEntity<ApiError> handleMethodNotValidException(MethodArgumentNotValidException exception) {
                ApiError apiError = new ApiError();
                apiError.setPath("/api/v1/users");
                String message = Messages.getMeesageForLocale("hoaxify.error.validation",
                                LocaleContextHolder.getLocale());
                apiError.setMessage(message);
                apiError.setStatus(400);
                var validationMapErrors = exception.getBindingResult().getFieldErrors().stream()
                                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,
                                                (existing, replacing) -> existing));
                apiError.setValidationErrors(validationMapErrors);
                return ResponseEntity.badRequest().body(apiError);
        }

        @ExceptionHandler(NotUniqueEmailException.class)
        ResponseEntity<ApiError> handleNotUniqueEmailException(NotUniqueEmailException exception) {
                ApiError apiError = new ApiError();
                apiError.setPath("/api/v1/users");
                apiError.setMessage(exception.getMessage());
                apiError.setStatus(400);
                apiError.setValidationErrors(exception.getValidationErrors());

                return ResponseEntity.badRequest().body(apiError);
        }
}

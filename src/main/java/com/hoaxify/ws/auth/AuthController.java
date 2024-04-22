package com.hoaxify.ws.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.hoaxify.ws.auth.dto.AuthResponse;
import com.hoaxify.ws.auth.dto.Credentials;
import com.hoaxify.ws.shared.GenericMessage;
import com.hoaxify.ws.shared.Messages;

import jakarta.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    AuthService authservice;

    @PostMapping("/api/v1/auth")
    AuthResponse handleAuthentication(@Valid @RequestBody Credentials creds) {
        return authservice.authenticate(creds);
    }

    @PostMapping("/api/v1/logout")
    GenericMessage handleLogout(@RequestHeader(name = "Authorization", required = false) String authorizatonHeader) {
        authservice.logout(authorizatonHeader);
        String message = Messages.getMessageForLocale(
                "hoaxify.user.logout.successful",
                LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

}

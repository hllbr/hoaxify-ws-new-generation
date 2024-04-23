package com.hoaxify.ws.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
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
        ResponseEntity<AuthResponse> handleAuthentication(@Valid @RequestBody Credentials creds) {
                var authResponse = authservice.authenticate(creds);
                var cookie = ResponseCookie.from("hoax-token", authResponse.getToken().getToken()).path("/")
                                .httpOnly(true)
                                .build();
                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(authResponse);
        }

        @PostMapping("/api/v1/logout")
        ResponseEntity<GenericMessage> handleLogout(
                        @RequestHeader(name = "Authorization", required = false) String authorizatonHeader,
                        @CookieValue(name = "hoax-token", required = false) String cookieValue) {
                var tokenWithPrefix = authorizatonHeader;
                if (cookieValue != null) {
                        tokenWithPrefix = "AnyPrefix " + cookieValue;
                }
                authservice.logout(tokenWithPrefix);
                var cookie = ResponseCookie.from("hoax-token", "").maxAge(0).path("/").httpOnly(true)
                                .build();
                String message = Messages.getMessageForLocale(
                                "hoaxify.user.logout.successful",
                                LocaleContextHolder.getLocale());
                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(
                                new GenericMessage(message));
        }

}

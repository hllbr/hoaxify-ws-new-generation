package com.hoaxify.ws.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.hoaxify.ws.auth.token.TokenService;
import com.hoaxify.ws.shared.Messages;
import com.hoaxify.ws.user.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            User user = tokenService.verifyToken(authorizationHeader);
            if (user != null) {
                if (!user.isActive()) {
                    String message = Messages
                    .getMessageForLocale(
                    "hoaxify.token.filter.user.disabled",
                    LocaleContextHolder.getLocale());
                    exceptionResolver.resolveException(request, response, null, new DisabledException(message));
                    return;
                    //Alternative to exceptionResolver.resolveException
                    // ApiError apiError = new ApiError();
                    // apiError.setStatus(401);
                    // String message = Messages
                    //         .getMessageForLocale(
                    //                 "hoaxify.token.filter.user.disabled",
                    //                 LocaleContextHolder.getLocale());
                    // apiError.setMessage(message);
                    // apiError.setPath(request.getRequestURI());
                    // ObjectMapper objectMapper = new ObjectMapper();

                    // response.setStatus(401);
                    // response.setContentType("application/json");
                    // OutputStream os = response.getOutputStream();
                    // objectMapper.writeValue(os, apiError);
                    // os.flush();
                    // return;
                }
                CurrentUser currentUser = new CurrentUser(user);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        currentUser, null, currentUser.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        filterChain.doFilter(request, response);
    }

}

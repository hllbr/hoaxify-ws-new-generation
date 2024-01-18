package com.hoaxify.ws.user.exception;

import java.util.Collections;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;

import com.hoaxify.ws.shared.Messages;

public class NotUniqueEmailException extends RuntimeException {

    public NotUniqueEmailException() {
        super(Messages.getMeesageForLocale("hoaxify.error.validation", LocaleContextHolder.getLocale()));
    }

    public Map<String, String> getValidationErrors() {
        return Collections.singletonMap("email",
                Messages.getMeesageForLocale("hoaxify.constraint.email.notunique", LocaleContextHolder.getLocale()));
    }
}

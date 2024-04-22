package com.hoaxify.ws.auth.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoaxify.ws.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class Token {

    @Transient
    String prefix = "Bearer";

    @Id
    String token;

    @JsonIgnore
    @ManyToOne
    User user;

    public Token(String prefix, String token) {
        this.prefix = prefix;
        this.token = token;
    }

    public Token() {
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

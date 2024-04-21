package com.hoaxify.ws.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "hoaxify")
@Configuration
public class HoaxifyProperties {

    private Email email;

    private Client client;

    private Storege storage = new Storege();

    public Storege getStorage() {
        return storage;
    }

    public void setStorage(Storege storage) {
        this.storage = storage;
    }

    public static record Email(
            String username,
            String password,
            String host,
            int port,
            String from) {

    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public static record Client(
            String host) {

    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public static class Storege {
        String root = "uploads";
        String profile = "profile";

        public String getRoot() {
            return root;
        }

        public void setRoot(String root) {
            this.root = root;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }
    }
}

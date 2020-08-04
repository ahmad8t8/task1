package com.assignment.user.json;

import javax.validation.constraints.NotBlank;

public class AuthRequest {
    @NotBlank(message = "username and password should not be empty")
    private String username;
    @NotBlank(message = "username and password should not be empty")
    private String password;

    public AuthRequest() {
    }

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

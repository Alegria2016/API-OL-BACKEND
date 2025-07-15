package com.backend.authentication;

import com.backend.dto.UserResponseDTO;

public class AuthenticationResponse {

    private boolean valid;
    private String token;

    private UserResponseDTO user;

    public AuthenticationResponse(String token,boolean valid, UserResponseDTO user) {
        this.token = token;
        this.valid = valid;
        this.user = user;
    }


    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }
}

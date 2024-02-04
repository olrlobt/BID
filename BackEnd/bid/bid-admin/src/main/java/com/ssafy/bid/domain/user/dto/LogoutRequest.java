package com.ssafy.bid.domain.user.dto;


public class LogoutRequest {
    private final String token;

    public LogoutRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

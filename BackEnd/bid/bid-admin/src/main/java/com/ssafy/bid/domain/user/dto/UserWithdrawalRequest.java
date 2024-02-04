package com.ssafy.bid.domain.user.dto;

import lombok.Getter;

@Getter
public class UserWithdrawalRequest {
    private String password;

    public UserWithdrawalRequest() {
    }

    public UserWithdrawalRequest(String password) {
        this.password = password;
    }
}

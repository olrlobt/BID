package com.ssafy.bid.domain.user.dto;

import lombok.Getter;

@Getter
public class FindUserIDRequest {
    private String name;
    private String tel;

    public FindUserIDRequest(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }
}

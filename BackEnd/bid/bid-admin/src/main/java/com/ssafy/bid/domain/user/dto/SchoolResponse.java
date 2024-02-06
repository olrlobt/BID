package com.ssafy.bid.domain.user.dto;

import lombok.Getter;

@Getter
public class SchoolResponse {
    private Integer no;
    private String name;
    private String region;
    private String code;

    public SchoolResponse(Integer no, String name, String region, String code) {
        this.no = no;
        this.name = name;
        this.region = region;
        this.code = code;
    }
}

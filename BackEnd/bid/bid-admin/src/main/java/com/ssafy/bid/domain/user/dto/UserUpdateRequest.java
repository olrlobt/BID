package com.ssafy.bid.domain.user.dto;

import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.User;
import lombok.Getter;

@Getter
public class UserUpdateRequest {
    private String name;
    private Integer schoolNo;
    private String tel;

    public UserUpdateRequest(String name, Integer schoolNo, String tel) {
        this.name = name;
        this.schoolNo = schoolNo;
        this.tel = tel;
    }

    public Admin toEntity(User existingUser) {
        return new Admin(
                existingUser.getId(),
                existingUser.getPassword(),
                this.name,
                this.schoolNo,
                this.tel
        );
    }
}

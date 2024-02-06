package com.ssafy.bid.domain.user.security;

import com.ssafy.bid.domain.user.User;
import lombok.Getter;

@Getter
public class CustomUserInfo {
    private final String id;
    private final String password;
    private final String name;
    private final String schoolName;
    private final String teacherName;

    public CustomUserInfo(User user, String schoolName, String teacherName) {
        this.id = user.getId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.schoolName = schoolName;
        this.teacherName = teacherName;
    }
}

package com.ssafy.bid.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull(message = "아이디 입력은 필수입니다.")
    private String id;

    @NotNull(message = "비밀번호 입력은 필수입니다.")
    private String password;
}

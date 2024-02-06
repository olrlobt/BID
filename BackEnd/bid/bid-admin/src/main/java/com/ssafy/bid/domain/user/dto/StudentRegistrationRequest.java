package com.ssafy.bid.domain.user.dto;

import com.ssafy.bid.domain.user.Student;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class StudentRegistrationRequest {
    private Integer schoolNo;
    private String id;
    private String password;
    private String name;
    private Integer gradeNo;

    public Student toEntity(PasswordEncoder passwordEncoder) {
        return Student.builder()
                .id(this.id)
                .password(passwordEncoder.encode(this.password))
                .name(this.name)
                .schoolNo(this.schoolNo)
                .gradeNo(this.gradeNo)
                .birthDate(null)
                .asset(0)
                .ballCount(0)
                .profileImgUrl(null)
                .build();
    }
}

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
        return new Student(
            this.id,
            passwordEncoder.encode(this.password),
            this.name,
            this.schoolNo,
            null,
            0,
            0,
            null,
            this.gradeNo
        );
    }
}

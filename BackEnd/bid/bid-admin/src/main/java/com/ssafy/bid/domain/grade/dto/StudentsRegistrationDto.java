package com.ssafy.bid.domain.grade.dto;

import com.ssafy.bid.domain.user.Student;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StudentsRegistrationDto {
    private String id;
    private String password;
    private String name;
    private String birthDate;
    private Integer gradeNo;

    public Student toEntity(PasswordEncoder passwordEncoder, Integer schoolNo) {
        return Student.builder()
                .id(this.id)
                .password(passwordEncoder.encode(this.password))
                .name(this.name)
                .schoolNo(schoolNo)
                .birthDate(this.birthDate)
                .gradeNo(this.gradeNo)
                .build();
    }
}

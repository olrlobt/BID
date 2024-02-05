package com.ssafy.bid.domain.user.service;

import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.dto.StudentsResponse;
import com.ssafy.bid.domain.user.repository.UserRepository;
import com.ssafy.bid.model.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;

    @Test
    @DisplayName("관리자 학생 목록 조회 서비스 테스트")
    void adminStudentsSearchTest() {
        // given
        Student studentA = UserFixture.create("6-3-27");
        Student studentB = UserFixture.create("6-3-11");
        Student savedStudentA = userRepository.save(studentA);
        Student savedStudentB = userRepository.save(studentB);
        List<Student> savedStudents = List.of(savedStudentA, savedStudentB);

        // when
        List<StudentsResponse> responses = userService.findStudents(savedStudentA.getGradeNo());

        // then
        // TODO
    }
}

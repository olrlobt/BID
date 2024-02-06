package com.ssafy.bid.domain.user.security;

import com.ssafy.bid.domain.grade.Grade;
import com.ssafy.bid.domain.user.School;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.repository.GradeRepository;
import com.ssafy.bid.domain.user.repository.SchoolRepository;
import com.ssafy.bid.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final SchoolRepository schoolRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserById(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다."));

        if (!(user instanceof Student)) {
            throw new UsernameNotFoundException("학생 정보가 아닙니다.");
        }
        Student student = (Student) user;
        Grade grade = gradeRepository.findById(student.getGradeNo())
                .orElseThrow(() -> new EntityNotFoundException("학급 정보를 찾을 수 없습니다."));

        String schoolName = schoolRepository.findById(student.getSchoolNo())
                .map(School::getName)
                .orElseThrow(() -> new EntityNotFoundException("학교 정보를 찾을 수 없습니다."));

        String teacherName = userRepository.findById(grade.getUserNo())
                .map(User::getName)
                .orElseThrow(() -> new EntityNotFoundException("선생님 정보를 찾을 수 없습니다."));

        CustomUserInfo customUserInfo = new CustomUserInfo(student, schoolName, teacherName);

        return new CustomUserDetails(customUserInfo);
    }
}

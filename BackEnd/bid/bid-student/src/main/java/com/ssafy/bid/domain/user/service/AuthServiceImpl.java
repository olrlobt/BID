package com.ssafy.bid.domain.user.service;

import com.ssafy.bid.domain.grade.Grade;
import com.ssafy.bid.domain.user.School;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.TokenBlacklist;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.dto.LoginRequest;
import com.ssafy.bid.domain.user.dto.TokenBlacklistDTO;
import com.ssafy.bid.domain.user.repository.GradeRepository;
import com.ssafy.bid.domain.user.repository.SchoolRepository;
import com.ssafy.bid.domain.user.repository.TokenBlacklistRepository;
import com.ssafy.bid.domain.user.repository.UserRepository;
import com.ssafy.bid.domain.user.security.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final SchoolRepository schoolRepository;
    private final PasswordEncoder encoder;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Override
    @Transactional(readOnly = true)
    public String login(LoginRequest request) {
        User user = userRepository.findUserById(request.getId())
                .orElseThrow(() -> new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다."));

        if (!(user instanceof Student)) {
            throw new UsernameNotFoundException("학생 정보가 아닙니다.");
        }
        Student student = (Student) user;
        if (!encoder.matches(request.getPassword(), student.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        Grade grade = gradeRepository.findById(student.getGradeNo())
                .orElseThrow(() -> new EntityNotFoundException("학급 정보를 찾을 수 없습니다."));

        String schoolName = schoolRepository.findById(student.getSchoolNo())
                .map(School::getName)
                .orElseThrow(() -> new EntityNotFoundException("학교 정보를 찾을 수 없습니다."));

        String teacherName = userRepository.findById(grade.getUserNo())
                .map(User::getName)
                .orElseThrow(() -> new EntityNotFoundException("선생님 정보를 찾을 수 없습니다."));

        return jwtUtil.createTokenWithTeacherInfo(student.getId(), schoolName, teacherName);
    }

    @Override
    @Transactional
    public void logout(String token) {
        String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        TokenBlacklistDTO tokenBlacklistDTO = new TokenBlacklistDTO(actualToken);
        TokenBlacklist blacklistedToken = tokenBlacklistDTO.toEntity();
        tokenBlacklistRepository.save(blacklistedToken);
    }
}

package com.ssafy.bid.domain.user.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.School;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.dto.AccountFindRequest;
import com.ssafy.bid.domain.user.dto.AccountFindResponse;
import com.ssafy.bid.domain.user.dto.AccountsFindResponse;
import com.ssafy.bid.domain.user.dto.AdminInfo;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;
import com.ssafy.bid.domain.user.dto.LoginRequest;
import com.ssafy.bid.domain.user.dto.LoginResponse;
import com.ssafy.bid.domain.user.dto.StudentFindRequest;
import com.ssafy.bid.domain.user.dto.StudentFindResponse;
import com.ssafy.bid.domain.user.dto.StudentInfo;
import com.ssafy.bid.domain.user.dto.TokenResponse;
import com.ssafy.bid.domain.user.dto.UserCouponsFindResponse;
import com.ssafy.bid.domain.user.repository.CoreUserRepository;
import com.ssafy.bid.domain.user.repository.SchoolRepository;
import com.ssafy.bid.domain.user.security.JwtTokenProvider;
import com.ssafy.bid.global.error.exception.AuthenticationFailedException;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;
import com.ssafy.bid.global.util.SecurityUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CoreUserServiceImpl implements CoreUserService {

	private final JwtTokenProvider jwtTokenProvider;
	private final CoreUserRepository coreUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final RedisTemplate redisTemplate;
	private final SchoolRepository schoolRepository;

	private User authenticateUser(String id, String password) {
		return coreUserRepository.findById(id)
			.filter(user -> passwordEncoder.matches(password, user.getPassword()))
			.orElseThrow(() -> new IllegalArgumentException("해당하는 아이디의 유저 없음 또는 비밀번호 불일치"));
	}

	@Override
	@Transactional
	public LoginResponse login(LoginRequest loginRequest, boolean isAdmin) {
		User user = authenticateUser(loginRequest.getId(), loginRequest.getPassword());
		CustomUserInfo userInfo = createCustomUserInfo(user);

		TokenResponse tokenResponse = jwtTokenProvider.createToken(userInfo);

		redisTemplate.opsForValue().set("RT:" + user.getNo(), tokenResponse.getRefreshToken(), 7, TimeUnit.DAYS);

		if (user instanceof Student student) {
			if (isAdmin) {
				throw new AuthenticationFailedException("로그인: 알맞은 권한이 아님.");
			}

			School school = schoolRepository.findById(user.getSchoolNo())
				.orElseThrow(() -> new ResourceNotFoundException("학교 없음.", user.getSchoolNo()));

			List<StudentInfo> studentList = coreUserRepository.findByGradeNo(student.getGradeNo());
			StudentInfo studentInfo = null;
			for (StudentInfo info : studentList) {
				if (info.getNo() == user.getNo()) {
					studentInfo = new StudentInfo(info.getNo(), info.getGradeNo(), info.getName(),
						info.getProfileImgUrl(), school.getName());
				}
			}
			return new LoginResponse(tokenResponse, studentList, studentInfo, null);
		} else {
			if (!isAdmin) {
				throw new AuthenticationFailedException("로그인: 알맞은 권한이 아님.");
			}

			School school = schoolRepository.findById(user.getSchoolNo())
				.orElseThrow(() -> new ResourceNotFoundException("학교 없음.", user.getSchoolNo()));
			AdminInfo adminInfo = new AdminInfo(user.getNo(), school.getNo(), school.getCode(), school.getName(),
				user.getName());

			return new LoginResponse(tokenResponse, null, null, adminInfo);
		}
	}

	private CustomUserInfo createCustomUserInfo(User user) {
		return CustomUserInfo.builder()
			.no(user.getNo())
			.id(user.getId())
			.password(user.getPassword())
			.name(user.getName())
			.schoolNo(user.getSchoolNo())
			.gradeNo(user instanceof Student student ? student.getGradeNo() : -1)
			.tel(user instanceof Admin admin ? admin.getTel() : null)
			.build();
	}

	@Override
	@Transactional
	public void logout(int userNo, HttpServletRequest request) {
		String token = SecurityUtils.getAccessToken(request);

		if (!jwtTokenProvider.validateToken(token)) {
			throw new AuthenticationFailedException("로그아웃하려는 엑세스 토큰이 검증되지 않았음.");
		}

		if (redisTemplate.opsForValue().get("RT:" + userNo) != null) {
			redisTemplate.delete("RT:" + userNo);
		}

		redisTemplate.opsForValue().set(token, "logout", 7, TimeUnit.DAYS);
	}

	@Override
	public StudentFindResponse findStudent(int userNo, StudentFindRequest studentFindRequest) {
		List<UserCouponsFindResponse> couponResponses = coreUserRepository.findUserCouponsByUserNo(userNo);
		List<AccountsFindResponse> accountResponses = coreUserRepository.findAccountsByUserNo(userNo,
			studentFindRequest);

		StudentFindResponse response = coreUserRepository.findStudentByUserNo(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("학생상세조회-회원PK", userNo));

		response.completeResponse(couponResponses, accountResponses);
		return response;
	}

	@Override
	public List<AccountFindResponse> findAccount(int userNo, AccountFindRequest accountFindRequest) {
		return coreUserRepository.findAccountByUserNo(userNo, accountFindRequest);
	}

	@Override
	@Transactional
	public void resetAttendance() {
		coreUserRepository.findAllStudentsAndSalaries()
			.forEach(response -> response.calculateSalary(response.getSalary()));
	}

	@Override
	@Transactional
	public void calculateIncomeLevel() {
		coreUserRepository.findAllIncomes().forEach(response -> {
			response.calculateIncomeLevel();
			response.getStudent().calculateTaxRate(response.getTaxRate());
		});
	}
}

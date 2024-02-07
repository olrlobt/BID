package com.ssafy.bid.domain.user.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.TokenBlacklist;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.dto.AccountFindRequest;
import com.ssafy.bid.domain.user.dto.AccountFindResponse;
import com.ssafy.bid.domain.user.dto.AccountsFindResponse;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;
import com.ssafy.bid.domain.user.dto.LoginRequest;
import com.ssafy.bid.domain.user.dto.StudentFindRequest;
import com.ssafy.bid.domain.user.dto.StudentFindResponse;
import com.ssafy.bid.domain.user.dto.UserCouponsFindResponse;
import com.ssafy.bid.domain.user.repository.CoreTokenBlacklistRepository;
import com.ssafy.bid.domain.user.repository.CoreUserRepository;
import com.ssafy.bid.domain.user.security.JwtTokenProvider;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CoreUserServiceImpl implements CoreUserService {

	private final JwtTokenProvider jwtTokenProvider;
	private final CoreUserRepository coreUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final CoreTokenBlacklistRepository coreTokenBlacklistRepository;

	@Override
	public String login(LoginRequest loginRequest) {
		String id = loginRequest.getId();
		String password = loginRequest.getPassword();

		User user = coreUserRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 아이디의 유저 없음"));//TODO: 커스텀 예외처리

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new IllegalArgumentException("비밀번호 불일치"); // TODO: 커스텀 예외처리
		}

		CustomUserInfo userInfo = CustomUserInfo.builder()
			.no(user.getNo())
			.id(user.getId())
			.password(user.getPassword())
			.name(user.getName())
			.schoolNo(user.getSchoolNo())
			.gradeNo(user instanceof Student ? ((Student)user).getGradeNo() : -1)
			.tel(user instanceof Admin ? ((Admin)user).getTel() : null)
			.build();

		return jwtTokenProvider.createAccessToken(userInfo);
	}

	@Override
	@Transactional
	public void logout(String token) {
		String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;

		TokenBlacklist blacklist = TokenBlacklist.builder()
			.token(actualToken)
			.expiryDate(LocalDateTime.now().plusMinutes(30))
			.build();

		coreTokenBlacklistRepository.save(blacklist);
	}

	@Override
	public StudentFindResponse findStudent(int userNo, StudentFindRequest studentFindRequest) {
		// 학생PK를 통해 회원쿠폰목록 조회, 기간내 입출금내역목록 조회
		List<UserCouponsFindResponse> couponResponses = coreUserRepository.findUserCouponsByUserNo(userNo);
		List<AccountsFindResponse> accountResponses = coreUserRepository.findAccountsByUserNo(userNo,
			studentFindRequest);

		// 학생PK를 통해 회원 상세(통계) 정보 조회 및 파라미터 검증
		StudentFindResponse response = coreUserRepository.findStudentByUserNo(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("학생상세조회-회원PK", userNo));

		// 응답 데이터 완성
		response.completeResponse(couponResponses, accountResponses);

		// 응답 반환
		return response;
	}

	@Override
	public List<AccountFindResponse> findAccount(int userNo, AccountFindRequest accountFindRequest) {
		return coreUserRepository.findAccountByUserNo(userNo, accountFindRequest);
	}
}

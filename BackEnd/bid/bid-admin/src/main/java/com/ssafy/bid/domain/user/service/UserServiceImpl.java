package com.ssafy.bid.domain.user.service;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.dto.AdminSaveRequest;
import com.ssafy.bid.domain.user.dto.BallsFindResponse;
import com.ssafy.bid.domain.user.dto.SchoolsFindResponse;
import com.ssafy.bid.domain.user.dto.StudentSaveRequest;
import com.ssafy.bid.domain.user.dto.StudentsFindResponse;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendRequest;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendResponse;
import com.ssafy.bid.domain.user.dto.UserIdFindRequest;
import com.ssafy.bid.domain.user.dto.UserUpdateRequest;
import com.ssafy.bid.domain.user.dto.UserDeleteRequest;
import com.ssafy.bid.domain.user.repository.UserRepository;
import com.ssafy.bid.global.error.exception.InvalidParameterException;
import com.ssafy.bid.global.error.exception.ResourceAlreadyExistsException;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final MessageService messageService;

	@Override //TODO: 회원가입 인증코드 전송 로직 수정
	public TelAuthenticationSendResponse sendTelAuthentication(
		TelAuthenticationSendRequest telAuthenticationSendRequest
	) {
		String code = RandomStringUtils.randomNumeric(6);
		messageService.sendAuthenticationCode(telAuthenticationSendRequest.getTel(), code);

		return new TelAuthenticationSendResponse(code);
	}

	@Override //TODO: 아이디 중복여부 체크 인덱스 조회로 수정
	public boolean isIdDuplicate(String id) {
		return userRepository.checkExistsById(id);
	}

	@Override
	public List<SchoolsFindResponse> findSchools(String name) {
		return userRepository.findSchoolsByName(name);
	}

	@Override
	@Transactional
	public void saveAdmin(AdminSaveRequest request) {
		if (userRepository.checkExistsById(request.getId())) {
			throw new ResourceAlreadyExistsException("관리자회원가입-ID", request.getId());
		}

		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new InvalidParameterException("관리자회원가입-PW", request.getPassword());
		}

		Admin admin = request.toEntity(passwordEncoder);
		userRepository.save(admin);
	}

	@Override
	@Transactional
	public void saveStudent(StudentSaveRequest request) {
		if (userRepository.checkExistsById(request.getId())) {
			throw new ResourceAlreadyExistsException("학생회원가입-ID", request.getId());
		}

		Student student = request.toEntity(passwordEncoder);
		userRepository.save(student);
	}

	@Override
	public List<StudentsFindResponse> findAllStudents(int gradeNo) {
		List<StudentsFindResponse> responses = userRepository.findAllStudentByGradeNo(gradeNo);

		if (responses.isEmpty()) {
			throw new ResourceNotFoundException("학생목록조회-학급PK", gradeNo);
		}

		return responses;
	}

	@Override
	public String findUserId(UserIdFindRequest request) {
		return userRepository.findUserIdByNameAndTel(request.getName(), request.getTel())
			.orElseThrow(() -> new ResourceNotFoundException("관리자ID조회-이름", request.getName()));
	}

	@Override
	@Transactional
	public void updateUser(Integer userNo, UserUpdateRequest request) {
		Admin admin = userRepository.findAdminByUserNo(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("회원수정-회원PK", userNo));

		admin.update(request.getName(), request.getSchoolNo(), request.getTel());
	}

	@Override
	@Transactional
	public void deleteUser(Integer userNo, UserDeleteRequest request) {
		User user = userRepository.findById(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("회원탈퇴-회원PK", userNo));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new InvalidParameterException("회원탈퇴-패스워드", request.getPassword());
		}

		userRepository.delete(user);
	}

	@Override
	public List<BallsFindResponse> findAllBalls(int gradeNo) {
		List<BallsFindResponse> responses = userRepository.findAllBallsByGradeNo(gradeNo);

		if (responses.isEmpty()) {
			throw new ResourceNotFoundException("보유공개수조회-학급PK", gradeNo);
		}

		return userRepository.findAllBallsByGradeNo(gradeNo);
	}

	@Override
	@Transactional
	public void resetAllBalls(int gradeNo) {
		List<Student> responses = userRepository.findAllStudentsByGradeNo(gradeNo);

		if (responses.isEmpty()) {
			throw new ResourceNotFoundException("학생목록조회-학급PK", gradeNo);
		}

		responses.forEach(Student::resetBalls);
	}
}

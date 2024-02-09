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
import com.ssafy.bid.domain.user.dto.StudentsGetResponse;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendRequest;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendResponse;
import com.ssafy.bid.domain.user.dto.UserDeleteRequest;
import com.ssafy.bid.domain.user.dto.UserIdFindRequest;
import com.ssafy.bid.domain.user.dto.UserUpdateRequest;
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
	public boolean isDuplicated(String id) {
		return userRepository.existsById(id);
	}

	@Override
	public List<SchoolsFindResponse> getSchools(String name) {
		return userRepository.findSchoolsByName(name);
	}

	@Override
	@Transactional
	public void saveAdmin(AdminSaveRequest request) {
		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new InvalidParameterException("패스워드와 패스워드 확인 불일치..", request.getPassword());
		}
		Admin admin = request.toEntity(passwordEncoder);
		userRepository.save(admin);
	}

	@Override
	@Transactional
	public void saveStudent(StudentSaveRequest request) {
		if (userRepository.existsById(request.getId())) {
			throw new ResourceAlreadyExistsException("회원가입하려는 아이디가 중복됨.", request.getId());
		}
		Student student = request.toEntity(passwordEncoder);
		userRepository.save(student);
	}

	@Override
	@Transactional
	public void resetStudentPassword(int userNo) {
		Student student = userRepository.findStudentByUserNo(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("패스워드 초기화하려는 User 엔티티가 없음.", userNo));
		student.resetPassword(passwordEncoder);
	}

	@Override
	public List<StudentsGetResponse> getStudents(int gradeNo) {
		List<StudentsGetResponse> responses = userRepository.findAllStudentByGradeNo(gradeNo);
		if (responses.isEmpty()) {
			throw new ResourceNotFoundException("조회하려는 User 가 없음.", gradeNo);
		}
		return responses;
	}

	@Override
	public String getUserId(UserIdFindRequest request) {
		return userRepository.findUserIdByNameAndTel(request.getName(), request.getTel())
			.orElseThrow(() -> new ResourceNotFoundException("조회하려는 User 가 없음.", request.getName()));
	}

	@Override
	@Transactional
	public void updateUser(Integer userNo, UserUpdateRequest request) {
		Admin admin = userRepository.findAdminByUserNo(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("수정하려는 User 가 없음.", userNo));

		admin.update(request.getName(), request.getSchoolNo(), request.getTel());
	}

	@Override
	@Transactional
	public void deleteUser(Integer userNo, UserDeleteRequest request) {
		User user = userRepository.findById(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("탈퇴하려는 User 가 없음.", userNo));
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new InvalidParameterException("패스워드와 패스워드 확인 불일치.", request.getPassword());
		}
		userRepository.delete(user);
	}

	@Override
	public List<BallsFindResponse> getAllBalls(int gradeNo) {
		List<BallsFindResponse> responses = userRepository.findAllBallsByGradeNo(gradeNo);
		if (responses.isEmpty()) {
			throw new ResourceNotFoundException("조회하려는 Grade 가 없음.", gradeNo);
		}
		return userRepository.findAllBallsByGradeNo(gradeNo);
	}

	@Override
	@Transactional
	public void resetAllBalls(int gradeNo) {
		List<Student> responses = userRepository.findAllStudentsByGradeNo(gradeNo);
		if (responses.isEmpty()) {
			throw new ResourceNotFoundException("조회하려는 User 가 없음.", gradeNo);
		}
		responses.forEach(Student::resetBalls);
	}
}

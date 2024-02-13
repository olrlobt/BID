package com.ssafy.bid.domain.user.service;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.grade.Grade;
import com.ssafy.bid.domain.grade.repository.GradeRepository;
import com.ssafy.bid.domain.grade.repository.StudentRepository;
import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.TelAuthentication;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.UserType;
import com.ssafy.bid.domain.user.dto.AdminPasswordUpdateRequest;
import com.ssafy.bid.domain.user.dto.AdminSaveRequest;
import com.ssafy.bid.domain.user.dto.BallsFindResponse;
import com.ssafy.bid.domain.user.dto.SchoolsFindResponse;
import com.ssafy.bid.domain.user.dto.StudentSaveRequest;
import com.ssafy.bid.domain.user.dto.StudentUpdateRequest;
import com.ssafy.bid.domain.user.dto.StudentsGetResponse;
import com.ssafy.bid.domain.user.dto.TelAuthenticationCheckRequest;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendRequest;
import com.ssafy.bid.domain.user.dto.UserDeleteRequest;
import com.ssafy.bid.domain.user.dto.UserIdFindRequest;
import com.ssafy.bid.domain.user.dto.UserUpdateRequest;
import com.ssafy.bid.domain.user.repository.TelAuthenticationRepository;
import com.ssafy.bid.domain.user.repository.UserRepository;
import com.ssafy.bid.global.error.exception.AuthenticationFailedException;
import com.ssafy.bid.global.error.exception.AuthorizationFailedException;
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
	private final TelAuthenticationRepository telAuthenticationRepository;
	private final GradeRepository gradeRepository;
	private final StudentRepository studentRepository;

	@Override
	@Transactional
	public void sendTelAuthentication(
		TelAuthenticationSendRequest request
	) {
		boolean exists = userRepository.existsByIdAndTel(request.getId(), request.getTel());
		if (!exists) {
			throw new ResourceNotFoundException("인증코드전송: 해당 id와 tel 을 가진 User 엔티티가 없음.", request.getTel());
		}

		TelAuthentication telAuthentication = createAndSendAuthenticationCode(request.getTel());
		telAuthenticationRepository.save(telAuthentication);
	}

	@Override
	@Transactional
	public void sendRegisterTelAuthentication(TelAuthenticationSendRequest request) {
		TelAuthentication telAuthentication = createAndSendAuthenticationCode(request.getTel());
		telAuthenticationRepository.save(telAuthentication);
	}

	private TelAuthentication createAndSendAuthenticationCode(String tel) {
		String code = RandomStringUtils.randomNumeric(6);
		messageService.sendAuthenticationCode(tel, code);

		return createTelAuthentication(tel, code);
	}

	private TelAuthentication createTelAuthentication(String tel, String code) {
		return TelAuthentication.builder()
			.tel(tel)
			.code(code)
			.isAuthenticated(false)
			.build();
	}

	@Override
	@Transactional
	public boolean checkTelAuthentication(TelAuthenticationCheckRequest request) {
		TelAuthentication telAuthentication = telAuthenticationRepository.findById(request.getTel())
			.orElseThrow(() -> new InvalidParameterException("인증코드확인: 해당 번호로 전송된 인증코드가 만료됐거나 없음.", request.getTel()));

		boolean equals = telAuthentication.getCode().equals(request.getCode());
		if (!equals) {
			throw new AuthenticationFailedException("인증코드확인: 인증코드 불일치.");
		}

		telAuthentication.authenticate();
		telAuthenticationRepository.save(telAuthentication);
		return equals;
	}

	@Override
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
			throw new InvalidParameterException("회원가입: 패스워드와 패스워드 확인 불일치.", request.getPassword());
		}

		TelAuthentication telAuthentication = telAuthenticationRepository.findById(request.getTel())
			.orElseThrow(() -> new AuthenticationFailedException("회원가입: 인증되지 않은 회원임."));
		if (!telAuthentication.getIsAuthenticated()) {
			throw new AuthenticationFailedException("회원가입: 인증되지 않은 회원임.");
		}

		Admin admin = request.toEntity(passwordEncoder);
		userRepository.save(admin);
	}

	@Override
	@Transactional
	public void saveStudent(UserType userType, StudentSaveRequest request) {
		if (!userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("학생등록: Admin 권한 사용자가 아님.");
		}

		Grade grade = gradeRepository.findById(request.getGradeNo())
			.orElseThrow(() -> new ResourceNotFoundException("학생등록: 학생을 등록하려는 Grade 엔티티가 없음."));

		String studentId = generateStudentId(grade, request.getNumber());

		if (userRepository.existsById(studentId)) {
				throw new ResourceAlreadyExistsException("학생등록: 회원가입하려는 아이디가 중복됨.", studentId);
		}

		Student student = request.toEntity(passwordEncoder, studentId);
		userRepository.save(student);
	}

	private String generateStudentId(Grade grade, int number) {
		return grade.getSchoolCode() + String.valueOf(grade.getYear())
			+ String.format("%02d", grade.getClassRoom()) + String.format("%02d", number);
	}

	@Override
	@Transactional
	public void updateStudent(UserType userType, int userNo, StudentUpdateRequest request) {
		if (!userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("학생정보수정: 학생 정보를 수정할 권한이 없습니다.");
		}

		Student student = studentRepository.findById(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("학생정보수정: 정보를 수정하려는 Student가 없음", userNo));

		Grade grade = gradeRepository.findById(request.getGradeNo())
			.orElseThrow(() -> new ResourceNotFoundException("학생정보수정: 학생정보를 수정하려는 Grade가 없음."));

		String newStudentId = generateStudentId(grade, request.getNumber());
		student.updateStudentInfo(newStudentId, request.getName(), request.getBirthDate(), passwordEncoder);
		userRepository.save(student);
	}

	@Override
	@Transactional
	public void deleteStudent(UserType userType, int userNo) {
		if (!userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("학생삭제: 학생을 삭제할 권한이 없습니다.");
		}

		User user = userRepository.findById(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("학생삭제: 삭제하려는 Student가 없음", userNo));
		userRepository.delete(user);
	}

	@Override
	@Transactional
	public void resetStudentPassword(UserType userType, int userNo) {
		if (!userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("학생패스워드초기화: Admin 권한 사용자가 아님.");
		}

		Student student = userRepository.findStudentByUserNo(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("학생패스워드초기화: 패스워드 초기화하려는 Student 엔티티가 없음.", userNo));

		student.resetPassword(passwordEncoder);
	}

	@Override
	@Transactional
	public void updateAdminPassword(AdminPasswordUpdateRequest request) {
		Admin admin = userRepository.findAdminById(request.getId())
			.orElseThrow(() -> new ResourceNotFoundException("관리자패스워드변경: 패스워드 수정하려는 Admin 엔티티가 없음.", request.getId()));

		if (!request.getNewPassword().equals(request.getNewPasswordCheck())) {
			throw new InvalidParameterException("관리자패스워드변경: 패스워드와 패스워드 확인 불일치.", request.getNewPassword());
		}

		TelAuthentication telAuthentication = telAuthenticationRepository.findById(admin.getTel())
			.orElseThrow(() -> new AuthenticationFailedException("관리자패스워드변경: 인증되지 않은 회원임."));
		if (!telAuthentication.getIsAuthenticated()) {
			throw new AuthenticationFailedException("관리자패스워드변경: 인증되지 않은 회원임.");
		}

		admin.changePassword(passwordEncoder.encode(request.getNewPassword()));
	}

	@Override
	public List<StudentsGetResponse> getStudents(UserType userType, int gradeNo) {
		if (!userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("학생목록조회: Admin 권한 사용자가 아님.");
		}

		List<StudentsGetResponse> responses = userRepository.findAllStudentByGradeNo(gradeNo);
		if (responses.isEmpty()) {
			throw new ResourceNotFoundException("학생목록조회: 조회하려는 Student 가 없음.", gradeNo);
		}

		return responses;
	}

	@Override
	public String getUserId(UserIdFindRequest request) {
		return userRepository.findUserIdByNameAndTel(request.getName(), request.getTel())
			.orElseThrow(() -> new ResourceNotFoundException("관리자ID조회: 조회하려는 Admin 가 없음.", request.getName()));
	}

	@Override
	@Transactional
	public void updateUser(UserType userType, int userNo, UserUpdateRequest request) {
		if (!userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("관리자수정: Admin 권한 사용자가 아님.");
		}

		Admin admin = userRepository.findAdminByUserNo(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("관리자수정: 수정하려는 Admin 가 없음.", userNo));

		admin.update(request.getName(), request.getSchoolNo(), request.getTel());
	}

	@Override
	@Transactional
	public void deleteUser(UserType userType, int userNo, UserDeleteRequest request) {
		if (!userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("관리자탈퇴: Admin 권한 사용자가 아님.");
		}

		User user = userRepository.findById(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("관리자탈퇴: 탈퇴하려는 Admin 가 없음.", userNo));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new InvalidParameterException("관리자탈퇴: 패스워드와 패스워드 확인 불일치.", request.getPassword());
		}
		userRepository.delete(user);
	}

	@Override
	public List<BallsFindResponse> getAllBalls(UserType userType, int gradeNo) {
		if (!userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("대포목록조회: Admin 권한 사용자가 아님.");
		}

		List<BallsFindResponse> responses = userRepository.findAllBallsByGradeNo(gradeNo);
		if (responses.isEmpty()) {
			throw new ResourceNotFoundException("대포목록조회: 조회하려는 Grade 가 없음.", gradeNo);
		}

		return userRepository.findAllBallsByGradeNo(gradeNo);
	}

	@Override
	@Transactional
	public void resetAllBalls(UserType userType, int gradeNo) {
		if (!userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("대포초기화: Admin 권한 사용자가 아님.");
		}

		List<Student> responses = userRepository.findAllStudentsByGradeNo(gradeNo);
		if (responses.isEmpty()) {
			throw new ResourceNotFoundException("대포초기화: 조회하려는 Student 가 없음.", gradeNo);
		}

		responses.forEach(Student::resetBalls);
	}
}

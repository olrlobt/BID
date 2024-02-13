package com.ssafy.bid.domain.grade.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.avatar.UserAvatar;
import com.ssafy.bid.domain.grade.Grade;
import com.ssafy.bid.domain.grade.dto.GradeListGetResponse;
import com.ssafy.bid.domain.grade.dto.GradePeriodRequest;
import com.ssafy.bid.domain.grade.dto.GradeSaveRequest;
import com.ssafy.bid.domain.grade.dto.GradeUpdateRequest;
import com.ssafy.bid.domain.grade.dto.SalaryUpdateRequest;
import com.ssafy.bid.domain.grade.dto.SavingPeriodUpdateRequest;
import com.ssafy.bid.domain.grade.repository.GradeRepository;
import com.ssafy.bid.domain.grade.repository.StudentRepository;
import com.ssafy.bid.domain.gradeperiod.GradePeriod;
import com.ssafy.bid.domain.gradeperiod.repository.GradePeriodRepository;
import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.UserType;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;
import com.ssafy.bid.domain.user.repository.UserAvatarRepository;
import com.ssafy.bid.global.error.exception.AuthorizationFailedException;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GradeServiceImpl implements GradeService {

	private final GradeRepository gradeRepository;
	private final StudentRepository studentRepository;
	private final GradePeriodRepository gradePeriodRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserAvatarRepository userAvatarRepository;

	@Override
	@Transactional
	public void saveGrade(int userNo, GradeSaveRequest request) {
		Grade grade = request.toEntity();
		Grade savedGrade = gradeRepository.save(grade);

		int schoolNo = request.getSchoolNo();
		List<Student> students = request.getStudentListSaveRequests().stream()
			.map(studentListSaveRequest -> studentListSaveRequest.toEntity(passwordEncoder, schoolNo, grade.getNo()))
			.toList();
		List<Student> savedStudents = studentRepository.saveAll(students);

		List<UserAvatar> userAvatars = savedStudents.stream()
			.map(student -> new UserAvatar(student.getNo(), 1))
			.toList();
		userAvatarRepository.saveAll(userAvatars);

		GradePeriodRequest gradePeriodRequest = new GradePeriodRequest(grade.getNo());
		List<GradePeriod> gradePeriods = gradePeriodRequest.toEntity();
		gradePeriodRepository.saveAll(gradePeriods);

		Admin admin = gradeRepository.findAdminByUserNo(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("학급등록: Admin 엔티티가 없음.", userNo));
		admin.alterMainGrade(savedGrade.getNo());
	}

	@Override
	public List<GradeListGetResponse> getGrades(CustomUserInfo userInfo) {
		if (!userInfo.getUserType().equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("학급목록조회: Admin 권한 사용자가 아님.");
		}

		return gradeRepository.findAllWithSchoolName(userInfo.getNo());
	}

	@Override
	@Transactional
	public void updateMainGrade(int userNo, GradeUpdateRequest gradeUpdateRequest) {
		Admin admin = gradeRepository.findAdminByUserNo(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("학급수정: 해당하는 Admin 엔티티가 없음.", userNo));

		admin.alterMainGrade(gradeUpdateRequest.getNo());
	}

	@Override
	@Transactional
	public void deleteGrade(UserType userType, int gradeNo) {
		if (!userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("학급삭제: Admin 권한 사용자가 아님.");
		}

		boolean exists = gradeRepository.existsByGradeNo(gradeNo);
		if (!exists) {
			throw new ResourceNotFoundException("학급삭제: 삭제하려는 Grade 엔티티가 없음.", gradeNo);
		}

		gradeRepository.deleteById(gradeNo);
		studentRepository.deleteAllByGradeNo(gradeNo);
	}

	@Override
	@Transactional
	public void updateSalary(UserType userType, int gradeNo, SalaryUpdateRequest salaryUpdateRequest) {
		if (!userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("주급수정: Admin 권한 사용자가 아님.");
		}

		Grade grade = gradeRepository.findById(gradeNo)
			.orElseThrow(() -> new ResourceNotFoundException("주급수정: 수정하려는 Grade 엔티티가 없음.", gradeNo));

		grade.updateSalary(salaryUpdateRequest.getSalary());
	}

	@Override
	@Transactional
	public void updateSavingPeriod(UserType userType, int gradeNo,
		SavingPeriodUpdateRequest savingPeriodUpdateRequest) {
		if (!userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("적금시간수정: Admin 권한 사용자가 아님.");
		}

		Grade grade = gradeRepository.findById(gradeNo)
			.orElseThrow(() -> new ResourceNotFoundException("적금시간수정: 수정하려는 Grade 엔티티가 없음.", gradeNo));

		grade.updateSavingPeriod(
			savingPeriodUpdateRequest.getTransferAlertPeriod(),
			savingPeriodUpdateRequest.getTransferPeriod()
		);
	}

	@Transactional
	public void holdBid(int gradeNo) {
		Grade grade = gradeRepository.findById(gradeNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당 학급이 없습니다.", gradeNo));

		grade.holdBid();
	}

	@Transactional
	public void unHoldBid(int gradeNo) {
		Grade grade = gradeRepository.findById(gradeNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당 학급이 없습니다.", gradeNo));

		grade.unHoldBid();
	}
}

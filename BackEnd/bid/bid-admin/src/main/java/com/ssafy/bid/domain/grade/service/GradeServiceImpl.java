package com.ssafy.bid.domain.grade.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.grade.Grade;
import com.ssafy.bid.domain.grade.dto.GradeListGetResponse;
import com.ssafy.bid.domain.grade.dto.GradeSaveRequest;
import com.ssafy.bid.domain.grade.dto.GradeUpdateRequest;
import com.ssafy.bid.domain.grade.dto.SalaryUpdateRequest;
import com.ssafy.bid.domain.grade.dto.SavingPeriodUpdateRequest;
import com.ssafy.bid.domain.grade.repository.GradeRepository;
import com.ssafy.bid.domain.grade.repository.StudentRepository;
import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GradeServiceImpl implements GradeService {

	private final GradeRepository gradeRepository;
	private final StudentRepository studentRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void saveGrade(GradeSaveRequest request) {
		Grade grade = request.toEntity();
		Grade savedGrade = gradeRepository.save(grade);

		int schoolNo = request.getSchoolNo();
		List<Student> students = request.getStudentListSaveRequests().stream()
			.map(studentListSaveRequest -> studentListSaveRequest.toEntity(passwordEncoder, schoolNo, grade.getNo()))
			.toList();
		studentRepository.saveAll(students);

		Admin admin = gradeRepository.findAdminByUserNo(request.getUserNo())
			.orElseThrow(() -> new ResourceNotFoundException("학급을 등록하려는 User 엔티티가 없음.", ""));
		admin.alterMainGrade(savedGrade.getNo());
	}

	@Override
	public List<GradeListGetResponse> getGrades(int userNo) {
		return gradeRepository.findAllWithSchoolName(userNo);
	}

	@Override
	@Transactional
	public void updateMainGrade(int userNo, GradeUpdateRequest gradeUpdateRequest) {
		Admin admin = gradeRepository.findAdminByUserNo(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("메인학급을 등록하려는 User 엔티티가 없음.", ""));
		admin.alterMainGrade(gradeUpdateRequest.getNo());
	}

	@Override
	@Transactional
	public void deleteGrade(int gradeNo) {
		boolean exists = gradeRepository.existsByGradeNo(gradeNo);
		if (!exists) {
			throw new ResourceNotFoundException("삭제하려는 Grade 엔티티가 존재하지 않음.", gradeNo);
		}
		gradeRepository.deleteById(gradeNo);
		studentRepository.deleteAllByGradeNo(gradeNo);
	}

	@Override
	@Transactional
	public void updateSalary(int gradeNo, SalaryUpdateRequest salaryUpdateRequest) {
		Grade grade = gradeRepository.findById(gradeNo)
			.orElseThrow(() -> new ResourceNotFoundException("수정하려는 Grade 엔티티가 존재하지 않음.", gradeNo));

		grade.updateSalary(salaryUpdateRequest.getSalary());
	}

	@Override
	@Transactional
	public void updateSavingPeriod(int gradeNo, SavingPeriodUpdateRequest savingPeriodUpdateRequest) {
		Grade grade = gradeRepository.findById(gradeNo)
			.orElseThrow(() -> new ResourceNotFoundException("수정하려는 Grade 엔티티가 존재하지 않음.", gradeNo));

		grade.updateSavingPeriod(
			savingPeriodUpdateRequest.getTransferAlertPeriod(),
			savingPeriodUpdateRequest.getTransferPeriod()
		);
	}
}

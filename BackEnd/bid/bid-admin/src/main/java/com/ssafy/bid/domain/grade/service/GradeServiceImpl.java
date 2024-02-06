package com.ssafy.bid.domain.grade.service;

import com.ssafy.bid.domain.grade.dto.GradeCreationRequest;
import com.ssafy.bid.domain.grade.dto.GradeDTO;
import com.ssafy.bid.domain.grade.dto.GradeStatisticsFindResponse;
import com.ssafy.bid.domain.grade.repository.SchoolRepository;
import com.ssafy.bid.domain.grade.repository.StudentRepository;
import com.ssafy.bid.domain.user.School;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.grade.Grade;
import com.ssafy.bid.domain.grade.dto.SalaryModifyRequest;
import com.ssafy.bid.domain.grade.dto.SavingPeriodModifyRequest;
import com.ssafy.bid.domain.grade.repository.GradeRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional
@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository, StudentRepository studentRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, SchoolRepository schoolRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
    }

    @Override
    @Transactional
    public void createGrade(GradeCreationRequest request) {
        Grade grade = new Grade(request.getSchoolCode(), request.getYear(), request.getClassRoom());
        gradeRepository.save(grade);

        School school = schoolRepository.findByCode(request.getSchoolCode())
                .orElseThrow(() -> new EntityNotFoundException("해당코드를 가진 학교가 없습니다."));

        Integer schoolNo = school.getNo();

        List<Student> students = request.getStudents().stream()
                .map(studentDto -> studentDto.toEntity(passwordEncoder, schoolNo))
                .collect(Collectors.toList());
        studentRepository.saveAll(students);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeDTO> listGrades() {
        return gradeRepository.findAll().stream()
                .map(grade -> {
                    String schoolName = schoolRepository.findByCode(grade.getSchoolCode())
                            .map(School::getName)
                            .orElse("학교명 미상");
                    return new GradeDTO(
                            grade.getNo(),
                            schoolName,
                            grade.getYear(),
                            grade.getClassRoom());
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteGrade(Integer gradeNo) {
        gradeRepository.deleteById(gradeNo);
    }

	@Override
	public GradeStatisticsFindResponse findGrade(int gradeNo) {
		return null;
	}

	@Override
	public void modifySalary(int gradeNo, SalaryModifyRequest salaryModifyRequest) {
		Grade grade = gradeRepository.findById(gradeNo)
			.orElseThrow(() -> new IllegalArgumentException(""));//TODO: 커스텀 예외처리

		grade.modifySalary(salaryModifyRequest.getSalary());
	}

	@Override
	public void modifySavingTime(int gradeNo, SavingPeriodModifyRequest savingPeriodModifyRequest) {
		Grade grade = gradeRepository.findById(gradeNo)
			.orElseThrow(() -> new IllegalArgumentException(""));//TODO: 커스텀 예외처리

		grade.modifySavingTime(
			savingPeriodModifyRequest.getTransferAlertPeriod(),
			savingPeriodModifyRequest.getTransferPeriod()
		);
	}
}

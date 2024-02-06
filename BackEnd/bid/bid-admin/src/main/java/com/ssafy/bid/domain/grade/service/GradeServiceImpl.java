package com.ssafy.bid.domain.grade.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.grade.Grade;
import com.ssafy.bid.domain.grade.dto.SalaryModifyRequest;
import com.ssafy.bid.domain.grade.dto.SavingPeriodModifyRequest;
import com.ssafy.bid.domain.grade.repository.GradeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class GradeServiceImpl implements GradeService {

	private final GradeRepository gradeRepository;

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

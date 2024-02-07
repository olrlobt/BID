package com.ssafy.bid.domain.saving.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.saving.Saving;
import com.ssafy.bid.domain.saving.dto.SavingModifyRequest;
import com.ssafy.bid.domain.saving.dto.SavingRequest;
import com.ssafy.bid.domain.saving.repository.SavingRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SavingServiceImpl implements SavingService {

	private final SavingRepository savingRepository;

	@Override
	public void modifySavings(int gradeNo, SavingModifyRequest savingModifyRequest) {
		List<Saving> savings = savingRepository.findAllByGradeNo(gradeNo);
		List<SavingRequest> savingRequests = savingModifyRequest.getSavingRequests();

		savings.forEach(saving -> savingRequests.stream()
			.filter(request -> request.getNo() == saving.getNo())
			.forEach(request -> saving.modify(request.getDepositPrice(), request.getInterestRate()))
		);
	}
}

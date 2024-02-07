package com.ssafy.bid.domain.saving.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.saving.Saving;
import com.ssafy.bid.domain.saving.dto.SavingListUpdateRequest;
import com.ssafy.bid.domain.saving.dto.SavingUpdateRequest;
import com.ssafy.bid.domain.saving.repository.SavingRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SavingServiceImpl implements SavingService {

	private final SavingRepository savingRepository;

	@Override
	public void updateSaving(int gradeNo, SavingListUpdateRequest savingListUpdateRequest) {
		List<Saving> savings = savingRepository.findAllByGradeNo(gradeNo);
		if (savings.isEmpty()) {
			throw new ResourceNotFoundException("수정하려는 Saving 엔티티 없음.", gradeNo);
		}

		List<SavingUpdateRequest> savingUpdateRequests = savingListUpdateRequest.getSavingUpdateRequests();
		savings.forEach(saving -> savingUpdateRequests.stream()
			.filter(request -> request.getNo() == saving.getNo())
			.forEach(request -> saving.modify(request.getDepositPrice(), request.getInterestRate()))
		);
	}
}

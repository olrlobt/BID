package com.ssafy.bid.domain.saving.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.saving.Saving;
import com.ssafy.bid.domain.saving.dto.SavingModifyRequest;
import com.ssafy.bid.domain.saving.dto.SavingRequest;
import com.ssafy.bid.domain.saving.repository.SavingRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SavingServiceImpl implements SavingService {

	private final SavingRepository savingRepository;

	@Override
	public void modifySaving(int gradeNo, SavingModifyRequest savingModifyRequest) {
		// 학급PK를 통해 적금 목록 조회
		List<Saving> savings = savingRepository.findAllByGradeNo(gradeNo);

		// 파라미터 검증
		if (savings.isEmpty()) {
			throw new ResourceNotFoundException("적금수정-학급PK", gradeNo);
		}

		// 클라이언트가 입력한 변경내역 DTO 추출
		List<SavingRequest> savingRequests = savingModifyRequest.getSavingRequests();

		// 적금 한꺼번에 수정
		savings.forEach(saving -> savingRequests.stream()
			.filter(request -> request.getNo() == saving.getNo())
			.forEach(request -> saving.modify(request.getDepositPrice(), request.getInterestRate()))
		);
	}
}

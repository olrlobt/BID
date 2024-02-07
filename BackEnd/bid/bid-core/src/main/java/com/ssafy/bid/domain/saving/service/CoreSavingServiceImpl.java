package com.ssafy.bid.domain.saving.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.saving.dto.SavingFindResponse;
import com.ssafy.bid.domain.saving.repository.CoreSavingRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CoreSavingServiceImpl implements CoreSavingService {

	private final CoreSavingRepository coreSavingRepository;

	@Override
	public List<SavingFindResponse> findAllSaving(int gradeNo) {
		// 학급PK를 통해 적금 목록 조회
		List<SavingFindResponse> responses = coreSavingRepository.findAllSavingByGradeNo(gradeNo);

		// 파라미터 검증
		if (responses.isEmpty()) {
			throw new ResourceNotFoundException("적금목록조회-학급PK", gradeNo);
		}

		// 응답 데이터 반환
		return responses;
	}
}

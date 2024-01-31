package com.ssafy.bid.domain.saving.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.saving.dto.SavingsResponse;
import com.ssafy.bid.domain.saving.repository.SavingRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SavingServiceImpl implements SavingService {

	private final SavingRepository savingRepository;

	@Override
	public List<SavingsResponse> findSavings(int gradeNo) {
		return savingRepository.findSavings(gradeNo);
	}
}

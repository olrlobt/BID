package com.ssafy.bid.domain.saving.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.saving.UserSaving;
import com.ssafy.bid.domain.saving.dto.SavingRequest;
import com.ssafy.bid.domain.saving.dto.SavingsResponse;
import com.ssafy.bid.domain.saving.repository.SavingRepository;
import com.ssafy.bid.domain.saving.repository.UserSavingRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SavingServiceImpl implements SavingService {

	private final SavingRepository savingRepository;
	private final UserSavingRepository userSavingRepository;

	@Override
	@Transactional(readOnly = true)
	public List<SavingsResponse> findSavings(int gradeNo) {
		return savingRepository.findSavings(gradeNo);
	}

	@Override
	public void saveSaving(int userNo, SavingRequest savingRequest) {
		UserSaving userSaving = savingRequest.toEntity(userNo);
		userSavingRepository.save(userSaving);
	}
}

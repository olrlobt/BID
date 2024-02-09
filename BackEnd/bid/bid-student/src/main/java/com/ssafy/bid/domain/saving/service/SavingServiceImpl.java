package com.ssafy.bid.domain.saving.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.saving.Saving;
import com.ssafy.bid.domain.saving.UserSaving;
import com.ssafy.bid.domain.saving.dto.SavingSaveRequest;
import com.ssafy.bid.domain.saving.dto.UserSavingListGetResponse;
import com.ssafy.bid.domain.saving.repository.SavingRepository;
import com.ssafy.bid.domain.saving.repository.UserSavingRepository;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;
import com.ssafy.bid.domain.user.repository.UserRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SavingServiceImpl implements SavingService {

	private final SavingRepository savingRepository;
	private final UserSavingRepository userSavingRepository;
	private final UserRepository userRepository;

	@Override
	public List<UserSavingListGetResponse> getAllSavings(int gradeNo, int userNo) {
		return userSavingRepository.findAllByUserNoAndGradeNo(userNo, gradeNo);
	}

	@Override
	@Transactional
	public void saveSavings(CustomUserInfo userInfo, SavingSaveRequest savingSaveRequest) {
		Saving saving = savingRepository.findById(savingSaveRequest.getNo())
			.orElseThrow(() -> new ResourceNotFoundException("등록하려는 Saving 엔티티가 없음.", savingSaveRequest.getNo()));
		UserSaving userSaving = savingSaveRequest.toEntity(saving, userInfo);
		userSavingRepository.save(userSaving);
	}

	@Override
	@Transactional
	public void deleteSavings(int userNo, int savingNo) {
		boolean exists = userSavingRepository.existsByUserNoAndSavingNo(userNo, savingNo);
		if (!exists) {
			throw new ResourceNotFoundException("삭제하려는 UserSaving 엔티티가 없음.", userNo);
		}
		userSavingRepository.deleteByUserNoAndSavingNo(userNo, savingNo);
	}
}

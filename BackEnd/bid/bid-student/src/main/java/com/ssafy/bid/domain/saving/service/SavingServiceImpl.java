package com.ssafy.bid.domain.saving.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.saving.Saving;
import com.ssafy.bid.domain.saving.UserSaving;
import com.ssafy.bid.domain.saving.dto.SavingSaveRequest;
import com.ssafy.bid.domain.saving.dto.TaxRateListGetResponse;
import com.ssafy.bid.domain.saving.dto.TaxRateResponse;
import com.ssafy.bid.domain.saving.dto.UserSavingListGetResponse;
import com.ssafy.bid.domain.saving.repository.SavingRepository;
import com.ssafy.bid.domain.saving.repository.UserSavingRepository;
import com.ssafy.bid.domain.user.UserType;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;
import com.ssafy.bid.global.error.exception.AuthorizationFailedException;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SavingServiceImpl implements SavingService {

	private final SavingRepository savingRepository;
	private final UserSavingRepository userSavingRepository;

	@Override
	public List<UserSavingListGetResponse> getAllSavings(CustomUserInfo userInfo) {
		if (!userInfo.getUserType().equals(UserType.STUDENT)) {
			throw new AuthorizationFailedException("적금목록조회: Student 권한 사용자가 아님.");
		}

		List<UserSavingListGetResponse> responses = userSavingRepository.findAllByUserNoAndGradeNo(userInfo.getNo(),
			userInfo.getGradeNo());

		TaxRateResponse taxRateResponse = userSavingRepository.findAllBiddingIncomes(userInfo.getGradeNo(),
				userInfo.getNo())
			.orElseThrow(() -> new ResourceNotFoundException("적금목록조회: Student 엔티티가 없음.", userInfo.getNo()));

		List<TaxRateListGetResponse> taxRateListGetResponses = createTaxRateListGetResponses(taxRateResponse);
		responses.forEach(response -> {
			response.setIncomeLevel(taxRateResponse.getIncomeLevel());
			response.setTaxRateListGetResponses(taxRateListGetResponses);
		});

		return responses;
	}

	public List<TaxRateListGetResponse> createTaxRateListGetResponses(TaxRateResponse taxRateResponse) {
		List<TaxRateListGetResponse> taxRateListGetResponses = new ArrayList<>();
		taxRateListGetResponses.add(taxRateResponse.createZero());
		taxRateListGetResponses.add(taxRateResponse.createOne());
		taxRateListGetResponses.add(taxRateResponse.createTwo());
		taxRateListGetResponses.add(taxRateResponse.createThree());
		taxRateListGetResponses.add(taxRateResponse.createFour());
		taxRateListGetResponses.add(taxRateResponse.createFive());
		taxRateListGetResponses.add(taxRateResponse.createSix());
		return taxRateListGetResponses;
	}

	@Override
	@Transactional
	public void saveSavings(CustomUserInfo userInfo, SavingSaveRequest savingSaveRequest) {
		if (!userInfo.getUserType().equals(UserType.STUDENT)) {
			throw new AuthorizationFailedException("적금가입: Student 권한 사용자가 아님.");
		}

		Saving saving = savingRepository.findById(savingSaveRequest.getNo())
			.orElseThrow(() -> new ResourceNotFoundException("적금가입: Saving 엔티티가 없음.", savingSaveRequest.getNo()));

		UserSaving userSaving = savingSaveRequest.toEntity(saving, userInfo);
		userSavingRepository.save(userSaving);
	}

	@Override
	@Transactional
	public void deleteSavings(CustomUserInfo userInfo, int savingNo) {
		if (!userInfo.getUserType().equals(UserType.STUDENT)) {
			throw new AuthorizationFailedException("적금해지: Student 권한 사용자가 아님.");
		}

		boolean exists = userSavingRepository.existsByUserNoAndSavingNo(userInfo.getNo(), savingNo);
		if (!exists) {
			throw new ResourceNotFoundException("적금해지: UserSaving 엔티티가 없음.", userInfo.getNo());
		}

		userSavingRepository.deleteByUserNoAndSavingNo(userInfo.getNo(), savingNo);
	}
}

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
		List<UserSavingListGetResponse> responses = userSavingRepository.findAllByUserNoAndGradeNo(userNo, gradeNo);

		TaxRateResponse taxRateResponse = userSavingRepository.findAllBiddingIncomes(gradeNo, userNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당하는 User 엔티티가 없음.", userNo));
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

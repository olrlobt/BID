package com.ssafy.bid.domain.saving.api;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.saving.dto.SavingModifyRequest;
import com.ssafy.bid.domain.saving.dto.SavingsResponse;
import com.ssafy.bid.domain.saving.service.SavingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class SavingApi {

	private final SavingService savingService;

	@GetMapping("/{gradeNo}/savings")
	public List<SavingsResponse> findSavings(@PathVariable int gradeNo) {
		return savingService.findSavings(gradeNo);
	}

	@PatchMapping("/{gradeNo}/savings")
	public void modifySavings(@PathVariable int gradeNo, @RequestBody SavingModifyRequest savingModifyRequest) {
		savingService.modifySavings(gradeNo, savingModifyRequest);
	}
}

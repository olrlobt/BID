package com.ssafy.bid.domain.saving.api;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.saving.dto.SavingFindResponse;
import com.ssafy.bid.domain.saving.dto.SavingModifyRequest;
import com.ssafy.bid.domain.saving.service.CoreSavingService;
import com.ssafy.bid.domain.saving.service.SavingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class SavingApi {

	private final SavingService savingService;
	private final CoreSavingService coreSavingService;

	@GetMapping("/{gradeNo}/savings")
	public ResponseEntity<List<SavingFindResponse>> findSavings(@PathVariable int gradeNo) {
		List<SavingFindResponse> responses = coreSavingService.findAllSaving(gradeNo);
		return ResponseEntity.status(OK).body(responses);
	}

	@PatchMapping("/{gradeNo}/savings")
	public void modifySavings(@PathVariable int gradeNo, @RequestBody SavingModifyRequest savingModifyRequest) {
		savingService.modifySavings(gradeNo, savingModifyRequest);
	}
}

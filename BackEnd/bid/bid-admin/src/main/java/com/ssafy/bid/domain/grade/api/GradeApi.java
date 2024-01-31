package com.ssafy.bid.domain.grade.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.grade.dto.GradeFindResponse;
import com.ssafy.bid.domain.grade.service.GradeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class GradeApi {

	private final GradeService gradeService;

	@GetMapping("/{gradeNo}/statistics")
	public GradeFindResponse findGrade(@PathVariable int gradeNo) {
		return gradeService.findGrade(gradeNo);
	}
}

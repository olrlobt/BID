package com.ssafy.bid.domain.grade.api;

import com.ssafy.bid.domain.grade.dto.GradeCreationRequest;
import com.ssafy.bid.domain.grade.dto.GradeDTO;
import com.ssafy.bid.domain.grade.service.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class GradeApi {
    private final GradeService gradeService;

    public GradeApi(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping("/grade/register")
    public ResponseEntity<Void> createGrade(@RequestBody GradeCreationRequest request) {
        gradeService.createGrade(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/grade")
    public ResponseEntity<List<GradeDTO>> listGrades() {
        List<GradeDTO> grades = gradeService.listGrades();
        return ResponseEntity.ok(grades);
    }

    @DeleteMapping("/grade/{gradeNo}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Integer gradeNo) {
        gradeService.deleteGrade(gradeNo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

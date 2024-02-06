package com.ssafy.bid.domain.grade.dto;

import lombok.Getter;

@Getter
public class GradeDTO {
    private Integer no;
    private String schoolName;
    private Integer year;
    private Integer classRoom;

    public GradeDTO(Integer no, String schoolName, Integer year, Integer classRoom) {
        this.no = no;
        this.schoolName = schoolName;
        this.year = year;
        this.classRoom = classRoom;
    }
}

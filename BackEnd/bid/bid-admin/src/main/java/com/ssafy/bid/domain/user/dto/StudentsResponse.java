package com.ssafy.bid.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentsResponse {
    private int no;
    private int number;
    private String name;
    private int asset;

    public StudentsResponse(
            int no,
            String id,
            String name,
            int asset
    ) {
        this.no = no;
        this.number = Integer.parseInt(id.substring(6).strip());
        this.name = name;
        this.asset = asset;
    }
}

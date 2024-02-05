package com.ssafy.bid.domain.user.dto;

import com.ssafy.bid.domain.user.AccountType;
import com.ssafy.bid.domain.user.DealType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AccountResponse {
    private AccountType accountType;
    private int price;
    private String content;
    private DealType dealType;
    private LocalDateTime createdAt;

    public AccountResponse(
            AccountType accountType,
            int price,
            String content,
            DealType dealType,
            LocalDateTime createdAt
    ) {
        this.accountType = accountType;
        this.price = price;
        this.content = content;
        this.dealType = dealType;
        this.createdAt = createdAt;
    }
}

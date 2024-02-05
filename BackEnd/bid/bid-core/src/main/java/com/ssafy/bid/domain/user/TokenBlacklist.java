package com.ssafy.bid.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class TokenBlacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private LocalDateTime expiryDate;

    protected TokenBlacklist() {}

    public TokenBlacklist(
            String token,
            LocalDateTime expiryDate
    ) {
        this.token = token;
        this.expiryDate = expiryDate;
    }
}

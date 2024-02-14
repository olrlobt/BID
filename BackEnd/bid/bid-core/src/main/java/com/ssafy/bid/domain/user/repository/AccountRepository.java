package com.ssafy.bid.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.user.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}

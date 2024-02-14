package com.ssafy.bid.domain.user.repository;

import org.springframework.data.repository.CrudRepository;

import com.ssafy.bid.domain.user.TelAuthentication;

public interface TelAuthenticationRepository extends CrudRepository<TelAuthentication, String> {
}

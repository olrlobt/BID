package com.ssafy.bid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import com.ssafy.bid.configuration.datasource.RedisProperties;
import com.ssafy.bid.configuration.security.JwtProperties;

@EnableCaching
@EnableConfigurationProperties({JwtProperties.class, RedisProperties.class})
@SpringBootApplication
public class BidApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidApplication.class, args);
	}
}

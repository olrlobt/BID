package com.ssafy.bid;

import com.ssafy.bid.domain.user.security.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties(JwtProperties.class)
@ConfigurationPropertiesScan
@EnableJpaAuditing
@SpringBootApplication
public class BidApplication {

    public static void main(String[] args) {
        SpringApplication.run(BidApplication.class, args);
    }
}

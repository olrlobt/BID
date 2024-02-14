package com.ssafy.bid.domain.user.security;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.ssafy.bid.configuration.security.JwtProperties;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;
import com.ssafy.bid.domain.user.dto.TokenResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
	private final Key key;
	private final long ACCESS_TOKEN_EXPIRATION;
	private final long REFRESH_TOKEN_EXPIRATION;

	public JwtTokenProvider(JwtProperties jwtProperties) {
		byte[] keyBytes = Decoders.BASE64URL.decode(jwtProperties.getSecret());
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.ACCESS_TOKEN_EXPIRATION = jwtProperties.getExpirationTime();
		this.REFRESH_TOKEN_EXPIRATION = jwtProperties.getRefreshExpirationTime();
	}

	public TokenResponse createToken(CustomUserInfo userInfo) {
		String accessToken = createAccessToken(userInfo, ACCESS_TOKEN_EXPIRATION);
		String refreshToken = createRefreshToken();
		return TokenResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	private String createAccessToken(CustomUserInfo userInfo, long accessTokenExpTime) {
		Claims claims = Jwts.claims();
		claims.put("no", userInfo.getNo());
		claims.put("id", userInfo.getId());
		claims.put("name", userInfo.getName());
		claims.put("gradeNo", userInfo.getGradeNo());

		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime tokenValidity = now.plusSeconds(accessTokenExpTime);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(Date.from(now.toInstant()))
			.setExpiration(Date.from(tokenValidity.toInstant()))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	private String createRefreshToken() {
		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime tokenValidity = now.plusSeconds(REFRESH_TOKEN_EXPIRATION);

		return Jwts.builder()
			.setExpiration(Date.from(tokenValidity.toInstant()))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public String getUserId(String token) {
		return parseClaims(token).get("id", String.class);
	}

	private Claims parseClaims(String accessToken) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(accessToken)
			.getBody();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty", e);
		}
		return false;
	}
}

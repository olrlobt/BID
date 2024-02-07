package com.ssafy.bid.domain.user.security;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.ssafy.bid.configuration.security.JwtProperties;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;

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
	private final long accessTokenExpTime;

	public JwtTokenProvider(JwtProperties jwtProperties) {
		byte[] keyBytes = Decoders.BASE64URL.decode(jwtProperties.getSecret());
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.accessTokenExpTime = jwtProperties.getExpirationTime();
	}

	public String createAccessToken(CustomUserInfo userInfo) {
		return createToken(userInfo, accessTokenExpTime);
	}

	private String createToken(CustomUserInfo userInfo, long accessTokenExpTime) {
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

package com.ssafy.bid.domain.user.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    private final Key key;
    private final long accessTokenExpTime;

    public JwtUtil(JwtProperties jwtProperties) {
        try {
            log.info("JWT Secret:{}", jwtProperties.getSecret());
            byte[] keyBytes = Decoders.BASE64URL.decode(jwtProperties.getSecret());
            this.key = Keys.hmacShaKeyFor(keyBytes);
            this.accessTokenExpTime = jwtProperties.getExpirationTime();
        } catch (IllegalArgumentException e) {
            log.error("JWT Secret loading error: ", e);
            throw new IllegalStateException("JWT configuration is invalid", e);
        }
    }

    public String createAccessToken(CustomUserInfo user) {
        return createToken(user, accessTokenExpTime);
    }

    private String createToken(CustomUserInfo user, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("UserId", user.getId());
        claims.put("name", user.getName());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserId(String token) {
        return parseClaims(token).get("UserId", String.class);
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

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}

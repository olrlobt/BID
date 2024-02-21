package com.ssafy.bid.domain.user.security;

import java.io.IOException;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;

import com.ssafy.bid.domain.user.service.CustomUserDetailsService;
import com.ssafy.bid.global.error.exception.AuthenticationFailedException;
import com.ssafy.bid.global.util.SecurityUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

	private final JwtTokenProvider jwtTokenProvider;
	private final CustomUserDetailsService customUserDetailsService;
	private final RedisTemplate redisTemplate;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		String token = SecurityUtils.getAccessToken((HttpServletRequest)request);
		log.info("token = {}", token);
		log.info("request = {}", request);
		if (token != null && jwtTokenProvider.validateToken(token)) {

			String isLogout = (String)redisTemplate.opsForValue().get(token);
			if (!ObjectUtils.isEmpty(isLogout)) {
				throw new AuthenticationFailedException("로그아웃된 사용자 토큰.");
			}

			String id = jwtTokenProvider.getUserId(token);

			UserDetails userDetails = customUserDetailsService.loadUserByUsername(id);

			if (userDetails != null) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
}

package com.ssafy.bid.domain.user.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;

import com.ssafy.bid.domain.user.repository.CoreTokenBlacklistRepository;
import com.ssafy.bid.domain.user.service.CustomUserDetailsService;
import com.ssafy.bid.global.util.SecurityUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

	private final JwtTokenProvider jwtTokenProvider;
	private final CustomUserDetailsService customUserDetailsService;
	private final CoreTokenBlacklistRepository coreTokenBlacklistRepository;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		String token = SecurityUtils.getAccessToken((HttpServletRequest)request);

		if (token != null && jwtTokenProvider.validateToken(token)) {
			if (isTokenBlacklisted(token)) {
				((HttpServletResponse)response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
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

	private boolean isTokenBlacklisted(String token) {
		return coreTokenBlacklistRepository.findByToken(token).isPresent();
	}
}

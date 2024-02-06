package com.ssafy.bid.domain.user.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.bid.domain.user.repository.TokenBlacklistRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final CustomUserDetailsService customUserDetailsService;
	private final JwtUtil jwtUtil;
	private TokenBlacklistRepository tokenBlacklistRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);

			if (isTokenBlacklisted(token)) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}

			if (jwtUtil.validateToken(token)) {
				String id = jwtUtil.getUserId(token);

				UserDetails userDetails = customUserDetailsService.loadUserByUsername(id);

				if (userDetails != null) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	private boolean isTokenBlacklisted(String token) {
		return tokenBlacklistRepository.findByToken(token).isPresent();
	}
}

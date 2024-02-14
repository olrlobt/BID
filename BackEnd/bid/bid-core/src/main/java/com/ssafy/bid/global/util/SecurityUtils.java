package com.ssafy.bid.global.util;

import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtils {

	public static String getAccessToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}

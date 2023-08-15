package com.itwill.myleaves.repository.member;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String loginId = request.getParameter("userId");
		String password = request.getParameter("pwd");

		log.info("onAuthenticationSuccess(userId={}, pwd={})", loginId, password);

		// 사용자의 역할(Role) 확인
		String role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst()
				.orElse("");

		// 관리자인지 확인하여 페이지 리다이렉트
		if (role.equals("ROLE_MEMBER")) {
			response.sendRedirect("/"); // 일반 사용자 페이지로 리다이렉트
		} else {
			response.sendRedirect("/mngr"); // 관리자 페이지로 리다이렉트
		}
	}
}
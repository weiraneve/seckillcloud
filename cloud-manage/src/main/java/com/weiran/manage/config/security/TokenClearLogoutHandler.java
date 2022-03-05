package com.weiran.manage.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class TokenClearLogoutHandler implements LogoutHandler {
	
	private final JwtUserService jwtUserService;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		clearToken(authentication);
	}

	private void clearToken(Authentication authentication) {
		if (authentication == null) {
			return;
		}
		UserDetails user = (UserDetails)authentication.getPrincipal();
		if (user != null && user.getUsername() != null) {
			jwtUserService.deleteUserLoginInfo(user.getUsername());
		}
	}

}

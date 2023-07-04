package com.weiran.manage.config.security;

import com.weiran.manage.filter.JwtAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * 配置JWT登录
 */
public class JwtLoginConfigurer<T extends JwtLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {

	private final JwtAuthenticationFilter authFilter;

	JwtLoginConfigurer() {
		this.authFilter = new JwtAuthenticationFilter();
	}

	@Override
	public void configure(B http) {
		authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		authFilter.setAuthenticationFailureHandler(new HttpStatusLoginFailureHandler());

		JwtAuthenticationFilter filter = postProcess(authFilter);
		http.addFilterBefore(filter, LogoutFilter.class);
	}

	public JwtLoginConfigurer<T, B> permissiveRequestUrls(String ... urls) {
		authFilter.setPermissiveUrl(urls);
		return this;
	}

	public JwtLoginConfigurer<T, B> tokenValidSuccessHandler(AuthenticationSuccessHandler successHandler) {
		authFilter.setAuthenticationSuccessHandler(successHandler);
		return this;
	}

}

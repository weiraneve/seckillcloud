package com.weiran.manage.config.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * JWT认证成功以及刷新
 */
@RequiredArgsConstructor
public class JwtRefreshSuccessHandler implements AuthenticationSuccessHandler {

    // 刷新间隔5分钟
    private static final int TOKEN_REFRESH_INTERVAL = 300;

    private static final String RESPONSE_HEADER = "Authorization";

    private final JwtUserService jwtUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        DecodedJWT jwt = ((JwtAuthenticationToken) authentication).getToken();
        boolean shouldRefresh = shouldTokenRefresh(jwt.getIssuedAt());
        if (shouldRefresh) {
            String newToken = jwtUserService.saveUserLoginInfo((UserDetails) authentication.getPrincipal());
            response.setHeader(RESPONSE_HEADER, newToken);
        }
    }

    private boolean shouldTokenRefresh(Date issueAt) {
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(TOKEN_REFRESH_INTERVAL).isAfter(issueTime);
    }

}

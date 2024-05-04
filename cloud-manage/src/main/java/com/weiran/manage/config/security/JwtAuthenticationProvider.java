package com.weiran.manage.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Calendar;

/**
 * 用户自定义身份认证
 */
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    private final JwtUserService userService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        DecodedJWT jwt = ((JwtAuthenticationToken) authentication).getToken();
        if (jwt.getExpiresAt().before(Calendar.getInstance().getTime())) {
            throw new BadCredentialsException("令牌已过期");
        }
        String username = jwt.getSubject();
        UserDetails user = userService.getUserLoginInfo(username);
        if (user == null || user.getPassword() == null) {
            throw new UsernameNotFoundException("未找到用户或密码为空");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withSubject(username)
                    .build();
            verifier.verify(jwt.getToken());
        } catch (Exception e) {
            throw new BadCredentialsException("JWT 令牌验证失败", e);
        }
        return new JwtAuthenticationToken(user, jwt, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }

}

package com.weiran.manage.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.weiran.manage.mapper.admin.AdminUserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

/**
 * JWT身份认证
 */
public class JwtUserService implements UserDetailsService {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000;

    private final PasswordEncoder passwordEncoder;
    private final AdminUserMapper adminUserMapper;

    public JwtUserService(PasswordEncoder passwordEncoder, AdminUserMapper adminUserMapper) {
        this.passwordEncoder = passwordEncoder;
        this.adminUserMapper = adminUserMapper;
    }

    UserDetails getUserLoginInfo(String username) {
        return loadUserByUsername(username);
    }

    public String saveUserLoginInfo(UserDetails user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + validityInMilliseconds);
        //设置2小时后过期
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiresAt)
                .withIssuedAt(now)
                .sign(algorithm);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminUserMapper.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    }

    public void createUser(String username, String password) {
        String encryptPwd = passwordEncoder.encode(password);
//        adminUserMapper.insert(username);
    }

    void deleteUserLoginInfo(String username) {

    }
}

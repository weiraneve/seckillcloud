package com.weiran.uaa.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weiran.common.enums.RedisCacheTimeEnum;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.obj.Result;
import com.weiran.common.redis.key.UserKey;
import com.weiran.common.redis.manager.RedisLua;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.common.utils.AuthUtil;
import com.weiran.uaa.entity.User;
import com.weiran.uaa.manager.UserManager;
import com.weiran.uaa.param.LoginParam;
import com.weiran.uaa.param.RegisterParam;
import com.weiran.uaa.param.UpdatePassParam;
import com.weiran.uaa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final UserManager userManager;
    final RedisService redisService;
    final RedisLua redisLua;

    @Override
    public Result<String> doLogin(LoginParam loginParam) {
        User user = userManager.getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getPhone, loginParam.getMobile()));
        if (user == null) {
            return Result.fail(ResponseEnum.MOBILE_NOT_EXIST.getMsg());
        }
        // 数据库里存储的都是密码本身加加盐后的密文
        if (!user.getPassword().equals(loginParam.getPassword())) {
            return Result.fail(ResponseEnum.PASSWORD_ERROR.getMsg());
        }
        // 密码置为空 防止泄漏
        user.setPassword("");
        long userId = user.getId();
        // 将用户手机号进行MD5和随机数加盐加密，作为Token给到前端服务器
        String loginToken = generateLoginToken(user);
        // 更新用户的最后登录时间
        updateLastLoginTime(loginParam, user);
        redisService.set(UserKey.getById, loginToken, userId, RedisCacheTimeEnum.LOGIN_EXTIME.getValue());
        log.info("用户" + userId + " 登录成功");
        return Result.success(loginToken);
    }

    private void updateLastLoginTime(LoginParam loginParam, User user) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        simpleDateFormat.format(date);
        user.setPassword(loginParam.getPassword());
        user.setLastLoginTime(date);
        userManager.updateById(user);
    }

    private String generateLoginToken(User user) {
        int salt = RandomUtil.randomInt(100000);
        return SecureUtil.md5(user.getPhone() + salt);
    }

    @Override
    public Result<ResponseEnum> doLogout(HttpServletRequest request) {
        String loginToken = AuthUtil.getLoginTokenByRequest(request);
        if (loginToken == null) {
            return Result.fail(ResponseEnum.TOKEN_PARSING_ERROR);
        }
        long userId = redisService.get(UserKey.getById, loginToken, Long.class);
        redisService.delete(UserKey.getById, loginToken);
        log.info("用户" + userId + " 已经注销");
        return Result.success();
    }

    @Override
    public Result<ResponseEnum> doRegister(RegisterParam registerParam) {
        // 判断电话、用户名
        if (userManager.getOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, registerParam.getRegisterMobile())) != null) {
            return Result.fail(ResponseEnum.REPEATED_REGISTER_MOBILE);
        }
        userManager.save(getUserByRegisterParam(registerParam));
        log.info(registerParam.getRegisterMobile() + "用户注册成功");
        return Result.success();
    }

    private User getUserByRegisterParam(RegisterParam registerParam) {
        User user = new User();
        user.setPhone(registerParam.getRegisterMobile());
        user.setPassword(registerParam.getRegisterPassword());
        return user;
    }

    @Override
    public Result<ResponseEnum> updatePass(UpdatePassParam updatePassParam, HttpServletRequest request) {
        long userId = redisService.get(UserKey.getById, AuthUtil.getLoginTokenByRequest(request), Long.class);
        User user = userManager.getById(userId);
        if (!user.getPassword().equals(updatePassParam.getOldPassword())) {
            return Result.fail(ResponseEnum.OLD_PASSWORD_ERROR);
        }

        user.setPassword(updatePassParam.getNewPassword());
        userManager.update(user, Wrappers.<User>lambdaQuery().eq(User::getId, user.getId()));
        log.info(user.getPhone() + "用户更换密码成功");
        return Result.success();
    }

}

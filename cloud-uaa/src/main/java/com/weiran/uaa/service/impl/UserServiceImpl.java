package com.weiran.uaa.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weiran.common.enums.RedisCacheTimeEnum;
import com.weiran.common.enums.CodeMsg;
import com.weiran.common.exception.LoginException;
import com.weiran.common.exception.RegisterException;
import com.weiran.common.exception.UpdatePassException;
import com.weiran.common.obj.Result;
import com.weiran.common.redis.key.UserKey;
import com.weiran.common.redis.manager.RedisLua;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.uaa.entity.User;
import com.weiran.uaa.manager.UserManager;
import com.weiran.uaa.param.LoginParam;
import com.weiran.uaa.param.RegisterParam;
import com.weiran.uaa.param.UpdatePassParam;
import com.weiran.uaa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import cn.hutool.crypto.SecureUtil;

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

    // 登录
    @Override
    public Result<String> doLogin(LoginParam loginParam) {
        Result<User> userResult = login(loginParam);
        if (!userResult.isSuccess()) {
            throw new LoginException(userResult, loginParam.getMobile());
        }
        User user = userResult.getData();
        long userId  = user.getId();
        // 将用户手机号进行MD5和随机数加盐加密，作为Token给到前端服务器
        String loginToken = getLoginToken(user);
        // 更新用户的最后登录时间
        updateLastLoginTime(loginParam, user);
        // 将loginToken传入Redis
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

    private String getLoginToken(User user) {
        int salt = RandomUtil.randomInt(100000);
        return SecureUtil.md5(user.getPhone() + salt);
    }

    // 注销
    @Override
    public Result<CodeMsg> doLogout(HttpServletRequest request) {
        String loginToken = getTokenByRequest(request);
        long userId = redisService.get(UserKey.getById, loginToken, Long.class);
        redisService.delete(UserKey.getById, loginToken);
        log.info("用户" + userId + " 已经注销");
        return Result.success();
    }

    private String getTokenByRequest(HttpServletRequest request) {
        String authInfo = request.getHeader("Authorization");
        return authInfo.split("Bearer ")[1];
    }

    // 注册
    @Override
    public Result<CodeMsg> doRegister(RegisterParam registerParam) {
        // 判断电话、用户名、身份证有无被注册
        isRegistered(registerParam);
        try {
            userManager.save(getUserByRegisterParam(registerParam));
            log.info(registerParam.getRegisterMobile() + "用户注册成功");
        } catch (Exception e) {
            log.error(registerParam.getRegisterMobile() + "用户注册失败");
            log.error(e.toString());
            throw new RegisterException(CodeMsg.SERVER_ERROR);
        }
        return new Result<>(CodeMsg.SUCCESS);
    }

    private void isRegistered(RegisterParam registerParam) {
        // 电话、用户名、身份证都不等于的模型为空，或逻辑，全部为false才返回false; 范例lambdaQuery().or(i -> i.eq(User::getUserName, registerUsername))
        if (userManager.getOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, registerParam.getRegisterMobile())) != null) {
            throw new RegisterException(CodeMsg.REPEATED_REGISTER_MOBILE); // 手机号已经被注册
        }
        if (userManager.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, registerParam.getRegisterUsername())) != null) {
            throw new RegisterException(CodeMsg.REPEATED_REGISTER_USERNAME); // 用户名已经被注册
        }
        if (userManager.getOne(Wrappers.<User>lambdaQuery().eq(User::getIdentityCardId, registerParam.getRegisterIdentity())) != null) {
            throw new RegisterException(CodeMsg.REPEATED_REGISTER_IDENTITY); // 身份证已经被注册
        }
    }

    private User getUserByRegisterParam(RegisterParam registerParam) {
        User user = new User();
        user.setPhone(registerParam.getRegisterMobile());
        user.setUserName(registerParam.getRegisterUsername());
        user.setPassword(registerParam.getRegisterPassword());
        user.setIdentityCardId(registerParam.getRegisterIdentity());
        return user;
    }

    // 更换密码
    @Override
    public Result<CodeMsg> updatePass(UpdatePassParam updatePassParam, HttpServletRequest request) {
        long userId = getUserId(request);
        User user = userManager.getById(userId);
        if (!user.getPassword().equals(updatePassParam.getOldPassword())) {
            throw new UpdatePassException(CodeMsg.OLD_PASSWORD_ERROR);
        }
        user.setPassword(updatePassParam.getNewPassword());
        try {
            userManager.update(user, Wrappers.<User>lambdaQuery().eq(User::getId, user.getId()));
        } catch (Exception e) {
            log.error(user.getPhone() + "用户更换密码失败");
            log.error(e.toString());
            throw new UpdatePassException((CodeMsg.UPDATE_PASSWORD_ERROR));
        }
        log.info(user.getPhone() + "用户更换密码成功");
        return Result.success();
    }

    private long getUserId(HttpServletRequest request) {
        String loginToken = getTokenByRequest(request);
        return redisService.get(UserKey.getById, loginToken, Long.class);
    }

    // 登录方法，检查比对传入的登录字段
    private Result<User> login(LoginParam loginParam) {
        User user = userManager.getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getPhone, loginParam.getMobile())); // 根据手机号查询出User对象数据
        if (user == null) {
            return Result.error(CodeMsg.MOBILE_NOT_EXIST);
        }
        // 数据库里存储的都是密码本身加加盐后的密文
        if (!user.getPassword().equals(loginParam.getPassword())) {
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }
        // 密码置为空 防止泄漏
        user.setPassword("");
        return Result.success(user);
    }
}

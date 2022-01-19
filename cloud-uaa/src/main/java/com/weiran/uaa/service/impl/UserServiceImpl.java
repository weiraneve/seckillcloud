package com.weiran.uaa.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weiran.common.enums.Constant;
import com.weiran.common.enums.RedisCacheTimeEnum;
import com.weiran.common.obj.CodeMsg;
import com.weiran.common.obj.Result;
import com.weiran.common.redis.key.UserKey;
import com.weiran.common.redis.manager.RedisLua;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.common.utils.CookieUtil;
import com.weiran.common.utils.MD5Util;
import com.weiran.uaa.entity.User;
import com.weiran.uaa.manager.UserManager;
import com.weiran.uaa.param.LoginParam;
import com.weiran.uaa.param.RegisterParam;
import com.weiran.uaa.service.SiftService;
import com.weiran.uaa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final UserManager userManager;
    final RedisService redisService;
    final SiftService siftService;
    final RedisLua redisLua;

    @Override
    public Result doLogin(HttpServletResponse response, HttpSession session, LoginParam loginParam) {
        Result<User> userResult = login(loginParam);
        if (userResult.isSuccess()) {
            User user = userResult.getData();
            long userId = user.getId();
            CookieUtil.writeLoginToken(response, session.getId());
            log.info(loginParam.getMobile() + "登陆成功");
            redisService.set(UserKey.getById, session.getId(), userId, RedisCacheTimeEnum.REDIS_SESSION_EXTIME.getValue());
        } else if (userResult.getCode() == 500700) {
            log.info("{}" , "客户初筛未通过");
        } else {
            log.info(loginParam.getMobile() + "登陆失败");
            return userResult;
        }
        return Result.success(CodeMsg.SUCCESS);
    }

    @Override
    public Result doLogout(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtil.readLoginToken(request);
        CookieUtil.delLoginToken(request, response);
        long userId = redisService.get(UserKey.getById, token, Long.class);
        redisService.delete(UserKey.getById, token);
        log.info(userId + "已经注销");
        return Result.success(CodeMsg.SUCCESS);
    }

    @Override
    public Result doRegister(RegisterParam registerParam) {
        String phone = registerParam.getMobile();
        User preUser = userManager.getOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, phone));
        if (preUser != null) {
            return Result.error(CodeMsg.REPEATED_REGISTER);
        }
        String salt = "9d5b364d";
        String dbPassword = MD5Util.formPassToDBPass(registerParam.getPassword(), salt);
        User user = new User();
        user.setUserName(registerParam.getUsername()); // 用户名
        user.setPhone(phone); // 手机号
        user.setPassword(dbPassword); // 加盐加密后的密码
        user.setIdentityCardId(registerParam.getIdentityCardId()); // 身份证
        boolean flag = userManager.save(user);
        if (!flag) {
            return new Result(CodeMsg.SERVER_ERROR);
        }
        log.info(phone + "用户注册成功");
        return new Result(CodeMsg.SUCCESS);
    }

    // 后台查询本次成功参与活动的用户信息
    @Override
    public Result<User> inquiryUser(long userId) {
        User user = userManager.getById(userId);
        return Result.success(user);
    }

    // 根据手机号查询出User对象数据
    private User checkPhone(String phone) {
        return userManager.getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getPhone, phone));
    }

    // 登陆方法，检查比对传入的登陆字段
    private Result<User> login(LoginParam loginParam) {
        User user = checkPhone(loginParam.getMobile());
        String dbPwd = user.getPassword(); // 数据库查询到的加盐后的密码
        //密码置为空 防止泄漏
        user.setPassword("");
        Long userId = user.getId();
        // 可以设计为邮箱登陆、手机登陆等
        if (userId == null) {
            return Result.error(CodeMsg.MOBILE_NOT_EXIST);
        }
        String salt = "9d5b364d";
        String calcPass = MD5Util.formPassToDBPass(loginParam.getPassword(), salt);
        // 数据库里存储的都是密码本身加加盐后的密文
        if (!dbPwd.equals(calcPass)) {
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }
        if (siftService.findByStatus(user.getId()).isSuccess()) {
            return Result.success(user);
        } else {
            return siftService.findByStatus(user.getId());
        }
    }

    // 利用LUA脚本统计访问次数，功能只是测试，没有统计到每个账号访问网站的次数。
    private void countLogin() {
        redisLua.addVisitorCount(Constant.COUNT_LOGIN);
        Long count = redisLua.getVisitorCount(Constant.COUNT_LOGIN);
        log.info("访问网站的次数为:{}", count);
    }
}

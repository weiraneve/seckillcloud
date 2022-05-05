package com.weiran.uaa.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weiran.common.enums.RedisConstant;
import com.weiran.common.enums.RedisCacheTimeEnum;
import com.weiran.common.enums.CodeMsg;
import com.weiran.common.obj.Result;
import com.weiran.common.redis.key.UserKey;
import com.weiran.common.redis.manager.RedisLua;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.uaa.entity.User;
import com.weiran.uaa.manager.UserManager;
import com.weiran.uaa.param.LoginParam;
import com.weiran.uaa.param.RegisterParam;
import com.weiran.uaa.param.UpdatePassParam;
import com.weiran.uaa.service.SiftService;
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
    final SiftService siftService;
    final RedisLua redisLua;

    // 登录
    @Override
    public Result<String> doLogin(LoginParam loginParam) {
        Result<User> userResult = login(loginParam);
        if (!userResult.isSuccess()) {
            if (userResult.getCode() == CodeMsg.No_SIFT_PASS.getCode()) {
                log.info("客户{}初筛未通过", userResult.getData().getId());
            } else if (userResult.getCode() == CodeMsg.PASSWORD_ERROR.getCode()) {
                log.info("{} 号码登录失败，密码错误" , loginParam.getMobile());
            } else if (userResult.getCode() == CodeMsg.MOBILE_NOT_EXIST.getCode()) {
                log.info("{} 号码登录，无此手机号", loginParam.getMobile());
            }
            return Result.error(userResult.getMsg());
        }
        User user = userResult.getData();
        long userId  = user.getId();
        // 将用户手机号进行MD5和随机数加盐加密，作为Token给到前端服务器
        int salt = RandomUtil.randomInt(100000);
        String loginToken = SecureUtil.md5(user.getPhone() + salt);
        log.info("用户" + userId + " 登录成功");
        redisService.set(UserKey.getById, loginToken, userId, RedisCacheTimeEnum.LOGIN_EXTIME.getValue());
        // 更新用户的最后登录时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        simpleDateFormat.format(date);
        user.setPassword(loginParam.getPassword());
        user.setLastLoginTime(date);
        userManager.updateById(user);
        return Result.success(loginToken);
    }

    // 注销
    @Override
    public Result<CodeMsg> doLogout(HttpServletRequest request) {
        String authInfo = request.getHeader("Authorization");
        String loginToken = authInfo.split("Bearer ")[1]; // 只要Bearer 之后的部分
        long userId = redisService.get(UserKey.getById, loginToken, Long.class);
        redisService.delete(UserKey.getById, loginToken);
        log.info("用户" + userId + " 已经注销");
        return Result.success();
    }

    // 注册
    @Override
    public Result<CodeMsg> doRegister(RegisterParam registerParam) {
        String registerMobile = registerParam.getRegisterMobile();
        String registerUsername = registerParam.getRegisterUsername();
        String registerIdentity = registerParam.getRegisterIdentity();
        String registerPassword = registerParam.getRegisterPassword();
        // 电话、用户名、身份证都不等于的模型为空，或逻辑，全部为false才返回false; 范例lambdaQuery().or(i -> i.eq(User::getUserName, registerUsername))
        if (userManager.getOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, registerMobile)) != null) {
            return Result.error(CodeMsg.REPEATED_REGISTER_MOBILE); // 手机号已经被注册
        } else if (userManager.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, registerUsername)) != null) {
            return Result.error(CodeMsg.REPEATED_REGISTER_USERNAME); // 用户名已经被注册
        } else if (userManager.getOne(Wrappers.<User>lambdaQuery().eq(User::getIdentityCardId, registerIdentity)) != null) {
            return Result.error(CodeMsg.REPEATED_REGISTER_IDENTITY); // 身份证已经被注册
        } else {
            User user = new User();
            user.setPhone(registerMobile);
            user.setUserName(registerUsername);
            user.setPassword(registerPassword);
            user.setIdentityCardId(registerIdentity);
            boolean flag = userManager.save(user);
            if (flag) {
                log.info(registerMobile + "用户注册成功");
            } else {
                log.info(registerMobile + "用户注册失败");
                return new Result<>(CodeMsg.SERVER_ERROR);
            }
            return new Result<>(CodeMsg.SUCCESS);
        }
    }

    // 更换密码
    @Override
    public Result<CodeMsg> updatePass(UpdatePassParam updatePassParam, HttpServletRequest request) {
        String authInfo = request.getHeader("Authorization");
        String loginToken = authInfo.split("Bearer ")[1]; // 只要Bearer 之后的部分
        long userId = redisService.get(UserKey.getById, loginToken, Long.class);
        User user = userManager.getById(userId);
        if (!user.getPassword().equals(updatePassParam.getOldPassword())) {
            return Result.error(CodeMsg.OLD_PASSWORD_ERROR);
        }
        user.setPassword(updatePassParam.getNewPassword());
        boolean flag = userManager.update(user, Wrappers.<User>lambdaQuery().eq(User::getId, user.getId()));
        if (flag) {
            log.info(user.getPhone() + "用户更换密码成功");
            return Result.success();
        } else {
            return Result.error(CodeMsg.UPDATE_PASSWORD_ERROR);
        }
    }

    // 登录方法，检查比对传入的登录字段
    private Result<User> login(LoginParam loginParam) {
        User user = userManager.getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getPhone, loginParam.getMobile())); // 根据手机号查询出User对象数据
        if (user == null) {
            return Result.error(CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPwd = user.getPassword(); // 数据库查询到的加盐后的密码
        // 密码置为空 防止泄漏
        user.setPassword("");
        // 可以设计为邮箱登录、手机登录等
        // 数据库里存储的都是密码本身加加盐后的密文
        if (!dbPwd.equals(loginParam.getPassword())) {
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }
        if (siftService.findByStatus(user).isSuccess()) {
            return Result.success(user);
        } else {
            return siftService.findByStatus(user);
        }
    }

    // 利用LUA脚本统计访问次数，功能只是测试，没有统计到每个账号访问网站的次数。
    private void countLogin() {
        redisLua.addVisitorCount(RedisConstant.COUNT_LOGIN);
        Long count = redisLua.getVisitorCount(RedisConstant.COUNT_LOGIN);
        log.info("访问网站的次数为:{}", count);
    }
}

package com.weiran.manage.cloud.fallback;

import com.weiran.common.obj.Result;
import com.weiran.manage.cloud.UaaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UaaClientFallback implements UaaClient {

    // 后台查询本次成功参与活动的用户信息
    @Override
    public Result inquiryUser(long userId) {
        log.warn("=============== inquiry方法调用失败，服务被降级");
        return null;
    }
}

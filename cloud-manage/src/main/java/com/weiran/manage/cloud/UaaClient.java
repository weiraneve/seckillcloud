package com.weiran.manage.cloud;

import com.weiran.common.obj.Result;
import com.weiran.manage.cloud.fallback.UaaClientFallback;
import com.weiran.manage.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cloud-uaa", fallback = UaaClientFallback.class, configuration = FeignConfig.class)
public interface UaaClient {

    /**
     * 后台查询本次成功参与活动的用户信息
     * @param userId
     */
    @RequestMapping(value = "/user/inquiryUser")
    Result inquiryUser(@RequestParam("userId") long userId);

}

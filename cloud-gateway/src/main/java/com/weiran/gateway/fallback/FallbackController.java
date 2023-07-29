package com.weiran.gateway.fallback;

import com.weiran.gateway.common.ErrorCode;
import com.weiran.gateway.common.FallbackMsg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  熔断回调
 */
@RestController
public class FallbackController {

    final String UNAVAILABLE_MSG = "系统不可用，请稍后再试";

    /**
     * 全局服务降级处理返回
     */
    @RequestMapping("/defaultFallback")
    public FallbackMsg<Integer> fallback() {
        FallbackMsg<Integer> fallbackMsg = new FallbackMsg<>();
        fallbackMsg.setCode(ErrorCode.MICRO_SERVICE_UNAVAILABLE);
        fallbackMsg.setMsg(UNAVAILABLE_MSG);
        return fallbackMsg;
    }
}

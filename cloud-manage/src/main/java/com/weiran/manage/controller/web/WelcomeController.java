package com.weiran.manage.controller.web;

import com.weiran.common.obj.Result;
import com.weiran.manage.response.WelcomeVO;
import com.weiran.manage.service.web.WelcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/welcome")
@RequiredArgsConstructor
public class WelcomeController {

    private final WelcomeService welcomeService;

    /**
     * 统计
     */
    @GetMapping
    public Result<WelcomeVO> welcome() {
        WelcomeVO welcomeVO = welcomeService.welcomeCount();
        return Result.success(welcomeVO);
    }
}

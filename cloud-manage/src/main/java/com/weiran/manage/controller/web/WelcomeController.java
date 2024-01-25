package com.weiran.manage.controller.web;

import com.weiran.common.obj.Result;
import com.weiran.common.pojo.vo.WelcomeVO;
import com.weiran.manage.cloud.MissionClient;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/welcome")
@RequiredArgsConstructor
public class WelcomeController {

    private final MissionClient missionClient;

    @GetMapping
    @ApiOperation("统计数据")
    public Result<WelcomeVO> welcome() {
        return missionClient.welcome();
    }
}

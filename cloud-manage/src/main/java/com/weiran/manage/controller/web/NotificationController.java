package com.weiran.manage.controller.web;


import com.github.pagehelper.PageInfo;
import com.weiran.common.obj.Result;
import com.weiran.common.pojo.dto.SeckillNotificationDTO;
import com.weiran.manage.cloud.MissionClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("通知列表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
    private final MissionClient missionClient;

    @ApiOperation("分页查询通知")
    @GetMapping
    public Result<PageInfo<SeckillNotificationDTO>> goodsIndex(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
                                                               @RequestParam(required = false) String goodsName) {
        return missionClient.notificationIndex(page, pageSize, goodsName);
    }

}

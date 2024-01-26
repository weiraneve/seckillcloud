package com.weiran.manage.controller.web;

import com.github.pagehelper.PageInfo;
import com.weiran.common.obj.Result;
import com.weiran.common.pojo.dto.SeckillGoodsDTO;
import com.weiran.manage.cloud.MissionClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("秒杀商品列表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/seckill")
public class SeckillGoodsController {

    private final MissionClient missionClient;

    @ApiOperation("分页查询")
    @GetMapping
    public Result<PageInfo<SeckillGoodsDTO>> seckillIndex(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize, @RequestParam(required = false) Long goodsId) {
        return missionClient.seckillGoods(page, pageSize, goodsId);
    }

}

package com.weiran.mission.controller;

import com.weiran.common.obj.Result;
import com.weiran.mission.entity.SeckillGoods;
import com.weiran.mission.service.SeckillGoodsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀商品控制器
 */
@RestController
@RequestMapping("seckillGoods")
@RequiredArgsConstructor
public class SeckillGoodsController {

    final SeckillGoodsService seckillGoodsService;

    @ApiOperation("在库存表增加")
    @RequestMapping("/addSeckillGoods")
    public Result addSeckillGoods(SeckillGoods seckillGoods) {
        return seckillGoodsService.addSeckillGoods(seckillGoods);
    }

    @ApiOperation("在库存表删除")
    @RequestMapping("/deleteSeckillGoods")
    public Result deleteSeckillGoods(@RequestParam("goodsId") long goodsId) {
        return seckillGoodsService.deleteSeckillGoods(goodsId);
    }

    @ApiOperation("在库存表更改")
    @RequestMapping("/changeSeckillGoods")
    public Result changeSeckillGoods(SeckillGoods seckillGoods, @RequestParam("goodsId") long goodsId) {
        return seckillGoodsService.changeSeckillGoods(seckillGoods, goodsId);
    }

}

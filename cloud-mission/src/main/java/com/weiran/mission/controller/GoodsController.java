package com.weiran.mission.controller;

import com.weiran.common.obj.Result;
import com.weiran.mission.pojo.vo.GoodsDetailVo;
import com.weiran.mission.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("商品列表详情控制器")
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsController {

    final GoodsService goodsService;

    @ApiOperation("获得商品列表")
    @GetMapping("/getGoodsList")
    public Result<List<GoodsDetailVo>> getGoodsList() {
        return goodsService.getGoodsList();
    }

    @ApiOperation("获得具体的商品细节")
    @GetMapping("/getDetail/{goodsId}")
    public Result<GoodsDetailVo> getDetail(@PathVariable("goodsId") long goodsId) {
        return goodsService.getGoodsDetail(goodsId);
    }

}


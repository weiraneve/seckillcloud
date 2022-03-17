package com.weiran.mission.controller;

import com.weiran.common.obj.Result;
import com.weiran.mission.service.GoodsService;
import com.weiran.mission.pojo.vo.GoodsDetailVo;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品列表详情控制器
 */
@RestController
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
    public Result<GoodsDetailVo> getDetail(@PathVariable("goodsId")long goodsId) {
        return goodsService.getDetail(goodsId);
    }

}


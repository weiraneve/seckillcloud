package com.weiran.mission.controller;

import com.weiran.common.obj.Result;
import com.weiran.mission.entity.Goods;
import com.weiran.mission.service.GoodsService;
import com.weiran.mission.pojo.vo.GoodsDetailVo;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品列表详情控制器
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodsController {

    final GoodsService goodsService;

    @GetMapping("/list")
    public String listPage() {
        return "/goods_list.html";
    }

    @ApiOperation("获得商品列表")
    @GetMapping("/getGoodsList")
    @ResponseBody
    public Result<List<GoodsDetailVo>> getGoodsList() {
        return goodsService.getGoodsList();
    }

    @ApiOperation("获得具体的商品细节")
    @GetMapping("/getDetail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> getDetail(@PathVariable("goodsId")long goodsId) {
        return goodsService.getDetail(goodsId);
    }

    @ApiOperation("增加商品")
    @ResponseBody
    @RequestMapping("/addGoods")
    public Result addGoods(Goods goods) {
        return goodsService.addGoods(goods);
    }

    @ApiOperation("删除商品")
    @ResponseBody
    @RequestMapping("/deleteGoods")
    public Result deleteGoods(@RequestParam("id") long id) {
        return goodsService.deleteGoods(id);
    }

    @ApiOperation("更改商品详情")
    @ResponseBody
    @RequestMapping("/changeGoods")
    public Result changeGoods(Goods goods, @RequestParam("id") long id) {
        return goodsService.changeGoods(goods, id);
    }
    
}


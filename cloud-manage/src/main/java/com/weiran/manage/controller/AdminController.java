package com.weiran.manage.controller;

import com.weiran.common.obj.Result;
import com.weiran.manage.cloud.UaaClient;
import com.weiran.manage.entity.Goods;
import com.weiran.manage.entity.SeckillGoods;
import com.weiran.manage.service.ManageGoodsService;
import com.weiran.manage.service.ManageSeckillGoodsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@ApiOperation("后台管理控制器")
public class AdminController {

    final ManageGoodsService manageGoodsService;
    final ManageSeckillGoodsService manageSeckillGoodsService;
    final UaaClient uaaClient;

    @RequestMapping("/")
    public String getAdmin() {
        return "/admin.html";
    }

    @ApiOperation("增加商品")
    @ResponseBody
    @RequestMapping("/addGoods")
    public Result addGoods(Goods goods) {
        return manageGoodsService.addGoods(goods);
    }

    @ApiOperation("删除商品")
    @ResponseBody
    @RequestMapping("/deleteGoods")
    public Result deleteGoods(@RequestParam("id") long id) {
        return manageGoodsService.deleteGoods(id);
    }

    @ApiOperation("更改商品详情")
    @ResponseBody
    @RequestMapping("/changeGoods")
    public Result changeGoods(@RequestParam("id") long id) {
        return manageGoodsService.changeGoods(id);
    }

    @ApiOperation("显示所有商品列表")
    @ResponseBody
    @RequestMapping("/getAllGoods")
    public Result getAllGoods() {
        return manageGoodsService.getAllGoods();
    }

    @ApiOperation("增加秒杀商品")
    @ResponseBody
    @RequestMapping("/addSeckillGoods")
    public Result addSeckillGoods(SeckillGoods seckillGoods) {
        return manageSeckillGoodsService.addSeckillGoods(seckillGoods);
    }

    @ApiOperation("删除秒杀商品")
    @ResponseBody
    @RequestMapping("/deleteSeckillGoods")
    public Result deleteSeckillGoods(@RequestParam("goodsId") long goodsId) {
        return manageSeckillGoodsService.deleteSeckillGoods(goodsId);
    }

    @ApiOperation("更改秒杀商品详情")
    @ResponseBody
    @RequestMapping("/changeSeckillGoods")
    public Result changeSeckillGoods(@RequestParam("goodsId") long goodsId) {
        return manageSeckillGoodsService.changeSeckillGoods(goodsId);
    }

    @ApiOperation("显示所有秒杀商品列表")
    @ResponseBody
    @RequestMapping("/getAllSeckillGoods")
    public Result getAllSeckillGoods() {
        return manageSeckillGoodsService.getAllSeckillGoods();
    }

    @ApiOperation("后台查询本次成功参与活动的用户信息")
    @ResponseBody
    @RequestMapping("/inquiryUser")
    public Result inquiryUser(@RequestParam("userId") long userId) {
        return uaaClient.inquiryUser(userId);
    }

}

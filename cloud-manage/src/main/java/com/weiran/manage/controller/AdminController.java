package com.weiran.manage.controller;

import com.weiran.common.obj.Result;
import com.weiran.manage.cloud.MissionClient;
import com.weiran.manage.cloud.UaaClient;
import com.weiran.manage.entity.Goods;
import com.weiran.manage.entity.SeckillGoods;
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

    final UaaClient uaaClient;
    final MissionClient missionClient;

    @RequestMapping("/")
    public String getAdmin() {
        return "/admin.html";
    }

    @ApiOperation("后台查询本次成功参与活动的用户信息")
    @ResponseBody
    @RequestMapping("/inquiryUser")
    public Result inquiryUser(@RequestParam("userId") long userId) {
        return uaaClient.inquiryUser(userId);
    }

    @ApiOperation("增加商品")
    @ResponseBody
    @RequestMapping("/addGoods")
    public Result addGoods(Goods goods) {
        return missionClient.addGoods(goods);
    }

    @ApiOperation("删除商品")
    @ResponseBody
    @RequestMapping("/deleteGoods")
    public Result deleteGoods(@RequestParam("id") long id) {
        return missionClient.deleteGoods(id);
    }

    @ApiOperation("更改商品详情")
    @ResponseBody
    @RequestMapping("/changeGoods")
    public Result changeGoods(@RequestParam("id") long id) {
        return missionClient.changeGoods(id);
    }

    @ApiOperation("在库存表增加")
    @ResponseBody
    @RequestMapping("/addSeckillGoods")
    public Result addSeckillGoods(SeckillGoods seckillGoods) {
        return missionClient.addSeckillGoods(seckillGoods);
    }

    @ApiOperation("在库存表删除")
    @ResponseBody
    @RequestMapping("/deleteSeckillGoods")
    public Result deleteSeckillGoods(@RequestParam("goodsId") long goodsId) {
        return missionClient.deleteSeckillGoods(goodsId);
    }

    @ApiOperation("在库存表更改")
    @ResponseBody
    @RequestMapping("/changeSeckillGoods")
    public Result changeSeckillGoods(@RequestParam("goodsId") long goodsId) {
        return missionClient.changeSeckillGoods(goodsId);
    }


}

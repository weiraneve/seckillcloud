package com.weiran.mission.controller;

import com.weiran.common.obj.Result;
import com.weiran.mission.annotations.AccessLimit;
import com.weiran.mission.service.SeckillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 秒杀控制器
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("seckill")
public class SeckillController  {

    final SeckillService seckillService;

    /**
     * 秒杀接口
     */
    @PostMapping(value = "/{path}/seckill")
    @ResponseBody
    public Result<Integer> doSeckill(@RequestParam("goodsId") long goodsId,
                                @PathVariable("path") String path,
                                HttpServletRequest request) {

        return seckillService.doSeckill(goodsId, path, request);
    }

    /**
     * 客户端轮询查询是否下单成功
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     */
    @GetMapping(value = "/result")
    @ResponseBody
    public Result<Long> seckillResult(@RequestParam("goodsId") long goodsId, HttpServletRequest request) {
        return seckillService.seckillResult(goodsId, request);
    }

    /**
     * 返回一个唯一的path的id
     */
    @AccessLimit(seconds = 5, maxCount = 5)
    @GetMapping(value = "/path")
    @ResponseBody
    public Result<String> getSeckillPath(HttpServletRequest request, @RequestParam("goodsId") long goodsId) {
        return seckillService.getSeckillPath(request, goodsId);
    }
}

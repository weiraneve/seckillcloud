package com.weiran.mission.controller;

import com.weiran.common.obj.Result;
import com.weiran.mission.annotations.SeckillLimit;
import com.weiran.mission.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api("秒杀控制器")
@RequiredArgsConstructor
@RequestMapping("seckill")
public class SeckillController {

    private final SeckillService seckillService;

    /**
     * 秒杀接口
     */
    @ApiOperation("秒杀接口")
    @GetMapping
    public Result<Integer> doSeckill(@RequestParam("goodsId") long goodsId,
                                     @RequestParam("path") String path,
                                     HttpServletRequest request) {

        return seckillService.doSeckill(goodsId, path, request);
    }

    /**
     * 客户端轮询查询是否下单成功
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     */
    @ApiOperation("客户端轮询查询是否下单成功")
    @GetMapping(value = "/result")
    public Result<Long> seckillResult(@RequestParam("goodsId") long goodsId, HttpServletRequest request) {
        return seckillService.seckillResult(goodsId, request);
    }

    /**
     * 返回一个唯一的path的id
     * 注解配合拦截器，限制规定时间内访问次数
     */
    @ApiOperation("返回一个唯一的path的id")
    @SeckillLimit(seconds = 5, maxCount = 5)
    @GetMapping(value = "/getPath")
    public Result<String> getSeckillPath(HttpServletRequest request, @RequestParam("goodsId") long goodsId) {
        return seckillService.getSeckillPath(request, goodsId);
    }
}

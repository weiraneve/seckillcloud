package com.weiran.mission.controller;

import com.weiran.common.obj.Result;
import com.weiran.mission.service.OrderService;
import com.weiran.mission.pojo.vo.OrderDetailVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 秒杀订单控制器
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    final OrderService orderService;

    @ApiOperation("根据OrderId返回订单详细信息")
    @ApiImplicitParam(value = "订单Id", dataType = "long", required = true)
    @GetMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(@RequestParam("orderId") long orderId) {
        return orderService.info(orderId);
    }
}

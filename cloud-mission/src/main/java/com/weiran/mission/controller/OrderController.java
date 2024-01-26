package com.weiran.mission.controller;

import com.weiran.common.obj.Result;
import com.weiran.mission.pojo.vo.OrderDetailVo;
import com.weiran.mission.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api("订单控制器")
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    final OrderService orderService;

    @ApiOperation("返回客户的所有订单数据")
    @GetMapping
    public Result<List<OrderDetailVo>> getOrderList(HttpServletRequest request) {
        return orderService.getOrderList(request);
    }

}

package com.weiran.mission.controller;

import com.weiran.common.obj.Result;
import com.weiran.mission.service.OrderService;
import com.weiran.mission.pojo.vo.OrderDetailVo;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 订单控制器
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    final OrderService orderService;

    @ApiOperation("返回客户的所有订单数据")
    @GetMapping
    @ResponseBody
    public Result<List<OrderDetailVo>> getOrderList(HttpServletRequest request) {
        return orderService.getOrderList(request);
    }

}

package com.weiran.manage.controller.web;

import com.github.pagehelper.PageInfo;
import com.weiran.manage.entity.web.Order;
import com.weiran.manage.response.ResultVO;
import com.weiran.manage.service.web.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    /**
     * 分页查询订单
     */
    @GetMapping
    public ResultVO findByOrders(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
                                 Long id) {
        PageInfo<Order> order = orderService.findByOrders(page, pageSize, id);
        return ResultVO.success(order);
    }

}

package com.weiran.manage.controller.web;

import com.github.pagehelper.PageInfo;
import com.weiran.manage.dto.web.OrderDTO;
import com.weiran.common.obj.Result;
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
    public Result<PageInfo<OrderDTO>> findByOrders(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
                                 Long id) {
        PageInfo<OrderDTO> order = orderService.findByOrders(page, pageSize, id);
        return Result.success(order);
    }

}

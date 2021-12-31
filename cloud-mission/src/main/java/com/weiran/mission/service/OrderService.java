package com.weiran.mission.service;

import com.weiran.common.obj.Result;
import com.weiran.mission.pojo.vo.OrderDetailVo;

public interface OrderService {

    /**
     * 查询订单信息
     */
    Result<OrderDetailVo> info(long orderId);

}

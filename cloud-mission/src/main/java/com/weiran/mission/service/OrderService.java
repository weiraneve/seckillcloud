package com.weiran.mission.service;

import com.weiran.common.obj.Result;
import com.weiran.mission.pojo.vo.OrderDetailVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {

    /**
     * 返回客户的所有订单数据
     */
    Result<List<OrderDetailVo>> getOrderList(HttpServletRequest request);

}

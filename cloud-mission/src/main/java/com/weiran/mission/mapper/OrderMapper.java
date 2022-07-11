package com.weiran.mission.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weiran.mission.pojo.entity.Order;
import com.weiran.common.pojo.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
@DS("order")
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 查询订单
     */
    List<OrderDTO> findByOrder();

    /**
     * 根据传入的id查询订单
     */
    List<OrderDTO> findOrderById(Long id);

}

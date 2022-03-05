package com.weiran.manage.mapper.web;

import com.weiran.manage.entity.web.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 *
 */
@Mapper
@Repository
public interface OrderMapper {

    /**
     * 查询订单
     */
    List<Order> findByOrder();

    /**
     * 根据传入的id查询订单
     */
    List<Order> findOrderById(Long id);

}

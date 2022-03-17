package com.weiran.manage.mapper.web;

import com.weiran.manage.dto.web.OrderDTO;
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
    List<OrderDTO> findByOrder();

    /**
     * 根据传入的id查询订单
     */
    List<OrderDTO> findOrderById(Long id);

}

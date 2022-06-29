package com.weiran.mission.service;


import com.github.pagehelper.PageInfo;
import com.weiran.common.pojo.dto.SeckillGoodsDTO;

public interface SeckillGoodsService {

    /**
     * 库存减一
     */
    int reduceStock(long goodsId);

    /**
     * 分页查询秒杀商品
     */
    PageInfo<SeckillGoodsDTO> findSeckill(Integer page, Integer pageSize, Long goodsId);

}

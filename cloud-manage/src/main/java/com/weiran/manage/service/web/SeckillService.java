package com.weiran.manage.service.web;

import com.github.pagehelper.PageInfo;
import com.weiran.manage.dto.web.SeckillGoodsDTO;


public interface SeckillService {

    /**
     * 分页查询
     */
    PageInfo<SeckillGoodsDTO> findSeckill(Integer page, Integer pageSize, Long goodsId);

    /**
     * 增加秒杀商品
     */
    void addSeckillGoods(SeckillGoodsDTO seckillGoodsDTO);

    /**
     * 删除秒杀商品
     */
    void deleteSeckillGoods(Long goodsId);

}

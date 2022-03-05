package com.weiran.manage.service.web;

import com.github.pagehelper.PageInfo;
import com.weiran.manage.entity.web.SeckillGoods;


public interface SeckillService {

    /**
     * 分页查询
     */
    PageInfo<SeckillGoods> findSeckill(Integer page, Integer pageSize, Long goodsId);

    /**
     * 增加秒杀商品
     */
    void addSeckillGoods(SeckillGoods seckillGoods);

    /**
     * 删除秒杀商品
     */
    void deleteSeckillGoods(Long goodsId);

}

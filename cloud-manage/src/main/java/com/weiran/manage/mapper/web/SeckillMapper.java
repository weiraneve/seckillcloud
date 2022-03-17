package com.weiran.manage.mapper.web;


import com.weiran.manage.dto.web.SeckillGoodsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SeckillMapper {

    /**
     * 查询全部
     */
    List<SeckillGoodsDTO> findSeckill();

    /**
     * 通过goodsId模糊查询
     */
    List<SeckillGoodsDTO> findByGoodsIdLike(Long goodsId);

    /**
     * 增加秒杀商品
     */
    int add(@Param("seckillGoodsDTO") SeckillGoodsDTO seckillGoodsDTO);

    /**
     * 删除秒杀商品
     */
    void delete(@Param("goodsId") Long goodsId);

    /**
     * 更新
     */
    Integer update(@Param("seckillGoodsDTO") SeckillGoodsDTO seckillGoodsDTO);

}

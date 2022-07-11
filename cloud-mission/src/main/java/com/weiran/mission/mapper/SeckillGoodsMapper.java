package com.weiran.mission.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weiran.mission.pojo.entity.SeckillGoods;
import com.weiran.common.pojo.dto.SeckillGoodsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@DS("seckill")
public interface SeckillGoodsMapper extends BaseMapper<SeckillGoods> {

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
    void addSeckillGoods(@Param("seckillGoodsDTO") SeckillGoodsDTO seckillGoodsDTO);

    /**
     * 删除秒杀商品
     */
    void deleteSeckillGoods(@Param("goodsId") Long goodsId);

    /**
     * 更新
     */
    void updateSeckillGoods(@Param("seckillGoodsDTO") SeckillGoodsDTO seckillGoodsDTO);

}

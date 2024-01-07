package com.weiran.mission.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weiran.mission.pojo.entity.Goods;
import com.weiran.common.pojo.dto.GoodsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@DS("goods")
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 查询goods
     */
    List<GoodsDTO> findGoods();

    /**
     * 通过商品名模糊查询
     */
    List<GoodsDTO> findByGoodsNameLike(String goodsName);

    /**
     * 通过id查询goods
     */
    GoodsDTO selectGoodsById(@Param("id") Long id);

    /**
     * 根据id列表查询返回goods列表
     */
    List<GoodsDTO> findGoodsByIds(@Param("ids") List<String> ids);

    /**
     * 新增goods
     */
    void addGoods(@Param("goodsDTO") GoodsDTO goodsDTO);

    /**
     * 删除单个goods
     */
    void deleteGoods(@Param("id") Long id);

    /**
     * 批量删除
     */
    void deletesGoods(@Param("ids") List<String> ids);

    /**
     * 更新goods
     */
    void updateGoods(@Param("goodsDTO") GoodsDTO goodsDTO);

    /**
     * 通过id更新goods的Using状态
     */
    void updateGoodsUsingById(@Param("id") Long id);
}

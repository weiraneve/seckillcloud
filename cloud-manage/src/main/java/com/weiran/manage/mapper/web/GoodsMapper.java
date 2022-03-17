package com.weiran.manage.mapper.web;

import com.weiran.manage.dto.web.GoodsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GoodsMapper {

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
    GoodsDTO selectById(@Param("id") Long id);

    /**
     * 根据id列表查询返回goods列表
     */
    List<GoodsDTO> findGoodsByIds(@Param("ids") List<String> ids);

    /**
     * 新增goods
     */
    int add(@Param("goodsDTO") GoodsDTO goodsDTO);
    
    /**
     * 删除单个goods
     */
    void delete(@Param("id") Long id);

    /**
     * 批量删除
     */
    void deletes(@Param("ids") List<String> ids);

    /**
     * 更新goods
     */
    Integer update(@Param("goodsDTO") GoodsDTO goodsDTO);

    /**
     * 通过id更新goods的Using状态
     */
    void updateUsingById(@Param("id") Long id);

}

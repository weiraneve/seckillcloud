package com.weiran.manage.mapper.web;

import com.weiran.manage.entity.web.Goods;
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
    List<Goods> findGoods();

    /**
     * 通过商品名模糊查询
     */
    List<Goods> findByGoodsNameLike(String goodsName);

    /**
     * 通过id查询goods
     */
    Goods selectById(@Param("id") Long id);

    /**
     * 根据id列表查询返回goods列表
     */
    List<Goods> findGoodsByIds(@Param("ids") List<String> ids);

    /**
     * 新增goods
     */
    int add(@Param("goods") Goods goods);
    
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
    Integer update(@Param("goods") Goods goods);

    /**
     * 通过id更新goods的Using状态
     */
    void updateUsingById(@Param("id") Long id);

}

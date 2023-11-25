package com.weiran.mission.service;

import com.github.pagehelper.PageInfo;
import com.weiran.common.obj.Result;
import com.weiran.common.pojo.dto.GoodsDTO;
import com.weiran.mission.pojo.vo.GoodsDetailVo;

import java.util.List;

public interface GoodsService {

    /**
     * 显示商品列表
     */
    Result<List<GoodsDetailVo>> getGoodsList();

    /**
     * 显示秒杀商品细节
     */
    Result<GoodsDetailVo> getGoodsDetail(long goodsId);

    /**
     * 查询所有goods
     */
    PageInfo<GoodsDTO> findGoods(Integer page, Integer pageSize, String goodsName);

    /**
     * 单个删除goods
     */
    void delete(Long id);

    /**
     * 新增goods
     */
    void create(GoodsDTO goodsDTO);

    /**
     * 修改goods
     */
    void update(GoodsDTO goodsDTO);

    /**
     * 选择单个goods
     */
    GoodsDTO selectById(Long id);

    /**
     * 修改是否可用
     */
    void updateUsingById(Long id);

    /**
     * 批量删除goods
     */
    void deletes(String ids);

}

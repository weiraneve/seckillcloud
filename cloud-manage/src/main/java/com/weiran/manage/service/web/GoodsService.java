package com.weiran.manage.service.web;

import com.github.pagehelper.PageInfo;
import com.weiran.manage.dto.web.GoodsDTO;

public interface GoodsService {

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
    boolean create(GoodsDTO goodsDTO);

    /**
     * 修改goods
     */
    boolean update(GoodsDTO goodsDTO);

    /**
     * 选择单个goods
     */
    GoodsDTO selectById(Long id);

    /**
     * 修改是否可用
     */
    void updateUsingById(Long id);

    /**
     * 批量删除
     * @param ids
     */
    void deletes(String ids);

}

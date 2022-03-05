package com.weiran.manage.service.web;

import com.github.pagehelper.PageInfo;
import com.weiran.manage.entity.web.Goods;

public interface GoodsService {

    /**
     * 查询所有goods
     */
    PageInfo<Goods> findGoods(Integer page, Integer pageSize, String goodsName);

    /**
     * 单个删除goods
     */
    void delete(Long id);

    /**
     * 新增goods
     */
    boolean create(Goods goods);

    /**
     * 修改goods
     */
    boolean update(Goods goods);

    /**
     * 选择单个goods
     */
    Goods selectById(Long id);

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

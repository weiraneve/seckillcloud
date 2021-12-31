package com.weiran.manage.service;

import com.weiran.manage.entity.Goods;
import com.weiran.common.obj.Result;

public interface ManageGoodsService {

    /**
     * 增加商品
     */
    Result addGoods(Goods goods);

    /**
     * 删除商品
     */
    Result deleteGoods(long id);

    /**
     * 更改商品详情
     */
    Result changeGoods(long id);

    /**
     * 显示所有商品列表
     */
    Result getAllGoods();

}

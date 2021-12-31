package com.weiran.manage.service.impl;

import com.weiran.manage.entity.Goods;
import com.weiran.manage.manager.GoodsManager;
import com.weiran.manage.service.ManageGoodsService;
import com.weiran.common.obj.CodeMsg;
import com.weiran.common.obj.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManageGoodsServiceImpl implements ManageGoodsService {

    final GoodsManager goodsManager;

    // 增加商品
    @Override
    public Result addGoods(Goods goods) {
        goodsManager.save(goods);
        return Result.success(CodeMsg.SUCCESS);
    }

    // 删除商品
    @Override
    public Result deleteGoods(long id) {
        return Result.success(CodeMsg.SUCCESS);
    }

    // 更改商品详情
    @Override
    public Result changeGoods(long id) {
        return Result.success(CodeMsg.SUCCESS);
    }

    // 显示所有商品列表
    @Override
    public Result getAllGoods() {
        return null;
    }

}

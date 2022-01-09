package com.weiran.mission.service;

import com.weiran.common.obj.Result;
import com.weiran.mission.entity.Goods;
import com.weiran.mission.pojo.vo.GoodsDetailVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GoodsService {

    /**
     * 显示商品列表
     */
    Result<List<GoodsDetailVo>> getGoodsList();

    /**
     * 显示秒杀商品细节
     */
    Result<GoodsDetailVo> getDetail(long goodsId);

    /**
     * 增加商品
     */
    Result addGoods(Goods goods);

    /**
     * 删除商品
     */
    Result deleteGoods(@RequestParam("id") long id);

    /**
     * 更改商品详情
     */
    Result changeGoods(Goods goods, @RequestParam("id") long id);

}

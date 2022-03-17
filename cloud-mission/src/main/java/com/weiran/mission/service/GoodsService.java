package com.weiran.mission.service;

import com.weiran.common.obj.Result;
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
    Result<GoodsDetailVo> getDetail(long goodsId);

}

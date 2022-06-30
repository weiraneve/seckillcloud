package com.weiran.mission.utils;

import com.weiran.common.pojo.dto.GoodsDTO;
import com.weiran.common.pojo.dto.SeckillGoodsDTO;

public class POJOConverter {

    public static SeckillGoodsDTO converter(GoodsDTO goodsDTO) {
        SeckillGoodsDTO seckillGoodsDTO = new SeckillGoodsDTO();
        seckillGoodsDTO.setGoodsId(goodsDTO.getId());
        seckillGoodsDTO.setStockCount(goodsDTO.getGoodsStock());
        return seckillGoodsDTO;
    }
}

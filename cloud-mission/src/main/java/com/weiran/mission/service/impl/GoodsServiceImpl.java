package com.weiran.mission.service.impl;

import com.weiran.common.enums.RedisCacheTimeEnum;
import com.weiran.common.obj.Result;
import com.weiran.common.redis.key.GoodsKey;
import com.weiran.common.redis.key.SeckillGoodsKey;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.mission.entity.Goods;
import com.weiran.mission.manager.GoodsManager;
import com.weiran.mission.service.GoodsService;
import com.weiran.mission.pojo.vo.GoodsDetailVo;
import com.weiran.mission.service.SeckillGoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    final GoodsManager goodsManager;
    final RedisService redisService;
    final SeckillGoodsService seckillGoodsService;

    /**
     * 系统初始化，把商品信息加载到Redis缓存中。后续客户访问都从缓存中读取。
     */
    @PostConstruct
    public void initGoodsInfo() {
        List<Goods> goodsList = goodsManager.list();
        if (goodsList == null) {
            return;
        }
        for (Goods goods : goodsList) {
            redisService.set(GoodsKey.goodsKey, "" + goods.getId(), goods, RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
        }
    }

    // 显示商品列表
    @Override
    public Result<List<GoodsDetailVo>> getGoodsList() {
        List<GoodsDetailVo> goodsDetailVoList = new ArrayList<>();
        // 这里想要遍历缓存中所有商品，但没有更好的办法
        for (long goodsId = 1L; goodsId < 50L; goodsId++) {
            // 是否存在
            if (!redisService.exists(GoodsKey.goodsKey, "" + goodsId)) {
                continue;
            }
            Result<GoodsDetailVo> result = getDetail(goodsId);
            // 如果是null，则说明商品信息为不可用，不发送给前端
            if (result == null) {
                continue;
            }
            GoodsDetailVo goodsDetailVo = result.getData();
            if (goodsDetailVo.getGoods().getIsUsing())
            goodsDetailVoList.add(goodsDetailVo);
        }
        Result<List<GoodsDetailVo>> result = new Result<>();
        result.setData(goodsDetailVoList);
        return result;
    }

    // 显示商品细节。剩余时间等于0，正在秒杀中；剩余时间大于0，还没有开始秒杀；小于0，已经结束秒杀。
    @Override
    public Result<GoodsDetailVo> getDetail(long goodsId) {
        // 从Redis中读取商品数据，这样多次访问都从缓存中取，减少数据库的压力
        Goods goods = redisService.get(GoodsKey.goodsKey, "" + goodsId, Goods.class);
        // 判断商品信息是否可用
        if (!goods.getIsUsing()) {
            return null;
        }
        // 用goodsId取出存在Redis中的秒杀商品中的库存值
        int stockCount = redisService.get(SeckillGoodsKey.seckillCount, "" + goodsId, Integer.class);
        long startAt = Timestamp.valueOf(goods.getStartTime()).getTime(); // 秒杀开始时间
        long endAt = Timestamp.valueOf(goods.getEndTime()).getTime(); // 秒杀结束时间
        long now = System.currentTimeMillis(); // 系统当前时间
        int remainSeconds; // 定义剩余时间
        if (now < startAt) { // 秒杀还没开始，倒计时
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) { // 秒杀已经结束
            remainSeconds = -1;
        } else { // 秒杀进行中
            remainSeconds = 0;
        }
        return Result.success(GoodsDetailVo.builder()
                .goods(goods)
                .stockCount(stockCount)
                .remainSeconds(remainSeconds)
                .build());
    }

}


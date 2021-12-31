package com.weiran.mission.service.impl;

import com.weiran.common.obj.CodeMsg;
import com.weiran.common.obj.Result;
import com.weiran.common.redis.key.GoodsBoKey;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.mission.pojo.bo.GoodsBo;
import com.weiran.mission.entity.Goods;
import com.weiran.mission.entity.SeckillGoods;
import com.weiran.mission.manager.GoodsManager;
import com.weiran.mission.manager.SeckillGoodsManager;
import com.weiran.mission.service.GoodsService;
import com.weiran.mission.pojo.vo.GoodsBoListVo;
import com.weiran.mission.pojo.vo.GoodsDetailVo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    final GoodsManager goodsManager;
    final SeckillGoodsManager seckillGoodsManager;
    final RedisService redisService;
    final ApplicationContext applicationContext;

    // 存储项目中所有goodsBo的id
    private List<Long> goodsBoIdList = new ArrayList<>();

    // 显示商品列表
    @Override
    public Result<GoodsBoListVo> getGoodsList() {
        // 从Redis中读取商品数据，这样多次访问都从缓存中取，减少数据库的压力
        List<GoodsBo> goodsBoList = new ArrayList<>();
        for (Long goodsBoId : goodsBoIdList) {
            GoodsBo goodsBo = redisService.get(GoodsBoKey.goodsKey, "" + goodsBoId, GoodsBo.class);
            goodsBoList.add(goodsBo);
        }
        GoodsBoListVo goodsBoListVo = new GoodsBoListVo();
        goodsBoListVo.setGoodsBoList(goodsBoList);
        Result result = new Result();
        result.setData(goodsBoListVo);
        return result;
    }

    // 显示秒杀商品细节
    @Override
    public Result<GoodsDetailVo> getDetail(long goodsId) {
        GoodsBo goodsBo = getGoodsBoByGoodsId(goodsId);
        if (goodsBo == null) {
            return Result.error(CodeMsg.NO_GOODS);
        } else {
            long startAt = goodsBo.getStartDate().getTime();
            long endAt = goodsBo.getEndDate().getTime();
            long now = System.currentTimeMillis();

            int seckillStatus;
            int remainSeconds;
            if (now < startAt) { // 秒杀还没开始，倒计时
                seckillStatus = 0;
                remainSeconds = (int) ((startAt - now) / 1000);
            } else if (now > endAt) { // 秒杀已经结束
                seckillStatus = 2;
                remainSeconds = -1;
            } else { // 秒杀进行中
                seckillStatus = 1;
                remainSeconds = 0;
            }
            GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
            goodsDetailVo.setGoodsBo(goodsBo);
            goodsDetailVo.setRemainSeconds(remainSeconds);
            goodsDetailVo.setSeckillStatus(seckillStatus);
            return Result.success(goodsDetailVo);
        }
    }

    // 查询全部的商品
    @Override
    public List<GoodsBo> selectAllGoods() {
        List<GoodsBo> resultList = new ArrayList<>();
        List<Goods> goodsList = goodsManager.list();
        List<SeckillGoods> seckillGoodsList = seckillGoodsManager.list();
        for (Goods goods : goodsList) {
            for (SeckillGoods seckillGoods : seckillGoodsList) {
                if (goods.getId() == seckillGoods.getGoodsId()) {
                    GoodsBo goodsBo = new GoodsBo();
                    goodsBo.setId(goods.getId());
                    goodsBoIdList.add(goods.getId());
                    goodsBo.setGoodsName(goods.getGoodsName());
                    goodsBo.setGoodsTitle(goods.getGoodsTitle());
                    goodsBo.setGoodsImg(goods.getGoodsImg());
                    goodsBo.setGoodsPrice(goods.getGoodsPrice());
                    goodsBo.setGoodsStock(goods.getGoodsStock());
                    // LocalDateTime转为Date
                    goodsBo.setCreateDate(Date.from(goods.getCreateDate().atZone(ZoneId.systemDefault()).toInstant()));
                    goodsBo.setUpdateDate(Date.from(goods.getUpdateDate().atZone(ZoneId.systemDefault()).toInstant()));
                    goodsBo.setSeckillPrice(seckillGoods.getSeckillPrice());
                    goodsBo.setStockCount(seckillGoods.getStockCount());
                    goodsBo.setStartDate(Date.from(seckillGoods.getStartDate().atZone(ZoneId.systemDefault()).toInstant()));
                    goodsBo.setEndDate(Date.from(seckillGoods.getEndDate().atZone(ZoneId.systemDefault()).toInstant()));
                    resultList.add(goodsBo);
                }
            }
        }
        return resultList;
    }

    // 根据商品id查询返回goodsBo
    @Override
    public GoodsBo getGoodsBoByGoodsId(long goodsId) {
        GoodsBo goodsBo = new GoodsBo();
        List<Goods> goodsList = goodsManager.list();
        List<SeckillGoods> seckillGoodsList = seckillGoodsManager.list();
        for (Goods goods : goodsList) {
            for (SeckillGoods seckillGoods : seckillGoodsList) {
                if (goods.getId() == goodsId && seckillGoods.getGoodsId() == goodsId) {
                    goodsBo.setId(goods.getId());
                    goodsBo.setGoodsName(goods.getGoodsName());
                    goodsBo.setGoodsTitle(goods.getGoodsTitle());
                    goodsBo.setGoodsImg(goods.getGoodsImg());
                    goodsBo.setGoodsPrice(goods.getGoodsPrice());
                    goodsBo.setGoodsStock(goods.getGoodsStock());
                    // LocalDateTime转为Date
                    goodsBo.setCreateDate(Date.from(goods.getCreateDate().atZone(ZoneId.systemDefault()).toInstant()));
                    goodsBo.setUpdateDate(Date.from(goods.getUpdateDate().atZone(ZoneId.systemDefault()).toInstant()));
                    goodsBo.setSeckillPrice(seckillGoods.getSeckillPrice());
                    goodsBo.setStockCount(seckillGoods.getStockCount());
                    goodsBo.setStartDate(Date.from(seckillGoods.getStartDate().atZone(ZoneId.systemDefault()).toInstant()));
                    goodsBo.setEndDate(Date.from(seckillGoods.getEndDate().atZone(ZoneId.systemDefault()).toInstant()));
                }
            }
        }
        return goodsBo;
    }
}


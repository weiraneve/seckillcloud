package com.weiran.manage.service.web.impl;

import cn.hutool.core.util.BooleanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiran.common.enums.RedisCacheTimeEnum;
import com.weiran.common.redis.key.GoodsKey;
import com.weiran.common.redis.key.SeckillGoodsKey;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.manage.entity.web.SeckillGoods;
import com.weiran.manage.exception.CustomizeException;
import com.weiran.manage.mapper.web.GoodsMapper;
import com.weiran.manage.entity.web.Goods;
import com.weiran.manage.enums.ResponseEnum;
import com.weiran.manage.mapper.web.SeckillMapper;
import com.weiran.manage.service.web.GoodsService;
import com.weiran.manage.service.web.SeckillService;
import com.weiran.manage.utils.qiniu.ImageScalaKit;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final ImageScalaKit imageScalaKit;
    private final GoodsMapper goodsMapper;
    private final RedisService redisService;
    private final SeckillService seckillService;
    private final SeckillMapper seckillMapper;

    // 查询所有goods
    @Override
    public PageInfo<Goods> findGoods(Integer page, Integer pageSize, String goodsName) {
        PageHelper.startPage(page, pageSize);
        List<Goods> goodsList;
        if (StringUtils.isEmpty(goodsName)) {
            goodsList = goodsMapper.findGoods();
        } else {
            // 如果有字段传入，则模糊查询
            goodsList = goodsMapper.findByGoodsNameLike(goodsName);
        }
        return new PageInfo<>(goodsList);
    }

    // 新增商品
    @Override
    public boolean create(Goods goods) {
        int row = goodsMapper.add(goods);
        // 表增加后，在缓存中增加
        if (row > 0) {
            SeckillGoods seckillGoods = new SeckillGoods();
            seckillGoods.setStockCount(goods.getGoodsStock());
            seckillGoods.setGoodsId(goods.getId());
            seckillMapper.add(seckillGoods);
            redisService.set(GoodsKey.goodsKey, "" + goods.getId(), goods, RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
            redisService.set(SeckillGoodsKey.seckillCount, "" + goods.getId(), goods.getGoodsStock(), RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
        }
        return row > 0;
    }

    // 删除指定商品
    @Override
    public void delete(Long id) {
        Goods goods = goodsMapper.selectById(id);
        if (goods.getGoodsImg() != null) {
            try {
                URL url = new URL(goods.getGoodsImg());
                imageScalaKit.delete(url.getPath().replaceFirst("/",""));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        // 删除对应缓存
        redisService.delete(GoodsKey.goodsKey, "" + id);
        // 删除库存对应缓存
        redisService.delete(SeckillGoodsKey.seckillCount, "" + id);
        goodsMapper.delete(id);
        seckillMapper.delete(id);
    }

    // 批量删除商品
    @Override
    public void deletes(String ids) {
        String[] split = ids.split(",");
        List<String> goodsIds = Arrays.asList(split);
        deleteGoods(goodsIds);
    }

    // 删除
    @Async
    void deleteGoods(List<String> goodsIds) {
        List<Goods> goodsList = goodsMapper.findGoodsByIds(goodsIds);
        for (Goods goods : goodsList) {
            try {
                URL url = new URL(goods.getGoodsImg());
                imageScalaKit.delete(url.getPath().replaceFirst("/",""));
                // 删除对应缓存
                redisService.delete(GoodsKey.goodsKey, "" + goods.getId());
                redisService.delete(SeckillGoodsKey.seckillCount, "" + goods.getId());
                goodsMapper.delete(goods.getId());
                seckillMapper.delete(goods.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 更新商品
    @Override
    public boolean update(Goods goods) {
        int row = goodsMapper.update(goods);
        if (row > 0) {
            // 更改对应缓存
            redisService.set(GoodsKey.goodsKey, "" + goods.getId(), goods, RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
            // 更改秒杀对应缓存
            redisService.set(SeckillGoodsKey.seckillCount, "" + goods.getId(), goods.getGoodsStock(), RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
            SeckillGoods seckillGoods = new SeckillGoods();
            seckillGoods.setGoodsId(goods.getId());
            seckillGoods.setStockCount(goods.getGoodsStock());
            // 更改秒杀库
            seckillMapper.update(seckillGoods);
        }
        return row > 0;
    }

    // 选择单个商品
    @Override
    public Goods selectById(Long id) {
        Goods goods = goodsMapper.selectById(id);
        if (goods == null) {
            throw new CustomizeException(ResponseEnum.RESOURCE_NOT_FOUND);
        }
        return goods;
    }

    // 修改商品的是否可用
    @Override
    public void updateUsingById(Long id) {
        goodsMapper.updateUsingById(id);
        Goods goods = redisService.get(GoodsKey.goodsKey, "" + id, Goods.class);
        goods.setIsUsing(BooleanUtil.negate(goods.getIsUsing())); // 布尔值取反
        // 商品缓存中更改
        redisService.set(GoodsKey.goodsKey, "" + id, goods, RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
    }
}

package com.weiran.mission.service.impl;

import com.github.pagehelper.PageInfo;
import com.weiran.common.pojo.dto.SeckillGoodsDTO;
import com.weiran.common.redis.key.SeckillGoodsKey;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.mission.manager.SeckillGoodsManager;
import com.weiran.mission.mapper.SeckillGoodsMapper;
import com.weiran.mission.pojo.entity.SeckillGoods;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeckillGoodsServiceImplTest {

    @InjectMocks
    private SeckillGoodsServiceImpl seckillGoodsService;

    @Mock
    private SeckillGoodsManager seckillGoodsManager;

    @Mock
    private RedisService redisService;

    @Mock
    private SeckillGoodsMapper seckillGoodsMapper;

    @Test
    public void testReduceStock() {
        long goodsId = 1L;
        SeckillGoods seckillGoods = new SeckillGoods();
        seckillGoods.setStockCount(5);

        // 使用 any() 来匹配任何 LambdaQueryWrapper 参数
        when(seckillGoodsManager.getOne(any())).thenReturn(seckillGoods);

        when(redisService.get(SeckillGoodsKey.seckillCount, String.valueOf(goodsId), Integer.class))
                .thenReturn(4);
        when(seckillGoodsManager.update(eq(seckillGoods), any())).thenReturn(true);

        int result = seckillGoodsService.reduceStock(goodsId);

        assertEquals(1, result);
        verify(redisService, times(1)).get(SeckillGoodsKey.seckillCount, String.valueOf(goodsId), Integer.class);
        verify(seckillGoodsManager, times(1)).update(eq(seckillGoods), any());
    }


    @Test
    public void testFindSeckill() {
        int page = 1;
        int pageSize = 5;
        long goodsId = 1L;

        SeckillGoodsDTO seckillGoodsDTO = new SeckillGoodsDTO();
        when(seckillGoodsMapper.findSeckill()).thenReturn(Collections.singletonList(seckillGoodsDTO));
        when(seckillGoodsMapper.findByGoodsIdLike(goodsId)).thenReturn(Collections.singletonList(seckillGoodsDTO));

        PageInfo<SeckillGoodsDTO> pageInfo = seckillGoodsService.findSeckill(page, pageSize, null);

        assertEquals(1, pageInfo.getList().size());

        pageInfo = seckillGoodsService.findSeckill(page, pageSize, goodsId);

        assertEquals(1, pageInfo.getList().size());
        verify(seckillGoodsMapper, times(1)).findByGoodsIdLike(goodsId);
    }
}

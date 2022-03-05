//package com.weiran.manage.cloud.fallback;
//
//import com.weiran.common.obj.Result;
//import com.weiran.manage.cloud.MissionClient;
//import com.weiran.manage.entity.Goods;
//import com.weiran.manage.entity.SeckillGoods;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class MissionClientFallback implements MissionClient {
//
//    // 在库存表增加
//    @Override
//    public Result addSeckillGoods(SeckillGoods seckillGoods) {
//        log.warn("=============== addSeckillGoods方法调用失败，服务被降级");
//        return null;
//    }
//
//    // 在库存表删除
//    @Override
//    public Result deleteSeckillGoods(long goodsId) {
//        log.warn("=============== deleteSeckillGoods方法调用失败，服务被降级");
//        return null;
//    }
//
//    // 在库存表更改
//    @Override
//    public Result changeSeckillGoods(long goodsId) {
//        log.warn("=============== changeSeckillGoods方法调用失败，服务被降级");
//        return null;
//    }
//
//    // 增加商品
//    @Override
//    public Result addGoods(Goods goods) {
//        log.warn("=============== addGoods方法调用失败，服务被降级");
//        return null;
//    }
//
//    // 删除商品
//    @Override
//    public Result deleteGoods(long id) {
//        log.warn("=============== deleteGoods方法调用失败，服务被降级");
//        return null;
//    }
//
//    // 更改商品详情
//    @Override
//    public Result changeGoods(long id) {
//        log.warn("=============== changeGoods方法调用失败，服务被降级");
//        return null;
//    }
//
//}

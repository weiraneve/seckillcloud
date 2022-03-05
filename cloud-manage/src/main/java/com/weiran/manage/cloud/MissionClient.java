//package com.weiran.manage.cloud;
//
//import com.weiran.common.obj.Result;
//import com.weiran.manage.cloud.fallback.MissionClientFallback;
//import com.weiran.manage.config.FeignConfig;
//import com.weiran.manage.entity.Goods;
//import com.weiran.mission.entity.SeckillGoods;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@FeignClient(value = "cloud-mission", fallback = MissionClientFallback.class, configuration = FeignConfig.class)
//public interface MissionClient {
//
//    /**
//     * 在库存表增加
//     */
//    @RequestMapping("/seckillGoods/addSeckillGoods")
//    Result addSeckillGoods(SeckillGoods seckillGoods);
//
//    /**
//     * 在库存表删除
//     */
//    @RequestMapping("/seckillGoods/deleteSeckillGoods")
//    Result deleteSeckillGoods(@RequestParam("goodsId") long goodsId);
//
//    /**
//     * 在库存表更改
//     */
//    @RequestMapping("/seckillGoods/changeSeckillGoods")
//    Result changeSeckillGoods(@RequestParam("goodsId") long goodsId);
//
//    /**
//     * 增加商品
//     */
//    @RequestMapping("/goods/addGoods")
//    Result addGoods(Goods goods);
//
//    /**
//     * 删除商品
//     */
//    @RequestMapping("/goods/deleteGoods")
//    Result deleteGoods(@RequestParam("id") long id);
//
//    /**
//     * 更改商品详情
//     */
//    @RequestMapping("/goods/changeGoods")
//    Result changeGoods(@RequestParam("id") long id);
//
//}

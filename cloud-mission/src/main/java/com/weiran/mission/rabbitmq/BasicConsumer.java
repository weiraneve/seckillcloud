package com.weiran.mission.rabbitmq;

import cn.hutool.json.JSONUtil;
import com.weiran.mission.pojo.bo.GoodsBo;
import com.weiran.mission.entity.User;
import com.weiran.mission.service.GoodsService;
import com.weiran.mission.service.SeckillOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * rabbitmq demo-消费者
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BasicConsumer {

    final SeckillOrderService seckillOrderService;
    final GoodsService goodsService;

    /**
     * 监听并接收消费队列中的消息-在这里采用单一容器工厂实例即可
     */
    @RabbitListener(queues = RabbitMqConstants.BASIC_QUEUE, containerFactory = "singleListenerContainer") // 设置消费者监听的队列以及监听的消息容器
    public void consumeMsg(SeckillMessage seckillMessage) {
        try {
            log.info("rabbitmq demo-消费者-监听消息：{} ", JSONUtil.toJsonStr(seckillMessage));
            User user = seckillMessage.getUser();
            long goodsId = seckillMessage.getGoodsId();
            GoodsBo goodsBo = goodsService.getGoodsBoByGoodsId(goodsId);
            // 减库存 下订单 写入秒杀订单
            seckillOrderService.insertByUserAndGoodsBo(user, goodsBo);
        } catch (Exception e) {
            log.error("rabbitmq demo-消费者-发生异常：", e.fillInStackTrace());
        }
    }
}

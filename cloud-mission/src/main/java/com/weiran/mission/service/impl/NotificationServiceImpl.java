package com.weiran.mission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weiran.mission.mapper.SeckillNotificationMapper;
import com.weiran.mission.pojo.entity.SeckillNotification;
import com.weiran.mission.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final int LOOP_CHECK_INTERVAL = 60000;

    private final SeckillNotificationMapper notificationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createNotification(Long userId, Long goodsId) {
        log.info("创建秒杀通知 - 用户ID: {}, 商品ID: {}", userId, goodsId);
        LambdaQueryWrapper<SeckillNotification> checkWrapper = new LambdaQueryWrapper<>();
        checkWrapper.eq(SeckillNotification::getUserId, userId)
                .eq(SeckillNotification::getGoodsId, goodsId)
                .eq(SeckillNotification::getIsNotified, false);

        if (notificationMapper.selectCount(checkWrapper) > 0) {
            throw new RuntimeException("已经订阅过该商品的秒杀通知");
        }

        SeckillNotification notification = new SeckillNotification();
        notification.setUserId(userId);
        notification.setGoodsId(goodsId);
        notification.setIsNotified(false);

        try {
            notificationMapper.insert(notification);
            log.info("Successfully created notification for userId: {}, goodsId: {}", userId, goodsId);
        } catch (Exception e) {
            log.error("Failed to create notification: {}", e.getMessage(), e);
            throw new RuntimeException("创建秒杀通知失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelNotification(Long userId, Long goodsId) {
        LambdaQueryWrapper<SeckillNotification> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SeckillNotification::getUserId, userId)
                .eq(SeckillNotification::getGoodsId, goodsId);
        try {
            notificationMapper.delete(wrapper);
            log.info("Successfully cancelled notification for userId: {}, goodsId: {}", userId, goodsId);
        } catch (Exception e) {
            log.error("Failed to cancel notification: {}", e.getMessage(), e);
            throw new RuntimeException("取消秒杀通知失败");
        }
    }

    @Scheduled(fixedRate = LOOP_CHECK_INTERVAL)
    @Transactional(rollbackFor = Exception.class)
    public void checkAndNotify() {
        log.info("开始检查待发送的秒杀通知");
        try {
            LambdaQueryWrapper<SeckillNotification> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SeckillNotification::getIsNotified, false);
            List<SeckillNotification> unNotifyList = notificationMapper.selectList(queryWrapper);

            if (unNotifyList.isEmpty()) {
                log.info("没有待发送的秒杀通知");
                return;
            }

            for (SeckillNotification notification : unNotifyList) {
                try {
                    notify(notification.getUserId(), notification.getGoodsId());
                } catch (Exception e) {
                    log.error("处理通知失败, notificationId: {}, error: {}", notification.getId(), e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            log.error("检查秒杀通知任务执行失败: {}", e.getMessage(), e);
        }
    }

    private void notify(Long userId, Long goodsId) {

    }
}

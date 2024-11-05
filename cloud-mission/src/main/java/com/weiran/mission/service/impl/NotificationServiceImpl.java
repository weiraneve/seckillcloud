package com.weiran.mission.service.impl;

import com.weiran.mission.mapper.SeckillNotificationMapper;
import com.weiran.mission.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final int LOOP_CHECK_INTERVAL = 60000;

    private final SeckillNotificationMapper seckillNotificationMapper;

    @Override
    public void createNotification(Long userId, Long goodsId) {

    }

    @Override
    public void cancelNotification(Long userId, Long goodsId) {

    }

    @Override
    public void scanAndSendNotification() {

    }

    @Override
    @Scheduled(fixedRate = LOOP_CHECK_INTERVAL)
    public void checkAndNotify() {

    }
}

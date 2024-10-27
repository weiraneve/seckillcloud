package com.weiran.mission.service.impl;

import com.weiran.mission.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {


    @Override
    public void registerNotification(Long userId, Long productId, String notifyType, String notifyTarget) {

    }

    @Override
    public void scanAndSendNotification() {

    }
}

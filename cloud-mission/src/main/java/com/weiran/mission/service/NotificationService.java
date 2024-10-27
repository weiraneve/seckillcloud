package com.weiran.mission.service;

public interface NotificationService {

    void registerNotification(Long userId, Long productId, String notifyType, String notifyTarget);

    void scanAndSendNotification();
}

package com.weiran.mission.service;

public interface NotificationService {

    void createNotification(Long userId, Long goodsId);

    void cancelNotification(Long userId, Long goodsId);
}

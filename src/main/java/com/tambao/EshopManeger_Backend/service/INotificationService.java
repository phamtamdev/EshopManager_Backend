package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.NotificationDto;

import java.util.List;

public interface INotificationService {
    NotificationDto addNotification(NotificationDto notificationDto);
    NotificationDto updateNotification(Integer notificationId);
    Boolean areAllNotificationsSeen(Integer userId);
    List<NotificationDto> getAllNotificationByUserId(Integer userId);
}

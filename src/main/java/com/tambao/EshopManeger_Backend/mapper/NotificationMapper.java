package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.NotificationDto;
import com.tambao.EshopManeger_Backend.entity.Notification;
import com.tambao.EshopManeger_Backend.entity.Users;

public class NotificationMapper {
    public static NotificationDto mapToNotificationDto(Notification notification) {
        return new NotificationDto(
                notification.getId(),
                notification.getMessage(),
                notification.getLink(),
                notification.getLogo(),
                notification.isSeen(),
                notification.getCreateAt(),
                notification.getUser().getId()
        );
    }

    public static Notification mapToNotification(NotificationDto notificationDto) {
        Notification notification = new Notification();
        notification.setId(notificationDto.getId());
        notification.setMessage(notificationDto.getMessage());
        notification.setLogo(notificationDto.getLogo());
        notification.setLink(notificationDto.getLink());
        notification.setSeen(notificationDto.isSeen());
        notification.setCreateAt(notificationDto.getCreateAt());
        if (notificationDto.getUserId() != null) {
            Users user = new Users();
            user.setId(notificationDto.getUserId());
            notification.setUser(user);
        }
        return notification;
    }
}

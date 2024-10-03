package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.NotificationDto;
import com.tambao.EshopManeger_Backend.entity.Notification;
import com.tambao.EshopManeger_Backend.entity.Users;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.NotificationMapper;
import com.tambao.EshopManeger_Backend.repository.NotificationRepository;
import com.tambao.EshopManeger_Backend.repository.UserRepository;
import com.tambao.EshopManeger_Backend.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public NotificationDto addNotification(NotificationDto notificationDto) {
        Notification notification = NotificationMapper.mapToNotification(notificationDto);
        notification.setCreateAt(new Timestamp(System.currentTimeMillis()));
        notificationRepository.save(notification);
        return NotificationMapper.mapToNotificationDto(notification);
    }

    @Override
    public NotificationDto updateNotification(Integer notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        notification.setSeen(true);
        notificationRepository.save(notification);
        return NotificationMapper.mapToNotificationDto(notification);
    }

    @Override
    public Boolean areAllNotificationsSeen(Integer userId) {
        return notificationRepository.areAllNotificationsSeenByUser(userId);
    }

    @Override
    public List<NotificationDto> getAllNotificationByUserId(Integer userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        List<Notification> notifications = notificationRepository.findByUserId(user.getId());
        return notifications.stream()
                .sorted((n1, n2) -> n2.getCreateAt().compareTo(n1.getCreateAt()))
                .map(NotificationMapper::mapToNotificationDto)
                .collect(Collectors.toList());
    }
}

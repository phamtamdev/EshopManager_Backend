package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.NotificationDto;
import com.tambao.EshopManeger_Backend.service.Impl.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getNotificationByUserId(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(notificationService.getAllNotificationByUserId(userId));
    }

    @GetMapping("/seen-status")
    public boolean checkNotificationsSeenStatus(@RequestParam Integer userId) {
        return notificationService.areAllNotificationsSeen(userId);
    }

    @PostMapping
    public ResponseEntity<?> addNotification(@RequestBody NotificationDto dto) {
        dto.setId(0);
        dto = notificationService.addNotification(dto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateNotification(@RequestParam Integer notificationId) {
        return ResponseEntity.ok(notificationService.updateNotification(notificationId));
    }
}

package com.tambao.EshopManeger_Backend.repository;

import com.tambao.EshopManeger_Backend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserId(Integer userId);
    @Query("SELECT CASE WHEN COUNT(n) > 0 THEN FALSE ELSE TRUE END FROM Notification n WHERE n.user.id = :userId AND n.seen = FALSE")
    Boolean areAllNotificationsSeenByUser(@Param("userId") Integer userId);
}

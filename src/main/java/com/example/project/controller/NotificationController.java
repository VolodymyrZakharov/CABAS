package com.example.project.controller;

import com.example.project.entity.severity.SeverityStatus;
import com.example.project.serviseNotification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/api/notifications/notify")
    public void notifyArea(@RequestParam Long area_code, @RequestParam SeverityStatus severity) {
        notificationService.notify(area_code, severity);
    }

}

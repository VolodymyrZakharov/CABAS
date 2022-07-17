package com.example.project.serviseNotification;

import com.example.project.entity.severity.SeverityStatus;

public interface NotificationService {

    void notify(Long areaCode, SeverityStatus severity);
}

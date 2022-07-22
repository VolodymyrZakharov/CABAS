package com.example.project.serviceNotification;

import com.example.project.entity.severity.SeverityStatus;

public interface NotificationService {

    void notify(String areaCode, SeverityStatus severity);
}

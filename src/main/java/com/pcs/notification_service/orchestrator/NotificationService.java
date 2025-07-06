package com.pcs.notification_service.orchestrator;

import com.pcs.notification_service.persistence.dtos.NotificationEvent;

public interface NotificationService {
    void processEvent(NotificationEvent event);
}

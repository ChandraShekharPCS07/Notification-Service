package com.pcs.notification_service.persistence.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    private String eventId;
    private String userId;
    private String eventType; // e.g. "APPOINTMENT_REMINDER"
    private Map<String, String> payload;
    private Instant createdAt;
}

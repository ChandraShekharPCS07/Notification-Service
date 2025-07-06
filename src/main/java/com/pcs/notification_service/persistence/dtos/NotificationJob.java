package com.pcs.notification_service.persistence.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationJob {
    private String userId;
    private String preferredChannel; // SMS
    private String renderedMessage;
    private Map<String, String> metadata; // eventId, etc.
}

package com.pcs.notification_service.templates;

import java.util.Map;

public interface TemplateRenderer {
    String render(String eventType, Map<String, String> payload);
}

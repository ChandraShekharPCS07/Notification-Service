package com.pcs.notification_service.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.notification_service.orchestrator.NotificationService;
import com.pcs.notification_service.persistence.dtos.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaNotificationConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${app.kafka.topic.notification-events}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, NotificationEvent> record) {
        try {
            NotificationEvent event = record.value();
            log.info("Received NotificationEvent: {}", event);
            notificationService.processEvent(event);
        } catch (Exception ex) {
            log.error("Error processing event. Sending to DLQ.", ex);
            throw ex;
        }
    }
}

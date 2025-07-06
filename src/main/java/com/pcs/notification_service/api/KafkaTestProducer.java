package com.pcs.notification_service.api;


import com.pcs.notification_service.persistence.dtos.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test-produce")
public class KafkaTestProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topic.notification-events}")
    private String topic;

    @PostMapping
    public ResponseEntity<?> sendTest(@RequestBody NotificationEvent event) {
        if (event == null || event.getEventType() == null) {
            return ResponseEntity.badRequest().body("Invalid event data");
        }
        kafkaTemplate.send(topic, event);
        return ResponseEntity.ok("Sent");
    }
}


package com.pcs.notification_service.orchestrator;

import com.pcs.notification_service.channel.sms.TwilioSmsSender;
import com.pcs.notification_service.persistence.DeliveryRecordRepository;
import com.pcs.notification_service.persistence.UserPreferenceRepository;
import com.pcs.notification_service.persistence.dtos.NotificationEvent;
import com.pcs.notification_service.persistence.entities.DeliveryRecord;
import com.pcs.notification_service.persistence.entities.UserPreference;
import com.pcs.notification_service.templates.TemplateRenderer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserPreferenceRepository userPreferenceRepository;
    private final DeliveryRecordRepository deliveryRecordRepository;
    private final TemplateRenderer templateRenderer;
    private final TwilioSmsSender smsSender;

    @Transactional
    @Override
    public void processEvent(NotificationEvent event) {
        log.info("Processing NotificationEvent for user: {}", event.getUserId());

        Optional<UserPreference> preferenceOpt = userPreferenceRepository.findById(event.getUserId());
        if (preferenceOpt.isEmpty() || !preferenceOpt.get().isSmsEnabled()) {
            log.warn("User {} has not consented to SMS. Skipping.", event.getUserId());
            saveDeliveryRecord(event, "SMS", "FAILED", "User not opted in", null);
            return;
        }

        String message = templateRenderer.render(event.getEventType(), event.getPayload());

        try {
            String providerId = smsSender.sendSms(event.getUserId(), message);
            saveDeliveryRecord(event, "SMS", "SENT", null, providerId);
        } catch (Exception e) {
            log.error("Failed to send SMS for user {}", event.getUserId(), e);
            saveDeliveryRecord(event, "SMS", "FAILED", e.getMessage(), null);
            throw e;
        }
    }

    private void saveDeliveryRecord(NotificationEvent event, String channel, String status,
                                    String failureReason, String providerMessageId) {
        DeliveryRecord record = new DeliveryRecord();
        record.setUserId(event.getUserId());
        record.setChannel(channel);
        record.setStatus(status);
        record.setFailureReason(failureReason);
        record.setProviderMessageId(providerMessageId);
        record.setCreatedAt(LocalDateTime.now());
        deliveryRecordRepository.save(record);
    }
}

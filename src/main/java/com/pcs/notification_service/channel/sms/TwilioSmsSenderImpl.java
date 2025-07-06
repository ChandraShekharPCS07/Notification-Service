package com.pcs.notification_service.channel.sms;

import com.pcs.notification_service.config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TwilioSmsSenderImpl implements TwilioSmsSender {

    private final TwilioConfig twilioConfig;

    @PostConstruct
    public void init() {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    @Override
    public String sendSms(String userId, String message) {
        try {
            Message twilioMessage = Message.creator(
                    new PhoneNumber(userId),       // To
                    new PhoneNumber(twilioConfig.getFromNumber()), // From
                    message
            ).create();

            log.info("Twilio SMS sent to {} with SID={}", userId, twilioMessage.getSid());
            return twilioMessage.getSid();
        } catch (ApiException ex) {
            log.error("Twilio API error: {}", ex.getMessage(), ex);
            throw new SmsSendingException("Twilio API error: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            log.error("Unexpected SMS send error", ex);
            throw new SmsSendingException("Unexpected error sending SMS", ex);
        }
    }
}

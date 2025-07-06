package com.pcs.notification_service.channel.sms;

public interface TwilioSmsSender {
    String sendSms(String userId, String message) throws SmsSendingException;
}

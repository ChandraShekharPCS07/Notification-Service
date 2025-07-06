package com.pcs.notification_service.channel.sms;

public class SmsSendingException extends RuntimeException {
    public SmsSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}
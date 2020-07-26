package ru.spb.avetall.indexerservice.service;

import org.springframework.messaging.Message;

public interface MessageReceiver {
    void receive(Message<?> message);
}

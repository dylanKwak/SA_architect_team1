package com.sds.swa1.sdk.consumer;

import com.sds.swa1.sdk.service.SimpleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SimpleConsumer {
    final SimpleService kafkaService;

    public void listen(@Payload final String payload) {
        log.debug("Received payload {}",  payload);
        kafkaService.processEvent(payload);
    }
}

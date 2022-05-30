package com.sds.swa1.sdk.consumer;

import com.sds.swa1.sdk.service.SimpleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SimpleConsumer {
    final SimpleService kafkaService;

    @KafkaListener(topics = "simple")
    public void listen(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                       @Payload final String payload) {
        log.debug("Received key{} payload {}",key,  payload);
        try {
            kafkaService.processEvent(key, payload);
        } catch (DuplicateKeyException e) {
            log.debug("duplicate message received : {}", payload);
        }
    }
}

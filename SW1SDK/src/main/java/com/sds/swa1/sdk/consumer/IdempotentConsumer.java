package com.sds.swa1.sdk.consumer;

import com.sds.swa1.sdk.service.IdempotentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class IdempotentConsumer {
    final IdempotentService kafkaService;

    @KafkaListener(topics = "idempotent")
    public void listen(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                       @Payload final String payload,
                       Acknowledgment ack) {
        log.debug("Received eventId {} payload {}", key, payload);
        kafkaService.processEvent(key, payload);
        ack.acknowledge();
    }
}

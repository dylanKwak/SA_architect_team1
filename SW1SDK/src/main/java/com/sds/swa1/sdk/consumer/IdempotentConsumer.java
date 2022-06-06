package com.sds.swa1.sdk.consumer;

import com.sds.swa1.sdk.common.Constants;
import com.sds.swa1.sdk.service.IdempotentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
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

    @KafkaListener(topics = Constants.TOPIC_IDEMPOTENT)
    public void listen(@Header(Constants.EVENT_ID_HEADER) String eventId,
                       @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                       @Payload final String payload,
                       Acknowledgment ack) {
        log.debug("Received eventId {} key {} payload {}", eventId, key, payload);
        try {
            kafkaService.processEvent(eventId, key, payload);
        } catch (DuplicateKeyException e) {
            log.debug("Duplicate message received key {} payload {}", key, payload);
        }
        ack.acknowledge();
        log.debug("complete acknowledge");
    }
}

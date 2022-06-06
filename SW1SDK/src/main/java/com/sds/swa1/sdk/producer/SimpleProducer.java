package com.sds.swa1.sdk.producer;

import com.sds.swa1.sdk.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SimpleProducer {
    private static final String TOPIC = "exam";
    private final KafkaTemplate kafkaTemplate;

    @Autowired
    public SimpleProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String eventId, String key, String payload) throws Exception{
        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader(Constants.EVENT_ID_HEADER, eventId != null ? eventId.getBytes() : null));

        final ProducerRecord<Long, String> record = new ProducerRecord(topic, null, key, payload, headers);
        record.headers().add(new RecordHeader(Constants.EVENT_ID_HEADER, eventId.getBytes(StandardCharsets.UTF_8)));

        log.debug(String.format("Produce message : %s", payload));
        final SendResult result = (SendResult)kafkaTemplate.send(record).get();
        final RecordMetadata metadata = result.getRecordMetadata();
        log.debug(String.format("SimpleProducer Sent record(key=%s value=%s) meta(topic=%s, partition=%d, offset=%d)",
                record.key(), record.value(), metadata.topic(), metadata.partition(), metadata.offset()));
    }
}

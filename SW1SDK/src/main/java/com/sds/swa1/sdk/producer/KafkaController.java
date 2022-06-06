package com.sds.swa1.sdk.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {
    private final SimpleProducer producer;

    @Autowired
    KafkaController(SimpleProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String sendMessage(@RequestParam("topic") String topic,
            @RequestParam("eventId") String eventId,
            @RequestParam("payload") String payload) throws Exception {
        String key = "key1";
        log.debug("sendMessage topic {}, eventId {}, payload {}", topic, eventId, payload);
        this.producer.sendMessage(topic, eventId, key, payload);

        return "success";
    }
}

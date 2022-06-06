package com.sds.swa1.sdk.service;

import com.sds.swa1.sdk.IdempotentServiceListenerImpl;
import com.sds.swa1.sdk.listener.KafkaconsumerServiceListener;
import com.sds.swa1.sdk.repository.EventRepository;
import com.sds.swa1.sdk.repository.ProcessedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class IdempotentService {
    private KafkaconsumerServiceListener listener;

    public void setListener(KafkaconsumerServiceListener listener) {
        this.listener = listener;
    }

    IdempotentService() {
        listener = new IdempotentServiceListenerImpl();
    }

    @Autowired
    EventRepository eventRepository;

    @Transactional
    public void processEvent(String eventId, String key, String payload) {
        deduplicate(eventId);
        log.debug("Idempotent processEvent eventId{} key{}",eventId, key);
        if(listener != null) {
            listener.onProcess(payload);
        }
    }

    private void deduplicate(String eventId) throws DuplicateKeyException{
        try {
            eventRepository.saveAndFlush(new ProcessedEvent(eventId));
            log.debug("payload persisted with id {} ", eventId);
        }catch (DataIntegrityViolationException e) {
            log.warn("key already processed: {}", eventId);
            throw new DuplicateKeyException(eventId);
        }

    }

}

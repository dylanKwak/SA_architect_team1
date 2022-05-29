package com.sds.swa1.sdk.service;

import com.sds.swa1.sdk.listener.KafkaconsumerServiceListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class IdempotentService {
    private KafkaconsumerServiceListener listener;

    @Transactional
    public void processEvent(String key, String payload) {
        deduplicate(key);
        log.debug("processIdempotent {}", key);
        if(listener != null) {
            listener.onProcess(payload);
        }
    }

    private void deduplicate(String key) {
        try {
            //TODO: process Repository saveAndFlush
            log.debug("payload persisted with id {} ", key);
        }catch (DataIntegrityViolationException e) {
            log.warn("key already processed: {}", key);
            throw new DuplicateKeyException(key);
        }

    }

}

package com.sds.swa1.sdk.service;

import com.sds.swa1.sdk.IdempotentServiceListenerImpl;
import com.sds.swa1.sdk.SimpleServiceListenerImpl;
import com.sds.swa1.sdk.listener.KafkaconsumerServiceListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SimpleService {
    private KafkaconsumerServiceListener listener;

    SimpleService () {
        listener = new SimpleServiceListenerImpl();
    }

    public void setListener(KafkaconsumerServiceListener listener) {
        this.listener = listener;
    }

    public void processEvent(String key, String payload) {
        log.debug("Simple processEvent key {}", key);
        if(listener != null) {
            listener.onProcess(payload);
        }
    }
}

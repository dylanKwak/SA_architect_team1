package com.sds.swa1.sdk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SimpleService {
    public void processEvent(String payload) {
        log.debug("processEvent {}", payload);
    }
}

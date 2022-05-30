package com.sds.swa1.sdk;

import com.sds.swa1.sdk.listener.KafkaconsumerServiceListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleServiceListenerImpl implements KafkaconsumerServiceListener {
    @Override
    public void onProcess(String payload) {
      log.debug("SimpleService onProcess {}", payload);
    }
}

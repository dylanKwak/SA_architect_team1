package com.sds.swa1.sdk.listener;

public interface KafkaconsumerServiceListener {
    void onProcess(String payload);
}

package com.sds.swa1.sdk;

import com.sds.swa1.sdk.listener.KafkaconsumerServiceListener;
import com.sds.swa1.sdk.service.IdempotentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class SWA1SDKApplication {

    @Autowired
    IdempotentService service;

    public static void main(String[] args)
    {
        SpringApplication.run(SWA1SDKApplication.class, args);
    }
}

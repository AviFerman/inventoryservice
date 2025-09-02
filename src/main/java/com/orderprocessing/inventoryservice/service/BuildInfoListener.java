package com.orderprocessing.inventoryservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class BuildInfoListener {

    @Autowired
    private BuildProperties buildProperties;

    @EventListener(ApplicationStartedEvent.class)
    public void logBuildInfo() {
        log.info("=============================================================");
        log.info("Application: {} version: {}",
                buildProperties.getName(),
                buildProperties.getVersion());
        log.info("Build time: {}", buildProperties.getTime());
        log.info("=============================================================");
    }
}

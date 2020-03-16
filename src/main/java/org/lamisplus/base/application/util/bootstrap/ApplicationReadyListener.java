package org.lamisplus.base.application.util.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationReadyListener {

    @EventListener
    @Async
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LOG.info( "Bootstrapping modules: {}");
    }
}

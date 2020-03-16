package org.lamisplus.base.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StartupLogger {

    private final ApplicationProperties applicationProperties;

    @Bean
    public Void dummyBean() {
        LOG.info( "Your application key is: {}", applicationProperties.getApplicationKey());
        return null;
    }
}

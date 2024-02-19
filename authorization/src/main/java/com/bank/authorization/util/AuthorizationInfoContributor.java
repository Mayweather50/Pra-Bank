package com.bank.authorization.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class AuthorizationInfoContributor implements InfoContributor {

    private final Environment environment;

    @Autowired
    private AuthorizationInfoContributor(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("Название", environment.getProperty("spring.application.name"))
                .withDetail("ArtifactId", environment.getProperty("spring.application.artifactId"))
                .withDetail("Время запуска", LocalDateTime.now().format(DateTimeFormatter
                        .ofPattern("dd-MM-yyyy HH:mm:ss")))
                .withDetail("Версия приложения", environment.getProperty("spring.application.version"))
                .withDetail("Context Path", environment.getProperty("server.servlet.context-path"));
    }
}

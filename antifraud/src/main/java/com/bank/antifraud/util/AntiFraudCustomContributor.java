package com.bank.antifraud.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class AntiFraudCustomContributor implements InfoContributor {
    private final LocalDateTime startTime = LocalDateTime.now();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String formattedTime = startTime.format(dateTimeFormatter);

    @Value("${project.name}")
    private String applicationName;
    @Value("${project.artifactId}")
    private String artifactId;
    @Value("${project.version}")
    private String projectVersion;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("Название", applicationName)
                .withDetail("ArtifactId", artifactId)
                .withDetail("Версия приложения", projectVersion)
                .withDetail("Время запуска", formattedTime)
                .withDetail("Context Path", contextPath);
    }
}


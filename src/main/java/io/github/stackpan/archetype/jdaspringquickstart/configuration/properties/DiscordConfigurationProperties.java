package io.github.stackpan.archetype.jdaspringquickstart.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "discord")
public record DiscordConfigurationProperties(String botToken) {
}

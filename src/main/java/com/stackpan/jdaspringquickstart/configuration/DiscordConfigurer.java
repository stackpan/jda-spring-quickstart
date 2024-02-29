package com.stackpan.jdaspringquickstart.configuration;

import com.freya02.botcommands.api.CommandsBuilder;
import com.stackpan.jdaspringquickstart.configuration.properties.DiscordConfigurationProperties;
import com.stackpan.jdaspringquickstart.discord.ExtensionRegister;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DiscordConfigurer {

    private final DiscordConfigurationProperties discordConfigurationProperties;

    private final ExtensionRegister extensionRegister;

    @Bean
    public JDA jda() throws InterruptedException {
        JDA jda =  JDABuilder.createDefault(discordConfigurationProperties.botToken()).build();

        jda.awaitReady();
        CommandsBuilder.newBuilder()
                .extensionsBuilder(extensionRegister)
                .build(jda, "com.stackpan.jdaspringquickstart.command");

        return jda;
    }

}

package io.github.stackpan.archetype.jdaspringquickstart.configuration;

import com.freya02.botcommands.api.CommandsBuilder;
import io.github.stackpan.archetype.jdaspringquickstart.configuration.properties.DiscordConfigurationProperties;
import io.github.stackpan.archetype.jdaspringquickstart.discord.ExtensionRegister;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DiscordConfigurer {

    private final DiscordConfigurationProperties discordConfigurationProperties;

    private final ExtensionRegister extensionRegister;

    @Bean
    public JDA jda() throws InterruptedException {
        JDA jda =  JDABuilder
                .createDefault(discordConfigurationProperties.botToken())
                .setActivity(Activity.listening("User command"))
                .build();

        jda.awaitReady();
        CommandsBuilder.newBuilder()
                .extensionsBuilder(extensionRegister) // Don't remove this! This is necessary for registering your beans
                .build(jda, "io.github.stackpan.archetype.jdaspringquickstart.command");

        return jda;
    }

}

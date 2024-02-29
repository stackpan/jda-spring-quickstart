package io.github.stackpan.archetype.jdaspringquickstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("io.github.stackpan.archetype.jdaspringquickstart.configuration.properties")
public class JdaSpringQuickstartApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdaSpringQuickstartApplication.class, args);
    }

}

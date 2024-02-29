package com.stackpan.jdaspringquickstart.discord;

import com.freya02.botcommands.api.builder.ExtensionsBuilder;
import com.stackpan.jdaspringquickstart.service.ExampleService;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ExtensionRegister implements Consumer<ExtensionsBuilder>, ApplicationContextAware {

    @Setter
    private ApplicationContext applicationContext;

    /**
     * Register your beans here, so it can be injected into your commands classes
     *
     * @param extensionsBuilder the input argument
     */
    @Override
    public void accept(ExtensionsBuilder extensionsBuilder) {
        extensionsBuilder
                .registerConstructorParameter(ExampleService.class, t -> applicationContext.getBean(ExampleService.class));
    }
}

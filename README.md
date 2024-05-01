# JDA Spring Quickstart Maven Archetype

Starter project for building JDA Discord bot app with Spring

This archetype is including these libraries:

- **Spring Framework & Spring Boot**

  These things can makes your Java programming quicker and easy. [More info](https://spring.io)

- **JDA (Java Discord API)**

  Popular Java wrapper for Discord to build discord bot app. [More info](https://github.com/discord-jda/JDA)

- **BotCommands**

  A JDA command framework (& much more) which helps to develop your bots faster. [More info](https://github.com/freya022/BotCommands)

- **Project Lombok**

  Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java. [More info](https://projectlombok.org)

## Installation

Use Maven Archetype Generator and find archetype with coordinate: `io.github.stackpan.archetype:jda-spring-quickstart-archetype`

Described as this command:

```shell
mvn archetype:generate \
  -DarchetypeGroupId=io.github.stackpan.archetype \
  -DarchetypeArtifactId=jda-spring-quickstart-archetype \
  -DarchetypeVersion={version} \
  -DgroupId={your.project.group.id} \
  -DartifactId={your-project-artifact-id} \
  -Dversion={your-project-version}
```

## Usage

After your project is successfully generated. You will find the following folder structure:

```
your-project
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    └── main
        ├── java
        │   └── {package}
        │       ├── command  // Commands definition and handler
        │       ├── configuration // Application configurations
        │       ├── discord  // Additional discord configurations
        │       ├── service  // Application logic
        │       └── JdaSpringQuickstartApplication.java  // Main class
        └── resources
            └── application.properties
```
Example of its uses are provided in the generated project.

First thing you must to do is to configure your Discord bot token. Place your token inside `src/main/resource/application.properties` file:

```properties
discord.bot-token=<YOUR_DISCORD_BOT_TOKEN>
```

To run the application, use this command:
```shell
./mvnw spring-boot:run
```

### Configuration

#### Bot Token

```properties
# file: src/main/resource/application.properties

discord.bot-token=<YOUR_DISCORD_BOT_TOKEN>
```

#### JDA Instance

We have defined `JDA` instance bean configuration inside `{package}.configuration.DiscordConfigurer` class. You can define your own configuration inside `DiscordConfigurer.jda()` bean method configuration:

```java
// File: src/main/java/{package}/configuration/DiscordConfigurer.java

@Bean
public JDA jda() throws InterruptedException {
  // ...
}
```

see [JDABuilder Javadocs](https://docs.jda.wiki/net/dv8tion/jda/api/JDABuilder.html)

#### Bean Registration

This archetype uses **freya022/BotCommands** framework to easily manage your bot commands, but this has an issue when injecting your bean from Spring IoC Container into command classes. For example:

```java
// File: src/main/java/{package}/command/ExampleCommand.java

@CommandMarker
@RequiredArgsConstructor
public class ExampleCommand extends ApplicationCommand {

    private final ExampleService exampleService; // Requires ExampleService as a dependency

    @JDASlashCommand(name = "ping", scope = CommandScope.GLOBAL)
    public void ping(GlobalSlashEvent event) {
        var message = exampleService.pong();
        event.reply(message).queue();
    }

}
```

When you declare your bean inside your `ApplicationCommand` classes, an error will occur when starting the app. This is happened because `ApplicationCommand` classes dependencies cannot resolve your bean dependencies.

To address this issue, you need to register your bean as command extension from Spring `ApplicationContext` inside `{package}.discord.ExtensionRegister` class:

```java
// File: src/main/java/{package}/discord/ExtensionRegister.java

@Component
public class ExtensionRegister implements Consumer<ExtensionsBuilder>, ApplicationContextAware {

    @Setter
    private ApplicationContext applicationContext;
    
    @Override
    public void accept(ExtensionsBuilder extensionsBuilder) {
        extensionsBuilder
                // Registering ExampleService from Spring ApplicationContext, so it can be resolved by ApplicationCommand classes
                .registerConstructorParameter(ExampleService.class, t -> applicationContext.getBean(ExampleService.class));
    }
}
```

see [BotCommands/writing-extensions/Constructor-injection](https://freya022.github.io/BotCommands/2.X/writing-extensions/Constructor-injection/)

## Resources

- [Spring.io](https://spring.io)
- [JDA wiki](https://jda.wiki)
- [freya022/BotCommands wiki](https://freya022.github.io/BotCommands/2.X/)

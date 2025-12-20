package travel.letstrip.config;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.Logger;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import travel.letstrip.clients.TelegramClient;
import travel.letstrip.telegram.TelegramLogbackAppender;
import travel.letstrip.telegram.TelegramProperties;

/**
 * Initializes and registers a custom Logback appender
 * that sends log messages to Telegram.
 *
 * <p>This component is automatically discovered by Spring
 * and executed during application startup.</p>
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Create and configure {@link TelegramLogbackAppender}</li>
 *   <li>Attach the appender to the root Logback logger</li>
 *   <li>Provide a {@link TelegramClient} bean</li>
 * </ul>
 *
 * <p>Once initialized, application logs can be forwarded
 * to configured Telegram groups or topics.</p>
 */
@Component
@RequiredArgsConstructor
public class TelegramAppenderInitializer {

    private final TelegramClient telegramClient;
    private final TelegramProperties properties;

    /**
     * Initializes the Telegram Logback appender after
     * Spring dependency injection is complete.
     *
     * <p>The appender is attached to the root logger,
     * allowing it to capture all application log events.</p>
     */
    @PostConstruct
    public void init() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        TelegramLogbackAppender appender = new TelegramLogbackAppender();

        appender.setContext(context);
        appender.setTelegramClient(telegramClient);
        appender.setProperties(properties);
        appender.start();

        Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(appender);
    }

    /**
     * Creates a {@link TelegramClient} bean used by the
     * Telegram Logback appender.
     *
     * @return configured {@link TelegramClient} instance
     */
    @Bean
    public TelegramClient telegramClient() {
        return new TelegramClient(properties);
    }
}

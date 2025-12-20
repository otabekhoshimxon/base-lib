package travel.letstrip.telegram;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.AppenderBase;
import lombok.Setter;
import travel.letstrip.clients.TelegramClient;

import java.time.Instant;
import java.util.Set;

/**
 * Logback appender that sends log events to a Telegram group.
 *
 * <p>This appender filters log events by level and forwards
 * formatted messages to Telegram using {@link TelegramClient}.</p>
 *
 * <p>Features:</p>
 * <ul>
 *   <li>Enable/disable logging via configuration</li>
 *   <li>Filter log events by allowed log levels</li>
 *   <li>Optional stack trace forwarding</li>
 *   <li>Configurable stack trace line limit</li>
 *   <li>HTML-safe message formatting</li>
 * </ul>
 *
 * <p>The appender is designed to be initialized and configured
 * by Spring during application startup.</p>
 */
public class TelegramLogbackAppender extends AppenderBase<ILoggingEvent> {

    /**
     * Telegram client used to send messages.
     */
    @Setter
    private TelegramClient telegramClient;

    /**
     * Telegram-related configuration properties.
     */
    private TelegramProperties properties;

    /**
     * Set of allowed log levels (e.g. ERROR, WARN).
     */
    private Set<String> allowedLevels;

    /* ==== Configured via Spring ==== */

    /**
     * Sets Telegram configuration properties and initializes
     * the allowed log levels.
     *
     * @param properties Telegram logging configuration
     */
    public void setProperties(TelegramProperties properties) {
        this.properties = properties;
        this.allowedLevels = Set.of(properties.getLoggingLevels());
    }

    /**
     * Processes a Logback logging event.
     *
     * <p>The event is sent to Telegram only if:</p>
     * <ul>
     *   <li>The appender is started</li>
     *   <li>Telegram logging is enabled</li>
     *   <li>The event level is allowed</li>
     * </ul>
     *
     * @param event logging event
     */
    @Override
    protected void append(ILoggingEvent event) {
        if (!isStarted() || telegramClient == null || properties == null) {
            return;
        }

        if (!properties.isLoggingEnabled()) {
            return;
        }

        if (!allowedLevels.contains(event.getLevel().levelStr)) {
            return;
        }

        String message = buildMessage(event);
        telegramClient.sendToErrorGroup(message);
    }

    /* ================= MESSAGE BUILDERS ================= */

    /**
     * Builds a formatted Telegram message from a logging event.
     *
     * <p>The message includes:</p>
     * <ul>
     *   <li>Log level</li>
     *   <li>Logger name</li>
     *   <li>Timestamp</li>
     *   <li>Formatted log message</li>
     *   <li>Optional stack trace</li>
     * </ul>
     *
     * @param event logging event
     * @return formatted message string
     */
    private String buildMessage(ILoggingEvent event) {
        StringBuilder sb = new StringBuilder();

        sb.append("🚨 <b>").append(event.getLevel()).append("</b>\n");
        sb.append("📦 <b>").append(event.getLoggerName()).append("</b>\n");
        sb.append("🕒 ").append(Instant.ofEpochMilli(event.getTimeStamp())).append("\n\n");

        sb.append("<pre>").append(escape(event.getFormattedMessage())).append("</pre>");

        if (properties.getLogging().isIncludeStackTrace()
                && event.getThrowableProxy() != null) {
            sb.append("\n\n").append(buildStackTrace(event.getThrowableProxy()));
        }

        return sb.toString();
    }

    /**
     * Builds a formatted stack trace block for Telegram.
     *
     * <p>The number of stack trace lines is limited by configuration.</p>
     *
     * @param throwable throwable proxy
     * @return formatted stack trace
     */
    private String buildStackTrace(IThrowableProxy throwable) {
        StringBuilder sb = new StringBuilder();
        sb.append("<b>Stacktrace:</b>\n<pre>");

        StackTraceElementProxy[] elements = throwable.getStackTraceElementProxyArray();
        int limit = Math.min(
                elements.length,
                properties.getLogging().getMaxStackTraceLines()
        );

        for (int i = 0; i < limit; i++) {
            sb.append(elements[i].getStackTraceElement()).append("\n");
        }

        sb.append("</pre>");
        return sb.toString();
    }

    /**
     * Escapes special HTML characters to prevent
     * Telegram message formatting issues.
     *
     * @param text raw text
     * @return escaped text
     */
    private String escape(String text) {
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}

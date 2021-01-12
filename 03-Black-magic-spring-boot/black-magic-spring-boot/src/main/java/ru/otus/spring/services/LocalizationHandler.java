package ru.otus.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.configuration.ApplicationProperties;

import java.util.Locale;

@Component
@AllArgsConstructor
public class LocalizationHandler {

    private final MessageSource messageSource;
    private final ApplicationProperties applicationProperties;

    public String getIntroduceMessage() {
        return getMessage("introduce.user");
    }

    public String getHelloMessage(String name) {
        return getMessage("hello.user", name);
    }

    public String getHintMessage() {
        return getMessage("hint.user");
    }

    public String getAnswerMessage() {
        return getMessage("user.answer");
    }

    public String getResultMessage(String ...args) {
        return getMessage("user.result", args);
    }

    public String getSuccessMessage() {
        return getMessage("user.success");
    }

    public String getFailMessage() {
        return getMessage("user.fail");
    }

    public Locale getLocale() {
        return applicationProperties.getLocale();
    }

    private String getMessage(String code, String ...args) {
        return messageSource.getMessage(code, args, getLocale());
    }
}

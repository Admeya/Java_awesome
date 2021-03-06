package ru.otus.spring.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import ru.otus.spring.services.MessagePublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест команд shell ")
@SpringBootTest
class ApplicationEventsCommandsTest {

    @MockBean
    private MessagePublisher messagePublisher;

    @Autowired
    private Shell shell;

    private static final String GREETING_PATTERN = "Привет, %s! Приступим к тестированию.\n" +
            "Если ответ предполагает несколько ответов, разделите их запятой. \n" +
            " Например: a,c";
    private static final String DEFAULT_LOGIN = "DearUser";
    private static final String CUSTOM_LOGIN = "Irina";
    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_LOGIN_SHORT = "l";

    @DisplayName(" должен возвращать приветствие для всех форм команды логина")
    @Test
    void login() {
        String res = (String) shell.evaluate(() -> COMMAND_LOGIN);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));

        res = (String) shell.evaluate(() -> COMMAND_LOGIN_SHORT);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));

//        res = (String) shell.evaluate(() -> String.format(COMMAND_LOGIN_PATTERN, COMMAND_LOGIN_SHORT, CUSTOM_LOGIN));
//        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, CUSTOM_LOGIN));
    }

    @Test
    void startTesting() {
    }

    @Test
    void goodbye() {
    }
}
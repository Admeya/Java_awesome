package ru.otus.spring.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.spring.services.TestingService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Тест команд shell ")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class ApplicationEventsCommandsTest {

    @MockBean
    private TestingService testingService;

    @Autowired
    private Shell shell;

    private static final String GREETING_PATTERN = "Привет, %s! Приступим к тестированию.\n" +
            "Если ответ предполагает несколько ответов, разделите их запятой. \n" +
            " Например: a,c\n";
    private static final String DEFAULT_LOGIN = "DearUser";
    private static final String CUSTOM_LOGIN = "Irina";
    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_LOGIN_SHORT = "l";
    private static final String COMMAND_LOGIN_PATTERN = "%s %s";

    private static final String COMMAND_TEST = "test";
    private static final String COMMAND_TEST_SHORT = "t";
    private static final String RESULT_TESTING = "Тестирование завершено";

    private static final String COMMAND_RESULT = "result";
    private static final String COMMAND_RESULT_SHORT = "res";
    private static final String RESULT_ANSWER = "Возвращайтесь снова!";


    @DisplayName(" должен возвращать приветствие для всех форм команды логина")
    @Test
    void login() {
        String res = (String) shell.evaluate(() -> COMMAND_LOGIN);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));

        res = (String) shell.evaluate(() -> COMMAND_LOGIN_SHORT);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));

        res = (String) shell.evaluate(() -> String.format(COMMAND_LOGIN_PATTERN, COMMAND_LOGIN_SHORT, CUSTOM_LOGIN));
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, CUSTOM_LOGIN));
    }

    @Test
    void startTesting() {
        given(testingService.launchTesting()).willReturn(5);

        String res = (String) shell.evaluate(() -> COMMAND_TEST);
        assertThat(res).isEqualTo(RESULT_TESTING);

        res = (String) shell.evaluate(() -> COMMAND_TEST_SHORT);
        assertThat(res).isEqualTo(RESULT_TESTING);
    }

    @Test
    void goodbye() {
        String res = (String) shell.evaluate(() -> COMMAND_RESULT);
        assertThat(res).isEqualTo(RESULT_ANSWER);

        res = (String) shell.evaluate(() -> COMMAND_RESULT_SHORT);
        assertThat(res).isEqualTo(RESULT_ANSWER);
    }
}
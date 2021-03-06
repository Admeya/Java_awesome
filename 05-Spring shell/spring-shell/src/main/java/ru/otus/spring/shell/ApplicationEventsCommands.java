package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.services.MessagePublisher;
import ru.otus.spring.services.TestingService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommands {

    private final MessagePublisher messagePublisher;
    private final TestingService testingService;

    private String userName;
    private int countCorrectAnswer;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "DearUser") String userName) {
        this.userName = userName;
        return messagePublisher.getHelloMessage(userName) +
                "\n" + messagePublisher.getHintMessage();
    }

    @ShellMethod(value = "Testing", key = {"t", "test"})
    @ShellMethodAvailability(value = "testProcess")
    public String startTesting() {
        if (isPublishEventCommandAvailable().isAvailable()) {
            this.countCorrectAnswer = testingService.launchTesting();
        }
        return "Тестирование завершено";
    }

    @ShellMethod(value = "Show result", key = {"res", "result"})
    @ShellMethodAvailability(value = "showResult")
    public String goodbye() {
        if (isPublishEventCommandAvailable().isAvailable()) {
            testingService.checkExamine(userName, countCorrectAnswer);
        }
        return "Возвращайтесь снова!";
    }

    private Availability isPublishEventCommandAvailable() {
        return userName == null ? Availability.unavailable("Сначала залогиньтесь") : Availability.available();
    }
}

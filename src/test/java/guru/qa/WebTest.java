package guru.qa;

import com.codeborne.selenide.*;
import guru.qa.domain.MenuItem;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.files.DownloadActions.click;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;


public class WebTest {

    @BeforeAll
    static void BeforeAll() {
        Configuration.browserSize = "1920x1080";
    }

    @ValueSource(strings = {
            "Alexander",
            "Alena"
    })
    @ParameterizedTest(name = "Заполнение формы имя {0}")
    void yaSearchTest(String testData) {
//        Предусловия:
        Selenide.open("https://demoqa.com/text-box");
//        Шаги:
        $("#userName").setValue(testData);
        $("#submit").click();
//        Ожидаемый результат:
        $("#output").shouldHave(text(testData));
    }

    @CsvSource(value = {
            "Alexander, example@mail.ru,",
            "JUnit, example2@mail.ru,"
    })
    @ParameterizedTest(name = "Проверка заполнение формы имя {0}, емайл {1}")
    void yaSearchComplexTest(String testData, String emailData) {
//        Предусловия:
        Selenide.open("https://demoqa.com/text-box");
//        Шаги:
        $("#userName").setValue(testData);
        $("#userEmail").setValue(emailData);
        $("#submit").click();
//        Ожидаемый результат:
        $("#output")
                .shouldHave(text(testData))
                .shouldHave(text(emailData));
    }

    static Stream<Arguments> methodSourceExampleTest() {
        return Stream.of(
                Arguments.of("example@test.ru"),
                Arguments.of("example2@test.ru")
        );
    }

    @MethodSource()
    @ParameterizedTest
    void methodSourceExampleTest(String login) {
        Selenide.open("https://demoqa.com/text-box");
        $("#userEmail").setValue(login);
        $("#submit").click();
    }
}
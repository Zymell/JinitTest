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
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;


public class WebTest {

    @BeforeAll
    static void BeforeAll(){
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

    static Stream<Arguments> argumentsForTest() {
        return Stream.of(
                Arguments.of("example@mail.ru"),
                Arguments.of("example2@mail.ru")
        );
    }

    @MethodSource("methodSourceExampleTest")
    @ParameterizedTest
    void methodSourceExampleTest(String first, List<Integer> second) {
        System.out.println(first + " and list: " + second);
    }

    @EnumSource(MenuItem.class)
    @ParameterizedTest()
    void yaSearchMenuTest(MenuItem testData) {
//        Предусловия:
        Selenide.open("https://ya.ru");
//        Шаги:
        $("#text").setValue("Allure TestOps");
        $("button[type='submit']").click();
//        Ожидаемый результат:
        $$(".navigation__item")
                .find(Condition.text(testData.rusName))
                .click();

        System.out.println(MenuItem.IMG.rusName);

        Assertions.assertEquals(
                2,
                WebDriverRunner.getWebDriver().getWindowHandles().size()
        );
    }

    @AfterEach
    void close() {
        Selenide.closeWebDriver();
    }

 @Override
    public String toString() {
        return "WebTest{}";
    }
}

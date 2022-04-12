package guru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import guru.qa.domain.MenuItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class WebTest {

    @ValueSource(strings = {
            "Selenide",
            "JUnit"
    })
    @ParameterizedTest(name = "Проверка поиска в яндексе по слову {0}")
    void yaSearchTest(String testData) {
//        Предусловия:
        Selenide.open("https://ya.ru");
//        Шаги:
        $("#text").setValue(testData);
        $("button[type='submit']").click();
//        Ожидаемый результат:
        $$(".serp-item")
                .find(Condition.text(testData))
                .shouldBe(visible);
    }

    @CsvSource(value = {
            "Selenide, is an open source library for test",
            "JUnit, Support JUnit"
    })
    @ParameterizedTest(name = "Проверка поиска в яндексе по слову {0}, ожидаем результат: {1}")
    void yaSearchComplexTest(String testData, String expectedResult) {
//        Предусловия:
        Selenide.open("https://ya.ru");
//        Шаги:
        $("#text").setValue(testData);
        $("button[type='submit']").click();
//        Ожидаемый результат:
        $$(".serp-item")
                .find(Condition.text(expectedResult))
                .shouldBe(visible);
    }

    static Stream<Arguments> methodSourceExampleTest() {
        return Stream.of(
                Arguments.of("first string", List.of(42, 13)),
                Arguments.of("second string", List.of(1, 2))
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

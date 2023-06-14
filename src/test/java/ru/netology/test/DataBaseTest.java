package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class DataBaseTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void clean() {
        SqlHelper.cleanDatabase();
    }

    @Test
    public void shouldLogin() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id='login'] input").setValue(DataHelper.getAuthInfo().getLogin());
        $("[data-test-id='password'] input").setValue(DataHelper.getAuthInfo().getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id='code'] input").setValue(SqlHelper.getCode().getCode());
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id='dashboard']").shouldBe(visible);
    }

    @Test
    public void InvalidLogin() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id='login'] input").setValue(DataHelper.generateLogin());
        $("[data-test-id='password'] input").setValue(DataHelper.getAuthInfo().getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $(byText("Ошибка!")).shouldBe(visible);
        $(".notification__content")
                .shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(visible);
    }

    @Test
    public void InvalidPassword() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id='login'] input").setValue(DataHelper.getAuthInfo().getLogin());
        $("[data-test-id='password'] input").setValue(DataHelper.generatePassword());
        $$("button").find(exactText("Продолжить")).click();
        $(byText("Ошибка!")).shouldBe(visible);
        $(".notification__content")
                .shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(visible);
    }

    @Test
    public void InvalidCode() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id='login'] input").setValue(DataHelper.getAuthInfo().getLogin());
        $("[data-test-id='password'] input").setValue(DataHelper.getAuthInfo().getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id='code'] input").setValue(DataHelper.getRandomCode().getCode());
        $$("button").find(exactText("Продолжить")).click();
        $(byText("Ошибка!")).shouldBe(visible);
        $(".notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан код! Попробуйте ещё раз."))
                .shouldBe(visible);

    }

    @Test
    public void UserBlocked() {
        Configuration.holdBrowserOpen = true;
        $("[data-test-id='login'] input").setValue(DataHelper.getAuthInfo().getLogin());
        $("[data-test-id='password'] input").setValue(DataHelper.generatePassword());
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id='password'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='password'] input").setValue(DataHelper.generatePassword());
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id='password'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='password'] input").setValue(DataHelper.generatePassword());
        $$("button").find(exactText("Продолжить")).click();
        $(byText("Ошибка!")).shouldBe(visible);
        $(".notification__content")
                .shouldHave(Condition.text("Пользователь заблокирован!"))
                .shouldBe(visible);

    }

}
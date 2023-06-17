package ru.netology.page;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LoginPage {
    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        $("[data-test-id='login'] input").setValue(authInfo.getLogin());
        $("[data-test-id='password'] input").setValue(authInfo.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        return new VerificationPage();
    }

    public void invalidLogin(DataHelper.AuthInfo authInfo) {
        $("[data-test-id='login'] input").setValue(DataHelper.generateLogin());
        $("[data-test-id='password'] input").setValue(authInfo.getPassword());
        $$("button").find(exactText("Продолжить")).click();
    }

    public void invalidPassword(DataHelper.AuthInfo authInfo) {
        $("[data-test-id='login'] input").setValue(authInfo.getLogin());
        $("[data-test-id='password'] input").setValue(DataHelper.generatePassword());
        $$("button").find(exactText("Продолжить")).click();
    }

    public void errorMessage(String expectedText) {
        $(".notification__content")
                .shouldHave(Condition.text(expectedText))
                .shouldBe(visible);
    }

    public void userBlocked(DataHelper.AuthInfo authInfo) {
        $("[data-test-id='login'] input").setValue(authInfo.getLogin());
        $("[data-test-id='password'] input").setValue(DataHelper.generatePassword());
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id='password'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='password'] input").setValue(DataHelper.generatePassword());
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id='password'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='password'] input").setValue(DataHelper.generatePassword());
        $$("button").find(exactText("Продолжить")).click();
    }

    public void messageAboutBlock(String expectedText) {
        $(".notification__content")
                .shouldHave(Condition.text(expectedText))
                .shouldBe(visible);
    }
}

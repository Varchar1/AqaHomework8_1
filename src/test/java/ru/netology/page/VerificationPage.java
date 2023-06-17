package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public DashboardPage validVerify() {
        codeField.setValue(SqlHelper.getCode().getCode());
        verifyButton.click();
        return new DashboardPage();
    }

    public void invalidCode() {
        codeField.setValue(DataHelper.getRandomCode().getCode());
        verifyButton.click();
    }

    public void errorMessage(String expectedText) {
        $(".notification__content")
                .shouldHave(Condition.text(expectedText))
                .shouldBe(visible);
    }
}

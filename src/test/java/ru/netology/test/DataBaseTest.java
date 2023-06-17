package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.SqlHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataHelper.getAuthInfo;


public class DataBaseTest {
    LoginPage loginPage;

    @BeforeEach
    void setup() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @AfterAll
    static void clean() {
        SqlHelper.cleanDatabase();
    }

    @Test
    public void shouldLogin() {
        Configuration.holdBrowserOpen = true;
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var dashboardPage = verificationPage.validVerify();
    }

    @Test
    public void InvalidLogin() {
        Configuration.holdBrowserOpen = true;
        var authInfo = getAuthInfo();
        loginPage.invalidLogin(authInfo);
        loginPage.errorMessage("Неверно указан логин или пароль");
    }

    @Test
    public void InvalidPassword() {
        Configuration.holdBrowserOpen = true;
        var authInfo = getAuthInfo();
        loginPage.invalidPassword(getAuthInfo());
        loginPage.errorMessage("Неверно указан логин или пароль");
    }

    @Test
    public void InvalidCode() {
        Configuration.holdBrowserOpen = true;
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.invalidCode();
        verificationPage.errorMessage("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }

    @Test
    public void UserBlocked() {
        Configuration.holdBrowserOpen = true;
        var authInfo = getAuthInfo();
        loginPage.userBlocked(getAuthInfo());
        loginPage.messageAboutBlock("Пользователь заблокирован!");
    }

}
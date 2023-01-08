package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({ScreenShooterExtension.class})
public class AuthTest {
    private final String baseUrl = "http://localhost:9999";
    private AuthorisationPage page;

    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        Configuration.baseUrl = baseUrl;
        Configuration.holdBrowserOpen = true;  // false не оставляет браузер открытым по завершению теста
        Configuration.reportsFolder = "build/reports/tests/test/screenshoots";
        Selenide.open("");
        page = new AuthorisationPage();
    }

    @DisplayName("Активен, креды верны")
    @Test
    void activeUserTest() {
        page
                .fillForm(DataHelper.setCredentials(DataHelper.getActiveAuthorisationInfo()))
                .clickSubmit()
                .checkPersonalAccount();
    }

    @DisplayName("Активен, все креды не верны")
    @Test
    void activeBadCredentialsTest() {
        page
                .fillForm(
                        DataHelper.breakCredentials(
                                DataHelper.setCredentials(
                                        DataHelper.getActiveAuthorisationInfo()),
                                DataHelper.BreakCredentialsType.BOTH))
                .clickSubmit()
                .checkErrorMessage();
    }

    @DisplayName("Заблокирован, креды верны")
    @Test
    void blockedUserTest() {
        page
                .fillForm(DataHelper.setCredentials(DataHelper.getBlockedAuthorisationInfo()))
                .clickSubmit()
                .checkBlockedMessage();
    }

    @DisplayName("Заблокирован, все креды не верны")
    @Test
    void blockedBadCredentialsTest() {
        page
                .fillForm(
                        DataHelper.breakCredentials(
                                DataHelper.setCredentials(
                                        DataHelper.getBlockedAuthorisationInfo()),
                                DataHelper.BreakCredentialsType.BOTH))
                .clickSubmit()
                .checkErrorMessage();
    }

    @DisplayName("Неизвестный пользователь")
    @Test
    void unknownUserTest() {
        page
                .fillForm(DataHelper.getActiveAuthorisationInfo())
                .clickSubmit()
                .checkErrorMessage();
    }

    @DisplayName("Активен, только пароль не верный")
    @Test
    void activeUserBadPassTest() {
        page
                .fillForm(
                        DataHelper.breakCredentials(
                                DataHelper.setCredentials(
                                        DataHelper.getActiveAuthorisationInfo()),
                                DataHelper.BreakCredentialsType.PASSWORD))
                .clickSubmit()
                .checkErrorMessage();
    }

    @DisplayName("Активен, только логин не верный")
    @Test
    void activeUserBadLoginTest() {
        page
                .fillForm(
                        DataHelper.breakCredentials(
                                DataHelper.setCredentials(
                                        DataHelper.getActiveAuthorisationInfo()),
                                DataHelper.BreakCredentialsType.LOGIN))
                .clickSubmit()
                .checkErrorMessage();
    }

    @DisplayName("Заблокирован, только пароль не верный")
    @Test
    void blockedUserBadPassTest() {
        page
                .fillForm(
                        DataHelper.breakCredentials(
                                DataHelper.setCredentials(
                                        DataHelper.getBlockedAuthorisationInfo()),
                                DataHelper.BreakCredentialsType.PASSWORD))
                .clickSubmit()
                .checkErrorMessage();
    }

    @DisplayName("Заблокирован, только логин не верный")
    @Test
    void blockedUserBadLoginTest() {
        page
                .fillForm(
                        DataHelper.breakCredentials(
                                DataHelper.setCredentials(
                                        DataHelper.getBlockedAuthorisationInfo()),
                                DataHelper.BreakCredentialsType.LOGIN))
                .clickSubmit()
                .checkErrorMessage();
    }
}

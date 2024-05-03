package ru.netology.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;
import static ru.netology.data.SQLHelper.cleanAuthCodes;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class BankVerificationTest {
    LoginPage loginPage;
    @AfterEach
    void tearDown(){
        cleanAuthCodes();
    }
     @AfterAll
    static void tearDawnAll(){
        cleanDatabase();
    }
    @BeforeEach
    void setUp(){
        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
    }
    @Test
    @DisplayName("Should test successfully login and password from Database")
    void shouldTestSuccessfullyLoginAndPasswordFromDatabase(){
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.visibleVerificationPage();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.verify(verificationCode.getCode());
    }
    @Test
    @DisplayName("Should test with error notification if user has random verification code")
    void shouldTestWithErrorNotificationIfUserHasRandomVerificationCode(){
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.visibleVerificationPage();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.setErrorNotification("Ошибка! \nНеверно указан код! Попробуйте ещё раз.");
    }
    @Test
    @DisplayName("Should test with error notification if user is not exist in Database")
    void shouldTestWithErrorNotificationIfUserIsNotExistInDatabase() {
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.setErrorNotification("Ошибка! \nНеверно указан логин или пароль");
    }
}

package ru.iteco.fmhandroid;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.AppActivity;
// Импорт нашего нового класса с данными
import ru.iteco.fmhandroid.utils.TestData;

@RunWith(AllureAndroidJUnit4.class) // РАННЕР ДЛЯ ALLURE
@Epic("Тестирование UI приложения Мобильный Хоспис")
@Feature("Авторизация")
public class AuthTest {

    AuthSteps authSteps = new AuthSteps();

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        // Убеждаемся, что перед каждым тестом авторизации мы находимся на экране входа
        authSteps.ensureLoggedOut();
    }

    @Test
    @Story("Тест 1: Успешный вход в систему")
    public void testSuccessfulLogin() {
        // ИСПОЛЬЗУЕМ КОНСТАНТЫ ИЗ TEST DATA
        authSteps.login(TestData.VALID_LOGIN, TestData.VALID_PASSWORD);
        authSteps.checkNewsScreenLoaded();
    }

    @Test
    @Story("Тест 2: Пустой логин и пароль")
    public void testEmptyFields() {
        authSteps.login("", "");
        authSteps.checkToast("Логин и пароль не могут быть пустыми", "Login and password cannot be empty");
    }

    @Test
    @Story("Тест 3: Пустой пароль")
    public void testEmptyPassword() {
        // Здесь используем валидный логин, но пустой пароль
        authSteps.login(TestData.VALID_LOGIN, "");
        authSteps.checkToast("Логин и пароль не могут быть пустыми", "Login and password cannot be empty");
    }

    @Test
    @Story("Тест 4: Пустой логин")
    public void testEmptyLogin() {
        // Здесь пустой логин и валидный пароль
        authSteps.login("", TestData.VALID_PASSWORD);
        authSteps.checkToast("Логин и пароль не могут быть пустыми", "Login and password cannot be empty");
    }

    @Test
    @Story("Тест 5: Неверный пароль")
    public void testInvalidPassword() {
        authSteps.login(TestData.VALID_LOGIN, "123456");
        authSteps.checkToast("Неверный логин или пароль", "Wrong login or password");
    }

    @Test
    @Story("Тест 6: Неверный логин")
    public void testInvalidLogin() {
        authSteps.login("user999", TestData.VALID_PASSWORD);
        authSteps.checkToast("Неверный логин или пароль", "Wrong login or password");
    }

    @Test
    @Story("Тест 7: Логин со спецсимволами")
    public void testLoginWithSpecialChars() {
        authSteps.login("@#$%^", TestData.VALID_PASSWORD);
        authSteps.checkToast("Неверный логин или пароль", "Wrong login or password");
    }
}
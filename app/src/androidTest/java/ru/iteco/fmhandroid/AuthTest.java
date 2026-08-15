package ru.iteco.fmhandroid;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.AppActivity;

@RunWith(AndroidJUnit4.class)
@Epic("Тестирование UI приложения Мобильный Хоспис")
@Feature("Авторизация")
public class AuthTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    // Этот тест запускается, логинится, проверяет и ВЫХОДИТ. Он чистит за собой.
    @Test
    @Story("Успешный вход в систему и выход")
    public void testSuccessfulLoginAndLogout() {
        AuthSteps authSteps = new AuthSteps();
        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }

        authSteps.login("login2", "password2");
        authSteps.checkNewsScreenLoaded();
        authSteps.logout();
    }

    // Этот тест запускается на ЧИСТОМ приложении (т.к. прошлый вышел), делает проверку и всё.
    @Test
    @Story("Неуспешный вход в систему (неверный пароль)")
    public void testInvalidPassword() {
        AuthSteps authSteps = new AuthSteps();
        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }

        authSteps.login("login2", "wrong_password");
        authSteps.checkErrorToastIsDisplayed();
    }
}
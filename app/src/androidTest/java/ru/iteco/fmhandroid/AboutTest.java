package ru.iteco.fmhandroid;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.steps.AboutSteps;
import ru.iteco.fmhandroid.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.AppActivity;

@RunWith(AndroidJUnit4.class)
@Epic("Тестирование UI приложения Мобильный Хоспис")
@Feature("Раздел About (О приложении)")
public class AboutTest {

    AuthSteps authSteps;
    AboutSteps aboutSteps;

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        authSteps = new AuthSteps();
        aboutSteps = new AboutSteps();
    }

    @Test
    @Story("Открытие экрана About")
    @Description("Проверка перехода на экран About и отображения информации о версии")
    public void testOpenAboutScreen() {
        // 1. Авторизуемся, так как меню доступно только после входа
        authSteps.login("login2", "password2");
        authSteps.checkNewsScreenLoaded();

        // 2. Идем в About и проверяем его
        aboutSteps.openAboutScreen();
        aboutSteps.checkAboutScreenLoaded();

        // 3. УБИРАЕМ ЗА СОБОЙ (чтобы не сломать другие тесты при массовом запуске)
        aboutSteps.goBack();
        authSteps.logout();
    }
}
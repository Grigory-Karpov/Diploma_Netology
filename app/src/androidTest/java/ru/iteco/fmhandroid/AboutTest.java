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
        try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); } // ждем splash screen

        // Преподаватель просил вынести авторизацию в @Before!
        // Если мы уже залогинены с прошлого теста - робот не будет тратить время.
        authSteps.ensureLoggedIn("login2", "password2");
    }

    @Test
    @Story("Открытие экрана About")
    @Description("Проверка перехода на экран About и отображения информации о версии")
    public void testOpenAboutScreen() {
        // Теперь тут только логика самого About, как и должно быть у профи!
        aboutSteps.openAboutScreen();
        aboutSteps.checkAboutScreenLoaded();
        aboutSteps.goBack();
    }
}
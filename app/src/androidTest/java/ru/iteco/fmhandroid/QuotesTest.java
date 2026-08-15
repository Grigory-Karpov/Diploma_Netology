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
import ru.iteco.fmhandroid.steps.AuthSteps;
import ru.iteco.fmhandroid.steps.QuotesSteps;
import ru.iteco.fmhandroid.ui.AppActivity;

@RunWith(AndroidJUnit4.class)
@Epic("Тестирование UI приложения Мобильный Хоспис")
@Feature("Раздел Тематические цитаты (Our Mission)")
public class QuotesTest {

    AuthSteps authSteps;
    QuotesSteps quotesSteps;

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        authSteps = new AuthSteps();
        quotesSteps = new QuotesSteps();
        try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
        // Проверяем состояние: если нужно - логинимся
        authSteps.ensureLoggedIn("login2", "password2");
    }

    @Test
    @Story("Открытие экрана Тематические цитаты")
    @Description("Проверка перехода на экран Our Mission по клику на иконку бабочки")
    public void testOpenQuotesScreen() {
        // Открываем Цитаты и проверяем, что они загрузились
        quotesSteps.openQuotesScreen();
        quotesSteps.checkQuotesScreenLoaded();

        // ВАЖНО: Мы не делаем тут logout(), чтобы сэкономить время для следующих тестов.
        // Наш умный робот сам выйдет из аккаунта в начале следующего теста, если это будет нужно!
    }
}
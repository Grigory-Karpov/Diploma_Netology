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
import ru.iteco.fmhandroid.steps.NewsSteps;
import ru.iteco.fmhandroid.ui.AppActivity;

@RunWith(AndroidJUnit4.class)
@Epic("Тестирование UI приложения Мобильный Хоспис")
@Feature("Раздел Новости (Управление)")
public class NewsTest {

    AuthSteps authSteps;
    NewsSteps newsSteps;

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        authSteps = new AuthSteps();
        newsSteps = new NewsSteps();

        // Умная проверка сама дождется окончания загрузки и войдет, если надо!
        authSteps.ensureLoggedIn("login2", "password2");
    }

    @Test
    @Story("Негативный сценарий создания новости")
    @Description("Проверка попытки создания новости с пустыми полями (ожидается ошибка, форма не закрывается)")
    public void testCreateNewsWithEmptyFields() {
        newsSteps.openControlPanel();
        newsSteps.clickAddNews();
        newsSteps.clickSaveAndCheckError();
    }
}
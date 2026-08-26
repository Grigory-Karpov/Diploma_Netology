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

    AuthSteps authSteps = new AuthSteps();
    NewsSteps newsSteps = new NewsSteps();

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        try { Thread.sleep(8000); } catch (InterruptedException e) {}
        authSteps.ensureLoggedIn("login2", "password2");
        newsSteps.openControlPanel(); // Все тесты новостей начинаются в Control Panel
    }

    @Test
    @Story("Тест 1: Переход в Control Panel")
    public void testOpenControlPanel() {
        newsSteps.checkControlPanelLoaded();
    }

    @Test
    @Story("Тест 2: Негативный сценарий создания новости")
    public void testCreateNewsWithEmptyFields() {
        newsSteps.clickAddNews();
        newsSteps.clickSaveAndCheckError();
    }

    @Test
    @Story("Тест 3: Отмена создания новости")
    public void testCancelNewsCreation() {
        newsSteps.clickAddNews();
        newsSteps.clickCancelAndConfirm();
        newsSteps.checkControlPanelLoaded();
    }

    @Test
    @Story("Тест 4: Открытие фильтра новостей")
    public void testOpenFilter() {
        newsSteps.openFilter();
    }

    @Test
    @Story("Тест 5: Отмена фильтрации новостей")
    public void testCancelFilter() {
        newsSteps.openFilter();
        newsSteps.cancelFilter();
        newsSteps.checkControlPanelLoaded();
    }

    @Test
    @Story("Тест 6: Открытие формы создания новости")
    public void testOpenCreateNewsForm() {
        newsSteps.clickAddNews();
    }

    @Test
    @Story("Тест 7: Возврат в Control Panel из создания")
    public void testReturnToControlPanelFromCreation() {
        newsSteps.clickAddNews();
        newsSteps.clickCancelAndConfirm();
    }
}
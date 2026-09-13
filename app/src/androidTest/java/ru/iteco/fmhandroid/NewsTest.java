package ru.iteco.fmhandroid;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.steps.AuthSteps;
import ru.iteco.fmhandroid.steps.NewsSteps;
import ru.iteco.fmhandroid.ui.AppActivity;

@RunWith(AllureAndroidJUnit4.class)
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

    @Test
    @Story("Тест 8: Позитивный сценарий создания новости")
    public void testCreateNewsPositive() {
        String testTitle = "Дипломная новость Создание";
        String testDesc = "Описание позитивного теста создания";

        newsSteps.clickAddNews();
        newsSteps.fillAndSaveNews("Объявление", testTitle, testDesc);
        newsSteps.checkControlPanelLoaded(); // Ждем возврата в контрольную панель
        newsSteps.checkNewsWithTitleExists(testTitle);
    }

    @Test
    @Story("Тест 9: Позитивный сценарий редактирования новости")
    public void testEditNewsPositive() {
        String originalTitle = "Новость для редактирования";
        String originalDesc = "Старое описание";
        String updatedDesc = "Обновленное описание после редактирования";

        newsSteps.clickAddNews();
        newsSteps.fillAndSaveNews("Зарплата", originalTitle, originalDesc);
        newsSteps.checkControlPanelLoaded();

        newsSteps.clickEditCreatedNews(originalTitle);
        newsSteps.editNewsDescriptionAndSave(updatedDesc);
        newsSteps.checkControlPanelLoaded();
    }
}
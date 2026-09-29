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
import ru.iteco.fmhandroid.steps.NewsSteps;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.utils.TestData;

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
        // Убран слип, добавлена авторизация через TestData
        authSteps.ensureLoggedIn(TestData.VALID_LOGIN, TestData.VALID_PASSWORD);
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

    // НОВЫЕ ТЕСТЫ ПО ЗАМЕЧАНИЯМ ПРЕПОДАВАТЕЛЯ

    @Test
    @Story("Тест 8: Позитивный сценарий создания новости")
    public void testCreateNewsSuccess() {
        // Генерируем уникальный заголовок, чтобы он не сливался со старыми
        String uniqueTitle = "Тестовая новость " + System.currentTimeMillis();

        newsSteps.clickAddNews();
        newsSteps.fillNewsForm("Объявление", uniqueTitle, "Успешное создание новости");
        newsSteps.clickSaveButton();

        // Проверяем, что заголовок появился в списке
        newsSteps.checkNewsWithTitleExists(uniqueTitle);
    }

    @Test
    @Story("Тест 9: Позитивный сценарий редактирования новости с проверкой (ассертом)")
    public void testEditNewsSuccess() {
        String originalTitle = "Создано для теста ред. " + System.currentTimeMillis();
        String editedTitle = "Отредактировано " + System.currentTimeMillis();
        String editedDescription = "Это новое описание после изменения";

        // 1. Создаем новость-донор
        newsSteps.clickAddNews();
        newsSteps.fillNewsForm("Праздник", originalTitle, "Старое описание");
        newsSteps.clickSaveButton();

        // 2. Находим её в списке, скроллим к ней и нажимаем карандаш
        newsSteps.clickEditExistingNews(originalTitle);

        // 3. Заполняем форму новыми данными
        newsSteps.fillNewsForm("Праздник", editedTitle, editedDescription);
        newsSteps.clickSaveButton();

        // 4. ПРОВЕРКА 1: Убеждаемся, что новость с новым заголовком видна
        newsSteps.checkNewsWithTitleExists(editedTitle);

        // 5. ПРОВЕРКА 2: Разворачиваем её и проверяем, что описание сохранилось!
        newsSteps.openNewsAndCheckDescription(editedTitle, editedDescription);
    }
}
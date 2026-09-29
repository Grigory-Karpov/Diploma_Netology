package ru.iteco.fmhandroid.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;

// Импорт для скролла по спискам
import androidx.test.espresso.contrib.RecyclerViewActions;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.utils.ToastMatcher;
import ru.iteco.fmhandroid.utils.WaitUtils;

public class NewsSteps {

    public void openControlPanel() {
        Allure.step("Переход в Панель управления новостями");
        // Ждем появления бургер-меню
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.main_menu_image_button, 8000));
        // Кликаем по меню
        onView(withId(R.id.main_menu_image_button)).perform(click());

        // Ждем загрузки пунктов выпадающего списка
        onView(isRoot()).perform(WaitUtils.waitForElement(android.R.id.title, 3000));
        // Выбираем пункт News
        onView(withText("News")).perform(click());

        // Ждем загрузки страницы новостей и появления кнопки редактирования (блокнот)
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.edit_news_material_button, 10000));
        // Нажимаем на блокнот для перехода в Control Panel
        onView(withId(R.id.edit_news_material_button)).perform(click());
    }

    public void clickAddNews() {
        Allure.step("Нажатие кнопки создания новости (+)");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.add_news_image_view, 5000));
        onView(withId(R.id.add_news_image_view)).perform(click());
    }

    public void clickSaveAndCheckError() {
        Allure.step("Нажатие Сохранить и проверка всплывающей ошибки");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.save_button, 5000));
        onView(withId(R.id.save_button)).perform(click());
        checkToast("Заполните пустые поля", "Fill empty fields");
    }

    public void clickCancelAndConfirm() {
        Allure.step("Отмена создания новости");
        onView(withId(R.id.cancel_button)).perform(click());
        onView(isRoot()).perform(WaitUtils.waitForElement(android.R.id.button1, 3000));
        onView(withId(android.R.id.button1)).perform(click()); // Кнопка ОК
    }

    public void openFilter() {
        Allure.step("Открытие фильтра новостей");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.filter_news_material_button, 5000));
        onView(withId(R.id.filter_news_material_button)).perform(click());
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.filter_button, 5000));
        onView(withId(R.id.filter_button)).check(matches(isDisplayed()));
    }

    public void cancelFilter() {
        Allure.step("Отмена фильтрации");
        onView(withId(R.id.cancel_button)).perform(click());
    }

    public void checkControlPanelLoaded() {
        Allure.step("Проверка загрузки Control Panel");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.add_news_image_view, 5000));
        onView(withId(R.id.add_news_image_view)).check(matches(isDisplayed()));
    }

    public void checkToast(String textRu, String textEn) {
        Allure.step("Проверка появления всплывающего сообщения об ошибке");
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 5000;
        boolean found = false;
        while (System.currentTimeMillis() < endTime) {
            try {
                onView(anyOf(withText(textRu), withText(textEn)))
                        .inRoot(new ToastMatcher())
                        .check(matches(isDisplayed()));
                found = true;
                break;
            } catch (Exception | AssertionError e) {
                try { Thread.sleep(200); } catch (InterruptedException ignored) {}
            }
        }
        if (!found) throw new AssertionError("Тост об ошибке не найден!");
    }

    // НОВЫЕ ПОЗИТИВНЫЕ МЕТОДЫ ПО ЗАМЕЧАНИЯМ ПРЕПОДАВАТЕЛЯ

    public void fillNewsForm(String category, String title, String description) {
        Allure.step("Заполнение полей формы новости");
        // Ждем поле категории и вводим текст
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.news_item_category_text_auto_complete_text_view, 5000));
        onView(withId(R.id.news_item_category_text_auto_complete_text_view)).perform(click());
        onView(withId(R.id.news_item_category_text_auto_complete_text_view)).perform(androidx.test.espresso.action.ViewActions.replaceText(category), androidx.test.espresso.action.ViewActions.closeSoftKeyboard());

        // Вводим заголовок
        onView(withId(R.id.news_item_title_text_input_edit_text)).perform(click());
        onView(withId(R.id.news_item_title_text_input_edit_text)).perform(androidx.test.espresso.action.ViewActions.replaceText(title), androidx.test.espresso.action.ViewActions.closeSoftKeyboard());

        // Вводим дату и время (жестко задаем, чтобы не кликать по календарю)
        onView(withId(R.id.news_item_publish_date_text_input_edit_text)).perform(androidx.test.espresso.action.ViewActions.replaceText("25.10.2026"), androidx.test.espresso.action.ViewActions.closeSoftKeyboard());
        onView(withId(R.id.news_item_publish_time_text_input_edit_text)).perform(androidx.test.espresso.action.ViewActions.replaceText("12:00"), androidx.test.espresso.action.ViewActions.closeSoftKeyboard());

        // Вводим описание
        onView(withId(R.id.news_item_description_text_input_edit_text)).perform(click());
        onView(withId(R.id.news_item_description_text_input_edit_text)).perform(androidx.test.espresso.action.ViewActions.replaceText(description), androidx.test.espresso.action.ViewActions.closeSoftKeyboard());
    }

    public void clickSaveButton() {
        Allure.step("Нажатие кнопки 'Сохранить'");
        onView(withId(R.id.save_button)).perform(click());
        // Ждем возврата на панель управления (ждем плюс)
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.add_news_image_view, 5000));
    }

    public void checkNewsWithTitleExists(String title) {
        Allure.step("Скролл к новости и проверка, что заголовок '" + title + "' есть в списке");
        // Скроллим список до карточки с нужным текстом (решает проблему новостей вне экрана)
        onView(withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(title))));
        // Проверяем, что заголовок виден
        onView(allOf(withText(title), isDisplayed())).check(matches(isDisplayed()));
    }

    public void clickEditExistingNews(String title) {
        Allure.step("Нажатие карандаша для редактирования новости '" + title + "'");
        // Скроллим до нужной новости
        onView(withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(title))));
        // Ищем кнопку редактирования (карандаш) конкретно в блоке с нашим заголовком и кликаем
        onView(allOf(withId(R.id.edit_news_item_image_view),
                androidx.test.espresso.matcher.ViewMatchers.hasSibling(withText(title))))
                .perform(click());
        // Ждем форму (кнопку сохранить)
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.save_button, 5000));
    }

    public void openNewsAndCheckDescription(String title, String expectedDescription) {
        Allure.step("Разворачивание новости и проверка нового описания (ассерт)");
        // Скроллим до новости
        onView(withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(title))));

        // Кликаем по карточке новости, чтобы развернуть её (находим её по заголовку внутри)
        Allure.step("Клик по карточке для раскрытия");
        onView(allOf(withId(R.id.news_item_material_card_view), hasDescendant(withText(title))))
                .perform(click());

        // Проверяем, что внутри этой новости появилось ожидаемое описание
        Allure.step("Проверка совпадения описания");
        onView(allOf(withId(R.id.news_item_description_text_view), withText(expectedDescription)))
                .check(matches(isDisplayed()));
    }
}
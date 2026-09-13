package ru.iteco.fmhandroid.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.utils.ToastMatcher;
import ru.iteco.fmhandroid.utils.WaitUtils;

public class NewsSteps {

    public void openControlPanel() {
        Allure.step("Переход в Панель управления новостями");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.main_menu_image_button, 8000));
        onView(withId(R.id.main_menu_image_button)).perform(click());

        onView(isRoot()).perform(WaitUtils.waitForElement(android.R.id.title, 3000));
        onView(withText("News")).perform(click());

        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.edit_news_material_button, 10000));
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
        onView(withId(android.R.id.button1)).perform(click()); // Кнопка OK
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
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 5000;
        boolean found = false;
        while (System.currentTimeMillis() < endTime) {
            try {
                onView(anyOf(withText(textRu), withText(textEn)))
                        .inRoot(new ToastMatcher()).check(matches(isDisplayed()));
                found = true;
                break;
            } catch (Exception | AssertionError e) {
                try { Thread.sleep(200); } catch (InterruptedException ignored) {}
            }
        }
        if (!found) throw new AssertionError("Тост об ошибке не найден!");
    }

    // --- НОВЫЕ ПОЗИТИВНЫЕ МЕТОДЫ ДЛЯ ТЕСТОВ ---
    public void fillAndSaveNews(String category, String title, String description) {
        Allure.step("Заполнение полей новости и Сохранение");

        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.news_item_category_text_auto_complete_text_view, 5000));

        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .perform(replaceText(category), closeSoftKeyboard());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .perform(replaceText(title), closeSoftKeyboard());

        onView(withId(R.id.news_item_publish_date_text_input_edit_text))
                .perform(replaceText("15.09.2026"), closeSoftKeyboard());

        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .perform(replaceText("12:00"), closeSoftKeyboard());

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .perform(replaceText(description), closeSoftKeyboard());

        onView(withId(R.id.save_button)).perform(click());
    }

    public void checkNewsWithTitleExists(String title) {
        Allure.step("Проверка, что новость с заголовком '" + title + "' появилась в списке");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.news_item_title_text_view, 5000));
        onView(allOf(
                withId(R.id.news_item_title_text_view),
                withText(title)
        )).check(matches(isDisplayed()));
    }

    public void clickEditCreatedNews(String title) {
        Allure.step("Нажатие на кнопку редактирования новости с заголовком: " + title);
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.edit_news_item_image_view, 5000));
        onView(allOf(
                withId(R.id.edit_news_item_image_view),
                hasSibling(withText(title))
        )).perform(click());
    }

    public void editNewsDescriptionAndSave(String newDescription) {
        Allure.step("Изменение описания новости и сохранение");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.news_item_description_text_input_edit_text, 5000));
        onView(withId(R.id.news_item_description_text_input_edit_text))
                .perform(replaceText(newDescription), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
    }
}
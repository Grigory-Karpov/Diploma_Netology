package ru.iteco.fmhandroid.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.utils.WaitUtils;

public class NewsSteps {

    public void openControlPanel() {
        Allure.step("Переход в Панель управления новостями (через боковое меню)");

        // 1. Надежно открываем боковое меню
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.main_menu_image_button, 8000));
        onView(withId(R.id.main_menu_image_button)).perform(click());

        // 2. Ждем анимацию и выбираем раздел News
        try { Thread.sleep(1500); } catch (InterruptedException e) { e.printStackTrace(); }
        onView(withText("News")).perform(click());

        // 3. Ждем загрузки экрана новостей и кликаем по иконке карандаша
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.edit_news_material_button, 10000));
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        onView(withId(R.id.edit_news_material_button)).perform(click());
    }

    public void clickAddNews() {
        Allure.step("Нажатие кнопки создания новости (+)");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.add_news_image_view, 10000));
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        onView(withId(R.id.add_news_image_view)).perform(click());
    }

    public void clickSaveAndCheckError() {
        Allure.step("Нажатие кнопки Сохранить без заполнения полей и проверка ошибки");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.save_button, 10000));
        onView(withId(R.id.save_button)).perform(click());

        // Проверяем, что форма не закрылась
        onView(withId(R.id.save_button)).check(matches(isDisplayed()));
    }
}
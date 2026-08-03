package ru.iteco.fmhandroid.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.utils.WaitUtils;

public class AboutSteps {

    public void openAboutScreen() {
        Allure.step("Нажатие на кнопку главного меню (бургер)");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.main_menu_image_button, 5000));
        onView(withId(R.id.main_menu_image_button)).perform(click());

        Allure.step("Выбор раздела About в меню");
        // Ждем 1.5 сек пока выедет боковое меню, и сразу кликаем по тексту (без поиска системных ID)
        try { Thread.sleep(1500); } catch (InterruptedException e) { e.printStackTrace(); }
        onView(withText("About")).perform(click());
    }

    public void checkAboutScreenLoaded() {
        Allure.step("Проверка загрузки экрана About (наличие надписи Version)");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.about_version_title_text_view, 7000));
        onView(withId(R.id.about_version_title_text_view)).check(matches(isDisplayed()));
    }

    public void goBack() {
        Allure.step("Возврат на предыдущий экран (системная кнопка Назад)");
        pressBack();
        // Ждем возврата на главную
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.main_menu_image_button, 5000));
    }
}
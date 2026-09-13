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
        onView(isRoot()).perform(WaitUtils.waitForElement(android.R.id.title, 3000));
        onView(withText("About")).perform(click());
    }

    public void checkAboutScreenLoaded() {
        Allure.step("Проверка загрузки экрана About");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.about_version_title_text_view, 7000));
        onView(withId(R.id.about_version_title_text_view)).check(matches(isDisplayed()));
    }

    public void goBack() {
        Allure.step("Возврат на предыдущий экран");
        pressBack();
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.main_menu_image_button, 5000));
    }
}
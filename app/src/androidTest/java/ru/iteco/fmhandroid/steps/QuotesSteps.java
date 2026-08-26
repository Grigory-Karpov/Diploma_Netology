package ru.iteco.fmhandroid.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.utils.WaitUtils;

public class QuotesSteps {
    public void openQuotesScreen() {
        Allure.step("Переход в раздел 'Тематические цитаты' (нажатие на бабочку)");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.our_mission_image_button, 5000));
        onView(withId(R.id.our_mission_image_button)).perform(click());
    }

    public void checkQuotesScreenLoaded() {
        Allure.step("Проверка загрузки экрана цитат (наличие заголовка)");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.our_mission_title_text_view, 5000));
        onView(withId(R.id.our_mission_title_text_view)).check(matches(isDisplayed()));
    }
}
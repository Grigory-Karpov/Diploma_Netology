package ru.iteco.fmhandroid.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.supportsInputMethods;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.utils.WaitUtils;

public class AuthSteps {

    public void ensureLoggedOut() {
        Allure.step("Подготовка: проверяем, что мы разлогинены");
        try {
            onView(isRoot()).perform(WaitUtils.waitForElement(R.id.main_menu_image_button, 10000));
            logout();
        } catch (Throwable t) {
            // Если кнопки нет, значит мы уже на экране входа.
        }
    }

    public void ensureLoggedIn(String login, String password) {
        Allure.step("Подготовка: проверяем, что мы залогинены");
        try {
            onView(isRoot()).perform(WaitUtils.waitForElement(R.id.main_menu_image_button, 10000));
        } catch (Throwable t) {
            login(login, password);
            checkNewsScreenLoaded();
        }
    }

    public void login(String login, String password) {
        Allure.step("Ввод логина и пароля");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.login_text_input_layout, 15000));

        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.login_text_input_layout))))
                .perform(replaceText(login), closeSoftKeyboard());

        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.password_text_input_layout))))
                .perform(replaceText(password), closeSoftKeyboard());

        Allure.step("Нажатие кнопки 'SIGN IN'");
        onView(withId(R.id.enter_button)).perform(click());
    }

    public void checkNewsScreenLoaded() {
        Allure.step("Проверка успешной авторизации (появление меню новостей)");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.main_menu_image_button, 10000));
        onView(withId(R.id.main_menu_image_button)).check(matches(isDisplayed()));
    }

    public void checkErrorToastIsDisplayed() {
        Allure.step("Проверка появления ошибки (кнопка входа остается на экране)");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.enter_button, 5000));
        onView(withId(R.id.enter_button)).check(matches(isDisplayed()));
    }

    public void logout() {
        Allure.step("Выход из аккаунта (Log out)");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.authorization_image_button, 5000));
        onView(withId(R.id.authorization_image_button)).perform(click());
        onView(isRoot()).perform(WaitUtils.waitForElement(android.R.id.title, 5000));
        onView(allOf(withId(android.R.id.title), withText("Log out"))).perform(click());
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.enter_button, 10000));
    }
}
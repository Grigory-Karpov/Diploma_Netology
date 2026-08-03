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

    public void login(String login, String password) {
        Allure.step("Ввод логина и пароля");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.login_text_input_layout, 8000));

        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.login_text_input_layout))))
                .perform(replaceText(login), closeSoftKeyboard());

        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.password_text_input_layout))))
                .perform(replaceText(password), closeSoftKeyboard());

        Allure.step("Нажатие кнопки 'SIGN IN'");
        onView(withId(R.id.enter_button)).perform(click());
    }

    public void checkNewsScreenLoaded() {
        Allure.step("Проверка успешной авторизации (появление меню новостей)");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.main_menu_image_button, 8000));
        onView(withId(R.id.main_menu_image_button)).check(matches(isDisplayed()));
    }

    public void checkErrorToastIsDisplayed() {
        Allure.step("Проверка появления ошибки (кнопка входа остается на экране)");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.enter_button, 5000));
        onView(withId(R.id.enter_button)).check(matches(isDisplayed()));
    }

    public void logout() {
        Allure.step("Выход из аккаунта (Log out)");
        onView(withId(R.id.authorization_image_button)).perform(click());
        // Ждем менюшку профиля
        onView(isRoot()).perform(WaitUtils.waitForElement(android.R.id.title, 3000));
        onView(allOf(withId(android.R.id.title), withText("Log out"))).perform(click());

        // САМОЕ ГЛАВНОЕ: Ждем, пока на экране появится кнопка SIGN IN.
        // Это гарантирует, что мы точно вышли из приложения до начала следующего теста!
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.enter_button, 8000));
    }
}
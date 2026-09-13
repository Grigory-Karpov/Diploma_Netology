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
import static org.hamcrest.Matchers.anyOf;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.utils.ToastMatcher;
import ru.iteco.fmhandroid.utils.WaitUtils;

public class AuthSteps {

    public void ensureLoggedOut() {
        Allure.step("Подготовка: проверяем, что мы разлогинены");
        try {
            // Ждем появления кнопки профиля (авторизации), если мы уже залогинены
            onView(isRoot()).perform(WaitUtils.waitForElement(R.id.authorization_image_button, 5000));
            logout();
        } catch (Exception e) {
            // Если кнопки профиля нет, значит уже на экране логина — ждем кнопку входа
            try {
                onView(isRoot()).perform(WaitUtils.waitForElement(R.id.enter_button, 5000));
            } catch (Exception ignored) {
            }
        }
    }

    public void ensureLoggedIn(String login, String password) {
        Allure.step("Подготовка: проверяем, что мы залогинены");
        try {
            // Проверяем наличие главного меню, если уже в системе
            onView(isRoot()).perform(WaitUtils.waitForElement(R.id.main_menu_image_button, 5000));
        } catch (Exception e) {
            // Если не залогинены — логинимся
            login(login, password);
            checkNewsScreenLoaded();
        }
    }

    public void login(String login, String password) {
        Allure.step("Ввод логина: " + login + " и пароля: " + password);
        // Ожидание загрузки полей экрана авторизации
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.login_text_input_layout, 10000));

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

    public void logout() {
        Allure.step("Выход из аккаунта (Log out)");
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.authorization_image_button, 5000));
        onView(withId(R.id.authorization_image_button)).perform(click());

        onView(isRoot()).perform(WaitUtils.waitForElement(android.R.id.title, 5000));
        onView(allOf(withId(android.R.id.title), withText("Log out"))).perform(click());

        // Ждем возврата на экран входа (кнопки SIGN IN)
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.enter_button, 10000));
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
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {
                }
            }
        }
        if (!found) {
            throw new AssertionError("Всплывающее окно с текстом ошибки не найдено!");
        }
    }
}

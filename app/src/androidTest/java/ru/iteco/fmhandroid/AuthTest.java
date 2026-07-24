package ru.iteco.fmhandroid;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

// Импорты для красивых отчетов Allure
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;

import ru.iteco.fmhandroid.ui.AppActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.supportsInputMethods;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@Epic("Тестирование UI приложения Мобильный Хоспис")
@Feature("Авторизация")
public class AuthTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Test
    @Story("Успешный вход в систему")
    @Description("Проверка того, что при вводе валидного логина и пароля открывается главный экран с новостями")
    public void testSuccessfulLogin() {
        try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }

        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.login_text_input_layout))))
                .perform(replaceText("login2"), closeSoftKeyboard());

        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.password_text_input_layout))))
                .perform(replaceText("password2"), closeSoftKeyboard());

        onView(withId(R.id.enter_button)).perform(click());

        try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }

        onView(withText("News")).check(matches(isDisplayed()));

        // Выход из аккаунта
        onView(withId(R.id.authorization_image_button)).perform(click());
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        onView(withText("Log out")).perform(click());
    }

    @Test
    @Story("Неуспешный вход в систему (неверный пароль)")
    @Description("Проверка того, что при вводе неверного пароля пользователь остается на экране авторизации")
    public void testInvalidLogin() {
        try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }

        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.login_text_input_layout))))
                .perform(replaceText("login2"), closeSoftKeyboard());

        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.password_text_input_layout))))
                .perform(replaceText("wrong_password"), closeSoftKeyboard());

        onView(withId(R.id.enter_button)).perform(click());

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        onView(withId(R.id.enter_button)).check(matches(isDisplayed()));
    }
}
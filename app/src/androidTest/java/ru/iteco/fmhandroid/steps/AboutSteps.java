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
        // Ждем пока кнопка бургер-меню появится на экране (динамическое ожидание до 5 секунд)
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.main_menu_image_button, 5000));
        // Выполняем клик по бургер-меню
        onView(withId(R.id.main_menu_image_button)).perform(click());

        Allure.step("Выбор раздела About в меню");
        // Заменили Thread.sleep на умное ожидание загрузки системного элемента списка (title)
        onView(isRoot()).perform(WaitUtils.waitForElement(android.R.id.title, 3000));
        // Ищем в выпавшем меню пункт с текстом "About" и кликаем по нему
        onView(withText("About")).perform(click());
    }

    public void checkAboutScreenLoaded() {
        Allure.step("Проверка загрузки экрана About");
        // Ждем появления текста версии (что означает успешную загрузку экрана)
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.about_version_title_text_view, 7000));
        // Убеждаемся, что элемент с версией действительно отображается
        onView(withId(R.id.about_version_title_text_view)).check(matches(isDisplayed()));
    }

    public void goBack() {
        Allure.step("Возврат на предыдущий экран");
        // Имитируем системную кнопку "Назад" на устройстве
        pressBack();
        // Убеждаемся, что мы вернулись на главный экран (ждем появления меню)
        onView(isRoot()).perform(WaitUtils.waitForElement(R.id.main_menu_image_button, 5000));
    }
}
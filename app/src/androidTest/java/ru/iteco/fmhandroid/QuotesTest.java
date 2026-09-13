package ru.iteco.fmhandroid;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.steps.AuthSteps;
import ru.iteco.fmhandroid.steps.QuotesSteps;
import ru.iteco.fmhandroid.ui.AppActivity;

@RunWith(AllureAndroidJUnit4.class)
@Epic("Тестирование UI")
@Feature("Раздел Цитаты")
public class QuotesTest {

    AuthSteps authSteps = new AuthSteps();
    QuotesSteps quotesSteps = new QuotesSteps();

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        authSteps.ensureLoggedIn("login2", "password2");
    }

    @Test
    @Story("Открытие экрана Цитат")
    public void testOpenQuotesScreen() {
        quotesSteps.openQuotesScreen();
        quotesSteps.checkQuotesScreenLoaded();
    }
}
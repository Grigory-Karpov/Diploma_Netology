package ru.iteco.fmhandroid;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import ru.iteco.fmhandroid.steps.AboutSteps;
import ru.iteco.fmhandroid.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.AppActivity;

@RunWith(AndroidJUnit4.class)
@Epic("Тестирование UI")
@Feature("Раздел About")
public class AboutTest {
    AuthSteps authSteps = new AuthSteps();
    AboutSteps aboutSteps = new AboutSteps();

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule = new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        try { Thread.sleep(3000); } catch (InterruptedException e) {}
        authSteps.ensureLoggedIn("login2", "password2");
    }

    @Test
    @Story("Открытие экрана About")
    public void testOpenAboutScreen() {
        aboutSteps.openAboutScreen();
        aboutSteps.checkAboutScreenLoaded();
        aboutSteps.goBack();
    }
}
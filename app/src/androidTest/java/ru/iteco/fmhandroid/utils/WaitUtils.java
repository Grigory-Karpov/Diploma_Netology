package ru.iteco.fmhandroid.utils;

import android.view.View;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;
import org.hamcrest.Matcher;
import java.util.concurrent.TimeoutException;

public class WaitUtils {
    // Этот метод заставляет тест ждать появления элемента (viewId) максимум millis миллисекунд
    public static ViewAction waitForElement(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() { return ViewMatchers.isRoot(); }
            @Override
            public String getDescription() { return "Ожидание элемента " + viewId + " в течение " + millis + " мс"; }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> matchId = ViewMatchers.withId(viewId);
                final Matcher<View> matchDisplayed = ViewMatchers.isDisplayed();
                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        if (matchId.matches(child) && matchDisplayed.matches(child)) { return; }
                    }
                    uiController.loopMainThreadForAtLeast(50);
                } while (System.currentTimeMillis() < endTime);
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }
}
package de.dhbw.towerdefense;

import androidx.test.rule.ActivityTestRule;

import com.mauriciotogneri.greencoffee.GreenCoffeeConfig;
import com.mauriciotogneri.greencoffee.GreenCoffeeTest;
import com.mauriciotogneri.greencoffee.ScenarioConfig;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;

import de.dhbw.activities.MainActivity;
import de.dhbw.towerdefense.steps.Test_Steps_StartTheGameFromTheMenu;

@RunWith(Parameterized.class)
public class Test_StartTheGameFromTheMenu extends GreenCoffeeTest {

    @Rule
    public ActivityTestRule<MainActivity> activity = new ActivityTestRule<>(MainActivity.class);

    public Test_StartTheGameFromTheMenu(ScenarioConfig scenario) {
        super(scenario);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<ScenarioConfig> scenarios() throws IOException
    {
        return new GreenCoffeeConfig()
                .withFeatureFromAssets("assets/features/StartTheGameFromTheMenu.feature")
                .takeScreenshotOnFail()
                .withTags("@easy")
                .scenarios();
    }

    @Test
    public void test()
    {
        start(new Test_Steps_StartTheGameFromTheMenu());
    }

}
